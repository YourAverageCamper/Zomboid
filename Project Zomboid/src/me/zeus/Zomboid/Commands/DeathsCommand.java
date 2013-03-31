
package me.zeus.Zomboid.Commands;


import me.zeus.Zomboid.Core.ZPlayer;
import me.zeus.Zomboid.Core.Zomboid;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class DeathsCommand implements CommandExecutor {

    // =================================================================

    private Zomboid plugin;

    public DeathsCommand(Zomboid instance)
    {
        plugin = instance;
    }

    // =================================================================

    private ZPlayer zplayer;

    // =================================================================

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            if (plugin.availablez.containsKey(sender.getName()))
            {
                zplayer = plugin.availablez.get(sender.getName());
                sender.sendMessage(ChatColor.RED + "Deaths: " + zplayer.getDeaths());
            }
        }
        return false;
    }

    // =================================================================

}
