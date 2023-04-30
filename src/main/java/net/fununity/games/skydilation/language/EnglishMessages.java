package net.fununity.games.skydilation.language;

import net.fununity.main.api.common.messages.MessageList;
import net.fununity.misc.translationhandler.TranslationHandler;

/**
 * Translates all Keys from {@link TranslationKeys} to german.
 *
 * @author Niko
 * @since 1.0
 */
public class EnglishMessages  extends MessageList {

    /**
     * Translates keys and insert it
     *
     * @since 1.0
     */
    public EnglishMessages() {
        super(TranslationHandler.getInstance().getLanguageHandler().getLanguageByCode("en"));

        //CURRENCY
        add(TranslationKeys.CURRENCY_COAL, "&8Coal");
        add(TranslationKeys.CURRENCY_IRON, "&7Iron");
        add(TranslationKeys.CURRENCY_GOLD, "&6Gold");
        add(TranslationKeys.CURRENCY_QUARTZ, "&fQuartz");
        add(TranslationKeys.CURRENCY_NETHER_STAR, "&dNetherstar");

        add(TranslationKeys.SHOP_TITLE, "&6Shop");
        add(TranslationKeys.SHOP_BLOCKS, "Blocks");
        add(TranslationKeys.SHOP_ARMOR, "Armour");
        add(TranslationKeys.SHOP_PICKAXE, "Pickaxe");
        add(TranslationKeys.SHOP_SWORDS, "Swords");
        add(TranslationKeys.SHOP_BOWS, "Bows");
        add(TranslationKeys.SHOP_FOOD, "Food");
        add(TranslationKeys.SHOP_SPECIAL, "Specials");
        add(TranslationKeys.SHOP_TRANKS, "Potions");
        add(TranslationKeys.SHOP_CHOOSER, "Biome Picker");
        add(TranslationKeys.SHOP_NOT_ENOUGH, "&cNot enough &e${type}&7!");
        add(TranslationKeys.SHOP_NOT_ENOUGH_AMOUNT, "&cYou need &e${amount} &cmore ${type}&7!");

        // SHOP ITEMS
        add(TranslationKeys.ARMOR_LEATHER_HELMET, "&7Leather Hat");
        add(TranslationKeys.ARMOR_LEATHER_CHESTPLATE, "&7Leather Tunic");
        add(TranslationKeys.ARMOR_LEATHER_LEGGINGS, "&7Leather Trousers");
        add(TranslationKeys.ARMOR_LEATHER_BOOTS, "&7Leather Boots");
        add(TranslationKeys.ARMOR_GOLD_HELMET, "&7Golden Helmet");
        add(TranslationKeys.ARMOR_GOLD_CHESTPLATE, "&7Golden Chestplate");
        add(TranslationKeys.ARMOR_GOLD_LEGGINGS, "&7Golden Leggins");
        add(TranslationKeys.ARMOR_GOLD_BOOTS, "&7Golden Boots");
        add(TranslationKeys.ARMOR_IRON_HELMET, "&7Iron Helmet");
        add(TranslationKeys.ARMOR_IRON_CHESTPLATE, "&7Iron Chestplate");
        add(TranslationKeys.ARMOR_IRON_BOOTS, "&7Iron Boots");

        add(TranslationKeys.BOWS_BOW, "&7Bow I");
        add(TranslationKeys.BOWS_BOW2, "&7Bow II");
        add(TranslationKeys.BOWS_ARROW, "&7Arrow");

        add(TranslationKeys.FOOD_CARROT_ITEM, "&7Carrot");
        add(TranslationKeys.FOOD_COOKED_BEEF, "&7Steak");
        add(TranslationKeys.FOOD_CAKE, "&7Cake");
        add(TranslationKeys.FOOD_GOLD_CARROT, "&7Golden Carrot");
        add(TranslationKeys.FOOD_GOLD_APPLE, "&7Golden Appel");

        add(TranslationKeys.PICKAXE_WOOD_PICKAXE, "&7Wooden Pickaxe");
        add(TranslationKeys.PICKAXE_STONE_PICKAXE, "&7Stone Pickaxe");
        add(TranslationKeys.PICKAXE_IRON_PICKAXE, "&7Iron Pickaxe");
        add(TranslationKeys.PICKAXE_GOLD_PICKAXE, "&7Golden Pickaxe");

        add(TranslationKeys.SWORDS_STICK, "&7Knockback Stick");
        add(TranslationKeys.SWORDS_WOOD_SWORD, "&7Wooden Sword");
        add(TranslationKeys.SWORDS_GOLD_SWORD, "&7Golden Sword");
        add(TranslationKeys.SWORDS_IRON_SWORD, "&7Iron Sword");

        add(TranslationKeys.TRANK_HEAL, "&7Healing");
        add(TranslationKeys.TRANK_REGENERATION, "&7Regeneration");
        add(TranslationKeys.TRANK_INCREASE_DAMAGE, "&7Strength");
        add(TranslationKeys.TRANK_JUMP, "&7Leaping");
        add(TranslationKeys.TRANK_INVISIBILITY, "&7Invisibility");
        add(TranslationKeys.TRANK_SPEED, "&7Swiftness");

        add(TranslationKeys.SPECIAL_FISHING_ROD, "&7Fishing Rod");
        add(TranslationKeys.SPECIAL_FLINT_AND_STEEL, "&7Flint and Steel");
        add(TranslationKeys.SPECIAL_SNOW_BALL, "&7Snowball");
        add(TranslationKeys.SPECIAL_SULPHUR, "&7Home-Teleport");
        add(TranslationKeys.SPECIAL_BLAZE_POWDER, "&7Rescue Platform");
        add(TranslationKeys.SPECIAL_ARMOR_STAND, "&7Mobile Shop");
        add(TranslationKeys.SPECIAL_TNT, "&7TNT");
        add(TranslationKeys.SPECIAL_CARPET, "&7Swiftness");
        add(TranslationKeys.SPECIAL_WEB, "&7Cobweb");
        add(TranslationKeys.SPECIAL_LADDER, "&7Ladder");
        add(TranslationKeys.SPECIAL_WATER_BUCKET, "&7Water Bucket");
        add(TranslationKeys.SPECIAL_ENDER_PEARL, "&7Ender Pearl");
        add(TranslationKeys.SPECIAL_BLAZE_ROD, "&7Granade Launcher");
        add(TranslationKeys.SPECIAL_GOLD_PLATE, "&7Jumppad");
        add(TranslationKeys.ITEM_TELEPORT_START, "&7Teleport! Don't move for &e3 seconds&7!");
        add(TranslationKeys.ITEM_TELEPORT_CANCEL, "&cCanceled &7teleport!");

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
