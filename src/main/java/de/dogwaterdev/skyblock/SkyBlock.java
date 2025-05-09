//package org.dogwaterdev.skyblock;
//
//import org.bukkit.Bukkit;
//import org.bukkit.World;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.entity.Player;
//import org.bukkit.plugin.java.JavaPlugin;
//import org.dogwaterdev.skyblock.commands.*;
//import org.dogwaterdev.skyblock.events.PlayerDropItem;
//import org.dogwaterdev.skyblock.events.PlayerJoin;
//
//import java.util.ArrayList;
//import java.util.logging.Level;
//
//public final class SkyBlock extends JavaPlugin {
//    public static World world ;
//    public static FileConfiguration config;
//
//
//    @Override
//    public void onEnable() {
//        world = Bukkit.getWorld("skyblock");
//        saveDefaultConfig();
//        config = getConfig();
//        // register da commandos con kawasaki, kago, kriko, iestriper
//        getCommand("sbspawn").setExecutor((new Spawn()));
//
//
//        // register da eventos sin kawasaki, kago, kriko, iestriper
//        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
//        getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
//
//
//        // logging
//        Bukkit.getLogger().log(Level.INFO, "SkyBlock starting...");
//
//    }
//
//    public ArrayList<Player> getTeleportingPlayers() {
//        return this.teleportingPlayers;
//    }
//
//    @Override
//    public void onDisable() {
//        Bukkit.getLogger().log(Level.INFO, "SkyBlock disabling...");
//    }
//}

package de.dogwaterdev.skyblock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import de.dogwaterdev.komodoperms.PermissionsChecker;
import de.dogwaterdev.skyblock.commands.SkyBlockCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;


import org.mineacademy.fo.plugin.SimplePlugin;

import pro_crafting.commandframework.CommandArgs;
import pro_crafting.commandframework.CommandFramework;
import pro_crafting.commandframework.Completer;


public class SkyBlock extends SimplePlugin implements Listener {
    private static final Logger log = Logger.getLogger("Minecraft");
    public static final String chatPrefix = "「SkyBlock」 |";
    private CommandFramework cmdFramework;

    public SkyBlock() {super();}

    @Override
    protected void onPluginStart() {

        log("test");

        PermissionsChecker.check("de.dogwaterdev");
        cmdFramework = new CommandFramework(this);
        cmdFramework.setInGameOnlyMessage(chatPrefix + "Dieser Befehl darf nur Ingame verwendet werden!");
        cmdFramework.setNoPermMessage("Unknown command. Type /help for help.");
        cmdFramework.registerCommands(new SkyBlockCommands());

        Bukkit.getPluginManager().registerEvents(this, this);


        Method[] methods = this.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equalsIgnoreCase("completeCommands")) {
                this.cmdFramework.registerCompleter("skyblock", m, this);
            }
        }

    }
    @Override
    protected void onReloadablesStart() {

        // You can check for necessary plugins and disable loading if they are missing
        //Valid.checkBoolean(HookManager.isVaultLoaded(), "You need to install Vault so that we can work with packets, offline player data, prefixes and groups.");

        // Uncomment to load variables
        // Variable.loadVariables();

        //
        // Add your own plugin parts to load automatically here
        // Please see @AutoRegister for parts you do not have to register manually
    }

    @Override
    protected void onPluginPreReload() {

        //Database.getInstance().close();
    }

    public static void log(final String message){
        Bukkit.getConsoleSender().sendMessage(chatPrefix + message);
    }

    @Completer(name = "skyblock")
    public List<String> completeCommands2(CommandArgs args) {
        List<String> ret = new ArrayList<String>();
        String label = args.getCommand().getLabel();
        for (String arg : args.getArgs()) {
            label += " " + arg;
        }
        for (String currentLabel : cmdFramework.getCommandLabels()) {
            String current = currentLabel.replace('.', ' ');
            if (currentLabel.contains("skyblock"))
                continue;
            if (current.contains(label)) {
                current = current.substring(label.lastIndexOf(' ')).trim();
                current = current.substring(0, current.indexOf(' ') != -1 ? current.indexOf(' ') : current.length())
                        .trim();
                if (!ret.contains(current)) {
                    ret.add(current);
                }
            }
        }
        return ret;
    }

    public static class CommandInjector {
        private static String packageName = Bukkit.getServer().getClass().getPackage().getName();
        private static String version;

        static {
            version = packageName.substring(packageName.lastIndexOf(".") + 1);
        }

        @SuppressWarnings("rawtypes")
        public static void injectCommand(Command cmd) throws Exception {
            Class serverClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
            Field f1 = serverClass.getDeclaredField("commandMap");
            f1.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) f1.get(Bukkit.getServer());
            commandMap.register("skyblock", cmd);
        }
    }

    public static SkyBlock getInstance() {
        return (SkyBlock) SimplePlugin.getInstance();
    }

    public static void log(String section, String msg) {
        Bukkit.getConsoleSender().sendMessage( chatPrefix + " " + section + "] " + msg);
    }

}