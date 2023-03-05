package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all kit shop contents.
 * Will be displayed in ShopGUI and will be seen in kit slot
 * @see ShopTopic
 * @author Niko
 * @since 1.0
 */
public enum BiomePickerContents implements IBiomeShopContent, IItemSpecialName {
    PLAINS(CurrencyType.NETHERSTAR, 2,
            new ItemBuilder(Material.GRASS).setName(TranslationKeys.BIOME_CHOOSER_PLAINS).setLore(TranslationKeys.BIOME_CHOOSER_LORE),
            TranslationKeys.BIOME_CHOOSER_PLAINS, Biomes.PLAINS,
            new ItemBuilder(Material.GRASS).craft()),
    SAVANNA(CurrencyType.NETHERSTAR, 3,
            new ItemBuilder(Material.LOG_2).setName(TranslationKeys.BIOME_CHOOSER_SAVANNA).setLore(TranslationKeys.BIOME_CHOOSER_LORE),
            TranslationKeys.BIOME_CHOOSER_SAVANNA, Biomes.SAVANNA,
            new ItemBuilder(Material.LOG_2).craft()),
    JUNGLE(CurrencyType.NETHERSTAR, 4,
            new ItemBuilder(Material.LOG, (short) 3).setName(TranslationKeys.BIOME_CHOOSER_JUNGLE).setLore(TranslationKeys.BIOME_CHOOSER_LORE),
            TranslationKeys.BIOME_CHOOSER_JUNGLE, Biomes.JUNGLE,
            new ItemBuilder(Material.LOG, (short) 3).craft()),
    MUSHROOM(CurrencyType.NETHERSTAR, 10,
            new ItemBuilder(Material.MYCEL).setName(TranslationKeys.BIOME_CHOOSER_MUSHROOM).setLore(TranslationKeys.BIOME_CHOOSER_LORE),
            TranslationKeys.BIOME_CHOOSER_MUSHROOM, Biomes.MUSHROOM,
            new ItemBuilder(Material.MYCEL).craft()),
    NETHER_1(CurrencyType.NETHERSTAR, 15,
            new ItemBuilder(Material.NETHER_WARTS).setName(TranslationKeys.BIOME_CHOOSER_NETHER_1).setLore(TranslationKeys.BIOME_CHOOSER_LORE),
            TranslationKeys.BIOME_CHOOSER_NETHER_1, Biomes.NETHER_1,
            new ItemBuilder(Material.NETHER_WARTS).craft()),
    NETHER_2(CurrencyType.NETHERSTAR, 20,
            new ItemBuilder(Material.MAGMA).setName(TranslationKeys.BIOME_CHOOSER_NETHER_2).setLore(TranslationKeys.BIOME_CHOOSER_LORE),
            TranslationKeys.BIOME_CHOOSER_NETHER_2, Biomes.NETHER_2,
            new ItemBuilder(Material.MAGMA).craft()),
    END(CurrencyType.NETHERSTAR, 30,
            new ItemBuilder(Material.ENDER_STONE).setName(TranslationKeys.BIOME_CHOOSER_END).setLore(TranslationKeys.BIOME_CHOOSER_LORE),
            TranslationKeys.BIOME_CHOOSER_END, Biomes.END,
            new ItemBuilder(Material.ENDER_STONE).craft());


    private final CurrencyType currencyType;
    private final int price;
    private final ItemBuilder displayInShop;
    private final String nameKey;
    private final ItemStack[] buyItem;
    private final Biomes biome;

    BiomePickerContents(CurrencyType currencyType, int price, ItemBuilder displayInShop, String nameKey, Biomes biome, ItemStack... buyItem) {
        this.currencyType = currencyType;
        this.price = price;
        this.displayInShop = displayInShop;
        this.nameKey = nameKey;
        this.biome = biome;
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
        return nameKey;
    }

    @Override
    public Biomes getRepresentingBiomes() {
        return biome;
    }
}
