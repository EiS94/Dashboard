var events = [
  {'Date': new Date(2019, 10, 7), 'Title': 'Doctor appointment at 3:25pm.'},
  {'Date': new Date(2019, 10, 18), 'Title': 'New Garfield movie comes out!'},
  {'Date': new Date(2019, 10, 27), 'Title': '25 year anniversary'},
];
var settings = {};
var element = document.getElementById('calendar');
calendar(element, events, settings);
