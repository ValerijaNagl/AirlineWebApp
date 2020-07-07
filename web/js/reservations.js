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
    let username = window.localStorage.getItem("username").toString();
    let type = window.localStorage.getItem("type").toString();
    $('#user').text(username);
    $('#type').text(type);

    if(type === "ADMIN"){
        $('#search-form').css("visibility", "hidden");
    }else if(type === "REGULAR"){
        $('#add-ticket-form').css("visibility", "hidden");
        $('#register-form').css("visibility", "hidden");
        loadBookingsNumber();
    }
}

function loadBookings() {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {

            let result = JSON.parse(this.response);
            let table = document.getElementById("bookings-tbl");
            let oldTBody = table.tBodies[0];
            let newTBody = document.createElement("tBody");

            for (let i = 0; i < result.length; i++) {

                let bRow = document.createElement("tr");

                    let tdFrom = document.createElement("td");
                    tdFrom.innerHTML = result[i].flight.origin.name;

                    let tdTo = document.createElement("td");
                    tdTo.innerHTML = result[i].flight.destination.name;

                    let tdOneWay = document.createElement("td");
                    tdOneWay.innerHTML = result[i].ticket.oneWay;

                    let tdDepartDate = document.createElement("td");
                    tdDepartDate.innerHTML = result[i].ticket.departDate;

                    let tdReturnDate = document.createElement("td");
                    tdReturnDate.innerHTML = result[i].ticket.returnDate;

                    let tdAirline = document.createElement("td");
                    tdAirline.innerHTML = result[i].ticket.airline.name;

                    // let tdCount = document.createElement("td");
                    // tdCount.innerHTML = result[i].ticket.count;

                    let tdExpired = document.createElement("td");
                    tdExpired.innerHTML = result[i].isAvailable;

                    let btn = document.createElement('input');
                    let tdButton = document.createElement("td");

                    console.log(canYouDeleteReservation(result[i]));
                    if(canYouDeleteReservation(result[i])){

                        btn.type = "button";
                        btn.className = "btn";
                        btn.value = "Delete reservation";
                        btn.onclick = function(){deleteReservation(result[i])};
                        tdButton.appendChild(btn);
                    }
                    bRow.appendChild(tdFrom);
                    bRow.appendChild(tdTo);
                    bRow.appendChild(tdOneWay);
                    bRow.appendChild(tdDepartDate);
                    bRow.appendChild(tdReturnDate);
                    bRow.appendChild(tdAirline);
                    // bRow.appendChild(tdCount);
                    bRow.appendChild(tdExpired);
                    bRow.appendChild(tdButton);
                    newTBody.appendChild(bRow);
                }

              table.replaceChild(newTBody, oldTBody);
            }
        };


    let user_id = window.localStorage.getItem("id");
    let url = "/rest/reservations/user/" + user_id;
    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();
}

function deleteReservation(reservation) {

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            // refresujemo broj rezervacija i table rezervacija
            loadBookingsNumber();
            loadBookings();
        }
    };
    let id = reservation.id;
    let url = "/rest/reservations/delete/" + id;
    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();

}


function canYouDeleteReservation(reservation){
    let departDate = reservation.ticket.departDate;
    let departDateFormatted = stringToDate(departDate.toString(),"dd-mm-yyyy");
    let today = new Date();
    let date2 = departDateFormatted.getTime() / 1000;
    let date1 = today.getTime() / 1000;
    let difference = (date2 - date1)/60/60;
    console.log(difference);
    if(difference < 24)
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
            window.localStorage.clear()
            window.location.replace("/login.jsp");
        },
        error: function(data){
            alert("Must be a valid user!");
        }
    });
    return false;
}

window.onload = loadBookings();
window.onload = loadUser();
document.getElementById('logout-button').addEventListener("click", function() {logout(); }, false);



