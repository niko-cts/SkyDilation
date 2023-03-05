package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.inventory.ItemStack;

/**
 * Interface for all shop contents.
 * The shop contents are hold in {@link ShopTopic} and will be displayed,
 * when a player opens the specified shop.
 * @author Niko
 * @since 1.0
 */
public interface IShopContent {

    /**
     * Get the item to display in shop in the given language
     * @param language Language - Language to display the item
     * @return ItemStack - Item to display in shop
     */
    ItemStack getDisplayItem(Language language);

    /**
     * Return the item the player gets, when buying it.
     * @return ItemStack - Raw item
     */
    ItemStack[] getBuyItems();

    /**
     * The Type to sell the item (BRONZE, IRON, GOLD)
     * @return CurrencyType - Currency of the item
     */
    CurrencyType getCurrencyType();

    /**
     * Price or amount for this item
     * Price will be displayed 'currencyType price'
     * @return int - price of content
     */
    int getPrice();

}
