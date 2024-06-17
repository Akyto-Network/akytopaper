package org.spigotmc;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.github.paperspigot.SharedConfig;

public class SpigotWorldConfig
{
    private static YamlConfiguration config;
    private static boolean verbose;

    public static void init()
    {
        config = SharedConfig.config;
        verbose = getBoolean( "verbose", true );

        SharedConfig.readConfig( SpigotWorldConfig.class, null );
    }

    private static void log(String s)
    {
        if ( verbose )
        {
            Bukkit.getLogger().info( s );
        }
    }

    private static boolean getBoolean(String path, boolean def)
    {
        config.addDefault( "spigot.world-settings." + path, def );
        return config.getBoolean( "spigot.world-settings." + path, config.getBoolean( "spigot.world-settings." + path ) );
    }

    private static double getDouble(String path, double def)
    {
        config.addDefault( "spigot.world-settings." + path, def );
        return config.getDouble( "spigot.world-settings." + path, config.getDouble( "spigot.world-settings." + path ) );
    }

    private static int getInt(String path, int def)
    {
        config.addDefault( "spigot.world-settings." + path, def );
        return config.getInt( "spigot.world-settings." + path, config.getInt( "spigot.world-settings." + path ) );
    }

    private static  <T> List getList(String path, T def)
    {
        config.addDefault( "spigot.world-settings." + path, def );
        return (List<T>) config.getList( "spigot.world-settings." + path, config.getList( "spigot.world-settings." + path ) );
    }

    private static String getString(String path, String def)
    {
        config.addDefault( "spigot.world-settings." + path, def );
        return config.getString( "spigot.world-settings." + path, config.getString( "spigot.world-settings." + path ) );
    }

    public static int chunksPerTick;
    public static boolean clearChunksOnTick;
    private static void chunksPerTick()
    {
        chunksPerTick = getInt( "chunks-per-tick", 650 );
        log( "Chunks to Grow per Tick: " + chunksPerTick );

        clearChunksOnTick = getBoolean( "clear-tick-list", false );
        log( "Clear tick list: " + clearChunksOnTick );
    }

    // Crop growth rates
    public static int cactusModifier;
    public static int caneModifier;
    public static int melonModifier;
    public static int mushroomModifier;
    public static int pumpkinModifier;
    public static int saplingModifier;
    public static int wheatModifier;
    public static int wartModifier;
    private static int getAndValidateGrowth(String crop)
    {
        int modifier = getInt( "growth." + crop.toLowerCase() + "-modifier", 100 );
        if ( modifier == 0 )
        {
            log( "Cannot set " + crop + " growth to zero, defaulting to 100" );
            modifier = 100;
        }
        log( crop + " Growth Modifier: " + modifier + "%" );

        return modifier;
    }
    private static void growthModifiers()
    {
        cactusModifier = getAndValidateGrowth( "Cactus" );
        caneModifier = getAndValidateGrowth( "Cane" );
        melonModifier = getAndValidateGrowth( "Melon" );
        mushroomModifier = getAndValidateGrowth( "Mushroom" );
        pumpkinModifier = getAndValidateGrowth( "Pumpkin" );
        saplingModifier = getAndValidateGrowth( "Sapling" );
        wheatModifier = getAndValidateGrowth( "Wheat" );
        wartModifier = getAndValidateGrowth( "NetherWart" );
    }

    public static double itemMerge;
    private static void itemMerge()
    {
        itemMerge = getDouble("merge-radius.item", 2.5 );
        log( "Item Merge Radius: " + itemMerge );
    }

    public static double expMerge;
    private static void expMerge()
    {
        expMerge = getDouble("merge-radius.exp", 3.0 );
        log( "Experience Merge Radius: " + expMerge );
    }

    public static int viewDistance;
    private static void viewDistance()
    {
        viewDistance = getInt( "view-distance", Bukkit.getViewDistance() );
        log( "View Distance: " + viewDistance );
    }

    public static byte mobSpawnRange;
    private static void mobSpawnRange()
    {
        mobSpawnRange = (byte) getInt( "mob-spawn-range", 4 );
        log( "Mob Spawn Range: " + mobSpawnRange );
    }

