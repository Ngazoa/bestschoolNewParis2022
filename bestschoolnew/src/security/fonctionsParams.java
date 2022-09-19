/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import etab.login;
import etab.message;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.prefs.Preferences;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.modelEleve;
import model.modelEnseignant;
import paie.personnel.sha1;
import static security.fonctions.idAnneScolaire;
import static security.fonctions.idEtablissement;
import utils.anneeEditor;
import utils.cycleEditor;
import utils.formatAmount;
import utils.paiementPersonnelEditor;
import utils.trancheEditor;

/**
 *
 * @author ASSUS GAMER
 */
public class fonctionsParams {

    public static TreeMap<Integer, String> cycleMap = new TreeMap<>();
    public static TreeMap<Integer, String> trancheMap = new TreeMap<>();
    Preferences prf = Preferences.userNodeForPackage(fonctions.class);

    private final mysqli mysqli;

    public fonctionsParams() {
        mysqli = new mysqli();
        if (mysqli.connect() == null) {
            new message().error("Erreur de connexion", "Impossible d'etablir la connexion avec\n le serveur distant");
            System.exit(0);
        }

    }

    public int checkCycle(String cycle) throws SQLException {
        String query = "select * from cycle where "
                + "id_etab=?  and nom_cycle=? and etat_cycle='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(2, cycle);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            new message().warning("Impossible de poursuivre", "Desole, mais ce cycle existe deja");
            return 1;
        } else {
            insercycle(cycle);
        }
        return 0;
    }

    public void insercycle(String nom) throws SQLException {
        String sqlInsert = "insert into cycle (nom_cycle,id_etab) "
                + "values (?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nom);
        ps.setString(2, idEtablissement);
        int t = ps.executeUpdate();
        if (t == 1) {
            new message().info("Enregistre avec Succes ");
        } else {
            new message().error("Erreur", "Erreur Iconnu");
        }
    }

    public void listeCycles(JComboBox j) throws SQLException {
        String query = "select * from cycle where id_etab=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        j.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"select"}));

        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                j.addItem(rs.getString("nom_cycle"));
                cycleMap.put(rs.getInt("id_cycle"), rs.getString("nom_cycle"));
            }

        } else {
            new message().info("Aucun cycle trouve svp\nVeuillez d'abord les creer dans\nGestion"
                    + "des cycles");
        }
    }

    public void listeCycles(JTable jt) throws SQLException {
        String query = "select * from cycle where id_etab=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_cycle"));
                modelEnseignant.setnom(rs.getString("nom_cycle"));

                listeMatiere.add(modelEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Cycle", "Editer"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column == 1) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;
            for (final modelEnseignant detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getid(), detail.getNom()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(70);
            jt.getColumnModel().getColumn(3).setMaxWidth(70);

            TableColumn testColumn3 = jt.getColumnModel().getColumn(3);

            testColumn3.setCellRenderer(new PlusMinusCellRenderer());
            testColumn3.setCellEditor(new cycleEditor(new JCheckBox()));

        }
    }

    public void getlisteAnneeEcoulees(JTable jt) throws SQLException {
        String query = "select * from annee_ecoulee ae inner join annee a where"
                + " ae.id_annee=a.id_annee and id_etab=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_annee"));
                modelEnseignant.setnom(rs.getString("nom_annee"));
                listeMatiere.add(modelEnseignant);
            }
            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom annee", "Activer"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column == 1) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;
            for (final modelEnseignant detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getid(), detail.getNom()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(70);
            jt.getColumnModel().getColumn(3).setMaxWidth(70);

            TableColumn testColumn3 = jt.getColumnModel().getColumn(3);

            testColumn3.setCellRenderer(new ActiverCellRenderer());
            testColumn3.setCellEditor(new anneeEditor(new JCheckBox()));

        } else {
            new message().info("Desole, mais il peut que votre\n"
                    + "Etablissement ne possede pas d'annees archivees");
        }
    }

    public void getlistMoratoire(JTable jt, Date date, String idTranche) throws SQLException {
        java.sql.Date spdate = null;
        String query;
        PreparedStatement ps;
        if (date != null) {
            spdate = new java.sql.Date(date.getTime());
            query = "select * from "
                    + " moratoire m inner join eleve e "
                    + "inner join eleve_classe ec"
                    + " inner join classe c where "
                    + "m.id_eleve=e.id_eleve and m.id_tranche=?"
                    + " and date_creation_m=? and m.id_annee=?"
                    + " and ec.id_eleve=e.id_eleve and "
                    + " ec.id_classe=c.id_classe ";
            ps = mysqli.connect().prepareStatement(query);
            ps.setString(1, idTranche);
            ps.setDate(2, spdate);
            ps.setString(3, idAnneScolaire);
        } else {

            query = "select * from "
                    + " moratoire m inner join eleve e "
                    + "inner join eleve_classe ec"
                    + " inner join classe c where "
                    + "m.id_eleve=e.id_eleve and m.id_tranche=?"
                    + " and m.id_annee=?"
                    + " and ec.id_eleve=e.id_eleve and "
                    + " ec.id_classe=c.id_classe ";
            ps = mysqli.connect().prepareStatement(query);
            ps.setString(1, idTranche);
            ps.setString(2, idAnneScolaire);
        }

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_eleve"));
                modelEnseignant.setnom(rs.getString("nom_eleve") + " "
                        + rs.getString("prenom_eleve"));
                modelEnseignant.setsexe(rs.getString("nom_classe"));
                modelEnseignant.setmoratoire(rs.getString("montant"));
                modelEnseignant.settelephone(rs.getString("date_creation_m"));
                modelEnseignant.setFinish(rs.getBoolean("is_finish"));
                listeMatiere.add(modelEnseignant);
            }
            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N",
                "Code", "Eleve", "classe", "montant","Statut", "Date"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column == 1) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;
            for (final modelEnseignant detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getid(), detail.getNom(), detail.getsexe(),
                     detail.getMoratoire(),detail.getFinish()?"soldé":"Inachevé", detail.gettelephone()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(70);
        }
    }

    public void getPersonnelPeriodePaiement(JTable jt) throws SQLException {
        String query = "select * from periode_paie where id_etab=? "
                + "and etat_periode='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve modelElEnseignant = new modelEleve();
                modelElEnseignant.setidMatiere(rs.getString("id_periode_paie"));
                modelElEnseignant.setnomMatiere(rs.getString("nom_periode_paie"));
                modelElEnseignant.settelephone(rs.getString("rang"));
                listeMatiere.add(modelElEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Periode Paie",
                "Ordre paiement", "Editer", "Supp"}, 0) {
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
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.gettelephne()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(40);

            jt.getColumnModel().getColumn(4).setMaxWidth(50);
            jt.getColumnModel().getColumn(5).setMaxWidth(50);

            TableColumn testColumn3 = jt.getColumnModel().getColumn(4);
            TableColumn testColumn4 = jt.getColumnModel().getColumn(5);

            testColumn3.setCellRenderer(new PlusMinusCellRenderer());
            testColumn3.setCellEditor(new paiementPersonnelEditor(new JCheckBox()));
            testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
            testColumn4.setCellEditor(new paiementPersonnelEditor(new JCheckBox()));

        } else {
            new message().info("Aucune Periode  de paiement trouvee");
        }
    }

    private String checkSiPaiementpersonnel(String id_personnel, String periode) throws SQLException {
        String query = "select * from paie_personnel where id_annee=? and id_periode_paie=? and"
                + " id_user_paye=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(2, periode);
        ps.setString(1, idAnneScolaire);
        ps.setString(3, id_personnel);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>");
            return rs.getString("montant");
        } else {
            System.out.println("?????????????????????????");
            //new message().info("erreur inconnue survenue");
            return "";
        }
    }

    public void getPersonnelPourPaiement(JTable jt, String id) throws SQLException {
        String query = "select * from periode_paie  where id_etab=? and etat_periode='1' order by rang";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {

                modelEleve modelElEnseignant = new modelEleve();
                String montnt = checkSiPaiementpersonnel(id, rs.getString("id_periode_paie"));
                if (montnt.isEmpty()) {
                    modelElEnseignant.setSelect(false);
                    modelElEnseignant.settelephone("/ /");
                } else {
                    modelElEnseignant.setSelect(true);
                    modelElEnseignant.settelephone(montnt);

                }
                modelElEnseignant.setidMatiere(rs.getString("id_periode_paie"));
                modelElEnseignant.setnomMatiere(rs.getString("nom_periode_paie"));

                listeMatiere.add(modelElEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Periode Paie",
                "montant", "etat", "Payer", "Recu"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column < 5) {
                        test = false;
                        return test;
                    }

                    return test;
                }

                @Override
                public Class getColumnClass(int columnIndex) {
                    if (columnIndex == 4) {
                        return Boolean.class;
                    }
                    return super.getColumnClass(columnIndex);
                }
            };
            int j = 1;
            for (final modelEleve detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(),
                    detail.gettelephne(), detail.getSelect()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(40);

            jt.getColumnModel().getColumn(4).setMaxWidth(50);
            jt.getColumnModel().getColumn(5).setMaxWidth(50);
            jt.getColumnModel().getColumn(6).setMaxWidth(50);

            TableColumn testColumn3 = jt.getColumnModel().getColumn(5);
            TableColumn testColumn4 = jt.getColumnModel().getColumn(6);

            testColumn3.setCellRenderer(new PayerInscrirepayerCellRenderer());
            testColumn3.setCellEditor(new paiementPersonnelEditor(new JCheckBox()));
            testColumn4.setCellRenderer(new ImrpimerpayerCellRenderer());
            testColumn4.setCellEditor(new paiementPersonnelEditor(new JCheckBox()));

        } else {
            new message().info("Aucune Periode  de paiement trouvee");
        }
    }

    public void getTranchePaiement(JTable jt) throws SQLException {
        String query = "select * from cycle c inner join tranche_scolarite t where t.id_etab=? and t."
                + "id_cycle=c.id_cycle and etat_tranche='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve modelElEnseignant = new modelEleve();
                modelElEnseignant.setidMatiere(rs.getString("id_tranche"));
                modelElEnseignant.setnomMatiere(rs.getString("nom_tranche"));
                modelElEnseignant.setprenom(rs.getString("nom_cycle"));
                modelElEnseignant.setmoyenne(rs.getString("prix"));
                modelElEnseignant.settelephone(rs.getString("rang"));
                listeMatiere.add(modelElEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Tranche",
                " Cycle", " Prix ", "Ordre paiement", "Editer", "Supp"}, 0) {
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
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getprenom(),
                    new formatAmount().montantFormatte(detail.getMoyenne()), detail.gettelephne()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(40);
            jt.getColumnModel().getColumn(5).setMaxWidth(80);
            jt.getColumnModel().getColumn(6).setMaxWidth(50);
            jt.getColumnModel().getColumn(7).setMaxWidth(50);

            TableColumn testColumn3 = jt.getColumnModel().getColumn(6);
            TableColumn testColumn4 = jt.getColumnModel().getColumn(7);

            testColumn3.setCellRenderer(new PlusMinusCellRenderer());
            testColumn3.setCellEditor(new trancheEditor(new JCheckBox()));
            testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
            testColumn4.setCellEditor(new trancheEditor(new JCheckBox()));

        } else {
            new message().info("Aucune tranche de paiement trouvee");
        }
    }

    public void getTranchePaiementJco(JComboBox jt) throws SQLException {
        String query = "select * from cycle c inner join tranche_scolarite t where t.id_etab=? and t."
                + "id_cycle=c.id_cycle and etat_tranche='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            jt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"select"}));

            while (rs.next()) {
                modelEleve modelElEnseignant = new modelEleve();
                modelElEnseignant.setidMatiere(rs.getString("id_tranche"));
                jt.addItem(rs.getString("nom_tranche"));
                trancheMap.put(Integer.parseInt(rs.getString("id_tranche")),
                        rs.getString("nom_tranche"));
            }

        } else {
            new message().info("Aucune tranche de paiement trouvee");
        }
    }

    public String getIdcycle(String value) {
        for (Entry<Integer, String> entry : cycleMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return "" + entry.getKey();
            }
        }
        return null;
    }

    public String getIdtranche(String value) {
        for (Entry<Integer, String> entry : trancheMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return "" + entry.getKey();
            }
        }
        return value;
    }

    public void getTranchesPourStatistiquesPrevisions(JTable jt) throws SQLException {
        String query = "select * from cycle where id_etab=? and  etat_cycle='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        //ps.setString(2, idAnneScolaire);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_cycle"));
                modelEnseignant.setnom((rs.getString("nom_cycle")));
                modelEnseignant.setsexe(getnombreClasseCycle(rs.getString("id_cycle")));
                modelEnseignant.setdate(getnombreEleveCycle(rs.getString("id_cycle")));
                modelEnseignant.settelephone(getsommeTrancheEleve(rs.getString("id_cycle")));
                listeMatiere.add(modelEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Cycle", "Nbre clasee"
                + "", "Nbre eleve", "Montant par eleve", "Montant Total"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column < 5) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;
            int nbreeleve = 0, classenb = 0;
            float momtele = 0, totalmt = 0;
            for (final modelEnseignant detail : listeMatiere) {
                System.err.println("eleve " + detail.getdate() + " *** " + (detail.gettelephone()));
                nbreeleve += Integer.parseInt(detail.getdate());
                momtele += Float.parseFloat(detail.gettelephone());
                classenb += Integer.parseInt(detail.getsexe());
                totalmt += (Integer.parseInt(detail.getdate())
                        * Float.parseFloat(detail.gettelephone()));
                model.addRow(new Object[]{j, detail.getid(), detail.getNom(), detail.getsexe(), detail.getdate(),
                    detail.gettelephone(), new formatAmount().montantFormatte("" + (Integer.parseInt(detail.getdate())
                    * Float.parseFloat(detail.gettelephone())))});
                j++;
            }
            model.addRow(new Object[]{"TOTAUX", "", "", classenb, nbreeleve, new formatAmount().montantFormatte("" + momtele), new formatAmount().montantFormatte("" + totalmt)});
            jt.setModel(model);
            //jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(30);
            //jt.getColumnModel().getColumn(3).setMaxWidth(70);

            //TableColumn testColumn3 = jt.getColumnModel().getColumn(3);
            //testColumn3.setCellRenderer(new PlusMinusCellRenderer());
            //testColumn3.setCellEditor(new cycleEditor(new JCheckBox()));
        }
    }

    public int getStatistiquesPrevisionsJurnalier(Date date, JTable jt) throws SQLException {
        String query = "select * from cycle where id_etab=? and etat_cycle='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        //ps.setString(2, idAnneScolaire);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_cycle"));
                modelEnseignant.setnom((rs.getString("nom_cycle")));
                modelEnseignant.setsexe(getnombreClasseCycle(rs.getString("id_cycle")));
                modelEnseignant.setdate(getnombreEleveCycleJournalier(rs.getString("id_cycle"), date));
                modelEnseignant.settelephone(getsommeTrancheJournalierEleve(rs.getString("id_cycle"), date));
                modelEnseignant.setmoratoire(
                        getsommeMoratoireJournalierEleve(false,rs.getString("id_cycle"), date));
                listeMatiere.add(modelEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Cycle", "Nbre clasee"
                + "", "Nbre eleve", "Montant par eleve", "Moratoire", "Montant Total"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column < 5) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;
            int nbreeleve = 0, classenb = 0;
            float momtele = 0, totalmt = 0, montantMoratoire = 0;
            for (final modelEnseignant detail : listeMatiere) {
                System.err.println("eleve " + detail.getdate() + " *** " + (detail.gettelephone()));
                nbreeleve += Integer.parseInt(detail.getdate());
                momtele += Float.parseFloat(detail.gettelephone());
                classenb += Integer.parseInt(detail.getsexe());
                montantMoratoire += Float.parseFloat(detail.getMoratoire());
                totalmt += (Integer.parseInt(detail.getdate())
                        * Float.parseFloat(detail.gettelephone()));
                model.addRow(new Object[]{j, detail.getid(), detail.getNom(), detail.getsexe(), detail.getdate(),
                    detail.gettelephone(), new formatAmount().montantFormatte(
                    detail.getMoratoire()),
                    new formatAmount().montantFormatte("" + ((Integer.parseInt(detail.getdate())
                    * Float.parseFloat(detail.gettelephone())) + Float.parseFloat(detail.getMoratoire())))});
                j++;
            }
            model.addRow(new Object[]{"TOTAUX", "", "", classenb, nbreeleve, momtele,
                new formatAmount().montantFormatte("" + montantMoratoire),
                new formatAmount().montantFormatte("" + (montantMoratoire + totalmt))});
            jt.setModel(model);
            //jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(30);
            if (totalmt < 1) {

                return 0;
            }

            //TableColumn testColumn3 = jt.getColumnModel().getColumn(3);
            //testColumn3.setCellRenderer(new PlusMinusCellRenderer());
            //testColumn3.setCellEditor(new cycleEditor(new JCheckBox()));
        } else {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Cycle", "Nbre clasee"
                + "", "Nbre eleve", "Montant par eleve", "Montant Total"}, 0);
            jt.setModel(model);
            new message().info("Desole, mais aucun enregistrement trouve");
        }
        return 1;
    }

