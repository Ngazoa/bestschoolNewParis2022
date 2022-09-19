/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

/**
 *
 * @author Michelle Wafo
 */
public class ZDialogInfo {
    
private String nom, sexe, age, cheveux, taille,matiere;
public ZDialogInfo(){}
public ZDialogInfo(String nom, String sexe, String age,
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

@Override
public String toString(){
String str;
if(this.nom != null && this.sexe != null &&
this.taille != null && this.age != null &&
this.cheveux != null){
str = " ";
str += "Nom : " + this.nom + "\n";
str += "Sexe : " + this.sexe + "\n";
str += "cathegorie : " + this.age + "\n";
str += "Classe : " + this.cheveux + "\n";
str += "Contact : " + this.taille + "\n";
str += "Matiere : " + this.matiere + "\n";
str += "Cotat horaire mensuel: " + memoire.coeff+ " Heures\n";


}else{
str = "Aucune information !\nSouhaitez vous continuer ?";
}return str;
}
}