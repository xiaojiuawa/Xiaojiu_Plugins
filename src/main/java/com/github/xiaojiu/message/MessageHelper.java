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
     *
     * @param node
     * @return
     */
    public static String getMessageCompletion(String node) {
        if (!node.startsWith("message")) {
            node = "message." + node;
        }
        return getMessage(node);
    }

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
        builder.delete(builder.length() - 1, builder.length());
        return getMessage(builder.toString());
    }
}
