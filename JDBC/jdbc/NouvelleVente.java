package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class NouvelleVente{
  private Connection con;

  public NouvelleVente(Connection con, String categorie){
    try{
      Scanner sc = new Scanner(System.in);
      System.out.println("La vente sera par defaut de type montante, non nevocable, sans limite de temps, enchere multiple. Souhaitez vous creer ce type de vente (oui/non)");
      String reponse = sc.nextLine();
      boolean estDescendante = false;
      boolean estRevocable = false;
      boolean estLimitee = false;
      boolean estUnique = false;
      ResultSet getMax;
      int max = -1;
      if (reponse.equals("non")) {
          Statement salleMax = con.createStatement();
          getMax = salleMax.executeQuery("SELECT MAX(id_salle) FROM SALLE ");
          getMax.next();
          max = getMax.getInt("id_salle");
          PreparedStatement salles;
          ResultSet rs;
          System.out.println("Souhaitez vous que la vente soit descendante (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estDescendante = true;
          }
          System.out.println("Souhaitez vous que la vente soit revocable (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estRevocable = true;
          }
          System.out.println("Souhaitez vous que la vente soit limitee dans le temps (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estLimitee = true;
          }
          System.out.println("Souhaitez vous que la vente enchere unique (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estUnique = true;
          }
      }
      Type salleType = new Type(con, categorie, estUnique, estDescendante, estRevocable, estLimitee);
      int idSalle = salleType.getId();
      if (idSalle == -1) {
        PreparedStatement nouvelleSalle = con.prepareStatement("INSERT INTO SALLE VALUE (id_salle=?, nom_categorie=?)");
        nouvelleSalle.setInt(1, max);
        nouvelleSalle.setString(2, categorie);
        idSalle = max + 1;
      }
      System.out.println("Nous allons vous demander les champs relatifs au produit");
      System.out.println("Entrez le nom du produit");
      String nomProduit = sc.nextLine();
      System.out.println("Entrez le prix de revient souhaite du produit");
      // Est ce que ca marche
      while (!sc.hasNextInt()) sc.next();
      int prixRevient = sc.nextInt();
      System.out.println("Entrez le stock du produit");
      // Est ce que ca marche
      while (!sc.hasNextInt()) sc.next();
      int stock = sc.nextInt();
      PreparedStatement produit = con.prepareStatement("INSERT INTO PRODUIT VALUES (id_produit=?, nom_produit=?, prix_revient_produit=?, stock_produit=?, nom_categorie=?, id_salle=?)");


      Statement prodMax = con.createStatement();
      getMax = prodMax.executeQuery("SELECT MAX(id_produit) FROM PRODUIT ");
      getMax.next();
      max = getMax.getInt("id_produit");
      produit.setInt(1, max);
      produit.setString(2, nomProduit);
      produit.setInt(3, prixRevient);
      produit.setInt(4, stock);
      produit.setString(5, categorie);
      produit.setInt(6, idSalle);
      produit.executeQuery();

      System.out.println("Nous allons maintenant determiner les informations relatives a la vente");
      System.out.println("Entrez le prix de depart");
      // Est ce que ca marche
      while (!sc.hasNextInt()) sc.next();
      int prixDepart = sc.nextInt();

      Statement venteMax = con.createStatement();
      getMax = venteMax.executeQuery("SELECT MAX(id_vente) FROM PRODUIT ");
      getMax.next();
      max = getMax.getInt("id_vente");
      PreparedStatement vente = con.prepareStatement("INSERT INTO VENTE VALUES (id_vente=?, prix_depart_vente=?, id_salle=?)");
      vente.setInt(1, max);
      vente.setInt(2, prixDepart);
      vente.setInt(3, idSalle);
      vente.executeQuery();


      if (estDescendante) {
          PreparedStatement desc = con.prepareStatement("UPDATE VENTE SET sens_vente='descendante'");
          desc.executeQuery();
      }

      if (estUnique){
        PreparedStatement uniq = con.prepareStatement("UPDATE VENTE SET unicite_enchere='unique'");
        uniq.executeQuery();
      }

      if(estLimitee){
        PreparedStatement dur = con.prepareStatement("UPDATE VENTE SET duree_vente='limitee', date_fin=?, heure_fin=?");


        System.out.println("Entrez la date de fin sous la forme aaaammjjhhmmss");
        String dateFin = sc.nextLine();
        dur.setString(1, dateFin);
        dur.executeQuery();


      }

      if (estRevocable) {
        PreparedStatement rev = con.prepareStatement("UPDATE VENTE SET annulation_vente='revocable'");
        rev.executeQuery();
      }

      PreparedStatement commit = con.prepareStatement("COMMIT");
      commit.executeQuery();

    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }

  }
}
