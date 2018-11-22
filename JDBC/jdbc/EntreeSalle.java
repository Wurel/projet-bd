package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class EntreeSalle{
  private Connection con;
  private String categorie;

  public EntreeSalle(Connection con, String emailUtilisateur){
    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Souhaitez vous voir toutes les categories (oui/non)");
      String reponse = sc.nextLine();
      if (reponse.equals("oui")) {
        Statement salles = con.createStatement();
        ResultSet rs = salles.executeQuery("SELECT DISTINCT nom_categorie FROM SALLE");
        while (rs.next()) {
          System.out.println("Id Salle : " + rs.getInt("id_salle") + " Categorie : " + rs.getString("nom_categorie"));
        }
      }
      System.out.println("Veuillez choisir la categorie de produit :");

      this.categorie = sc.nextLine();

      boolean salleExiste = new Boolean(false);
      int numSalle = -1;
      while(!salleExiste){
        System.out.println("Veuillez choisir la salle d'enchere :");
        PreparedStatement salles = con.prepareStatement("SELECT * FROM SALLE WHERE nom_categorie=?");
        this.categorie.toLowerCase();
        salle.setString(1, this.categorie);
        ResultSet rs = salles.executeQuery();
        // Est ce que ca marche
        while (!sc.hasNextInt()) sc.next();
        numSalle = sc.nextInt();
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
      PreparedStatement commit = con.prepareStatement("COMMIT");
      commit.executeQuery();
      return numSalle;
    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }
  }

  public String getCategorie(){
    return this.categorie;
  }



}
