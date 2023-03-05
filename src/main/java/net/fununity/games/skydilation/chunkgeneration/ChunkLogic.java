package net.fununity.games.skydilation.chunkgeneration;

import net.fununity.games.skydilation.GameLogic;
import net.fununity.games.skydilation.SkyDilation;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.mgs.gamespecifc.Arena;
import net.fununity.mgs.gamespecifc.Team;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.*;

/**
 * This class manages new spawning of chunks.
 * @author Niko
 * @since 0.0.1
 */
public class ChunkLogic {

    private static ChunkLogic instance;

    public static void loadLogic(GameLogic gameLogic) {
        instance = new ChunkLogic(gameLogic.getArena());
    }

    public static ChunkLogic getInstance() {
        return instance;
    }

    private final Map<String, Queue<Biomes>> nextBiomesMap;
    private final Arena arena;
    private final Chunk[] minMax;

    private ChunkLogic(Arena arena) {
        this.arena = arena;
        this.minMax = new Chunk[2];
        this.nextBiomesMap = new HashMap<>();
    }

    public void generateRandomChunk(World world) {
        int tries = 0;
        int x;
        int z;
        do {
            x = RandomUtil.getRandomInt(minMax[1].getX() - minMax[0].getX()) + minMax[0].getX();
            z = RandomUtil.getRandomInt(minMax[1].getZ() - minMax[0].getZ()) + minMax[0].getZ();
            tries++;
        } while (containsChunk(world.getChunkAt(x, z)) && tries < 30);

        SkyDilation.getInstance().getGenerator().generateChunk(world.getChunkAt(x, z));
    }

    public void generateNewTeamChunk(Team team) {
        Chunk start = arena.getTeamLocations(team).get(0).getChunk();
        Chunk chunk = (Chunk) RandomUtil.getRandomObjectFromList(getAllPossibleDilation(start, new ArrayList<>()));
        Queue<Biomes> nextBiomes = nextBiomesMap.getOrDefault(team.getName(), new LinkedList<>());
        if (nextBiomes.isEmpty()) {
            SkyDilation.getInstance().getGenerator().generateChunk(chunk);
        } else {
            SkyDilation.getInstance().getGenerator().generateChunk(chunk, nextBiomes.poll());
        }
    }

    /**
     * Returns all chunks that hasn't been generated yet from starting position.
     * @param start Chunk - Chunk to start from.
     * @param blacklist List<Chunk> - Chunks that should not been checked.
     * @return List<Chunk> - All chunks, which have not been generated yet.
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

    public Chunk[] getMinMax() {
        return minMax;
    }

    public void setBiomesQueue(Team team, Queue<Biomes> queue) {
        nextBiomesMap.put(team.getName(), queue);
    }

    public Queue<Biomes> getBiomeQueue(Team team) {
        return nextBiomesMap.getOrDefault(team.getName(), new LinkedList<>());
    }
}
