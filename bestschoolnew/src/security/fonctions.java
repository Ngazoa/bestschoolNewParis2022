/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import etab.dashbord;
import etab.message;
import etablissement.Curseur;
import etablissement.impressionmodel_11;
import etablissement.memoire;
import model.login;
import model.modelClasse;
import model.modelEleve;
import model.modelEnseignant;
import org.json.JSONException;
import paie.personnel.sha1;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * @author ASSUS GAMER
 */
public class fonctions {

    public static TreeMap<Integer, String> mapPays = new TreeMap<>();
    public static TreeMap<Integer, String> enseignantMap = new TreeMap<>();
    public static TreeMap<Integer, String> matiereMap = new TreeMap<>();
    public static TreeMap<Integer, String> rangClasseMap = new TreeMap<>();
    public static TreeMap<Integer, String> ClasseMap = new TreeMap<>();
    public static JTable moratoireJtable;
    //Properties pt = new Properties();
    static String idEtablissement, idAnneScolaire;
    static String nom_anneeScolaire;
    mysqli mysqli;
    //Preferences prf = Preferences.userRoot().node(getClass().getName());
    Preferences prf = Preferences.userNodeForPackage(fonctions.class);

    public fonctions() {
        mysqli = new mysqli();
        if (mysqli.connect() == null) {
            new message().error("Erreur de connexion", "Impossible d'etablir la connexion avec\n le serveur distant");
            System.exit(0);
        }
    }

    public void loadData() throws SQLException {
        System.err.println("/*-/*- " + idAnneScolaire + " ***>>> " + idEtablissement);
        JComboBox matiere = new JComboBox(), classe = new JComboBox(), annee = new JComboBox(), ens = new JComboBox();
        getAllYears();
        getmatiere();
        getAllClasseJcombo();
        enseignantPOurAjouMatiere();
        getconseil();
    }

    public void editionAbsence(String id, String pass) throws SQLException {
        String sqlInsert = "update absence set etat_ab='0',motif=?,id_justiifer=?  where id_absence=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, pass);
        ps.setString(3, id);
        ps.setString(2, prf.get("id_user", ""));
        System.out.println("<<<<<<<<<<<" + ps);
        int p = ps.executeUpdate();
        if (p == 1) {
            new message().info("Edition  reussie");
            RefreshDashbord();
        } else {
            new message().error("erreur", "Erreur Iconnue survenue !");
        }
        mysqli.close();

    }

    public void editionpass(String id, String pass) throws SQLException {
        String sqlInsert = "update users set password=?  where id_user=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, pass);
        ps.setString(2, id);
        System.out.println("<<<<<<<<<<<" + ps);
        int p = ps.executeUpdate();
        if (p == 1) {
            new message().info("Edition  reussie");
            RefreshDashbord();
        } else {
            new message().error("erreur", "Erreur Iconnue survenue !");
        }
        mysqli.close();

    }

    public void changepassword(String id, String ancienpass, String password) throws NoSuchAlgorithmException, SQLException {

        //login = "beni@mail.com";
        // password = "aaaa";
        String query = "select * from users u inner join etablissement e"
                + " inner join annee a where u.id_user=? and u.Password=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, new sha1().sha1(ancienpass));

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {

            editionpass(id, new sha1().sha1(password));

        } else {
            new message().info("Ancien mot de passe Incorrect ");
        }
    }

