package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class ChoixVente {
  int idVente;
  boolean enchereMontante;
  boolean enchereMultipleAutorisee;

  public ChoixVente(Connection con, int salleCourante){
    try{
      System.out.println("Veuillez choisir une vente parmi celles proposees");
      Statement produits = con.createStatement();
      ResultSet rs = produits.executeQuery("SELECT * FROM VENTE V, PRODUIT_SOUMIS_A_LA_VENTE PSV, PRODUIT P WHERE V.id_salle = " + salleCourante + " AND PSV.id_produit = P.id_produit AND PSV.id_vente = V.id_vente");
      while (rs.next()) {
        System.out.println("Id produit " + rs.getInt("id_produit") + " Nom produit " + rs.getString("nom_produit"));
        this.enchereMontante = "montante".equals(rs.getString("sens_vente"));
        this.enchereMultipleAutorisee = "plusieurs".equals(rs.getString("unicite_enchere"));
      }
      Scanner sc = new Scanner(System.in);
      this.idVente = (int)sc.nextInt();
    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }
  }

  public int getIdVente(){
    return this.idVente;
  }

  public boolean getEnchereMontante(){
    return this.enchereMontante;
  }

  public boolean getEnchereMultipleAutorisee(){
    return this.enchereMultipleAutorisee;
  }
}
