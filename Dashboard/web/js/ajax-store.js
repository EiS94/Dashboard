

    function getData() {
        var result = "";

        var birthdayBody = $('#bd');
        var birthdayRows = birthdayBody.find('tr');
        for (var i = 0; i < birthdayRows.length; i++) {
            //every row of birthdays
            var cols = $(birthdayRows[i]).find('td');
            var aux = cols[0].innerHTML;
            var temp = aux.toString().split(",");
            var firstName = temp[0];
            var lastName = temp[1];
            if (lastName.toString() === "-") {
                lastName = "empty";
            }
            var date = cols[1].innerHTML;
            var age = cols[2].innerHTML;
            var present = cols[3].innerHTML;
            if (present.toString() === "-" || present.toString() === "") {
                present = "empty";
            }
            result += "Birthday;" + firstName + ";" + lastName + ";" + date + ";" + age + ";" + present + "\n";
        }

        var appointmentBody = $('#ap');
        var appointmentRows = appointmentBody.find('tr');
        for (var x = 0; x < appointmentRows.length; x++) {
            var colsA = $(appointmentRows[x]).find('td');
            var nameA = colsA[0].innerHTML;
            var destinationA = colsA[1].innerHTML;
            if (destinationA.toString() === "-") {
                destinationA = "empty";
            }
            var temp2 = colsA[2].innerHTML.split(',');
            var startDateA = temp2[0];
            if (startDateA.toString() === "-") {
                startDateA = "empty";
            }
            var startTimeA = temp2[1];
            if (startTimeA.toString() === "-") {
                startTimeA = "empty";
            }
            var temp3 = colsA[3].innerHTML.split(',');
            var endDateA = temp3[0];
            if (endDateA.toString() === "-") {
                endDateA = "empty";
            }
            var endTimeA = temp3[1];
            if (endTimeA.toString() === "-") {
                endTimeA = "empty";
            }
            result += "Appointment;" + nameA + ";" + startDateA + ";" + endDateA + ";" + startTimeA + ";"
                + endTimeA + ";" + destinationA + "\n";
        }

        var otherBody = $('#ot');
        var otherRows = otherBody.find('tr');
        for (var y = 0; y < otherRows.length; y++) {
            var colsO = $(otherRows[y]).find('td');

            var nameO = colsO[0].innerHTML;
            var temp4 = colsO[1].innerHTML.split(',');
            var startDateO = temp4[0];  //not optional
            var startTimeO = temp4[1];
            if (startTimeO.toString() === "-") {
                startTimeO = "empty";
            }

            var endDateO = colsO[2].innerHTML;
            if (endDateO.toString() === "-") {
                endDateO = "empty";
            }

            var descriptionO = colsO[3].innerHTML;

            result += "Other;" + nameO + ";" + descriptionO + ";" + startDateO + ";" + startTimeO + ";" +
                endDateO + "\n";
        }

        if (result.length > 0) {
            result = result.substr(0, result.length - 1);
        }
        return result;
    }



function storeRequest() {
    var result = getData();
    if (result === "") result = "empty";
    $.ajax({
        url: 'ControllerStore',
        type: 'GET',
        data: {
            result: result
        },
        success:
            function(result) {
                //alert("Saved successfully")
                requestTransit();
                requestWeather();
                requestWeatherDest();
        }
    });
}
