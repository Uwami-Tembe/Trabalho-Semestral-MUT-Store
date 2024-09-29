package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TelaLogin {
    private Stage primaryStage;

    public TelaLogin(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void changeScene(String fxml) throws Exception {
        if (primaryStage == null) {
            throw new IllegalStateException("PrimaryStage não inicializado!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
