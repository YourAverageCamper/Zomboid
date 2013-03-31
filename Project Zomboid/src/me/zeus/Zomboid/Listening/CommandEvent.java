
package me.zeus.Zomboid.Listening;


import java.util.HashSet;
import java.util.Set;

import me.zeus.Zomboid.Core.Zomboid;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;



public class CommandEvent implements Listener {

    // =================================================================

    @SuppressWarnings("unused")
    private Zomboid plugin;

    public CommandEvent(Zomboid instance)
    {
        this.plugin = instance;
    }

    // =================================================================

    public Set<String> notLoaded = new HashSet<String>();

    // =================================================================

    @EventHandler
    public void onCommandPerform(PlayerCommandPreprocessEvent e)
    {
        Player player = e.getPlayer();

        if (e.getMessage().startsWith("/"))
        {
            if (notLoaded.contains(player.getName()))
            {
                e.setCancelled(true);
                player.sendMessage(ChatColor.DARK_RED + "You can't do this until your information is loaded!");
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
            }
        }

    }

    // =================================================================

}
