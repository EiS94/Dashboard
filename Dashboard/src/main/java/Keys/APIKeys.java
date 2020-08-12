package Keys;

import com.google.maps.GeoApiContext;

public class APIKeys {

    //Transit Keys:
    //please insert own Google-API-Key
    public static String getGoogleKey() {
        return "";
    }
    public static GeoApiContext getApiGoogleContext() {
        GeoApiContext.Builder contextHelper = new GeoApiContext.Builder().apiKey(getGoogleKey());
        return contextHelper.build();
    }

    //IPGeo Key:
    //please insert own IPGeo-Key
    public static String getIPGeoKey() {
        return "30dab8a36fa54c0a987c733d50f9f804";
    }

    //Weather Key
    //please insert own DarkSky-Key
    public static String getDarkSkyKey(){
        return "5640e4e5d97b320808f494bb468a2ac2";
    }

}
