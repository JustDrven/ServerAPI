package lib.firecraft.world;

import lib.firecraft.player.FPlayer;
import lib.firecraft.plugin.ServerAPI;
import lib.firecraft.plugin.utils.Colors;
import lib.firecraft.world.interfaces.WorldInterface;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class World implements WorldInterface {

    private final ServerAPI serverAPI;
    private final String name;

    public World(String name, ServerAPI serverAPI) {
        this.name = name;
        this.serverAPI = serverAPI;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public org.bukkit.World getWorld() {
        return Bukkit.getWorld(this.name);
    }

    @Override
    public List<FPlayer> getFPlayers() {
        return this.serverAPI.getPlayerManager()
                .getFPlayers().stream()
                .filter(player -> player.getPlayer().getWorld().getName().equals(this.name))
                .collect(Collectors.toList());
    }

    @Override
    public FPlayer getFPlayer(UUID uuid) {
        return this.getFPlayers().stream()
                .filter(ps -> ps.getUuid().equals(uuid))
                .findFirst()
                .get();
    }

    @Override
    public FPlayer getFPlayer(String name) {
        return this.getFPlayers().stream()
                .filter(ps -> ps.getName().equals(name))
                .findFirst()
                .get();
    }

    @Override
    public boolean load() {
        if (isLoaded()) return true;

        org.bukkit.World w = Bukkit.createWorld(new WorldCreator(this.name));

        w.setAutoSave(true);
        w.setStorm(false);
        w.setMonsterSpawnLimit(0);

        Bukkit.getConsoleSender().sendMessage(Colors.format("&aWorld: " + this.name + " was been loaded!"));

        return isLoaded();
    }

    @Override
    public void unload() {
        Bukkit.unloadWorld(Bukkit.getWorld(this.name), false);

        this.delete(Bukkit.getWorld(this.name).getWorldFolder().getParentFile());

        Bukkit.getConsoleSender().sendMessage(Colors.format("&cWorld: " + this.name + " was been unloaded!"));
    }

    @Override
    public void delete(File path) {
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files == null) return;
            Arrays.stream(files).forEach(this::delete);
        }
        path.delete();
    }

    @Override
    public boolean isLoaded() {
        return this.getWorld() != null;
    }
}
