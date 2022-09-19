/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Benito
 */
public class justification_absence extends JDialog {
private final ZDialogInfo zInfo = new ZDialogInfo();
private JLabel nomLabel,coeff, sexeLabel, dureeLabel, jourLabel,
tailleLabel, taille2Label, icon;
private JRadioButton tranche1, tranche2, tranche3, tranche4;
private JComboBox sexe,matieres,coefficient,liste_des_classe;
private JTextField nom;
      JTextArea  taille;
String[] classe;
/**
* Constructeur
* @param parent
* @param title
* @param modal
     * @throws org.json.JSONException
     * @throws java.io.IOException
*/
public justification_absence(JFrame parent, String title, boolean modal) throws JSONException, IOException{
super(parent, title, modal);

this.setSize(400, 300);
this.setLocationRelativeTo(null);
this.setResizable(false);
this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
this.initComponent();
} 

public ZDialogInfo showZDialog(){
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
panNom.setBorder(BorderFactory.createTitledBorder("Jour"));
nomLabel = new JLabel(memoire.jour_cours);
panNom.add(nomLabel);
//Le temps
JPanel panSexe = new JPanel();
panSexe.setBorder(BorderFactory.createTitledBorder("duree  en heure"));
jourLabel = new JLabel(memoire.duree_cours);
panSexe.setPreferredSize(new Dimension(130, 80));

panSexe.add(jourLabel);


//La taille
JPanel panTaille = new JPanel();
                  //panTaille.setPreferredSize(new Dimension(120, 150));
                 panTaille.setBorder(BorderFactory.createTitledBorder("Motif pour justfication*"));
tailleLabel = new JLabel("Motif: ");
taille2Label = new JLabel("");
taille = new JTextArea("  ");
panTaille.setPreferredSize(new Dimension(396, 160));

panTaille.add(tailleLabel);
panTaille.add(taille);
panTaille.add(taille2Label);

JPanel content = new JPanel();
content.add(panNom);
content.add(panSexe);
content.add(panTaille);






JPanel control = new JPanel();
control.setBackground(Color.darkGray);

JButton okBouton = new JButton("OK");
okBouton.addActionListener(new ActionListener(){
@Override
public void actionPerformed(ActionEvent arg0) {
 
    try { 
        if(taille.getText().trim().length()>10){
        justifionsAbsence();
        memoire.etatactuel="oui";
        }else
        {                             
           memoire.etatactuel="non"; 
            JOptionPane.showMessageDialog(null, "Vous ne pouvez justifier cette absence car le motif s'avérerai \ntrop court ou  pas assez détaillé","Erreur survenue",JOptionPane.ERROR_MESSAGE);

        }
    } catch (IOException ex) {
        Logger.getLogger(justification_absence.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JSONException ex) {
        Logger.getLogger(justification_absence.class.getName()).log(Level.SEVERE, null, ex);
    }
setVisible(false);
} 

            });
JButton cancelBouton = new JButton("Annuler");
cancelBouton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent arg0) {
 memoire.etatactuel="non";

dispose();
}
});

control.add(okBouton);
control.add(cancelBouton);
this.getContentPane().add(panIcon, BorderLayout.WEST);
this.getContentPane().add(content, BorderLayout.CENTER);
this.getContentPane().add(control, BorderLayout.SOUTH);


}
    private void justifionsAbsence() throws IOException, JSONException{
			String url = memoire.justifions_absences;

             URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
       
              String urlParameters = "id_absence="+memoire.id_absence+"&duree="
                      +memoire.duree_cours+"&id_eleve="+memoire.id_eleve+"&modificateur="+memoire_intendance.id_user
                      +"&motif="+taille.getText();
		
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\n Sending 'POST' request to URL : " + url);
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

       

         in.close();

} 
}
