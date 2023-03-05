package net.fununity.games.skydilation.chunkgeneration.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.chunkgeneration.distribution.SkyDilationDistribution;
import net.fununity.games.skydilation.chunkgeneration.distribution.TreeDistribution;
import org.bukkit.Chunk;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Level;

public class TreePopulator extends SkyDilationPopulator {

    public TreePopulator() {
        super(new EnumMap<>(Biomes.class));

        FileConfiguration cfg = SkyDilation.getInstance().getConfig();
        for (String biomeName : cfg.getConfigurationSection("trees.").getKeys(false)) {
            Biomes biome;
            try {
                biome = Biomes.valueOf(biomeName.toUpperCase());
            } catch (IllegalArgumentException exception) {
                SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in tree populator {0}", biomeName);
                continue;
            }

            List<SkyDilationDistribution> distribution = new ArrayList<>();
            for (String tree : cfg.getConfigurationSection("trees." + biomeName + ".").getKeys(false)) {
                try {
                    distribution.add(new TreeDistribution(TreeType.valueOf(tree.toUpperCase()),
                            cfg.getInt("trees." + biomeName + "." + tree + ".tries"),
                            cfg.getInt("trees." + biomeName + "." + tree + ".percentage")));
                } catch (IllegalArgumentException exception) {
                    SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong tree type in tree populator {0}", tree);
                }
            }
            this.distributionBiomes.put(biome, distribution);
        }

    }

    @Override
    public void generateAtPosition(World world, Random random, Chunk chunk, int x, int y, int z, SkyDilationDistribution distribution) {
        world.generateTree(chunk.getBlock(x, y, z).getLocation(), ((TreeDistribution) distribution).getTreeType());
    }

}
