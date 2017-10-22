import com.jcoinche.model.Player;
import com.jcoinche.model.Room;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RoomConfig {

    public void configPlayerTest() {
        List<PlayerTest> playerList = new ArrayList<>();
        PlayerTest playerTest = new PlayerTest();

        //playerTest.getId();
        playerList.add(new PlayerTest());

    }

    @Bean
    public Room RoomTest() {
        Room myRoom = new Room();

        return myRoom;
    }
}
