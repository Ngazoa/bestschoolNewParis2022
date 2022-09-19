/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import com.pack.entity.Entete;
import com.pack.entity.InfoMatiere;
import com.pack.entity.Profile;

import com.pack.report.Report;
import com.pack.report.ReportsCards;
import etab.message;
import etablissement.impressionmodel_11;
import etablissement.memoire;
import impressionlycee.Impression;
import impressionlycee.moyenG;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.prefs.Preferences;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import model.modelEleve;
import static security.fonctions.idAnneScolaire;
import static security.fonctions.idEtablissement;

/**
 *
 * @author ASSUS GAMER
 */
public class fonctionsBulletins {

    public static TreeMap<String, String> coeffMapp = new TreeMap<>();
    public static TreeMap<String, String> groupMapp = new TreeMap<>();
    public static TreeMap<String, String> enseignantMap = new TreeMap<>();
    public static TreeMap<Integer, String> matiereMap = new TreeMap<>();
    public static TreeMap<Integer, String> ClasseMap = new TreeMap<>();
    public static TreeMap<Integer, String> eleveMap = new TreeMap<>();
    mysqli mysqli;

    // String[] avatar =new String[250];
    // Preferences prf = Preferences.userRoot().node(getClass().getName());
    Preferences prf = Preferences.userNodeForPackage(fonctions.class);

    //Preferences prf1 = Preferences.userRoot().node(getClass().getName());
// Preferences pref= Preferences.userNodeForPackage(null);
    // Properties pt = new Properties();
    //String idEtablissement, idAnneScolaire;
    private static String[] groupeclasse;
    private static String[] coeffclasse;
    private static String[] enseignantclasse;
    private static String[] avatar;
    private static String[] redoublantTab;
    private static String[] matiereclasse;
    private static float tableauMoyenne;

    public fonctionsBulletins() {
        mysqli = new mysqli();
        if (mysqli.connect() == null) {
            new message().error("Erreur de connexion", "Impossible d'etablir la connexion avec\n le serveur distant");
            System.exit(0);
        }
        tableauMoyenne=prf.getFloat("tableau_honneur", 12);

    }

