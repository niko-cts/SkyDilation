package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Holds all pickaxe shop contents.
 * Will be displayed in ShopGUI and will be seen in pickaxe slot
 * @see ShopTopic
 * @author Niko
 * @since 1.0
 */
public enum PickaxeContents implements IShopContent {
    PLACEHOLDER1(null, 0, null, (ItemStack) null),
    PLACEHOLDER2(null, 0, null, (ItemStack) null),
    WOOD_PICKAXE(CurrencyType.COAL, 4, new ItemBuilder(Material.WOOD_PICKAXE).setName(TranslationKeys.PICKAXE_WOOD_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1), new ItemBuilder(Material.WOOD_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1).craft()),
    STONE_PICKAXE(CurrencyType.IRON, 3, new ItemBuilder(Material.STONE_PICKAXE).setName(TranslationKeys.PICKAXE_STONE_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1), new ItemBuilder(Material.STONE_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1).craft()),
    PLACEHOLDER3(null, 0, null, (ItemStack) null),
    IRON_PICKAXE(CurrencyType.QUARTZ, 2, new ItemBuilder(Material.IRON_PICKAXE).setName(TranslationKeys.PICKAXE_IRON_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1), new ItemBuilder(Material.IRON_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1).craft()),
    GOLD_PICKAXE(CurrencyType.QUARTZ, 30, new ItemBuilder(Material.GOLD_PICKAXE, (short) 20).setName(TranslationKeys.PICKAXE_GOLD_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 2), new ItemBuilder(Material.GOLD_PICKAXE, (short) 20).addEnchantment(Enchantment.DIG_SPEED, 2).craft());


    private final CurrencyType currencyType;
    private final int price;
    private final ItemBuilder displayInShop;
    private final ItemStack[] buyItem;

    PickaxeContents(CurrencyType currencyType, int price, ItemBuilder displayInShop, ItemStack... buyItem) {
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
