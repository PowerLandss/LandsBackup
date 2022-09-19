package ru.powerlands.back;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import ru.powerlands.back.save.LBCommand;
import ru.powerlands.back.save.LBManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class LandsBackup extends JavaPlugin {
    private static LandsBackup Main;
    private static SimpleDateFormat sdfDate;
    private LBManager lbManager;

    @Override
    public void onEnable() {
        Main = this;
        saveDefaultConfig();
        Bukkit.getPluginCommand("landsbackup").setExecutor(new LBCommand());
        new File(getDataFolder().getAbsolutePath() + "/backups").mkdirs();
        lbManager = new LBManager();
        System.out.println("LandsBackup: Включаю плагин версии " + getPlugin(LandsBackup.class).getDescription().getVersion() + " by PowerLands");
        start();
    }
    private void start() {
            if(getMain().getConfig().getBoolean("settings.task.enabled")) {
                Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
                    lbManager.save();
                }, 5, 20 * getMain().getConfig().getLong("settings.task.time"));
            }
    }

    @Override
    public void onDisable() {
        if(getConfig().getBoolean("settings.onStop")) {
            lbManager.save();
        }
    }

    public static LandsBackup getMain() {
        return Main;
    }
    public static String date() {
        sdfDate = new SimpleDateFormat(getMain().getConfig().getString("settings.time.format"));
        return sdfDate.format(new Date());
    }
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
