package lib.firecraft.customevents;

import lib.firecraft.player.FPlayer;
import lib.firecraft.plugin.ServerAPI;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeft extends Event {

    public HandlerList HANDLERS = new HandlerList();

    private final FPlayer FPlayer;
    private final ServerAPI serverAPI;

    public PlayerLeft(FPlayer fPlayer, ServerAPI serverAPI) {
        this.FPlayer = fPlayer;
        this.serverAPI = serverAPI;
    }

    public lib.firecraft.player.FPlayer getFPlayer() {
        return FPlayer;
    }

    public ServerAPI getServerAPI() {
        return serverAPI;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
