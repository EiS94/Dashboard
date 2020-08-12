package Transportation;

import com.google.maps.errors.ApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.*;
import java.util.List;

import static org.junit.Assert.*;

public class TransitTest {

    Transit transitCorrect, transitNoWays;

    public TransitTest() throws InterruptedException, ApiException, IOException {
        LocalDateTime ldt = LocalDateTime.of(2020, Month.JANUARY, 2, 11,11);
        //transitCorrect = Transit.getTransitions("fc nurnberg", "greuther furth", ldt);
        transitNoWays = Transit.getTransitions("broadchurch", "wurzburg", LocalDateTime.now());
    }

    @Test
    public void testGetAllPossibleTransitions() {
        //TODO
        /*List<TransitionSingle> allPossibleTransitions = null;
        if (transitCorrect != null) {
             allPossibleTransitions = transitCorrect.allPossibleTransitions;
            assertTrue("Zu wenige Moeglichkeiten von Nurnberg nach Fuerth", allPossibleTransitions.size() > 3);
            assertEquals("Umstiegsanzahl stimmt nicht", 7, allPossibleTransitions.get(0).getTransition().size());
            assertEquals("Falsche Ankunftszeit", LocalTime.of(12,18,44), allPossibleTransitions.get(0).getArrivalTime());
        }*/
    }

    @Test
    public void testNoWaysFound() {
        List<TransitionSingle> allPossibleTransitions = null;
        if (transitNoWays != null) {
                allPossibleTransitions = transitNoWays.allPossibleTransitions;
                assertTrue("Es sollte keine Verbindung gefunden werden", allPossibleTransitions.size() != 1);
            }
    }

    @Test
    public void testToString() {
        //TODO
        assertEquals("Keine Zugverbindung gefunden.", transitNoWays.toString());
    }
}