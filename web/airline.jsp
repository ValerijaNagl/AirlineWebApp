<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Airline page</title>
    <link rel="stylesheet" href="style/main.css">
</head>
<body>


<div class="top-container">
    <h6 id="user"></h6>
    <h6 id="type" style="display: inline-block; width: 120px"></h6>
    <input id="reservations-btn" type="submit" value="Reservations">
    <input id="logout-btn" type="submit" value="Logout">
</div>

<br><br>
<h2 id="airline_name"></h2>
<button id="delete-airline-btn" type="button" style="height:3px;">Delete airline</button>
<p id="delete-mistake"></p>

<div>
    <h5>Tickets</h5>
    <table id="airlines-tbl">
        <thead>
        <tr>
            <th>Origin</th>
            <th>Destination</th>
            <th>One way</th>
            <th>Depart date</th>
            <th>Return date</th>
            <th>Airline</th>
            <th id="delete-th">Delete ticket</th>
            <th id="change-th">Change ticket</th>
            <th id="book-th">Book</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>

<hr>
<div id="change-airline-form">
    <h3>Change airline</h3>
    <p id="mistake1"></p>
    <form>
        <input type="text" name="change-name" id="change-name" placeholder="Name" style="width:150px"><br>
        <br>
        <input id="change-airline-btn" type="button" value="Change airline">
    </form>
    <p id="change-airline-message"></p>
</div>
<hr>
<div id="add-airline-form">
    <h3>Add airline</h3>
    <p id="mistake2"></p>
    <form>
        <input type="text" name="add-name" id="add-name" placeholder="Name" style="width:150px"><br>
        <br>
        <button id="add-airline-btn" type="button" style="height:3px;">Add</button>
        <p id="add-airline-message"></p>
    </form>
</div>




<br>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="js/airline.js"></script>
<link rel="stylesheet" href="style/main.css">
</body>
</html>
