package net.minecraft.server;

// CraftBukkit start
import akyto.spigot.aSpigot;
import akyto.spigot.util.PearlUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.github.paperspigot.PaperSpigotWorldConfig;

// CraftBukkit end

public class EntityEnderPearl extends EntityProjectile {

    private EntityLiving c;
    private Location lastValidLocation;

    public EntityEnderPearl(World world) {
        super(world);
        this.loadChunks = PaperSpigotWorldConfig.loadUnloadedEnderPearls; // PaperSpigot
    }

    public EntityEnderPearl(World world, EntityLiving entityLiving) {
        super(world, entityLiving);
        this.c = entityLiving;
        this.loadChunks = PaperSpigotWorldConfig.loadUnloadedEnderPearls; // PaperSpigot
        if (aSpigot.INSTANCE.getConfig().isAntiglitchPearl()) this.lastValidLocation = entityLiving.getBukkitEntity().getLocation(); // nPaper - antipearl glitch - fix nullpointer
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
        if (this.inUnloadedChunk && PaperSpigotWorldConfig.removeUnloadedEnderPearls) {
            this.die();
        }
        // PaperSpigot end

        if (!this.world.isClientSide) {
            if (entityliving instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entityliving;
                if (entityplayer.playerConnection.a().g() && entityplayer.world == this.world && !entityplayer.isSleeping()) {
                    // CraftBukkit start - Fire PlayerTeleportEvent
                    CraftPlayer player = entityplayer.getBukkitEntity();
                    Location location = getBukkitEntity().getLocation();
                    location.setYaw(entityplayer.yaw);
                    location.setPitch(entityplayer.pitch);
                    if (aSpigot.INSTANCE.getConfig().isAntiglitchPearl()){
                        this.addToLocation(PearlUtils.direction(location), location, 0.85d);
                        if (PearlUtils.risky(location)) {
                            location = lastValidLocation.clone();
                            location.setYaw(entityplayer.yaw);
                            location.setPitch(entityplayer.pitch);
                        }
                    }
                    for (int i = 0; i < 32; ++i) {
                        this.world.addParticle(EnumParticle.PORTAL, location.getX(), location.getY() + this.random.nextDouble() * 2.0D, location.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian(), new int[0]);
                    }
                    PlayerTeleportEvent tpEvent = new PlayerTeleportEvent(player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    Bukkit.getPluginManager().callEvent(tpEvent);

                    if (!tpEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
                        if (this.random.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
                            EntityEndermite entityendermite = new EntityEndermite(this.world);

                            entityendermite.a(true);
                            entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
                            this.world.addEntity(entityendermite);
                        }

                        if (entityliving.au()) {
                            entityliving.mount((Entity) null);
                        }
                        entityplayer.playerConnection.teleport(tpEvent.getTo());
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
            case "SE": {
                location.setZ(location.getZ() + x);
                location.setX(location.getX() + x);
                break;
            }
            case "E": {
                location.setX(location.getX() + x);
                break;
            }
            case "NE": {
                location.setZ(location.getZ() - x);
                location.setX(location.getX() + x);
                break;
            }
            case "SW": {
                location.setZ(location.getZ() + x);
                location.setX(location.getX() - x);
                break;
            }
            case "W": {
                location.setX(location.getX() - x);
                break;
            }
            case "NW": {
                location.setZ(location.getZ() - x);
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

    protected void removeToLocation(final String d, final Location location, final double x) {
        switch(d) {
            case "SE": {
                location.setX(location.getX() - x);
                location.setZ(location.getZ() + x);
            }
            case "E": {
                location.setX(location.getX() - x);
                break;
            }
            case "NE": {
                location.setX(location.getX() + x);
                location.setZ(location.getZ() + x);
            }
            case "SW": {
                location.setX(location.getX() + x);
                location.setZ(location.getZ() + x);
            }
            case "W": {
                location.setX(location.getX() + x);
                break;
            }
            case "NW": {
                location.setX(location.getX() - x);
                location.setZ(location.getZ() + x);
            }
            case "N": {
                location.setZ(location.getZ() + x);
                break;
            }
            case "S": {
                location.setZ(location.getZ() - x);
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
        if (aSpigot.INSTANCE.getConfig().isAntiglitchPearl() && this.world.getCubes(this, this.getBoundingBox().grow(0.225D, 0.1D, 0.225D)).isEmpty()) {
            this.lastValidLocation = getBukkitEntity().getLocation();
        }
        super.t_();
    }
}
