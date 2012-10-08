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


        <form method="post" action="ConnexionProjet">
            <fieldset>
                <legend>Connexion à un projet</legend>

                <label for="ip">Ip</label>
                <input type="text" id="ip" name="ip" value="127.0.0.1" /> </br>
                  
                <label for="nomUser">Utilisateur de la BDD</label>
                <input type="text" id="nomUser" name="nomUser" value="root" /></br>

                 <label for="mdp">Mot de pass</label>
                <input type="password" id="mdp" name="mdp" value="4444" /></br>

                 <label for="nom">Nom du projet</label>
                <input type="text" id="nom" name="nom" value="Nom projet" maxlength="40" />   <br />
             

                <input type="submit" value="Connexion"/>
                <br />

            </fieldset>
        </form>
    </body>
</html>