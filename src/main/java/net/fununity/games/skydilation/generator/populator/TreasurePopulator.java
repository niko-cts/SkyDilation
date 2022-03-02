package net.fununity.games.skydilation.generator.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.generator.Biomes;
import net.fununity.games.skydilation.util.RandomItemsUtil;
import net.fununity.games.skydilation.util.TreasureDistribution;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

public class TreasurePopulator extends BlockPopulator {

    private final Map<Biomes, List<TreasureDistribution>> distributionBiomes;

    public TreasurePopulator() {
        this.distributionBiomes = new EnumMap<>(Biomes.class);

        SkyDilation.getInstance().getServer().getScheduler().runTaskAsynchronously(SkyDilation.getInstance(), () -> {
            FileConfiguration cfg = SkyDilation.getInstance().getConfig();
            for (String biomeName : cfg.getConfigurationSection("treasure").getKeys(false)) {
                Biomes biome;
                try {
                    biome = Biomes.valueOf(biomeName.toUpperCase());
                } catch (IllegalArgumentException exception) {
                    SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in treasure populator {0}", biomeName);
                    continue;
                }

                List<TreasureDistribution> distribution = new ArrayList<>();
                for (String material : cfg.getConfigurationSection("treasure." + biomeName).getKeys(false)) {

                    try {
                        distribution.add(new TreasureDistribution(
                                cfg.getInt("treasure." + biomeName + "." + material + ".tries"),
                                cfg.getInt("treasure." + biomeName + "." + material + ".percentage"),
                                cfg.getInt("treasure." + biomeName + "." + material + ".maxRichness")));
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

        for (TreasureDistribution treasure : this.distributionBiomes.getOrDefault(biome, new ArrayList<>())) {
            for (int t = 0; t < treasure.tries; t++) {
                if (random.nextInt(treasure.percentage) <= 100) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);
                    int y;
                    for (y = world.getMaxHeight() - 1; chunk.getBlock(x, y, z).getType() == Material.AIR && y > 0; y--)
                        ; // Find the highest block of the (X,Z) coordinate chosen.
                    if (chunk.getBlock(x, y, z).getType() == Material.GRASS) {
                        Block block = chunk.getBlock(x, y + 1, z);
                        block.setType(Material.CHEST);
                        ((Chest) block).getBlockInventory()
                                .setContents(RandomItemsUtil.getInstance().getRandomChestItems(random.nextInt(treasure.maxRichness)));
                    }
                }
            }
        }
    }

}
