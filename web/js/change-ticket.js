'use strict';

let airlines=[];
let flights=[];
let ticket;


function loadUser(){
    let username = window.localStorage.getItem("username").toString();
    let type = window.localStorage.getItem("type").toString();
    $('#user').text(username);
    $('#type').text(type);

    if(type === "ADMIN"){
        $('#reservations-btn').css("display", "none");
    }
}


function loadAirlines() {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let result = JSON.parse(this.response);
            let comboBox = document.getElementById("airlines");

            for (let i = 0; i < result.length; i++) {
                let option = document.createElement("option");
                option.text = result[i].name;
                comboBox.add(option, comboBox[i]);
                airlines.push(result[i]);
            }
            $('#airlines-message').text("");
        }else{
            $('#airlines-message').text("Mistake happened while loading airlines!");
        }
    };

    xhttp.open("GET", "/rest/airlines", true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();
}

function loadFlights() {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let result = JSON.parse(this.response);
            let comboBox = document.getElementById("flights");

            for (let i = 0; i < result.length; i++) {
                let option = document.createElement("option");
                option.text = result[i].origin.name + "-" + result[i].destination.name;
                comboBox.add(option, comboBox[i]);
                flights.push(result[i]);
            }
             $('#flights-message').text("");

        }else{
            $('#flights-message').text("Mistake happened while loading flights!");
        }
    };

    xhttp.open("GET", "/rest/flights", true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();

}

function logout(){
    let username = window.localStorage.getItem("username").toString();
    let password = window.localStorage.getItem("password").toString();

    $.ajax({
        url: "/rest/users/logout",
        type:"GET",
        data: {
            username: username,
            password: password
        },
        contentType:"application/json",
        dataType:"json",
        success: function(data){
            console.log("Odgovor servera:");
            console.log(data);
            // brisemo token
            window.localStorage.clear();
            // redirekcija
            window.location.replace("/login.jsp");
        },
        error: function(data){
            alert("Must be a valid user!");
        }
    });

    return false;
}

function cancel(){
    $('#depart-date').text("");
    $('#change-date').text("");
    $('#count').text("");
}


function changeTicket(id,oneWay,departDate,returnDate,airline,flight,count,flight_id){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status >= 200 && this.status <300 ){
            window.location.replace("/index.jsp");
        }else{
            window.location.replace("/index.jsp");
        }
    };

    xhttp.open("POST", "/rest/tickets/change", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send(JSON.stringify({id:id,airline: airline, count:count, departDate:departDate, flight:flight, flight_id:flight_id, oneWay:oneWay, returnDate:returnDate}));
}

function stringToDate(_date,_format) {
    let formatLowerCase=_format.toLowerCase();
    let formatItems=formatLowerCase.split("-");
    let dateItems=_date.split("-");
    let monthIndex=formatItems.indexOf("mm");
    let dayIndex=formatItems.indexOf("dd");
    let yearIndex=formatItems.indexOf("yyyy");
    let month=parseInt(dateItems[monthIndex]);
    month-=1;
    let formatedDate = new Date(dateItems[yearIndex],month,dateItems[dayIndex]);
    return formatedDate;
}


function changeTicketForm() {

    let ticket_id = window.localStorage.getItem("ticket_id");

    // treba da se promeni karta, let i avio kompanija

    let flightString = $("#flights").val();
    let flightArray = flightString.split("-");
    let oneWay = $("#oneway").val();
    let airline = $("#airlines").val();
    let departDate = $("#depart-date").val();
    let departDateFormatted = stringToDate(departDate.toString(),"dd-mm-yyyy");
    let returnDate = $("#return-date").val();
    let count = $('#count').val();
    let returnDateFormatted = stringToDate(returnDate.toString(),"dd-mm-yyyy");
    let airline_object = null;
    let flight_object= null;

    if(departDate==="" || returnDate==="" || count===""){
        $('#mistake').text("All fields must be full!");
        return;
    }else{
        $('#mistake').text("");
    }

    if(!(returnDate.includes("-"))){
        $('#mistake').text("You have to use format dd-mm-yyyy for dates!");
        return;
    }else{
        $('#mistake').text("");
    }

    if(!(departDate.includes("-"))){
        $('#mistake').text("You have to use format dd-mm-yyyy for dates!");
        return;
    }else{
        $('#mistake').text("");
    }

    if(departDateFormatted.getTime() > returnDateFormatted.getTime()){
        $('#mistake').text("Depart date can't be after return date!");
        return;
    }else{
        $('#mistake').text("");
    }

    if(count === '0'){
        $('#mistake2').text("Count can't be 0");
        return;
    }else{
        $('#mistake2').text("");
    }


    // da bismo dobili objekat kompanije
    for (const element of airlines) {
        if(element.name === airline){
            airline_object= element;
        }
    }


    // da bismo dobili objekat leta
    let flight_id;
    for (const f of flights) {
        if(f.origin.name === flightArray[0] && f.destination.name === flightArray[1]){
            flight_object= f;
            flight_id = f.id;
        }
    }

    changeTicket(ticket_id,oneWay,departDate,returnDate,airline_object,flight_object,count, flight_id);
    return false;
}


window.onload = loadUser();
//za combo boxove
window.onload = loadAirlines();
window.onload = loadFlights();
document.getElementById('logout-btn').addEventListener("click", function() {logout(); }, false);
document.getElementById('cancel-btn').addEventListener("click", cancel);
document.getElementById('change-ticket-btn').addEventListener("click", changeTicketForm);



