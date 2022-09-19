/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

import enseignant.page_enseignant;
import intendancee.memoire_intendance;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Junior ENAMA
 */
public class jesusChrist {

    String ft = "libraries/benito/licence/cl.txt";
    String ereur;
    int licence = 0;
    int essaie = 0;
    Boolean response_correcte = false;
    private Date todayDate, historyDate, futureDate, debut, fin;

    public boolean identification_licenece(String nom, String pass, String identifiant) throws Exception {

        String url = memoire_intendance.login;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = "login=" + nom + "&password=" + pass;
        try {
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);

            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
        } catch (IOException ex) {
            int option = showConfirmDialog(null, "Impossible de joindre le serveur distant ....\nBien vouloir vérifier la nouvelle adresse ip", "Erreur de connexion",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        System.out.println("\nSending 'POST' request to URL : " + url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine, inline2 = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }

        org.json.JSONObject bj = new JSONObject(inline2);

        System.out.println(inline2);
        org.json.JSONObject jsonArray = bj.getJSONObject("personne");
        org.json.JSONObject erreur = bj.getJSONObject("connecter");
        String ereur = erreur.getString("erreur");
        System.out.println(ereur);

        if (ereur.equals("non")) {
            String password = jsonArray.getString("password");

            intendancee.PasswordAuthentication f = new intendancee.PasswordAuthentication();

            Boolean decode;
            decode = f.authenticat(pass, password);

            System.out.println("Confirmer : " + password);

            if (decode.equals(true)) {

                response_correcte = true;
            }
        } else {
            response_correcte = false;

        }

        return response_correcte;

    }

    public boolean enregistrer_cle() {

        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();

        String mo = JOptionPane.showInputDialog(null, "Veuillez Entrer votre nouveau mot de passe", "Entrer votre nouveau mot de passe", JOptionPane.QUESTION_MESSAGE);
        if (mo == null || (mo != null && ("".equals(mo)))) {
            JOptionPane.showMessageDialog(null, "votre mot de passe ne doit pas etre null", "ECHEC", JOptionPane.ERROR_MESSAGE);

        } else {
            if (mo.equals("jesus m'aime")) {
                String no = JOptionPane.showInputDialog(null, "Veuillez entrer la cle !", "Ajout de la cle", JOptionPane.INFORMATION_MESSAGE);

                try {
                    mdofifier_mot_passe(no);
                    JOptionPane.showMessageDialog(null, "Votre cle a ete bien enregistree", "SUCCES", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    Logger.getLogger(page_enseignant.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(page_enseignant.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "AH HA HA AHA .... Mr le voleur\nMot de passe Incorrect !!!", "ECHEC", JOptionPane.ERROR_MESSAGE);

            }
        }

        return false;
    }

    public void mdofifier_mot_passe(String id) throws IOException, JSONException {

        PasswordAuthentication f = new PasswordAuthentication();

        String pass = f.hash(id);
        System.out.println("MOT de passe hashe : " + pass);

        String url = memoire.modifier_mot_passe;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "password=" + pass + "&id_user=" + memoire.id_user;

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

        String inputLine, test = null;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            test = inputLine.trim();

            System.out.println(inputLine);

        }

    }

    public boolean test_fichier_texte() throws Exception {
        boolean reponse_correct = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date debut = null;
        String ojudui, cle = null;
        ojudui = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        String fileName = "libraries/benito/licence/police.txt";
        String cl = "libraries/benito/licence/cl.txt";

        // This will reference one line at a time
        String line = null;
        System.out.println(
                "dbut '"
                + ojudui + "'");
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(
                        ""
                        + line + "'");
                debut = dateFormat.parse(line);
            }

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileReader fileR = new FileReader(cl);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedR
                    = new BufferedReader(fileR);

            while ((line = bufferedR.readLine()) != null) {
                System.out.println(
                        ""
                        + line + "'");
                cle = line;
            }
            System.out.println(
                    "cle en memoire : "
                    + cle + "'");

            // Always close files.
            bufferedR.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + cl + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + cl + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }

        String no = null;
        fin = dateFormat.parse("15/05/2020");
        todayDate = new Date();
        Random id = new Random();
        int numb = id.nextInt(50);

        if (todayDate.before(fin) && todayDate.after(debut)) {
            //se rassurer si licene deja payee pour l'annee
            if (memoire.connecte.equals("null")) {
                PasswordAuthentication f = new PasswordAuthentication();
                //rassurer que le numero de licence est unique
                if ("nom".equals(memoire.numero)) {
                    if (memoire.essaie != 0) {

                        if (memoire.licence == 0) {
                            // a remplacer 1 par numb
                            lire("" + numb);
                            no = JOptionPane.showInputDialog(null, "Veuillez entrer la licence  20" + numb + "17 \nEssaie(s) restant : " + memoire.essaie, "Licence 20" + numb + "17", JOptionPane.INFORMATION_MESSAGE);
                            memoire.licence = numb;
                        } else {
                            // a remplacer 1 par licence
                            lire("" + memoire.licence);
                            no = JOptionPane.showInputDialog(null, "Veuillez entrer la licence : 20" + memoire.licence + "17\nEssaie(s) restant : " + memoire.essaie, "Licence : 20" + memoire.licence + "17", JOptionPane.INFORMATION_MESSAGE);

                        }
                        memoire.essaie--;
                    } else {
                        JOptionPane.showMessageDialog(null, "Fonctionnalité blocquée ", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    if (memoire.essaie != 0) {
                        lire("" + memoire.numero);
                        no = JOptionPane.showInputDialog(null, "Veuillez entrer la licence  20" + memoire.numero + "17\nEssaie(s) restant : " + memoire.essaie, "Licence 20" + memoire.numero + "17", JOptionPane.INFORMATION_MESSAGE);
                        memoire.essaie--;

                    } else {
                        JOptionPane.showMessageDialog(null, "Fonctionnalité blocquée ", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    }

                }

                if (no != null) {
                    Boolean decode;
                    decode = f.authenticat(no, ereur);

                    if (decode == true) {
                        reponse_correct = true;
                        try {
                            FileWriter fileWriter
                                    = new FileWriter(ft);

                            BufferedWriter bufferedWriter
                                    = new BufferedWriter(fileWriter);

                            bufferedWriter.write(cl);

                            bufferedWriter.close();
                        } catch (IOException ex) {
                            System.out.println(
                                    "Error writing to file '"
                                    + fileName + "'");
                        }
                        modifier_num("" + numb);

                    } else {
                        JOptionPane.showMessageDialog(null, "ERREUR de licence !", "ERREUR", JOptionPane.INFORMATION_MESSAGE);

                        reponse_correct = false;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Saisie du mot de passe erronée !", "ERREUR", JOptionPane.INFORMATION_MESSAGE);

                    reponse_correct = false;

                }

            } else {
                reponse_correct = true; //licence deja validee 
            }
        } else {

            reponse_correct = false;

        }
        return reponse_correct;
    }

    private void modifier_num(String num) throws IOException, JSONException {

        String url = memoire.modifier_numero;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "password=" + num;

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
        String inputLine, test = null;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            test = inputLine.trim();

            System.out.println(inputLine);

        }
        org.json.JSONObject bj = new JSONObject(test);

    }

    private void lire(String code) throws IOException, JSONException {

        //System.out.println("MOT de passe hashe : " + pass);
        String url = memoire.lire_cle;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "password=" + code;

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

        String inputLine, test = null;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            test = inputLine.trim();

            System.out.println(inputLine);

        }
        org.json.JSONObject bj = new JSONObject(test);

        org.json.JSONObject jsonArray = bj.getJSONObject("personne");
        ereur = jsonArray.getString("personne");
        System.out.println(ereur);

    }
}
