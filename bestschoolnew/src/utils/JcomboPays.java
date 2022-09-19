/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author ASSUS GAMER
 */
public class JcomboPays {

    String nom;
    int id;

    public JcomboPays(String name, int id) {
        this.id = id;
        this.nom = name;
    }

    public String toString() {
        return nom + " " + id;
    }

}
