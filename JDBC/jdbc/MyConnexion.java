package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class MyConnexion{
  private Connection con;
  private String emailUtilisateur;

  /**
    * Permet de se connecter a une base de donnees
    * @param url Url a laquelle se trouve la base de donnees
    */
  public MyConnexion(String url){
  try {
    Scanner sc = new Scanner(System.in);
    // System.out.println("Veuillez saisir votre identifiant :");
    // String login =  sc.nextLine();
    // System.out.println("Veuillez saisir votre mdp :");
    // String mdp = sc.nextLine();

    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    this.con = DriverManager.getConnection(url, "poraa", "poraa");
    this.con.setAutoCommit(false);

    boolean connecte = false;
    while(!connecte){
      System.out.println("Avez vous deja un compte (oui/non)");
      String reponse = sc.nextLine();
      if (reponse.equals("oui")) {
        System.out.println("Inserez votre mail");
        String mail = sc.nextLine();
        Statement email = con.createStatement();
        ResultSet rs = email.executeQuery("SELECT email_utilisateur FROM UTILISATEUR WHERE email_utilisateur = '" + mail + "'");
        this.emailUtilisateur = mail;
        if (rs.next()) {
          System.out.println("Connecte");
          connecte = true;
        }
        else {
          System.out.println("Email non renseigne dans la base de donnees");
        }
      }
      else {
        System.out.println("Inserez votre mail");
        String mail = sc.nextLine();

        System.out.println("Inserez votre Nom");
        String nom = sc.nextLine();

        System.out.println("Inserez votre Prenom");
        String prenom = sc.nextLine();

        System.out.println("Inserez votre Adresse Postale");
        String adressePostale = sc.nextLine();

        PreparedStatement creationCompte = con.prepareStatement("INSERT INTO UTILISATEUR VALUES (?, ?, ?, ?)");
        creationCompte.setString(1, mail);
        creationCompte.setString(2, nom);
        creationCompte.setString(3, prenom);
        creationCompte.setString(4, adressePostale);
        creationCompte.executeQuery();
        PreparedStatement commit = con.prepareStatement("COMMIT");
        commit.executeQuery();
        this.emailUtilisateur = mail;
        connecte = true;

      }
    }

  }catch (SQLException e) {
    e.printStackTrace();
    throw new NullPointerException();
    }
  }

  /**
    * @return Renvoie la Connection
    */
  public Connection getConnexion(){
    return this.con;
  }

  public String getEmail(){
    return this.emailUtilisateur;
  }
}
