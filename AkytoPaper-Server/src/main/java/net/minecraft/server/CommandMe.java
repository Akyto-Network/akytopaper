package net.minecraft.server;

import java.util.List;

public class CommandMe extends CommandAbstract {
    public CommandMe() {
    }

    public String getCommand() {
        return "me";
    }

    public int a() {
        return 0;
    }

    public String getUsage(ICommandListener var1) {
        return "commands.me.usage";
    }

    public void execute(ICommandListener var1, String[] var2) throws CommandException {
        throw new CommandException("commands.generic.permission", new Object[0]);
    }

    public List<String> tabComplete(ICommandListener var1, String[] var2, BlockPosition var3) {
        return a(var2, MinecraftServer.getServer().getPlayers());
    }

    @Override
    public int compareTo(ICommand o) {
        return this.a(o);
    }
}
