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
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Benito
 */
public class impressioLliselevetnote extends JPanel implements Printable {

    List<impressionmodel_11> eleves = new ArrayList<impressionmodel_11>();
    int comp = 29;

    public impressioLliselevetnote(List<impressionmodel_11> eleve) {
        eleves = eleve;
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(impressioLliselevetnote.this);
        pj.printDialog();
        try {
            pj.setJobName("Liste de remplissage de note " + memoire.classe);
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
        Image imagelogo = null;

        try {
            URL url = new URL(memoire.logo);
            imagelogo = ImageIO.read(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Impression.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Impression.class.getName()).log(Level.SEVERE, null, ex);
        }
        g2.drawImage(imagelogo, 250, 35, 70, 60, null);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.drawString("Annee Scolaire/", 20, 140);
        g2.setFont(new Font(Font.SERIF, Font.ITALIC, 10));
        g2.drawString("     School year : " + report.getAnneeScolaire(), 100, 140);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        //g2.drawString("BP "+report.getBp()+"   Tel/phone : "+report.getNumero(), 20, 150);
        g2.drawString("Enseignant :______________________________   Mati??re :_____________________________", 20, 160);
        g2.drawLine(100, 165, 500, 165);
        // g2.drawString("Liste ??l??ve "+, 115, 170);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(19, 185 - 10, 552, 25);
        g2.setColor(Color.black);

        g2.drawString("Liste remplissage de notes des ??l??ves de : " + memoire.classe, 115, 190);
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
        g2.drawRect(positionX + 405, positionY - 10, 50, 20);
        g2.drawRect(positionX + 455, positionY - 10, 50, 20);
        g2.drawRect(positionX + 505, positionY - 10, 45, 20);
        g2.drawRect(positionX, positionY - 10, 205, 20);

        g2.drawString("N??", positionX + 8, positionY);
        g2.drawString("Noms et prenoms ", positionX + 28, positionY);
        g2.drawString("Note1", positionX + 200 + 10, positionY);
        g2.drawString("Note2", positionX + 255 + 10, positionY);
        g2.drawString("Note3", positionX + 305 + 10, positionY);
        g2.drawString("Note4", positionX + 355 + 10, positionY);
        g2.drawString("Note5", positionX + 405 + 10, positionY);
        g2.drawString("Note6", positionX + 455 + 10, positionY);
        g2.drawString("Total", positionX + 505 + 10, positionY);

        g2.setColor(Color.black);
        positionY = positionY + 19;
        for (int j = pageIndex * comp; j < eleves.size() && j < comp * (pageIndex + 1); j++) {

            g2.drawRect(positionX, positionY - 10, 25, 20);
            g2.drawRect(positionX, positionY - 10, 205, 20);
            g2.drawRect(positionX + 205, positionY - 10, 50, 20);
            g2.drawRect(positionX + 255, positionY - 10, 50, 20);
            g2.drawRect(positionX + 305, positionY - 10, 50, 20);
            g2.drawRect(positionX + 355, positionY - 10, 50, 20);
            g2.drawRect(positionX + 405, positionY - 10, 50, 20);
            g2.drawRect(positionX + 455, positionY - 10, 50, 20);
            g2.drawRect(positionX + 505, positionY - 10, 45, 20);

            g2.drawString("" + i, positionX + 3, positionY);
            g2.drawString(trimNom(eleves.get(j).nom()), positionX + 28, positionY);
            g2.drawString("", positionX + 200 + 10, positionY);
            g2.drawString("", positionX + 255 + 10, positionY);
            g2.drawString("", positionX + 305 + 10, positionY);
            g2.drawString("", positionX + 355 + 10, positionY);
            g2.drawString("", positionX + 405 + 10, positionY);
            g2.drawString("", positionX + 455 + 10, positionY);
            g2.drawString("", positionX + 505 + 10, positionY);
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
}
