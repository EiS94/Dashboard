//Ändern der Tablerow
var rIndex,
    table = document.getElementById("tableComplete"),
    kind;

function showDatesFromSelectedDate() {
    var selectedDate = new Date(yearIndex, monthIndex, dayIndex);
    var tableBd = document.getElementById("bd");
    for (var i = 0, row; row = tableBd.rows[i]; i++) {
        var birthday = row.cells[2].innerText;
        var splitBd = birthday.split("-");
        var dayBd = parseInt(splitBd[2]);
        var monthBd = parseInt(splitBd[1]);
        if (parseInt(dayIndex) === dayBd && monthIndex + 1 === monthBd) row.style.display = '';
        else row.style.display = 'none';
    }
    var tableApp = document.getElementById("ap");
    for (var i = 0, row; row = tableApp.rows[i]; i++) {
        var appointment = row.cells[3].innerText;
        var splitAp = appointment.split("-");
        var yearAp = parseInt(splitAp[0]);
        var monthAp = parseInt(splitAp[1]);
        var daySplitNo = splitAp[2].split(",");
        var dayAp = parseInt(daySplitNo[0]);
        if (parseInt(dayIndex) === dayAp && monthIndex + 1 === monthAp && yearIndex === yearAp) row.style.display = '';
        else row.style.display = 'none';
    }
    var tableNotes = document.getElementById("ot");
    for (var i = 0, row; row = tableNotes.rows[i]; i++) {
        var note = row.cells[2].innerText;
        var splitNo = note.split("-");
        var yearNo = parseInt(splitNo[0]);
        var monthNo = parseInt(splitNo[1]);
        var daySplit = splitNo[2].split(",");
        var dayNo = parseInt(daySplit[0]);
        if (parseInt(dayIndex) === dayNo && monthIndex + 1 === monthNo && yearIndex === yearNo) row.style.display = '';
        else row.style.display = 'none';
    }
}

function showDatesAktuell() {
    showDatesFromSelectedDate()
}

function showDatesWeek() {
    var selectedDate = new Date(yearIndex, monthIndex, dayIndex);
    var tableBd = document.getElementById("bd");
    for (var i = 0, row; row = tableBd.rows[i]; i++) {
        var birthday = row.cells[2].innerText;
        var splitBd = birthday.split("-");
        var dayBd = parseInt(splitBd[2]);
        var monthBd = parseInt(splitBd[1]);

        if (parseInt(dayIndex) <= dayBd && dayBd < parseInt(dayIndex) + 7 && monthIndex + 1 === monthBd) row.style.display = '';
        else row.style.display = 'none';
    }
    var tableApp = document.getElementById("ap");
    for (var i = 0, row; row = tableApp.rows[i]; i++) {
        var appointment = row.cells[3].innerText;
        var splitAp = appointment.split("-");
        var yearAp = parseInt(splitAp[0]);
        var monthAp = parseInt(splitAp[1]);
        var daySplitNo = splitAp[2].split(",");
        var dayAp = parseInt(daySplitNo[0]);

        if (parseInt(dayIndex) <= dayAp && dayAp < parseInt(dayIndex) + 7 && monthIndex + 1 === monthAp && yearIndex === yearAp) row.style.display = '';
        else row.style.display = 'none';
    }
    var tableNotes = document.getElementById("ot");
    for (var i = 0, row; row = tableNotes.rows[i]; i++) {
        var note = row.cells[2].innerText;
        var splitNo = note.split("-");
        var yearNo = parseInt(splitNo[0]);
        var monthNo = parseInt(splitNo[1]);
        var daySplit = splitNo[2].split(",");
        var dayNo = parseInt(daySplit[0]);
        if (parseInt(dayIndex) <= dayNo && dayNo < parseInt(dayIndex) + 7 && monthIndex + 1 === monthNo && yearIndex === yearNo) row.style.display = '';
        else row.style.display = 'none';
    }
}

