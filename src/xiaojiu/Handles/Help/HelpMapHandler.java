package xiaojiu.Handles.Help;

import xiaojiu.api.HelpMap;

public class HelpMapHandler implements HelpMap {
    public HelpMapHandler(String faster, String command, String permissionNode, String introduce) {
        this.faster = faster;
        this.command = command;
        this.PermissionNode = permissionNode;
        this.introduce = introduce;
    }

    public String faster;
    public String command;
    public String PermissionNode;
    public String introduce;
    @Override
    public String getFather() {
        return faster;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getPermissionNode() {
        return PermissionNode;
    }

    @Override
    public String getIntroduce() {
        return introduce;
    }
}
