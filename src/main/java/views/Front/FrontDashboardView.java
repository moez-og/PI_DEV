package views.Front;

<<<<<<< HEAD
public class FrontDashboardView {
}
=======
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import utils.BaseLayout;
import utils.SceneManager;
import views.Back.BackDashboardView;

public class FrontDashboardView extends BaseLayout {

    public FrontDashboardView() {
        super("Front • Accueil");
        setUser("Hayfa Touzi", "ABONNE");

        Button btnAccueil = addNavButton("Accueil", true);
        Button btnExplorer = addNavButton("Explorer", false);
        Button btnMesDemandes = addNavButton("Mes participations", false);
        Button btnProfil = addNavButton("Profil", false);
        Button btnGoBack = addNavButton("Aller vers Back", false);

        setCenterNodes(kpiRow(), card("Bienvenue 👋", "Front : explorer, filtrer, participer, favoris."));

        btnAccueil.setOnAction(e -> setCenterNodes(kpiRow(), card("Bienvenue 👋", "Front : explorer, filtrer, participer, favoris.")));
        btnExplorer.setOnAction(e -> setCenterNodes(kpiRow(), card("Explorer", "Liste des sorties, lieux, offres, événements (cards).")));
        btnMesDemandes.setOnAction(e -> setCenterNodes(kpiRow(), card("Mes participations", "Suivi des demandes envoyées + statut.")));
        btnProfil.setOnAction(e -> setCenterNodes(kpiRow(), card("Profil", "Compte, abonnement, préférences.")));

        btnGoBack.setOnAction(e -> SceneManager.show(new BackDashboardView()));

        btnRefresh.setOnAction(e -> setCenterNodes(kpiRow(), card("Refresh ✅", "Données rechargées (branche service ici).")));
    }

    private HBox kpiRow() {
        VBox k1 = kpi("Sorties proches", "5", "Aujourd’hui / demain");
        VBox k2 = kpi("Offres actives", "12", "Réservées aux abonnés");
        VBox k3 = kpi("Événements", "3", "Cette semaine");

        HBox row = new HBox(14, k1, k2, k3);
        HBox.setHgrow(k1, Priority.ALWAYS);
        HBox.setHgrow(k2, Priority.ALWAYS);
        HBox.setHgrow(k3, Priority.ALWAYS);
        return row;
    }

    private VBox kpi(String label, String value, String hint) {
        Label l = new Label(label);
        l.getStyleClass().add("muted");
        l.setStyle("-fx-font-weight: 900;");

        Label v = new Label(value);
        v.getStyleClass().add("title");
        v.setStyle("-fx-font-size: 28px;");

        Label h = new Label(hint);
        h.getStyleClass().add("muted");

        VBox box = new VBox(6, l, v, h);
        box.getStyleClass().add("card");
        box.setStyle("-fx-padding: 14;");
        return box;
    }
}
>>>>>>> db5ee53 (Template)
