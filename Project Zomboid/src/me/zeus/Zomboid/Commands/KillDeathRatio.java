
package me.zeus.Zomboid.Commands;


import me.zeus.Zomboid.API.ZPlayer;
import me.zeus.Zomboid.Core.Zomboid;
import me.zeus.Zomboid.Util.Calculator;
import me.zeus.Zomboid.Util.Cooldown;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class KillDeathRatio implements CommandExecutor {

    // =================================================================

    private ZPlayer zplayer;
    private ZPlayer zcommander;
    private Calculator calc;
    private Cooldown cooldown;

    private double KD;

    // =================================================================

    private Zomboid plugin;

    public KillDeathRatio(Zomboid instance)
    {
        plugin = instance;
        calc = new Calculator();
        cooldown = new Cooldown(plugin, 15);
    }

    // =================================================================

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            zplayer = new ZPlayer(plugin);

            if (zplayer.isAvailable(sender.getName()))
            {

                zcommander = plugin.availablez.get(sender.getName());

                if (cooldown.isCooling(sender.getName()))
                {
                    if (cooldown.isOver())
                    {
                        cooldown.removePlayer(sender.getName());
                    } else
                    {
                        sender.sendMessage(ChatColor.RED + "Do not spam this command! Time remaining: " + cooldown.getTimeLeft());
                    }
                } else
                {
                    calculateKD(zcommander);
                    sender.sendMessage(ChatColor.RED + "Your K/D is: " + ChatColor.YELLOW + KD);
                    sender.sendMessage(ChatColor.RED + "If this is inaccurate, please wait until the server updates stats!");
                    cooldown.reset();
                    cooldown.addPlayer((Player) sender);
                }
            }
        }
        return false;
    }

    // =================================================================

    public void calculateKD(ZPlayer zp)
    {
        try
        {
            KD = calc.calculateAverage(zp.getPKills() + zp.getZKills(), zp.getDeaths());
        } catch (ArithmeticException e)
        {
            KD = 1.00;
        }
    }

    // =================================================================

}
