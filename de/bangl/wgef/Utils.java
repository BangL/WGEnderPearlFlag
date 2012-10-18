package de.bangl.wgef;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author BangL
 */
public class Utils {

    public static WGCustomFlagsPlugin getWGCustomFlags(WGEnderpearlFlagPlugin plugin) {
        Plugin wgcf = plugin.getServer().getPluginManager().getPlugin("WGCustomFlags");
        if (wgcf == null || !(wgcf instanceof WGCustomFlagsPlugin)) {
            return null;
        }
        return (WGCustomFlagsPlugin)wgcf;
    }

    public static WorldGuardPlugin getWorldGuard(WGEnderpearlFlagPlugin plugin) {
        Plugin wg = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wg == null || !(wg instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)wg;
    }

    public static void loadConfig(WGEnderpearlFlagPlugin plugin) {
        plugin.getConfig().addDefault("messages.blocked.to", "You are not allowed to use enderpearls into this region.");
        plugin.getConfig().addDefault("messages.blocked.from", "You are not allowed to use enderpearls in this region.");
        plugin.getConfig().addDefault("settings.keeppearl", true);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
}
