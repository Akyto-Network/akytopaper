package akyto.spigot;

import akyto.spigot.handler.MovementHandler;
import akyto.spigot.handler.PacketHandler;
import akyto.spigot.hitdetection.LagCompensator;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.Entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum aSpigot {

	INSTANCE;

    @Setter
    private aSpigotConfig config;
    private final Set<PacketHandler> packetHandlers = new HashSet<>();
    private final Set<MovementHandler> movementHandlers = new HashSet<>();
	private final LagCompensator lagCompensator = new LagCompensator();

	public void addPacketHandler(PacketHandler handler) {
		this.packetHandlers.add(handler);
	}

	public void addMovementHandler(MovementHandler handler) {
		this.movementHandlers.add(handler);
	}

}
