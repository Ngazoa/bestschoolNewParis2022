/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pack.impression;

import com.pack.entity.InfoGroupMatiere;
import com.pack.entity.InfoMatiere;
import com.pack.gui.WindowBox;
import com.pack.report.Report;
import com.pack.report.ReportsCards;
import etablissement.memoire;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import security.fonctions;
import security.fonctionsBulletins;

/**
 *
 * @author Michelle Wafo
 */
public class moyenG {

    private final double moyendecision = 0;
    private final double moyenneG = 0;
    private final double moyp = 0;
    private final double moyd = 0;
    private double ver = 0;
    private double stock1 = 0;
    private double stock2 = 20;
    ArrayList<String> rang = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    ArrayList<String> moyenne = new ArrayList<>();
    ArrayList<String> no = new ArrayList<>();
    ArrayList<String> se = new ArrayList<>();
    ArrayList<String> moy = new ArrayList<>();
    private ReportsCards reports = new ReportsCards();
    private String period = "";
    private String nom_eleve = "";

    private double produits1 = 0, produits2 = 0, produits3 = 0;
    private int coefs1 = 0, coefs2 = 0, coefs3 = 0;
    private final WindowBox alert = new WindowBox(new JFrame()).setMsg("Chargement en cours.....").setPanel().addProgressBar().animate();

    public moyenG imprimer() {

        try {
            netoyeur();
            // memoire.moyenne = moyenne;
            triBulleDecroissant(rang);
            memoire.classement = rang;
            memoire.code = code;
            memoire.sex = se;
            memoire.disciplines = moy;

            memoire.note = no;
        } catch (Exception PrintException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'impression \n[ motif : " + PrintException.getMessage() + " ]");
            PrintException.printStackTrace();
        }
        return this;
    }

