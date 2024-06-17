package net.minecraft.server;

import java.io.File;

public class ServerNBTManager extends WorldNBTStorage {

    public ServerNBTManager(File file, String s, boolean flag) {
        super(file, s, flag);
    }

    public IChunkLoader createChunkLoader(WorldProvider worldprovider) { // SportPaper - allow for custom format loaders
        return format.createChunkLoader(worldprovider);
    }

    public void saveWorldData(WorldData worlddata, NBTTagCompound nbttagcompound) {
        worlddata.e(19133);
        super.saveWorldData(worlddata, nbttagcompound);
    }

    public void a() {
        try {
            FileIOThread.a().b();
        } catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }

        RegionFileCache.a();
    }
}
