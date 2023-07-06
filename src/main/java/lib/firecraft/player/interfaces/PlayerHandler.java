package lib.firecraft.player.interfaces;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface PlayerHandler {
    boolean join();
    boolean quit();

    void rejoin();
    void teleport(Location l);
    void teleport(Player var);
    void reset(GameMode gm);
}
