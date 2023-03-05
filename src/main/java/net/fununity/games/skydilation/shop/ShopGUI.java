package net.fununity.games.skydilation.shop;

import net.fununity.games.skydilation.GameLogic;
import net.fununity.games.skydilation.SkyDilation;
import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.chunkgeneration.ChunkLogic;
import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.shop.shops.*;
import net.fununity.main.api.inventory.ClickAction;
import net.fununity.main.api.inventory.CustomInventory;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.main.api.util.ColorUtil;
import net.fununity.mgs.gamespecifc.Team;
import net.fununity.misc.translationhandler.TranslationHandler;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

/**
 * Creates and opens the villager shops
 *
 * @author Niko
 * @since 1.0
 */
public class ShopGUI {

    private ShopGUI() {
        throw new IllegalStateException("ShopGUI is a utility class.");
    }

    private static final Map<String, CustomInventory> SHOPS = new HashMap<>(); // 'ShopTopic-LanguageCode'

    /**
     * Creates all villager shops for all languages
     *
     * @since 1.0
     */
    public static void setupShops() {
        for (Language language : TranslationHandler.getInstance().getLanguageHandler().getLanguages()) {
            for (ShopTopic shop : ShopTopic.values()) {
                CustomInventory menu = new CustomInventory(language.getTranslation(TranslationKeys.SHOP_TITLE), 9 * 3);
                menu.fill(UsefulItems.BACKGROUND_BLACK);
                for (int i = 0; i < ShopTopic.values().length; i++) {
                    ShopTopic top = ShopTopic.values()[i];
                    menu.setItem(i, top.getDisplayItem(language), new ClickAction() {
                        @Override
                        public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int z) {
                            openShopPage(apiPlayer, top);
                        }
                    });
                }

                if (shop != ShopTopic.BIOME_PICKER) {
                    for (int i = 0, position = 9; i < shop.getShops().length; i++, position++) {
                        IShopContent content = shop.getShops()[i];
                        if (content.getCurrencyType() == null) {
                            continue;
                        }

                        menu.setItem(position, setDisplayItemCost(language, content).craft(), new ClickAction() {
                            @Override
                            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                                buy(apiPlayer, content, false);
                            }

                            @Override
                            public void onShiftClick(APIPlayer apiPlayer, ItemStack itemStack, int slot) {
                                buy(apiPlayer, content, true);
                            }
                        });
                    }
                }

                SHOPS.put(shop.name() + "-" + language.getCode(), menu);
            }
        }
    }

    /**
     * Buys an item for the player,
     *
     * @param apiPlayer  APIPlayer - Player who buys
     * @param content    {@link IShopContent} - The content the player buys
     * @param shiftClick boolean - if player shift clicks
     * @since 0.0.1
     */
    private static void buy(APIPlayer apiPlayer, IShopContent content, boolean shiftClick) {
        ItemStack[] items = new ItemStack[content.getBuyItems().length];
        for (int i = 0; i < items.length; i++) {
            items[i] = content.getBuyItems()[i].clone();
        }

        // Color leather armor and carpet
        if (content instanceof ArmorContents || content instanceof SpecialContents) {
            for (ItemStack buyItem : items) {
                switch (buyItem.getType()) {
                    case LEATHER_BOOTS:
                    case LEATHER_CHESTPLATE:
                    case LEATHER_LEGGINGS:
                    case LEATHER_HELMET:
                        Team team = GameLogic.getInstance().getPlayerTeam(apiPlayer.getPlayer());
                        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) buyItem.getItemMeta();
                        leatherArmorMeta.setColor(ColorUtil.convertChatToDyeColor(team.getColor()).getColor());
                        buyItem.setItemMeta(leatherArmorMeta);
                        break;
                    case CARPET:
                        team = GameLogic.getInstance().getPlayerTeam(apiPlayer.getPlayer());
                        buyItem.setDurability(ColorUtil.convertChatToDyeColor(team.getColor()).getWoolData());
                        break;
                    default:
                        break;
                }
            }
        }
        if (content instanceof IItemSpecialName) {
            IItemSpecialName specialName = (IItemSpecialName) content;
            if (!specialName.getTranslationKey().isEmpty()) {
                for (int i = 0; i < items.length; i++) {
                    String translation = apiPlayer.getLanguage().getTranslation(specialName.getTranslationKey());
                    items[i] = new ItemBuilder(items[i]).setName(translation).craft();
                }
            }
        }

        buysShopItem(apiPlayer, content.getCurrencyType(), content.getPrice(), shiftClick, items);
    }

    /**
     * Reduces the currency out of the users inventory and gives him the given items.
     * Used by {@link ShopGUI}
     *
     * @param player     APIPlayer - Player that buys
     * @param type       CurrencyType - Type of currency
     * @param price      int - Amount of currency should reduce
     * @param shiftClick boolean - player shift clicks, buys it 64 times
     * @param items      ItemStack[] - Items that the player gets
     * @return boolean - player has enough currency in his inventory, operation was successful
     * @since 1.0
     */
    private static boolean buysShopItem(APIPlayer player, CurrencyType type, int price, boolean shiftClick, ItemStack... items) {
        Player p = player.getPlayer();
        PlayerInventory inv = p.getInventory();

        int amountInInv = 0;
        for (ItemStack content : inv.getContents()) {
            if (content != null && content.getType() == type.getMaterial())
                amountInInv += content.getAmount();
        }
        int amountOfItems = shiftClick ? 64 : 1;

        while (amountInInv < price * amountOfItems)
            amountOfItems--;

        if (amountOfItems < 1) {
            player.sendMessage(MessagePrefix.ERROR, TranslationKeys.SHOP_NOT_ENOUGH_AMOUNT,
                    Arrays.asList("${type}", "${amount}"),
                    Arrays.asList(type.getColor() + type.getName(player.getLanguage()), (price * (shiftClick ? 64 : 1) - amountInInv) + ""));
            player.playSound(Sound.ENTITY_VILLAGER_NO);
            return false;
        }


        ItemStack item = new ItemStack(type.getMaterial());
        for (int i = 0; i < amountOfItems; i++) {
            if (inv.containsAtLeast(item, price)) {
                inv.removeItem(new ItemStack(item.getType(), price));

                for (ItemStack result : items) {
                    inv.addItem(result).forEach((integer, itemStack) -> p.getWorld().dropItemNaturally(p.getLocation(), itemStack));
                }

            } else {
                player.sendMessage(MessagePrefix.ERROR, TranslationKeys.SHOP_NOT_ENOUGH, "${type}", type.getColor() + type.getName(player.getLanguage()));
                player.playSound(Sound.ENTITY_VILLAGER_NO);
                return false;
            }
        }

        player.playSound(Sound.ENTITY_VILLAGER_YES);
        return true;
    }

    /**
     * Adds the price to the lore of an item
     *
     * @param language Language - Language to translate
     * @param content  IShopContent - Item to add the lore
     * @return ItemStack - Created item with modified lore
     * @since 1.0
     */
    private static ItemBuilder setDisplayItemCost(Language language, IShopContent content) {
        CurrencyType currencyType = content.getCurrencyType();
        ItemBuilder itemBuilder = new ItemBuilder(content.getDisplayItem(language)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore;
        if (itemBuilder.getItemMeta().hasLore()) {
            lore = itemBuilder.getItemMeta().getLore();
            itemBuilder.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else
            lore = new ArrayList<>();
        lore.add(" ");
        lore.add(new StringBuilder().append(currencyType.getColor()).append(content.getPrice()).append(" ").append(currencyType.getName(language)).toString());
        itemBuilder.setLore(lore);
        return itemBuilder;
    }

    /**
     * Opens a specified shop with its content
     *
     * @param player APIPlayer - Player to open the Shop
     * @param topic  ShopTopic - The topic (header) of the shop
     * @since 1.0
     */
    public static void openShopPage(APIPlayer player, ShopTopic topic) {
        CustomInventory customInventory = SHOPS.getOrDefault(topic.name() + "-" + player.getLanguage().getCode(), null);
        if (customInventory == null) return;
        if (topic == ShopTopic.BIOME_PICKER) {
            openBiomePicker(player, customInventory);
        } else {
            customInventory.open(player);
        }
    }

    private static void selectQueue(APIPlayer apiPlayer, Team team, IBiomeShopContent content) {
        Queue<Biomes> biomeQueue = ChunkLogic.getInstance().getBiomeQueue(team);
        Biomes biome = content.getRepresentingBiomes();
        if (biomeQueue.contains(biome)) {
            biomeQueue.remove(biome);
            ChunkLogic.getInstance().setBiomesQueue(team, biomeQueue);

            if (content.getPrice() > 64) {
                int amount = content.getPrice();
                while (amount > 0) {
                    int toAdd = Math.min(64, amount);
                    apiPlayer.getPlayer().getInventory().addItem(new ItemStack(content.getCurrencyType().getMaterial(), toAdd));
                    amount -= toAdd;
                }
            } else {
                apiPlayer.getPlayer().getInventory().addItem(new ItemStack(content.getCurrencyType().getMaterial(), content.getPrice()));
            }
        } else {
            PlayerInventory inventory = apiPlayer.getPlayer().getInventory();
            int price = content.getPrice();
            CurrencyType type = content.getCurrencyType();
            int amountInInv = 0;
            for (ItemStack inv : inventory.getContents()) {
                if (inv != null && inv.getType() == type.getMaterial())
                    amountInInv += inv.getAmount();
            }


            if (amountInInv < price) {
                apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.SHOP_NOT_ENOUGH_AMOUNT,
                        Arrays.asList("${type}", "${amount}"),
                        Arrays.asList(type.getColor() + type.getName(apiPlayer.getLanguage()), (price - amountInInv) + ""));
                apiPlayer.playSound(Sound.ENTITY_VILLAGER_NO);
                return;
            }

            ItemStack item = new ItemStack(content.getCurrencyType().getMaterial());
            if (inventory.containsAtLeast(item, content.getPrice())) {
                inventory.removeItem(new ItemStack(item.getType(), price));

                biomeQueue.add(biome);
                ChunkLogic.getInstance().setBiomesQueue(team, biomeQueue);

                apiPlayer.playSound(Sound.ENTITY_VILLAGER_YES);
            } else {
                apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.SHOP_NOT_ENOUGH, "${type}", type.getColor() + type.getName(apiPlayer.getLanguage()));
                apiPlayer.playSound(Sound.ENTITY_VILLAGER_NO);
            }
        }
    }

    private static void openBiomePicker(APIPlayer player, CustomInventory old) {
        CustomInventory menu = new CustomInventory(old.getInventory().getTitle(), old.getInventory().getSize());
        for (int i = 0; i < old.getInventory().getContents().length; i++) {
            menu.setItem(i, old.getInventory().getItem(i), old.getClickAction(i));
        }

        ShopTopic shop = ShopTopic.BIOME_PICKER;

        Team team = GameLogic.getInstance().getPlayerTeam(player.getPlayer());
        Queue<Biomes> biomeQueue = ChunkLogic.getInstance().getBiomeQueue(team);

        for (int i = 0, position = 9; i < shop.getShops().length; i++, position++) {
            IBiomeShopContent content = (IBiomeShopContent) shop.getShops()[i];
            if (content.getCurrencyType() == null) {
                continue;
            }

            menu.setItem(position, setDisplayItemCost(player.getLanguage(), content)
                    .addEnchantment(biomeQueue.contains(content.getRepresentingBiomes()) ? Enchantment.ARROW_FIRE : null, 1, true, false)
                    .craft(), new ClickAction(true) {
                @Override
                public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                    selectQueue(apiPlayer, team, content);
                    Bukkit.getScheduler().runTaskLater(SkyDilation.getInstance(), () -> openBiomePicker(apiPlayer, old), 1L);
                }
            });
        }

        menu.open(player);
    }

}