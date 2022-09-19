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
import java.util.ArrayList;

/**
 *
 * @author Junior ENAMA
 */
public class enregistrer_moyenne_eleves {
 public void   enregistrer_moyenne_eleves (ArrayList<String> code,
 ArrayList<String> moyenne,ArrayList<String> rang,String classe,String periode) throws IOException{
    String url = memoire.enregistrer_notes;

               URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		//on.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
               
            String urlParameters ="classe="+classe+"&code="+code+
           "&periode="+periode+"&moyenne="+moyenne+"&rang="+rang; 
		con.setDoOutput(true);
              
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
        	
        con.setConnectTimeout(250000);

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
		System.out.println("Response Code : " + response.append(inline2));
                reini();
                }
 }
 private void reini(){
  
 }
}
