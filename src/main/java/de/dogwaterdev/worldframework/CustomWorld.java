package de.dogwaterdev.worldframework;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

abstract class CustomWorld {
    private String name;
    private UUID worldUUID;
    private final Long seed;
    private int length;
    private int width;
    private final UUID creator;
    private final WorldCreator worldCreator;
    private WorldSettings settings;

    protected CustomWorld(String name, Long seed, int length, int width, UUID creator) {
        this(name, seed, length, width, creator, null); // Calls the main constructor with settings as null
    }

    protected CustomWorld(String name, Long seed, int length, int width, UUID creator, WorldSettings settings) {
        this.name = name;
        this.seed = seed;
        this.length = length;
        this.width = width;
        this.creator = creator;
        this.settings = settings;
        this.worldCreator = new WorldCreator(name);

        if (Bukkit.getServer().getWorlds().stream().anyMatch(world -> world.getName().equals(name))) {
            Bukkit.getLogger().log(Level.WARNING, "Unable to initialize new World object with name " + name +
                    " as World object with this name already exists, assuming creator " + creator +
                    " intended to load world, loading as new CustomWorld object.");
        }
    }

    protected CustomWorld(String name, Long seed, int length, int width) {
        this(name, seed, length, width, UUID.fromString("Console")); // Calls constructor with "Console" creator
    }

    protected abstract World generateWorld();
    protected abstract void defineWorldUUID();

    UUID getUUID() {
        return worldUUID;
    }
    protected String getName() {
        return name;
    }

    protected WorldSettings getSettings() {
        return settings;
    }

    protected void setSettings(WorldSettings settings) {
        this.settings = settings;
    }


    /**
     * @param newName the {@code String} name to which the function should be renamed to
     * @param caller Who ({@code org.bukkit.Player}) is trying to rename this because they were dumb when creating the world?
     * @return boolean value whether it was successful.
     */
    boolean renameWorld(String newName, Player caller) {
        //bitchass forgot to give it the correct name smh
        try {
            Bukkit.getServer().unloadWorld(Objects.requireNonNull(Bukkit.getWorld(getUUID())), true);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE,
                    String.format("Internal Error, CustomWorld unable to unload %s for renaming. Stack: %s",
                            name,
                            Arrays.toString(e.getStackTrace())));
            return false;
        }
        File oldFolder = new File(Bukkit.getWorldContainer(), name);
        File newFolder = new File(Bukkit.getWorldContainer(), newName);

        if (!oldFolder.exists()) {
            System.out.println("World folder not found: " + name);
            return false;
        }

        try {
            Files.move(oldFolder.toPath(), newFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("World renamed from " + name + " to " + newName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        WorldCreator creator = new WorldCreator(newName);
        World newWorld = creator.createWorld();

        if (newWorld != null) {
            System.out.println("Successfully loaded world: " + newName);
            name = newName;
            return true;
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to load world: " + newName);
            return false;
        }

    }


}
