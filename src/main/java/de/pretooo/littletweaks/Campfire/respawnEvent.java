package de.pretooo.littletweaks.Campfire;

import de.pretooo.littletweaks.LittleTweaks;
import de.pretooo.littletweaks.utilities.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class respawnEvent implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        DataManager.SetupConfig(null, "spawns.yml");
        if (!DataManager.getDataManager().getBoolean("modules.campfire.use"))
            return;

        Double x = DataManager.getDataManager().getDouble(event.getPlayer().getName() + ".X");
        Double y = DataManager.getDataManager().getDouble(event.getPlayer().getName() + ".Y");
        Double z = DataManager.getDataManager().getDouble(event.getPlayer().getName() + ".Z");
        Float yaw = (float)DataManager.getDataManager().getDouble(event.getPlayer().getName() + ".yaw");
        Float pitch = (float)DataManager.getDataManager().getDouble(event.getPlayer().getName() + ".pitch");

        if (event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), x, y, z)).getType() == Material.CAMPFIRE) {
            event.setRespawnLocation(new Location(event.getPlayer().getWorld(), x, y+1, z, yaw, pitch));

            Bukkit.getScheduler().runTaskLater(LittleTweaks.getInstance(), task -> {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 1));
            }, 5L);
        }
    }
}
