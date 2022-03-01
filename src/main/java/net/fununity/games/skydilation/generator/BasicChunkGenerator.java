package net.fununity.games.skydilation.generator;

import net.fununity.games.skydilation.generator.populator.GrassPopulator;
import net.fununity.games.skydilation.generator.populator.OrePopulator;
import net.fununity.games.skydilation.generator.populator.TreePopulator;
import net.fununity.main.api.common.util.RandomUtil;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.*;

public class BasicChunkGenerator extends ChunkGenerator {

    protected final Map<int[], Biomes> generateAbleChunks;
    private final List<BlockPopulator> populators;

    public BasicChunkGenerator() {
        this.generateAbleChunks = new HashMap<>();
        this.populators = Arrays.asList(new TreePopulator(), new GrassPopulator(), new OrePopulator());
    }

    public boolean containsChunk(Chunk chunk) {
        return getSkyDilationBiome(chunk.getX(), chunk.getZ()) != null;
    }

    public void generateChunk(Chunk chunk) {
        generateChunk(chunk, Biomes.values()[RandomUtil.getRandomInt(Biomes.values().length)]);
    }

    public void generateChunk(Chunk chunk, Biomes biome) {
        this.generateAbleChunks.put(new int[]{chunk.getX(), chunk.getZ()}, biome);
        System.out.println("Generating new chunk " + chunk.getX() + " " + chunk.getZ() + "  " + biome.biome + chunk.getWorld().getName());
        chunk.getWorld().unloadChunk(chunk);
        chunk.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
    }

    public Biomes getSkyDilationBiome(Chunk chunk) {
        return getSkyDilationBiome(chunk.getX(), chunk.getZ());
    }

    private Biomes getSkyDilationBiome(int x, int z) {
        Map.Entry<int[], Biomes> entry = this.generateAbleChunks.entrySet().stream().filter(e -> e.getKey()[0] == x && e.getKey()[1] == z).findFirst().orElse(null);
        return entry != null ? entry.getValue() : null;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
        ChunkData chunk = createChunkData(world);
        Biomes biome = getSkyDilationBiome(chunkX, chunkZ);

        if (biome == null)
            return chunk;

        Biomes.setWorld(world);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int realX = x + chunkX * 16;
                int realZ = z + chunkZ * 16;

                //We get the 3 closest biome's to the temperature and rainfall at this block
                //Map<Biomes, Double> biomes = biomeGenerator.getBiomes(realX, realZ);
                //And tell bukkit (who tells the client) what the biggest biome here is
                biomeGrid.setBiome(x, z, biome.biome);

                // To make it more maintainable, we've abstracted finding height
                // and density values
                int height = getHeight(realX, realZ, biome);

                int bedrockY = 2 + RandomUtil.getRandomInt(3);
                for (int y = 0; y < bedrockY; y++)
                    chunk.setBlock(x, y, z, Material.BEDROCK);

                int subtractHeight = 3 + RandomUtil.getRandomInt(2);
                for (int y = bedrockY + 1; y < height - subtractHeight; y++) {
                    chunk.setBlock(x, y, z, Material.STONE);
                }

                Material bottomMaterial = biome.getBottomMaterial();
                for (int y = height - subtractHeight; y < height; y++)
                    chunk.setBlock(x, y, z, bottomMaterial);

                chunk.setBlock(x, height, z, biome.getTopMaterial());
            }
        }
        return chunk;
    }

    private int getHeight(int x, int z, Biomes biomes) {
        return (int) (biomes.getGenerator().get2DNoise(x, z) + 64);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return populators;
    }
}