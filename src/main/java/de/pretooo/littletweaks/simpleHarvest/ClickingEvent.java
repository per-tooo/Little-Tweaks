package de.pretooo.littletweaks.simpleHarvest;

import de.pretooo.littletweaks.LittleTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ClickingEvent implements Listener {
    @EventHandler
    public void simpleHarvest(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK) || !(LittleTweaks.getInstance().getConfig().getBoolean("modules.simpleHarvest.use")))
            return;

        boolean harvestable = false;
        switch (event.getClickedBlock().getType()) {
            case WHEAT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case NETHER_WART:
                harvestable = true;

            default:
                harvestable = false;
        }

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        BlockData blockData = block.getBlockData();
        if (!(blockData instanceof Ageable)) {
            Bukkit.getLogger().log(Level.INFO, "Selected Block is not ageable. You may not use SimpleHarvest on it.");
            return;
        }
        Ageable ageable = (Ageable)blockData;
        boolean ripe = ageable.getAge() == ageable.getMaximumAge();

        Material seedMaterial = null;
        switch (block.getType()) {
            case WHEAT:
                seedMaterial = Material.WHEAT_SEEDS;
                break;
            case CARROTS:
                seedMaterial = Material.CARROT;
                break;
            case POTATOES:
                seedMaterial = Material.POTATO;
                break;
            case BEETROOTS:
                seedMaterial = Material.BEETROOT_SEEDS;
                break;
            case NETHER_WART:
                seedMaterial = Material.NETHER_WART;
                break;
        }
        ItemStack seed = new ItemStack(seedMaterial, 1);

        List<ItemStack> drops;
        EntityEquipment entityEquipment = player.getEquipment();
        if (entityEquipment.getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
            drops = new ArrayList<>(block.getDrops(entityEquipment.getItemInMainHand()));
        else if (entityEquipment.getItemInOffHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
            drops = new ArrayList<>(block.getDrops(entityEquipment.getItemInOffHand()));
        drops = new ArrayList<>(block.getDrops());

        boolean seedSearch = false;
        for (ItemStack itemStack : drops) {
            if (itemStack.getType() == seed.getType()) {
                int newAmount = itemStack.getAmount() - 1;
                if (newAmount > 0)
                    itemStack.setAmount(newAmount);
                else
                    drops.remove(itemStack);
                seedSearch = true;
                break;
            }
        }
        if (!seedSearch) {
            Inventory inventory = player.getInventory();
            if (inventory.contains(seed, 1)) {
                inventory.remove(seed);
                seedSearch = true;
            }
        }
        if (seedSearch) {
            blockData = block.getBlockData();
            ageable = (Ageable)blockData;
            ageable.setAge(0);
            block.setBlockData(ageable);
        } else
            block.setType(Material.AIR);

        for (ItemStack itemStack : drops)
            block.getWorld().dropItem(block.getLocation(), itemStack);
    }
}
