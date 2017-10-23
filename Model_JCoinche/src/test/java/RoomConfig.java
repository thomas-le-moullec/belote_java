import com.jcoinche.model.Player;
import com.jcoinche.model.Room;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RoomConfig {

    @Bean
    public Room RoomClassTest() {
        Room myRoom = new Room();

        return myRoom;
    }
}
