
package me.zeus.Zomboid.API;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.zeus.Zomboid.Core.Zomboid;



public class ZPlayer implements Serializable {

    // =================================================================

    private static final long serialVersionUID = -4808022018699734130L;
    private String name;
    private int pKills;
    private int zKills;
    private int deaths;
    private double money;
    private List<ZPlayer> victims;

    // =================================================================

    public ZPlayer()
    {
        super();
    }

    public static ZPlayer getZPlayer(String name)
    {
        return Zomboid.getInstance().getPlayers().get(name);
    }

    // =================================================================

    public static void createZPlayer(String name, int pkills, int zkills, int deaths, double money)
    {
        File f = new File(Zomboid.getInstance().getDataFolder() + File.separator + "players" + File.separator + name + ".dat");
        if (f.exists())
        {
            System.out.println("Error: File already exists!");
            return;
        }
        try
        {
            f.createNewFile();
            ZPlayer zplayer = new ZPlayer();
            zplayer.setName(name);
            zplayer.setPlayerKills(pkills);
            zplayer.setZombieKills(zkills);
            zplayer.setDeaths(deaths);
            zplayer.setMoney(money);
            zplayer.setVictims(new ArrayList<ZPlayer>());
            zplayer.save();
            Zomboid.getInstance().getPlayers().put(name, zplayer);
        } catch (IOException ioe)
        {
            System.out.println("There was an error creating data for " + name + "!");
            ioe.printStackTrace();
            return;
        }
    }

    public void save()
    {
        File f = new File(Zomboid.getInstance().getDataFolder() + File.separator + "players" + File.separator + name + ".dat");
        if (!f.exists())
        {
            System.out.println("Can not save data for " + name + " because file does not exist!");
            return;
        }
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(this);
            oos.close();
            System.out.println("Saved data for " + name);
        } catch (IOException ioe)
        {
            System.out.println("There was an error SAVING data for " + name + "!");
            ioe.printStackTrace();
            return;
        }
    }

    // =================================================================

    /* getters */
    
    public String getName()
    {
        return name;
    }

    public int getPlayerKills()
    {
        return pKills;
    }

    public int getZombieKills()
    {
        return zKills;
    }

    public int getDeaths()
    {
        return deaths;
    }

    public double getMoney()
    {
        return money;
    }

    public List<ZPlayer> getVictims()
    {
        return victims;
    }

    /* Setters */

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPlayerKills(int pKills)
    {
        this.pKills = pKills;
    }

    public void setZombieKills(int zKills)
    {
        this.zKills = zKills;
    }

    public void setDeaths(int deaths)
    {
        this.deaths = deaths;
    }

    public void setMoney(double money)
    {
        this.money = money;
    }

    public void setVictims(List<ZPlayer> victims)
    {
        this.victims = victims;
    }

    // =================================================================

}