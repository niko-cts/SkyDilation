package net.fununity.games.skydilation.listener;

import net.fununity.games.skydilation.GameLogic;
import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.ShopGUI;
import net.fununity.games.skydilation.shop.shops.ShopTopic;
import net.fununity.games.skydilation.shop.shops.SpecialContents;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.util.ColorUtil;
import net.fununity.mgs.gamespecifc.Team;
import net.fununity.mgs.gamestates.Game;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class SpecialItemsListener implements Listener {

    private static final List<Material> BLACKLISTED_WATERBLOCKS = Arrays.asList(Material.FLOWER_POT, Material.YELLOW_FLOWER, Material.CHORUS_FLOWER, Material.TORCH, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON, Material.STONE_BUTTON, Material.WOOD_BUTTON);

    private final Map<Team, Inventory> teamChest;
    private final Map<UUID, BukkitTask> teleportNoMove;

    /**
     * Instantiates maps and actionbar messages
     *
     * @since 1.0
     */
    public SpecialItemsListener() {
        this.teamChest = new HashMap<>();
        this.teleportNoMove = new HashMap<>();
    }

    /**
     * Manages team chest and other special items.
     *
     * @param event PlayerInteractEvent - Event that got triggered.
     * @since 1.0
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (GameManager.getInstance().isSpectator(player)) return;

        if (event.getClickedBlock() != null) {
            switch (event.getClickedBlock().getType()) {
                case ENDER_CHEST:
                    Team team = Game.getInstance().getPlayerTeam(player);
                    if (team == null || GameManager.getInstance().isSpectator(player)) return;
                    Inventory inv = teamChest.getOrDefault(team, null);
                    if (inv == null) {
                        inv = Bukkit.createInventory(null, 9 * 3, team.getColoredName());
                        teamChest.put(team, inv);
                    }
                    player.openInventory(inv);
                    return;
                case FLOWER_POT:
                    event.setCancelled(true);
                    return;
                default:
                    break;
            }
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        switch (item.getType()) {
            case BLAZE_POWDER:
                event.setCancelled(true);
                Location loc = player.getLocation().clone().subtract(0, 2, 0);
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        Location subtract = loc.clone().subtract(x, 0, z);
                        if (subtract.getBlock().getType() != Material.AIR) break;

                        subtract.getBlock().setType(Material.GLASS);
                        Bukkit.getScheduler().runTaskLater(SkyDilation.getInstance(), () ->
                                subtract.getBlock().setType(Material.AIR), 20L * 15);

                        Location webs = loc.clone().add(x, 1, z);
                        if (webs.getBlock().getType() != Material.AIR) break;

                        if (z == 0) {
                            webs.getBlock().setType(Material.WEB);
                            Bukkit.getScheduler().runTaskLater(SkyDilation.getInstance(), () ->
                                    webs.getBlock().setType(Material.AIR), 20L * 15);
                        }
                    }
                }
                removeItem(player, item);
                break;
            case ARMOR_STAND:
                event.setCancelled(true);
                Bukkit.getScheduler().runTask(SkyDilation.getInstance(), ()->
                        ShopGUI.openShopPage(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(event.getPlayer()), ShopTopic.ARMOR));
                removeItem(player, item);
                break;
            case SULPHUR:
                event.setCancelled(true);
                if (teleportNoMove.containsKey(player.getUniqueId())) return;
                player.getWorld().playEffect(player.getLocation(), Effect.BOW_FIRE, 10);
                int duration = 3;
                FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player).sendMessage(TranslationKeys.ITEM_TELEPORT_START, "${duration}", duration + "");
                BukkitTask task = Bukkit.getScheduler().runTaskLater(SkyDilation.getInstance(), () -> {
                    if (teleportNoMove.containsKey(player.getUniqueId())) {
                        teleportNoMove.remove(player.getUniqueId());
                        Team team = Game.getInstance().getPlayerTeam(player);
                        if (team == null) return;
                        player.teleport(GameLogic.getInstance().getArena().getTeamLocations(team).get(0));
                    }
                }, 20 * 3);
                teleportNoMove.put(player.getUniqueId(), task);
                removeItem(player, item);
                break;
            case WATER_BUCKET:
                if (event.getClickedBlock() == null) {
                    return;
                }

                Block waterBlock = event.getClickedBlock().getRelative(event.getBlockFace());
                if (BLACKLISTED_WATERBLOCKS.contains(event.getClickedBlock().getType()) || waterBlock != null && waterBlock.getType() != Material.AIR)
                    event.setCancelled(true);
                return;
            case BLAZE_ROD:
                if(!item.getItemMeta().getDisplayName().equals(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player).getLanguage().getTranslation(TranslationKeys.SPECIAL_BLAZE_ROD)))
                    break;
                Projectile pro = player.launchProjectile(LargeFireball.class);
                pro.setVelocity(pro.getVelocity().multiply(2));
                pro.setBounce(false);
                pro.setShooter(player);
                pro.setMetadata("sd", new FixedMetadataValue(SkyDilation.getInstance(), "blazerod"));
                removeItem(player, item);
                break;
            default:
                break;
        }
    }

    private void removeItem(Player player, ItemStack item) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack content = player.getInventory().getContents()[i];
            if (item.equals(content)) {
                if (content.getAmount() > 1)
                    content.setAmount(content.getAmount() - 1);
                else
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    /**
     * If the special item hits something it explodes.
     * @param event ProjectileHitEvent - Event that gets triggered
     * @since 1.0
     */
    @EventHandler
    public void projectileHit(ProjectileHitEvent event) {
        if (event.getHitEntity() instanceof Player && GameManager.getInstance().isSpectator((Player) event.getHitEntity())) return;
        if (!event.getEntity().hasMetadata("sd")) return;
        Location location = event.getHitEntity() == null ? event.getHitBlock().getLocation() : event.getHitEntity().getLocation();
        event.getEntity().getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 1.5f, false, false);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;
        Player player = event.getPlayer();
        if (event.getTo().getY() < 50) {
            if (GameManager.getInstance().getCurrentGameState() == GameState.INGAME)
                player.damage(10000);
            else
                net.fununity.mgs.Main.getInstance().teleportLobby(player);
        }
    }

    /**
     * Kills(or teleports to lobby spawn) a player when hes below Y: 0
     * @param event PlayerMoveEvent - Event that got triggered.
     * @since 1.0
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getX() == event.getTo().getX() &&
                event.getFrom().getY() == event.getTo().getY() &&
                event.getFrom().getZ() == event.getTo().getZ()) return;

        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();
        if (teleportNoMove.containsKey(uuid)) {
            teleportNoMove.get(uuid).cancel();
            teleportNoMove.remove(uuid);
            FunUnityAPI.getInstance().getPlayerHandler().getPlayer(uuid).sendMessage(MessagePrefix.ERROR, TranslationKeys.ITEM_TELEPORT_CANCEL);
            for (Map.Entry<Integer, ItemStack> entry : player.getInventory().addItem(SpecialContents.SULPHUR.getBuyItems()).entrySet()) {
                player.getWorld().dropItemNaturally(player.getLocation(), entry.getValue());
            }
        }

        if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;

        // SPEED CARPET
        if (event.getTo().getBlock().getType() == Material.CARPET) {
            Team playerTeam = GameLogic.getInstance().getPlayerTeam(player);
            if (playerTeam == null)
                return;
            byte carpetID = event.getTo().getBlock().getData();
            if (ColorUtil.convertChatToDyeColor(playerTeam.getColor()).getWoolData() == carpetID)
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 1));
        } else if (event.getTo().getBlock().getType() == Material.GOLD_PLATE) {
            player.setVelocity(player.getEyeLocation().getDirection().multiply(7.2).setY(2.5));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 1);
        }

    }

    @EventHandler
    public void onFloat(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID)
            player.damage(10000);
    }


}
