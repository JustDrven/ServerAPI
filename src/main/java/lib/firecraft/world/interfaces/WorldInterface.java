package lib.firecraft.world.interfaces;

import lib.firecraft.player.FPlayer;
import org.bukkit.World;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface WorldInterface {
    String getName();
    World getWorld();
    List<FPlayer> getFPlayers();
    FPlayer getFPlayer(UUID uuid);
    FPlayer getFPlayer(String name);

    boolean load();
    void unload();
    void delete(File path);
    boolean isLoaded();
}
