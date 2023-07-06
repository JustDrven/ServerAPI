package lib.firecraft.plugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class Message {

    private String message;

    public Message(String message, String prefix) {
        this.message = DARK_GRAY + "[" + RED + prefix + DARK_GRAY + "] " + ChatColor.RESET + Colors.format(message);
    }

    public Message(String message) {
        this.message = Colors.format(message);
    }

    public void sendAll() {
        Bukkit.getServer().getOnlinePlayers().forEach(p ->
                p.sendMessage(this.message));
    }

    public void sendAll(String perm) {
        Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.hasPermission(perm)).forEach(p ->
                p.sendMessage(this.message));
    }

    public void send(Player var) {
        var.sendMessage(this.message);
    }

    public void send(Player var, String perm) {
        if (var.hasPermission(perm)) var.sendMessage(this.message);
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
