package ru.powerlands.back.save;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.powerlands.back.LandsBackup;

import static ru.powerlands.back.LandsBackup.color;

public class LBCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!LandsBackup.getMain().getConfig().getBoolean("settings.commands.enabled")) {
            sender.sendMessage(color(LandsBackup.getMain().getConfig().getString("settings.messages.cmdnt")));
            return true;
        }
        if(!sender.hasPermission(LandsBackup.getMain().getConfig().getString("settings.commands.perm"))) {
            sender.sendMessage(color(LandsBackup.getMain().getConfig().getString("settings.messages.noperms")));
            return true;
        }
        (new Thread(() -> {
        long m = System.currentTimeMillis();
        sender.sendMessage(color(LandsBackup.getMain().getConfig().getString("settings.messages.save")));
        new LBManager().save();
        sender.sendMessage(color(LandsBackup.getMain().getConfig().getString("settings.messages.saved").replaceAll("%ms", String.valueOf((System.currentTimeMillis() - m)))));
        })).start();
        return false;
    }
}
