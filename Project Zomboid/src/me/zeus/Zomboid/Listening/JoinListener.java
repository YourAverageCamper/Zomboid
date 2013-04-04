
package me.zeus.Zomboid.Listening;


import java.util.Arrays;

import me.zeus.Zomboid.Core.ZPlayer;
import me.zeus.Zomboid.Core.Zomboid;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;



public class JoinListener implements Listener {

    // =================================================================

    private Zomboid plugin;

    private ZPlayer zjoiner;
    private ZPlayer zplayer;

    public JoinListener(Zomboid instance)
    {
        plugin = instance;
    }

    // =================================================================

    @EventHandler
    public void onJoin(final PlayerJoinEvent e)
    {
        final Player joiner = e.getPlayer();

        zplayer = new ZPlayer(plugin);

        plugin.commandevent.notLoaded.add(joiner.getName());
        if (zplayer.isZPlayer(joiner.getName()))
        {
            // delay message
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
            {

                @Override
                public void run()
                {
                    zjoiner = new ZPlayer(plugin, joiner.getName(), 0, 0, 0, Arrays.asList(""));
                    zjoiner.reloadStats();
                    zjoiner.loadStats();
                    plugin.availablez.put(joiner.getName(), zjoiner);
                    joiner.sendMessage(ChatColor.GREEN + "[Zomboid] Stats are loaded!");
                    joiner.playSound(joiner.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                    plugin.commandevent.notLoaded.remove(joiner.getName());
                }
            }, 60L);
        } else
        {
            // delay message
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
            {

                @Override
                public void run()
                {
                    zjoiner = new ZPlayer(plugin, joiner.getName(), 0, 0, 0, Arrays.asList(""));
                    zjoiner.createStats();
                    plugin.zplayers.addName(joiner.getName());
                    plugin.availablez.put(joiner.getName(), zjoiner);
                    joiner.sendMessage(ChatColor.GREEN + "\n[Zomboid] Stats are created!");
                    joiner.playSound(joiner.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                    plugin.commandevent.notLoaded.remove(joiner.getName());
                    e.setJoinMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Welcome to hell, " + ChatColor.DARK_RED + joiner.getName());
                }
            }, 60L);
        }

    }
    // =================================================================

}
