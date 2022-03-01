package net.fununity.games.skydilation.generator.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.generator.Biomes;
import net.fununity.games.skydilation.util.TreeDistribution;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;

import java.util.*;
import java.util.logging.Level;

public class TreePopulator extends BlockPopulator {

    private final Map<Biomes, List<TreeDistribution>> distributionBiomes;

    public TreePopulator() {
        this.distributionBiomes = new EnumMap<>(Biomes.class);
        SkyDilation.getInstance().getServer().getScheduler().runTaskAsynchronously(SkyDilation.getInstance(), () -> {
            FileConfiguration cfg = SkyDilation.getInstance().getConfig();
            for (String biomeName : cfg.getConfigurationSection("trees").getKeys(false)) {
                Biomes biome;
                try {
                    biome = Biomes.valueOf(biomeName.toUpperCase());
                } catch (IllegalArgumentException exception) {
                    SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in tree populator {0}", biomeName);
                    continue;
                }

                List<TreeDistribution> distribution = new ArrayList<>();
                for (String tree : cfg.getConfigurationSection("trees." + biomeName).getKeys(false)) {
                    try {
                        distribution.add(new TreeDistribution(TreeType.valueOf(tree.toUpperCase()),
                                cfg.getInt("trees." + biomeName + "." + tree + ".percentage"),
                                cfg.getInt("trees." + biomeName + "." + tree + ".tries")));
                    } catch (IllegalArgumentException exception) {
                        SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong treetype in tree populator {0}", tree);
                    }
                }
                this.distributionBiomes.put(biome, distribution);
            }
        });
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        Biomes biome = SkyDilation.getInstance().getGenerator().getSkyDilationBiome(chunk);
        if (biome == null) return;

        for (TreeDistribution tree : this.distributionBiomes.getOrDefault(biome, new ArrayList<>())) {
            for (int t = 0; t < tree.tries; t++) {
                if (random.nextInt(tree.percentage) <= 100) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);
                    int y;
                    for (y = world.getMaxHeight() - 1; chunk.getBlock(x, y, z).getType() == Material.AIR && y > 0; y--)
                        ; // Find the highest block of the (X,Z) coordinate chosen.
                    world.generateTree(chunk.getBlock(x, y, z).getLocation(), tree.treeType); // The tree type can be changed if you want.
                }
            }
        }
    }

}
