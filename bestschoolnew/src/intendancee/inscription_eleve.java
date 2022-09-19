/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

import etablissement.memoire;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

public class inscription_eleve extends JDialog {

    private Infoinscription_eleve zInfo = new Infoinscription_eleve();
    private JLabel nomLabel, coeff, sexeLabel, cheveuxLabel, ageLabel,
            tailleLabel, taille2Label, icon;
    private JRadioButton tranche1, tranche2, tranche3, tranche4;
    private JComboBox sexe, matieres, coefficient, liste_des_classe, periode, cycle;
    private JTextField nom, taille;

    /**
     * Constructeur
     *
     * @param parent
     * @param title
     * @param modal
     * @throws org.json.JSONException
     * @throws java.io.IOException
     */
    public inscription_eleve(JFrame parent, String title, boolean modal) throws JSONException, IOException {
        super(parent, title, modal);
        liste_classe();
        listecycledefinie();

        liste_des_classe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                liste_classeItemStateChanged(evt);
                matieres.setEnabled(true);
            }
        });
        cycle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cyleItemStateChanged(evt);
                //matieres.setEnabled(true);
            }
        });
        periode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                periodeItemStateChanged(evt);
                //matieres.setEnabled(true);
            }
        });

        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }

    public Infoinscription_eleve showZDialog() {
        this.setVisible(true);
        return this.zInfo;
    }

    private void initComponent() throws IOException, JSONException {
//Icone
        icon = new JLabel(new ImageIcon("error.png"));
        JPanel panIcon = new JPanel();
        panIcon.setLayout(new BorderLayout());
        panIcon.add(icon);
//Le nom
        JPanel panNom = new JPanel();
        panNom.setPreferredSize(new Dimension(220, 90));
        nom = new JTextField();
        nom.setText("");
        nom.setPreferredSize(new Dimension(150, 35));
        panNom.setBorder(BorderFactory.createTitledBorder("Nom(s) et Prenom(s) "));
        nomLabel = new JLabel("");
        panNom.add(nomLabel);
        panNom.add(nom);
//Le sexe
        JPanel panSexe = new JPanel();
        panSexe.setPreferredSize(new Dimension(220, 90));
        panSexe.setBorder(BorderFactory.createTitledBorder("Sexe "));
        sexe = new JComboBox();
        sexe.addItem("Masculin");
        sexe.addItem("Féminin");
        sexe.addItem("Indéterminé");
        sexeLabel = new JLabel("Sexe : ");
        panSexe.add(sexeLabel);
        panSexe.add(sexe);
//L'âge
        JPanel panAge = new JPanel();
        panAge.setBorder(BorderFactory.createTitledBorder("Cathegories enseignant"));
        panAge.setPreferredSize(new Dimension(440, 70));
        tranche1 = new JRadioButton("Plet ou Pleg");
        tranche1.setSelected(true);
        tranche2 = new JRadioButton("Dipet ou Dipes");
        tranche3 = new JRadioButton("Vacataire");
        tranche4 = new JRadioButton("Autre");
        ButtonGroup bg = new ButtonGroup();
        bg.add(tranche1);
        bg.add(tranche2);
        bg.add(tranche3);
        bg.add(tranche4);
        panAge.add(tranche1);
        panAge.add(tranche2);
        panAge.add(tranche3);
        panAge.add(tranche4);
//La taille
        JPanel panTaille = new JPanel();
        panTaille.setPreferredSize(new Dimension(220, 90));
        panTaille.setBorder(BorderFactory.createTitledBorder("Montant à payer"));
        tailleLabel = new JLabel("Montant en Chiffre (CFA) : ");
        taille2Label = new JLabel("");

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);

        taille = new JFormattedTextField(decimalFormat);
        taille.setColumns(10);
        taille.setPreferredSize(new Dimension(90, 25));

        panTaille.add(tailleLabel);
        panTaille.add(taille);
        panTaille.add(taille2Label);
