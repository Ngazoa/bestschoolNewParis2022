/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

import etablissement.memoire1;
import java.util.ArrayList;

/**
 *
 * @author Michelle Wafo
 */
public class memoire_intendance {
    
    
        public static final String ip_adress="/ecole/inten-dist/jesus/parametre/";         
       // String ip=ip_adress;

        public static String nom_conserve="";
         public static String tel_conserve="";
        public static String id_user;
        public static String nom_user;
        public static String titre;
        public static final String serveur="http://"+memoire1.ip_adress;
        public static String login =serveur+ip_adress+"intendance/login.php";
        public static String solde =serveur+ip_adress+"intendance/solde.php";
        public static String liste_depenses_validee =serveur+ip_adress+"intendance/liste_depenses_validee.php";
        public static String liste_matiere_enseignant =serveur+ip_adress+"intendance/liste_matiere_enseignant.php";
        public static String Ajout_depense=serveur+ip_adress+"intendance/Ajouter_depense.php";
        public static String Ajout_fonds=serveur+ip_adress+"intendance/Ajouter_fond.php";
        public static String valider_fonds_ou_depense=serveur+ip_adress+"intendance/valider_fonds_ou_depense.php";
        public static String modifier_mot_passe=serveur+ip_adress+"intendance/modifier_mot_passe.php";
        public static String Ajouter_fond=serveur+ip_adress+"intendance/Ajouter_fond.php";
        public static String valider_entree=serveur+ip_adress+"intendance/valider_entree.php";
        public static String Validation_decharge=serveur+ip_adress+"intendance/Validation_decharge.php";
        public static String desactiver_secretaire=serveur+ip_adress+"intendance/desactiver_secretaire.php";

        public static String liste_secretaires=serveur+ip_adress+"intendance/liste_secretaires.php";
        public static String ajout_secretaire=serveur+ip_adress+"intendance/ajout_secretaire.php";
        public static String toutes_decharge_sortie =serveur+ip_adress+"intendance/toutes_decharge_sortie.php";
        public static String toutes_decharge_entree =serveur+ip_adress+"intendance/toutes_decharge_entree.php";
        public static String liste_fonds_en_attente =serveur+ip_adress+"intendance/liste_fonds_en_attente.php";
        public static String liste_depenses_en_attente =serveur+ip_adress+"intendance/liste_depenses_en_attente.php";
        public static String liste_fonds_validees =serveur+ip_adress+"intendance/liste_fonds_validees.php";
        // public static    ArrayList<String> prix= new ArrayList<>(); 

         public static String nom;
         public static int reste=0;
         public static String erreur="";
         public static String fonction;
         public static int longueur_tableau;
         public static String classe;
         public static String matiere;
         public static String sexe;
         public static String telephone;
         public static String cathegorie;
         public static String lieu_naissance;
         public static String date_naissance;
         public static String desactivation_eleve =serveur+ip_adress+"intendance/desactivation_eleve.php";

        public static String difference;
        public static String modifier_eleve =serveur+ip_adress+"intendance/modifier_eleve.php";
        public static String ajouter_enseignant =serveur+ip_adress+"intendance/ajouter_enseignant.php";
        public static String ajouter_blame =serveur+ip_adress+"intendance/ajouter_blame.php";
        public static String prix="";   
        public static String periode;
        public static String but;
        public static String desciption;
        public static String cateristiques_paie;
        
                 public static String ecole="College la renovation";               
                 public static String telephone_ecole="(+237 33 40 51 16)";
                 public static String dirigeant="Proviseur";
                 public static String logo="image/akouma.png";
                 public static String filigran="image/logo.png";
                 public static String bp="8335";
                 public static String anne_scolaire="2018/2019";
}
