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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import org.json.JSONObject;

public class fonctions extends javax.swing.JFrame {

    JSONObject JSONObject = new JSONObject();
//    JSONParser parser = new JSONParser();
    Boolean response_correcte = false;

    private final String USER_AGENT = "Mozilla/5.0";
    BufferedReader rd;
    OutputStreamWriter wr;

    public static void main(String[] args) throws Exception {

        fonctions http = new fonctions();

        System.out.println("Testing 1 - Send Http GET request");
        //http.sendGet(String nom,String pass);

        System.out.println("\nTesting 2 - Send Http POST request");

    }

    public String loginn(String nom, String pass, String identifiant) throws Exception {

        StringBuilder sb = new StringBuilder();
        URL urln = new URL(memoire.login + "?login=" + nom + "&password=" + pass + "&cle=" + identifiant);
        HttpURLConnection conn = (HttpURLConnection) urln.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
            String inline = null;

            Scanner sc = new Scanner(urln.openStream());
            if (sc.hasNext()) {

                while (sc.hasNext()) {

                    inline = sc.nextLine();
                    System.out.println(inline);

                }

                org.json.JSONObject bj = new JSONObject(inline);
                org.json.JSONArray jsonArray = bj.getJSONArray("perso");
                String nom_user = jsonArray.getJSONObject(0).getString("nom");
                String id = jsonArray.getJSONObject(0).getString("id");
                return inline;
            } else {
                return null;
            }
        }
    }

//	public void sendGet(String nom,String pass,String identifiant) throws Exception {
//
//		String url = memoire.login+"?login="+nom+"&password="+pass+"&cle="+identifiant;
//		
//		URL obj = new URL(url);
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//
//
//try
//{		StringBuilder sb = new StringBuilder();
//    URL urln = new URL(memoire.login+"?login="+nom+"&password="+pass+"&cle="+identifiant);
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
//     List<model_eleves> liste_eleve = new ArrayList<model_eleves>(); 
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
//for( final model_eleves detail : liste_eleve) 
//{
//    Vector<String> row = new Vector<String>();
//    row.add(detail.getUsername());
//    row.add(detail.getclasse());
//    row.add(detail.getnote());
//    row.add(detail.getnote2());
//    model.addRow( row );
//}
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
//                
//                
//         
//        con.setConnectTimeout(150000);
////		int responseCode = con.getResponseCode();
////		System.out.println("\nSending 'GET' request to URL : " + url);
////		System.out.println("Response Code : " + responseCode);
////
////                StringBuffer response;
////        
////                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
////                String inputLine;
////                    String data = "";  
////                StringBuilder builder = new StringBuilder();
////                response = new StringBuffer();
////                    List al = new ArrayList();
////                    
////                    while ((inputLine = in.readLine()) != null) {
////	            response.append(inputLine.trim());}
////                    Reader reader= new StringReader(response.toString());
////                                            System.out.println(response );
////
//////	        JSONObject j;
//////        j = new JSONObject(JSONObject);
//////             parser.parse(reader);
//////	        // get Array type
//////	        org.json.JSONArray results = j.getJSONArray("jesus");
//////	        // under results, get string type
//////	        String jobtitle = results.getJSONObject(0).getString("nom");
//////	        // under results, get double type
//////	        double lat = results.getJSONObject(0).getDouble("id");
//////	        double lon = results.getJSONObject(0).getDouble("password");                       
//////                      
//////                 System.out.println(jobtitle+" coordinates: "+ lat + " , "+ lon);
////		
////                        al.add(inputLine);
////                        System.out.println(inputLine.toString() );
////                    
//////                    }
//////               data = builder.toString();
//////                   JSONObject jsonObject = JSONObject.fromObject(data);
////           
////            
//
//		//print result
//
//	}
//        
//        
    // HTTP POST request
    public boolean sendPost(String nom, String pass, String identifiant) throws Exception {

        String url = memoire.login;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = "login=" + nom + "&password=" + pass + "&cle=" + identifiant;
        try {
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);

            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
        } catch (IOException ex) {
            int option = showConfirmDialog(null, "Impossible de joindre le serveur distant ....Bien vouloir vérifier la nouvelle adresse ip", "Erreur de connexion",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        System.out.println("\nSending 'POST' request to URL : " + url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine, inline2 = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }

        org.json.JSONObject bj = new JSONObject(inline2);

        //BufferedReader in = new BufferedReader(
        //        new InputStreamReader(con.getInputStream()));
        System.out.println(inline2);

        //org.json.JSONObject bj = new JSONObject(inline2);
        org.json.JSONObject jsonArray = bj.getJSONObject("personne");
        org.json.JSONObject erreur = bj.getJSONObject("connecter");
        String ereur = erreur.getString("erreur");
        System.out.println(ereur);

        if (ereur.equals("non")) {
            String password = jsonArray.getString("password");

            PasswordAuthentication f = new PasswordAuthentication();

            Boolean decode;
            decode = f.authenticat(pass, password);

            System.out.println("Confirmer : " + password);

            if (decode.equals(true)) {
                //String nom_user = jsonArray.getString("nom");
                memoire.id_user = jsonArray.getString("id_user");
                memoire.nom_user = jsonArray.getString("nom");

                System.out.println("Fonction " + memoire.nom_user);

                response_correcte = true;
            }
        } else {
            response_correcte = false;

        }

        return response_correcte;

    }
}
