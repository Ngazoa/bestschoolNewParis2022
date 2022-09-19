package com.pack.entity;

import java.util.Date;

public class Profile {
	private String matricule = "/",id_eleve="";
	private String nom = "/";
	private String photo = "/";
	private Date date ;
	private String lieu = "/";
	private int effectif = 0;
	private String classe = "/";
	private String sexe = "/";
	private String rd = "/";
	
	
	public String rd() {
		return rd;
	}
	public Profile rd(String matricule) {
		this.rd = matricule;
		return this;
	}
        public String getMatricule() {
		return matricule;
	}
	public Profile setMatricule(String matricule) {
		this.matricule = matricule;
		return this;
	}
	public Profile setDate(Date date) {
		this.date = date;
                return this;

	}
	public String id_eleve() {
		return id_eleve;
	}
	public Profile id_eleve(String nom) {
		this.id_eleve = nom;
		return this;
	}
        public String getNom() {
		return nom;
	}
	public Profile setNom(String nom) {
		this.nom = nom;
		return this;
	}
	public  Date geDate() {	
		
		return date;
	}
	
	/*
	 * @param dateValue : Exp: 10-05-2013 ou 10/05/2013
	 * */
	
	public String getLieu() {
		return lieu;
	}
	public Profile setLieu(String lieu) {
		this.lieu = lieu;
		return this;
	}
	public int getEffectif() {
		return effectif;
	}
	public Profile setEffectif(int effectif) {
		this.effectif = effectif;
		return this;
	}
	public String getClasse() {
		return classe;
	}
	public Profile setClasse(String classe) {
		this.classe = classe;
		return this;
	}
	public String getSexe() {
		return sexe;
	}
	public Profile setSexe(String sexe) {
		this.sexe = sexe;
		return this;
	}public String getphoto() {
		return photo;
	}
	public Profile setphoto(String sexe) {
		this.photo = sexe;
		return this;
	}
	
	
}
