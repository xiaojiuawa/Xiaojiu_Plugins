package xiaojiu.Log;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.SQLException;
@Deprecated
public class EventListener implements Listener {
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) throws SQLException {
//        Connection con = DriverManager.getConnection("1","2","3");
        System.out.println(event.getBlock().toString());
        System.out.println(event.getBlock().getType().name());
        System.out.println(event.getBlock().getState().toString());
//        statement.
    }
}
