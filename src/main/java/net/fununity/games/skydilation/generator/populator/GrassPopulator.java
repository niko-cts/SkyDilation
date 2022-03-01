package net.fununity.games.skydilation.generator.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.generator.Biomes;
import net.fununity.games.skydilation.util.GrassDistribution;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;

import java.util.*;
import java.util.logging.Level;

public class GrassPopulator extends BlockPopulator {

    private final Map<Biomes, List<GrassDistribution>> distributionBiomes;

    public GrassPopulator() {
        this.distributionBiomes = new EnumMap<>(Biomes.class);

        SkyDilation.getInstance().getServer().getScheduler().runTaskAsynchronously(SkyDilation.getInstance(), () -> {
            FileConfiguration cfg = SkyDilation.getInstance().getConfig();
            for (String biomeName : cfg.getConfigurationSection("grass").getKeys(false)) {
                Biomes biome;
                try {
                    biome = Biomes.valueOf(biomeName.toUpperCase());
                } catch (IllegalArgumentException exception) {
                    SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in grass populator {0}", biomeName);
                    continue;
                }

                List<GrassDistribution> distribution = new ArrayList<>();
                for (String material : cfg.getConfigurationSection("grass." + biomeName).getKeys(false)) {

                    try {
                        distribution.add(new GrassDistribution(Material.valueOf(material.toUpperCase()),
                                (byte) cfg.getInt("grass." + biomeName + "." + material + ".data"),
                                cfg.getInt("grass." + biomeName + "." + material + ".tries"),
                                cfg.getInt("grass." + biomeName + "." + material + ".percentage")));
                    } catch (IllegalArgumentException exception) {
                        SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong material in grass distribution {0}", biome);
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

        for (GrassDistribution grass : this.distributionBiomes.getOrDefault(biome, new ArrayList<>())) {
            for (int t = 0; t < grass.tries; t++) {
                if (random.nextInt(grass.percentage) <= 100) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);
                    int y;
                    for (y = world.getMaxHeight() - 1; chunk.getBlock(x, y, z).getType() == Material.AIR && y > 0; y--)
                        ; // Find the highest block of the (X,Z) coordinate chosen.
                    if (chunk.getBlock(x, y, z).getType() == Material.GRASS) {
                        Block block = chunk.getBlock(x, y + 1, z);
                        block.setType(grass.block);
                        block.setData(grass.data);
                    }
                }
            }
        }
    }
}
