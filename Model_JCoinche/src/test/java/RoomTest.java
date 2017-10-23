import com.jcoinche.model.Player;
import com.jcoinche.model.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class RoomTest {

    @Test
    public void getPlayerTest() {
        Room room = new Room();

        room.setPlayers(new ArrayList<>());
        for (int i = 0; i < 2; i++) {
            room.getPlayers().add(new Player());
        }
        room.getPlayers().get(0).setId("1");
        room.getPlayers().get(0).setScore(10);
        room.getPlayers().get(1).setId("2");
        room.getPlayers().get(1).setScore(20);

        Player player = room.getPlayer("1");
        assertEquals(player.getScore(), 10);
        player = room.getPlayer("2");
        assertEquals(player.getScore(), 20);
    }
}
