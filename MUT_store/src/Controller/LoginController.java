package Controller;

import Controller.Models.Api.User;
import Controller.Models.Usuario;
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
import javax.swing.JOptionPane;
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
    public void mostrarMensagemErro(String S){
        JOptionPane.showMessageDialog(null, S);
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
void onBT_entrarPressed(ActionEvent event) throws Exception {
    // Obter as entradas do usuário
    String username = txt_usuario.getText();
    String password = ps_senha.getText();

    // Validações essenciais
    if (username == null || username.trim().isEmpty()) {
        mostrarMensagemErro("O nome de usuário não pode estar vazio.");
        return;
    }

    if (password == null || password.trim().isEmpty()) {
        mostrarMensagemErro("A senha não pode estar vazia.");
        return;
    }

    // Cria o objeto Usuario com as informações fornecidas
    Usuario user = new Usuario(username, password);

    // Chama o método loginAPI e verifica se o login foi bem-sucedido
    boolean sucesso = User.loginAPI(user);

    // Se o login for bem-sucedido, navega para o menu principal
    if (sucesso) {
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
    } else {
        // Exibe uma mensagem de erro ao usuário caso o login falhe
        mostrarMensagemErro("Usuário ou senha incorretos. Tente novamente.");
    }
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