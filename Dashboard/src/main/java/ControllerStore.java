import CalendarPackage.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ControllerStore")
public class ControllerStore extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Calendar cal = new Calendar();
        String[] dataList = req.getParameter("result").split("\n");
        if (!dataList[0].equals("empty")) {
            for (String data : dataList) {
                if (data.startsWith("Other")) {
                    EntryOther other = cal.createOther(data);
                    cal.addOther(other);
                } else if (data.startsWith("Appointment")) {
                    EntryAppointment appointment = cal.createAppointment(data);
                    cal.addAppointment(appointment);
                } else {
                    EntryBirthday birthday = cal.createBirthday(data);
                    cal.addBirthday(birthday);
                }
            }
        }
        cal.saveCalendar();
    }
}
