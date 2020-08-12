package Weather;

import Keys.APIKeys;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class WeatherModuleDarkSky {


    public static List<ArrayList<SingleForecastData>> getTwoDayExactForecast(String latitude, String longitude) throws IOException {
        String urlString = "https://api.darksky.net/forecast/" + APIKeys.getDarkSkyKey() + "/" + latitude + "," + longitude +
                "?lang=de&exclude=currently,minutely,daily,alerts,flags&units=auto";

        ArrayList allDataList = listFromURL(urlString);

        ArrayList<SingleForecastData> allSingleForecastData = new ArrayList<>();
        for (Object singleDataBlock : allDataList) {
            allSingleForecastData.add(createSingleForecastData(singleDataBlock.toString()));
        }

        ArrayList<SingleForecastData> currentDayRestHourly = new ArrayList<>();
        ArrayList<SingleForecastData> nextDayCompleteHourly = new ArrayList<>();

        if (allSingleForecastData.size() == 50){                                                                        //Zeitumstellung Sommer -> Winter
            LocalDate today = allSingleForecastData.get(0).getLocalDateTime().toLocalDate();
            int counter = 0;
            int twoOClockCounter = 0;
            while (allSingleForecastData.get(counter).getLocalDateTime().toLocalDate() == today){
                if (allSingleForecastData.get(counter).getLocalDateTime().getHour() == 2){
                    twoOClockCounter++;
                }
                if ((twoOClockCounter < 2) || (allSingleForecastData.get(counter).getLocalDateTime().getHour() != 2)){
                    currentDayRestHourly.add(allSingleForecastData.get(counter));
                }
                counter++;
            }

            if (twoOClockCounter == 2) {
                LocalDate tomorrow = allSingleForecastData.get(counter).getLocalDateTime().toLocalDate();
                for (int i = counter; i < allSingleForecastData.size(); i++) {
                    if (allSingleForecastData.get(counter).getLocalDateTime().toLocalDate() == tomorrow){
                        nextDayCompleteHourly.add(allSingleForecastData.get(i));
                    }else break;
                }
            }else {
                //TwoOClockCounter has to be 1, so the Timeshift is gonna be in day 2:
                for (int i = counter; i < allSingleForecastData.size(); i++) {                                          //weil die stunde nach 2 uhr (also wieder 2) übersprungen werden muss
                    if (allSingleForecastData.get(counter).getLocalDateTime().getHour() == 2){
                        nextDayCompleteHourly.add(allSingleForecastData.get(i));
                        i++;
                    }else nextDayCompleteHourly.add(allSingleForecastData.get(i));
                }
            }
        }else if (allSingleForecastData.size() == 48){                                                                  //Zeitumstellung Winter -> Sommer
            int counterNewDay = 0;
            for (int i = 0; i < allSingleForecastData.size(); i++) {
                if (counterNewDay < 2) {
                    int dayOfI = allSingleForecastData.get(i).getLocalDateTime().getDayOfMonth();
                    int dayOfIPlusOne = allSingleForecastData.get(i + 1).getLocalDateTime().getDayOfMonth();
                    int hourOfI = allSingleForecastData.get(i).getLocalDateTime().getHour();
                    int hourOfIPlusOne = allSingleForecastData.get(i + 1).getLocalDateTime().getHour();
                    if (counterNewDay == 0) {
                        if (hourOfIPlusOne == hourOfI + 1){
                            currentDayRestHourly.add(allSingleForecastData.get(i));
                        }else {
                            currentDayRestHourly.add(allSingleForecastData.get(i));                                     //1:59 -> 3:00, deswegen zwei mal den wert von 1 uhr speichern
                            currentDayRestHourly.add(allSingleForecastData.get(i));
                        }

                    } else{
                        if (hourOfIPlusOne == hourOfI + 1){
                            nextDayCompleteHourly.add(allSingleForecastData.get(i));
                        }else {
                            nextDayCompleteHourly.add(allSingleForecastData.get(i));                                     //1:59 -> 3:00, deswegen zwei mal den wert von 1 uhr speichern
                            nextDayCompleteHourly.add(allSingleForecastData.get(i));
                        }
                    }
                    if (dayOfI != dayOfIPlusOne) {
                        counterNewDay += 1;
                    }
                } else break;
            }

        }else if (allSingleForecastData.size() == 49) {
            if (allSingleForecastData.get(allSingleForecastData.size() - 1).getLocalDateTime().getHour() == 0) {
                for (int i = 0; i < 24; i++) {
                    currentDayRestHourly.add(allSingleForecastData.get(i));
                }
                for (int i = 24; i < 48; i++) {
                    nextDayCompleteHourly.add(allSingleForecastData.get(i));
                }
            } else {
                int counterNewDay = 0;
                for (int i = 0; i < allSingleForecastData.size(); i++) {
                    if (counterNewDay < 2) {
                        int dayOfI = allSingleForecastData.get(i).getLocalDateTime().getDayOfMonth();
                        int dayOfIPlusOne = allSingleForecastData.get(i + 1).getLocalDateTime().getDayOfMonth();
                        if (counterNewDay == 0) {
                            currentDayRestHourly.add(allSingleForecastData.get(i));
                        } else nextDayCompleteHourly.add(allSingleForecastData.get(i));
                        if (dayOfI != dayOfIPlusOne) {
                            counterNewDay += 1;
                        }
                    } else break;
                }
            }
        }else throw new IllegalStateException("Wrong amount of weather informations");

        List<ArrayList<SingleForecastData>> result = new ArrayList<>();
        result.add(currentDayRestHourly);
        result.add(nextDayCompleteHourly);
        return result;
    }

    public static List<CompleteDayForecast> getNextFourDays(String latitude, String longitude) throws IOException {

        String urlString = "https://api.darksky.net/forecast/" + APIKeys.getDarkSkyKey() + "/" + latitude + "," + longitude +
                "?lang=de&exclude=currently,minutely,hourly,alerts,flags&units=auto";
        ArrayList allDataList = listFromURL(urlString);

        ArrayList<CompleteDayForecast> dayThreeDayFour = new ArrayList<>();
        dayThreeDayFour.add(createCompleteDayForecast(allDataList.get(0).toString()));
        dayThreeDayFour.add(createCompleteDayForecast(allDataList.get(1).toString()));
        dayThreeDayFour.add(createCompleteDayForecast(allDataList.get(2).toString()));
        dayThreeDayFour.add(createCompleteDayForecast(allDataList.get(3).toString()));

        return dayThreeDayFour;
    }

    //Auxiliary Methods
    private static String getFromUrl(String url) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL link = new URL(url);
        URLConnection connection = link.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null){
            sb.append(line);
        }
        rd.close();
        return sb.toString();
    }

    private static ArrayList listFromURL(String urlString) throws IOException {

        String json = getFromUrl(urlString);
        json = json.substring(json.indexOf("\"data\""));
        json = "{" + json;
        int temp = json.indexOf(",\"offset\"");
        json = json.substring(0, temp);

        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        JsonArray jsonArr = jsonObject.getAsJsonArray("data");
        Gson gson = new Gson();
        ArrayList allDataList = gson.fromJson(jsonArr, ArrayList.class);
        return allDataList;
    }

    private static SingleForecastData createSingleForecastData(String json){
        String[] allAttributes = json.split(",");

        String description = "";
        String unixTimeStamp = "";
        int temp = 0;
        double precipProbabilityInPercent = 0.0;
        String kindOfPrecipitation = "";

        for (int i = 0; i < allAttributes.length; i++) {
            if (allAttributes[i].contains("time")){
                unixTimeStamp = allAttributes[i].substring(allAttributes[i].indexOf("=")+1);
            }else if (allAttributes[i].contains("summary")){
                description = allAttributes[i].substring(allAttributes[i].indexOf("=")+1);
            }else if (allAttributes[i].contains("temperature")){
                temp = (int) Double.parseDouble(allAttributes[i].substring(allAttributes[i].indexOf("=")+1));
            }else if (allAttributes[i].contains("precipProbability")){
                precipProbabilityInPercent = Math.round(Double.parseDouble(allAttributes[i].substring(allAttributes[i].indexOf("=")+1)) * 100.0);
            }else if (allAttributes[i].contains("precipType")){
                kindOfPrecipitation = allAttributes[i].substring(allAttributes[i].indexOf("=")+1);
            }
        }

        if (kindOfPrecipitation.equals("")){
            kindOfPrecipitation = "No Precipitation";
        }

        LocalDateTime localDateTime = unixToLocalDateTime(unixTimeStamp);
        return new SingleForecastData(temp, precipProbabilityInPercent, kindOfPrecipitation, description, localDateTime);
    }

    private static CompleteDayForecast createCompleteDayForecast(String json){
        String[] allAttributes = json.split(",");

        int high = 0;
        int low = 0;
        double precipitationProb = 0.0;
        String kindOfPrecipitation = "";
        String shortDescription = "";
        LocalDate localDate = null;


        for (int i = 0; i < allAttributes.length; i++) {
            if (allAttributes[i].contains("time")){
                localDate = unixToLocalDateTime(allAttributes[i].substring(allAttributes[i].indexOf("=") + 1)).toLocalDate();
            }else if (allAttributes[i].contains("precipProbability")){
                precipitationProb = Math.round(Double.parseDouble(allAttributes[i].substring(allAttributes[i].indexOf("=") + 1)) * 100.0);
            }else if (allAttributes[i].contains("precipType")){
                kindOfPrecipitation = allAttributes[i].substring(allAttributes[i].indexOf("=") + 1);
            }else if (allAttributes[i].contains("temperatureHigh")){
                if (!allAttributes[i].contains("temperatureHighTime")){
                    high = (int) Double.parseDouble(allAttributes[i].substring(allAttributes[i].indexOf("=") + 1));
                }
            }else if (allAttributes[i].contains("temperatureLow")){
                if (!allAttributes[i].contains("temperatureLowTime")){
                    low = (int) Double.parseDouble(allAttributes[i].substring(allAttributes[i].indexOf("=") + 1));
                }
            }
        }

        if (!allAttributes[2].contains("=")){
            shortDescription = allAttributes[1].substring(allAttributes[1].indexOf("=") + 1) + allAttributes[2];
        }else shortDescription = allAttributes[1].substring(allAttributes[1].indexOf("=") + 1);


        //high, low, prob, kindOfPre, desc, date
        return new CompleteDayForecast(high, low, precipitationProb, kindOfPrecipitation, shortDescription, localDate);
    }

    private static LocalDateTime unixToLocalDateTime(String unixTimeStamp){
        long unix = Double.valueOf(unixTimeStamp).longValue();
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unix), TimeZone.getDefault().toZoneId());
    }
}
