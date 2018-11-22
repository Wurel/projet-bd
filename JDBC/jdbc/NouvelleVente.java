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
      if (reponse.equals("non")) {
          Statement salleMax = con.createStatement("SELECT MAX(id_salle) FROM SALLE ");
          ResultSet getMax = salleMax.executeQuery();
          rs.next();
          int max = rs.getInt("id_salle")


          System.out.println("Souhaitez vous que la vente soit non-montante (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            PreparedStatement salles = con.prepareStatement("SELECT * FROM SALLE as S, NON_MONTANTE as V WHERE nom_categorie=?, S.id_salle=V.id_salle");
            salles.setString(1, categorie);
            ResultSet rs = salles.executeQuery();
            if (!rs.next()) {
              
            }

          }
          System.out.println("Souhaitez vous que la vente soit revocable (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {

          }
          System.out.println("Souhaitez vous que la vente soit limitee dans le temps (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {
            System.out.println("Heure de fin de vente (oui/non)");

            System.out.println("Date de fin de vente (oui/non)");

          }
          System.out.println("Souhaitez vous que la vente enchere unique (oui/non)");
          reponse = sc.nextLine();
          if (reponse.equals("oui")) {

          }
      }

    }

  }
}
