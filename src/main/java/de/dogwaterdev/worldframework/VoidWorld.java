package de.dogwaterdev.worldframework;

import de.dogwaterdev.worldframework.VoidChunkGenerator;
import org.bukkit.World;
import org.bukkit.WorldCreator;


import java.util.UUID;


@WorldTypeMeta(name = "Void World", customMap = false)
public class VoidWorld extends CustomWorld {
    private WorldCreator creator;
    public VoidWorld(String name, Long seed, int chunksX, int chunksZ, UUID creator) {
        super(name, seed, chunksX, chunksZ, creator);
    }

    public VoidWorld(String name, Long seed, int chunksX, int chunksZ) {
        super(name, seed, chunksX, chunksZ);
    }

    @Override
    public World generateWorld() {
        World world = null;
        creator = new WorldCreator(getName());
        creator.generateStructures(false);
        creator.generator(new VoidChunkGenerator());
        world = creator.createWorld();

        return world;
    }

    @Override
    public void defineWorldUUID() {

    }
}
