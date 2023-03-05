package net.fununity.games.skydilation.language;

public class TranslationKeys {

    private TranslationKeys() {
        throw new UnsupportedOperationException("This class only holds constants.");
    }

    private static final String SKYDILATION_COL = "skydilation.";

    // CURRENCY
    public static final String CURRENCY_COAL = SKYDILATION_COL + "currency.coal";
    public static final String CURRENCY_IRON = SKYDILATION_COL + "currency.iron";
    public static final String CURRENCY_GOLD = SKYDILATION_COL + "currency.gold";
    public static final String CURRENCY_QUARTZ = SKYDILATION_COL + "currency.diamond";
    public static final String CURRENCY_NETHER_STAR = SKYDILATION_COL + "currency.emerald";


    private static final String SHOP_COL = SKYDILATION_COL + "shop.";
    public static final String SHOP_TITLE = SHOP_COL + "title";
    public static final String SHOP_BLOCKS = SHOP_COL + "blocks";
    public static final String SHOP_ARMOR = SHOP_COL + "armor";
    public static final String SHOP_PICKAXE = SHOP_COL + "pickaxe";
    public static final String SHOP_SWORDS = SHOP_COL + "swords";
    public static final String SHOP_BOWS = SHOP_COL + "bows";
    public static final String SHOP_FOOD = SHOP_COL + "food";
    public static final String SHOP_SPECIAL = SHOP_COL + "special";
    public static final String SHOP_TRANKS = SHOP_COL + "tranks";
    public static final String SHOP_CHOOSER = SHOP_COL + "kits";
    public static final String SHOP_NOT_ENOUGH = SHOP_COL + "illegal.notenough";
    public static final String SHOP_NOT_ENOUGH_AMOUNT = SHOP_COL + "illegal.notenoughamount";

    // shop items
    private static final String SHOP_ITEM_COL = SHOP_COL + "item.";

    public static final String ARMOR_LEATHER_HELMET = SHOP_ITEM_COL + "armor.leatherhelmet";
    public static final String ARMOR_LEATHER_CHESTPLATE = SHOP_ITEM_COL + "armor.leatherchestplate";
    public static final String ARMOR_LEATHER_LEGGINGS = SHOP_ITEM_COL + "armor.leatherleggins";
    public static final String ARMOR_LEATHER_BOOTS = SHOP_ITEM_COL + "armor.leatherboots";
    public static final String ARMOR_GOLD_HELMET = SHOP_ITEM_COL + "armor.goldhelmet";
    public static final String ARMOR_GOLD_CHESTPLATE = SHOP_ITEM_COL + "armor.goldchestplate";
    public static final String ARMOR_GOLD_LEGGINGS = SHOP_ITEM_COL + "armor.goldleggins";
    public static final String ARMOR_GOLD_BOOTS = SHOP_ITEM_COL + "armor.goldboots";
    public static final String ARMOR_IRON_HELMET = SHOP_ITEM_COL + "armor.ironhelmet";
    public static final String ARMOR_IRON_CHESTPLATE = SHOP_ITEM_COL + "armor.ironchestplate";
    public static final String ARMOR_IRON_BOOTS = SHOP_ITEM_COL + "armor.ironboots";


    public static final String BOWS_BOW = SHOP_ITEM_COL + "bows.bow";
    public static final String BOWS_BOW2 = SHOP_ITEM_COL + "bows.bow2";
    public static final String BOWS_ARROW = SHOP_ITEM_COL + "bows.arrow";

    public static final String FOOD_CARROT_ITEM = SHOP_ITEM_COL + "food.carrot";
    public static final String FOOD_COOKED_BEEF = SHOP_ITEM_COL + "food.cookedbeef";
    public static final String FOOD_CAKE = SHOP_ITEM_COL + "food.cake";
    public static final String FOOD_GOLD_CARROT = SHOP_ITEM_COL + "food.goldencarrot";
    public static final String FOOD_GOLD_APPLE = SHOP_ITEM_COL + "food.goldenapple";

    public static final String PICKAXE_WOOD_PICKAXE = SHOP_ITEM_COL + "pickaxe.wood";
    public static final String PICKAXE_STONE_PICKAXE = SHOP_ITEM_COL + "pickaxe.stone";
    public static final String PICKAXE_IRON_PICKAXE = SHOP_ITEM_COL + "pickaxe.iron";
    public static final String PICKAXE_GOLD_PICKAXE = SHOP_ITEM_COL + "pickaxe.gold";

