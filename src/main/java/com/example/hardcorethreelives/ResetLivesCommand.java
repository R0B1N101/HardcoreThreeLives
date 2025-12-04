package com.example.hardcorethreelives;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import java.util.UUID;

public class ResetLivesCommand implements CommandExecutor {

    private final HardcoreThreeLives plugin;

    public ResetLivesCommand(HardcoreThreeLives plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Je moet operator zijn om dit command te gebruiken.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /resetlives <speler>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (target == null || ( !target.hasPlayedBefore() && !target.isOnline())) {
            sender.sendMessage(ChatColor.RED + "Speler '" + args[0] + "' is niet gevonden of heeft nog nooit gespeeld.");
            return true;
        }

        UUID uuid = target.getUniqueId();
        plugin.getDataManager().setDeaths(uuid, 0);

        sender.sendMessage(ChatColor.GREEN + "Deaths van " + target.getName() + " zijn gereset naar 0.");

        if (target.isOnline() && target.getPlayer() != null) {
            target.getPlayer().sendMessage(ChatColor.GREEN + "Een operator heeft jouw deaths gereset. Je hebt weer al je levens terug.");
        }

        return true;
    }
}
