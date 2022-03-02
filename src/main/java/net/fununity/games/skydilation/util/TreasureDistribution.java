package net.fununity.games.skydilation.util;

import net.fununity.games.skydilation.generator.populator.TreasurePopulator;

public class TreasureDistribution extends TreasurePopulator {

    public final int tries;
    public final int percentage;
    public final int maxRichness;

    public TreasureDistribution(int tries, int percentage, int popularity) {
        this.tries = tries;
        this.percentage = percentage;
        this.maxRichness = popularity;
    }
}
