/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.json.JSONException;

/**
 *
 * @author Michelle Wafo
 */
public class ajout_nouvelle_entree extends JDialog {
    
private Ajouteleveinfo zInfo = new Ajouteleveinfo();
private JLabel nomLabel, sexeLabel, cheveuxLabel, ageLabel,
tailleLabel, taille2Label, icon;
private JRadioButton tranche1, tranche2, tranche3, tranche4;
private JComboBox sexe, cheveux,matieres, annee,mois,jour;
private JTextField nom, taille,lieu_naissance;
  String inline; 


public ajout_nouvelle_entree(JFrame parent, String title, boolean modal) throws IOException, JSONException{
super(parent, title, modal);
this.setSize(600, 400);
this.setLocationRelativeTo(null);
this.setResizable(false);
this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
this.initComponent();
} 

public Ajouteleveinfo showZDialog() throws JSONException, IOException{
this.setVisible(true);
return this.zInfo;

}

private void initComponent() throws IOException, JSONException{
//Icone
icon = new JLabel(new ImageIcon("error.png"));
JPanel panIcon = new JPanel();
panIcon.setLayout(new BorderLayout());
panIcon.add(icon);


JPanel panNom = new JPanel();
            panNom.setPreferredSize(new Dimension(220, 90));
               nom = new JTextField("");
               NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
                 decimalFormat.setGroupingUsed(false);
                  nom= new JFormattedTextField(decimalFormat);
                  nom.setColumns(10);
                  nom.setPreferredSize(new Dimension(80, 25));
               panNom.setBorder(BorderFactory.createTitledBorder("Montant à créditer"));
            nomLabel = new JLabel(" En chiffre uniquement ");
           panNom.add(nomLabel);
           panNom.add(nom);

           
JPanel panSexe = new JPanel();
       panSexe.setPreferredSize(new Dimension(220, 90));
       panSexe.setBorder(BorderFactory.createTitledBorder("Période de validité "));

                sexe = new JComboBox();
         sexe.addItem("1 er trimestre");
         sexe.addItem("2 eme trimestre");
         sexe.addItem("3 eme trimestre");
         sexe.addItem("Toute l'annee");

sexeLabel = new JLabel("Periode : ");
panSexe.add(sexeLabel);
panSexe.add(sexe);
//L'âge
JPanel panAge = new JPanel();
panAge.setBorder(BorderFactory.createTitledBorder("Age de l'élève"));
panAge.setPreferredSize(new Dimension(440,70));
tranche1 = new JRadioButton("08 - 12 ans");
tranche1.setSelected(true);
tranche2 = new JRadioButton("13 - 16 ans");
tranche3 = new JRadioButton("17 - 19 ans");
tranche4 = new JRadioButton("plus ");
ButtonGroup bg = new ButtonGroup();
bg.add(tranche1);
bg.add(tranche2);
bg.add(tranche3);
bg.add(tranche4);
panAge.add(tranche1);
panAge.add(tranche2);
panAge.add(tranche3);
panAge.add(tranche4);
//La taille
JPanel panTaille = new JPanel();
                  panTaille.setPreferredSize(new Dimension(220, 140));
                 panTaille.setBorder(BorderFactory.createTitledBorder("Infos complémentaires "));
tailleLabel = new JLabel("Descrition brève : ");
taille2Label = new JLabel("");
taille = new JTextField("");
taille.setPreferredSize(new Dimension(150, 70));

panTaille.add(tailleLabel);
panTaille.add(taille);
panTaille.add(taille2Label);
//La couleur des cheveux
         JPanel panCheveux = new JPanel();
         panCheveux.setPreferredSize(new Dimension(220, 90));
         panCheveux.setBorder(BorderFactory.createTitledBorder("Classe actuelle"));

cheveuxLabel = new JLabel("Classe");
panCheveux.add(cheveuxLabel);

JPanel matiere = new JPanel();
matiere.setPreferredSize(new Dimension(450, 100));
matiere.setBorder(BorderFactory.createTitledBorder("Objet justifiant la creditation du compte"));

        String [] jours = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22",
            "23","24","25","26","27","28","29","30","31"};
   jour = new JComboBox(jours);
        String [] but = {"Droit de marchés","Quantine scolaire ","Subvention de l'Etat","Service de scolarité","Location de salle","Autre"};
   mois = new JComboBox(but);
          String [] annees = {"1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001"
              ,"2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014"};
    annee = new JComboBox(annees);

   tailleLabel = new JLabel("selectionner le type : ");

matiere.add(tailleLabel);
matiere.add(mois);



JPanel lieu_nissanc = new JPanel();
                 lieu_nissanc.setPreferredSize(new Dimension(00, 00));

        
        
JPanel content = new JPanel();
content.add(panNom);
content.add(panSexe);
content.add(matiere);

content.add(lieu_nissanc);

content.add(matiere);
content.add(panTaille);






JPanel control = new JPanel();
control.setBackground(Color.darkGray);

JButton okBouton = new JButton("OK");
okBouton.addActionListener(new ActionListener(){
    
@Override
public void actionPerformed(ActionEvent arg0) {
   memoire_intendance.prix =nom.getText();
   memoire_intendance.periode=(String)sexe.getSelectedItem();
   memoire_intendance.but=(String)mois.getSelectedItem();
   memoire_intendance.desciption=taille.getText();
//final String salle_choisie = (String)cheveux.getSelectedItem() ;    

//nom.getText();
//lieu_naissance.getText();
// memoire_intendance.classe=salle_choisie;
// memoire_intendance.date_naissance=(String)(String)jour.getSelectedItem()+"/"+mois.getSelectedItem().toString()+"/"+annee.getSelectedItem().toString();
 //memoire.sexe=(String)sexe.getSelectedItem();
// memoire_intendance.telephone=getTaille();
zInfo = new Ajouteleveinfo(nom.getText(),
(String)sexe.getSelectedItem(),(String)mois.getSelectedItem(),
taille.getText());
setVisible(false);
} 
 public String getAge(){
            return (tranche1.isSelected()) ? tranche1.getText() :
            (tranche2.isSelected()) ? tranche2.getText() :
            (tranche3.isSelected()) ? tranche3.getText() :
            (tranche4.isSelected()) ? tranche4.getText() : tranche1.getText();
            } public String getTaille(){
            return (taille.getText().equals("")) ? "Vide !" : taille.getText();
        }
            });
JButton cancelBouton = new JButton("Annuler");
cancelBouton.addActionListener(new ActionListener(){
@Override
public void actionPerformed(ActionEvent arg0) {
setVisible(false);
}
});
control.add(okBouton);
control.add(cancelBouton);
this.getContentPane().add(panIcon, BorderLayout.WEST);
this.getContentPane().add(content, BorderLayout.CENTER);
this.getContentPane().add(control, BorderLayout.SOUTH);

}



}


