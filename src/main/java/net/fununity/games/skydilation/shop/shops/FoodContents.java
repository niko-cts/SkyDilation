package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all food shop contents.
 * Will be displayed in ShopGUI and will be seen in food slot
 * @see ShopTopic
 * @author Niko
 * @since 1.0
 */
public enum FoodContents implements IShopContent {
    PLACEHOLDER1(null, 0, null, (ItemStack) null),
    PLACEHOLDER2(null, 0, null, (ItemStack) null),
    CARROT_ITEM(CurrencyType.COAL, 1, new ItemBuilder(Material.CARROT).setName(TranslationKeys.FOOD_CARROT_ITEM), new ItemStack(Material.CARROT)),
    COOKED_BEEF(CurrencyType.COAL, 5, new ItemBuilder(Material.COOKED_BEEF).setName(TranslationKeys.FOOD_COOKED_BEEF), new ItemStack(Material.COOKED_BEEF)),
    CAKE(CurrencyType.IRON, 3, new ItemBuilder(Material.CAKE).setName(TranslationKeys.FOOD_CAKE), new ItemStack(Material.CAKE)),
    GOLD_CARROT(CurrencyType.IRON, 2, new ItemBuilder(Material.GOLDEN_CARROT).setName(TranslationKeys.FOOD_GOLD_CARROT), new ItemStack(Material.GOLDEN_CARROT)),
    GOLD_APPLE(CurrencyType.QUARTZ, 15, new ItemBuilder(Material.GOLDEN_APPLE).setName(TranslationKeys.FOOD_GOLD_APPLE), new ItemStack(Material.GOLDEN_APPLE));


    private final CurrencyType currencyType;
    private final int price;
    private final ItemBuilder displayInShop;
    private final ItemStack[] buyItem;

    FoodContents(CurrencyType currencyType, int price, ItemBuilder displayInShop, ItemStack... buyItem) {
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
