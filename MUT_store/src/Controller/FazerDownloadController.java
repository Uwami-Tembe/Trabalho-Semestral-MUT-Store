package Controller;

import Model.AppModelDetails;
import Model.Usuario;
import Models.Api.App;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
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
import javafx.scene.layout.VBox;

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

//    @FXML
//    private Label lb_preco1;

    @FXML
    private VBox panel_comentarios;

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

public void loadDownloadPageContent(AppModelDetails app) {
    // Limpa o conteúdo anterior
    clearPreviousContent();

    // Carrega os novos dados do aplicativo
    img_icon.setImage(new Image(app.getIcon()));
    lb_Nome.setText(app.getNome());

    List<String> imagePaths = app.getImagePaths();  // Supondo que imagePaths é uma List<String>
    appfile = app.getAppFilePath();

    // Carrega as imagens de captura de tela
    img_shot_1.setImage(new Image(imagePaths.get(0)));
    img_shot_2.setImage(new Image(imagePaths.get(1)));
    img_shot_3.setImage(new Image(imagePaths.get(2)));
    img_shot_4.setImage(new Image(imagePaths.get(3)));

    // Outros dados do app
    lb_DescricaoLonga.setText(app.getDescription());
    lb_politicsLongo.setText(app.getPolitics());
    lb_developerName.setText(app.getDeveloperName());

    // Torna as imagens visíveis (caso necessário)
    img_icon.setOpacity(1.0);
    img_shot_1.setOpacity(1.0);
    img_shot_2.setOpacity(1.0);
    img_shot_3.setOpacity(1.0);
    img_shot_4.setOpacity(1.0);

    // Define o preço
    if (app.getPreco() == 0.0f) {
        lb_preco.setText("Grátis");
    } else {
        lb_preco.setText(Float.toString((float) app.getPreco()));
    }
}

// Método para limpar os campos da interface
public void clearPreviousContent() {
    img_icon.setImage(null);
    lb_Nome.setText("");
    img_shot_1.setImage(null);
    img_shot_2.setImage(null);
    img_shot_3.setImage(null);
    img_shot_4.setImage(null);
    lb_DescricaoLonga.setText("");
    lb_politicsLongo.setText("");
    lb_developerName.setText("");
    lb_preco.setText("");
    img_icon.setOpacity(0.0);
    img_shot_1.setOpacity(0.0);
    img_shot_2.setOpacity(0.0);
    img_shot_3.setOpacity(0.0);
    img_shot_4.setOpacity(0.0);
}


    @FXML
    void On_add_icon_click(MouseEvent event) {

    }
    
    @FXML
    void On_bt_comentar_pressed(MouseEvent event) {

    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {
 try {
             MainStage.changeScene("CriarApp");
          } catch (Exception ex) {
             ex.printStackTrace();
             }
    }

     @FXML
    void On_bt_Loja_pressed(ActionEvent event) throws Exception {
        MenuPrincipalController menuController = (MenuPrincipalController) getController("MenuPrincipal");
        Usuario user = userInfo();
        if (user != null) {
            // Definir o nome do usuário no controlador da tela principal
            menuController.setLb_NomeDoUsuario(user.getName());
            menuController.setUser(user);// Atualiza o Label com o nome do usuário
            menuController.appshome();

        } else {
            // Lidar com o erro se o usuário não foi encontrado
            System.err.println("Erro ao buscar informações do usuário.");
        }

        changeScene("MenuPrincipal");

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
void On_bt_baixar_pressed(ActionEvent event) {
    String fileURL = appfile;
    String savePath = "C:/downloads/app.apk";

    // Ocultar os botões e rótulos
    bt_Baixar.setVisible(false);
    lb_preco.setVisible(false);
//    lb_preco1.setVisible(false);
    
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
