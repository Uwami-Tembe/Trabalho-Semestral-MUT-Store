package Controller;

import Model.AppModel;
import View.MainStage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

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
    public TilePane panel_apps = new TilePane();
    
    @FXML
    private TilePane panel_best = new TilePane();

    @FXML
    public TilePane panel_games = new TilePane();

    @FXML
    private ScrollPane sp_best;

    @FXML
    private ScrollPane sp_games;

    @FXML
    private ScrollPane sp_apps;

    @FXML
    private TextField txt_pesquisa;
    @FXML
    private Pane panel_profile;

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) throws IOException{
            MainStage.resetScene("CriarApp", "CriarApp.fxml");
            MainStage.defineGroupForNewAppRadios();
            MainStage.goTo("CriarApp");
    }
    
    @FXML
    void on_panel_profile_click(MouseEvent event) throws IOException {
        MainStage.resetScene("Perfil", "Perfil.fxml");
        MainStage.goTo("Perfil");
    }
    @FXML
    public void updateMenu(List<AppModel>appList){

       for(AppModel app: appList){
            
            panel_games.setPrefColumns(2);
            VBox appBox = new VBox();
            appBox.setSpacing(15);
            ImageView formatedImage= new ImageView();            
            formatedImage.setImage(app.getIconImage().getImage());
            formatedImage.setFitWidth(50);
            formatedImage.setFitHeight(50);
            Label appName = new Label(app.getNome());
            appName.setLayoutX(appBox.getLayoutX());
            appName.setLayoutY(appBox.getLayoutY());
            appName.setStyle("-fx-text-fill: #517983");
            appName.setFont(Font.font("System",javafx.scene.text.FontWeight.BOLD,11));
            
            appBox.getChildren().addAll(formatedImage, appName);
            appBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent t){
                    try { 
                       
                       MainStage.resetScene("TelaDownload","FazerDownload.fxml");
                       MainStage.goTo("TelaDownload");
                       //MainStage.showComments();
                       MainStage.createDownloadPage(app);
    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            
            
            if(app.getPreco()>0){
                FazerDownloadController.haveToPay=true;
                    if(app.useCard()){
                        FazerDownloadController.useCard=true;
                    }
                    if(app.useWallet()){
                        FazerDownloadController.useWallet=true;
                    }
            }
            if(app.getCategoria().equals("Jogo")){
                panel_games.getChildren().addAll(appBox);
            }
            else{
               panel_apps.getChildren().addAll(appBox);
            }
        }
   }
    
    public static int searchAppByPrefix(List<AppModel>appList,String prefix){
        
        int begin =0;
        int end = CriarAppController.appList.size()-1;
        String key= prefix;
      
        while(begin<=end){
       
        int mid = begin+(end-begin)/2;
        
        if(CriarAppController.appList.get(mid).getNome().startsWith(key)){
            if(mid==0 || !CriarAppController.appList.get(mid-1).getNome().toLowerCase().startsWith(key)){
                return mid;
            }  
            
            else{
                end=mid-1;
            }
        }
        
        else if(CriarAppController.appList.get(mid).getNome().compareTo(key)<0){
            begin= mid+1;
        }
        
        else{
            end= mid-1;
            }
        }
      return -1;
    }
    
    public static List<AppModel>searchAppListByPrefix(List<AppModel>appList,String prefix){
        List<AppModel>all_apps_with_the_prefix=new ArrayList<>();
        
        int index = searchAppByPrefix(appList, prefix);
        
        if(index<0){
            return all_apps_with_the_prefix;
        }
        
        for(int i = index; i<=appList.size()-1; i++){
            if(appList.get(i).getNome().startsWith(prefix)){
                all_apps_with_the_prefix.add(appList.get(i));
            }
            else{
                break;
            }
        }
        return all_apps_with_the_prefix;
    }
    
    
    @FXML
    void On_bt_Loja_pressed(ActionEvent event) {
        updateMenu(CriarAppController.appList);
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
        
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws Exception {
        MainStage.resetScene("TelaLogin", "LoginDesign.fxml");
        MainStage.goTo("TelaLogin");
    }

    @FXML
    void On_img_pesquisar_click(MouseEvent event) throws IOException {
        
        Collections.sort(CriarAppController.appList);
        String prefix = txt_pesquisa.getText();
        List<AppModel>result =searchAppListByPrefix(CriarAppController.appList, prefix);
        
        if(result.isEmpty()){
            MainStage.goTo("Carregando");
            
                     PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     MainStage.resetScene("MenuPrincipal", "MenuPrincipal.fxml");
                     MainStage.goTo("MenuPrincipal");
                     VBox messageBox= new VBox();
                     Label notFound = new Label("Nenhuma aplicação encontrada");
                     notFound.setFont(Font.font("System",javafx.scene.text.FontWeight.BOLD,25));
                     notFound.setPrefHeight(400);
                     notFound.setPrefWidth(200);
                     messageBox.getChildren().add(notFound);
                     
                     panel_apps.getChildren().add(messageBox);
                 
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
        
        else{
         MainStage.changeScene("Carregando");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                      MainStage.resetScene("MenuPrincipal", "MenuPrincipal.fxml");
                      MainStage.goTo("MenuPrincipal");
                      MainStage.actualizarMenu(result);     
                 
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
    }
    
    

}
