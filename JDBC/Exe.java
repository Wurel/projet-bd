import java.sql.*;
import oracle.jdbc.driver.*;
// https://docs.oracle.com/javase/7/docs/api/java/sql/package-summary.html


public class Exe {

  public static void main(String[] args) {


    Connection con = connexion(new String("poraa"), new String("poraa"));


    try {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT CODE_PILOTE, NOM_PILOTE FROM PILOTE");
    // }catch (SQLException e) {
    //   e.printStackTrace();
    // }

    // try {
      while (rs.next()) {
        System.out.println("code pilote " + rs.getInt("CODE_PILOTE") + " NOM " + rs.getString("NOM_PILOTE"));
      }
      // on ferme la Connection
      con.close();
    }catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection connexion(String login, String mdp){
    String url = "jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1";
    try {
      DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      Connection con = DriverManager.getConnection(url, login, mdp);
      con.setAutoCommit(false);
      return con;
    }catch (SQLException e) {
      e.printStackTrace();
    }
    return Connection;

  }


}
