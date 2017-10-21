package com.jcoinche.server;

import com.jcoinche.model.Greeting;
import com.jcoinche.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class Server {

    public int id = 0; // remove

    @MessageMapping("/jcoinche/general")
    @SendTo("/topic/greetings/general")
    public Greeting greeting(HelloMessage message) throws Exception {
        id++;
        //need a getAvailableRoom to get a unique room{x} string and the number of player + 1
        return new Greeting("Hello, " + message.getName() + "!", id, "room1");
    }
}