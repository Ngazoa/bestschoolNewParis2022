/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import etab.message;
import etablissement.memoire;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import static security.fonctions.idAnneScolaire;
import static security.fonctions.idEtablissement;

/**
 *
 * @author ASSUS GAMER
 */
public class fonctionStatistiques {

    mysqli mysqli;
    Preferences prf = Preferences.userRoot().node(getClass().getName());
    int tableauHonneur = 0;

    private final int garconAbsentMatiere = 0;
    private final int filleansenteMatiere = 0;
    private int garconAbsentCompo = 0;
    private int filleansenteCompo = 0;
    private final int garconpresentMatiere = 0;
    private final int fillepresenteMatiere = 0;
    private int garconpresentCompo = 0;
    private int fillePresenteCompo = 0;
    private int garconadmis = 0;
    private int filleadmis = 0;
    private String sexe;
    private final ArrayList matiere = new ArrayList();
    private final ArrayList discipline = new ArrayList();
    private final ArrayList note = new ArrayList();
    private final ArrayList sexeNote = new ArrayList();

    public fonctionStatistiques() {
        mysqli = new mysqli();
        if (mysqli.connect() == null) {
            new message().error("Erreur de connexion", "Impossible d'etablir la connexion avec\n le serveur distant");
            System.exit(0);
        }
//        prf.put("id_etab", "1");
//        prf.put("id_annee", "1");
//        //prf.put("id_etab", "1");
//        //pt.setProperty("id_etab", "1");
//        // pt.setProperty("id_annee", "1");
//        idEtablissement = prf.get("id_etab", null);
//        idAnneScolaire = prf.get("id_annee", null);
    }

