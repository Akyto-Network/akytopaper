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

public class KbCommand extends Command {

    private final Map<String, Method> subCommands = new HashMap<>();
    private final aSpigotConfig config = aSpigot.INSTANCE.getConfig();

    public KbCommand() {
        super(
                "knockback",
                "Change the knockback",
                "/knockback <view | sethor | setver | setfric| setxhor | setxver | setslowdown | setverlimit> <value>",
                List.of("kb")
        );
        this.setPermission("aspigot.knockback");

        try {
            subCommands.put("sethor", aSpigotConfig.class.getMethod("setHorizontal", double.class));
            subCommands.put("setver", aSpigotConfig.class.getMethod("setVertical", double.class));
            subCommands.put("setfric", aSpigotConfig.class.getMethod("setFriction", double.class));
            subCommands.put("setxhor", aSpigotConfig.class.getMethod("setExtraHorizontal", double.class));
            subCommands.put("setxver", aSpigotConfig.class.getMethod("setExtraVertical", double.class));
            subCommands.put("setverlimit", aSpigotConfig.class.getMethod("setVerticalLimit", double.class));
            subCommands.put("setslowdown", aSpigotConfig.class.getMethod("setSlowdown", double.class));
        } catch (NoSuchMethodException ex) {
            System.err.println("Failed to init kb command table");
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
            sender.sendMessage(currentKnockBack());
            return true;
        }

        if (args.length == 2) {
            String sub = args[0].toLowerCase();
            if (!subCommands.containsKey(sub)) {
                sender.sendMessage(ChatColor.RED + this.usageMessage);
                return false;
            }

            try {
                double value = Double.parseDouble(args[1]);
                subCommands.get(sub).invoke(config, value);
                config.save();
                sender.sendMessage(ChatColor.GREEN + "KnockBack successfully updated!");
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + String.format("'%s' is not a valid double.", args[1]));
            } catch (InvocationTargetException | IllegalAccessException e) {
                sender.sendMessage(ChatColor.RED + "Failed to invoke method, contact dev");
            }
            return true;
        }

        sender.sendMessage(ChatColor.RED + this.usageMessage);
        return false;
    }

    private String currentKnockBack() {
        return ChatColor.GRAY + "Current knockback:\n" +
                ChatColor.GRAY + String.format("Horizontal: %s%.4f\n", ChatColor.RESET, config.getHorizontal()) +
                ChatColor.GRAY + String.format("Vertical: %s%.4f\n", ChatColor.RESET, config.getVertical()) +
                ChatColor.GRAY + String.format("Friction: %s%.4f\n", ChatColor.RESET, config.getFriction()) +
                ChatColor.GRAY + String.format("Horizontal Extra: %s%.4f\n", ChatColor.RESET, config.getExtraHorizontal()) +
                ChatColor.GRAY + String.format("Vertical Extra: %s%.4f\n", ChatColor.RESET, config.getExtraVertical()) +
                ChatColor.GRAY + String.format("Slowdown: %s%.4f\n", ChatColor.RESET, config.getSlowdown()) +
                ChatColor.GRAY + String.format("Vertical limit: %s%.4f\n", ChatColor.RESET, config.getVerticalLimit());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length < 2) {
            return Stream.of("view", "sethor", "setver", "setfric", "setxhor", "setxver", "setverlimit", "setslowdown")
                    .filter(sub -> args.length == 0 || sub.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toUnmodifiableList());
        }

        return ImmutableList.of();
    }
}