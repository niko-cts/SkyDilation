package net.fununity.games.skydilation.util;

import org.bukkit.Material;

public class OreDistribution {

    public final Material ore;
    public final int tries;
    public final int chanceOfSpawning;
    public final int minY;
    public final int maxY;
    public final int chanceOfVein;

    public OreDistribution(Material ore, int tries, int chanceOfSpawning, int minY, int maxY, int chanceOfVein) {
        this.ore = ore;
        this.chanceOfSpawning = chanceOfSpawning;
        this.tries = tries;
        this.minY = minY;
        this.maxY = maxY;
        this.chanceOfVein = chanceOfVein;
    }
}