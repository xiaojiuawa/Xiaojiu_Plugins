package xiaojiu.commandExecutor;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand {
    public static Map<String,HelpMap> helpMap = new HashMap<>();
    public static void setHelpMap(String common, HelpMap commons){
        helpMap.put(common,commons);
    }
    {
        setHelpMap("cancel",new HelpMap("rest","/xj rest cancel","xiaojiu.rest.cancel","通过这个命令取消重启服务器"));
        setHelpMap("reset",new HelpMap("reset","/xj rest reset","xiaojiu.rest.reset [time]","通过这个指令重设服务器计划重启时间"));
        //todo
    }
    public class HelpMap{
        public HelpMap(String faster,String command,String permissionNode,String introduce){
            this.faster=faster;
            this.command=command;
            this.PermissionNode=permissionNode;
            this.introduce=introduce;
        }
        public String faster;
        public String command;
        public String PermissionNode;
        public String introduce;
    }
}
