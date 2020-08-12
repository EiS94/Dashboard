package Testing;

import Location.Location;
import Transportation.Transit;
import Web.Transport;
import com.google.maps.errors.ApiException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;

public class TestTransit {

    public static void main(String[] args) throws InterruptedException, ApiException, IOException, ParseException {

        //Transit.async("hubland", "wurzburg hbf", LocalDateTime.now());

        Transit transit = Transit.getTransitions("würzburg salvatorstrasse", "würzburg", LocalDateTime.now());

        System.out.println(transit.toString());

    }

}