function showDatesMonth() {
    var selectedDate = new Date(yearIndex, monthIndex, dayIndex);
    var tableBd = document.getElementById("bd");
    for (var i = 0, row; row = tableBd.rows[i]; i++) {
        var birthday = row.cells[2].innerText;
        var splitBd = birthday.split("-");
        //var dayBd = parseInt(splitBd[2]);
        var monthBd = parseInt(splitBd[1]);
        if (monthIndex + 1 === monthBd) row.style.display = '';
        else row.style.display = 'none';
    }
    var tableApp = document.getElementById("ap");
    for (var i = 0, row; row = tableApp.rows[i]; i++) {
        var appointment = row.cells[3].innerText;
        var splitAp = appointment.split("-");
        var yearAp = parseInt(splitAp[0]);
        var monthAp = parseInt(splitAp[1]);
        //var daySplitNo = splitAp[2].split(",");
        //var dayAp = parseInt(daySplitNo[0]);
        if (monthIndex + 1 === monthAp && yearIndex === yearAp) row.style.display = '';
        else row.style.display = 'none';
    }
    var tableNotes = document.getElementById("ot");
    for (var i = 0, row; row = tableNotes.rows[i]; i++) {
        var note = row.cells[2].innerText;
        var splitNo = note.split("-");
        var yearNo = parseInt(splitNo[0]);
        var monthNo = parseInt(splitNo[1]);
        //var daySplit = splitNo[2].split(",");
        //var dayNo = parseInt(daySplit[0]);
        if (monthIndex + 1 === monthNo && yearIndex === yearNo) row.style.display = '';
        else row.style.display = 'none';
    }

}

function showDatesYear() {
    var selectedDate = new Date(yearIndex, monthIndex, dayIndex);
    var tableBd = document.getElementById("bd");
    for (var i = 0, row; row = tableBd.rows[i]; i++) {
        //var birthday = row.cells[2].innerText;
        //var splitBd = birthday.split("-");
        //var dayBd = parseInt(splitBd[2]);
        //var monthBd = parseInt(splitBd[1]);
        //var yearBd=parseInt(splitBd[0]);
        row.style.display = '';
    }
    var tableApp = document.getElementById("ap");
    for (var i = 0, row; row = tableApp.rows[i]; i++) {
        var appointment = row.cells[3].innerText;
        var splitAp = appointment.split("-");
        var yearAp = parseInt(splitAp[0]);
        //var monthAp = parseInt(splitAp[1]);
        //var daySplitNo = splitAp[2].split(",");
        //var dayAp = parseInt(daySplitNo[0]);
        if (yearIndex === yearAp) row.style.display = '';
        else row.style.display = 'none';
    }
    var tableNotes = document.getElementById("ot");
    for (var i = 0, row; row = tableNotes.rows[i]; i++) {
        var note = row.cells[2].innerText;
        var splitNo = note.split("-");
        var yearNo = parseInt(splitNo[0]);
        //var monthNo = parseInt(splitNo[1]);
        //var daySplit = splitNo[2].split(",");
        //var dayNo = parseInt(daySplit[0]);
        if (yearIndex === yearNo) row.style.display = '';
        else row.style.display = 'none';
    }

}

function checkEmptyInput() {
    var isEmpty = false;
    if (kind === "birth") {
        var fname = document.getElementById("firstName").value,
            birthday = document.getElementById("birthday").value,
            year = birthday.split("-");
        //age = document.getElementById("age").value;
        //present = document.getElementById("pesent").value;

        if (fname === "") {
            alert("Vorname darf nicht leer sein!");
            isEmpty = true;
        } else if (birthday === "") {
            alert("Geburtstag darf nicht leer sein!");
            isEmpty = true;
        } else if (year[0].length > 4) {
            alert("Bitte geben Sie ein korrektes Datum ein!");
            isEmpty = true;
        }

        /*else if (age === "") {
            alert("");
            isEmpty = true;
        } else if (present === "") {
            present = "-";
        }*/
    } else if (kind === "app") {
        var name = document.getElementById("nameApp").value,
            startD = document.getElementById("startDateApp").value,
            endD = document.getElementById("endDateApp").value,
            startYear = startD.split("-"),
            endYear = endD.split("-");


        //startT = document.getElementById("startTimeApp").value,
        //location = document.getElementById("locationApp").value,
        //endT = document.getElementById("endTimeApp").value;

        if (name === "") {
            alert("Name darf nicht leer sein!");
            isEmpty = true;
        } else if (startD === "") {
            alert("Startdatum darf nicht leer sein!");
            isEmpty = true;
        } else if (startYear[0].length > 4) {
            alert("Bitte geben Sie ein korrektes Startdatum ein!");
            isEmpty = true;
        } else if (endYear[0].length > 4) {
            alert("Bitte geben Sie ein korrektes Enddatum ein!");
            isEmpty = true;
        }


        /*else if (location === "") {
            document.getElementById("locationApp").value = "-";
        } else if (startT === "") {
            document.getElementById("startTimeApp").value = "-";
        } else if (endD === "") {
            document.getElementById("endDateApp").value = "-";
        } else if (endT === "") {
            document.getElementById("endTimeApp").value = "-";
        }*/
    } else if (kind === "other") {
        var nameO = document.getElementById("nameOther").value,
            startDO = document.getElementById("startDateOther").value,
            endDO = document.getElementById("endDateApp").value,
            startYearO = startDO.split("-"),
            endYearO = endDO.split("-");
        //startTO = document.getElementById("startTimeApp").value,
        //info = document.getElementById("infoOther").value;

        if (nameO === "") {
            alert("Name kann nicht leer sein!");
            isEmpty = true;
        } else if (startDO === "") {
            alert("Startdatum kann nicht leer sein!");
            isEmpty = true;
        } else if (startYearO[0].length > 4) {
            alert("Bitte geben Sie ein korrektes Startdatum ein!");
            isEmpty = true;
        } else if (endYearO[0].length > 4) {
            alert("Bitte geben Sie ein korrektes Enddatum ein!");
            isEmpty = true;
        }
        /*else if (startTO === "") {
            document.getElementById("startTimeOther").value = "-";
        } else if (endDO === "") {
            document.getElementById("endDateOther").value = "-";
        } else if (endTO === "") {
            document.getElementById("endTimeOther").value = "-";
        }else if (info===""){
            document.getElementById("infoOther").value = "-";
        }*/
    }
    return isEmpty;
}

