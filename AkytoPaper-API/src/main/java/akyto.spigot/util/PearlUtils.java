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

    private static boolean blockRisk(final Block block) {
        if (block.getType().isSolid()) {
            return true;
        }
        if (!block.isEmpty()) {
            return true;
        }
        return block.getType().toString().contains("GLASS");
    }

    public static boolean risky(final Location loc) {
        Location top = new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ());
        if (blockRisk(top.getBlock())) {
            return true;
        }
        switch (direction(loc)){
            case "N": {
                Location forward = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
                Location back = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
                Location left = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
                Location right = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
            case "NW": {
                Location forward = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()-1);
                Location back = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()+1);
                Location left = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()+1);
                Location right = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()-1);
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
            case "W": {
                Location forward = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
                Location back = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
                Location left = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
                Location right = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
            case "SW": {
                Location forward = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()+1);
                Location back = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()-1);
                Location left = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()+1);
                Location right = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()-1);
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
            case "S": {
                Location forward = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
                Location back = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
                Location left = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
                Location right = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
            case "SE": {
                Location forward = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()+1);
                Location back = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()-1);
                Location left = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()-1);
                Location right = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()+1);
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
            case "E": {
                Location forward = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ());
                Location back = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ());
                Location left = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1);
                Location right = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1);
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
            case "NE": {
                Location forward = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()-1);
                Location back = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()+1);
                Location left = new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ()-1);
                Location right = new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ()+1);
                if (blockRisk(left.getBlock()) || blockRisk(right.getBlock()) || blockRisk(forward.getBlock()) || blockRisk(back.getBlock())){
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
