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


    public  ConnexionProjet(){
        super();
    }
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
              this.getServletContext().getRequestDispatcher( "/inscription.jsp" ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       

    }

    
    


}