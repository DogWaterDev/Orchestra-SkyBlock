package de.dogwaterdev.skyblock.menus;

import org.mineacademy.fo.menu.Menu;

public class TeleportMenu extends Menu {
    TeleportMenu(Menu previous) {
        super(previous);
        setTitle("&d「SkyBlock」 &1-- &6 Teleports");
        setSize(9 * 3);
    }
    public TeleportMenu() {
        setTitle("&d「SkyBlock」 &1-- &6 Teleports");
        setSize(9 * 3);
    }
}