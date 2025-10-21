package com.github.xiaojiu.commandExecutor;

import com.github.xiaojiu.Handles.Help.HelpMapHandler;
import com.github.xiaojiu.Handles.Save.*;
import com.github.xiaojiu.Xiaojiu;
import com.github.xiaojiu.api.HelpMap;
import com.github.xiaojiu.api.XiaojiuCommandExecutor;
import com.github.xiaojiu.exceptions.parametersExceptions;
import com.github.xiaojiu.message.MessageHelper;
import com.github.xiaojiu.tools.PostHelper;
import com.github.xiaojiu.tools.Utils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class SaveCommand implements XiaojiuCommandExecutor {

    public static final Map<String, HelpMap> SaveMap = new HashMap<>();

    public static final String PermissionNode = "save";

    public static final String CommandNode = "save";

    @Override
    public void InitMap() {
        SaveMap.put("config", new HelpMapHandler(CommandNode, "/xj save new config [true/false]{是否异步}", "xiaojiu.op.save.config", "通过这个来立即保存所有可以保存的配置文件"));
        SaveMap.put("player", new HelpMapHandler(CommandNode, "/xj save new player [true/false]{是否异步}", "xiaojiu.op.save.player", "通过这个指令来立刻存储所有玩家或某个的文件"));
        SaveMap.put("world", new HelpMapHandler(CommandNode, "/xj save new world [true/false]{是否异步}", "xiaojiu.op.save.world", "通过这个指令来保存所有世界或是某个世界"));
        SaveMap.put("task", new HelpMapHandler(CommandNode, "/xj save new taskname [true/false]{是否异步} [true/false](是否为临时任务) [时间数值]{延迟时间} [时间数值]{执行时间间隔} ", "xiaojiu.op.save.task", "通过这个指令来对某个保存指令创建一个自动任务(所有新建命令遵从这个格式)"));
    }

    @Override
    public Map<String, HelpMap> GetHelpMap() {
        return SaveMap;
    }

    @Override
    public String GetPermissionNode() {
        return PermissionNode;
    }

    @Override
    public String GetCommandNode() {
        return CommandNode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // [是否新建] [任务名] [是否异步] [是否记录] [延迟执行] [时间间隔]    [参数....]
        //     0        1        2     3          4          5         6
//        commandSender.sendMessage("1");
        try {
            if (strings.length < 1) {
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + "请输入正确的命令格式"));
                return true;
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(PostHelper.InitMessage("控制台还是不要用这个指令比较好，你有啥需求你为啥不能自己干"));
                commandSender.sendMessage(PostHelper.InitMessage("主要还是因为我这个指令需要有发起人，底层写的是玩家，改起来比较麻烦"));
//            StartPlugins.logger.info(commandSender.getClass().toString());
                return true;
            }
            Player player = (Player) commandSender;
            if (strings[0].equalsIgnoreCase("new")) {
                SaveTaskManager manager = SaveTaskManager.getInstance();
                String[] taskArgs = null;
                if (strings.length >= 6) {
                    taskArgs = Arrays.copyOfRange(strings, 6, strings.length);
                }
                commandSender.sendMessage(strings);
                boolean Asynchronously;
                String taskName;
                boolean Record;
                int delay;
                int timer;
                try {
                    taskName = manager.getTaskName(strings[1]);
                    Asynchronously = strings[2].equalsIgnoreCase("true");
                    Record = strings[3].equalsIgnoreCase("true");
                    delay = Integer.parseInt(strings[4]);
                    timer = Integer.parseInt(strings[5]);
                } catch (RuntimeException exception) {
//                    exception.getCause()
                    throw new parametersExceptions(MessageHelper.getMessageCompletion("Command.parametersException"));
                }

                BasicSaveHandles Instance = manager.newTaskInstance(taskName, Xiaojiu.getInstance(), player, taskArgs);
//                Instance = manager.newTaskInstance(SavePlayerTask.class, Xiaojiu.getInstance(), player, taskArgs);
                manager.addTask(Instance);
                if (Asynchronously) {
                    Instance.RunTaskAsynchronously(timer, delay);
                } else {
                    Instance.RunTask(timer, delay);
                }
                if (Record) {
                    SaveTaskManager.getInstance().addRecordTask(Instance);
                }
                player.sendMessage(PostHelper.InitMessage(ChatColor.DARK_GREEN + "任务创建成功" + "任务id:" + Instance.getTaskid()));
            } else if (strings[0].equalsIgnoreCase("show")) {
                if (SaveTaskManager.getInstance().getTaskList().isEmpty())
                    player.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + "目前无任务进行"));
                if (strings.length > 2) {
                    if (Utils.isNumber(strings[1])) {
                        BasicSaveHandles task = SaveTaskManager.getInstance().getTask(Integer.parseInt(strings[1]));
                        SaveCmdHelper.ShowTaskInfo(task, player);
                    }
                } else {
                    SaveTaskManager.getInstance().getTaskList().forEach(task -> {
                        SaveCmdHelper.ShowTaskInfo(task, player);
                        player.sendMessage(ChatColor.GOLD + "=========我是分隔符=========");
                    });
                }
            } else if (strings[0].equalsIgnoreCase("cancel")) {
                //xj save cancel taskid/taskPlayer/taskName
                BasicSaveHandles task = null;
                try {
                    if (Utils.isNumber(strings[1])) {
                        task = SaveTaskManager.getInstance().getTask(Integer.parseInt(strings[1]));
                    } else {
                        OfflinePlayer player1 = Xiaojiu.getInstance().getServer().getOfflinePlayer(strings[1]);
                        if (player1 != null) {
                            for (BasicSaveHandles basicSaveHandles : SaveTaskManager.getInstance().getTaskList()) {
                                if (basicSaveHandles.getPlayer().equals(player1)) {
                                    task = basicSaveHandles;
                                    break;
                                }
                            }
                        } else {
                            for (BasicSaveHandles basicSaveHandles : SaveTaskManager.getInstance().getTaskList()) {
                                if (basicSaveHandles.getDescribe().equals(strings[1])) {
                                    task = basicSaveHandles;
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new parametersExceptions(MessageHelper.getMessageCompletion("Command.taskGetFail"));
                }
                if (task == null) throw new parametersExceptions(MessageHelper.getMessageCompletion("Command.taskGetFail"));
                task.cancelTask();
                commandSender.sendMessage(PostHelper.InitMessage(ChatColor.LIGHT_PURPLE + "任务取消成功"));
                //todo
            }
        } catch (Exception exception) {
            //命令执行失败处理
            commandSender.sendMessage(MessageHelper.getMessageCompletion("Command.FailException"));
            commandSender.sendMessage(Arrays.toString(exception.getStackTrace()));
//            commandSender.sendMessage(String.valueOf(exceptions.getStackTrace()[0]));
            commandSender.sendMessage(MessageHelper.getMessageCompletion("Command.Fail"));
            commandSender.sendMessage(exception.getMessage());
        }


        //todo
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            this.GetHelpMap().forEach((string, helpMap) -> {
                if (string.startsWith(strings[0].toLowerCase()) && !string.equalsIgnoreCase("task")) list.add(string);

            });
        } else if (strings.length == 2 || strings.length == 3) {
            list.addAll(addTrueFalse(strings[strings.length - 1]));
        }
//
        return list;
    }

    private List<String> addTrueFalse(String s) {
        List<String> list = new ArrayList<>();
        if ("true".startsWith(s.toLowerCase())) {
            list.add("true");
        }
        if ("false".startsWith(s.toLowerCase())) {
            list.add("false");
        }
        return list;
    }
}
