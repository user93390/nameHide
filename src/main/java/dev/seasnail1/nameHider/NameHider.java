package dev.seasnail1.nameHider;

import dev.seasnail1.nameHider.command.HideCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class NameHider extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("starting NameHider plugin");

        saveDefaultConfig();

        HideCommand obfuscationCommand = new HideCommand();
        PluginCommand command = this.getCommand("hide");
        if (command != null) {
            command.setExecutor(obfuscationCommand);
            command.setTabCompleter(obfuscationCommand);
        }
    }

    @Override
    public void onDisable() {
        System.out.println("shutting down NameHider plugin");
    }
}
