'use strict';

let airlines=[];
let flights=[];

// UCITAVANJE PODATAKA O USERU I SAKRIVANJE FORMI U ZAVISNOSTI OD TIPA USERA
function loadUser(){
    let username = window.localStorage.getItem("username").toString();
    let type = window.localStorage.getItem("type").toString();
    let bookingsNumber = window.localStorage.getItem("bookings-number").length;
    let bookingBtn = "Reservations: " + bookingsNumber;
    $('#user').text(username);
    $('#type').text(type);

    if(type === "ADMIN"){
        $('#search-form').css("visibility", "hidden");
        $('#reservations-btn').css("visibility", "hidden");
    }else if(type === "REGULAR"){
        $('#add-ticket-form').css("visibility", "hidden");
        $('#register-form').css("visibility", "hidden");
        $("#reservations-btn").attr('value', bookingBtn.toString());
    }
}


// UCITAVANJE KARATA KADA SE PRITISNE FILTER ONE-WAY
function filterTickets() {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {

            let filter = document.getElementById("tickets-filter").value;
            let result = JSON.parse(this.response);
            let table = document.getElementById("tickets-tbl");
            let oldTBody = table.tBodies[0];
            let newTBody = document.createElement("tBody");

            for (let i = 0; i < result.length; i++){
                let bRow = document.createElement("tr");
                if(filter==="one-way" && result[i].oneWay==="Yes") {

                    let tdId = document.createElement("td");
                    tdId.innerHTML = result[i].id;

                    let tdFrom = document.createElement("td");
                    tdFrom.innerHTML = result[i].flight.origin.name;

                    let tdTo = document.createElement("td");
                    tdTo.innerHTML = result[i].flight.destination.name;

                    let tdOneWay = document.createElement("td");
                    tdOneWay.innerHTML = result[i].oneWay;

                    let tdDepartDate = document.createElement("td");
                    tdDepartDate.innerHTML = result[i].departDate;

                    let tdReturnDate = document.createElement("td");
                    tdReturnDate.innerHTML = result[i].returnDate;

                    let tdAirline = document.createElement("td");
                    tdAirline.innerHTML = result[i].airline.name;

                    let tdCount = document.createElement("td");
                    tdCount.innerHTML = result[i].count;

                    bRow.appendChild(tdId);
                    bRow.appendChild(tdFrom);
                    bRow.appendChild(tdTo);
                    bRow.appendChild(tdOneWay);
                    bRow.appendChild(tdDepartDate);
                    bRow.appendChild(tdReturnDate);
                    bRow.appendChild(tdAirline);
                    bRow.appendChild(tdCount);

                    let type = window.localStorage.getItem("type").toString();
                    let btn = document.createElement('input');
                    let btn2 = document.createElement('input');

                    if(type === "ADMIN"){
                        btn.id = "delete-btn";
                        btn.type = "button";
                        btn.className = "btn";
                        btn.value = "Delete";
                        btn.onclick =  function(){deleteTicket(result[i].id)};

                        btn2.type = "button";
                        btn2.className = "btn";
                        btn2.value = "Change ticket";
                        btn2.onclick =  function(){changeTicket(result[i].id)};
                        let tdButton = document.createElement("td");
                        tdButton.appendChild(btn);

                        let tdButton2 = document.createElement("td");
                        tdButton2.appendChild(btn2);

                        bRow.appendChild(tdButton);
                        bRow.appendChild(tdButton2);
                    }else if(type === "REGULAR"){
                        btn.type = "button";
                        btn.className = "btn";
                        btn.value = "Book";
                        btn.onclick = function(){bookATicketAndGetUser(result[i])};
                        let tdButton = document.createElement("td");
                        if(isTicketAvailable(result[i].departDate)){
                            tdButton.appendChild(btn);
                            bRow.appendChild(tdButton);
                        }
                    }


                }else if(filter === "two-ways" && result[i].oneWay==="No"){

                    let tdId = document.createElement("td");
                    tdId.innerHTML = result[i].id;

                    let tdFrom = document.createElement("td");
                    tdFrom.innerHTML = result[i].flight.origin.name;

                    let tdTo = document.createElement("td");
                    tdTo.innerHTML = result[i].flight.destination.name;

                    let tdOneWay = document.createElement("td");
                    tdOneWay.innerHTML = result[i].oneWay;

                    let tdDepartDate = document.createElement("td");
                    tdDepartDate.innerHTML = result[i].departDate;

                    let tdReturnDate = document.createElement("td");
                    tdReturnDate.innerHTML = result[i].returnDate;

                    let tdAirline = document.createElement("td");
                    tdAirline.innerHTML = result[i].airline.name;

                    let tdCount = document.createElement("td");
                    tdCount.innerHTML = result[i].count;

                    bRow.appendChild(tdId);
                    bRow.appendChild(tdFrom);
                    bRow.appendChild(tdTo);
                    bRow.appendChild(tdOneWay);
                    bRow.appendChild(tdDepartDate);
                    bRow.appendChild(tdReturnDate);
                    bRow.appendChild(tdAirline);
                    bRow.appendChild(tdCount);

                    let type = window.localStorage.getItem("type").toString();
                    let btn = document.createElement('input');
                    let btn2 = document.createElement('input');

                    if(type === "ADMIN"){
                        btn.id = "delete-btn";
                        btn.type = "button";
                        btn.className = "btn";
                        btn.value = "Delete";
                        btn.onclick =  function(){deleteTicket(result[i].id)};

                        btn2.type = "button";
                        btn2.className = "btn";
                        btn2.value = "Change ticket";
                        btn2.onclick =  function(){changeTicket(result[i].id)};
                        let tdButton = document.createElement("td");
                        tdButton.appendChild(btn);

                        let tdButton2 = document.createElement("td");
                        tdButton2.appendChild(btn2);

                        bRow.appendChild(tdButton);
                        bRow.appendChild(tdButton2);
                    }else if(type === "REGULAR"){
                        btn.type = "button";
                        btn.className = "btn";
                        btn.value = "Book";
                        btn.onclick = function(){bookATicketAndGetUser(result[i])};
                        let tdButton = document.createElement("td");
                        if(isTicketAvailable(result[i].departDate)){
                            tdButton.appendChild(btn);
                            bRow.appendChild(tdButton);
                        }
                    }

                }else if(filter === "all"){

                    let tdId = document.createElement("td");
                    tdId.innerHTML = result[i].id;

                    let tdFrom = document.createElement("td");
                    tdFrom.innerHTML = result[i].flight.origin.name;

                    let tdTo = document.createElement("td");
                    tdTo.innerHTML = result[i].flight.destination.name;

                    let tdOneWay = document.createElement("td");
                    tdOneWay.innerHTML = result[i].oneWay;

                    let tdDepartDate = document.createElement("td");
                    tdDepartDate.innerHTML = result[i].departDate;

                    let tdReturnDate = document.createElement("td");
                    tdReturnDate.innerHTML = result[i].returnDate;

                    let tdAirline = document.createElement("td");
                    tdAirline.innerHTML = result[i].airline.name;

                    let tdCount = document.createElement("td");
                    tdCount.innerHTML = result[i].count;

                    bRow.appendChild(tdId);
                    bRow.appendChild(tdFrom);
                    bRow.appendChild(tdTo);
                    bRow.appendChild(tdOneWay);
                    bRow.appendChild(tdDepartDate);
                    bRow.appendChild(tdReturnDate);
                    bRow.appendChild(tdAirline);
                    bRow.appendChild(tdCount);

                    let type = window.localStorage.getItem("type").toString();
                    let btn = document.createElement('input');
                    let btn2 = document.createElement('input');

                    if(type === "ADMIN"){
                        btn.id = "delete-btn";
                        btn.type = "button";
                        btn.className = "btn";
                        btn.value = "Delete";
                        btn.onclick =  function(){deleteTicket(result[i].id)};

                        btn2.type = "button";
                        btn2.className = "btn";
                        btn2.value = "Change ticket";
                        btn2.onclick =  function(){changeTicket(result[i].id)};

                        let tdButton = document.createElement("td");
                        tdButton.appendChild(btn);

                        let tdButton2 = document.createElement("td");
                        tdButton2.appendChild(btn2);

                        bRow.appendChild(tdButton);
                        bRow.appendChild(tdButton2);
                    }else if(type === "REGULAR"){
                        btn.type = "button";
                        btn.className = "btn";
                        btn.value = "Book";
                        btn.onclick = function(){bookATicketAndGetUser(result[i])};
                        let tdButton = document.createElement("td");
                        if(isTicketAvailable(result[i].departDate)){
                            tdButton.appendChild(btn);
                            bRow.appendChild(tdButton);
                        }
                    }
                }

                newTBody.appendChild(bRow)
            }
            table.replaceChild(newTBody, oldTBody)
        }
    };

    xhttp.open("GET", "/rest/tickets", true);
    xhttp.send();
}

