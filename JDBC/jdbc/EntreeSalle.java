package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class EntreeSalle{
  private Connection con;
  private String categorie;
  private int numSalle;

  public EntreeSalle(Connection con, String emailUtilisateur){
    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Souhaitez vous voir toutes les categories (oui/non)");
      String reponse = sc.nextLine();
      if (reponse.equals("oui")) {
        Statement salles = con.createStatement();
        ResultSet rs = salles.executeQuery("SELECT * FROM CATEGORIES");
        while (rs.next()) {
          System.out.println(rs.getString("NOM_CATEGORIE") + " " + rs.getString("DESCRIPTION_CATEGORIE"));
        }
      }
      System.out.println("Veuillez choisir la categorie de produit :");

      this.categorie = sc.nextLine();

      boolean salleExiste = new Boolean(false);
      int numSalle = -1;
      while(!salleExiste){
        PreparedStatement salles = con.prepareStatement("SELECT DISTINCT(S.id_salle), S.nom_categorie, V.unicite_enchere, V.sens_vente, V.annulation_vente, V.duree_vente FROM SALLE S, VENTE V WHERE S.nom_categorie=? AND S.id_salle = V.id_salle");
        this.categorie.toLowerCase();
        salles.setString(1, this.categorie);
        ResultSet rs = salles.executeQuery();
        // Est ce que ca marche
        // while (!sc.hasNextInt()) sc.next();
        while (rs.next()) {
          System.out.println(rs.getInt("id_salle") + " " + rs.getString("nom_categorie") + " " + rs.getString("unicite_enchere") + " " + rs.getString("sens_vente")+ " " + rs.getString("annulation_vente")+ " " + rs.getString("duree_vente"));
        }
        System.out.println("Veuillez choisir la salle d'enchere :");
        numSalle = sc.nextInt();
        rs = salles.executeQuery();
        while (rs.next()) {
          if(rs.getInt("id_salle") == numSalle){
            salleExiste = true;
          }
        }
      }
      PreparedStatement salle = con.prepareStatement("INSERT INTO RENTRE_DANS VALUES (?, ?) ");
      salle.setString(1, emailUtilisateur);
      salle.setInt(2, numSalle);
      ResultSet rs = salle.executeQuery();
      PreparedStatement commit = con.prepareStatement("COMMIT");
      commit.executeQuery();
      this.numSalle = numSalle;
    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }
  }

  public String getCategorie(){
    return this.categorie;
  }

  public int getSalle(){
    return this.numSalle;
  }



}