//   public String getnombreClasseCycle(String id_cycle) throws SQLException {
//        int nbre = 0;
//        String query = "select * from classe where id_etab=? and id_cycle=?"
//                + " and etat_classe='1'";
//        PreparedStatement ps = mysqli.connect().prepareStatement(query);
//        ps.setString(1, idEtablissement);
//        ps.setString(2, id_cycle);
//
//        ResultSet rs = ps.executeQuery();
//        if (rs.isBeforeFirst()) {
//            rs.last();
//            nbre = rs.getRow();
//            rs.beforeFirst();
//            List<modelEnseignant> listeMatiere = new ArrayList<>();
//            while (rs.next()) {
//                modelEnseignant modelEnseignant = new modelEnseignant();
//                modelEnseignant.setid(rs.getString("id_cycle"));
//                modelEnseignant.setnom(rs.getString("nom_cycle"));
//
//                listeMatiere.add(modelEnseignant);
//            }
//        }
//        return "" + nbre;
//    } 
    public String getnombreEleveCycleJournalier(String id_cycle, Date date) throws SQLException {
        int nbre = 0;
        java.sql.Date spdate;
        spdate = new java.sql.Date(date.getTime());
        String query = "select * from classe c inner join eleve_classe ec inner join eleve e "
                + "inner join scolarite_paie sp "
                + " where c.id_classe=ec.id_classe and e.id_eleve=ec.id_eleve and c.id_cycle=?"
                + " and c.etat_classe='1' and e.etat_eleve='1' and sp.id_eleve=ec.id_eleve and sp.date_creation_s=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_cycle);
        ps.setDate(2, spdate);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            rs.last();
            nbre = rs.getRow();
            rs.beforeFirst();
        }
        return "" + nbre;
    }

    public String getnombreClasseCycle(String id_cycle) throws SQLException {
        int nbre = 0;
        String query = "select * from classe where id_etab=? and id_cycle=?"
                + " and etat_classe='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, id_cycle);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            rs.last();
            nbre = rs.getRow();
            rs.beforeFirst();
        }
        return "" + nbre;
    }

    public String getnombreEleveCycle(String id_cycle) throws SQLException {
        int nbre = 0;
        String query = "select * from classe c inner join eleve_classe ec inner join eleve e"
                + " where c.id_classe=ec.id_classe and e.id_eleve=ec.id_eleve and c.id_cycle=?"
                + " and c.etat_classe='1' and e.etat_eleve='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_cycle);
        //ps.setString(2, id_cycle);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            rs.last();
            nbre = rs.getRow();
            rs.beforeFirst();
        }
        return "" + nbre;
    }

    public String getsommeTrancheEleve(String id_cycle) throws SQLException {
        String nbre = "0";
        String query = "select sum(prix) as montant from tranche_scolarite  "
                + " where id_cycle=? and etat_tranche='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_cycle);
        //ps.setString(2, id_cycle);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                nbre = "" + rs.getFloat("montant");
            }
        }
        return "" + nbre;
    }

    public String getsommeTrancheJournalierEleve(String id_cycle, Date date) throws SQLException {
        String nbre = "0";
        java.sql.Date spdate;
        spdate = new java.sql.Date(date.getTime());
        System.out.println(" DDDDDDDDDAAte   " + spdate);

        String query = "select sum(prix) as montant from tranche_scolarite ts "
                + "inner join scolarite_paie sp  "
                + " where ts.id_cycle=? and ts.etat_tranche='1' and ts.id_tranche=sp.id_tranche "
                + " and sp.date_creation_s=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_cycle);
        ps.setDate(2, spdate);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                nbre = "" + rs.getFloat("montant");
            }
        }
        return "" + nbre;
    }

    public String getsommeMoratoireJournalierEleve(boolean is_finish,String id_cycle, Date date) throws SQLException {
        String nbre = "0";
        java.sql.Date spdate;
        spdate = new java.sql.Date(date.getTime());
        System.out.println(" DDDDDDDDDAAte   " + spdate);

        String query = "select sum(m.montant) as montant from tranche_scolarite ts "
                + "inner join moratoire m  "
                + " where ts.id_tranche=? and ts.etat_tranche='1' and ts.id_tranche=m.id_tranche "
                + " and m.date_creation_m=? and is_finish=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_cycle);
        ps.setDate(2, spdate);
        ps.setBoolean(3, is_finish);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                nbre = "" + rs.getFloat("montant");
            }
        }
        return "" + nbre;
    }

    public String getsommeMoratoireEncoursEleve(boolean is_finish,String id_cycle) throws SQLException {
        String nbre = "0";
        java.sql.Date spdate;
        //spdate = new java.sql.Date(date.getTime());
        // System.out.println(" DDDDDDDDDAAte   "+spdate);

        String query = "select sum(m.montant) as montant from tranche_scolarite ts "
                + "inner join moratoire m  "
                + " where ts.id_tranche=? and is_finish=? and ts.etat_tranche='1' and ts.id_tranche=m.id_tranche "
                + "";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_cycle);
        ps.setBoolean(2, is_finish);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                nbre = "" + rs.getFloat("montant");
            }
        }
        return "" + nbre;
    }

    public String getTranchepayeParEleve(JTable jt) throws SQLException {
        String nbre = "0";
        String query = "select * from tranche_scolarite ts inner join cycle c where "
                + "ts.id_cycle=c.id_cycle and ts.id_etab=? and ts.etat_tranche='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        // ps.setString(2, id);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve modelElEnseignant = new modelEleve();
                modelElEnseignant.setidMatiere(rs.getString("id_tranche"));
                modelElEnseignant.setnomMatiere(rs.getString("nom_tranche"));
                modelElEnseignant.setprenom(rs.getString("nom_cycle"));
                modelElEnseignant.setmoyenne(rs.getString("prix"));
                modelElEnseignant.setnote(getnombreElevePayetranche(rs.getString("id_tranche")));
                modelElEnseignant.setsexe(getsommeMoratoireEncoursEleve(false,rs.getString("id_tranche")));

                listeMatiere.add(modelElEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Tranche",
                " Cycle", " Prix ", "Nbre Eleve ayant cmpl. paye ", "moratoire", "Mnt. total", "voir plus"}, 0) {
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
            float v = 0, t = 0;
            int ele = 0, montantMoratoire = 0;
            for (final modelEleve detail : listeMatiere) {
                ele += Integer.parseInt(detail.getNote());
                v += Float.parseFloat(detail.getMoyenne());
                montantMoratoire += Float.parseFloat(detail.getsexe());
                t += Integer.parseInt(detail.getNote()) * Float.parseFloat(detail.getMoyenne());
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(),
                    detail.getprenom(),
                    new formatAmount().montantFormatte(detail.getMoyenne()),
                    detail.getNote(),
                    new formatAmount().montantFormatte(detail.getsexe()),
                    new formatAmount().montantFormatte("" + ((Integer.parseInt(detail.getNote())
                    * Float.parseFloat(detail.getMoyenne())
                    + +Float.parseFloat(detail.getsexe()))))});
                j++;

            }
            model.addRow(new Object[]{"TOTAUX", "", "", "", new formatAmount().montantFormatte("" + v), ele, new formatAmount().montantFormatte("" + montantMoratoire), new formatAmount().montantFormatte("" + (montantMoratoire + t))});

            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(40);
            jt.getColumnModel().getColumn(8).setMaxWidth(50);
            TableColumn testColumn3 = jt.getColumnModel().getColumn(8);

            testColumn3.setCellRenderer(new voirCellRenderer());
            testColumn3.setCellEditor(new trancheEditor(new JCheckBox()));

        }
        return "" + nbre;
    }

    public String getnombreElevePayetranche(String id_cycle) throws SQLException {
        int nbre = 0;
        String query = "select * from scolarite_paie where id_tranche=? and id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_cycle);
        ps.setString(2, idAnneScolaire);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            rs.last();
            nbre = rs.getRow();
            rs.beforeFirst();
        }
        return "" + nbre;
    }

    public String getEleveayantpayetranchs(JTable jt, String idTranche) throws SQLException {
        String nbre = "0";
        String query = "select * from scolarite_paie tp inner join classe c inner join eleve e "
                + " inner join users u where u.id_user=tp.id_user and "
                + "c.id_classe=tp.id_classe and e.id_eleve=tp.id_eleve"
                + " and tp.id_annee=? and tp.id_tranche=? order by nom_classe,nom_eleve";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, getIdtranche(idTranche));
        // ps.setString(2, id);
        System.out.println("//////" + ps);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve modelElEnseignant = new modelEleve();
                modelElEnseignant.setidMatiere(rs.getString("id_tranche"));
                modelElEnseignant.setnomMatiere(rs.getString("nom_eleve") + ""
                        + " " + rs.getString("prenom_eleve"));
                modelElEnseignant.setprenom(rs.getString("nom_user"));
                modelElEnseignant.setmoyenne(rs.getString("date_creation_s"));
                modelElEnseignant.setnote((rs.getString("nom_classe")));
                listeMatiere.add(modelElEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom et prenom eleve",
                "classe", " inscrit par ", " date inscription "}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column < 2) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;

            for (final modelEleve detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getNote(),
                    detail.getprenom(),
                    detail.getMoyenne()});
                j++;

            }

            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(40);

        } else {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom et prenom eleve",
                "classe", " inscrit par ", " date inscription "}, 0);
            jt.setModel(model);
            new message().info("Aucun enregistrement trouve");
        }
        return "" + nbre;
    }

    public String getEleveinsolvables(JTable jt, String idTranche) throws SQLException {
        String nbre = "0";
        String query = "select distinct e.id_eleve,nom_eleve,prenom_eleve,nom_classe from "
                + "scolarite_paie tp inner join eleve_classe c"
                + " inner join eleve e inner join classe cl "
                + " where cl.id_classe=c.id_classe and e.id_eleve=c.id_eleve and "
                + " e.id_eleve not in (tp.id_eleve)"
                + " and tp.id_annee=? and tp.id_tranche=? order by nom_classe,nom_eleve";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, getIdtranche(idTranche));
        // ps.setString(2, id);
        System.out.println("//////" + ps);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve modelElEnseignant = new modelEleve();
                modelElEnseignant.setidMatiere(rs.getString("id_eleve"));
                modelElEnseignant.setnomMatiere(rs.getString("nom_eleve") + ""
                        + " " + rs.getString("prenom_eleve"));
                modelElEnseignant.setnote((rs.getString("nom_classe")));
                listeMatiere.add(modelElEnseignant);
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom et prenom eleve",
                "classe", " inscrit par ", " date inscription "}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean test = true;
                    if (column < 2) {
                        test = false;
                        return test;
                    }
                    return test;
                }
            };
            int j = 1;

            for (final modelEleve detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getNote(),
                    detail.getprenom(),
                    detail.getMoyenne()});
                j++;

            }

            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(40);

        } else {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom et prenom eleve",
                "classe", " inscrit par ", " date inscription "}, 0);
            jt.setModel(model);
            new message().info("Aucun enregistrement trouve");
            return null;
        }
        return "" + nbre;
    }

    public boolean checkCle(String cle) {
        int p = chrd();
        if (p == 1427) {
            p = chrd();
        }

        return false;
    }

    public int chrd() {
        Random id = new Random();
        return id.nextInt(9272);
    }

    public int UpdateCle(String cycle) throws SQLException {
        String query = "update pass set etat='0' where id_pass=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, prf.get("id_pass", null));
        int s = ps.executeUpdate();
        if (s > 0) {
            //new message().warning("Impossible de poursuivre", "Desole, mais ce cycle existe deja");
            return 1;
        } else {
            // insercycle(cycle);
        }
        return 0;
    }

    public String AddCle() throws SQLException {
        int nom = chrd();
        String sqlInsert = "insert into pass (id_key,id_etab,struct,etat) "
                + "values (?,?,?,'1')";
        String cle = nom + "-" + chrd();
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, "" + nom);
        ps.setString(2, idEtablissement);
        ps.setString(3, cle);

        int t = ps.executeUpdate();
        if (t == 1) {
        } else {
        }
        return cle;
    }

    public String getCleAinserer() throws SQLException {
        int nbre = 0;
        String query = "select * from pass where"
                + " id_etab=? and etat='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        //ps.setString(2, idAnneScolaire);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                prf.put("cle", rs.getString("id_key"));
                prf.put("id_pass", rs.getString("id_pass"));
                return rs.getString("struct");
            }
        }
        return "" + nbre;
    }

    public int checkcle(String id, String cle) throws SQLException, NoSuchAlgorithmException {
        String query = "select * from cle where "
                + "id_cle=?  and hash_cle=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, new sha1().sha1(cle));
        ResultSet rs = ps.executeQuery();
        System.err.println("Requete ----?>>>>> " + ps);
        if (rs.isBeforeFirst()) {
            UpdateCle(id);
            ArchiverAnnee();
        } else {
            new message().error("Erreur survenue", "Cle Incorrecte");
        }
        return 0;
    }

    public void ArchiverAnnee() throws SQLException {
        String sqlInsert = "insert into annee_ecoulee (id_etab,id_annee) "
                + "values (?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(2, idAnneScolaire);
        ps.setString(1, idEtablissement);

        int t = ps.executeUpdate();
        if (t == 1) {
            UpdateAnneeScolaireEtablissement();
        } else {
            new message().error("Erreur", "Erreur Iconnue survenue");
        }
    }

    public int UpdateAnneeScolaireEtablissement() throws SQLException {
        String query = "update etablissement set id_annee=id_annee+1 where id_etab=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        int s = ps.executeUpdate();
        if (s > 0) {
            new message().info("passage reussie avec succes  ! \nVotre "
                    + "application Bestschool va redemarrer pour\nPouvoir activer les modifications");
            System.exit(0);
            new login().setVisible(true);
            return 1;
        } else {
            new message().error("Erreur", "Erreur Iconnue survenue");
        }
        return 0;
    }
}
