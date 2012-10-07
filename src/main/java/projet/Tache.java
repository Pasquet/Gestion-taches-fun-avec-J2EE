/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tache {

    private String nom, description;
    private ArrayList<Membre> membres = new ArrayList<Membre>();
    private String depart = "depart", duree = "duree";

    public Tache(String nom) throws Exception {
        this(nom, "","" ,"");
    }

    public Tache(String nom, String description,String debut,String duree) throws Exception {
        this.nom = nom;
        this.description = description;
        this.depart = debut;
        this.duree = duree;
        
        if(!verificationDate())
                throw new Exception( "Les dates "+ depart +" et/ou "+ duree+" sont incorrectes." );

    }

    void addMembre(Membre m) {
        membres.add(m);
    }

    public String toString() {
        String out = nom + "  : " + description + " \n (";
        for (int i = 0; i < membres.size(); i++) {
            out += membres.get(i) + "  ; ";
        }
        out += " )";
        return out;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public int getNbMembres() {
        return membres.size();
    }

    public Membre getMembre(int i) {
        return membres.get(i);
    }

    void retirerMembre(int i) {
        membres.remove(i);
    }

    public String getDuree() {
        return duree;
    }

    public String getDepart() {
        return depart;
    }
    
    public long percent(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
        Date d1 = null;
        Date d2 = null;
        Date d3 = new Date();
        try {
            d1 = format.parse(depart);
            d2 = format.parse(duree);
        } catch (ParseException e) {
        return -1;
        }    
        double diff = ((double)(System.currentTimeMillis() - d1.getTime())/(double)(d2.getTime() - d1.getTime()));
        if(diff>1.)
            diff = 1;
        else if(diff<=0.)
            diff = 0.;
        
        
        return (long) (diff*100);
        //return (int) ((t2.getTimeInMillis()-t1.getTimeInMillis())/(System.currentTimeMillis()-t1.getTimeInMillis()));
        
    }

    private boolean verificationDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(depart);
            d2 = format.parse(duree);
        } catch (ParseException e) {
        return false;
        }    
        if(d2.getTime()-d1.getTime()<=0){
            return false;
        }
        else
            return true;
    }

    public boolean estPresent(Membre me) {
        return membres.contains(me);
    }
}
