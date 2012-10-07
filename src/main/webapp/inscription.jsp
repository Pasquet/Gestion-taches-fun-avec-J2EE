<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion à un projet</title>
    </head>
    <body>

        <%
            String msg = (String) request.getAttribute("messageAlerte");
            if (msg != null) {
                out.println(msg);
            }
        %>


        <form method="post" action="serveur">
            <fieldset>
                <legend>Connexion à un projet</legend>

                <label for="nom">Nom du projet</label>
                <input type="text" id="nom" name="nom" value="Nom projet" maxlength="40" />
                <br />

                <input type="submit" value="serveur"/>
                <br />

            </fieldset>
        </form>
    </body>
</html>