    public void netoyeur() {
        Graphics g = null;
        for (int ele = 0; ele < reports.getListReports().size(); ele++) {
            produits1 = 0;
            produits3 = 0;
            produits2 = 0;
            coefs1 = 0;
            coefs2 = 0;
            coefs3 = 0;

            Graphics2D g2 = (Graphics2D) g;

            Report report = reports.getListReports().get(ele);

            int positionY = 110, positionX = 20;

            /*Report Group1*/
            positionY += 15;
            positionY = drawCaseEntete(g2, positionX, positionY);

            if (getPeriod().toLowerCase().contains("annuel")) {
            } else if (getPeriod().toLowerCase().contains("trimestre")) {
            } else if (getPeriod().toLowerCase().contains("sequence")) {
            }

            positionY = drawCaseGroup(g2, positionX, positionY, report, 1, reports.getMoyenneGeneral());
            positionY = drawCaseGroup(g2, positionX, positionY, report, 2, reports.getMoyenneGeneral());
            positionY = drawCaseGroup(g2, positionX, positionY, report, 3, reports.getMoyenneGeneral());
            //positionY = drawCaseDiscAppreciation(g2,positionX,positionY,report);
            //positionY = drawCaseObservation(g2, positionX, positionY);
            System.out.println("positionY=" + positionY);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        alert.setProgress(100);
        alert.close();

    }

    /**
     * Cette m�thode permet d'imprim� les notes des sequences pour un bulletin
     * sequentiel
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     * @param matiere objet contenant les informations sur les notes,
     * coefficient de la matiere
     */
    /**
     * Cette m�thode permet d'imprim� les notes des sequences pour un bulletin
     * trimestriel
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     * @param matiere objet contenant les informations sur les notes,
     * coefficient de la matiere
     */
    /**
     * Cette m�thode permet d'imprim� les notes des sequences pour un bulletin
     * annuel
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     * @param matiere objet contenant les informations sur les notes,
     * coefficient de la matiere
     */
    /**
     * Cette m�thode permet d'imprim� les nombres de colonnes des sequences pour
     * un bulletin sequentiel
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     */
    /**
     * Cette m�thode permet d'imprim� le box des notes et mati�res appartenant �
     * un groupe grp
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     * @param report objet repr�sentant un bulletin de note
     * @param grp le groupe des mati�re
     * @param moyclasse La moyenne de la classe
     */
    public int drawCaseGroup(Graphics2D g2, int positionX, int positionY, Report report, int grp, String moyclasse) {
        if (!nom_eleve.equals(report.getProfile().getNom())) {

            int coef = 0;
            float prod = 0;
            InfoGroupMatiere groupeMatiere = new InfoGroupMatiere();
            switch (grp) {
                case 1:
                    groupeMatiere = report.getGroup1();
                    break;
                case 2:
                    groupeMatiere = report.getGroup2();
                    break;
                case 3:
                    groupeMatiere = report.getGroup3();
                    break;
                default:
                    break;
            }
            for (InfoMatiere matiere : groupeMatiere.getMatieres()) {

                if (getPeriod().toLowerCase().contains("annuel"))
	        	//drawCaseNoteAnGroup(g2, positionX+100, positionY,matiere)
                                ; else if (getPeriod().toLowerCase().contains("trimestre"))
	        	//drawCaseNoteTrimGroup(g2, positionX+100, positionY,matiere)
                                ; else if (getPeriod().toLowerCase().contains("sequence"))
	        	//drawCaseNoteSeqGroup(g2, positionX+100, positionY,matiere);
	        ;

                if (getPeriod().toLowerCase().contains("annuel")) {
                    int nbre = 0, nbre2 = 0;
                    float som = 0, som2 = 0;

                    if (matiere.getNoteSeq6() > 0) {
                        som2 += matiere.getNoteSeq6() * 0.7;
                        nbre2++;
                    }
                    if (matiere.getNoteSeq5() > 0) {
                        som2 += matiere.getNoteSeq5() * 0.7;
                        nbre2++;
                    }
                    if (matiere.getNoteSeq4() > 0) {
                        som2 += matiere.getNoteSeq4() * 0.7;
                        nbre2++;
                    }
                    if (matiere.getNoteSeq3() > 0) {
                        som += matiere.getNoteSeq3() * 0.3;
                        nbre++;
                    }
                    if (matiere.getNoteSeq2() > 0) {
                        som += matiere.getNoteSeq2() * 0.3;
                        nbre++;
                    }
                    if (matiere.getNoteSeq1() > 0) {
                        som += matiere.getNoteSeq1() * 0.3;
                        nbre++;
                    }
                    if (nbre != 0) {
                        if (nbre2 == 0) {
                            nbre2 = 1;
                        }
                        System.out.println("total sequence : " + nbre);
                        coef += matiere.getCoef();

                        prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                        double r = (som / nbre) + (som2 / nbre2);
                        se.add(report.getProfile().getSexe());
                        moy.add(matiere.getMatiere());
                        no.add("" + r);
                    } else {
                        se.add(report.getProfile().getSexe());
                        moy.add(matiere.getMatiere());
                        no.add("" + 0);
                    }

                } else if (getPeriod().toLowerCase().contains("trimestre")) {
                    if (getPeriod().contains("troisieme")) {
                        int nbre = 0, nbre2 = 0;
                        float som = 0, som2 = 0;

                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    } else if (getPeriod().contains("deuxieme")) {
                        int nbre = 0, nbre2 = 0;
                        float som = 0, som2 = 0;

                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    } else if (getPeriod().contains("premier")) {
                        int nbre = 0, nbre2 = 0;
                        float som = 0, som2 = 0;

                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    }
                } else if (getPeriod().toLowerCase().contains("sequence")) {
                    int nbre = 0, nbre2 = 0;
                    float som = 0, som2 = 0;
                    if (getPeriod().contains("troisieme")) {

                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }

                    } else if (getPeriod().contains("deuxieme")) {
                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    } else if (getPeriod().contains("premiere")) {
                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    } else if (getPeriod().contains("quatrieme")) {
                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    } else if (getPeriod().contains("cinquieme")) {
                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    } else if (getPeriod().contains("sixieme")) {
                        if (matiere.getNoteSeq6() > 0) {
                            som2 += matiere.getNoteSeq6() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq5() > 0) {
                            som2 += matiere.getNoteSeq5() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq4() > 0) {
                            som2 += matiere.getNoteSeq4() * 0.7;
                            nbre2++;
                        }
                        if (matiere.getNoteSeq3() > 0) {
                            som += matiere.getNoteSeq3() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq2() > 0) {
                            som += matiere.getNoteSeq2() * 0.3;
                            nbre++;
                        }
                        if (matiere.getNoteSeq1() > 0) {
                            som += matiere.getNoteSeq1() * 0.3;
                            nbre++;
                        }
                        if (nbre != 0) {
                            if (nbre2 == 0) {
                                nbre2 = 1;
                            }
                            System.out.println("total sequence : " + nbre);
                            coef += matiere.getCoef();

                            prod += ((som / nbre) + (som2 / nbre2)) * matiere.getCoef();
                            double r = (som / nbre) + (som2 / nbre2);
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + r);
                        } else {
                            se.add(report.getProfile().getSexe());
                            moy.add(matiere.getMatiere());
                            no.add("" + 0);
                        }
                    }

                }

            }
            switch (grp) {
                case 1:
                    produits1 += prod;
                    coefs1 += coef;
                    break;
                case 2:
                    produits2 += prod;
                    coefs1 += coef;
                    break;
                case 3:
                    produits3 += prod;
                    coefs1 += coef;
                    break;
                default:
                    break;
            }

            drawTotauxGroup(g2, positionX, positionY, coef, prod, (prod / coef), "" + grp);
            if (grp == 3) {
                positionY += 15;
                String app = report.getAppreciation();
                if (app.equals("/")) {
                    app = appreciation("" + (produits1 + produits2 + produits3) / (coefs1 + coefs2 + coefs3));
                }
                double MOY = 0;

                if ((coefs1 + coefs2 + coefs3) != 0) {
                    MOY = (produits1 + produits2 + produits3) / (coefs1 + coefs2 + coefs3);
                    System.out.println("Nom " + report.getProfile().getNom() + " Avec coeff nul produit1: " + produits1 + " produit2 :" + produits2 + " produit3 :" + produits3);

                    //cas eleve present
                } else {
                    //cas eleve absent
                    MOY = (produits1 + produits2 + produits3);
                    System.out.println(" produit1:" + produits1 + " produit2" + produits2 + " produit3" + produits3);
                }
                System.out.println(" MOYENNE " + MOY);

                BigDecimal valeur = (new BigDecimal(MOY)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                double moyen_eleve = valeur.doubleValue();

                if (moyen_eleve != ver) {
                    try {
                        nom_eleve = report.getProfile().getNom();
                        
                        String total = String.valueOf(produits1 + produits2 + produits3);
                        rang.add(String.valueOf(moyen_eleve));
                        moyenne.add(String.valueOf(moyen_eleve));
                        code.add(report.getProfile().id_eleve());
                        new fonctions().addEleveMoyenne(new fonctions().getIClasse(report.getProfile().getClasse()),
                                String.valueOf(new fonctionsBulletins().getPeriodeSaveStats(period)),
                                report.getProfile().getMatricule(),
                                "" + moyen_eleve);
                        
                        //System.out.println( "\n\n\nLaveur totaux cote Laveur :  "+ver);
                        //memoire.MOYGENc =+moyen_eleve;
                        memoire.MOYGEN += moyen_eleve;
                        System.out.println(" MOYENNE " + report.getProfile().getNom() + " : " + moyen_eleve);
                        ver = valeur.doubleValue();
                        String sexe = report.getProfile().getSexe();
                        switch (sexe) {
                            case "M":
                                memoire.nombre_garcon++;
                                break;

                            default:
                                memoire.nombre_fille++;
                                
                                break;
                        } //rcherche moyenne
                        if (moyen_eleve > stock1) {
                            // memoire.MOYP = moyen_eleve;
                            stock1 = moyen_eleve;
                            
                        }
                        //rechercher moyenne premier
                        if (moyen_eleve < stock2 && moyen_eleve != 0) {
                            // memoire.MOYD = moyen_eleve;
                            stock2 = moyen_eleve;
                        }
                        if (moyen_eleve > 10) {
                            switch (sexe) {
                                case "M":
                                    memoire.nombre_garcons_admis++;
                                    System.out.println(" Dmission garcons : " + moyen_eleve);
                                    
                                    break;
                                case "F":
                                    memoire.nombre_fille_admise++;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (moyen_eleve == 0.0) {
                            
                            switch (sexe) {
                                case "M":
                                    memoire.nombre_garcon_absent++;
                                    System.out.println(" Absenteiste  : " + moyen_eleve);
                                    
                                    break;
                                    
                                default:
                                    memoire.nombre_fille_absente++;
                                    
                                    break;
                            }
                        }
                        if (moyen_eleve > 0 && moyen_eleve < 4) {
                            memoire.mention1++;
                            
                        } else if (moyen_eleve > 4 && moyen_eleve < 7.5) {
                            memoire.mention2++;
                            
                        } else if (moyen_eleve > 7.5 && moyen_eleve < 10) {
                            memoire.mention3++;
                        } else if (moyen_eleve > 10 && moyen_eleve < 12) {
                            memoire.mention4++;
                            
                        } else if (moyen_eleve > 12 && moyen_eleve < 14) {
                            memoire.mention5++;
                            
                        } else if (moyen_eleve > 14 && moyen_eleve < 16) {
                            memoire.mention6++;
                            
                        } else if (moyen_eleve > 16 && moyen_eleve < 18) {
                            memoire.mention7++;
                            
                        } else if (moyen_eleve > 18 && moyen_eleve < 20) {
                            memoire.mention8++;
                            
                        } else if (moyen_eleve == 20) {
                            memoire.mention9++;
                            
                        }
                        if (moyen_eleve > 12) {
                            memoire.mention10++;
                            
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(moyenG.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            return positionY + 40;
        } else {
            return positionY + 15;
        }
    }

    /**
     * Cette m�thode permet d'imprim� le box des totaux et moyennes
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     * @param coef total des coeficients
     * @param produit total des produits note x coefficient
     * @param moyenne la moyenne
     * @param group valeur du groupe des matiere variant entre 1 et 3
     */
    public void drawTotauxGroup(Graphics2D g2, int positionX, int positionY, int coef, float produit, float moyenne, String group) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 100, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 100, 15);
        g2.drawString("TOTAL GROUPE " + group, positionX + 5, positionY + 10);

        g2.setColor(Color.GRAY);
        g2.fillRect(positionX + 100 + 200, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200, positionY, 40, 15);
        g2.drawString("" + coef, positionX + 100 + 200 + 20, positionY + 10);

        g2.setColor(Color.GRAY);
        g2.fillRect(positionX + 100 + 200 + 40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200 + 40, positionY, 40, 15);
        g2.drawString(trimValue("" + moyenne), positionX + 100 + 200 + 40 + 10, positionY + 10);

        g2.setColor(Color.GRAY);
        g2.fillRect(positionX + 100 + 200 + 40 + 40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200 + 40 + 40, positionY, 40, 15);
        g2.drawString(trimValue("" + produit), positionX + 100 + 200 + 40 + 40 + 10, positionY + 10);

    }

    /**
     * Cette m�thode permet d'imprim� le box des totaux et moyennes
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     * @param coef total des coeficients
     * @param prod total des produits note x coefficient
     * @param moyenne la moyenne
     * @param rang le rang
     * @param appreciation l'appr�ciation
     * @param moyClasse La moyenne de la classe
     */
    public int drawCaseTotaux(Graphics2D g2, int positionX, int positionY, int coef, double prod, double moyenne, String rang, String appreciation, String moyClasse) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100, positionY, 200, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100, positionY, 200, 15);
        g2.drawString("TOTAUX | MOYENNE  " + getStatusPeriod() + " /20", positionX + 100 + 30, positionY + 10);

        g2.setColor(Color.black);
        g2.fillRect(positionX + 100 + 200, positionY, 40, 15);
        g2.setColor(Color.WHITE);
        g2.drawRect(positionX + 100 + 200, positionY, 40, 15);
        g2.drawString("" + coef, positionX + 100 + 200 + 20, positionY + 10);
        g2.setColor(Color.black);
        g2.fillRect(positionX + 100 + 200 + 40, positionY, 40, 15);
        g2.setColor(Color.WHITE);
        g2.drawRect(positionX + 100 + 200 + 40, positionY, 40, 15);

        g2.drawString(trimValue("" + moyenne), positionX + 100 + 200 + 40 + 10, positionY + 10);
        g2.setColor(Color.black);
        g2.fillRect(positionX + 100 + 200 + 40 + 40, positionY, 40, 15);
        g2.setColor(Color.white);
        g2.drawRect(positionX + 100 + 200 + 40 + 40, positionY, 40, 15);
        g2.drawString(trimValue("" + prod), positionX + 100 + 200 + 40 + 40 + 10, positionY + 10);
        g2.setColor(Color.black);
        g2.fillRect(positionX + 100 + 200 + 40 + 40 + 40, positionY, 40, 15);
        g2.setColor(Color.white);
        g2.drawRect(positionX + 100 + 200 + 40 + 40 + 40, positionY, 40, 15);
        g2.drawString("" + rang, positionX + 100 + 200 + 40 + 40 + 40 + 15, positionY + 10);

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(positionX + 100 + 200 + 40 + 40 + 40 + 40, positionY, 95, 40);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200 + 40 + 40 + 40 + 40, positionY, 95, 40);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
        String[] pattern = appreciation.split("<BR>");
        int i = 1;
        for (String motif : pattern) {
            g2.drawString(motif, positionX + 100 + 300 + 55 + 10, positionY + (10 * i));
            i++;
        }
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 6));

        positionY += 15;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 100, 20);
        g2.drawString("MOYENNE DE LA CLASSE/20", positionX + 5, positionY + 10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100, positionY, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100, positionY, 100, 20);
        g2.drawString(trimValue(moyClasse), positionX + 100 + 42, positionY + 10);

