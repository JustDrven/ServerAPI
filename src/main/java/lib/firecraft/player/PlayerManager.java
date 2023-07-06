package lib.firecraft.player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerManager {

    private final Set<FPlayer> FPlayers = new HashSet<>();

    public void add(UUID uuid) {
        this.FPlayers.add(new FPlayer(uuid));
    }

    public void remove(UUID uuid) {
        this.FPlayers.removeIf(player -> player.getUuid().equals(uuid));
    }

    public FPlayer getFPlayer(UUID uuid) {
        return this.FPlayers.stream()
                .filter(p -> p.getUuid().equals(uuid))
                .findFirst()
                .get();
    }

    public FPlayer getFPlayer(String playerName) {
        return this.FPlayers.stream()
                .filter(p -> p.getName().equals(playerName))
                .findFirst()
                .get();
    }

    public Set<FPlayer> getFPlayers() {
        return FPlayers;
    }
}
