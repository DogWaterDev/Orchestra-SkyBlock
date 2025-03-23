package de.dogwaterdev.worldframework;

import de.dogwaterdev.komodoperms.RequiresPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WorldManager {

    /**
     * Renames a world. Only this class can call the `renameWorld` method in `CustomWorld` for protection from dummies.
     *
     * @param world   The world to rename.
     * @param newName The new name.
     * @param caller  The player requesting the rename.
     */
    @RequiresPermission(permission = "worldframework.rename")
    public static void renameCustomWorld(CustomWorld world, String newName, Player caller) {
        String prevName = world.getName();
         if (world.renameWorld(newName, caller)) {
             caller.sendMessage(Component.text(
                     String.format("%s object previously known as %s renamed to %s.",
                             world.getClass().getName(), prevName, newName),
                     TextColor.color(120, 190, 36)));
         }
         else {
             caller.sendMessage(Component.text(
                     String.format("Failure to rename %s object %s to %s.",
                             world.getClass().getName(), prevName, newName),
                     TextColor.color(199, 17, 17)));
         }
    }

    public static CustomWorld createWorld(WorldTypeMeta type, String name, Long seed, int chunksY, int chunksZ, UUID creator) {
        return null;
    }
}
