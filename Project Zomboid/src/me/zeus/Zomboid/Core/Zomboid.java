
package me.zeus.Zomboid.Core;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zeus.Zomboid.API.ZPlayer;
import me.zeus.Zomboid.Listening.CommandEvent;
import me.zeus.Zomboid.Listening.JoinListener;
import me.zeus.Zomboid.Listening.LeaveListener;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Zomboid extends JavaPlugin {

    // =================================================================

    private File playersDir;
    private File[] playerFiles;

    private Map<String, ZPlayer> availablez;

    public Map<String, ZPlayer> getPlayers()
    {
        return availablez;
    }

    private List<String> notLoadedPlayers;

    public List<String> getUnloadedPlayers()
    {
        return notLoadedPlayers;
    }

    private static Zomboid instance;

    public static Zomboid getInstance()
    {
        return instance;
    }

    // =================================================================

    // plugin load
    @Override
    public void onEnable()
    {
        instance = this;
        
        availablez = new HashMap<String, ZPlayer>();
        notLoadedPlayers = new ArrayList<String>();
        
        checkDirs();
        loadData();

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable()
    {
        instance = null;
    }

    private void checkDirs()
    {
        playersDir = new File(getDataFolder() + File.separator + "players" + File.separator);
        if (!playersDir.exists())
        {
            playersDir.mkdirs();
        }
    }

    private void registerCommands()
    {
        /*
        getCommand("kills").setExecutor(new CMD_Kills);
        getCommand("deaths").setExecutor(new CMD_Deaths);
        getCommand("kd").setExecutor(new CMD_KD);
        */
    }

    private void registerEvents()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinListener(this), this);
        pm.registerEvents(new CommandEvent(this), this);
        pm.registerEvents(new LeaveListener(this), this);
    }

    private void loadData()
    {
        playerFiles = playersDir.listFiles();
        for (int i = 0; i < playerFiles.length; i++)
        {
            if (!playerFiles[i].exists())
            {
                return;
            }
            try
            {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerFiles[i]));
                ZPlayer loaded = (ZPlayer) ois.readObject();
                ois.close();
                System.out.println("Loaded stats for " + loaded.getName());
                availablez.put(loaded.getName(), loaded);
            } catch (IOException ioe)
            {
                ioe.printStackTrace();
                return;
            } catch (ClassNotFoundException cnfe)
            {
                cnfe.printStackTrace();
                return;
            }
        }
    }
    // =================================================================

}
