package org.bukkit.craftbukkit.world;

import net.minecraft.server.WorldNBTStorage;

import java.io.File;

public interface IWorldFormatProvider { // SportPaper

    IWorldFormat create(WorldNBTStorage storage, File worldDir);

}
