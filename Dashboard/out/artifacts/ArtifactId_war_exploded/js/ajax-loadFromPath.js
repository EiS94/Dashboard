$(document).ready(function () {

    var path = $('#customFile').innerHTML;

    request();
    function request() {
        $.ajax({
            url: 'ControllerLoadFromPath',
            type: 'GET',
            data: {
                filepath: path
            },
            success:
                function (result) {
                    var temp = result.toString();
                    var allEvents = temp.split("\n");
                    allEvents.forEach(function() {
                        if (value.toString().startsWith("Other;")){
                            var dataArrO = value.toString().split(";");
                            var nameO = dataArrO[1];
                            var descriptionO = dataArrO[2];
                            var startDateO = dataArrO[3];
                            var startTimeO = dataArrO[4];
                            if (startDateO.localeCompare("empty")){
                                startTimeO = "-";
                            }
                            var endDateO = dataArrO[5];
                            if (endDateO.localeCompare("empty")){
                                endDateO = "-";
                            }
                            $('#tableComplete').find('#ot').append("<tr class=\"otherRow\"><th scope=\"col\"></th><td>" + nameO + "</td><td>" + startDateO +"," + startTimeO +
                                "</td><td>" + endDateO + "</td><td>"+ descriptionO +"</td></tr>");
                        }else if (value.toString().startsWith("Birthday;")){
                            var dataArrB = value.toString().split(";");
                            var firstNameB = dataArrB[1];
                            var lastNameB = dataArrB[2];
                            if (lastNameB.localeCompare("empty")){
                                lastNameB = "-";
                            }
                            var dateB = dataArrB[3];
                            var ageB = dataArrB[4];
                            var presentB = dataArrB[5];
                            if (presentB.localeCompare("empty")){
                                presentB = "-";
                            }
                            $('#tableComplete').find('#bd').append("<tr class=\"birthdayRow\"><th scope=\"col\"></th><td>" + firstNameB + "," + lastNameB + "</td><td>" + dateB +
                                "</td><td>" + ageB + "</td><td>" + presentB + "</td></tr>");
                        }else if (value.toString().startsWith("Appointment;")) {
                            var dataArrA = value.toString().split(";");
                            var nameA = dataArrA[1];
                            var startDateA = dataArrA[2];
                            var endDateA = dataArrA[3];
                            if (endDateA.localeCompare("empty")){
                                endDateA = "-";
                            }
                            var startTimeA = dataArrA[4];
                            if (startTimeA.localeCompare("empty")){
                                startTimeA = "-";
                            }
                            var endTimeA = dataArrA[5];
                            if (endTimeA.localeCompare("empty")){
                                endTimeA = "-";
                            }
                            var destinationA = dataArrA[6];
                            if (destinationA.localeCompare("empty")){
                                destinationA = "-";
                            }
                            $('#tableComplete').find('#ap').append("<tr class=\"appRow\"><th scope=\"col\"></th><td>" + nameA + "</td><td>" + destinationA +
                                "</td><td>" + startDateA + "," + startTimeA + "</td><td>" + endDateA + "," + endTimeA + "</td></tr>");
                        }
                    });
                }
        });
    }
});