package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;
import java.text.*;

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

                try {
                    PreparedStatement droitEnchere = con.prepareStatement("SELECT COUNT(num_enchere) As total FROM ENCHERE_PROPOSEE WHERE email_utilisateur =? AND id_vente =?");
                    droitEnchere.setString(1, emailAcheteur);
                    String idVenteString = "";
                    droitEnchere.setString(2, idVenteString.valueOf(idVente));
                    ResultSet nombreEnchere = droitEnchere.executeQuery();
                    while(nombreEnchere.next()){
                        if (nombreEnchere.getInt("total") == 0) {
                            // 2nd cas :
                            autorisation = true;
                        }
                        else{
                            System.out.println("Désolé, les enchères multiples ne sont pas autorisées. Vous avez déjà enchéri sur cette vente.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new NullPointerException();
                }
            }
        }
        else{
            // Si l'enchere est descendante
            try {
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
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NullPointerException();
            }
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

        // On cherche les clés primaires dans les tables ENCHERE et ENCHERE_PROPOSEE :
        try {
            PreparedStatement clesPrimairesExistantesTableEnchere = con.prepareStatement("SELECT MAX(num_enchere) AS cle FROM ENCHERE");
            ResultSet cleMax = clesPrimairesExistantesTableEnchere.executeQuery();
            
            int clePrimaireEnchere = 0;
            while (cleMax.next()){
                clePrimaireEnchere = cleMax.getInt("cle") + 1;
            }

            PreparedStatement clesPrimairesExistantesTableEnchereProposee = con.prepareStatement("SELECT MAX(num_enchere) AS cleproposee FROM ENCHERE_PROPOSEE");
            ResultSet cleMaxProposee = clesPrimairesExistantesTableEnchereProposee.executeQuery();
            
            int clePrimaireEnchereProposee = 0;
            while (cleMaxProposee.next()) {
                clePrimaireEnchereProposee = cleMaxProposee.getInt("cleproposee") + 1;
            }
        } catch (SQLException e) {
              e.printStackTrace();
              throw new NullPointerException();
        }

            // Il nous faut la date et l'heure :
            java.util.Date dateEtHeure = new java.util.Date();
            // Il y a conflit entre java.util.Date et java.sql.Date
            // Donc on met le nom complet.

            // TODO : On donne la date :
            SimpleDateFormat formatDate = new SimpleDateFormat ("yyyy.MM.dd");
            String date = formatDate.format(dateEtHeure);

            // TODO : On donne l'heure :
            SimpleDateFormat formatHeure = new SimpleDateFormat ("HH:mm:ss:SSS");
            String heure = formatHeure.format(dateEtHeure);


        try{
            // On insère l'offre :
            PreparedStatement enchere = con.prepareStatement("INSERT INTO ENCHERE VALUES (=?, =?, =?, =?, =?) ");
            // On insère les attributs dans le bon sens :
            enchere.setInt(1, clePrimaireEnchere);
            enchere.setInt(2, prixEnchere);
            enchere.setString(3, date);
            // enchere.setString(4, heure);
            enchere.setInt(4, 1);

            ResultSet rs = enchere.executeQuery();
            PreparedStatement commit = con.prepareStatement("COMMIT");
            commit.executeQuery();

            PreparedStatement enchereProposee = con.prepareStatement("INSERT INTO ENCHERE_PROPOSEE VALUES (=?, =?, =?) ");
            // On insère les attributs dans le bon sens :
            enchere.setInt(1, clePrimaireEnchereProposee);
            enchere.setInt(2, idVente);
            enchere.setString(3, emailAcheteur);

            rs = enchereProposee.executeQuery();
            commit = con.prepareStatement("COMMIT");
            commit.executeQuery();

            System.out.println("Félicitation! Vous avez bien encherri sur cette vente.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
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
                // On donne exprès des valeurs absures pour éviter des messages d'erreurs désagréables. 
                // Avant de les utiliser, on vérifiera bien que la valeur a été changée
                int prixAchat = -1;
                int prixActuel = -1;

                String offre = "";
                while (!enchereSuffisante){
                    while (!nombreCorrect) {

                        Scanner sc = new Scanner(System.in);
                        // L'utilisateur saisit un nombre et on vérifie qu'il est correct :
                        System.out.println("Veuillez saisir votre offre pour ce produit : ");
                        offre = sc.nextLine();
                        nombreCorrect = true;
                        try{
                            prixAchat = Integer.parseInt(offre);
                        } catch (Exception e) {
                            System.out.println("ERREUR : Vous avez saisi un nombre incorrect!");
                            nombreCorrect = false;
                        }
                    }
                    // On vérifie maintenant que l'offre est suffisante :
                    try {

                        PreparedStatement prixEncheres = con.prepareStatement("SELECT MAX(EN.prix_enchere) As prix FROM ENCHERE_PROPOSEE EP join ENCHERE EN ON EP.num_enchere = EN.num_enchere WHERE EP.id_vente =? ");
                        prixEncheres.setInt(1, idVente);
                        ResultSet rs = prixEncheres.executeQuery();
                        // Comparaison des nombres :
                        while(rs.next()) {
                            prixActuel = rs.getInt("prix");
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                        throw new NullPointerException();
                    }
                    prixAchat = Integer.parseInt(offre);
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

                // On récupère le prix actuel :
                int prixActuel;
                try {
                    PreparedStatement prixEncheres = con.prepareStatement("SELECT prix_depart_vente FROM VENTE WHERE id_vente =? ");
                    prixEncheres.setInt(1, idVente);
                    ResultSet rs = prixEncheres.executeQuery();

                    prixActuel = rs.getInt("prix_depart_vente");
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new NullPointerException();
                }

                System.out.println("Le prix actuel est : ");
                System.out.println(prixActuel);
                System.out.println("Voulez-vous acheter cet article à ce prix là? (oui/non)");
                Scanner sc = new Scanner(System.in);
                String reponse = sc.nextLine();

                if (reponse.equals("oui")) {
                    FaireEnchere(emailAcheteur, idVente, prixActuel);
                }

                else {
                    System.out.println("Le prix va continuer de descendre jusqu'à ce que quelqu'un achète cette vente. Ne passez pas à côté!");
                }

            }
        }

    }
}
