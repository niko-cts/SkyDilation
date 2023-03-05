package net.fununity.games.skydilation.shop;

import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * All currency types of Flower Wars.
 * Holds information about the color and the translation key
 * @author Niko
 * @since 1.0
 */
public enum CurrencyType {

    COAL(ChatColor.DARK_GRAY, TranslationKeys.CURRENCY_COAL, Material.COAL),
    IRON(ChatColor.GRAY, TranslationKeys.CURRENCY_IRON, Material.IRON_INGOT),
    GOLD(ChatColor.GOLD, TranslationKeys.CURRENCY_GOLD, Material.GOLD_INGOT),
    QUARTZ(ChatColor.WHITE, TranslationKeys.CURRENCY_QUARTZ, Material.QUARTZ),
    NETHERSTAR(ChatColor.LIGHT_PURPLE, TranslationKeys.CURRENCY_NETHER_STAR, Material.NETHER_STAR);


    private final ChatColor color;
    private final String translationKey;
    private final Material material;

    /**
     * Needs a color and translationKey
     * @param color ChatColor - Color of currency
     * @param translationKey String - Translation Key of currency
     * @since 1.0
     */
    CurrencyType(ChatColor color, String translationKey, Material material) {
        this.color = color;
        this.translationKey = translationKey;
        this.material = material;
    }

    /**
     * Gets the color of the currency
     * @return ChatColor - Currency Color
     * @since 1.0
     */
    public ChatColor getColor() {
        return color;
    }

    /**
     * Translates the TranslationKey to given language
     * @param language Language - Language to translate
     * @return String - Translated shop Name
     * @since 1.0
     */
    public String getName(Language language) {
        return language.getTranslation(translationKey);
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
     * Get Material of Currency
     * @return Material - Item Material of Currency
     * @since 1.0
     */
    public Material getMaterial() {
        return material;
    }

}
