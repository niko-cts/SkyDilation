package net.fununity.games.skydilation.chunkgeneration;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.chunkgeneration.populator.UpperBlockPopulator;
import net.fununity.games.skydilation.chunkgeneration.populator.OrePopulator;
import net.fununity.games.skydilation.chunkgeneration.populator.TreasurePopulator;
import net.fununity.games.skydilation.chunkgeneration.populator.TreePopulator;
import net.fununity.main.api.common.util.RandomUtil;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.*;
import java.util.logging.Level;

public class BasicChunkGenerator extends ChunkGenerator {

    private static final List<BlockPopulator> POPULATOR = Arrays.asList(new TreePopulator(), new UpperBlockPopulator(), new TreasurePopulator());
    private static final int NORMAL_Y = 30;
    private static final OrePopulator ORE_POPULATOR = new OrePopulator();

    private final TreeMap<Float, Biomes> biomesProbabilty;
    protected final Map<int[], Biomes> generateAbleChunks;

    public BasicChunkGenerator() {
        this.generateAbleChunks = new HashMap<>();
        this.biomesProbabilty = new TreeMap<>();
        float current = 0;
        FileConfiguration cfg = SkyDilation.getInstance().getConfig();
        for (String biome : cfg.getConfigurationSection("biomes.").getKeys(false)) {
            try {
                this.biomesProbabilty.put(current += cfg.getDouble("biomes." + biome), Biomes.valueOf(biome));
            } catch (IllegalArgumentException exception) {
                SkyDilation.getInstance().getLogger().log(Level.WARNING, "Biome is not registered: {0}", biome);
            }
        }
    }


    public boolean containsChunk(Chunk chunk) {
        return getSkyDilationBiome(chunk.getX(), chunk.getZ()) != null;
    }

    public void generateChunk(Chunk chunk) {
        generateChunk(chunk, getRandomBiome());
    }

    private Biomes getRandomBiome() {
        float chance = RandomUtil.getRandom().nextFloat();
        Map.Entry<Float, Biomes> entry = this.biomesProbabilty.ceilingEntry(chance);
        System.out.println("Biomes probability with " + chance + " hit " + entry);
        return entry != null ? entry.getValue() : Biomes.PLAINS;
    }

    public void generateChunk(Chunk chunk, Biomes biome) {
        this.generateAbleChunks.put(new int[]{chunk.getX(), chunk.getZ()}, biome);
        chunk.getWorld().unloadChunk(chunk.getX(), chunk.getZ(), false);
        chunk.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
    }

    public Biomes getSkyDilationBiome(int chunkX, int chunkZ) {
        Map.Entry<int[], Biomes> entry = this.generateAbleChunks.entrySet().stream().filter(e -> e.getKey()[0] == chunkX && e.getKey()[1] == chunkZ).findFirst().orElse(null);
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
                //We get the 3 closest biome's to the temperature and rainfall at this block
                //Map<Biomes, Double> biomes = biomeGenerator.getBiomes(realX, realZ);
                //And tell bukkit (who tells the client) what the biggest biome here is
                biomeGrid.setBiome(x, z, biome.biome);

                int realX = x + chunkX * 16;
                int realZ = z + chunkZ * 16;
                // To make it more maintainable, we've abstracted finding height
                // and density values
                int height = (int) (biome.getGenerator().get2DNoise(realX, realZ) + NORMAL_Y);

                int bedrockY = 2 + RandomUtil.getRandomInt(3);
                for (int y = 0; y <= bedrockY; y++)
                    chunk.setBlock(x, y, z, Material.BEDROCK);

                int subtractHeight = 2 + RandomUtil.getRandomInt(3);
                for (int y = bedrockY + 1; y < height - subtractHeight; y++) {
                    if (chunk.getType(x, y, z) == null || chunk.getType(x, y, z) == Material.AIR) {
                        if (!ORE_POPULATOR.generatesHere(world, random, chunk, biome, x, y, z))
                            chunk.setBlock(x, y, z, biome.getGroundMaterial());
                    }
                }

                Material bottomMaterial = biome.getBottomMaterial();
                for (int y = height - subtractHeight; y < height; y++)
                    chunk.setBlock(x, y, z, bottomMaterial);

                chunk.setBlock(x, height, z, biome.getTopMaterial());
            }
        }
        return chunk;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return POPULATOR;
    }


    public static List<BlockPopulator> getPopulator() {
        return POPULATOR;
    }
}