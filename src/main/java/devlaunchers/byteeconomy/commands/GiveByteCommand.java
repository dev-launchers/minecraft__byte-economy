package devlaunchers.byteeconomy.commands;

import devlaunchers.byteeconomy.ByteEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveByteCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Create a new ItemStack (type: diamond)
            ItemStack byteItem = ByteEconomy.getItemUtil().getByteItem().clone();
            int numBytes = 1;

            if (args.length == 0) { } //Added the if to prevent this case from going to the else
            else if (args.length == 1) {
                try {
                    numBytes = Integer.parseInt(args[0]);
                } catch(NumberFormatException nfe) {
                    Player tempPlayer = Bukkit.getPlayer(args[0]);
                    if (tempPlayer == null) {
                        player.sendMessage(ChatColor.RED + "Player not found!");
                        return true;
                    }
                    player = tempPlayer;
                }
            } else if (args.length == 2) {
                Player tempPlayer = Bukkit.getPlayer(args[0]);
                if (tempPlayer == null) {
                    player.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                player = tempPlayer;
                try {
                    numBytes = Integer.parseInt(args[1]);
                } catch (NumberFormatException nfe) {
                    player.sendMessage(ChatColor.RED + "Wrong format!");
                }
            }
                else {
                    player.sendMessage(ChatColor.RED + "Wrong format!");
                }

            // Set the amount of the ItemStack
            byteItem.setAmount(numBytes);

            // Give the player our items (comma-seperated list of all ItemStack)
            player.getInventory().addItem(byteItem);

            // Modify the message depending on the receiver
            if (player.equals((Player) sender)) {
                player.sendMessage(ChatColor.GREEN + "You have received " + numBytes + " Byte$");
            } else {
                player.sendMessage(ChatColor.GREEN + "" + numBytes + " Byte$ have been added to " + player.getName());
            }

            System.out.println("[ByteEconomy] [LOG] Gave byte$! Amount: " + numBytes);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}