/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intendancee;

/**
 *
 * @author Michelle Wafo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnector {
   //JDBC driver name and database URL
    private final String JDBC_DRIVER;  
    private final String DB_URL;
    //Database credentials
    PreparedStatement pst = null;
    private final String USER;
    private final String PASS;
    private Connection conn;
    Statement stmt;
    public  MySQLConnector(){
        
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://localhost/ecole?useUnicode=yes&characterEncoding=UTF-8";
        USER = "root";
        PASS = "";
        conn = null;
    }
    public void openConnection(){
    try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);  
               conn = DriverManager.getConnection(DB_URL, USER, PASS);
               System.out.println("La connexion  pour la bd est un : Success!");  
               stmt = conn.createStatement();
           
               String sqlInsert = "insert into user (id_user, nom, password) "
               + "values (3004, 'Fishing 101', 'Kumar')";
               System.out.println("The SQL query is: " + sqlInsert);  // Echo for debugging
               int countInserted = stmt.executeUpdate(sqlInsert);
               System.out.println(countInserted + " Insertion reussie.\n");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Victoire et erreur!"+e);     

        }
    } 
        
      public ArrayList logincorrect(String nom,String pass) throws ClassNotFoundException, SQLException {
      
           Class.forName(JDBC_DRIVER);  
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("La connexion  pour la bd est un : Success!");  
           stmt = conn.createStatement();
           
            String strSelect = "select * from user where nom = ? and password = ? ";
            pst=conn.prepareStatement(strSelect);
            pst.setString(1, nom);
            pst.setString(2, pass);
            ResultSet rset=pst.executeQuery();
            System.out.println("The SQL query is: " + strSelect);  
        if (!rset.next())
     {
            
            System.out.println("c'est vide tara: ");  
          return null;
      }else{
             
             List al = new ArrayList();
             ArrayList all = new ArrayList();
             while(rset.next()) {       
                        al.add(rset.getString("nom"));
                        al.add(rset.getString("poste") );
                         al.add( rset.getString("password"));
                        al.add(rset.getString("id_user"));
                        
                         System.out.println(rset.getInt("id_user") + ", " 
                         + rset.getString("nom") + ", "
                         + rset.getString("poste") + ", "
                         + rset.getString("password"));

             }
             all.add(al);
          return  all;

        }

        }
  
    public void closeConnection(){
        try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        System.out.println("Connection closed");
    }
    public Connection getConnection(){
        return conn;
    }
}