    public boolean login(String login, String password) throws NoSuchAlgorithmException {

        //login = "beni@mail.com";
        // password = "aaaa";
        try {
            String query = "select * from users u inner join etablissement e"
                    + " inner join annee a where u.login=? and u.Password=? and u.id_etab=e.id_etab and "
                    + " a.id_annee=e.id_annee and etat_user='1'";
            PreparedStatement ps = mysqli.connect().prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, new sha1().sha1(password));

            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {

                while (rs.next()) {
                    idAnneScolaire = rs.getString("id_annee");
                    idEtablissement = rs.getString("id_etab");
                    memoire.anne_scolaire = rs.getString("nom_annee");
                    memoire.ecole = rs.getString("nom_etab");
                    memoire.bp = rs.getString("bp");
                    memoire.logo = new login().getLinkPhotosEleves() + rs.getString("logo");
                    memoire.carte = new login().getLinkPhotosEleves() + rs.getString("carte");
                    memoire.filigran = new login().getLinkPhotosEleves() + rs.getString("filigran");
                    memoire.signature = new login().getLinkPhotosEleves() + rs.getString("signature");
                    prf.put("id_annee", rs.getString("id_annee"));
                    prf.put("nom_annee", rs.getString("nom_annee"));
                    prf.put("id_etab", rs.getString("id_etab"));
                    prf.put("nom_etab", rs.getString("nom_etab"));
                    prf.put("contact_etab", rs.getString("contact"));
                    prf.put("bp", rs.getString("bp"));
                    prf.put("principal", rs.getString("principal"));
                    prf.put("nom_user", rs.getString("nom_user"));
                    prf.put("prenom_user", rs.getString("prenom_user"));
                    prf.put("id_user", rs.getString("id_user"));
                    prf.put("role", rs.getString("role"));
                    prf.put("login", rs.getString("login"));

                    //risqe d' erreur si pas present
                    // prf.put("avatar", rs.getString("avatar"));
                    prf.put("logo", rs.getString("logo"));
                    prf.put("filigran", rs.getString("filigran"));
                    prf.put("signature", rs.getString("signature"));
                    System.out.println("Logo    *** " + memoire.logo);

                    if ("enseignant".equals(rs.getString("role"))) {
                        prf.put("id_enseignant", rs.getString("id_enseignant"));
                    } else if ("censeur".equals(rs.getString("role"))) {
                        prf.put("id_principal", rs.getString("id_principal"));
                    } else if ("eleve".equals(rs.getString("role"))) {
                        prf.put("id_eleve", rs.getString("id_eleve"));
                    } else if ("admin".equals(rs.getString("role"))) {
                        //prf.put("id_censeur", rs.getString("id_censeur"));
                    }
                }

                return true;
            } else {
            }

        } catch (SQLException ex) {
            Logger.getLogger(mysqli.class.getName()).log(Level.SEVERE, null, ex);
        }
        mysqli.close();
        return false;
    }

    public void modifierPhotosEtablissement(String id, String lo, String fi, String si, String car) throws IOException, JSONException {
        if (id == null) {
            id = idEtablissement;
        }
        String url = new login().getLinkPhotosEleves() + "editEtablissementPhotos.php";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = "id=" + id + "&logo=" + lo + "&filigran=" + fi + "&signature=" + si + "&carte=" + car;
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        System.out.println(urlParameters);
        String inputLine, inline2 = null;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }
    }

    public void addNewEtablissement(String nom, String principal, String bp,
                                    String phone, String idAnnee,
                                    String lo, String fi, String car, String si, String th) throws SQLException,
            NoSuchAlgorithmException, IOException, JSONException {
        if (checkUser(phone) != 1) {
            String sqlInsert = "insert into etablissement (id_annee, nom_etab, contact,"
                    + "bp,principal) "
                    + "values (?,?,?,?,?)";
            PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
            ps.setString(1, idAnnee);
            ps.setString(2, nom);
            ps.setString(3, phone);
            ps.setString(4, bp);
            ps.setString(5, principal);

            int countInserted = ps.executeUpdate();
            if (countInserted == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    System.err.println(">> id etab " + rs.getLong(1));
                    int resultat = addEtabAsUser(nom, principal, phone, phone, phone, null,
                            null, null, null, "" + rs.getLong(1), phone, "etablissement");

                    if (resultat == 1) {
                        modifierPhotosEtablissement("" + rs.getLong(1), lo, fi, si, car);
                        new message().info(" Creation de l'etablissement reussie avec success.. merci " + nom);
                    } else {
                        new message().error("erreur creation", "erreur survenue lors de l'enregistrement priere de reessayer plus tard");
                    }
                } else {
                    new message().error("erreur creation", "erreur survenue lors de l'enregistrement priere de reessayer plus tard");
                }
            }
        } else {
            new message().error("erreur de duplicata", "Desole, mais ce numero de telephone est deja utilise dans le systeme");
        }
        mysqli.close();

    }

    public void addNewEnseignant(String nom, String prenom, String phone, String id_etab, String sexe, String role) throws SQLException, NoSuchAlgorithmException {
        if (checkUser(phone) != 1) {

            String sqlInsert = "insert into enseignant ("
                    + "telephone,id_etab) "
                    + "values (?,?)";
            PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
            ps.setString(1, phone);
            ps.setString(2, idEtablissement);

            int countInserted = ps.executeUpdate();
            if (countInserted == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int resultat = addUser(nom, prenom, sexe, phone, phone, "" + rs.getLong(1),
                            null, null, null, null, phone, role);
                    if (resultat == 1) {
                        new message().info(" Creation de l'enseignant  | " + nom + " " + prenom + " |  reussie avec success.. merci ");
                    } else {
                        new message().error("erreur creation", "erreur survenue lors de l'enregistrement priere de reessayer plus tard");
                    }
                } else {
                    new message().error("erreur creation", "erreur survenue lors de l'enregistrement priere de reessayer plus tard");
                }
            }
        } else {
            new message().error("erreur de duplicata", "Desole, mais ce numero de telephone est deja utilise dans le systeme");
        }
        mysqli.close();

    }

    public int addEleve(String nom, String prenom, String sexe, Date date,
                        String lieu, String classe, String pere, String mere, String telephone,
                        String redoublant) throws SQLException, NoSuchAlgorithmException {
        int t = insertEleveTodatabase(nom, prenom, sexe, date, lieu,
                getIClasse(classe), pere, mere, telephone, redoublant);
        mysqli.close();

        return t;

    }

    public void EditerEleve(String nom, String prenom, String sexe, Date date,
                            String lieu, String classe, String pere, String mere, String telephone,
                            String redoublant, String ideleve) throws SQLException, NoSuchAlgorithmException {
        java.sql.Date spdate = new java.sql.Date(date.getTime());

        String sqlInsert = "update eleve set nom_eleve=?, prenom_eleve=?"
                + " ,date_naissance=?, lieu=?,sexe_eleve=?,pere=?,"
                + "mere=?,telephone=? where id_eleve=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setDate(3, spdate);
        ps.setString(4, lieu);
        ps.setString(5, sexe);
        ps.setString(6, pere);
        ps.setString(7, mere);
        ps.setString(8, telephone);
        ps.setString(9, ideleve);
        System.out.println("<<>>>>>" + ps);

        int s = ps.executeUpdate();
        if (s == 1) {
            String sqlInsert2 = "update eleve_classe set id_classe=?"
                    + ",redoublant=? where id_eleve=? and id_annee=? ";
            PreparedStatement p = mysqli.connect().prepareStatement(sqlInsert2);
            p.setString(1, getIClasse(classe));
            p.setString(2, redoublant);
            p.setString(3, ideleve);
            p.setString(4, idAnneScolaire);
            int s2 = p.executeUpdate();
            if (s2 == 1) {
                new message().info("Eleve modifie avec succes");
            }

        }
        mysqli.close();

    }

    public void changeIdAnneePourAchivage(String s, String nomAnn) {
        idAnneScolaire = s;
        memoire.anne_scolaire = nomAnn;
        new message().info("Votre annee " + nomAnn + " \nest desormais active vous pouvez y travailler\n"
                + "Mais pour revenir a l'annee en cours\n"
                + "Vous devriez redemarrer votre logiciel");
    }

    private int insertEleveTodatabase(String nom, String prenom, String sexe, Date date,
                                      String lieu, String classe, String pere, String mere, String telephone,
                                      String redoublant) throws SQLException, NoSuchAlgorithmException {
        java.sql.Date spdate;
        spdate = new java.sql.Date(date.getTime());
        String sqlInsert = "insert into eleve (nom_eleve,prenom_eleve,"
                + "date_naissance,lieu,sexe_eleve,pere,mere,telephone,id_etab) "
                + "values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setDate(3, spdate);
        ps.setString(4, lieu);
        ps.setString(5, sexe);
        ps.setString(6, pere);
        ps.setString(7, mere);
        ps.setString(8, telephone);
        ps.setString(9, idEtablissement);
        int s = ps.executeUpdate();
        if (s == 1) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                addUser(nom, prenom, sexe, "BS-" + rs.getLong(1), "BS-" + rs.getLong(1), null, "" + rs.getLong(1),
                        null, null, null, telephone, "eleve");
            }
            addEleveClasse(nom + " " + prenom, "" + rs.getLong(1), classe, redoublant);
        }
        mysqli.close();
        return 0;
    }

    private int addUser(String nom, String prenom, String sexe, String login, String password,
                        String idEnsei, String idEleve, String idPrincipal, String idCenseur, String idEtab, String telephone, String role) throws SQLException, NoSuchAlgorithmException {
        idEtab = prf.get("id_etab", null);
        String sqlInsert = "insert into users (nom_user,prenom_user,sexe_user,login,password,id_enseignant,"
                + "id_eleve,id_principal,id_censeur,id_etab,telephone,role) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setString(3, sexe);
        ps.setString(4, login);
        ps.setString(5, new sha1().sha1(password));
        ps.setString(6, idEnsei);
        ps.setString(7, idEleve);
        ps.setString(8, idPrincipal);
        ps.setString(9, idCenseur);
        ps.setString(10, idEtablissement);
        ps.setString(11, telephone);
        ps.setString(12, role);

        int countInserted = 0;
        countInserted = ps.executeUpdate();
        mysqli.close();

        return countInserted;

    }

    private int addEtabAsUser(String nom, String prenom, String sexe, String login, String password,
                              String idEnsei, String idEleve, String idPrincipal, String idCenseur, String idEtab, String telephone, String role) throws SQLException, NoSuchAlgorithmException {

        String sqlInsert = "insert into users (nom_user,prenom_user,sexe_user,login,password,id_enseignant,"
                + "id_eleve,id_principal,id_censeur,id_etab,telephone,role) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setString(3, sexe);
        ps.setString(4, login);
        ps.setString(5, new sha1().sha1(password));
        ps.setString(6, idEnsei);
        ps.setString(7, idEleve);
        ps.setString(8, idPrincipal);
        ps.setString(9, idCenseur);
        ps.setString(10, idEtab);
        ps.setString(11, telephone);
        ps.setString(12, role);

        int countInserted = 0;
        countInserted = ps.executeUpdate();
        System.out.println("<<<< " + ps);
        mysqli.close();

        return countInserted;

    }

    private void addEleveClasse(String nom, String id_eleve, String id_classe,
                                String redoublant) throws SQLException {
        String sqlInsert = "insert into eleve_classe ("
                + " id_eleve,id_classe,redoublant,id_annee) "
                + "values (?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, id_eleve);
        ps.setString(2, id_classe);
        ps.setString(3, redoublant);
        ps.setString(4, idAnneScolaire);
        int s = ps.executeUpdate();
        if (s == 1) {
            new message().info("eleve | " + nom + " | ajoute avec succees");
        } else {
            new message().error("erreur", "Impossible de sauvegarder cette eleve");
        }
        mysqli.close();

    }

    public void getAllYears() {
        try {
            String query = "select * from annee order by rang asc";
            PreparedStatement ps = mysqli.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    mapPays.put(rs.getInt("id_annee"), rs.getString("nom_annee"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(mysqli.class.getName()).log(Level.SEVERE, null, ex);
        }
        mysqli.close();

    }

    public ArrayList<String> getAllYears(JComboBox jc) {
        jc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"select"}));
        try {
            String query = "select * from annee order by rang asc";
            PreparedStatement ps = mysqli.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    //anne.add(rs.getString("nom_annee"));
                    jc.addItem(rs.getString("nom_annee"));
                    mapPays.put(rs.getInt("id_annee"), rs.getString("nom_annee"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(mysqli.class.getName()).log(Level.SEVERE, null, ex);
        }
        mysqli.close();
        return null;
    }

    public void addMatiere(JTable jt) throws SQLException {
        int count = jt.getRowCount();
        String nomMatiere;
        int result = 0;
        String succes = "Matiere(s) creee(s) avec succes:\n\n|";
        for (int i = 0; i < count; i++) {
            if (jt.getValueAt(i, 1) != null && jt.getValueAt(i, 1) != "") {
                nomMatiere = jt.getValueAt(i, 1).toString();
                insertMatiereDb(nomMatiere);
                succes += nomMatiere + "\n";
                result++;
            }
        }
        if (result > 0) {
            new message().info(succes);
        } else {
            new message().info("Desole, mais aucune Matiere retrouvee \npour continuer l'enregistrement");
        }
        mysqli.close();

    }

    public void editionClasse(String id, String nom, String cycle, String rang) throws SQLException {
        String sqlInsert = "update classe set nom_classe=? , id_cycle=? , rang=? where id_classe=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nom);
        ps.setString(2, cycle);
        ps.setString(3, rang);
        ps.setString(4, id);
        System.out.println("<<<<<<<<<<<" + ps);
        int p = ps.executeUpdate();
        if (p == 1) {
            new message().info("Edition  reussie");
            RefreshDashbord();
        } else {
            new message().error("erreur", "Erreur Iconnue survenue !");
        }
        mysqli.close();

    }

    public void edition(String id, String id_table, String table, String etat, String valeur) throws SQLException {
        String sqlInsert = "update " + table + " set " + etat
                + "=? where " + id_table + "=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, valeur);
        ps.setString(2, id);
        System.out.println("<<<<<<<<<<<" + ps);
        int p = ps.executeUpdate();
        if (p == 1) {
            new message().info("Edition  reussie");
            RefreshDashbord();
        } else {
            new message().error("erreur", "Erreur Iconnue survenue !");
        }
        mysqli.close();

    }

    public void delete(String id, String id_table, String table, String etat) throws SQLException {
        String sqlInsert = "update " + table + " set " + etat
                + "='0' where " + id_table + "=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, id);
        System.out.println("<<<<<<<<<<<" + ps);
        int p = ps.executeUpdate();
        if (p == 1) {
            new message().info("Suppression reussie");
            RefreshDashbord();
        } else {
            new message().error("erreur", "Erreur Iconnue survenue !");
        }
        mysqli.close();

    }

    public void creationclasse(String nomclasse, JTable jt, String cycle, String rang) throws SQLException {
        if (checkClasse(nomclasse) != 1) {
            if (cherckjtableError(jt)) {
                String sqlInsert = "insert into classe (nom_classe,id_etab,id_cycle,rang) "
                        + "values (?,?,?,?)";
                PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
                ps.setString(1, nomclasse);
                ps.setString(2, idEtablissement);
                ps.setString(3, cycle);
                ps.setString(4, rang);
                int s = ps.executeUpdate();
                System.err.println("<<<>>>>>" + s);
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    addjtableElementoDatabaseAfterCheck(jt, "" + rs.getLong(1));
                    new message().info(nomclasse + " Cree avec succes");
                } else {
                    new message().error("erreur inconnu", "erreur inconnu veuillez reessayer plus tard");
                }
            } else {
                new message().warning("Impossible de continuer", "Veuillez selectionner tous les elements\n"
                        + "dont vous avez cochez pour ajouter avant de continuer");
            }
        } else {
            new message().error("erreur", "Desole,mais ce nom de classe \nexiste deja dans votre etablissement");
        }
        mysqli.close();

    }

    public boolean addjtableElementoDatabaseAfterCheck(JTable jt, String classe) throws SQLException {
        int t = jt.getRowCount();
        for (int i = 0; i < t; i++) {
            boolean val = Boolean.valueOf(jt.getValueAt(i, 1).toString());
            if (val) {
                if (jt.getValueAt(i, 3).toString() != "select" && jt.getValueAt(i, 4).toString() != "select"
                        && jt.getValueAt(i, 5).toString() != "select" && jt.getValueAt(i, 6).toString() != "select") {
                    String sqlInsert = "insert into matiere_classe_enseignant (id_classe,id_matiere,"
                            + "id_enseignant,groupe,coeff,horaire,id_annee) "
                            + "values (?,?,?,?,?,?,?)";
                    PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
                    ps.setString(1, classe);
                    ps.setString(2, getIdmatier(jt.getValueAt(i, 2).toString()));
                    ps.setString(3, getIdenseignant(jt.getValueAt(i, 3).toString()));
                    ps.setString(4, jt.getValueAt(i, 4).toString());
                    ps.setString(5, jt.getValueAt(i, 5).toString());
                    ps.setString(6, jt.getValueAt(i, 6).toString());
                    ps.setString(7, idAnneScolaire);
                    int s = ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    //addMatiereClasse(classe, getIdmatier(jt.getValueAt(i, 2).toString()));
                } else {
                    return false;
                }
            }
        }
        mysqli.close();

        return true;
    }

    public void addCoseilClasse(String mosup, String moytb, String moyenexcl, String moyenred, String heure) throws SQLException {
        String query = "insert into detail_conseil ("
                + "tableau_honneur,admission,exclusion,redouble,heure,id_etab)"
                + "values(?,?,?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, moytb);
        ps.setString(2, mosup);
        ps.setString(3, moyenexcl);
        ps.setString(4, moyenred);
        ps.setString(5, heure);
        ps.setString(6, idEtablissement);
        if (ps.executeUpdate() > 0) {
            new message().info("Conseil ajoute avec succes");
        }
    }

    public String getconseil()
            throws SQLException {
        String details = "";
        String query = "select * from detail_conseil where id_etab=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                System.out.println(">>>>>>>\\\\|||||||||||||||| " + rs.getFloat("tableau_honneur"));
                prf.putFloat("tableau_honneur", rs.getFloat("tableau_honneur"));
                prf.putFloat("admission", rs.getFloat("admission"));
                prf.putFloat("redouble", rs.getFloat("redouble"));
                prf.putFloat("exclusion", rs.getFloat("exclusion"));
                prf.putFloat("heure", rs.getFloat("heure"));
                System.err.println(">>>><<<<<<< " + prf.getFloat("tableau_honneur", 0));

            }
        } else {
            details = "Attention  Conseil  Non Defini\nCeci pourra entrainer des problemes plustard\nmerci de le definir";

        }
        return details;
    }

    public String getCustumeconseil()
            throws SQLException {
        String details = "";
        String query = "select * from detail_conseil where id_etab=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                details += "Moyen tab honneur. | " + rs.getString("tableau_honneur") + "\n";
                details += "Moyen. admission | " + rs.getString("admission") + "\n";
                details += "Moyen. redouble | " + rs.getString("redouble") + "\n";
                details += "Moyen. exclusion | " + rs.getString("exclusion") + "\n";
                details += "Nbre heures exlusion | " + rs.getString("heure") + "\n";
            }
        } else {
            details = "Conseil Non Defini";

        }
        return details;
    }

    public void Custumeconseil(String mosup, String moytb, String moyenexcl, String moyenred, String heure)
            throws SQLException {

        String query = "select * from detail_conseil where id_etab=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {

            updateCoseilClasse(mosup, moytb, moyenexcl, moyenred, heure);
        } else {
            addCoseilClasse(mosup, moytb, moyenexcl, moyenred, heure);
        }

    }

    public void updateCoseilClasse(String mosup, String moytb, String moyenexcl, String moyenred, String heure) throws SQLException {
        String query = "update detail_conseil set "
                + "tableau_honneur=?,admission=?,exclusion=?,redouble=?"
                + ",heure=? where id_etab=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, moytb);
        ps.setString(2, mosup);
        ps.setString(3, moyenexcl);
        ps.setString(4, moyenred);
        ps.setString(5, heure);
        ps.setString(6, idEtablissement);
        if (ps.executeUpdate() > 0) {
            new message().info("Conseil ajoute avec succes");
        }
    }

    public void addMatiereClas(String idClasse, String idMatiere) throws SQLException {
        String query = "insert into matiere_classe (id_classe,id_matiere,id_annee)"
                + "values(?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idClasse);
        ps.setString(2, idMatiere);
        ps.setString(3, idAnneScolaire);
        ps.executeUpdate();
    }

    public int editMatiereClasse(String classe, String matiere, String id_enseignant,
                                 String groupe, String coeff, String horaire, String code) throws SQLException {
        String query = "update matiere_classe_enseignant "
                + "set id_classe=?, id_matiere=?,id_enseignant=? ,coeff=?"
                + ",groupe=?,horaire=? where id_m_c=? and id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, getIClasse(classe));
        ps.setString(2, getIdmatier(matiere));
        ps.setString(3, getIdenseignant(id_enseignant));
        ps.setString(4, coeff);
        ps.setString(5, groupe);
        ps.setString(6, horaire);
        ps.setString(7, code);
        ps.setString(8, idAnneScolaire);
        System.out.println("<<<<<<<<" + ps);
        int rs = ps.executeUpdate();
        if (rs == 0) {
            return 0;
        }
        return 1;
    }

    public int checkMatiere(String classe, String matiere, String id_enseignant,
                            String groupe, String coeff, String horaire) throws SQLException {
        String query = "select * from matiere_classe_enseignant where "
                + "id_classe=?  and id_matiere=? and  etat_mc='1' and id_enseignant=? and id_annee=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, classe);
        ps.setString(2, getIdmatier(matiere));
        ps.setString(3, getIdenseignant(id_enseignant));
        ps.setString(4, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            return 0;
        }
        insertmatiereClasse(classe, matiere, id_enseignant, groupe, coeff, horaire);
        return 1;
    }

    public void insertmatiereClasse(String classe, String matiere, String id_enseignant,
                                    String groupe, String coeff, String horaire) throws SQLException {
        String sqlInsert = "insert into matiere_classe_enseignant (id_classe,id_matiere,"
                + "id_enseignant,groupe,coeff,horaire,id_annee) "
                + "values (?,?,?,?,?,?,?)";
        System.out.println("------>>>>" + memoire.id_absence);
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, classe);
        ps.setString(2, getIdmatier(matiere));
        ps.setString(3, getIdenseignant(id_enseignant));
        ps.setString(4, groupe);
        ps.setString(5, coeff);
        ps.setString(6, horaire);
        ps.setString(7, idAnneScolaire);
        System.out.println("------>>>>" + ps);

        int s = ps.executeUpdate();
    }

    public boolean cherckjtableError(JTable jt) {
        int t = jt.getRowCount();
        for (int i = 0; i < t; i++) {
            boolean val = Boolean.valueOf(jt.getValueAt(i, 1).toString());
            if (val) {
                if (jt.getValueAt(i, 3).toString() != "select" && jt.getValueAt(i, 4).toString() != "select"
                        && jt.getValueAt(i, 5).toString() != "select" && jt.getValueAt(i, 6).toString() != "select") {
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void insertMatiereDb(String nomMatiere) throws SQLException {
        //String idEtab = pt.getProperty("id_etab", null);
        String sqlInsert = "insert into matiere (nom_matiere,id_etab) "
                + "values (?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nomMatiere);
        ps.setString(2, idEtablissement);
        ps.executeUpdate();
        //RefreshDashbord();
    }

    public void addMatiereClasseExistant(JComboBox matiere, JComboBox enseignant, JComboBox goupe,
                                         JComboBox coeff, JComboBox horaire) throws SQLException {
        getmatiere(matiere);
        enseignantPOurAjouMatiere(enseignant);
        CoeffMatieres1(coeff);
        groupeMatieres1(goupe);
        CotatMatieres(horaire);
        RefreshDashbord();
    }

    public int getMatiereCreationClasse(JTable jt) throws SQLException {
        //String idEtab = pt.getProperty("id_etab", null);
        String query = "select * from matiere where id_etab=? and etat_matiere not in(?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, "0");
        ResultSet rs = ps.executeQuery();
        int i = 0;
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_matiere"));
                matiere.setnomMatiere(rs.getString("nom_matiere"));
                matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                i++;
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Ajouter", "Matiere", "Enseignant", "Coeff", "Groupe", "Cotat horaire semaine"}, 0) {
                    @Override
                    public Class getColumnClass(int columnIndex) {
                        if (columnIndex == 1) {
                            return Boolean.class;
                        }
                        return super.getColumnClass(columnIndex);
                    }

                    public boolean isCellEditable(int row, int column) {
                        boolean test = true;
                        if (column == 2) {
                            test = false;
                            return test;
                        }
                        return test;
                    }
                };
                int j = 1;
                for (final modelClasse detail : listeMatiere) {
                    model.addRow(new Object[]{j, false, detail.getNommatiere(), "select", "select", "select", "select"});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(0).setResizable(false);
                jt.getColumnModel().getColumn(4).setMaxWidth(60);
                jt.getColumnModel().getColumn(5).setMaxWidth(60);
                TableColumn testColumn = jt.getColumnModel().getColumn(3);
                TableColumn testColumn2 = jt.getColumnModel().getColumn(4);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(5);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(6);
                //testColumn1.setCellEditor(new DefaultCellEditor(boolean));
                testColumn.setCellEditor(new DefaultCellEditor(ensei));
                testColumn2.setCellEditor(new DefaultCellEditor(CoeffMatieres()));
                testColumn3.setCellEditor(new DefaultCellEditor(groupeMatieres()));
                testColumn4.setCellEditor(new DefaultCellEditor(CotatMatieres()));

            } else {
                new message().error("erreur enseignant", "Veuillez creer des enseignants avant de continuer\nMerci ");
            }
        } else {
            new message().error("erreur Matiere", "Veuillez creer des ematieres avant de continuer\nMerci ");
        }
        return i;
    }

    public void editAppreciation(JTable jt, String id, String nomApp, String bi, String bs) throws SQLException {

        String sqlInsert = "update apreciation_note set nom_apreciation=?,borne_inf =? "
                + ",borne_sup=? where id_apreciation_note=? "
                + "values (?,?,?,?)";
        PreparedStatement p = mysqli.connect().prepareStatement(sqlInsert);
        p.setString(1, nomApp);
        p.setString(2, bi);
        p.setString(3, bs);
        p.setString(4, id);
        if (p.executeUpdate() == 1) {
            new message().info("Modifie avec succes");
        } else {
            new message().error("erreur inconnu", "Erreur inatendu survenue");
        }
    }

    public void checkAppreciation(String nomApp, String bi, String bs) throws SQLException {

        String query = "select * from apreciation_note where id_etab=? and nom_apreciation=? and etat_ap='1'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, nomApp);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            new message().info("Le nom | " + nomApp + " | existe deja");
        } else {
            String sqlInsert = "insert into apreciation_note (nom_apreciation,borne_inf"
                    + ",borne_sup,id_etab) "
                    + "values (?,?,?,?)";
            PreparedStatement p = mysqli.connect().prepareStatement(sqlInsert);
            p.setString(1, nomApp);
            p.setString(2, bi);
            p.setString(3, bs);
            p.setString(4, idEtablissement);
            if (p.executeUpdate() == 1) {
                new message().info("Appreciation ajoutee avec succes");
            } else {
                new message().info("Erreur inconnu survenue");

            }

        }
    }

    public void getAllAbsencesClasse(String classe, JTable jt) throws SQLException {

        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec inner join total_absences ta where "
                + "ec.etat_ec='1' and e.id_eleve=u.id_eleve and"
                + " e.id_eleve=ta.id_eleve and ec.id_eleve=e.id_eleve "
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
                matiere.setredoublant(rs.getString("heure"));
                matiere.setprenom(rs.getString("prenom_eleve"));
                matiere.setmatricule(rs.getString("login"));
                listeMatiere.add(matiere);

            }
        }

        DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code",
                "Matricule", "Nom & Prenom",
                "Nbre heures", "voir plus"}, 0) {
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
                    detail.getmatricule(), detail.getNommatiere() + " " + detail.getprenom(), detail.getredoublant(),});
            j++;
        }
        jt.setModel(model);
        jt.getColumnModel().getColumn(0).setMaxWidth(30);

        jt.getColumnModel().getColumn(4).setMaxWidth(110);
        jt.getColumnModel().getColumn(1).setMaxWidth(60);
        jt.getColumnModel().getColumn(5).setMaxWidth(100);

        TableColumn testColumn = jt.getColumnModel().getColumn(5);
        testColumn.setCellRenderer(new classeRenderer());
        testColumn.setCellEditor(new absenceCellEditor(new JCheckBox()));

    }

    public void getAllAbsencesClasseJustifiees(String classe, JTable jt) throws SQLException {

        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec inner join absence ta where "
                + "ec.etat_ec='0' and e.id_eleve=u.id_eleve and"
                + " e.id_eleve=ta.id_eleve and ec.id_eleve=e.id_eleve "
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
                matiere.setredoublant(rs.getString("heure"));
                matiere.setprenom(rs.getString("motif"));
                matiere.setmatricule(rs.getString("login"));
                listeMatiere.add(matiere);

            }
        }

        DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code",
                "Matricule", "Nom & Prenom",
                "Nbre heures", "Motif"}, 0) {
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
                    detail.getmatricule(), detail.getNommatiere(), detail.getredoublant(), detail.getprenom()});
            j++;
        }
        jt.setModel(model);
        jt.getColumnModel().getColumn(0).setMaxWidth(30);

        jt.getColumnModel().getColumn(4).setMaxWidth(110);
        jt.getColumnModel().getColumn(1).setMaxWidth(60);
        //jt.getColumnModel().getColumn(5).setMaxWidth(100);

        TableColumn testColumn = jt.getColumnModel().getColumn(5);
        testColumn.setCellRenderer(new classeRenderer());
        testColumn.setCellEditor(new absenceCellEditor(new JCheckBox()));

    }

    public void getAllAbsencesEleves(String id_eleve, JTable jt) throws SQLException {

        String query = "select * from absence a inner join matiere m inner join users u "
                + " where a.id_matiere=m.id_matiere and u.id_user=a.id_user and a.id_annee=? "
                + " and a.id_eleve=? and etat_ab='1' order by id_absence";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(2, id_eleve);
        ps.setString(1, idAnneScolaire);
        ResultSet rs = ps.executeQuery();

        List<modelEleve> listeMatiere = new ArrayList<>();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                modelEleve matiere = new modelEleve();
                matiere.setidMatiere(rs.getString("id_absence"));
                matiere.setnomMatiere(rs.getString("nom_matiere"));
                matiere.setredoublant(rs.getString("heure"));
                matiere.setprenom(rs.getString("nom_user"));
                matiere.setmatricule(rs.getString("date_creation_absence"));
                listeMatiere.add(matiere);

            }
        }

        DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code",
                "Date", "Nom matiere", "Appelant",
                "Nbre heures", "voir plus"}, 0) {
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
        for (final modelEleve detail : listeMatiere) {
            model.addRow(new Object[]{j, detail.getidClasse(),
                    detail.getmatricule(), detail.getNommatiere(), detail.getprenom(), detail.getredoublant(),});
            j++;
        }
        jt.setModel(model);
        jt.getColumnModel().getColumn(0).setMaxWidth(30);

        jt.getColumnModel().getColumn(4).setMaxWidth(110);
        jt.getColumnModel().getColumn(1).setMaxWidth(60);
        jt.getColumnModel().getColumn(6).setMaxWidth(100);

        TableColumn testColumn = jt.getColumnModel().getColumn(6);
        testColumn.setCellRenderer(new classeRenderer());
        testColumn.setCellEditor(new absenceCellEditor(new JCheckBox()));

    }

    public int getAppreciation(JTable jt) throws SQLException {
        //String idEtab = pt.getProperty("id_etab", null);
        String query = "select * from apreciation_note where id_etab=? and etat_ap not in(?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, "0");
        ResultSet rs = ps.executeQuery();
        int i = 0;
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_apreciation_note"));
                matiere.setnomMatiere(rs.getString("nom_apreciation"));
                matiere.setgroupe(rs.getString("borne_inf"));
                matiere.setcoeff(rs.getString("borne_sup"));

                listeMatiere.add(matiere);
                i++;
            }

            DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "code", "Apreciation", "Note min", "Note sup", "Editer", "supprimer"}, 0) {
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
            for (final modelClasse detail : listeMatiere) {
                model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getGroup(), detail.getCoeff()});
                j++;
            }
            jt.setModel(model);
            jt.getColumnModel().getColumn(0).setMaxWidth(30);
            jt.getColumnModel().getColumn(1).setMaxWidth(70);

            jt.getColumnModel().getColumn(4).setMaxWidth(60);
            jt.getColumnModel().getColumn(5).setMaxWidth(60);

            TableColumn testColumn3 = jt.getColumnModel().getColumn(5);
            TableColumn testColumn4 = jt.getColumnModel().getColumn(6);
            //testColumn1.setCellEditor(new DefaultCellEditor(boolean));

            testColumn3.setCellEditor(new appreciationEditor(new JCheckBox()));
            testColumn4.setCellEditor(new appreciationEditor(new JCheckBox()));
            testColumn3.setCellRenderer(new PlusMinusCellRenderer());
            testColumn4.setCellRenderer(new PlusMinusCellRenderer2());

        } else {
            new message().error("erreur de recup", "Veuillez creer des apreciations ");
        }
        return i;
    }

    public String getrandClasse(String value) {
        for (Entry<Integer, String> entry : rangClasseMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return "" + entry.getKey();
            }
        }
        return null;
    }

    public String getIdpays(String value) {
        for (Entry<Integer, String> entry : mapPays.entrySet()) {
            if (entry.getValue().equals(value)) {
                return "" + entry.getKey();
            }
        }
        return null;
    }

    public String getIClasse(String value) {
        for (Entry<Integer, String> entry : ClasseMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return "" + entry.getKey();
            }
        }
        return value;
    }

    public String getIdmatier(String value) {
        for (Entry<Integer, String> entry : matiereMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return "" + entry.getKey();
            }
        }
        return null;
    }

    public String getIdenseignant(String value) {
        for (Entry<Integer, String> entry : enseignantMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return "" + entry.getKey();
            }
        }
        return null;
    }

    public int checkUser(String phone) throws SQLException {
        String query = "select * from users where telephone=? and role not in(?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, phone);
        ps.setString(2, "eleve");
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            return 1;
        }
        return 0;
    }

    public int checkClasse(String phone) throws SQLException {
        String query = "select * from classe where nom_classe=? and  etat_classe='1' and id_etab=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, phone);
        ps.setString(2, idEtablissement);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            return 1;
        }
        return 0;
    }

    private JComboBox enseignant() throws SQLException {
        JComboBox<String> enseignant = new JComboBox<>();
        String query = "select * from enseignant e inner join users u where u.id_etab="
                + "e.id_etab and u.id_etab=? and u.etat_user='1' and u.id_enseignant=e.id_enseignant and role='enseignant'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        System.out.println(">>>>" + ps);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                enseignant.addItem(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
            }
        } else {
            return null;
        }
        return enseignant;
    }

    private JComboBox enseignantPOurAjouMatiere(JComboBox enseignant) throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from enseignant e inner join users u where u.id_etab="
                + "e.id_etab and u.id_etab=? and u.etat_user='1' and u.id_enseignant=e.id_enseignant and role='enseignant'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        System.out.println(">>>>" + ps);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                enseignant.addItem(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
            }
        } else {
            return null;
        }
        return enseignant;
    }

    private void enseignantPOurAjouMatiere() throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from enseignant e inner join users u where u.id_etab="
                + "e.id_etab and u.id_etab=? and u.etat_user='1' and u.id_enseignant=e.id_enseignant and role='enseignant'";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        System.out.println(">>>>" + ps);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                enseignantMap.put(rs.getInt("id_enseignant"), rs.getString("nom_user") + " " + rs.getString("prenom_user"));
            }
        } else {
        }
    }

    private JComboBox getmatiere(JComboBox<String> enseignant) throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from matiere where id_etab=? and etat_matiere not in(?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, "0");
        ResultSet rs = ps.executeQuery();
        int i = 0;
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_matiere"));
                matiere.setnomMatiere(rs.getString("nom_matiere"));
                enseignant.addItem(rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                i++;
            }
        } else {
            return null;
        }
        return enseignant;
    }

    private void getmatiere() throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from matiere where id_etab=? and etat_matiere not in(?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, "0");
        ResultSet rs = ps.executeQuery();
        int i = 0;
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_matiere"));
                matiere.setnomMatiere(rs.getString("nom_matiere"));
                matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                i++;
            }
        } else {

        }

    }

    public void getmatiereClasse(JComboBox<String> enseignant, String classe) throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from matiere_classe_enseignant mc "
                + "inner join matiere m inner join enseignant e inner join users u"
                + " where m.etat_matiere='1' and id_annee=? "
                + "and m.id_matiere=mc.id_matiere and mc.id_enseignant=e.id_enseignant "
                + "and e.id_enseignant=u.id_enseignant "
                + "and mc.etat_mc='1' and mc.id_classe=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, getIClasse(classe));
        System.out.println(">>>>>>>>>>>>>>>>>/////****" + ps);

        ResultSet rs = ps.executeQuery();
        enseignant.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"select"}));
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                // matiere.setidMatiere(rs.getString("id_m_c"));
                matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                enseignant.addItem(rs.getString("nom_matiere"));
                System.out.println(">>>>>>>>>>>>>>>>>" + rs.getString("nom_matiere"));
            }
        }
    }

    public void getmatiereEnseignantClasse(JComboBox<String> enseignant, String classe, String id_eng) throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from matiere_classe_enseignant mc "
                + "inner join matiere m inner join enseignant e inner join users u"
                + " where m.etat_matiere='1' and id_annee=? "
                + "and m.id_matiere=mc.id_matiere and mc.id_enseignant=e.id_enseignant "
                + "and e.id_enseignant=u.id_enseignant "
                + "and mc.etat_mc='1' and mc.id_classe=? and mc.id_enseignant=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, getIClasse(classe));
        ps.setString(3, id_eng);
        System.out.println(">>>>>>>>>>>>>>>>>/////****" + ps);

        ResultSet rs = ps.executeQuery();
        enseignant.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"select"}));
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                // matiere.setidMatiere(rs.getString("id_m_c"));
                matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                enseignant.addItem(rs.getString("nom_matiere"));
                System.out.println(">>>>>>>>>>>>>>>>>" + rs.getString("nom_matiere"));
            }
        }
    }

    public String[] getmatiereClasse(String classe) throws SQLException {
        String[] MatiereParams = null;
        String query = "select * from matiere_classe_enseignant mc "
                + "inner join matiere m inner join enseignant e inner join users u"
                + " where m.etat_matiere='1' and id_annee=? "
                + "and m.id_matiere=mc.id_matiere and mc.id_enseignant=e.id_enseignant "
                + "and e.id_enseignant=u.id_enseignant "
                + "and mc.etat_mc='1' and mc.id_classe=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, getIClasse(classe));
        System.out.println(">>>>>>>>>>>>>>>>>/////****" + ps);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            int taille = 0;
            rs.last();
            taille = rs.getRow();
            MatiereParams = new String[taille];
            while (rs.next()) {
                // matiere.setidMatiere(rs.getString("id_m_c"));
                matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                //enseignant.addItem(rs.getString("nom_matiere"));
                System.out.println(">>>>>>>>>>>>>>>>>" + rs.getString("nom_matiere"));
            }
        }
        return MatiereParams;
    }

    public JComboBox getAllClasseJcombo(JComboBox<String> enseignant) throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from classe c inner join cycle cy"
                + " where c.etat_classe='1' and c.id_etab=? and cy.id_cycle=c.id_cycle";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        enseignant.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"select"}));

        int i = 0;
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_classe"));
                matiere.setnomMatiere(rs.getString("nom_classe"));
                enseignant.addItem(rs.getString("nom_classe"));
                ClasseMap.put(rs.getInt("id_classe"), rs.getString("nom_classe"));
                listeMatiere.add(matiere);
                i++;
            }
        } else {
            new message().info("Veuillez creer au prealabe les classes de l'etablissement");
        }
        return enseignant;
    }

    public JComboBox getAllClasseEnseignantJcombo(JComboBox<String> enseignant, String id_enseignant) throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select distinct nom_classe,mce.id_classe from classe c "
                + " inner join matiere_classe_enseignant mce"
                + " inner join enseignant e where c.etat_classe='1' and mce.id_enseignant=e.id_enseignant"
                + "  and  mce.id_enseignant=? and mce.id_annee=? and "
                + "c.id_classe=mce.id_classe and mce.etat_mc='1' ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_enseignant);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        enseignant.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"select"}));

        int i = 0;
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_classe"));
                matiere.setnomMatiere(rs.getString("nom_classe"));
                enseignant.addItem(rs.getString("nom_classe"));
                ClasseMap.put(rs.getInt("id_classe"), rs.getString("nom_classe"));
                listeMatiere.add(matiere);
                i++;
                System.err.println("CCCCLLLAAASSSE --- " + rs.getString("id_classe"));
            }
        } else {
            new message().info("Veuillez creer au prealabe les classes de l'etablissement");
        }
        return enseignant;
    }

    public void getAllClasseJcombo() throws SQLException {
        //enseignant = new JComboBox<>();
        String query = "select * from classe c inner join cycle cy"
                + " where c.etat_classe='1' and c.id_etab=? and cy.id_cycle=c.id_cycle";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();

        int i = 0;
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_classe"));
                matiere.setnomMatiere(rs.getString("nom_classe"));
                ClasseMap.put(rs.getInt("id_classe"), rs.getString("nom_classe"));
                rangClasseMap.put(rs.getInt("rang"), rs.getString("nom_classe"));
                listeMatiere.add(matiere);
                i++;
            }
        } else {
            new message().info("Veuillez creer au prealabe les classes de l'etablissement");
        }
    }

    private JComboBox groupeMatieres() throws SQLException {
        JComboBox<String> group = new JComboBox<>();
        group.addItem("1");
        group.addItem("2");
        group.addItem("3");
        return group;
    }

    private JComboBox CoeffMatieres() throws SQLException {
        JComboBox<String> group = new JComboBox<>();
        for (int i = 1; i < 18; i++) {
            group.addItem("" + i);
        }
        return group;
    }

    private JComboBox CoeffMatieres1(JComboBox<String> group) throws SQLException {
        //group = new JComboBox<>();
        for (int i = 1; i < 18; i++) {
            group.addItem("" + i);
        }
        return group;
    }

    private JComboBox groupeMatieres1(JComboBox<String> group) throws SQLException {
        //group = new JComboBox<>();
        group.addItem("1");
        group.addItem("2");
        group.addItem("3");
        return group;
    }

    private JComboBox CotatMatieres(JComboBox<String> group) throws SQLException {
        // group = new JComboBox<>();
        for (int i = 1; i < 30; i++) {
            group.addItem("" + i);
        }
        return group;
    }

    private JComboBox CotatMatieres() throws SQLException {
        JComboBox<String> group = new JComboBox<>();
        for (int i = 1; i < 30; i++) {
            group.addItem("" + i);
        }
        return group;
    }

    public void AdminDAshbord(JFrame f, JTable enseignant, JTable matiere, JTable classe) throws SQLException {
        Curseur.startWaitCursor(f);
        getClasseEtablissement(classe);
        getEnseignantEtablissement(enseignant);
        getMatiereEtablissement(matiere);
        Curseur.stopWaitCursor(f);
    }

    public void getAllmatiereEnseignant(JTable jt, String id_enseignant) throws SQLException {
        String query = "select * from matiere m inner join classe c inner join "
                + " matiere_classe_enseignant mc where mc.id_classe=c.id_classe and "
                + "m.id_matiere=mc.id_matiere and  mc.etat_mc='1' and mc.id_enseignant=? and mc.id_annee=?"
                + " order by nom_matiere";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, id_enseignant);
        ps.setString(2, idAnneScolaire);
        System.err.println("DDDDDDDDD ==== " + ps);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_matiere"));
                modelEnseignant.setnom(rs.getString("nom_matiere") + "  ");
                modelEnseignant.setdate(rs.getString("nom_classe"));
                modelEnseignant.setsexe(rs.getString("coeff"));
                modelEnseignant.settelephone(rs.getString("horaire"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(modelEnseignant);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Matiere", "Classe", "coeff", "Cota Hor."}, 0) {
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
                    model.addRow(new Object[]{j, detail.getid(), detail.getNom(), detail.getdate(), detail.getsexe(), detail.gettelephone()});
                    j++;
                }
                jt.setModel(model);
            }
        } else {
            new message().info("Aucune information supplementaire \ntrouvee pour cet enseignant");
        }
    }

    public void getEnseignantEtablissement(JTable jt) throws SQLException {
        String query = "select * from enseignant e inner join users u where u.id_etab="
                + "e.id_etab and u.id_etab=? and u.etat_user='1' and u.id_enseignant=e.id_enseignant"
                + " and role='enseignant' order by nom_user";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_enseignant"));
                modelEnseignant.setnom(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
                modelEnseignant.setdate(rs.getString("date_creation_user"));
                modelEnseignant.setsexe(rs.getString("sexe_user"));
                modelEnseignant.settelephone(rs.getString("u.telephone"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(modelEnseignant);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Enseignant", "sexe", "Telephone", "Date creation", "Modifier", "Supprimer"}, 0) {
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
                    model.addRow(new Object[]{j, detail.getid(), detail.getNom(), detail.getsexe(), detail.gettelephone(), detail.getdate()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(6).setMaxWidth(80);
                jt.getColumnModel().getColumn(7).setMaxWidth(80);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(6);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(7);
                testColumn3.setCellRenderer(new PlusMinusCellRenderer());
                testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
                testColumn3.setCellEditor(new enseignantEditor(new JCheckBox()));
                testColumn4.setCellEditor(new enseignantEditor(new JCheckBox()));

            }
        } else {
            new message().info("Aucun element trouve");
        }
    }

    public void getEnseignantPaieEtablissement(JTable jt) throws SQLException {
        String query = "select * from  users u where "
                + "u.id_etab=? and u.etat_user='1' "
                + " and role not in('eleve') order by nom_user";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelEnseignant> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEnseignant modelEnseignant = new modelEnseignant();
                modelEnseignant.setid(rs.getString("id_enseignant"));
                modelEnseignant.setnom(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
                modelEnseignant.setdate(rs.getString("date_creation_user"));
                modelEnseignant.setsexe(rs.getString("role"));
                modelEnseignant.settelephone(rs.getString("u.telephone"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(modelEnseignant);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom Enseignant", "fonction", "Payer"}, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        boolean test = true;
                        if (column < 3) {
                            test = false;
                            return test;
                        }
                        return test;
                    }
                };
                int j = 1;
                for (final modelEnseignant detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getid(), detail.getNom(), detail.getsexe()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(4).setMaxWidth(80);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(4);

                testColumn3.setCellRenderer(new PayerInscrirepayerCellRenderer());

                testColumn3.setCellEditor(new enseignantEditor(new JCheckBox()));

            }
        } else {
            new message().info("Aucun Personnel  trouve");
        }
    }

    public List<impressionmodel_11> getEleveparclassePourImpression(String classe) throws SQLException {
        List<impressionmodel_11> listeMatiere = new ArrayList<>();

        String query = "SELECT * from eleve e inner join users u "
                + "inner join eleve_classe "
                + " ec where ec.etat_ec='1' and e.id_eleve=u.id_eleve"
                + " and ec.id_eleve=e.id_eleve and ec.id_classe=? "
                + "and ec.id_annee=? order by nom_eleve,prenom_eleve";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, getIClasse(classe));
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        int effec = 0;
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                impressionmodel_11 matiere = new impressionmodel_11();
                matiere.id_eleve(rs.getString("id_eleve"));
                matiere.nom(rs.getString("nom_eleve") + " " + rs.getString("prenom_eleve"));

                matiere.date(rs.getString("date_naissance"));
                matiere.lnaissance(rs.getString("lieu"));
                matiere.moyenne(rs.getString("telephone"));
                matiere.sexe(rs.getString("sexe_eleve"));
                matiere.matricule(rs.getString("login"));
                matiere.photo(rs.getString("avatar"));
                matiere.r3(rs.getString("pere"));
                matiere.r4(rs.getString("mere"));
                matiere.matricule(rs.getString("login"));
                matiere.r5(rs.getString("login"));
                matiere.classe(classe);

                // matiere.setredoublant(rs.getString("redoublant"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                effec++;
            }
        } else {
            new message().info("Aucun element trouve pour imprimer");
            return null;
        }
        return listeMatiere;
    }

    public List<impressionmodel_11> getEleveEtabPourImpression() throws SQLException {
        List<impressionmodel_11> listeMatiere = new ArrayList<>();

        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec inner join classe c inner join cycle cy"
                + " where c.etat_classe='1' and cy.id_cycle=c.id_cycle and ec.id_classe=c.id_classe and "
                + "ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve "
                + "and u.id_etab=? and ec.id_annee=? order by rang,nom_eleve";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        int effec = 0;
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                impressionmodel_11 matiere = new impressionmodel_11();
                matiere.id_eleve(rs.getString("id_eleve"));
                matiere.nom(rs.getString("nom_eleve") + " " + rs.getString("prenom_eleve"));

                matiere.date(rs.getString("date_naissance"));
                matiere.lnaissance(rs.getString("lieu"));
                matiere.moyenne(rs.getString("telephone"));
                matiere.sexe(rs.getString("sexe_eleve"));
                matiere.classe(rs.getString("nom_classe"));

//               matiere.setpere(rs.getString("pere"));
//                matiere.setmere(rs.getString("mere"));
                matiere.matricule(rs.getString("login"));
                // matiere.setredoublant(rs.getString("redoublant"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                effec++;
            }
        } else {
            new message().info("Aucun element trouve pour imprimer");

            return null;
        }
        return listeMatiere;
    }

    public int getEleveparclasse(JTable jt, String classe) throws SQLException {
        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec where "
                + "ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve "
                + "and ec.id_classe=? and ec.id_annee=? order by nom_eleve,prenom_eleve";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, classe);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        System.out.println("request=>>> " + ps);
        int effec = 0;
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve matiere = new modelEleve();
                matiere.setidMatiere(rs.getString("id_eleve"));
                matiere.setnomMatiere(rs.getString("nom_eleve"));
                matiere.setprenom(rs.getString("prenom_eleve"));
                matiere.setdate(rs.getDate("date_naissance"));
                matiere.setglieu(rs.getString("lieu"));
                matiere.settelephone(rs.getString("telephone"));
                matiere.setsexe(rs.getString("sexe_eleve"));
                matiere.setpere(rs.getString("pere"));
                matiere.setmere(rs.getString("mere"));
                matiere.setmatricule(rs.getString("login"));
                matiere.setredoublant(rs.getString("redoublant"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                effec++;
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom ", "Prenom", "Sexe", "Date Na.",
                        "lieu", "contact", "Nom pere", "Mere", "matricule", "Redoublant", "Edit", "Supp."}, 0) {
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
                for (final modelEleve detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getprenom(), detail.getsexe(),
                            detail.getDate(), detail.getLieu(), detail.gettelephne(),
                            detail.getpere(), detail.getmere(), detail.getmatricule(), detail.getredoublant()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(12).setMaxWidth(80);
                jt.getColumnModel().getColumn(13).setMaxWidth(80);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(12);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(13);
                testColumn3.setCellRenderer(new PlusMinusCellRenderer());
                testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
                testColumn3.setCellEditor(new eleveEditor(new JCheckBox()));
                testColumn4.setCellEditor(new eleveEditor(new JCheckBox()));

            }
        } else {
            new message().info("Aucun element trouve pour imprimer");

        }
        return effec;
    }

    public int getEleveToutEtab(JTable jt) throws SQLException {
        String query = "select * from eleve e inner join users u inner join eleve_classe "
                + " ec inner join classe c  where c.id_classe=ec.id_classe and "
                + "ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve  and e.etat_eleve='1'"
                + "and u.id_etab=? and ec.id_annee=? order by nom_classe,nom_user";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        int effec = 0;
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve matiere = new modelEleve();
                matiere.setidMatiere(rs.getString("id_eleve"));
                matiere.setnomMatiere(rs.getString("nom_eleve") + " " + rs.getString("prenom_eleve"));
                matiere.setprenom(rs.getString("nom_classe"));
                matiere.setdate(rs.getDate("date_naissance"));
                matiere.setglieu(rs.getString("lieu"));
                matiere.settelephone(rs.getString("telephone"));
                matiere.setsexe(rs.getString("sexe_eleve"));
                matiere.setpere(rs.getString("pere"));
                matiere.setmere(rs.getString("mere"));
                matiere.setmatricule(rs.getString("login"));
                matiere.setredoublant(rs.getString("redoublant"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                effec++;
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom & prenom ", "classe", "Sexe", "Date Naissance.",
                        "lieu", "contact", "Nom pere", "Mere", "matricule", "Redoublant", "Edit", "Supp."}, 0) {
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
                for (final modelEleve detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getprenom(), detail.getsexe(),
                            detail.getDate(), detail.getLieu(), detail.gettelephne(),
                            detail.getpere(), detail.getmere(), detail.getmatricule(), detail.getredoublant()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(4).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(12).setMaxWidth(80);
                jt.getColumnModel().getColumn(13).setMaxWidth(80);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(12);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(13);
                testColumn3.setCellRenderer(new PlusMinusCellRenderer());
                testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
                testColumn3.setCellEditor(new eleveEditor(new JCheckBox()));
                testColumn4.setCellEditor(new eleveEditor(new JCheckBox()));

            }
        } else {
            new message().info("Aucun element trouve pour imprimer");

        }
        return effec;
    }

    public boolean getEleveparclassePourPaieScolarite(JTable jt, String classe) throws SQLException {
        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec where "
                + "ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve "
                + "and ec.id_classe=? and ec.id_annee=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
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
                matiere.setglieu(rs.getString("lieu"));
                matiere.settelephone(rs.getString("telephone"));
                if (rs.getString("avatar") != null) {
                    matiere.setsexe(rs.getString("avatar"));
                } else {
                    matiere.setsexe("Ras");
                }
                matiere.setpere(rs.getString("pere"));
                matiere.setmere(rs.getString("mere"));
                matiere.setmatricule(rs.getString("login"));
                matiere.setredoublant(classe);
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                effec++;
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom ", "Prenom", "avat", "Date Na.",
                        "classe", "contact", "matricule", "Inscrire"}, 0) {
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
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getprenom(), detail.getsexe(),
                            detail.getDate(), detail.getredoublant(), detail.gettelephne(),
                            detail.getmatricule()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(4).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(9).setMaxWidth(80);

                TableColumn testColumn4 = jt.getColumnModel().getColumn(9);
                testColumn4.setCellRenderer(new InscrireCellRenderer());
                testColumn4.setCellEditor(new incriptioneleveEditor(new JCheckBox()));

            }
        } else {
            new message().info("Aucun element trouve pour inscrire dans cette salle");
            return false;
        }
        return true;
    }

    private String getCycleId(String classe) throws SQLException {
        String query = "select * from classe where id_classe=? and etat_classe='1' ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        // ps.setString(2, idAnneScolaire);
        String idCycle = null;
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {

            while (rs.next()) {
                idCycle = rs.getString("id_cycle");
            }
        } else {
            new message().info("erreur inconnue survenue");
        }
        return idCycle;
    }

    private boolean checkSiPaiementTranche(String classe, String eleve, String tranche) throws SQLException {
        memoire.id_eleve = eleve;
        String query = "select * from tranche_scolarite ts"
                + " inner join scolarite_paie sp where ts.id_tranche =sp.id_tranche"
                + "  and sp.id_classe=? and sp.id_annee=? and sp.id_eleve=? and sp.id_tranche=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, new fonctions().getIClasse(classe));
        ps.setString(2, idAnneScolaire);
        ps.setString(3, eleve);
        ps.setString(4, tranche);
        String idCycle = null;
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>");
            return true;
        } else {
            System.out.println("?????????????????????????");
            //new message().info("erreur inconnue survenue");
            return false;
        }
    }

    public boolean getEleveTranchesPaiement(JTable jt, String classe, String idEleve) throws SQLException {
        String idCy = getCycleId(classe);//recupere le cycle
        memoire.classe = classe;
        String query = "select * from tranche_scolarite ts"
                + " inner join cycle c where c.id_cycle=ts.id_cycle and"
                + " ts.id_cycle=? and ts.etat_tranche='1' order by ts.rang,ts.nom_tranche";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idCy);
        ResultSet rs = ps.executeQuery();
        boolean test = false;
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve matiere = new modelEleve();
                test = checkSiPaiementTranche(classe, idEleve, rs.getString("id_tranche"));
                // recupere etat paiement de tranche
                matiere.setSelect(test);
                matiere.setidMatiere(rs.getString("id_tranche"));
                matiere.setnomMatiere(rs.getString("nom_tranche"));
                matiere.setprenom(rs.getString("nom_cycle"));
                matiere.setredoublant(rs.getString("prix"));
                listeMatiere.add(matiere);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom tranche ",
                        "Cycle",
                        "Prix", "Etat frais", "Payer", "Imprimer recu", "Moratoire"}, 0) {
                    @Override
                    public Class getColumnClass(int columnIndex) {
                        if (columnIndex == 5) {
                            return Boolean.class;
                        }
                        return super.getColumnClass(columnIndex);
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        boolean test = true;
                        if (column < 6) {
                            test = false;
                            return test;
                        }
                        return test;
                    }
                };
                int j = 1;
                for (final modelEleve detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(),
                            detail.getprenom(),
                            detail.getredoublant(), detail.getSelect()
                    });
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(30);
                jt.getColumnModel().getColumn(5).setMaxWidth(40);

                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(6).setMaxWidth(70);
                jt.getColumnModel().getColumn(7).setMaxWidth(75);
                jt.getColumnModel().getColumn(8).setMaxWidth(70);

                TableColumn testColumn4 = jt.getColumnModel().getColumn(6);
                TableColumn testColumn5 = jt.getColumnModel().getColumn(7);
                TableColumn moratoire = jt.getColumnModel().getColumn(8);

                testColumn4.setCellRenderer(new PayerInscrirepayerCellRenderer());
                testColumn4.setCellEditor(new incriptioneleveEditor(new JCheckBox()));
                testColumn5.setCellRenderer(new ImrpimerpayerCellRenderer());
                testColumn5.setCellEditor(new incriptioneleveEditor(new JCheckBox()));
                moratoire.setCellRenderer(new MoratoirCellRenderer());
                moratoire.setCellEditor(new incriptioneleveEditor(new JCheckBox()));
            }
            //getEleveMoratoireTranchesPaiement(idEleve);
        } else {
            new message().info("Aucun element trouve ");
            return false;
        }
        return true;
    }

    public modelEleve  getEleveMoratoireTranchesPaiementIMpression(String id_tranche, String idEleve) throws SQLException {

        String query = "select * from moratoire m"
                + " inner join tranche_scolarite t where m.id_tranche=t.id_tranche and"
                + " m.id_eleve=? and m.id_annee=? and m.id_tranche=? and is_finish=? and t.etat_tranche='1' order by t.rang,t.nom_tranche";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEleve);
        ps.setString(2, idAnneScolaire);
        ps.setString(3, id_tranche);
        ps.setBoolean(4, false);
        ResultSet rs = ps.executeQuery();
        boolean test = false;
        modelEleve matiere = new modelEleve();
        if (rs.isBeforeFirst()) {

            while (rs.next()) {

                matiere.setidMatiere(rs.getString("id"));
                matiere.setnomMatiere(rs.getString("montant"));
                matiere.setprenom(rs.getString("montant_restant"));
                matiere.setredoublant(String.valueOf(rs.getDate("date_creation_m")));
                matiere.settelephone(rs.getString("nom_tranche"));

            }
            return matiere;

        }
        return null;
    }



    public boolean getEleveMoratoireTranchesPaiement(JTable jt, String idEleve) throws SQLException {

        String query = "select * from moratoire m"
                + " inner join tranche_scolarite t where m.id_tranche=t.id_tranche and"
                + " m.id_eleve=? and m.id_annee=? and t.etat_tranche='1' order by t.rang,t.nom_tranche";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEleve);
        ps.setString(2, idAnneScolaire);
        ResultSet rs = ps.executeQuery();
        boolean test = false;
        if (rs.isBeforeFirst()) {
            List<modelEleve> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelEleve matiere = new modelEleve();

                matiere.setidMatiere(rs.getString("id"));
                matiere.setnomMatiere(rs.getString("montant"));
                matiere.setprenom(rs.getString("montant_restant"));
                matiere.setredoublant(String.valueOf(rs.getDate("date_creation_m")));
                matiere.settelephone(rs.getString("nom_tranche"));
                listeMatiere.add(matiere);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"Code", "Montant paye ",
                        "Reste a payer",
                        "Date ", "Periode", "Imprimer recu"}, 0) {

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
                for (final modelEleve detail : listeMatiere) {
                    model.addRow(new Object[]{detail.getidClasse(), detail.getNommatiere(),
                            detail.getprenom(),
                            detail.getredoublant(), detail.gettelephne()
                    });
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);


                jt.getColumnModel().getColumn(5).setMaxWidth(70);


                TableColumn testColumn5 = jt.getColumnModel().getColumn(5);


                testColumn5.setCellRenderer(new ImrpimerpayerCellRenderer());
                testColumn5.setCellEditor(new incriptioneleveEditor(new JCheckBox()));

            }
        } else {

            return false;
        }
        return true;
    }

    public boolean getEleveparclasseRecherchePourPaieScolarite(JTable jt, String eleve) throws SQLException {

        String query = "select * from eleve e inner join users u inner join eleve_classe"
                + " ec inner join classe c where "
                + "ec.etat_ec='1' and e.id_eleve=u.id_eleve and ec.id_eleve=e.id_eleve and c.id_classe=ec.id_classe "
                + " and ec.id_annee=? and u.nom_user like ? or u.login like ? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, "%" + eleve + "%");
        ps.setString(3, "%" + eleve + "%");

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
                matiere.setglieu(rs.getString("lieu"));
                matiere.settelephone(rs.getString("telephone"));
                matiere.setsexe(rs.getString("avatar"));
                matiere.setpere(rs.getString("pere"));
                matiere.setmere(rs.getString("mere"));
                matiere.setmatricule(rs.getString("login"));
                matiere.setredoublant(rs.getString("nom_classe"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                effec++;
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom ", "Prenom", "Sexe", "Date Na.",
                        "classe", "contact", "matricule", "Inscrire"}, 0) {
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
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getprenom(), detail.getsexe(),
                            detail.getDate(), detail.getredoublant(), detail.gettelephne(),
                            detail.getmatricule()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(9).setMaxWidth(80);
                jt.getColumnModel().getColumn(4).setMaxWidth(30);

                TableColumn testColumn4 = jt.getColumnModel().getColumn(9);
                testColumn4.setCellRenderer(new InscrireCellRenderer());
                testColumn4.setCellEditor(new incriptioneleveEditor(new JCheckBox()));

            }
        } else {
            new message().info("Aucun element trouve pour inscrire dans cette salle");
            return false;
        }
        return true;
    }

    public void getMatiereEtablissement(JTable jt) throws SQLException {
        String query = "select * from matiere where etat_matiere='1' and id_etab=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_matiere"));
                matiere.setnomMatiere(rs.getString("nom_matiere"));
                matiere.setdate(rs.getString("date_creation_matiere"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom matiere", "Date creation", "Edit", "Supp."}, 0) {
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
                for (final modelClasse detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getDate()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(4).setMaxWidth(80);
                jt.getColumnModel().getColumn(5).setMaxWidth(80);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(4);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(5);
                testColumn3.setCellRenderer(new PlusMinusCellRenderer());
                testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
                testColumn3.setCellEditor(new matiereEditor(new JCheckBox()));
                testColumn4.setCellEditor(new matiereEditor(new JCheckBox()));

            }
        } else {

        }
    }

    public void addEleveMoyenne(String classe, String period, String eleve, String moyenne) throws SQLException {
        String query = "select * from stat_moyenne where matricule=? and id_classe=? "
                + " and id_annee=? and id_periode=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, eleve);
        ps.setString(2, getIClasse(classe));
        ps.setString(3, idAnneScolaire);
        ps.setString(4, period);

        ResultSet rs = ps.executeQuery();
        System.err.println("QQQQQ " + ps);
        if (rs.isBeforeFirst()) {

            String sqlInsert = "update stat_moyenne set moyenne=? where "
                    + "matricule=? and id_classe=? and id_annee=? and id_periode=? ";

            PreparedStatement ps2 = mysqli.connect().prepareStatement(sqlInsert);
            ps2.setString(1, moyenne);
            ps2.setString(2, eleve);
            ps2.setString(3, getIClasse(classe));
            ps2.setString(4, idAnneScolaire);
            ps2.setString(5, period);
            ps2.executeUpdate();
            System.err.println("QQQQQ POL " + ps2);

        } else {
            String sqlInsert = "insert into stat_moyenne ("
                    + "moyenne, matricule,id_classe,id_annee,id_periode) "
                    + "values (?,?,?,?,?)";
            PreparedStatement ps2 = mysqli.connect().prepareStatement(sqlInsert);
            ps2.setString(1, moyenne);
            ps2.setString(2, eleve);
            ps2.setString(3, getIClasse(classe));
            ps2.setString(4, idAnneScolaire);
            ps2.setString(5, period);
            ps2.executeUpdate();
            System.err.println("QQQQQ POL2 " + ps2);

        }
    }

    public void getClasseDetails(JFrame f, JTable eleve, JTable matiere, JLabel l) throws SQLException {

        String classe = memoire.id_absence;
        //System.out.println("CLASSE ID >>>>>>>>>>>>>>>>>>>" + classe);
        int tst = getMatiereEnseignantParClasseAnneeEncours(matiere, classe);
        String ld = "Nombre de matieres: ";
        if (tst == 0) {
            int tst2 = getMatiereEnseignantParClasseAnneePassee(matiere, classe);
            if (tst2 == 0) {
                new message().info("Desole, Mais vous n'avez jamais definis des matieres"
                        + " \ndans cette salle de classe");
            } else {
                ld += " | " + tst2;
            }
        } else {
            ld += " | " + tst;
        }
        ld += " | Nombre d'eleves presents: | ";
        int effec = getEleveparclasse(eleve, classe);
        ld += effec;
        l.setText(ld);
    }

    public int getMatiereEnseignantParClasseAnneeEncours(JTable jt, String classe) throws SQLException {
        String query = "select * from matiere_classe_enseignant mc "
                + "inner join matiere m inner join enseignant e inner join users u"
                + " where m.etat_matiere='1' and id_annee=?"
                + "and m.id_matiere=mc.id_matiere and mc.id_enseignant=e.id_enseignant "
                + "and e.id_enseignant=u.id_enseignant "
                + "and mc.etat_mc='1' and mc.id_classe=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        System.err.println(">>>>>>>>" + ps);
        ps.setString(1, idAnneScolaire);
        ps.setString(2, classe);
        int total = 0;
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_m_c"));
                matiere.setnomMatiere(rs.getString("nom_matiere"));
                matiere.setEnseignant(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
                matiere.setcoeff(rs.getString("coeff"));
                matiere.setgroupe(rs.getString("groupe"));
                matiere.setcotathoraire(rs.getString("horaire"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
                total++;
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code",
                        "Nom matiere", "Enseignant",
                        "Coeff", "Groupe", "cotat hor. semaine", "Edit", "Supp."}, 0) {
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
                for (final modelClasse detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(),
                            detail.getEnseignant(), detail.getCoeff(),
                            detail.getGroup(), detail.getHoraire()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(4).setMaxWidth(80);
                jt.getColumnModel().getColumn(5).setMaxWidth(80);
                jt.getColumnModel().getColumn(7).setMaxWidth(60);
                jt.getColumnModel().getColumn(8).setMaxWidth(60);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(7);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(8);
                testColumn3.setCellRenderer(new PlusMinusCellRenderer());
                testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
                testColumn3.setCellEditor(new matiereEditor(new JCheckBox()));
                testColumn4.setCellEditor(new matiereEditor(new JCheckBox()));

            }
        } else {

        }
        mysqli.close();

        return total;
    }

    private int getMatiereEnseignantParClasseAnneePassee(JTable jt, String classe) throws SQLException {
        int io = (Integer.parseInt(idAnneScolaire) - 1);
        String query = "select * from matiere_classe_enseignant mc "
                + "inner join matiere m inner join enseignant e inner join users u"
                + " where m.etat_matiere='1' and id_annee=? "
                + "and m.id_matiere=mc.id_matiere and mc.id_enseignant=e.id_enseignant "
                + "and e.id_enseignant=u.id_enseignant "
                + "and mc.etat_mc='1' and mc.id_classe=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, "" + io);
        ps.setString(2, classe);
        int total = 0;
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_m_c"));
                matiere.setnomMatiere(rs.getString("nom_matiere"));
                matiere.setEnseignant(rs.getString("nom_user") + " " + rs.getString("prenom_user"));
                matiere.setcoeff(rs.getString("coeff"));
                matiere.setgroupe(rs.getString("groupe"));
                matiere.setcotathoraire(rs.getString("horaire"));
                total++;
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code",
                        "Nom matiere", "Enseignant",
                        "Coeff", "Groupe", "cotat hor. semaine", "Edit", "Supp."}, 0) {
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
                for (final modelClasse detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(),
                            detail.getEnseignant(), detail.getCoeff(),
                            detail.getGroup(), detail.getHoraire()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(4).setMaxWidth(80);
                jt.getColumnModel().getColumn(5).setMaxWidth(80);
                jt.getColumnModel().getColumn(6).setMaxWidth(60);
                jt.getColumnModel().getColumn(7).setMaxWidth(60);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(7);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(8);
                testColumn3.setCellRenderer(new PlusMinusCellRenderer());
                testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
                testColumn3.setCellEditor(new matiereEditor(new JCheckBox()));
                testColumn4.setCellEditor(new matiereEditor(new JCheckBox()));

            }
        } else {

        }
        mysqli.close();

        return total;
    }

    public void getClasseEtablissement(JTable jt) throws SQLException {
        String query = "select * from classe c inner join cycle cy"
                + " where c.etat_classe='1' and c.id_etab=? and cy.id_cycle=c.id_cycle";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idEtablissement);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            List<modelClasse> listeMatiere = new ArrayList<>();
            while (rs.next()) {
                modelClasse matiere = new modelClasse();
                matiere.setidMatiere(rs.getString("id_classe"));
                matiere.setnomMatiere(rs.getString("nom_classe"));
                matiere.setdate(rs.getString("nom_cycle"));
                matiere.setcotathoraire(rs.getString("rang"));
                //matiereMap.put(rs.getInt("id_matiere"), rs.getString("nom_matiere"));
                listeMatiere.add(matiere);
            }
            JComboBox ensei = enseignant();
            if (ensei != null) {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"# N", "Code", "Nom classe", "Cycle", "Rang", "Voir", "Edit", "Supp"}, 0) {
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
                for (final modelClasse detail : listeMatiere) {
                    model.addRow(new Object[]{j, detail.getidClasse(), detail.getNommatiere(), detail.getDate(), detail.getHoraire()});
                    j++;
                }
                jt.setModel(model);
                jt.getColumnModel().getColumn(0).setMaxWidth(30);
                jt.getColumnModel().getColumn(1).setMaxWidth(70);
                jt.getColumnModel().getColumn(7).setMaxWidth(65);
                jt.getColumnModel().getColumn(5).setMaxWidth(65);
                jt.getColumnModel().getColumn(6).setMaxWidth(65);
                TableColumn testColumn3 = jt.getColumnModel().getColumn(5);
                TableColumn testColumn4 = jt.getColumnModel().getColumn(7);
                TableColumn testColumn5 = jt.getColumnModel().getColumn(6);
                testColumn3.setCellRenderer(new classeRenderer());
                testColumn4.setCellRenderer(new PlusMinusCellRenderer2());
                testColumn5.setCellRenderer(new ClasseEditRendere());
                testColumn3.setCellEditor(new classeEditor(new JCheckBox()));
                testColumn4.setCellEditor(new classeEditor(new JCheckBox()));
                testColumn5.setCellEditor(new classeEditor(new JCheckBox()));
            }
        } else {

        }
        mysqli.close();

    }

    public void validerPaiementTranche(String nomt, String ideleve, String idtran, String idclasse, String iduser) throws SQLException {
        java.sql.Date spdate;
        Date date = new Date();
        spdate = new java.sql.Date(date.getTime());
        String sqlInsert = "insert into scolarite_paie ("
                + " id_eleve,id_tranche,id_classe,id_user,id_annee,date_creation_s) "
                + "values (?,?,?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, ideleve);
        ps.setString(2, idtran);
        ps.setString(3, getIClasse(idclasse));
        ps.setString(4, prf.get("id_user", null));
        ps.setString(5, idAnneScolaire);
        ps.setDate(6, spdate);
        int s = ps.executeUpdate();
        if (s == 1) {
            new message().info("| " + nomt + " | payee avec succes");
        } else {
            new message().error("erreur", "Impossible de sauvegarder cette Tranche");
        }
        mysqli.close();

    }

    public void addTranchePaiement(String nomt, String nomCycle, String rang, String prix) throws SQLException {
        String sqlInsert = "insert into tranche_scolarite ("
                + " nom_tranche,id_etab,id_cycle,rang,prix) "
                + "values (?,?,?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nomt);
        ps.setString(2, idEtablissement);
        ps.setString(3, new fonctionsParams().getIdcycle(nomCycle));
        ps.setString(4, rang);
        ps.setString(5, prix);
        int s = ps.executeUpdate();
        if (s == 1) {
            new message().info("Tranche | " + nomt + " | ajoute avec succes");
        } else {
            new message().error("erreur", "Impossible de sauvegarder cette Tranche");
        }
        mysqli.close();

    }

    public boolean checkPeiodePaieExists(String nomPeriode, String rang) throws SQLException {
        String query = "select * from periode_paie where nom_periode_paie=? and id_etab=? "
                + " and rang=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, nomPeriode);
        ps.setString(2, idEtablissement);
        ps.setString(3, rang);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            new message().warning("Duplicate", "Desole mais il se pourrait que cette tranche existe deja");
            return false;
        } else {
            addPeriodePaiementPersonnel(nomPeriode, rang);
            return true;
        }

    }

    public void addPeriodePaiementPersonnel(String nomt, String rang) throws SQLException {
        String sqlInsert = "insert into periode_paie ("
                + " nom_periode_paie,id_etab,rang) "
                + "values (?,?,?)";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nomt);
        ps.setString(2, idEtablissement);
        ps.setString(3, rang);
        int s = ps.executeUpdate();
        if (s == 1) {
            new message().info("Periode de paiement | " + nomt + " | ajoute avec succes");
        } else {
            new message().error("erreur", "Impossible de sauvegarder cette Periode de paiement");
        }
        mysqli.close();

    }

    public boolean checkTrancheExists(String nomt, String nomCycle, String rang, String prix) throws SQLException {
        String query = "select * from tranche_scolarite where nom_tranche=? and id_etab=? "
                + " and id_cycle=? and rang=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, nomt);
        ps.setString(2, idEtablissement);
        ps.setString(3, new fonctionsParams().getIdcycle(nomCycle));
        ps.setString(4, rang);

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            new message().warning("Duplicate", "Desole mais il se pourrait que cette tranche existe deja");
            return false;
        } else {
            addTranchePaiement(nomt, nomCycle, rang, prix);
            return true;
        }

    }

    public boolean checkTrancheDelete(String tra, String nomt, String nomCycle, String rang, String prix) throws SQLException {
        String query = "select * from scolarite_paie where id_tranche=? and id_annee=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, tra);
        ps.setString(2, idAnneScolaire);
        //ps.setString(3, new fonctionsParams().getIdcycle(nomCycle));

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            new message().warning("Operation avortee",
                    "Desole impossible de modifier cette tanche car\n"
                            + "Certains elves y sont deja iscrits");
            return true;
        } else {
            return false;
        }

    }

    public boolean checkPeriodpaiDelete(String tra) throws SQLException {
        String query = "select * from paie_personnel where id_periode_paie=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, tra);
        //ps.setString(3, new fonctionsParams().getIdcycle(nomCycle));

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            new message().warning("Operation avortee",
                    "Desole impossible de modifier cette Periode car\n"
                            + "Vous avez deja paye le personnel avec");
            return true;
        } else {
            return false;
        }

    }

    public boolean checkTrancheEditable(String tra, String nomt, String nomCycle, String rang, String prix) throws SQLException {
        String query = "select * from scolarite_paie where id_tranche=? and id_annee=? ";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, tra);
        ps.setString(2, idAnneScolaire);
        //ps.setString(3, new fonctionsParams().getIdcycle(nomCycle));

        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            new message().warning("Operation avortee",
                    "Desole impossible de modifier cette tanche car\n"
                            + "Certains elves y sont deja iscrits");
            return true;
        } else {
            EditTranchePaiement(tra, nomt, nomCycle, rang, prix);
            return false;
        }

    }


    public List<modelEleve> getEleveTranchesPaiementPourImpression(String classe, String idEleve) throws SQLException {
        String idCy = getCycleId(classe);//recupere le cycle
        memoire.classe = classe;
        String query = "select * from tranche_scolarite ts"
                + " inner join cycle c where c.id_cycle=ts.id_cycle and"
                + " ts.id_cycle=? and ts.etat_tranche='1' order by ts.rang,ts.nom_tranche";
        PreparedStatement ps = mysqli.connect().prepareStatement(query);
        ps.setString(1, idCy);
        ResultSet rs = ps.executeQuery();
        boolean test = false;
        List<modelEleve> listeMatiere = new ArrayList<>();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                modelEleve matiere = new modelEleve();
                test = checkSiPaiementTranche(classe, idEleve, rs.getString("id_tranche"));
                // recupere etat paiement de tranche
                matiere.setSelect(test);
                matiere.setidMatiere(rs.getString("id_tranche"));
                matiere.setnomMatiere(rs.getString("nom_tranche"));
                matiere.setprenom(rs.getString("nom_cycle"));
                matiere.setredoublant(rs.getString("prix"));
                listeMatiere.add(matiere);
            }
        }
        return listeMatiere;
    }


    public void EditTranchePaiement(String tra, String nomt, String nomCycle, String rang, String prix) throws SQLException {
        String sqlInsert = "update tranche_scolarite set"
                + " nom_tranche=?,id_etab=?,id_cycle=?,rang=?,prix=? "
                + "where id_tranche=?";
        PreparedStatement ps = mysqli.connect().prepareStatement(sqlInsert);
        ps.setString(1, nomt);
        ps.setString(2, idEtablissement);
        ps.setString(3, new fonctionsParams().getIdcycle(nomCycle));
        ps.setString(4, rang);
        ps.setString(5, prix);
        ps.setString(6, tra);
        System.out.println("?????>>?>>>>>" + ps);
        int s = ps.executeUpdate();
        if (s == 1) {
            new message().info("Tranche | " + nomt + " | edite avec succes");
        } else {
            new message().error("erreur", "Impossible d'editer cette Tranche");
        }
        mysqli.close();

    }

    public void RefreshDashbord() {

        SwingUtilities.updateComponentTreeUI(new dashbord());
    }
}

class classeRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("Voir"));
        return this;
    }
}

class ClasseEditRendere extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("Edit"));
        return this;
    }
}

class ActiverCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("activer"));
        return this;
    }
}

class PlusMinusCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("Edit"));
        return this;
    }
}

class voirCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("plus.."));
        return this;
    }
}

class MoratoirCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("Moratoire"));
        return this;
    }
}

class ImrpimerpayerCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("Reçu"));
        return this;
    }
}

class PayerInscrirepayerCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("payer"));
        return this;
    }
}

class InscrireCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("Inscrire"));
        return this;
    }
}

class PlusMinusCellRenderer2 extends JPanel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            final JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        this.add(new JButton("Supp"));
        return this;
    }
}
