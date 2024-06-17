package net.minecraft.server;

import org.bukkit.event.entity.PotionEffectAddEvent.EffectAddReason;

public class ItemGoldenApple extends ItemFood {

    public ItemGoldenApple(int i, float f, boolean flag) {
        super(i, f, flag);
        this.a(true);
    }

    public EnumItemRarity g(ItemStack itemstack) {
        return itemstack.getData() == 0 ? EnumItemRarity.RARE : EnumItemRarity.EPIC;
    }

    protected void c(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if (!world.isClientSide) {
            entityhuman.addEffect(new MobEffect(MobEffectList.ABSORBTION.id, 2400, 0), EffectAddReason.GOLDEN_APPLE);
        }

        if (itemstack.getData() > 0) {
            if (!world.isClientSide) {
                entityhuman.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 600, 4), EffectAddReason.GOLDEN_APPLE);
                entityhuman.addEffect(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0), EffectAddReason.GOLDEN_APPLE);
                entityhuman.addEffect(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0), EffectAddReason.GOLDEN_APPLE);
            }
        } else {
            super.c(itemstack, world, entityhuman);
        }

    }
}