// add Row
function addTableRowBirthday() {

    // get the table by id
    // create a new row and cells
    // get value from input text
    // set the values into row cell's
    kind = "birth";
    if (!checkEmptyInput()) {
        /* var kindTable = document.getElementById("bd");
        var newRow = kindTable.insertRow(kindTable.length),
            cell1 = newRow.insertCell(0),
            cell2 = newRow.insertCell(1),
            cell3 = newRow.insertCell(2),
            cell4 = newRow.insertCell(3),
            cell5 = newRow.insertCell(4),
            empty = "";*/
        var fname = document.getElementById("firstName").value,
            lname = document.getElementById("lastName").value,
            birthday = document.getElementById("birthday").value,
            age = document.getElementById("age").value,
            present = document.getElementById("present").value;
        if (lname === "") {
            lname = "-";
        }

        /*cell1.innerHTML = empty;
        if (lname === "") {
            cell2.innerHTML = fname + ",-"
        } else cell2.innerHTML = fname + "," + lname;
        cell3.innerHTML = birthday;
        cell4.innerHTML = age;
        if (present === "") {
            cell5.innerHTML = "-";
        } else cell5.innerHTML = present;*/
        $('#tableComplete').find('#bd').append("<tr class=\"birthdayRow\"><th scope=\"col\"></th>"
            + "<td>" + fname + "," + lname + "</td>"
            + "<td>" + birthday + "</td>"
            + "<td>" + age + "</td>"
            + "<td>" + present + "</td>");
        selectedRowToInput();
        storeRequest();
        alert("Erfogreich hinzugefügt!");
        showDatesFromSelectedDate();
        $('#btnGroupDrop2').text("Tag");
    }



}

function addTableRowApp() {

    kind = "app";
    if (!checkEmptyInput()) {
        /*var kindTable = document.getElementById("ap");
        var newRowA = kindTable.insertRow(kindTable.length),
            cell1A = newRowA.insertCell(0),
            cell2A = newRowA.insertCell(1),
            cell3A = newRowA.insertCell(2),
            cell4A = newRowA.insertCell(3),
            cell5A = newRowA.insertCell(4),
            emptyA = "";*/
        var name = document.getElementById("nameApp").value,
            location = document.getElementById("locationApp").value,
            startD = document.getElementById("startDateApp").value,
            startT = document.getElementById("startTimeApp").value,
            endD = document.getElementById("endDateApp").value,
            endT = document.getElementById("endTimeApp").value;
        if (location === "") {
            location = "-";
        }
        if (startT === "") {
            startT = "-";
        }
        if (endD === "") {
            endD = "-";
        }
        if (endT === "") {
            endT = "-";
        }

        /*cell1A.innerHTML = emptyA;
        cell2A.innerHTML = name;
        if (location === "") {
            cell3A.innerHTML = "-";
        } else cell3A.innerHTML = location;
        if (startT === "") {
            cell4A.innerHTML = startD + ",-"
        } else cell4A.innerHTML = startD + "," + startT;
        if (endD === "" && endT === "") {
            cell5A.innerHTML = "-,-"
        } else if (endD === "") {
            cell5A.innerHTML = "-," + endT;
        } else if (endT === "") {
            cell5A.innerHTML = endD + ",-";
        } else cell5A.innerHTML = endD + "," + endT;*/
        $('#tableComplete').find('#ap').append("<tr class=\"appRow\"><th scope=\"col\"></th>"
            + "<td>" + name + "</td>"
            + "<td>" + location + "</td>"
            + "<td>" + startD + "," + startT + "</td>"
            + "<td>" + endD + "," + endT + "</td>");
        selectedRowToInput();
        storeRequest();
        alert("Erfogreich hinzugefügt!");
        showDatesFromSelectedDate();
        $('#btnGroupDrop2').text("Tag");
    }


}

