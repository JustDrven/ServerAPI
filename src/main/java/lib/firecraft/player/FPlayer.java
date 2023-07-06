package lib.firecraft.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class FPlayer {

    private final UUID uuid;
    private int coins = 0;

    public FPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    public String getName() {
        return Bukkit.getPlayer(this.uuid).getName();
    }

    public boolean isOnline() {
        return Bukkit.getOfflinePlayer(this.uuid).isOnline();
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int a) {
        if (a < 0) return;

        this.coins = this.coins + a;
    }

    public void removeCoins(int a) {
        if (this.coins <= 0) return;
        if (a < 0) return;

        this.coins = this.coins - a;
    }
}
