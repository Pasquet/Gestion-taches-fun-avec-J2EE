/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.Serializable;

public class Membre implements Serializable {
    
    private String nom, prenom;
    private int id;
    
    public Membre(String nom, String prenom, int id){
        this.nom= nom;
        this.prenom = prenom;
        this.id = id;
    }
    
    
    
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getId() {
        return id;
    }
    public String toString(){
        return nom+" "+prenom;
    }
}
