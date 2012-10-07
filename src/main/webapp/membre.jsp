<%-- 
    Document   : membre
    Created on : 7 oct. 2012, 21:36:41
    Author     : jitou
--%>

<%@page import="projet.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Projet projet = (Projet) request.getAttribute("projet");
    Membre membre = (Membre) request.getAttribute("membre");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Presentation d'un membre.</title>
    </head>
    <body>
        <h1><% out.print(membre.getNom() + "   " + membre.getPrenom());%></h1>
    </body>
</html>
