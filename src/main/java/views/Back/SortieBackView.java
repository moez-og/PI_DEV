package views.Back;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene; // ✅ AJOUT
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.AnnonceSortie;
import services.AnnonceSortieService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class SortieBackView extends VBox {

    private final AnnonceSortieService service = new AnnonceSortieService();

    private final ObservableList<AnnonceSortie> master = FXCollections.observableArrayList();
    private final FilteredList<AnnonceSortie> filtered = new FilteredList<>(master, a -> true);

    private TableView<AnnonceSortie> table;
    private TextField searchField;

    private Label kpiTotal, kpiOpen, kpiNearest;

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public SortieBackView() {
        setSpacing(14);
        setPadding(new Insets(0));

        HBox kpis = buildKpis();
        VBox cardTable = buildTableCard();

        getChildren().addAll(kpis, cardTable);

        reload();
    }

    private HBox buildKpis() {
        kpiTotal = new Label("—");
        kpiOpen = new Label("—");
        kpiNearest = new Label("—");

        VBox c1 = kpiCard("Total sorties", kpiTotal, "Nombre total d'annonces");
        VBox c2 = kpiCard("OUVERTES", kpiOpen, "Annonces ouvertes");
        VBox c3 = kpiCard("Plus proche", kpiNearest, "Date future la plus proche");

        HBox row = new HBox(14, c1, c2, c3);
        HBox.setHgrow(c1, Priority.ALWAYS);
        HBox.setHgrow(c2, Priority.ALWAYS);
        HBox.setHgrow(c3, Priority.ALWAYS);
        return row;
    }

    private VBox kpiCard(String title, Label value, String hint) {
        Label t = new Label(title);
        t.getStyleClass().add("muted");

        value.getStyleClass().add("title");
        value.setStyle("-fx-font-size: 28px;");

        Label h = new Label(hint);
        h.getStyleClass().add("muted");

        VBox box = new VBox(6, t, value, h);
        box.getStyleClass().add("card");
        box.setStyle("-fx-padding: 14;");
        return box;
    }

    private VBox buildTableCard() {
        // top actions
        searchField = new TextField();
        searchField.setPromptText("Rechercher (titre / ville / lieu / type / statut)...");
        searchField.getStyleClass().add("search");
        searchField.textProperty().addListener((o, old, q) -> applyFilter(q));

        Button add = new Button("Ajouter");
        add.getStyleClass().add("btn-refresh");
        add.setOnAction(e -> openEditor(null));

        Button edit = new Button("Modifier");
        edit.getStyleClass().add("btn-refresh");
        edit.setOnAction(e -> {
            AnnonceSortie sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { warn("Sélectionne une sortie."); return; }
            openEditor(sel);
        });

        Button del = new Button("Supprimer");
        del.getStyleClass().add("btn-refresh");
        del.setOnAction(e -> {
            AnnonceSortie sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { warn("Sélectionne une sortie."); return; }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer ID=" + sel.getId() + " ?");
            confirm.setContentText("Action irréversible.");
            confirm.showAndWait().ifPresent(b -> {
                if (b == ButtonType.OK) {
                    service.delete(sel.getId());
                    reload();
                }
            });
        });

        HBox actions = new HBox(10, searchField, add, edit, del);
        actions.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        table = buildTable();

        VBox card = new VBox(12, actions, table);
        card.getStyleClass().add("card");
        card.setStyle("-fx-padding: 14;");
        return card;
    }

    private TableView<AnnonceSortie> buildTable() {
        TableView<AnnonceSortie> tv = new TableView<>(filtered);
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<AnnonceSortie, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMaxWidth(90);

        TableColumn<AnnonceSortie, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));

        TableColumn<AnnonceSortie, String> colVille = new TableColumn<>("Ville");
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));

        TableColumn<AnnonceSortie, String> colLieu = new TableColumn<>("Lieu");
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        TableColumn<AnnonceSortie, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("typeActivite"));

        TableColumn<AnnonceSortie, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        TableColumn<AnnonceSortie, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getDateSortie() == null ? "" : c.getValue().getDateSortie().format(DT_FMT)
        ));

        tv.getColumns().addAll(colId, colTitre, colVille, colLieu, colType, colStatut, colDate);

        tv.setRowFactory(tableView -> {
            TableRow<AnnonceSortie> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) openEditor(row.getItem());
            });
            return row;
        });

        tv.setFixedCellSize(36);
        var sizeBinding = Bindings.size(filtered);
        tv.prefHeightProperty().bind(
                Bindings.createDoubleBinding(() -> (sizeBinding.get() + 1.2) * tv.getFixedCellSize(), sizeBinding)
        );
        tv.setMinHeight(Region.USE_PREF_SIZE);
        tv.setMaxHeight(Region.USE_PREF_SIZE);

        return tv;
    }

    private void openEditor(AnnonceSortie existing) {
        boolean edit = existing != null;

        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle(edit ? "Modifier sortie" : "Ajouter sortie");

        VBox root = new VBox(12);
        root.setPadding(new Insets(16));

        TextField tfCreator = new TextField(edit ? String.valueOf(existing.getCreatorId()) : "1");
        TextField tfTitre = new TextField(edit ? existing.getTitre() : "");
        TextField tfVille = new TextField(edit ? existing.getVille() : "");
        TextField tfLieu = new TextField(edit ? existing.getLieu() : "");
        TextField tfType = new TextField(edit ? existing.getTypeActivite() : "");
        TextArea taDesc = new TextArea(edit ? existing.getDescription() : "");
        taDesc.setPrefRowCount(3);

        DatePicker dp = new DatePicker(edit && existing.getDateSortie() != null ? existing.getDateSortie().toLocalDate() : LocalDate.now().plusDays(1));

        Spinner<Integer> spH = new Spinner<>(0, 23, edit && existing.getDateSortie() != null ? existing.getDateSortie().getHour() : 18);
        Spinner<Integer> spM = new Spinner<>(0, 59, edit && existing.getDateSortie() != null ? existing.getDateSortie().getMinute() : 0);
        spH.setEditable(true);
        spM.setEditable(true);

        TextField tfBudget = new TextField(edit ? String.valueOf(existing.getBudgetMax()) : "25");
        Spinner<Integer> spPlaces = new Spinner<>(1, 50, edit ? existing.getNbPlaces() : 4);
        spPlaces.setEditable(true);

        ComboBox<String> cbStatut = new ComboBox<>(FXCollections.observableArrayList("OUVERTE", "CLOTUREE", "ANNULEE"));
        cbStatut.setValue(edit ? existing.getStatut() : "OUVERTE");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        int r = 0;
        grid.add(field("Creator ID", tfCreator), 0, r++, 2, 1);
        grid.add(field("Titre", tfTitre), 0, r++, 2, 1);
        grid.add(field("Ville", tfVille), 0, r++, 2, 1);
        grid.add(field("Lieu", tfLieu), 0, r++, 2, 1);
        grid.add(field("Type", tfType), 0, r++, 2, 1);
        grid.add(field("Description", taDesc), 0, r++, 2, 1);

        HBox time = new HBox(10, field("Date", dp), field("Heure", spH), field("Min", spM));
        grid.add(time, 0, r++, 2, 1);

        grid.add(field("Budget", tfBudget), 0, r);
        grid.add(field("Places", spPlaces), 1, r++);
        grid.add(field("Statut", cbStatut), 0, r++, 2, 1);

        ScrollPane formScroll = new ScrollPane(grid);
        formScroll.setFitToWidth(true);
        formScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        formScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Button save = new Button(edit ? "Enregistrer" : "Ajouter");
        save.getStyleClass().add("btn-refresh");

        Button cancel = new Button("Annuler");
        cancel.getStyleClass().add("btn-refresh");
        cancel.setOnAction(e -> dlg.close());

        HBox actions = new HBox(10, cancel, save);
        actions.setAlignment(Pos.CENTER_RIGHT);

        save.setOnAction(e -> {
            try {
                int creatorId = Integer.parseInt(tfCreator.getText().trim());
                String titre = tfTitre.getText().trim();
                String ville = tfVille.getText().trim();
                String lieu = tfLieu.getText().trim();
                String type = tfType.getText().trim();

                if (titre.isEmpty() || ville.isEmpty() || lieu.isEmpty() || type.isEmpty()) {
                    warn("Titre / Ville / Lieu / Type sont obligatoires.");
                    return;
                }

                double budget = Double.parseDouble(tfBudget.getText().trim());
                int places = spPlaces.getValue();
                LocalDateTime dateSortie = LocalDateTime.of(dp.getValue(), LocalTime.of(spH.getValue(), spM.getValue()));
                String statut = cbStatut.getValue();

                if (!edit) {
                    service.add(new AnnonceSortie(creatorId, titre, taDesc.getText(), ville, lieu, type, dateSortie, budget, places, statut));
                } else {
                    service.update(new AnnonceSortie(existing.getId(), creatorId, titre, taDesc.getText(), ville, lieu, type, dateSortie, budget, places, statut));
                }

                reload();
                dlg.close();
            } catch (Exception ex) {
                warn("Champs invalides: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(new Label(edit ? "Modifier sortie" : "Ajouter sortie"), formScroll, actions);

        dlg.setScene(new Scene(root, 560, 560));
        dlg.showAndWait();
    }

    private VBox field(String label, Control control) {
        Label l = new Label(label);
        VBox box = new VBox(6, l, control);
        return box;
    }

    private void reload() {
        master.setAll(service.getAll());
        applyFilter(searchField == null ? "" : searchField.getText());
        updateKpis();
    }

    private void updateKpis() {
        int total = master.size();
        long open = master.stream().filter(a -> "OUVERTE".equalsIgnoreCase(a.getStatut())).count();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nearestFuture = master.stream()
                .map(AnnonceSortie::getDateSortie)
                .filter(d -> d != null && d.isAfter(now))
                .min(Comparator.naturalOrder())
                .orElse(null);

        kpiTotal.setText(String.valueOf(total));
        kpiOpen.setText(String.valueOf(open));
        kpiNearest.setText(nearestFuture == null ? "—" : nearestFuture.format(DT_FMT));
    }

    private void applyFilter(String q) {
        String query = q == null ? "" : q.trim().toLowerCase();
        filtered.setPredicate(a -> query.isEmpty()
                || contains(a.getTitre(), query)
                || contains(a.getVille(), query)
                || contains(a.getLieu(), query)
                || contains(a.getTypeActivite(), query)
                || contains(a.getStatut(), query));
    }

    private boolean contains(String s, String q) {
        return s != null && s.toLowerCase().contains(q);
    }

    private void warn(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Fin Tokhroj");
        a.setHeaderText("Attention");
        a.setContentText(msg);
        a.showAndWait();
    }
}