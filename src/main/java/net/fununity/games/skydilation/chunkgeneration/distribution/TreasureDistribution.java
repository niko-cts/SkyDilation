package net.fununity.games.skydilation.chunkgeneration.distribution;

import org.bukkit.Material;

public class TreasureDistribution extends SkyDilationDistribution {

    private final int maxRichness;

    public TreasureDistribution(int tries, int percentage, int popularity) {
        super(Material.CHEST, (byte) 0, tries, percentage);
        this.maxRichness = popularity;
    }

    public int getMaxRichness() {
        return maxRichness;
    }
}
