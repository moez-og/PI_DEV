package utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BaseLayout extends BorderPane {

    private static final String LOGO_PATH = "/images/logo.png";

    protected final VBox sidebar = new VBox(14);
    protected final ScrollPane sidebarScroll = new ScrollPane(sidebar);

    protected final HBox topbar = new HBox(12);
    protected final Label topTitle = new Label();
    protected final TextField searchField = new TextField();
    protected final Button btnRefresh = new Button("Refresh");

    private final Label profileName = new Label("Utilisateur");
    private final Label profileRole = new Label("ROLE");

    protected final VBox content = new VBox(14);
    protected final ScrollPane centerScroll = new ScrollPane(content);

    public BaseLayout(String title) {
        getStyleClass().add("root-bg");
        buildSidebar();
        buildTopbar(title);
        buildCenter();
        getStylesheets().add(inlineCssUrl(cssRaw()));
    }

    private void buildSidebar() {
        sidebar.setPadding(new Insets(18));
        sidebar.setPrefWidth(280);
        sidebar.getStyleClass().add("sidebar");

        ImageView logo = loadLogo(54);

        Label appName = new Label("Fin Tokhroj");
        appName.getStyleClass().add("app-name");

        Label sub = new Label("Votre Guide Social");
        sub.getStyleClass().add("app-sub");

        VBox brandText = new VBox(2, appName, sub);
        brandText.setAlignment(Pos.CENTER_LEFT);

        HBox brand = new HBox(12, logo, brandText);
        brand.setAlignment(Pos.CENTER_LEFT);
        brand.getStyleClass().add("brand");

        Label section = new Label("MENU");
        section.getStyleClass().add("section-title");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label footer = new Label("© Fin Tokhroj • JavaFX");
        footer.getStyleClass().add("muted");

        sidebar.getChildren().addAll(brand, section, spacer, footer);

        sidebarScroll.setFitToWidth(true);
        sidebarScroll.setFitToHeight(true);
        sidebar.minHeightProperty().bind(sidebarScroll.heightProperty());
        sidebarScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sidebarScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sidebarScroll.getStyleClass().add("sidebar-scroll");

        setLeft(sidebarScroll);
    }

    private void buildTopbar(String title) {
        topbar.setPadding(new Insets(14, 18, 14, 18));
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.getStyleClass().add("topbar");

        topTitle.setText(title);
        topTitle.getStyleClass().add("title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        searchField.setPromptText("Rechercher...");
        searchField.getStyleClass().add("search");

        btnRefresh.getStyleClass().add("btn-refresh");

        profileName.getStyleClass().add("profile-name");
        profileRole.getStyleClass().add("chip");

        VBox profileBox = new VBox(2, profileName, profileRole);
        profileBox.setAlignment(Pos.CENTER_RIGHT);

        topbar.getChildren().addAll(topTitle, spacer, searchField, btnRefresh, profileBox);
        setTop(topbar);
    }

    private void buildCenter() {
        content.setPadding(new Insets(18));
        content.getStyleClass().add("content");

        centerScroll.getStyleClass().add("center-scroll");
        centerScroll.setFitToWidth(true);
        centerScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        centerScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        setCenter(centerScroll);
    }

    protected Button addNavButton(String text, boolean active) {
        Button b = new Button(text);
        b.setMaxWidth(Double.MAX_VALUE);
        b.getStyleClass().add("nav-btn");
        if (active) b.getStyleClass().add("nav-active");

        // insert avant spacer/footer (les 2 derniers nodes)
        int insertIndex = Math.max(2, sidebar.getChildren().size() - 2);
        sidebar.getChildren().add(insertIndex, b);
        return b;
    }

    protected void setUser(String name, String role) {
        profileName.setText(name);
        profileRole.setText(role);
    }

    protected void setCenterNodes(Node... nodes) {
        content.getChildren().setAll(nodes);
    }

    protected VBox card(String title, String subtitle) {
        Label t = new Label(title);
        t.getStyleClass().add("title");
        t.setStyle("-fx-font-size: 18px;");

        Label s = new Label(subtitle);
        s.getStyleClass().add("muted");

        VBox box = new VBox(8, t, s);
        box.getStyleClass().add("card");
        box.setPadding(new Insets(16));
        return box;
    }

    private ImageView loadLogo(double size) {
        ImageView iv = new ImageView();
        iv.setFitWidth(size);
        iv.setFitHeight(size);
        iv.setPreserveRatio(true);

        try {
            URL url = getClass().getResource(LOGO_PATH);
            if (url != null) {
                iv.setImage(new Image(url.toExternalForm(), true));
                DropShadow ds = new DropShadow();
                ds.setRadius(18);
                ds.setSpread(0.12);
                iv.setEffect(ds);
            }
        } catch (Exception ignored) {}
        return iv;
    }

    private static String inlineCssUrl(String css) {
        String encoded = URLEncoder.encode(css, StandardCharsets.UTF_8).replace("+", "%20");
        return "data:text/css," + encoded;
    }

    private String cssRaw() {
        return """
        .root-bg{
            -fx-font-family: "Segoe UI", "Inter", Arial;
            -fx-background-color: linear-gradient(to bottom right, #f6f9ff, #e8e8e8);
        }

        .center-scroll{
            -fx-background-color: transparent;
            -fx-background: transparent;
        }
        .center-scroll .viewport{ -fx-background-color: transparent; }

        .sidebar-scroll{
            -fx-background-color: linear-gradient(to bottom, #2a467c, #0f4662);
            -fx-background: linear-gradient(to bottom, #2a467c, #0f4662);
            -fx-border-width: 0;
            -fx-padding: 0;
        }
        .sidebar-scroll .viewport{ -fx-background-color: transparent; }

        .sidebar{
            -fx-background-color: transparent;
            -fx-effect: dropshadow(gaussian, rgba(15,70,98,0.30), 30, 0.20, 0, 10);
        }

        .brand{
            -fx-padding: 14;
            -fx-background-radius: 18;
            -fx-border-radius: 18;
            -fx-background-color: rgba(255,255,255,0.96);
            -fx-border-color: rgba(255,255,255,0.35);
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 16, 0.18, 0, 6);
        }

        .app-name{
            -fx-text-fill: #0f4662;
            -fx-font-size: 18px;
            -fx-font-weight: 900;
        }
        .app-sub{
            -fx-text-fill: rgba(15,70,98,0.75);
            -fx-font-size: 12px;
            -fx-font-weight: 800;
        }

        .section-title{
            -fx-text-fill: rgba(232,232,232,0.85);
            -fx-font-size: 11px;
            -fx-font-weight: 900;
        }

        .nav-btn{
            -fx-background-color: rgba(255,255,255,0.14);
            -fx-text-fill: white;
            -fx-font-weight: 900;
            -fx-background-radius: 14;
            -fx-padding: 12 14;
            -fx-cursor: hand;
            -fx-border-color: rgba(255,255,255,0.16);
            -fx-border-radius: 14;
        }
        .nav-btn:hover{
            -fx-background-color: rgba(255,255,255,0.22);
            -fx-translate-x: 2;
        }
        .nav-active{
            -fx-background-color: rgba(255,255,255,0.26);
            -fx-border-color: rgba(204,134,65,0.70);
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 14, 0.18, 0, 6);
        }

        .topbar{
            -fx-background-color: rgba(255,255,255,0.96);
            -fx-border-color: rgba(42,70,124,0.14);
            -fx-border-width: 0 0 1 0;
            -fx-effect: dropshadow(gaussian, rgba(15,70,98,0.08), 18, 0.20, 0, 8);
        }

        .title{
            -fx-text-fill: #0f4662;
            -fx-font-size: 24px;
            -fx-font-weight: 900;
        }

        .search{
            -fx-min-width: 420;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            -fx-border-color: rgba(42,70,124,0.20);
            -fx-background-color: rgba(232,232,232,0.65);
            -fx-text-fill: #0f4662;
            -fx-prompt-text-fill: rgba(121,148,160,0.95);
            -fx-padding: 11 14;
        }

        .profile-name{
            -fx-text-fill: #2a467c;
            -fx-font-weight: 900;
        }
        .chip{
            -fx-background-color: rgba(204,134,65,0.18);
            -fx-text-fill: rgba(204,134,65,0.98);
            -fx-padding: 6 12;
            -fx-background-radius: 999;
            -fx-font-weight: 900;
            -fx-font-size: 11px;
        }

        .card{
            -fx-background-color: rgba(255,255,255,0.98);
            -fx-background-radius: 16;
            -fx-border-radius: 16;
            -fx-border-color: rgba(42,70,124,0.12);
            -fx-effect: dropshadow(gaussian, rgba(15,70,98,0.10), 22, 0.18, 0, 10);
        }

        .muted{
            -fx-text-fill: rgba(121,148,160,0.95);
            -fx-font-size: 11px;
            -fx-font-weight: 800;
        }

        .btn-refresh{
            -fx-background-color: rgba(232,232,232,0.85);
            -fx-text-fill: #0f4662;
            -fx-font-weight: 900;
            -fx-background-radius: 14;
            -fx-padding: 11 16;
            -fx-cursor: hand;
            -fx-border-color: rgba(42,70,124,0.16);
            -fx-border-radius: 14;
        }
        """;
    }
}