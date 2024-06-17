package org.bukkit.craftbukkit.world;

import java.io.File;

import net.minecraft.server.WorldNBTStorage;

public interface IWorldFormatProvider { // SportPaper

    IWorldFormat create(WorldNBTStorage storage, File worldDir);

}
