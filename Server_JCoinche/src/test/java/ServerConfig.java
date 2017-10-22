import com.jcoinche.server.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public Server ServerTest() {
        Server Serv = new Server();


        return Serv;
    }
}
