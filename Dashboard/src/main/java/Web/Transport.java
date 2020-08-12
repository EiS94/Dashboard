package Web;

import Location.Location;
import Transportation.Transit;
import Transportation.TransitionSingle;
import Transportation.TransitionStep;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Transport {
    public static ArrayList<String> getTransport() throws InterruptedException, ApiException, IOException {
        //Termindaten für den Transport
        Transit transit = Transit.getTransitions("hubland", "nurnberg hbf", LocalDateTime.now());
        if (transit != null) {
            ArrayList<String> transp = new ArrayList<>();
            if (transit.getAllPossibleTransitions().size() == 0) {
                //Liste pos 0-1;
                transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + "startPoint" + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + "endPoint" + "</h6>Keine Verbindungen möglich!");
                return transp;
            } else {
                //Liste min 0-2; sonst 0-4, 0-6, 0-8; Reihenfolge Ziel und Startort, dann Überblick, dann Beschreibung usw.;
                List<TransitionSingle> singleList = transit.getAllPossibleTransitions();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
                transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + singleList.get(0).getStartAddress() + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + singleList.get(0).getEndAddress() + "</h6>");
                for (TransitionSingle single : singleList) {
                    transp.add(single.getDepartureTime().format(dtf) + " - " + single.getArrivalTime().format(dtf) + "; " + single.getDuration() + "; " + single.getMoves() + " Umstieg/e");
                    transp.add(single.toString());
                }
            }
            return transp;
        }
        return null;
    }


    public static ArrayList<String> getTransport(Location startLocation, Location endLocation, LocalDateTime time) throws InterruptedException, ApiException, IOException {
        //System.out.println(location.toString());
        //Termindaten für den Transport
        Transit transit = Transit.getTransitions(startLocation.getCity() + " " + startLocation.getStreet(),
                endLocation.getCity() + " " + endLocation.getStreet(), time);
        ArrayList<String> transp = new ArrayList<>();

        if (transit.getAllPossibleTransitions().size() == 0) {
            //Liste pos 0-1;
            transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + startLocation.getCity() + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + "endPoint" + "</h6>Keine Verbindungen möglich!");
            return transp;
        } else {
            //Liste min 0-2; sonst 0-4, 0-6, 0-8; Reihenfolge Ziel und Startort, dann Überblick, dann Beschreibung usw.;
            List<TransitionSingle> singleList = transit.getAllPossibleTransitions();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
            transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + singleList.get(0).getStartAddress() + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + singleList.get(0).getEndAddress() + "</h6>");
            for (TransitionSingle single : singleList) {
                transp.add(single.getDepartureTime().format(dtf) + " - " + single.getArrivalTime().format(dtf) + "; " + single.getDuration() + "; " + single.getMoves() + " Umstieg/e");
                transp.add(getRepresentationFromTransition(single.getTransition()));
            }
            return transp;

        }
    }

    public static ArrayList<String> getTransport(Location startLocation, String endLocation, LocalDateTime time) throws InterruptedException, ApiException, IOException {
        //System.out.println(location.toString());
        //Termindaten für den Transport
        Transit transit = Transit.getTransitions(startLocation.getCity() + " " + startLocation.getStreet(),
                endLocation, time);
        ArrayList<String> transp = new ArrayList<>();

        if (transit.getAllPossibleTransitions().size() == 0) {
            //Liste pos 0-1;
            transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + startLocation.getCity() + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + endLocation + "</h6>Keine Verbindungen möglich!");
            return transp;
        } else {
            //Liste min 0-2; sonst 0-4, 0-6, 0-8; Reihenfolge Ziel und Startort, dann Überblick, dann Beschreibung usw.;
            List<TransitionSingle> singleList = transit.getAllPossibleTransitions();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
            transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + singleList.get(0).getStartAddress() + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + singleList.get(0).getEndAddress() + "</h6>");
            for (TransitionSingle single : singleList) {
                if (single.getDepartureTime() != null & single.getArrivalTime() != null) {
                    transp.add(single.getDepartureTime().format(dtf) + " - " + single.getArrivalTime().format(dtf) + "; " + single.getDuration() + "; " + single.getMoves() + " Umstieg/e");
                } else {
                    transp.add(single.getDuration() + "; " + single.getMoves() + " Umstieg/e");
                }
                transp.add(getRepresentationFromTransition(single.getTransition()));
            }
            return transp;

        }
    }

    public static ArrayList<String> getTransport(Location startLocation) throws InterruptedException, ApiException, IOException {
        //System.out.println(location.toString());
        //Termindaten für den Transport
        Transit transit = Transit.getTransitions(startLocation.getCity() + " " + startLocation.getStreet(),
                "max morlock stadion", LocalDateTime.now());
        ArrayList<String> transp = new ArrayList<>();

        if (transit.getAllPossibleTransitions().size() == 0) {
            //Liste pos 0-1;
            transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + startLocation.getCity() + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + "endPoint" + "</h6>Keine Verbindungen möglich!");
            return transp;
        } else {
            //Liste min 0-2; sonst 0-4, 0-6, 0-8; Reihenfolge Ziel und Startort, dann Überblick, dann Beschreibung usw.;
            List<TransitionSingle> singleList = transit.getAllPossibleTransitions();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("kk:mm");
            transp.add("<h6 class=\"font-weight-bold\">Von: </h6><h6>" + singleList.get(0).getStartAddress() + "</h6><h6 class=\"font-weight-bold\">Nach: </h6><h6>" + singleList.get(0).getEndAddress() + "</h6>");
            for (TransitionSingle single : singleList) {
                transp.add(single.getDepartureTime().format(dtf) + " - " + single.getArrivalTime().format(dtf) + "; " + single.getDuration() + "; " + single.getMoves() + " Umstieg/e");
                transp.add(getRepresentationFromTransition(single.getTransition()));
            }
            return transp;

        }
    }

    public static String getRepresentationFromTransition(List<TransitionStep> steps) {
        if (steps.size() > 0) {
            steps.remove(0);
        }
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (TransitionStep step : steps) {
            sb.append(counter + ". " + step.toString() + "<br>");
            counter++;
        }
        return sb.toString();
    }

    public static String getPossibleTransitions(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list.size() > 1) {
            list.remove(0);
            sb.append("<div class=\"accordion\" id=\"accordionExample\">");
            for (int i = 0; i < list.size(); i += 2) {
                String heading = null;
                String collapse = null;
                String expanded = null;
                if (i == 0) {
                    heading = "headingOne";
                    collapse = "collapseOne";
                    expanded = "true";
                } else if (i == 2) {
                    heading = "headingTwo";
                    collapse = "collapseTwo";
                    expanded = "false";
                } else if (i == 4) {
                    heading = "headingThree";
                    collapse = "collapseThree";
                    expanded = "false";
                } else if (i == 6) {
                    heading = "headingFour";
                    collapse = "collapseFour";
                    expanded = "false";
                }
                sb.append("<div class=\"card\"><div class=\"card-header\" id=\"" + heading + "\">" +
                        "<h2 class=\"mb-0\"><button class=\"btn btn-link\" type=\"button\" " +
                        "data-toggle=\"collapse\" data-target=\"#" + collapse + "\" aria-expanded=\"" + expanded + "\" " +
                        "aria-controls=\"" + collapse + "\">" + list.get(i) + "</button></h2></div><div " +
                        "id=\"" + collapse + "\" class=\"collapse\" aria-labelledby=\"" + heading + "\" " +
                        "data-parent=\"#accordionExample\"><div class=\"card-body\">" +
                        list.get(i + 1) + "</div></div></div>");
            }
            sb.append("</div>");
        }
        return sb.toString();
    }
}
