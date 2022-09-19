/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pack.impression;

import com.pack.entity.Entete;
import etablissement.impressionmodel_11;
import etablissement.memoire;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Junior ENAMA
 */
public final class listeAdmis extends JPanel implements Printable {

    int v = 0;
    String ancienne_notes;
    List<impressionmodel_11> eleves;
    int comp = 29;

    public listeAdmis() throws IOException, JSONException {
        eleves = new ArrayList<>();
        eleves = liste_classement_eleve();
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(listeAdmis.this);
        pj.printDialog();
        try {
            pj.setJobName("liste admis " + memoire.classe);
            PageFormat pf = pj.defaultPage();
            Paper p = pf.getPaper();
            p.setImageableArea(9, 0, 1024, 1024);
            pf.setPaper(p);
            pf.setOrientation(PageFormat.PORTRAIT);
            System.out.println("pf.largeur hauteur()=" + pf.getHeight() + "  pf.largeur obtenu=" + pf.getWidth());
            pj.setPrintable(this, pf);
            pj.print();
        } catch (Exception PrintException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'impression \n[ motif : " + PrintException.getMessage() + " ]");
            PrintException.printStackTrace();

        }
        inaitiallisateur();

    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int i = 1;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        if (pageIndex * comp >= eleves.size()) {
            return NO_SUCH_PAGE;
        }
        Entete report = new Entete();

        i = pageIndex * 29 + 1;
        g2.setColor(Color.black);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));

        // g2.drawImage((new ImageIcon(report.getLinkFiligrane())).getImage(), 150, 300,300,300, null);
        g2.drawString(report.getHierarchieLevel1(), 20, 40);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
        g2.drawString(report.getHierarchieLevel1a(), 25, 50);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString(report.getCountry(), 440, 40);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
        g2.drawString("Republic of Cameroon", 442, 50);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString(report.getHierarchieLevel2(), 20, 65);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
        g2.drawString(report.getHierarchieLevel2a(), 25, 75);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString(report.getDevice(), 460, 70);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
        g2.drawString("Peace-Work-Fatherland", 463, 80);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString(report.getHierarchieLevel3(), 20, 90);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
        g2.drawString(report.getHierarchieLevel3a(), 25, 100);
        g2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        g2.drawString(report.getSchool().toUpperCase(), 200, 123);
        g2.drawImage((new ImageIcon(report.getLinkLogo())).getImage(), 250, 35, 70, 60, null);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString("Annee Scolaire/", 20, 140);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 10));
        g2.drawString("     School year : " + report.getAnneeScolaire(), 100, 140);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString("BP " + report.getBp() + "   Tel/phone : " + report.getNumero(), 20, 150);
        g2.drawString("Date : " + new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()), 20, 160);
        g2.drawLine(100, 165, 500, 165);
        // g2.drawString("Liste élève "+, 115, 170);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(19, 185 - 10, 552, 25);
        g2.setColor(Color.black);

        g2.drawString("Liste de merites  : " + memoire.classe + " pour " + memoire.choix_sequence2, 115, 190);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));

        int positionY = 210, positionX = 20;
        /*profile*/
        g2.setColor(Color.DARK_GRAY);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawRect(positionX, positionY - 10, 25, 20);
        g2.drawRect(positionX + 205, positionY - 10, 50, 20);
        g2.drawRect(positionX + 255, positionY - 10, 50, 20);
        g2.drawRect(positionX + 305, positionY - 10, 50, 20);
        g2.drawRect(positionX + 355, positionY - 10, 50, 20);
        g2.drawRect(positionX + 405, positionY - 10, 145, 20);
        //g2.drawRect(positionX+505,positionY-10 , 120, 20);
        //g2.drawRect(positionX+505,positionY-10 , 45, 20);
        g2.drawRect(positionX, positionY - 10, 205, 20);

        g2.drawString("N°", positionX + 8, positionY);
        g2.drawString("Noms et prenoms ", positionX + 28, positionY);
        g2.drawString("Code", positionX + 200 + 10, positionY);
        g2.drawString("sexe", positionX + 255 + 10, positionY);
        g2.drawString(memoire.choix_sequence2, positionX + 305 + 7, positionY);
        g2.drawString(ancienne_notes, positionX + 355 + 7, positionY);
        g2.drawString("Statut en cours", positionX + 405 + 20, positionY);
        // g2.drawString("blame", positionX+455+10, positionY);
        //g2.drawString("Appre.", positionX+505+10, positionY);

        g2.setColor(Color.black);
        positionY = positionY + 19;
        Double a = 0.0;
        String decideur;

        for (int j = pageIndex * comp; j < eleves.size() && j < comp * (pageIndex + 1); j++) {

            g2.drawRect(positionX, positionY - 10, 25, 20);
            g2.drawRect(positionX, positionY - 10, 205, 20);
            g2.drawRect(positionX + 205, positionY - 10, 50, 20);
            g2.drawRect(positionX + 255, positionY - 10, 50, 20);
            g2.drawRect(positionX + 305, positionY - 10, 50, 20);
            g2.drawRect(positionX + 355, positionY - 10, 50, 20);
            g2.drawRect(positionX + 405, positionY - 10, 145, 20);
            // g2.drawRect(positionX+505,positionY-10 , 100, 20);
            //g2.drawRect(positionX+505,positionY-10 , 45, 20);
            Color vert = new Color(0, 102, 0);
            g2.drawString("" + i, positionX + 3, positionY);
            g2.drawString(trimNom(eleves.get(j).nom()), positionX + 28, positionY);
            g2.drawString(eleves.get(j).note1(), positionX + 200 + 10, positionY);
            g2.drawString(eleves.get(j).sexe(), positionX + 255 + 10, positionY);
            a = Double.parseDouble(eleves.get(j).moyenne());
            if (a >= 12) {
                g2.setColor(vert);
                g2.drawString(pource("" + a), positionX + 305 + 10, positionY);
                g2.setColor(Color.black);
            } else if (a < 12 && a > 10) {
                g2.setColor(Color.black);
                g2.drawString(pource("" + a), positionX + 305 + 10, positionY);
                g2.setColor(Color.black);
            } else if (a > 8 && a < 10) {
                g2.setColor(Color.blue);
                g2.drawString(pource("" + a), positionX + 305 + 10, positionY);
                g2.setColor(Color.black);
            } else if (a == 0) {
                g2.setColor(Color.red);
                g2.drawString("Abs", positionX + 305 + 10, positionY);
                g2.setColor(Color.black);
            } else {
                g2.setColor(Color.red);
                g2.drawString(pource("" + a), positionX + 305 + 10, positionY);
                g2.setColor(Color.black);
            }
            decideur = decision(Double.parseDouble(eleves.get(j).note20a()), Double.parseDouble(eleves.get(j).moyenne()), v);
            g2.drawString(pource(eleves.get(j).note20a()), positionX + 355 + 10, positionY);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString("" + decideur, positionX + 405 + 5, positionY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            // g2.drawString("", positionX+455+10, positionY);
            //g2.drawString("", positionX+505+10, positionY);
            i = i + 1;
            positionY = positionY + 20;
        }

        int page = (pageIndex + 1);
        g2.drawString("Page   - " + page + " -", positionX + 240 + 10, 814);

        return PAGE_EXISTS;
    }

    private String trimNom(String name) {

        if (name.length() > 28) {
            return name.substring(0, 28) + ".";
        } else {
            return name;
        }
    }

    private List<impressionmodel_11> liste_classement_eleve() throws IOException, JSONException {

        String url = memoire.liste_eleve_par_classement;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "classe=" + memoire.classe;

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

        org.json.JSONObject bj = new JSONObject(inline2);
        org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
        int totalEleves = jsonArray.length();

        List<impressionmodel_11> liste_eleve;
        liste_eleve = new ArrayList<>();

        ancienne_notes = intelligence(memoire.choix_sequence2.toLowerCase());
        String seq2;
        seq2 = memoire.choix_sequence2.toLowerCase();
        for (int i = 0; i < jsonArray.length(); i++) {
            impressionmodel_11 ed = new impressionmodel_11();

            JSONObject s11 = jsonArray.getJSONObject(i);

            String code = s11.getString("id_eleve");

            String nom_eleve = s11.getString("nom");
            String rang = s11.getString("rang");
            String note1_eleve = s11.getString(seq2);
            String sexe = s11.getString("sexe");
            if ((!"sq1".equals(ancienne_notes)) && (!"annuel".equals(ancienne_notes))) {
                String ancienne_note = s11.getString(ancienne_notes);
                ed.note20a(ancienne_note);
                v = 1;
            } else {
                ed.note20a("0.0");
                v = 0;
            }

            ed.nom(nom_eleve);
            ed.sexe(sexe);
            ed.classe(rang);
            ed.moyenne(note1_eleve);// pour contact parents
            ed.note1(code); //pour code eleve
            System.out.println(nom_eleve + " " + code);
            liste_eleve.add(ed);

        }
        in.close();
        return liste_eleve;

    }

    public void inaitiallisateur() {
        memoire.MOYGENc = 0;
        memoire.nombre_fille = 0;
        memoire.nombre_fille_absente = 0;
        memoire.nombre_garcon_absent = 0;
        memoire.nombre_admis = 0;
        memoire.nombre_garcon = 0;
        memoire.nombre_absent = 0;
        memoire.nombre_fille_admise = 0;
        memoire.nombre_garcons_admis = 0;
        memoire.pourcentage_reussite = 0;
        memoire.mention1 = 0;
        memoire.mention2 = 0;
        memoire.mention3 = 0;
        memoire.mention4 = 0;
        memoire.mention5 = 0;
        memoire.mention6 = 0;
        memoire.mention7 = 0;
        memoire.mention8 = 0;
        memoire.mention9 = 0;
        memoire.mention10 = 0;        
    }

    private String pource(String name) {
        try {
            BigDecimal valeur = (new BigDecimal(name)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            if (valeur.doubleValue() < 10) {
                return "0" + valeur.toString().replace(".", ",");
            }

            return valeur.toString().replace(".", ",");
        } catch (NumberFormatException ex) {
            return "/";
        }
    }

    private String intelligence(String moyenne) {

        if (moyenne.equals("sq1")) {
            return "sq1";
        } else if (moyenne.equals("trim1")) {
            return "sq1";
        } else if (moyenne.equals("sq3")) {
            return "trim1";
        } else if (moyenne.equals("trim2")) {
            return "trim1";
        } else if (moyenne.equals("sq5")) {
            return "trim2";
        } else if (moyenne.equals("trim3")) {
            return "trim2";
        } else if (moyenne.equals("annuel")) {
            return "annuel";
        } else {
            return "erreur";
        }

    }

    private String decision(double a, double b, int dec) {
        String deci = null;
        if (dec == 1) {
            if (b > a) {
                if (b > 10) {
                    double comparaison = (b - a);
                    if (comparaison > 0.0 && comparaison <= 2.0) {
                        deci = "Evolution minim peut faire mieux";
                    } else if (comparaison > 2.0 && comparaison <= 3.0) {
                        deci = "Evolution moyenne  courage! ";
                    } else if (comparaison > 3.0 && comparaison < 5.0) {
                        deci = "Reussite a 60 % si travail maintenue";
                    } else {
                        deci = "Reussite a 80 % si travail maintenue";

                    }
                } else {
                    double comparaison = (b - a);
                    if (comparaison > 0.0 && comparaison <= 2.0) {
                        deci = "Evolution minim peut faire mieux";
                    } else if (comparaison > 2.0 && comparaison <= 3.0) {
                        deci = "Evolution moyenne  courage! ";
                    } else if (comparaison > 3.0 && comparaison < 5.0) {
                        deci = "Assez bonne evolution";
                    } else {
                        deci = "Reussite en vue si travail maintenue";
                    }
                }
            } else {
                if (b < 10) {
                    double comparaison = (a - b);
                    if (comparaison > 0.0 && comparaison <= 2.0) {
                        deci = "Attention en baisse peux echouer";
                    } else if (comparaison > 2.0 && comparaison <= 3.0) {
                        deci = "Echec visible a 55 %";
                    } else if (comparaison > 3.0 && comparaison < 5.0) {
                        deci = "Echec visible a 65 % ";
                    } else {
                        deci = "Echec visible a 90 % ";

                    }
                } else {
                    double comparaison = (a - b);
                    if (comparaison > 0.0 && comparaison <= 2.0) {
                        deci = "Attention  travail en baisse ";
                    } else if (comparaison > 2.0 && comparaison <= 3.0) {
                        deci = "Grosse chute de travail ";
                    } else if (comparaison > 3.0 && comparaison < 5.0) {
                        deci = "Echec visible a 40 % ";
                    } else {
                        deci = "Echec visible a 50%  ";

                    }

                }
            }
        } else {
            if (b <= 5.0) {
                deci = "Probabilite echec si exam a 90%";
            } else if (b > 5.0 && b <= 7.0) {
                deci = "Probabilite echec si exam a 75%";
            } else if (b > 7.0 && b <= 8.0) {
                deci = "Attention echec si exam a 60%";
            } else if (b > 8.0 && b < 10.0) {
                deci = "Probabilite echec si exam a 50%";
            } else if (b >= 10.0 && b < 12.0) {
                deci = "Probabilite reussite si exam a 50%";
            } else if (b > 12.0 && b <= 13.0) {
                deci = "Probabilite reussite si exam a 75%";
            } else if (b > 13.0 && b <= 15.0) {
                deci = "Mention Bien si exam a 50%";
            } else if (b > 15.0) {
                deci = "Mention Bien si exam a 65%";
            }
        }
        return deci;
    }

   
    
}
