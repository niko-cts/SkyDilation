package net.fununity.games.skydilation.chunkgeneration;

import org.bukkit.World;
import org.bukkit.util.noise.PerlinOctaveGenerator;

public class BiomeNoiseGenerator {

    // The perlin octave generator works the same way as
    // a simplex octave generator (just faster on lower
    // dimensions i.e.. 2 and 3).
    protected PerlinOctaveGenerator generator;
    private final double magnitude;
    private final double scale;
    private final Double yScale;

    public BiomeNoiseGenerator(double magnitude, double scale) {
        this.magnitude = magnitude;
        this.scale = scale;
        this.yScale = null;
    }

    public BiomeNoiseGenerator(double magnitude, double scale, double yScale) {
        this.magnitude = magnitude;
        this.scale = scale;
        this.yScale = yScale;
    }

    // We use a state machine in order to handle multiple worlds (thanks @Icyene)
    public void setWorld(World world) {
        this.generator = new PerlinOctaveGenerator(world, 8);
        this.generator.setScale(1.0 / scale);
        if (yScale != null)
            this.generator.setYScale(1.0 / yScale);
    }

    //These methods can be overridden later on to deal with more specific biome generation
    public double get2DNoise(double x, double z) {
        return generator.noise(x, z, 0.5, 0.5) * magnitude;
    }

    public double get3DNoise(double x, double y, double z) {
        return generator.noise(x, y, z, 0.5, 0.5);
    }

}
