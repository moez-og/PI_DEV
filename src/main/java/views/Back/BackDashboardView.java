package views.Back;

<<<<<<< HEAD
public class BackDashboardView {
}
=======
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import utils.BaseLayout;
import utils.SceneManager;
import views.Front.FrontDashboardView;

public class BackDashboardView extends BaseLayout {

    public BackDashboardView() {
        super("Back • Dashboard");
        setUser("Hayfa Touzi", "ADMIN");

        // ===== Menu =====
        Button btnDashboard = addNavButton("Dashboard", true);
        Button btnSorties   = addNavButton("Sorties", false);
        Button btnLieux     = addNavButton("Lieux", false);
        Button btnOffres    = addNavButton("Offres", false);
        Button btnEvents    = addNavButton("Événements", false);

        Button btnGoFront   = addNavButton("Aller vers Front", false);

        // ===== Contenu par défaut =====
        setCenterNodes(kpiRow(), card("Espace Back", "Gestion complète : CRUD, modération, statistiques."));

        // ===== Actions =====
        btnDashboard.setOnAction(e ->
                setCenterNodes(kpiRow(), card("Espace Back", "Gestion complète : CRUD, modération, statistiques."))
        );

        // ✅ BRANCHEMENT vers la gestion Sorties (TableView + CRUD)
        btnSorties.setOnAction(e -> setCenterNodes(new SortieBackView()));

        // placeholders (tu brancheras tes vues plus tard)
        btnLieux.setOnAction(e ->
                setCenterNodes(kpiRow(), card("Lieux (Back)", "Branche ici : LieuBackView (CRUD)."))
        );

        btnOffres.setOnAction(e ->
                setCenterNodes(kpiRow(), card("Offres (Back)", "Branche ici : OffreBackView (CRUD)."))
        );

        btnEvents.setOnAction(e ->
                setCenterNodes(kpiRow(), card("Événements (Back)", "Branche ici : EvenementBackView (CRUD)."))
        );

        btnGoFront.setOnAction(e -> SceneManager.show(new FrontDashboardView()));

        // Refresh global
        btnRefresh.setOnAction(e -> {
            // Si tu veux : détecter la vue active et appeler reload()
            setCenterNodes(kpiRow(), card("Refresh ✅", "Branche ici le reload global si nécessaire."));
        });
    }

    // ===== KPI Row =====
    private HBox kpiRow() {
        VBox k1 = kpi("Total", "—", "Eléments gérés");
        VBox k2 = kpi("En cours", "—", "Validations");
        VBox k3 = kpi("Alertes", "—", "Points à traiter");

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
