package net.fununity.games.skydilation.listener;

import net.fununity.games.skydilation.GameLogic;
import net.fununity.games.skydilation.util.RandomItemsUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;

public class BlockBreakListener implements Listener {

    private static final List<Material> BLOCK_DROPS = Arrays.asList(Material.CACTUS, Material.WHEAT, Material.CARROT, Material.POTATO, Material.LEAVES, Material.LEAVES_2);

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled() || !GameLogic.getInstance().isIngame(event.getPlayer())) return;
        if (!BLOCK_DROPS.contains(event.getBlock().getType())) return;
        event.setDropItems(false);
        event.setExpToDrop(0);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), RandomItemsUtil.getInstance().getRandomItemFromBlockDrop());
    }

}
