
package me.zeus.Zomboid.Core;


import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;



public class ZPlayerFile {

    // =================================================================

    String fileName;
    File file;
    FileConfiguration config;

    // =================================================================

    private Zomboid plugin;

    public ZPlayerFile(Zomboid instance, String name, boolean root)
    {
        plugin = instance;
        fileName = name;

        file = new File(plugin.getDataFolder() + File.separator + name);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public ZPlayerFile()
    {

    }

    // =================================================================

    public void reload()
    {
        if (file == null)
        {
            file = new File(plugin.getDataFolder() + File.separator + fileName);
        }

        if (config == null)
        {
            config = YamlConfiguration.loadConfiguration(file);
        }
    }

    // =================================================================

    public FileConfiguration getAsEditable()
    {
        if (config == null)
        {
            reload();
        }
        return config;
    }

    // =================================================================

    public void save()
    {
        file = new File(plugin.getDataFolder() + File.separator + fileName);
        try
        {
            getAsEditable().save(file);
            System.out.println("[Zomboid] Saved zplayers.yml !");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // =================================================================

    public void addName(String name)
    {
        getAsEditable().set(name, "");
        save();
    }

    // =================================================================

}
