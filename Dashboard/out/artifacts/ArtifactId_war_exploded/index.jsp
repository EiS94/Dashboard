<!-- Created by IntelliJ IDEA.
User: lydia
Date: 18.11.2019
Time: 15:59
To change this template use File | Settings | File Templates.
-->
<%@ page import="com.google.maps.errors.ApiException" %>
<%@ page import="org.json.simple.parser.ParseException" %>
<%@ page import="java.net.ConnectException" %>
<%@ page import="java.net.UnknownHostException" %>
<%@ page import="static Location.Coordinates.getPlaceCoordinates" %>
<%@ page import="Location.Coordinates" %>
<%@ page import="Location.Location" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page errorPage="error.jsp" %>
<html>
<head>
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/pics/nav.png"/>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">

    <!--Calendar-->
    <link href="${pageContext.request.contextPath}/css/calendar.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/demo.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme2.css"/>

    <!-- AppointmentTable CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webScript.css"/>

    <title>Dashboard</title>

    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-w.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-t.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-store.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-load.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax-w-dest.js"></script>


</head>
<body onload="getLocation()">

<script>
    var d = new Date();
    var dayIndex = d.getUTCDate();
    var monthIndex = d.getUTCMonth();
    var yearIndex = d.getUTCFullYear();
    var reload = false;
</script>

<% //get Geolocation-Cookie and set current Position in Coordinates
    //reloads the page, if the cookies set the first time
    Cookie[] cookies = request.getCookies();
    String geolocation = "";
    boolean reload = false;
    Location currentPosition = null;
    try {
        for (Cookie cookie : cookies) {
            if (cookie != null) {
                if (cookie.getName().equals("geolocation")) {
                    geolocation = cookie.getValue();
                }
            }
        }
        if (!geolocation.equals("")) {
            String[] split = geolocation.split("and");
            try {
                currentPosition = Location.getLocationFromCoordinates(new Coordinates(Double.parseDouble(split[0]),
                        Double.parseDouble(split[1])));
            } catch (ParseException | ConnectException | UnknownHostException e) {
                e.printStackTrace();
            }
        } else {%>
<script>reload = true</script>
<%
        }
    } catch (NullPointerException e) {
        reload = true;
    }

    if (!reload) {
    } else {%>
<script> reload = true</script>
<%
    }
%>