    public static int animalActivationRange = 32;
    public static int monsterActivationRange = 32;
    public static int miscActivationRange = 16;
    private static void activationRange()
    {
        animalActivationRange = getInt( "entity-activation-range.animals", animalActivationRange );
        monsterActivationRange = getInt( "entity-activation-range.monsters", monsterActivationRange );
        miscActivationRange = getInt( "entity-activation-range.misc", miscActivationRange );
        log( "Entity Activation Range: An " + animalActivationRange + " / Mo " + monsterActivationRange + " / Mi " + miscActivationRange );
    }

    public static int playerTrackingRange = 48;
    public static int animalTrackingRange = 48;
    public static int monsterTrackingRange = 48;
    public static int miscTrackingRange = 32;
    public static int otherTrackingRange = 64;
    private static void trackingRange()
    {
        playerTrackingRange = getInt( "entity-tracking-range.players", playerTrackingRange );
        animalTrackingRange = getInt( "entity-tracking-range.animals", animalTrackingRange );
        monsterTrackingRange = getInt( "entity-tracking-range.monsters", monsterTrackingRange );
        miscTrackingRange = getInt( "entity-tracking-range.misc", miscTrackingRange );
        otherTrackingRange = getInt( "entity-tracking-range.other", otherTrackingRange );
        log( "Entity Tracking Range: Pl " + playerTrackingRange + " / An " + animalTrackingRange + " / Mo " + monsterTrackingRange + " / Mi " + miscTrackingRange + " / Other " + otherTrackingRange );
    }

    public static int hopperTransfer;
    public static int hopperCheck;
    public static int hopperAmount;
    private static void hoppers()
    {
        // Set the tick delay between hopper item movements
        hopperTransfer = getInt( "ticks-per.hopper-transfer", 8 );
        // Set the tick delay between checking for items after the associated
        // container is empty. Default to the hopperTransfer value to prevent
        // hopper sorting machines from becoming out of sync.
        hopperCheck = getInt( "ticks-per.hopper-check", hopperTransfer );
        hopperAmount = getInt( "hopper-amount", 1 );
        log( "Hopper Transfer: " + hopperTransfer + " Hopper Check: " + hopperCheck + " Hopper Amount: " + hopperAmount );
    }

    public static boolean randomLightUpdates;
    private static void lightUpdates()
    {
        randomLightUpdates = getBoolean( "random-light-updates", false );
        log( "Random Lighting Updates: " + randomLightUpdates );
    }

    public static boolean saveStructureInfo;
    private static void structureInfo()
    {
        saveStructureInfo = getBoolean( "save-structure-info", true );
        log( "Structure Info Saving: " + saveStructureInfo );
        if ( !saveStructureInfo )
        {
            log( "*** WARNING *** You have selected to NOT save structure info. This may cause structures such as fortresses to not spawn mobs!" );
            log( "*** WARNING *** Please use this option with caution, SpigotMC is not responsible for any issues this option may cause in the future!" );
        }
    }

    public static int itemDespawnRate;
    private static void itemDespawnRate()
    {
        itemDespawnRate = getInt( "item-despawn-rate", 6000 );
        log( "Item Despawn Rate: " + itemDespawnRate );
    }

    public static int arrowDespawnRate;
    private static void arrowDespawnRate()
    {
        arrowDespawnRate = getInt( "arrow-despawn-rate", 1200  );
        log( "Arrow Despawn Rate: " + arrowDespawnRate );
    }
    
    public static boolean antiXray;
    public static int engineMode;
    public static List<Integer> hiddenBlocks;
    public static List<Integer> replaceBlocks;
    public static AntiXray antiXrayInstance;
    private static void antiXray()
    {
        antiXray = getBoolean( "anti-xray.enabled", true );
        log( "Anti X-Ray: " + antiXray );

        engineMode = getInt( "anti-xray.engine-mode", 1 );
        log( "\tEngine Mode: " + engineMode );

        hiddenBlocks = getList( "anti-xray.hide-blocks", Arrays.asList( new Integer[]
        {
            14, 15, 16, 21, 48, 49, 54, 56, 73, 74, 82, 129, 130
        } ) );
        log( "\tHidden Blocks: " + hiddenBlocks );

        replaceBlocks = getList( "anti-xray.replace-blocks", Arrays.asList( new Integer[]
        {
            1, 5
        } ) );
        log( "\tReplace Blocks: " + replaceBlocks );

        antiXrayInstance = new AntiXray( new SpigotWorldConfig() );
    }

