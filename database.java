/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Megi
 */

public class database {
   public static Connection mycon(){
       Connection conn=null;
       try {
           Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://192.168.43.128/jaf","Admin","123");
           return conn;
           
       } catch (ClassNotFoundException | SQLException e) {
           JOptionPane.showMessageDialog(null, "Please check your connection");
           return conn;
       }
        
       
   }
    
}
