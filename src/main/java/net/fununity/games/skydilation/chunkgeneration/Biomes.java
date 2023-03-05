package net.fununity.games.skydilation.chunkgeneration;

import net.fununity.games.skydilation.util.MaterialPercentage;
import net.fununity.main.api.common.util.RandomUtil;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum Biomes {

    // COAL
    PLAINS(Biome.PLAINS, List.of(new MaterialPercentage(Material.GRASS, 0.95F), new MaterialPercentage(Material.GRASS_PATH, 0.05F)), List.of(new MaterialPercentage(Material.DIRT, 1F)), Material.STONE, new BiomeNoiseGenerator(3, 64)),
    SAVANNA(Biome.SAVANNA, List.of(new MaterialPercentage(Material.GRASS, 0.9F), new MaterialPercentage(Material.GRASS_PATH, 0.1F)), List.of(new MaterialPercentage(Material.DIRT, 1F)), Material.STONE, new BiomeNoiseGenerator(3, 64)),
    JUNGLE(Biome.JUNGLE, List.of(new MaterialPercentage(Material.GRASS, 0.95F), new MaterialPercentage(Material.GRASS_PATH, 0.05F)), List.of(new MaterialPercentage(Material.DIRT, 1F)), Material.STONE, new BiomeNoiseGenerator(3, 64)),

    // IRON
    DESERT(Biome.DESERT, List.of(new MaterialPercentage(Material.SAND, 0.9F), new MaterialPercentage(Material.SANDSTONE, 0.1F)), List.of(new MaterialPercentage(Material.SANDSTONE, 1F)), Material.STONE, new BiomeNoiseGenerator(6, 64)),
    MUSHROOM(Biome.MUSHROOM_ISLAND, List.of(new MaterialPercentage(Material.MYCEL, 0.95F), new MaterialPercentage(Material.GRASS_PATH, 0.05F)), List.of(new MaterialPercentage(Material.DIRT, 1F)), Material.STONE, new BiomeNoiseGenerator(6, 64)),

    // GOLD
    NETHER_1(Biome.HELL, List.of(new MaterialPercentage(Material.NETHERRACK, 0.8F), new MaterialPercentage(Material.NETHER_BRICK, 0.05F), new MaterialPercentage(Material.LAVA, 0.05F), new MaterialPercentage(Material.GRAVEL, 0.1F)), List.of(new MaterialPercentage(Material.NETHERRACK, 0.4F), new MaterialPercentage(Material.GRAVEL, 0.2F), new MaterialPercentage(Material.NETHER_BRICK, 0.4F)), Material.NETHERRACK, new BiomeNoiseGenerator(3, 64)),
    NETHER_2(Biome.HELL, List.of(new MaterialPercentage(Material.NETHERRACK, 0.7F), new MaterialPercentage(Material.LAVA, 0.1F), new MaterialPercentage(Material.NETHER_BRICK, 0.15F)), List.of(new MaterialPercentage(Material.NETHERRACK, 0.4F), new MaterialPercentage(Material.GRAVEL, 0.1F), new MaterialPercentage(Material.NETHER_BRICK, 0.4F)), Material.NETHERRACK, new BiomeNoiseGenerator(3, 64)),

    // NETHER STAR
    END(Biome.SKY, List.of(new MaterialPercentage(Material.ENDER_STONE, 0.99F), new MaterialPercentage(Material.OBSIDIAN, 0.01F)), List.of(new MaterialPercentage(Material.ENDER_STONE, 0.9F), new MaterialPercentage(Material.OBSIDIAN, 0.1F)), Material.ENDER_STONE, new BiomeNoiseGenerator(4, 64));

    public final Biome biome;
    private final TreeMap<Float, Material> bottomMaterial;
    private final TreeMap<Float, Material> topMaterial;
    private final Material groundMaterial;
    private final BiomeNoiseGenerator generator;


    Biomes(Biome biome, List<MaterialPercentage> topMaterial, List<MaterialPercentage> bottomMaterial, Material groundMaterial, BiomeNoiseGenerator generator) {
        this.biome = biome;
        this.groundMaterial = groundMaterial;
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

    public Material getGroundMaterial() {
        return groundMaterial;
    }

    public Material getBottomMaterial() {
        Map.Entry<Float, Material> entry = this.bottomMaterial.ceilingEntry(RandomUtil.getRandom().nextFloat());
        return entry != null ? entry.getValue() : groundMaterial;
    }

    public Material getTopMaterial() {
        Map.Entry<Float, Material> entry = this.topMaterial.ceilingEntry(RandomUtil.getRandom().nextFloat());
        return entry != null ? entry.getValue() : Material.GRASS;
    }

    public boolean containsTopMaterial(Material material) {
        return this.topMaterial.values().contains(material);
    }
}
