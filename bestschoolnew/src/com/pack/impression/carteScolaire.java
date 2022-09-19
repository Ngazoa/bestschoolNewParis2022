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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.login;

/**
 *
 * @author Junior ENAMA
 */
public class carteScolaire extends JPanel implements Printable {

    List<impressionmodel_11> eleves = new ArrayList<impressionmodel_11>();

    int comp = 6;

    public carteScolaire(List<impressionmodel_11> eleve) {
        eleves = eleve;
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(this);
        pj.printDialog();
        try {
            pj.setJobName("carte de " + memoire.classe);
            PageFormat pf = pj.defaultPage();
            Paper p = pf.getPaper();
            p.setImageableArea(9, 0, 1024, 1024);
            pf.setPaper(p);
            pf.setOrientation(PageFormat.PORTRAIT);
            System.out.println("pf.largeur hauteur()=" + pf.getHeight() + "  pf.largeur obtenu=" + pf.getWidth());
            pj.setPrintable(this, pf);
            if (pj != null) {
                pj.print();
            }
        } catch (PrinterException | IllegalArgumentException PrintException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'impression \n[ motif : " + PrintException.getMessage() + " ]");
            PrintException.printStackTrace();

        }
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int i = 1;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        Image imagesig = null;
        try {
            URL url = new URL(memoire.signature);
            imagesig = ImageIO.read(url);
            System.out.println("UUUURL <<< " + url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(impression_ecole_primaire.Impression.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(impression_ecole_primaire.Impression.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pageIndex * comp >= eleves.size()) {
            return NO_SUCH_PAGE;
        }
        Entete report = new Entete();
        int x1 = 40;

        int positionY = 65;
        int positionX, k = 5, p;
        for (int j = pageIndex * comp; j < eleves.size() && j < comp * (pageIndex + 1); j++) {
            //positionY += 55;

            if ((i % 2 == 0)) {
                x1 = 315;
                positionY -= 10;

            } else {
                x1 = 20;

            }
            g2.setColor(Color.black);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            URL url2 = null;
            Image carteIm = null;
            try {
                url2 = new URL(memoire.carte);
                carteIm = ImageIO.read(url2);
            } catch (MalformedURLException ex) {
                Logger.getLogger(carteScolaire.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(carteScolaire.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.drawImage(carteIm, x1 - 10, positionY - 15, 285, 190, null);

            g2.drawString("Republique du Cameroon", x1, positionY);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString("Republic of Cameroon", x1, positionY + 10);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            g2.drawString(report.getDevice(), x1 + 140, positionY);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString("Peace-Work-Fatherland", x1 + 140, positionY + 10);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
            g2.drawString("CARTE SCOLAIRE ", x1 + 66, positionY + 30);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 9));
            g2.drawString(report.getSchool().toUpperCase(), x1 + 47, positionY + 55);
            URL url1 = null;
            Image imageFili = null;
            try {
                url1 = new URL(memoire.filigran);
                imageFili = ImageIO.read(url1);
            } catch (MalformedURLException ex) {
                Logger.getLogger(carteScolaire.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(carteScolaire.class.getName()).log(Level.SEVERE, null, ex);
            }

            g2.drawImage(imageFili, x1 + 70, positionY + 55, 100, 100, null);
            System.err.println("LLLLLLLLLLLOOGOOFIL" + memoire.filigran);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString("Annee Scolaire/year ", x1, positionY + 68);
            g2.drawString(memoire.anne_scolaire, x1 + 70, positionY + 68);
            g2.drawString("Classe/class : ", x1, positionY + 75);
            g2.drawString(eleves.get(j).classe(), x1 + 70, positionY + 75);

            Image image = null;
            try {
                URL url = new URL(new login().getLinkPhotosEleves() + eleves.get(j).photo());
                image = ImageIO.read(url);

            } catch (MalformedURLException ex) {
                Logger.getLogger(impression_ecole_primaire.Impression.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(impression_ecole_primaire.Impression.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.setColor(Color.LIGHT_GRAY);

            if (i % 2 == 0) {
                positionX = 310;
            } else {
                positionX = 20;
            }
            g2.fillRect(positionX + 210, positionY + 15, 60, 60);
            g2.drawImage(image, positionX + 210, positionY + 17, 57, 56, null);

            positionY = positionY + 80;

            g2.setColor(Color.black);

            /*profile*/
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));

            g2.drawString("Matricule°", positionX, positionY += 10);
            g2.drawString("Noms/prenoms", positionX, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString(" (name/surname )", positionX + 70, positionY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            g2.drawString("Date naissance ", positionX, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString(" (Date of birth )", positionX + 70, positionY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            g2.drawString("Sexe ", positionX, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString(" (Sex )", positionX + 70, positionY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            g2.drawString("Nom du pere ", positionX, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString("Father's name", positionX + 70, positionY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            g2.drawString("Nom de la mere ", positionX, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString("mother's name ", positionX + 70, positionY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            g2.drawString("Contact Parent ", positionX, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawString("parent's phone ", positionX + 70, positionY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));

            g2.setColor(Color.black);
            positionY = positionY - 70;

            g2.setColor(Color.black);

            if (i % 2 == 0) {
                positionX = 320 + 125;
            } else {
                positionX = 20 + 125;
            }
            g2.drawString("" + eleves.get(j).matricule(), positionX + 3, positionY += 10);
            g2.drawString(trimNom(": " + eleves.get(j).nom()), positionX, positionY += 10);
            g2.drawString(trimNom(": " + eleves.get(j).date() + " a/at " + eleves.get(j).lnaissance()), positionX, positionY += 10);
            g2.drawString(trimNom(": " + eleves.get(j).sexe()), positionX, positionY += 10);
            g2.drawString(trimNom(": " + eleves.get(j).r3()), positionX, positionY += 10);
            g2.drawString(trimNom(": " + eleves.get(j).r4()), positionX, positionY += 10);
            g2.drawString(trimNom(": " + eleves.get(j).moyenne()), positionX, positionY += 10);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 8));
            g2.drawImage(imagesig, positionX + 105, positionY - 15, 40, 40, null);
            g2.drawString("Visa principal", positionX + 40, positionY += 15);

            i = i + 1;
            if (i % 2 == 0) {
                positionY = positionY - 115;
            } else {
            }
        }

        return PAGE_EXISTS;
    }

    public ImageIcon createImageIcon(String filename, String description) {
        String path = "/image/" + filename;
        return new ImageIcon(this.getClass().getResource(path));
    }

    public ImageIcon createfiligrand() {
        String path = "/image/im1.png";
        return new ImageIcon(this.getClass().getResource(path));
    }

    private String trimNom(String name) {

        if (name.length() > 32) {
            return name.substring(0, 28) + ".";
        } else {
            return name;
        }
    }
}
