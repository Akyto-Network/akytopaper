package net.minecraft.server;

import akyto.spigot.aSpigot;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static akyto.spigot.aSpigot.lastClickTimes;

public class PacketPlayInUseEntity implements Packet<PacketListenerPlayIn> {

    private int a;
    public int getEntityId() { return this.a; } // Paper - add accessor
    private PacketPlayInUseEntity.EnumEntityUseAction action;
    private Vec3D c;

    private static final int MAX_CLICKS_PER_SECOND = aSpigot.INSTANCE.getConfig().getCpsMax();
    private static final long MIN_CLICK_INTERVAL = 1000 / MAX_CLICKS_PER_SECOND;

    public PacketPlayInUseEntity() {}

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.e();
        this.action = (PacketPlayInUseEntity.EnumEntityUseAction) packetdataserializer.a(PacketPlayInUseEntity.EnumEntityUseAction.class);
        if (this.action == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
            this.c = new Vec3D((double) packetdataserializer.readFloat(), (double) packetdataserializer.readFloat(), (double) packetdataserializer.readFloat());
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.b(this.a);
        packetdataserializer.a((Enum) this.action);
        if (this.action == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
            packetdataserializer.writeFloat((float) this.c.a);
            packetdataserializer.writeFloat((float) this.c.b);
            packetdataserializer.writeFloat((float) this.c.c);
        }

    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        if (this.action == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && aSpigot.INSTANCE.getConfig().isCpsCapPacketEnabled()) {
            PlayerConnection playerConnection = (PlayerConnection) packetlistenerplayin;
            EntityPlayer player = playerConnection.player;
            UUID playerId = player.getUniqueID();
            long currentTime = System.currentTimeMillis();
            handleAttack(playerId, currentTime, packetlistenerplayin);
        }
        else {
            packetlistenerplayin.a(this);
        }
    }

    private void handleAttack(UUID playerId, long currentTime, PacketListenerPlayIn packetlistenerplayin) {
        if (!(packetlistenerplayin instanceof PlayerConnection)) {
            return;
        }
        PlayerConnection playerConnection = (PlayerConnection) packetlistenerplayin;
        if (playerConnection.player == null || playerConnection.player.dead) {
            return;
        }
        PlayerConnectionUtils.ensureMainThread(this, playerConnection, playerConnection.player.u());
        long lastProcessedTime = aSpigot.lastProcessedClickTimes.getOrDefault(playerId, 0L);
        long lastClickTime = aSpigot.lastClickTimes.getOrDefault(playerId, 0L);
        System.out.println((currentTime - lastProcessedTime >= MIN_CLICK_INTERVAL ? "packet sended" : "packet not sended") + " for " + Bukkit.getPlayer(playerId).getName());
        if (currentTime - lastProcessedTime >= MIN_CLICK_INTERVAL) {
            aSpigot.lastProcessedClickTimes.put(playerId, currentTime);
            packetlistenerplayin.a(this);
        }
        lastClickTimes.put(playerId, currentTime);
        int clickCount = aSpigot.clickCounts.getOrDefault(playerId, 0);
        if (currentTime - lastClickTime < 1000) {
            clickCount++;
        } else {
            clickCount = 0;
        }
        aSpigot.clickCounts.put(playerId, clickCount);
    }

    public Entity a(World world) {
        return world.a(this.a);
    }

    public PacketPlayInUseEntity.EnumEntityUseAction a() {
        return this.action;
    }

    public Vec3D b() {
        return this.c;
    }

    public static enum EnumEntityUseAction {

        INTERACT, ATTACK, INTERACT_AT;

        private EnumEntityUseAction() {}
    }
}
