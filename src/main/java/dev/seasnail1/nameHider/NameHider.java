package dev.seasnail1.nameHider;

import dev.seasnail1.nameHider.command.HideCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import me.neznamy.tab.api.TabAPI;
import org.bukkit.Bukkit;
import java.util.logging.Level;

public final class NameHider extends JavaPlugin {
    public static NameHider instance;
    public TabAPI tabAPI;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "starting NameHider plugin");

        instance = this;

        HideCommand obfuscationCommand = new HideCommand();
        PluginCommand command = this.getCommand("hide");
        if (command != null) {
            command.setExecutor(obfuscationCommand);
            command.setTabCompleter(obfuscationCommand);
        }

        if(!Bukkit.getPluginManager().isPluginEnabled("TAB")) {
            getLogger().log(Level.SEVERE, "TAB plugin is not installed. Many features won't work!");
        }

        this.tabAPI = TabAPI.getInstance();

            getLogger().log(Level.INFO, "All dependencies are loaded successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "stopping NameHider plugin");
    }

    public static NameHider getInstance() {
        return instance;
    }

    public TabAPI getTabAPI() {
        return this.tabAPI != null ? this.tabAPI : TabAPI.getInstance();
    }
}