// TABLE ZA OBICNOG KORISNIKA KADA SE RADI PRETRAGA PO FILTERIMA
function makeFilteredTable(result){

    let table = document.getElementById("tickets-tbl");
    let oldTBody = table.tBodies[0];
    let newTBody = document.createElement("tBody");

    for (let i = 0; i < result.length; i++) {

        let bRow = document.createElement("tr");

            let tdId = document.createElement("td");
            tdId.innerHTML = result[i].id;

            let tdFrom = document.createElement("td");
            tdFrom.innerHTML = result[i].flight.origin.name;

            let tdTo = document.createElement("td");
            tdTo.innerHTML = result[i].flight.destination.name;

            let tdOneWay = document.createElement("td");
            tdOneWay.innerHTML = result[i].oneWay;

            let tdDepartDate = document.createElement("td");
            tdDepartDate.innerHTML = result[i].departDate;

            let tdReturnDate = document.createElement("td");
            tdReturnDate.innerHTML = result[i].returnDate;

            let tdAirline = document.createElement("td");
            tdAirline.innerHTML = result[i].airline.name;

            let tdCount = document.createElement("td");
            tdCount.innerHTML = result[i].count;

            let btn = document.createElement('input');
            btn.id = "book-btn";
            btn.type = "button";
            btn.className = "btn";
            btn.value = "Book";
            btn.onclick = function(){bookATicketAndGetUser(result[i])};

            let tdButton = document.createElement("td");
            tdButton.appendChild(btn);

            bRow.appendChild(tdId);
            bRow.appendChild(tdFrom);
            bRow.appendChild(tdTo);
            bRow.appendChild(tdOneWay);
            bRow.appendChild(tdDepartDate);
            bRow.appendChild(tdReturnDate);
            bRow.appendChild(tdAirline);
            bRow.appendChild(tdCount);
            bRow.appendChild(tdButton);

            newTBody.appendChild(bRow);
        }
    table.replaceChild(newTBody, oldTBody);
}


