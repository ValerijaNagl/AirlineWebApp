<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
    <link rel="stylesheet" href="style/main.css">
</head>
<body>


<div class="top-container">
    <h6 id="user"></h6>
    <h6 id="type" style="display: inline-block; width: 120px"></h6>
    <input id="reservations-btn" type="submit" value="Reservations">
    <input id="logout-btn" type="submit" value="Logout">
</div>

<div id="search-form">
    <label>Search ticket:</label>
    <form style="display:inline">
        <input type="text" id="origin-search" name="origin-search" placeholder="Origin" style="width:120px">
        <input type="text" id="destination-search" name="destination-search" placeholder="Destination" style="width:120px;">
        <input type="text" id="depart-date-search" name="depart-date-search" placeholder="" style="width:120px;">
        <input type="text" id="return-date-search"  name="return-date-search" placeholder="" style="width:120px;">
        <button id="search-btn" type="button" style="height:3px;">Search</button>
    </form>
</div>

<p id="mistake1"></p>
<p id="mistake2"></p>
<p id="mistake3"></p>


<br><br>
<label for="tickets-filter">Filter by:</label>
<select id="tickets-filter">
    <option value="all">All</option>
    <option value="one-way">One way</option>
    <option value="two-ways">Two ways</option>
</select>
<br><br>

<div>
    <h5>Tickets</h5>
    <table id="tickets-tbl">
        <thead>
        <tr>
            <th id="id_row">Id</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>One way</th>
            <th>Depart date</th>
            <th>Return date</th>
            <th>Airline</th>
            <th>Count</th>
            <th>Delete ticket</th>
            <th>Change ticket</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<p id="ticket-table-message"></p>

<div id="add-ticket-form">
<hr>
<h3>Add ticket</h3>
    <form>
        <label for="flights">Flights</label>
        <select id="flights"></select>

        <label for="airlines">Airlines</label>
        <select id="airlines">
        </select>

        <label for="oneway">One way ticket</label>
        <select id="oneway">
            <option value="Yes">Yes</option>
            <option value="No">No</option>
        </select>
        <br>

        <input type="text" name="depart-date" placeholder="Depard date in form dd-mm-yyyy" style="width:200px"><br>
        <input type="text" name="return-date" placeholder="Return date in form dd-mm-yyyy" style="width:200px"><br>
        <input type="text" name="count" placeholder="Count" style="width:120px"><br>

        <br>
        <input id="add-ticket-btn"type="submit" value="Create ticket">
    </form>
    <p id="ticket-add-message"></p>
    <p id="airlines-message"></p>
    <p id="flights-message"></p>
</div>

<div id="register-form">
    <hr>
    <h3>Register user</h3>
    <form>

        <input type="text" id="register-username" name="register-username" placeholder="Username" style="width:250px"><br>
        <input type="text" id="register-password" name="register-password" placeholder="Password" style="width:250px"><br>

        <label for="register-type">Type of user</label>
        <select id="register-type">
            <option value="ADMIN">ADMIN</option>
            <option value="REGULAR">REGULAR</option>
        </select>
        <br>
        <input id="register-btn" type="submit" value="Register">
    </form>
    <p id="register-message"></p>
</div>


<br>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="js/index.js"></script>
<link rel="stylesheet" href="style/main.css">
</body>
</html>
