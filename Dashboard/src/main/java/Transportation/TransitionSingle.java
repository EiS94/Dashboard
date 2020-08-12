package Transportation;

import Location.Coordinates;
import com.google.maps.model.TravelMode;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class TransitionSingle {

    private List<TransitionStep> transition;
    private String duration, startAddress, endAddress;
    private LocalTime departureTime, arrivalTime;
    private Coordinates startCoordinates, endCoordinates;

    public TransitionSingle(List<TransitionStep> transition, String duration, String startAddress, String endAddress,
                            LocalTime departureTime, LocalTime arrivalTime, Coordinates startCoordinates, Coordinates endCoordinates) {
        this.transition = transition;
        this.duration = duration;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
    }

    public int getMoves() {
        int counter = 0;
        for (TransitionStep step : transition) {
            if (step.mode == TravelMode.TRANSIT) {
                counter++;
            }
        }
        if (counter >= 1) counter--;
        return counter;
    }

    public Coordinates getStartCoordinates() {
        return startCoordinates;
    }

    public void setStartCoordinates(Coordinates startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public Coordinates getEndCoordinates() {
        return endCoordinates;
    }

    public void setEndCoordinates(Coordinates endCoordinates) {
        this.endCoordinates = endCoordinates;
    }

    public List<TransitionStep> getTransition() {
        return transition;
    }

    public void setTransition(List<TransitionStep> transition) {
        this.transition = transition;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransitionSingle that = (TransitionSingle) o;

        if (!Objects.equals(transition, that.transition)) return false;
        if (!Objects.equals(duration, that.duration)) return false;
        if (!Objects.equals(startAddress, that.startAddress)) return false;
        if (!Objects.equals(endAddress, that.endAddress)) return false;
        if (!Objects.equals(departureTime, that.departureTime)) return false;
        return Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        int result = transition != null ? transition.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (startAddress != null ? startAddress.hashCode() : 0);
        result = 31 * result + (endAddress != null ? endAddress.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (departureTime != null && arrivalTime != null) return startAddress + " -> " + endAddress + "; Abfahrtszeit: " + departureTime.toString() +"; Ankunftszeit: " +
                arrivalTime.toString() + "; Dauer: " + duration + "; Umstiege: " + getMoves();
        else return startAddress + " -> " + endAddress + "; Dauer: " + duration + "; Umstiege: " + getMoves();
    }
}
