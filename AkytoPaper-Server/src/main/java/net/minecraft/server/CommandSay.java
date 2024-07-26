package net.minecraft.server;

import java.util.List;

public class CommandSay extends CommandAbstract {
    public CommandSay() {
    }

    public String getCommand() {
        return "say";
    }

    public int a() {
        return 1;
    }

    public String getUsage(ICommandListener var1) {
        return "commands.say.usage";
    }

    public void execute(ICommandListener var1, String[] var2) throws CommandException {
        throw new CommandException("commands.generic.permission", new Object[0]);
    }

    public List<String> tabComplete(ICommandListener var1, String[] var2, BlockPosition var3) {
        return var2.length >= 1 ? a(var2, MinecraftServer.getServer().getPlayers()) : null;
    }

    @Override
    public int compareTo(ICommand o) {
        return this.a(o);
    }
}
