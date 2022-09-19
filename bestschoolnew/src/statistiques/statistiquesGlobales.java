/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistiques;

import com.pack.entity.Entete;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Benito
 */
public final class statistiquesGlobales extends JPanel implements Printable {

    int npreset = 0, nsbs = 0;
    String classe, periode;

    public statistiquesGlobales(String classe, String periode) {
        this.classe = classe;
        this.periode = periode;
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(statistiquesGlobales.this);
        //pj.printDialog();
        try {
            pj.setJobName("STATISTIQUES " + classe + "-" + periode);
            PageFormat pf = pj.defaultPage();
            Paper p = pf.getPaper();
            p.setImageableArea(1, 1, 1024, 1024);
            pf.setPaper(p);
            pf.setOrientation(PageFormat.PORTRAIT);
            pj.setPrintable(this, pf);
            pj.print();
            System.out.println("pf.largeur hauteur()=" + pf.getHeight() + "  pf.largeur obtenu=" + pf.getWidth());

        } catch (Exception PrintException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'impression \n[ motif : " + PrintException.getMessage() + " ]");
            PrintException.printStackTrace();

        }

        inaitiallisateur();
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Entete report = new Entete();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;

        }
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
        g2.setColor(Color.red);
        g2.drawString((" statistiques " + classe + " pour  :" +periode).toUpperCase(), 120, 123);
        g2.setColor(Color.black);
        g2.drawImage((new ImageIcon(report.getLinkLogo())).getImage(), 250, 35, 70, 60, null);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString("Annee Scolaire/", 20, 140);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 10));
        g2.drawString("     School year : " + report.getAnneeScolaire(), 100, 140);

        g2.drawLine(100, 155, 500, 155);

        int positionY = 178, positionX = 20, j = 250;
        /*profile*/
        g2.setColor(Color.LIGHT_GRAY);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawRect(positionX, positionY, 250, 45);
        g2.fillRect(positionX, positionY, 250, 45);

        g2.setColor(Color.black);

        g2.drawString("STATISTIQUES FILLES  : " + periode, positionX + 100, positionY - 10);
        positionY += 15;
        g2.setColor(Color.darkGray);
        g2.drawString("Nombre total de filles  : ", positionX + 10, positionY);
        g2.drawString("" + pource1("" + memoire.nombre_fille), j, positionY);
        // g2.drawRect(positionX, positionY, 650, 45);
        g2.setColor(Color.darkGray);

        g2.drawString("  Nombre d'absences      : ", positionX, positionY + 20);
        g2.drawString("" + pource1("" + memoire.nombre_fille_absente), j, positionY + 20);
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawRect(positionX, positionY + 5, 250, 45);
        g2.setColor(Color.darkGray);

        g2.drawString("  Nombre de filles admises: ", positionX, positionY + 40);
        g2.drawString("" + pource1("" + memoire.nombre_fille_admise) + " ", j, positionY + 40);
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawRect(positionX, positionY + 5, 250, 45);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(" ", positionX, positionY + 65);
        double pourcentage1 = 0;
        if ((double) memoire.nombre_fille != 0) {
            pourcentage1 = (double) memoire.nombre_fille_admise * 100 / (double) memoire.nombre_fille;
        }
        BigDecimal valeur1 = (new BigDecimal(pourcentage1)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        double moyen_eleve1 = valeur1.doubleValue();
        g2.drawString(" Pourcentage de reussite : " + pource("" + valeur1) + " %", positionX + 290, positionY);
        g2.drawString(" Pourcentage d'echec     : " + pource("" + ((100 - moyen_eleve1))) + " %", positionX + 290, positionY + 20);
        positionY += 15;
        g2.setColor(Color.darkGray);
        g2.setColor(Color.LIGHT_GRAY);

        g2.setColor(Color.darkGray);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        int positionY2 = positionY + 80;
        /*profile*/
        g2.setColor(Color.LIGHT_GRAY);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawRect(positionX, positionY2, 250, 45);
        g2.fillRect(positionX, positionY2, 250, 45);

        g2.setColor(Color.black);

        g2.drawString("STATISTIQUES GARCONS : " + periode, positionX + 100, positionY2 - 10);
        positionY2 += 15;
        g2.setColor(Color.darkGray);

        g2.drawString("Nombre de garcons      : ", positionX + 10, positionY2);
        g2.drawString("" + pource1("" + memoire.nombre_garcon), j, positionY2);
        // g2.drawRect(positionX, positionY, 650, 45);
        g2.setColor(Color.darkGray);

        g2.drawString("  Nombre d'absents       : ", positionX, positionY2 + 20);
        g2.drawString("" + pource1("" + memoire.nombre_garcon_absent), j, positionY2 + 20);
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawRect(positionX, positionY2 + 5, 250, 45);
        g2.setColor(Color.darkGray);

        g2.drawString("  Nombre de garcons admis: ", positionX, positionY2 + 40);
        g2.drawString("" + pource1("" + memoire.nombre_garcons_admis) + " ", j, positionY2 + 40);
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawRect(positionX, positionY2 + 5, 250, 45);
        g2.setColor(Color.DARK_GRAY);
        //g2.drawString("signature bÃ©nÃ©ficiaire", positionX, positionY2+65);
        double pourcentage = 0;
        if ((double) memoire.nombre_garcon != 0) {
            pourcentage = (double) memoire.nombre_garcons_admis * 100 / (double) memoire.nombre_garcon;
        }
        BigDecimal valeur = (new BigDecimal(pourcentage)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        double moyen_eleve = valeur.doubleValue();
        g2.drawString(" Pourcentage de reussite : " + pource("" + valeur) + " %", positionX + 290, positionY2);
        g2.drawString(" Pourcentage d'echec     : " + pource("" + (100 - moyen_eleve)) + " %", positionX + 290, positionY2 + 20);
        positionY2 += 15;
        g2.setColor(Color.darkGray);
        positionY += 15;
        g2.setColor(Color.darkGray);
        g2.setColor(Color.LIGHT_GRAY);

        // g2.drawLine(1, 80, 600, 80);
        g2.setColor(Color.darkGray);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        int position = positionY2 + 90;
        /*profile*/
        g2.setColor(Color.LIGHT_GRAY);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawRect(positionX, position, 250, 45);
        g2.fillRect(positionX, position, 250, 45);

        g2.setColor(Color.black);

        g2.drawString("TOTAL : " + memoire.classe, positionX + 100, position - 10);
        position += 15;
        g2.setColor(Color.darkGray);
        int totale = (memoire.nombre_garcon + memoire.nombre_fille);
        npreset = totale;
        g2.drawString("Nombre d'eleve     : ", positionX + 10, position);
        g2.drawString("" + totale, j, position);
        g2.setColor(Color.darkGray);

        g2.drawString("  Nombre d'absents  : ", positionX, position + 20);
        nsbs = memoire.nombre_garcon_absent + memoire.nombre_fille_absente;
        g2.drawString("" + nsbs, j, position + 20);
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawRect(positionX, position + 5, 250, 45);
        g2.setColor(Color.darkGray);

        g2.drawString("  Nombre d' admis   : ", positionX, position + 40);
        g2.drawString("" + (memoire.nombre_garcons_admis + memoire.nombre_fille_admise) + " ", j, position + 40);
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawRect(positionX, position + 5, 250, 45);
        g2.setColor(Color.DARK_GRAY);
        //g2.drawString("signature bÃ©nÃ©ficiaire", positionX, positionY2+65);
        double pourcen = 0;
        if (totale != 0) {
            pourcen = (double) (memoire.nombre_garcons_admis + memoire.nombre_fille_admise) * 100 / (double) totale;
        }
        BigDecimal va = (new BigDecimal(pourcen)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        double moyen_ = va.doubleValue();
        g2.drawString(" Pourcentage de reussite : " + pource("" + va) + " %", positionX + 290, position);
        g2.drawString(" Pourcentage d'echec     : " + pource("" + (100 - moyen_)) + " %", positionX + 290, position + 20);
        g2.setColor(Color.darkGray);
        g2.drawLine(1, position + 60, 800, position + 60);

        g2.drawString(" -  NULL       ( [0 - 4[ ) : " + pource1("" + memoire.mention1) + " / " + totale, positionX + 20, position + 85);
        g2.drawString(" -  Faible     ([4 -7,50[) : " + pource1("" + memoire.mention2) + " / " + totale, positionX + 20, position + 100);
        g2.drawString(" -  Mediocre   ([7,50-10[) : " + pource1("" + memoire.mention3) + " / " + totale, positionX + 20, position + 115);
        g2.drawString(" -  Passable   ([10 - 12[) : " + pource1("" + memoire.mention4) + " / " + totale, positionX + 20, position + 130);
        g2.drawString(" -  Assez-bien ([12 - 14[) : " + pource1("" + memoire.mention5) + " / " + totale, positionX + 20, position + 145);
        g2.drawString(" -  Bien       ([14 - 16[) : " + pource1("" + memoire.mention6) + " / " + totale, positionX + 20, position + 160);
        g2.drawString(" -  Très-bien  ([16 - 18[) : " + pource1("" + memoire.mention7) + " / " + totale, positionX + 20, position + 175);
        g2.drawString(" -  Excellent  ([18 - 19[) : " + pource1("" + memoire.mention8) + " / " + totale, positionX + 20, position + 190);
        g2.drawString(" -  Parfait    ([19 - 20]) : " + pource1("" + memoire.mention9) + " / " + totale, positionX + 20, position + 205);

        g2.setColor(Color.BLUE);

        g2.drawString(" * Moyenne premier : " + pource("" + memoire.MOYP) + " / 20", positionX + 260, position + 85);
        g2.drawString(" * Moyenne dernier : " + pource("" + memoire.MOYD) + " / 20", positionX + 260, position + 100);
        g2.drawString(" * Moyen. G.classe : " + pource(memoire.MOYGENstatistiques) + " / 20", positionX + 260, position + 115);

        g2.drawString(" * Nombre de tableaux d'honneur : " + pource1("" + memoire.mention10), positionX + 260, position + 135);
        int positionu = position + 210;
        g2.setColor(Color.black);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        g2.drawString(("Statistiques par matière (P V) : " + classe).toUpperCase(), positionX + 100, positionu + 15);
        positionu += 30;

        System.out.println("nombre de disciplines relevees \n:  "+memoire.disciplines+"\n note : "+memoire.note.size()+"\n Disc :"+memoire.disciplines.size());
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        int positionX1 = 20;
        int fi = 0, gm = 0, gs = 0, fm = 0, fs = 0, fa = 0, ga = 0;
        fi = memoire.matieres.size();
        ArrayList<String> pourcentagematier = new ArrayList<>();
        int fin = fi - 1;
        int nbrematiere = memoire.note.size();
        System.out.println("nombre de disciplines relevees \n:  "+memoire.disciplines.size()+"\n note : "+ memoire.note);

        for (int i = 0; i < fi; i++) {

            g2.drawRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.white);
            g2.drawString(trimMatiere(memoire.matieres.get(i)), positionX1 + 2, positionu + 10);
            positionX1 += 31;
            g2.setColor(Color.black);

        }
        positionX1 = 51;
        positionu += 15;
        g2.drawRect(20, positionu, 30, 15);
        g2.drawString("G>10", 20 + 2, positionu + 10);

        for (int l = 0; l < fin; l++) {
            for (int note = 0; note < nbrematiere; note++) {

                if ((memoire.matieres.get(l).equals(memoire.disciplines.get(note))) && (Double.parseDouble(memoire.note.get(note)) >= 10)) {
                    if (memoire.sex.get(note).equals("M")) {
                        gm++;
                    } else {
                        fm++;
                    }
                }
            }

            g2.drawRect(positionX1, positionu, 30, 15);
            //g2.setColor(Color.LIGHT_GRAY);
            //g2.fillRect(positionX1, positionu, 26, 15);
            g2.drawString(" " + gm, positionX1 + 4, positionu + 10);
            positionX1 += 31;
            double pour = ((double) (gm + fm) / (double) (npreset - (nsbs))) * 100;
            pourcentagematier.add("" + pour);
            gm = 0;
            fm = 0;

        }
        positionX1 = 51;
        positionu += 15;
        g2.drawRect(20, positionu, 30, 15);
        g2.drawString("G<10", 20 + 2, positionu + 10);

        for (int l = 0; l < fin; l++) {
            for (int note = 0; note < nbrematiere; note++) {

                if ((memoire.matieres.get(l).equals(memoire.disciplines.get(note))) && (Double.parseDouble(memoire.note.get(note)) < 10) && (Double.parseDouble(memoire.note.get(note)) > 0)) {
                    if (memoire.sex.get(note).equals("M")) {
                        gs++;
                    }
                }
            }
            g2.drawRect(positionX1, positionu, 30, 15);
            //g2.setColor(Color.LIGHT_GRAY);
            //g2.fillRect(positionX1, positionu, 26, 15);
            g2.drawString(trimMatiere(" " + gs), positionX1 + 4, positionu + 10);
            positionX1 += 31;
            gs = 0;

        }
        positionX1 = 51;
        positionu += 15;
        g2.drawRect(20, positionu, 30, 15);
        g2.drawString("G Abs", 20 + 2, positionu + 10);

        for (int l = 0; l < fin; l++) {
            for (int note = 0; note < nbrematiere; note++) {
                if (memoire.matieres.get(l ).equals(memoire.disciplines.get(note)) && memoire.sex.get(note).equals("M")) {
                    if ("//".equals(memoire.note.get(note))) {
                        ga++;
                    }
                }
            }

            g2.drawRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.black);                    //g2.setColor(Color.LIGHT_GRAY);
            //g2.fillRect(positionX1, positionu, 26, 15);
            g2.drawString(trimMatiere(" " + ga), positionX1 + 4, positionu + 10);
            positionX1 += 31;

            ga = 0;

        }

        positionX1 = 51;
        positionu += 15;
        g2.drawRect(20, positionu, 30, 15);
        g2.drawString("F>10", 20 + 2, positionu + 10);

        for (int l = 0; l < fin; l++) {
            for (int note = 0; note < nbrematiere; note++) {
                if ((memoire.matieres.get(l ).equals(memoire.disciplines.get(note))) && (Double.parseDouble(memoire.note.get(note)) >= 10)) {
                    if (memoire.sex.get(note).equals("F")) {
                        fm++;
                    }
                }
            }
            g2.drawRect(positionX1, positionu, 30, 15);
            //g2.setColor(Color.LIGHT_GRAY);
            //g2.fillRect(positionX1, positionu, 26, 15);
            g2.drawString(trimMatiere(" " + fm), positionX1 + 4, positionu + 10);
            positionX1 += 31;
            fm = 0;

        }
        positionX1 = 51;
        positionu += 15;
        g2.drawRect(20, positionu, 30, 15);
        g2.drawString("F<10", 20 + 2, positionu + 10);
        for (int l = 0; l < fin; l++) {
            for (int note = 0; note < nbrematiere; note++) {
                if ((memoire.matieres.get(l).equals(memoire.disciplines.get(note))) && (Double.parseDouble(memoire.note.get(note)) < 10) && (Double.parseDouble(memoire.note.get(note)) > 0)) {
                    if (memoire.sex.get(note).equals("F")) {
                        fs++;
                    }
                }
            }

            g2.drawRect(positionX1, positionu, 30, 15);
            g2.drawString(trimMatiere(" " + fs), positionX1 + 4, positionu + 10);
            positionX1 += 31;
            fs = 0;

        }
        positionX1 = 51;
        positionu += 15;
        g2.drawRect(20, positionu, 30, 15);
        g2.drawString("F Abs", 20 + 2, positionu + 10);

        for (int l = 0; l < fin; l++) {
            for (int note = 0; note < nbrematiere; note++) {
                if ((memoire.matieres.get(l).equals(memoire.disciplines.get(note)) && (memoire.sex.get(note).equals("F"))) && (Double.parseDouble(memoire.note.get(note)) == 0.0)) {

                    fa++;

                }
            }
            g2.drawRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.black);                    //g2.setColor(Color.LIGHT_GRAY);
            //g2.fillRect(positionX1, positionu, 26, 15);
            g2.drawString(trimMatiere(" " + fa), positionX1 + 4, positionu + 10);
            positionX1 += 31;

            fa = 0;

        }
        positionX1 = 51;
        positionu += 15;
        g2.drawRect(20, positionu, 30, 15);
        g2.drawString("%Reus", 20 + 2, positionu + 10);

        for (int l = 0; l < fin; l++) {

            g2.drawRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.ORANGE);
            g2.fillRect(positionX1, positionu, 30, 15);
            g2.setColor(Color.black);                    //g2.setColor(Color.LIGHT_GRAY);
            //g2.fillRect(positionX1, positionu, 26, 15);
            g2.drawString(pource1(trimMatiere1(pourcentagematier.get(l))).replace(".", ",") + "%", positionX1 + 1, positionu + 10);
            positionX1 += 31;

        }
        positionu += 20;
        fi = 0;
        return PAGE_EXISTS;

    }

    private String trimMatiere1(String name) {
        if (name.length() > 4) {
            return name.substring(0, 4);
        } else {
            return name;
        }
    }

    private String trimMatiere(String name) {
        if (name.length() > 3) {
            return name.substring(0, 3) + "";
        } else {
            return name;
        }
    }

    private String pource1(String name) {
        try {
            BigDecimal valeur = (new BigDecimal(name)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            if (valeur.doubleValue() < 10) {
                return "0" + name;
            }
            return name;

        } catch (NumberFormatException ex) {
            return "/";
        }
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
        memoire.MOYP = "0";
        memoire.MOYD = "0";

    }
}
