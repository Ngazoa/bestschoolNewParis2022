package com.pack.entity;

import etablissement.memoire;
import java.util.prefs.Preferences;
import security.fonctions;

public class Entete {
        Preferences prf = Preferences.userNodeForPackage(fonctions.class);

	private String hierarchieLevel1 = "Ministere des Enseignements Secondaires";
	private String hierarchieLevel2 = "Delegation Regionale du Littoral";
	private String hierarchieLevel3 = "Delegation Departementale du Wouri";
	private String school =prf.get("nom_etab", null);
	
	private String country = "Republique du Cameroun";
	private String device = "Paix-Travail-Patrie";
        private String hierarchieLevel1a = "Ministry of secondary education";
	private String hierarchieLevel2a = "Regional delegation of Littoral ";
	private String hierarchieLevel3a = "Depertmental  delegation of Wouri";
	private final String schoola = "Lycée Bilingue de Mbalmayo";
	
	private final String countrya = "Republic of Cameroon"; 
	private final String devicea = "Peace-Work-Fatherland";
        
	private String statusHeadmaster = prf.get("pricipal", null);

	private String linkLogo = memoire.logo;
	private String carte = memoire.carte;
	private String linkFiligrane = memoire.filigran;
	
	private String bp =prf.get("bp", null);
	private String anneeScolaire =prf.get("nom_annee", null);
	private String numero =prf.get("contact_etab", null);
	

	
	public String getBp() {
		return bp;
	}
	public Entete setBp(String bp) {
		this.bp = bp;
		return this;
	}public String getcarte() {
		return carte;
	}
	public Entete setcarte(String bp) {
		this.carte = bp;
		return this;
	}
	public String getAnneeScolaire() {
		return anneeScolaire;
	}
	public Entete setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
		return this;
	}
	public String getNumero() {
		return numero;
	}
	public Entete setNumero(String numero) {
		this.numero = numero;
		return this;
	}
	public String getLinkFiligrane() {
		return linkFiligrane;
	}
	public Entete setLinkFiligrane(String linkFiligrane) {
		this.linkFiligrane = linkFiligrane;
		return this;
	}
	public String getLinkLogo() {
		return linkLogo;
	}
	public Entete setLinkLogo(String linkLogo) {
		this.linkLogo = linkLogo;
		return this;
	}
	
	public String getHierarchieLevel1() {
		return hierarchieLevel1;
	}
	public Entete setHierarchieLevel1(String hierarchieLevel1) {
		this.hierarchieLevel1 = hierarchieLevel1;
		return this;
	}
	public String getHierarchieLevel2() {
		return hierarchieLevel2;
	}
	public Entete setHierarchieLevel2(String hierarchieLevel2) {
		this.hierarchieLevel2 = hierarchieLevel2;
		return this;
	}
	public String getHierarchieLevel3() {
		return hierarchieLevel3;
	}
	public Entete setHierarchieLevel3(String hierarchieLevel3) {
		this.hierarchieLevel3 = hierarchieLevel3;
		return this;
	}public String getHierarchieLevel1a() {
		return hierarchieLevel1a;
	}
	public Entete setHierarchieLevel1a(String hierarchieLevel1) {
		this.hierarchieLevel1a = hierarchieLevel1;
		return this;
	}
	public String getHierarchieLevel2a() {
		return hierarchieLevel2a;
	}
	public Entete setHierarchieLevel2a(String hierarchieLevel2) {
		this.hierarchieLevel2a = hierarchieLevel2;
		return this;
	}
	public String getHierarchieLevel3a() {
		return hierarchieLevel3a;
	}
	public Entete setHierarchieLevel3a(String hierarchieLevel3) {
		this.hierarchieLevel3a = hierarchieLevel3;
		return this;
	}
	public String getSchool() {
		return school;
	}
	public Entete setSchool(String school) {
		this.school = school;
		return this;
	}
	public String getCountry() {
		return country;
	}
	public Entete setCountry(String country) {
		this.country = country;
		return this;
	}
	public String getDevice() {
		return device;
	}
	public Entete setDevice(String device) {
		this.device = device;
		return this;
	}
	public String getStatusHeadmaster() {
		return statusHeadmaster;
	}
	public Entete setStatusHeadmaster(String statusHeadmaster) {
		this.statusHeadmaster = statusHeadmaster;
		return this;
	}
	
	
}
