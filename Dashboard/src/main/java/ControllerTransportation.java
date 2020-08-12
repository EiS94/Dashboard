import Location.Coordinates;
import Location.Location;
import Web.Transport;
import com.google.maps.errors.ApiException;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@WebServlet("/ControllerTransportation")
public class ControllerTransportation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lat = req.getParameter("currLat");
        String lon = req.getParameter("currLon");
        Location startPoint = null;
        if (lat != null && lon != null) {
            try {
                startPoint = Location.getLocationFromCoordinates(new Coordinates(Double.parseDouble(lat), Double.parseDouble(lon)));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {

            }
        }

        String[] appointment = null;
        LocalDateTime appointmentTime = null;
        String a = req.getParameter("a");
        if (!a.equals("Keine ausstehenden Termine") && !a.equals("Nächster Termin hat keinen Zielort")) {
            if (a != null) {
                a = a.substring(0, a.length() - 2);
                appointment = a.split("##");

                String[] time = appointment[2].split(",");

                //set Time to 23:59 if no time is set by User
                if (time[1].equals("-")) time[1] = "23:59";
                //delets the seconds if available
                if (time[1].length() > 5) time[1] = time[1].substring(0, time[1].length() - 3);


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                appointmentTime = LocalDateTime.parse(time[0] + " " + time[1], formatter);
            }

            ArrayList<String> t = new ArrayList<>();
            if (appointment != null && appointmentTime != null && startPoint != null) {
                try {
                    t = Web.Transport.getTransport(startPoint, appointment[1], appointmentTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

            StringBuilder sb = new StringBuilder();
            if (t.size() > 0) {
                sb.append(t.get(0)).append("##");
            }

            String possibleTransitions = Transport.getPossibleTransitions(t);
            sb.append(possibleTransitions);

            if (sb.toString().equals("")) {
                sb.append("Keine Internetverbindung");
            }

            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();
            out.print(sb.toString());
        } else {
            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();
            out.print(a);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