//FILTRIRANJE KOD OBICNOG KORISNIKA PREMA RETURN/DEPART DATE, ORIGIN, DESTINATION
function filterTicketsByRegularUser(origin, destination, departDate, returnDate,departDateFormatted, returnDateFormatted) {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {

            let filter =  $("#tickets-filter").val();
            let result = JSON.parse(this.response);
            let list = [];

            for (let i = 0; i < result.length; i++) {

                let resultDepartDate = stringToDate(result[i].departDate,"dd-mm-yyyy");
                let resultReturnDate = stringToDate(result[i].returnDate,"dd-mm-yyyy");

                if(filter==="one-way" && result[i].oneWay==="Yes" && result[i].flight.origin.name===origin
                    && result[i].flight.destination.name===destination) {

                    if(departDate!=="" && returnDate===""){
                        if(resultDepartDate.getTime() > departDateFormatted.getTime()){
                            list.push(result);
                        }else{
                            continue;
                        }
                    }else if(departDate==="" && returnDate!==""){
                        if(resultReturnDate.getTime() < returnDateFormatted.getTime()){
                            list.push(result);
                        }else{
                            continue;
                        }
                    }if(departDate!=="" && returnDate!==""){
                        if(resultDepartDate.getTime() > departDateFormatted.getTime() &&
                            resultReturnDate.getTime() < returnDateFormatted.getTime()){
                            list.push(result);
                        }else{
                            continue;
                        }
                    }

                }else if(filter === "two-ways" && result[i].oneWay==="No" && result[i].flight.origin.name===origin
                    && result[i].flight.destination.name===destination){

                    if(departDate!=="" && returnDate===""){
                        if(resultDepartDate.getTime() > departDateFormatted.getTime()){
                            list.push(result);
                        }else{
                            continue;
                        }
                    }else if(departDate==="" && returnDate!==""){
                        if(resultReturnDate.getTime() < returnDateFormatted.getTime()){
                            list.push(result);
                        }else{
                            continue;
                        }
                    }if(departDate!=="" && returnDate!==""){
                        if(resultDepartDate.getTime() > departDateFormatted.getTime() &&
                            resultReturnDate.getTime() < returnDateFormatted.getTime()){
                            list.push(result);
                        }else{
                            continue;
                        }
                    }
                }else if(filter === "all" && result[i].flight.origin.name===origin && result[i].flight.destination.name===destination){

                    if(departDate!=="" && returnDate===""){
                        if (resultDepartDate.getTime() > departDateFormatted.getTime()) {
                            list.push(result);
                        }
                    }else if(departDate==="" && returnDate!==""){
                        if(resultReturnDate.getTime() < returnDateFormatted.getTime()){
                            list.push(result);
                        }
                    }else if(departDate!=="" && returnDate!==""){
                        if(resultDepartDate.getTime() > departDateFormatted.getTime() &&
                            resultReturnDate.getTime() < returnDateFormatted.getTime()){
                            list.push(result[i]);
                        }
                    }
                }
            }
            console.log(list);
            makeFilteredTable(list);
        }
    };
    xhttp.open("GET", "/rest/tickets", true);
    xhttp.send();
}

