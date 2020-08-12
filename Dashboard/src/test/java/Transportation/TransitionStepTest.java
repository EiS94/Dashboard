package Transportation;

import com.google.maps.model.TravelMode;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class TransitionStepTest {

    TransitionStep validStep, invalidStep;

    public TransitionStepTest() {
    }

    @Test
    public void testIllegalArguments() {
        String invalidString = "";
        try {
            invalidStep = new TransitionStep("", "100 km", "1h", "Würzburg", "Nürnberg", LocalTime.now(), LocalTime.now(), Optional.empty(), Optional.empty(), TravelMode.TRANSIT);
        } catch (IllegalArgumentException e) {
            invalidString = e.getMessage();
        }
        assertEquals("Ein Parameter hat keinen Inhalt", invalidString);

        try {
            invalidStep = new TransitionStep("Zug", "100 km", "", "", "Nürnberg", LocalTime.now(), LocalTime.now(), Optional.empty(), Optional.empty(), TravelMode.WALKING);
        } catch (IllegalArgumentException e) {
            invalidString = e.getMessage();
        }
        assertEquals("Ein Parameter hat keinen Inhalt", invalidString);

        try {
            invalidStep = new TransitionStep("Test", "100 km", "1h", "Würzburg", "Nürnberg", LocalTime.now(), LocalTime.now(), Optional.empty(), Optional.empty(), TravelMode.UNKNOWN);
        } catch (IllegalArgumentException e) {
            invalidString = e.getMessage();
        }
        assertEquals("Keine Zugverbindung gefunden", invalidString);

        try {
            invalidStep = new TransitionStep("Zug von Würzburg nach Nürnberg", "110 km", "1h 19min", "Würzburg Hbf", "Nürnberg Hbf", LocalTime.of(10, 42), LocalTime.of(9, 55), Optional.empty(), Optional.empty(), TravelMode.TRANSIT);
        } catch (IllegalArgumentException e) {
            invalidString = e.getMessage();
        }
        assertEquals("Abfahrtszeit muss vor Ankunftszeit sein", invalidString);
    }

    @Test
    public void testValidStep() {
        String validString = "";
        try {
            validStep = new TransitionStep("Laufen", "100 km", "2h", "", "Nürnberg", LocalTime.now(), LocalTime.now(), Optional.empty(), Optional.empty(), TravelMode.WALKING);
            validString = validStep.toString();
        } catch (IllegalArgumentException e) {
            validString = e.getMessage();
        }
        assertEquals("Laufen", validString);

        try {
            validStep = new TransitionStep("Laufen", "100 km", "2h", "start", "", LocalTime.now(), LocalTime.now(), Optional.empty(), Optional.empty(), TravelMode.WALKING);
            validString = validStep.toString();
        } catch (IllegalArgumentException e) {
            validString = e.getMessage();
        }
        assertEquals("Laufen", validString);

        try {
            validStep = new TransitionStep("Zug", "100 km", "2h", "start", "end", LocalTime.now(), LocalTime.now(), Optional.empty(), Optional.empty(), TravelMode.TRANSIT);
            validString = validStep.toString();
        } catch (IllegalArgumentException e) {
            validString = e.getMessage();
        }
        assertEquals("Zug", validString);
    }


    @Test
    public void testToString() {
        validStep = new TransitionStep("Zug", "100 km", "2h", "start", "end", LocalTime.now(), LocalTime.now(), Optional.empty(), Optional.empty(), TravelMode.TRANSIT);
        assertEquals("Zug", validStep.toString());
    }
}