    public static final String SWORDS_STICK = SHOP_ITEM_COL + "swords.stick";
    public static final String SWORDS_WOOD_SWORD = SHOP_ITEM_COL + "swords.wood";
    public static final String SWORDS_GOLD_SWORD = SHOP_ITEM_COL + "swords.gold";
    public static final String SWORDS_IRON_SWORD = SHOP_ITEM_COL + "swords.iron";

    public static final String TRANK_HEAL = SHOP_ITEM_COL + "trank.heal";
    public static final String TRANK_REGENERATION = SHOP_ITEM_COL + "trank.regeneration";
    public static final String TRANK_INCREASE_DAMAGE = SHOP_ITEM_COL + "trank.increasedmg";
    public static final String TRANK_JUMP = SHOP_ITEM_COL + "trank.jump";
    public static final String TRANK_INVISIBILITY = SHOP_ITEM_COL + "trank.invisibility";
    public static final String TRANK_SPEED = SHOP_ITEM_COL + "trank.speed";

    public static final String SPECIAL_FISHING_ROD = SHOP_ITEM_COL + "special.fishingrod";
    public static final String SPECIAL_FLINT_AND_STEEL = SHOP_ITEM_COL + "special.flintandsteel";
    public static final String SPECIAL_SNOW_BALL = SHOP_ITEM_COL + "special.snowball";
    public static final String SPECIAL_SULPHUR = SHOP_ITEM_COL + "special.sulphur";
    public static final String SPECIAL_BLAZE_POWDER = SHOP_ITEM_COL + "special.blazepowder";
    public static final String SPECIAL_ARMOR_STAND = SHOP_ITEM_COL + "special.armorstand";
    public static final String SPECIAL_TNT = SHOP_ITEM_COL + "special.tnt";
    public static final String SPECIAL_CARPET = SHOP_ITEM_COL + "special.carpet";
    public static final String SPECIAL_WEB = SHOP_ITEM_COL + "special.web";
    public static final String SPECIAL_LADDER = SHOP_ITEM_COL + "special.ladder";
    public static final String SPECIAL_WATER_BUCKET = SHOP_ITEM_COL + "special.waterbucket";
    public static final String SPECIAL_ENDER_PEARL = SHOP_ITEM_COL + "special.enderpearl";
    public static final String SPECIAL_BLAZE_ROD = SHOP_ITEM_COL + "special.blazerod";
    public static final String SPECIAL_GOLD_PLATE = SHOP_ITEM_COL + "special.goldplate";
    public static final String SPECIAL_TELEPORT = SHOP_ITEM_COL + "special.message.home";

    // biome chooser
    public static final String BIOME_CHOOSER_LORE = SHOP_COL + ".biomechooser.lore";
    public static final String BIOME_CHOOSER_PLAINS = SHOP_COL + ".biomechooser.plains";
    public static final String BIOME_CHOOSER_SAVANNA = SHOP_COL + ".biomechooser.savanna";
    public static final String BIOME_CHOOSER_JUNGLE = SHOP_COL + ".biomechooser.jungle";
    public static final String BIOME_CHOOSER_MUSHROOM = SHOP_COL + ".biomechooser.mushroom";
    public static final String BIOME_CHOOSER_NETHER_1 = SHOP_COL + ".biomechooser.nether1";
    public static final String BIOME_CHOOSER_NETHER_2 = SHOP_COL + ".biomechooser.nether2";
    public static final String BIOME_CHOOSER_END = SHOP_COL + ".biomechooser.end";



    // commands
    public static final String COMMANDS_ITEMDROPPING_INFO = SKYDILATION_COL + "commands.itemdropping.info";
    public static final String COMMANDS_ITEMDROPPING_USAGE = SKYDILATION_COL + "commands.itemdropping.usage";
    public static final String COMMANDS_ITEMDROPPING_DESCRIPTION = SKYDILATION_COL + "commands.itemdropping.description";
    public static final String COMMANDS_ITEMDROPPING_TITLE = SKYDILATION_COL + "commands.itemdropping.title";
}
