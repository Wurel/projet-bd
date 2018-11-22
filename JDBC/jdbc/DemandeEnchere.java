package jdbc;

import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.*;

public class DemandeEnchere{
    private Connection con;
    private String emailAcheteur;


    public DemandeEnchere(Connection con, String emailAcheteur){

        /*
         * Cette fonction effectue une demande d'enchère sur une vente.
         *
         * /!\ : Pour l'instant, cette fonction ne s'occupe que des enchères montantes.
         */

        // On demande une offre à l'acheteur en vérifiant que le nombre soit correct :
        boolean nombreCorrect = False;

        while (!nombreCorrect) {
            System.out.println("Veuillez saisir votre offre pour ce produit : ");
            String offre = sc.nextLine();
            nombreCorrect = True;
            try{
                int prix = Integer.parseInt(offre);
            } catch (Exception e) {
                System.out.println("ERREUR : Vous avez saisi un nombre incorrect!");
                nombreCorrect = False;
            }
        }

        // TODO : Vérifier que l'offre soit correcte (qu'il n'y a pas d'offre supérieure)

        // TODO : Insérer l'offre dans la table:
        


    }
}
