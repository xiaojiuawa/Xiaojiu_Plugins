package xiaojiu.tools;


import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xiaojiu.StartPlugins;

public class PermissionHelper {
    public static boolean isHasPermission(CommandSender commandSender, String... node) {
        String PermissionNode = GetPermissionNode("xiaojiu", node);
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
    public static boolean isHasPermissionNoLog(CommandSender commandSender,String... nodes){
        String PermissionNode = GetPermissionNode("xiaojiu",nodes);
        if (commandSender.isOp()&&commandSender instanceof ConsoleCommandSender) return true;
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            return player.hasPermission(PermissionNode);
        }
        return false;
    }

    public static String GetPermissionNode(String father, String... nodes) {
        StringBuilder result = new StringBuilder(father + ".");
        for (String s : nodes) {
            result.append(s).append(".");
        }
        result.delete(result.length()-1,result.length());
    return result.toString();
    }
}
