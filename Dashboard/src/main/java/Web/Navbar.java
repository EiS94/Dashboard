package Web;
import Location.Coordinates;
import Location.Location;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Navbar {
    public static String getNavbar()  {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
        //Ort+Uhrzeit+TagUndDatum
        String cString = null;
        try {
            cString = Coordinates.getNameFromIP();
        } catch (IOException e) {
            cString = "Keine Internetverbindung";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cString +", "+ LocalTime.now().format(dtf) + " Uhr, " + LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    public static String getNavbar(Location location) throws IOException, ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
        //Ort+Uhrzeit+TagUndDatum
        String city = null;
        if (location != null) city = location.getCity();
        else city = "Keine Internetverbindung";
        return city +", "+ LocalTime.now().format(dtf) + " Uhr, " + LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }
}