    public void getStatsMoyennes(String classe, String periode) throws SQLException {
        memoire.classe = classe;
        String query = "select * from stat_moyenne sm inner join users u inner join "
                + " eleve e where sm.matricule=u.login and u.id_eleve=e.id_eleve "
                + "and sm.id_classe=? and id_periode=? and id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        ps.setString(2, "" + new fonctionsBulletins().getPeriodeSaveStats(periode));
        ps.setString(3, idAnneScolaire);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                String moyenne = rs.getString("moyenne");
                sexe = rs.getString("sexe_eleve");
                if (moyenne.equals("0.0")) {
                    if (sexe.equals("F")) {
                        filleansenteCompo++;
                    } else {
                        garconAbsentCompo++;
                    }
                } else {
                    if (sexe.equals("F")) {
                        fillePresenteCompo++;
                    } else {
                        garconpresentCompo++;
                    }
                    if (Float.parseFloat(moyenne) >= prf.getFloat("admission", 10)) {
                        if (sexe.equals("F")) {
                            filleadmis++;
                        } else {
                            garconadmis++;
                        }
                    }
                    if (Float.parseFloat(moyenne) >= prf.getFloat("tableau_honneur", 12)) {
                        tableauHonneur++;
                    }
                }
                MoyenneEleveParIntervalle(Float.parseFloat(moyenne));
            }
            memoire.nombre_fille_absente = filleansenteCompo;
            memoire.nombre_garcon_absent = garconAbsentCompo;
            memoire.nombre_garcons_admis = garconadmis;
            memoire.nombre_fille_admise = filleadmis;
            memoire.nombre_fille = fillePresenteCompo + filleansenteCompo;
            memoire.nombre_garcon = garconAbsentCompo + garconpresentCompo;
            memoire.mention10 = tableauHonneur;
        } else {
            new message().warning("Alert de rappel", "Attention les bulletins de cette classe \n"
                    + " De cette periode -" + periode + "- n'ont pas encore ete generes ou sont\nabsents");
        }
        getNotesElevesStatistiques(classe, periode);
        new fonctionsBulletins().getMoyenneDernier(classe, "" + new fonctionsBulletins().getPeriodeSaveStats(periode));
        new fonctionsBulletins().getMoyennePremier(classe, "" + new fonctionsBulletins().getPeriodeSaveStats(periode));
        new fonctionsBulletins().getMoyenneGenerale(classe, "" + new fonctionsBulletins().getPeriodeSaveStats(periode));

    }

    public void getStatsMoyennesglobal(String periode, String etab) throws SQLException {
        memoire.classe = etab;
        String query = "select * from stat_moyenne sm inner join users u inner join "
                + " eleve e where sm.matricule=u.login and u.id_eleve=e.id_eleve "
                + "and u.id_etab=? and sm.id_periode=? and sm.id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, "" + new fonctionsBulletins().getPeriodeSaveStats(periode));
        ps.setString(3, idAnneScolaire);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                String moyenne = rs.getString("moyenne");
                sexe = rs.getString("sexe_eleve");
                if (moyenne.equals("0.0")) {
                    if (sexe.equals("F")) {
                        filleansenteCompo++;
                    } else {
                        garconAbsentCompo++;
                    }
                } else {
                    if (sexe.equals("F")) {
                        fillePresenteCompo++;
                    } else {
                        garconpresentCompo++;
                    }
                    if (Float.parseFloat(moyenne) >= prf.getFloat("admission", 10)) {
                        if (sexe.equals("F")) {
                            filleadmis++;
                        } else {
                            garconadmis++;
                        }
                    }
                    if (Float.parseFloat(moyenne) >= prf.getFloat("tableau_honneur", 12)) {
                        tableauHonneur++;
                    }
                }
                MoyenneEleveParIntervalle(Float.parseFloat(moyenne));
            }
            memoire.nombre_fille_absente = filleansenteCompo;
            memoire.nombre_garcon_absent = garconAbsentCompo;
            memoire.nombre_garcons_admis = garconadmis;
            memoire.nombre_fille_admise = filleadmis;
            memoire.nombre_fille = fillePresenteCompo + filleansenteCompo;
            memoire.nombre_garcon = garconAbsentCompo + garconpresentCompo;
            memoire.mention10 = tableauHonneur;

        } else {
            new message().warning("Alert de rappel", "Attention les bulletins de cette etablissement \n"
                    + " De cette periode -" + periode + "- n'ont pas encore ete generes \nou sont absents");
        }
        getNotesElevesStatistiquesGlogable(periode);
        new fonctionsBulletins().getMoyenneDernierG("" + new fonctionsBulletins().getPeriodeSaveStats(periode));
        new fonctionsBulletins().getMoyennePremierG("" + new fonctionsBulletins().getPeriodeSaveStats(periode));
        new fonctionsBulletins().getMoyenneGeneraleG("" + new fonctionsBulletins().getPeriodeSaveStats(periode));

    }

    private void MoyenneEleveParIntervalle(Float moyen_eleve) {
        if (moyen_eleve >= 0 && moyen_eleve < 4) {
            memoire.mention1++;

        } else if (moyen_eleve >= 4 && moyen_eleve < 7.5) {
            memoire.mention2++;

        } else if (moyen_eleve >= 7.5 && moyen_eleve < 10) {
            memoire.mention3++;
        } else if (moyen_eleve >= 10 && moyen_eleve < 12) {
            memoire.mention4++;

        } else if (moyen_eleve >= 12 && moyen_eleve < 14) {
            memoire.mention5++;

        } else if (moyen_eleve >= 14 && moyen_eleve < 16) {
            memoire.mention6++;

        } else if (moyen_eleve >= 16 && moyen_eleve < 18) {
            memoire.mention7++;

        } else if (moyen_eleve >= 18 && moyen_eleve < 20) {
            memoire.mention8++;

        } else if (moyen_eleve == 20) {
            memoire.mention9++;

        }

    }

    public void getNotesElevesStatistiquesGlogable(
            String period) throws SQLException {
        String query = "select distinct nom_matiere from matiere_classe_enseignant mc "
                + "inner join matiere m inner join enseignant e inner join users u"
                + " where id_annee=? "
                + "and m.id_matiere=mc.id_matiere and mc.id_enseignant=e.id_enseignant "
                + "and e.id_enseignant=u.id_enseignant "
                + "and mc.etat_mc='1' and u.id_etab=? order by m.nom_matiere";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                String matierem = rs.getString("nom_matiere");
                matiere.add(matierem);
            }
            memoire.matieres = matiere;
        }
        boolean siNotedeja = GetAllNotesElevesParPeriodeGlobal(period);
        if (!siNotedeja) {
            new message().warning("A votre attention", ""
                    + "Il se pourrait que cette etablissement n'aie pas\nde notes ou d'eleves");
        }
    }

    public void getNotesElevesStatistiques(String classe,
            String period) throws SQLException {
        String query = "select * from matiere_classe_enseignant mc "
                + "inner join matiere m inner join enseignant e inner join users u"
                + " where id_annee=? "
                + "and m.id_matiere=mc.id_matiere and mc.id_enseignant=e.id_enseignant "
                + "and e.id_enseignant=u.id_enseignant "
                + "and mc.etat_mc='1' and mc.id_classe=? order by m.nom_matiere";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, new fonctions().getIClasse(classe));

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                String matierem = rs.getString("nom_matiere");
                matiere.add(matierem);
            }
            memoire.matieres = matiere;
        }
        boolean siNotedeja = GetAllNotesElevesParPeriode(period, classe);
        if (!siNotedeja) {
            new message().warning("A votre attention", ""
                    + "Il se pourrait que cette classe n'aie pas\nde notes ou d'eleves");
        }
    }

    private boolean GetAllNotesElevesParPeriode(String periode, String classe)
            throws SQLException {
        String query = "select * from note n inner join matiere_classe_enseignant"
                + " ec inner join eleve e inner join matiere m where "
                + "ec.id_matiere=m.id_matiere and ec.id_m_c=n.id_m_c_e and "
                + "ec.id_classe=? and n.id_eleve=e.id_eleve "
                + "and n.id_periode=? "
                + "and ec.id_annee=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        ps.setString(2, "" + new fonctionsBulletins().getPeriode(periode));
        ps.setString(3, idAnneScolaire);
        System.err.println("et      >>>>>" + ps);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {

                note.add(rs.getString("note"));
                sexeNote.add(rs.getString("sexe_eleve").toUpperCase());
                discipline.add(rs.getString("nom_matiere"));
            }
            memoire.note = note;
            memoire.sex = sexeNote;
            memoire.disciplines = discipline;
            mysqli.close();
            return true;
        }

        rs.close();
        mysqli.close();
        return false;
    }

    private boolean GetAllNotesElevesParPeriodeGlobal(String periode)
            throws SQLException {
        boolean ee = false;
        String query = "select * from note n inner join matiere_classe_enseignant"
                + " ec inner join eleve e inner join users u inner join matiere m where "
                + "ec.id_matiere=m.id_matiere and ec.id_m_c=n.id_m_c_e and "
                + "u.id_etab=? and n.id_eleve=e.id_eleve and u.id_eleve=e.id_eleve "
                + "and n.id_periode=? "
                + "and ec.id_annee=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, "" + new fonctionsBulletins().getPeriode(periode));
        ps.setString(3, idAnneScolaire);
        System.err.println("et      >>>>>" + ps);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {

                note.add(rs.getString("note"));
                sexeNote.add(rs.getString("sexe_eleve").toUpperCase());
                discipline.add(rs.getString("nom_matiere"));
            }
            memoire.note = note;
            memoire.sex = sexeNote;
            memoire.disciplines = discipline;
            mysqli.close();
            ee = true;
        }

        rs.close();
        mysqli.close();
        return ee;
    }

}