function addTableRowOther() {

    kind = "other";
    if (!checkEmptyInput()) {
        /* var kindTable = document.getElementById("ot");
        var newRowO = kindTable.insertRow(kindTable.length),
             cell1O = newRowO.insertCell(0),
             cell2O = newRowO.insertCell(1),
             cell3O = newRowO.insertCell(2),
             cell4O = newRowO.insertCell(3),
             cell5O = newRowO.insertCell(4),
             emptyO = "",*/
        var nameO = document.getElementById("nameOther").value,
            startDO = document.getElementById("startDateOther").value,
            startTO = document.getElementById("startTimeOther").value,
            endDO = document.getElementById("endDateOther").value,
            info = document.getElementById("infoOther").value;
        if (startTO === "") {
            startTO = "-";
        }
        if (endDO === "") {
            endDO = "-";
        }
        if (info === "") {
            info = "-";
        }

        /*cell1O.innerHTML = emptyO;
        cell2O.innerHTML = nameO;
        if (startTO === "") {
            cell3O.innerHTML = startDO + ",-"
        } else cell3O.innerHTML = startDO + "," + startTO;
        if (endDO === "") {
            cell4O.innerHTML = "-"
        } else cell4O.innerHTML = endDO;
        if (info === "") {
            cell5O.innerHTML = "-"
        } else cell5O.innerHTML = info;*/
        $('#tableComplete').find('#ot').append("<tr class=\"otherRow\"><th scope=\"col\"></th>"
            + "<td>" + nameO + "</td>"
            + "<td>" + startDO + "," + startTO + "</td>"
            + "<td>" + endDO + "</td>"
            + "<td>" + info + "</td>");
        selectedRowToInput();
        storeRequest();
        alert("Erfogreich hinzugefügt!");
        showDatesFromSelectedDate();
        $('#btnGroupDrop2').text("Tag");
    }

}


// display selected row data into input text
function selectedRowToInput() {
    for (var i = 1; i < table.rows.length; i++) {
        // get the selected row index
        table.rows[i].onclick = function () {
            rIndex = this.rowIndex;
            if (this.className === "birthdayRow active") {
                kind = "birth";
                var str = this.cells[1].innerHTML;
                var array = str.split(",");
                document.getElementById("firstName").value = array[0];
                if (array[1] === "-") {
                    document.getElementById("lastName").value = "";
                } else document.getElementById("lastName").value = array[1];
                document.getElementById("birthday").value = this.cells[2].innerHTML;
                document.getElementById("age").value = this.cells[3].innerHTML;
                if (this.cells[4] === "-") {
                    document.getElementById("present").value = "";
                } else document.getElementById("present").value = this.cells[4].innerHTML;
            } else if (this.className === "appRow active") {
                kind = "app";
                var start = (this.cells[3].innerHTML).split(",");
                var end = (this.cells[4].innerHTML).split(",");

                document.getElementById("nameApp").value = this.cells[1].innerHTML;
                if (this.cells[2].innerHTML === "-") {
                    document.getElementById("locationApp").value = "-";
                }
                if (start[0] === "-") {
                    document.getElementById("startDateApp").value = "-";
                } else document.getElementById("startDateApp").value = start[0];
                if (start[1] === "-") {
                    document.getElementById("startTimeApp").value = "-";
                } else document.getElementById("startTimeApp").value = start[1];
                if (end[0] === "-") {
                    document.getElementById("endDateApp").value = "-";
                } else document.getElementById("endDateApp").value = end[0];
                if (end[1] === "-") {
                    document.getElementById("endTimeApp").value = "-";
                } else document.getElementById("endTimeApp").value = end[1];

            } else if (this.className === "otherRow active") {
                kind = "other";
                var startO = (this.cells[2].innerHTML).split(",");


                document.getElementById("nameOther").value = this.cells[1].innerHTML;
                if (startO[0] === "-") {
                    document.getElementById("startDateOther").value = "-";
                } else document.getElementById("startDateOther").value = startO[0];
                if (startO[1] === "-") {
                    document.getElementById("startTimeOther").value = "-";
                } else document.getElementById("startTimeOther").value = startO[1];
                if (this.cells[3].innerHTML === "-") {
                    document.getElementById("endDateOther").value = "-";
                } else document.getElementById("endDateOther").value = this.cells[3].innerHTML;
                if (this.cells[4].innerHTML === "-") {
                    document.getElementById("infoOther").value = "-";
                } else document.getElementById("infoOther").value = this.cells[4].innerHTML;
            }
        }
    }
}

