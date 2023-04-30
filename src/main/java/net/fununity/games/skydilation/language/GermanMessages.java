package net.fununity.games.skydilation.language;

import net.fununity.main.api.common.messages.MessageList;
import net.fununity.misc.translationhandler.TranslationHandler;
/**
 * Translates all Keys from {@link TranslationKeys} to german.
 *
 * @author Niko
 * @since 1.0
 */
public class GermanMessages extends MessageList {

    /**
     * Translates keys and insert it
     *
     * @since 1.0
     */
    public GermanMessages() {
        super(TranslationHandler.getInstance().getLanguageHandler().getLanguageByCode("de"));

        //CURRENCY
        add(TranslationKeys.CURRENCY_COAL, "&8Kohle");
        add(TranslationKeys.CURRENCY_IRON, "&7Eisen");
        add(TranslationKeys.CURRENCY_GOLD, "&6Gold");
        add(TranslationKeys.CURRENCY_QUARTZ, "&fQuartz");
        add(TranslationKeys.CURRENCY_NETHER_STAR, "&dNetherstar");

        add(TranslationKeys.SHOP_TITLE, "&6Shop");
        add(TranslationKeys.SHOP_BLOCKS, "Blöcke");
        add(TranslationKeys.SHOP_ARMOR, "Rüstung");
        add(TranslationKeys.SHOP_PICKAXE, "Spitzhacke");
        add(TranslationKeys.SHOP_SWORDS, "Schwerter");
        add(TranslationKeys.SHOP_BOWS, "Bögen");
        add(TranslationKeys.SHOP_FOOD, "Essen");
        add(TranslationKeys.SHOP_SPECIAL, "Specials");
        add(TranslationKeys.SHOP_TRANKS, "Tränke");
        add(TranslationKeys.SHOP_CHOOSER, "Kits");
        add(TranslationKeys.SHOP_NOT_ENOUGH, "&cNicht genug &e${type}&7!");
        add(TranslationKeys.SHOP_NOT_ENOUGH_AMOUNT, "&cDu brauchst weitere &e${amount} ${type}&7!");

        // SHOP ITEMS
        add(TranslationKeys.ARMOR_LEATHER_HELMET, "&7Lederhelm");
        add(TranslationKeys.ARMOR_LEATHER_CHESTPLATE, "&7Lederbrustplatte");
        add(TranslationKeys.ARMOR_LEATHER_LEGGINGS, "&7Lederhose");
        add(TranslationKeys.ARMOR_LEATHER_BOOTS, "&7Lederschuhe");
        add(TranslationKeys.ARMOR_GOLD_HELMET, "&7Goldhelm");
        add(TranslationKeys.ARMOR_GOLD_CHESTPLATE, "&7Goldbrustplatte");
        add(TranslationKeys.ARMOR_GOLD_LEGGINGS, "&7Goldhose");
        add(TranslationKeys.ARMOR_GOLD_BOOTS, "&7Goldschuhe");
        add(TranslationKeys.ARMOR_IRON_HELMET, "&7Eisenhelm");
        add(TranslationKeys.ARMOR_IRON_CHESTPLATE, "&7Eisenbrustplatte");
        add(TranslationKeys.ARMOR_IRON_BOOTS, "&7Eisenschuhe");


        add(TranslationKeys.BOWS_BOW, "&7Bogen I");
        add(TranslationKeys.BOWS_BOW2, "&7Bogen II");
        add(TranslationKeys.BOWS_ARROW, "&7Pfeil");

        add(TranslationKeys.FOOD_CARROT_ITEM, "&7Karotte");
        add(TranslationKeys.FOOD_COOKED_BEEF, "&7Steak");
        add(TranslationKeys.FOOD_CAKE, "&7Kuche");
        add(TranslationKeys.FOOD_GOLD_CARROT, "&7Goldene Karrotte");
        add(TranslationKeys.FOOD_GOLD_APPLE, "&7Goldener Apfel");

        add(TranslationKeys.PICKAXE_WOOD_PICKAXE, "&7Holzspitzhacke");
        add(TranslationKeys.PICKAXE_STONE_PICKAXE, "&7Steinspitzhacke");
        add(TranslationKeys.PICKAXE_IRON_PICKAXE, "&7Eisenspitzhacke");
        add(TranslationKeys.PICKAXE_GOLD_PICKAXE, "&7Goldspitzhacke");

        add(TranslationKeys.SWORDS_STICK, "&7Knüppel");
        add(TranslationKeys.SWORDS_WOOD_SWORD, "&7Holzschwert");
        add(TranslationKeys.SWORDS_GOLD_SWORD, "&7Goldschwert");
        add(TranslationKeys.SWORDS_IRON_SWORD, "&7Eisenschwert");

        add(TranslationKeys.TRANK_HEAL, "&7Heilung");
        add(TranslationKeys.TRANK_REGENERATION, "&7Regeneration");
        add(TranslationKeys.TRANK_INCREASE_DAMAGE, "&7Stärke");
        add(TranslationKeys.TRANK_JUMP, "&7Sprung");
        add(TranslationKeys.TRANK_INVISIBILITY, "&7Unsichtbarkeit");
        add(TranslationKeys.TRANK_SPEED, "&7Geschwindigkeit");

        add(TranslationKeys.SPECIAL_FISHING_ROD, "&7Angel");
        add(TranslationKeys.SPECIAL_FLINT_AND_STEEL, "&7Feuerzeug");
        add(TranslationKeys.SPECIAL_SNOW_BALL, "&7Schneeball");
        add(TranslationKeys.SPECIAL_SULPHUR, "&7Home-Teleport");
        add(TranslationKeys.SPECIAL_BLAZE_POWDER, "&7Rettungsplattform");
        add(TranslationKeys.SPECIAL_ARMOR_STAND, "&7Mobiler Shop");
        add(TranslationKeys.SPECIAL_TNT, "&7TNT");
        add(TranslationKeys.SPECIAL_CARPET, "&7Schnelligkeit");
        add(TranslationKeys.SPECIAL_WEB, "&7Spinnennetz");
        add(TranslationKeys.SPECIAL_LADDER, "&7Leiter");
        add(TranslationKeys.SPECIAL_WATER_BUCKET, "&7Wassereimer");
        add(TranslationKeys.SPECIAL_ENDER_PEARL, "&7Enderperle");
        add(TranslationKeys.SPECIAL_BLAZE_ROD, "&7Granatenwerfer");
        add(TranslationKeys.SPECIAL_GOLD_PLATE, "&7Sprungplatte");
        add(TranslationKeys.ITEM_TELEPORT_START, "&7Teleportation! &e3 Sekunden &7nicht bewegen!");
        add(TranslationKeys.ITEM_TELEPORT_CANCEL, "&7Teleportation &cabgebrochen&7!");

        // biome chooser
        add(TranslationKeys.BIOME_CHOOSER_LORE, "&7Choose what the next biome should be");
        add(TranslationKeys.BIOME_CHOOSER_PLAINS, "&aPlain &7- &8Coal x1");
        add(TranslationKeys.BIOME_CHOOSER_SAVANNA, "&eSavanna &7- &8Coal x2");
        add(TranslationKeys.BIOME_CHOOSER_JUNGLE, "&2Jungle &7- &8Coal x3");
        add(TranslationKeys.BIOME_CHOOSER_MUSHROOM, "&cMushroom &7- &7Iron");
        add(TranslationKeys.BIOME_CHOOSER_NETHER_1, "&4Nether &7- &fQuartz");
        add(TranslationKeys.BIOME_CHOOSER_NETHER_2, "&4Nether &7- &6Gold");
        add(TranslationKeys.BIOME_CHOOSER_END, "&8End &7- &dNetherstar");


        // commands
        add(TranslationKeys.COMMANDS_ITEMDROPPING_INFO, "&7Type '/items' to see all item spawners.");
        add(TranslationKeys.COMMANDS_ITEMDROPPING_USAGE, "items");
        add(TranslationKeys.COMMANDS_ITEMDROPPING_DESCRIPTION, "&7Opens a book with all items generating a material.");
        add(TranslationKeys.COMMANDS_ITEMDROPPING_TITLE, "Place this on the ground and let it generate the new material:");

        insertIntoLanguage();
    }
}
