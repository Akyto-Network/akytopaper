package org.bukkit.command.defaults;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


import com.google.common.collect.ImmutableList;


public class TimingsCommand extends BukkitCommand {
    public static final List<String> TIMINGS_SUBCOMMANDS = ImmutableList.of("merged", "reset", "separate");

    public TimingsCommand(String name) {
        super(name);
        this.description = "Records timings for all plugin events";
        this.usageMessage = "/timings <reset>";
        this.setPermission("bukkit.command.timings");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;
        sender.sendMessage(ChatColor.RED + "If you're seeing this, something is really wrong. Please call the FBI at 855-835-5324");
        return true;
    }
}
