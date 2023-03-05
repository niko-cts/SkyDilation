package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all special shop contents.
 * Will be displayed in ShopGUI and will seen in special slot
 * @see ShopTopic
 * @author Niko
 * @since 1.0
 */
public enum SpecialContents implements IShopContent, IItemSpecialName {
    PLACEHOLDER1(null, 0, null, "", (ItemStack) null),
    FISHING_ROD(CurrencyType.IRON, 8, new ItemBuilder(Material.FISHING_ROD).setName(TranslationKeys.SPECIAL_FISHING_ROD), TranslationKeys.SPECIAL_FISHING_ROD, new ItemStack(Material.FISHING_ROD, 1, (short) 32)),
    FLINT_AND_STEEL(CurrencyType.QUARTZ, 2, new ItemBuilder(Material.FLINT_AND_STEEL).setName(TranslationKeys.SPECIAL_FLINT_AND_STEEL), TranslationKeys.SPECIAL_FLINT_AND_STEEL, new ItemStack(Material.FLINT_AND_STEEL)),
    SNOW_BALL(CurrencyType.COAL, 16, new ItemBuilder(Material.SNOW_BALL).setName(TranslationKeys.SPECIAL_SNOW_BALL), TranslationKeys.SPECIAL_SNOW_BALL, new ItemStack(Material.SNOW_BALL)),
    SULPHUR(CurrencyType.IRON, 8, new ItemBuilder(Material.SULPHUR).setName(TranslationKeys.SPECIAL_SULPHUR), TranslationKeys.SPECIAL_SULPHUR, new ItemStack(Material.SULPHUR)),
    BLAZE_POWDER(CurrencyType.IRON, 8, new ItemBuilder(Material.BLAZE_POWDER).setName(TranslationKeys.SPECIAL_BLAZE_POWDER), TranslationKeys.SPECIAL_BLAZE_POWDER, new ItemStack(Material.BLAZE_POWDER)),
    ARMOR_STAND(CurrencyType.IRON, 6, new ItemBuilder(Material.ARMOR_STAND).setName(TranslationKeys.SPECIAL_ARMOR_STAND), TranslationKeys.SPECIAL_ARMOR_STAND, new ItemStack(Material.ARMOR_STAND)),
    TNT(CurrencyType.QUARTZ, 3, new ItemBuilder(Material.TNT).setName(TranslationKeys.SPECIAL_TNT), TranslationKeys.SPECIAL_TNT, new ItemStack(Material.TNT)),
    PLACEHOLDER2(null, 0, null, "", (ItemStack) null),
    PLACEHOLDER3(null, 0, null, "", (ItemStack) null),
    CARPET(CurrencyType.COAL, 16, new ItemBuilder(Material.CARPET).setName(TranslationKeys.SPECIAL_CARPET), TranslationKeys.SPECIAL_CARPET, new ItemStack(Material.CARPET)),
    WEB(CurrencyType.COAL, 16, new ItemBuilder(Material.WEB).setName(TranslationKeys.SPECIAL_WEB), TranslationKeys.SPECIAL_WEB, new ItemStack(Material.WEB)),
    LADDER(CurrencyType.COAL, 16, new ItemBuilder(Material.LADDER).setName(TranslationKeys.SPECIAL_LADDER), TranslationKeys.SPECIAL_LADDER, new ItemStack(Material.LADDER)),
    WATER_BUCKET(CurrencyType.COAL, 32, new ItemBuilder(Material.WATER_BUCKET).setName(TranslationKeys.SPECIAL_WATER_BUCKET), TranslationKeys.SPECIAL_WATER_BUCKET, new ItemStack(Material.WATER_BUCKET)),
    ENDER_PEARL(CurrencyType.QUARTZ, 14, new ItemBuilder(Material.ENDER_PEARL).setName(TranslationKeys.SPECIAL_ENDER_PEARL), TranslationKeys.SPECIAL_ENDER_PEARL, new ItemStack(Material.ENDER_PEARL)),
    BLAZE_ROD(CurrencyType.IRON, 15, new ItemBuilder(Material.BLAZE_ROD, 6).setName(TranslationKeys.SPECIAL_BLAZE_ROD), TranslationKeys.SPECIAL_BLAZE_ROD, new ItemStack(Material.BLAZE_ROD, 6)),
    GOLD_PLATE(CurrencyType.IRON, 25, new ItemBuilder(Material.GOLD_PLATE).setName(TranslationKeys.SPECIAL_GOLD_PLATE), TranslationKeys.SPECIAL_GOLD_PLATE, new ItemStack(Material.GOLD_PLATE));


    private final CurrencyType currencyType;
    private final int price;
    private final ItemBuilder displayInShop;
    private final String translationKey;
    private final ItemStack[] buyItem;

    SpecialContents(CurrencyType currencyType, int price, ItemBuilder displayInShop, String translationKey, ItemStack... buyItem) {
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
        return this.translationKey;
    }
}
