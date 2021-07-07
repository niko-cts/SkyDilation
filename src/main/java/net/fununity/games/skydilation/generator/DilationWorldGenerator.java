package net.fununity.games.skydilation.generator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DilationWorldGenerator extends ChunkGenerator {

    protected final List<int[]> generateAbleChunks;
    private final List<BlockPopulator> populators;
    private Chunk[] minAndMax;

    public DilationWorldGenerator() {
        this.generateAbleChunks = new ArrayList<>();
        this.populators = Arrays.asList(new TreePopulator(generateAbleChunks), new GrassPopulator(generateAbleChunks), new OrePopulator(generateAbleChunks));
        this.minAndMax = new Chunk[2];
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
        ChunkData chunk = createChunkData(world);
        generator.setScale(0.005D);

        if (!containsChunk(chunkX, chunkZ))
            return chunk;

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++) {
                int currentHeight = (int) (generator.noise(chunkX * 16 + x, chunkZ * 16 + z, 0.5D, 0.5D) * 15D + 50D);
                chunk.setBlock(x, currentHeight, z, Material.GRASS);
                chunk.setBlock(x, currentHeight - 1, z, Material.DIRT);
                for (int y = currentHeight - 2; y > 0; y--)
                    chunk.setBlock(x, y, z, Material.STONE);
                chunk.setBlock(x, 0, z, Material.BEDROCK);
            }
        return chunk;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return populators;
    }

    public void addChunkWhitelist(Chunk chunk) {
        addChunkWhitelist(chunk.getX(), chunk.getZ());
    }

    public void addChunkWhitelist(int x, int z) {
        this.generateAbleChunks.add(new int[]{x, z});
    }

    public boolean containsChunk(int x, int z) {
        return generateAbleChunks.stream().anyMatch(c -> c[0] == x && c[1] == z);
    }


    public void generateRandomChunk(World world) {
        int x = RandomUtil.getRandomInt(30 - 5);
        int z = RandomUtil.getRandomInt(30 - 5);
        int tries = 0;
        while (containsChunk(x, z)) {
            x = RandomUtil.getRandomInt(30 - 5);
            z = RandomUtil.getRandomInt(30 - 5);
            tries++;
            if (tries > 20)
                return;
        }
        generateChunk(world, x, z);
    }

    public void generateChunk(World world, int x, int z) {
        SkyDilation.getInstance().getSkyDilationGenerator().addChunkWhitelist(x, z);
        world.regenerateChunk(x, z);
    }

    public Chunk[] getMinAndMax() {
        return minAndMax;
    }

    private static class TreePopulator extends BlockPopulator {


        private final List<int[]> populators;

        public TreePopulator(List<int[]> generateAbleChunks) {
            this.populators = generateAbleChunks;
        }

        @Override
        public void populate(World world, Random random, Chunk chunk) {
            if (populators.stream().noneMatch(c -> c[0] == chunk.getX() && c[1] == chunk.getZ()))
                return;

            if (random.nextBoolean()) {
                int amount = random.nextInt(2)+1;  // Amount of trees
                for (int i = 1; i < amount; i++) {
                    int x = random.nextInt(15);
                    int z = random.nextInt(15);
                    int y;
                    for (y = world.getMaxHeight()-1; chunk.getBlock(x, y, z).getType() == Material.AIR && y > 0; y--); // Find the highest block of the (X,Z) coordinate chosen.
                    world.generateTree(chunk.getBlock(x, y, z).getLocation(), TreeType.TREE); // The tree type can be changed if you want.
                }
            }
        }
    }
    private static class GrassPopulator extends BlockPopulator {
        private final List<int[]> populators;

        public GrassPopulator(List<int[]> generateAbleChunks) {
            this.populators = generateAbleChunks;
        }
        @Override
        public void populate(World world, Random random, Chunk chunk) {
            if (populators.stream().noneMatch(c -> c[0] == chunk.getX() && c[1] == chunk.getZ()))
                return;
            if (random.nextBoolean()) {
                int amount = random.nextInt(2)+1;  // Amount of trees
                for (int i = 1; i < amount; i++) {
                    int x = random.nextInt(15);
                    int z = random.nextInt(15);
                    int y;
                    for (y = world.getMaxHeight()-1; chunk.getBlock(x, y, z).getType() == Material.AIR && y > 0; y--); // Find the highest block of the (X,Z) coordinate chosen.
                    chunk.getBlock(x, y+1, z).setType(Material.GRASS);
                }
            }
        }
    }
    private static class OrePopulator extends BlockPopulator {
        private final List<int[]> populators;

        public OrePopulator(List<int[]> generateAbleChunks) {
            this.populators = generateAbleChunks;
        }
        @Override
        public void populate(World world, Random random, Chunk chunk) {
            if (populators.stream().noneMatch(c -> c[0] == chunk.getX() && c[1] == chunk.getZ()))
                return;
            int x;
            int y;
            int z;
            boolean isStone;
            for (int i = 1; i < 15; i++) {  // Number of tries
                if (random.nextInt(100) < 60) {  // The chance of spawning
                    x = random.nextInt(15);
                    z = random.nextInt(15);
                    y = random.nextInt(40)+20;  // Get randomized coordinates
                    if (world.getBlockAt(x, y, z).getType() == Material.STONE) {
                        isStone = true;
                        while (isStone) {
                            world.getBlockAt(x, y, z).setType(Material.COAL_ORE);
                            if (random.nextInt(100) < 40)  {   // The chance of continuing the vein
                                switch (random.nextInt(6)) {  // The direction chooser
                                    case 0: x++; break;
                                    case 1: y++; break;
                                    case 2: z++; break;
                                    case 3: x--; break;
                                    case 4: y--; break;
                                    case 5: z--; break;
                                    default:
                                        break;
                                }
                                isStone = (world.getBlockAt(x, y, z).getType() == Material.STONE) && (world.getBlockAt(x, y, z).getType() != Material.COAL_ORE);
                            } else isStone = false;
                        }
                    }
                }
            }
        }
    }
}
