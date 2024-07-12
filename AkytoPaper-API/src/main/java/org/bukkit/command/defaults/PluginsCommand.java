package org.bukkit.command.defaults;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PluginsCommand extends BukkitCommand {
    public PluginsCommand(String name) {
        super(name);
        this.description = "Gets a list of plugins running on the server";
        this.usageMessage = "/plugins";
        this.setPermission("bukkit.command.plugins");
        this.setAliases(Arrays.asList("pl"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------");
        sender.sendMessage(getPluginList());
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------");
        return true;
    }

    private String[] getPluginList() {
        // Paper start
        TreeMap<String, String> plugins = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            plugins.put((plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + plugin.getDescription().getName(),
                    ChatColor.GRAY + "[" + ChatColor.RED + plugin.getDescription().getAuthors() + ChatColor.GRAY + " - " + ChatColor.DARK_RED + plugin.getDescription().getVersion() + ChatColor.GRAY + "]");
        }

        StringBuilder pluginList = new StringBuilder();
        for (Map.Entry<String, String> entry : plugins.entrySet()) {
            if (pluginList.length() > 0) {
                pluginList.append("\n");
            }

            pluginList.append(entry.getKey());
            pluginList.append(" ");
            pluginList.append(entry.getValue());
        }

        return new String[] {
                ChatColor.DARK_GRAY + "Plugins " + ChatColor.GRAY + "(" + ChatColor.RED + plugins.size() + ChatColor.GRAY + "):",
                " ",
                pluginList.toString()
        };
        // Paper end
    }

    // Spigot Start
    @Override
    public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
    {
        return java.util.Collections.emptyList();
    }
    // Spigot End
}
