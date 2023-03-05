package net.fununity.games.skydilation.chunkgeneration.populator;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.chunkgeneration.distribution.OreDistribution;
import net.fununity.games.skydilation.chunkgeneration.distribution.SkyDilationDistribution;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;

import java.util.*;
import java.util.logging.Level;

/**
 * Populator for generating ore veins in a chunk
 * @author Niko
 * @since 0.0.2
 */
public class OrePopulator {

    private static final int MAX_VEIN_AMOUNT = 3;

    private final EnumMap<Biomes, List<SkyDilationDistribution>> distributionBiomes;


    public OrePopulator() {
        this.distributionBiomes = new EnumMap<>(Biomes.class);

        FileConfiguration cfg = SkyDilation.getInstance().getConfig();
        for (String biomeOres : cfg.getConfigurationSection("ores.").getKeys(false)) {
            Biomes biome;
            try {
                biome = Biomes.valueOf(biomeOres.toUpperCase());
            } catch (IllegalArgumentException exception) {
                SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong biome in ore populator {0}", biomeOres);
                continue;
            }

            List<SkyDilationDistribution> distribution = new ArrayList<>();
            for (String ore : cfg.getString("ores." + biomeOres).split(";")) {
                try {
                    String[] oreData = ore.split(":");

                    Material material = Material.valueOf(oreData[0]);
                    byte data = Byte.parseByte(oreData[1]);
                    float percentage = Float.parseFloat(oreData[2]) * 100F;

                    distribution.add(new OreDistribution(material, data, 1, percentage));
                } catch (IllegalArgumentException exception) {
                    SkyDilation.getInstance().getLogger().log(Level.WARNING, "Wrong ore data for biome {0}", biome);
                }
                distribution.sort(Comparator.comparingDouble(SkyDilationDistribution::getPercentage));
            }

            this.distributionBiomes.put(biome, distribution);
        }
    }

    private void changeBlock(World world, ChunkGenerator.ChunkData chunk, Random random, int x, int y, int z, SkyDilationDistribution distribution, int veinAmount) {
        if (veinAmount == 0)
            return;

        chunk.setBlock(x, y, z, distribution.getType().getId(), distribution.getData());

        int newX;
        do {
            newX = x + random.nextInt(3) - 1;
        } while (newX < 0 || newX > 15);

        int newZ;
        do {
            newZ = z + random.nextInt(3) - 1;
        } while (newZ < 0 || newZ > 15);

        int newY;
        do {
            newY = y + random.nextInt(3) - 1;
        } while (newY < 1);

        changeBlock(world, chunk, random, newX, newY, newZ, distribution, veinAmount - 1);
    }

    public boolean generatesHere(World world, Random random, ChunkGenerator.ChunkData chunk, Biomes biome, int x, int y, int z) {
        for (SkyDilationDistribution distribution : this.distributionBiomes.getOrDefault(biome, new ArrayList<>())) {
            if (random.nextFloat() * 100F <= distribution.getPercentage()) {
                changeBlock(world, chunk, random, x, y, z, distribution, random.nextInt(MAX_VEIN_AMOUNT) + 1);
                return true;
            }
        }
        return false;
    }
}
