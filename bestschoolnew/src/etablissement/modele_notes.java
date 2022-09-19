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
public class modele_notes {
    
    
 private String nom,date,lnaissance,sexe;
    private String classe;
    private String 
            anglais,langue,litterature,redaction,hist_geo,ecm 
            ,svt,chimie,physiques,mathematiques,eps,tm,pct,informatique,esf,
            allemand,espagnole,
            metier_formation,qhse,entrep,techno,circuit_elect
            ,machine_elect,techno_instaleclec,schema_elec,transp_dist,dessin_tech,TST,
            cir_num,cir_anal,tp_elect,install_elect,intall_elect_ind,mesure_essai_elect,TSP;
    private String note2,matiere;

    public modele_notes() {
    }

    public modele_notes(String name, String date, String lnaissance, String sexe,String classe,String anglais,String langue,String litterature,String redaction,
            String hist_geo,String ecm, String svt,String chimie, String physiques,String mathematiques, String eps,
            String tm,String informatique,String esf,String allemand,String espagnole,
            String metier_formation,String qhse ,String entrep,String techno
            , String circuit_elect,String machine_elect,String techno_instaleclec,String schema_elec, String transp_dist,String dessin_tech,String TST,String cir_num
            ,String cir_anal,String tp_elect,String install_elect,String intall_elect_ind, String mesure_essai_elect,String TSP)
    { 
        this.cir_anal=cir_anal;
        this.tp_elect=tp_elect; this.intall_elect_ind=intall_elect_ind;
        this.install_elect=install_elect; this.mesure_essai_elect=mesure_essai_elect;
        this.TSP=TSP; 
        
        this.nom = name;
        this.date= date;
        this.lnaissance = lnaissance;
        this.sexe= sexe;
        this.classe=classe;
        this.anglais=anglais;
        this.langue=langue;
        this.litterature=litterature;
        this.redaction=redaction;
        this.hist_geo=hist_geo;
        this.ecm=ecm;//  
        this.svt=svt;
        this.chimie=chimie;
        this.physiques=physiques;
        this.mathematiques=mathematiques;
        this.eps=eps;          
           

        this.dessin_tech=dessin_tech;
        this.tm=tm;
        this.informatique=informatique;
        this.esf=esf;
        this.allemand=allemand;
        this.espagnole=espagnole;
        this.metier_formation=metier_formation;this.qhse=qhse;
        this.entrep=entrep;this.techno=techno;
        this.circuit_elect=circuit_elect;this.machine_elect=machine_elect;
        this.techno_instaleclec=techno_instaleclec;this.transp_dist=transp_dist;
        this.schema_elec=schema_elec;this.dessin_tech=dessin_tech;
        this.TST=TST;this.cir_num=cir_num;
        
    }
    
    public void sexe(String s) {
        this.sexe= s;
    }
    
     public String sexe() {
        return sexe;
    }
    
    
     public String anglais() {
        return anglais;
    }
           
    public void anglais(String s) {
        this.anglais= s;
    }
     public String langue() {
        return langue;
    }
public String machine_elect() {
        return machine_elect;
    }
    public void langue(String s) {
        this.langue= s;
    }
    
     public String litterature() {
        return litterature;
    }

    public void litterature(String s) {
        this.litterature= s;
    }
    
     public String redaction() {
        return redaction;
    }
     
     
     public String TSP() {
        return TSP;
    }
           
    public void TSP(String s) {
        this.TSP= s;
    }
     public String nom() {
        return nom;
    }

    public void nom(String s) {
        this.nom= s;
    }
    
     public String date() {
        return date;
    }

    public void date(String s) {
        this.date= s;
    }
    
     public String lnaissance() {
        return lnaissance;
    } 
     
public void lnaissance(String s) {
        this.lnaissance= s;
    }
    public void redaction(String s) {
        this.redaction= s;
    }
    
    
     public String hist_geo() {
        return hist_geo;
    }

