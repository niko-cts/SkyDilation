package net.fununity.games.skydilation.generator.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.generator.Biomes;
import net.fununity.games.skydilation.util.OreDistribution;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;

import java.util.*;
import java.util.logging.Level;

public class OrePopulator extends BlockPopulator {

    private final Map<Biomes, List<OreDistribution>> distributionBiomes;

    public OrePopulator() {
        this.distributionBiomes = new EnumMap<>(Biomes.class);
        SkyDilation.getInstance().getServer().getScheduler().runTaskAsynchronously(SkyDilation.getInstance(), () -> {
            FileConfiguration cfg = SkyDilation.getInstance().getConfig();
            for (String biomeName : cfg.getConfigurationSection("ore").getKeys(false)) {
                Biomes biome;
                try {
                    biome = Biomes.valueOf(biomeName.toUpperCase());
                } catch (IllegalArgumentException exception) {
                    SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in ore populator {0}", biomeName);
                    continue;
                }

                List<OreDistribution> distribution = new ArrayList<>();
                for (String material : cfg.getConfigurationSection("ore." + biomeName).getKeys(false)) {
                    try {
                        distribution.add(new OreDistribution(Material.valueOf(material.toUpperCase()),
                                cfg.getInt("ore." + biomeName + "." + material + ".tries"),
                                cfg.getInt("ore." + biomeName + "." + material + ".chanceOfSpawning"),
                                cfg.getInt("ore." + biomeName + "." + material + ".minY"),
                                cfg.getInt("ore." + biomeName + "." + material + ".maxY"),
                                cfg.getInt("ore." + biomeName + "." + material + ".chanceOfVein")));
                    } catch (IllegalArgumentException exception) {
                        SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong material in ore populator {0}", material);
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

        for (OreDistribution ore : this.distributionBiomes.getOrDefault(biome, new ArrayList<>())) {
            for (int i = 0; i < ore.tries; i++) {  // Number of tries
                if (random.nextInt(100) <= ore.chanceOfSpawning) {  // The chance of spawning
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);
                    int y = random.nextInt(ore.maxY - ore.minY) + ore.minY;  // Get randomized coordinates
                    if (world.getBlockAt(x, y, z).getType() == Material.STONE) {
                        boolean isStone = true;
                        while (isStone) {
                            world.getBlockAt(x, y, z).setType(ore.ore);
                            if (random.nextInt(100) <= ore.chanceOfVein) {   // The chance of continuing the vein
                                switch (random.nextInt(y > 4 ? 6 : 5)) {  // The direction chooser
                                    case 0:
                                        x++;
                                        break;
                                    case 1:
                                        y++;
                                        break;
                                    case 2:
                                        z++;
                                        break;
                                    case 3:
                                        x--;
                                        break;
                                    case 4:
                                        z--;
                                        break;
                                    case 5:
                                        y--;
                                        break;
                                    default:
                                        break;
                                }
                                isStone = (world.getBlockAt(x, y, z).getType() == Material.STONE) && (world.getBlockAt(x, y, z).getType() != ore.ore);
                            } else
                                isStone = false;
                        }
                    }
                }
            }
        }
    }

}
