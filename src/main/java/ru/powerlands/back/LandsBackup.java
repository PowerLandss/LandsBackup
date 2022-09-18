package ru.powerlands.back;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class LandsBackup extends JavaPlugin {
    private static LandsBackup Main;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setMain(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LandsBackup getMain() {
        return Main;
    }

    private static void setMain(LandsBackup main) {
        Main = main;
    }
}
