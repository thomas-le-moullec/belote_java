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

import java.util.*;


@Controller
public class Server {

    private List<Room> rooms;

    @MessageMapping("/jcoinche/greeting/{id}")
    @SendTo("/topic/users/{id}")
    public Greeting greeting(@DestinationVariable("id") String id, HelloMessage message) throws Exception {
        //System.out.print("Id User =>"+id+" WE WILL CREATE ROOM !!\n");
        //System.out.println("ROOM SIZE:"+ rooms.size());
        addPlayerInRoom(id);
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/jcoinche/endGame/{id}")
    @SendTo("/topic/endGame/{id}")
    public ScoreBoard endGame(@DestinationVariable("id") String id) throws Exception {

        int teamWin;
        int scorePlayer0;
        int scorePlayer1;
        int scorePlayer2;
        int scorePlayer3;

        scorePlayer0 = getRoomOfPlayer(id).getPlayers().get(0).getScore();
        scorePlayer1 = getRoomOfPlayer(id).getPlayers().get(1).getScore();
        scorePlayer2 = getRoomOfPlayer(id).getPlayers().get(2).getScore();
        scorePlayer3 = getRoomOfPlayer(id).getPlayers().get(3).getScore();
        if (scorePlayer0+scorePlayer2 < scorePlayer1+scorePlayer3) {
            teamWin = 1;
        }
        else {
            teamWin = 0;
        }
        return new ScoreBoard(scorePlayer0+scorePlayer2, scorePlayer1+scorePlayer3, teamWin);
    }

    @MessageMapping("/jcoinche/getPlayer/{id}")
    @SendTo("/topic/getPlayer/{id}")
    public Player getCards(@DestinationVariable("id") String id) throws Exception {
        //getRoomOfPlayer(id).getPlayer(id).setTask(ProtoTask.Protocol.GETPLAYER);
        for (int i = 0; i < getRoomOfPlayer(id).getPlayer(id).getCards().size(); i++) {
//            System.out.println("Card TYPE:"+getRoomOfPlayer(id).getPlayer(id).getCards().get(i).getType()+ "VALUE:"+getRoomOfPlayer(id).getPlayer(id).getCards().get(i).getValue());
        }
        return getRoomOfPlayer(id).getPlayer(id);
    }

    @MessageMapping("/jcoinche/board/{id}")
    @SendTo("/topic/board/{id}")
    public Board greeting(@DestinationVariable("id") String id) throws Exception {
        return getRoomOfPlayer(id).getBoard();
    }

    @MessageMapping("/jcoinche/takeCards/{id}")
    @SendTo("/topic/takeCards/{id}")
    public Player takeCards(@DestinationVariable("id") String id) throws Exception {
        //Conditions to determine what to do.
        Player player = getRoomOfPlayer(id).getPlayer(id);
        getRoomOfPlayer(id).setPlays(getRoomOfPlayer(id).getPlays() + 1);
        if (getRoomOfPlayer(id).getPlays() == 4) {
            getRoomOfPlayer(id).setIdTurn(id);
//            System.out.println("Id PLAYER WHEN GET ASSET:"+getRoomOfPlayer(id).getPlayers().get(0).getId());
//            System.out.println("Id PLAYER WHEN GET ASSET:"+getRoomOfPlayer(id).getPlayers().get(1).getId());
//            System.out.println("Id PLAYER WHEN GET ASSET:"+getRoomOfPlayer(id).getPlayers().get(2).getId());
//            System.out.println("Id PLAYER WHEN GET ASSET:"+getRoomOfPlayer(id).getPlayers().get(3).getId());
            getRoomOfPlayer(id).getPlayers().get(0).setTask(ProtoTask.Protocol.GETASSET);
            getRoomOfPlayer(id).getPlayers().get(1).setTask(ProtoTask.Protocol.WAIT);
            getRoomOfPlayer(id).getPlayers().get(2).setTask(ProtoTask.Protocol.WAIT);
            getRoomOfPlayer(id).getPlayers().get(3).setTask(ProtoTask.Protocol.WAIT);
            getRoomOfPlayer(id).setPlays(0);
        }
        /*for (int i = 0; i < getRoomOfPlayer(id).getPlayers().size(); i++) {
            System.out.println("Id : "+id+" and id Get : "+getRoomOfPlayer(id).getPlayer(id).getId()+" Task:"+getRoomOfPlayer(id).getPlayers().get(i).getTask() + " id["+i+"] : "+getRoomOfPlayer(id).getPlayers().get(i).getId());
        }*/
        return player;
    }

    @MessageMapping("/jcoinche/takeAsset/{id}")
    public void takeAsset(@DestinationVariable("id") String id, String response) throws Exception {
        System.out.print("Id User =>"+id+" answered to take asset, response:"+response+"\n");
        if (response.equals("Yes") == true || response.equals("Y") == true) {
            getRoomOfPlayer(id).setAssetTaker(id);
            for (int i = 0; i < getRoomOfPlayer(id).getPlayers().size(); i++) {
                getRoomOfPlayer(id).getPlayers().get(i).setTask(ProtoTask.Protocol.WAIT);
                distributeCards(getRoomOfPlayer(id), getRoomOfPlayer(id).getPlayers().get(i), 3);
            }
            getRoomOfPlayer(id).getPlayer(id).setTask(ProtoTask.Protocol.PUTCARD);
//            System.out.println("Id PLAYER WHEN GET TAKEASSET:"+getRoomOfPlayer(id).getPlayers().get(0).getId()+" task:"+getRoomOfPlayer(id).getPlayers().get(0).getTask());
//            System.out.println("Id PLAYER WHEN GET TAKEASSET:"+getRoomOfPlayer(id).getPlayers().get(1).getId()+" task:"+getRoomOfPlayer(id).getPlayers().get(1).getTask());
//            System.out.println("Id PLAYER WHEN GET TAKEASSET:"+getRoomOfPlayer(id).getPlayers().get(2).getId()+" task:"+getRoomOfPlayer(id).getPlayers().get(2).getTask());
//            System.out.println("Id PLAYER WHEN GET TAKEASSET:"+getRoomOfPlayer(id).getPlayers().get(3).getId()+" task:"+getRoomOfPlayer(id).getPlayers().get(3).getTask());
        }
        else {
            getRoomOfPlayer(id).getPlayer(id).setTask(ProtoTask.Protocol.WAIT);
            int index = getRoomOfPlayer(id).getPlayers().indexOf(getRoomOfPlayer(id).getPlayer(id));
            getRoomOfPlayer(id).getPlayers().get((index + 1)%4).setTask(ProtoTask.Protocol.GETASSET);
            getRoomOfPlayer(id).setIdTurn(getRoomOfPlayer(id).getPlayers().get((index + 1)%4).getId());
        }
    }

    @MessageMapping("/jcoinche/getAsset/{id}")
    @SendTo("/topic/getAsset/{id}")
    public Card getAsset(@DestinationVariable("id") String id) throws Exception {
//        System.out.print("Id User =>"+id+" is IN GET ASSET !!! \n");
        return getRoomOfPlayer(id).getBoard().getAsset();
    }

    @MessageMapping("/jcoinche/putCard/{id}")
    @SendTo("/topic/putCard/{id}")
    public PutCard putCard(@DestinationVariable("id") String id, Card card) throws Exception {
        Room myRoom = getRoomOfPlayer(id);
        Player player = myRoom.getPlayer(id);
        List<Card> fold = myRoom.getBoard().getFold();

        if (!checkValidity(myRoom, player, fold, card)) {
//            System.out.println("Not VALID CARD\n");
            return new PutCard(false);
        }
        getRoomOfPlayer(id).setPlays(getRoomOfPlayer(id).getPlays() + 1);
        exchangeCards(myRoom, player, card);
        int index = 0;
        if (getRoomOfPlayer(id).getPlays() == 4) {
            getRoomOfPlayer(id).setPlays(0);
            index = getRoomOfPlayer(id).getPlayers().indexOf(countFoldScore(id));
            getRoomOfPlayer(id).getBoard().getFold().clear();
            System.out.println("END OF FOLD !\n\n\n");
        }
        else {
            index = getRoomOfPlayer(id).getPlayers().indexOf(getRoomOfPlayer(id).getPlayer(id));
        }
        if (getRoomOfPlayer(id).getPlayer(id).getCards().size() == 0) {
            for (int i = 0; i < getRoomOfPlayer(id).getPlayers().size(); i++) {
                getRoomOfPlayer(id).getPlayers().get(i).setTask(ProtoTask.Protocol.END);
                return new PutCard(true);
            }
        }
        for (int i = 0; i < getRoomOfPlayer(id).getPlayers().size(); i++) {
            getRoomOfPlayer(id).getPlayers().get(i).setTask(ProtoTask.Protocol.WAIT);
        }
        getRoomOfPlayer(id).getPlayers().get((index + 1)%4).setTask(ProtoTask.Protocol.PUTCARD);
        getRoomOfPlayer(id).setIdTurn(getRoomOfPlayer(id).getPlayers().get((index + 1)%4).getId());
//        System.out.println("VALID CARD\n");
        return new PutCard(true);
    }

    @MessageMapping("/jcoinche/askForTask/{id}")
    @SendTo("/topic/info/{id}")
    public ProtoTask askForTask(@DestinationVariable("id") String id) throws Exception {
        //Conditions to determine what to do.
        ProtoTask.Protocol task = getRoomOfPlayer(id).getPlayer(id).getTask();
//        System.out.print("Id User =>"+id+" WE WILL "+task+" !!! \n");
        return new ProtoTask(task);
    }

    public void addPlayerInRoom(String id) throws Exception {
        {
            Player newPlayer = new Player(id, new ArrayList<Card>(), 0, 0, ProtoTask.Protocol.WAIT);
            if (rooms.size() == 0 || rooms.get(rooms.size() - 1).getPlayers().size() == 4) {
                rooms.add(new Room(rooms.size(), new ArrayList<Player>(), new Board(), ""));
            }
            newPlayer.setTeam((rooms.size() - 1) % 2);
            rooms.get(rooms.size() - 1).getPlayers().add(newPlayer);
            if (rooms.get(rooms.size() - 1).getPlayers().size() == 4) {
                for (int i = 0; i < rooms.get(rooms.size() - 1).getPlayers().size(); i++) {
                    distributeCards(rooms.get(rooms.size() - 1), rooms.get(rooms.size() - 1).getPlayers().get(i), 5);
                }
                rooms.get(rooms.size() - 1).getBoard().setAsset(rooms.get(rooms.size() - 1).getBoard().getPick().get(new Random().nextInt(rooms.get(rooms.size() - 1).getBoard().getPick().size())));
                rooms.get(rooms.size() - 1).getBoard().getPick().remove(rooms.get(rooms.size() - 1).getBoard().getPick().indexOf(rooms.get(rooms.size() - 1).getBoard().getAsset()));
                for (int i = 0; i < 4; i++) {
                    rooms.get(rooms.size() - 1).getPlayers().get(i).setTask(ProtoTask.Protocol.TAKECARD);
                }
            }
        }
    }

    public static Boolean compareColor(Card card1, Card card2) {
        if (card1.getType().equals(card2.getType()) == true) {
            return (true);
        }
        return (false);
    }

    public static Boolean isAsset(Card card, Card.TypeCard asset) {
        if (card.getType().equals(asset)) {
            return (true);
        }
        return (false);
    }

    public static Boolean hasAsset(List<Card> cards, Card.TypeCard asset) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getType().equals(asset) == true) {
                return true;
            }
        }
        return false;
    }

    public static Boolean checkCardExists(List<Card> cardList, Card card) {
        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).getType().equals(card.getType()) ==  true &&
                    cardList.get(i).getValue().equals(card.getValue()) == true)
                return true;
        }
        return false;
    }

    public static Boolean checkValidity(Room myRoom, Player player, List<Card> fold, Card card) {
        if (checkCardExists(player.getCards(), card) == false)
            return false;
        if (fold.size() == 0) {
            return true;
        }

        if (compareColor(card, fold.get(fold.size() - 1)))
            return true;
        if (isAsset(card, myRoom.getBoard().getAsset().getType()))
            return true;
        if (hasAsset(player.getCards(), myRoom.getBoard().getAsset().getType()) == false)
            return true;
        return true;
    }
    public static void exchangeCards(Room myRoom, Player player, Card card) {
        myRoom.getBoard().getFold().add(card);
        for (int i = 0; i < player.getCards().size(); i++) {
            System.out.println("BEFORE["+i+"] Type:"+player.getCards().get(i).getType()+" and Value"+player.getCards().get(i).getValue());
        }
        for (int j = 0; j < player.getCards().size(); j++) {
            if (player.getCards().get(j).getType().equals(card.getType()) == true && player.getCards().get(j).getValue().equals(card.getValue()) == true) {
                player.getCards().remove(j);
            }
        }
        for (int i = 0; i < player.getCards().size(); i++) {
            System.out.println(i+" Type:"+player.getCards().get(i).getType()+" and Value"+player.getCards().get(i).getValue());
        }
    }

    public Player countFoldScore(String id) throws Exception {
        int index = determineFoldWinner(getRoomOfPlayer(id).getBoard().getFold(),
                getRoomOfPlayer(id).getBoard().getFold().get(0),
                getRoomOfPlayer(id).getBoard().getAsset().getType(),
                getRoomOfPlayer(id).getBoard().getValueCardAsset(),
                getRoomOfPlayer(id).getBoard().getValueCard());

        int score = getRoomOfPlayer(id).getPlayers().get(index).getScore();

        for (int i = 0; i < getRoomOfPlayer(id).getBoard().getFold().size(); i++) {
            if (isAsset(getRoomOfPlayer(id).getBoard().getFold().get(i), getRoomOfPlayer(id).getBoard().getAsset().getType())) {
                score += getRoomOfPlayer(id).getBoard().getValueCardAsset().get(getRoomOfPlayer(id).getBoard().getFold().get(i).getValue());
            }
            else {
                score += getRoomOfPlayer(id).getBoard().getValueCard().get(getRoomOfPlayer(id).getBoard().getFold().get(i).getValue());
            }
        }
        getRoomOfPlayer(id).getPlayers().get(index).setScore(score);
        return getRoomOfPlayer(id).getPlayers().get(index);
    }

    public void distributeCards(Room myRoom, Player player, int x) {
        int index;

        for (int tour = 0; tour < x; tour++) {
            if (player.getCards().size() == 7 && player.getId().equals(myRoom.getAssetTaker())) {
                player.getCards().add(myRoom.getBoard().getAsset());
            }
            else {
                index = new Random().nextInt(myRoom.getBoard().getPick().size());
                player.getCards().add(myRoom.getBoard().getPick().get(index));
                myRoom.getBoard().getPick().remove(index);
            }
        }
    }

    public static int determineFoldWinner(List<Card> fold, Card firstCard, Card.TypeCard asset, Map<String, Integer> valueAsset, Map<String, Integer> valueNonAsset) {
        int max;

        max = 0;
        for (int i = 1; i < fold.size(); i++) {
            if (compareColor(fold.get(i), fold.get(max))) {
                if (isAsset(firstCard, asset)) {
                    if (valueAsset.get(fold.get(i).getValue()) > valueAsset.get(fold.get(max).getValue())) {
                        max = i;
                    }
                }
                else {
                    if (valueNonAsset.get(fold.get(i).getValue()) > valueNonAsset.get(fold.get(max).getValue())) {
                        max = i;
                    }
                }
            }
            else {
                if (isAsset(fold.get(i), asset)) {
                    max = i;
                }
                if (fold.get(i).getType().equals(firstCard.getType()) == true && !isAsset(fold.get(max), asset)) {
                    max = i;
                }
            }
        }
        return max;
    }

    public Room getRoomOfPlayer(String id) {
        for (int i = 0; i < getRooms().size(); i++) { // Check if - 1 is ok or not.
            for (int j = 0; j < getRooms().get(i).getPlayers().size(); j++) {
                if (id.equals(getRooms().get(i).getPlayers().get(j).getId()) == true) {
                    return getRooms().get(i);
                }
            }
        }
        return getRooms().get(0);//have to manage error here.
    }

    public Server() {
        rooms = new ArrayList<Room>();
        rooms.add(new Room(rooms.size(), new ArrayList<Player>(), new Board(), ""));
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

}