    public void getElevesClassePourSaisieNotes(JTable jt, String classe,
            String mat, String periode) throws SQLException {
        String idclasse1 = new fonctions().getIClasse(classe);
        String idmatiere = new fonctions().getIdmatier(mat);
        int idperiode = getPeriode(periode);
        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec where ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve "
                + "and ec.id_classe=? and ec.id_annee=? order by nom_eleve,prenom_eleve,date_naissance";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idclasse1);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        int effec = 0;
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve matiere = new modelEleve();
                matiere.setidMatiere(rs.getString("id_eleve"));
                matiere.setnomMatiere(rs.getString("nom_eleve"));
                matiere.setprenom(rs.getString("prenom_eleve"));
                matiere.setdate(rs.getDate("date_naissance"));
                matiere.setmatricule(rs.getString("login"));
                matiere.setsexe(rs.getString("sexe_eleve"));
                eleveMap.put(rs.getInt("id_eleve"), rs.getString("avatar"));
                matiere.setnote(checkNote("" + idperiode, idmatiere, idclasse1, rs.getString("id_eleve")));
                listeMatiere.add(matiere);
                effec++;
            }
            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom ", "Prenom", "Sexe", "Date Na.",
                "matricule", "Note"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column != 7) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;
            for (final modelEleve detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getprenom(), detail.getsexe(),
                    detail.getDate(), detail.getmatricule(), detail.getNote()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(40);
            jt.getColumnModel().getColumn(5).setMaxWidth(120);
            jt.getColumnModel().getColumn(6).setMaxWidth(100);
            jt.getColumnModel().getColumn(4).setMaxWidth(60);
            jt.getColumnModel().getColumn(2).setMaxWidth(160);
            jt.getColumnModel().getColumn(3).setMaxWidth(160);
        }
        mysqli.close();

    }

    public void saveNoteEleve(JTable tableau_eleves, String classe,
            String matiere, String periode) throws SQLException {
        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String id_eleve;
        int idperiod = getPeriode(periode);
        String idmat = new fonctions().getIdmatier(matiere);
        String idclasse = new fonctions().getIClasse(classe);

        String code = getCodeMatere(idmat, idclasse);

        String note, nom;
        int test = 0, naneror = 0;
        for (int i = 0; i < count; i++) {
            id_eleve = tableau_eleves.getValueAt(i, 1).toString();
            note = (tableau_eleves.getValueAt(i, 7).toString()).replace(",", ".");
            if (!note.isEmpty()) {

                System.err.println("<<<<<" + code);
                try {
                    if (Float.parseFloat(note) <= 20 && Float.parseFloat(note) >= 00) {
                        String check = checkNoteBeforeInset("" + idperiod, idmat, idclasse, id_eleve);
                        if ("".equals(check)) {
                            InsertNoteEleve(code, id_eleve, note, "" + idperiod);
                        } else {
                            updateNoteEleve(code, id_eleve, note);
                        }
                        naneror++;
                    } else {
                        nom = tableau_eleves.getValueAt(i, 2).toString() + " " + tableau_eleves.getValueAt(i, 3).toString();
                        new message().error("Erreur de saisie", "Un élève au nom de:   ||  " + nom + "  ||  se \nretrouve avec une note \nsuperieure  a 20 ou infeieure a 0");

                    }
                } catch (NumberFormatException | SQLException ex) {
                    System.err.println("error>>>>" + ex);
                    nom = tableau_eleves.getValueAt(i, 2).toString() + " " + tableau_eleves.getValueAt(i, 3).toString();
                    new message().error("Erreur de saisie", "un élève au nom de:|| " + nom + " || se retrouve \navec une note pas normale \nBien vouloir verifier sa note ");
              // tableau_eleves.n
                }
                test++;
            }
        }
        if (naneror == test) {
            new message().info("Enregistrement reussie");
            new fonctionsBulletins().getElevesClassePourSaisieNotes(tableau_eleves, classe,
                    matiere, periode);

        }
        mysqli.close();
    }

    private void updateNoteEleve(String code, String id_eleve, String note) throws SQLException {
        String sqlInsert = "update note set note=? where  id_eleve=? and id_m_c_e=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, note);
        ps.setString(2, id_eleve);
        ps.setString(3, code);
        System.out.println("update >>>>>>>>>" + ps);
        ps.executeUpdate();

    }

    private String getCodeMatere(String mat, String classe) throws SQLException {
        String code = null;
        String query = "select * from matiere_classe_enseignant where id_classe=?"
                + " and  id_matiere=? and id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);

        ps.setString(1, classe);
        ps.setString(2, mat);
        ps.setString(3, idAnneScolaire);
        System.out.println("llllLLLL" + ps);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                code = rs.getString("id_m_c");
            }
        }
        return code;
    }

    private void InsertNoteEleve(String code, String id_eleve, String note, String periode) throws SQLException {
        String sqlInsert = "insert into note(id_eleve,id_m_c_e,note,id_periode)values(?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, id_eleve);
        ps.setString(2, code);
        ps.setString(3, note);
        ps.setString(4, periode);
        System.out.println("<<<<<<<<<<<<<<<<< ins " + ps);
        ps.executeUpdate();

    }

    private String checkNoteBeforeInset(String periode, String mat, String classe, String eleve) throws SQLException {
        String note = "";
        String query = "select * from note n inner join matiere_classe_enseignant"
                + " ec inner join eleve e where ec.id_m_c=n.id_m_c_e and "
                + "ec.id_classe=? and n.id_eleve=e.id_eleve and ec.id_matiere=? "
                + "and n.id_periode=? and n.id_eleve=? and ec.id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, classe);
        ps.setString(2, mat);
        ps.setString(3, periode);
        ps.setString(4, eleve);
        ps.setString(5, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                note = rs.getString("id_m_c_e");
            }
        }
        return note;
    }

    private String checkNote(String periode, String mat, String classe, String eleve) throws SQLException {
        String note = "";
        String query = "select * from note n inner join matiere_classe_enseignant"
                + " ec inner join eleve e where ec.id_m_c=n.id_m_c_e and "
                + "ec.id_classe=? and n.id_eleve=e.id_eleve and ec.id_matiere=? "
                + "and n.id_periode=? and n.id_eleve=? and ec.id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, classe);
        ps.setString(2, mat);
        ps.setString(3, periode);
        ps.setString(4, eleve);
        ps.setString(5, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                note = rs.getString("note");
                System.out.println("requete>>>>>>>>>>>>>>>" + note);

            }
        }
        return note;
    }

    private String checkNote2(String periode, String mat, String classe, String eleve) throws SQLException {
        String note = null;
        String query = "select * from note n inner join matiere_classe_enseignant"
                + " ec inner join eleve e where ec.id_m_c=n.id_m_c_e and "
                + "ec.id_classe=? and n.id_eleve=e.id_eleve "
                + "and n.id_periode=? and n.id_eleve=? and ec.id_annee=? and ec.id_matiere=? order by id_matiere";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, classe);
        ps.setString(2, periode);
        ps.setString(3, eleve);
        ps.setString(4, idAnneScolaire);
        ps.setString(5, mat);
        System.err.println("et      >>>>>" + ps);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                if (!rs.getString("note").isEmpty() && rs.getString("note").length() >= 1) {
                    note = rs.getString("note");

                } else {
                    note = "//";
                }
                System.out.println("requete>>>>>>>>>>>>>>>" + note);

            }
            mysqli.close();

        } else {
            note = "//";
        }
        rs.close();
        mysqli.close();
        return note;
    }

    public int getPeriodeSaveStats(String nomPeriode) {
        int rst;
        if (null == nomPeriode) {
            rst = 7;
        } else {
            switch (nomPeriode) {
                case "sequence 1":
                    rst = 1;
                    break;
                case "sequence 2":
                    rst = 2;
                    break;
                case "sequence 3":
                    rst = 3;
                    break;
                case "sequence 4":
                    rst = 4;
                    break;
                case "sequence 5":
                    rst = 5;
                    break;
                case "sequence 6":
                    rst = 6;
                    break;
                case "trimestre 1":
                    rst = 2;
                    break;
                case "trimestre 2":
                    rst = 4;
                    break;
                case "trimestre 3":
                    rst = 6;
                    break;
                default:
                    rst = 7;
                    break;
            }
        }
        return rst;
    }

    public int getPeriode(String nomPeriode) {
        int rst;
        if (null == nomPeriode) {
            rst = 7;
        } else {
            switch (nomPeriode) {
                case "sequence 1":
                    rst = 1;
                    break;
                case "sequence 2":
                    rst = 2;
                    break;
                case "sequence 3":
                    rst = 3;
                    break;
                case "sequence 4":
                    rst = 4;
                    break;
                case "sequence 5":
                    rst = 5;
                    break;
                case "sequence 6":
                    rst = 6;
                    break;
                case "trimestre 1":
                    rst = 1;
                    break;
                case "trimestre 2":
                    rst = 2;
                    break;
                case "trimestre 3":
                    rst = 3;
                    break;
                default:
                    rst = 7;
                    break;
            }
        }
        return rst;
    }

    public String getNomImpresionPeriode(String nomPeriode) {
        String rst;
        if (null == nomPeriode) {
            rst = "annuel";
        } else {
            switch (nomPeriode) {
                case "sequence 1":
                    rst = "sequence1";
                    break;
                case "sequence 2":
                    rst = "sequence2";
                    break;
                case "sequence 3":
                    rst = "sequence3";
                    break;
                case "sequence 4":
                    rst = "sequence4";
                    break;
                case "sequence 5":
                    rst = "sequence5";
                    break;
                case "sequence 6":
                    rst = "sequence6";
                    break;
                case "trimestre 1":
                    rst = "trimestre1";
                    break;
                case "trimestre 2":
                    rst = "trimestre2";
                    break;
                case "trimestre 3":
                    rst = "trimestre3";
                    break;
                default:
                    rst = "annuel";
                    break;
            }
        }
        return rst;
    }

    public void getNotesElevesPourBulletins(JTable jt, String classe,
            String period) throws SQLException {
        String idclasse1 = new fonctions().getIClasse(classe);
        int idperiode = getPeriode(period);

        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec where ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve "
                + "and ec.id_classe=? and ec.id_annee=? order by nom_eleve asc";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idclasse1);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        int avt = 0;
        String[] result = getmatiereClasse(idclasse1);
        if (result.length > 0) {
            if (rs.isBeforeFirst()) {
                int taille = 0;
                rs.last();
                taille = rs.getRow();
                rs.beforeFirst();
                avatar = new String[taille];
                redoublantTab = new String[taille];

                List<modelEleve> listeMatiere = new ArrayList<>();
                while (rs.next()) {
                    modelEleve matiere = new modelEleve();
                    matiere.setidMatiere(rs.getString("id_eleve"));
                    matiere.setnomMatiere(rs.getString("nom_eleve"));
                    matiere.setprenom(rs.getString("prenom_eleve"));
                    matiere.setdate(rs.getDate("date_naissance"));
                    matiere.setglieu(rs.getString("lieu"));
                    matiere.setmatricule(rs.getString("login"));
                    matiere.setsexe(rs.getString("sexe_eleve"));
                    avatar[avt] = rs.getString("e.avatar");
                    redoublantTab[avt] = rs.getString("redoublant");
                    String[] note = new String[result.length];
                    String[] note2 = new String[result.length];
                    avt++;
                    int idp = idperiode + 1;
                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idperiode + "----" + new fonctions().getIdmatier(result[h]));
                        note[h] = checkNote2("" + idperiode,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    matiere.setlisteNotes(note);
                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idp + "----" + new fonctions().getIdmatier(result[h]));
                        note2[h] = checkNote2("" + idp,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    matiere.setlisteNotes2(note2);
                    listeMatiere.add(matiere);
                }
                int longueur = 3 * result.length + 4 + 9;
                int classement1 = 9, classement2 = 10 + result.length, classemntrang = 11 + 2 * result.length;
                String[] entete = new String[longueur];
                entete[0] = "N";
                entete[1] = "Tt select";
                entete[2] = "Nom";
                entete[3] = "Prenom";
                entete[4] = "Sexe";
                entete[5] = "Date Na.";
                entete[6] = "lieu";
                entete[7] = "Matricule";
                entete[8] = " ";

                for (int k = 0; k < result.length; k++) {
                    entete[k + classement1] = "N" + idperiode + "-" + result[k];

                }
                entete[9 + result.length] = "";
                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classement2] = "N" + (idperiode + 1) + "-" + result[k];

                }
                entete[10 + 2 * result.length] = "";

                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classemntrang] = "Rang-" + result[k];

                }
                DefaultTableModel model = new DefaultTableModel(entete, 0) {
                    @Override
                    public Class getColumnClass(int columnIndex) {
                        if (columnIndex == 1) {
                            return Boolean.class;
                        }
                        return super.getColumnClass(columnIndex);
                    }

                    public boolean isCellEditable(int row, int column) {
                        boolean test = true;
                        if (column < 6 && column != 1) {
                            test = false;
                            return test;
                        }
                        return test;
                    }
                };
                int j = 1;
                for (final modelEleve detail : listeMatiere) {
                    model.addRow(new Object[]{j, false, detail.getNommatiere(), detail.getprenom(), detail.getsexe(),
                        detail.getDate(), detail.getLieu(), detail.getmatricule(), "------"});
                    j++;
                }
                //jt.getTableHeader().setPreferredSize(new Dimension(150, 60));

                jt.getColumnModel().getColumn(1).setHeaderRenderer(new HeaderRenderer2(jt.getTableHeader()));

                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(0).setResizable(false);
                jt.getColumnModel().getColumn(4).setMaxWidth(60);
                jt.getColumnModel().getColumn(5).setMaxWidth(80);
                addNoteToJtable(listeMatiere, model, classement1, classement2);
                RangPositionnement(jt, model, classement1,
                        classement2, result.length, classemntrang);
            } else {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom ", "Prenom", "Sexe", "Date Na.",
                    "matricule", "Note"}, 0) {
                    @Override
                    public Class getColumnClass(int columnIndex) {
                        if (columnIndex == 1) {
                            return Boolean.class;
                        }
                        return super.getColumnClass(columnIndex);
                    }

                    public boolean isCellEditable(int row, int column) {
                        boolean test = true;
                        if (column < 6 && column != 1) {
                            test = false;
                            return test;
                        }
                        return test;
                    }
                };
                jt.setModel(model);
            }
        } else {
            new message().warning("Alert", "Vous n'avez pas encore definis"
                    + " \ndes matieres pour cette annee \ndans cette classe");
        }
    }

    public void getNotesElevesPourBulletinsAnnuel(JTable jt, String classe,
            String period) throws SQLException {
        String idclasse1 = new fonctions().getIClasse(classe);
        int idperiode = getPeriode(period);

        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec where ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve "
                + "and ec.id_classe=? and ec.id_annee=? order by nom_eleve asc";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idclasse1);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        String[] result = getmatiereClasse(idclasse1);
        int avt = 0;
        //String[] result = getmatiereClasse(idclasse1);
        if (result.length > 0) {
            if (rs.isBeforeFirst()) {
                int taille = 0;
                rs.last();
                taille = rs.getRow();
                rs.beforeFirst();
                avatar = new String[taille];
                redoublantTab = new String[taille];
                List<modelEleve> listeMatiere = new ArrayList<>();
                while (rs.next()) {
                    modelEleve matiere = new modelEleve();
                    matiere.setidMatiere(rs.getString("id_eleve"));
                    matiere.setnomMatiere(rs.getString("nom_eleve"));
                    matiere.setprenom(rs.getString("prenom_eleve"));
                    matiere.setdate(rs.getDate("date_naissance"));
                    matiere.setglieu(rs.getString("lieu"));
                    matiere.setmatricule(rs.getString("login"));
                    matiere.setsexe(rs.getString("sexe_eleve"));
                    redoublantTab[avt] = rs.getString("redoublant");
                    avatar[avt] = rs.getString("e.avatar");
                    avt++;
                    String[] note = new String[result.length];
                    String[] note2 = new String[result.length];
                    String[] note3 = new String[result.length];
                    String[] note4 = new String[result.length];
                    String[] note5 = new String[result.length];
                    String[] note6 = new String[result.length];
                    int idp = idperiode + 1;
                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idperiode + "----" + new fonctions().getIdmatier(result[h]));
                        note[h] = checkNote2("" + idperiode,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    matiere.setlisteNotes(note);
                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idp + "----" + new fonctions().getIdmatier(result[h]));
                        note2[h] = checkNote2("" + idp,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    //
                    matiere.setlisteNotes2(note2);

                    idp++;
                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idp + "----" + new fonctions().getIdmatier(result[h]));
                        note3[h] = checkNote2("" + idp,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    idp++;
                    matiere.setlisteNotes3(note3);

                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idp + "----" + new fonctions().getIdmatier(result[h]));
                        note4[h] = checkNote2("" + idp,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    idp++;
                    matiere.setlisteNotes4(note4);
                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idp + "----" + new fonctions().getIdmatier(result[h]));
                        note5[h] = checkNote2("" + idp,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    idp++;
                    matiere.setlisteNotes5(note5);
                    for (int h = 0; h < result.length; h++) {
                        System.err.println(idp + "----" + new fonctions().getIdmatier(result[h]));
                        note6[h] = checkNote2("" + idp,
                                new fonctions().getIdmatier(result[h]),
                                idclasse1,
                                rs.getString("id_eleve"));
                    }
                    matiere.setlisteNotes6(note6);

                    listeMatiere.add(matiere);
                }
                int longueur = 15 + 7 * result.length + 4;
                int classement1 = 9, classement2 = 10 + result.length,
                        classement3 = 11 + 2 * result.length,
                        classement4 = 12 + 3 * result.length,
                        classement5 = 13 + 4 * result.length,
                        classement6 = 14 + 5 * result.length,
                        classemntrang = 15 + 6 * result.length;
                String[] entete = new String[longueur];
                entete[0] = "N";
                entete[1] = "Tt select";
                entete[2] = "Nom";
                entete[3] = "Prenom";
                entete[4] = "Sexe";
                entete[5] = "Date Na.";
                entete[6] = "lieu";
                entete[7] = "Matricule";
                entete[8] = " ";

                for (int k = 0; k < result.length; k++) {
                    entete[k + classement1] = "N" + idperiode + "-" + result[k];

                }
                entete[classement2 - 1] = "";
                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classement2] = "N" + (idperiode + 1) + "-" + result[k];
                }

                entete[classement3 - 1] = "";
                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classement3] = "N" + (idperiode + 2) + "-" + result[k];
                }
                entete[classement4 - 1] = "";
                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classement4] = "N" + (idperiode + 3) + "-" + result[k];
                }
                entete[classement5 - 1] = "";
                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classement5] = "N" + (idperiode + 4) + "-" + result[k];
                }
                entete[classement6 - 1] = "";
                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classement6] = "N" + (idperiode + 5) + "-" + result[k];
                }
                entete[classemntrang - 1] = "";

                for (int k = 0; k < result.length; k++) {
                    System.out.println("<<<>>>>" + result[k]);
                    entete[k + classemntrang] = "Rang-" + result[k];

                }
                DefaultTableModel model = new DefaultTableModel(entete, 0) {
                    @Override
                    public Class getColumnClass(int columnIndex) {
                        if (columnIndex == 1) {
                            return Boolean.class;
                        }
                        return super.getColumnClass(columnIndex);
                    }

                    public boolean isCellEditable(int row, int column) {
                        boolean test = true;
                        if (column < 6 && column != 1) {
                            test = false;
                            return test;
                        }
                        return test;
                    }
                };
                int j = 1;
                for (final modelEleve detail : listeMatiere) {
                    model.addRow(new Object[]{j, false, detail.getNommatiere(), detail.getprenom(), detail.getsexe(),
                        detail.getDate(), detail.getLieu(), detail.getmatricule(), "------"});
                    j++;
                }
                //jt.getTableHeader().setPreferredSize(new Dimension(150, 60));

                jt.getColumnModel().getColumn(1).setHeaderRenderer(new HeaderRenderer2(jt.getTableHeader()));

                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(0).setResizable(false);
                jt.getColumnModel().getColumn(4).setMaxWidth(60);
                jt.getColumnModel().getColumn(5).setMaxWidth(80);
                addNoteToJtableAnnuel(listeMatiere, model, classement1, classement2,
                        classement3, classement4, classement5, classement6);
                RangPositionnementAnnuel(jt, model, classement1,
                        classement2, classement3, classement4, classement5, classement6, result.length, classemntrang);
            } else {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom ", "Prenom", "Sexe", "Date Na.",
                    "matricule", "Note"}, 0) {
                    @Override
                    public Class getColumnClass(int columnIndex) {
                        if (columnIndex == 1) {
                            return Boolean.class;
                        }
                        return super.getColumnClass(columnIndex);
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        boolean test = true;
                        if (column < 6 && column != 1) {
                            test = false;
                            return test;
                        }
                        return test;
                    }
                };
                jt.setModel(model);
            }
            rs.close();
        } else {
            new message().warning("Alert", "Vous n'avez pas encore definis"
                    + " \ndes matieres pour cette annee \ndans cette classe");
        }
        mysqli.close();
    }

    private void addNoteToJtableAnnuel(List<modelEleve> listeMatiere, DefaultTableModel model,
            int c1, int c2, int c3, int c4, int c5, int c6) {
        int eleve = 0;
        for (final modelEleve detail : listeMatiere) {
            int db = c1;
            int db3 = c3;
            int db4 = c4;
            int db5 = c5;
            int db6 = c6;
            int db2 = c2;
            for (int i = 0; i < detail.getlisteNoteseleves().length; i++) {
                model.setValueAt(detail.getlisteNoteseleves()[i], eleve, db);
                model.setValueAt(detail.getlisteNoteseleves3()[i], eleve, db3);
                model.setValueAt(detail.getlisteNoteseleves4()[i], eleve, db4);
                model.setValueAt(detail.getlisteNoteseleves5()[i], eleve, db5);
                model.setValueAt(detail.getlisteNoteseleves6()[i], eleve, db6);
                model.setValueAt(detail.getlisteNoteseleves2()[i], eleve, db2);
                db++;
                db3++;
                db4++;
                db5++;
                db6++;
                db2++;
            }
            eleve++;
        }
    }

    private void addNoteToJtable(List<modelEleve> listeMatiere, DefaultTableModel model, int c1, int c2) {
        int eleve = 0;
        for (final modelEleve detail : listeMatiere) {
            int db = c1;
            int db2 = c2;
            for (int i = 0; i < detail.getlisteNoteseleves().length; i++) {
                model.setValueAt(detail.getlisteNoteseleves()[i], eleve, db);
                model.setValueAt(detail.getlisteNoteseleves2()[i], eleve, db2);
                db++;
                db2++;
            }
            eleve++;
        }
    }

    private void RangPositionnement(JTable jt, DefaultTableModel model, int classet1,
            int classet2, int nbreMat, int rangPosition) {
        int eleve = jt.getRowCount();
        int eleveTaille = jt.getRowCount();
        String note1, note2;
        float[] note = new float[eleve];
        float[] notecomp = new float[eleve];
        int rang = rangPosition;
        int clas1 = classet1;
        int clas2 = classet2;
        for (int nm = 0; nm < nbreMat; nm++) {

            for (int i = 0; i < eleveTaille; i++) {
                System.err.println("klop" + nm + " <<<" + clas1);
                note1 = model.getValueAt(i, clas1).toString();
                note2 = model.getValueAt(i, clas2).toString();
                if ("//".equals(note1)) {
                    note1 = "0";
                }
                if ("//".equals(note2)) {
                    note2 = "0";
                }
                note[i] = (Float.parseFloat(note2) + Float.parseFloat(note1)) / 2;
                System.out.println("LLLLLLLLLL***" + note[i]);
            }
            classementNotes(note);
            System.out.println("note classe = " + Arrays.toString(note));
            for (int i = 0; i < eleveTaille; i++) {
                note1 = model.getValueAt(i, clas1).toString();
                note2 = model.getValueAt(i, clas2).toString();
                if ("//".equals(note1)) {
                    note1 = "0";
                }
                if ("//".equals(note2)) {
                    note2 = "0";
                }
                notecomp[i] = (Float.parseFloat(note2) + Float.parseFloat(note1)) / 2;
                for (int k = 0; k < note.length; k++) {
                    if (note[k] == notecomp[i]) {
                        model.setValueAt((k + 1), i, rang);
                    }
                }
            }
            clas1++;
            clas2++;
            eleve++;
            rang++;
            eleve = eleveTaille;
        }
    }

    private void RangPositionnementAnnuel(JTable jt, DefaultTableModel model, int classet1,
            int classet2, int c3, int c4, int c5, int c6, int nbreMat, int rangPosition) {
        int eleve = jt.getRowCount();
        int eleveTaille = jt.getRowCount();
        String note1, note2, note3, note4, note5, note6;
        float[] note = new float[eleve];
        float[] notecomp = new float[eleve];
        int rang = rangPosition;
        int clas1 = classet1;
        int clas2 = classet2;
        int clas3 = c3;
        int clas4 = c4;
        int clas5 = c5;
        int clas6 = c6;

        for (int nm = 0; nm < nbreMat; nm++) {

            for (int i = 0; i < eleveTaille; i++) {
                System.err.println("klop" + nm + " <<<" + clas1);
                note1 = model.getValueAt(i, clas1).toString();
                note2 = model.getValueAt(i, clas2).toString();
                note3 = model.getValueAt(i, clas3).toString();
                note4 = model.getValueAt(i, clas4).toString();
                note5 = model.getValueAt(i, clas5).toString();
                note6 = model.getValueAt(i, clas6).toString();
                if ("//".equals(note1)) {
                    note1 = "0";
                }
                if ("//".equals(note2)) {
                    note2 = "0";
                }
                if ("//".equals(note3)) {
                    note3 = "0";
                }
                if ("//".equals(note4)) {
                    note4 = "0";
                }
                if ("//".equals(note5)) {
                    note5 = "0";
                }
                if ("//".equals(note6)) {
                    note6 = "0";
                }
                note[i] = (Float.parseFloat(note2) + Float.parseFloat(note1) + Float.parseFloat(note3)
                        + Float.parseFloat(note4) + Float.parseFloat(note5) + Float.parseFloat(note6)) / 6;
                System.out.println("LLLLLLLLLL***" + note[i]);
            }
            classementNotes(note);
            System.out.println("note classe = " + Arrays.toString(note));
            for (int i = 0; i < eleveTaille; i++) {
                note1 = model.getValueAt(i, clas1).toString();
                note2 = model.getValueAt(i, clas2).toString();
                note3 = model.getValueAt(i, clas3).toString();
                note4 = model.getValueAt(i, clas4).toString();
                note5 = model.getValueAt(i, clas5).toString();
                note6 = model.getValueAt(i, clas6).toString();
                if ("//".equals(note1)) {
                    note1 = "0";
                }
                if ("//".equals(note2)) {
                    note2 = "0";
                }
                if ("//".equals(note3)) {
                    note3 = "0";
                }
                if ("//".equals(note4)) {
                    note4 = "0";
                }
                if ("//".equals(note5)) {
                    note5 = "0";
                }
                if ("//".equals(note6)) {
                    note6 = "0";
                }
                notecomp[i] = (Float.parseFloat(note2) + Float.parseFloat(note1) + Float.parseFloat(note3)
                        + Float.parseFloat(note4) + Float.parseFloat(note5) + Float.parseFloat(note6)) / 6;
                for (int k = 0; k < note.length; k++) {
                    if (note[k] == notecomp[i]) {
                        model.setValueAt((k + 1), i, rang);
                    }
                }
            }
            clas1++;
            clas2++;
            clas3++;
            clas4++;
            clas5++;
            clas6++;
            eleve++;
            rang++;
            eleve = eleveTaille;
        }
    }

    public static void classementNotes(float[] tableau) {
        int longueur = tableau.length;
        float tampon = 0;
        boolean permut;
        do {
            // hypothèse : le tableau est trié
            permut = false;
            for (int i = 0; i < longueur - 1; i++) {
                // Teste si 2 éléments successifs sont dans le bon ordre ou non
                if (tableau[i] < tableau[i + 1]) {
                    // s'ils ne le sont pas, on échange leurs positions
                    tampon = tableau[i];
                    tableau[i] = tableau[i + 1];
                    tableau[i + 1] = tampon;
                    permut = true;
                }
            }
        } while (permut);
    }

    public String[] getmatiereClasse(String classe) throws SQLException {
        String[] MatiereParams = null;
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
            int taille = 0;
            rs.last();
            taille = rs.getRow();
            MatiereParams = new String[taille];
            rs.beforeFirst();
            int i = 0;
            matiereclasse = new String[taille];
            groupeclasse = new String[taille];
            coeffclasse = new String[taille];
            enseignantclasse = new String[taille];
            while (rs.next()) {
                String matierem = rs.getString("nom_matiere");
                String enseignant = rs.getString("nom_user") + " " + rs.getString("prenom_user");
                String coeff = rs.getString("coeff");
                String groupe = rs.getString("coeff");
                MatiereParams[i] = matierem;
                matiereclasse[i] = matierem;
                coeffclasse[i] = coeff;
                groupeclasse[i] = groupe;
                enseignantclasse[i] = enseignant;
                i++;
            }

        }
        return MatiereParams;
    }

    public void imprimerBulletinsTrimestriels(JTable jt, String periode, String classe) throws Exception {
        int effectifTotal = jt.getRowCount();
        String nom, prenom, dateNaissance, Matricule, sexe, lieuNaissance, id;
        ArrayList<modelEleve> listeeleve = new ArrayList<>();

        boolean select;
        String[] result = matiereclasse;
        for (int i = 0; i < effectifTotal; i++) {
            modelEleve eleve = new modelEleve();
            nom = jt.getValueAt(i, 2).toString();
            prenom = jt.getValueAt(i, 3).toString();
            sexe = jt.getValueAt(i, 4).toString();
            dateNaissance = jt.getValueAt(i, 5).toString();
            lieuNaissance = jt.getValueAt(i, 6).toString();
            Matricule = jt.getValueAt(i, 7).toString();
            select = Boolean.valueOf(jt.getValueAt(i, 1).toString());
            System.out.println(">>>>" + Arrays.toString(matiereclasse));
            String[] notes = new String[result.length];
            String[] notes2 = new String[result.length];
            String[] rang = new String[result.length];
            int longueur = 15 + 7 * result.length + 4;
            int classement1 = 9, classement2 = 10 + result.length,
                    classement3 = 11 + 2 * result.length,
                    classement4 = 12 + 3 * result.length,
                    classement5 = 13 + 4 * result.length,
                    classement6 = 14 + 5 * result.length,
                    classemntrang = 15 + 6 * result.length;
            for (int j = 0; j < matiereclasse.length; j++) {
                if (jt.getValueAt(i, classement1).toString().equals("//")) {
                    notes[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 1 " + notes[j]);

                } else {
                    notes[j] = jt.getValueAt(i, classement1).toString();
                    System.out.println(j + " Voici alors ca ! : 1 " + jt.getValueAt(i, classement1).toString());

                }
                if (jt.getValueAt(i, classement2).toString().equals("//")) {
                    notes2[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 2 " + notes2[j]);

                } else {
                    notes2[j] = jt.getValueAt(i, classement2).toString();
                    System.out.println(j + " Voici alors ca ! : 2 " + jt.getValueAt(i, classement2).toString());

                }

                rang[j] = jt.getValueAt(i, classement3).toString();
                classement1++;
                classement2++;
                classement3++;
            }
            eleve.setredoublant(redoublantTab[i]);
            eleve.setRangMatiere(rang);
            eleve.setnomMatiere(nom);
            eleve.setprenom(prenom);
            eleve.setsexe(sexe);
            eleve.setdate(new SimpleDateFormat("dd-MM-yyyy").parse(dateNaissance));
            eleve.setSelect(select);
            eleve.setlisteNotes(notes);
            eleve.setlisteNotes2(notes2);
            eleve.setmatricule(Matricule);
            eleve.setglieu(lieuNaissance);
            eleve.setAvatar(avatar[i]);
            listeeleve.add(eleve);
        }
        String prd = "" + getPeriodeSaveStats(periode);
        float mg = MoyenneGeneraleTrim(jt, prd, classe);

        ImprimerTrim(listeeleve, effectifTotal, classe, periode, mg);

    }

    public void imprimerBulletinsAnnuel(JTable jt, String periode, String classe) throws Exception {
        int effectifTotal = jt.getRowCount();
        String nom, prenom, dateNaissance, Matricule, sexe, lieuNaissance, id;
        ArrayList<modelEleve> listeeleve = new ArrayList<>();

        boolean select;
        String[] result = matiereclasse;
        for (int i = 0; i < effectifTotal; i++) {
            modelEleve eleve = new modelEleve();
            nom = jt.getValueAt(i, 2).toString();
            prenom = jt.getValueAt(i, 3).toString();
            sexe = jt.getValueAt(i, 4).toString();
            dateNaissance = jt.getValueAt(i, 5).toString();
            lieuNaissance = jt.getValueAt(i, 6).toString();
            Matricule = jt.getValueAt(i, 7).toString();
            select = Boolean.valueOf(jt.getValueAt(i, 1).toString());
            System.out.println(">>>>" + Arrays.toString(matiereclasse));
            String[] notes = new String[result.length];
            String[] notes2 = new String[result.length];
            String[] notes3 = new String[result.length];
            String[] notes4 = new String[result.length];
            String[] notes5 = new String[result.length];
            String[] notes6 = new String[result.length];
            String[] rang = new String[result.length];
            int longueur = 15 + 7 * result.length + 4;
            int classement1 = 9, classement2 = 10 + result.length,
                    classement3 = 11 + 2 * result.length,
                    classement4 = 12 + 3 * result.length,
                    classement5 = 13 + 4 * result.length,
                    classement6 = 14 + 5 * result.length,
                    classemntrang = 15 + 6 * result.length;
            for (int j = 0; j < matiereclasse.length; j++) {
                if (jt.getValueAt(i, classement1).toString().equals("//")) {
                    notes[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 1 " + notes[j]);

                } else {
                    notes[j] = jt.getValueAt(i, classement1).toString();
                    System.out.println(j + " Voici alors ca ! : 1 " + jt.getValueAt(i, classement1).toString());

                }

                if (jt.getValueAt(i, classement2).toString().equals("//")) {
                    notes2[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 2 " + notes2[j]);

                } else {
                    notes2[j] = jt.getValueAt(i, classement2).toString();
                    System.out.println(j + " Voici alors ca ! : 2 " + jt.getValueAt(i, classement2).toString());

                }
                if (jt.getValueAt(i, classement3).toString().equals("//")) {
                    notes3[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 2 " + notes2[j]);

                } else {
                    notes3[j] = jt.getValueAt(i, classement3).toString();
                    System.out.println(j + " Voici alors ca ! : 2 " + jt.getValueAt(i, classement2).toString());

                }
                if (jt.getValueAt(i, classement4).toString().equals("//")) {
                    notes4[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 2 " + notes4[j]);

                } else {
                    notes4[j] = jt.getValueAt(i, classement4).toString();
                    System.out.println(j + " Voici alors ca ! : 2 " + jt.getValueAt(i, classement5).toString());

                }
                if (jt.getValueAt(i, classement5).toString().equals("//")) {
                    notes5[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 2 " + notes5[j]);

                } else {
                    notes5[j] = jt.getValueAt(i, classement5).toString();
                    System.out.println(j + " Voici alors ca ! : 2 " + jt.getValueAt(i, classement5).toString());

                }
                if (jt.getValueAt(i, classement6).toString().equals("//")) {
                    notes6[j] = "100";
                    System.out.println(j + " Voici alors ca ! : 2 " + notes6[j]);

                } else {
                    notes6[j] = jt.getValueAt(i, classement6).toString();
                    System.out.println(j + " Voici alors ca ! : 2 " + jt.getValueAt(i, classement6).toString());

                }

                rang[j] = jt.getValueAt(i, classemntrang).toString();
                classement1++;
                classement2++;
                classement3++;
                classement4++;
                classement5++;
                classement6++;
            }
            eleve.setredoublant(redoublantTab[i]);
            eleve.setRangMatiere(rang);
            eleve.setnomMatiere(nom);
            eleve.setprenom(prenom);
            eleve.setsexe(sexe);
            eleve.setdate(new SimpleDateFormat("dd-MM-yyyy").parse(dateNaissance));
            eleve.setSelect(select);
            eleve.setlisteNotes(notes);
            eleve.setlisteNotes2(notes2);
            eleve.setlisteNotes3(notes3);
            eleve.setlisteNotes4(notes4);
            eleve.setlisteNotes5(notes5);
            eleve.setlisteNotes6(notes6);
            eleve.setmatricule(Matricule);
            eleve.setglieu(lieuNaissance);
            eleve.setAvatar(avatar[i]);
            listeeleve.add(eleve);
        }
        String prd = "" + getPeriodeSaveStats(periode);
        float mg = MoyenneGeneraleTrim(jt, prd, classe);

        ImprimerTrimAnnee(listeeleve, effectifTotal, classe, periode, mg);

    }

    private float MoyenneGeneraleTrim(JTable jt, String periode, String classe) throws SQLException {
        int effectifTotal = jt.getRowCount();
        String nom, prenom, dateNaissance, Matricule, sexe, lieuNaissance, id;
        int totalpresent = effectifTotal;
        String[] result = matiereclasse;
        float moyenne = 0;
        float coefficient = 0;
        for (int i = 0; i < effectifTotal; i++) {
            nom = jt.getValueAt(i, 2).toString();
            prenom = jt.getValueAt(i, 3).toString();
            sexe = jt.getValueAt(i, 4).toString();
            dateNaissance = jt.getValueAt(i, 5).toString();
            lieuNaissance = jt.getValueAt(i, 6).toString();
            Matricule = jt.getValueAt(i, 7).toString();

            System.out.println(">>>>" + Arrays.toString(matiereclasse));
            String[] notes = new String[result.length];
            String[] notes2 = new String[result.length];
            float moyenneEleve = 0, noteEleveMo = 0;
            int classement1 = 9, classement2 = 10 + result.length;

            for (int j = 0; j < matiereclasse.length; j++) {
                notes[j] = "0";
                notes2[j] = "0";
                int k = 0; // pour obtenir le nombre de sequence saisie
                if (!jt.getValueAt(i, classement1).toString().equals("//")) {
                    notes[j] = jt.getValueAt(i, classement1).toString();
                    k++;
                }
                if (!jt.getValueAt(i, classement2).toString().equals("//")) {
                    notes2[j] = jt.getValueAt(i, classement2).toString();
                    k++;
                    System.out.println(j + " == " + notes2[j]);
                }
                System.out.println(notes[j] + " = s2= " + notes2[j] + " coeff:: " + coeffclasse[j]);

                float MG = 0;
                if (k != 0) {
                    MG = (((Float.parseFloat(notes[j]) + Float.parseFloat(notes2[j])) / k)
                            * Float.parseFloat(coeffclasse[j]));
                }
                System.out.println(" MG : " + MG);
                noteEleveMo += MG;
                coefficient += Float.parseFloat(coeffclasse[j]);
                classement1++;
                classement2++;
            }
            moyenneEleve = noteEleveMo / coefficient;
            BigDecimal valeur = (new BigDecimal(moyenneEleve)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            double moyen_eleve = valeur.doubleValue();
            if (moyenneEleve == 0) {
                totalpresent--;
            }
            new fonctions().addEleveMoyenne(classe, periode,
                    jt.getValueAt(i, 7).toString(), "" + moyen_eleve);
            System.out.println("LISTE PRESENTS> : "
                    + totalpresent + " Moyen eleve obtenu " + moyen_eleve);
            moyenne += moyen_eleve;
        }
        if (totalpresent == 0) {
            totalpresent = 1;
        }
        return (moyenne / (totalpresent));
    }

    public void ImprimerTrim(ArrayList<modelEleve> listeleveClasse, int effectif, String classe,
            String periode, float Mg) throws Exception {
        ReportsCards reports = new ReportsCards();
        ReportsCards reports2 = new ReportsCards();
        System.out.println("<<<< CLASSSE " + classe);
        int count = 0;
        for (modelEleve detail : listeleveClasse) {
            Report report = new Report().setEntete(new Entete().setAnneeScolaire(memoire.anne_scolaire).setBp(memoire.bp)
                    .setCountry(memoire.country)
                    .setDevice(memoire.device)
                    .setHierarchieLevel1(memoire.hierarchieLevel1)
                    .setHierarchieLevel2(memoire.hierarchieLevel2)
                    .setHierarchieLevel3(memoire.hierarchieLevel3)
                    .setStatusHeadmaster(memoire.statusHeadmaster)
                    .setLinkFiligrane(memoire.filigran)
                    .setLinkLogo(memoire.logo)
                    .setNumero(memoire.telephone_ecole)
                    .setSchool(memoire.ecole)
                    .setStatusHeadmaster(memoire.dirigeant)
            );
            //detail.ansencenonjustifiee();
            //report.setDiscipline(detail.ansencenonjustifiee());
            report.setProfile(new Profile().setClasse(classe)
                    .setDate(detail.getDate())
                    .setEffectif(effectif)
                    .setLieu(detail.getLieu())
                    .setNom(detail.getNommatiere())
                    .id_eleve(detail.getmatricule())
                    .setphoto(detail.getAvarar())
                    .setMatricule(detail.getmatricule())
                    //.rd(detail.rd())
                    .setSexe(detail.getsexe()));
            //report.setRang(detail.rang());

            for (int i = 0; i < detail.getlisteNoteseleves().length; i++) {
                String groupe = groupeclasse[i];
                System.err.println(matiereclasse[i] + " = " + detail.getlisteNoteseleves()[i] + "    Error>>>>>><<<<<<" + detail.getNommatiere() + " MMM ==" + groupe);
                switch (groupe) {
                    case "1":
                        report.getGroup1().getMatieres().add(new InfoMatiere().setCoef((Integer.parseInt(coeffclasse[i])))
                                .setEnseignant("M. " + enseignantclasse[i])
                                .setMatiere(matiereclasse[i])
                                .setNoteSeq1(Float.parseFloat(detail.getlisteNoteseleves()[i]))
                                .setNoteSeq2(Float.parseFloat(detail.getlisteNoteseleves2()[i]))
                                .setRang(detail.getRangMatiere()[i]));
                        break;
                    case "2":
                        report.getGroup2().getMatieres().add(new InfoMatiere().setCoef((Integer.parseInt(coeffclasse[i])))
                                .setEnseignant("M. " + enseignantclasse[i])
                                .setMatiere(matiereclasse[i])
                                .setNoteSeq1(Float.parseFloat(detail.getlisteNoteseleves()[i]))
                                .setNoteSeq2(Float.parseFloat(detail.getlisteNoteseleves2()[i]))
                                .setRang(detail.getRangMatiere()[i]));
                        break;
                    default:
                        report.getGroup3().getMatieres().add(new InfoMatiere().setCoef((Integer.parseInt(coeffclasse[i])))
                                .setEnseignant("M. " + enseignantclasse[i])
                                .setMatiere(matiereclasse[i])
                                .setNoteSeq1(Float.parseFloat(detail.getlisteNoteseleves()[i]))
                                .setNoteSeq2(Float.parseFloat(detail.getlisteNoteseleves2()[i]))
                                .setRang(detail.getRangMatiere()[i]));
                        break;

                }
            }
            if (detail.getSelect()) {
                reports2.getListReports().add(report);
                count++;
            }
            reports.getListReports().add(report);
        }
        Lancerimprimer(periode, classe, reports, Mg, reports2, count);

    }

    public void ImprimerTrimAnnee(ArrayList<modelEleve> listeleveClasse, int effectif,
            String classe, String periode, float Mg) throws Exception {
        ReportsCards reports = new ReportsCards();
        ReportsCards reports2 = new ReportsCards();
        int count = 0;
        for (modelEleve detail : listeleveClasse) {

            Report report = new Report().setEntete(new Entete().setAnneeScolaire(memoire.anne_scolaire).setBp(memoire.bp)
                    .setCountry(memoire.country)
                    .setDevice(memoire.device)
                    .setHierarchieLevel1(memoire.hierarchieLevel1)
                    .setHierarchieLevel2(memoire.hierarchieLevel2)
                    .setHierarchieLevel3(memoire.hierarchieLevel3)
                    .setStatusHeadmaster(memoire.statusHeadmaster)
                    .setLinkFiligrane(memoire.filigran)
                    .setLinkLogo(memoire.logo)
                    .setNumero(memoire.telephone_ecole)
                    .setSchool(memoire.ecole)
                    .setStatusHeadmaster(memoire.dirigeant)
            );
            //detail.ansencenonjustifiee();
            //report.setDiscipline(detail.ansencenonjustifiee());
            report.setProfile(new Profile().setClasse(classe)
                    .setDate(detail.getDate())
                    .setEffectif(effectif)
                    .setLieu(detail.getLieu())
                    .setNom(detail.getNommatiere())
                    .id_eleve(detail.getmatricule())
                    .setphoto(detail.getAvarar())
                    .setMatricule(detail.getmatricule())
                    .rd(detail.getredoublant())
                    .setSexe(detail.getsexe()));
            //report.setRang(detail.rang());

            for (int i = 0; i < detail.getlisteNoteseleves().length; i++) {
                String groupe = groupeclasse[i];

                switch (groupe) {
                    case "1":
                        report.getGroup1().getMatieres().add(new InfoMatiere().setCoef((Integer.parseInt(coeffclasse[i])))
                                .setEnseignant("M. " + enseignantclasse[i])
                                .setMatiere(matiereclasse[i])
                                .setNoteSeq1(Float.parseFloat(detail.getlisteNoteseleves()[i]))
                                .setNoteSeq2(Float.parseFloat(detail.getlisteNoteseleves2()[i]))
                                .setNoteSeq3(Float.parseFloat(detail.getlisteNoteseleves3()[i]))
                                .setNoteSeq4(Float.parseFloat(detail.getlisteNoteseleves4()[i]))
                                .setNoteSeq5(Float.parseFloat(detail.getlisteNoteseleves5()[i]))
                                .setNoteSeq6(Float.parseFloat(detail.getlisteNoteseleves6()[i]))
                                .setRang("" + detail.getRangMatiere()[i]));
                        break;
                    case "2":
                        report.getGroup2().getMatieres().add(new InfoMatiere().setCoef((Integer.parseInt(coeffclasse[i])))
                                .setEnseignant("M. " + enseignantclasse[i])
                                .setMatiere(matiereclasse[i])
                                .setNoteSeq1(Float.parseFloat(detail.getlisteNoteseleves()[i]))
                                .setNoteSeq2(Float.parseFloat(detail.getlisteNoteseleves2()[i]))
                                .setNoteSeq3(Float.parseFloat(detail.getlisteNoteseleves3()[i]))
                                .setNoteSeq4(Float.parseFloat(detail.getlisteNoteseleves4()[i]))
                                .setNoteSeq5(Float.parseFloat(detail.getlisteNoteseleves5()[i]))
                                .setNoteSeq6(Float.parseFloat(detail.getlisteNoteseleves6()[i]))
                                .setRang(detail.getRangMatiere()[i]));
                        break;
                    default:
                        report.getGroup3().getMatieres().add(new InfoMatiere().setCoef((Integer.parseInt(coeffclasse[i])))
                                .setEnseignant("M. " + enseignantclasse[i])
                                .setMatiere(matiereclasse[i])
                                .setNoteSeq1(Float.parseFloat(detail.getlisteNoteseleves()[i]))
                                .setNoteSeq2(Float.parseFloat(detail.getlisteNoteseleves2()[i]))
                                .setNoteSeq3(Float.parseFloat(detail.getlisteNoteseleves3()[i]))
                                .setNoteSeq4(Float.parseFloat(detail.getlisteNoteseleves4()[i]))
                                .setNoteSeq5(Float.parseFloat(detail.getlisteNoteseleves5()[i]))
                                .setNoteSeq6(Float.parseFloat(detail.getlisteNoteseleves6()[i]))
                                .setRang(detail.getRangMatiere()[i]));
                        break;

                }
            }
            if (detail.getSelect() == true) {
                reports2.getListReports().add(report);
                count++;
            }
            reports.getListReports().add(report);

        }
        Lancerimprimer(periode, classe, reports, Mg, reports2, count);

    }

    public void Lancerimprimer(String periode, String classe, ReportsCards reports, float mg,
            ReportsCards reports2, int count) throws Exception {
        //new moyenG().setPeriod("sequence1").setReports(reports).imprimer();
        //float res = (float) memoire.MOYGEN / ((float) count);
        String prd = getNomImpresionPeriode(periode);
        String prdCode = "" + getPeriodeSaveStats(periode);
        float MG = 0;

        System.out.println("MGGGGGGGGG " + mg + " >>> " + prd);

        new moyenG(classe, prdCode).setPeriod(prd).setReports(reports).imprimer();
        System.out.println("LLLLLLLLL -" + memoire.MOYGEN + " **** " + memoire.elevesabsent);
        if (memoire.elevesabsent != reports.getListReports().size()) {
            MG = memoire.MOYGEN / (reports.getListReports().size() - memoire.elevesabsent);
        }
        System.err.println("MG:=========== " + MG);
        getMoyenneDernier(classe, prdCode);
        getMoyennePremier(classe, prdCode);
        reports2.setMoyenneGeneral("" + MG);
        if (count == 0) {
            new message().error("Erreur d'impression", "Veuillez au mois selectionner\n"
                    + "un eleve poour continuer");
        } else {
            new Impression(classe).setPeriod(prd).setReports(reports2).imprimer();
            new message().info("Operation terminee");

        }
        memoire.MOYGEN = 0;
        memoire.elevesabsent = 0;
    }

    public void getMoyennePremier(String classe, String period) throws SQLException {
        String query = "select max(moyenne) as maxmoyenne from stat_moyenne where id_classe=? "
                + " and id_annee=? and id_periode=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        ps.setString(2, idAnneScolaire);
        ps.setString(3, period);

        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ " + ps);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                memoire.MOYP = rs.getString("maxmoyenne");
                System.err.println("Premiernn                 ????? " + memoire.MOYP);

            }
        }
    }

    public void getMoyenneDernier(String classe, String period) throws SQLException {
        String query = "select min(moyenne)as minmoyenne from stat_moyenne where id_classe=? "
                + " and id_annee=? and id_periode=? and moyenne not in('0.0')";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        ps.setString(2, idAnneScolaire);
        ps.setString(3, period);

        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ " + ps);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                memoire.MOYD = rs.getString("minmoyenne");
                System.err.println("Dernier????? " + memoire.MOYD);

            }
        }
    }

    public void getMoyenneGenerale(String classe, String period) throws SQLException {
        String query = "select sum(moyenne) as somme from stat_moyenne where id_classe=? "
                + " and id_annee=? and id_periode=? and moyenne not in('0.0')";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        ps.setString(2, idAnneScolaire);
        ps.setString(3, period);

        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ " + ps);
        if (rs.isBeforeFirst()) {
            String query2 = "select * from stat_moyenne where id_classe=? "
                    + " and id_annee=? and id_periode=? and moyenne not in('0.0')";
            PreparedStatement ps2 = mysqli.connect().prepareStatement(query2);
            ps2.setString(1, new fonctions().getIClasse(classe));
            ps2.setString(2, idAnneScolaire);
            ps2.setString(3, period);

            ResultSet rs2 = ps2.executeQuery();
            int taille = 0;
            rs2.last();
            taille = rs2.getRow();
            rs2.beforeFirst();
            while (rs.next()) {

                String md = rs.getString("somme");
                memoire.MOYGENstatistiques = String.valueOf((Float.parseFloat(md) / taille));

            }
        }
    }

    public List<impressionmodel_11> getMoyenneTableauHonneur(String period) throws SQLException {
        List<impressionmodel_11> listeMatiere = new ArrayList<>();

        String query = "select * from stat_moyenne sm inner join users e inner join classe c inner join annee a "
                + " where a.id_annee=sm.id_annee and sm.matricule=e.login and c.id_classe=sm.id_classe and "
                + "  sm.id_periode =? and sm.id_annee=? and sm.moyenne>=? order by nom_classe,nom_user ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, "" + getPeriode(period));
        ps.setString(2, idAnneScolaire);
        ps.setFloat(3, tableauMoyenne);
        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ //////////********************" + tableauMoyenne);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                impressionmodel_11 matiere = new impressionmodel_11();

                matiere.nom(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
                matiere.moyenne(rs.getString("moyenne"));
                matiere.sexe(rs.getString("sexe_user"));
                matiere.matricule(rs.getString("matricule"));
                matiere.classe(rs.getString("nom_classe"));
                matiere.date(period);
                matiere.lnaissance(rs.getString("nom_annee"));

                // matiere.setredoublant(rs.getString("redoublant"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
            }
            return listeMatiere;
        }
        return null;
    }

    public List<impressionmodel_11> getMoyenneTableauHonneurParclasse(String period, String classe) throws SQLException {
        List<impressionmodel_11> listeMatiere = new ArrayList<>();

        String query = "select * from stat_moyenne sm inner join users e inner join classe c inner join annee a "
                + " where a.id_annee=sm.id_annee and sm.matricule=e.login and c.id_classe=sm.id_classe and "
                + "  sm.id_periode =? and sm.id_annee=? and sm.moyenne>=? and sm.id_classe=? order by nom_classe,nom_user ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, "" + getPeriode(period));
        ps.setString(2, idAnneScolaire);
        ps.setFloat(3, tableauMoyenne);
        ps.setString(4, new fonctions().getIClasse(classe));
        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ //////////" + tableauMoyenne);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                impressionmodel_11 matiere = new impressionmodel_11();

                matiere.nom(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
                matiere.moyenne(rs.getString("moyenne"));
                matiere.sexe(rs.getString("sexe_user"));
                matiere.matricule(rs.getString("matricule"));
                matiere.classe(rs.getString("nom_classe"));
                matiere.date(period);
                matiere.lnaissance(rs.getString("nom_annee"));

                // matiere.setredoublant(rs.getString("redoublant"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
            }
            return listeMatiere;
        }
        return null;
    }

    public void getMoyennePremierG(String period) throws SQLException {
        String query = "select max(moyenne) as maxmoyenne from stat_moyenne st inner join users"
                + " u where u.id_etab=?  and"
                + " st.matricule=u.login and id_annee=? and id_periode=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, idAnneScolaire);
        ps.setString(3, period);

        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ " + ps);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                memoire.MOYP = rs.getString("maxmoyenne");
            }
        }
    }

    public void getMoyenneDernierG(String period) throws SQLException {
        String query = "select min(moyenne)as minmoyenne from stat_moyenne st inner join users u "
                + "where u.id_etab=? and "
                + " st.matricule=u.login and id_annee=? and id_periode=? and moyenne not in('0.0')";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, idAnneScolaire);
        ps.setString(3, period);

        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ " + ps);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                memoire.MOYD = rs.getString("minmoyenne");
            }
        }
    }

    public void getMoyenneGeneraleG(String period) throws SQLException {
        String query = "select sum(moyenne) as somme from stat_moyenne st inner join users u"
                + " where u.id_etab=? and "
                + " st.matricule=u.login and id_annee=? and id_periode=? and moyenne not in('0.0')";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, idAnneScolaire);
        ps.setString(3, period);

        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ " + ps);
        if (rs.isBeforeFirst()) {
            String query2 = "select * from stat_moyenne st inner join users u where u.id_etab=? and "
                    + " st.matricule=u.login and id_annee=? and id_periode=? and moyenne not in('0.0')";
            PreparedStatement ps2 = mysqli.connect().prepareStatement(query2);
            ps2.setString(1, idEtablissement);
            ps2.setString(2, idAnneScolaire);
            ps2.setString(3, period);

            ResultSet rs2 = ps2.executeQuery();
            int taille = 0;
            rs2.last();
            taille = rs2.getRow();
            rs2.beforeFirst();
            while (rs.next()) {

                String md = rs.getString("somme");
                memoire.MOYGENstatistiques = String.valueOf((Float.parseFloat(md) / taille));

            }
        }
    }
}

