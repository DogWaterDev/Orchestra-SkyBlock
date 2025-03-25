package de.dogwaterdev.skyblock.commands;


import de.dogwaterdev.worldframework.CustomWorldTypes;
import de.dogwaterdev.worldframework.WorldManager;
import org.bukkit.entity.Player;

import de.dogwaterdev.skyblock.menus.HelpMenu;
import de.dogwaterdev.skyblock.menus.SettingsMenu;
import de.dogwaterdev.skyblock.menus.SkyBlockMenu;

import de.dogwaterdev.skyblock.menus.TeleportMenu;
import pro_crafting.commandframework.Command;
import pro_crafting.commandframework.CommandArgs;

import java.util.UUID;

import static de.dogwaterdev.skyblock.SkyBlock.log;

public class SkyBlockCommands{
    @Command(name = "skyblock", aliases = "sb")
    public void onSkyBlock(CommandArgs args) {
        if (!(args.getSender() instanceof Player)) {return;}
        new SkyBlockMenu().displayTo(args.getPlayer());
    }

    @Command(name = "sbhelp", aliases = "skyblockhelp")
    public void onHelp(CommandArgs args) {
        if (!(args.getSender() instanceof Player)) {return;}
        new HelpMenu().displayTo(args.getPlayer());
    }
    @Command(name = "sbtp", aliases = "sbteleport")
    public void onTeleport(CommandArgs args) {
        if (!(args.getSender() instanceof Player)) {return;}
        new TeleportMenu().displayTo(args.getPlayer());
    }
    @Command(name = "sbsettings")
    public void onSettings(CommandArgs args) {
        if (!(args.getSender() instanceof Player)) {return;}
        new SettingsMenu().displayTo(args.getPlayer());
    }
    @Command(name = "create")
    public void onCreateWorld(CommandArgs args) {
        if (args.length() == 1) {
            if (!(args.getSender() instanceof Player)) {
                log("Creating world");
                WorldManager.createWorld(CustomWorldTypes.VOID,
                        args.getArgs(0),
                        Long.valueOf("123123123"), 3750000, 3750000, UUID.fromString("allah"));
            }
            args.getPlayer().sendMessage("Creating world");
            WorldManager.createWorld(CustomWorldTypes.VOID,
                    args.getArgs(0),
                    Long.valueOf("123123123"), 3750000, 3750000, args.getPlayer().getUniqueId());
        }

    }
}
