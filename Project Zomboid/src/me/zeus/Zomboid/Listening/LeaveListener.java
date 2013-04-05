
package me.zeus.Zomboid.Listening;


import me.zeus.Zomboid.API.ZPlayer;
import me.zeus.Zomboid.Core.Zomboid;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;



public class LeaveListener implements Listener {

    // =================================================================

    private Zomboid plugin;
    private ZPlayer zquitter;

    public LeaveListener(Zomboid instance)
    {
        plugin = instance;
    }

    // =================================================================

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        if (plugin.availablez.containsKey(p.getName()))
        {
            zquitter = plugin.availablez.get(p.getName());
        }
        zquitter.saveStats();
        plugin.availablez.remove(p.getName());

        System.out.println("[Zomboid] Stats saved for " + p.getName());
    }

    // =================================================================

}
