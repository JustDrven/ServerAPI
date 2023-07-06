package lib.firecraft.plugin.utils;

import org.bukkit.ChatColor;

public class Colors {
    private Colors() {
        throw new IllegalStateException("Colors is't defined!");
    }

    public static String format(String var1) {
        return ChatColor.translateAlternateColorCodes('&', var1);
    }
}
