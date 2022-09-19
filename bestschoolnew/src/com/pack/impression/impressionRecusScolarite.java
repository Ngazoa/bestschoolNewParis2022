/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pack.impression;

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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.modelEleve;
import security.fonctions;

/**
 *
 * @author ASSUS GAMER
 */
public class impressionRecusScolarite extends JPanel implements Printable {

    private static String tranche, nomeleve, cycle, prix, classe, anneeScolaire, bp, etabissement, contact;
    Preferences prf = Preferences.userNodeForPackage(fonctions.class);
    List<modelEleve> trnachesEleve = new ArrayList<>();
    modelEleve moratoirelist = new modelEleve();

    public impressionRecusScolarite(String nom, String tranche, String prix,
            String classe, String cycle) {
        nomeleve = nom;
        impressionRecusScolarite.prix = prix;
        impressionRecusScolarite.tranche = tranche;
        impressionRecusScolarite.classe = classe;
        impressionRecusScolarite.cycle = cycle;
        PrinterJob pj = PrinterJob.getPrinterJob();
        //pj.setPrintable(Intendancee.impression_recus.this);
        // pj.printDialog();
        try {
            pj.setJobName("Impression recu");
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

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        try {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }
            Entete report = new Entete();
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getHierarchieLevel1().toUpperCase(), 20, 40);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString(report.getHierarchieLevel1a(), 25, 50);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getCountry().toUpperCase().toUpperCase(), 440, 40);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString("Republic of Cameroon", 442, 50);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getHierarchieLevel2().toUpperCase(), 20, 65);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString(report.getHierarchieLevel2a(), 25, 75);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getDevice().toUpperCase(), 460, 70);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString("Peace-Work-Fatherland", 463, 80);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString(report.getHierarchieLevel3().toUpperCase(), 20, 90);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            g2.drawString(report.getHierarchieLevel3a(), 25, 100);
            g2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
            g2.drawString(report.getSchool().toUpperCase(), 200, 123);
            g2.drawImage((new ImageIcon(report.getLinkLogo())).getImage(), 250, 35, 70, 60, null);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString("Annee Scolaire/", 20, 140);
            g2.setFont(new Font(Font.SERIF, Font.ITALIC, 10));
            g2.drawString("   School year : " + report.getAnneeScolaire(), 100, 140);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            g2.drawString("BP " + report.getBp() + "   Tel/phone : " + report.getNumero(), 20, 150);
            g2.drawString("Date : " + new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()), 20, 160);
            // g2.drawString("Liste élève "+, 115, 170);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(19, 185 - 20, 552, 25);
            g2.setColor(Color.black);
            //g2.drawLine(1, 80, 600, 80);
            g2.setColor(Color.blue);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
            int positionY = 190, positionX = 20;
            /*profile*/
            g2.setColor(Color.LIGHT_GRAY);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
            //g2.drawRect(positionX, positionY, 550, 45);
            g2.setColor(Color.black);
            g2.drawString("Nom(s) et prenom(s)/name & surname : " + prf.get("nom_eleve", null).toUpperCase(), positionX + 100, positionY - 10);
            positionY += 15;
            g2.setColor(Color.blue);
            g2.drawString("Classe/class  : " + classe, positionX + 10, positionY);
            // g2.drawRect(positionX, positionY, 650, 45);
            g2.setColor(Color.black);
            positionY += 20;
            g2.drawString("          RECU SCOLARITE (" + cycle + ")", positionX, positionY + 20);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRect(positionX, positionY + 5, 550, 90);
            g2.setColor(Color.black);
            System.out.print(" total a payer " + prix + " percu : " + prix);
            positionY = positionY + 40;
            //g2.drawString("Montant total payé : " + prix + " FCFA", positionX, positionY);
            trnachesEleve = new fonctions().
                    getEleveTranchesPaiementPourImpression(memoire.classe, memoire.id_eleve);
            String etat = null;
            if (trnachesEleve != null) {
                float reste = 0, montantotal = 0, deja_paye = 0;
                for (modelEleve eleve : trnachesEleve) {
                    moratoirelist = new fonctions().
                            getEleveMoratoireTranchesPaiementIMpression(eleve.getidClasse(), memoire.id_eleve);
                    etat = (eleve.getSelect()) ? " payé " : " Impayé";
                    if (eleve.getSelect()) {
                        deja_paye += Float.parseFloat(eleve.getredoublant());
                    }

                    if (moratoirelist != null) {
                        System.out.println("---------------------"+moratoirelist.getNommatiere());
                        g2.drawString(" - Objet paiement :" + eleve.getNommatiere().toUpperCase() + "...Prix:" + eleve.getredoublant() + "...Avance :"
                                +moratoirelist.getNommatiere() + "...Reste: " + (Float.parseFloat(eleve.getredoublant()) - Float.parseFloat(moratoirelist.getNommatiere())) + "...STATUT:" + "En cours ", positionX, positionY);
                                                  deja_paye += Float.parseFloat(moratoirelist.getNommatiere());
                    } else {
                        g2.drawString(" - Objet paiement : " + eleve.getNommatiere().toUpperCase() + "...Prix : " + eleve.getredoublant() + "..."
                                + "...STATUT :" + etat, positionX, positionY);
                    }
                    montantotal += Float.parseFloat(eleve.getredoublant());
                    positionY = positionY + 20;

                }
                positionY += 15;

                g2.drawString("Montant dejà payé :  " + deja_paye + " FCFA ", positionX, positionY);
                positionY += 15;

                g2.drawString("Reste à payer : " + (montantotal - deja_paye) + " FCFA ", positionX, positionY);
                positionY += 15;

                g2.drawString("Montant total Scolarité : " + montantotal + " FCFA ", positionX, positionY);

            }
//int reste = memoire_intendance.reste - Integer.parseInt(memoire_intendance.prix);
            //g2.drawString("Reste à payer : " + reste + " FCFA", positionX + 280, positionY + 40);
            positionY += 65;
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("Fait par M. : " + prf.get("nom_user", null), positionX + 280, positionY);
            positionY += 15;
            g2.drawString("Signature :", positionX + 280, positionY);
            positionY += 15;
            g2.setColor(Color.blue);

        } catch (SQLException ex) {
            Logger.getLogger(impressionRecusScolarite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PAGE_EXISTS;
    }

}
