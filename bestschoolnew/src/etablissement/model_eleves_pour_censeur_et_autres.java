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
public class model_eleves_pour_censeur_et_autres {private String nom;
    private String classe;
    private String note1,s1,s2,s3,s4,s5,s6;
    private String note2,matiere;

    public model_eleves_pour_censeur_et_autres() {
    }

    public model_eleves_pour_censeur_et_autres(String username, String firstName
            , String lastName, String address,String matiere,String s1,String s2,String s3,String s4,String s5,String s6) {
        this.nom = username;
        this.classe= firstName;
        this.note1 = lastName;
        this.note2 = address;
        this.matiere=matiere;
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
        this.s4=s4;
        this.s5=s5;
        this.s6=s6;
    }
    
     public String s6() {
        return s6;
    }

    public void s6(String s) {
        this.s6= s;
    }
     public String s5() {
        return s5;
    }

    public void s5(String s) {
        this.s5= s;
    }
    
     public String s4() {
        return s4;
    }

    public void s4(String s) {
        this.s4= s;
    }
    
     public String s3() {
        return s3;
    }

    public void s3(String s) {
        this.s3= s;
    }
    
    
     public String s2() {
        return s2;
    }

    public void s2(String s) {
        this.s2= s;
    }
    
    
    
 public String s1() {
        return s1;
    }

    public void s1(String s) {
        this.s1= s;
    }
    
    
    
      public String getmatiere() {
        return matiere;
    }

    public void setmatiere(String matiere) {
        this.matiere= matiere;
    }
    
    public String getUsername() {
        return nom;
    }

    public void setUsername(String username) {
        this.nom= username;
    }

    public String getclasse() {
        return classe;
    }

    public void setclasse(String firstName) {
        this.classe = firstName;
    }

    public String getnote() {
        return note1;
    }

    public void setnote(String lastName) {
        this.note1 = lastName;
    }
    public String getnote2() {
        return note2;
    }

    public void setnote2(String address) {
        this.note2 = address;
    }
   
    @Override
    public String toString()
    {
        String object = "username: "+nom+" firstName: "+classe+" lastName: "+note1+" address: "+note2;
        return object;
    } 
}
