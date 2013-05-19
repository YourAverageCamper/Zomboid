
package me.zeus.Zomboid.Listening;


import me.zeus.Zomboid.API.ZPlayer;
import me.zeus.Zomboid.Core.Zomboid;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;



public class LeaveListener implements Listener {

    // =================================================================

    @SuppressWarnings("unused")
    private Zomboid plugin;

    public LeaveListener(Zomboid instance)
    {
        plugin = instance;
    }

    // =================================================================

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        ZPlayer quitter = Zomboid.getInstance().getPlayers().get(p.getName());
        if (quitter == null)
        {
            return;
        }
        quitter.save();
        System.out.println("[Zomboid] Stats saved for " + p.getName());
    }

    // =================================================================

}
