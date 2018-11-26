package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class Type{
  private int idSalle;

  public Type(Connection con, String categorie, boolean estUnique, boolean estDescendante, boolean estRevocable, boolean estLimitee){
    try {
      PreparedStatement salle = con.prepareStatement("SELECT id_salle FROM SALLE as S, VENTE as V WHERE S.categorie =?, S.id_salle=V.id_salle, V.unicite_enchere=?, V.sens_vente=?, V.annulation_vente=?, V.duree_vente=?");
      salle.setString(1, categorie);
      if (estUnique) {
        salle.setString(2, "unique");
      }
      else{
        salle.setString(2, "plusieurs");
      }

      if (estDescendante) {
        salle.setString(3, "descendante");
      }
      else{
        salle.setString(3, "montante");
      }

      if (estRevocable) {
        salle.setString(4, "revocable");
      }
      else{
        salle.setString(4, "non_revocable");
      }

      if (estLimitee) {
        salle.setString(5, "limitee");
      }
      else {
        salle.setString(6, "nom_limitee");
      }

      ResultSet rs = salle.executeQuery();
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
