package jdbc;

import java.sql.*;
import java.util.*;

/**
  * Classe permettant de faire une requeteSql
  */

public class Requete{
  private PreparedStatement requeteSql;

  /**
    * Initialise la requeteSql
    * @param con Connection sur laquelle on fait la requeteSql
    */
  public Requete(Connection con){
  try {
    Scanner sc = new Scanner(System.in);

    System.out.println("Souhaitez vous voir tous les salles d'encheres (oui/non)");
      String reponse = sc.nextLine();
    if (reponse.equals("oui")) {
      Statement salles = con.createStatement();
      ResultSet rs = salles.executeQuery("SELECT * FROM SALLES");
      while (rs.next()) {
        System.out.println("Nom " + rs.getString("NOM"));
      }
    }
    System.out.println("Veuillez choisir la salle d'enchere :");
    while (!sc.hasNextInt()) sc.next();
    int numSalle = sc.nextInt();
    PreparedStatement salle = con.prepareStatement("SELECT * FROM PILOTE WHERE CODE_PILOTE =? ");
    salle.setInt(1, numSalle);
    this.requeteSql = salle;
  }catch (SQLException e){
    e.printStackTrace();
    throw new NullPointerException();
  }
  }

  /**
    * @return Renvoie la requeteSql
    */
  public PreparedStatement getRequete(){
    return this.requeteSql;
  }

  /**
    * @return Renvoie le resultat de la requeteSql
    */
  @Override
  public String toString(){
    String s = new String();
    try{
      ResultSet rs = this.requeteSql.executeQuery();
      while (rs.next()) {
        System.out.println("Nom " + rs.getString("NOM_PILOTE"));
      }
      return s;
    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }
  }
}
