package akyto.spigot;

import akyto.spigot.command.FlyCommand;
import akyto.spigot.command.KbCommand;
import akyto.spigot.command.PingCommand;
import akyto.spigot.handler.MovementHandler;
import akyto.spigot.handler.PacketHandler;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.Command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public enum aSpigot {

	INSTANCE;

    @Setter
    private aSpigotConfig config;
    private final Set<PacketHandler> packetHandlers = new HashSet<>();
    private final Set<MovementHandler> movementHandlers = new HashSet<>();

    public void addPacketHandler(PacketHandler handler) {
		this.packetHandlers.add(handler);
	}

	public void addMovementHandler(MovementHandler handler) {
		this.movementHandlers.add(handler);
	}

	public void registerCommands() {
		Map<String, Command> commands = new HashMap<>();

		commands.put("ping", new PingCommand());
		commands.put("fly", new FlyCommand());
		commands.put("kb", new KbCommand());
//		commands.put("misplace", new MisplaceCommand());

		for (Map.Entry<String, Command> entry : commands.entrySet()) {
			MinecraftServer.getServer().server.getCommandMap().register(entry.getKey(), "Spigot", entry.getValue());
		}
	}

}
