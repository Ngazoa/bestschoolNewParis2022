/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paie.personnel;

import etablissement.memoire;
import intendancee.*;
import intendancee.Intendancee;
import intendancee.memoire_intendance;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Junior ENAMA
 */
public class paie_personnel_enseignant extends JDialog {
private infopaie zInfo = new infopaie();
private JLabel nomLabel,coeff, sexeLabel, cheveuxLabel, ageLabel,
tailleLabel, taille2Label, icon;
private JRadioButton tranche1, tranche2, tranche3, tranche4;
private JComboBox sexe,matieres,coefficient,liste_des_classe,periode,cycle;
private JTextField nom, taille;
/**
* Constructeur
* @param parent
* @param title
* @param modal
     * @throws org.json.JSONException
     * @throws java.io.IOException
*/
public paie_personnel_enseignant(JFrame parent, String title, boolean modal) throws JSONException, IOException{
super(parent, title, modal);
liste_classe();
      

cycle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cyleItemStateChanged(evt);
                //matieres.setEnabled(true);
            }
        });
periode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                periodeItemStateChanged(evt);
                //matieres.setEnabled(true);
            }
        });

        
        
        this.setSize(600, 200);
this.setLocationRelativeTo(null);
this.setResizable(false);
this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
this.initComponent();
} 

public infopaie showZDialog(){
this.setVisible(true);
return this.zInfo;
}

private void initComponent() throws IOException, JSONException{
//Icone
icon = new JLabel(new ImageIcon("error.png"));
JPanel panIcon = new JPanel();
panIcon.setLayout(new BorderLayout());
panIcon.add(icon);
//Le nom
JPanel panNom = new JPanel();
panNom.setPreferredSize(new Dimension(220, 90));
nom = new JTextField();
nom.setText("");
nom.setPreferredSize(new Dimension(150, 35));
panNom.setBorder(BorderFactory.createTitledBorder("Nom(s) et Prenom(s)  personnel "));
nomLabel = new JLabel("");
panNom.add(nomLabel);
panNom.add(nom);
//Le sexe
JPanel panSexe = new JPanel();
panSexe.setPreferredSize(new Dimension(220, 90));
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
panAge.setBorder(BorderFactory.createTitledBorder("Cathegories enseignant"));
panAge.setPreferredSize(new Dimension(440,70));
tranche1 = new JRadioButton("Plet ou Pleg");
tranche1.setSelected(true);
tranche2 = new JRadioButton("Dipet ou Dipes");
tranche3 = new JRadioButton("Vacataire");
tranche4 = new JRadioButton("Autre");
ButtonGroup bg = new ButtonGroup();

panAge.add(tranche1);
panAge.add(tranche2);
panAge.add(tranche3);
panAge.add(tranche4);
//La taille
JPanel panTaille = new JPanel();
                  panTaille.setPreferredSize(new Dimension(220, 90));
                 panTaille.setBorder(BorderFactory.createTitledBorder("Montant à payer"));
tailleLabel = new JLabel("Montant en Chiffre (CFA) : ");
taille2Label = new JLabel("");


NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
decimalFormat.setGroupingUsed(true);


taille= new JFormattedTextField(decimalFormat);
taille.setColumns(10); 
taille.setPreferredSize(new Dimension(90, 25));

panTaille.add(tailleLabel);
panTaille.add(taille);
panTaille.add(taille2Label);
//La couleur des cheveux
         JPanel panCheveux = new JPanel();
         panCheveux.setPreferredSize(new Dimension(220, 90));
         panCheveux.setBorder(BorderFactory.createTitledBorder("Nom(s) et Prenom(s) du  personnel "));
         

//cheveuxLabel = new JLabel("Nom(s) et Prenom(s)  personnel ");
//panCheveux.add(cheveuxLabel);
panCheveux.add(liste_des_classe);

JPanel matiere = new JPanel();
matiere.setPreferredSize(new Dimension(130, 120));
matiere.setBorder(BorderFactory.createTitledBorder("Mois de paie "));

String [] jours = {"Janvier","Fevrier","Mars ","Avril","Mai ","Juin ","Juillet","Aout"
,"Septembre","Octobre","Novembre","Decembre"};String [] jrs = {""};

coefficient = new JComboBox(jours);
matieres =new JComboBox(jrs);
sexeLabel = new JLabel("Mois de paie: ");
coeff = new JLabel("Objet de paiement  : ");





//matiere.add(sexeLabel);
//matiere.add(matieres);
//.setEnabled(false);

//matiere.add(coeff);
matiere.add(coefficient);


//ulta important pour lyclee
//matiere.add(coefficient);



//matiere.add(periode);

JPanel content = new JPanel();
content.add(panCheveux);
content.add(matiere);

content.add(panTaille);






JPanel control = new JPanel();
control.setBackground(Color.darkGray);

JButton okBouton = new JButton("OK");
okBouton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent arg0) {
 
 final String salle_choisie = liste_des_classe.getSelectedItem().toString() ;    
  if(!liste_des_classe.getSelectedItem().toString().equals("Personnels diponibles")){
        memoire_intendance.prix=taille.getText();
        memoire_intendance.nom=(String)liste_des_classe.getSelectedItem();
        memoire_intendance.classe=coefficient.getSelectedItem().toString(); //classe ici est remplace en realite par le mois de paie du personnel concerne
        memoire_intendance.but="Paie mois de : "+coefficient.getSelectedItem().toString();

       zInfo = new infopaie( 

       memoire_intendance.nom,coefficient.getSelectedItem().toString(),memoire_intendance.but,
       memoire_intendance.prix);
       setVisible(false);

  }else{
                    JOptionPane.showMessageDialog(null,"Priere de selectionner le personnel a payer");
    
  }
} 

            });
