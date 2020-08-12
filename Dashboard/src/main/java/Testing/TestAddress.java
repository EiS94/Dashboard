package Testing;

import Location.Address;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestAddress {

    public static void main(String[] args) throws Exception {

    }

    public static void testNormalAddress() throws Exception{
        String[] arr = Address.getGeoLocation();
        System.out.println("Lat: " + arr[0]);
        System.out.println("Lon: " + arr[1]);
    }

}
