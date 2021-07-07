package net.fununity.games.skydilation;

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

    private static final int DISTANCE_BETWEEN = 100;

    @Override
    public void preMinigameStart() {
        super.preMinigameStart();

        Arena arena = getArena();

        int teamSize = getAliveTeams().size();
        Location location = new Location(arena.getWorld(), 0, 50, 0);
        SkyDilation.getInstance().getSkyDilationGenerator().getMinAndMax()[0] = location.getChunk();
        int[] max = new int[]{location.getChunk().getX(), location.getChunk().getZ()};
        for (int i = 0; i < teamSize; i++) {
            Team team = getAliveTeams().get(i);
            Chunk chunk = location.getChunk();
            for (int x = chunk.getX() - 1; x < chunk.getX() + 1; x++) {
                for (int z = chunk.getZ() - 1; z < chunk.getZ() + 1; z++) {
                    SkyDilation.getInstance().getSkyDilationGenerator().generateChunk(arena.getWorld(), x, z);
                    System.out.println(team.getName() + ": " + x + "  " + z);

                    max[1] = Math.max(z, max[1]);
                }
                max[0] = Math.max(x, max[0]);
            }
            location.setY(LocationUtil.getBlockHeight(location) + 1);
            arena.setSpecifiedLocations(team.getName(), Collections.singletonList(location));
            Location chest = location.clone().add(1, 0, 0);
            chest.setY(chest.getWorld().getHighestBlockYAt(chest));
            chest.getBlock().setType(Material.ENDER_CHEST);
            addSpawnLocation(location, i, teamSize);
        }
        SkyDilation.getInstance().getSkyDilationGenerator().getMinAndMax()[1] = arena.getWorld().getChunkAt(max[0], max[1]);
    }

    private void addSpawnLocation(Location location, int i, int teamSize) {
        double angle = ((teamSize - 2) * 180.0) / teamSize - 180.0 / teamSize * (i - 1.0);
        location.add(Math.cos(angle) * DISTANCE_BETWEEN, 0, Math.sin(angle) * DISTANCE_BETWEEN);
    }

    @Override
    public void startMinigame() {
        // todo
    }

    @Override
    public void minigameCountdown(int seconds) {
        if (seconds % 30 == 0)
            generateNewChunks();

    }

    private void generateNewChunks() {
        System.out.println("Try to generate");
        SkyDilation.getInstance().getDefaultWorldGenerator("", "").addChunkWhitelist(1, 0);
        getArena().getWorld().regenerateChunk(getRemainingTicks() / 30, 0);
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
