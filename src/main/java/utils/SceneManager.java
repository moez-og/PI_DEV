package utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class SceneManager {
    private static Stage stage;

    private SceneManager() {}

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void show(Parent root) {
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.show();
    }
}