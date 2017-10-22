package com.jcoinche.server;

import com.jcoinche.model.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;

import java.util.ArrayList;
import java.util.List;


@Controller
public class Server {

    private List<Room> rooms;

    public void addPlayerInRoom(String id) {

        Player newPlayer = new Player(id, new ArrayList<Card>(), 0, 0);
        if (rooms.size() == 0 || rooms.get(rooms.size() - 1).getPlayers().size() == 4) {
            rooms.add(new Room(rooms.size(), new ArrayList<Player>(), new Board(), 0));
        }
        newPlayer.setTeam((rooms.size() - 1) % 2);
        rooms.get(rooms.size() - 1).getPlayers().add(newPlayer);
    }

    @MessageMapping("/jcoinche/greeting/{id}")
    @SendTo("/topic/users/{id}")
    public Greeting greeting(@DestinationVariable("id") String id, HelloMessage message) throws Exception {
        System.out.print("Id User =>"+id+" WE WILL CREATE ROOM !!\n");
        System.out.println("ROOM SIZE:"+ rooms.size());
        addPlayerInRoom(id);
        return new Greeting("Hello, " + message.getName() + "!");
    }

    public Server() {
        rooms = new ArrayList<Room>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}