package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class EntreeSalle{
  private Connection con;

  public EntreeSalle(Connection con, String emailUtilisateur){
    try {
      Scanner sc = new Scanner(System.in);

      System.out.println("Souhaitez vous voir tous les salles d'encheres (oui/non)");
      String reponse = sc.nextLine();
      if (reponse.equals("oui")) {
        Statement salles = con.createStatement();
        ResultSet rs = salles.executeQuery("SELECT * FROM SALLE");
        while (rs.next()) {
          System.out.println("Id Salle : " + rs.getInt("id_salle") + " Categorie : " + rs.getString("nom_categorie"));
        }
      }
      boolean salleExiste = new Boolean(false);
      int numSalle = -1;
      while(!salleExiste){
        System.out.println("Veuillez choisir la salle d'enchere :");
        while (!sc.hasNextInt()) sc.next();
        numSalle = sc.nextInt();
        Statement salles = con.createStatement();
        ResultSet rs = salles.executeQuery("SELECT * FROM SALLE");
        while (rs.next()) {
          if(rs.getInt("id_salle") == numSalle){
            salleExiste = true;
          }
        }
      }
      PreparedStatement salle = con.prepareStatement("INSERT INTO RENTRE_DANS VALUES (=?, =?) ");
      salle.setString(1, emailUtilisateur);
      salle.setInt(2, numSalle);
      ResultSet rs = salle.executeQuery();
    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }

  }



}
