package net.fununity.games.skydilation.listener;

import net.fununity.games.skydilation.GameLogic;
import net.fununity.games.skydilation.dropping.ItemDroppingManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled() || !GameLogic.getInstance().isIngame(event.getPlayer())) return;
        ItemDroppingManager.getInstance().startDropping(event.getBlock().getLocation(), event.getBlock().getType());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled() || !GameLogic.getInstance().isIngame(event.getPlayer())) return;
        ItemDroppingManager.getInstance().stopDropping(event.getBlock().getLocation());
        if (event.getBlock().getType() == Material.CACTUS || event.getBlock().getType() == Material.DEAD_BUSH) {
            event.setDropItems(false);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.DEAD_BUSH));
        }
    }
}
