package com.jcoinche.server;

import com.jcoinche.model.Greeting;
import com.jcoinche.model.HelloMessage;
import com.jcoinche.model.ProtoTask;
import com.jcoinche.model.Room;
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

    @MessageMapping("/jcoinche/greeting/{id}")
    @SendTo("/topic/users/{id}")
    public Greeting greeting(@DestinationVariable("id") String id, HelloMessage message) throws Exception {
        System.out.print("Id User =>"+id+" WE WILL CREATE ROOM !!\n");
        System.out.println("ROOM SIZE:"+ rooms.size());
        addPlayerInRoom(id);
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/jcoinche/putCard/{id}")
    @SendTo("/topic/users/{id}")
    public boolean putCard(@DestinationVariable("id") String id, Card card) throws Exception {
        Room myRoom = getRoomOfPlayer(id);
        Player player = myRoom.getPlayer(id);
        List<Card> fold = myRoom.getBoard().getFold();

        if (!checkValidity(myRoom, player, fold, card)) {
            return false;
        }
        myRoom.getBoard().getFold().add(card);
        player.getCards().remove(card);
        return true;
    }

    @MessageMapping("/jcoinche/takeCards/{id}")
    @SendTo("/topic/takeCards/{id}")
    public Player takeCardS(@DestinationVariable("id") String id) throws Exception {

        System.out.println("IN TAKECARD");
        //Conditions to determine what to do.
        Player player = getRoomOfPlayer(id).getPlayer(id);
        getRoomOfPlayer(id).getPlayer(id).setTask(ProtoTask.Protocol.WAIT);
        System.out.println("Cards of player => :"+player.getCards());
        return player;
    }

    @MessageMapping("/jcoinche/askForTask/{id}")
    @SendTo("/topic/info/{id}")
    public ProtoTask askForTask(@DestinationVariable("id") String id) throws Exception {
        //Conditions to determine what to do.
        ProtoTask task = getRoomOfPlayer(id).getPlayer(id).getTaskProtocol();
        System.out.print("Id User =>"+id+" WE WILL "+task.getTask()+" !!! \n");
        return task;
    }

    public void addPlayerInRoom(String id) {

        Player newPlayer = new Player(id, new ArrayList<Card>(), 0, 0, new ProtoTask(ProtoTask.Protocol.WAIT));
        if (rooms.size() == 0 || rooms.get(rooms.size() - 1).getPlayers().size() == 4) {
            rooms.add(new Room(rooms.size(), new ArrayList<Player>(), new Board(), 0));
        }
        newPlayer.setTeam((rooms.size() - 1) % 2);
        rooms.get(rooms.size() - 1).getPlayers().add(newPlayer);
        if (rooms.get(rooms.size() - 1).getPlayers().size() == 4) {
            rooms.get(rooms.size() - 1).getPlayers().get(0).setTask(ProtoTask.Protocol.TAKECARD);
            rooms.get(rooms.size() - 1).getPlayers().get(1).setTask(ProtoTask.Protocol.TAKECARD);
            rooms.get(rooms.size() - 1).getPlayers().get(2).setTask(ProtoTask.Protocol.TAKECARD);
            rooms.get(rooms.size() - 1).getPlayers().get(3).setTask(ProtoTask.Protocol.TAKECARD);
        }
    }

    private Boolean compareColor(Card card1, Card card2) {
        return (card1.getType() == card2.getType());
    }

    private Boolean isAsset(Card card, Card.TypeCard asset) {
        return (card.getType() == asset);
    }

    private Boolean hasAsset(List<Card> cards, Card.TypeCard asset) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getType() == asset) {
                return true;
            }
        }
        return false;
    }

    private Boolean checkCardExists(List<Card> cardList, Card card) {
        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).getType() == card.getType() &&
                    cardList.get(i).getValue() == card.getValue())
                return true;
        }
        return false;
    }

    private Boolean checkValidity(Room myRoom, Player player, List<Card> fold, Card card) {
        if (!checkCardExists(player.getCards(), card))
            return false;
        if (fold.size() == 0) {
            return true;
        }

        if (compareColor(card, fold.get(fold.size() - 1)))
            return true;
        if (isAsset(card, myRoom.getBoard().getAsset().getType()))
            return true;
        if (!hasAsset(player.getCards(), myRoom.getBoard().getAsset().getType()))
            return true;
        return false;
    }

    public Room getRoomOfPlayer(String id) {
        for (int i = 0; i < getRooms().size(); i++) { // Check if - 1 is ok or not.
            for (int j = 0; j < getRooms().get(i).getPlayers().size(); j++) {
                if (id == getRooms().get(i).getPlayers().get(j).getId()) {
                    return getRooms().get(i);
                }
            }
        }
        return getRooms().get(0);//have to manage error here.
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