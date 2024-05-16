package xiaojiu.Handles.Help;

import xiaojiu.api.HelpMap;

import java.util.Objects;

public class HelpMapHandler implements HelpMap {
    //    public HelpMapHandler(XiaojiuCommandExecutor executor,String command ,String permissionNode,String introduce){
//        this.faster = executor.GetCommandNode();
//        this.command = command;
//        this.introduce= introduce;
//        this.PermissionNode=permissionNode;
//    }
    public String faster;
    public String command;
    public String PermissionNode;
    public String introduce;
    public HelpMapHandler(String faster, String command, String permissionNode, String introduce) {
        this.faster = faster;
        this.command = command;
        this.PermissionNode = permissionNode;
        this.introduce = introduce;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpMapHandler that = (HelpMapHandler) o;
        return Objects.equals(faster, that.faster) && Objects.equals(getCommand(), that.getCommand()) && Objects.equals(getPermissionNode(), that.getPermissionNode()) && Objects.equals(getIntroduce(), that.getIntroduce());
    }

    @Override
    public int hashCode() {
        return Objects.hash(faster, getCommand(), getPermissionNode(), getIntroduce());
    }
}
