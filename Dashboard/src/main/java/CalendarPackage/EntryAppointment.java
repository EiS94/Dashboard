package CalendarPackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EntryAppointment extends CalendarEvent{

    private EntryType type = EntryType.APPOINTMENT;

    private Optional<LocalTime> startTime;
    private Optional<LocalTime> endTime;

    private String name;
    private LocalDate startDate;
    private Optional<LocalDate> endDate;
    private Optional<String> destinationLocation;


    public EntryAppointment(String name, LocalDate startDate, Optional<LocalDate> endDate, Optional<LocalTime> startTime, Optional<LocalTime> endTime, Optional<String> destinationLocation) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destinationLocation = destinationLocation;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public EntryType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public Optional<LocalDate> getEndDate() {
        return endDate;
    }

    public void setEndDate(Optional<LocalDate> endDate) {
        this.endDate = endDate;
    }

    @Override
    public Optional<String> getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(Optional<String> destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    @Override
    public LocalDateTime getLocalDateTimeForSort(){
        LocalDateTime result;
        if (startTime.isPresent()){
            result=startDate.atTime(startTime.get());
        }else result=startDate.atTime(23,59,0);
        return result;
    }

    @Override
    public Optional<LocalTime> getStartTime() {
        return startTime;
    }

    public void setStartTime(Optional<LocalTime> startTime) {
        this.startTime = startTime;
    }

    @Override
    public Optional<LocalTime> getEndTime() {
        return endTime;
    }

    public void setEndTime(Optional<LocalTime> endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTimeString;
        String endDateString;
        String endTimeString;
        String destination;
        if (endDate.isPresent()) endDateString = endDate.get().toString();
        else endDateString = "empty";
        if (startTime.isPresent()) startTimeString = startTime.get().format(dateTimeFormatter);
        else startTimeString = "empty";
        if (endTime.isPresent()) endTimeString = endTime.get().format(dateTimeFormatter);
        else endTimeString = "empty";
        if (destinationLocation.isPresent()) destination = destinationLocation.get().toString();
        else destination = "empty";
        return "Appointment;" + name + ";" + startDate.toString() + ";" + endDateString + ";" + startTimeString + ";"
                + endTimeString + ";" + destination;
    }
}
