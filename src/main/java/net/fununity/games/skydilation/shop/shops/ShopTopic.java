package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all general shops.
 * Each shop holds information about it's name, page and shop items
 * @author Niko
 * @since 1.0
 */
public enum ShopTopic {

    ARMOR(TranslationKeys.SHOP_ARMOR, Material.IRON_CHESTPLATE, ArmorContents.values()),
    PICKAXE(TranslationKeys.SHOP_PICKAXE, Material.IRON_PICKAXE, PickaxeContents.values()),
    SWORDS(TranslationKeys.SHOP_SWORDS, Material.IRON_SWORD, SwordContents.values()),
    BOWS(TranslationKeys.SHOP_BOWS, Material.BOW, BowContents.values()),
    FOOD(TranslationKeys.SHOP_FOOD, Material.CARROT, FoodContents.values()),
    SPECIAL(TranslationKeys.SHOP_SPECIAL, Material.ENDER_PEARL, SpecialContents.values()),
    TRANKS(TranslationKeys.SHOP_TRANKS, Material.POTION, TrankContents.values()),
    BIOME_PICKER(TranslationKeys.SHOP_CHOOSER, Material.WATCH, BiomePickerContents.values());

    private final String translationKey;
    private final Material displayMaterial;
    private final IShopContent[] shops;

    /**
     * Init the enum
     * @param translationKey String - TranslationKey of general shop
     * @param shops IShop[] - All shop contents
     * @since 1.0
     */
    ShopTopic(String translationKey, Material displayMaterial, IShopContent[] shops) {
        this.translationKey = translationKey;
        this.displayMaterial = displayMaterial;
        this.shops = shops;
    }

    /**
     * Translates the TranslationKey to given language
     * @param language Language - Language to translate
     * @return String - Translated shop Name
     * @since 1.0
     */
    public String getName(Language language) {
        return language.getTranslation(getTranslationKey());
    }

    /**
     * Get the raw translation Key
     * @return String - Translation key of shop
     * @since 1.0
     */
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Get the contents of shop topic
     * @return IShop[] - Shop contents
     * @since 1.0
     */
    public IShopContent[] getShops() {
        return shops;
    }

    /**
     * Returns an item that contains the displayMaterial and shop name in it.
     * @param language Language - Language to translate
     * @return ItemStack - item to represent the shop
     * @since 1.0
     */
    public ItemStack getDisplayItem(Language language) {
        return new ItemBuilder(displayMaterial).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setName(ChatColor.GRAY + SpecialChars.DOUBLE_ARROW_RIGHT + ChatColor.BLUE + getName(language)).craft();
    }
}
