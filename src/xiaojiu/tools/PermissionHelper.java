package xiaojiu.tools;


import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;
import xiaojiu.commandExecutor.MainCommand;

public class PermissionHelper {
    @Deprecated
    public static boolean isHasPermission(CommandSender commandSender, String... node) {
        String PermissionNode;
        if (!node[0].startsWith("xiaojiu")) {
            PermissionNode = GetPermissionNode("xiaojiu", node);
        } else {
            PermissionNode = GetPermissionNode("", node);
        }
//        String PermissionNode = GetPermissionNode("xiaojiu", node);
        if (commandSender.isOp() && commandSender instanceof ConsoleCommandSender) return true;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (commandSender.hasPermission(PermissionNode)) return true;
            else {
                StartPlugins.logger.info("PlayerNeedPermission");
                StartPlugins.logger.info("PlayerName : " + player.getName());
                StartPlugins.logger.info(PermissionNode);
                return false;
            }
        } else {
            StartPlugins.logger.info(commandSender.toString());
            return false;

        }
    }

    //
    public static boolean isHasPermission(CommandSender commandSender, boolean isOp, String CommandNode, String... nodes) {
        String PermissionNode;
        PermissionNode = GetPermissionNode(MainCommand.CommonNode, isOp, CommandNode, nodes);
        if (commandSender.isOp() && commandSender instanceof ConsoleCommandSender) return true;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (commandSender.hasPermission(PermissionNode)) return true;
            else {
                StartPlugins.logger.info("PlayerNeedPermission");
                StartPlugins.logger.info("PlayerName : " + player.getName());
                StartPlugins.logger.info(PermissionNode);
                return false;
            }
        } else {
            StartPlugins.logger.info(commandSender.toString());
            return false;

        }
    }

    public static boolean isHasPermissionNoLog(CommandSender commandSender, String... nodes) {
        String PermissionNode;
        if (!nodes[0].startsWith("xiaojiu")) {
            PermissionNode = GetPermissionNode("xiaojiu", nodes);
        } else {
            PermissionNode = GetPermissionNode("", nodes);
        }
//        String PermissionNode = GetPermissionNode("xiaojiu",nodes);
        if (commandSender.isOp() && commandSender instanceof ConsoleCommandSender) return true;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            return player.hasPermission(PermissionNode);
        }
        return false;
    }

    @Deprecated
    public static String GetPermissionNode(String father, String... nodes) {
        StringBuilder result;
        if (father.equalsIgnoreCase("")) {
            result = new StringBuilder();
        } else {
            result = new StringBuilder(father + ".");
        }
        for (String s : nodes) {
            result.append(s).append(".");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    public static String GetPermissionNode(String father, boolean isOP, String CommandNode, String... nodes) {
        StringBuilder result = new StringBuilder();
        result.append(father).append(".").append(isOP ? "op" : "normal").append(".").append(CommandNode);
        for (String node : nodes) {
            result.append(".").append(node);
        }
        return result.toString();
    }
}
