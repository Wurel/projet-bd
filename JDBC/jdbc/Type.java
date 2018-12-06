package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class Type{
  private int idSalle;

  public Type(Connection con, String categorie, boolean estUnique, boolean estDescendante, boolean estRevocable, boolean estLimitee){
    try {
      // PreparedStatement salle = con.prepareStatement("SELECT V.id_salle FROM SALLE S, VENTE V WHERE S.nom_categorie ='?' AND S.id_salle=V.id_salle AND V.unicite_enchere='?' AND V.sens_vente='?' AND V.annulation_vente='?' AND V.duree_vente='?'");
      Statement salle = con.createStatement();
      // salle.setString(1, categorie);
      // if (estUnique) {
      //   salle.setString(2, "unique");
      // }
      // else{
      //   salle.setString(2, "plusieurs");
      // }
      //
      // if (estDescendante) {
      //   salle.setString(3, "descendante");
      // }
      // else{
      //   salle.setString(3, "montante");
      // }
      //
      // if (estRevocable) {
      //   salle.setString(4, "revocable");
      // }
      // else{
      //   salle.setString(4, "non_revocable");
      // }
      //
      // if (estLimitee) {
      //   salle.setString(5, "limitee");
      // }
      // else{
      //   salle.setString(5, "non_limitee");
      // }
      System.out.println(categorie);
      ResultSet rs = salle.executeQuery("SELECT V.id_salle FROM SALLE S, VENTE V WHERE S.nom_categorie ='" + categorie + "' AND S.id_salle=V.id_salle AND V.unicite_enchere='" + (estUnique ? "unique":"plusieurs") + "' AND V.sens_vente='" + (estDescendante ? "descendante":"montante") +"' AND V.annulation_vente='" + (estRevocable? "revocable":"non_revocable") + "' AND V.duree_vente='" + (estLimitee ? "limitee":"non_limitee") +"'");
      if(rs.next()){
        this.idSalle = rs.getInt("id_salle");
      }
      else{
        this.idSalle = -1;
      }
  }catch (SQLException e){
    e.printStackTrace();
    throw new NullPointerException();
  }

  }

  public int getId(){
    return this.idSalle;
  }

}
