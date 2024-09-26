package Controller;

import Model.AppModel;
import View.MainStage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MenuPrincipalController {

    @FXML
    private Button bt_criarApp;

    @FXML
    private Button bt_definicoes;

    @FXML
    private Button bt_loja;

    @FXML
    private Button bt_sair;

    @FXML
    private Button bt_sobre;

    @FXML
    private ImageView img_pesquisar;

    @FXML
    private Label lb_NomeDoUsuario;

    @FXML
    public FlowPane panel_apps = new FlowPane();

    @FXML
    private ScrollPane sp_apps;

    @FXML
    private TextField txt_pesquisa;

    public static Scene currentScene;

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) throws IOException{
            MainStage.resetScene("CriarApp", "CriarApp.fxml");
            MainStage.changeScene("CriarApp");

    }
    @FXML
    public void updateMenu(){

       for(AppModel app: CriarAppController.appList){
            VBox appBox = new VBox();
            appBox.setSpacing(20);
            ImageView formatedImage= new ImageView();            
            formatedImage.setImage(app.getIconImage().getImage());
            formatedImage.setFitWidth(80);
            formatedImage.setFitHeight(80);
            
            Label appName = new Label(app.getNome());
            appName.setAlignment(Pos.CENTER);
            appName.setLayoutX(appBox.getLayoutX());
            appName.setLayoutY(appBox.getLayoutY()+5);
            appName.setStyle("-fx-text-fill: #517983");
            appName.setFont(Font.font("System",javafx.scene.text.FontWeight.BOLD,14));
            
            appBox.getChildren().addAll(formatedImage, appName);
            
            appBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent t){
                    try { 
                       
                        MainStage.resetScene("TelaDownload","FazerDownload.fxml");
                       MainStage.changeScene("TelaDownload");
                       MainStage.createDownloadPage(app);
    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            
            panel_apps.getChildren().addAll(appBox);
        }
   }
    
    
    @FXML
    void On_bt_Loja_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
        
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws Exception {
        MainStage.goTo("TelaLogin");
    }

    @FXML
    void On_img_pesquisar_click(MouseEvent event) {

    }

}
