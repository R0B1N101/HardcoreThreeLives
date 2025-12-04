package com.example.hardcorethreelives;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class HardcoreCommand implements CommandExecutor {

    private final HardcoreThreeLives plugin;

    public HardcoreCommand(HardcoreThreeLives plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Alleen operators
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Je moet operator zijn om dit command te gebruiken.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Gebruik: /hardcore reload");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            long start = System.currentTimeMillis();

            // Config herladen
            plugin.reloadConfig();

            long took = System.currentTimeMillis() - start;
            sender.sendMessage(ChatColor.GREEN + "HardcoreThreeLives config herladen (" + took + " ms).");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Onbekend subcommand. Gebruik: /hardcore reload");
        return true;
    }
}
