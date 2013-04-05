
package me.zeus.Zomboid.Core;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.zeus.Zomboid.API.ZPlayer;
import me.zeus.Zomboid.Commands.DeathsCommand;
import me.zeus.Zomboid.Commands.KillDeathRatio;
import me.zeus.Zomboid.Commands.KillsCommand;
import me.zeus.Zomboid.Listening.CommandEvent;
import me.zeus.Zomboid.Listening.DeathListener;
import me.zeus.Zomboid.Listening.JoinListener;
import me.zeus.Zomboid.Listening.LeaveListener;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Zomboid extends JavaPlugin {

    // =================================================================

    // listeners
    public DeathListener deathListener;
    public JoinListener joinListener;
    public LeaveListener leaveListener;
    public CommandEvent commandevent;

    // command executors
    public KillDeathRatio killdeathratio;
    public KillsCommand killscommand;
    public DeathsCommand deathscommand;

    // other
    public Map<String, ZPlayer> availablez;

    // =================================================================

    // basic variables
    private File statsDirectory;
    public ZPlayerFile zplayers;

    // constructor
    public Zomboid()
    {
    }

    // =================================================================

    // plugin load
    @Override
    public void onEnable()
    {
        // listeners
        deathListener = new DeathListener(this);
        joinListener = new JoinListener(this);
        leaveListener = new LeaveListener(this);
        commandevent = new CommandEvent(this);

        // executors
        killdeathratio = new KillDeathRatio(this);
        killscommand = new KillsCommand(this);
        deathscommand = new DeathsCommand(this);

        // check for /stats/ directory
        createStatsDir();

        // available players
        availablez = new HashMap<String, ZPlayer>();

        // create ZPlayers.yml 
        zplayers = new ZPlayerFile(this, "zplayers.yml", true);

        // register events
        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(joinListener, this);
        pm.registerEvents(deathListener, this);
        pm.registerEvents(leaveListener, this);
        pm.registerEvents(commandevent, this);

        // get commands
        getCommand("kd").setExecutor(killdeathratio);
        getCommand("kills").setExecutor(killscommand);
        getCommand("deaths").setExecutor(deathscommand);

        // kick all players
        for (Player p : getServer().getOnlinePlayers())
        {
            p.kickPlayer("Server reload, join back!");
        }
    }

    // create stats directory 
    private void createStatsDir()
    {
        statsDirectory = new File(getDataFolder() + "/stats/");
        if (!statsDirectory.exists())
        {
            statsDirectory.mkdirs();
            System.out.println("[Zomboid] **Stats directory created** at " + statsDirectory.getPath());
        }
    }

    // =================================================================

}
