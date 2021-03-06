
package devlaunchers.byteeconomy.populationevents;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.items.DevLauncherItem;
import devlaunchers.items.ItemRepository;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ChestBytePopulator implements Listener {

    private static final int[] byteAmounts = new int[]{0, 1, 2, 4, 8, 16};

    private int getRandomByteAmount() {
        return byteAmounts[(int)(Math.random()*byteAmounts.length)];
    }

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent e) {
        BlockState[] tileEntities = e.getChunk().getTileEntities();
        for (BlockState state : tileEntities) {
            if(state.getType() == Material.CHEST) {
                ItemStack byteItemStack = ItemRepository.getItem(DevLauncherItem.ECONOMY_BYTE_ITEM).clone();
                int numBytes = getRandomByteAmount();
                byteItemStack.setAmount(numBytes);

                Chest chest = (Chest)state;//.getBlock();

                tryInsertIntoRandomSlot(chest.getBlockInventory(), byteItemStack);
            }
        }
    }

    private boolean tryInsertIntoRandomSlot(Inventory inventory, ItemStack itemStack) {
        boolean inserted = false;
        int sentinel = 0;
        while (sentinel < 30) {
            sentinel++;
            int randomSlotIndex = (int)(Math.random()*20);
            if (inventory.getItem(randomSlotIndex) != null) {
                inventory.setItem(randomSlotIndex, itemStack);
                inserted = true;
                break;
            }
        }
        return inserted;
    }
}

