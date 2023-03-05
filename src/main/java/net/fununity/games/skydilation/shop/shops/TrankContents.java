package net.fununity.games.skydilation.shop.shops;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.CurrencyType;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Holds all trank shop contents.
 * Will be displayed in ShopGUI and will seen in trank slot
 * @see ShopTopic
 * @author Niko
 * @since 1.0
 */
public enum TrankContents implements IShopContent, IItemSpecialName {

    PLACEHOLDER1(null, 0, null, null, (ItemStack) null),
    HEAL(CurrencyType.IRON, 5, TranslationKeys.TRANK_HEAL, new ItemBuilder(Material.POTION).setName(TranslationKeys.TRANK_HEAL).addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 0, 1, false, true)), new ItemBuilder(Material.POTION).addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 0, 1, false, true)).craft()),
    REGENERATION(CurrencyType.IRON, 10, TranslationKeys.TRANK_REGENERATION, new ItemBuilder(Material.POTION).setName(TranslationKeys.TRANK_REGENERATION).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*5, 1, false, true)), new ItemBuilder(Material.POTION).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*20, 1, false, true)).craft()),
    INCREASE_DAMAGE(CurrencyType.QUARTZ, 10, TranslationKeys.TRANK_INCREASE_DAMAGE, new ItemBuilder(Material.POTION).setName(TranslationKeys.TRANK_INCREASE_DAMAGE).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*30, 1, false, true)), new ItemBuilder(Material.POTION).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*20, 1, false, true)).craft()),
    PLACEHOLDER2(null, 0, null, null, (ItemStack) null),
    JUMP(CurrencyType.IRON, 5, TranslationKeys.TRANK_JUMP, new ItemBuilder(Material.POTION).setName(TranslationKeys.TRANK_JUMP).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*60, 1, false, true)), new ItemBuilder(Material.POTION).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*60, 1, false, true)).craft()),
    INVISIBILITY(CurrencyType.QUARTZ, 5, TranslationKeys.TRANK_JUMP, new ItemBuilder(Material.POTION).setName(TranslationKeys.TRANK_INVISIBILITY).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*30, 1, false, true)), new ItemBuilder(Material.POTION).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*30, 1, false, true)).craft()),
    SPEED(CurrencyType.IRON, 7, TranslationKeys.TRANK_SPEED, new ItemBuilder(Material.POTION).setName(TranslationKeys.TRANK_SPEED).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*30, 1, false, true)), new ItemBuilder(Material.POTION).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*30, 1, false, true)).craft());

    private final CurrencyType currencyType;
    private final int price;
    private final String translation;
    private final ItemBuilder displayInShop;
    private final ItemStack[] buyItem;

    TrankContents(CurrencyType currencyType, int price, String translation, ItemBuilder displayInShop, ItemStack... buyItem) {
        this.currencyType = currencyType;
        this.price = price;
        this.translation = translation;
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

    /**
     * Returns the translation key of the name
     * This Key will be applied on the buy item
     * @return String - Name key of item
     * @since 1.0
     */
    @Override
    public String getTranslationKey() {
        return translation;
    }
}
