import com.jcoinche.model.Board;
import com.jcoinche.model.Card;
import com.jcoinche.model.Player;
import com.jcoinche.model.Room;
import com.jcoinche.server.Server;
import org.junit.Test;
import org.mockito.cglib.beans.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Server serv = new Server();
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

        serv.distributeCards(room, room.getPlayers().get(0), 5);

        assertTrue(room.getPlayers().get(0).getCards().get(0).getType() == Card.TypeCard.CLUBS);
    }

    @Test
    public void determineFoldWinnerTest() {
        List<Card> fold = new ArrayList<>();
        Card firstCard = new Card(Card.TypeCard.CLUBS, "K", 0);
        int ret;

        fold.add(new Card(Card.TypeCard.CLUBS, "K", 0));
        fold.add(new Card(Card.TypeCard.HEART, "Q", 0));
        fold.add(new Card(Card.TypeCard.DIAMOND, "V", 0));
        fold.add(new Card(Card.TypeCard.SPADES, "A", 0));

        ret = serverTest.determineFoldWinner(fold, firstCard, Card.TypeCard.HEART, new Board().getValueCardAsset(), new Board().getValueCard());
        assertTrue(ret == 1);
    }

    @Test
    public void countFoldScoreTest() throws Exception {
        Server testS = new Server();
        Room room = new Room();
        List<Card> fold = new ArrayList<>();
        Player player = new Player();
        int score;

        room.setBoard(new Board());
        fold.add(new Card(Card.TypeCard.SPADES, "K", 0));
        fold.add(new Card(Card.TypeCard.HEART, "Q", 0));
        fold.add(new Card(Card.TypeCard.DIAMOND, "V", 0));
        fold.add(new Card(Card.TypeCard.CLUBS, "A", 0));

        room.getBoard().setFold(fold);

        player.setId("1");
        player.setScore(0);
        room.setPlayers(new ArrayList<>());
        room.getPlayers().add(player);
        room.getBoard().setAsset(new Card(Card.TypeCard.SPADES, "A", 0));

        testS.setRooms(new ArrayList<>());
        testS.getRooms().add(room);

        player = testS.countFoldScore("1");
        assertEquals(player.getScore(), 20);
    }

    @Test
    public void getRoomOfPlayerTest() {
        Server testS = new Server();

        testS.setRooms(new ArrayList<>());
        for (int i = 0; i < 2; i++) {
            testS.getRooms().add(new Room());
            testS.getRooms().get(i).setPlayers(new ArrayList<>());
            testS.getRooms().get(i).getPlayers().add(new Player());
        }
        testS.getRooms().get(0).getPlayers().get(0).setId("1");
        testS.getRooms().get(0).getPlayers().get(0).setScore(10);
        testS.getRooms().get(1).getPlayers().get(0).setId("2");
        testS.getRooms().get(1).getPlayers().get(0).setScore(20);

        Room testRoom = testS.getRoomOfPlayer("1");
        assertEquals(testRoom.getPlayers().get(0).getScore(), 10);
        testRoom = testS.getRoomOfPlayer("2");
        assertEquals(testRoom.getPlayers().get(0).getScore(), 20);
    }
}
