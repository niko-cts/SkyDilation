package net.fununity.games.skydilation.generator;

import net.fununity.games.skydilation.GameLogic;
import net.fununity.games.skydilation.SkyDilation;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.mgs.gamespecifc.Arena;
import net.fununity.mgs.gamespecifc.Team;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkLogic {

    private static ChunkLogic instance;

    public static void loadLogic(GameLogic gameLogic) {
        instance = new ChunkLogic(new ArrayList<>(gameLogic.getAliveTeams()), gameLogic.getArena());
    }

    public static ChunkLogic getInstance() {
        return instance;
    }

    private final List<Team> teams;
    private final Map<Team, Biomes> nextBiomesMap;
    private final Arena arena;
    private final Chunk[] minAndMax;

    private ChunkLogic(List<Team> teams, Arena arena) {
        this.teams = teams;
        this.arena = arena;
        this.minAndMax = new Chunk[2];
        this.nextBiomesMap = new HashMap<>();
    }

    public void generateRandomChunk(World world) {
        int x = RandomUtil.getRandomInt(minAndMax[1].getX() + 1 - minAndMax[0].getX()) + minAndMax[0].getX();
        int z = RandomUtil.getRandomInt(minAndMax[1].getZ() + 1 - minAndMax[0].getZ()) + minAndMax[0].getZ();
        int tries = 0;
        while (containsChunk(world.getChunkAt(x, z)) && tries < 20) {
            x = RandomUtil.getRandomInt(minAndMax[1].getX() + 1 - minAndMax[0].getX()) + minAndMax[0].getX();
            z = RandomUtil.getRandomInt(minAndMax[1].getZ() + 1 - minAndMax[0].getZ()) + minAndMax[0].getZ();
            tries++;
        }
        SkyDilation.getInstance().getGenerator().generateChunk(world.getChunkAt(x, z));
    }

    public void generateNewTeamChunks() {
        for (Team team : teams) {
            Chunk start = arena.getTeamLocations(team).get(0).getChunk();
            List<Chunk> possibleChunkDilation = getAllPossibleDilation(start, new ArrayList<>());
            Chunk chunk = possibleChunkDilation.get(RandomUtil.getRandomInt(possibleChunkDilation.size()));
            Biomes biome = nextBiomesMap.getOrDefault(team, null);
            if (biome != null)
                SkyDilation.getInstance().getGenerator().generateChunk(chunk, biome);
            else
                SkyDilation.getInstance().getGenerator().generateChunk(chunk);
        }
    }

    /**
     * Returns all chunks that hasn't been generated yet from starting position.
     * @param start Chunk - Chunk to start from.
     * @param blacklist List<Chunk> - Chunks that should not been checked.
     * @return List<Chunk> - All ungenerated chunks around the starting chunk.
     * @since 0.0.1
     */
    private List<Chunk> getAllPossibleDilation(Chunk start, List<Chunk> blacklist) {
        List<Chunk> possibles = new ArrayList<>();
        blacklist.add(start);

        for (int x : new int[]{start.getX() - 1, start.getX() + 1}) {
            Chunk checkChunk = start.getWorld().getChunkAt(x, start.getZ());
            if (blacklist.contains(checkChunk))
                continue;
            if (containsChunk(checkChunk))
                possibles.addAll(getAllPossibleDilation(checkChunk, blacklist));
            else
                possibles.add(checkChunk);
        }
        for (int z : new int[]{start.getZ() - 1, start.getZ() + 1}) {
            Chunk checkChunk = start.getWorld().getChunkAt(start.getX(), z);
            if (blacklist.contains(checkChunk))
                continue;
            if (containsChunk(checkChunk))
                possibles.addAll(getAllPossibleDilation(checkChunk, blacklist));
            else
                possibles.add(checkChunk);
        }

        return possibles;
    }

    private boolean containsChunk(Chunk chunk) {
        return SkyDilation.getInstance().getGenerator().containsChunk(chunk);
    }

    public Chunk[] getMinAndMax() {
        return minAndMax;
    }
}
