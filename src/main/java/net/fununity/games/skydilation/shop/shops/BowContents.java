package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all bow shop contents.
 * Will be displayed in ShopGUI and will seen in bow slot
 * @see ShopTopic
 * @author Niko
 * @since 1.0
 */
public enum BowContents implements IShopContent {
    PLACEHOLDER1(null, 0, null, (ItemStack) null),
    PLACEHOLDER2(null, 0, null, (ItemStack) null),
    PLACEHOLDER3(null, 0, null, (ItemStack) null),
    BOW(CurrencyType.QUARTZ, 7, new ItemBuilder(Material.BOW).setName(TranslationKeys.BOWS_BOW).addEnchantment(Enchantment.ARROW_INFINITE, 1), new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_INFINITE, 1).craft()),
    BOW2(CurrencyType.QUARTZ, 11, new ItemBuilder(Material.BOW).setName(TranslationKeys.BOWS_BOW2).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 1), new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment (Enchantment.ARROW_DAMAGE, 1).craft()),
    ARROW(CurrencyType.IRON, 2, new ItemBuilder(Material.ARROW).setName(TranslationKeys.BOWS_ARROW), new ItemStack(Material.ARROW));


    private final CurrencyType currencyType;
    private final int price;
    private final ItemBuilder displayInShop;
    private final ItemStack[] buyItem;

    BowContents(CurrencyType currencyType, int price, ItemBuilder displayInShop, ItemStack... buyItem) {
        this.currencyType = currencyType;
        this.price = price;
        this.displayInShop = displayInShop;
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
}
