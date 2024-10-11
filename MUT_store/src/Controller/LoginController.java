package Controller;

import Model.Usuario;
import Models.Api.Response;
import Models.Api.User;
import View.MainStage;
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
   TelaLogin loginDisplay = new TelaLogin(MainStage.primaryStage);
         loginDisplay.changeScene("Carregando.fxml");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     loginDisplay.changeScene("CriarConta.fxml");
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
            TelaLogin loginDisplay = new TelaLogin(MainStage.primaryStage);
         loginDisplay.changeScene("Carregando.fxml");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     loginDisplay.changeScene("DigitarCodigo.fxml");
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
    String mensagemErro = validarEntradas(username, password);
    if (mensagemErro != null) {
        // Se houver erro de validação, exibe a mensagem correspondente
        mostrarMensagemErro(mensagemErro);
        return;
    }

    // Cria o objeto Usuario com as informações fornecidas
    Usuario user = new Usuario(username, password);

    try {
        // Chama o método loginAPI e verifica a resposta
        Response res = User.loginAPI(user);

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
}


// Valida as entradas e retorna a mensagem de erro, se houver, ou null
private String validarEntradas(String username, String password) {
    if (username == null || username.trim().isEmpty()) {
        return "O nome de usuário não pode estar vazio.";
    }
    if (password == null || password.trim().isEmpty()) {
        return "A senha não pode estar vazia.";
    }
    return null; // Nenhum erro, retorna null
}

// Exibe a mensagem de sucesso e navega para outra tela
private void exibirMensagemSucesso(String mensagemSucesso) throws Exception {
    mostrarMensagemSucesso(mensagemSucesso);  // Método para exibir mensagem na GUI
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


// Método para exibir mensagens de sucesso na GUI
private void mostrarMensagemSucesso(String mensagemSucesso) {
    // Lógica para exibir a mensagem de sucesso na interface, por exemplo, em um Label ou Alert
    System.out.println(mensagemSucesso); // Exemplo de saída no console
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