
package View;

import Controller.CriarAppController;
import Controller.MenuPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainStage extends Application {
    public FXMLLoader LoginLoader;
    public FXMLLoader menuLoader;
    public FXMLLoader uploadLoader;
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage){
        MainStage.primaryStage=primaryStage;
        try{
        LoginLoader = new FXMLLoader(getClass().getResource("LoginDesign.fxml"));
        Parent root = LoginLoader.load();
        
        Scene telaLogin = new Scene(root);
        primaryStage.setTitle("MUT Store");
        primaryStage.setScene(telaLogin);
        primaryStage.show();
        
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    

    public static Object changeScene (String fxml) throws Exception{
        FXMLLoader loader = new FXMLLoader(MainStage.class .getResource(fxml));
        Parent root =loader.load();
        Object controller = loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        return controller;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
