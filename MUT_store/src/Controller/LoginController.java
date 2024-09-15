package Controller;

import View.TelaLogin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.SwingWorker;

public class LoginController {

    @FXML
    private Button bt_Entrar;

    @FXML
    private Button bt_criarConta;

    @FXML
    private ImageView img_viewPassword;

    @FXML
    private PasswordField ps_senha;
    
    @FXML
    private TextField genericTextField;

    @FXML
    private TextField txt_usuario;
    
    
    @FXML
    void onBT_criarContaPressed(ActionEvent event) throws Exception {
         TelaLogin.changeScene("Carregando.fxml");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     TelaLogin.changeScene("CriarConta.fxml");
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
    void On_bt_esqueci_Pressed(ActionEvent event) throws Exception {
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
         
         //O código deve ser gerado e enviado aqui
    }
    
    @FXML
    void onBT_entrarPressed(ActionEvent event) {
        
        //A lógica necesssária para entar no menu principal fica aqui
        
        
    }
   @FXML
    void showPassword(MouseEvent event) {
        genericTextField.setText(ps_senha.getText());
        genericTextField.setManaged(true);
        genericTextField.setVisible(true);
        ps_senha.setManaged(false);
        ps_senha.setVisible(false);
    }
    
    @FXML
    void hidePassword(MouseEvent event) {
        genericTextField.setManaged(false);
        genericTextField.setVisible(false);
        ps_senha.setManaged(true);
        ps_senha.setVisible(true);
    }
    

}