package com.example.hardcorethreelives;

import org.bukkit.plugin.java.JavaPlugin;

public class HardcoreThreeLives extends JavaPlugin {

    private static HardcoreThreeLives instance;

    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        // Config
        saveDefaultConfig();
        reloadConfig();

        // Data manager voor data.yml
        dataManager = new DataManager(this);

        // Events
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        // Commands
        if (getCommand("lives") != null) {
            getCommand("lives").setExecutor(new LivesCommand(this));
        }
        if (getCommand("resetlives") != null) {
            getCommand("resetlives").setExecutor(new ResetLivesCommand(this));
        }
        if (getCommand("hardcore") != null) {
            getCommand("hardcore").setExecutor(new HardcoreCommand(this));
        }

        getLogger().info("HardcoreThreeLives is ingeschakeld.");
    }

    @Override
    public void onDisable() {
        if (dataManager != null) {
            dataManager.save();
        }
        getLogger().info("HardcoreThreeLives is uitgeschakeld.");
    }

    public static HardcoreThreeLives getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
