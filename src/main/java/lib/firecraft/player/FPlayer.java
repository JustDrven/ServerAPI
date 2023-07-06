package lib.firecraft.player;

import lib.firecraft.player.interfaces.PlayerHandler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class FPlayer implements PlayerHandler {

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

    @Override
    public boolean join() {
        return true;
    }

    @Override
    public boolean quit() {
        return false;
    }

    @Override
    public void rejoin() {
        if (!(join())) {
            this.reset(GameMode.SURVIVAL);
        }
    }

    @Override
    public void teleport(Location l) {
        this.getPlayer().teleport(l);
    }

    @Override
    public void teleport(Player var) {
        this.getPlayer().teleport(var.getLocation());
    }

    @Override
    public void reset(GameMode gm) {
        this.getPlayer().getInventory().clear();
        this.getPlayer().getInventory().setArmorContents(null);

        this.getPlayer().setExp(0);
        this.getPlayer().setLevel(0);
        this.getPlayer().setHealth(20.0);
        this.getPlayer().setFoodLevel(20);
        this.getPlayer().setGameMode(gm);
        this.getPlayer().setFlying(false);
        this.getPlayer().setAllowFlight(false);
        this.getPlayer().setFireTicks(0);
        this.getPlayer().getActivePotionEffects().forEach(effect ->
                this.getPlayer().removePotionEffect(effect.getType()));
    }

    @Override
    public boolean equals(Object obj) {
        FPlayer fPlayer = (FPlayer)obj;
        if (fPlayer == null) return false;

        if (fPlayer.getName().equals(this.getName())) return true;

        return false;
    }
}
