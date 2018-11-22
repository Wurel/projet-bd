package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class NouvelleVente{
  private Connection con;

  public NouvelleVente(Connection con, String categorie){
    try{
      Scanner sc = new Scanner(System.in);
      System.out.println("La vente sera par defaut de type montante,
      non nevocable, sans limite de temps, enchere multiple. Souhaitez vous creer ce type de vente (oui/non)");
      String reponse = sc.nextLine();
      boolean estDescendante = false;
      int idSalleDescendante = -1;
      boolean estRevocable = false;
      int idSalleRevocable = -1;
      boolean estLimitee = false;
      int idSalleLimitee = -1;
      boolean estUnique = false;
      int idSalleUnique = -1;
      if (reponse.equals("non")) {
          Statement salleMax = con.createStatement("SELECT MAX(id_salle) FROM SALLE ");
          ResultSet getMax = salleMax.executeQuery();
          getMax.next();
          int max = getMax.getInt("id_salle");
          PreparedStatement nouvelleSalle;
          PreparedStatement salles;
          ResultSet rs;
          System.out.println("Souhaitez vous que la vente soit descendante (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estDescendante = True;
            salles = con.prepareStatement("SELECT * FROM SALLE as S, DESCENDANTE as V WHERE nom_categorie=?, S.id_salle=V.id_salle");
            salles.setString(1, categorie);
            rs = salles.executeQuery();
            if (!rs.next()) {
              nouvelleSalle = con.prepareStatement("INSERT INTO DESCENDANTE VALUES (id_salle=?, nom_categorie=?)");
              nouvelleSalle.setString(2, categorie);
              nouvelleSalle.setInt(1, max + 1);
              max += 1;
              idSalleDescendante = max;
            }
            else{
              idSalleDescendante = rs.getInt("id_salle");
            }

          }
          System.out.println("Souhaitez vous que la vente soit revocable (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estRevocable = true;
            salles = con.prepareStatement("SELECT * FROM SALLE as S, REVOCABLE as V WHERE nom_categorie=?, S.id_salle=V.id_salle");
            salles.setString(1, categorie);
            rs = salles.executeQuery();
            if (!rs.next()) {
              nouvelleSalle = con.prepareStatement("INSERT INTO REVOCABLE VALUES (id_salle=?, nom_categorie=?)");
              nouvelleSalle.setString(2, categorie);
              nouvelleSalle.setInt(1, max + 1);
              max += 1;
              idSalleRevocable = max;
            }
            else{
              idSalleRevocable = rs.getInt("id_salle");
            }
          }
          System.out.println("Souhaitez vous que la vente soit limitee dans le temps (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estLimitee = true;
            System.out.println("Heure de fin de vente (oui/non)");

            System.out.println("Date de fin de vente (oui/non)");

            salles = con.prepareStatement("SELECT * FROM SALLE as S, DUREE_LIMITEE as V WHERE nom_categorie=?, S.id_salle=V.id_salle");
            salles.setString(1, categorie);
            rs = salles.executeQuery();
            if (!rs.next()) {
              nouvelleSalle = con.prepareStatement("INSERT INTO DUREE_LIMITEE VALUES (id_salle=?, nom_categorie=?)");
              nouvelleSalle.setString(2, categorie);
              nouvelleSalle.setInt(1, max + 1);
              max += 1;
              idSalleLimitee = max;
            }
            else{
              idSalleLimitee = rs.getInt("id_salle");
            }
          }
          System.out.println("Souhaitez vous que la vente enchere unique (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            estUnique = true;
            salles = con.prepareStatement("SELECT * FROM SALLE as S, UNIQUE as V WHERE nom_categorie=?, S.id_salle=V.id_salle");
            salles.setString(1, categorie);
            rs = salles.executeQuery();
            if (!rs.next()) {
              nouvelleSalle = con.prepareStatement("INSERT INTO UNIQUE VALUES (id_salle=?, nom_categorie=?)");
              nouvelleSalle.setString(2, categorie);
              nouvelleSalle.setInt(1, max + 1);
              max += 1;
              idSalleUnique = max;
            }
            else{
              idSalleUnique = rs.getInt("id_salle");
            }
          }
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

      System.out.println("Nous allons maintenant determiner les informations relatives a la vente");
      System.out.println("Entrez le prix de depart");
      // Est ce que ca marche
      while (!sc.hasNextInt()) sc.next();
      int prixDepart = sc.nextInt();

      Statement venteMax = con.createStatement("SELECT MAX(id_vente) FROM PRODUIT ");
      getMax = venteMax.executeQuery();
      getMax.next();
      max = getMax.getInt("id_vente");
      PreparedStatement vente = con.prepareStatement("INSERT INTO VENTE VALUES (id_vente=?, prix_depart_vente=?, id_salle=?)");
      vente.setInt(1, max);
      vente.setInt(2, prix_depart_vente);
      vente.setInt(3, )

      Statement prodMax = con.createStatement("SELECT MAX(id_produit) FROM PRODUIT ");
      getMax = prodMax.executeQuery();
      getMax.next();
      max = getMax.getInt("id_produit");
      produit.setInt(1, max);
      produit.setString(2, nomProduit);
      produit.setInt(3, prixRevient);
      produit.setInt(4, stock);
      produit.setString(5, categorie);
      salles = con.prepareStatement("SELECT * FROM SALLE as S WHERE S.nom_categorie=? !!!!! et les ventes de types classiques !!!!");
      salles.setString(1, categorie);
      rs = salles.executeQuery();
      produit.setInt(6, rs.getInt("id_salle"));
      produit.executeQuery()

      if (estDescendante) {
        produit.setInt(6, idSalleDescendante);
        produit.executeQuery();
      }

      if (estUnique){
        produit.setInt(6, idSalleUnique);
        produit.executeQuery();
      }

      if(estLimitee){
        produit.setInt(6, idSalleLimitee);
        produit.executeQuery();


        //  ATTENTION JE SAIS PAS GERER LES DATES ENCORE


        System.out.println("Entrez la date de depart ");
        System.out.println("Entrez la date de fin ");
      }

      if (estRevocable) {
        produit.setInt(6, idSalleRevocable);
        produit.executeQuery();
      }



      PreparedStatement commit = con.prepareStatement("COMMIT");
      commit.executeQuery();

    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }

  }
}
