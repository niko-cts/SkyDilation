package net.fununity.games.skydilation;

import net.fununity.games.skydilation.generator.Biomes;
import net.fununity.games.skydilation.generator.ChunkLogic;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.server.ServerSetting;
import net.fununity.main.api.util.LocationUtil;
import net.fununity.mgs.gamespecifc.Arena;
import net.fununity.mgs.gamespecifc.Team;
import net.fununity.mgs.gamestates.Game;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Collections;
import java.util.List;

public class GameLogic extends Game {

    private static final int DISTANCE_BETWEEN = 300;

    @Override
    public void preMinigameStart() {
        super.preMinigameStart();
        ChunkLogic.loadLogic(this);

        Arena arena = getArena();

        Location location = new Location(arena.getWorld(), 0, 50, 0);
        // set min chunk
        ChunkLogic.getInstance().getMinAndMax()[0] = location.getChunk();

        int[] max = new int[]{location.getChunk().getX(), location.getChunk().getZ()};
        for (int i = 0; i < getAliveTeams().size(); i++) {
            Team team = getAliveTeams().get(i);
            Chunk chunk = location.getChunk();
            for (int x = chunk.getX() - 1; x < chunk.getX() + 1; x++) {
                for (int z = chunk.getZ() - 1; z < chunk.getZ() + 1; z++) {
                    SkyDilation.getInstance().getGenerator().generateChunk(arena.getWorld().getChunkAt(x, z), Biomes.PLAINS);

                    max[1] = Math.max(z, max[1]);
                }
                max[0] = Math.max(x, max[0]);
            }
            location.setY(LocationUtil.getBlockHeight(location) + 1);
            arena.setSpecifiedLocations(team.getName(), Collections.singletonList(location.clone()));
            Location chest = location.clone().add(1, 0, 0);
            chest.setY(chest.getWorld().getHighestBlockYAt(chest));
            chest.getBlock().setType(Material.ENDER_CHEST);
            addSpawnLocation(location, i, getAliveTeams().size());
        }

        // set max chunk
        ChunkLogic.getInstance().getMinAndMax()[1] = arena.getWorld().getChunkAt(max[0], max[1]);
    }

    @Override
    public void startMinigame() {
        FunUnityAPI.getInstance().getServerSettings().enable(ServerSetting.FOOD_LEVEL_CHANGE, ServerSetting.BLOCK_BREAK, ServerSetting.BLOCK_PLACE, ServerSetting.ENTITY_DAMAGE, ServerSetting.HANGING_BREAK, ServerSetting.ITEM_CONSUME, ServerSetting.PVP);
    }

    private void addSpawnLocation(Location location, int i, int teamSize) {
        double angle = ((teamSize - 2) * 180.0) / teamSize - 180.0 / teamSize * (i - 1.0);
        location.add(Math.cos(angle) * DISTANCE_BETWEEN, 0, Math.sin(angle) * DISTANCE_BETWEEN);
    }


    @Override
    public void minigameCountdown(int seconds) {
        if (seconds % 60 == 0)
            ChunkLogic.getInstance().generateRandomChunk(getArena().getWorld());
        if (seconds % 30 == 0)
            ChunkLogic.getInstance().generateNewTeamChunks();
    }

    @Override
    public List<Player> endMinigame() {
        return null; // mgs handles everything
    }

    @Override
    public void playerLeaves(Player player) {
        // mgs handles everything
    }

    @Override
    public boolean playerDiesInMinigame(Player player, PlayerDeathEvent playerDeathEvent) {
        return false; // MGS handles everything.
    }

    @Override
    public void useExtraLobbyItem(Player player) {
        // not needed
    }

    @Override
    public void gameWorldLoaded(Arena arena) {
        // not needed
    }
}
