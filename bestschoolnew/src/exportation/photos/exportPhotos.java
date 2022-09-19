/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exportation.photos;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.login;

/**
 *
 * @author Junior ENAMA
 */
public class exportPhotos extends JPanel implements Printable {

    List<impressionmodel_11> eleves = new ArrayList<impressionmodel_11>();
    int comp = 6;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
String classe;
    public exportPhotos(List<impressionmodel_11> eleve,String classe) {
        eleves = eleve;
this.classe=classe;
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(exportPhotos.this);
        pj.printDialog();
        try {
            pj.setJobName("IMPORTATION PHOTOS " + memoire.classe);
            PageFormat pf = pj.defaultPage();
            Paper p = pf.getPaper();
            p.setImageableArea(9, 0, 1024, 1024);
            pf.setPaper(p);
            pf.setOrientation(PageFormat.PORTRAIT);
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
        int i = 1;
        if (pageIndex * comp >= eleves.size()) {
            return NO_SUCH_PAGE;
        }
        Entete report = new Entete();

        i = pageIndex * 29 + 1;
//				
//	        g2.setColor(Color.black);
//	        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));  
//                    	        
//	       // g2.drawImage((new ImageIcon(report.getLinkFiligrane())).getImage(), 150, 300,300,300, null);
//	   
//                g2.drawString(report.getHierarchieLevel1(), 20, 40);
//                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
//                g2.drawString(report.getHierarchieLevel1a(), 25, 50); 
//                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
//                g2.drawString(report.getCountry(), 440, 40);
//                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
//                g2.drawString("Republic of Cameroon", 442, 50);
//                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
//                g2.drawString(report.getHierarchieLevel2(), 20, 65);
//                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
//                g2.drawString(report.getHierarchieLevel2a(), 25, 75);
//                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10)); 
//                g2.drawString(report.getDevice(), 460, 70);	     
//                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
//                g2.drawString("Peace-Work-Fatherland", 463, 80);
//                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
//                g2.drawString(report.getHierarchieLevel3(), 20, 90);	      
//                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
//                g2.drawString(report.getHierarchieLevel3a(), 25, 100);
//                g2.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,12));
//                g2.drawString(report.getSchool().toUpperCase(), 200, 123);
//	        g2.drawImage((new ImageIcon(report.getLinkLogo())).getImage(), 250, 35,70, 60, null);
//	        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
//                g2.drawString("Annee Scolaire/", 20, 140); 
//                g2.setFont(new Font(Font.SERIF,Font.ITALIC,10));
//                g2.drawString("     School year : "+report.getAnneeScolaire(), 100, 140);    
//                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
//	        g2.drawString("BP "+report.getBp()+"   Tel/phone : "+report.getNumero(), 20, 150);
//	        g2.drawString("Date : "+new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()), 20, 160);
//	        g2.drawLine(100, 165, 500, 165);
//	       // g2.drawString("Liste élève "+, 115, 170);
//               g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
//	       g2.setColor(Color.LIGHT_GRAY);
//               g2.fillRect(19,185-10 , 551, 25);
//               g2.setColor(Color.black);
//g2.drawString("Photos classe de : "+memoire.classe, 115, 190);
//               
//	        int positionY = 180,positionX=20;
        g2.setColor(Color.black);

        g2.drawString("Photos classe de : " + classe, 15, 20);

        int positionY = 20, positionX = 20;
        /*profile*/
        g2.setColor(Color.DARK_GRAY);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        g2.setColor(Color.black);
        positionY = positionY + 19;
        for (int j = pageIndex * comp; j < eleves.size() && j < comp * (pageIndex + 1); j++) {

            Image image = null;
            try {
                URL url = new URL(new login().getLinkPhotosEleves() + eleves.get(j).photo());
                System.out.println("??? "+url);
                image = ImageIO.read(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(impression_ecole_primaire.Impression.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(impression_ecole_primaire.Impression.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.setColor(Color.LIGHT_GRAY);
            int x = 20, y;
          //  String n = eleves.get(j).r5().trim().replace("-", "/").substring(0, 10);
           // String dat = "20/11/2019";
            for (int tof = 0; tof < 4; tof++) {
                y = positionY + 1;
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x - 2, y - 2, 125, 127);
                g2.drawImage(image, x, y, 120, 123, null);
                g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 7));
                g2.setColor(Color.black);
                g2.drawString(trimNom(eleves.get(j).nom()), x + 6, y + 109);
                g2.drawString(code_classe(eleves.get(j).classe()), x + 6, y + 114);
                g2.drawString("CR 4314", x + 6, y + 119);
                g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
                g2.drawString(eleves.get(j).r5(), x + 6, y + 9);
                x += 138;
            }
            positionY = positionY + 130;

        }

        int page = (pageIndex + 1);

        //g2.drawString("Page   - "+page+" -", positionX+240+10, 814);
        return PAGE_EXISTS;

    }

    private String code_classe(String nomtrait) {
        String nom = nomtrait.replace(" ", "");
        if (nom.trim().contains("1A4All")) {
            nom = "PB A4All";
        } else if (nom.trim().contains("1A4Esp")) {
            nom = "PB A4ESP";

        } else if (nom.trim().contains("PC")) {
            nom = "PB C";

        } else if (nom.trim().contains("PD1")) {
            nom = "PB D";
        } else if (nom.trim().contains("PD2")) {
            nom = "PB D";

        } else if (nom.trim().contains("1ereAcc")) {
            nom = "PB ACC";
        } else if (nom.trim().contains("1ereCG")) {
            nom = "PB CG";

        } else if (nom.trim().contains("TleA4All")) {
            nom = "BA A4All";
        } else if (nom.trim().contains("TleA4Esp")) {
            nom = "BA A4ESP";

        } else if (nom.trim().contains("TleC")) {
            nom = "BA C";

        } else if (nom.trim().contains("TleD")) {
            nom = "BA D";

        } else if (nom.trim().contains("TleAcc")) {
            nom = "BA ACC";
        } else if (nom.trim().contains("TleCG")) {
            nom = "BA CG";

        } else if (nom.trim().contains("Tle")) {
            nom = "Bacc";
        } else if (nom.trim().contains("P")) {
            nom = "PROB";
        } else {
            nom = memoire.classe;
        }
        return nom;
    }

    private String trimNom(String name) {

        if (name.length() > 26) {
            return name.substring(0, 26) + ".";
        } else {
            return name;
        }
    }

}
