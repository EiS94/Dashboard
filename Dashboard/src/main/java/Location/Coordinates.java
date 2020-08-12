package Location;

import Keys.APIKeys;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.internal.GeolocationResponseAdapter;
import com.google.maps.model.GeocodingResult;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class Coordinates {

    private double longitude, latitude;

    public Coordinates(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "lat: " + latitude + ", long: " + longitude;
    }

    public static Coordinates getPlaceCoordinates(String place) throws InterruptedException, ApiException, IOException {
        GeolocationResponseAdapter adapter = new GeolocationResponseAdapter();
        GeocodingApiRequest request = new GeocodingApiRequest(APIKeys.getApiGoogleContext());
        request.address(place);
        GeocodingResult[] results = request.await();
        return new Coordinates(results[0].geometry.location.lat, results[0].geometry.location.lng);
    }

    public static String getNameFromIP() throws IOException, ParseException {

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
        String ip = in.readLine();

        JSONObject json = getRequest(ip);

        return (String) json.get("city");
    }

    private static JSONObject getRequest(String ip) throws IOException, ParseException {
        URL url = new URL("http://ip-api.com/json/" + ip);

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

        return json;
    }

    public static Coordinates getPlaceCoordinatesFromIP() throws IOException, ParseException {

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        InputStream stream = null;
        try {
            stream = whatismyip.openStream();
        } catch (UnknownHostException e) {
            System.out.println("Verbindung zum Server konnte nicht hergestellt werden");
        }

        String ip = null;
        if (stream != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            ip = in.readLine();
        }
        return getCoordinates(ip);
    }

    private static Coordinates getCoordinates(String ip) throws IOException, ParseException {
        if (ip == null) return null;

        JSONObject json = getRequest(ip);

        //System.out.println("IP-Adresse: " + ip + " -> Standort: " + json.get("city"));

        return new Coordinates((Double) json.get("lat"), (Double) json.get("lon"));
    }

    public static Coordinates getPlaceCoordinatesFromIP(String ip) throws IOException, ParseException {
        return getCoordinates(ip);
    }

    public static String getPlaceFromCoordinates(Coordinates c) throws IOException, ParseException {
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

        return (String) json.get("city");
    }
}
