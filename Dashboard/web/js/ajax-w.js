//$(document).ready(function () {

    var refreshInterval = 0;
    //requestWeather();

    function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length === 2) return parts.pop().split(";").shift();
        else return "not found";
    }


    function requestWeather() {
        var longitude, latitude;
        var location = getCookie("geolocation").split("and");
        if (location.length === 2) {
            latitude = location[0];
            longitude = location[1];
        } else {
            latitude = $('#lat').html();
            longitude = $('#long').html();
        }
        $('#icon').attr("src", "WeatherIcons/animated/loading_Bars_White.svg");
        $('#icon').attr("style", "width:auto; height:160px");
        $.ajax({
            url: 'Controller',
            type: 'GET',
            data:{
                currLat: latitude,
                currLon: longitude
            },
            success:
                function (result) {

                    if (result === "Keine Internetverbindung") {
                        $('#icon').attr("src", "pics/noInternet.svg");
                        $('#icon').attr("style", "width:60px; height:60px");
                        $('#city').html("Keine Internetverbindung")
                        $('#time').html("");
                        $('#forecasttable').html("");
                        $('#temperatur').html("");
                        $('#rainpercent').html("");
                        $('#description').html("");
                    } else {

                        if (result.toString().localeCompare("keine Wetterdaten!") === 0) {

                            return;
                        }

                        var temp = result.match(/^\[src="(.*?)"/);
                        var temp1 = temp.toString().substr(6);
                        var temp2 = temp1.split("\"")[0];
                        var temparr;
                        temparr = result.split(",");
                        $('#icon').attr("src", temp2); //bild-URL PASST
                        $('#time').text((temparr[1].toString() + temparr[2].toString()).toString().substr(1)); //PASST
                        var temp3 = result.match(/<tr>.+<\/tr>/);
                        //console.log(temp1.toString());
                        $('#forecasttable').html(temp3.toString()); //PASST
                        $('#description').text(temparr[3].toString()); //PASST
                        $('#temperatur').text("Temperatur: " + temparr[4].toString()); //PASST
                        $('#rainpercent').text("Regenwahrscheinlichkeit: " + temparr[5].toString()); //PASST
                        $('#city').html("WETTER AKTUELL IN " + temparr.pop().toString().replace("]", ""));
                    }//PASST
                    if(refreshInterval != null){

                        clearInterval(refreshInterval);
                    }
                },
            complete: function () {
                refreshInterval = setInterval(requestWeather, 60*10*1000); //timeout muss noch variabel gemacht werden TODO
            }
        });
    }

//});

