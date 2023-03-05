package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all armor shop contents.
 * Will be displayed in ShopGUI and will seen in armor slot
 *
 * @author Niko
 * @see ShopTopic
 * @since 1.0
 */
public enum ArmorContents implements IShopContent {
    LEATHER_HELMET(CurrencyType.COAL, 2, new ItemBuilder(Material.LEATHER_HELMET).setName(TranslationKeys.ARMOR_LEATHER_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.LEATHER_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    LEATHER_CHESTPLATE(CurrencyType.COAL, 8, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName(TranslationKeys.ARMOR_LEATHER_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.LEATHER_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    LEATHER_LEGGINGS(CurrencyType.COAL, 4, new ItemBuilder(Material.LEATHER_LEGGINGS).setName(TranslationKeys.ARMOR_LEATHER_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.LEATHER_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    LEATHER_BOOTS(CurrencyType.COAL, 2, new ItemBuilder(Material.LEATHER_BOOTS).setName(TranslationKeys.ARMOR_LEATHER_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.LEATHER_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    PLACEHOLDER1(null, 0, null, (ItemStack) null),
    GOLD_HELMET(CurrencyType.IRON, 2, new ItemBuilder(Material.GOLD_HELMET).setName(TranslationKeys.ARMOR_GOLD_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.GOLD_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    GOLD_CHESTPLATE(CurrencyType.IRON, 8, new ItemBuilder(Material.GOLD_CHESTPLATE).setName(TranslationKeys.ARMOR_GOLD_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.GOLD_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    GOLD_LEGGINGS(CurrencyType.IRON, 4, new ItemBuilder(Material.GOLD_LEGGINGS).setName(TranslationKeys.ARMOR_GOLD_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.GOLD_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    GOLD_BOOTS(CurrencyType.IRON, 2, new ItemBuilder(Material.GOLD_BOOTS).setName(TranslationKeys.ARMOR_GOLD_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1), new ItemBuilder(Material.GOLD_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).craft()),
    PLACEHOLDER2(null, 0, null, (ItemStack) null),
    PLACEHOLDER3(null, 0, null, (ItemStack) null),
    PLACEHOLDER4(null, 0, null, (ItemStack) null),
    IRON_HELMET(CurrencyType.QUARTZ, 3, new ItemBuilder(Material.IRON_HELMET).setName(TranslationKeys.ARMOR_IRON_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 2), new ItemBuilder(Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 2).craft()),
    IRON_CHESTPLATE(CurrencyType.QUARTZ, 6, new ItemBuilder(Material.IRON_CHESTPLATE).setName(TranslationKeys.ARMOR_IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 2), new ItemBuilder(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.THORNS, 2).craft()),
    IRON_BOOTS(CurrencyType.QUARTZ, 3, new ItemBuilder(Material.IRON_BOOTS).setName(TranslationKeys.ARMOR_IRON_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.PROTECTION_FALL, 1), new ItemBuilder(Material.IRON_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchantment(Enchantment.PROTECTION_FALL, 2).craft());


    private final CurrencyType currencyType;
    private final int price;
    private final ItemBuilder displayInShop;
    private final ItemStack[] buyItem;

    ArmorContents(CurrencyType currencyType, int price, ItemBuilder displayInShop, ItemStack... buyItem) {
        this.currencyType = currencyType;
        this.price = price;
        this.displayInShop = displayInShop;
        this.buyItem = buyItem;
    }

    /**
     * Get the item to display in shop in the given language
     *
     * @param language Language - Language to display the item
     * @return ItemStack - Item to display in shop
     * @since 1.0
     */
    @Override
    public ItemStack getDisplayItem(Language language) {
        return this.displayInShop.translate(language);
    }

    /**
     * Return the item the player gets, when buying it.
     *
     * @return ItemStack[] - Raw items
     * @since 1.0
     */
    @Override
    public ItemStack[] getBuyItems() {
        return this.buyItem;
    }

    /**
     * The Type to sell the item (BRONZE, IRON, GOLD)
     *
     * @return CurrencyType - Currency of the item
     * @since 1.0
     */
    @Override
    public CurrencyType getCurrencyType() {
        return this.currencyType;
    }

    /**
     * Price or amount for this item
     * Price will be displayed 'currencyType price'
     *
     * @return int - price of content
     * @since 1.0
     */
    @Override
    public int getPrice() {
        return this.price;
    }
}
