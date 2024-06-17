package org.bukkit.craftbukkit.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.WorldNBTStorage;

public class CraftFormatManager { // SportPaper

    private static List<IWorldFormatProvider> providers = new ArrayList<>();

    public static IWorldFormat findFormat(WorldNBTStorage worldStorage, File worldDir) {
        for (IWorldFormatProvider provider : providers) {
            IWorldFormat result = provider.create(worldStorage, worldDir);
            if (result != null)
                return result;
        }

        return new AnvilFormatLoader(worldStorage, worldDir);
    }

    public static void addFormatProvider(IWorldFormatProvider provider) {
        providers.add(provider);
    }

}
