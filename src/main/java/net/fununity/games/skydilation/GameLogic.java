package net.fununity.games.skydilation;

import net.fununity.games.skydilation.chunkgeneration.Biomes;
import net.fununity.games.skydilation.chunkgeneration.ChunkLogic;
import net.fununity.games.skydilation.commands.ItemDropsCommand;
import net.fununity.games.skydilation.dropping.ItemDroppingManager;
import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.games.skydilation.listener.BlockListener;
import net.fununity.games.skydilation.listener.VillagerClickListener;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.server.BroadcastHandler;
import net.fununity.main.api.server.ServerSetting;
import net.fununity.main.api.util.LocationUtil;
import net.fununity.main.api.util.RegisterUtil;
import net.fununity.mgs.gamespecifc.Arena;
import net.fununity.mgs.gamespecifc.Team;
import net.fununity.mgs.gamestates.Game;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Collections;
import java.util.List;

public class GameLogic extends Game {

    private static GameLogic instance;
    private static final int MIN_MAX_CHUNKS = 4;
    private static final int DISTANCE_MULTIPLIER = 70;
    private int distanceBetween;

    public GameLogic() {
        instance = this;
    }

    @Override
    public void preMinigameStart() {
        super.preMinigameStart();
        ChunkLogic.loadLogic(this);

        Arena arena = getArena();
        List<Team> aliveTeams = getAliveTeams();
        this.distanceBetween = aliveTeams.size() * DISTANCE_MULTIPLIER;

        Location location = new Location(arena.getWorld(), 0, 50, 0);
        int[] min = new int[]{location.getChunk().getX(), location.getChunk().getZ()};
        int[] max = new int[]{location.getChunk().getX(), location.getChunk().getZ()};
        for (int i = 0; i < aliveTeams.size(); i++) {
            Team team = aliveTeams.get(i);
            Chunk chunk = location.getChunk();
            location = new Location(chunk.getWorld(), chunk.getX() << 4, 64, chunk.getZ() << 4).add(7, 0, 7);

            for (int x = chunk.getX() - 1; x < chunk.getX() + 1; x++) {
                for (int z = chunk.getZ() - 1; z < chunk.getZ() + 1; z++) {
                    SkyDilation.getInstance().getGenerator().generateChunk(arena.getWorld().getChunkAt(x, z), Biomes.PLAINS);
                }
            }

            min[0] = Math.min(chunk.getX() - 1, min[0]);
            min[1] = Math.min(chunk.getZ() - 1, min[1]);
            max[0] = Math.max(chunk.getX() + 1, max[0]);
            max[1] = Math.max(chunk.getZ() + 1, max[1]);
            location.setY(LocationUtil.getBlockHeight(location) + 1);
            arena.setSpecifiedLocations(team.getName(), Collections.singletonList(location.clone()));
            Location villagerLoc = location.clone().add(1, 0, 0);
            villagerLoc.setY(LocationUtil.getBlockHeight(villagerLoc) + 1);
            Villager villager = (Villager) villagerLoc.getWorld().spawnEntity(villagerLoc, EntityType.VILLAGER);
            villager.setAI(false);
            villager.setCanPickupItems(false);
            villager.setCustomNameVisible(true);
            villager.setCustomName(team.getColoredName());
            villager.setInvulnerable(true);
            villager.setGravity(false);

            Location saplingLoc = location.clone().subtract(1, 0, 0);
            saplingLoc.setY(LocationUtil.getBlockHeight(location) + 1);
            saplingLoc.getBlock().setType(Material.SAPLING);
            ItemDroppingManager.getInstance().startDropping(saplingLoc.getBlock().getLocation(), Material.SAPLING);


            addSpawnLocation(location, aliveTeams.size());
        }

        // set max chunk
        ChunkLogic.getInstance().getMinMax()[0] = arena.getWorld().getChunkAt(min[0] - MIN_MAX_CHUNKS, min[1] - MIN_MAX_CHUNKS);
        ChunkLogic.getInstance().getMinMax()[1] = arena.getWorld().getChunkAt(max[0] + MIN_MAX_CHUNKS, max[1] + MIN_MAX_CHUNKS);

        // set world border
        Chunk minChunk = ChunkLogic.getInstance().getMinMax()[0];
        Chunk maxChunk = ChunkLogic.getInstance().getMinMax()[1];
        Location minLocation = new Location(minChunk.getWorld(), minChunk.getX() << 4, 64, minChunk.getZ() << 4).subtract(7, 0, 7);
        Location maxLocation = new Location(maxChunk.getWorld(), maxChunk.getX() << 4, 64, maxChunk.getZ() << 4).add(7, 0, 7);
        arena.getWorld().getWorldBorder().setCenter(new Location(minLocation.getWorld(), (minLocation.getX() + maxLocation.getX()) / 2.0, 0, (minLocation.getZ() + maxLocation.getZ()) / 2.0));
        arena.getWorld().getWorldBorder().setSize(minLocation.distance(maxLocation));
    }

    @Override
    public void startMinigame() {
        FunUnityAPI.getInstance().getServerSettings().enable(ServerSetting.FOOD_LEVEL_CHANGE, ServerSetting.BLOCK_BREAK, ServerSetting.BLOCK_PLACE, ServerSetting.ENTITY_DAMAGE, ServerSetting.HANGING_BREAK, ServerSetting.ITEM_CONSUME, ServerSetting.PVP);
        RegisterUtil registerUtil = new RegisterUtil(SkyDilation.getInstance());
        registerUtil.addListeners(new BlockListener(), new VillagerClickListener());
        registerUtil.addCommand(new ItemDropsCommand());
        registerUtil.register();
        BroadcastHandler.broadcastMessage(MessagePrefix.INFO, TranslationKeys.COMMANDS_ITEMDROPPING_INFO);
    }

    private void addSpawnLocation(Location location, int playerSize) {
        double angle = 360.0 / playerSize;
        location.add(Math.cos(Math.toRadians(angle)) * distanceBetween, 0, Math.sin(Math.toRadians(angle)) * distanceBetween);
    }


    @Override
    public void minigameCountdown(int seconds) {
        if (seconds % 30 == 0) {
            getAliveTeams().forEach(team -> ChunkLogic.getInstance().generateNewTeamChunk(team));
            if (seconds % 60 == 0)
                ChunkLogic.getInstance().generateRandomChunk(getArena().getWorld());
        }
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

    public static GameLogic getInstance() {
        return instance;
    }

}
