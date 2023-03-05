package net.fununity.games.skydilation.dropping;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.listener.BlockListener;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will drop all items from sapplings etc
 * @author Niko
 * @since 0.0.2
 */
public class ItemDroppingManager {

    private static ItemDroppingManager instance;
    public static ItemDroppingManager getInstance() {
        if(instance == null)
            instance = new ItemDroppingManager();
        return instance;
    }

    private final Map<Location, ItemDrop> droppingLocations;
    private final Map<Material, Object[]> toDropItem;
    private final Map<Material, Object[]> particleItem; // triggerMaterial, [Particle, count, repeatingLong]

    /**
     * Instantiates the class and loads the item dropping from the config.
     */
    public ItemDroppingManager() {
        this.droppingLocations = new HashMap<>();
        this.toDropItem = new EnumMap<>(Material.class);
        this.particleItem = new EnumMap<>(Material.class);
        for (String item : SkyDilation.getInstance().getConfig().getConfigurationSection("items").getKeys(false)) {
            Material triggerMat;
            try {
                triggerMat = Material.valueOf(item);
            } catch (IllegalArgumentException exception) {
                SkyDilation.getInstance().getLogger().warning("ItemDropManager: 'items." + item + "' is not a material ");
                continue;
            }

            try {
                for (String number : SkyDilation.getInstance().getConfig().getConfigurationSection("items." + item).getKeys(false)) {
                    byte data = Byte.parseByte(number);
                    putInMap(item + "." + data, triggerMat);
                }
            } catch (NumberFormatException e) {
                putInMap(item, triggerMat);
            }
        }
    }

    private void putInMap(String path, Material triggerMat) {
        long ticks;
        try {
            ticks = SkyDilation.getInstance().getConfig().getLong("items." + path + ".time");
            String matName = SkyDilation.getInstance().getConfig().getString("items." + path + ".drop");
            try {
                this.toDropItem.put(triggerMat, new Object[]{Material.valueOf(matName), ticks});


                int color = SkyDilation.getInstance().getConfig().getInt("items." + path + ".particle.color");
                try {
                    this.particleItem.put(triggerMat, new Object[]{Color.fromRGB(color),
                            SkyDilation.getInstance().getConfig().getLong("items." + path + ".particle.repeat")});
                } catch (IllegalArgumentException exception) {
                    this.particleItem.put(triggerMat, new Object[]{Particle.PORTAL, 1, 300});
                    SkyDilation.getInstance().getLogger().warning("ItemDropManager: 'items." + path + ".particle.name' is not a particle");
                }
            } catch (IllegalArgumentException exception) {
                SkyDilation.getInstance().getLogger().warning("ItemDropManager: 'items." + path + ".drop' is not a material ");
            }
        } catch (IllegalArgumentException exception) {
            SkyDilation.getInstance().getLogger().warning("ItemDropManager: 'items." + path + ".time' is not a long ");
        }
    }

    /**
     * Checks if the trigger material exists and starts the task for given location.
     * @param location Location - the location to spawn the items.
     * @param triggerMaterial Material - the trigger material
     */
    public void startDropping(Location location, Material triggerMaterial) {
        Object[] dropping = this.toDropItem.getOrDefault(triggerMaterial, null);
        if (dropping != null) {
            Object[] particles = this.particleItem.get(triggerMaterial);
            this.droppingLocations.put(location, new ItemDrop(location.clone().add(0.5, 1.01, 0.5), (Material) dropping[0], (long) dropping[1], (Color) particles[0], (long) particles[1]));
        }
    }

    /**
     * Stops the dropping at the given location.
     * @param location Location - the location to stop dropping items.
     */
    public void stopDropping(Location location) {
        ItemDrop itemDrop = this.droppingLocations.getOrDefault(location, null);
        if (itemDrop != null) {
           itemDrop.stop();
           this.droppingLocations.remove(location);
        }
    }

    /**
     * Gets a copied enum map of the dropped items.
     * Key: The trigger material
     * Value: [Material - Spawning material, Long - repeating ticks ]
     * @return Map<Material, Object[]> - the map
     */
    public Map<Material, Object[]> getDroppings() {
        return new EnumMap<>(this.toDropItem);
    }
}
