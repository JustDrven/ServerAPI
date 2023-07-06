package lib.firecraft.plugin;

import lib.firecraft.customevents.PlayerJoin;
import lib.firecraft.customevents.PlayerLeft;
import lib.firecraft.player.PlayerManager;
import lib.firecraft.plugin.interfaces.ServerSettings;
import lib.firecraft.plugin.interfaces.ServerStatus;
import lib.firecraft.plugin.utils.Colors;
import lib.firecraft.world.WorldManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public abstract class ServerAPI implements ServerSettings, Listener {

    private final String serverName;
    private ServerStatus serverStatus;
    private final PlayerManager playerManager;
    private final WorldManager worldManager;
    private String joinMessage = null;
    private String leftMessage = null;
    private final char[] ids = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'y', 'z'};
    private boolean pvp = true;
    private boolean breaks = true;
    private boolean places = true;
    private boolean weather = false;

    public ServerAPI(Plugin pl) {
        this.serverName = Bukkit.getServerName() + this.ids[new Random().nextInt(ids.length)];
        this.playerManager = new PlayerManager();
        this.worldManager = new WorldManager(this);

        Bukkit.getServer().getPluginManager().registerEvents(this, pl);

        if (Bukkit.getOnlinePlayers().size() <= 0) this.serverStatus = ServerStatus.EMPTY;
    }

    public abstract void setup(JavaPlugin plugin);
    public abstract void stop(JavaPlugin plugin);

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public char[] getIDS() {
        return ids;
    }

    @Override
    public String getName() {
        return this.serverName;
    }

    @Override
    public int getMaxPlayers() {
        return Bukkit.getMaxPlayers();
    }

    @Override
    public boolean isPvP() {
        return this.pvp;
    }

    @Override
    public boolean canBreak() {
        return this.breaks;
    }

    @Override
    public boolean canPlace() {
        return this.places;
    }

    @Override
    public boolean canChangeWeather() {
        return this.weather;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        if (serverStatus == this.serverStatus) return;
        this.serverStatus = serverStatus;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public ServerAPI pvp(boolean var) {
        this.pvp = var;
        return this;
    }

    public ServerAPI breaks(boolean var) {
        this.breaks = var;
        return this;
    }

    public ServerAPI places(boolean var) {
        this.places = var;
        return this;
    }

    public void setWhitelist(boolean value) {
        if (value) {
            this.setServerStatus(ServerStatus.WHITELIST);
        } else {
            this.setServerStatus(ServerStatus.NORMAL);
        }
    }

    public ServerAPI weather(boolean var) {
        this.weather = var;
        return this;
    }

    public ServerAPI join(String var) {
        this.joinMessage = ChatColor.translateAlternateColorCodes('&', var);
        return this;
    }

    public ServerAPI left(String var) {
        this.leftMessage = ChatColor.translateAlternateColorCodes('&', var);
        return this;
    }

    public void init() {
        Bukkit.getConsoleSender().sendMessage(Colors.format("&8[&cConnector&8] &7Server &a" + this.serverName + " &7was been loaded!"));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void blockBreak(BlockBreakEvent e) {
        if (!(this.breaks)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void blockPlace(BlockPlaceEvent e) {
        if (!(this.places)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void hitPlayer(EntityDamageEvent e) {
        if (!(this.pvp)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void weatherChange(WeatherChangeEvent e) {
        if (!(this.weather)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void JoinedMessage(PlayerJoinEvent e) {
        e.setJoinMessage(this.joinMessage);

        this.getPlayerManager().add(e.getPlayer().getUniqueId());

        if (!(this.getServerStatus().equals(ServerStatus.WHITELIST))) {
            if (Bukkit.getOnlinePlayers().size() >= this.getMaxPlayers()) {
                this.setServerStatus(ServerStatus.FULL);
            } else {
                this.setServerStatus(ServerStatus.NORMAL);
            }
        }
        Bukkit.getServer().getPluginManager().callEvent(new PlayerJoin(this.getPlayerManager().getFPlayer(e.getPlayer().getUniqueId()), this));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void LeftedMessage(PlayerQuitEvent e) {
        e.setQuitMessage(this.leftMessage);
        if (!(this.getServerStatus().equals(ServerStatus.WHITELIST))) this.setServerStatus(ServerStatus.NORMAL);

        Bukkit.getServer().getPluginManager().callEvent(new PlayerLeft(this.getPlayerManager().getFPlayer(e.getPlayer().getUniqueId()), this));

        this.getPlayerManager().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void login(PlayerLoginEvent e) {
        if (this.getServerStatus().equals(ServerStatus.WHITELIST)) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "This server is in whitelist!");
        }
        if (this.getServerStatus().equals(ServerStatus.FULL)) {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatColor.RED + "This server is full!");
        }
    }

    @EventHandler
    public void CustomJoin(PlayerJoin e) {
        this.onPlayerJoin(e);
    }

    @EventHandler
    public void CustomLeft(PlayerLeft e) {
        this.onPlayerLeft(e);
    }

    public void onPlayerJoin(PlayerJoin e){
    }
    public void onPlayerLeft(PlayerLeft e){
    }
}
