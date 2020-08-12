//$(document).ready(function () {

    var refreshInterval = 0;
    //requestTransit();

    function requestTransit() {
        var longitude, latitude;
        var location = getCookie("geolocation").split("and");
        if (location.length === 2) {
            latitude = location[0];
            longitude = location[1];
        } else {
            latitude = $('#lat').html();
            longitude = $('#long').html();
        }
        $('#iconTransport').show();
        var appointment = getNextAppointment();
        var a;
        if (appointment != null && appointment[1] !== "-") {
            for (var i = 0; i < appointment.length; i++) {
                a += appointment[i] + "##";
            }
        } else if (appointment[1] === "-") {
            a = "Nächster Termin hat keinen Zielort";
        } else a = "Keine ausstehenden Termine";
        $.ajax({
            url: 'ControllerTransportation',
            type: 'GET',
            data: {
                a: a,
                currLat: latitude,
                currLon: longitude
            },
            success:
                function (result) {
                    $('#iconTransport').hide();
                    if (result === "Keine ausstehenden Termine" || result === "Keine Internetverbindung") {
                        $('#transport').html(result);
                        $('#transportTable').html("");
                    } else {
                        result = result.substr(0, result.length - 2);
                        transport = result.split("##");
                        $('#transport').html(transport[0]);
                        $('#transportTable').html(transport[1]);
                    }
                },
            complete: function () {
                refreshInterval = setInterval(requestTransit, 10 * 60 * 1000);
            }
        });
    }


//});