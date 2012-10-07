/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

public class Membre {
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
