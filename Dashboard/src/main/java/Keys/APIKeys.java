package Keys;

import com.google.maps.GeoApiContext;

public class APIKeys {

    //Transit Keys:
    public static String getGoogleKey() {
        return "AIzaSyAGQiTWX-Cf3IwTlyl5S7Wp8BEjCpz_cY8";
    }
    public static GeoApiContext getApiGoogleContext() {
        GeoApiContext.Builder contextHelper = new GeoApiContext.Builder().apiKey(getGoogleKey());
        return contextHelper.build();
    }

    //IPGeo Key:
    public static String getIPGeoKey() {
        return "30dab8a36fa54c0a987c733d50f9f804";
    }

    //Weather Key
    public static String getDarkSkyKey(){
        return "5640e4e5d97b320808f494bb468a2ac2";
    }

}
