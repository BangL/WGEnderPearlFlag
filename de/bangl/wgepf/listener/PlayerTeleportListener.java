package de.bangl.wgepf.listener;

import com.sk89q.worldguard.protection.flags.StateFlag;
import de.bangl.wgepf.WGEnderPearlFlagPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author BangL
 */
public class PlayerTeleportListener implements Listener {
    private WGEnderPearlFlagPlugin plugin;

    // Command flags
    public static final StateFlag FLAG_ENDER_PEARLS = new StateFlag("enderpearls", true);

    public PlayerTeleportListener(WGEnderPearlFlagPlugin plugin) {
        this.plugin = plugin;

        // Register custom flags
        plugin.getWGCFP().addCustomFlag(FLAG_ENDER_PEARLS);

        // Register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {

        // Only handle if the reason was an enderpearl
        PlayerTeleportEvent.TeleportCause cause = event.getCause();
        if ((cause == null) || (cause != PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            return;
        }

        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (!plugin.getWGP().getRegionManager(from.getWorld()).getApplicableRegions(from).allows(PlayerTeleportListener.FLAG_ENDER_PEARLS)) {
            cancelEnderpearlEvent(player, this.plugin.getConfig().getString("messages.blocked.from"), event);
        } else if (!plugin.getWGP().getRegionManager(to.getWorld()).getApplicableRegions(to).allows(PlayerTeleportListener.FLAG_ENDER_PEARLS)) {
            cancelEnderpearlEvent(player, this.plugin.getConfig().getString("messages.blocked.to"), event);
        }
    }
    
    public void cancelEnderpearlEvent(Player player, String msg, PlayerTeleportEvent event) {

        // Give the pearl back to the player.
        if (player.isOnline() && this.plugin.getConfig().getBoolean("settings.keeppearl", true)) {
            player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
            player.updateInventory();
        }

        player.sendMessage(ChatColor.RED + msg);
        event.setCancelled(true);
    }
}
