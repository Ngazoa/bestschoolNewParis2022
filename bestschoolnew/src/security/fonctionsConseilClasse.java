/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import etab.message;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.modelEleve;
import static security.fonctions.idAnneScolaire;
import static security.fonctions.idEtablissement;
/**
 *
 * @author ASSUS GAMER
 */
public class fonctionsConseilClasse {

    static Float admission, repeche, heures, exclusion, tableau_honneur;
    static int classeMigrate = 0;
    private final mysqli mysqli;
    Preferences prf = Preferences.userRoot().node(getClass().getName());

    public fonctionsConseilClasse() {
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

    public void getClassePourConseil(String classe, JTable jt) throws SQLException {

        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec where "
                + "ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve "
                + "and ec.id_classe=? and ec.id_annee=? order by nom_eleve";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();

        List<modelEleve> listeMatiere = new ArrayList<>();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                modelEleve matiere = new modelEleve();
                matiere.setidMatiere(rs.getString("id_eleve"));
                matiere.setnomMatiere(rs.getString("nom_eleve"));
                matiere.setredoublant(rs.getString("redoublant"));
                matiere.setprenom(rs.getString("prenom_eleve"));
                matiere.setmatricule(rs.getString("login"));
                listeMatiere.add(matiere);

            }
        }
        JComboBox ensei = getRangClasseSuperieur(classe);
        if (ensei != null) {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Redoublant", "Nom & Prenom",
                "matricule", "Classe si admission"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column < 4) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;
            for (final modelEleve detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getidClasse(),
                    detail.getNommatiere() + detail.getprenom(), detail.getredoublant(),
                    detail.getmatricule(), "veuillez selectionner"});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(70);
            jt.getColumnModel().getColumn(2).setMaxWidth(70);

            jt.getColumnModel().getColumn(4).setMaxWidth(110);
            TableColumn testColumn = jt.getColumnModel().getColumn(5);
            testColumn.setCellEditor(new DefaultCellEditor(ensei));
        }
    }

    JComboBox<Object> getRangClasseSuperieur(String classe) throws SQLException {
        String query = "select * from classe where rang=? and id_etab=? order by nom_classe";
        String rang = new fonctions().getrandClasse(classe);
        System.err.println(">>>>>>> "+rang);
        try{
        int rangsup = Integer.parseInt(rang) + 1;
        classeMigrate = rangsup;
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, "" + rangsup);
        ps.setString(2, idEtablissement);

        ResultSet rs = ps.executeQuery();
        JComboBox j = new JComboBox();
        if (rs.isBeforeFirst()) {

            while (rs.next()) {
                j.addItem(rs.getString("nom_classe"));
            }
            return j;
        } else {
            new message().warning("Impossible de continuer", "Impossible de"
                    + " continuer car\ncette salle de "
                    + "classe ne possede pas un niveau\nsuperieur..."
                    + " Merci de verifier dans\nles rangs de classe");
        }
         }catch(Exception ex){
            new message().info(ex.toString());
        }
        return null;

    }

    public boolean verifierSiBulletinAnnuelDisponible(String classe) throws SQLException {
        String query = "select * from stat_moyenne st inner join users u"
                + " inner join eleve e where st.id_classe=? and u.login=st.matricule"
                + " and u.id_eleve=e.id_eleve and id_periode='7' "
                + " and  id_annee=? order by e.nom_eleve";
        String rang = new fonctions().getIClasse(classe);

        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, rang);
        ps.setString(2, idAnneScolaire);

        ResultSet rs = ps.executeQuery();

        return rs.isBeforeFirst();
    }

    public void ApplicationDesMigrations(JTable jt, String classe) throws SQLException {
        int taille = jt.getRowCount();
        String matricule, id_eleve;
        String classedecision;
        classe = new fonctions().getIClasse(classe);
        float moy = 0;
        int test = 0;
        for (int i = 0; i < taille; i++) {
            System.out.println(">>> " + jt.getValueAt(i, 5).toString());
            if (!jt.getValueAt(i, 5).toString().equals("veuillez selectionner")) {
                test++;
            }
        }
        if (test == taille) {
            for (int i = 0; i < taille; i++) {
                matricule = jt.getValueAt(i, 4).toString();
                classedecision = jt.getValueAt(i, 5).toString();
                String rd = jt.getValueAt(i, 2).toString();
                id_eleve = jt.getValueAt(i, 1).toString();
                moy = getElevesMoyenne(matricule);
                System.err.println("moyenne " + moy + " admission " + admission);
                int d = decision(moy, rd);
                switch (d) {
                    case 200:
                        upgradeClasse(id_eleve, "" + classeMigrate, "non", "1");
                        break;
                    case 100:
                        upgradeClasse(id_eleve, "" + classeMigrate, "non", "1");
                        break;
                    case 500:
                        upgradeClasse(id_eleve, "" + classe, "oui", "1");
                        break;
                    case 01:
                        upgradeClasse(id_eleve, "" + classe, "oui", "0");
                       insertRedoublant(id_eleve, classe, "EXCLU pour ne peut tripler");

                        break;
                    default:
                        upgradeClasse(id_eleve, "" + classe, "oui", "0");
                        insertRedoublant(id_eleve, classe, "EXCLU pour travail insuffisant");
                        break;
                }
            }
            new message().info("Operation terminee !");
        } else {
            new message().error("erreur survenue", "Veuiller attribuer a chaque eleve \n"
                    + "une salle de classe si admission");
        }

    }

    public boolean getDecisionConseilClasse() throws SQLException {
        String query = "select * from detail_conseil where id_etab=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                exclusion = rs.getFloat("exclusion");
                heures = rs.getFloat("heure");
                admission = rs.getFloat("admission");
                repeche = rs.getFloat("redouble");
                tableau_honneur = rs.getFloat("tableau_honneur");
            }
            return true;
        } else {
            new message().warning("Conseil de classe invalide", "Veuillez au"
                    + "prealabe definir votre conseil de classe ");
        }
        return false;

    }

    private int decision(float moy, String rd) {
        if (moy > admission) {
            return 200;// admis en classe superieur
        } else if (moy < admission && moy > repeche) {
            return 100;// peut etre repeche
        } else if (moy < repeche && moy > exclusion && rd.equals("non")) {
            return 500;// redouble
        } else if (moy < repeche && moy > exclusion && rd.equals("oui")) {
            return 01;// exclu pour ne peut tripler
        } else {
            return 0;// exclu pour mauvais travail
        }
    }

    Float getElevesMoyenne(String matricule) throws SQLException {
        float moyenneleve = 0;
        String query = "select * from stat_moyenne where matricule=? and "
                + "id_annee=? and id_periode='7'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, matricule);
        ps.setString(2, idEtablissement);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                moyenneleve = rs.getFloat("moyenne");
            }
        }
        return moyenneleve;
    }

    void upgradeClasse(String eleve, String classe, String statut, String etat_ec) throws SQLException {
        String query = "update eleve_classe set id_classe=?,redoublant=?,"
                + "etat_ec=? where id_eleve=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, classe);
        ps.setString(2, statut);
        ps.setString(3, etat_ec);
        ps.setString(4, eleve);
        if (ps.executeUpdate() > 0) {

        }
    }
    void insertRedoublant(String eleve, String classe, String raison) throws SQLException {
        String query = "insert into eleves_exclu (id_eleve,id_classe"
                + ",id_annee,raison)values(?,?,?,?) ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, eleve);
        ps.setString(2, classe);
        ps.setString(4, raison);
        ps.setString(3, idAnneScolaire);
        if (ps.executeUpdate() > 0) {

        }
    }
}
