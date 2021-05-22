package de.pretooo.littletweaks.Campfire;

import de.pretooo.littletweaks.LittleTweaks;
import de.pretooo.littletweaks.utilities.DataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class registerCampfire implements Listener {
    @EventHandler
    public void campfirePlaced(BlockPlaceEvent event) {
        if (!LittleTweaks.getInstance().getConfig().getBoolean("modules.campfire.use"))
            return;

        Block block = event.getBlockPlaced();
        if (block.getType() == Material.CAMPFIRE) {
            DataManager.SetupConfig(null, "spawns.yml");

            DataManager.getDataManager().set(event.getPlayer().getName() + ".X", event.getBlockPlaced().getLocation().getX());
            DataManager.getDataManager().set(event.getPlayer().getName() + ".Y", event.getBlockPlaced().getLocation().getY());
            DataManager.getDataManager().set(event.getPlayer().getName() + ".Z", event.getBlockPlaced().getLocation().getZ());
            DataManager.getDataManager().set(event.getPlayer().getName() + ".yaw", event.getPlayer().getLocation().getYaw());
            DataManager.getDataManager().set(event.getPlayer().getName() + ".pitch", event.getPlayer().getLocation().getPitch());

            DataManager.saveConfig();
        }
    }
}