function editTableSelectedRow() {
    if (kind === "birth") {

        var fname = document.getElementById("firstName").value,
            lname = document.getElementById("lastName").value,
            birthday = document.getElementById("birthday").value,
            age = document.getElementById("age").value,
            present = document.getElementById("present").value;
        if (!checkEmptyInput()) {
            if (lname === "") {
                table.rows[rIndex].cells[1].innerHTML = fname + ",-";
            } else table.rows[rIndex].cells[1].innerHTML = fname + "," + lname;
            table.rows[rIndex].cells[2].innerHTML = birthday;
            table.rows[rIndex].cells[3].innerHTML = age;
            if (present === "") {
                table.rows[rIndex].cells[4].innerHTML = "-";
            } else table.rows[rIndex].cells[4].innerHTML = present;
            storeRequest();
            alert("Erfolgreich geändert!");
            showDatesFromSelectedDate();
            $('#btnGroupDrop2').text("Tag");
        }
    } else if (kind === "app") {
        var name = document.getElementById("nameApp").value,
            location = document.getElementById("locationApp").value,
            startD = document.getElementById("startDateApp").value,
            startT = document.getElementById("startTimeApp").value,
            endD = document.getElementById("endDateApp").value,
            endT = document.getElementById("endTimeApp").value;
        if (!checkEmptyInput()) {
            table.rows[rIndex].cells[1].innerHTML = name;
            if (location === "") {
                table.rows[rIndex].cells[2].innerHTML = "-";
            } else table.rows[rIndex].cells[2].innerHTML = location;
            if (startT === "") {
                table.rows[rIndex].cells[3].innerHTML = startD + ",-";
            } else table.rows[rIndex].cells[3].innerHTML = startD + "," + startT;
            if (endD === "" && endT === "") {
                table.rows[rIndex].cells[4].innerHTML = "-,-";
            } else if (endD === "") {
                table.rows[rIndex].cells[4].innerHTML = "-," + endT;
            } else if (endT === "") {
                table.rows[rIndex].cells[4].innerHTML = endD + ",-";
            } else table.rows[rIndex].cells[4].innerHTML = endD + "," + endT;
            storeRequest();
            alert("Erfolgreich geändert!");
            showDatesFromSelectedDate();
            $('#btnGroupDrop2').text("Tag");
        }
    } else if (kind === "other") {
        var nameO = document.getElementById("nameOther").value,
            startDO = document.getElementById("startDateOther").value,
            startTO = document.getElementById("startTimeOther").value,
            endDO = document.getElementById("endDateOther").value,
            info = document.getElementById("infoOther").value;
        if (!checkEmptyInput()) {
            table.rows[rIndex].cells[1].innerHTML = nameO;
            if (startTO === "") {
                table.rows[rIndex].cells[2].innerHTML = startDO + ",-";
            } else table.rows[rIndex].cells[2].innerHTML = startDO + "," + startTO;
            if (endDO === "") {
                table.rows[rIndex].cells[3].innerHTML = "-";
            } else table.rows[rIndex].cells[3].innerHTML = endDO;
            if (info === "") {
                table.rows[rIndex].cells[4].innerHTML = "-";
            } else table.rows[rIndex].cells[4].innerHTML = info;
            storeRequest();
            alert("Erfolgreich geändert!");
            showDatesFromSelectedDate();
            $('#btnGroupDrop2').text("Tag");
        }
    }

}

function clickTable() {
    $('#tableComplete tr').on('click', function () {
        $('#tableComplete tr').removeClass('active');
        $(this).addClass('active');
    });
    selectedRowToInput();
}

function deleteTable() {
    if (confirm("Wirklich löschen?")) {
        table.rows[rIndex].remove();
    } else {
        //nichts
    }
    storeRequest();
}

function editButton() {
    selectedRowToInput();
    if (kind === "birth") {
        $( "#saveBirthday" ).prop( "disabled", true );
        $( "#editBirthday" ).prop( "disabled", false );
        $('#birth').modal('show');
    } else if (kind === "app") {
        $( "#saveApp" ).prop( "disabled", true );
        $( "#editApp" ).prop( "disabled", false );
        $('#app').modal('show');
    } else if (kind === "other") {
        $( "#saveO" ).prop( "disabled", true );
        $( "#editO" ).prop( "disabled", false );
        $('#other').modal('show');

    }
}