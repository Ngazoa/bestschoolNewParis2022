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
public class modelClasse {

    String nom;
    String id;
    String enseignant;
    String coeff;
    String groupe;
    String cotat;
    String date;

    public String getGroup() {
        return this.groupe;
    }

    public String getEnseignant() {
        return this.enseignant;
    }

    public String getHoraire() {
        return this.cotat;
    }

    public String getCoeff() {
        return this.coeff;
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

    public void setdate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setgroupe(String date) {
        this.groupe = date;
    }

    public void setcoeff(String date) {
        this.coeff = date;
    }

    public void setcotathoraire(String date) {
        this.cotat = date;
    }

    public void setEnseignant(String date) {
        this.enseignant = date;
    }
}
