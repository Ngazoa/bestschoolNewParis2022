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
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Junior ENAMA
 */
public class tableauDhonneur extends JPanel implements Printable {

   // List<String> eleves = new ArrayList<>();
    List<impressionmodel_11> eleves;
    int comp = 2;

    public tableauDhonneur( List<impressionmodel_11> elevese) {
       // eleves = memoire.notetableaux;
       this.eleves=elevese;
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(this);
        pj.printDialog();
        try {
            pj.setJobName("Tableaux d'honneurs " + memoire.classe);
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
        int x1 = 40;

        int positionY = 0;
        int positionY1 = 0;
        int p = 0;
        for (int j = pageIndex * comp; j < eleves.size() && j < comp * (pageIndex + 1); j++) {
            positionY += 55;
            p += 55;

            g2.setColor(Color.black);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));

            g2.drawImage((new ImageIcon(memoire.tableauH)).getImage(), 0, p - 56, 600, 390, null);

            g2.drawString(report.getHierarchieLevel1(), 20, positionY -= 15);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString(report.getHierarchieLevel1a(), 25, positionY += 10);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getCountry(), 440, positionY -= 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString("Republic of Cameroon", 442, positionY += 10);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getHierarchieLevel2(), 20, positionY += 15);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString(report.getHierarchieLevel2a(), 25, positionY += 10);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getDevice(), 460, positionY -= 5);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString("Peace-Work-Fatherland", 463, positionY += 10);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getHierarchieLevel3(), 20, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString(report.getHierarchieLevel3a(), 25, positionY += 10);
            g2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
            g2.drawImage((new ImageIcon(report.getLinkLogo())).getImage(), 250, positionY - 60, 70, 60, null);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            //g2.drawString("Annee Scolaire/", 20, 140); 
            // g2.setFont(new Font(Font.SERIF,Font.ITALIC,10));
            //  g2.drawString("     School year : "+report.getAnneeScolaire(), 100, 140);

            // g2.drawImage((new ImageIcon(memoire.logo)).getImage(), 250, 35,70, 60, null);
            //   g2.setColor(Color.LIGHT_GRAY);
            //g2.fillRect(250, positionY+15, 60, 60);
            g2.setColor(Color.black);

            positionY = p + 200;
            int positionX = 100;
            /*profile*/
            g2.setColor(Color.DARK_GRAY);
            /*profile*/
            g2.setColor(Color.DARK_GRAY);

            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
            g2.setColor(Color.black);

            g2.drawString(eleves.get(j).nom(), positionX + 40, positionY - 5);

            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.setColor(Color.red);
            g2.drawString(virgue(eleves.get(j).moyenne() + " /20"), positionX + 50, positionY + 80);
            g2.setColor(Color.black);
            g2.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));
            g2.drawString(eleves.get(j).classe(), positionX, positionY += 30);
            g2.drawString(eleves.get(j).date(), positionX + 225, positionY);// date remplace la periode
            g2.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 11));
            g2.setColor(Color.red);
            g2.drawString(observation(eleves.get(j).moyenne()), positionX + 195, positionY + 50);
            g2.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 10));
            g2.drawString(eleves.get(j).lnaissance(), positionX + 410, positionY);//replace l'annee scolaire
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));

            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));

            g2.setColor(Color.black);
            positionY1 = positionY - 70;
            positionX = 40 + 125;
            g2.drawString("Visa/cachet chef d'etablissement", positionX + 250, positionY1 + 110);
            i = i + 1;
            positionY = positionY1 + 180;
            p = positionY1 + 180;

        }

        return PAGE_EXISTS;
    }

    private String observation(String Val) {
        String app = "", p;
        try {
            if (!Val.trim().equals("")) {
                if ((0 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 4)) {
                    app = "nul/ " + ("null").toLowerCase(Locale.ITALY);
                } else if ((4 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 6)) {
                    app = "Faible/ " + ("poor").toLowerCase(Locale.ITALY);
                } else if ((6 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 7)) {
                    app = "Très-insuf./ " + ("very-Insuffi.").toLowerCase(Locale.ITALY);
                } else if ((7 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 9)) {
                    app = "Insuffisant/ " + ("Insufficient").toLowerCase(Locale.ITALY);
                } else if ((9 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 10)) {
                    app = "Mediocre/ " + ("Mediocre").toLowerCase(Locale.ITALY);
                } else if ((10 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 12)) {
                    app = "Passable/ " + ("Average").toLowerCase(Locale.ITALY);
                } else if ((12 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 14)) {
                    p = "Assez-B/\nWell-E";
                    app = p.toLowerCase(Locale.ITALY);
                } else if ((14 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 17)) {
                    p = "Bien/\nGood";
                    app = p.toLowerCase(Locale.ITALY);
                } else if ((17 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 19)) {
                    p = "Très-B/\nVery-good";
                    app = p.toLowerCase(Locale.ITALY);
                } else if ((19 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 20)) {
                    p = "Excellent/\nExcel.";
                    app = p.toLowerCase(Locale.ITALY);
                } else if (20 == Float.parseFloat(Val)) {
                    app = "Parfait/ " + ("Perfect").toLowerCase(Locale.ITALY);
                } else {
                    app = "Absent/ " + ("Absent").toLowerCase(Locale.ITALY);

                }
            }
        } catch (NumberFormatException ex) {
        }
        return app;
    }

    private String virgue(String name) {

        if (name.length() > 2) {
            return name.replace(".", ",");
        } else {
            return name;
        }
    }
}
