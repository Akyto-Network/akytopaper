package akyto.spigot.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Openable;

public class PearlUtils {

    public static String direction(final Location l) {
        double d = (l.getYaw() - 90) % 360;
        if (d < 0)
            d += 360;
        if (0 <= d && d < 22.5)
            return "W";
        else if (22.5 <= d && d < 67.5)
            return "NW";
        else if (67.5 <= d && d < 112.5)
            return "N";
        else if (112.5 <= d && d < 157.5)
            return "NE";
        else if (157.5 <= d && d < 202.5)
            return "E";
        else if (202.5 <= d && d < 247.5)
            return "SE";
        else if (247.5 <= d && d < 292.5)
            return "S";
        else if (292.5 <= d && d < 337.5)
            return "SW";
        else if (337.5 <= d && d < 360)
            return "W";
        else return null;
    }

    public static boolean risky(final Location loc) {
        switch (direction(loc)){
            case "N": {
                Location left = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
                Location right = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
            case "W": {
                Location left = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
                Location right = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
            case "NW": {
                Location left = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()-1);
                Location right = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()-1);
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
            case "S": {
                Location left = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
                Location right = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
            case "SW": {
                Location left = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()+1);
                Location right = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()-1);
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
            case "E": {
                Location left = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
                Location right = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
            case "NE": {
                Location left = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()-1);
                Location right = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()+1);
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
            case "SE": {
                Location left = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()+1);
                Location right = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()+1);
                if (left.getBlock().getType().toString().contains("GLASS") && right.getBlock().getType().toString().contains("GLASS")){
                    return true;
                }
            }
        }
        return false;
    }
}
