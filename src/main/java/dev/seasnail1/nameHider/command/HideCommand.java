package dev.seasnail1.nameHider.command;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HideCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player) || args.length == 0) {
            Command.broadcastCommandMessage(sender, net.kyori.adventure.text.Component.text("Unknown error. Try again later!", NamedTextColor.RED));
            return true;
        }

        if (!player.hasPermission("nameHide.hide")) {
            Command.broadcastCommandMessage(sender, net.kyori.adventure.text.Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "true" -> {
                player.displayName(Component.text(player.getName()).decoration(TextDecoration.OBFUSCATED, TextDecoration.State.TRUE));
                player.playerListName(Component.text(player.getName()).decoration(TextDecoration.OBFUSCATED, TextDecoration.State.TRUE));

                Command.broadcastCommandMessage(sender, net.kyori.adventure.text.Component.text("Now hiding your name.", NamedTextColor.GREEN));
                return true;
            }

            case "false" -> {
                player.displayName(Component.text(sender.getName()).decoration(TextDecoration.OBFUSCATED, TextDecoration.State.FALSE));
                player.playerListName(Component.text(sender.getName()).decoration(TextDecoration.OBFUSCATED, TextDecoration.State.FALSE));

                Command.broadcastCommandMessage(sender, net.kyori.adventure.text.Component.text("Your name is now visible", net.kyori.adventure.text.format.NamedTextColor.RED));
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