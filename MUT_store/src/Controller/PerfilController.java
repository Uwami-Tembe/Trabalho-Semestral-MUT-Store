package Controller;

import Model.Usuario;
import View.MainStage;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PerfilController {

    
        @FXML
    private ProgressIndicator ploader;
    @FXML
    private Button bt_criarApp;

    @FXML
    private Button bt_definicoes;

    @FXML
    private Button bt_loja;

    @FXML
    private Button bt_pedir;

    @FXML
    private Button bt_sair;

    @FXML
    private Button bt_sobre;

    @FXML
    private ImageView img_editarMail;

    @FXML
    private ImageView img_editarTelefone;
    
    @FXML
    private ImageView img_BI;

    @FXML
    private Label lb_NomeDoUsuario;

    @FXML
    private Label lb_info;

    @FXML
    private Label lb_mail;

    @FXML
    private Label lb_numeroDeTelefone;

    @FXML
    private Pane panel_BI;

    @FXML
    private Pane panel_pedido;

    @FXML
    private Pane panel_profile;

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) throws IOException {
        MainStage.resetScene("MenuPrincipal", "MenuPrincipal.fxml");
        MainStage.goTo("MenuPrincipal");
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_pedir_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws IOException {
        MainStage.resetScene("TelaLogin", "LoginDesign.fxml");
        MainStage.goTo("TelaLogin");
    }

    @FXML
    void om_img_editarMail_click(MouseEvent event) {

    }

    @FXML
    void on_img_editarTelefone_click(MouseEvent event) {

    }

    @FXML
    void on_panel_BI_click(MouseEvent event) {
        handleOpenImage(img_BI);
    }

    @FXML
    void on_panel_profile_click(MouseEvent event) {

    }
    
    public void setInfoOfUser(Usuario user){
    lb_NomeDoUsuario.setText(user.getName());
    lb_mail.setText(user.getEmail());
    lb_numeroDeTelefone.setText(user.getMobileNumber());
    
    }
    
    
    
    
    
        private String handleOpenImage(ImageView imgv){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png","*.jpg","*.jpeg","*.PNG","*.JPG","*.JPEG"));
        Stage stage =(Stage) imgv.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
            if(file!=null){
                Image image = new Image(file.toURI().toString());
                imgv.setImage(image);
                imgv.setOpacity(1.0);
            }
        return file.toURI().toString();
    }

}
