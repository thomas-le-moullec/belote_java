import com.jcoinche.model.Card;
import com.jcoinche.server.Server;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        assertTrue(Server.compareColor(card1, card2));
        assertTrue(!Server.compareColor(card1, card3));
    }

    @Test
    public void isAssetTest() {
        Card card = new Card();
        Card.TypeCard type = Card.TypeCard.HEART;

        card.setType(Card.TypeCard.CLUBS);
        //assertTrue(card != assert);
    }

}


/*
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
 */