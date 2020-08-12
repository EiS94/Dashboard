package Location;

import com.google.maps.errors.ApiException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class Location {

    private int zipCode;
    private String city, street, country, region;
    private Coordinates coordinates;

    public Location(int zipCode, String city, String street, String region, String country, Coordinates coordinates) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.region = region;
        this.country = country;
        this.coordinates = coordinates;
    }

    public Location(String city) throws InterruptedException, ApiException, IOException, ParseException {
        this.coordinates = Coordinates.getPlaceCoordinates(city);
        Location location = Location.getLocationFromCoordinates(this.coordinates);
        this.country = location.country;
        this.region = location.region;
        this.street = location.street;
        this.city = location.city;
        this.zipCode = location.zipCode;
    }

    public Location(String city, String street) throws InterruptedException, ApiException, IOException, ParseException {
        this.coordinates = Coordinates.getPlaceCoordinates(city + " " + street);
        Location location = Location.getLocationFromCoordinates(this.coordinates);
        this.country = location.country;
        this.region = location.region;
        this.street = street;
        this.city = city;
        this.zipCode = location.zipCode;
    }

    public static Location getLocationFromCoordinates(Coordinates c) throws IOException, ParseException {
        URL url = new URL("https://geocode.xyz/" + c.getLatitude() + "," + c.getLongitude() + "?geoit=json");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();

        StringBuilder sb = new StringBuilder();
        if (status < 299) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
        }

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(sb.toString());

        String city = convert((String) json.get("city"));
        String street = convert((String) json.get("staddress"));
        String country = convert((String) json.get("country"));
        String region = convert((String) json.get("region"));
        int zip = 0;
        try {
            zip = Integer.parseInt((String) json.get("postal"));
        } catch (Exception e) {

        }

        return new Location(zip, city, street, region, country, c);
    }

    public static String convert(String original) {
        original = original.replace("Ã¼", "ü").replace("ÃŸ", "ß").
                replace("Ã¶", "ö").replace("Ã¤", "ä").replace(
                "Ã„", "Ä").replace("Ã–", "Ö").replace("Ãœ", "Ü");
        return original;
    }

    @Override
    public String toString() {
        return zipCode + " " + city + "\n" + street + "\n" + region + "\n" + country + "\n" + coordinates.toString();
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
