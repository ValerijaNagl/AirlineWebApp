<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bookings</title>
    <link rel="stylesheet" href="style/main.css">
</head>
<body>


<div class="top-container">
    <h6 id="user"></h6>
    <h6 id="type" style="display: inline-block; width: 120px"></h6>
    <input id="reservations-button" type="submit" value="Reservations">
    <input id="logout-button" type="submit" value="Logout">
</div>

<br><br>

<br><br>
<div>
    <h5>Bookings</h5>
    <table id="bookings-tbl">
        <thead>
        <tr>
            <th>Origin</th>
            <th>Destination</th>
            <th>One way</th>
            <th>Depart date</th>
            <th>Return date</th>
            <th>Airline</th>
<%--            <th>Count</th>--%>
            <th>Expired</th>
            <th>Button</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>

<br>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script src="js/reservations.js"></script>
<link rel="stylesheet" href="style/main.css">
</body>
</html>