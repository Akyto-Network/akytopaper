package akyto.spigot.command;

import akyto.spigot.aSpigot;
import akyto.spigot.aSpigotConfig;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PotCommand extends Command {

    private final Map<String, Method> subCommands = new HashMap<>();
    private final aSpigotConfig config = aSpigot.INSTANCE.getConfig();

    public PotCommand() {
        super(
                "knockback",
                "Change the knockback",
                "/pot <view | setgravity | setvelocity | setinaccuracy> <value>",
                List.of("pot")
        );
        this.setPermission("aspigot.pot");

        try {
            subCommands.put("setgravity", aSpigotConfig.class.getMethod("setGravity", float.class));
            subCommands.put("setvelocity", aSpigotConfig.class.getMethod("setVelocity", float.class));
            subCommands.put("setinaccuracy", aSpigotConfig.class.getMethod("setInaccuracy", float.class));
            subCommands.put("setsmooth", aSpigotConfig.class.getMethod("setSmoothPotting", boolean.class));
        } catch (NoSuchMethodException ex) {
            System.err.println("Failed to init pot command table");
            ex.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + this.usageMessage);
            return false;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("view")) {
            sender.sendMessage(currentPotion());
            return true;
        }

        if (args.length == 2) {
            String sub = args[0].toLowerCase();
            if (!subCommands.containsKey(sub)) {
                sender.sendMessage(ChatColor.RED + this.usageMessage);
                return false;
            }

            try {
                float value = Float.parseFloat(args[1]);
                subCommands.get(sub).invoke(config, value);
                config.save();
                sender.sendMessage(ChatColor.GREEN + "Potion settings successfully updated!");
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + String.format("'%s' is not a valid float.", args[1]));
            } catch (InvocationTargetException | IllegalAccessException e) {
                sender.sendMessage(ChatColor.RED + "Failed to invoke method, contact dev");
            }
            return true;
        }

        sender.sendMessage(ChatColor.RED + this.usageMessage);
        return false;
    }

    private String currentPotion() {
        return ChatColor.GRAY + "Current potion settings:\n" +
                ChatColor.GRAY + String.format("Gravity: %s%.4f\n", ChatColor.RESET, config.getGravity()) +
                ChatColor.GRAY + String.format("Velocity: %s%.4f\n", ChatColor.RESET, config.getVelocity()) +
                ChatColor.GRAY + String.format("Inaccuracy: %s%.4f\n", ChatColor.RESET, config.getInaccuracy()) +
                ChatColor.GRAY + String.format("Smooth Potting: %s%b\n", ChatColor.RESET, config.isSmoothPotting());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length < 2) {
            return Stream.of("view", "setgravity", "setvelocity", "setinaccuracy")
                    .filter(sub -> args.length == 0 || sub.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toUnmodifiableList());
        }

        return ImmutableList.of();
    }
}