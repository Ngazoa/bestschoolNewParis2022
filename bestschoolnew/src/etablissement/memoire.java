package etablissement;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.prefs.Preferences;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michelle Wafo
 */
public class memoire {
    Preferences prf = Preferences.userNodeForPackage(security.fonctions.class);

    public static final String ip_adress = "/ecole/inten-dist/jesus/parametre/ense_censeur";
    static String couleur = "Nimbus";
    public static String type;
    public static String classe_de_travail = "";
    public static int elevesabsent=0;
    String ip = ip_adress;

    public static final String serveur = "http://" + memoire1.ip_adress;
    public static String photo = serveur + ip_adress + "/";
    public static String modifier_mot_passe = serveur + ip_adress + "intendance/modifier_mot_passe.php";
    public static String login = serveur + ip_adress + "/connexionn.php";
    public static String liste_classe = serveur + ip_adress + "/liste_classe.php";
    public static String liste_cycles = serveur + ip_adress + "/liste_cycles.php";
    public static String liste_personnels = serveur + ip_adress + "/liste_personnels.php";
    public static String liste_matiere_enseignant = serveur + ip_adress + "/liste_matiere_enseignant.php";
    public static String id_matiere = serveur + ip_adress + "/id_matiere.php";
    public static String definition_periodes = serveur + ip_adress + "/definition_periodes.php";
    public static String verification_remplissage = serveur + ip_adress + "/verification_remplissage.php";

    public static String liste_tte_les_matieres = serveur + ip_adress + "/liste_tte_les_matieres.php";
    public static String liste_matiere = serveur + ip_adress + "/liste_matiere.php";
    public static String liste_matiere_parclasse = serveur + ip_adress + "/liste_matiere_parclasse.php";
    public static String justifions_absences = serveur + ip_adress + "/justifions_absences.php";
    public static String cycle_classeChoisie = serveur + ip_adress + "/cycle_classeChoisie.php";

    public static String ajout_matiere = serveur + ip_adress + "/ajout_nouvellematiere.php";
    public static String inserer_reactivation_enseignant = serveur + ip_adress + "/modifier_rectivation_enseignant.php";
    public static String liste_eleve_par_classe = serveur + ip_adress + "/liste_eleve_par_classe.php";
    public static String liste_eleve_par_classement = serveur + ip_adress + "/liste_eleve_par_classement.php";
    public static String liste_absences_eleves = serveur + ip_adress + "/liste_absences_eleves.php";
    public static String liste_reactivation_eleves = serveur + ip_adress + "/liste_reactivation_eleves.php";
    public static String liste_reactivation_enseignants = serveur + ip_adress + "/liste_reactivation_enseignants.php";
    public static String liste_reactivation_modifier = serveur + ip_adress + "/liste_reactivation_modifier.php";
    public static String liste_lasse_en_fonction_Ensiegnant = serveur + ip_adress + "/liste_lasse_en_fonction_Ensiegnant.php";
    public static String liste_personnel = serveur + ip_adress + "/liste_personnel.php";

    public static String liste_enseignant = serveur + ip_adress + "/liste_enseignant.php";
    public static String ajout_cle = serveur + ip_adress + "/ajout_cle.php";
    public static String lire_cle = serveur + ip_adress + "/lire_cle.php";
    public static String config = serveur + ip_adress + "/config.php";
    public static String lire_numero = serveur + ip_adress + "/lire_numero.php";
    public static String modifier_numero = serveur + ip_adress + "/modifiernumero.php";
    public static String liste_enseignant2 = serveur + ip_adress + "/liste_enseignant2.php";
    public static String ajout_classe = serveur + ip_adress + "/ajout_classe.php";
    public static String ajout_pansion = serveur + ip_adress + "/ajout_pansion.php";
    public static String enregistrer_notes = serveur + ip_adress + "/enregistrer_notes.php";
    public static String supprimer_classes = serveur + ip_adress + "/supprimer_classes.php";
    public static String ajout_cycle = serveur + ip_adress + "/ajout_cycle.php";
    public static ArrayList<String> prix = new ArrayList<>();
    public static ArrayList<String> periode = new ArrayList<>();

    public static String modifier_eleve = serveur + ip_adress + "/modifier_eleve.php";
    public static String ajouter_enseignant = serveur + ip_adress + "/ajouter_enseignant.php";
    public static String ajouter_personnel_admin = serveur + ip_adress + "/ajouter_personnel_admin.php";
    public static String ajouter_eleve = serveur + ip_adress + "/ajouter_eleve.php";
    public static String selection_classes = serveur + ip_adress + "/selection_classes.php";
    public static String ajouter_blame = serveur + ip_adress + "/ajouter_blame.php";

