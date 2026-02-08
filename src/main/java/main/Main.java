package main;

<<<<<<< HEAD
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
=======
import javafx.application.Application;
import javafx.stage.Stage;
import utils.SceneManager;
import views.Back.BackDashboardView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.init(stage);
        SceneManager.show(new BackDashboardView());
        stage.setTitle("Fin Tokhroj");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
>>>>>>> db5ee53 (Template)
    }
}