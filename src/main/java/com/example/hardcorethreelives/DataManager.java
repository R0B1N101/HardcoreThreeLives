package com.example.hardcorethreelives;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataManager {

    private final HardcoreThreeLives plugin;

    private File dataFile;
    private FileConfiguration dataConfig;

    public DataManager(HardcoreThreeLives plugin) {
        this.plugin = plugin;
        createDataFile();
    }

    private void createDataFile() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        dataFile = new File(plugin.getDataFolder(), "data.yml");

        if (!dataFile.exists()) {
            try {
                if (dataFile.createNewFile()) {
                    plugin.getLogger().info("data.yml aangemaakt.");
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Kon data.yml niet aanmaken!");
                e.printStackTrace();
            }
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getConfig() {
        return dataConfig;
    }

    public void save() {
        if (dataConfig == null || dataFile == null) return;
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Kon data.yml niet opslaan!");
            e.printStackTrace();
        }
    }

    // ====== Speler data helpers ======

    private String basePath(UUID uuid) {
        return uuid.toString();
    }

    /**
     * Zorgt dat er een entry bestaat:
     * "<uuid>":
     *   name: Naam
     *   deaths: 0
     */
    public void ensurePlayerEntry(Player player) {
        UUID uuid = player.getUniqueId();
        String base = basePath(uuid);

        if (!dataConfig.contains(base)) {
            dataConfig.set(base + ".name", player.getName());
            dataConfig.set(base + ".deaths", 0);
            save();
        } else {
            // Naam updaten als speler naam heeft veranderd
            String storedName = dataConfig.getString(base + ".name");
            if (storedName == null || !storedName.equals(player.getName())) {
                dataConfig.set(base + ".name", player.getName());
                save();
            }
        }
    }

    public String getName(UUID uuid) {
        return dataConfig.getString(basePath(uuid) + ".name");
    }

    public int getDeaths(UUID uuid) {
        return dataConfig.getInt(basePath(uuid) + ".deaths", 0);
    }

    public void setDeaths(UUID uuid, int amount) {
        String base = basePath(uuid);
        if (!dataConfig.contains(base + ".name")) {
            dataConfig.set(base + ".name", uuid.toString());
        }
        dataConfig.set(base + ".deaths", amount);
        save();
    }

    /**
     * Verhoogt deaths met 1 en geeft de nieuwe waarde terug.
     */
    public int addDeath(UUID uuid) {
        int current = getDeaths(uuid) + 1;
        setDeaths(uuid, current);
        return current;
    }
}
