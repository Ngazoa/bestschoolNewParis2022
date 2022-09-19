/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Junior ENAMA
 */
public class definition_des_periodes_de_remplissages_notes extends JDialog {
    
public infoperiode zInfo = new infoperiode();
private JLabel nomLabel, sexeLabel, cheveuxLabel, ageLabel,mere,pere,
tailleLabel, taille2Label, icon;
private JRadioButton tranche1, tranche2, tranche3, tranche4;
private JComboBox sexe, cheveux,matieres, annee,mois,jour, annee1,mois1,jour1;
private JTextField nom, taille,lieu_naissance,nom_prenomp,nom_prenomM;
  String inline; 


public definition_des_periodes_de_remplissages_notes(JFrame parent, String title, boolean modal) throws IOException, JSONException{
super(parent, title, modal);

this.setSize(500, 260);
this.setLocationRelativeTo(null);
this.setResizable(false);
this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
this.initComponent();
} 

public infoperiode showZDialog() throws JSONException, IOException{
this.setVisible(true);
return this.zInfo;

}

private void initComponent() throws IOException, JSONException{
//Icone
icon = new JLabel(new ImageIcon(memoire.eleves));
icon.setPreferredSize(new Dimension(90, 190));
JPanel panIcon = new JPanel();
panIcon.setLayout(new BorderLayout());
panIcon.add(icon);
//Le nom
JPanel panNom = new JPanel();
panNom.setPreferredSize(new Dimension(220, 90));
nom = new JTextField(memoire.nom_conserve);
nom.setPreferredSize(new Dimension(150, 25));
panNom.setBorder(BorderFactory.createTitledBorder("Nom(s) et Prenom(s) "));
nomLabel = new JLabel("");
panNom.add(nomLabel);
panNom.add(nom);
//Le sexe
JPanel panSexe = new JPanel();
panSexe.setPreferredSize(new Dimension(220, 90));
panSexe.setBorder(BorderFactory.createTitledBorder("Sexe "));
sexe = new JComboBox();
sexe.addItem("M");
sexe.addItem("F");
sexeLabel = new JLabel("Sexe : ");
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
                  panTaille.setPreferredSize(new Dimension(240, 160));
                 panTaille.setBorder(BorderFactory.createTitledBorder("Contact des parents"));
tailleLabel = new JLabel("Téléphone :");
taille2Label =new JLabel("Nom Mere  :");
pere = new JLabel("Nom Pere  : ");
nom_prenomp = new JTextField("");
nom_prenomM = new JTextField("");
taille = new JTextField("");
taille.setPreferredSize(new Dimension(120, 25));
nom_prenomM.setPreferredSize(new Dimension(120, 25));
nom_prenomp.setPreferredSize(new Dimension(120, 25));

panTaille.add(pere);
panTaille.add(nom_prenomp);
panTaille.add(taille2Label);
panTaille.add(nom_prenomM);
panTaille.add(tailleLabel);
panTaille.add(taille);

//La couleur des cheveux
         JPanel panCheveux = new JPanel();
         panCheveux.setPreferredSize(new Dimension(220, 90));
         panCheveux.setBorder(BorderFactory.createTitledBorder("Classe actuelle"));

cheveuxLabel = new JLabel("Periode de remplissage");
String [] periode = {"Periode","Sequence 1","Sequence 2","Sequence 3","Sequence 4","Sequence 5","Sequence 6"};
cheveux = new JComboBox(periode);

panCheveux.add(cheveuxLabel);
panCheveux.add(cheveux);

JPanel matiere = new JPanel();
matiere.setPreferredSize(new Dimension(320, 100));
matiere.setBorder(BorderFactory.createTitledBorder("Date de debut et fin de remplissage des notes"));

String [] jours = {"Jour","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22",
    "23","24","25","26","27","28","29","30","31"};
String [] moiss = {"Mois","01","02","03","04","05","06","07","08","09","10","11","12"};
String [] annees = {"Annee","2019","2020","2021","2022","2023","2024","2025"};
jour = new JComboBox(jours);
mois = new JComboBox(moiss);
annee = new JComboBox(annees);
sexeLabel = new JLabel("Date de debut");
matiere.add(sexeLabel);
matiere.add(jour);
matiere.add(mois);
matiere.add(annee);

tailleLabel = new JLabel("Date de fin    ");
jour1 = new JComboBox(jours);
mois1 = new JComboBox(moiss);
annee1 = new JComboBox(annees);
matiere.add(tailleLabel);
matiere.add(jour1);
matiere.add(mois1);
matiere.add(annee1);
JPanel lieu_nissanc = new JPanel();
                 lieu_nissanc.setPreferredSize(new Dimension(00, 00));
                 lieu_nissanc.setBorder(BorderFactory.createTitledBorder("lieu de naissance"));

        
        
JPanel content = new JPanel();
content.add(panCheveux);

content.add(matiere);







JPanel control = new JPanel();
control.setBackground(Color.darkGray);

JButton okBouton = new JButton("OK");
okBouton.addActionListener(new ActionListener(){
    
@Override
public void actionPerformed(ActionEvent arg0) {
    if(jour.getSelectedItem().toString()!="Jour"&&mois.getSelectedItem().toString()!="Mois"&&annee.getSelectedItem().toString()!="Annee"
            &&jour1.getSelectedItem().toString()!="Jour"&&mois1.getSelectedItem().toString()!="Mois"&&annee1.getSelectedItem().toString()!="Annee"){
        
final String salle_choisie = (String)cheveux.getSelectedItem() ;    
if(!salle_choisie.equals("Periode")){
 memoire.nom=annee.getSelectedItem().toString()+"/"+mois.getSelectedItem().toString()+"/"+jour.getSelectedItem().toString();
 memoire.pere=nom_prenomp.getText();
 memoire.mere=nom_prenomM.getText();
// memoire.lieu_naissance=lieu_naissance.getText();
 memoire.classe=salle_choisie;
 memoire.date_naissance= jour.getSelectedItem() +"/"+mois.getSelectedItem().toString()+"/"+annee.getSelectedItem().toString();
 memoire.sexe=annee1.getSelectedItem().toString()+"/"+mois1.getSelectedItem().toString()+"/"+jour1.getSelectedItem().toString();
 memoire.telephone=cheveux.getSelectedItem().toString();
        String deb=new SimpleDateFormat("EEEE, dd MMMM yyyy").format(Date.parse(memoire.nom));
        String fin=new SimpleDateFormat("EEEE, dd MMMM yyyy").format(Date.parse(memoire.sexe));
                SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd");

    try {
        if(dateFormat.parse(memoire.nom).before(dateFormat.parse(memoire.sexe))){
            
            zInfo = new infoperiode(deb,
                    fin,cheveux.getSelectedItem().toString(),
                    salle_choisie ,getTaille(), jour.getSelectedItem() +" / "+mois.getSelectedItem().toString()+" / "+annee.getSelectedItem().toString());
            setVisible(false);
        }else{
            JOptionPane.showMessageDialog(null,"Veuillez vous rassurer que la date de fin de remplissage soit correcte \net inferieure a la date du de but","Erreur de definition des dates de remplissages",JOptionPane.ERROR_MESSAGE);
            
        }   
    
    } catch (ParseException ex) {
        Logger.getLogger(definition_des_periodes_de_remplissages_notes.class.getName()).log(Level.SEVERE, null, ex);
    }
    }else{
                              JOptionPane.showMessageDialog(null,"Veuillez vous rassurer du choix de la periode ","Erreur de definition de la periode concernee",JOptionPane.ERROR_MESSAGE);

    }
    }else{
                     JOptionPane.showMessageDialog(null,"Veuillez selectionner une date valide","Erreur de definition des dates de remplissages",JOptionPane.ERROR_MESSAGE);
     
    }
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


private void liste_classe() throws IOException, JSONException{
		String url = memoire.liste_classe;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		StringBuilder sb = new StringBuilder();
                            URL urln = new URL(memoire.liste_classe);
                            HttpURLConnection conn = (HttpURLConnection)urln.openConnection();
                             conn.setRequestMethod("GET"); 
                             conn.connect(); 
                            int responsecode = conn.getResponseCode(); 
        if(responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " +responsecode);

     else{
         Scanner sc = new Scanner(urln.openStream());

              while(sc.hasNext()){
                  inline = sc.nextLine().trim();
              }

            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
            String address = jsonArray.getJSONObject(0).getString("nom_classe");
            
                  List<liste_classe> liste_classe = new ArrayList<liste_classe>(); 
 //classe = new String[jsonArray.length()];
 cheveux = new JComboBox();

    for(int i=0; i<jsonArray.length(); i++){
            JSONObject eleve = jsonArray.getJSONObject(i);

            String nom_eleve = eleve.getString("nom_classe");
             cheveux.addItem(nom_eleve);

               
           sc.close();
       }
} 
}
}