        return positionY + 20;
    }

    /**
     * Cette m�thode permet d'imprim� le box des appreciations et disciplines
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     * @param report objet repr�sentant un bulletin de notes
     */
    public int drawCaseDiscAppreciation(Graphics2D g2, int positionX, int positionY, Report report) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("DISCIPLINE", positionX + 60, positionY + 10);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 276, positionY, 279, 15);
        g2.drawString("APPRECIATION \nTRAVAIL".toUpperCase(), positionX + 276 + 90, positionY + 10);

        positionY += 15;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 69, 15);
        g2.drawString("ABSENCE(heure)", positionX + 5, positionY + 10);
        g2.drawRect(positionX, positionY + 15, 69, 15);
        g2.drawString("" + (calculHours(report)), positionX + 33, positionY + 15 + 10);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 69, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 69, positionY, 69, 15);
        g2.drawString("AVERTISSEMENT", positionX + 69 + 5, positionY + 10);
        g2.drawRect(positionX + 69, positionY + 15, 69, 15);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 69 + 69, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 69 + 69, positionY, 69, 15);
        g2.drawString("BLAME".toUpperCase(), positionX + 69 + 69 + 15, positionY + 10);
        g2.drawRect(positionX + 69 + 69, positionY + 15, 69, 15);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 69 + 69 + 69, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 69 + 69 + 69, positionY, 69, 15);
        g2.drawString("EXCLUSION", positionX + 69 + 69 + 69 + 5, positionY + 10);
        g2.drawRect(positionX + 69 + 69 + 69, positionY + 15, 69, 15);

        g2.drawRect(positionX + 276, positionY, 34, 15);
        g2.drawString("TB", positionX + 276 + 15, positionY + 10);
        g2.drawRect(positionX + 276 + 34, positionY, 34, 15);
        g2.drawString("B", positionX + 276 + 34 + 15, positionY + 10);
        g2.drawRect(positionX + 276 + 34 + 34, positionY, 34, 15);
        g2.drawString("AB", positionX + 276 + 34 + 34 + 15, positionY + 10);
        g2.drawRect(positionX + 276 + 34 + 34 + 34, positionY, 35, 15);
        g2.drawString("PASS", positionX + 276 + 34 + 34 + 34 + 10, positionY + 10);
        g2.drawRect(positionX + 276 + 34 + 35 + 34 + 34, positionY, 34, 15);
        g2.drawString("MED", positionX + 276 + 35 + 34 + 34 + 34 + 10, positionY + 10);
        g2.drawRect(positionX + 276 + 34 + 35 + 34 + 34 + 34, positionY, 36, 15);
        g2.drawString("INSU", positionX + 276 + 35 + 34 + 34 + 34 + 34 + 10, positionY + 10);
        g2.drawRect(positionX + 276 + 34 + 35 + 34 + 34 + 34 + 36, positionY, 36, 15);
        g2.drawString("FAIB", positionX + 276 + 35 + 34 + 36 + 34 + 34 + 34 + 10, positionY + 10);
        g2.drawRect(positionX + 276 + 34 + 35 + 34 + 34 + 34 + 36 + 36, positionY, 36, 15);
        g2.drawString("NUL", positionX + 276 + 35 + 34 + 34 + 34 + 34 + 36 + 36 + 10, positionY + 10);

        g2.drawRect(positionX + 276, positionY + 15, 55, 15);
        g2.drawString("Encouragement", positionX + 276 + 5, positionY + 15 + 10);

        g2.drawRect(positionX + 276 + 55, positionY + 15, 55, 15);
        g2.drawString("Félicitations", positionX + 276 + 55 + 5, positionY + 15 + 10);

        g2.drawRect(positionX + 276 + 55 + 55, positionY + 15, 65, 15);
        g2.drawString("Tableau d'honneur", positionX + 276 + 55 + 55 + 1, positionY + 15 + 10);

        g2.drawRect(positionX + 276 + 55 + 55 + 65, positionY + 15, 55, 15);
        g2.drawString("Avertissements", positionX + 276 + 55 + 55 + 65 + 1, positionY + 15 + 10);

        g2.drawRect(positionX + 276 + 55 + 55 + 65 + 55, positionY + 15, 49, 15);
        g2.drawString("Blames", positionX + 276 + 55 + 55 + 65 + 55 + 5, positionY + 15 + 10);

        positionY += 30;
        g2.drawRect(positionX, positionY, 276, 35);
        g2.drawString("MOTIF : ", positionX + 5, positionY + 10);

        if (getPeriod().contains("annuel")) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(positionX + 276, positionY, 279, 15);
            g2.setColor(Color.black);
            g2.drawRect(positionX + 276, positionY, 279, 15);
            g2.drawString("DECISION", positionX + 276 + 95, positionY + 10);

            g2.drawRect(positionX + 276, positionY + 15, 279, 20);
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
            if (moyendecision >= 10) {
                g2.drawString("ADMIS CLASSE SUPERIEURE ", positionX + 276 + 10, positionY + 15 + 10);
            } else if (moyendecision <= 10 && moyendecision >= 9) {
                g2.drawString("REDOUBLE ou peut etre repeché", positionX + 276 + 10, positionY + 15 + 10);
            } else {
                g2.drawString("REDOUBLE ", positionX + 276 + 10, positionY + 15 + 10);
            }
            g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 6));
            return positionY + 35;
        }

        return positionY + 35;
    }

    /**
     * Cette m�thode permet de calculer le cumul des heures pendant une periode
     *
     * @param report objet repr�sentant un bulletin
     */
    public String calculHours(Report report) {

        if (getPeriod().toLowerCase().contains("annuel")) {
            return "" + (report.getDiscipline());
        } else if (getPeriod().toLowerCase().contains("trimestre")) {
            if (getPeriod().contains("troisieme")) {
                return "" + (report.getDiscipline());
            } else if (getPeriod().contains("deuxieme")) {
                return "" + (report.getDiscipline());
            } else if (getPeriod().contains("premier")) {
                return "" + (report.getDiscipline());
            }
        } else if (getPeriod().toLowerCase().contains("sequence")) {
            if (getPeriod().contains("troisieme")) {
                return "" + (report.getDiscipline());
            } else if (getPeriod().contains("deuxieme")) {
                return "" + (report.getDiscipline());
            } else if (getPeriod().contains("premiere")) {
                return "" + (report.getDiscipline());
            } else if (getPeriod().contains("quatrieme")) {
                return "" + (report.getDiscipline());
            } else if (getPeriod().contains("cinquieme")) {
                return "" + (report.getDiscipline());
            } else if (getPeriod().contains("sixieme")) {
                return "" + (report.getDiscipline());
            }
        }
        return "/";
    }

    /**
     * Cette m�thode permet d'imprim� le box des observations
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     */
    public int drawCaseObservation(Graphics2D g2, int positionX, int positionY) {
        if (period.trim().contains("sequence")) {
            return drawCaseObservationSequence(g2, positionX, positionY);
        } else if (period.trim().contains("trimestre")) {
            return drawCaseObservationTrimestre(g2, positionX, positionY);
        } else if (period.trim().contains("annuel")) {
            return drawCaseObservationAnnuel(g2, positionX, positionY);
        }
        return positionY;
    }

    /**
     * Cette m�thode permet d'imprim� le box des observations trimestrielles
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     */
    public int drawCaseObservationTrimestre(Graphics2D g2, int positionX, int positionY) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU PARENT", positionX + 10, positionY + 10);
        g2.drawRect(positionX, positionY, 276, 842 - (positionY + 15));

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 276, positionY, 279, 15);
        g2.drawString("OBSERVATION ET VISA DU CHEF D'ETABLISSEMENT", positionX + 276 + 10, positionY + 10);
        g2.drawRect(positionX + 276, positionY, 279, 842 - (positionY + 15));

        return positionY + 842 - (positionY + 15);
    }

    /**
     * Cette m�thode permet d'imprim� le box des observations sequentielles
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     */
    public int drawCaseObservationSequence(Graphics2D g2, int positionX, int positionY) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU SURVEILLANT GENERAL", positionX + 10, positionY + 10);
        g2.drawRect(positionX, positionY, 276, (842 - (positionY + 15)) / 2);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY + (842 - (positionY + 15)) / 2, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY + (842 - (positionY + 15)) / 2, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU PARENT", positionX + 10, positionY + (842 - (positionY + 15)) / 2 + 10);
        g2.drawRect(positionX, positionY + (842 - (positionY + 15)) / 2, 276, (842 - (positionY + 15)) / 2);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 276, positionY, 279, 15);
        g2.drawString("OBSERVATION ET VISA DU PROFESSEUR PRINCIPAL", positionX + 276 + 10, positionY + 10);
        g2.drawRect(positionX + 276, positionY, 279, 842 - (positionY + 15));

        return positionY + 842 - (positionY + 15);
    }

    /**
     * Cette m�thode permet d'imprim� le box des observations annuelles
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     */
    public int drawCaseObservationAnnuel(Graphics2D g2, int positionX, int positionY) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU PARENT", positionX + 10, positionY + 10);
        g2.drawRect(positionX, positionY, 276, 842 - (positionY + 15));

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 276, positionY, 279, 15);
        g2.drawString("OBSERVATION ET VISA DU CHEF D'ETABLISSEMENT", positionX + 276 + 10, positionY + 10);
        g2.drawRect(positionX + 276, positionY, 279, 842 - (positionY + 15));

        return positionY + 842 - (positionY + 15);
    }

    /**
     * Cette m�thode permet d'imprim� l'ent�te du tableau des notes et mati�res
     *
     * @param g2 objet permettant de dessiner
     * @param positionX coordonn�e de l'abcisse X
     * @param positionY coordonn�e de l'ordonn�e Y
     */
    public int drawCaseEntete(Graphics2D g2, int positionX, int positionY) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 100, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 100, 15);
        g2.drawString("MATIERE", positionX + 40, positionY + 10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100, positionY, 300, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100, positionY, 200, 15);
        g2.drawString("SEQUENCE", positionX + 100 + 80, positionY + 10);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100 + 200, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200, positionY, 40, 15);
        g2.drawString("COEF", positionX + 100 + 200 + 15, positionY + 10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100 + 200 + 40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200 + 40, positionY, 40, 15);
        g2.drawString("MOY", positionX + 100 + 200 + 40 + 15, positionY + 10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100 + 200 + 40 + 40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200 + 40 + 40, positionY, 40, 15);
        g2.drawString("M x C", positionX + 100 + 200 + 40 + 40 + 15, positionY + 10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100 + 200 + 40 + 40 + 40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200 + 40 + 40 + 40, positionY, 40, 15);
        g2.drawString("RANG", positionX + 100 + 200 + 40 + 40 + 40 + 15, positionY + 10);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX + 100 + 200 + 40 + 40 + 40 + 40, positionY, 90, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX + 100 + 200 + 40 + 40 + 40 + 40, positionY, 90, 15);
        g2.drawString("OBSERVATION", positionX + 100 + 300 + 55 + 30, positionY + 10);
        return positionY + 15;
    }

    public moyenG setReports(ReportsCards reports) {
        this.reports = reports;
        return this;
    }

    public String getPeriod() {
        return period;
    }

    public String getStatusPeriod() {
        if (period.trim().toLowerCase().contains("sequence")) {
            return "SEQUENTIELLE";
        } else if (period.trim().toLowerCase().contains("trimestre")) {
            return "TRIMESTRIELLE";
        } else if (period.trim().toLowerCase().contains("annuel")) {
            return "ANNUELLE";
        }
        return "";
    }

    /**
     * Cette m�thode permet de configur� le nom � donner au bulletin
     *
     * @param period la valeur de la period pouvant �tre
     * 'sequence1','sequence3', 'trimestre3', 'trimestre1' ou 'annuel'
     */
    public moyenG setPeriod(String period) throws Exception {
        period = period.trim().toLowerCase();
        if (period.trim().toLowerCase().contains("sequence1")) {
            this.period = "Bulletin de la premiere sequence";
        } else if (period.trim().toLowerCase().contains("sequence2")) {
            this.period = "Bulletin de la deuxieme sequence";
        } else if (period.trim().toLowerCase().contains("sequence3")) {
            this.period = "Bulletin de la troisieme sequence";
        } else if (period.trim().toLowerCase().contains("sequence4")) {
            this.period = "Bulletin de la quatrieme sequence";
        } else if (period.trim().toLowerCase().contains("sequence5")) {
            this.period = "Bulletin de la cinquieme sequence";
        } else if (period.trim().toLowerCase().contains("sequence6")) {
            this.period = "Bulletin de la sixieme sequence";
        } else if (period.trim().toLowerCase().contains("trimestre1")) {
            this.period = "Bulletin du premier trimestre";
        } else if (period.trim().toLowerCase().contains("trimestre2")) {
            this.period = "Bulletin du deuxieme trimestre";
        } else if (period.trim().toLowerCase().contains("trimestre3")) {
            this.period = "Bulletin du troisieme trimestre";
        } else if (period.trim().toLowerCase().contains("annuel")) {
            this.period = "Bulletin annuel";
        } else {
            throw new Exception("Format incorrect !!!! format acceptable : sequence<num> ou trimestre<num> ou annuel");
        }
        return this;
    }

    /**
     * Cette m�thode permet compter le nombre d'espace entre les mots d'un
     * chaine
     *
     * @param word la chaine � traiter
     * @return le nombre d'espace
     */
    private int espaceWord(String word) {
        int i = 0, num = 0;
        while (i < word.length()) {
            if (word.charAt(i) == ' ') {
                num++;
            }
            i++;
        }
        return num;
    }

    private String trimValue(String strValeur) {
        try {
            BigDecimal valeur = (new BigDecimal(strValeur)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            if (valeur.doubleValue() < 10) {
                return "0" + valeur;
            }
            return valeur.toString();
        } catch (NumberFormatException ex) {
            return "/";
        }
    }

    private String trimValuerep(String strValeur) {
        try {
            BigDecimal valeur = (new BigDecimal(strValeur)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            if (valeur.doubleValue() < 10) {
                return "0" + valeur;
            }
            return valeur.toString();
        } catch (NumberFormatException ex) {
            return "/";
        }
    }

    /**
     * Cette m�thode permet d'arrondir � deux chiffres apr�s la virgule
     *
     * @param strValeur la valeur r�elle a traiter
     * @return la valeur avec deux chiffres apr�s la virgule
     */
    /**
     * Cette m�thode permet d'arrondir les noms des enseignants des mati�res
     *
     * @param name la valeur r�elle a traiter
     * @return le nom arrondi
     */
    private String trimNom(String name) {
        if ((name == null) || name.trim().isEmpty()) {
            return "M. ";
        }
        name = name.trim();
        if (name.toLowerCase().trim().indexOf("m.") != 0 && name.toLowerCase().trim().indexOf("m ") != 0
                && name.toLowerCase().trim().indexOf("mr ") != 0 && name.toLowerCase().trim().indexOf("mr.") != 0
                && name.toLowerCase().trim().indexOf("abbe ") != 0 && name.toLowerCase().trim().indexOf("abbe.") != 0
                && name.toLowerCase().trim().indexOf("ab.") != 0 && name.toLowerCase().trim().indexOf("ab ") != 0
                && name.toLowerCase().trim().indexOf("pr ") != 0 && name.toLowerCase().trim().indexOf("pr.") != 0
                && name.toLowerCase().trim().indexOf("mm ") != 0 && name.toLowerCase().trim().indexOf("mm.") != 0
                && name.toLowerCase().trim().indexOf("sr.") != 0 && name.toLowerCase().trim().indexOf("sr ") != 0
                && name.toLowerCase().trim().indexOf("mme.") != 0 && name.toLowerCase().trim().indexOf("mme ") != 0
                && name.toLowerCase().trim().indexOf("mgr.") != 0 && name.toLowerCase().trim().indexOf("mgr ") != 0
                && name.toLowerCase().trim().indexOf("pere.") != 0 && name.toLowerCase().trim().indexOf("pere ") != 0) {
            name = "M. " + name;
        }
        if (name.length() > 22) {
            return name.substring(0, 21) + ".";
        } else {
            return name;
        }
    }

    /**
     * Cette m�thode permet d'arrondir les noms des mati�res
     *
     * @param name la valeur r�elle a traiter
     * @return le nom arrondi
     */
    private String trimDiscipline(String arg) {
        if (arg.trim().isEmpty()) {
            return "/";
        }
        if (arg.length() <= 25) {
            return arg;
        }
        String value = "";

        String[] data = arg.split(" ");
        if (data.length < 2) {
            data = arg.split("/");
        }
        if (1 < data.length) {
            for (int i = 0; i != data.length; i++) {
                if (4 < data[i].length()) {
                    value += data[i].substring(0, 3) + ". ";
                }
            }
        }
        return value;
    }

    private String observation(String Val) {
        String app = "";
        try {
            if (!Val.trim().equals("")) {
                if ((0 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 4)) {
                    app = "nul";
                }
                if ((4 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 8)) {
                    app = "Faible";
                }
                if ((8 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 10)) {
                    app = "Mediocre";
                }
                if ((10 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 12)) {
                    app = "Passable";
                }
                if ((12 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 14)) {
                    app = "Assez Bien";
                }
                if ((14 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 17)) {
                    app = "Bien";
                }
                if ((17 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 20)) {
                    app = "Tres Bien";
                }
                if (20 == Float.parseFloat(Val)) {
                    app = "Parfait";
                }
            }
        } catch (NumberFormatException ex) {
        }
        return app;
    }

    public String appreciation(String Val) {
        String app = "";
        try {
            if (!Val.trim().equals("")) {
                if ((0 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 4)) {
                    app = "nul<br>Blame travail";
                }
                if ((4 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 7.5)) {
                    app = "Faible<br>Blame travail";

                }
                if ((7.5 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 10)) {
                    app = "Mediocre<br>Avertissement<br>travail";

                }
                if ((10 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 12)) {
                    app = "Passable<br>Encouragement";

                }
                if ((12 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 14)) {
                    app = "Assez-Bien<br>Encouragement";

                }
                if ((14 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 17)) {
                    app = "Bien<br>Felicitation";

                }
                if ((17 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 20)) {
                    app = "Tres Bien<br>Excellent";

                }
                if (20 == Float.parseFloat(Val)) {
                    app = "Parfait<br>Excellent";

                }

                if (12 <= Float.parseFloat(Val) && !getPeriod().trim().toLowerCase().contains("sequence")) {
                    app += "<br>Tableau d'honneur";

                }
                app = app.toUpperCase();
            }
        } catch (NumberFormatException ex) {
        }
        return app;
    }

    public static void triBulleDecroissant(ArrayList<String> classement) {
        int longueur = classement.size();
        double tampon = 0;
        boolean permut;

        do {
            // hypothèse : le tableau est trié
            permut = false;
            for (int i = 0; i < longueur - 1; i++) {
                // Teste si 2 éléments successifs sont dans le bon ordre ou non
                if (Double.parseDouble(classement.get(i)) < Double.parseDouble(classement.get(i + 1))) {
                    // s'ils ne le sont pas, on échange leurs positions
                    tampon = Double.parseDouble(classement.get(i));
                    classement.set(i, classement.get(i + 1));
                    classement.set(i + 1, String.valueOf(tampon));

                    //tableau[i + 1] = tampon;
                    permut = true;
                }
            }
        } while (permut);
    }

}
