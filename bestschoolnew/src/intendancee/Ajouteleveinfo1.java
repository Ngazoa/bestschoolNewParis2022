/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

/**
 *
 * @author Michelle Wafo
 */
public class Ajouteleveinfo1 {
    
private String nom, sexe, age, cheveux, taille,matiere;
public Ajouteleveinfo1(){}
public Ajouteleveinfo1(String nom, String sexe, String age,
String cheveux, String taille,String matiere){
this.nom = nom;
this.sexe = sexe;
this.age = age;
this.cheveux = cheveux;
this.taille = taille;
this.matiere = matiere;
  
}
 
//------------------------------------
public String getNom() {
return nom;
} public void setNom(String nom) {
this.nom = nom;
} //------------------------------------
public String getSexe() {
return sexe;
} public void setSexe(String sexe) {
this.sexe = sexe;
} //------------------------------------
public String getAge() {
return age;
} public void setAge(String age) {
this.age = age;
} //------------------------------------
public String getCheveux() {
return cheveux;
} public void setCheveux(String cheveux) {
this.cheveux = cheveux;
} //------------------------------------
public String getTaille() {
return taille;
} public void setTaille(String taille) {
this.taille = taille;
} //-------------------------------------

public String getmatiere() {
return matiere;
} public void setmatiere(String matiere) {
this.nom = matiere;
}

public String toString(){
String str;
if(this.nom != null && this.sexe != null &&
this.taille != null && this.age != null &&
this.cheveux != null){
str = "";
str += "Nom : " + this.nom + "\n";
str += "Sexe : " + this.sexe + "\n";
str += "Classe : " + this.cheveux + "\n";
str += "Contact parents : " + this.taille + "\n";
str += "Date de naissance : " + this.matiere + "\n";
str += "Lieu de naissance : " + this.age + "\n";

}else{
str = "Aucune information ! Souhaitez vous continuer ?";
}return str;
}
}