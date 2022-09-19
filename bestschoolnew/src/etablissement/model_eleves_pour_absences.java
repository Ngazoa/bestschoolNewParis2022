/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

/**
 *
 * @author Benito
 */
public class model_eleves_pour_absences {
    
 private String nom,id;
    private String classe;
    private String note1;
    private String note2;
    private String djajustifie;

    public model_eleves_pour_absences() {
    }

    public model_eleves_pour_absences(String id,String username, String firstName, String lastName, String address,String dja) {
        this.nom = username;
        this.classe= firstName;
        this.djajustifie=dja;
        this.note1 = lastName;
        this.note2 = address;
        this.id = id;
    }

    public String id() {
        return id;
    }

    public void djajustifie(String username) {
        this.djajustifie= username;
    }public String djajustifie() {
        return djajustifie;
    }

    public void id(String username) {
        this.id= username;
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
