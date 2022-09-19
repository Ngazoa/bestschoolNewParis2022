/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enseignant;

import etablissement.ZDialogInfo;

import etablissement.liste_classe;
import etablissement.memoire;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajouter_enseignant extends JDialog {

    private ZDialogInfo zInfo = new ZDialogInfo();
    private JLabel nomLabel, coeff, sexeLabel, cheveuxLabel, ageLabel,
            tailleLabel, taille2Label, icon;
    private JRadioButton tranche1, tranche2, tranche3, tranche4;
    private JComboBox sexe, matieres, coefficient, liste_des_classe;
    private JTextField nom, taille;
    String[] classe;

    /**
     * Constructeur
     *
     * @param parent
     * @param title
     * @param modal
     * @throws org.json.JSONException
     * @throws java.io.IOException
     */
    public Ajouter_enseignant(JFrame parent, String title, boolean modal) throws JSONException, IOException {
        super(parent, title, modal);
        liste_classe();
        liste_des_classe.addItemListener((java.awt.event.ItemEvent evt) -> {
            liste_classeItemStateChanged(evt);
            matieres.setEnabled(true);
        });
        this.setSize(600, 470);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }

    public ZDialogInfo showZDialog() {
        this.setVisible(true);
        return this.zInfo;
    }

    private void initComponent() throws IOException, JSONException {
//Icone
        icon = new JLabel(new ImageIcon(memoire.enseignant));
        icon.setPreferredSize(new Dimension(90, 190));
        JPanel panIcon = new JPanel();
        panIcon.setLayout(new BorderLayout());
        panIcon.add(icon);
//Le nom
        JPanel panNom = new JPanel();
        panNom.setPreferredSize(new Dimension(220, 90));
        nom = new JTextField();
        nom.setText(memoire.nom_conserve);
        nom.setPreferredSize(new Dimension(150, 25));
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
        panTaille.setBorder(BorderFactory.createTitledBorder("Contact"));
        tailleLabel = new JLabel("Téléphone : ");
        taille2Label = new JLabel("");
        taille = new JTextField(memoire.tel_conserve);
        taille.setPreferredSize(new Dimension(90, 25));

        panTaille.add(tailleLabel);
        panTaille.add(taille);
        panTaille.add(taille2Label);
//La couleur des cheveux
        JPanel panCheveux = new JPanel();
        panCheveux.setPreferredSize(new Dimension(220, 90));
        panCheveux.setBorder(BorderFactory.createTitledBorder("Classes concernées"));

        cheveuxLabel = new JLabel("Classe");
        panCheveux.add(cheveuxLabel);
        panCheveux.add(liste_des_classe);

        JPanel matiere = new JPanel();
        matiere.setPreferredSize(new Dimension(400, 120));
        matiere.setBorder(BorderFactory.createTitledBorder("Matiere dispensée "));

        String[] jours = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
             "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60"};
        String[] jrs = {""};

        coefficient = new JComboBox(jours);
        matieres = new JComboBox(jrs);
        sexeLabel = new JLabel("Matiere : ");
        coeff = new JLabel("Nbre d'heure/semaine dans cette classe");

        matiere.add(sexeLabel);
        matiere.add(matieres);
        matieres.setEnabled(false);

        matiere.add(coeff);
        matiere.add(coefficient);

        JPanel content = new JPanel();
        content.add(panNom);
        content.add(panSexe);
        content.add(panAge);
        content.add(panTaille);
        content.add(panCheveux);
        content.add(matiere);

        JPanel control = new JPanel();
        control.setBackground(Color.darkGray);

        JButton okBouton = new JButton("OK");
        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                final String salle_choisie = liste_des_classe.getSelectedItem().toString();

                memoire.coeff = coefficient.getSelectedItem().toString();
                memoire.nom = nom.getText();
                memoire.cathegorie = getAge();
                memoire.classe = salle_choisie;
                memoire.matiere = (String) matieres.getSelectedItem();
                memoire.sexe = (String) sexe.getSelectedItem();
                memoire.telephone = getTaille();
                zInfo = new ZDialogInfo(nom.getText(),
                        (String) sexe.getSelectedItem(), getAge(),
                        salle_choisie,
                        getTaille(), (String) matieres.getSelectedItem());
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
            //JOptionPane.showMessageDialog(null,"Veuillez séléctionner votre classe\n à saisir les pour pouvoir continuer");
        } else {
            try {
                memoire.classe = val;

                liste_matiere();
                matieres.setEnabled(true);

            } catch (IOException ex) {

            } catch (JSONException ex) {

            }
        }
        matieres.setEnabled(false);

    }

    private void liste_classe() throws IOException, JSONException {
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

            List<liste_classe> liste_classe = new ArrayList<liste_classe>();
            classe = new String[jsonArray.length()];
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
        String url = memoire.liste_matiere_parclasse;

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
        System.out.println("\n Sending 'POST' request to URL : " + url);
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

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject eleve = jsonArray.getJSONObject(i);
            String nom_eleve = eleve.getString("matiere");
            //matieres.addItem(nom_eleve);
            matieres.addItem(nom_eleve);

        }

        in.close();

    }
}
