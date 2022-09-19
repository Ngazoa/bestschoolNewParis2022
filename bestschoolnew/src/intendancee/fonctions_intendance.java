/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

import etablissement.memoire;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;
import static javax.swing.JOptionPane.showConfirmDialog;
import org.json.JSONException;




public class fonctions_intendance extends javax.swing.JFrame {
    JSONObject JSONObject = new JSONObject();

    Boolean response_correcte = false;
org.json.JSONObject bj;
	private final String USER_AGENT = "Mozilla/5.0";
        BufferedReader rd;
        OutputStreamWriter wr;

        
	public static void main(String[] args) throws Exception {
                
  
		fonctions_intendance http = new fonctions_intendance();

		System.out.println("Testing 1 - Send Http GET request");
		//http.sendGet(String nom,String pass);
		
		System.out.println("\nTesting 2 - Send Http POST request");

	}



        
        
        
        public String loginn(String nom,String pass,String identifiant) throws Exception {

		


                      StringBuilder sb = new StringBuilder();
                    URL urln = new URL(memoire_intendance.login+"?login="+nom+"&password="+pass+"&cle="+identifiant);
                    HttpURLConnection conn = (HttpURLConnection)urln.openConnection();
                     conn.setRequestMethod("GET"); 
                     conn.connect(); 
                    int responsecode = conn.getResponseCode(); 
                   if(responsecode != 200)throw new RuntimeException("HttpResponseCode: " +responsecode);

                  else

                 {
                     String inline=null ;

                      Scanner sc = new Scanner(urln.openStream());
                        if (sc.hasNext()){

                               while(sc.hasNext())

                                    {

                                     inline = sc.nextLine();
                                     System.out.println(inline );

                                    }

                                   org.json.JSONObject bj = new JSONObject(inline);
                                    org.json.JSONArray jsonArray = bj.getJSONArray("perso");
                                   String nom_user = jsonArray.getJSONObject(0).getString("nom");
                                   String id = jsonArray.getJSONObject(0).getString("id");
                                 return inline;
                          }else{
                           return null; 
                        }
                }
                    }
              
                
                
              

	
        
        
   

//	public void sendGet(String nom,String pass,String identifiant) throws Exception {
//
//		String url = memoire_intendance.login+"?login="+nom+"&password="+pass+"&cle="+identifiant;
//		
//		URL obj = new URL(url);
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//
//
//try
//{		StringBuilder sb = new StringBuilder();
//    URL urln = new URL(memoire_intendance.login+"?login="+nom+"&password="+pass+"&cle="+identifiant);
//    HttpURLConnection conn = (HttpURLConnection)urln.openConnection();
//     conn.setRequestMethod("GET"); 
//     conn.connect(); 
//    int responsecode = conn.getResponseCode(); 
//if(responsecode != 200)
//
//throw new RuntimeException("HttpResponseCode: " +responsecode);
//
//else
//
//{
//      String inline=null ;
//
//Scanner sc = new Scanner(urln.openStream());
//
//while(sc.hasNext())
//
//{
//
// inline = sc.nextLine().trim();
//
//}
//
//            org.json.JSONObject bj = new JSONObject(inline);
////Store the JSON object in JSON array as objects (For level 1 array element i.e Results)
//            org.json.JSONArray jsonArray = bj.getJSONArray("jesus");
//            String address = jsonArray.getJSONObject(0).getString("nom");
////JSONArray jsonarr_1 = (JSONArray) jobj.get("jesus");
//
////Get data for Results array
//
//   //  List<model_eleves> liste_eleve = new ArrayList<model_eleves>(); 
//
//
//    for(int i=0; i<jsonArray.length(); i++){
//   JSONObject eleve = jsonArray.getJSONObject(i);
//
//    String nom_eleve = eleve.getString("nom");
//    String classe = eleve.getString("password");
//    String note1_eleve = eleve.getString("nom");
//    String note2_eleve = eleve.getString("nom");
////    liste_eleve.add(new model_eleves( nom_eleve,classe,note1_eleve,note2_eleve));
//     System.out.println(nom_eleve+" "+classe);
//
//    }
//
//
//    
//                    
//System.out.println("\nJSON data in string format"+address);
//
//
//sc.close();
//
//
//        
//        String[] columnNames = { "NOM ET PRENOMS", "CLASSE","NOTE1", "NOTE2" };
//DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//
//
//
//JTable table = new JTable( model );
//JScrollPane scrollPane = new JScrollPane( table );
// System.out.println(table);
//
//}
//   
//       
//
//}
//catch (Exception e) {
//        System.out.println(e.toString());
//    }           
//                
//    con.setConnectTimeout(150000);
//
//	}
//        
        
	
	// HTTP POST request
	public boolean sendPost (String nom,String pass,String identifiant) throws Exception {
		/*String url = memoire_intendance.login;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "login="+nom+"&password="+pass;
		
		// Send post request
		con.setDoOutput(true);
        try (DataOutputStream url_params = new DataOutputStream(con.getOutputStream())) {
            url_params.writeBytes(urlParameters);
            url_params.flush();
        }catch(IOException ex){
            int option = showConfirmDialog(null, "Impossible de joindre le serveur distant ....Bien vouloir vérifier la nouvelle adresse ip", "Erreur de connexion",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
        }

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);*/
                	String url = memoire_intendance.login;

             URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
               String urlParameters = "login="+nom+"&password="+pass;
try{
               con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
              wr.writeBytes(urlParameters);

		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
                 }catch(IOException ex){
            int option = showConfirmDialog(null, "Impossible de joindre le serveur distant ....\nBien vouloir vérifier la nouvelle adresse ip", "Erreur de connexion",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
        }
		System.out.println("\nSending 'POST' request to URL : " + url);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		 

    		String inputLine,inline2 = null;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		 response.append(inputLine.trim());
                    inline2 = inputLine.trim();
		System.out.println("Response Code : " + response.append(inputLine));

		}

            try{
                bj = new JSONObject(inline2);
               }catch(Exception ex){
                    int option = showConfirmDialog(null, "Erreur de configuration du serveur....", "Erreur de connexion",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
               }

		//BufferedReader in = new BufferedReader(
		//        new InputStreamReader(con.getInputStream()));
		 
		System.out.println(inline2);

                     //org.json.JSONObject bj = new JSONObject(inline2);
                        org.json.JSONObject jsonArray = bj.getJSONObject("personne");
                        org.json.JSONObject erreur = bj.getJSONObject("connecter");
                        String ereur = erreur.getString("erreur");
                                            System.out.println(ereur );

                                        
                                             
                                           String user = null;

                                        if (ereur.equals("non")){
                                            String password = jsonArray.getString("password");

                                             PasswordAuthentication f = new PasswordAuthentication();

                                             Boolean decode;
                                             decode = f.authenticat(pass,password);
                                             
        		                     System.out.println("Confirmer : " + password);

                                            if(decode.equals(true)){
                                            memoire_intendance.id_user = jsonArray.getString("id_user");
                                             user = jsonArray.getString("fonction");
                                            if(identifiant.equalsIgnoreCase(user)){
                                              memoire_intendance.fonction =user;
                                              response_correcte= true;  

                                            }
                                            memoire_intendance.nom_user=jsonArray.getString("nom");
                                            memoire.nom_user=jsonArray.getString("nom");
                                            memoire.id_user=jsonArray.getString("id_user");

                                            System.out.println("Fonction "+user.toLowerCase()+" "+" f2"+identifiant.toLowerCase()+" f3 "+ memoire_intendance.fonction+" "+memoire_intendance.id_user );
                                            
                                            }
                                        }else{                   
                                            response_correcte= false;
                                        

                                        }

            return response_correcte;

}
         
        public void lire() throws IOException, JSONException{
         
    		//System.out.println("MOT de passe hashe : " + pass);
   
             String url = memoire.config;

        URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        
              String urlParameters = "password=";
		
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
                org.json.JSONObject bj = new JSONObject(test);

                        org.json.JSONObject jsonArray = bj.getJSONObject("personne");
                         memoire.ecole = jsonArray.getString("personne");
                         memoire.type= jsonArray.getString("type_ecole");
                         memoire.telephone_ecole = jsonArray.getString("telephone");
                                          System.out.println("Type etablissement : "+memoire.type );
                                   
 }
}