    public static String liste_eleve_insolvables = serveur + ip_adress + "/liste_eleve_insolvables.php";
    public static String liste_elve_insolvable_classe = serveur + ip_adress + "/liste_elve_insolvable_classe.php";
    public static String inscription_eleve = serveur + ip_adress + "/inscription_eleve.php";
    public static String paie_enseignants = serveur + ip_adress + "/paie_enseignants.php";

    public static String liste_eleves_classe = serveur + ip_adress + "/liste_eleve_classe.php";
    public static String liste_periode_remplissage_notes = serveur + ip_adress + "/liste_periode_remplissage_notes.php";
    public static String liste_eleves_justifie = serveur + ip_adress + "/liste_eleves_justifie.php";
    public static String liste_cycle = serveur + ip_adress + "/liste_cycle.php";
    public static String liste_prix = serveur + ip_adress + "/prix_tranches.php";
    public static String liste_eleves_etab = serveur + ip_adress + "/liste_eleves_etab.php";
    public static String desactivation_enseignant = serveur + ip_adress + "/desactivation_enseignant.php";
    public static String desactivation_eleve = serveur + ip_adress + "/desactivation_eleve.php";
    public static String parametres = serveur + ip_adress + "/parametres.php";

    public static String erregistrer_note = serveur + ip_adress + "/erregistrer_note.php";
    public static String erregistrer_notes1 = serveur + ip_adress + "/sequence1_Remplir.php";
    public static String erregistrer_notes2 = serveur + ip_adress + "/sequence2_Remplir.php";
    public static String erregistrer_notes3 = serveur + ip_adress + "/sequence3_Remplir.php";
    public static String erregistrer_notes4 = serveur + ip_adress + "/sequence4_Remplir.php";
    public static String erregistrer_notes5 = serveur + ip_adress + "/sequence5_Remplir.php";
    public static String erregistrer_notes6 = serveur + ip_adress + "/sequence6_Remplir.php";
    public static String liste_classe_a_impression = serveur + ip_adress + "/liste_classe_a_impression.php";
    public static String trait;
    public static String etatactuel;
    public static String id_user;
    public static String id_absence;
    public static String id_eleve;
    public static int nbre_eleve;
    public static int essaie = 3;
    public static int licence = 0;
    public static String choix_sequence;
    public static String choix_sequence2;
    public static String choix_impression;
    public static String nom_user;
    public static String difference;
    public static String motif;
    public static String numero;
    public static String connecte;
    public static String coeff;
    public static String numero_sequence;
    public static String enregistrer_classe;

    public static String description;
    public static String nom_conserve = "";
    public static String tel_conserve = "";
    public static ArrayList<String> classement = new ArrayList<>();
    public static ArrayList<String> disciplines = new ArrayList<>();
    public static ArrayList<String> sex = new ArrayList<>();
    public static ArrayList<String> note = new ArrayList<>();
    public static ArrayList<String> rangs = new ArrayList<>();
    public static ArrayList<String> code = new ArrayList<>();
    public static float[] moyenne;
    public static ArrayList<String> noms = new ArrayList<>();
    public static ArrayList<String> notetableaux = new ArrayList<>();
    public static ArrayList<String> matieres = new ArrayList<>();

    //parametres statistiques
    public static String nom;
    public static String redoublant;
    public static String pere;
    public static String mere;
    public static int mention1;
    public static double MOYGENc;
    public static int mention2;
    public static int mention3;
    public static int mention4;
    public static int mention5;
    public static int mention6;
    public static int mention7;
    public static int mention8;
    public static int mention9;
    public static int mention10;

    public static int nombre_fille;
    public static int nombre_fille_absente;
    public static int nombre_garcon_absent;
    public static int nombre_admis;
    public static int nombre_garcon;
    public static int nombre_absent;
    public static int nombre_fille_admise;
    public static int nombre_garcons_admis;
    public static int pourcentage_reussite;

    public static int longueur_tableau;
    public static String classe;
    public static String duree_cours;
    public static String jour_cours;
    public static String matiere_cours;
    public static String matiere;
    public static String sexe;
    public static String telephone;
    public static String cathegorie;
    public static String lieu_naissance;
    public static String date_naissance;
    public static int decision;

