/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

/**
 *
 * @author Michelle Wafo
 */
public class model_eleves {
    
    
    private String nom,id;
    private String classe;
    private String note1;
    private String note2;
    private String periode;
    private String date;

    public model_eleves() {
    }

    public model_eleves(String id,String username, String firstName, String lastName, String address,String p,String d) {
        this.nom = username;
        this.classe= firstName;
        this.note1 = lastName;
        this.note2 = address;
        this.id = id;
        this.date=d;
        this.periode=p;
    }

    public String id() {
        return id;
    }

    public void id(String username) {
        this.id= username;
    }
    public String getUsername() {
        return nom;
    }
    public void periode(String username) {
        this.periode= username;
    }
    public String periode() {
        return periode;
    }public void date(String username) {
        this.date= username;
    }
    public String date() {
        return date;
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