// VADJENJE PODATKA IZ SEARCH FORME ZA OBICNOG KORISNIKA
function searchForm(){

    let origin = $( "#origin-search" ).val();
    let destination = $("#destination-search").val();
    let departDate = $("#depart-date-search").val();
    let departDateFormatted = "";
    if(departDate!==""){
        departDateFormatted = stringToDate(departDate.toString(),"dd-mm-yyyy");
    }
    let returnDate = $("#return-date-search").val();
    let returnDateFormatted = "nema";
    if(returnDate!==""){
        returnDateFormatted = stringToDate(returnDate.toString(),"dd-mm-yyyy");
    }

    // if(returnDateFormatted === "nema"){
    //     returnDate="nema";
    // }

    if(origin==="" || destination==="" || (departDate==="" && returnDate==="")){
        $('#mistake1').text("Origin, destination and one of the dates must be filled!");
        return;
    }else{
        $('#mistake1').text("");
    }


    if(!(returnDate.includes("-"))){
        if(returnDate!==""){
            $('#mistake2').text("You have to use format dd-mm-yyyy for dates!");
        }
        return;
    }else{
        $('#mistake2').text("");
    }

    if(!(departDate.includes("-"))){
        if(departDate!=="")
        $('#mistake2').text("You have to use format dd-mm-yyyy for dates!");
        return;
    }else{
        $('#mistake2').text("");
    }

    if(departDate!=="" && returnDate!==""){
        if(departDateFormatted.getTime() > returnDateFormatted.getTime()){
            $('#mistake3').text("Depart date can't be after return date!");
            return;
        }else{
            $('#mistake3').text("");
        }
    }

    filterTicketsByRegularUser(origin, destination, departDate, returnDate,departDateFormatted, returnDateFormatted);
    return false;
}


// otvara se stranica za rezervaciju
function loadReservationPage(){
    window.location.replace("/reservations.jsp");
}

// otvara se stranica za izmenu karte
function changeTicket(id){
    window.localStorage.setItem("ticket_id",id);
    window.location.replace("/changeticket.jsp");
}

// BRISANJE KARTE NA OSNOVU NJENOG ID-JA
function deleteTicket(id){
    console.log(ticket);
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            filterTickets();
        }
    };

    let url = "/rest/tickets/" + id;
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify({id:id}));
    location.reload();
}


// RESERVISANJE KARTE TAKO STO SE NADJE PRVO NJEN USER
function bookATicketAndGetUser(ticket){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let result = JSON.parse(this.response);
            bookATicket(ticket,result);
        }
    };
    let userId = window.localStorage.getItem("id");
    let url = "/rest/users/" +userId;
    xhttp.open("GET", url, true);
    xhttp.send();
}

// RESERVISANJE KARTE TAKO STO SE SALJU SVI POTREBNI PODACI
function bookATicket(ticket, user){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
           decrementCount(ticket);
        }
    };

    console.log(ticket);
    let flight = ticket.flight;
    console.log(flight);
    let response = isTicketAvailable(ticket.departDate);
    let isAvailable = "";
    if(response){
        isAvailable = "Yes";
    }else{
        isAvailable = "No";
    }
    xhttp.open("POST", "/rest/reservations/new", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify({flight:flight,isAvailable:isAvailable,ticket:ticket,user:user}));
}

