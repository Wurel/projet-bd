package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class SortirSalle{

  public SortirSalle(Connection con, String mail, int salleCourante){
    try{
      Statement salle = con.createStatement();
      salle.executeQuery("DELETE FROM RENTRE_DANS WHERE email_utilisateur='" + mail + "' AND id_salle=" + salleCourante);
      PreparedStatement commit = con.prepareStatement("COMMIT");
      commit.executeQuery();
    }catch (SQLException e){
      e.printStackTrace();
      throw new NullPointerException();
    }
  }
}
