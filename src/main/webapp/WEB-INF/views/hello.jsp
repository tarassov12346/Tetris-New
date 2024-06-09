<%@ page import="com.app.game.tetris.serviceImpl.State" %>
<%@ page import="com.app.game.tetris.serviceImpl.Stage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tetris</title>

    <link href="resources/styleHello.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <script src="<c:url value="/resources/jquery-3.6.0.min.js"/>"></script>
</head>
<body>

<h1 text align="center"> <b id="helloBox"> Hello ${player} </b> </h1>

<img class="displayed" src="../img/black.png" alt="" width="240" height="384">

<div id="controls">
   <button id="startGameButton" onclick="start()" >Start</button>
</div>

<script>




function start() {
 window.location='/start';
}


</script>

</body>
</html>