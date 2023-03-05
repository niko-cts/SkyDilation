package net.fununity.games.skydilation.dropping;

import net.fununity.games.skydilation.SkyDilation;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

/**
 * Class for tick timer, which drops item at given location.
 * @author Niko
 * @since 0.0.1
 */
public class ItemDrop {

    private final Location location;
    private final Material droppingItem;
    private final BukkitTask taskTimer;


    private final BukkitTask particleTimer;
    private final Color color;

    /**
     * Instantiates the class and starts timer.
     * @param location Location - location to drop item
     * @param droppingItem Material - item to drop.
     * @param ticks long - repeating ticks.
     */
    public ItemDrop(Location location, Material droppingItem, long ticks, Color color, long repeat) {
        this.location = location;
        this.droppingItem = droppingItem;
        this.color = color;
        this.taskTimer = Bukkit.getScheduler().runTaskTimer(SkyDilation.getInstance(), this::drop, ticks, ticks);
        this.particleTimer = Bukkit.getScheduler().runTaskTimer(SkyDilation.getInstance(), this::particle, 0, repeat);
    }

    private void particle() {
        location.getWorld().spawnParticle(Particle.REDSTONE, location.getX(), location.getY(), location.getZ(), 0, color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Drops the item.
     */
    private void drop() {
        location.getWorld().dropItem(location, new ItemStack(droppingItem));
    }

    /**
     * Stops the task timer.
     */
    public void stop() {
        taskTimer.cancel();
        particleTimer.cancel();
    }


}
