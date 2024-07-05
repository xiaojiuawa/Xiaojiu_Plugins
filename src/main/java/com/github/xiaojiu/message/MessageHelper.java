package com.github.xiaojiu.message;

import com.github.xiaojiu.Xiaojiu;

public class MessageHelper {

    /**
     * 使用完全节点获取语言
     * @param node 完全节点
     * @return 语言
     */
    public static String getMessage(String node) {
        return Xiaojiu.getMessage().getMessage(node);
    }

    /**
     * 使用完全节点获取语言(会自动补全message.)
     * 命令节点直接需要.分割，不能尾随.
     * @param node 命令节点
     * @return 语言
     */
    public static String getMessageCompletion(String node) {
        if (!node.startsWith("message")) {
            node = "message." + node;
        }
        return getMessage(node);
    }

    /**
     * 使用单个命令节点获取语言,作为一串参数传入,之间不需要尾随.
     * @deprecated
     * @param nodes 节点串
     * @return 语言
     */
    @Deprecated
    public static String getMessageCompletion(String... nodes) {
        StringBuilder builder = new StringBuilder();
        if (!nodes[0].equalsIgnoreCase("message")) {
            builder.append("message.");
        }
        for (String node : nodes) {
            builder.append(node);
            builder.append(".");
        }
        builder.deleteCharAt(builder.length()-1);
        return getMessage(builder.toString());
    }
}
