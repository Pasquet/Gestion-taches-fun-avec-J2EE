package projet_j2ee;

/**
 *
 * @author jitou
 */
import java.io.IOException;
import java.util.Map;

import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import projet.Projet;

public class Servlet extends HttpServlet {

    private Projet projet = null;

    public Servlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> liste = request.getParameterMap();
        String messageAlerte = "Aucune alerte";



        if (projet == null) {
            projet = (Projet) request.getAttribute("projet");

        } else {
            try {
                doAction(liste);
            } catch (Exception ex) {
                messageAlerte = ex.getMessage();
            }
        }



        request.setAttribute("projet", projet);
        request.setAttribute("messageAlerte", messageAlerte);
        this.getServletContext().getRequestDispatcher("/visionProjet.jsp").forward(request, response);

    }

    private void doAction(Map<String, String[]> liste) throws Exception {
        Set<String> keys = liste.keySet();
        String messageAlerte = "";
        if (keys.size() == 1) {
            String str = (String) keys.toArray()[0];
            if (str.charAt(0) == 'r')/* Supprime personne d'une tache */ {
                String[] splitStr = str.split("_");
                if (splitStr.length == 3) {
                    int tache = Integer.parseInt(splitStr[1]);
                    int personne = Integer.parseInt(splitStr[2]);
                    projet.retirerPersonneDeTache(tache, personne);
                } else {
                    throw new Exception("Erreur lors de la suppression d'une tache split incorrecte: (" + splitStr.length + ")");
                }

            } else if (str.charAt(0) == 'e')/* Supprime la tache */ {
                int tache = Integer.parseInt(str.split("_")[1]);
                messageAlerte = projet.supprimerTache(tache);
            } else if (str.charAt(0) == 's')/* Save locale */ {
                messageAlerte = projet.sauvegarder();
                if (messageAlerte.length() >= 1) {
                    throw new Exception(messageAlerte);
                }

            }
        } else {

            if (liste.keySet().contains("ajout") && liste.keySet().contains("location")) {
                messageAlerte = projet.ajouterPersonne(Integer.parseInt(liste.get("ajout")[0].split(" ")[ liste.get("ajout")[0].split(" ").length - 1]), Integer.parseInt((String) liste.get("location")[0].split("_")[1]));
            } else if (liste.keySet().contains("depart") && liste.keySet().contains("fin")) {
                String nom = liste.get("nomNew")[0];
                String description = liste.get("description")[0];
                String date1 = liste.get("depart")[0];
                String date2 = liste.get("fin")[0];
                messageAlerte = projet.creationTache(nom, description, date1, date2);
            }
        }
    }
}