class HeaderRenderer2 implements TableCellRenderer {

    private final JCheckBox check = new JCheckBox("Tt select");

    public HeaderRenderer2(JTableHeader header) {
        check.setOpaque(false);
        check.setFont(header.getFont());
        check.setBackground(Color.red);
        header.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = ((JTableHeader) e.getSource()).getTable();
                TableColumnModel columnModel = table.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int modelColumn = table.convertColumnIndexToModel(viewColumn);
                if (modelColumn == 1) {
                    check.setSelected(!check.isSelected());
                    TableModel m = table.getModel();
                    Boolean f = check.isSelected();
                    for (int i = 0; i < m.getRowCount(); i++) {
                        m.setValueAt(f, i, 1);
                    }
                    ((JTableHeader) e.getSource()).repaint();
                }
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable tbl, Object val, boolean isS, boolean hasF, int row, int col) {
        TableCellRenderer r = tbl.getTableHeader().getDefaultRenderer();
        JLabel l = (JLabel) r.getTableCellRendererComponent(tbl, val, isS, hasF, row, col);
        l.setForeground(Color.RED);
        l.setBackground(Color.red);
        l.setIcon(new CheckBoxIcon(check));
        return l;
    }

    private static class CheckBoxIcon implements Icon {

        private final JCheckBox check;

        public CheckBoxIcon(JCheckBox check) {
            this.check = check;
        }

        @Override
        public int getIconWidth() {
            return check.getPreferredSize().width;
        }

        @Override
        public int getIconHeight() {
            return check.getPreferredSize().height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            SwingUtilities.paintComponent(
                    g, check, (Container) c, x, y, getIconWidth(), getIconHeight());
        }
    }

}
