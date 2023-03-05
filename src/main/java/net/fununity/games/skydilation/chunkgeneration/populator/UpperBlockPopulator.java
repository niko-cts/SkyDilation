package net.fununity.games.skydilation.chunkgeneration.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.chunkgeneration.distribution.SkyDilationDistribution;
import net.fununity.games.skydilation.dropping.ItemDroppingManager;
import net.fununity.games.skydilation.listener.BlockListener;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Level;

public class UpperBlockPopulator extends SkyDilationPopulator {

    public UpperBlockPopulator() {
        super(new EnumMap<>(Biomes.class));

        FileConfiguration cfg = SkyDilation.getInstance().getConfig();
        for (String biomeName : cfg.getConfigurationSection("grass.").getKeys(false)) {
            Biomes biome;
            try {
                biome = Biomes.valueOf(biomeName.toUpperCase());
            } catch (IllegalArgumentException exception) {
                SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in grass populator {0}", biomeName);
                continue;
            }

            List<SkyDilationDistribution> distribution = new ArrayList<>();
            for (String material : cfg.getConfigurationSection("grass." + biomeName + ".").getKeys(false)) {

                try {
                    distribution.add(new net.fununity.games.skydilation.chunkgeneration.distribution.UpperBlockPopulator(Material.valueOf(material.toUpperCase()),
                            (byte) cfg.getInt("grass." + biomeName + "." + material + ".data"),
                            cfg.getInt("grass." + biomeName + "." + material + ".tries"),
                            cfg.getInt("grass." + biomeName + "." + material + ".percentage")));
                } catch (IllegalArgumentException exception) {
                    SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong material in grass distribution {0}", biome);
                }
            }
            this.distributionBiomes.put(biome, distribution);
        }
    }

    @Override
    public void generateAtPosition(World world, Random random, Chunk chunk, int x, int y, int z, SkyDilationDistribution distribution) {
        if (distribution.getType() == Material.NETHER_WARTS)
           chunk.getBlock(x, y - 1, z).setType(Material.SOUL_SAND);
        else if(distribution.getType() == Material.MAGMA)
            y--;

        Block block = chunk.getBlock(x, y, z);

        block.setType(distribution.getType());
        block.setData(distribution.getData());

        ItemDroppingManager.getInstance().startDropping(block.getLocation(), block.getType());
    }
}
