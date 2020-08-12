package Transportation;

import Location.Coordinates;
import Keys.APIKeys;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TravelMode;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Transit {

    List<TransitionSingle> allPossibleTransitions;

    public Transit(List<TransitionSingle> allPossibleTransitions) {
        this.allPossibleTransitions = allPossibleTransitions;
    }

    public List<TransitionSingle> getAllPossibleTransitions() {
        return allPossibleTransitions;
    }

    public void setAllPossibleTransitions(List<TransitionSingle> allPossibleTransitions) {
        this.allPossibleTransitions = allPossibleTransitions;
    }

    public static Transit getTransitions(String endPoint, LocalDateTime arrivalTime) throws IOException, ParseException, ApiException, InterruptedException {
        if (endPoint.length() < 1 || endPoint == null) throw new IllegalArgumentException("gueltigen Endpunkt eingeben");
        if (arrivalTime == null) throw new IllegalArgumentException("Zeitpunkt festlegen");

        String startPoint = Coordinates.getNameFromIP();
        return getTransitions(startPoint, endPoint, arrivalTime);
    }

    public static void async(String start, String end, LocalDateTime time) throws InterruptedException {
        final List<DirectionsResult> response = new ArrayList<>();
        PendingResult.Callback<DirectionsResult> result = new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult directionsResult) {
                response.add(directionsResult);
            }

            @Override
            public void onFailure(Throwable throwable) {
                            }
        };
        DirectionsApiRequest request = DirectionsApi.newRequest(APIKeys.getApiGoogleContext());
        request.departureTime(time.atZone(ZoneId.of("Europe/Berlin")).toInstant());
        request.mode(TravelMode.TRANSIT);
        request.alternatives(true);
        request.language("de");
        request.origin(start);
        request.destination(end);

        request.setCallback(result);

        Thread.sleep(2000);

        System.out.println(response.get(0).routes[0].toString());
    }

    public static Transit getTransitions(String startPoint, String endPoint, LocalDateTime arrivalTime) throws InterruptedException, ApiException, IOException {
        if (startPoint.length() < 1 || startPoint == null) throw new IllegalArgumentException("gueltigen Startpunkt eingeben");
        if (endPoint.length() < 1 || endPoint == null) throw new IllegalArgumentException("gueltigen Endpunkt eingeben");
        if (arrivalTime == null) throw new IllegalArgumentException("Zeitpunkt festlegen");

        DirectionsApiRequest request = DirectionsApi.getDirections(APIKeys.getApiGoogleContext(), startPoint, endPoint);
        request.arrivalTime(arrivalTime.atZone(ZoneId.of("Europe/Berlin")).toInstant());
        request.mode(TravelMode.TRANSIT);
        request.alternatives(true);
        request.language("de");
        DirectionsResult result = null;
        try {
            result = request.await();
        } catch (UnknownHostException e) {
            System.out.println("Fehler: Verbindung zum Server konnte nicht hergestellt werden");
        } catch (InvalidRequestException e) {
            System.out.println("Fehler: Kein Startpunkt angegeben");
        }

        if (result != null) {
            List<TransitionSingle> transitions = new ArrayList<>();
            for (DirectionsRoute route : result.routes) {
                List<TransitionStep> steps = new ArrayList<>();
                for (DirectionsStep step : route.legs[0].steps) {
                    String sp, ep;
                    LocalTime arrTime, depTime;
                    Optional<String> vehicle = Optional.empty();
                    Optional<String> direction = Optional.empty();
                    if (step.travelMode == TravelMode.WALKING) {
                        sp = null;
                        ep = null;
                        arrTime = null;
                        depTime = null;
                    } else {
                        ep = step.transitDetails.arrivalStop.name;
                        sp = step.transitDetails.departureStop.name;
                        if (step.transitDetails.line.shortName != null) {
                            vehicle = Optional.of(step.transitDetails.line.shortName);
                        }
                        direction = Optional.of(step.transitDetails.headsign);
                        arrTime = step.transitDetails.arrivalTime.toLocalTime();
                        depTime = step.transitDetails.departureTime.toLocalTime();
                    }
                    steps.add(new TransitionStep(step.htmlInstructions, step.distance.humanReadable, step.duration.humanReadable,
                            sp, ep, depTime, arrTime, vehicle, direction, step.travelMode));
                }
                LocalTime dTime = null;
                LocalTime aTime = null;
                if (route.legs[0].departureTime != null) dTime = route.legs[0].departureTime.toLocalTime();
                if (route.legs[0].arrivalTime != null) aTime = route.legs[0].arrivalTime.toLocalTime();
                transitions.add(new TransitionSingle(steps, route.legs[0].duration.humanReadable, route.legs[0].startAddress,
                        route.legs[0].endAddress, dTime, aTime,
                        new Coordinates(route.legs[0].startLocation.lat, route.legs[0].startLocation.lng),
                        new Coordinates(route.legs[0].endLocation.lat, route.legs[0].endLocation.lng)));
            }
            return new Transit(transitions);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (TransitionSingle single : allPossibleTransitions) {
            sb.append(counter + ". ");
            sb.append(single.toString());
            sb.append("\n");
            counter++;
            int counter2 = 1;
            for (TransitionStep step : single.getTransition()) {
                sb.append("\t" + counter2 + ". " + step.toString());
                sb.append("\n");
                counter2++;
            }
            sb.append("\n");
        }
        if (sb.length() > 0) {
            return sb.toString();
        } else return "Keine Zugverbindung gefunden.";
    }
}
