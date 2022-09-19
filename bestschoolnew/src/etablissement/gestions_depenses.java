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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Michelle Wafo
 */
public class gestions_depenses  extends JDialog {

        private blame_information zInfo = new blame_information();
        private JLabel nomLabel, sexeLabel, cheveuxLabel, ageLabel,
        tailleLabel, taille2Label, icon;
        private JRadioButton tranche1, tranche2, tranche3, tranche4;
        private JComboBox sexe, cheveux,matieres, annee,mois,jour ,personnel;
        private JTextField nom,lieu_naissance;
         JTextArea  taille;


        public gestions_depenses(JFrame parent, String title, boolean modal){
        super(parent, title, modal);
        this.setSize(400, 360);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
        } 

        public blame_information blame_information(){
        this.setVisible(true);
        return this.zInfo;
        }

        private void initComponent(){
        //Icone
        icon = new JLabel(new ImageIcon("error.png"));
        JPanel panIcon = new JPanel();
        //panIcon.setBackground(Color.white);
        panIcon.setLayout(new BorderLayout());
        panIcon.add(icon);
        
        
        
        
        
        
         JPanel panCheveux = new JPanel();
         panCheveux.setPreferredSize(new Dimension(220, 90));
         panCheveux.setBorder(BorderFactory.createTitledBorder("Classes concernées"));
         

      cheveuxLabel = new JLabel("Message destinné à : ");
      panCheveux.add(cheveuxLabel);
        //Le nom
        JPanel panNom = new JPanel();
        //panNom.setBackground(Color.white);
        panNom.setPreferredSize(new Dimension(220, 90));
        nom = new JTextField();
        nom.setPreferredSize(new Dimension(150, 35));
        panNom.setBorder(BorderFactory.createTitledBorder("Motif du blame "));
        nomLabel = new JLabel("Motif :");
        panNom.add(nomLabel);
        panNom.add(nom);
        //Le sexe
        JPanel panSexe = new JPanel();
        panSexe.setBackground(Color.white);
        panSexe.setPreferredSize(new Dimension(320, 90));
        panSexe.setBorder(BorderFactory.createTitledBorder("Sexe "));
        sexe = new JComboBox();
        sexe.addItem("Masculin");
        sexe.addItem("Féminin");
        sexe.addItem("Indéterminé");
        sexeLabel = new JLabel("Sexe : ");
        panSexe.add(sexeLabel);
        panSexe.add(sexe);
        //L'âge
        JPanel panAge = new JPanel();
        panAge.setBackground(Color.white);
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
                         // panTaille.setBackground(Color.white);
                          panTaille.setPreferredSize(new Dimension(320, 165));
                         panTaille.setBorder(BorderFactory.createTitledBorder("Description complete du blame"));
        tailleLabel = new JLabel("Description: ");
        taille2Label = new JLabel("");
        taille = new JTextArea();
        taille.setPreferredSize(new Dimension(300,100));

        panTaille.add(tailleLabel);
        panTaille.add(taille);
        panTaille.add(taille2Label);
        //La couleur des cheveux
                 JPanel panCheveu = new JPanel();
                 panCheveux.setBackground(Color.white);
                 panCheveux.setPreferredSize(new Dimension(220, 90));
                 panCheveux.setBorder(BorderFactory.createTitledBorder("Classe actuelle"));
        cheveux = new JComboBox();
        cheveux.addItem("Blond");
        cheveux.addItem("Brun");
        cheveux.addItem("Roux");
        cheveux.addItem("Blanc");
        cheveuxLabel = new JLabel("Classe");
        panCheveux.add(cheveuxLabel);
        panCheveux.add(cheveux);

        JPanel matiere = new JPanel();
        matiere.setBackground(Color.white);
        matiere.setPreferredSize(new Dimension(450, 100));
        matiere.setBorder(BorderFactory.createTitledBorder("Coordonnees de naissance"));

        String [] jours = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22",
            "23","24","25","26","27","28","29","30","31"};
        jour = new JComboBox(jours);
        String [] moiss = {"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","Décembre"};
        mois = new JComboBox(moiss);
        String [] annees = {"1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001"
        ,"2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014"};
        annee = new JComboBox(annees);
        sexeLabel = new JLabel("date de naissance : ");
        matiere.add(sexeLabel);
        matiere.add(jour);
        matiere.add(mois);
        matiere.add(annee);

        tailleLabel = new JLabel("Lieu de naissance : ");
        taille2Label = new JLabel("");
        lieu_naissance= new JTextField("");
        lieu_naissance.setPreferredSize(new Dimension(150, 25));
        matiere.add(tailleLabel);
        matiere.add(lieu_naissance);

        JPanel lieu_nissanc = new JPanel();
                          lieu_nissanc.setBackground(Color.white);
                         lieu_nissanc.setPreferredSize(new Dimension(00, 00));
                         lieu_nissanc.setBorder(BorderFactory.createTitledBorder("lieu de naissance"));



        JPanel content = new JPanel();
        //content.setBackground(Color.white);
        content.add(panNom);
        //content.add(panSexe);
        //content.add(matiere);

        content.add(panTaille);
        //content.add(panCheveux);
        //content.add(matiere);
        content.add(lieu_nissanc);






        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");
        control.setBackground(Color.darkGray);

        okBouton.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent arg0) {
         memoire.motif=nom.getText();
         memoire.lieu_naissance=lieu_naissance.getText();
         memoire.classe=(String)cheveux.getSelectedItem();
         memoire.date_naissance= jour.getSelectedItem() +"/"+mois.getSelectedItem().toString()+"/"+annee.getSelectedItem().toString();
         memoire.sexe=(String)sexe.getSelectedItem();
         memoire.description=getTaille();
        zInfo = new blame_information(nom.getText(),
        (String)sexe.getSelectedItem(),lieu_naissance.getText(),
        (String)cheveux.getSelectedItem() ,getTaille(), jour.getSelectedItem() +"/"+mois.getSelectedItem().toString()+"/"+annee.getSelectedItem().toString());
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
		String inline = null;
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
            
                  List<liste_classe> liste_classe = new ArrayList<liste_classe>(); 
 //classe = new String[jsonArray.length()];
 personnel = new JComboBox();

    for(int i=0; i<jsonArray.length(); i++){
            JSONObject eleve = jsonArray.getJSONObject(i);
            String nom_eleve = eleve.getString("nom");
             personnel.addItem(nom_eleve);

                		System.out.println(nom_eleve);
   
           sc.close();
       }

} 
}
        
        
        
        }

