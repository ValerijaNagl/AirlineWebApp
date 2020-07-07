'use strict';


function loadBookingsNumber(){
    let id = window.localStorage.getItem("id");

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let result = JSON.parse(this.response);
            $("#reservations-btn").attr('value', 'Reservations: '+result.toString());
        }
    };

    let url = "/rest/users/bookingnum/" + id;
    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();
}

function loadUser(){
    let airline_name = window.localStorage.getItem("airline_name");
    $('#airline_name').text('Airline: ' + airline_name);

    let username = window.localStorage.getItem("username").toString();
    let type = window.localStorage.getItem("type").toString();

    $('#user').text(username);
    $('#type').text(type);

    if(type === "ADMIN"){
        $('#reservations-btn').css("display", "none");
    }else if(type === "REGULAR"){
        $('#delete-airline-btn').css("display", "none");
        $('#change-airline-form').css("display", "none");
        $("#add-airline-form").css("display", "none");
        loadBookingsNumber();
    }
}



function loadTickets() {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {

            let result = JSON.parse(this.response);
            let table = document.getElementById("airlines-tbl");
            let oldTBody = table.tBodies[0];
            let newTBody = document.createElement("tBody");

            for (let i = 0; i < result.length; i++) {

                let bRow = document.createElement("tr");

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

                // let tdCount = document.createElement("td");
                // tdCount.innerHTML = result[i].ticket.count;

                bRow.appendChild(tdFrom);
                bRow.appendChild(tdTo);
                bRow.appendChild(tdOneWay);
                bRow.appendChild(tdDepartDate);
                bRow.appendChild(tdReturnDate);
                bRow.appendChild(tdAirline);

                let type = window.localStorage.getItem("type");
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

                    $('#delete-th').css("display","none");
                    $('#change-th').css("display","none");
                }
                newTBody.appendChild(bRow);
            }

            table.replaceChild(newTBody, oldTBody);
        }

    };


    let airline_id = window.localStorage.getItem("airline_id");
    let url = "/rest/tickets/airline/" + airline_id;
    xhttp.open("GET", url, true);
    xhttp.send();
}

// otvara se stranica za rezervaciju
function loadReservationPage(){
    window.location.replace("/reservations.jsp");
}


function changeTicket(id){
    window.localStorage.setItem("ticket_id",id);
    window.location.replace("/changeticket.jsp");
}


function deleteTicket(id){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            loadUser();
            loadTickets();
        }
    };

    let url = "/rest/tickets/delete" + id;
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send(JSON.stringify({id:id}));
    location.reload();
}

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
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();
}

function bookATicket(ticket, user){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            decrementCount(ticket);
        }
    };

    let flight = ticket.flight;
    let response = isTicketAvailable(ticket.departDate);
    let isAvailable = "";
    if(response){
        isAvailable = "Yes";
    }else{
        isAvailable = "No";
    }
    xhttp.open("POST", "/rest/reservations/new", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send(JSON.stringify({flight:flight,isAvailable:isAvailable,ticket:ticket,user:user}));
}

function decrementCount(ticket){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            loadTickets();
            loadBookingsNumber();
        }
    };

    let url = "/rest/tickets/decrement/" + ticket.id;
    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();
}

function isTicketAvailable(departDate){

    let departDateFormatted = stringToDate(departDate.toString(),"dd-mm-yyyy");
    let today = new Date();

    if(departDateFormatted.getTime() < today.getTime())
        return false;
    else return true;
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

function addAirline(name){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status >= 200 && this.status <300 ) {
            loadTickets();
            $('#add-airline-message').text("New airline successfully added!");
        }else{
            $('#add-airline-message').text("Mistake happeneds while adding new airline!");
        }
    };

    xhttp.open("POST", "/rest/airlines", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send(JSON.stringify({name: name}));
}


function addAirlineForm(){
    let name = $("#add-name").val();
    addAirline(name);
    return false;
}


function changeAirline(){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status >= 200 && this.status <300 ) {
            let result = JSON.parse(this.response);
            window.localStorage.setItem("airline_id",result.id);
            window.localStorage.setItem("airline_name",result.name);
            // pozivamo da bi se refresovlo ime kompanije
            loadUser();
            loadTickets();
            $('#change-airline-message').text("Airline is successfully changed!");
        }else{
            $('#change-airline-message').text("Mistake happened while changing airline!");
        }
    };

    let id = window.localStorage.getItem("airline_id");
    let name = $("#change-name").val();

    xhttp.open("POST", "/rest/airlines/change", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send(JSON.stringify({id:id, name: name}));

}


function deleteAirline(){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status >= 200 && this.status <300 ) {
            window.localStorage.removeItem("airline_id");
            window.localStorage.removeItem("airline_name");
            window.location.replace("/index.jsp");
        }else{
            $('#delete-mistake').text("Error while deleting airline");
        }
    };

    let id = window.localStorage.getItem("airline_id");
    let url = "/rest/airlines/delete/" + id;
    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();

}

window.onload = loadTickets();
window.onload = loadUser();

document.getElementById('reservations-btn').addEventListener("click", function() {loadReservationPage(); }, false);
document.getElementById('logout-btn').addEventListener("click", function() {logout(); }, false);
document.getElementById('add-airline-btn').addEventListener("click", addAirlineForm);
document.getElementById('change-airline-btn').addEventListener("click", changeAirline);
document.getElementById('delete-airline-btn').addEventListener("click", deleteAirline);

