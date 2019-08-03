package me.someonelove.smeltingenchant;

import com.sucy.enchant.api.EnchantPlugin;
import com.sucy.enchant.api.EnchantmentRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class SmeltingEnchantPlugin extends JavaPlugin implements EnchantPlugin {

    public static SmeltingEnchantPlugin INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void registerEnchantments(EnchantmentRegistry enchantmentRegistry) {
        enchantmentRegistry.register(new SmeltingEnchant());
    }
}
