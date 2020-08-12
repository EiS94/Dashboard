$(document).ready(function () {
    request();
    function request() {
        $.ajax({
            url: 'ControllerLoad',
            type: 'GET',
            data: {
            },
            success:
                function (result) {
                    var temp = result.toString();
                    var allEvents = temp.split("\n");
                    allEvents.forEach(function(value) {
                        if (value.toString().startsWith("Other;")){
                            var dataArrO = value.toString().split(";");
                            var nameO = dataArrO[1];
                            var descriptionO = dataArrO[2];
                            var startDateO = dataArrO[3];
                            var startTimeO = dataArrO[4];
                            if (startTimeO === "empty"){
                                startTimeO = "-";
                            }
                            var endDateO = dataArrO[5];
                            if (endDateO ==="empty"){
                                endDateO = "-";
                            }
                            $('#tableComplete').find('#ot').append("<tr class=\"otherRow\"><th scope=\"col\"></th><td>" + nameO + "</td><td>" + startDateO +"," + startTimeO +
                                "</td><td>" + endDateO + "</td><td>"+ descriptionO +"</td></tr>");
                        }else if (value.toString().startsWith("Birthday;")){
                            var dataArrB = value.toString().split(";");
                            var firstNameB = dataArrB[1];
                            var lastNameB = dataArrB[2];
                            if (lastNameB ==="empty"){
                                lastNameB = "-";
                            }
                            var dateB = dataArrB[3];
                            var ageB = dataArrB[4];
                            var presentB = dataArrB[5];
                            if (presentB ==="empty"){
                                presentB = "-";
                            }
                            $('#tableComplete').find('#bd').append("<tr class=\"birthdayRow\"><th scope=\"col\"></th><td>" + firstNameB + "," + lastNameB + "</td><td>" + dateB +
                                "</td><td>" + ageB + "</td><td>" + presentB + "</td></tr>");
                        }else if (value.toString().startsWith("Appointment;")) {
                            var dataArrA = value.toString().split(";");
                            var nameA = dataArrA[1];
                            var startDateA = dataArrA[2];
                            var endDateA = dataArrA[3];
                            if (endDateA ==="empty"){
                                endDateA = "-";
                            }
                            var startTimeA = dataArrA[4];
                            if (startTimeA ==="empty"){
                                startTimeA = "-";
                            }
                            var endTimeA = dataArrA[5];
                            if (endTimeA ==="empty"){
                                endTimeA = "-";
                            }
                            var destinationA = dataArrA[6];
                            if (destinationA ==="empty"){
                                destinationA = "-";
                            }
                            $('#tableComplete').find('#ap').append("<tr class=\"appRow\"><th scope=\"col\"></th><td>" + nameA + "</td><td>" + destinationA +
                                "</td><td>" + startDateA + "," + startTimeA + "</td><td>" + endDateA + "," + endTimeA + "</td></tr>");
                        }
                    });
                    requestTransit();
                    requestWeather();
                    requestWeatherDest();
                }
        });
    }
});

//all events to add according to toString separated by \n
//"Other;" + name + ";" + description + ";" + startDate.toString() + ";" + startTimeString + ";" + endDateString;
//return "Birthday;" + firstName + ";" + lastnameString + ";" + date.toString() + ";" + age + ";" + presentString;
//return "Appointment;" + name + ";" + startDate.toString() + ";" + endDateString + ";" + startTimeString + ";" + endTimeString + ";" + destination;