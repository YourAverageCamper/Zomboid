
package me.zeus.Zomboid.Util;


import java.util.HashMap;
import java.util.Map;

import me.zeus.Zomboid.Core.Zomboid;

import org.bukkit.entity.Player;



public class Cooldown {

    // =================================================================

    private Map<String, Long> cooling = new HashMap<String, Long>();
    private long startTime;
    private long endTime;
    private long duration;

    // =================================================================

    @SuppressWarnings("unused")
    private Zomboid plugin;

    public Cooldown(Zomboid instance, long duration)
    {
        plugin = instance;
        this.duration = duration * 1000;
        this.startTime = System.currentTimeMillis();
        this.endTime = startTime + duration;
    }

    // =================================================================

    // add player
    public void addPlayer(Player p)
    {
        cooling.put(p.getName(), System.currentTimeMillis());
    }

    public void removePlayer(String p)
    {
        cooling.remove(p);
    }

    // =================================================================

    public long getDuration()
    {
        return duration;
    }

    public boolean isCooling(String nameToCheck)
    {
        return cooling.containsKey(nameToCheck);
    }

    public boolean isOver()
    {
        return endTime < System.currentTimeMillis();
    }

    public int getTimeLeft()
    {
        return (int) (endTime - System.currentTimeMillis()) / 1000;
    }

    // =================================================================

    public void reset()
    {
        startTime = System.currentTimeMillis();
        endTime = startTime + duration;
    }

    // =================================================================

}
