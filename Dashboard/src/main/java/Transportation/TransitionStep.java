package Transportation;

import com.google.maps.model.TransitMode;
import com.google.maps.model.TravelMode;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class TransitionStep {

    private String description, distance, duration, startPoint, endPoint;
    private LocalTime arrivalTime, departureTime;
    private Optional<String> vehicleNumber, vehicleDirection;
    TravelMode mode;

    public TransitionStep(String description, String distance, String duration, String startPoint, String endPoint,LocalTime departureTime, LocalTime arrivalTime, Optional<String> vehicleNumber, Optional<String> vehicleDirection, TravelMode mode) {
        if (mode == TravelMode.TRANSIT) {
            if (description.length() == 0 || distance.length() == 0 || duration.length() == 0 || startPoint.length() == 0 ||
                    endPoint.length() == 0) throw new IllegalArgumentException("Ein Parameter hat keinen Inhalt");
        } else if (mode == TravelMode.WALKING) {
            if (description.length() == 0 || distance.length() == 0 || duration.length() == 0)
                throw new IllegalArgumentException("Ein Parameter hat keinen Inhalt");
        } else if (mode == TravelMode.UNKNOWN || mode == TravelMode.DRIVING)
            throw new IllegalArgumentException("Keine Zugverbindung gefunden");
        this.description = description;
        this.distance = distance;
        this.duration = duration;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.vehicleNumber = vehicleNumber;
        this.vehicleDirection = vehicleDirection;
        this.mode = mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }


    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public Optional<String> getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(Optional<String> vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Optional<String> getVehicleDirection() {
        return vehicleDirection;
    }

    public void setVehicleDirection(Optional<String> vehicleDirection) {
        this.vehicleDirection = vehicleDirection;
    }

    public TravelMode getMode() {
        return mode;
    }

    public void setMode(TravelMode mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransitionStep that = (TransitionStep) o;

        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(distance, that.distance)) return false;
        if (!Objects.equals(duration, that.duration)) return false;
        if (!Objects.equals(startPoint, that.startPoint)) return false;
        if (!Objects.equals(endPoint, that.endPoint)) return false;
        if (!Objects.equals(arrivalTime, that.arrivalTime)) return false;
        if (!Objects.equals(departureTime, that.departureTime)) return false;
        if (!Objects.equals(vehicleNumber, that.vehicleNumber)) return false;
        if (!Objects.equals(vehicleDirection, that.vehicleDirection)) return false;
        return mode == that.mode;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (startPoint != null ? startPoint.hashCode() : 0);
        result = 31 * result + (endPoint != null ? endPoint.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (vehicleNumber.isPresent() ? vehicleNumber.hashCode() : 0);
        result = 31 * result + (vehicleDirection.isPresent() ? vehicleDirection.hashCode() : 0);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (mode == TravelMode.TRANSIT) {
            String[] split = description.split(" ");
            return split[0] + ": " + departureTime + " " + startPoint + " -> " + arrivalTime + " " + endPoint;
        } else return description;
    }
}
