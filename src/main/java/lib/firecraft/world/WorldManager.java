package lib.firecraft.world;

import lib.firecraft.plugin.ServerAPI;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private final ServerAPI serverAPI;

    private final List<World> worlds = new ArrayList<>();

    public WorldManager(ServerAPI serverAPI) {
        this.serverAPI = serverAPI;
        for (org.bukkit.World worlds : Bukkit.getWorlds()) {
            this.worlds.add(new World(worlds.getName(), serverAPI));
        }
    }

    public List<World> getMWorlds() {
        return worlds;
    }

    public World getMWorld(String name) {
        return this.worlds.stream()
                .filter(w -> w.getName().equals(name))
                .findFirst()
                .get();
    }

    public void resetAllWorld() {
        this.worlds.clear();
        for (org.bukkit.World worlds : Bukkit.getWorlds()) {
            this.worlds.add(new World(worlds.getName(), serverAPI));
        }
    }

    public ServerAPI getServerAPI() {
        return serverAPI;
    }
}
