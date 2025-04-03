/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author USER
 */

// Klasa za ucitavanje elemenata iz db.properties fajla

public class DBProperties {
    
    Properties properties;
    
    public DBProperties() throws FileNotFoundException, IOException {
        properties = new Properties();
        properties.load(new FileInputStream("db.properties"));//ucitava vrednosti iz fajla u properties objekat
        
    }

   public String getDBURL() {
       return properties.getProperty(DBKonstante.URL);//vraca ucitanu vrednost ya url
   }
      
    
     public String getDBUser() {
       return properties.getProperty(DBKonstante.USERNAME);
   }
      
      
     
      public String getDBPassword() {
       return properties.getProperty(DBKonstante.PASSWORD);
   }
      
      //public void setDBPassword(String password) {
      //     properties.setProperty(DBKonstante.PASSWORD,password);
      //}
      
      
      public String getDBPort() {
          return properties.getProperty(DBKonstante.PORT);
      }
   
      
}

