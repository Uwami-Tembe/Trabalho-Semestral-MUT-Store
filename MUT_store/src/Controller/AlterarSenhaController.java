package Controller;

import View.TelaLogin;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class AlterarSenhaController {

    @FXML
    private Button bt_alterarSenha;

    @FXML
    private Button bt_voltar;

    @FXML
    private TextField genericTextField;

    @FXML
    private ImageView img_viewPassword;

    @FXML
    private PasswordField ps_senha;

    @FXML
    private PasswordField ps_senha1;

    @FXML
    void On_bt_alterarSenha_Pressed(ActionEvent event) throws Exception {
        //A lógica para alterar a senha do usuário fica aqui
        
                 TelaLogin.changeScene("Carregando.fxml");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     TelaLogin.changeScene("LoginDesign.fxml");
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
    void On_bt_voltar_Pressed(ActionEvent event) throws Exception {
        
         TelaLogin.changeScene("Carregando.fxml");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     TelaLogin.changeScene("DigitarCodigo.fxml");
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
         }
         catch(Exception e){
             e.printStackTrace();
         }
         pause.play();
         
         //Se voltamos para a tela de digitar o código ele deve ser regenerado e re-enviado
    }

    @FXML
    void hidePassword(MouseEvent event) {
        genericTextField.setManaged(false);
        genericTextField.setVisible(false);
        ps_senha.setManaged(true);
        ps_senha.setVisible(true);
    }

    @FXML
    void showPassword(MouseEvent event) {
        genericTextField.setText(ps_senha.getText());
        genericTextField.setManaged(true);
        genericTextField.setVisible(true);
        ps_senha.setManaged(false);
        ps_senha.setVisible(false);
    }

}

