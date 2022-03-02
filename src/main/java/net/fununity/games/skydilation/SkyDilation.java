package net.fununity.games.skydilation;

import net.fununity.games.skydilation.generator.BasicChunkGenerator;
import net.fununity.games.skydilation.listener.BlockBreakListener;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.minigames.MinigameNames;
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

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);

        getServer().getScheduler().runTask(this, () -> {
            new Minigame(MinigameNames.SKYDILATION.getDisplayName(), Minigames.SKYDILATION, GameLogic.class);
            FunUnityAPI.getInstance().getCommandHandler().addCommands(new GeneratorCommand());
            GameManager.getInstance().getArenas().add(new Arena("void", FunUnityAPI.getPrefix(), new HashMap<>()));
        });
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
