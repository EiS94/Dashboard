jQuery(document).ready(function ($) {

    var counter = 0;

    window.setInterval(function(){
        updateClock();
    }, 1000);

    window.setInterval(function () {
        counter++;
        if (counter < 2 ) showDatesFromSelectedDate();
    }, 1000);

    /*$("#clickTable tbody td").click(function () {
        var $row = $(this).closest("tr"); // Finds the closest row <tr>
        var $tds = $row.find("td");                // Finds all children <td> elements
        var array = [];
        $.each($tds, function () {                // Visits every single <td> element
            array.push($(this).text())
        });
        //alert(array);
    });


    //table row highlighting for AppointmentTable
    $(function () {
        $('#clickTable tbody td').click(function () {
            $('tr').removeClass('active');
            $(this).parent().addClass('active');
        });

    });

    //löschen der Tablerow
    $(function () {
        $('#clickTable tbody tr').click(function () {
            var $row = $(this);
            $("#removeButton").click(function () {
                $row.remove();
            });
        });
    });*/


    //clear Forms
    $(function () {
        $('#clearBirthday').click(function () {
            $('form').trigger("reset");
        });
    });
    $(function () {
        $('#clearApp').click(function () {
            $('form').trigger("reset");
        });
    });
    $(function () {
        $('#clearO').click(function () {
            $('form').trigger("reset");
        });
    });
    //clear Forms vor dem hinzufügen; disable den editButton
    $(function () {
        $('#dropbirth').click(function () {
            $('form').trigger("reset");
            $( "#saveBirthday" ).prop( "disabled", false );
            $( "#editBirthday" ).prop( "disabled", true );

        });
    });
    $(function () {
        $('#dropapp').click(function () {
            $('form').trigger("reset");
            $( "#saveApp" ).prop( "disabled", false );
            $( "#editApp" ).prop( "disabled", true );

        });
    });
    $(function () {
        $('#dropother').click(function () {
            $('form').trigger("reset");
            $( "#saveO" ).prop( "disabled", false );
            $( "#editO" ).prop( "disabled", true );

        });
    });


    //change input of age out of date
    $('#birthday').change(function () {
        var now = new Date();
        var birthDate = new Date($(this).val());
        var age = now.getFullYear() - birthDate.getFullYear();
        var m = now.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && now.getDate() < birthDate.getDate())) {
            age--;
        }
        $('#age').val(age);
    });

    //zeigt den ausgewählten Inhalt im Dropdown von AppointmentTable
    $(".dropdown-menu li a").click(function(){
        var selText = $(this).text();
        $(this).parents('#dropTime').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
    });
    //funktionalität der Toggle-Buttons für die Wetterkarten
    $('#hideshow').on('click', function(event) {
        $('#weather').toggle();
        $('#weatherD').toggle();
    });
    $('#hideshowD').on('click', function(event) {
        $('#weather').toggle();
        $('#weatherD').toggle();
    });
});

function updateClock() {
    var navinfo = $('#nav').text();
    //returns "city, HH:MM Uhr, weekday, DD. Month YYYY"
    var split = navinfo.split(",");
    var time = new Date();
    time.setMinutes(time.getMinutes() - d.getTimezoneOffset());
    var minutes = time.getUTCMinutes().toString();
    if (minutes.length === 1) minutes = "0" + minutes;
    var secounds = time.getUTCSeconds().toString();
    if (secounds.length === 1) secounds = "0" + secounds;
    var path = document.getElementById( 'navPic' ).src;
    $('#nav').html("<img id='navPic' src=\"" + path + "\" class=\"d-inline-block align-top\" alt=\"...\" width=\"30\" height=\"30\">" +
        split[0] + ", " + time.getUTCHours() + ":" + minutes + ":" + secounds + " Uhr, " + split[2] + ", " + split[3]);
}

