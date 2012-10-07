/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Projet {

    private String name;
    private int id_projet = -1;
    private ArrayList<Tache> taches = new ArrayList<Tache>();
    private ArrayList<Membre> membres = new ArrayList<Membre>();
    private ArrayList<Membre> toutLesMembres = new ArrayList<Membre>();
    private String ip, nomUser, mdp;
    
    public Projet(String name, String ip, String nomUser, String mdp) {
        this.name = name;
        this.ip = ip;
        this.nomUser = nomUser;
        this.mdp = mdp;
    }
    
    public Projet(String name) {
        this(name, "127.0.0.1", "root", "4444");  
    }

    private void fermerConnexion(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ignore) {
            }
        }
    }

    public void chargementToutUser() {
        toutLesMembres.clear();
        String url = "jdbc:mysql://"+ip+":3306/gestion_projet";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;


        try {
            connexion = DriverManager.getConnection(url, nomUser, mdp);
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM membre;");
            while (resultat.next()) {
                int id = resultat.getInt("id");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                toutLesMembres.add(new Membre(nom, prenom, id));
            }
        } catch (SQLException e) {
        } finally {
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            this.fermerConnexion(statement);
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
        }

    }

    public String connexion() {
        String url = "jdbc:mysql://"+ip+":3306/gestion_projet";

        Connection connexion = null;
        Statement statement_1 = null;
        Statement statement_2 = null;
        Statement statement_3 = null;

        ResultSet resultat = null;
        String messageAlerte = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            messageAlerte = "Message Chargement class : " + e.getMessage();
            return messageAlerte;
        }

        chargementToutUser();

        try {
            connexion = DriverManager.getConnection(url, nomUser, mdp);
            statement_1 = connexion.createStatement();

            resultat = statement_1.executeQuery("SELECT id FROM projet WHERE nom=\'" + name + "\';");
            int id_prt = -1;
            while (resultat.next() && id_prt == -1) {
                id_prt = resultat.getInt("id");
            }

            if (id_prt != -1) {
                messageAlerte = "Connexion au projet ==> OK";
                this.id_projet = id_prt;
                statement_2 = connexion.createStatement();

                resultat = statement_2.executeQuery("SELECT * FROM tache WHERE id_projet=" + id_projet + ";");
                while (resultat.next()) {
                    int id_membre = resultat.getInt("id_membre");
                    String nom = resultat.getString("nom");
                    String description = resultat.getString("description");
                    String duree = resultat.getString("duree");
                    String debut = resultat.getString("debut");
                    Tache tache = null;
                    if (tachePresente(nom)) {
                        tache = trouverTache(nom);
                    } else {
                        try {
                            tache =  new Tache(nom, description, debut, duree);
                            this.addTache(tache);

                        } catch (Exception ex) {id_membre=-1;}

                    }
                    if (id_membre != -1) {
                        if (estUnMembrePresent(id_membre)) {
                            tache.addMembre(trouveMembre(id_membre));
                        } else {
                            statement_3 = connexion.createStatement();
                            ResultSet premiereRechercheMembre = statement_3.executeQuery("SELECT * FROM membre WHERE id=" + id_membre + ";");
                            String nomM = "", prenomM = "";
                            while (premiereRechercheMembre.next()) {
                                nomM = premiereRechercheMembre.getString("nom");
                                prenomM = premiereRechercheMembre.getString("prenom");
                            }
                            Membre membre = new Membre(nomM, prenomM, id_membre);
                            this.addMembre(membre);
                            tache.addMembre(membre);
                            this.fermerConnexion(statement_3);
                        }
                    }
                }
                this.fermerConnexion(statement_2);

            } else {
                messageAlerte = "Pas d'id associé.";
            }


        } catch (SQLException e) {
            messageAlerte = e.getMessage();
        } finally {
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            this.fermerConnexion(statement_1);
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
        }
        return messageAlerte;
    }

    public void addTache(Tache t) {
        taches.add(t);
    }

    public void addMembre(Membre t) {
        membres.add(t);
    }

    public boolean estUnMembrePresent(int id) {
        for (int i = 0; i < toutLesMembres.size(); i++) {
            if (toutLesMembres.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Membre trouveMembre(int id) {
        for (int i = 0; i < toutLesMembres.size(); i++) {
            if (toutLesMembres.get(i).getId() == id) {
                return toutLesMembres.get(i);
            }
        }
        return null;
    }

    public String toString() {
        String out = name + "  : " + id_projet + " \n\n";
        for (int i = 0; i < taches.size(); i++) {
            out += taches.get(i) + "\n";
        }
        return out;
    }

    public String getName() {
        return name;
    }

    public int getId_projet() {
        return id_projet;
    }

    public int getNbTaches() {
        return taches.size();
    }

    public Tache getTache(int i) {
        return taches.get(i);
    }

    private String executerSql(String str) throws SQLException {
        String url = "jdbc:mysql://"+ip+":3306/gestion_projet";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        String msg = "";

        connexion = DriverManager.getConnection(url, this.nomUser, mdp);
        statement = connexion.createStatement();
        statement.executeUpdate(str);
        msg = str + "  ==> OK!";


        if (resultat != null) {
            try {
                resultat.close();
            } catch (SQLException ignore) {
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ignore) {
            }
        }
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException ignore) {
            }
        }

        return msg;
    }

    public String retirerPersonneDeTache(int t, int p) throws SQLException, Exception {
        Tache ta = getTache(t);
        int id_p = ta.getMembre(p).getId();
        if(ta == null)
           throw new Exception( "Aucune tache associé à "+ t );
        String msg = executerSql("DELETE FROM tache WHERE id_membre=" + id_p + " and id_projet=" + this.id_projet + " and  nom=\'" + ta.getNom() + "\' ;");
        ta.retirerMembre(p);
        return msg;
    }

    public String supprimerTache(int t) throws SQLException, Exception {
        Tache ta = getTache(t);
       if(ta == null)
           throw new Exception( "Aucune tache associé à "+ t );
        String msg = executerSql("DELETE FROM tache WHERE  id_projet=" + this.id_projet + " and  nom=\'" + ta.getNom() + "\' ;");
        this.taches.remove(t);
        return msg;
    }

    public ArrayList<Membre> getToutLesMembres() {
        return toutLesMembres;
    }

    public String ajouterPersonne(int pers, int t) throws SQLException, Exception {
        Tache ta = getTache(t);
        Membre me = trouveMembre(pers);
        // return ta.getNom()+"  "+pers;
        if(ta == null)
           throw new Exception( "Aucune tache associé à "+ t );
        if(me == null)
           throw new Exception( "Aucun membre associé à "+ pers );
        
         if(ta.estPresent(me))
             throw new Exception(  me.getNom() +" travaille deja sur la tache "+ta.getNom());
        
        
        ta.addMembre(me);
        return executerSql("INSERT INTO tache (nom, description, duree, debut,  id_membre,  id_projet ) VALUES ('" + ta.getNom() + "', ' " + ta.getDescription() + "', '" + ta.getDuree() + "', '" + ta.getDepart() + "'," + me.getId() + "," + this.id_projet + ");");
    }

    private boolean tachePresente(String nom) {
        for (int i = 0; i < this.getNbTaches(); i++) {
            if (this.getTache(i).getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    private Tache trouverTache(String nom) {
        for (int i = 0; i < this.getNbTaches(); i++) {
            if (this.getTache(i).getNom().equals(nom)) {
                return this.getTache(i);
            }
        }
        return null;
    }

    public String creationTache(String nom, String description, String date1, String date2) throws SQLException, Exception {
       if(tachePresente(nom))
             throw new Exception( "Une tache est deja associé au nom de "+ nom );

        
        this.addTache(new Tache(nom, description, date1, date2));
        return executerSql("INSERT INTO tache (nom, description, duree, debut,  id_membre,  id_projet ) VALUES ('" + nom + "', ' " + description + "', '" + date2 + "', '" + date1 + "'," + "-1" + "," + this.id_projet + ");");
    }
    
    
    public String etatProjet(){
        int fini = 0;
        for(int i=0;i<this.getNbTaches();i++)
            if(this.getTache(i).percent()>=100)
                fini++;
        
        if(this.getNbTaches()<=0)
            return "";
        String s = String.valueOf(100.*fini/this.getNbTaches());
        
        return fini+"/"+this.getNbTaches()+"   soit "+s.substring(0, Math.min(4, s.length())) +" %";
        
    }
}
//      DELETE FROM gestion_projet.tache WHERE id_membre=0 and id_projet=0 nom='tache1' ;
