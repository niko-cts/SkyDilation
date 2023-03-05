package net.fununity.games.skydilation.chunkgeneration.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.chunkgeneration.distribution.SkyDilationDistribution;
import org.bukkit.*;
import org.bukkit.generator.BlockPopulator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

/**
 * This class represents a standard populator class for the chunk generation.
 * It provides a random selection to generate a block based on the {@link SkyDilationDistribution}.
 * See the other populators, which extend this class.
 * @author Niko
 * @since 0.0.2
 */
public abstract class SkyDilationPopulator extends BlockPopulator {

    protected EnumMap<Biomes, List<SkyDilationDistribution>> distributionBiomes;

    public SkyDilationPopulator(EnumMap<Biomes, List<SkyDilationDistribution>> distribution) {
        this.distributionBiomes = distribution;
    }

    /**
     * Is called, when the given distribution was successful and a coordinate was selected.
     * @param world World - world to spawn in.
     * @param random Random - the random from the block populator
     * @param chunk Chunk - the chunk to spawn in.
     * @param x int - x coordinate to spawn
     * @param y int - y coordinate to spawn
     * @param z int - z coordinate to spawn
     * @param distribution SkyDilationDistribution - the given distribution to spawn an element.
     * @since 0.0.2
     */
    public abstract void generateAtPosition(World world, Random random, Chunk chunk, int x, int y, int z, SkyDilationDistribution distribution);

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        Biomes biome = SkyDilation.getInstance().getGenerator().getSkyDilationBiome(chunk.getX(), chunk.getZ());
        if (biome == null) return;
        for (SkyDilationDistribution distribution : this.distributionBiomes.getOrDefault(biome, new ArrayList<>())) {
            for (int t = 0; t < distribution.getTries(); t++) {
                if (random.nextFloat() * 100F <= distribution.getPercentage()) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);
                    int y = world.getMaxHeight() - 1;
                    while (chunk.getBlock(x, y, z).getType() == Material.AIR && y > 0) { // Find the highest block of the (X,Z) coordinate chosen.
                        y--;
                    }
                    if (biome.containsTopMaterial(chunk.getBlock(x, y, z).getType())) {
                        generateAtPosition(world, random, chunk, x, y + 1, z, distribution);
                    }
                }
            }
        }
    }

}
