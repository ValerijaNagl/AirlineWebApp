<%--
  Created by IntelliJ IDEA.
  User: Windows 7
  Date: 30.5.2020
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change ticket</title>
</head>
<body>

<div class="top-container">
    <h6 id="user"></h6>
    <h6 id="type" style="display: inline-block; width: 120px"></h6>
    <input id="reservations-btn" type="submit" value="Reservations">
    <input id="logout-btn" type="submit" value="Logout">
</div>
<hr>

<div id="change-ticket-form">
    <h3>Change ticket</h3>
    <p id="mistake"></p>
    <p id="mistake2"></p>
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

        <input type="text" name="depart-date" id="depart-date" placeholder="Depard date in form dd-mm-yyyy" style="width:200px"><br>
        <input type="text" name="return-date" id="return-date" placeholder="Return date in form dd-mm-yyyy" style="width:200px"><br>
        <input type="text" name="count" id="count" placeholder="Count" style="width:120px"><br>

        <br>

        <button id="change-ticket-btn" type="button">Change ticket</button>
        <input id="cancel-btn" type="submit" value="Cancel">
    </form>
    <p id="ticket-change-message"></p>
    <p id="airlines-message"></p>
    <p id="flights-message"></p>
</div>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="js/change-ticket.js"></script>
<link rel="stylesheet" href="style/main.css">
</body>
</html>
