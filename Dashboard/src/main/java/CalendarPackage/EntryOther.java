package CalendarPackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EntryOther extends CalendarEvent{

    private EntryType type = EntryType.OTHER;
    private String description;

    private String name;
    private LocalDate startDate;
    private Optional<LocalTime> startTime;
    private Optional<LocalDate> endDate;



    public EntryOther(String name, String description, LocalDate startDate, Optional<LocalTime> startTime, Optional<LocalDate> endDate) {
        this.description = description;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
    }

    @Override
    public Optional<LocalTime> getStartTime() {
        return startTime;
    }

    @Override
    public EntryType getType() {
        return type;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<LocalDate> getEndDate() {
        return endDate;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDateTime getLocalDateTimeForSort(){
        LocalDateTime result;
        if (startTime.isPresent()){
            result=startDate.atTime(startTime.get());
        }else result=startDate.atTime(23,59, 0);
        return result;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Optional<LocalDate> endDate) {
        this.endDate = endDate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String endDateString;
        String destination;
        String startTimeString;
        if (endDate.isPresent()) endDateString = endDate.get().toString();
        else endDateString = "empty";
        if (startTime.isPresent())startTimeString = startTime.get().format(dateTimeFormatter);
        else startTimeString = "empty";
        return "Other;" + name + ";" + description + ";" + startDate.toString() + ";" + startTimeString + ";" +
                endDateString;
    }

}
