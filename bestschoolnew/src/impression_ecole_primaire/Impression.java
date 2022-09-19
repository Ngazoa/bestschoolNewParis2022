package impression_ecole_primaire;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.pack.entity.InfoGroupMatiere;
import com.pack.entity.InfoMatiere;
import com.pack.gui.WindowBox;
import com.pack.report.Report;
import com.pack.report.ReportsCards;
import etablissement.memoire;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Impression extends JFrame implements Printable {

    private double moyendecision = 0;
    private final double moyenneG = 0;
        private final double moyp = 0;
        private final double moyd = 0;
         ArrayList<String> rang=memoire.classement;
         ArrayList<String> rangs= new ArrayList<>();
         ArrayList<String> r=new ArrayList<>();

	private ReportsCards reports = new ReportsCards();
	private String period = "";
	private double produits1 = 0,produits2 = 0,produits3 = 0;
	private int coefs1 = 0,coefs2 = 0,coefs3 = 0;int position=0, position2=0;
	private final WindowBox alert;

    public Impression() {
        this.alert = new WindowBox(new JFrame("b")).setMsg("Chargement en cours.....").setPanel().addProgressBar().animate();
    }
public Impression imprimer(){
        PrinterJob pj = PrinterJob.getPrinterJob();	
        pj.setPrintable(Impression.this);	
        pj.printDialog();	
        try {
            pj.setJobName("Impression");
            PageFormat pf = pj.defaultPage();
            Paper p = pf.getPaper();
            p.setImageableArea(9, 0, 1024,1024);
            pf.setPaper(p);
            pf.setOrientation(PageFormat.PORTRAIT);
            System.out.println("pf.largeur hauteur()="+pf.getHeight()+"  pf.largeur obtenu="+pf.getWidth());
            pj.setPrintable(this,pf);
            alert.setProgress(0).display();
            pj.print();
                       memoire.rangs=r;
 
            
        } catch (Exception PrintException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'impression \n[ motif : "+PrintException.getMessage()+" ]");
            PrintException.printStackTrace();
        }
        return this;
    }
	
	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if(pageIndex<reports.getListReports().size()){
			produits1 = 0;produits3 = 0;produits2 = 0;
			coefs1 = 0;coefs2 = 0;coefs3 = 0;
			alert.setProgress(100*pageIndex/reports.getListReports().size());
			
			Graphics2D g2 = (Graphics2D)g;
			
			Report report = reports.getListReports().get(pageIndex);
				
	        g2.setColor(Color.black);
	        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));  
                    	        
	        g2.drawImage((new ImageIcon(report.getEntete().getLinkFiligrane())).getImage(), 150, 300,300,300, null);
	   
                g2.drawString(report.getEntete().getHierarchieLevel1().toUpperCase(), 20, 40);
                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
                g2.drawString(report.getEntete().getHierarchieLevel1a(), 25, 50); 
                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
                g2.drawString(report.getEntete().getCountry().toUpperCase(), 440, 40);
                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
                g2.drawString("Republic of Cameroon", 442, 50);
                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
                g2.drawString(report.getEntete().getHierarchieLevel2().toUpperCase(), 20, 65);
                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
                g2.drawString(report.getEntete().getHierarchieLevel2a(), 25, 75);
                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10)); 
                g2.drawString(report.getEntete().getDevice().toUpperCase(), 460, 70);	     
                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
                g2.drawString("Peace-Work-Fatherland", 463, 80);
                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
                g2.drawString(report.getEntete().getHierarchieLevel3().toUpperCase(), 20, 90);	      
                g2.setFont(new Font(Font.SERIF,Font.ITALIC,12));
                g2.drawString(report.getEntete().getHierarchieLevel3a(), 25, 100);
                g2.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,12));
                 int positionwx = 280 - ((report.getEntete().getSchool().length()/2)-espaceWord(report.getEntete().getSchool()))*8;
                g2.drawString(report.getEntete().getSchool().toUpperCase(), positionwx, 123);
	        g2.drawImage((new ImageIcon(report.getEntete().getLinkLogo())).getImage(), 250, 35,70, 60, null);
	        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
                g2.drawString("Annee Scolaire/", 20, 140); 
                g2.setFont(new Font(Font.SERIF,Font.ITALIC,10));
                g2.drawString("     School year : "+report.getEntete().getAnneeScolaire(), 100, 140);    
                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,10));
	        g2.drawString("BP "+report.getEntete().getBp()+"   Tel/phone : "+report.getEntete().getNumero(), 20, 150);
	        g2.drawString("Date : "+new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()), 20, 160);
	        g2.drawLine(100, 165, 500, 165);
	       // g2.drawLine(200, 115, 400, 115);

	        /*TITRE*/     
            g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,16));
	        int posX = 245 - ((this.getPeriod().length()/2)-espaceWord(this.getPeriod()))*8;
	        g2.drawString(this.getPeriod().toUpperCase(), posX, 195);
	        

	        int positionY = 200,positionX=20;
	        /*profile*/
	        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,8));
	        g2.drawRect(positionX, positionY, 550, 45);
	        positionY+=10;
	        g2.drawString("Matricule : "+report.getProfile().getMatricule(), positionX+10, positionY);
	        g2.drawString("Nom : "+report.getProfile().getNom().toUpperCase(), positionX+250, positionY);
                Image image =null;    
                try {
                        URL url =new URL(memoire.photo+report.getProfile().getphoto());
                        image =  ImageIO.read(url);

                    } catch (MalformedURLException ex) {
                        Logger.getLogger(Impression.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Impression.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        g2.setColor(Color.LIGHT_GRAY);
               g2.fillRect(456, 97, 64, 64);
    
               g2.drawImage(image, 458, 99,60, 60, null);
                        g2.setColor(Color.black);

                positionY+=15;
	        g2.drawString("Sexe : "+report.getProfile().getSexe(), positionX+10, positionY);
	        g2.drawString("Date et lieu de naissance : "+report.getProfile().geDate()+" à "+report.getProfile().getLieu(), positionX+250, positionY);
	        positionY+=15;
	        g2.drawString("Classe : "+report.getProfile().getClasse(), positionX+10, positionY);
	        int effectif = report.getProfile().getEffectif();
	        if(effectif==0) effectif = reports.getListReports().size();
	        g2.drawString("Effectif de la classe: "+effectif, positionX+250, positionY);
	        
	        /*Report Group1*/
	        positionY+=20;
	        positionY = drawCaseEntete(g2, positionX, positionY);
	        
	        if(getPeriod().toLowerCase().contains("annuel")){
                    positionY = drawCaseAnGroup(g2, positionX+100, positionY);}
	        else if(getPeriod().toLowerCase().contains("trimestre")){
                    positionY = drawCaseTrimGroup(g2, positionX+100, positionY);}
	        else if(getPeriod().toLowerCase().contains("mois")){
                    positionY = drawCaseSeqGroup(g2, positionX+100, positionY);}
	        
	        positionY = drawCaseGroup(g2, positionX, positionY,report,1,reports.getMoyenneGeneral());
	        positionY = drawCaseGroup(g2, positionX, positionY,report,2,reports.getMoyenneGeneral());
	        positionY = drawCaseGroup(g2, positionX, positionY,report,3,reports.getMoyenneGeneral());
	        positionY = drawCaseDiscAppreciation(g2,positionX,positionY+35,report);
	        positionY = drawCaseObservation(g2, positionX, positionY);
	        System.out.println("positionY="+positionY);
        	
	        try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
	        
	        return PAGE_EXISTS;
		}
        alert.setProgress(200);
        alert.close();

        return NO_SUCH_PAGE;
	}
	
	/**
	  * Cette m�thode permet d'imprim� les notes des sequences pour un bulletin sequentiel
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  * @param matiere objet contenant les informations sur les notes, coefficient de la matiere
	  */
	public void drawCaseNoteSeqGroup(Graphics2D g2,int positionX, int positionY,InfoMatiere matiere){
		g2.drawRect(positionX, positionY, 200, 15);
		if(getPeriod().contains("septembre")) {
                    if(matiere.getNoteSeq1()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq1()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }
                }
		else if(getPeriod().contains("octobre")){
                          if(matiere.getNoteSeq2()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq2()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }                }
		else if(getPeriod().contains("novembre")) {
                         if(matiere.getNoteSeq3()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq3()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }                }
		else if(getPeriod().contains("janvier")){
                          if(matiere.getNoteSeq1()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq1()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }                }
		else if(getPeriod().contains("fevrier")){
                    if(matiere.getNoteSeq2()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq2()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }                }
                else if(getPeriod().contains("avril")){
                          if(matiere.getNoteSeq1()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq1()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }                }
		else if(getPeriod().contains("mai")){
                    if(matiere.getNoteSeq2()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq2()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }                }
		else if(getPeriod().contains("mars")){
                        if(matiere.getNoteSeq3()!=0){
                           g2.drawString(trimValue(""+matiere.getNoteSeq3()), positionX+92, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+92, positionY+10);
                                    }                }
        
	}
	
	/**
	  * Cette m�thode permet d'imprim� les notes des sequences pour un bulletin trimestriel
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  * @param matiere objet contenant les informations sur les notes, coefficient de la matiere
	  */
	public void drawCaseNoteTrimGroup(Graphics2D g2,int positionX, int positionY,InfoMatiere matiere){
		for(int i=0;i!=3;i++){
                    if(i==0){
                     g2.drawRect(positionX, positionY, 66, 15);

                    }else if(i==1){
                     g2.drawRect(positionX, positionY, 67, 15);
     
                    }else{
                     g2.drawRect(positionX+1, positionY, 67, 15);
   
                    }

			if(getPeriod().contains("troisieme")) {
				if(i==0){
                                    if(matiere.getNoteSeq1()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq1()), positionX+33, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+32, positionY+10);
                                    }
                                  // g2.drawString(trimValue(""+matiere.getNoteSeq5()), positionX+42, positionY+10);
                                 }else if(i==1){ 
                                   if(matiere.getNoteSeq2()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq2()), positionX+33, positionY+10);
                                     }else{
                                    g2.drawString(trimValue("Abs"),positionX+33, positionY+10);
                                    }
                                }else if(i==2){ 
                                   if(matiere.getNoteSeq3()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq3()), positionX+33, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+33, positionY+10);
                                    }                               }
			}else if(getPeriod().contains("premier")){
				if(i==0){
                                    if(matiere.getNoteSeq1()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq1()), positionX+33, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+32, positionY+10);
                                    }
                                  // g2.drawString(trimValue(""+matiere.getNoteSeq5()), positionX+42, positionY+10);
                                 }else if(i==1){ 
                                   if(matiere.getNoteSeq2()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq2()), positionX+33, positionY+10);
                                     }else{
                                    g2.drawString(trimValue("Abs"),positionX+33, positionY+10);
                                    }
                                }else if(i==2){ 
                                   if(matiere.getNoteSeq3()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq3()), positionX+33, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+33, positionY+10);
                                    }                               }
			}
				
	        positionX+=66;
		}
	}

	/**
	  * Cette m�thode permet d'imprim� les notes des sequences pour un bulletin annuel
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  * @param matiere objet contenant les informations sur les notes, coefficient de la matiere
	  */
	public void drawCaseNoteAnGroup(Graphics2D g2,int positionX, int positionY,InfoMatiere matiere){
		for(int i=0;i!=3;i++){
                    if(i==0){
                     g2.drawRect(positionX, positionY, 66, 15);

                    }else if(i==1){
                     g2.drawRect(positionX, positionY, 67, 15);
     
                    }else{
                     g2.drawRect(positionX+1, positionY, 67, 15);
   
                    }

				if(i==0){
                                    if(matiere.getNoteSeq1()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq1()), positionX+33, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+32, positionY+10);
                                    }
                                  // g2.drawString(trimValue(""+matiere.getNoteSeq5()), positionX+42, positionY+10);
                                 }else if(i==1){ 
                                   if(matiere.getNoteSeq2()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq2()), positionX+33, positionY+10);
                                     }else{
                                    g2.drawString(trimValue("Abs"),positionX+33, positionY+10);
                                    }
                                }else if(i==2){ 
                                   if(matiere.getNoteSeq3()!=0){
                                     g2.drawString(trimValue(""+matiere.getNoteSeq3()), positionX+33, positionY+10);
                                    }else{
                                    g2.drawString(trimValue("Abs"), positionX+33, positionY+10);
                                    }                               }
			
				
	        positionX+=66;
		}
		
	}
	
	/**
	  * Cette m�thode permet d'imprim� les nombres de colonnes des sequences pour un bulletin sequentiel
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	public int drawCaseSeqGroup(Graphics2D g2,int positionX, int positionY){
		int index = 0;
		if(getPeriod().contains("novembre")) index = 3;
		else if(getPeriod().contains("octobre")) index = 2;
		else if(getPeriod().contains("septembre")) index = 1;
		else if(getPeriod().contains("decembre")) index = 4;
		else if(getPeriod().contains("janvier")) index = 5;
		else if(getPeriod().contains("mars")) index = 7;
		else if(getPeriod().contains("avril")) index = 8;
		else if(getPeriod().contains("mai")) index = 9;
		else if(getPeriod().contains("fevrier")) index = 6;
		g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 200, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 200, 15);
        g2.drawString("Mois : "+index+" / 8", positionX+40, positionY+10);
        return positionY+20;
	}
	
	/**
	  * Cette m�thode permet d'imprim� les nombres de colonnes des sequences pour un bulletin trimestriel
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	public int drawCaseTrimGroup(Graphics2D g2,int positionX, int positionY){
		int index = 0;
		if(getPeriod().contains("troisieme")) index = 6;
		else if(getPeriod().contains("deuxieme")) index = 3;
		else if(getPeriod().contains("premier")) index = 0;
		for(int i=index;i!=index+3;i++){
                     if(i==0){
                g2.setColor(Color.LIGHT_GRAY);
	        g2.fillRect(positionX, positionY, 66, 15);
	        g2.setColor(Color.black);
	        g2.drawRect(positionX, positionY, 66, 15);
	        g2.drawString("Mois "+(i+1), positionX+20, positionY+10);
                    }else if(i==1){
                g2.setColor(Color.LIGHT_GRAY);
	        g2.fillRect(positionX, positionY, 67, 15);
	        g2.setColor(Color.black);
	        g2.drawRect(positionX, positionY, 67, 15);
	        g2.drawString("Mois "+(i+1), positionX+20, positionY+10);
                    }else{
                g2.setColor(Color.LIGHT_GRAY);
	        g2.fillRect(positionX, positionY, 67, 15);
	        g2.setColor(Color.black);
	        g2.drawRect(positionX+1, positionY, 67, 15);
	        g2.drawString("Mois "+(i+1), positionX+20, positionY+10);
                    }
	        positionX+=66;
		}
		return positionY+20;
	}
	

	/**
	  * Cette m�thode permet d'imprim� les nombres de colonnes des sequences pour un bulletin annuel
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	public int drawCaseAnGroup(Graphics2D g2,int positionX, int positionY){
		int index = 0;
		for(int i=index;i!=index+3;i++){
                     if(i==0){
                g2.setColor(Color.LIGHT_GRAY);
	        g2.fillRect(positionX, positionY, 66, 15);
	        g2.setColor(Color.black);
	        g2.drawRect(positionX, positionY, 66, 15);
	        g2.drawString("Trim. "+(i+1), positionX+20, positionY+10);
                    }else if(i==1){
                g2.setColor(Color.LIGHT_GRAY);
	        g2.fillRect(positionX, positionY, 67, 15);
	        g2.setColor(Color.black);
	        g2.drawRect(positionX, positionY, 67, 15);
	        g2.drawString("Trim. "+(i+1), positionX+20, positionY+10);
                    }else{
                g2.setColor(Color.LIGHT_GRAY);
	        g2.fillRect(positionX, positionY, 67, 15);
	        g2.setColor(Color.black);
	        g2.drawRect(positionX+1, positionY, 67, 15);
	        g2.drawString("Trim. "+(i+1), positionX+20, positionY+10);
                    }
	        positionX+=66;
		}
		return positionY+20;
	}
	public int drawCaseAnGroupcollege(Graphics2D g2,int positionX, int positionY){
		for(int i=0;i!=6;i++){
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(positionX, positionY, 35, 15);
	        g2.setColor(Color.black);
	        g2.drawRect(positionX, positionY, 35, 15);
                if(i<3){
	        g2.drawString(""+(i+1), positionX+14, positionY+10);
                }else{
                g2.drawString(""+(i+1-3), positionX+14, positionY+10);

                }
                positionX+=33;
		}
		return positionY+20;
	}

	/**
	  * Cette m�thode permet d'imprim� le box des notes et mati�res appartenant � un groupe grp
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  * @param report objet repr�sentant un bulletin de note 
	  * @param grp le groupe des mati�re
	  * @param moyclasse La moyenne de la classe
	  */
	
	public int drawCaseGroup(Graphics2D g2,int positionX, int positionY,Report report,int grp,String moyclasse){
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
		for(InfoMatiere matiere: groupeMatiere.getMatieres()){
	        g2.drawRect(positionX, positionY, 100, 15);
                
	        g2.drawString(trimMatiere(trimDiscipline(matiere.getMatiere().toLowerCase())), positionX+5, positionY+6);
	        g2.setFont(new Font(Font.SERIF,Font.ITALIC,6));
                g2.drawString(trimNom(matiere.getEnseignant().toUpperCase()), positionX+5, positionY+12);
	        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,8));

	        if(getPeriod().toLowerCase().contains("annuel"))
	        	drawCaseNoteAnGroup(g2, positionX+100, positionY,matiere);
	        else if(getPeriod().toLowerCase().contains("trimestre"))
	        	drawCaseNoteTrimGroup(g2, positionX+100, positionY,matiere);
	        else if(getPeriod().toLowerCase().contains("mois"))
	        	drawCaseNoteSeqGroup(g2, positionX+100, positionY,matiere);
	        
	        g2.drawRect(positionX+100+200, positionY, 40, 15);
	        g2.drawString(""+matiere.getCoef(), positionX+100+200+20, positionY+10);
	        g2.drawRect(positionX+100+200+40, positionY, 40, 15);
	        g2.drawRect(positionX+100+200+40+40, positionY, 40, 15);
	        
	        String observation = "/";
                	
	        if(getPeriod().toLowerCase().contains("annuel")){
	        	int nbre = 0,nbre2=0;
	        	float som = 0,som2=0;
                        
	        	if(matiere.getNoteSeq1()>0){
		        		som+=matiere.getNoteSeq1();
		        		nbre++;

		        	}
		        	if(matiere.getNoteSeq2()>0){
		        		som+=matiere.getNoteSeq2();
		        		nbre++;

		        	}
                                if(matiere.getNoteSeq3()>0){
		        		som+=matiere.getNoteSeq3();
		        		nbre++;

		        	}
	        	if(nbre!=0) {
                            if(nbre2==0){
                              nbre2=1;  
                            }
                            System.out.println("total sequence : "+nbre);
                            	        coef+=matiere.getCoef();

	        		prod+=((som/nbre)+(som2/nbre2))*matiere.getCoef();
                               float not =(som/nbre)+(som2/nbre2);
                                if(not>=10){
	        		g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                }else{
                                g2.setColor(Color.RED);    
                                g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                 g2.setColor(Color.black);   
                                }                                not=(not)*matiere.getCoef();

                             g2.drawString(trimValuerep(""+not), positionX+100+200+40+40+10, positionY+10);
}
	        	if(matiere.getObservation().equals("/")){
	        		observation = observation(""+((som/nbre)+(som2/nbre2)));
	        	}
	        
		}else if(getPeriod().toLowerCase().contains("trimestre")){
	        	if(getPeriod().contains("troisieme")) {
		        	int nbre = 0;
		        	float som = 0;
		        	if(matiere.getNoteSeq1()>0){
		        		som+=matiere.getNoteSeq1();
		        		nbre++;

		        	}
		        	if(matiere.getNoteSeq2()>0){
		        		som+=matiere.getNoteSeq2();
		        		nbre++;

		        	}
                                if(matiere.getNoteSeq3()>0){
		        		som+=matiere.getNoteSeq3();
		        		nbre++;

		        	}
		        	if(nbre!=0) {	      
                                    coef+=matiere.getCoef();

		        		prod+=(som/nbre)*matiere.getCoef();
		        	  float not =som/nbre;
                                if(not>=10){
	        		g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                }else{
                                g2.setColor(Color.RED);    
                                g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                 g2.setColor(Color.black);   
                                }                                not=(not)*matiere.getCoef();

                             g2.drawString(trimValuerep(""+not), positionX+100+200+40+40+10, positionY+10);

                                }
		        	if(matiere.getObservation().equals("/")){
		        		observation = observation(""+((som/nbre)));
		        	}
		        }
				else if(getPeriod().contains("deuxieme")) {
					int nbre = 0;
		        	float som = 0;
		        	if(matiere.getNoteSeq1()>0){
		        		som+=matiere.getNoteSeq1();
		        		nbre++;

		        	}
		        	if(matiere.getNoteSeq2()>0){
		        		som+=matiere.getNoteSeq2();
		        		nbre++;

		        	}
                                if(matiere.getNoteSeq3()>0){
		        		som+=matiere.getNoteSeq3();
		        		nbre++;

		        	}
		        	if(nbre!=0) {	      
                                       coef+=matiere.getCoef();
	        		prod+=(som/nbre)*matiere.getCoef();

		        	float not =som/nbre;
                                if(not>=10){
	        		g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                }else{
                                g2.setColor(Color.RED);    
                                g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                 g2.setColor(Color.black);   
                                }
                               not=not*matiere.getCoef();

                             g2.drawString(trimValuerep(""+not), positionX+100+200+40+40+10, positionY+10);

                                }
		        	if(matiere.getObservation().equals("/")){
		        		observation = observation(""+((som/nbre)));
		        	}
				}
				else if(getPeriod().contains("premier")) {
					int nbre = 0;
		        	float som = 0;
		        	if(matiere.getNoteSeq1()>0){
		        		som+=matiere.getNoteSeq1();
		        		nbre++;

		        	}
		        	if(matiere.getNoteSeq2()>0){
		        		som+=matiere.getNoteSeq2();
		        		nbre++;
}
                                if(matiere.getNoteSeq3()>0){
		        		som+=matiere.getNoteSeq3();
		        		nbre++;

		        	}
		        	if(nbre!=0) {
                                    coef+=matiere.getCoef();
                                    prod+=(som/nbre)*matiere.getCoef();

                        float not =som/nbre;
                                if(not>=10){
	        		g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                }else{
                                g2.setColor(Color.RED);    
                                g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                 g2.setColor(Color.black);   
                                }
                                not=(not)*matiere.getCoef();
                             g2.drawString(trimValuerep(""+not), positionX+100+200+40+40+10, positionY+10);
}
		        	if(matiere.getObservation().equals("/")){
		        		observation = observation(""+((som/nbre)));
		        	}
				}
	        }else if(getPeriod().toLowerCase().contains("mois")){
	        	int nbre = 0;
	        	float som = 0;
	        	if(getPeriod().contains("novembre")) {
	        		if(matiere.getNoteSeq3()>0){
		        		som+=matiere.getNoteSeq3();
		        		nbre++;	 

		        	}
	        	}
	    		else if(getPeriod().contains("octobre")) {
	    			if(matiere.getNoteSeq2()>0){
		        		som+=matiere.getNoteSeq2();
		        		nbre++;	  

		        	}
	    		}
	    		else if(getPeriod().contains("septembre")) {
	    			if(matiere.getNoteSeq1()>0){
		        		som+=matiere.getNoteSeq1();
		        		nbre++;	  

		        	}
	    		}
	    		else if(getPeriod().contains("decembre")) {
	    			if(matiere.getNoteSeq4()>0){
		        		som+=matiere.getNoteSeq4();
		        		nbre++;	 

		        	}
	    		}
	    		else if(getPeriod().contains("janvier")){
	    			if(matiere.getNoteSeq1()>0){
		        		som+=matiere.getNoteSeq1();
		        		nbre++;	  

		        	}
	    		}
	    		else if(getPeriod().contains("fevrier")) {
	    			if(matiere.getNoteSeq2()>0){
		        		som+=matiere.getNoteSeq2();
		        		nbre++;	 

		        	}
	    		}else if(getPeriod().contains("mars")) {
	    			if(matiere.getNoteSeq3()>0){
		        		som+=matiere.getNoteSeq3();
		        		nbre++;	 

		        	}
	    		}else if(getPeriod().contains("avril")) {
	    			if(matiere.getNoteSeq1()>0){
		        		som+=matiere.getNoteSeq1();
		        		nbre++;	 

		        	}
	    		}
                        else if(getPeriod().contains("mai")) {
	    			if(matiere.getNoteSeq2()>0){
		        		som+=matiere.getNoteSeq2();
		        		nbre++;	 

		        	}
	    		}
	        	if(nbre!=0) {
                            	        coef+=matiere.getCoef();

	        		prod+=(som/nbre)*matiere.getCoef();
	        	  float not =som/nbre;
                                if(not>=10){
	        		g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                }else{
                                g2.setColor(Color.RED);    
                                g2.drawString(trimValuerep(""+(not)), positionX+100+200+40+10, positionY+10);
                                 g2.setColor(Color.black);   
                                }
                              not=(not)*matiere.getCoef();

                             g2.drawString(trimValuerep(""+not), positionX+100+200+40+40+10, positionY+10);

                        }
	        	if(matiere.getObservation().equals("/")){
	        		observation = observation(""+((som/nbre)));
	        	}
	        }
	        
	        
	        g2.drawRect(positionX+100+200+40+40+40, positionY, 40, 15);
	        g2.drawString(matiere.getRang(), positionX+100+200+40+40+40+15, positionY+10);
                
	        g2.drawRect(positionX+100+200+40+40+40+40, positionY, 95, 15);
                g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,8));
	        g2.drawString(observation, positionX+100+200+40+40+40+40+5, positionY+10);
	        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,8));

                positionY+=15;
                
		}
            switch (grp) {
                case 1:
                    produits1+=prod;
                    coefs1+=coef;
                    break;
                case 2:
                    produits2+=prod;
                    coefs1+=coef;
                    break;
                case 3:
                    produits3+=prod;
                    coefs1+=coef;
                    break;
                default:
                    break;
                 }
		drawTotauxGroup(g2, positionX, positionY,coef,prod,(prod/coef),""+grp);
		if(grp==3){
                    
			positionY+=15;
			String app = report.getAppreciation();
			if(app.equals("/"))
                        {
                            app = appreciation(""+(produits1+produits2+produits3)/(coefs1+coefs2+coefs3));
                        }
                      double MOY=0; 
                         if ((coefs1+coefs2+coefs3)!=0){
                                  MOY = (produits1+produits2+produits3) /(double)(coefs1+coefs2+coefs3);
                                 //cas eleve present
                           }else{
                      //cas eleve absent
                        MOY = produits1+produits2+produits3;
                    
                    }
                         BigDecimal valeur = (new BigDecimal(MOY)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                       double moyen_eleve=valeur.doubleValue();
                        
                                          for(int z=0;z<rang.size();z++){
                                              if(moyen_eleve==Double.parseDouble(rang.get(z))){
                                                position=z+1;
                                             }
                                                 
                                          }
                 if(position!=position2){
                                                
                         r.add(""+position);
                                               
                         position2=position;
                                               
                     }
                                          drawCaseTotaux(g2, positionX, positionY, coefs1+coefs2+coefs3, produits1+produits2+produits3, 
					       MOY, String.valueOf(position), app,reports.getMoyenneGeneral());
                                                                 
                                          
                                           moyendecision=MOY;
						return positionY+40; 
		}
		else return positionY+15;

	}
	

	/**
	  * Cette m�thode permet d'imprim� le box des totaux et moyennes
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  * @param coef total des coeficients
	  * @param produit total des produits note x coefficient
	  * @param moyenne la moyenne
	  * @param group valeur du groupe des matiere variant entre 1 et 3
	  */
	
	public void drawTotauxGroup(Graphics2D g2,int positionX, int positionY, int coef,float produit,float moyenne,String group){
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(positionX, positionY, 100, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 100, 15);
        g2.drawString("TOTAL GROUPE "+group, positionX+5, positionY+10);
        
        g2.setColor(Color.GRAY);
		g2.fillRect(positionX+100+200, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200, positionY, 40, 15);
        g2.drawString(""+coef, positionX+100+200+20, positionY+10);
        
        g2.setColor(Color.GRAY);
		g2.fillRect(positionX+100+200+40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200+40, positionY, 40, 15);
        g2.drawString(trimValuerep(""+moyenne), positionX+100+200+40+10, positionY+10);
        
        g2.setColor(Color.GRAY);
		g2.fillRect(positionX+100+200+40+40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200+40+40, positionY, 40, 15);
        g2.drawString(trimValuerep(String.valueOf(produit)), positionX+100+200+40+40+10, positionY+10);
        
	}
	

	/**
	  * Cette m�thode permet d'imprim� le box des totaux et moyennes
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
	public int drawCaseTotaux(Graphics2D g2,int positionX, int positioY,int coef,double prod,double moyenne,String rang, String appreciation,String moyClasse){
        int positionY=positioY+2;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100, positionY, 200, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100, positionY, 200, 15);
        g2.drawString("TOTAUX | MOYENNE  "+ getStatusPeriod()+" /20", positionX+100+30, positionY+10);
        
        g2.setColor(Color.black);
        g2.fillRect(positionX+100+200, positionY, 40, 15);
        g2.setColor(Color.WHITE);
        g2.drawRect(positionX+100+200, positionY, 40, 15);
        g2.drawString(""+coef, positionX+100+200+20, positionY+10);
        g2.setColor(Color.black);
        g2.fillRect(positionX+100+200+40, positionY, 40, 15);
        g2.setColor(Color.WHITE);
        g2.drawRect(positionX+100+200+40, positionY, 40, 15);
        g2.drawString(trimValuerep(""+moyenne), positionX+100+200+40+10, positionY+10);
        g2.setColor(Color.black);
        g2.fillRect(positionX+100+200+40+40, positionY, 40, 15);
        g2.setColor(Color.white);
        g2.drawRect(positionX+100+200+40+40, positionY, 40, 15);
        g2.drawString(trimValuerep(""+prod), positionX+100+200+40+40+10, positionY+10);
        g2.setColor(Color.black);
        g2.fillRect(positionX+100+200+40+40+40, positionY, 40, 15);
        g2.setColor(Color.white);
        g2.drawRect(positionX+100+200+40+40+40, positionY, 40, 15);
        g2.drawString(""+rang, positionX+100+200+40+40+40+15, positionY+10);
        
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(positionX+100+200+40+40+40+40, positionY, 95, 40);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200+40+40+40+40, positionY, 95, 40);

        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,8));
                g2.setColor(Color.orange);

        String[] pattern = appreciation.split("<BR>");
        int i = 1;
        for(String motif:pattern){
        	g2.drawString(motif, positionX+100+300+55+10, positionY+(10*i));
        	i++;
        }        g2.setColor(Color.black);

        g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,8));
        
        positionY+=17;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 100, 20);
        g2.drawString("MOYENNE CLASSE /20", positionX+5, positionY+10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100, positionY, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100, positionY, 100, 20);
        g2.drawString(trimValuerep(moyClasse), positionX+100+42, positionY+10);
        memoire.MOYGENstatistiques=""+moyClasse;

        positionY+=17;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+35, positionY, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX+35, positionY, 100, 20);
        g2.drawString("MOY PREMIER", positionX+40, positionY+10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100, positionY, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100, positionY, 100, 20);
        g2.drawString(trimValuerep(String.valueOf(memoire.MOYP)), positionX+100+42, positionY+10);
        
        positionY+=12;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+45, positionY+5, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX+45, positionY+5, 100, 20);
        g2.drawString("MOY DER.", positionX+50, positionY+15);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100, positionY+5, 100, 20);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100, positionY+5, 100, 20);
        g2.drawString(trimValuerep(String.valueOf(memoire.MOYD)), positionX+100+42, positionY+15);

        return positionY+20;
	}
	
	/**
	  * Cette m�thode permet d'imprim� le box des appreciations et disciplines
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  * @param report objet repr�sentant un bulletin de notes
	  */
	public int drawCaseDiscAppreciation(Graphics2D g2,int positionX, int positionY,Report report){
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("DISCIPLINE", positionX+60, positionY+10);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+276, positionY, 279, 15);
        g2.drawString("APPRECIATION \nTRAVAIL".toUpperCase(), positionX+276+90, positionY+10);
        
        positionY+=15;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 69, 15);
        g2.drawString("ABSENCE(heure)", positionX+5, positionY+10);
        g2.drawRect(positionX, positionY+15, 69, 15);
        g2.drawString(""+(calculHours(report)), positionX+33, positionY+15+10);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+69, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+69, positionY, 69, 15);
        g2.drawString("AVERTISSEMENT", positionX+69+5, positionY+10);
        g2.drawRect(positionX+69, positionY+15, 69, 15);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+69+69, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+69+69, positionY, 69, 15);
        g2.drawString("BLAME".toUpperCase(), positionX+69+69+15, positionY+10);
        g2.drawRect(positionX+69+69, positionY+15, 69, 15);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+69+69+69, positionY, 69, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+69+69+69, positionY, 69, 15);
        g2.drawString("EXCLUSION", positionX+69+69+69+5, positionY+10);
        g2.drawRect(positionX+69+69+69, positionY+15, 69, 15);
        
        g2.drawRect(positionX+276, positionY, 34, 15);
        g2.drawString("TB", positionX+276+15, positionY+10);
        g2.drawRect(positionX+276+34, positionY, 34, 15);
        g2.drawString("B", positionX+276+34+15, positionY+10);
        g2.drawRect(positionX+276+34+34, positionY, 34, 15);
        g2.drawString("AB", positionX+276+34+34+15, positionY+10);
        g2.drawRect(positionX+276+34+34+34, positionY, 35, 15);
        g2.drawString("PASS", positionX+276+34+34+34+10, positionY+10);
        g2.drawRect(positionX+276+34+35+34+34, positionY, 34, 15);
        g2.drawString("MED", positionX+276+35+34+34+34+10, positionY+10);
        g2.drawRect(positionX+276+34+35+34+34+34, positionY, 36, 15);
        g2.drawString("INSU", positionX+276+35+34+34+34+34+10, positionY+10);
        g2.drawRect(positionX+276+34+35+34+34+34+36, positionY, 36, 15);
        g2.drawString("FAIB", positionX+276+35+34+36+34+34+34+10, positionY+10);
        g2.drawRect(positionX+276+34+35+34+34+34+36+36, positionY, 36, 15);
        g2.drawString("NUL", positionX+276+35+34+34+34+34+36+36+10, positionY+10);
       
        g2.drawRect(positionX+276, positionY+15, 55, 15);
        g2.drawString("Encouragem.", positionX+276+5, positionY+15+10);
        
        g2.drawRect(positionX+276+55, positionY+15, 55, 15);
        g2.drawString("Félicit.", positionX+276+55+5, positionY+15+10);
        
        g2.drawRect(positionX+276+55+55, positionY+15, 65, 15);
        g2.drawString("Tableau d'hon.", positionX+276+55+55+1, positionY+15+10);

        g2.drawRect(positionX+276+55+55+65, positionY+15, 55, 15);
        g2.drawString("Avertissem.", positionX+276+55+55+65+1, positionY+15+10);
        
        g2.drawRect(positionX+276+55+55+65+55, positionY+15, 49, 15);
        g2.drawString(" Blames", positionX+276+55+55+65+55+5, positionY+15+10);
        
        positionY+=30;
        g2.drawRect(positionX, positionY, 276, 35);
        g2.drawString("MOTIF : ", positionX+5, positionY+10);
        
        if(getPeriod().contains("annuel")){
        	g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(positionX+276, positionY, 279, 15);
            g2.setColor(Color.black);
            g2.drawRect(positionX+276, positionY, 279, 15);
            g2.drawString("DECISION", positionX+276+95, positionY+10);
            
            g2.drawRect(positionX+276, positionY+15, 279, 20);
            g2.setColor(Color.RED);

            g2.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,8));
             if(moyendecision>=10.00){
              g2.drawString("ADMIS(E) EN CLASSE SUPERIEURE ", positionX+276+10, positionY+15+10);
              }else if(moyendecision<10 && moyendecision>=9){
              g2.drawString("REDOUBLE ou peut etre repeché", positionX+276+10, positionY+15+10);
              }else{
               g2.drawString("REDOUBLE ", positionX+276+10, positionY+15+10);
              }                                               
            g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,6));
            return positionY+35;
        }
        
        
        return positionY+35;
	}
	
	/**
	  * Cette m�thode permet de calculer le cumul des heures pendant une periode
	  * @param report objet repr�sentant un bulletin
	  */
	
	public String calculHours(Report report){
		
		if(getPeriod().toLowerCase().contains("annuel")){
			return ""+(report.getDiscipline());
		}else if(getPeriod().toLowerCase().contains("trimestre")){
        	if(getPeriod().contains("troisieme")) {
        		return ""+(report.getDiscipline());
	        }
			else if(getPeriod().contains("deuxieme")) {
				return ""+(report.getDiscipline());
			}
			else if(getPeriod().contains("premier")) {
				return ""+(report.getDiscipline()
						);
			}
        }else if(getPeriod().toLowerCase().contains("sequence")){
        	if(getPeriod().contains("troisieme")) {
        		return ""+(report.getDiscipline());
        	}
    		else if(getPeriod().contains("deuxieme")) {
    			return ""+(report.getDiscipline());
    		}
    		else if(getPeriod().contains("premiere")) {
    			return ""+(report.getDiscipline());
    		}
    		else if(getPeriod().contains("quatrieme")) {
    			return ""+(report.getDiscipline());
    		}
    		else if(getPeriod().contains("cinquieme")){
    			return ""+(report.getDiscipline());
    		}
    		else if(getPeriod().contains("sixieme")) {
    			return ""+(report.getDiscipline());
    		}
        }
		return "/";
	}
	
	/**
	  * Cette m�thode permet d'imprim� le box des observations
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	
	public int drawCaseObservation(Graphics2D g2,int positionX, int positionY){
		if(period.trim().contains("mois")) return drawCaseObservationSequence(g2, positionX, positionY);
		else if(period.trim().contains("trimestre")) return drawCaseObservationTrimestre(g2, positionX, positionY);
		else if(period.trim().contains("annuel")) return drawCaseObservationAnnuel(g2, positionX, positionY);
		return positionY;
	}
	
	/**
	  * Cette m�thode permet d'imprim� le box des observations trimestrielles
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	public int drawCaseObservationTrimestre(Graphics2D g2,int positionX, int positionY){
		g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU PARENT", positionX+10, positionY+10);
        g2.drawRect(positionX, positionY, 276, 842-(positionY+15));
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+276, positionY, 279, 15);
        g2.drawString("OBSERVATION ET VISA DU CHEF D'ETABLISSEMENT", positionX+276+10, positionY+10);
        g2.drawRect(positionX+276, positionY, 279, 842-(positionY+15));
        
		return positionY+842-(positionY+15);
	}
	
	/**
	  * Cette m�thode permet d'imprim� le box des observations sequentielles
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	public int drawCaseObservationSequence(Graphics2D g2,int positionX, int positionY){
		g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU SURVEILLANT GENERAL", positionX+10, positionY+10);
        g2.drawRect(positionX, positionY, 276, (842-(positionY+15))/2);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY+(842-(positionY+15))/2, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY+(842-(positionY+15))/2, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU PARENT", positionX+10, positionY+(842-(positionY+15))/2+10);
        g2.drawRect(positionX, positionY+(842-(positionY+15))/2, 276, (842-(positionY+15))/2);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+276, positionY, 279, 15);
        g2.drawString("OBSERVATION ET VISA DU PROFESSEUR PRINCIPAL", positionX+276+10, positionY+10);
        g2.drawRect(positionX+276, positionY, 279, 842-(positionY+15));
        
		return positionY+842-(positionY+15);
	}
	
	/**
	  * Cette m�thode permet d'imprim� le box des observations annuelles
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	
	public int drawCaseObservationAnnuel(Graphics2D g2,int positionX, int positionY){
		g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 276, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 276, 15);
        g2.drawString("OBSERVATION ET VISA DU PARENT", positionX+10, positionY+10);
        g2.drawRect(positionX, positionY, 276, 842-(positionY+15));
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+276, positionY, 279, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+276, positionY, 279, 15);
        g2.drawString("OBSERVATION ET VISA DU CHEF D'ETABLISSEMENT", positionX+276+10, positionY+10);
        g2.drawRect(positionX+276, positionY, 279, 842-(positionY+15));
        
		return positionY+842-(positionY+15);
	}
	
	/**
	  * Cette m�thode permet d'imprim� l'ent�te du tableau des notes et mati�res
	  * @param g2 objet permettant de dessiner
	  * @param positionX coordonn�e de l'abcisse X
	  * @param positionY coordonn�e de l'ordonn�e Y
	  */
	public int drawCaseEntete(Graphics2D g2,int positionX, int positionY){
		g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX, positionY, 100, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX, positionY, 100, 15);
        g2.drawString("MATIERE", positionX+40, positionY+10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100, positionY, 300, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100, positionY, 200, 15);
        g2.drawString(" NOTE MENSUELLE ", positionX+160, positionY+10);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100+200, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200, positionY, 40, 15);
        g2.drawString("COEF", positionX+100+200+15, positionY+10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100+200+40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200+40, positionY, 40, 15);
        g2.drawString("MOY", positionX+100+200+40+15, positionY+10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100+200+40+40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200+40+40, positionY, 40, 15);
        g2.drawString("MXC", positionX+100+200+40+40+15, positionY+10);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100+200+40+40+40, positionY, 40, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200+40+40+40, positionY, 40, 15);
        g2.drawString("RANG", positionX+100+200+40+40+40+15, positionY+10);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(positionX+100+200+40+40+40+40, positionY, 90, 15);
        g2.setColor(Color.black);
        g2.drawRect(positionX+100+200+40+40+40+40, positionY, 90, 15);
        g2.drawString("OBSERVATION", positionX+100+300+55+30, positionY+10);
        return positionY+15;
	}
	
	
	public Impression setReports(ReportsCards reports) {
		this.reports = reports;
		return this;
	}

	public String getPeriod() {
		return period;
	}

	
	public String getStatusPeriod() {
		if(period.trim().toLowerCase().contains("sequence")) return "MENSUELLE";
		else if(period.trim().toLowerCase().contains("trimestre")) return "TRIMESTRIELLE";
		else if(period.trim().toLowerCase().contains("annuel")) return "ANNUELLE";
		return "";
	}
	
	/**
	  * Cette m�thode permet de configur� le nom � donner au bulletin
	  * @param period la valeur de la period pouvant �tre 'sequence1','sequence3', 'trimestre3', 'trimestre1' ou 'annuel'
	  */
	
	public Impression setPeriod(String period) throws Exception {
		period = period.trim().toLowerCase();
		if(period.trim().toLowerCase().contains("janvier"))
			this.period = "Bulletin  mois de Janvier";
		else if(period.trim().toLowerCase().contains("fevrier"))
			this.period = "Bulletin  mois de fevrier";
		else if(period.trim().toLowerCase().contains("mars"))
			this.period = "Bulletin mois de mars";
		else if(period.trim().toLowerCase().contains("avril"))
			this.period = "Bulletin mois de avril";
		else if(period.trim().toLowerCase().contains("mai"))
			this.period = "Bulletin mois de mai";
		else if(period.trim().toLowerCase().contains("juin"))
			this.period = "Bulletin mois de juin";
		else if(period.trim().toLowerCase().contains("septembre"))
			this.period = "Bulletin mois de septembre";
                else if(period.trim().toLowerCase().contains("octobre"))
			this.period = "Bulletin mois d'octobre";
		else if(period.trim().toLowerCase().contains("novembre"))
			this.period = "Bulletin mois de novembre";
                else if(period.trim().toLowerCase().contains("decembre"))
			this.period = "Bulletin mois de decembre";
		else if(period.trim().toLowerCase().contains("trimestre1"))
			this.period = "Bulletin du premier trimestre";
                else if(period.trim().toLowerCase().contains("trimestre3"))
			this.period = "Bulletin du troisieme trimestre";
                else if(period.trim().toLowerCase().contains("trimestre2"))
			this.period = "Bulletin du deuxieme trimestre";
		else if(period.trim().toLowerCase().contains("annuel"))
			this.period = "Bulletin annuel";
                else if(period.trim().toLowerCase().contains("annuelc"))
			this.period = "Bulletin annuel";
		else throw new Exception("Format incorrect !!!! format acceptable : sequence<num> ou trimestre<num> ou annuel");
		return this;
	}

	/**
	  * Cette m�thode permet compter le nombre d'espace entre les mots d'un chaine
	  * @param word la chaine � traiter
	  * @return le nombre d'espace
	  */
	
	private int espaceWord(String word){
        int i=0,num=0;
        while(i<word.length()){
            if(word.charAt(i)==' ') num++;
            i++;
        }
        return num;
    }
	
	/**
	  * Cette m�thode permet d'arrondir � deux chiffres apr�s la virgule
	  * @param strValeur la valeur r�elle a traiter
	  * @return la valeur avec deux chiffres apr�s la virgule
	  */
	private String trimValue(String strValeur){
		try{
			BigDecimal valeur = (new BigDecimal(strValeur)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			if(valeur.doubleValue()<10) 
                            return "0"+valeur.toString().replace(".", ",");
                      
		    return valeur.toString().replace(".", ",");
		}catch(NumberFormatException ex){return "/";}   
	}private String trimValuerep(String strValeur){
		try{
			BigDecimal valeur = (new BigDecimal(strValeur)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			if(valeur.doubleValue()<10)  return "0"+valeur.toString().replace(".", ",");

            return valeur.toString().replace(".", ",");
		
		}catch(NumberFormatException ex){return "/";}   
	}
	
	/**
	  * Cette m�thode permet d'arrondir les noms des enseignants des mati�res
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
            return name.substring(0, 20) + ".";
        } else {
            return name;
        }
    }
      
	/**
	  * Cette m�thode permet d'arrondir les noms des enseignants des mati�res
	  * @param name la valeur r�elle a traiter
	  * @return le nom arrondi
	  */
	private String trimMatiere(String name) {
        if (name.length() > 20) {
            return name.substring(0, 19) + ".";
        } else {
            return name;
        }
    }
	
	/**
	  * Cette m�thode permet d'arrondir les noms des mati�res
	  * @param name la valeur r�elle a traiter
	  * @return le nom arrondi
	  */
	private String trimDiscipline(String arg) {
		if(arg.trim().isEmpty()) return "/";
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
                if ((0 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 11)) {
                    app = "NA/ "+("NA").toLowerCase(Locale.ITALY);
                }
              else  if ((11 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 14)) {
                    app = "ECA/ "+("EAD").toLowerCase(Locale.ITALY);
                }
               else if ((15 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 17)) {
                    app = "A./ "+("A").toLowerCase(Locale.ITALY);
                }else if ((17 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 20)) {
                    app = "A+/ "+("a+").toLowerCase(Locale.ITALY);
                }else if ((9 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 10)) {
                    app = "Mediocre/ "+("Mediocre").toLowerCase(Locale.ITALY);
                
                }else{
                 app = "Absent/ "+("Absent").toLowerCase(Locale.ITALY);
  
                }
            }
        } catch (NumberFormatException ex) {}
        return app;
    }
	
	public String appreciation(String Val) {
        String app = "";
        try {
            if (!Val.trim().equals("")) {
            	if ((0 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 11)) {
                    app = "NON<br>ACQUIS";
                }
               else if ((11 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 15)) {
                    app = "ECA<br>En cours Ac.";
                }
               else  if ((15 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 17)) {
                    app = "A<br>Acquis<br>travail";
                }
              else  if ((17 <= Float.parseFloat(Val)) && (Float.parseFloat(Val) < 21)) {
                    app = "A+<br>Expert travail<br>travail";
                
             
                }else{
                 app = "ABSENT";
  
                }

                if (12<= Float.parseFloat(Val)&&!getPeriod().trim().toLowerCase().contains("sequence")) {
                    app += "<br>Tableau d'honneur";
                }
                app = app.toUpperCase();
            }
        } catch (NumberFormatException ex) {
        }
        return app;
    }
}
