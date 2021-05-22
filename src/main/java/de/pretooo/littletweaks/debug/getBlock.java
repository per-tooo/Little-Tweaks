package de.pretooo.littletweaks.debug;

import de.pretooo.littletweaks.utilities.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getBlock implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if (args.length == 3) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);

            player.sendMessage(player.getWorld().getBlockAt(new Location(player.getWorld(), x, y, z)).getType().toString());
        }
        if (args.length == 1) {
            Player user = Bukkit.getPlayerExact(args[0]);

            DataManager.SetupConfig(null, "spawns.yml");
            Double x = DataManager.getDataManager().getDouble(user.getName() + ".X");
            Double y = DataManager.getDataManager().getDouble(user.getName() + ".Y");
            Double z = DataManager.getDataManager().getDouble(user.getName() + ".Z");

            player.sendMessage(user.getName() + ".X");
            player.sendMessage(x.toString() + " / " + y.toString() + " / " + z.toString());
            player.sendMessage(player.getWorld().getBlockAt(new Location(player.getWorld(), x, y, z)).getType().toString());
        }

        return false;
    }
}
