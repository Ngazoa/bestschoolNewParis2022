/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Junior ENAMA
 */
public class cle_key {
  public void cle() throws IOException{
      
      String mo;     JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
            
              mo = JOptionPane.showInputDialog(null, "Veuillez Entrer le code", "Entrer votre code", JOptionPane.QUESTION_MESSAGE);
             if(!mo.equals("")&& mo.length()>4){
                 ajouter(mo);
    
              }else{
             showConfirmDialog(null, "erreur de code", "Erreur de code",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE); 
             cle();
             }
  }
 private void ajouter(String code) throws IOException{
         

        PasswordAuthentication f= new PasswordAuthentication();

            String pass = f.hash(code);
    		//System.out.println("MOT de passe hashe : " + pass);
   
             String url = memoire.ajout_cle;

        URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        
              String urlParameters = "password="+pass;
		
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


		String inputLine,test = null;
		StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        test = inputLine.trim();

        System.out.println(inputLine);
 
    }
     // showMessageDialog(null, "Action reussie avec succes le code est : "+pass);
      cle();
 }  
}
