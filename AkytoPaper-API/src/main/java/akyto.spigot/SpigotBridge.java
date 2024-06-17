package akyto.spigot;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * A hack to handle values in the api module from the server module
 */
public class SpigotBridge {

    public static String version = "Unknown";
    public static Boolean disableOpPermissions = Boolean.FALSE;
}
