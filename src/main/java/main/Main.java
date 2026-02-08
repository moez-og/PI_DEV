package main;

import utils.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        // Création de l'objet DBConnection
        DBConnection db = new DBConnection();

        // Récupérer la connexion
        Connection cnx = db.getConnection();

        // Vérification
        if (cnx != null) {
            System.out.println("✅ Connexion réussie !");
        } else {
            System.out.println("❌ Connexion échouée !");
        }
    }
}