    public static boolean zombieAggressiveTowardsVillager;
    private static void zombieAggressiveTowardsVillager()
    {
        zombieAggressiveTowardsVillager = getBoolean( "zombie-aggressive-towards-villager", true );
        log( "Zombie Aggressive Towards Villager: " + zombieAggressiveTowardsVillager );
    }

    public static boolean nerfSpawnerMobs;
    private static void nerfSpawnerMobs()
    {
        nerfSpawnerMobs = getBoolean( "nerf-spawner-mobs", false );
        log( "Nerfing mobs spawned from spawners: " + nerfSpawnerMobs );
    }

    public static boolean enableZombiePigmenPortalSpawns;
    private static void enableZombiePigmenPortalSpawns()
    {
        enableZombiePigmenPortalSpawns = getBoolean( "enable-zombie-pigmen-portal-spawns", true );
        log( "Allow Zombie Pigmen to spawn from portal blocks: " + enableZombiePigmenPortalSpawns );
    }

    public static int maxBulkChunk;
    private static void bulkChunkCount()
    {
        maxBulkChunk = getInt( "max-bulk-chunks", 10 );
        log( "Sending up to " + maxBulkChunk + " chunks per packet" );
    }

    public static int maxCollisionsPerEntity;
    private static void maxEntityCollision()
    {
        maxCollisionsPerEntity = getInt( "max-entity-collisions", 8 );
        log( "Max Entity Collisions: " + maxCollisionsPerEntity );
    }

    public static int dragonDeathSoundRadius;
    private static void keepDragonDeathPerWorld()
    {
        dragonDeathSoundRadius = getInt( "dragon-death-sound-radius", 0 );
    }

    public static int witherSpawnSoundRadius;
    private static void witherSpawnSoundRadius()
    {
        witherSpawnSoundRadius = getInt( "wither-spawn-sound-radius", 0 );
    }

    public static int villageSeed;
    public static int largeFeatureSeed;
    private static void initWorldGenSeeds()
    {
        villageSeed = getInt( "seed-village", 10387312 );
        largeFeatureSeed = getInt( "seed-feature", 14357617 );
        log( "Custom Map Seeds:  Village: " + villageSeed + " Feature: " + largeFeatureSeed );
    }

    public static float walkExhaustion;
    public static float sprintExhaustion;
    public static float combatExhaustion;
    public static float regenExhaustion;
    private static void initHunger()
    {
        walkExhaustion = (float) getDouble( "hunger.walk-exhaustion", 0.2 );
        sprintExhaustion = (float) getDouble( "hunger.sprint-exhaustion", 0.8 );
        combatExhaustion = (float) getDouble( "hunger.combat-exhaustion", 0.3 );
        regenExhaustion = (float) getDouble( "hunger.regen-exhaustion", 3 );
    }

    public static int currentPrimedTnt = 0;
    public static int maxTntTicksPerTick;
    private static void maxTntPerTick() {
        maxTntTicksPerTick = getInt( "max-tnt-per-tick", 100 );
        log( "Max TNT Explosions: " + maxTntTicksPerTick );
    }

    public static int hangingTickFrequency;
    private static void hangingTickFrequency()
    {
        hangingTickFrequency = getInt( "hanging-tick-frequency", 100 );
    }

    public static int tileMaxTickTime;
    public static int entityMaxTickTime;
    private static void maxTickTimes()
    {
        tileMaxTickTime = getInt("max-tick-time.tile", 50);
        entityMaxTickTime = getInt("max-tick-time.entity", 50);
        log("Tile Max Tick Time: " + tileMaxTickTime + "ms Entity max Tick Time: " + entityMaxTickTime + "ms");
    }
}
