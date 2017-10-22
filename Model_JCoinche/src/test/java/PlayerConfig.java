import com.jcoinche.model.Card;
import com.jcoinche.model.Player;
import com.jcoinche.model.ProtoTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class PlayerConfig {

    @Bean
    public Player PlayerTest() {
        List<Card> myCardList = new ArrayList<>();
        myCardList.add(new Card());
//        Player myPlayer = new Player("0", myCardList, 0, 0, ProtoTask.Protocol.WAIT);
Player myPlayer = new Player();
        return myPlayer;
    }
}
