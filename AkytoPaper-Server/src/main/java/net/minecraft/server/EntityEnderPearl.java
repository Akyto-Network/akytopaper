package net.minecraft.server;

// CraftBukkit start
import akyto.spigot.aSpigot;
import akyto.spigot.util.PearlUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerTeleportEvent;

// CraftBukkit end

public class EntityEnderPearl extends EntityProjectile {

    private EntityLiving c;

    public EntityEnderPearl(World world) {
        super(world);
        this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls; // PaperSpigot
    }

    public EntityEnderPearl(World world, EntityLiving entityliving) {
        super(world, entityliving);
        this.c = entityliving;
        this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls; // PaperSpigot
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        EntityLiving entityliving = this.getShooter();

        if (movingobjectposition.entity != null) {
            if (movingobjectposition.entity == this.c) {
                return;
            }

            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, entityliving), 0.0F);
        }

        // PaperSpigot start - Remove entities in unloaded chunks
        if (this.inUnloadedChunk && world.paperSpigotConfig.removeUnloadedEnderPearls) {
            this.die();
        }
        // PaperSpigot end

        for (int i = 0; i < 32; ++i) {
            this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian(), new int[0]);
        }

        if (!this.world.isClientSide) {
            if (entityliving instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entityliving;
                if (entityplayer.playerConnection.a().g() && entityplayer.world == this.world && !entityplayer.isSleeping()) {
                    // CraftBukkit start - Fire PlayerTeleportEvent
                    CraftPlayer player = entityplayer.getBukkitEntity();
                    Location location = getBukkitEntity().getLocation();
                    location.setYaw(entityplayer.yaw);
                    location.setPitch(entityplayer.pitch);
                    this.addToLocation(PearlUtils.direction(location), location, 0.85d);
                    PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    Bukkit.getPluginManager().callEvent(teleEvent);

                    if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
                        if (this.random.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
                            EntityEndermite entityendermite = new EntityEndermite(this.world);

                            entityendermite.a(true);
                            entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
                            this.world.addEntity(entityendermite);
                        }

                        if (entityliving.au()) {
                            entityliving.mount((Entity) null);
                        }
                        entityplayer.playerConnection.teleport(teleEvent.getTo());
                        aSpigot.INSTANCE.getLagCompensator().registerMovement(player, location); // Nacho
                        entityliving.fallDistance = 0.0F;
                        CraftEventFactory.entityDamage = this;
                        entityliving.damageEntity(DamageSource.FALL, 5.0F);
                        CraftEventFactory.entityDamage = null;
                    }
                    // CraftBukkit end
                }
            } else if (entityliving != null) {
                entityliving.enderTeleportTo(this.locX-0.5d, this.locY-0.5d, this.locZ-0.5d);
                entityliving.fallDistance = 0.0F;
            }

            this.die();
        }

    }

    protected void addToLocation(final String d, final Location location, final double x) {
        switch(d) {
            case "E": {
                location.setX(location.getX() + x);
                break;
            }
            case "W": {
                location.setX(location.getX() - x);
                break;
            }
            case "N": {
                location.setZ(location.getZ() - x);
                break;
            }
            case "S": {
                location.setZ(location.getZ() + x);
                break;
            }
        }
    }

    public void t_() {
        final EntityLiving entityliving = this.getShooter();
        if (entityliving != null && entityliving instanceof EntityHuman && !entityliving.isAlive()) {
            this.die();
            return;
        }
        super.t_();
    }
}
