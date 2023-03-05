package net.fununity.games.skydilation.chunkgeneration.distribution;

import org.bukkit.Material;
import org.bukkit.TreeType;

public class TreeDistribution extends SkyDilationDistribution {

    private final TreeType treeType;

    public TreeDistribution(TreeType treeType, int tries, int percentage) {
        super(Material.AIR, (byte) 0, tries, percentage);
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }

}
