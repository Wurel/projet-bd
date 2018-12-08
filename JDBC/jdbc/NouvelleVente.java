package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class NouvelleVente{
  private Connection con;

  public NouvelleVente(Connection con, String categorie, String email){
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
          max = getMax.getInt("MAX(id_salle)");
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
        Statement salleMax = con.createStatement();
        getMax = salleMax.executeQuery("SELECT MAX(id_salle) FROM SALLE ");
        getMax.next();
        max = getMax.getInt("MAX(id_salle)") + 1;
        PreparedStatement nouvelleSalle = con.prepareStatement("INSERT INTO SALLE VALUES (?, ?)");
        nouvelleSalle.setInt(1, max);
        nouvelleSalle.setString(2, categorie);
        nouvelleSalle.executeUpdate();
        idSalle = max;
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
      PreparedStatement produit = con.prepareStatement("INSERT INTO PRODUIT VALUES (?, ?, ?, ?, ?, ?)");


      Statement prodMax = con.createStatement();
      getMax = prodMax.executeQuery("SELECT MAX(id_produit) FROM PRODUIT ");
      getMax.next();
      int idProduit = getMax.getInt("MAX(id_produit)")+1;
      produit.setInt(1, idProduit);
      produit.setString(2, nomProduit);
      produit.setInt(3, prixRevient);
      produit.setInt(4, stock);
      produit.setString(5, categorie);
      produit.setInt(6, idSalle);
      produit.executeUpdate();

      boolean ajouterCaract = true;
      while(ajouterCaract){
        System.out.println("Entrez le nom de la caracteristique que vous voulez decrire(un mot)");
        String nomCaract = sc.nextLine();
        System.out.println("Decrivez la caracteristique de votre produit");
        Scanner nvSc = new Scanner(System.in);
        String descrition = nvSc.nextLine();
        System.out.println("Voulez-vous ajouter d'autres caracteristiques?(oui=1/non=0)");
        while (!sc.hasNextInt()) sc.next();
        int nvCarac = sc.nextInt();
        Statement caracte = con.createStatement();
        caracte.executeUpdate("INSERT INTO CARACTERISTIQUE VALUES("+nomCaract+","+descrition+","+idProduit+")");
        if (nvCarac == 0) {
          ajouterCaract = false;
        }
      }

      System.out.println("Nous allons maintenant determiner les informations relatives a la vente");
      System.out.println("Entrez le prix de depart");
      // Est ce que ca marche
      while (!sc.hasNextInt()) sc.next();
      int prixDepart = sc.nextInt();

      Statement venteMax = con.createStatement();
      getMax = venteMax.executeQuery("SELECT MAX(id_produit) FROM PRODUIT ");
      getMax.next();
      int idVente = getMax.getInt("MAX(id_produit)") + 1;
      Statement vente = con.createStatement();
      // PreparedStatement vente = con.prepareStatement("INSERT INTO VENTE (id_vente, prix_depart_vente, id_salle) VALUES (?, ?, ?)");
      // vente.setInt(1, idVente);
      // vente.setInt(2, prixDepart);
      // vente.setInt(3, idSalle);
      // if (estUnique) {
      //   vente.setString(4, "'unique'");
      // }
      // else{
      //   vente.setString(4, "plusieurs");
      // }
      //
      // if (estDescendante) {
      //   vente.setString(5, "descendante");
      // }
      // else{
      //   vente.setString(5, "montante");
      // }
      //
      // if (estRevocable) {
      //   vente.setString(6, "revocable");
      // }
      // else{
      //   vente.setString(6, "non_revocable");
      // }
      //
      // if (estLimitee) {
      //   System.out.println("C est limite");
      //   vente.setString(7, "limitee");
      // }
      // else{
      //   System.out.println("C est pas limite");
      //   vente.setString(7, "non_limitee");
      // }

      System.out.println(idSalle);
      System.out.println(idVente);
      System.out.println(prixDepart);
      vente.executeQuery("INSERT INTO VENTE (id_vente, prix_depart_vente, id_salle) VALUES ("+idVente+", "+prixDepart+", "+idSalle+")");

      PreparedStatement prodSoumisVente = con.prepareStatement("INSERT INTO PRODUIT_SOUMIS_A_LA_VENTE VALUES(?, ?, ?)");
      prodSoumisVente.setInt(1, idProduit);
      prodSoumisVente.setInt(2, idVente);
      prodSoumisVente.setString(3, email);
      prodSoumisVente.executeUpdate();

      PreparedStatement salleVenteCree = con.prepareStatement("INSERT INTO SALLE_DE_VENTE_CREEE VALUES (?, ?, ?)");
      salleVenteCree.setInt(1, idSalle);
      salleVenteCree.setInt(2, idProduit);
      salleVenteCree.setInt(3, idVente);
      salleVenteCree.executeUpdate();

      if (estDescendante) {
          PreparedStatement desc = con.prepareStatement("UPDATE VENTE SET sens_vente='descendante' WHERE id_vente=" + idVente);
          desc.executeQuery();
      }

      if (estUnique){
        PreparedStatement uniq = con.prepareStatement("UPDATE VENTE SET unicite_enchere='unique' WHERE id_vente=" + idVente);
        uniq.executeQuery();
      }

      if(estLimitee){
        // #TODO

        PreparedStatement dur = con.prepareStatement("UPDATE VENTE SET date_fin = ? where id_vente=" + idVente);
        // UPDATE VENTE SET date_fin =timestamp '2018-11-26 10:20:00' where id_vente=5;

        System.out.println("Entrez l'annee de fin sous la forme aaaa mm jj hh mm");
        int anneeFin = sc.nextInt();
        int moisFin = sc.nextInt();
        int jourFin = sc.nextInt();
        int heureFin = sc.nextInt();
        int minuteFin = sc.nextInt();
        // System.out.println(anneeFin  +""+ moisFin +""+ jourFin +""+ heureFin +""+ minuteFin);
        dur.setTimestamp(1, new Timestamp(anneeFin, moisFin, jourFin, heureFin, minuteFin, 0, 0));
        dur.executeUpdate();


        // dur.setString(1, dateFin);
        // dur.executeQuery();


      }

      if (estRevocable) {
        PreparedStatement rev = con.prepareStatement("UPDATE VENTE SET annulation_vente='revocable' WHERE id_vente=" + idVente);
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