//La couleur des cheveux
        JPanel panCheveux = new JPanel();
        panCheveux.setPreferredSize(new Dimension(220, 90));
        panCheveux.setBorder(BorderFactory.createTitledBorder("Classe de l'élève "));

        cheveuxLabel = new JLabel("Classe");
        panCheveux.add(cheveuxLabel);
        panCheveux.add(liste_des_classe);

        JPanel matiere = new JPanel();
        matiere.setPreferredSize(new Dimension(530, 120));
        matiere.setBorder(BorderFactory.createTitledBorder("Matiere dispensée "));

        String[] jours = {" ", "APE", "APE + FRAIS EXIGIBLES (1 er Cycle fr)", "APE + FRAIS EXIGIBLES (2nd Cycle fr)",
            "APE + F. EXIGIBLES + F. EXAM (3 ème)", "APE + F. EXIGIBLES + F. EXAM (1 ère)", "APE + F. EXIGIBLES + F. EXAM (Tle)", "",
             "APE L6SC & U6SC", "APE + FRAIS EXIGIBLES (1 er Cycle an)", "APE + FRAIS EXIGIBLES (2nd Cycle an)"};
        String[] jrs = {""};

        coefficient = new JComboBox(jours);
        matieres = new JComboBox(jrs);
        sexeLabel = new JLabel("NOM ELEVE : ");
        coeff = new JLabel("Objet de paiement  : ");

        matiere.add(sexeLabel);
        matiere.add(matieres);
        matieres.setEnabled(false);

        matiere.add(coeff);
//matiere.add(cycle);

