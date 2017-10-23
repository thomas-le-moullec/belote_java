import com.jcoinche.model.Board;
import com.jcoinche.model.Card;
import com.jcoinche.model.Player;
import com.jcoinche.model.Room;
import com.jcoinche.server.Server;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

public class ServerTest {

    @Autowired
    public Server serverTest;

    @Test
    public void compareColorTest() {
        Card card1 = new Card();
        Card card2 = new Card();
        Card card3 = new Card();

        card1.setType(Card.TypeCard.CLUBS);
        card2.setType(Card.TypeCard.CLUBS);
        card3.setType(Card.TypeCard.HEART);
        assertTrue(serverTest.compareColor(card1, card2));
        assertTrue(!serverTest.compareColor(card1, card3));
    }

    @Test
    public void isAssetTest() {
        Card card = new Card();

        card.setType(Card.TypeCard.CLUBS);
        assertTrue(serverTest.isAsset(card, Card.TypeCard.CLUBS));
        assertTrue(!serverTest.isAsset(card, Card.TypeCard.HEART));
    }

    @Test
    public void hasAssetTest() {
        List<Card> cardList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            cardList.add(new Card());
            cardList.get(i).setType(Card.TypeCard.CLUBS);
        }
        assertTrue(serverTest.hasAsset(cardList, Card.TypeCard.CLUBS));
        assertTrue(!serverTest.hasAsset(cardList, Card.TypeCard.DIAMOND));
    }

    @Test
    public void checkCardExistsTest() {
        List<Card> cardList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            cardList.add(new Card(Card.TypeCard.CLUBS, "K", 0));
        }
        assertTrue(serverTest.checkCardExists(cardList, new Card(Card.TypeCard.CLUBS, "K", 0)));
        assertTrue(!serverTest.checkCardExists(cardList, new Card(Card.TypeCard.DIAMOND, "K", 0)));
    }

    @Test
    public void checkValidityTest() {
        Room room = new Room();
        List<Card> fold = new ArrayList<>();
        Player player = new Player();

        player.setCards(new ArrayList<>());
        player.getCards().add(new Card(Card.TypeCard.CLUBS, "7", 0));
        player.getCards().add(new Card(Card.TypeCard.CLUBS, "8", 0));
        player.getCards().add(new Card(Card.TypeCard.HEART, "9", 0));
        player.getCards().add(new Card(Card.TypeCard.SPADES, "K", 0));
        room.setBoard(new Board());
        room.getBoard().setAsset(new Card(Card.TypeCard.CLUBS, "K", 0));
        for (int i = 0; i < 4; i++) {
            fold.add(new Card());
        }
        fold.get(0).setType(Card.TypeCard.CLUBS);
        fold.get(0).setValue("Q");
        fold.get(1).setType(Card.TypeCard.HEART);
        fold.get(1).setValue("V");
        fold.get(2).setType(Card.TypeCard.SPADES);
        fold.get(2).setValue("8");
        assertTrue(serverTest.checkValidity(room, player, fold, new Card(Card.TypeCard.CLUBS, "7", 0)));
        assertTrue(!serverTest.checkValidity(room, player, fold, new Card(Card.TypeCard.CLUBS, "K", 0)));
        assertTrue(!serverTest.checkValidity(room, player, fold, new Card(Card.TypeCard.HEART, "9", 0)));
    }

    @Test
    public void putCardTest() {
        Room room = new Room();
        Player player = new Player();
        Card card = new Card(Card.TypeCard.SPADES, "9", 0);

        room.setBoard(new Board());
        room.getBoard().setFold(new ArrayList<>());
        room.getBoard().getFold().add(new Card(Card.TypeCard.CLUBS, "K", 0));

        player.setCards(new ArrayList<>());
        player.getCards().add(card);
        serverTest.exchangeCards(room, player, card);
        assertTrue(room.getBoard().getFold().get(1).getType() == Card.TypeCard.SPADES);
        assertTrue(player.getCards().size() == 0);
    }

    @Test
    public void distributeCardsTest() throws Exception {
        Room room = new Room();

        room.setPlayers(new ArrayList<>());
        for (int i = 0; i < 4; i++) {
            room.getPlayers().add(new Player());
            room.getPlayers().get(i).setCards(new ArrayList<>());
        }
        room.setBoard(new Board());
        room.getBoard().setPick(new ArrayList<>());
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "K", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "Q", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "V", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "10", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "9", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "K", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "Q", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "V", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "10", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "9", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "K", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "Q", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "V", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "10", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "9", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "K", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "Q", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "V", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "10", 0));
        room.getBoard().getPick().add(new Card(Card.TypeCard.CLUBS, "9", 0));

        //serverTest.distributeCards(room, 5);

        //assertTrue("------------->"+room.getPlayers().size(), room.getPlayers().get(0).getCards().get(0).getType() == Card.TypeCard.CLUBS);
    }
}

/*

    @MessageMapping("/jcoinche/distributeCards/{id}")
    @SendTo("/topic/users/{id}")
    public void distributeCards(room) throws Exception {
        Room myRoom = getRoomOfPlayer(id);
        int index;

        for (int tour = 0; tour < 5; tour++) {
            for (int i = 0; i < myRoom.getPlayers().size(); i++) {
                index = new Random().nextInt(myRoom.getBoard().getPick().size());
                myRoom.getPlayers().get(i).getCards().add(myRoom.getBoard().getPick().get(index));
                myRoom.getBoard().getPick().remove(index);
            }
        }
        System.out.println();
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
                if (fold.get(i).getType() == firstCard.getType() && !isAsset(fold.get(max), asset)) {
                    max = i;
                }
            }
        }
        return max;
    }

    @MessageMapping("/jcoinche/countFoldScore/{id}")
    @SendTo("/topic/users/{id}")
    public void countFoldScore(@DestinationVariable("i") String id) throws Exception {
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
    }

    @MessageMapping("/jcoinche/askForTask/{id}")
    @SendTo("/topic/info/{id}")
    public ProtoTask askForTask(@DestinationVariable("id") String id) throws Exception {
        //Conditions to determine what to do.
        ProtoTask task = getRoomOfPlayer(id).getPlayer(id).getTaskProtocol();
        System.out.print("Id User =>"+id+" WE WILL "+task.getTask()+" !!! \n");
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
 */