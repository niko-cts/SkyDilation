package net.fununity.games.skydilation;

import net.fununity.games.skydilation.generator.DilationWorldGenerator;
import net.fununity.main.api.minigames.MinigameNames;
import net.fununity.main.api.minigames.stats.minigames.Minigames;
import net.fununity.mgs.Minigame;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyDilation extends JavaPlugin {

    private static SkyDilation instance;
    private DilationWorldGenerator generator;

    @Override
    public void onEnable() {
        instance = this;
        this.generator = new DilationWorldGenerator();

        getServer().createWorld(new WorldCreator("void"));

        new Minigame(MinigameNames.SKYDILATION.getDisplayName(), Minigames.SKYDILATION, GameLogic.class);
    }


    public DilationWorldGenerator getSkyDilationGenerator() {
        return this.generator;
    }

    @Override
    public DilationWorldGenerator getDefaultWorldGenerator(String worldName, String id) {
        return getSkyDilationGenerator();
    }

    public static SkyDilation getInstance() {
        return instance;
    }
}
