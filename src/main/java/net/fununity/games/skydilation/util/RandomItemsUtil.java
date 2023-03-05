package net.fununity.games.skydilation.util;

import net.fununity.games.skydilation.SkyDilation;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomItemsUtil {

    public static RandomItemsUtil instance;

    public static RandomItemsUtil getInstance() {
        if (instance == null)
            instance = new RandomItemsUtil();
        return instance;
    }

    private final Map<Integer, TreeMap<Float, ItemStack>> chestDrops;
    private final Map<Integer, Float> totalChestDropValue;


    public ItemStack[] getRandomChestItems(int richness) {
        ItemStack[] array = new ItemStack[27];

        if (!chestDrops.containsKey(richness)) {
            for (int i = 0; i < array.length; i++)
                array[i] = new ItemStack(Material.AIR);
            return array;
        }

        TreeMap<Float, ItemStack> possibility = chestDrops.get(richness);
        for (int i = 0; i < array.length; i++) {
            float value = RandomUtil.getRandomInt(Math.round(totalChestDropValue.get(richness))) + RandomUtil.getRandom().nextFloat();
            Map.Entry<Float, ItemStack> entry = possibility.ceilingEntry(value);
            array[i] = entry != null ? entry.getValue() : new ItemStack(Material.AIR);
        }

        return array;
    }

    /**
     * Caching drop probabilities from config.
     */
    private RandomItemsUtil() {
        Logger log = SkyDilation.getInstance().getLogger();
        FileConfiguration cfg = SkyDilation.getInstance().getConfig();

        String colItemDrops = "treasure-items";
        this.chestDrops = new HashMap<>();
        this.totalChestDropValue = new HashMap<>();

        for (String key : cfg.getConfigurationSection(colItemDrops).getKeys(true)) {
            TreeMap<Float, ItemStack> itemDrops = new TreeMap<>();
            int richness;
            try {
                richness = Integer.parseInt(key);
            } catch (NumberFormatException exception) {
                log.log(Level.WARNING, "{0} is not a legal Material. (config: {1} )", new Object[]{key, colItemDrops});
                continue;
            }
            this.totalChestDropValue.put(richness, 0f);

            String[] items = cfg.getString(colItemDrops + "." + key).split(";");
            for (String block : items) {
                Material dropItem;
                String[] data = block.split(":");
                if (data.length < 4 || data.length > 8) {
                    log.log(Level.WARNING, "An entry in {0} has too less data in it!", colItemDrops);
                    continue;
                }

                try {
                    dropItem = Material.valueOf(data[0]);
                } catch (IllegalArgumentException exception) {
                    log.log(Level.WARNING, "The Material {0} is illegal and wont be included.", data[0]);
                    continue;
                }

                float possibility;
                int amount;
                short subID;
                try {
                    subID = Short.parseShort(data[1]);
                    amount = Integer.parseInt(data[2]);
                    possibility = Float.parseFloat(data[3]);
                } catch (NumberFormatException exception) {
                    log.log(Level.WARNING, "Either the subID {0}, the amount {1} or the the possibility {2} is not a number.",
                            new Object[]{data[1], data[2], data[3]});
                    continue;
                }

                if (possibility == 0)
                    continue;

                ItemStack item = new ItemStack(Material.STONE);
                if (data.length == 4)
                    item = new ItemStack(dropItem, amount, subID);
                else if (data.length == 6) {
                    try {
                        item = new ItemBuilder(dropItem, subID).setAmount(amount)
                                .addEnchantment(Enchantment.getByName(data[4]),
                                        Integer.parseInt(data[5]), true).craft();
                    } catch (NumberFormatException exception) {
                        log.log(Level.WARNING, "{0} is not a valid enchantment level", data[5]);
                    } catch (IllegalArgumentException exception) {
                        log.log(Level.WARNING, "{0} is not a valid enchantment", data[4]);
                    }
                } else if (data.length == 8) {
                    try {
                        item = new ItemBuilder(dropItem, subID).setAmount(amount)
                                .addPotionEffect(new PotionEffect(PotionEffectType.getByName(data[4]),
                                        Integer.parseInt(data[5]), Integer.parseInt(data[6]),
                                        true, true)).craft();
                    } catch (NumberFormatException exception) {
                        log.log(Level.WARNING, "Either the duration {0}, the level {1} or the rgb {2} is not a number.",
                                new Object[]{data[5], data[6], data[7]});
                    } catch (IllegalArgumentException exception) {
                        log.log(Level.WARNING, "{0} is not a valid potion", data[4]);
                    }
                } else
                    log.log(Level.WARNING, "The entry {0} has an invalid data length", Arrays.toString(data));

                float v = this.totalChestDropValue.get(richness) + possibility;
                this.totalChestDropValue.put(richness, v);
                itemDrops.put(v, item);
            }
            this.chestDrops.put(richness, itemDrops);
        }
    }
}
