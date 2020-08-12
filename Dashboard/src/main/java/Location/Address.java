package Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;


public class Address {

    public static String[] getGeoLocation() throws Exception{
        String address = InetAddress.getLocalHost().getHostAddress();


        String json = getFromUrl("http://ip-api.com/json/" + address);
        String[] allData = json.split(",");

        if (!allData[0].contains("success")){
            throw new Exception("IP to GeoAddress was unsuccessful");
        }
        String lat = allData[7].substring(allData[7].indexOf(":") + 1);
        String lon = allData[8].substring(allData[8].indexOf(":") + 1);
        return new String[]{lat, lon};
    }

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
}
