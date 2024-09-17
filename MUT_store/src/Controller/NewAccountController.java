package Controller;

import Controller.Models.Api.User;
import Controller.Models.Usuario;
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
import javax.swing.JOptionPane;

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
    // Validação dos campos
    if (txt_usuario.getText().isEmpty() || ps_senha.getText().isEmpty() || ps_senha1.getText().isEmpty() || 
        txt_numeroDeTelefone.getText().isEmpty() || txt_email.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!");
        return;
    }

    // Verificação de senhas
    if (!ps_senha.getText().equals(ps_senha1.getText())) {
        JOptionPane.showMessageDialog(null, "As senhas não correspondem!");
        return;
    }

    // Criação do objeto Usuario
    Usuario user = new Usuario(
        "ZeFelipe",
        txt_usuario.getText(),
        ps_senha.getText(),
        txt_numeroDeTelefone.getText(),
        txt_email.getText(),
        "Normal"
    );

    // Chamada à API para criar a conta
    try {
        boolean sucesso = User.criarContaAPI(user);
        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Conta criada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao criar conta. Tente novamente.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
    } 
    }

    @FXML
    void onBT_jaTenhoContaPressed(ActionEvent event) throws  Exception{
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
