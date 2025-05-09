package de.dogwaterdev.worldframework;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

class VoidChunkGenerator extends ChunkGenerator {
    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, @NotNull BiomeGrid biome) {
        return new ChunkData() {
            @Override
            public int getMinHeight() {
                return 0;
            }

            @Override
            public int getMaxHeight() {
                return 0;
            }

            @Override
            public @NotNull Biome getBiome(int i, int i1, int i2) {
                return null;
            }

            @Override
            public void setBlock(int i, int i1, int i2, @NotNull Material material) {

            }

            @Override
            public void setBlock(int i, int i1, int i2, @NotNull MaterialData materialData) {

            }

            @Override
            public void setBlock(int i, int i1, int i2, @NotNull BlockData blockData) {

            }

            @Override
            public void setRegion(int i, int i1, int i2, int i3, int i4, int i5, @NotNull Material material) {

            }

            @Override
            public void setRegion(int i, int i1, int i2, int i3, int i4, int i5, @NotNull MaterialData materialData) {

            }

            @Override
            public void setRegion(int i, int i1, int i2, int i3, int i4, int i5, @NotNull BlockData blockData) {

            }

            @Override
            public @NotNull Material getType(int i, int i1, int i2) {
                return null;
            }

            @Override
            public @NotNull MaterialData getTypeAndData(int i, int i1, int i2) {
                return null;
            }

            @Override
            public @NotNull BlockData getBlockData(int i, int i1, int i2) {
                return null;
            }

            @Override
            public byte getData(int i, int i1, int i2) {
                return 0;
            }
        };
    }
}