// NAKON REZERVISANJA KARTE DEKREMENTIRAMO COUNT OD KARTE
function decrementCount(ticket){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            filterTickets();
        }
    };

    let url = "/rest/tickets/decrement/" + ticket.id;
    xhttp.open("GET", url, true);
    xhttp.send();
}

// GLEDAMO DA LI JE KARTA DOSTUPNA KAKO BI MOGLA DA SE REZERVISE
function isTicketAvailable(departDate){

    let departDateFormatted = stringToDate(departDate.toString(),"dd-mm-yyyy");
    let today = new Date();

    // mozda nije dobro
    if(departDateFormatted.getTime() < today.getTime() )
        return false;
    else return true;
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
            window.localStorage.clear()
            // redirekcija
            window.location.replace("/login.jsp");
        },
        error: function(data){
            alert("Must be a valid user!");
        }
    });

    return false;
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
        }
    };

    xhttp.open("GET", "/rest/airlines", true);
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
        }
    };

    xhttp.open("GET", "/rest/flights", true);
    xhttp.send();

}

function createTicket(oneWay,departDate,returnDate,airline,flight,count, flight_id) {

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status >= 200 && this.status <300 ) {
            filterTickets();
            location.reload();
        }else{
            location.reload();
        }
    };

    xhttp.open("POST", "/rest/tickets", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify({airline: airline, count:count, departDate:departDate, flight:flight, flight_id:flight_id, oneWay:oneWay, returnDate:returnDate}));


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

function processForm(e) {
    if (e.preventDefault) e.preventDefault();
    let formData = new FormData(e.target);

    let flightString = document.getElementById("flights").value;
    let flightArray = flightString.split("-");
    let oneWay = document.getElementById("oneway").value;
    let airline = document.getElementById("airlines").value;
    let departDate = formData.get("depart-date");
    let departDateFormatted = stringToDate(departDate.toString(),"dd-mm-yyyy");
    let returnDate = formData.get("return-date");
    let count = formData.get("count");
    let returnDateFormatted = stringToDate(returnDate.toString(),"dd-mm-yyyy");
    let airline_object = null;
    let flight_object= null;

    if(!(returnDate.includes("-") || departDate.includes("-"))){
        alert("You have to use format dd-mm-yyyy for dates!");
    }

    if(departDate==="" || returnDate===""){
        alert("All fields must be full!")
        return;
    }

    if(departDateFormatted.getTime() > returnDateFormatted.getTime()){
        alert("Depart date can't be after return date!");
        return;
    }

    if(count === 0){
        alert("Count can't be 0!");
        return;
    }

    for (const element of airlines) {
        if(element.name === airline){
            airline_object= element;
        }
    }

    let id = null;

    for (const f of flights) {
        if(f.origin.name === flightArray[0] && f.destination.name === flightArray[1]){
            flight_object= f;
            id = f.id;
        }
    }

    createTicket(oneWay,departDate,returnDate,airline_object,flight_object,count, id);
    return false;
}


function registerForm(e){
    if (e.preventDefault) e.preventDefault();
    let formData = new FormData(e.target);
    let typeOfUser = document.getElementById("register-type").value;
    let username = formData.get("register-username");
    let password = formData.get("register-password");

    registerUser(password,typeOfUser,username);
    return false;
}

function registerUser(password,typeOfUser,username) {

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            filterTickets();
        }
    };

    xhttp.open("POST", "/rest/users/register", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify({password:password, typeOfUser:typeOfUser, username:username}));

}


window.onload = filterTickets();
window.onload = loadUser();
window.onload = loadAirlines();
window.onload = loadFlights();

document.getElementById("tickets-filter").addEventListener("click", function() {filterTickets(); }, false);
document.getElementById('reservations-btn').addEventListener("click", function() {loadReservationPage(); }, false);
document.getElementById('logout-btn').addEventListener("click", function() {logout(); }, false);
document.getElementById('search-btn').addEventListener("click", function() {searchForm(); }, false);
document.getElementById('add-ticket-form').addEventListener("submit", processForm);
document.getElementById('register-form').addEventListener("submit", registerForm);
document.getElementById('search-btn').addEventListener("click", searchForm);



