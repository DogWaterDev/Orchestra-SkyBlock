package de.dogwaterdev.skyblock.menus;

import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.remain.CompMaterial;

public class SkyBlockMenu  extends Menu {

    private final Button helpButton;
    private final Button teleportButton;
    private final Button settingsButton;

    // wrote it like this so can be changed to config later
    private final int helpButtonSlot = 11;
    private final int teleportButtonSlot = 13;
    private final int settingsButtonSlot = 15;

    private static SkyBlockMenu menu;

    public SkyBlockMenu() {
        menu = this;
        setTitle("&d「SkyBlock」");
        setSize(9 * 3);


        this.helpButton = new ButtonMenu(new HelpMenu(getSelf()), CompMaterial.DEAD_BUSH,
                "&1Help Menu",
                "&eClick this button to open the help menu!");

        this.teleportButton = new ButtonMenu(new TeleportMenu(getSelf()), CompMaterial.ENDER_PEARL,
                "&6Teleport Menu",
                "&eClick this button to open the teleport menu!");

        this.settingsButton = new ButtonMenu(new SettingsMenu(getSelf()), CompMaterial.COMMAND_BLOCK,
                "&7Settings",
                "&eClick this button to open the settings menu!");
    }

    public static SkyBlockMenu getSelf() {
        return menu;
    }

    @Override
    public ItemStack getItemAt(int slot) {
        if (slot == helpButtonSlot) {
            return helpButton.getItem();
        } else if (slot == teleportButtonSlot) {
            return teleportButton.getItem();
        } else if (slot == settingsButtonSlot) {
            return settingsButton.getItem();
        }
        return super.getItemAt(slot);
    }

}