//ulta important pour lyclee
//matiere.add(coefficient);
        matiere.add(periode);

        JPanel content = new JPanel();
        content.add(panCheveux);
        content.add(matiere);

        content.add(panTaille);

        JPanel control = new JPanel();
        control.setBackground(Color.darkGray);

        JButton okBouton = new JButton("OK");
        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                final String salle_choisie = liste_des_classe.getSelectedItem().toString();

                memoire_intendance.prix = taille.getText();
                memoire_intendance.nom = (String) matieres.getSelectedItem();
                memoire_intendance.classe = salle_choisie;
                memoire_intendance.but = "" + memoire.note9 + " ( " + periode.getSelectedItem() + " )";
                //memoire.note9 : represente le cycle
                zInfo = new Infoinscription_eleve(memoire_intendance.classe,
                        memoire_intendance.nom, memoire_intendance.but,
                        memoire_intendance.prix);
                setVisible(false);
            }

            public String getAge() {
                return (tranche1.isSelected()) ? tranche1.getText()
                        : (tranche2.isSelected()) ? tranche2.getText()
                        : (tranche3.isSelected()) ? tranche3.getText()
                        : (tranche4.isSelected()) ? tranche4.getText() : tranche1.getText();
            }

            public String getTaille() {
                return (taille.getText().equals("")) ? "Vide !" : taille.getText();
            }
        });
        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        coefficient.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                switch (coefficient.getSelectedItem().toString()) {
                    case "APE":
                        taille.setText("25000");
                        memoire_intendance.cateristiques_paie = "1";

                        break;
                    case "APE + FRAIS EXIGIBLES (1 er Cycle fr)":
                        taille.setText("32500");
                        memoire_intendance.cateristiques_paie = "2";
                        break;
                    case "APE + FRAIS EXIGIBLES (2nd Cycle fr)":
                        taille.setText("35000");
                        memoire_intendance.cateristiques_paie = "2";

                        break;
                    case "APE + F. EXIGIBLES + F. EXAM (3 ème)":
                        taille.setText("38600");
                        memoire_intendance.cateristiques_paie = "3";

                        break;
                    case "APE + F. EXIGIBLES + F. EXAM (1 ère)":
                        taille.setText("46100");
                        memoire_intendance.cateristiques_paie = "3";

                        break;
                    case "APE + F. EXIGIBLES + F. EXAM (Tle)":
                        taille.setText("48100");
                        memoire_intendance.cateristiques_paie = "3";

                        break;
                    case "APE L6SC & U6SC":
                        taille.setText("30000");
                        memoire_intendance.cateristiques_paie = "1";

                        break;
                    case "APE + FRAIS EXIGIBLES (1 er Cycle an)":
                        taille.setText("32500");
                        memoire_intendance.cateristiques_paie = "2";

                        break;
                    case "APE + FRAIS EXIGIBLES (2nd Cycle an)":
                        taille.setText("40000");
                        memoire_intendance.cateristiques_paie = "2";

                        break;

                    default:
                        taille.setText("Aucun motant actuel");
                        break;

                }

                taille.setEditable(false);
            }
        });
        control.add(okBouton);
        control.add(cancelBouton);
        this.getContentPane().add(panIcon, BorderLayout.WEST);
        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);

    }

    private void liste_classeItemStateChanged(java.awt.event.ItemEvent evt) {
        String val = liste_des_classe.getSelectedItem().toString();
        System.out.println("je travail actuellement sur : " + val);

        if (val.equals("Classes diponibles")) {
            JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\npour pouvoir continuer");
        } else {
            try {
                memoire_intendance.classe = val;

                liste_matiere();
                cycleClasse();
                liste_frais();
                matieres.setEnabled(true);

            } catch (IOException ex) {
                Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        matieres.setEnabled(false);

    }

    private void cyleItemStateChanged(java.awt.event.ItemEvent evt) {
        String val = cycle.getSelectedItem().toString();
        System.out.println("je travail actuellement sur : " + val);

        if (val.equals("Entrer le niveau/cycle/classe")) {
            JOptionPane.showMessageDialog(null, "Veuillez séléctionner le cycle");
        } else {
            try {
                memoire_intendance.classe = val;
                System.out.println("je travail periode : " + val);
                liste_frais();
                //matieres.setEnabled(true);

            } catch (IOException ex) {
                Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//matieres.setEnabled(false);

    }

    private void periodeItemStateChanged(java.awt.event.ItemEvent evt) {
        String val = periode.getSelectedItem().toString();

        if (val.equals("Entrer le niveau/cycle/classe")) {
            JOptionPane.showMessageDialog(null, "Veuillez séléctionner \nla periode ou la tanche a payer");
        } else {
            try {
                memoire_intendance.classe = val;

                liste_prix(val);
                //matieres.setEnabled(true);

            } catch (IOException ex) {
                Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//matieres.setEnabled(false);

    }

    private void liste_classe() throws IOException, JSONException {
        String[] cy = {"Entrer le cycle", "1 er Cycle", "2 nd Cycle", "3 eme Cyle", "Autre"};
        cycle = new JComboBox(cy);
        periode = new JComboBox();
        periode.setEnabled(false);

        String url = memoire.liste_classe;
        String inline = null;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        StringBuilder sb = new StringBuilder();
        URL urln = new URL(memoire.liste_classe);
        HttpURLConnection conn = (HttpURLConnection) urln.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
            Scanner sc = new Scanner(urln.openStream());

            while (sc.hasNext()) {
                inline = sc.nextLine().trim();
            }
            System.out.println(inline);

            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
            String address = jsonArray.getJSONObject(0).getString("nom_classe");

            liste_des_classe = new JComboBox();
            liste_des_classe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Classes diponibles"}));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eleve = jsonArray.getJSONObject(i);
                String nom_eleve = eleve.getString("nom_classe");
                liste_des_classe.addItem(nom_eleve);

                System.out.println(nom_eleve);

                sc.close();
            }
        }
    }

    private void liste_matiere() throws IOException, JSONException {
        String url = memoire.liste_eleves_classe;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "classe=" + liste_des_classe.getSelectedItem();

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

        String inputLine, inline = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }
        System.out.println(inline);

        org.json.JSONObject bj = new JSONObject(inline);
        org.json.JSONArray jsonArray = bj.getJSONArray("victoire");

        String[] clas = new String[jsonArray.length()];
        matieres.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"          "}));
        String id = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject eleve = jsonArray.getJSONObject(i);
            String nom_eleve = eleve.getString("nom");
            id = eleve.getString("id_eleve");
            matieres.addItem(nom_eleve + " ;" + id);

        }

        in.close();

    }

    private void liste_frais() throws IOException, JSONException {
        String url = memoire.liste_cycle;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "classe=" + memoire.note9;

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

        String inputLine, inline = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }
        System.out.println(inline);
        try {
            System.out.println(" OK OK OK ....");

            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");

            String[] clas = new String[jsonArray.length()];
            periode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"periode"}));

            String nom_eleve, etat, prix;
            ArrayList<String> liste_eleve = new ArrayList<>();
            memoire.prix = new ArrayList<>();
            memoire.periode = new ArrayList<>();
            memoire_intendance.reste = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eleve = jsonArray.getJSONObject(i);

                nom_eleve = eleve.getString("periode");
                etat = eleve.getString("etat");
                prix = eleve.getString("prix");
                if (etat.equals("1")) {
                    periode.addItem(nom_eleve);
                    periode.setEnabled(true);
                } else {
                    liste_eleve.add(nom_eleve + "  Prix : " + prix);
                    memoire.prix.add("" + prix);
                    memoire_intendance.reste += Integer.parseInt(prix);

                }

            }
            memoire.periode = liste_eleve;

        } catch (Exception e) {
            showConfirmDialog(null, "les modalites pour ce cycle \nne sont pas encore definies", "Erreur inattendue",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        in.close();

    }

    private void liste_prix(String periode) throws IOException, JSONException {
        String url = memoire.liste_prix;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "cycle=" + memoire.note9 + "&periode=" + periode;

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

        String inputLine, inline = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }
        System.out.println(inline);

        org.json.JSONObject bj = new JSONObject(inline);
        org.json.JSONArray jsonArray = bj.getJSONArray("victoire");

        String nom_eleve = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject eleve = jsonArray.getJSONObject(i);
            nom_eleve = eleve.getString("prix");
            //matieres.addItem(nom_eleve);
        }
        taille.setText(nom_eleve);
        taille.setEditable(false);

        in.close();

    }

    private void listecycledefinie() throws IOException, JSONException {
        String url = memoire.liste_cycles;
        try {
            URL urln = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urln.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                Scanner sc = new Scanner(urln.openStream());
                String inline = null;

                while (sc.hasNext()) {
                    inline = sc.nextLine().trim();
                }

                org.json.JSONObject bj = new JSONObject(inline);
                org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
                String[] cy = {"Entrer le niveau/cycle/classe"};

                cycle = new JComboBox(cy);
                String nom_eleve, etat, prix;
                ArrayList<String> liste_eleve = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject eleve = jsonArray.getJSONObject(i);

                    nom_eleve = eleve.getString("nom_cycle");

                    cycle.addItem(nom_eleve);

                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur survenue (" + e + ") Aucun niveau de paiement detecté \nVeuillez vous rassurez d'avoir au préalable definie \nles niveaux ou cycles de paiement ");

        }
    }

    private void cycleClasse() throws IOException, JSONException {
        String url = memoire.cycle_classeChoisie;
        try {
            URL urln = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urln.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "classe=" + memoire_intendance.classe;

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

            String inputLine, inline = null;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine.trim());
                inline = inputLine.trim();
                System.out.println("Response Code : " + response.append(inputLine));

            }
            System.out.println(inline);

            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
            String nom_eleve, etat, prix;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eleve = jsonArray.getJSONObject(i);

                memoire.note9 = eleve.getString("cycle");

                // cycle.addItem(nom_eleve);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur survenue (" + e + ") Aucun niveau de paiement detecté \nVeuillez vous rassurez d'avoir au préalable definie \nles niveaux ou cycles de paiement ");

        }
    }
}
