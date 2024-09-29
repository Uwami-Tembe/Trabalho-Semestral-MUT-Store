package View;

import static Constants.Constants.TOKEN_FILE_PATH;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        MainStage.primaryStage = primaryStage;  // Inicializa o primaryStage

        try {
            String tokenFilePath = TOKEN_FILE_PATH; // Substitua pelo caminho real do seu arquivo de token
            File tokenFile = new File(tokenFilePath);

            if (tokenFile.exists() && tokenFile.length() > 0) {
                // Se o arquivo de token existir e não estiver vazio, carregue a MainPage
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
                Parent root = loader.load();
                Scene mainPageScene = new Scene(root);
                primaryStage.setTitle("MUT Store - Menu Principal Page");
                primaryStage.setScene(mainPageScene);
            } else {
                // Caso contrário, carregue a tela de login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginDesign.fxml"));
                Parent root = loader.load();
                Scene loginScene = new Scene(root);
                primaryStage.setTitle("MUT Store - Login");
                primaryStage.setScene(loginScene);
            }
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


public static <T> T changeScene(String fxml) throws Exception {
    FXMLLoader loader = new FXMLLoader(MainStage.class.getResource(fxml));
    Parent root = loader.load();
    T controller = loader.getController(); // Obtem o controlador
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    return controller; // Retorna o controlador
}


    public static void main(String[] args) {
        launch(args);
    }
}
