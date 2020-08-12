package Testing;

import Transportation.Transit;
import Weather.CompleteDayForecast;
import Weather.SingleForecastData;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static Weather.WeatherModuleDarkSky.getNextFourDays;
import static Weather.WeatherModuleDarkSky.getTwoDayExactForecast;

public class TestTransitAndWeather {

    public static void main(String[] args) throws IOException, ApiException, InterruptedException {

        Transit transit = Transit.getTransitions("wurzburg", "max-morlock-stadion", LocalDateTime.now());
        System.out.println(transit.toString());

        String lat = String.valueOf(transit.getAllPossibleTransitions().get(0).getStartCoordinates().getLatitude());
        String lon = String.valueOf(transit.getAllPossibleTransitions().get(0).getStartCoordinates().getLongitude());
        List<ArrayList<SingleForecastData>> twoDays =  getTwoDayExactForecast(lat, lon);
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
