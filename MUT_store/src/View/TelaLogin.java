
package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaLogin extends Application {
    public FXMLLoader fxmlLoader;
    private static Stage primaryStage;
    @Override
    public void start(Stage primaryStage){
        TelaLogin.primaryStage=primaryStage;
        try{
        fxmlLoader = new FXMLLoader(getClass().getResource("LoginDesign.fxml"));
        Parent root = fxmlLoader.load();
        Scene telaLogin = new Scene(root);
        
        primaryStage.setTitle("Login");
        primaryStage.setScene(telaLogin);
        primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    
    public static void changeScene (String fxml) throws Exception{
        Parent root = FXMLLoader.load(TelaLogin.class.getResource(fxml));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
