/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ASSUS GAMER
 */
public class modelEnseignant {

    String id, nom, date, telephone, sexe, type,moratoire;
     boolean isfinish;
    public void setnom(String nom) {
        this.nom = nom;
    }
    
     public void setFinish(boolean nom) {
        this.isfinish = nom;
    }
     
     public boolean getFinish(){
         return this.isfinish;
     }
    public void setmoratoire(String nom) {
        this.moratoire = nom;
    }


    public void setid(String nom) {
        this.id = nom;
    }

    public void settelephone(String nom) {
        this.telephone = nom;
    }

    public void setdate(String nom) {
        this.date = nom;
    }

    public void setsexe(String nom) {
        this.sexe = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public String getid() {
        return this.id;
    }

    public String getdate() {
        return this.date;
    }

    public String getsexe() {
        return this.sexe;
    }
    public String getMoratoire() {
        return this.moratoire;
    }

    public String gettelephone() {
        return this.telephone;
    }
   public int michelle(){
        return 0;
       
   }
   public void gloyr(){
       
   }
}
