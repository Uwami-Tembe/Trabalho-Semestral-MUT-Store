package Controller;

import View.MainStage;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class NewAccountController {

    @FXML
    private Button bt_criarConta;

    @FXML
    private Button bt_jaTenhoConta;

    @FXML
    private TextField genericTextField;

    @FXML
    private ImageView img_viewPassword1;

    @FXML
    private PasswordField ps_senha;

    @FXML
    private PasswordField ps_senha1;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_numeroDeTelefone;

    @FXML
    private TextField txt_usuario;



    @FXML
    void onBT_criarContaPressed(ActionEvent event) {
        //A lógica para criar a conta fica aqui
        
        
        
    }

    @FXML
    void onBT_jaTenhoContaPressed(ActionEvent event) throws  Exception{
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
