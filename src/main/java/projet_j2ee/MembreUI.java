/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_j2ee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import projet.Membre;
import projet.Projet;

/**
 *
 * @author jitou
 */
public class MembreUI extends HttpServlet {

    private Membre membre = null;

    public MembreUI() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Projet p = (Projet) request.getSession().getAttribute("projet");
        membre = p.trouveMembre(id);
        request.setAttribute("membre", membre);
        request.setAttribute("projet", p);
        this.getServletContext().getRequestDispatcher( "/membre.jsp" ).forward( request, response );

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