    public void hist_geo(String s) {
        this.hist_geo= s;
    }
    
    
    
 public String ecm() {
        return ecm;
    }

    public void ecm(String s) {
        this.ecm= s;
    }
    
    
    
      public String svt() {
        return svt;
    }

    public void svt(String matiere) {
        this.svt= matiere;
    }
    
    public String chimie() {
        return chimie;
    }

    public void chimie(String username) {
        this.chimie= username;
    }

    public String physiques() {
        return physiques;
    }

    public void physiques(String firstName) {
        this.physiques = firstName;
    }

    public String mathematiques() {
        return mathematiques;
    }

    public void mathematiques(String lastName) {
        this.mathematiques = lastName;
    }
    public String eps() {
        return eps;
    }

    public void eps(String address) {
        this.eps = address;
    } 
    
    public String tm() {
        return tm;
    }
           
    public void tm(String s) {
        this.tm= s;
    }
     public String pct() {
        return pct;
    }

    public void pct(String s) {
        this.pct= s;
    }
    
     public String informatique() {
        return informatique;
    }

    public void informatique(String s) {
        this.informatique= s;
    }
    
     public String esf() {
        return esf;
    }

    public void esf(String s) {
        this.esf= s;
    }
     public String allemand() {
        return allemand;
    }

    public void allemand(String s) {
        this.allemand= s;
    }
    
     public String dessin_tech() {
        return dessin_tech;
    }

    public void dessin_tech(String s) {
        this.dessin_tech= s;
    }
    
    
    
 public String espagnole() {
        return espagnole;
    }

    public void metier_formation(String s) {
        this.metier_formation= s;
    }
    
    
    
      public String metier_formation() {
        return metier_formation;
    }

    public void qhse(String matiere) {
        this.qhse= matiere;
    }
    
    public String qhse() {
        return qhse;
    }

    public void entrep(String username) {
        this.entrep= username;
    }

    public String entrep() {
        return entrep;
    }

    public void techno(String firstName) {
        this.techno = firstName;
    }

    public String techno() {
        return techno;
    }

    public void circuit_elect(String lastName) {
        this.circuit_elect = lastName;
    }
    public String circuit_elect() {
        return circuit_elect;
    }

    public void machine_elect(String address) {
        this.machine_elect = address;
    } public String smachine_elect() {
        return machine_elect;
    }
           
    public void techno_instaleclec(String s) {
        this.techno_instaleclec= s;
    }
     public String techno_instaleclec() {
        return techno_instaleclec;
    }

    public void schema_elec(String s) {
        this.schema_elec= s;
    }
    
     public String schema_elec() {
        return schema_elec;
    }

    public void transp_dist(String s) {
        this.transp_dist= s;
    }
    
     public String transp_dist() {
        return transp_dist;
    }

   
    
    
     public String TST() {
        return TST;
    }

    public void TST(String s) {
        this.TST= s;
    }
    
    
    
 public String cir_num() {
        return cir_num;
    }

    public void cir_num(String s) {
        this.cir_num= s;
    }
    
    
    
      public String cir_anal() {
        return cir_anal;
    }

    public void cir_anal(String matiere) {
        this.cir_anal= matiere;
    }
    
    public String tp_elect() {
        return tp_elect;
    }

    public void tp_elect(String username) {
        this.tp_elect= username;
    }

    public String install_elect() {
        return install_elect;
    }

    public void install_elect(String firstName) {
        this.install_elect = firstName;
    }

    public String intall_elect_ind() {
        return intall_elect_ind;
    }

    public void intall_elect_ind(String lastName) {
        this.intall_elect_ind = lastName;
    }
    public String mesure_essai_elect() {
        return mesure_essai_elect;
    }

    public void mesure_essai_elect(String address) {
        this.mesure_essai_elect = address;
    }
   
    @Override
    public String toString()
    {
        String object = "username: "+nom+" firstName: "+classe+" lastName: "+mesure_essai_elect+" address: "+note2;
        return object;
    } 
}
