import jdbc.*;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

// https://docs.oracle.com/javase/7/docs/api/java/sql/package-summary.html

/**
  * Classe de test
  */

public class Exe {

    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1";

        MyConnexion connex = new MyConnexion(url);

        Connection con = connex.getConnexion();

        String email = connex.getEmail();
        System.out.println(email);

        boolean entreeAutreSalle = true;
        do {
            EntreeSalle entre = new EntreeSalle(con , email);

            Scanner sc = new Scanner(System.in);
            System.out.println("Etes vous vendeur (oui/non)");
            String reponse = sc.nextLine();
            if (reponse.equals("oui")) {
                new NouvelleVente(con, entre.getCategorie(), email);
            }
            int salleCourante = entre.getSalle();
            // new PresentationProduit(con, salleCourante);

            // ChoixVente();

            while (true) {
                // DemandeEnchere();
                System.out.println("Voulez-vous surenchérir sur ce produit? (oui/non) ");
                String surenchere = sc.nextLine();
                if (surenchere.equals("non")) {
                    break;
                }

            }

            // SortirSalle()
            System.out.println("Voulez-vous entrer dans une autre salle? (oui/non) ");
            String reponseAutreSalle = sc.nextLine();
            entreeAutreSalle = reponseAutreSalle.equals("oui");

        } while (entreeAutreSalle);



        // Requete rqt = new Requete(con);
        // System.out.println(rqt);
        //
        // while(true){
        //   requete(con, pstmt);
        // }

    }
}

//   /**
//     * Autre exemple de requete
//     * @param con Connection en cours
//     * @param pstmt une requete a completer
//     */
//   public static void requete(Connection con, PreparedStatement pstmt){
//     try {
//       Scanner sc = new Scanner(System.in);
//       System.out.println("Veuillez saisir le nom qui vous interessent :");
//       String select = sc.nextLine();
//       // System.out.println("SELECT " + select + " FROM " + table);
//
//       // PreparedStatement pstmt = con.prepareStatement("SELECT * FROM PILOTE WHERE NOM_PILOTE = ?");
//       pstmt.setString(1, select);
//       ResultSet rs = pstmt.executeQuery();
//       while (rs.next()) {
//         System.out.println(select + " " +rs.getInt("CODE_PILOTE"));
//       }
//     }catch (SQLException e){
//       e.printStackTrace();
//       throw new NullPointerException();
//     }
//     // return stmt.executeQuery("SELECT FROM ")
//   }
//
// }
