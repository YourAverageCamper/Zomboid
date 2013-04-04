
package me.zeus.Zomboid.Listening;


import me.zeus.Zomboid.Core.ZPlayer;
import me.zeus.Zomboid.Core.Zomboid;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;



public class DeathListener implements Listener {

    // =================================================================

    private Entity eKiller;
    private Entity eKilled;
    private ZPlayer zVictim;
    private ZPlayer zKiller;

    // =================================================================

    private Zomboid plugin;

    // =================================================================

    public DeathListener(Zomboid instance)
    {
        plugin = instance;
    }

    // =================================================================

    @EventHandler
    public void onEvE(final EntityDeathEvent e)
    {
        eKiller = e.getEntity().getKiller();
        eKilled = e.getEntity();

        if (eKiller instanceof Player)
        {
            Player killer = (Player) eKiller;

            if (plugin.availablez.containsKey(killer.getName()))
            {
                zKiller = plugin.availablez.get(killer.getName());

                if (eKilled instanceof Player)
                {
                    Player killed = (Player) eKilled;
                    zVictim = plugin.availablez.get(killed.getName());
                    zKiller.setPKills(zKiller.getPKills() + 1); // set killer kills + 1
                    zVictim.setDeaths(zVictim.getDeaths() + 1); // set victim deaths + 1
                    killer.getServer().broadcastMessage(
                            ChatColor.DARK_RED + "Warning: " + ChatColor.GOLD + killed.getName() + ChatColor.RED + " was slain by "
                                    + ChatColor.GOLD + killer.getName());
                }

                else if (eKilled instanceof Zombie)
                {
                    zKiller.setZKills(zKiller.getZKills() + 1); // set killer kills + 1
                }
            }

        }
    }

    // =================================================================

}
