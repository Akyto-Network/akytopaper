package akyto.spigot.command;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping", "Ping player.", "/ping <player>", Arrays.asList("ms", "latency"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        // If sender is a player send the player their own ping
        if ((args.length == 0) && sender instanceof Player) {
            sender.sendMessage(ChatColor.GRAY + "Your ping: " + ChatColor.WHITE + ((Player) sender).getPing() + ChatColor.GRAY + "ms");

        // Otherwise send the ping of the argument player if valid
        } else if (args.length == 1) {
            Player pingPlayer = sender.getServer().getPlayer(args[0]);
            if (pingPlayer != null && sender.getServer().getOnlinePlayers().contains(pingPlayer)) {
                sender.sendMessage(ChatColor.GRAY + pingPlayer.getName() + "'s ping: " + ChatColor.WHITE + pingPlayer.getPing() + ChatColor.GRAY + "ms");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid player!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /ping <player>");
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                String playerName = player.getName();
                if (StringUtil.startsWithIgnoreCase(playerName, args[0])) {
                    completions.add(playerName);
                }
            }
            return completions;
        }
        return ImmutableList.of();
    }
}