package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityLightning;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;

public class CraftLightningStrike extends CraftEntity implements LightningStrike {
    public CraftLightningStrike(final CraftServer server, final EntityLightning entity) {
        super(server, entity);
    }

    public boolean isEffect() {
        return ((EntityLightning) super.getHandle()).isEffect;
    }

    @Override
    public void setSilent(boolean bool) {
        getHandle().isSilent = bool;
    }

    @Override
    public EntityLightning getHandle() {
        return (EntityLightning) entity;
    }

    @Override
    public String toString() {
        return "CraftLightningStrike";
    }

    public EntityType getType() {
        return EntityType.LIGHTNING;
    }

    // Spigot start
    private final LightningStrike.Spigot spigot = new LightningStrike.Spigot() {
        
        @Override
        public boolean isSilent()
        {
            return getHandle().isSilent;
        }
    };
    
    @Override
    public LightningStrike.Spigot spigot() {
        return spigot;
    }
    // Spigot end
}
