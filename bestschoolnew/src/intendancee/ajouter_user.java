/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;


import etablissement.memoire;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Junior ENAMA
 */
public class ajouter_user extends JDialog {
    
private infoPersonnel_administratif zInfo = new infoPersonnel_administratif();
private JLabel nomLabel, sexeLabel, cheveuxLabel, ageLabel,
tailleLabel, taille2Label, icon;
private JRadioButton tranche1, tranche2, tranche3, tranche4;
private JComboBox sexe, cheveux,matieres, annee,mois,jour;
private JTextField nom, taille,lieu_naissance;
  String inline; 


public ajouter_user(JFrame parent, String title, boolean modal) throws IOException, JSONException{
super(parent, title, modal);
liste_classe();
this.setSize(600, 400);
this.setLocationRelativeTo(null);
this.setResizable(false);
this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
this.initComponent();
} 

public infoPersonnel_administratif showZDialog() throws JSONException, IOException{
this.setVisible(true);
return this.zInfo;

}

private void initComponent() throws IOException, JSONException{
//Icone
//icon = new JLabel(new ImageIcone(memoire.eleves));
JPanel panIcon = new JPanel();
panIcon.setLayout(new BorderLayout());
//panIcon.add(icon);
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
panSexe.setBorder(BorderFactory.createTitledBorder("selectionner fonction personnel "));
sexe = new JComboBox();
sexe.addItem("censeur");
sexe.addItem("Intendant");
sexe.addItem("Surv. Génér.");
sexe.addItem("Secretaire");
sexe.addItem("Autre");
sexeLabel = new JLabel("Fonction : ");
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
                  panTaille.setPreferredSize(new Dimension(220, 90));
                 panTaille.setBorder(BorderFactory.createTitledBorder("Contact "));
tailleLabel = new JLabel("Téléphone : ");
taille2Label = new JLabel("");
taille = new JTextField("");
taille.setPreferredSize(new Dimension(90, 25));

panTaille.add(tailleLabel);
panTaille.add(taille);
panTaille.add(taille2Label);
//La couleur des cheveux
         JPanel panCheveux = new JPanel();
         panCheveux.setPreferredSize(new Dimension(220, 90));
         panCheveux.setBorder(BorderFactory.createTitledBorder("Classe actuelle"));

cheveuxLabel = new JLabel("Classe");
panCheveux.add(cheveuxLabel);
panCheveux.add(cheveux);

JPanel matiere = new JPanel();
matiere.setPreferredSize(new Dimension(450, 100));
matiere.setBorder(BorderFactory.createTitledBorder("Infos complementaire : "));

String [] jours = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22",
    "23","24","25","26","27","28","29","30","31"};
jour = new JComboBox(jours);
String [] moiss = {"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","Décembre"};
mois = new JComboBox(moiss);
String [] annees = {"1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001"
,"2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014"};
annee = new JComboBox(annees);
sexeLabel = new JLabel("");
matiere.add(sexeLabel);


tailleLabel = new JLabel("Infos supplementaires: ");
taille2Label = new JLabel("");
lieu_naissance= new JTextField(memoire.tel_conserve);
lieu_naissance.setPreferredSize(new Dimension(150, 25));
matiere.add(tailleLabel);
matiere.add(lieu_naissance);

JPanel lieu_nissanc = new JPanel();
                 lieu_nissanc.setPreferredSize(new Dimension(00, 00));
                 lieu_nissanc.setBorder(BorderFactory.createTitledBorder("lieu de naissance"));

        
        
JPanel content = new JPanel();
content.add(panNom);
content.add(panSexe);
content.add(matiere);

content.add(panTaille);
content.add(panCheveux);
content.add(matiere);
content.add(lieu_nissanc);






JPanel control = new JPanel();
control.setBackground(Color.darkGray);

JButton okBouton = new JButton("OK");
okBouton.addActionListener(new ActionListener(){
    
@Override
public void actionPerformed(ActionEvent arg0) {
    
final String salle_choisie = (String)cheveux.getSelectedItem() ;    

 memoire.nom=nom.getText();
 memoire.lieu_naissance=lieu_naissance.getText();
 memoire.classe=salle_choisie;
 memoire.date_naissance= jour.getSelectedItem() +"/"+mois.getSelectedItem().toString()+"/"+annee.getSelectedItem().toString();
 memoire.sexe=(String)sexe.getSelectedItem();
 memoire.telephone=getTaille();
zInfo = new infoPersonnel_administratif(nom.getText(),
(String)sexe.getSelectedItem(),lieu_naissance.getText(),
salle_choisie ,getTaille(), jour.getSelectedItem() +" / "+mois.getSelectedItem().toString()+" / "+annee.getSelectedItem().toString());
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

    

