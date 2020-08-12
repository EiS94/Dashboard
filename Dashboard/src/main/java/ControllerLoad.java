import CalendarPackage.Calendar;
import CalendarPackage.CalendarEvent;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ControllerLoad")
public class ControllerLoad extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Calendar cal = new Calendar();
        cal.loadCalendar();
        StringBuilder sb = new StringBuilder();
        for (CalendarEvent e : cal.getAllEvents()){
            sb.append(e.toString());
            sb.append("\n");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        String res = sb.toString();
        resp.setContentType("text/plain");
        resp.getWriter().write(res);    //all events to add according to toString separated by \n
    }
}
