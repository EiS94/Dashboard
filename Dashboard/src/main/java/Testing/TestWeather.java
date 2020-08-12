package Testing;

import Location.Coordinates;
import Location.Location;
import Weather.CompleteDayForecast;
import Weather.SingleForecastData;
import Web.Navbar;
import Web.Transport;
import com.google.maps.errors.ApiException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static Weather.WeatherModuleDarkSky.getNextFourDays;
import static Weather.WeatherModuleDarkSky.getTwoDayExactForecast;

public class TestWeather {

    public static void main(String[] args) throws IOException, ParseException, ApiException, InterruptedException {
        try {
            Coordinates.getPlaceCoordinates("wurzburg");
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<String> w = Web.WebWeather.getWebWeather();

        Location l = null;
        String n = null;
        ArrayList<String> t = null;
        String city;
        Coordinates c;
        n = Navbar.getNavbar();
        city = n.split(",")[0];
        c = Coordinates.getPlaceCoordinates(city);
        Web.AppointmentTable.getTable();
        try {
            t = Web.Transport.getTransport();
        } catch (InterruptedException | ConnectException | ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            w = Web.WebWeather.getWebWeather();
        } catch (ParseException | ConnectException e) {
        } catch (IOException e) {
        }
        System.out.println("bla");
    }

    public static void testNormal() throws IOException, ParseException {
        Coordinates c = Coordinates.getPlaceCoordinatesFromIP();
        System.out.println("lat: " + c.getLatitude() + " long: " + c.getLongitude());
        String lat = String.valueOf(c.getLatitude());
        String lon = String.valueOf(c.getLongitude());
        List<ArrayList<SingleForecastData>> twoDays = getTwoDayExactForecast(lat, lon);
        List<CompleteDayForecast> dayThreeAndFour = getNextFourDays(lat, lon);

        System.out.println("Wetterdaten für heute:");
        for (int i = 0; i < twoDays.get(0).size(); i++) {
            System.out.println(twoDays.get(0).get(i).toString());
        }
        System.out.println("Wetterdaten für morgen:");
        for (int i = 0; i < twoDays.get(1).size(); i++) {
            System.out.println(twoDays.get(1).get(i).toString());
        }
        System.out.println("Wetterdaten für heute:");
        System.out.println(dayThreeAndFour.get(0).toString());
        System.out.println("Wetterdaten für morgen:");
        System.out.println(dayThreeAndFour.get(1).toString());
        System.out.println("Wetterdaten für übermorgen:");
        System.out.println(dayThreeAndFour.get(2).toString());
        System.out.println("Wetterdaten für überübermorgen:");
        System.out.println(dayThreeAndFour.get(3).toString());

    }
}
