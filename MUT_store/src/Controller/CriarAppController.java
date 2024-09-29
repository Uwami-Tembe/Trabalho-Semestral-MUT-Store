package Controller;

import Model.AppModel;
import Models.Api.App;
import Models.Api.Response;
import Models.Api.User;
import View.MainStage;
import View.TelaLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class CriarAppController {

    @FXML
    public Button bt_Upload;

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
    private CheckBox checB_card;

    @FXML
    private CheckBox checkB_mpesaeEmola;

    @FXML
    private ImageView img_delete_shots;

    @FXML
    private ImageView img_file;

    @FXML
    public ImageView img_icon;

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
    private Label lb_FacaUpload;

    @FXML
    public Label lb_Nome;

    @FXML
    private Label lb_Nome1;

    private File lastDirectory = null; // Variável para armazenar o último diretório

    public Image image = null;
    
    public File file = null;
    
    @FXML
    private Label lb_carregueImagens;

    @FXML
    private Label lb_ficheiro;

    @FXML
    private Label lb_icon;

    @FXML
    private Label lb_nota;

    @FXML
    private Label lb_politics;

    @FXML
    private Label lb_preco;

    @FXML
    private Label lb_screenshots;

    @FXML
    private Pane panel_add_file;

    @FXML
    private Pane panel_add_icon;

    @FXML
    private Pane panel_add_shot_1;

    @FXML
    private Pane panel_add_shot_2;

    @FXML
    private Pane panel_add_shot_3;

    @FXML
    private Pane panel_add_shot_4;

    @FXML
    private TextArea txt_appDetalhes;

    @FXML
    private TextField txt_appNome;

    @FXML
    private TextArea txt_appPolitics;

    @FXML
    private TextField txt_appPreco;
    
    public  String icon_path;
    
       public void mostrarMensagemErro(String S){
        JOptionPane.showMessageDialog(null, S);
    }
    
private void exibirMensagemSucesso(String mensagemSucesso) throws Exception {  // Método para exibir mensagem na GUI
                TelaLogin loginDisplay = new TelaLogin(MainStage.primaryStage);
    // Exibe a tela de carregamento antes de mudar para o próximo estágio
    loginDisplay.changeScene("Carregando.fxml");
    PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

    pause.setOnFinished(e -> {
        try {
            // Troca de cena para uma tela de menu ou próximo passo
            loginDisplay.changeScene("MenuPrincipal.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    pause.play();
}
       
    MenuPrincipalController m = new MenuPrincipalController();
    
    public void setMenuController(MenuPrincipalController m){
        this.m=m;
    }
    
    public static List<AppModel>appList = new ArrayList();
    @FXML
    void On_add_file_click(MouseEvent event) {
        handleOpenFile(img_file);
        
    }

    @FXML
    void On_add_icon_click(MouseEvent event) {
        icon_path=handleOpenImage(img_icon);
    }

    @FXML
    void On_add_shot_1_click(MouseEvent event) {
        handleOpenImage(img_shot_1);
    }

    @FXML
    void On_add_shot_2_click(MouseEvent event) {
         handleOpenImage(img_shot_2);
    }

    @FXML
    void On_add_shot_3_click(MouseEvent event) {
         handleOpenImage(img_shot_3);
    }

    @FXML
    void On_add_shot_4_click(MouseEvent event) {
         handleOpenImage(img_shot_4);
    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) throws Exception {
           
          m=(MenuPrincipalController)MainStage.changeScene("MenuPrincipal.fxml");
          setMenuController(m);
          m.updateMenu();

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
    void  On_bt_upload_pressed(ActionEvent event) {
        AppModel novaApp = new AppModel(img_icon,txt_appNome.getText(),Float.parseFloat(txt_appPreco.getText()),
                                        img_shot_1,img_shot_2,img_shot_3,img_shot_4,txt_appDetalhes.getText(),
                                        txt_appPolitics.getText(),txt_appNome.getText(), checB_card.isSelected(),
                                        checkB_mpesaeEmola.isSelected(),checkB_mpesaeEmola.isSelected(), img_file );
        
        
      
         try {
        // Chama o método loginAPI e verifica a resposta
        Response res =  App.adicionarApp(novaApp);

        // Exibe a mensagem retornada pela API, seja ela de sucesso ou falha
        if (res.getError_code() == 0) {
            exibirMensagemSucesso(res.getMsg());
            
        } else {
            mostrarMensagemErro(res.getMsg());  // Mensagem de erro da API
        }

    } catch (Exception e) {
        e.printStackTrace();
        mostrarMensagemErro("Erro no sistema. Tente novamente mais tarde.");
    }
        
        appList.add(novaApp);
        
        
          
    }

    @FXML
    void On_img_delete_shots_click(MouseEvent event) {
        ImageView defaultImage = new ImageView("/View/Login/imagens/image-icon.png");
       
        img_shot_1.setImage(defaultImage.getImage());
        img_shot_2.setImage(defaultImage.getImage());
        img_shot_3.setImage(defaultImage.getImage());
        img_shot_4.setImage(defaultImage.getImage());
        img_shot_1.setOpacity(0.4);
        img_shot_2.setOpacity(0.4);
        img_shot_3.setOpacity(0.4);
        img_shot_4.setOpacity(0.4);
    }
    
    private String handleOpenImage(ImageView imgv) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("*.png", "*.jpg", "*.jpeg", "*.PNG", "*.JPG", "*.JPEG"));
    
    // Verificar se há um último diretório usado e definir como o diretório inicial
    if (lastDirectory != null) {
        fileChooser.setInitialDirectory(lastDirectory);
    }
    
    Stage stage = (Stage) imgv.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    
    if (file != null) {
        lastDirectory = file.getParentFile(); // Atualizar o último diretório
        Image image = new Image(file.toURI().toString());
        imgv.setImage(image);
        imgv.setOpacity(1.0);
    }
    
    return file != null ? file.toURI().toString() : null;
}

            
 private void handleOpenFile(ImageView imgv) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("*.apk", "*.exe", "*.APK", "*.EXE"));
    
    // Verificar se há um último diretório usado e definir como o diretório inicial
    if (lastDirectory != null) {
        fileChooser.setInitialDirectory(lastDirectory);
    }
    
    Stage stage = (Stage) imgv.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    
    if (file != null) {
        lastDirectory = file.getParentFile(); // Atualizar o último diretório
        JOptionPane.showMessageDialog(null, file.getAbsolutePath());
    }
}

      

}   
    

