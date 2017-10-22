import com.jcoinche.model.Card;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardConfig {

        @Bean
        public Card CardTest() {
            Card card = new Card();

            return card;
        }
}
