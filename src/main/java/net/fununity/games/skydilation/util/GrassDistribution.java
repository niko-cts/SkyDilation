package net.fununity.games.skydilation.util;

import org.bukkit.Material;

public class GrassDistribution {

    public final Material block;
    public final byte data;
    public final int tries;
    public final int percentage;

    public GrassDistribution(Material block, byte data, int tries, int percentage) {
        this.block = block;
        this.data = data;
        this.tries = tries;
        this.percentage = percentage;
    }
}
