package net.fununity.games.skydilation.chunkgeneration.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.chunkgeneration.distribution.SkyDilationDistribution;
import net.fununity.games.skydilation.chunkgeneration.distribution.TreasureDistribution;
import net.fununity.games.skydilation.util.RandomItemsUtil;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Random;
import java.util.logging.Level;

public class TreasurePopulator extends SkyDilationPopulator {

    public TreasurePopulator() {
        super(new EnumMap<>(Biomes.class));

        FileConfiguration cfg = SkyDilation.getInstance().getConfig();
        for (String biomeName : cfg.getConfigurationSection("treasure.").getKeys(false)) {
            try {
                this.distributionBiomes.put(Biomes.valueOf(biomeName.toUpperCase()),
                        Collections.singletonList(new TreasureDistribution(cfg.getInt("treasure." + biomeName + ".tries"),
                                cfg.getInt("treasure." + biomeName + ".percentage"),
                                Math.max(0, cfg.getInt("treasure." + biomeName + ".maxRichness") + 1))));
            } catch (IllegalArgumentException exception) {
                SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in treasure populator {0}", biomeName);
            }
        }
    }

    @Override
    public void generateAtPosition(World world, Random random, Chunk chunk, int x, int y, int z, SkyDilationDistribution distribution) {
        Block block = chunk.getBlock(x, y, z);
        block.setType(distribution.getType());
        ((Chest) block.getState()).getBlockInventory()
                .setContents(RandomItemsUtil.getInstance().getRandomChestItems(random.nextInt(((TreasureDistribution) distribution).getMaxRichness())));
    }

}
