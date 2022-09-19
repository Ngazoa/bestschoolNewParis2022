/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;
import surveillant_general.SurveillantG;

/**
 *
 * @author Junior ENAMA
 */
public class reactivvation_eleve {
    
    public void toutes_absences_eleves() throws IOException, JSONException{
            org.json.JSONArray jsonArray = null;
		String url = memoire.liste_reactivation_eleves;

             URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
               

        
              String urlParameters = "id_eleve=";
		
		
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

    		String inputLine,inline2 = null;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine.trim());
                    inline2 = inputLine.trim();
		System.out.println("Response Code : " + response.append(inputLine));

		}
		System.out.println("Response Code : " + response.append(inputLine));

            org.json.JSONObject bj = new JSONObject(inline2);
            try{
                 jsonArray = bj.getJSONArray("victoire");
            
             }catch(JSONException ex){
                                  JOptionPane.showConfirmDialog(null,"Désolé, mais il s'averait que cet etablissement ne possede pas d'eleves exclue ou desactive", "Activation des eleves ",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
              }
             List<model_eleves_pour_absences> liste_eleve = new ArrayList<model_eleves_pour_absences>(); 

int dureetotal=0;
String ojudui;       
         ojudui = new SimpleDateFormat ("EEEE, dd MMMM yyyy").format(new Date());                                  
         
                for(int i=0; i<jsonArray.length(); i++){
                        JSONObject s11 = jsonArray.getJSONObject(i);


                         String nom_eleve = s11.getString("nom");
                         String code = s11.getString("id_eleve");
                         String classe = s11.getString("nom_classe");
                         String note1_eleve = s11.getString("sexe");
                         String note2_eleve = s11.getString("date_naissance");                        
                         String etat = s11.getString("lieu_naissance");                        
                         
                         // dureetotal=dureetotal+Integer.parseInt(nom_eleve);
                         
                         
                         
                         liste_eleve.add(new model_eleves_pour_absences( code,nom_eleve,classe,note2_eleve,note1_eleve,etat));
                          System.out.println(nom_eleve+" "+memoire.choix_sequence);

                            }
     in.close();
     
       
                              final JFrame jInternalFrame1 = new JFrame("Liste des eleves desactives ou exclue");    
                               String[] columnNames = {" Nom et prenom ", " Classe ","Date de naissance" , " Sexe ","Code eleve","lieu naissance"};
                                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                         for( final model_eleves_pour_absences detail : liste_eleve) 
                           {
                            Vector<String> row = new Vector<>();
                             
                            row.add(detail.getUsername());
                            row.add(detail.getclasse());
                            row.add(detail.getnote());
                            row.add(detail.getnote2());
                            row.add(detail.id());
                            row.add(detail.djajustifie());
                //            row.add(detail.getnote2());
                             model.addRow( row );
                            }

                            JTable table = new JTable( model );
                             JScrollPane scrollPane = new JScrollPane( table );

                             JLabel lblHeading = new JLabel("  ");
                             
                            lblHeading.setFont(new Font("Roboto",Font.TRUETYPE_FONT,12));
                            
                            
                            JPanel control = new JPanel();
                    control.setBackground(Color.darkGray);

                    JButton okBouton = new JButton("OK");
                    okBouton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent arg0) {

                    } 

                                });
                    JButton cancelBouton = new JButton(" Quitter ");
                    cancelBouton.addActionListener(new ActionListener(){
                        
                    public void actionPerformed(ActionEvent arg0) {
                    
                        jInternalFrame1.dispose();
                       
                    }
                    
                    });

                  control.add(cancelBouton);
                       jInternalFrame1.getContentPane().add(control, BorderLayout.SOUTH);

                               //jInternalFrame1.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
                               jInternalFrame1.getContentPane().add(scrollPane,BorderLayout.CENTER);
                               jInternalFrame1.setSize(750, 500);
                               jInternalFrame1.setResizable(false);
                               jInternalFrame1.setLocationRelativeTo(null);
                            jInternalFrame1.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                                                  jInternalFrame1.setIconImage(new ImageIcon(memoire.setup).getImage());

                               
     table.addMouseListener(new MouseAdapter(){
   
       int x;String urObjctInCell,nom,conf;
         
                @Override
        public void mouseClicked( MouseEvent e){
                 if(e.getClickCount()==1){   
                         JTable t=(JTable)e.getSource();
                        x =t.getSelectedRow();
                        final int y=t.getSelectedColumn();
                     memoire.duree_cours=(String)t.getValueAt(x, 0);
                     memoire.id_absence=(String)t.getValueAt(x, 4);
                     memoire.jour_cours=(String)t.getValueAt(x, 2);
            JOptionPane OptionPane=new JOptionPane();  
          int   option = JOptionPane.showConfirmDialog(null, "Souhaitez - vous reactiver l'eleve : "+memoire.duree_cours, "Confirmation de reactivation"
                             , JOptionPane.INFORMATION_MESSAGE);
    
          if(option == JOptionPane.OK_OPTION)
                   {
                             try {
                                 modifier(memoire.id_absence);
                             } catch (IOException ex) {
                                 Logger.getLogger(reactivvation_eleve.class.getName()).log(Level.SEVERE, null, ex);
                             } catch (JSONException ex) {
                                 Logger.getLogger(reactivvation_eleve.class.getName()).log(Level.SEVERE, null, ex);
                             }
                      jInternalFrame1.dispose();
                   }                
               
                 }}});
                               jInternalFrame1.setVisible(true);

         
  
     }
    public void modifier(String ig) throws IOException, JSONException{
     String url = memoire.liste_reactivation_modifier;

             URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
               

        
              String urlParameters = "id_eleve="+ig;
		
		
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

    		String inputLine,inline2 = null;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine.trim());
                    inline2 = inputLine.trim();
		System.out.println("Response Code : " + response.append(inputLine));

		}
              
      JOptionPane.showConfirmDialog(null,"Succes", "Activation des eleves ",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
   
    }
}
