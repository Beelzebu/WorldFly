package io.github.beelzebu.worldfly.command;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

/**
 * @author Beelzebu
 */
public class CommandAPI {

    public static void registerCommand(Plugin plugin, Command cmd) {
        unregisterCommand(cmd);
        getCommandMap().register(plugin.getName(), cmd);
    }

    public static void unregisterCommand(org.bukkit.command.Command cmd) {
        getKnownCommandsMap().remove(cmd.getName());
        cmd.getAliases().forEach(getKnownCommandsMap()::remove);
    }

    private static Object getPrivateField(Object object, String field) throws ReflectiveOperationException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        return objectField.get(object);
    }

    private static CommandMap getCommandMap() {
        try {
            return (CommandMap) getPrivateField(Bukkit.getPluginManager(), "commandMap");
        } catch (ReflectiveOperationException ex) {
            return new SimpleCommandMap(Bukkit.getServer());
        }
    }

    private static Map<String, org.bukkit.command.Command> getKnownCommandsMap() {
        try {
            return (Map<String, org.bukkit.command.Command>) getPrivateField(getCommandMap(), "knownCommands");
        } catch (ReflectiveOperationException ex) {
            return new HashMap<>();
        }
    }
}