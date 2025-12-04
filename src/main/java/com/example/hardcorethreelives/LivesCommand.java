package com.example.hardcorethreelives;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LivesCommand implements CommandExecutor {

    private final HardcoreThreeLives plugin;

    public LivesCommand(HardcoreThreeLives plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Dit command kan alleen in-game gebruikt worden.");
            return true;
        }

        Player player = (Player) sender;

        int deaths = plugin.getDataManager().getDeaths(player.getUniqueId());
        int maxLives = plugin.getConfig().getInt("lives.maxLives", 3);
        int livesLeft = Math.max(0, maxLives - deaths);

        player.sendMessage("§6===== §eHardcore Levens §6=====");
        player.sendMessage("§fNaam: §e" + player.getName());
        player.sendMessage("§fDeaths: §e" + deaths);
        player.sendMessage("§fMax levens: §e" + maxLives);

        if (livesLeft > 0) {
            player.sendMessage("§fJe hebt nog §a" + livesLeft + " §flevens over.");
        } else {
            player.sendMessage("§cJe hebt geen levens meer. Elke death geeft nu een ban.");
        }

        return true;
    }
}
