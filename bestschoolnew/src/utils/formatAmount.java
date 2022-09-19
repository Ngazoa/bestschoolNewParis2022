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
public class formatAmount {

    public String montantFormatte(String montant) {
        try {
            float mont = Float.parseFloat(montant);
            return String.format("%,.2f", mont);
        } catch (NumberFormatException ex) {
            return montant;
        }
    }
}
