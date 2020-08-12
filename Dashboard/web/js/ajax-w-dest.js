//$(document).ready(function weatherDest() { //kann dann bei Event gecallt werden (z.B. bei erstellen/löschen von Termin)


    var refreshInterval = 0;
    //request();

    function requestWeatherDest() {
        var nextAppointment = getNextAppointment();
        $('#iconD').attr("src", "WeatherIcons/animated/loading_Bars_White.svg");
        $('#iconD').attr("style", "width:auto; height:160px");
        $.ajax({
            url: 'ControllerDW',
            type: 'GET',
            data:{
                nextPlace: nextAppointment[1].toString()
            },
            success:
                function (result) {

                    if (result === "Keine Internetverbindung") {
                        $('#iconD').attr("src", "pics/noInternet.svg");
                        $('#iconD').attr("style", "width:60px; height:60px");
                        $('#cityD').html("Keine Internetverbindung")
                        $('#timeD').html("");
                        $('#forecasttableD').html("");
                        $('#temperaturD').html("");
                        $('#rainpercentD').html("");
                        $('#descriptionD').html("");
                    } else {

                        if (result.toString().localeCompare("keine Wetterdaten!") === 0) {

                            return;
                        }

                        var temp = result.match(/^\[src="(.*?)"/);
                        var temp1 = temp.toString().substr(6);
                        var temp2 = temp1.split("\"")[0];
                        var temparr;
                        temparr = result.split(",");
                        $('#iconD').attr("src", temp2); //bild-URL PASST
                        $('#timeD').text((temparr[1].toString() + temparr[2].toString()).toString().substr(1)); //PASST
                        var temp3 = result.match(/<tr>.+<\/tr>/);
                        //console.log(temp1.toString());
                        $('#forecasttableD').html(temp3.toString()); //PASST
                        $('#descriptionD').text(temparr[3].toString()); //PASST
                        $('#temperaturD').text("Temperatur: " + temparr[4].toString()); //PASST
                        $('#rainpercentD').text("Regenwahrscheinlichkeit: " + temparr[5].toString()); //PASST
                        $('#cityD').html("WETTER AKTUELL IN " + temparr.pop().toString().replace("]", ""));
                    }//PASST
                    if(refreshInterval != null){

                        clearInterval(refreshInterval);
                    }
                },
            complete: function () {
                refreshInterval = setInterval(requestWeatherDest, 10*60*1000); //timeout muss noch variabel gemacht werden TODO
            }
        });
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

//});

