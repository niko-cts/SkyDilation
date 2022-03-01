package net.fununity.games.skydilation.util;

import org.bukkit.TreeType;

public class TreeDistribution {

    public final TreeType treeType;
    public final int percentage;
    public final int tries;

    public TreeDistribution(TreeType treeType, int percentage, int tries) {
        this.treeType = treeType;
        this.percentage = percentage;
        this.tries = tries;
    }
}
