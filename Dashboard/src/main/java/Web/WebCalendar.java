package Web;

import CalendarPackage.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class WebCalendar {
    public static Calendar getCalendar() throws IOException {
        LocalDate mybirth = LocalDate.of(1994, 4, 21);
        LocalDate birth = LocalDate.of(1992, 4, 21);
        EntryBirthday mybirthday = new EntryBirthday("Lydia", Optional.empty(), mybirth, 25, Optional.of("Bier"));
        EntryBirthday bday = new EntryBirthday("Max", Optional.of("Mustermann"), LocalDate.of(1913,1,25), 107, Optional.of("Schnaps"));
        EntryOther other = new EntryOther("Österreich", "Urlaub", LocalDate.of(2020, 2, 20), Optional.of(LocalTime.of(11, 0, 0)), Optional.empty());
        EntryAppointment app1 = new EntryAppointment("Vortrag", LocalDate.of(2020, 1, 27), Optional.empty(), Optional.of(LocalTime.of(15,15)), Optional.empty(), Optional.of("Wien"));
        EntryAppointment app2 = new EntryAppointment("Hautarzt", LocalDate.of(2020,1,30), Optional.empty(), Optional.of(LocalTime.of(10,51)), Optional.empty(), Optional.of("Schweinfurt Hadergasse 40"));
        EntryAppointment app3 = new EntryAppointment("bla", LocalDate.of(2022,1,29), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of("Berlin"));
        EntryAppointment app4 = new EntryAppointment("Geburtstagsfeier", LocalDate.of(2020,1,25), Optional.empty(), Optional.of(LocalTime.of(20,30,0)), Optional.empty(), Optional.of("Bochum"));
        /*EntryOther other = new EntryOther("Österreich", "Urlaub", LocalDate.of(2020, 2, 20), Optional.of(LocalTime.of(11, 0, 0)), Optional.empty();
        EntryAppointment app1 = new EntryAppointment("Vortrag", LocalDate.of(2020, 1, 29), Optional.empty(), Optional.of(LocalTime.of(15,15,15)), Optional.empty(), Optional.of("Wien"));
        EntryAppointment app2 = new EntryAppointment("Hautarzt", LocalDate.of(2020,1,30), Optional.empty(), Optional.of(LocalTime.of(10,51)), Optional.empty(), Optional.of("Schweinfurt Hadergasse 40"));
        EntryAppointment app3 = new EntryAppointment("bla", LocalDate.of(2022,1,27), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of("Berlin"));
        EntryAppointment app4 = new EntryAppointment("Geburtstagsfeier", LocalDate.of(2020,1,25), Optional.empty(), Optional.of(LocalTime.of(10,56,0)), Optional.empty(), Optional.of("Bochum"));*/
        EntryBirthday birthday = new EntryBirthday("Kurt", Optional.empty(), birth, 27, Optional.empty());

        Calendar calendar = new Calendar();
        calendar.addBirthday(mybirthday);
        calendar.addOther(other);
        calendar.addOther(other);
        calendar.addOther(other);
        calendar.addOther(other);
        calendar.addOther(other);
        calendar.addAppointment(app1);
        calendar.addAppointment(app2);
        calendar.addAppointment(app3);
        calendar.addAppointment(app4);
        calendar.addBirthday(bday);
        calendar.addBirthday(mybirthday);
        calendar.addBirthday(birthday);
        calendar.addBirthday(birthday);
        //calendar.loadCalendar();
        //calendar.saveCalendar();

        return calendar;

    }

    public static ArrayList<CalendarEvent> appointmentsFor(Calendar ca, String s){
        ca.sortEvents();
        ArrayList<CalendarEvent> all = ca.getAllEvents();
        ArrayList<CalendarEvent> year =ca.getEventsForYear();
        ArrayList<CalendarEvent> month = ca.getEventsForMonth();
        ArrayList<CalendarEvent> week = ca.getEventsForWeek();
        ArrayList<CalendarEvent> today = ca.getEventsForToday();
        if (s.equals("all")){
            return all;
        }else if (s.equals("year")){
            return year;
        }
        else if (s.equals("month")){
            return month;
        }
        else if (s.equals("week")){
            return week;
        }else return today;
    }

    public static String getTableOfAll(ArrayList<CalendarEvent> events, EntryType type) {
        ArrayList<EntryBirthday> birthdays = new ArrayList<>();
        ArrayList<EntryAppointment> apps = new ArrayList<>();
        ArrayList<EntryOther> others = new ArrayList<>();
        for (CalendarEvent event : events) {
            if (event.getType().equals(EntryType.BIRTHDAY)) {
                birthdays.add((EntryBirthday) event);
            } else if (event.getType().equals(EntryType.APPOINTMENT)) {
                apps.add((EntryAppointment) event);
            } else others.add((EntryOther) event);
        }
        StringBuilder sbBirth = new StringBuilder();



        for (EntryBirthday birth : birthdays) {
            String lastname;
            String present;
            if (birth.getLastName().isPresent()) {
                lastname = birth.getLastName().get();
            } else lastname = "-";
            if (birth.getPresent().isPresent()) {
                present = birth.getPresent().get();
            } else present = "-";
            sbBirth.append("<tr class=\"birthdayRow\"><th scope=\"col\"></th>"
                    + "<td>" + birth.getFirstName() +","+ lastname + "</td>"
                    + "<td>" + birth.getStartDate() + "</td>"
                    + "<td>" + birth.getAge() + "</td>"
                    + "<td>" + present + "</td>");
        }
        StringBuilder sbApps = new StringBuilder();
        for (EntryAppointment app : apps) {
            String destLoc;
            String startTime;
            String endDate;
            String endTime;
            if (app.getDestinationLocation().isPresent()) {
                destLoc = app.getDestinationLocation().get();
            } else destLoc = "-";
            if (app.getStartTime().isPresent()) {
                startTime = app.getStartTime().get().toString();
            } else startTime = "-";
            if (app.getEndDate().isPresent()) {
                endDate = app.getEndDate().get().toString();
            } else endDate = "-";
            if (app.getEndTime().isPresent()) {
                endTime = app.getEndTime().get().toString();
            } else endTime = "-";
            sbApps.append("<tr class=\"appRow\"><th scope=\"col\"></th>"
                    + "<td>" + app.getName() + "</td>"
                    + "<td>" + destLoc + "</td>"
                    + "<td>" + app.getStartDate() + "," + startTime + "</td>"
                    + "<td>" + endDate + "," + endTime + "</td>"
            );
        }
        StringBuilder sbOther = new StringBuilder();
        for (EntryOther other : others) {
            String startTime;
            String endDate;
            if (other.getStartTime().isPresent()) {
                startTime = other.getStartTime().get().toString();
            } else startTime = "-";
            if (other.getEndDate().isPresent()) {
                endDate = other.getEndDate().get().toString();
            } else endDate = "-";
            sbOther.append("<tr class=\"otherRow\"><th scope=\"col\"></th>"
                    + "<td>" + other.getName() + "</td>"
                    + "<td>" + other.getStartDate() + "," + startTime + "</td>"
                    + "<td>" + endDate + "</td>"
                    + "<td>" + other.getDescription() + "</td>"
            );
        }

        if (type.equals(EntryType.BIRTHDAY)) {
            return sbBirth.toString();
        } else if (type.equals(EntryType.APPOINTMENT)) {
            return sbApps.toString();
        } else return sbOther.toString();
    }

}
