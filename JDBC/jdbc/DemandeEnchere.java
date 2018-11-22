package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class DemandeEnchere{
    private Connection con;
    private String emailAcheteur;


    public DemandeEnchere(Connection con, String emailAcheteur, int idVente, boolean enchereMontante, boolean enchereMultipleAutorisee){

        /*
         * Cette fonction effectue une demande d'enchère sur une vente.
         *
         * /!\ : Pour l'instant, cette fonction ne s'occupe que des enchères montantes.
         */

        /* On demande une offre à l'acheteur en vérifiant :
         *      - que le nombre soit correct,
         *      - que l'enchère soit suffisante.
         * Ce processus est réalisé par les 2 boucles while suivantes.
         */

        // Si l'enchère est montante :
        if (enchereMontante) {

            // Si les enchères multiples ne sont pas autorisées, il faut s'assurer que le client a le droit d'enchérir :

            if (!enchereMultipleAutorisee) {
                PreparedStatement droitEnchere = con.prepareStatement("SELECT COUNT(PRIX_ENCHERE) FROM ENCHERES WHERE emailAcheteur =? ");
                droitEnchere.setString(1, emailAcheteur);
                ResultSet nombreEnchere = droitEnchere.executeQuery();

                if (nombreEnchere.getInt("COUNT(PRIX_ENCHERE)") == 1) {
                    System.out.println("Désolé, les enchères multiples ne sont pas autorisées. Vous avez déjà enchéri sur cette vente.");
                    // TODO :  Quitter la vente.
                }

            }

            boolean nombreCorrect = False;
            boolean enchereSuffisante = False;

            while (!enchereSuffisante){
                while (!nombreCorrect) {

                    // L'utilisateur saisit un nombre et on vérifie qu'il est correct :
                    System.out.println("Veuillez saisir votre offre pour ce produit : ");
                    String offre = sc.nextLine();
                    nombreCorrect = True;
                    try{
                        int prixOffre = Integer.parseInt(offre);
                    } catch (Exception e) {
                        System.out.println("ERREUR : Vous avez saisi un nombre incorrect!");
                        nombreCorrect = False;
                    }
                }
                // On vérifie maintenant que l'offre est suffisante :

                PreparedStatement prixEncheres = con.prepareStatement("SELECT MAX(PRIX_ENCHERE) FROM ENCHERES WHERE ID_VENTE =? ");
                prixEncheres.setInt(1, idVente);
                ResultSet rs = prixEncheres.executeQuery();
                // Comparaison des nombres :
                int prixActuel = rs.getInt("PRIX_ENCHERE");
                enchereSuffisante = (prixOffre > prixActuel);

                // On informe le client si l'offre est insuffisante :
                if (!enchereSuffisante) {
                    System.out.println("Votre offre est insuffisante. Le prix actuel de l'enchère est :");
                    System.out.println(prixActuel);
                    System.out.println("Vous devez proposer plus si vous voulez acquérir cet article.");
                }
            }


            /*
             * On rappelle que les clés primaires sont de type INT.
             * On cherche une nouvelle clé primaire.
             * Pour cela on va :
             *      - chercher la plus grande clé primaire
             *      - ajouter 1
             */
            PreparedStatement clesPrimairesExistantes = con.prepareStatement("SELECT MAX(ID_ENCHERE) FROM ENCHERES");
            ResultSet cleMax = clesPrimairesExistantes.executeQuery();
            int clePrimaire = cleMax.getInt("id_enchere") + 1;

            // On insère l'offre :
            PreparedStatement enchere = con.prepareStatement("INSERT INTO ENCHERES VALUES (=?, =?, =?, =?) ");
            // TODO : Insérer les attributs dans le bon sens :
            enchere.setInt(1, clePrimaire);
            enchere.setInt(2, prixOffre);
            enchere.setString(3, emailUtilisateur);
            enchere.setInt(4, idVente);

            ResultSet rs = salle.executeQuery();
            PreparedStatement commit = con.prepareStatement("COMMIT");
            commit.executeQuery();
        }
    }
}
