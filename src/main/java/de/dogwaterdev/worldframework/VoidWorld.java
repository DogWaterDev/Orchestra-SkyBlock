package de.dogwaterdev.worldframework;

import de.dogwaterdev.worldframework.VoidChunkGenerator;
import org.bukkit.World;
import org.bukkit.WorldCreator;


import java.util.UUID;


class VoidWorld extends CustomWorld {
    private WorldCreator creator;
    VoidWorld(String name, Long seed, int length, int width, UUID creator) {
        super(name, seed, length, width, creator);
    }

    VoidWorld(String name, Long seed, int length, int width) {
        super(name, seed, length, width);
    }

    @Override
    public World generateWorld() {
        World world = null;
        creator = new WorldCreator(getName());
        creator.generateStructures(false);
        creator.generator(new VoidChunkGenerator());

        return world;
    }

    @Override
    public void defineWorldUUID() {

    }
}
