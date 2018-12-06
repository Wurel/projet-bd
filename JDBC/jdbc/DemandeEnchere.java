package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class DemandeEnchere{
    private Connection con;
    private String emailAcheteur;


    private boolean AutoriserEnchere(Connection con, String emailAcheteur, int idVente, boolean enchereMontante, boolean enchereMultipleAutorisee){
        /*
         * Cette fonction vérifie que l'utilisateur peut enchérir sur cette vente.
         * Elle renvoie un boolean.
         * Voici la liste exhaustive des cas où l'enchère est autorisée :
         *      - montante, multiple autorisée
         *      - montante, miltiple interdite, pas encore d'enchère de l'utilisateur
         *      - descendante, aucune enchère sur l'offre
         */

        boolean autorisation = false;
        if (enchereMontante) {
            if (enchereMultipleAutorisee) {

                // 1er cas :
                autorisation = true;
            }
            else {
                PreparedStatement droitEnchere = con.prepareStatement("SELECT COUNT(num_enchere) FROM ENCHERE_PROPOSEE WHERE email_utilisateur =? AND id_vente =?");
                droitEnchere.setString(1, emailAcheteur);
                droitEnchere.setString(2, idVente);
                ResultSet nombreEnchere = droitEnchere.executeQuery();

                if (nombreEnchere.getInt("COUNT(num_enchere)") == 0) {

                    // 2nd cas :
                    autorisation = true;
                }
                else{
                    System.out.println("Désolé, les enchères multiples ne sont pas autorisées. Vous avez déjà enchéri sur cette vente.");
                }
            }
        }
        else{
            // Si l'enchere est descendante
            PreparedStatement droitEnchere = con.prepareStatement("SELECT COUNT(num_enchere) FROM ENCHERE_PROPOSEE WHERE id_vente =? ");
                droitEnchere.setInt(1, idVente);
                ResultSet nombreEnchere = droitEnchere.executeQuery();

                if (nombreEnchere.getInt("COUNT(num_enchere)") == 0) {

                    // 3ème cas :
                    autorisation = true;
                }
                else{
                    System.out.println("Désolé, quelqu'un a déjà enchéri sur cette vente.");
        }
        return autorisation;
    }

    private void FaireEnchere(String emailAcheteur, int idVente, int prixEnchere){
        /*
         * Cette fonction réalise l'enchère en écrivant dans la base de données.
         *
         * On rappelle que les clés primaires sont de type INT.
         * La première chose à faire est de chercher une clé primaire
         *
         * Pour cela on va :
         *      - chercher la plus grande clé primaire
         *      - ajouter 1
         */

        PreparedStatement clesPrimairesExistantesTableEnchere = con.prepareStatement("SELECT MAX(num_enchere) FROM ENCHERE");
        ResultSet cleMax = clesPrimairesExistantesTableEnchere.executeQuery();
        int clePrimaireEnchere = cleMax.getInt("num_enchere") + 1;

        PreparedStatement clesPrimairesExistantesTableEnchereProposee = con.prepareStatement("SELECT MAX(num_enchere) FROM ENCHERE");
        ResultSet cleMax = clesPrimairesExistantesTableEnchereProposee.executeQuery();
        int clePrimaireEnchereProposee = cleMax.getInt("num_enchere") + 1;

        // On insère l'offre :
        PreparedStatement enchere = con.prepareStatement("INSERT INTO ENCHERE VALUES (=?, =?, =?, =?, =?) ");
        // TODO : Insérer les attributs dans le bon sens :
        enchere.setInt(1, clePrimaireEnchere);
        enchere.setInt(2, prixAchat);
        enchere.setString(3, date);
        enchere.setString(4, heure);
        enchere.setInt(5, 1);

        ResultSet rs = enchere.executeQuery();
        PreparedStatement commit = con.prepareStatement("COMMIT");
        commit.executeQuery();

        PreparedStatement enchereProposee = con.prepareStatement("INSERT INTO ENCHERE_PROPOSEE VALUES (=?, =?, =?) ");
        // TODO : Insérer les attributs dans le bon sens :
        enchere.setInt(1, clePrimaireEnchereProposee);
        enchere.setInt(2, idVente);
        enchere.setString(3, emailAcheteur);

        ResultSet rs = enchereProposee.executeQuery();
        PreparedStatement commit = con.prepareStatement("COMMIT");
        commit.executeQuery();

        System.out.println("Félicitation! Vous avez bien encherri sur cette vente.");
    }


    public DemandeEnchere(Connection con, String emailAcheteur, int idVente, boolean enchereMontante, boolean enchereMultipleAutorisee){

        /*
         * Cette fonction effectue une demande d'enchère sur une vente.
         * On demande une offre à l'acheteur en vérifiant :
         *      - que le nombre soit correct,
         *      - que l'enchère soit suffisante.
         */

        // Si l'enchère est autorisée :
        if (AutoriserEnchere(con, emailAcheteur, idVente, enchereMontante, enchereMultipleAutorisee)) {

            // Les processus d'enchères ne sont pal les mêmes pour des enchères montantes et descendantes
            // On va donc séparer les 2 processus :

            if (enchereMontante) {
                boolean nombreCorrect = false;
                boolean enchereSuffisante = false;

                while (!enchereSuffisante){
                    while (!nombreCorrect) {

                        // L'utilisateur saisit un nombre et on vérifie qu'il est correct :
                        System.out.println("Veuillez saisir votre offre pour ce produit : ");
                        String offre = sc.nextLine();
                        nombreCorrect = true;
                        try{
                            int prixAchat = Integer.parseInt(offre);
                        } catch (Exception e) {
                            System.out.println("ERREUR : Vous avez saisi un nombre incorrect!");
                            nombreCorrect = false;
                        }
                    }
                    // On vérifie maintenant que l'offre est suffisante :

                    PreparedStatement prixEncheres = con.prepareStatement("SELECT MAX(PRIX_ENCHERE) FROM ENCHERES WHERE ID_VENTE =? ");
                    prixEncheres.setInt(1, idVente);
                    ResultSet rs = prixEncheres.executeQuery();
                    // Comparaison des nombres :
                    int prixActuel = rs.getInt("PRIX_ENCHERE");
                    enchereSuffisante = (prixAchat > prixActuel);

                    // On informe le client si l'offre est insuffisante :
                    if (!enchereSuffisante) {
                        System.out.println("Votre offre est insuffisante. Le prix actuel de l'enchère est :");
                        System.out.println(prixActuel);
                        System.out.println("Vous devez proposer plus si vous voulez acquérir cet article.");
                    }
                }

                FaireEnchere(emailAcheteur, idVente, prixAchat);
            }

            // On traite maintenant le cas des enchères descendantes :
            else {
                // TODO : On récupère le prix actuel
                int prixAchat;

                System.out.println("Le prix actuel est : ");
                System.out.println(prixAchat);
                System.out.println("Voulez-vous acheter cet article à ce prix là? (oui/non)");
                String reponse = sc.nextLine();
                if (reponse.equals("oui")) {
                    FaireEnchere(emailAcheteur, idVente, prixAchat);
                }

            }
        }

    }
}
