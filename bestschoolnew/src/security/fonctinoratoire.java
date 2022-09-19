package security;

import etab.message;
import etablissement.memoire;
import org.json.JSONException;
import paie.personnel.sha1;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.prefs.Preferences;
import static security.fonctions.idAnneScolaire;

public class fonctinoratoire {

    private final mysqli mysqli;
    Preferences prf = Preferences.userRoot().node(getClass().getName());

    public fonctinoratoire() {
        mysqli = new mysqli();
        if (mysqli.connect() == null) {
            new message().error("Erreur de connexion", "Impossible d'etablir la connexion avec\n le serveur distant");
            System.exit(0);
        }

    }

    public boolean checkMoratoireEleveEXists( String tranche) throws SQLException {
        String query = "select * from moratoire where id_eleve=? and id_annee=? and id_tranche=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, memoire.id_eleve);
        ps.setString(2, idAnneScolaire);
        ps.setString(3, tranche);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
             return true; 
        }
        return false;
    }

    public void PayerMoratoire(JTable j, String nomTranche, float motant, String tranche, float montanttranche) throws SQLException, JSONException, NoSuchAlgorithmException, IOException {
        String query = "select * from moratoire where id_eleve=? and id_annee=? and id_tranche=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, memoire.id_eleve);
        ps.setString(2, idAnneScolaire);
        ps.setString(3, tranche);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                if (rs.getFloat("montant_restant") <= motant) {

                    //if((rs.getFloat("montant_restant")-motant)<(-30)){
                    new fonctions().validerPaiementTranche("moratoitre " + nomTranche, memoire.id_eleve, tranche, memoire.classe, null);
                    new fonctions().getEleveTranchesPaiement(j, memoire.classe, memoire.id_eleve);

                    editionmontantApayer(true, rs.getString("id"), rs.getFloat("montant_restant") - motant,
                             rs.getFloat("montant") + motant);
                } else {
                    editionmontantApayer(false, rs.getString("id"), rs.getFloat("montant_restant") - motant,
                            rs.getFloat("montant") + motant);
//
//                new message().warning("Erreur de montant","Le montant saisie est superieure de "
//                +(rs.getFloat("montant_restant")-motant)+ " au montant restant a payer");
                }
            }
        } else {
            addNewMoratoire(memoire.id_eleve, tranche, motant, (montanttranche - motant));
        }

    }

    public int editionmontantApayer(boolean is_finish, String id, float montant, float NewMontant) throws SQLException {
        String sqlInsert = "update moratoire set montant_restant=?,montant=? , is_finish=? where id=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setFloat(1, montant);
        ps.setString(4, id);
        ps.setFloat(2, NewMontant);
        ps.setBoolean(3, is_finish);
        System.out.println("<<<<<<<<<<<" + ps);
        int p = ps.executeUpdate();
        if (p == 1) {
            new message().info("Moratoire Mis a jour avec succes");

        } else {
            new message().error("erreur", "Erreur Iconnue survenue !");
        }
        mysqli.close();
        return p;
    }

    public int addNewMoratoire(String ideleve, String idtranche,
            float motant, float montantRestant) throws SQLException,
            NoSuchAlgorithmException, IOException, JSONException {
        System.out.println("??????????? " + idtranche);
        Date date = new Date();
        java.sql.Date sqldte = new java.sql.Date(date.getTime());
        String sqlInsert = "insert into moratoire (id_annee, id_eleve, id_tranche,"
                + "montant,montant_restant,date_creation_m) "
                + "values (?,?,?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, ideleve);
        ps.setString(3, idtranche);
        ps.setFloat(4, motant);
        ps.setDate(6, sqldte);
        ps.setFloat(5, montantRestant);

        int countInserted = ps.executeUpdate();
        if (countInserted == 1) {
            ResultSet rs = ps.getGeneratedKeys();
            new message().info("Moratoire cree avec succes");
        }
        return countInserted;

    }

}
