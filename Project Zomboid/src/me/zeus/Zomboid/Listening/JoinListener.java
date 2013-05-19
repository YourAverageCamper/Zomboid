
package me.zeus.Zomboid.Listening;


import me.zeus.Zomboid.API.ZPlayer;
import me.zeus.Zomboid.Core.Zomboid;
import me.zeus.Zomboid.Util.ZMessenger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;



public class JoinListener implements Listener {

    // =================================================================

    @SuppressWarnings("unused")
    private Zomboid plugin;

    public JoinListener(Zomboid plugin)
    {
        this.plugin = plugin;
    }

    // =================================================================

    @EventHandler
    public void onJoin(final PlayerJoinEvent e)
    {
        Player joiner = e.getPlayer();
        if (ZPlayer.getZPlayer(joiner.getName()) == null)
        {
            ZPlayer.createZPlayer(joiner.getName(), 0, 0, 0, 0.00);
            ZMessenger.statsCreated(joiner);
            return;
        }
    }

    // =================================================================

}
