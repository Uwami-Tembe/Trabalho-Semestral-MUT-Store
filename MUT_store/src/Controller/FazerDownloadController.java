package Controller;

import Model.ExternalAppModel;
import Models.Api.App;
import View.MainStage;
import java.util.List;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class FazerDownloadController {

    @FXML
    private Button bt_Baixar;

    
    @FXML
    private ProgressBar progressBar;
            
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
    private Label statusLabel;

    @FXML
    private Label lb_developerName;

    @FXML
    private Label lb_politicsLongo;

    @FXML
    private Label lb_preco;

    @FXML
    private Label lb_preco1;

    @FXML
    private AnchorPane panel_comentarios;

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
    private TextArea txt_comentar;
    
    
    public String appfile; 

    public void loadDownloadPageContent(ExternalAppModel app) {
        ImageView formatedImage = new ImageView();
        img_icon.setImage(new Image(app.getIcon()));
        lb_Nome.setText(app.getNome());
        List<String> imagePaths = app.getImagePaths();  // Supondo que imagePaths é uma List<String>
         appfile = app.getAppFilePath();
        img_shot_1.setImage(new Image(imagePaths.get(0)));
        img_shot_2.setImage(new Image(imagePaths.get(1)));
        img_shot_3.setImage(new Image(imagePaths.get(2)));
        img_shot_4.setImage(new Image(imagePaths.get(3)));
        lb_DescricaoLonga.setText(app.getDescription());
        lb_politicsLongo.setText(app.getPolitics());
        lb_developerName.setText(app.getDeveloperName());

        img_icon.setOpacity(1.0);
        img_shot_1.setOpacity(1.0);
        img_shot_2.setOpacity(1.0);
        img_shot_3.setOpacity(1.0);
        img_shot_4.setOpacity(1.0);

        if (app.getPreco() == 0.0f) {
            lb_preco.setText("Grátis");
            lb_preco1.setText("");
        } else {
            lb_preco.setText(Float.toString((float) app.getPreco()));
        }
    }

    @FXML
    void On_add_icon_click(MouseEvent event) {

    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {
 try {
             MainStage.changeScene("CriarApp.fxml");
          } catch (Exception ex) {
             ex.printStackTrace();
             }
    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) throws Exception {
 try {
             MainStage.changeScene("MenuPrincipal.fxml");
          } catch (Exception ex) {
             ex.printStackTrace();
             }
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) {

    }

    
    @FXML
void On_bt_upload_pressed(ActionEvent event) {
    String fileURL = appfile;
    String savePath = "C:/downloads/app.apk";

    // Ocultar os botões e rótulos
    bt_Baixar.setVisible(false);
    lb_preco.setVisible(false);
    lb_preco1.setVisible(false);
    
    // Mostrar a barra de progresso
    progressBar.setVisible(true);
    statusLabel.setVisible(true);

    // Cria a tarefa de download
    Task<Void> downloadTask = App.downloadFile(fileURL, savePath);

    // Vincula o progresso e a mensagem de status à interface
    progressBar.progressProperty().bind(downloadTask.progressProperty());
    statusLabel.textProperty().bind(downloadTask.messageProperty());

    // Inicia a tarefa de download em uma nova thread
    new Thread(downloadTask).start();
}

    

}
