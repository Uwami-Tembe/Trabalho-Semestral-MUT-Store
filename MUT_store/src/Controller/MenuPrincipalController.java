package Controller;

import Model.AppModel;
import Model.ExternalAppModel;
import Models.Api.App;
import View.MainStage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

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
    
    FazerDownloadController f = new FazerDownloadController();
    
    public void setFazerDownloadController (FazerDownloadController f){
        this.f = f;
    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event)  {

          try {
             MainStage.changeScene("CriarApp.fxml");
          } catch (Exception ex) {
             ex.printStackTrace();
             }
 
    }
    @FXML
    public void updateMenu(){
        panel_apps.setHgap(20);
        panel_apps.setVgap(30);
        panel_apps.setPrefWrapLength(4);
                
       for(AppModel app: CriarAppController.appList){
            VBox appBox = new VBox();
            appBox.setSpacing(20);
            ImageView formatedImage= new ImageView();            
            formatedImage.setImage(app.getIconImage().getImage());
            formatedImage.setFitWidth(80);
            formatedImage.setFitHeight(80);
            
            Label appName = new Label(app.getNome());
            //appName.setAlignment(Pos.CENTER);
            appName.setLayoutX(appBox.getLayoutX());
            appName.setLayoutY(appBox.getLayoutY()+5);
            appName.setStyle("-fx-text-fill: #517983");
            appName.setFont(Font.font("System",javafx.scene.text.FontWeight.BOLD,14));
            
            appBox.getChildren().addAll(formatedImage, appName);
            
            appBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent t){
                    try {
                       f=(FazerDownloadController)MainStage.changeScene("FazerDownload.fxml");
                        setFazerDownloadController(f);
                        f.loadDownloadPageContent(app.getIconImage(), app.getNome(),app.getPreco(),
                                app.getShot1Image(), app.getShot2Image(), app.getShot3Image(), app.getShot4Image(), app.getDescription(), app.getPolitics(), app.getDeveloperName());
    
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
    panel_apps.setHgap(20);
    panel_apps.setVgap(30);
    panel_apps.setPrefWrapLength(4);
    
    // Limpa os aplicativos existentes antes de adicionar novos
    panel_apps.getChildren().clear();
    
    // Busca os aplicativos da API
    List<ExternalAppModel> appList = App.buscarApps();
    
    // Verifica se a lista de aplicativos está vazia
    if (appList.isEmpty()) {
        // Se a lista estiver vazia, exibe um alerta
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText("Não foi possível buscar os aplicativos.");
        alert.showAndWait();
        return; // Sai do método se não houver aplicativos
    }
    
    // Adiciona os aplicativos encontrados ao painel
    for (ExternalAppModel app : appList) {
        VBox appBox = new VBox();
        appBox.setSpacing(20);
        
        // Carrega a imagem do ícone do aplicativo
        ImageView formattedImage = new ImageView(app.getIcon());
        formattedImage.setFitWidth(80);
        formattedImage.setFitHeight(80);
        formattedImage.setPreserveRatio(true); // Mantém a proporção da imagem
        
        // Cria o rótulo do nome do aplicativo
        Label appName = new Label(app.getNome());
        appName.setStyle("-fx-text-fill: #517983");
        appName.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        // Adiciona a imagem e o nome ao VBox
        appBox.getChildren().addAll(formattedImage, appName);
        
        // Adiciona o VBox ao painel de aplicativos
        panel_apps.getChildren().add(appBox);
    }
}



    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
        
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws Exception {
                         MainStage.changeScene("Carregando.fxml");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     MainStage.changeScene("LoginDesign.fxml");
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
         }
         catch(Exception e){
             e.printStackTrace();
         }
         pause.play();
    }

    @FXML
    void On_img_pesquisar_click(MouseEvent event) {

    }

}