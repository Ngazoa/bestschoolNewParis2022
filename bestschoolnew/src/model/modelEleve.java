/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;


/**
 *
 * @author ASSUS GAMER
 */
public class modelEleve {
    boolean select;
    String nom, sexe,avatar;
    String id;
    String lieu;
    String telephone;
    String matricule;
    String prenom, note, moyene;
    String[] listeNotes;
    Date date;
    String pere, mere, redoublant;
    private String[] listeNotes2;
    private String[] listeNotes3;
    private String[] listeNotes4;
    private String[] listeNotes5;
    private String[] listeNotes6;
    private String[] randmatiere;

    public String[] getlisteNoteseleves() {
        return this.listeNotes;
    }
    public boolean getSelect() {
        return this.select;
    }

    public String[] getlisteNoteseleves3() {
        return this.listeNotes3;
    }
    public String[] getRangMatiere() {
        return this.randmatiere;
    }

    public String[] getlisteNoteseleves4() {
        return this.listeNotes4;
    }

    public String[] getlisteNoteseleves5() {
        return this.listeNotes5;
    }

    public String[] getlisteNoteseleves6() {
        return this.listeNotes6;
    }

    public String[] getlisteNoteseleves2() {
        return this.listeNotes2;
    }

    public String getLieu() {
        return this.lieu;
    }
    public String getAvarar() {
        return this.avatar;
    }

    public String getNote() {
        return this.note;
    }

    public String getMoyenne() {
        return this.moyene;
    }

    public String getpere() {
        return this.pere;
    }

    public String getmere() {
        return this.mere;
    }

    public String getredoublant() {
        return this.redoublant;
    }

    public String getsexe() {
        return this.sexe;
    }

    public String gettelephne() {
        return this.telephone;
    }

    public String getprenom() {
        return this.prenom;
    }

    public String getmatricule() {
        return this.matricule;
    }

    public String getNommatiere() {
        return this.nom;
    }

    public String getidClasse() {
        return this.id;
    }

    public void setnomMatiere(String nom) {
        this.nom = nom;
    }

    public void setidMatiere(String id) {
        this.id = id;
    }

    public void setdate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setglieu(String date) {
        this.lieu = date;
    }

    public void setmatricule(String date) {
        this.matricule = date;
    }

    public void settelephone(String date) {
        this.telephone = date;
    }

    public void setprenom(String date) {
        this.prenom = date;
    }

    public void setsexe(String date) {
        this.sexe = date;
    }

    public void setpere(String date) {
        this.pere = date;
    }

    public void setmere(String date) {
        this.mere = date;
    }

    public void setredoublant(String date) {
        this.redoublant = date;
    }

    public void setnote(String date) {
        this.note = date;
    }

    public void setmoyenne(String date) {
        this.moyene = date;
    }
    public void setAvatar(String date) {
        this.avatar = date;
    }

    public void setlisteNotes(String[] date) {
        this.listeNotes = date;
    }
public void setRangMatiere(String[] date) {
        this.randmatiere = date;
    }
    public void setlisteNotes2(String[] date) {
        this.listeNotes2 = date;
    }

    public void setlisteNotes3(String[] date) {
        this.listeNotes3 = date;
    }

    public void setlisteNotes4(String[] date) {
        this.listeNotes4 = date;
    }

    public void setlisteNotes5(String[] date) {
        this.listeNotes5 = date;
    }

    public void setlisteNotes6(String[] date) {
        this.listeNotes6 = date;
    }
    public void setSelect(boolean date) {
        this.select = date;
    }
}
