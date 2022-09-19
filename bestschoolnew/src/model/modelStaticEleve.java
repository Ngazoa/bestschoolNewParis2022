/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.JTable;

/**
 *
 * @author ASSUS GAMER
 */
public class modelStaticEleve {

    static String nom, sexe;
    static String id;
    static String lieu;
    static String telephone;
    static String matricule;
    static String prenom;
    static String date, pere = "Inconnu ", mere = "Inconnue", redoublant = "Ras";
    static JTable jt;

    public static String getLieu() {
        return modelStaticEleve.lieu;
    }

    public static JTable gettable() {
        return modelStaticEleve.jt;
    }

    public static String getpere() {
        return modelStaticEleve.pere;
    }

    public static String getmere() {
        return modelStaticEleve.mere;
    }

    public static String getredoublant() {
        return modelStaticEleve.redoublant;
    }

    public static String getsexe() {
        return modelStaticEleve.sexe;
    }

    public static String gettelephne() {
        return modelStaticEleve.telephone;
    }

    public static String getprenom() {
        return modelStaticEleve.prenom;
    }

    public static String getmatricule() {
        return modelStaticEleve.matricule;
    }

    public static String getNommatiere() {
        return modelStaticEleve.nom;
    }

    public static String getideleve() {
        return modelStaticEleve.id;
    }

    public static void setnomMatiere(String nom) {
        modelStaticEleve.nom = nom;
    }

    public static void setidMatiere(String id) {
        modelStaticEleve.id = id;
    }

    public static void setdate(String date) {
        modelStaticEleve.date = date;
    }

    public static String getDate() {
        return modelStaticEleve.date;
    }

    public static void setlieu(String date) {
        modelStaticEleve.lieu = date;
    }

    public static void setmatricule(String date) {
        modelStaticEleve.matricule = date;
    }

    public static void settelephone(String date) {
        modelStaticEleve.telephone = date;
    }

    public static void setprenom(String date) {
        modelStaticEleve.prenom = date;
    }

    public static void setsexe(String date) {
        modelStaticEleve.sexe = date;
    }

    public static void setpere(String date) {
        modelStaticEleve.pere = date;
    }

    public static void setmere(String date) {
        modelStaticEleve.mere = date;
    }

    public static void setredoublant(String date) {
        modelStaticEleve.redoublant = date;
    }

    public static void setjtable(JTable date) {
        modelStaticEleve.jt = date;
    }
}
