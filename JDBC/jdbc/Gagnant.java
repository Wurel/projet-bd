package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class Gagnant{

  public Gagnant(Connection con){
    try {
        Statement salles = con.createStatement();
      ResultSet rs = salles.executeQuery("select E.ID_VENTE, MAX(E.DATE_ENCHERE) from PRODUIT_SOUMIS_A_LA_VENTE PSV, PRODUIT P, ENCHERE E, ENCHERE_PROPOSEE EP WHERE PSV.ID_VENTE = EP.ID_VENTE AND PSV.ID_PRODUIT = P.ID_PRODUIT GROUP BY E.ID_VENTE");
      while (rs.next()) {
        // System.out.println(rs.getString("NOM_CATEGORIE") + " " + rs.getString("DESCRIPTION_CATEGORIE"));
        Timestamp dateTemp = rs.getTimestamp("MAX(E.DATE_ENCHERE)");
        // System.out.println(dateTemp);
        // Timestamp = Timestamp.valueOf(dateTemp.toString() + "00:00:00");//+ " " + dateTemp.getHours() +":"+ dateTemp.getMinutes() +":"+ dateTemp.getSeconds());
        // System.out.println(date);
        // System.out.println("SELECT EP.EMAIL_UTILISATEUR FROM ENCHERE E, ENCHERE_PROPOSEE EP WHERE E.DATE_ENCHERE ='" + dateTemp +"  AND EP.NUM_ENCHERE = E.NUM_ENCHERE");
        Statement gag = con.createStatement();
        ResultSet nvRs = gag.executeQuery("SELECT EP.EMAIL_UTILISATEUR FROM ENCHERE E, ENCHERE_PROPOSEE EP WHERE E.DATE_ENCHERE =TIMESTAMP '" + dateTemp +"' AND EP.NUM_ENCHERE = E.NUM_ENCHERE");
        while(nvRs.next()){
          System.out.println(nvRs.getString("EMAIL_UTILISATEUR"));
        }
      }
      System.out.println("Pour plus de renseignements sur le gagnant contactez nous!");
    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }
  }



}
