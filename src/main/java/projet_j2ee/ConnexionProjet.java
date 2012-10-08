/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_j2ee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import projet.*;

public class ConnexionProjet extends HttpServlet {

    public ConnexionProjet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/inscription.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> liste = request.getParameterMap();
        String messageAlerte = "Aucune alerte";

        if (liste.containsKey("nom")) {
            try {
                String nom = request.getParameter("nom");
                Projet p = this.premiereConnexion(nom, request.getParameter("ip"), request.getParameter("nomUser"), request.getParameter("mdp"));
                request.setAttribute("projet", p);
                request.getSession().setAttribute("projet", p);
                request.getRequestDispatcher("/serveur").forward(request, response) ;
                
            } catch (Exception ex) {
                messageAlerte = ex.getMessage();
                request.setAttribute("messageAlerte", messageAlerte);
                this.getServletContext().getRequestDispatcher("/inscription.jsp").forward(request, response);
                return;
            }

        }

    }

    private Projet premiereConnexion(String nom, String ip, String user, String mdp) throws Exception {
        Projet projet = new ProjetSQL(nom, ip, user, mdp);
        String messageAlerte = projet.connexion();

        if (((ProjetSQL)projet).getId_projet() == -1) {
            throw new Exception("Erreur la connexion au projet semble impossible : " + messageAlerte);
        }

        return projet;
    }
}