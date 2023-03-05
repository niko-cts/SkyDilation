package net.fununity.games.skydilation.listener;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.shop.ShopGUI;
import net.fununity.games.skydilation.shop.shops.ShopTopic;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class VillagerClickListener implements Listener {

    /**
     * Opens Villager or witcher shop
     * @param event PlayerInteractEntityEvent - Event that got triggered.
     * @since 1.0
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (GameManager.getInstance().isSpectator(event.getPlayer())) return;
        if (event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(SkyDilation.getInstance(), () ->
                    ShopGUI.openShopPage(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(event.getPlayer()), ShopTopic.ARMOR));
        }
    }

}
