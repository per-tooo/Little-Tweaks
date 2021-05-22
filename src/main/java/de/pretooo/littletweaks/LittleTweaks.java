package de.pretooo.littletweaks;

import de.pretooo.littletweaks.Campfire.registerCampfire;
import de.pretooo.littletweaks.Campfire.respawnEvent;
import de.pretooo.littletweaks.debug.getBlock;
import de.pretooo.littletweaks.simpleHarvest.ClickingEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LittleTweaks extends JavaPlugin {
    private static LittleTweaks instance;
    public static LittleTweaks getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        registerEvents();
    }

    @Override
    public void onDisable() {

    }


    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new ClickingEvent(), this);

        Bukkit.getPluginManager().registerEvents(new registerCampfire(), this);
        Bukkit.getPluginManager().registerEvents(new respawnEvent(), this);
    }
}
