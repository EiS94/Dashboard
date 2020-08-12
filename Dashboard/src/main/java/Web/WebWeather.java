package Web;

import Location.Coordinates;
import Location.Location;
import Weather.CompleteDayForecast;
import Weather.SingleForecastData;
import Weather.WeatherModuleDarkSky;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class WebWeather {

    public static ArrayList<String> getWebWeather() throws IOException, ParseException {
        //System.out.println("Wetter!");
        //Koordinaten/Ort von der IP-Adresse
        Coordinates coor = Coordinates.getPlaceCoordinatesFromIP();
        String ort = Coordinates.getNameFromIP();
        //Wetterprognose Heute-drei Tage später
        List<CompleteDayForecast> nextFourDays = WeatherModuleDarkSky.getNextFourDays(String.valueOf(coor.getLatitude()), String.valueOf(coor.getLongitude()));
        //Wetterdetails Heute und Morgen
        List<ArrayList<SingleForecastData>> twoDayExact = WeatherModuleDarkSky.getTwoDayExactForecast(String.valueOf(coor.getLatitude()), String.valueOf(coor.getLongitude()));
        //Wetter aktuelle/ aktuelle Stunde
        ArrayList<SingleForecastData> firstSingleToday = twoDayExact.get(0);
        SingleForecastData aktuell = firstSingleToday.get(0);

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("kk");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        //Tabelle für die 3 tägige Wetterprognose; Tag und Datum+Höchsttemp.+Tiefsttemp.+Icon+Regenwahrscheinlichkeit
        StringBuilder sb = new StringBuilder();
        for (CompleteDayForecast nextDay : nextFourDays) {
            sb.append("<tr><td>").append(nextDay.getLocalDate().format(dtf2)).append("</td><td>")
                    .append(nextDay.getTempHigh()).append("/")
                    .append(nextDay.getTempLow()).append(" °C").append("</td><td><img src=\"")
                    .append(getIcon(nextDay.getShortDescription())).append("\" alt=\"...\">").append("</td><td>")
                    .append(nextDay.getPrecipitationProbabilityInPercent()).append(" %").append("</td></tr>");

        }

        //Listenpos: 0=Wettericon Heute; 1=Standort, Datum und Uhrzeit; 2=Kurzbeschreibung; 3=Temp.; 4=Regenwaharscheinlichkeit; 5=Tabelle; 6=Ort;
        String s = Location.convert(aktuell.getShortDescription());
        ArrayList<String> weatherList = new ArrayList<>();
        weatherList.add("src=\"" + getIcon(s) + "\"");
        weatherList.add("Wetter am " + aktuell.getLocalDateTime().format(dtf2) + " um " + aktuell.getLocalDateTime().format(dtf1) + " Uhr");
        weatherList.add(s);
        weatherList.add(aktuell.getTemp() + " °C");
        weatherList.add((int) aktuell.getPrecipitationProbabilityInPercent() + " %");
        weatherList.add(sb.toString());
        weatherList.add(ort.toUpperCase());

        return weatherList;
    }

    public static ArrayList<String> getWebWeather(Location location) throws IOException, ParseException {
        //System.out.println("Wetter!");
        String ort = location.getCity();
        //Wetterprognose Heute-drei Tage später
        List<CompleteDayForecast> nextFourDays = WeatherModuleDarkSky.getNextFourDays(String.valueOf(
                location.getCoordinates().getLatitude()), String.valueOf(location.getCoordinates().getLongitude()));
        //Wetterdetails Heute und Morgen
        List<ArrayList<SingleForecastData>> twoDayExact = WeatherModuleDarkSky.getTwoDayExactForecast(String.valueOf(
                location.getCoordinates().getLatitude()), String.valueOf(location.getCoordinates().getLongitude()));
        //Wetter aktuelle/ aktuelle Stunde
        ArrayList<SingleForecastData> firstSingleToday = twoDayExact.get(0);
        SingleForecastData aktuell = firstSingleToday.get(0);

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("kk");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        //Tabelle für die 3 tägige Wetterprognose; Tag und Datum+Höchsttemp.+Tiefsttemp.+Icon+Regenwahrscheinlichkeit
        StringBuilder sb = new StringBuilder();
        for (CompleteDayForecast nextDay : nextFourDays) {
            sb.append("<tr><td>").append(nextDay.getLocalDate().format(dtf2)).append("</td><td>")
                    .append(nextDay.getTempHigh()).append("/")
                    .append(nextDay.getTempLow()).append(" °C").append("</td><td><img src=\"")
                    .append(getIcon(nextDay.getShortDescription())).append("\" alt=\"...\">").append("</td><td>")
                    .append(nextDay.getPrecipitationProbabilityInPercent()).append(" %").append("</td></tr>");

        }

        //Listenpos: 0=Wettericon Heute; 1=Standort, Datum und Uhrzeit; 2=Kurzbeschreibung; 3=Temp.; 4=Regenwaharscheinlichkeit; 5=Tabelle; 6=Ort;
        String s = Location.convert(aktuell.getShortDescription());
        ArrayList<String> weatherList = new ArrayList<>();
        weatherList.add("src=\"" + getIcon(s) + "\"");
        weatherList.add("Wetter am " + aktuell.getLocalDateTime().format(dtf2) + " um " + aktuell.getLocalDateTime().format(dtf1) + " Uhr");
        weatherList.add(s);
        weatherList.add(aktuell.getTemp() + " °C");
        weatherList.add((int) aktuell.getPrecipitationProbabilityInPercent() + " %");
        weatherList.add(sb.toString());
        weatherList.add(ort.toUpperCase());

        return weatherList;
    }

    private static String getIcon(String kind) {

        //hier wird die Kurzbeschreibung ausgelesen und das entsprechende Icon als String (Pfad) zurück gegeben
        if (kind.toLowerCase().contains("klar")) {
            if (kind.toLowerCase().contains("tag")) {
                return "WeatherIcons/animated/day.svg";
            } else if (kind.toLowerCase().contains("nacht")) {
                return "WeatherIcons/animated/night.svg";
            } else return "WeatherIcons/animated/day.svg";
        } else if (kind.toLowerCase().contains("bewölkt")) {
            if (kind.toLowerCase().contains("stark")) {
                return "WeatherIcons/animated/cloudy.svg";
            } else if (kind.toLowerCase().contains("tag")) {
                return "WeatherIcons/animated/cloudy-day-3.svg";
            } else if (kind.toLowerCase().contains("nacht")) {
                return "WeatherIcons/animated/cloudy-night-3.svg";
            } else return "WeatherIcons/animated/cloudy.svg";
        } else if (kind.toLowerCase().contains("regen")) {
            return "WeatherIcons/animated/rainy-3.svg";
        } else if (kind.toLowerCase().contains("schnee")) {
            return "WeatherIcons/animated/snowy-3.svg";
        } else if (kind.toLowerCase().contains("schneeregen")) {
            return "WeatherIcons/animated/rainy-7.svg";
        } else if (kind.toLowerCase().contains("gewitter")) {
            return "WeatherIcons/animated/thunder.svg";
        } else if (kind.toLowerCase().contains("wind") || kind.toLowerCase().contains("nebel")) {
            if (kind.toLowerCase().contains("regen")) {
                return "WeatherIcons/animated/rainy-3.svg";
            } else return "WeatherIcons/animated/cloudy.svg";
        } else return "WeatherIcons/animated/default.svg";
    }
}

