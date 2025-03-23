package de.dogwaterdev.skyblock.menus;

import org.mineacademy.fo.menu.Menu;

public class SettingsMenu extends Menu {
    SettingsMenu(Menu previous) {
        super(previous);
        setTitle("&d「SkyBlock」 &1-- &7 Settings");
        setSize(9 * 3);
    }
    public SettingsMenu() {
        setTitle("&d「SkyBlock」 &1-- &7 Settings");
        setSize(9 * 3);
    }
}
