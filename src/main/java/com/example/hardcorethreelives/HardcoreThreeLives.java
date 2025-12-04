package com.example.hardcorethreelives;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class HardcoreThreeLives extends JavaPlugin {

    private static HardcoreThreeLives instance;

    private File dataFile;
    private FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        createDataFile();

        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

        getCommand("lives").setExecutor(new LivesCommand(this));
        getCommand("resetlives").setExecutor(new ResetLivesCommand(this));

        getLogger().info("HardcoreThreeLives ingeschakeld!");
    }

    @Override
    public void onDisable() {
        saveDataConfig();
    }

    public static HardcoreThreeLives getInstance() {
        return instance;
    }

    // Data file opslaan
    private void createDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getDeaths(UUID uuid) {
        return dataConfig.getInt("deaths." + uuid.toString(), 0);
    }

    public void setDeaths(UUID uuid, int amount) {
        dataConfig.set("deaths." + uuid.toString(), amount);
        saveDataConfig();
    }

    public int addDeath(UUID uuid) {
        int updated = getDeaths(uuid) + 1;
        setDeaths(uuid, updated);
        return updated;
    }
}