    // entete bulletin  ecole
    public static String hierarchieLevel1 = "Ministère des Enseignements Secondaires";
    public static String hierarchieLevel2 = "Délégation Régionale du Littoral";
    public static String hierarchieLevel3 = "Délégation Départementale du Wouri";
    public static String country = "République du Cameroun";
    public static String device = "Paix-Travail-Patrie";
    public static String statusHeadmaster = "Proviseur";
    public static String ecole;
    public static String telephone_ecole;
    public static String dirigeant;
    public static String logo = "src/image/logo.png";
    public static String carte = "src/image/carteeleve.jpg";
    public static String enseignant = "src/image/ty.png";
    public static String eleves = "src/image/eleve.png";
    public static String setup = "rrc/image/akouma.png";
    public static String filigran = "src/image/fi.png";
    public static String signature = "src/image/signature.png";
    public static String dra = "/im1.png";
    public static String tableauH = "src/image/tableau2.png";
    public static String th = "src/image/th.png";
    public static String bp;
    public static String anne_scolaire ;
    public static Blob Bloblogo ;
    public static Blob Blobfili ;
    public static Blob Blobsignature ;
    public static String matricule = "";
    public static float MOYGEN = 0;
    public static String MOYGENstatistiques = "00";
    public static String MOYP = "";
    public static String MOYD = "";

    public static String note1;
    public static String note2;
    public static String note3;
    public static String note4;
    public static String note5;
    public static String note6;
    public static String note7;
    public static String note8;
    public static String note9;
    public static String note10;
    public static String note11;
    public static String note12;
    public static String note13;
    public static String note14;
    public static String note15;
    public static String note16;
    public static String note17;
    public static String note18;
    public static String note19;
    public static String note20;
    public static String note21;
    public static String note22;
    public static String note23;
    public static String note24;
    public static String note25;

    public static String enseignant1 = "Mr /";
    public static String enseignant2 = "Mr /";
    public static String enseignant3 = "Mr /";
    public static String enseignant4 = "Mr /";
    public static String enseignant5 = "Mr /";
    public static String enseignant6 = "Mr /";
    public static String enseignant7 = "Mr /";
    public static String enseignant8 = "Mr /";
    public static String enseignant9 = "Mr /";
    public static String enseignant10 = "Mr /";
    public static String enseignant11 = "Mr /";
    public static String enseignant12 = "Mr /";
    public static String enseignant13 = "Mr /";
    public static String enseignant14 = "Mr /";
    public static String enseignant15 = "Mr /";
    public static String enseignant16 = "Mr /";
    public static String enseignant17 = "Mr /";
    public static String enseignant18 = "Mr /";
    public static String enseignant19 = "Mr /";
    public static String enseignant20 = "Mr /";
    public static String enseignant21 = "Mr /";
    public static String enseignant22 = "Mr /";
    public static String enseignant23 = "Mr /";
    public static String enseignant24 = "Mr /";
    public static String enseignant25 = "Mr /";

    public static String gpe1 = "4";
    public static String gpe2 = "4";
    public static String gpe3 = "4";
    public static String gpe4 = "4";
    public static String gpe5 = "4";
    public static String gpe6 = "4";
    public static String gpe7 = "4";
    public static String gpe8 = "4";
    public static String gpe9 = "4";
    public static String gpe10 = "4";
    public static String gpe11 = "4";
    public static String gpe12 = "4";
    public static String gpe13 = "4";
    public static String gpe14 = "4";
    public static String gpe15 = "4";
    public static String gpe16 = "4";
    public static String gpe17 = "4";
    public static String gpe18 = "4";
    public static String gpe19 = "4";
    public static String gpe20 = "4";
    public static String gpe21 = "4";
    public static String gpe22 = "4";
    public static String gpe23 = "4";
    public static String gpe24 = "4";
    public static String gpe25 = "4";

    public static String coefficient1 = "1";
    public static String coefficient2 = "1";
    public static String coefficient3 = "1";
    public static String coefficient4 = "1";
    public static String coefficient5 = "1";
    public static String coefficient6 = "1";
    public static String coefficient7 = "1";
    public static String coefficient8 = "1";
    public static String coefficient9 = "1";
    public static String coefficient10 = "1";
    public static String coefficient11 = "1";
    public static String coefficient12 = "1";
    public static String coefficient13 = "1";
    public static String coefficient14 = "1";
    public static String coefficient15 = "1";
    public static String coefficient16 = "1";
    public static String coefficient17 = "1";
    public static String coefficient18 = "1";
    public static String coefficient19 = "1";
    public static String coefficient20 = "1";
    public static String coefficient21 = "1";
    public static String coefficient22 = "1";
    public static String coefficient23 = "1";
    public static String coefficient24 = "1";
    public static String coefficient25 = "1";

}
