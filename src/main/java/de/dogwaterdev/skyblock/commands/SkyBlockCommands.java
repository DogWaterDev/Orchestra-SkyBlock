package de.dogwaterdev.skyblock.commands;


import org.bukkit.entity.Player;

import de.dogwaterdev.skyblock.menus.HelpMenu;
import de.dogwaterdev.skyblock.menus.SettingsMenu;
import de.dogwaterdev.skyblock.menus.SkyBlockMenu;

import de.dogwaterdev.skyblock.menus.TeleportMenu;
import pro_crafting.commandframework.Command;
import pro_crafting.commandframework.CommandArgs;

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
}
