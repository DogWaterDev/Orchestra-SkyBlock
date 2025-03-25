package de.dogwaterdev.worldframework;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.dogwaterdev.komodoperms.RequiresPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static de.dogwaterdev.skyblock.SkyBlock.log;

public class WorldManager {
    private static HikariDataSource dataSource;

    /**
     * Creates the HikariCP connection and creates if not already existing the tables
     */
    public static void initDatabase(String dbPath, String dbUser, String dbPassword) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + dbPath + "?useSSL=false");
        config.setUsername(dbUser);
        config.setPassword(dbPassword);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(600000);
        config.setConnectionTimeout(10000);
        config.setLeakDetectionThreshold(5000);

        dataSource = new HikariDataSource(config);
        log("Database connection initialized.");
        log("Creating if not exists TABLES");
        try (Connection conn = getConnection()) {
            executeStatement(conn, "CREATE TABLE IF NOT EXISTS world ("
                    + "uuid BINARY(16) PRIMARY KEY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "sizeX INT DEFAULT 16, "
                    + "sizeZ INT DEFAULT 16, "
                    + "type VARCHAR(100) NOT NULL, "
                    + "config_id BINARY(16) NOT NULL, "
                    + "permissions_id BINARY(16) NOT NULL, "
                    + "FOREIGN KEY (config_id) REFERENCES config(uuid), "
                    + "FOREIGN KEY (permissions_id) REFERENCES permissions(uuid))");

            executeStatement(conn, "CREATE TABLE IF NOT EXISTS config ("
                    + "uuid BINARY(16) PRIMARY KEY, "
                    + "spawnX INT DEFAULT 0, "
                    + "spawnY INT DEFAULT 0, "
                    + "spawnZ INT DEFAULT 0, "
                    + "damageEnabled BOOLEAN DEFAULT 1, "
                    + "items_id BINARY(16) NOT NULL, "
                    + "FOREIGN KEY (items_id) REFERENCES items(uuid))");

            executeStatement(conn, "CREATE TABLE IF NOT EXISTS permissions ("
                    + "uuid BINARY(16) PRIMARY KEY)");

            executeStatement(conn, "CREATE TABLE IF NOT EXISTS permissions_admins ("
                    + "permissions_id BINARY(16) NOT NULL, "
                    + "admin_uuid BINARY(16) NOT NULL, "
                    + "FOREIGN KEY (permissions_id) REFERENCES permissions(uuid))");

            executeStatement(conn, "CREATE TABLE IF NOT EXISTS items ("
                    + "uuid BINARY(16) PRIMARY KEY, "
                    + "id INT NOT NULL, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "slot INT NOT NULL, "
                    + "lore TEXT)");

            log("Database tables verified/created successfully.");
        } catch (SQLException e) {
            log("Error while creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Executes a SQL statement without returning results.
     *
     * @param conn Connection object
     * @param sql SQL query to execute
     */
    private static void executeStatement(Connection conn, String sql) {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            log("Failed to execute statement: " + sql);
            e.printStackTrace();
        }
    }
    /**
     * Gets a connection from the pool.
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    /**
     * Makes the HikariCP connection go to sleep.
     */
    public static void closeDatabase() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log("Database connection closed.");
        }
    }

    /**
     *
     * @param worldName Name of the world to add
     * @param sizeX How many chunks in X?
     * @param sizeZ How many chunks in Z?
     * @param type What type of World is it? VoidWorld, DefaultWorld, etc.
     * @param configId
     * @param permissionsId
     */
    public void addWorld(String worldName, int sizeX, int sizeZ, String type, UUID configId, UUID permissionsId) {
        String sql = "INSERT INTO world (uuid, name, sizeX, sizeZ, type, config_id, permissions_id) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, UUID_TO_BIN(?), UUID_TO_BIN(?))";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, worldName);
            stmt.setInt(3, sizeX);
            stmt.setInt(4, sizeZ);
            stmt.setString(5, type);
            stmt.setString(6, configId.toString());
            stmt.setString(7, permissionsId.toString());

            stmt.executeUpdate();
            log("World added successfully: " + worldName);
        } catch (Exception e) {
            log("Failed to add world: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Renames a world. Only this class can call the `renameWorld` method in `CustomWorld` for protection from dummies.
     *
     * @param worldUUID   The world to rename (Enter its UUID)
     * @param newName the {@code String} name to which the function should be renamed to
     * @param caller Who ({@code org.bukkit.Player}) is trying to rename this because they were dumb when creating the world?
     *
     */
    @RequiresPermission(permission = "worldframework.rename")
    public static void renameCustomWorld(UUID worldUUID, String newName, Player caller) {
        CustomWorld world = null;
        String prevName = world.getName();
        if (world.renameWorld(newName, caller)) {
            caller.sendMessage(Component.text(
                    String.format("%s object previously known as %s renamed to %s.",
                            world.getClass().getName(), prevName, newName),
                    TextColor.color(120, 190, 36)));
        } else {
            caller.sendMessage(Component.text(
                    String.format("Failure to rename %s object %s to %s.",
                            world.getClass().getName(), prevName, newName),
                    TextColor.color(199, 17, 17)));
        }
    }

    /** Creates a new custom world +
     *
      * @param type
     * @param name
     * @param seed
     * @param chunksX
     * @param chunksZ
     * @param creator
     */
    @RequiresPermission(permission = "worldframework.create")
    public static void createWorld(CustomWorldTypes type, String name, Long seed, int chunksX, int chunksZ, UUID creator) {
        WorldTypeMeta meta = type.getMeta();

        if (meta == null) {
            throw new IllegalArgumentException("World type " + type + " does not have @WorldTypeMeta annotation!");
        }

        log("Creating world of type: " + meta.name() + " (custom map: " + meta.customMap() + ")");

        try {
            CustomWorld customWorld = type.getWorldClass()
                    .getConstructor(String.class, Long.class, int.class, int.class, UUID.class)
                    .newInstance(name, seed, chunksX, chunksZ, creator);
            World world = customWorld.generateWorld();

            if (world == null) {
                throw new RuntimeException("Failed to load world: " + name);
            }

            System.out.println("World " + name + " has been successfully created and loaded!");

        } catch (Exception e) {
            throw new RuntimeException("Failed to create world of type " + type + ": " + e.getMessage(), e);
            }
        }

}
