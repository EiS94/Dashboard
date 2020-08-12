package Testing;

import Location.Coordinates;
import Location.Location;
import com.google.maps.errors.ApiException;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.time.LocalDate;

public class TestCoordinates {

    public static void main(String[] args) throws IOException, ParseException, ApiException, InterruptedException {

        Location.getLocationFromCoordinates(new Coordinates(49.2,9.3));

        System.out.println(new Location("Höchberg"));

        //System.out.println(Coordinates.getPlaceCoordinatesFromIP());

        //System.out.println(Coordinates.getPlaceCoordinates("wurzburg"));
    }

}
