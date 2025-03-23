package de.dogwaterdev.worldframework;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class WorldSettings {
    private int maxPlayers;
    private Location spawnpoint;
    private List<ItemStack> startingItems;

    public WorldSettings(int maxPlayers, Location spawnpoint, List<ItemStack> startingItems) {
        this.maxPlayers = maxPlayers;
        this.spawnpoint = spawnpoint;
        this.startingItems = startingItems;
    }

    // Getters & Setters
    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Location getSpawnpoint() {
        return spawnpoint;
    }

    public void setSpawnpoint(Location spawnpoint) {
        this.spawnpoint = spawnpoint;
    }

    public List<ItemStack> getStartingItems() {
        return startingItems;
    }

    public void setStartingItems(List<ItemStack> startingItems) {
        this.startingItems = startingItems;
    }
}