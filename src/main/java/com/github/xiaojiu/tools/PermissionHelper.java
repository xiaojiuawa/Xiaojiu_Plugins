package com.github.xiaojiu.tools;


import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.commandExecutor.MainCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PermissionHelper {
    /**
     * 通过节点字符串判断某个用户是否拥有权限
     * @param commandSender 用户
     * @param node 权限节点子节点
     * @return 是否拥有相应权限
     * @deprecated
     */
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
                Xiaojiu.logger.info("PlayerNeedPermission");
                Xiaojiu.logger.info("PlayerName : " + player.getName());
                Xiaojiu.logger.info(PermissionNode);
                return false;
            }
        } else {
            Xiaojiu.logger.info(commandSender.toString());
            return false;

        }
    }

    /**
     * 通过节点信息判断某个用户是否拥有某项权限节点
     * 此方法会在日志中输出某用户缺少的权限节点
     * @param commandSender 用户
     * @param isOp 节点是否为op节点
     * @param CommandNode 主命令节点
     * @param nodes 子节点
     * @return 是否有权限
     */
    public static boolean isHasPermission(CommandSender commandSender, boolean isOp, String CommandNode, String... nodes) {
        String PermissionNode;
        PermissionNode = GetPermissionNode(MainCommand.CommonNode, isOp, CommandNode, nodes);
        if (commandSender.isOp() && commandSender instanceof ConsoleCommandSender) return true;
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (commandSender.hasPermission(PermissionNode)) return true;
            else {
                Xiaojiu.logger.info("PlayerNeedPermission");
                Xiaojiu.logger.info("PlayerName : " + player.getName());
                Xiaojiu.logger.info(PermissionNode);
                return false;
            }
        } else {
            Xiaojiu.logger.info(commandSender.toString());
            return false;

        }
    }

    /**
     *
     * @param commandSender
     * @param nodes
     * @return
     */
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
