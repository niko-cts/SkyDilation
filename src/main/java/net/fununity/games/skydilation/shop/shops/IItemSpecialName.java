package net.fununity.games.skydilation.shop.shops;

/**
 * If an shop item has a special name, which should be applied to when a item was sold
 * @author Niko
 * @since 1.0
 */
public interface IItemSpecialName {

    /**
     * Returns the translation key of the name
     * This Key will be applied on the buy item
     * @return String - Name key of item
     * @since 1.0
     */
    String getTranslationKey();

}
