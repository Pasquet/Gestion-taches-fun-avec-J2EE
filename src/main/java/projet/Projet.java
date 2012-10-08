/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Projet implements Serializable {

    protected String name;
    protected ArrayList<Tache> taches = new ArrayList<Tache>();
    protected ArrayList<Membre> toutLesMembres = new ArrayList<Membre>();

    public Projet(String name) {
        this.name = name;
    }

    public String connexion() throws FileNotFoundException, IOException, ClassNotFoundException {
                String CHEMIN = "/home/jitou/Bureau/git/Gestion-taches-fun-avec-J2EE/";

        FileInputStream fichier = new FileInputStream(CHEMIN+name + ".dat");
        ObjectInputStream o = new ObjectInputStream(fichier);
        Projet p = (Projet) o.readObject();

        for (int i = 0; i < p.getToutLesMembres().size(); i++) {
            toutLesMembres.add(p.getToutLesMembres().get(i));
        }
        for (int i = 0; i < p.getNbTaches(); i++) {
            taches.add(p.getTache(i));
        }

        o.close();
        fichier.close();
        return "Chargement finie";
    }

    public String sauvegarder(){
        String CHEMIN = "/home/jitou/Bureau/git/Gestion-taches-fun-avec-J2EE/";
        try {
            FileOutputStream fichier = new FileOutputStream(CHEMIN+name + ".dat");
            ObjectOutputStream o = new ObjectOutputStream(fichier);
            o.writeObject(this);
            o.flush();
            o.close();
            
            fichier.close();
        } catch (IOException ex) {
            return ex.getMessage();
            }
        
        
        return "";
    }

    public void addTache(Tache t) {
        taches.add(t);
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
        String out = name + " \n\n";
        for (int i = 0; i < taches.size(); i++) {
            out += taches.get(i) + "\n";
        }
        return out;
    }

    public String getName() {
        return name;
    }

    public int getNbTaches() {
        return taches.size();
    }

    public Tache getTache(int i) {
        return taches.get(i);
    }

    public String retirerPersonneDeTache(int t, int p) throws SQLException, Exception {
        Tache ta = getTache(t);
        int id_p = ta.getMembre(p).getId();
        ta.retirerMembre(p);
        sauvegarder();

        return " ";
    }

    public String supprimerTache(int t) throws SQLException, Exception {
        Tache ta = getTache(t);
        if (ta == null) {
            throw new Exception("Aucune tache associé à " + t);
        }

        this.taches.remove(t);
        sauvegarder();

        return " ";
    }

    public ArrayList<Membre> getToutLesMembres() {
        return toutLesMembres;
    }

    public String ajouterPersonne(int pers, int t) throws SQLException, Exception {
        Tache ta = getTache(t);
        Membre me = trouveMembre(pers);
        // return ta.getNom()+"  "+pers;
        if (ta == null) {
            throw new Exception("Aucune tache associé à " + t);
        } else if (me == null) {
            throw new Exception("Aucun membre associé à " + pers);
        } else if (ta.estPresent(me)) {
            throw new Exception(me.getNom() + " travaille deja sur la tache " + ta.getNom());
        }


        ta.addMembre(me);
        sauvegarder();

        return " ";
    }

    protected boolean tachePresente(String nom) {
        for (int i = 0; i < this.getNbTaches(); i++) {
            if (this.getTache(i).getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    protected Tache trouverTache(String nom) {
        for (int i = 0; i < this.getNbTaches(); i++) {
            if (this.getTache(i).getNom().equals(nom)) {
                return this.getTache(i);
            }
        }

        return null;
    }

    public String creationTache(String nom, String description, String date1, String date2) throws SQLException, Exception {
        if (tachePresente(nom)) {
            throw new Exception("Une tache est deja associé au nom de " + nom);
        }


        this.addTache(new Tache(nom, description, date1, date2));
        sauvegarder();
        return " ";
    }

    public String etatProjet() {
        int fini = 0;
        for (int i = 0; i < this.getNbTaches(); i++) {
            if (this.getTache(i).percent() >= 100) {
                fini++;
            }
        }
        if (this.getNbTaches() <= 0) {
            return "";
        }
        String s = String.valueOf(100. * fini / this.getNbTaches());
        return fini + "/" + this.getNbTaches() + "   soit " + s.substring(0, Math.min(4, s.length())) + " %";
    }
}
