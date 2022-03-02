package net.fununity.games.skydilation.generator;

import net.fununity.games.skydilation.util.MaterialPercentage;
import net.fununity.main.api.common.util.RandomUtil;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum Biomes {

    // We store the biome, handler and the temperature and rainfall for each biome.
    DESERT(Biome.DESERT, List.of(new MaterialPercentage(Material.SANDSTONE, 1F)), List.of(new MaterialPercentage(Material.SAND, 1F)), new BiomeNoiseGenerator(6, 64)),
    FOREST(Biome.FOREST, List.of(new MaterialPercentage(Material.DIRT, 1F)), List.of(new MaterialPercentage(Material.GRASS, 0.95F), new MaterialPercentage(Material.GRASS_PATH, 0.05F)), new BiomeNoiseGenerator(3, 64)),
    PLAINS(Biome.PLAINS, List.of(new MaterialPercentage(Material.DIRT, 1F)), List.of(new MaterialPercentage(Material.GRASS, 1F)), new BiomeNoiseGenerator(3, 256)),
    SWAMP(Biome.SWAMPLAND, List.of(new MaterialPercentage(Material.DIRT, 1F)), List.of(new MaterialPercentage(Material.GRASS, 1F)), new BiomeNoiseGenerator(3, 64)),
    HILLS(Biome.ICE_MOUNTAINS, List.of(new MaterialPercentage(Material.PACKED_ICE, 1F)), List.of(new MaterialPercentage(Material.PACKED_ICE, 1F)), new BiomeNoiseGenerator(10, 64, 72));

    public final Biome biome;
    private final TreeMap<Float, Material> bottomMaterial;
    private final TreeMap<Float, Material> topMaterial;
    private final BiomeNoiseGenerator generator;

    Biomes(Biome biome, List<MaterialPercentage> bottomMaterial, List<MaterialPercentage> topMaterial, BiomeNoiseGenerator generator) {
        this.biome = biome;
        this.bottomMaterial = new TreeMap<>();
        float current = 0;
        for (MaterialPercentage material : bottomMaterial)
            this.bottomMaterial.put(current += material.percentage, material.material);
        this.topMaterial = new TreeMap<>();
        current = 0;
        for (MaterialPercentage material : topMaterial)
            this.topMaterial.put(current += material.percentage, material.material);
        this.generator = generator;
    }

    public static void setWorld(World world) {
        for (Biomes biome : Biomes.values()) {
            biome.generator.setWorld(world);
        }
    }

    public BiomeNoiseGenerator getGenerator() {
        return generator;
    }

    public Material getBottomMaterial() {
        Map.Entry<Float, Material> entry = this.bottomMaterial.ceilingEntry(RandomUtil.getRandom().nextFloat());
        return entry != null ? entry.getValue() : Material.DIRT;
    }

    public Material getTopMaterial() {
        Map.Entry<Float, Material> entry = this.topMaterial.ceilingEntry(RandomUtil.getRandom().nextFloat());
        System.out.println(entry + "  " + RandomUtil.getRandom().nextFloat() + "  " + topMaterial);
        return entry != null ? entry.getValue() : Material.GRASS;
    }
}
