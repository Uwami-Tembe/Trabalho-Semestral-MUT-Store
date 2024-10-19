package Controller;

import View.MainStage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FazerDownloadController {

    @FXML
    private Button bt_Baixar;

    @FXML
    private Button bt_Comentar;

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
    private ProgressBar downloadBar;

    @FXML
    private ImageView img_icon;

    @FXML
    private ImageView img_shot_1;

    @FXML
    private ImageView img_shot_2;

    @FXML
    private ImageView img_shot_3;

    @FXML
    private ImageView img_shot_4;

    @FXML
    private Label lb_Descricao;

    @FXML
    private Label lb_Descricao1;

    @FXML
    private Label lb_DescricaoLonga;

    @FXML
    private Label lb_Nome;

    @FXML
    private Label lb_politics;

    @FXML
    private Label lb_developerName;

    @FXML
    private Label lb_politicsLongo;

    @FXML
    public Label lb_preco;

    @FXML
    private Label lb_preco1;

    @FXML
    public  VBox panel_comentarios;

    @FXML
    private Pane panel_icon;

    @FXML
    private Pane panel_shot_1;

    @FXML
    private Pane panel_shot_2;

    @FXML
    private Pane panel_shot_3;

    @FXML
    private Pane panel_shot_4;

    @FXML
    public TextArea txt_comentar;
    
    @FXML
    private AnchorPane fazerDownloadPanel;
    
    private double progress =0;
    public static boolean haveToPay=false;
    public static boolean useCard=false;
    public static boolean useWallet=false;
       


    
    public void loadDownloadPageContent(ImageView icon,String nome, float preco, ImageView shot_1,ImageView shot_2,
                                        ImageView shot_3,ImageView shot_4,String description, String politics, 
                                        String develoerName)
    
    {
        img_icon.setImage(icon.getImage());
        lb_Nome.setText(nome);
        img_shot_1.setImage(shot_1.getImage());
        img_shot_2.setImage(shot_2.getImage());
        img_shot_3.setImage(shot_3.getImage());
        img_shot_4.setImage(shot_4.getImage());
        lb_DescricaoLonga.setText(description);
        lb_politicsLongo.setText(politics);
        lb_developerName.setText(develoerName);
        
        img_icon.setOpacity(1.0);
        img_shot_1.setOpacity(1.0);
        img_shot_2.setOpacity(1.0);
        img_shot_3.setOpacity(1.0);
        img_shot_4.setOpacity(1.0);
       
        if(preco==0.0f){
             lb_preco.setText("Grátis");
             
        }
        else
            lb_preco.setPrefWidth(224);
            lb_preco.setText(Float.toString(preco)+" MZN");
    }
    
    
    @FXML
    void On_add_icon_click(MouseEvent event) {

    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) throws  Exception{
        MainStage.goTo("MenuPrincipal");
 
        
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
         MainStage.goTo("Sobre");
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {
        MainStage.goTo("Settings");
    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws IOException {
        MainStage.resetScene("TelaLogin", "LoginDesign.fxml");
        MainStage.goTo("TelaLogin");
    }
       /* Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8),e->{
        
            progress+=0.1;
            downloadBar.setProgress(progress);
            if(progress>=1){
                ((Timeline)e.getSource()).stop();
            
            downloadBar.setVisible(false);
            Label instalado = new Label("instalado");
            instalado.setFont(Font.font("System",javafx.scene.text.FontWeight.BOLD,14));
            fazerDownloadPanel.getChildren().add(instalado);
            instalado.setLayoutX(bt_Baixar.getLayoutX());
            instalado.setLayoutY(bt_Baixar.getLayoutY());
            }  
        }));*/
   
        @FXML
    void On_bt_baixar_pressed(ActionEvent event) throws IOException {
        
       if(haveToPay){

           MainStage.resetScene("PayMetodo", "PayMetodo.fxml");
           MainStage.goTo("PayMetodo");
           System.out.println("card " + useCard);
           System.out.println("wallet " + useWallet);
           MainStage.showAvaliablePayMethods(useCard, useWallet);
       }
       /*Timeline timeline = new Timeline();
       KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.8),e->{
                       progress+=0.1;
            downloadBar.setProgress(progress);
            if(progress>=1){
                ((Timeline)e.getSource()).stop();
            
            downloadBar.setVisible(false);
            Label instalado = new Label("instalado");
            instalado.setFont(Font.font("System",javafx.scene.text.FontWeight.BOLD,14));
            fazerDownloadPanel.getChildren().add(instalado);
            instalado.setLayoutX(bt_Baixar.getLayoutX());
            instalado.setLayoutY(bt_Baixar.getLayoutY());
            }  
       });
       timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        progress=0;
        bt_Baixar.setVisible(false);
        downloadBar= new ProgressBar();
        downloadBar.setVisible(true);
        fazerDownloadPanel.getChildren().add(downloadBar);  
        downloadBar.setPrefSize(bt_Baixar.getPrefWidth(),bt_Baixar.getPrefHeight()-15);
        downloadBar.setLayoutX(bt_Baixar.getLayoutX());
        downloadBar.setLayoutY(bt_Baixar.getLayoutY());
        downloadBar.setStyle("-fx-border-radius:20");
        downloadBar.setProgress(progress);
        timeline.playFromStart();*/

    }
    
    @FXML
    void On_bt_comentar_pressed(){
        
        //MainStage.getUserComment();
        
    }

}
