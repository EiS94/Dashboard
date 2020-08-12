package CalendarPackage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Calendar {

    public ArrayList<CalendarEvent> allEvents;

    public Calendar() {
        allEvents = new ArrayList<>();
    }

    public Calendar(ArrayList events) {
        allEvents = events;
    }
    
    public void addAppointment(String name, LocalDate startDate, Optional<LocalDate> endDate, Optional<LocalTime> startTime, Optional<LocalTime> endTime, Optional<String> destinationLocation){
        EntryAppointment appointment = new EntryAppointment(name, startDate, endDate, startTime, endTime, destinationLocation);
        allEvents.add(appointment);
    }

    public void addAppointment(EntryAppointment appointment){
        allEvents.add(appointment);
    }

    public void addBirthday(String firstName, Optional<String> lastName, LocalDate date, int age, Optional<String> present){
        EntryBirthday birthday = new EntryBirthday(firstName, lastName, date, age, present);
        allEvents.add(birthday);
    }

    public void addBirthday(EntryBirthday birthday){
        allEvents.add(birthday);
    }

    public void addOther(String name, String description, LocalDate startDate, Optional<LocalTime> startTime, Optional<LocalDate> endDate, Optional<String> destinationLocation){
        EntryOther other = new EntryOther(name, description, startDate, startTime, endDate);
        allEvents.add(other);
    }

    public void addOther(EntryOther other){
        allEvents.add(other);
    }

    public void deleteEvent(CalendarEvent toDelete){
        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i).hashCode() == toDelete.hashCode()){
                allEvents.remove(i);
                return;
            }
        }
        System.out.println("toDelete not found");
    }

    public void editEvent(CalendarEvent toEdit, CalendarEvent edited){
        deleteEvent(toEdit);
        switch (edited.getType()){                                                                                      //the casts should not result in any errors because the
            case OTHER: {                                                                                               //type is already correct -> casts are just to let it compile
                addOther((EntryOther) edited);
                break;
            }
            case BIRTHDAY: {
                addBirthday((EntryBirthday) edited);
                break;
            }
            case APPOINTMENT: {
                addAppointment((EntryAppointment) edited);
                break;
            }
        }
    }

    public ArrayList<CalendarEvent> getAllEvents(){
        return allEvents;
    }

    public ArrayList<CalendarEvent> getEventsForYear(){
        ArrayList<CalendarEvent> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (CalendarEvent event: allEvents) {
            if (event.getStartDate().getYear()==today.getYear()){
                result.add(event);
            }
        }
        return result;
    }

    public ArrayList<CalendarEvent> getEventsForMonth(){
        ArrayList<CalendarEvent> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (CalendarEvent event: allEvents) {
            if (event.getStartDate().getMonth().equals(today.getMonth())){
                result.add(event);
            }
        }
        return result;
    }

    public ArrayList<CalendarEvent> getEventsForWeek(){
        ArrayList<CalendarEvent> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate oneWeekLater = today.plusDays(7);

        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i).getStartDate().isBefore(oneWeekLater)){
                result.add(allEvents.get(i));
            }else break;
        }
        return result;
    }

    public ArrayList<CalendarEvent> getEventsForToday(){
        ArrayList<CalendarEvent> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i).getStartDate().isEqual(today)){
                result.add(allEvents.get(i));
            }else break;
        }
        return result;
    }

    public void sortEvents(){
        allEvents.sort(Comparator.comparing(CalendarEvent::getLocalDateTimeForSort));
    }

    public void saveCalendar() throws IOException {
        String homeDir = System.getProperty("user.home") + "/Dashboard";
        File dir = new File(homeDir);
        dir.mkdir();
        StringBuilder stringBuilder = new StringBuilder();
        for (CalendarEvent allEvent : allEvents) {
            stringBuilder.append(allEvent.toString());
            stringBuilder.append("\n");
        }
        String toSave = stringBuilder.toString();
        Files.write(Paths.get(homeDir + "/calendarData.txt"), toSave.getBytes());
    }

    public ArrayList<CalendarEvent> loadCalendar() throws IOException {
        String homeDir = System.getProperty("user.home") + "/Dashboard";
        File dir = new File(homeDir);
        dir.mkdir();
        File toLoad = new File(homeDir + "/calendarData.txt");
        if (toLoad.createNewFile()){
            return new ArrayList<>();
        }
        List<String> dataList = Files.readAllLines(toLoad.toPath());
        for (String data : dataList) {
            if (data.startsWith("Other")){
                EntryOther other = createOther(data);
                addOther(other);
            }else if (data.startsWith("Appointment")){
                EntryAppointment appointment = createAppointment(data);
                addAppointment(appointment);
            }else {
                EntryBirthday birthday = createBirthday(data);
                addBirthday(birthday);
            }
        }
        return getAllEvents();
    }

    public ArrayList<CalendarEvent> loadCalendarFromFilepath(String path) throws IOException {
        File toLoad = new File(path);
        if (toLoad.createNewFile()){
            return new ArrayList<>();
        }
        List<String> dataList = Files.readAllLines(toLoad.toPath());
        for (String data : dataList) {
            if (data.startsWith("Other")){
                EntryOther other = createOther(data);
                addOther(other);
            }else if (data.startsWith("Appointment")){
                EntryAppointment appointment = createAppointment(data);
                addAppointment(appointment);
            }else {
                EntryBirthday birthday = createBirthday(data);
                addBirthday(birthday);
            }
        }
        return getAllEvents();
    }

    public EntryOther createOther(String data){
        String[] dataArray = data.split(";");

        if (dataArray.length != 6){
            System.out.println(dataArray.length);
            System.out.println("illegal length in calendarData.txt while loading EntryOther");
        }

        String name = dataArray[1];
        String description = dataArray[2];
        LocalDate localDate = LocalDate.parse(dataArray[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<LocalTime> startTime;
        if (dataArray[4].equals("empty")){
            startTime = Optional.empty();
        }else startTime = Optional.of(LocalTime.parse(dataArray[4]));
        Optional<LocalDate> endDate;
        if (dataArray[5].equals("empty")){
            endDate = Optional.empty();
        }else endDate = Optional.of(LocalDate.parse(dataArray[5], DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return new EntryOther(name, description, localDate, startTime, endDate);
    }

    public EntryAppointment createAppointment(String data){
        String[] dataArray = data.split(";");

        if (dataArray.length != 7){
            System.out.println("illegal length in calendarData.txt while loading EntryAppointment");
        }

        String name = dataArray[1];
        LocalDate startDate = LocalDate.parse(dataArray[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<LocalDate> endDate;
        if (dataArray[3].equals("empty")){
            endDate = Optional.empty();
        }else endDate = Optional.of(LocalDate.parse(dataArray[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Optional<LocalTime> startTime;
        if (dataArray[4].equals("empty")){
            startTime = Optional.empty();
        }else startTime = Optional.of(LocalTime.parse(dataArray[4]));
        Optional<LocalTime> endTime;
        if (dataArray[5].equals("empty")){
            endTime = Optional.empty();
        }else endTime = Optional.of(LocalTime.parse(dataArray[5]));
        Optional<String> destinationLocation;
        if (dataArray[6].equals("empty")){
            destinationLocation = Optional.empty();
        }else destinationLocation = Optional.of(dataArray[6]);

        return new EntryAppointment(name, startDate, endDate, startTime, endTime, destinationLocation);
    }

    public EntryBirthday createBirthday(String data){
        String[] dataArray = data.split(";");

        if (dataArray.length != 6){
            System.out.println("illegal length in calendarData.txt while loading EntryBirthday");
        }

        String firstName = dataArray[1];
        Optional<String> lastName;
        if (dataArray[2].equals("empty")){
            lastName = Optional.empty();
        }else lastName = Optional.of(dataArray[2]);
        LocalDate date = LocalDate.parse(dataArray[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int age = Integer.parseInt(dataArray[4]);
        Optional<String> present;
        if (dataArray[5].equals("empty") || dataArray[5].equals("")){
            present = Optional.empty();
        }else present = Optional.of(dataArray[5]);

        return new EntryBirthday(firstName, lastName, date, age, present);
    }
}
