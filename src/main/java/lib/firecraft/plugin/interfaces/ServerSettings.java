package lib.firecraft.plugin.interfaces;

/*
    @author: JustDrven copyrights 2023
 */
public interface ServerSettings {
    String getName();
    int getMaxPlayers();

    boolean isPvP();
    boolean canBreak();
    boolean canPlace();
    boolean canChangeWeather();
}
