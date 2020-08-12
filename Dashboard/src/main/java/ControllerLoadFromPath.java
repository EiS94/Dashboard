import CalendarPackage.Calendar;
import CalendarPackage.CalendarEvent;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ControllerLoadFromPath")
public class ControllerLoadFromPath extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("filepath");
        Calendar cal = new Calendar();
        cal.loadCalendarFromFilepath(path);

        StringBuilder sb = new StringBuilder();
        for (CalendarEvent e : cal.getAllEvents()){
            sb.append(e.toString());
            sb.append("\n");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        String res = sb.toString();
        resp.getWriter().write(res);    //all events to add according to toString separated by \n
    }
}
