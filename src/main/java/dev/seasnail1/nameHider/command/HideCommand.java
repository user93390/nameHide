package dev.seasnail1.nameHider.command;

import dev.seasnail1.nameHider.NameHider;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HideCommand implements CommandExecutor, TabCompleter {
    private final NameHider plugin = NameHider.getInstance();

    TabAPI tabAPI = plugin.getTabAPI();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player) || args.length == 0) {
            sender.sendMessage(net.kyori.adventure.text.Component.text("Usage: /hide <true|false>", NamedTextColor.RED));
            return true;
        }

        if (!player.hasPermission("nameHide.hide")) {
            player.sendMessage(net.kyori.adventure.text.Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        TabPlayer tabPlayer = tabAPI.getPlayer(player.getUniqueId());

        switch (subCommand) {
            case "true" -> {
                player.displayName(Component.text(player.getName()).decoration(TextDecoration.OBFUSCATED, TextDecoration.State.TRUE));
                tabAPI.getNameTagManager().setPrefix(tabPlayer, "§k");
                tabAPI.getTabListFormatManager().setName(tabPlayer, "§k" + player.getName());


                player.sendMessage(net.kyori.adventure.text.Component.text("Now hiding your name.", NamedTextColor.GREEN));
                return true;
            }

            case "false" -> {
                player.displayName(Component.text(player.getName()).decoration(TextDecoration.OBFUSCATED, TextDecoration.State.TRUE));
                tabAPI.getNameTagManager().setPrefix(tabPlayer, "");
                tabAPI.getTabListFormatManager().setName(tabPlayer, player.getName());

                player.sendMessage(net.kyori.adventure.text.Component.text("Now showing your name.", NamedTextColor.GREEN));
                return true;
            }

            default -> { return false; }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("true", "false");
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}