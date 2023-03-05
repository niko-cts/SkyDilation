package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all armor sword contents.
 * Will be displayed in ShopGUI and will seen in sword slot
 * @see ShopTopic
 * @author Niko
 * @since 1.0
 */
public enum SwordContents implements IShopContent, IItemSpecialName {
    PLACEHOLDER1(null, 0, null, "", (ItemStack) null),
    PLACEHOLDER2(null, 0, null, "",(ItemStack) null),
    STICK(CurrencyType.COAL, 8, new ItemBuilder(Material.STICK).setName(TranslationKeys.SWORDS_STICK).addEnchantment(Enchantment.KNOCKBACK, 2), TranslationKeys.SWORDS_STICK, new ItemBuilder(Material.STICK).addEnchantment(Enchantment.KNOCKBACK, 2).craft()),
    WOOD_SWORD(CurrencyType.IRON, 2, new ItemBuilder(Material.WOOD_SWORD).setName(TranslationKeys.SWORDS_WOOD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1), "", new ItemBuilder(Material.WOOD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).craft()),
    PLACEHOLDER3(null, 0, null, "",(ItemStack) null),
    GOLD_SWORD(CurrencyType.IRON, 3, new ItemBuilder(Material.GOLD_SWORD).setName(TranslationKeys.SWORDS_GOLD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2), "",new ItemBuilder(Material.GOLD_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).craft()),
    IRON_SWORD(CurrencyType.QUARTZ, 7, new ItemBuilder(Material.IRON_SWORD).setName(TranslationKeys.SWORDS_IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1), "", new ItemBuilder(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).craft());

    private final CurrencyType currencyType;
    private final int price;
    private final ItemBuilder displayInShop;
    private final String translationKey;
    private final ItemStack[] buyItem;

    SwordContents(CurrencyType currencyType, int price, ItemBuilder displayInShop, String translationKey, ItemStack... buyItem) {
        this.currencyType = currencyType;
        this.price = price;
        this.displayInShop = displayInShop;
        this.translationKey = translationKey;
        this.buyItem = buyItem;
    }

    /**
     * Get the item to display in shop in the given language
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
     * @return ItemStack[] - Raw items
     * @since 1.0
     */
    @Override
    public ItemStack[] getBuyItems() {
        return this.buyItem;
    }

    /**
     * The Type to sell the item (BRONZE, IRON, GOLD)
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
     * @return int - price of content
     * @since 1.0
     */
    @Override
    public int getPrice() {
        return this.price;
    }

    /**
     * Returns the translation key of the name
     * This Key will be applied on the buy item
     * @return String - Name key of item
     * @since 1.0
     */
    @Override
    public String getTranslationKey() {
        return translationKey;
    }
}
