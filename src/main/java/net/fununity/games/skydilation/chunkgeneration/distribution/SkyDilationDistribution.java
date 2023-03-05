package net.fununity.games.skydilation.chunkgeneration.distribution;

import org.bukkit.Material;

/**
 * This class holds the basic distribution data for spawning blocks in a chunk.
 * It is used by the {@link net.fununity.games.skydilation.chunkgeneration.populator.SkyDilationPopulator} to evaluate a spawning of a given block.
 * @author Niko
 * @since 0.0.2
 */
public abstract class SkyDilationDistribution {

    private final Material block;
    private final byte data;
    private final int tries;
    private final float percentage;

    public SkyDilationDistribution(Material block, byte data, int tries, float percentage) {
        this.block = block;
        this.data = data;
        this.tries = tries;
        this.percentage = percentage;
    }


    public Material getType() {
        return block;
    }

    public byte getData() {
        return data;
    }

    public int getTries() {
        return tries;
    }

    public float getPercentage() {
        return percentage;
    }
}
