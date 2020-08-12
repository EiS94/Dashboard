package CalendarPackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public abstract class CalendarEvent {

    public EntryType getType() {
        return null;
    }

    public LocalDate getStartDate(){
        return null;
    }

    public String getName() {
        return null;
    }

    public Optional<LocalDate> getEndDate() {
        return Optional.empty();
    }

    public Optional<String> getDestinationLocation() {
        return Optional.empty();
    }

    public Optional<LocalTime> getStartTime() {
        return null;
    }       // == null if entry != appointment

    public Optional<LocalTime> getEndTime() {
        return Optional.empty();
    }

    public LocalDateTime getLocalDateTimeForSort(){return null;}

    @Override
    public String toString() {
        return super.toString();
    }
}