JButton cancelBouton = new JButton("Annuler");
cancelBouton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent arg0) {
dispose();
}
});

control.add(okBouton);
control.add(cancelBouton);
this.getContentPane().add(panIcon, BorderLayout.WEST);
this.getContentPane().add(content, BorderLayout.CENTER);
this.getContentPane().add(control, BorderLayout.SOUTH);

}


    
   

private void cyleItemStateChanged(java.awt.event.ItemEvent evt) {                                              
    String val = cycle.getSelectedItem().toString();
		System.out.println("je travail actuellement sur : " + val);

if(val.equals("Entrer le cycle")){
                  JOptionPane.showMessageDialog(null,"Veuillez séléctionner le cycle");
   }else{
        try {memoire_intendance.classe=val;

            liste_frais();
            //matieres.setEnabled(true);

        } catch (IOException ex) {
            Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
//matieres.setEnabled(false);

  }private void periodeItemStateChanged(java.awt.event.ItemEvent evt) {                                              
    String val = periode.getSelectedItem().toString();

if(val.equals("Entrer le cycle")){
                  JOptionPane.showMessageDialog(null,"Veuillez séléctionner \nla periode ou la tanche a payer");
   }else{
        try {memoire_intendance.classe=val;

            liste_prix(val);
            //matieres.setEnabled(true);

        } catch (IOException ex) {
            Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Intendancee.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
//matieres.setEnabled(false);

  }

private void liste_classe() throws IOException, JSONException{
    String [] cy = {"Entrer le cycle","1 er Cycle","2 nd Cycle","3 eme Cyle","Autre"};
cycle =new JComboBox(cy);
periode =new JComboBox();
periode.setEnabled(false);

		String url = memoire.liste_personnels;
		String inline = null;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		StringBuilder sb = new StringBuilder();
                            URL urln = new URL(memoire.liste_personnels);
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
                		System.out.println(inline);

            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
            String address = jsonArray.getJSONObject(0).getString("nom");
            
 liste_des_classe = new JComboBox();
 String id;
 liste_des_classe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Personnels diponibles" }));

    for(int i=0; i<jsonArray.length(); i++){
            JSONObject eleve = jsonArray.getJSONObject(i);
             String nom_eleve = eleve.getString("nom");
    liste_des_classe.addItem(nom_eleve);		
    System.out.println(nom_eleve);
   
           sc.close();
       }
} 
}

private void liste_matieren() throws IOException, JSONException{
			String url = memoire.liste_eleves_classe;

             URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
               

        
              String urlParameters = "classe="+liste_des_classe.getSelectedItem();
		
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		 
         		System.out.println(urlParameters);

    		String inputLine,inline = null;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine.trim());
                    inline = inputLine.trim();
		System.out.println("Response Code : " + response.append(inputLine));

		}
                		System.out.println(inline);

            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
          
 String[] clas = new String[jsonArray.length()];
  matieres.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "          " }));
String id=null;
    for(int i=0; i<jsonArray.length(); i++){
            JSONObject eleve = jsonArray.getJSONObject(i);
            String nom_eleve = eleve.getString("nom");
            id=eleve.getString("id_eleve");
    matieres.addItem(nom_eleve+" ;"+id);

       }

         in.close();

} 

private void liste_frais() throws IOException, JSONException{
			String url = memoire.liste_cycle;

             URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
               

        
              String urlParameters = "classe="+cycle.getSelectedItem().toString();
		
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		 
         		System.out.println(urlParameters);

    		String inputLine,inline = null;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine.trim());
                    inline = inputLine.trim();
		System.out.println("Response Code : " + response.append(inputLine));

		}
                		System.out.println(inline);
try{
            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
          
 String[] clas = new String[jsonArray.length()];
    periode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "periode" }));

    for(int i=0; i<jsonArray.length(); i++){
            JSONObject eleve = jsonArray.getJSONObject(i);
            String nom_eleve = eleve.getString("periode");
             //matieres.addItem(nom_eleve);
    periode.addItem(nom_eleve);
periode.setEnabled(true);
       }
}catch(Exception e){
  
     showConfirmDialog(null, "les modalites pour ce cycle \nne sont pas encore definies","Erreur inattendue",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
     } 
         in.close();

}private void liste_prix(String periode) throws IOException, JSONException{
			String url = memoire.liste_prix;

             URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
               

        
              String urlParameters = "cycle="+cycle.getSelectedItem()+"&periode="+periode;
		
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		 
         		System.out.println(urlParameters);

    		String inputLine,inline = null;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine.trim());
                    inline = inputLine.trim();
		System.out.println("Response Code : " + response.append(inputLine));

		}
                		System.out.println(inline);

            org.json.JSONObject bj = new JSONObject(inline);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
          
  String nom_eleve=null;
    for(int i=0; i<jsonArray.length(); i++){
            JSONObject eleve = jsonArray.getJSONObject(i);
             nom_eleve= eleve.getString("prix");
             //matieres.addItem(nom_eleve);
       }
taille.setText(nom_eleve);
  taille.setEditable(false);

         in.close();

}
}
