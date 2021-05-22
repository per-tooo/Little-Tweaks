package de.pretooo.littletweaks.utilities;

import de.pretooo.littletweaks.LittleTweaks;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataManager {
    private static File file, folder;
    private static FileConfiguration fileConfiguration;

    public static void SetupConfig(File folder, String fileName) {
        if (folder == null)
            folder = setFolder();

        file = new File(folder, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                Bukkit.getLogger().severe("This file can not be saved: " + fileName);
                // admin alert [tbd]
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getDataManager() {
        return fileConfiguration;
    }

    public static void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e1) {
            e1.printStackTrace();
            Bukkit.getLogger().severe("A config file was not saved because of an internal error!");
            // admin alert [tbd]
        }
    }

    public static void reloadConfig(File folder, String fileName) {
        if (folder == null)
            folder = setFolder();

        file = new File(folder, fileName);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    private static File setFolder() {
        return LittleTweaks.getInstance().getDataFolder();
    }
}
