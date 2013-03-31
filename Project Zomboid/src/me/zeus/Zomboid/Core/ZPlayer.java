
package me.zeus.Zomboid.Core;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;



public class ZPlayer {

    // =================================================================

    private File statsFile;
    private FileConfiguration statsFileCFG;

    //
    private String username;

    private int pKills = 0;
    private int zKills = 0;
    private int deaths = 0;

    private List<String> victims = new ArrayList<String>();
    //

    // =================================================================

    private Zomboid plugin;

    public ZPlayer(Zomboid instance)
    {
        plugin = instance;
    }

    public ZPlayer(Zomboid instance, String username, int playerKills, int deaths, int zombieKills, List<String> victims)
    {
        plugin = instance;
        this.username = username;
        pKills = playerKills;
        this.deaths = deaths;
        zKills = zombieKills;
        this.victims = victims;
    }

    // =================================================================
    /*
     *                  THE MOST IMPORTANT STUFF IS HERE:
     * 
     *                HANDLING STATS , LOADING STATS, ETC...      
     */
    // =================================================================

    public void reloadStats()
    {
        if (statsFile == null)
        {
            statsFile = new File(plugin.getDataFolder() + "/stats/" + username + ".yml");
        }
        statsFileCFG = YamlConfiguration.loadConfiguration(statsFile);
    }

    public FileConfiguration getStats()
    {
        if (statsFile == null || statsFileCFG == null)
        {
            reloadStats();
        }
        return statsFileCFG;
    }

    public void createStats()
    {
        statsFile = new File(plugin.getDataFolder() + "/stats/" + username + ".yml");
        if (!statsFile.exists())
        {
            try
            {
                statsFile.createNewFile();
            } catch (IOException e)
            {
                System.out.println("There was an error creating a stats file for " + username);
            }
        } else
        {
            System.out.println("A stats file already exists for " + username);
            return;
        }
        getStats().set("Username", username);
        getStats().set("Zombie_Kills", 0);
        getStats().set("Player_Kills", 0);
        getStats().set("Deaths", 0);
        getStats().set("Tag_Color", "GREEN");
        getStats().set("Victims", victims);
        saveStats();
    }

    public void loadStats()
    {
        reloadStats();
        username = getStats().getString("Username");
        zKills = getStats().getInt("Zombie_Kills");
        pKills = getStats().getInt("Player_Kills");
        deaths = getStats().getInt("Deaths");
        victims = getStats().getStringList("Victims");
    }

    public void saveStats()
    {
        try
        {
            getStats().set("Username", username);
            getStats().set("Zombie_Kills", zKills);
            getStats().set("Player_Kills", pKills);
            getStats().set("Deaths", deaths);
            getStats().set("Victims", victims);
            getStats().save(statsFile);
        } catch (IOException e)
        {
            System.out.println("There was an error saving the stats file for " + username);
        }
    }

    // =================================================================

    public boolean isZPlayer()
    {
        plugin.zplayers.reload();
        return plugin.zplayers.getAsEditable().contains(username);
    }

    public boolean isZPlayer(String name)
    {
        plugin.zplayers.reload();
        return plugin.zplayers.getAsEditable().contains(name);
    }

    public boolean isAvailable()
    {
        return plugin.availablez.containsKey(username);
    }

    public boolean isAvailable(String name)
    {
        return plugin.availablez.containsKey(name);
    }

    // =================================================================
    /*
     *          THIS IS WHERE WE GET STUFF FOR THE OBJECT
     *                  LIKE NAME, KILLS ETC..
     *                  
     *                   PLEASE KEEP ORGANIZED!
     */
    // =================================================================

    // username
    public String getUsername()
    {
        return username;
    }

    // kills player
    public int getPKills()
    {
        return pKills;
    }

    // kills zombie
    public int getZKills()
    {
        return zKills;
    }

    // deaths
    public int getDeaths()
    {
        return deaths;
    }

    // victims
    public List<String> getVictims()
    {
        return victims;
    }

    public ZPlayer getPlayer(String name)
    {
        File temp = new File(plugin.getDataFolder() + "/stats/" + name + ".yml");
        FileConfiguration tempCFG = YamlConfiguration.loadConfiguration(temp);
        ZPlayer ztemp = new ZPlayer(plugin, name, 0, 0, 0, new ArrayList<String>());
        if (!temp.exists())
        {
            return null;
        } else
        {
            ztemp.setDeaths(tempCFG.getInt("Deaths"));
            ztemp.setPKills(tempCFG.getInt("Player_Kills"));
            ztemp.setZKills(tempCFG.getInt("Zombie_Kills"));
            ztemp.setVictims(tempCFG.getStringList("Victims"));
            ztemp.setUsername(tempCFG.getString("Username"));
            return ztemp;
        }
    }

    // =================================================================
    /*
     *          THIS IS WHERE WE **SET** STUFF FOR THE OBJECT
     *                  
     *                   PLEASE KEEP ORGANIZED!
     */
    // =================================================================

    public void setUsername(String name)
    {
        username = name;
    }

    public void setVictims(List<String> v)
    {
        victims = v;
    }

    public void setZKills(int amount)
    {
        zKills = amount;
    }

    public void setPKills(int amount)
    {
        pKills = amount;
    }

    public void setDeaths(int amount)
    {
        deaths = amount;
    }

    // =================================================================

}
