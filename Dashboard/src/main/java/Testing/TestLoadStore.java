package Testing;

import CalendarPackage.Calendar;
import CalendarPackage.CalendarEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class TestLoadStore {


    public static void main(String[] args) throws IOException {
        Calendar cal = new Calendar();
        cal.addBirthday("Michael", Optional.of("Bendel"), LocalDate.of(1999, 1, 7), 21, Optional.empty());
        cal.addBirthday("Michael", Optional.of("Bendel2"), LocalDate.of(1998, 1, 7), 21, Optional.empty());
        cal.addBirthday("Michael", Optional.of("Bendel3"), LocalDate.of(1997, 1, 7), 21, Optional.empty());
        cal.saveCalendar();
        System.out.println("events from cal");
        for (CalendarEvent e : cal.getAllEvents()){
            System.out.println(e.toString());
        }
        Calendar cal2 = new Calendar();
        cal2.loadCalendar();
        System.out.println("\nEvents from cal2:");
        for (CalendarEvent e : cal2.getAllEvents()){
            System.out.println(e.toString());
        }
    }

}
