package net.fununity.games.skydilation;

import net.fununity.games.skydilation.dropping.ItemDroppingManager;
import net.fununity.games.skydilation.chunkgeneration.BasicChunkGenerator;
import net.fununity.games.skydilation.language.EnglishMessages;
import net.fununity.games.skydilation.language.GermanMessages;
import net.fununity.games.skydilation.shop.ShopGUI;
import net.fununity.games.skydilation.util.RandomItemsUtil;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.minigames.Minigames;
import net.fununity.mgs.Minigame;
import net.fununity.mgs.gamespecifc.Arena;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class SkyDilation extends JavaPlugin implements Listener {

    private static SkyDilation instance;
    private BasicChunkGenerator generator;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.generator = new BasicChunkGenerator();
        getServer().getScheduler().runTaskAsynchronously(this, RandomItemsUtil::getInstance);
        getServer().getScheduler().runTaskAsynchronously(this, ItemDroppingManager::getInstance);
        getServer().getScheduler().runTaskAsynchronously(this, BasicChunkGenerator::getPopulator);

        // because plugin will load on LOAD state, implementations need to be loaded later on
        getServer().getScheduler().runTaskLater(this, () -> {
            new GermanMessages();
            new EnglishMessages();

            ShopGUI.setupShops();

            new Minigame(Minigames.SKYDILATION, GameLogic.class).setLobbyTime(2).setGameTime(3600);

            GameManager.getInstance().getArenas().add(new Arena("void", FunUnityAPI.getPrefix(), new HashMap<>()));
            getServer().getWorld("void").setGameRuleValue("randomTickSpeed", "0");
        }, 1L);
    }

    @Override
    public BasicChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return generator;
    }

    public BasicChunkGenerator getGenerator() {
        return generator;
    }

    public static SkyDilation getInstance() {
        return instance;
    }
}
