import com.jcoinche.model.Card;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CardTest {

    @Autowired
    private Card myClassTest;

    @Test
    public void getTypeTest() {
        //myClassTest.setType(Card.TypeCard.CLUBS);
    //    assertTrue(myClassTest.getType() == Card.TypeCard.CLUBS);
    }
}
