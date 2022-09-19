/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.login;
import paie.personnel.sha1;

/**
 * MySQL client
 *
 * @author Fobec 2010
 */
public class mysqli {

    private final String dbURL = "bestschool";
    private final String user = "root";
    private final String password = "";
    private java.sql.Connection dbConnect = null;
    private java.sql.Statement dbStatement = null;

    public Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.dbConnect = DriverManager.getConnection("jdbc:mysql://" + new login().getLink()+"/"+this.dbURL, this.user, this.password);
 
            this.dbStatement = this.dbConnect.createStatement();
            return dbConnect;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(mysqli.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbConnect;
    }

    public ResultSet exec(String sql) {
        try {
            ResultSet rs = this.dbStatement.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(mysqli.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void close() {
        try {
            this.dbStatement.close();
            this.dbConnect.close();
            this.dbConnect.close();
        } catch (SQLException ex) {
            Logger.getLogger(mysqli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String cle(){
        String result= new SecureRandom().ints(0,36).
                mapToObj(i->Integer.toString(i,36))
                .map(String::toUpperCase).distinct().limit(21).collect(Collectors.joining())
                .replaceAll("([A-Z0-9]{4})","$1-").substring(0,24);
        return result;
    } 
    public static void main(String[] args) throws NoSuchAlgorithmException {
       int i=0;

        while(i<10010){
            String cle;
                      mysqli mysqlCli = new mysqli();
   
         cle = mysqlCli.cle();
        String mail = "beni@mail.com", password = "aaaa";
        if (mysqlCli.connect() != null) {
            try {
                //String query = "select * from users where mail=? and Password=?";
                String query = "insert into cle (nom_cle,hash_cle)values(?,?)";
                PreparedStatement ps = mysqlCli.connect().prepareStatement(query);
                ps.setString(1, cle);
                ps.setString(2, new sha1().sha1(cle));

                int rs = ps.executeUpdate();
                                mysqlCli.close();

                System.out.println("entree: ===>"+i);
                //System.out.println("resultat: ===>"+rs);

              

            } catch (SQLException ex) {
                Logger.getLogger(mysqli.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Mysql connection failed !!!");
        }
        i++;
    }

}}
