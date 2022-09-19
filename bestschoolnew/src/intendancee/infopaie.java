/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

/**
 *
 * @author Junior ENAMA
 */
public class infopaie {
 private String nom, sexe, age, cheveux, taille,matiere;
public infopaie(){}
public infopaie(String nom, String sexe, String age,
String cheveux){
this.nom = nom;
this.sexe = sexe;
this.age = age;
this.cheveux = cheveux;

  
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
if(this.nom != null &&
this.cheveux != null){
str = " ";
str += "NOM PERSONNEL : " + this.nom  + "\n";
str += "MOIS: " + this.sexe + "\n";
str += "POUR : " + this.age + "\n";
str += "MONTANT : " + this.cheveux + "  FCFA\n";


}else{
str = "Le montant ne doit pas etre null !\n Souhaitez vous continuer ?";
}return str;
}
}