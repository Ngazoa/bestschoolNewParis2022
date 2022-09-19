/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pack.impression;

import etablissement.impressionmodel_11;
import etablissement.memoire;
import intendancee.memoire_intendance;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Beni
 */
class listeClasse extends JPanel implements Printable {

    List<impressionmodel_11> liste_eleves = new ArrayList<>();

    public listeClasse() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(listeClasse.this);
        pj.printDialog();
        try {
            pj.setJobName("STATISTIQUES");
            PageFormat pf = pj.defaultPage();
            Paper p = pf.getPaper();
            p.setImageableArea(1, 1, 1024, 1024);
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;

        }

        g2.setColor(Color.DARK_GRAY);
        g2.drawImage((new ImageIcon(memoire_intendance.filigran)).getImage(), 430, 20, 100, 100, null);

        g2.drawString("Ministere des enseignements secondaires", 20, 20);
        g2.drawString("REPUBLIQUE DU CAMEROUN", 400, 20);
        g2.drawString("Paix-travail-patrie", 400, 30);
        g2.setColor(Color.black);

        g2.drawString("STAISTIQUES GLOBALES CLASSE DE ", 220, 65);
        g2.setColor(Color.DARK_GRAY);

        g2.drawString(memoire.ecole, 20, 30);

        //g2.drawImage((new ImageIcon(report.getEntete().getLinkLogo())).getImage(), 250, 10,70, 50, null);
        g2.drawString("Annee Scolaire : " + memoire_intendance.anne_scolaire, 20, 40);

        g2.drawString("BP  :" + memoire_intendance.bp + " " + "   Tel : " + memoire_intendance.telephone, 20, 50);
        g2.setColor(Color.DARK_GRAY);

        g2.drawString("" + new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()), 20, 70);
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawLine(1, 80, 600, 80);
        g2.setColor(Color.darkGray);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        int positionY = 110, positionX = 20;
        /*profile*/
        g2.setColor(Color.LIGHT_GRAY);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawRect(positionX, positionY, 550, 45);
        g2.setColor(Color.black);

        liste_eleves.forEach((detail) -> {
            g2.drawRect(positionX, positionY - 100, 100, 45);
            g2.drawString(detail.nom(), positionX, 150);
        });

        return PAGE_EXISTS;
    }

}
