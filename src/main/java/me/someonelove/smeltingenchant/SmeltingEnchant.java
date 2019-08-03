package me.someonelove.smeltingenchant;

import com.sucy.enchant.api.CustomEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Collection;
import java.util.Iterator;

public class SmeltingEnchant extends CustomEnchantment {

    private static final Material[] PERMITTED_ITEMS = new Material[]{
            Material.WOOD_PICKAXE, Material.STONE_PICKAXE,
            Material.IRON_PICKAXE, Material.GOLD_PICKAXE,
            Material.DIAMOND_PICKAXE};

    /* package-private */ SmeltingEnchant() {
        super("Smelting", "auto-smelts block drops");
        this.addNaturalItems(PERMITTED_ITEMS);
        this.setWeight(1.0);
        this.setTableEnabled(true);
    }

    /**
     * Called when a block is broken
     */
    @Override
    public void applyBreak(LivingEntity user, Block block, int level, BlockEvent event) {
        Player player = (Player) user;
        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
        for (ItemStack stack : drops) {
            Iterator<Recipe> iter = Bukkit.getServer().recipeIterator();
            while (iter.hasNext()) {
                Recipe recipe = iter.next();
                if (!(recipe instanceof FurnaceRecipe)) continue;
                FurnaceRecipe f = (FurnaceRecipe) recipe;
                if (f.getInput().getType() != stack.getType()) continue;
                stack.setType(f.getResult().getType());
                break;
            }
        }
        // black magic required to modify the drops
        block.setType(Material.AIR);
        drops.forEach(e -> {
           user.getWorld().dropItemNaturally(block.getLocation(), e);
           user.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
           user.getWorld().playSound(block.getLocation(), Sound.BLOCK_FURNACE_FIRE_CRACKLE, 10, 1);
        });
    }
}
