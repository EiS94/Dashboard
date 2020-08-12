import Location.Location;
import Web.WebWeather;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import Location.Location;
import Location.Coordinates;
import Web.WebWeather;
import com.google.gson.Gson;
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
import java.time.LocalTime;
import java.util.ArrayList;
@WebServlet("/ControllerDW")
public class ControllerDestinationWeather extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> weather = null;
        Location location = null;
        String city = req.getParameter("nextPlace");


        try {
            location = new Location(city);


        } catch (Exception ignored) {

        }

        if(location != null){
            try {
                weather = WebWeather.getWebWeather(location);
            } catch (ParseException ignored) {

            }
        }
        else {
            try {
                weather = WebWeather.getWebWeather();
            } catch (ParseException | UnknownHostException ignored) {

            }
        }
        if(weather == null){
            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();
            out.print("Keine Internetverbindung");
        } else {
            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();
            out.print(weather.toString());
        }
    }
}