<!--Navbar-->
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" id="nav">
        <img id="navPic" src="${pageContext.request.contextPath}/pics/nav.png"
             width="30" height="30" class="d-inline-block align-top" alt="...">
        <% String city = "Unbekannter Ort";
            Coordinates c = new Coordinates(2, 3);
            if (currentPosition != null) {
                try {
                    try {
                        out.println(Web.Navbar.getNavbar(currentPosition));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (ConnectException e) {
                    e.printStackTrace();
                }
            } else {
                String navBar = Web.Navbar.getNavbar();
                city = navBar.split(",")[0];
                try {
                    c = getPlaceCoordinates(city);
                } catch (ApiException e) {
                    System.out.println(e.getMessage());
                } catch (UnknownHostException e) {
                    System.out.println(e.getMessage());
                }
                out.print(navBar);
            }%>
    </a>
</nav>

<p hidden id="long"><%=c.getLongitude()%>
</p>
<p hidden id="lat"><%=c.getLatitude()%>
</p>

<div class="container mt-3">
    <div class="row">
        <div class="col">
            <!--Calendar-->
            <div id="calendar">
            </div>
        </div>

        <!-- TableButtons -->
        <div class="col">

            <div class="btn-group" role="group">
                <button id="removeButton" onclick="deleteTable()" type="button" class="btn btn-secondary">
                    Löschen
                </button>
                <button id="editButton" onclick="editButton()" type="button" class="btn btn-secondary">
                    Ändern
                </button>

                <!-- Dtopdown Hinzufügen-->
                <div class="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button"
                            class="btn btn-secondary dropdown-toggle"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Hinzufügen
                    </button>
                    <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <a id="dropbirth" href="#birth" class="dropdown-item" data-toggle="modal" data-target="#birth">Geburtstag</a>
                        <a id="dropapp" href="#app" class="dropdown-item" data-toggle="modal"
                           data-target="#app">Termin</a>
                        <a id="dropother" href="#other" class="dropdown-item" data-toggle="modal" data-target="#other">Notiz</a>
                    </div>
                </div>
                <!-- Dtopdown Übersicht -->
                <div id="dropTime" class="btn-group" role="group">
                    <button id="btnGroupDrop2" type="button" class="btn btn-secondary dropdown-toggle"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Tag
                    </button>
                    <div class="dropdown-menu" aria-labelledby="btnGroupDrop2">
                        <li><a class="dropdown-item" onclick="showDatesAktuell()" href="#">Tag</a></li>
                        <li><a class="dropdown-item" onclick="showDatesWeek()" href="#">Woche</a></li>
                        <li><a class="dropdown-item" onclick="showDatesMonth()" href="#">Monat</a></li>
                        <li><a class="dropdown-item" onclick="showDatesYear()" href="#">Jahr</a></li>
                    </div>
                </div>

                <!-- Geburtstags modal -->
                <div class="modal fade bd-example-modal-lg" id="birth" tabindex="-1" role="dialog"
                     aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="birthdayModalLabel">Erstelle einen neuen Geburtstag!</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form class="modal-form">
                                    <div class="row">
                                        <div class="col">
                                            <label for="firstName">Vorname</label>
                                            <input id="firstName" type="text" class="form-control" placeholder="Jane">
                                        </div>
                                        <div class="col">
                                            <label for="lastName">Nachname (optional)</label>
                                            <input id="lastName" type="text" class="form-control" placeholder="Doe">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">
                                            <label for="birthday">Geburtsdatum</label>
                                            <input id="birthday" type="date" class="form-control"
                                                   placeholder="TT.MM.JJJJ">
                                        </div>
                                        <div class="col">
                                            <label for="age">Alter</label>
                                            <input id="age" type="text" class="form-control" placeholder="Jahre"
                                                   disabled>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="present">Geschenkidee (optional)</label>
                                        <input id="present" type="text" class="form-control"
                                               placeholder="Gib hier ein Geschenk an!">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="clearBirthday" type="button" class="btn btn-secondary">
                                    Eingaben Leeren
                                </button>
                                <button onclick="editTableSelectedRow()" id="editBirthday" type="button"
                                        class="btn btn-secondary" disabled>
                                    Ändern
                                </button>
                                <button onclick="addTableRowBirthday()" id="saveBirthday" type="button"
                                        class="btn btn-secondary"
                                >
                                    Hinzufügen
                                </button>
                                <button id="closeBirthday" type="button" class="btn btn-secondary" data-dismiss="modal">
                                    Schließen
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Termin modal -->
                <div id="app" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
                     aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="appModalLabel">Erstelle einen neuen Termin!</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form class="modal-form">
                                    <div class="row">
                                        <div class="col">
                                            <label for="nameApp">Name des Termins</label>
                                            <input id="nameApp" type="text" class="form-control"
                                                   placeholder="Konferenz">
                                        </div>
                                        <div class="col">
                                            <label for="locationApp">Ort (optional)</label>
                                            <input id="locationApp" type="text" class="form-control"
                                                   placeholder="Frankfurt">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">
                                            <label for="startDateApp">Startdatum</label>
                                            <input id="startDateApp" type="date" class="form-control"
                                                   placeholder="TT.MM.JJJJ">
                                        </div>
                                        <div class="col">
                                            <label for="endDateApp">Enddatum (optional)</label>
                                            <input id="endDateApp" type="date" class="form-control"
                                                   placeholder="TT.MM.JJJJ">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <label for="startTimeApp">Startzeit (optional)</label>
                                            <input id="startTimeApp" type="time" class="form-control"
                                                   placeholder="SS:MM">
                                        </div>
                                        <div class="col">
                                            <label for="endTimeApp">Endzeit (optional)</label>
                                            <input id="endTimeApp" type="time" class="form-control" placeholder="SS:MM">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="clearApp" type="button" class="btn btn-secondary">Eingaben
                                    Leeren
                                </button>
                                <button onclick="editTableSelectedRow()" id="editApp" type="button"
                                        class="btn btn-secondary" disabled>
                                    Ändern
                                </button>
                                <button onclick="addTableRowApp()" id="saveApp" type="button"
                                        class="btn btn-secondary">
                                    Hinzufügen
                                </button>
                                <button id="closeApp" type="button" class="btn btn-secondary" data-dismiss="modal">
                                    Schließen
                                </button>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- Sonstiges modal -->
                <div id="other" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
                     aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="otherModalLabel">Erstelle einen neuen Eintrag!</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form class="modal-form">
                                    <div class="row">
                                        <div class="col">
                                            <label for="nameOther">Name der Notiz</label>
                                            <input id="nameOther" type="text" class="form-control" placeholder="Urlaub">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">
                                            <label for="startDateOther">Startdatum</label>
                                            <input id="startDateOther" type="date" class="form-control"
                                                   placeholder="TT.MM.JJJJ">
                                        </div>
                                        <div class="col">
                                            <label for="endDateOther">Enddatum (optional)</label>
                                            <input id="endDateOther" type="date" class="form-control"
                                                   placeholder="TT.MM.JJJJ">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">
                                            <label for="startTimeOther">Startzeit (optional)</label>
                                            <input id="startTimeOther" type="time" class="form-control"
                                                   placeholder="SS:MM">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="infoOther">Notizen (optional)</label>
                                        <input id="infoOther" type="text" class="form-control" placeholder="Text...">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="clearO" type="button" class="btn btn-secondary">Eingaben
                                    Leeren
                                </button>
                                <button onclick="editTableSelectedRow()" id="editO" type="button"
                                        class="btn btn-secondary">
                                    Ändern
                                </button>
                                <button onclick="addTableRowOther()" id="saveO" type="button"
                                        class="btn btn-secondary" disabled>
                                    Hinzufügen
                                </button>
                                <button id="closeO" type="button" class="btn btn-secondary" data-dismiss="modal">
                                    Schließen
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <!--AppointmentTable-->
            <!--<div class="card">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">TERMINE</th>
                    </tr>
                    </thead>
                    <tbody id="appointmentTable">
                    <% out.println(Web.AppointmentTable.getTable());%>
                    </tbody>
                </table>
            </div>-->


            <!--AppointmentTable-->
            <div id="clickTable" class="table-responsive table-wrapper-scroll-y my-custom-scrollbar">
                <table id="tableComplete" onclick="clickTable()" class="table">
                    <thead id="birthdayTable" class="thead-light">
                    <tr class="clickable-row">
                        <th scope="col">Geburtstage</th>
                        <th scope="col">Name</th>
                        <th scope="col">Datum</th>
                        <th scope="col">Alter</th>
                        <th scope="col">Geschenk</th>
                    </tr>
                    </thead>
                    <tbody id="bd">

                    </tbody>
                    <thead id="appTable" class="thead-light">
                    <tr>
                        <th scope="col">Termine</th>
                        <th scope="col">Name</th>
                        <th scope="col">Ort</th>
                        <th scope="col">Start</th>
                        <th scope="col">Ende</th>
                    </tr>
                    </thead>
                    <tbody id="ap">

                    </tbody>

                    <thead id="otherTable" class="thead-light">
                    <tr>
                        <th scope="col">Notizen</th>
                        <th scope="col">Name</th>
                        <th scope="col">Start</th>
                        <th scope="col">Ende</th>
                        <th scope="col">Info</th>
                    </tr>
                    </thead>
                    <tbody id="ot">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="container mt-3">
    <!--zwei Karten nebeneinander
    <div class="card-deck">-->
    <!--TransportCard-->
    <div class="row row-cols-1 row-cols-md-3">
        <div class="col mb-4">
            <div class="card">
                <img src="${pageContext.request.contextPath}/pics/transit.jpeg"
                     class="card-img-top" alt="...">
                <div class="card-body">
                    <h4 class="card-title font-weight-bold">BUS UND BAHN</h4>
                    <img alt="" id="iconTransport" src="WeatherIcons/animated/loading_Bars_Black.svg"
                         style="width:500px; height:100px">
                    <p id="transport"></p>
                    <p id="transportTable"></p>
                </div>
            </div>
        </div>
        <div id="weather" class="col mb-4">

            <!--WeatherCard-->
            <div class="card bg-info mb-3">

                <div id="carouselExampleIndicators" class="carousel slide" data-interval="false">
                    <div class="carousel-inner">
                        <!--erste Seite vom Carousel-->
                        <div class="carousel-item active">
                            <div class="container">
                                <div class="row justify-content-md-center">
                                    <div class="col-md-auto">

                                        <img alt="" id="icon" src="WeatherIcons/animated/loading_Bars_White.svg"
                                             style="width:auto; height:160px">
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="container">
                                    <div class="row justify-content-md-center">
                                        <div class="col-md-auto">
                                            <h4 class="card-title font-weight-bold" id="city">
                                            </h4>
                                            <p class="card-text" id="time">
                                            </p>
                                            <p class="card-text" id="description">
                                            </p>
                                            <p class="card-text" id="temperatur">
                                            </p>
                                            <p class="card-text" id="rainpercent">
                                            </p>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <!--zweite Seite vom Carousel-->
                        <div class="carousel-item">
                            <div class="card-body">
                                <h4 class="card-title font-weight-bold">WETTER PROGNOSE</h4>
                                <div class="table-responsive">
                                    <table class="card-table table bg-info">
                                        <!--<thead>
                                        <tr>
                                            <th scope="col"></th>
                                            <th scope="col"></th>
                                            <th scope="col"></th>
                                        </tr>
                                        </thead>-->
                                        <tbody id="forecasttable">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--Knöpfe vom Carousel-->
                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
                <input type="button" id="hideshow" value="Wetter am Zielort anzeigen">
            </div>
        </div>
        <div id="weatherD" class="col mb-4" style="display: none">

            <!--WeatherCard-->
            <div id="testD"></div>
            <div class="card bg-info mb-3">

                <div id="carouselExampleIndicatorsD" class="carousel slide" data-interval="false">
                    <div class="carousel-inner">
                        <!--erste Seite vom Carousel-->
                        <div class="carousel-item active">
                            <div class="container">
                                <div class="row justify-content-md-center">
                                    <div class="col-md-auto">

                                        <img alt="" id="iconD" src="WeatherIcons/animated/loading_Bars_White.svg"
                                             style="width:auto; height:160px">
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="container">
                                    <div class="row justify-content-md-center">
                                        <div class="col-md-auto">
                                            <h4 class="card-title font-weight-bold" id="cityD">
                                            </h4>
                                            <p class="card-text" id="timeD">
                                            </p>
                                            <p class="card-text" id="descriptionD">
                                            </p>
                                            <p class="card-text" id="temperaturD">
                                            </p>
                                            <p class="card-text" id="rainpercentD">
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--zweite Seite vom Carousel-->
                        <div class="carousel-item">
                            <div class="card-body">
                                <h4 class="card-title font-weight-bold">WETTER PROGNOSE</h4>
                                <div class="table-responsive">
                                    <table class="card-table table bg-info">
                                        <!--<thead>
                                        <tr>
                                            <th scope="col"></th>
                                            <th scope="col"></th>
                                            <th scope="col"></th>
                                        </tr>
                                        </thead>-->
                                        <tbody id="forecasttableD">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--Knöpfe vom Carousel-->
                    <a class="carousel-control-prev" href="#carouselExampleIndicatorsD" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicatorsD" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
                <input type="button" id="hideshowD" value="Wetter am aktuellen Standort anzeigen">
            </div>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- Calendar.js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/demo.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/webScript.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/editTable.js"></script>


<script>

    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else window.alert("Ortungsdienst nicht verfuegbar");
    }

    function showPosition(position) {
        if (getCookie("geolocation") === position.coords.latitude + "and" + position.coords.longitude) {
        } else {
            var expireDate = new Date();
            expireDate.setMonth(expireDate.getMonth() + 1);
            //set an Cookie with the geolocation, expires after 1 Month
            document.cookie = "geolocation=" + position.coords.latitude + "and" + position.coords.longitude + "" +
                "; expires=" + expireDate + "; path=/";
            //reloads the page, if necessary
            location.reload();
        }
        if (reload) {
            location.reload();
        }
    }

    function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length === 2) return parts.pop().split(";").shift();
        else return "not found";
    }

    function createCookie(name, value, minutes) {
        if (minutes) {
            var date = new Date();
            date.setTime(date.getTime() + (minutes * 60 * 1000));
            var expires = "; expires=" + date.toGMTString();
        } else {
            expires = "";
        }
        document.cookie = name + "=" + value + expires + "; path=/";
    }

    //calculates the next Appointment of the table
    //return: Array of the next Appointment:
    //        [0] = name
    //        [1] = place
    //        [2] = startTime
    //        [3] = endTime
    function getNextAppointment() {
        var test = document.getElementById("tableComplete");
        var entryCounter = document.getElementById("tableComplete").tBodies.namedItem("ap").rows.length;
        //document.getElementById("appTable").rows.length;
        var entrys = [];
        for (var i = 0; i < entryCounter; i++) {
            entrys.push(document.getElementById("tableComplete").tBodies.namedItem("ap").rows[i].innerHTML);
            //document.getElementById("appTable").rows[i].innerHTML
        }
        var matched = [];
        for (var i = 0; i < entrys.length; i++) {
            var reg = entrys[i].match(/<td>(.*?)<\/td>/g).map(function (val) {
                return val.replace(/<\/?td>/g, '');
            });
            matched.push(reg);
        }
        var times = [];
        for (var i = 0; i < matched.length; i++) {
            var date = matched[i][2].split(",");
            if (date[1] === "-") date[1] = "23:59";
            times.push(new Date(date[0] + " " + date[1]));
        }
        var bestIndex = 99999;
        var bestTime = new Date(3000, 12, 31, 23, 59);
        var now = Date.now();
        for (var i = 0; i < times.length; i++) {
            if (times[i] < bestTime && times[i] > now) {
                bestIndex = i;
                bestTime = times[i];
            }
        }
        if (bestIndex == 99999) return null;
        return matched[bestIndex];
    }

</script>


</body>
</html>
