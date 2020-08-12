package Web;

import java.util.ArrayList;

public class AppointmentTable {
    public static String getTable() {
        ArrayList<String> app=new ArrayList<>();
        // hier müssten die Daten/Termine aus dem Kalender abgerufen werden
        String row1 = "12:00, Würzburg, 8070";
        String row2 ="15:00, Köln, 1234, 7";
        app.add(row1);
        app.add(row2);
        StringBuilder sb=new StringBuilder();
        for (String obj : app){
            sb.append("</br><tr><td>").append(obj).append("</td></tr>");
        }
        return sb.toString();
    }

}
