package com.example.hardcorethreelives;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DeathListener implements Listener {

    private final HardcoreThreeLives plugin;

    public DeathListener(HardcoreThreeLives plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Tel death en sla op in data.yml
        int deaths = plugin.addDeath(player.getUniqueId());

        FileConfiguration config = plugin.getConfig();

        int maxLives = config.getInt("lives.maxLives", 3);
        long baseBanMinutes = config.getLong("ban.baseMinutes", 60);
        long extraMinutesPerDeath = config.getLong("ban.extraMinutesPerDeath", 0);

        // Nog binnen je levens?
        if (deaths <= maxLives) {
            int remaining = maxLives - deaths;

            if (remaining > 0) {
                // Bericht als je nog levens over hebt
                String template = config.getString(
                        "messages.deathStillLives",
                        "&cJe bent doodgegaan! Je hebt nog &e{remaining} &ckansen over."
                );
                String msg = applyPlaceholders(template, deaths, maxLives, remaining, null);
                player.sendMessage(color(msg));
            } else {
                // Bericht als je nu op 0 levens staat
                String template = config.getString(
                        "messages.deathNoLivesLeft",
                        "&cJe hebt al je &e{maxLives} &ckansen verbruikt! Volgende death is een &4ban&c."
                );
                String msg = applyPlaceholders(template, deaths, maxLives, 0, null);
                player.sendMessage(color(msg));
            }
            return;
        }

        // Vanaf hier: speler heeft GEEN levens meer → ban-logica

        // Hoeveelste ban-death is dit? (1 = eerste keer dat hij geband wordt)
        int banDeathIndex = deaths - maxLives;

        long totalBanMinutes = baseBanMinutes;
        if (banDeathIndex > 1 && extraMinutesPerDeath > 0) {
            totalBanMinutes += (banDeathIndex - 1L) * extraMinutesPerDeath;
        }

        long banMillis = totalBanMinutes * 60_000L;
        Date expires = new Date(System.currentTimeMillis() + banMillis);

        // Datum/tijd mooi formatteren (Nederlands)
        LocalDateTime ldt = expires.toInstant()
                .atZone(ZoneId.of("Europe/Amsterdam"))
                .toLocalDateTime();

        String pattern = config.getString(
                "formatting.dateTime",
                "EEEE d MMMM yyyy 'om' HH:mm 'uur'"
        );

        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern(pattern, new Locale("nl", "NL"));
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Ongeldig dateTime patroon in config.yml, gebruik standaard.");
            formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy 'om' HH:mm 'uur'",
                    new Locale("nl", "NL"));
        }

        String formattedTime = ldt.format(formatter);

        // Ban reason uit config
        String banReasonTemplate = config.getString(
                "messages.banReason",
                "Geen levens meer in hardcore!"
        );
        String banReason = color(applySimple(banReasonTemplate, deaths, maxLives));

        // Ban plaatsen
        Bukkit.getBanList(BanList.Type.NAME).addBan(
                player.getName(),
                banReason,
                expires,
                "HardcoreThreeLives"
        );

        // Kick message uit config
        String kickTemplate = config.getString(
                "messages.deathBannedKick",
                "&cJe bent doodgegaan zonder levens!\n&fVerbannen tot:\n&e{time}"
        );
        String kickMsg = applyPlaceholders(kickTemplate, deaths, maxLives, 0, formattedTime);

        player.kickPlayer(color(kickMsg));
    }

    // ===== Helper methods =====

    private String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Vervang placeholders:
     * {deaths}, {maxLives}, {remaining}, {time}
     */
    private String applyPlaceholders(String template,
                                     int deaths,
                                     int maxLives,
                                     int remaining,
                                     String time) {

        String result = template;

        result = result.replace("{deaths}", String.valueOf(deaths));
        result = result.replace("{maxLives}", String.valueOf(maxLives));
        result = result.replace("{remaining}", String.valueOf(remaining));

        if (time != null) {
            result = result.replace("{time}", time);
        }

        return result;
    }

    /**
     * Versimpelde variant zonder tijd (voor banReason)
     */
    private String applySimple(String template, int deaths, int maxLives) {
        String result = template;
        result = result.replace("{deaths}", String.valueOf(deaths));
        result = result.replace("{maxLives}", String.valueOf(maxLives));
        return result;
    }
}
