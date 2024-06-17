package org.bukkit.command.defaults;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class SuspendCommand extends BukkitCommand {

    private final static List<String> ARGUMENTS = ImmutableList.of("false", "true");

    public SuspendCommand() {
        super(
            "suspend",
            "Suspends the server, if no players are connected",
            "/suspend [true|false]",
            Collections.emptyList()
        );
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!testPermission(sender)) return true;
        final Server server = sender.getServer();
        if(args.length == 0) {
            final Instant at = server.suspendedAt();
            if(at == null) {
                sender.sendMessage(new ComponentBuilder("Server is not suspended").color(ChatColor.WHITE).create());
            } else {
                final Duration dur = Duration.between(at, Instant.now());
                sender.sendMessage(new ComponentBuilder(
                    "Server suspended for " + dur +
                        " and interrupted " + server.interruptions() + " times"
                ).color(ChatColor.WHITE).create());
            }
        } else if("true".equals(args[0])) {
            if(server.isSuspended()) {
                sender.sendMessage(new ComponentBuilder("Server is already suspended").color(ChatColor.RED).create());
                return true;
            }
            if(!server.setSuspended(true)) {
                sender.sendMessage(new ComponentBuilder("Server cannot be suspended while players are connected").color(ChatColor.RED).create());
            }
        } else if("false".equals(args[0])) {
            if(!server.setSuspended(false)) {
                sender.sendMessage(new ComponentBuilder("Server is not currently suspended").color(ChatColor.RED).create());
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], ARGUMENTS, new ArrayList<>(ARGUMENTS.size()));
        }
        return ImmutableList.of();
    }
}
