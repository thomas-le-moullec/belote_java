package com.jcoinche.server;

import com.jcoinche.model.Greeting;
import com.jcoinche.model.HelloMessage;
import com.jcoinche.model.ProtoTask;
import com.jcoinche.model.Room;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import java.util.List;


@Controller
public class Server {

    private List<Room> rooms;

    @MessageMapping("/jcoinche/greeting/{id}")
    @SendTo("/topic/users/{id}")
    public Greeting greeting(@DestinationVariable("id") String id, HelloMessage message) throws Exception {
        System.out.print("Id User =>"+id+" WE WILL CREATE ROOM !!\n");
        //Create room, push player
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/jcoinche/askForTask/{id}")
    @SendTo("/topic/users/{id}")
    public ProtoTask askForTask(@DestinationVariable("id") String id) throws Exception {
        //Conditions to determine what to do.
        System.out.print("Id User =>"+id+" WE WILL WAIT !!! \n");
        ProtoTask task = new ProtoTask(ProtoTask.Protocol.WAIT, getRoomOfPlayer(id).getBoard());
        return task;
    }

    public Room getRoomOfPlayer(String id) {
        for (int i = 0; i < getRooms().size() - 1; i++) { // Check if - 1 is ok or not.
            for (int j = 0; j < getRooms().get(i).getPlayers().size() - 1; j++) {
                if (id == getRooms().get(i).getPlayers().get(j).getId()) {
                    return getRooms().get(i);
                }
            }
        }
        return getRooms().get(0);//have to manage error here.
    }

    public List<Room> getRooms() {
        return rooms;
    }
}