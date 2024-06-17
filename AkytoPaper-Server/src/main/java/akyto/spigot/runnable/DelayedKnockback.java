package akyto.spigot.runnable;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.PacketPlayOutEntityVelocity;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DelayedKnockback implements Runnable {

    private long delay;
    private EntityPlayer victim;
    private double x;
    private double y;
    private double z;

    public DelayedKnockback(long delay, EntityPlayer victim, double x, double y, double z) {
        this.delay = delay;
        this.victim = victim;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void run() {
        PlayerVelocityEvent event = new PlayerVelocityEvent(victim.getBukkitEntity(), new Vector(x, y, z));
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            victim.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(victim.getId(), x, y, z));
        }
        victim.velocityChanged = false;
        victim.motX = x;
        victim.motY = y;
        victim.motZ = z;
    }

    public void runTaskLaterAsync() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.setRemoveOnCancelPolicy(true);
        executor.schedule(this, delay, TimeUnit.MILLISECONDS);
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
