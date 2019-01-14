package io.github.beelzebu.worldfly.listener;

import io.github.beelzebu.worldfly.WorldFly;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

/**
 * @author Beelzebu
 */
@RequiredArgsConstructor
public class FlyListener implements Listener {

    private final WorldFly plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (player.isFlying() && plugin.getConfig().getStringList("disabled-worlds").contains(world.getName()) && (plugin.getFlyCommand() != null && !player.hasPermission(plugin.getFlyCommand().getPermission() + ".bypass-disabled"))) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(WorldFly.replace(plugin.getConfig().getString("messages.fly-disabled-world"), player));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFlyChange(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (e.isFlying() && plugin.getConfig().getStringList("disabled-worlds").contains(world.getName()) && (plugin.getFlyCommand() != null && !player.hasPermission(plugin.getFlyCommand().getPermission() + ".bypass-disabled"))) {
            e.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(WorldFly.replace(plugin.getConfig().getString("messages.fly-disabled-world"), player));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (plugin.getFlyCommand() == null) {
            return;
        }
        if (!plugin.getConfig().getBoolean("fly-on-login")) {
            return;
        }
        Player player = e.getPlayer();
        if (player.hasPermission(plugin.getFlyCommand().getPermission())) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(WorldFly.replace(plugin.getConfig().getString("messages.fly-on-login"), player));
        }
    }
}
