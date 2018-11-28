package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class PresentationProduit{

    public PresentationProduit(Connection con, int idSalle){
      try{
        Scanner sc = new Scanner(System.in);
        System.out.println("Souhaitez vous voir les produits presents dans la salle (oui/non)");
        String reponse = sc.nextLine();
        if (reponse.equals("oui")) {
          Statement produits = con.createStatement();
          // produits.setInt(1, idSalle);
          ResultSet rs = produits.executeQuery("SELECT * FROM SALLE S, PRODUIT P, CARACTERISTIQUE C WHERE S.id_salle = P.id_salle AND C.id_produit=P.id_produit AND S.id_salle =" + idSalle);
          while (rs.next()) {
            System.out.println("Id produit " + rs.getInt("id_produit") + "\n" +" Nom produit " + rs.getString("nom_produit") + " Prix de revient " + rs.getInt("prix_revient_produit") + " Stock du produit " + rs.getInt("stock_produit") + "\n" + " Nom caracteristique " + rs.getString("nom_caracteristique") + rs.getString("description_caracteristique"));
          }
        }

      }catch (SQLException e){
        e.printStackTrace();
        throw new NullPointerException();
      }
    }
}
