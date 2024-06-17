package akyto.spigot.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly");
        this.description = "Toggle flying mode";
        this.usageMessage = "/fly";
        this.setPermission("kspigot.command.fly");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do that!");
            return false;
        }

        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        Player player = (Player) sender;

        player.setAllowFlight(!player.getAllowFlight());

        if (!player.getAllowFlight())
            player.setFlying(false);

        player.sendMessage(ChatColor.GREEN + "Successfully " + (player.getAllowFlight() ? "enabled" : "disabled") + " fly mode.");

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return java.util.Collections.emptyList();
    }
}

