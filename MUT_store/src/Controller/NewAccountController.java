package Controller;

import Model.Usuario;
import Models.Api.Response;
import Models.Api.User;
import View.MainStage;
import static View.MainStage.changeScene;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class NewAccountController {

    
        @FXML
    private ProgressIndicator ploader;
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
    private TextField txt_username;



@FXML
void onBT_criarContaPressed(ActionEvent event) {
    // Limpar estilos antigos de erros
    limparEstilosDeErro();

    // Validação dos campos obrigatórios
    String mensagemErro = validarEntradasCriacaoConta();
    if (mensagemErro != null) {
        mostrarMensagemErro(mensagemErro);
        return;
    }

    // Verificação se as senhas correspondem
    if (!ps_senha.getText().equals(ps_senha1.getText())) {
        ps_senha.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
        ps_senha1.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
        mostrarMensagemErro("As senhas não correspondem!");
        return;
    }

    // Criação do objeto Usuario
    Usuario user = new Usuario(
        txt_usuario.getText(),
        txt_username.getText(),
        ps_senha.getText(),
        txt_numeroDeTelefone.getText(),
        txt_email.getText(),
        "normal"
    );

    // Chamada à API para criar a conta
    try {
        Response res = User.criarContaAPI(user);

        // Exibir a mensagem retornada pela API
        if (res.getError_code() == 0) {
            exibirMensagemSucesso(res.getMsg());
        } else {
            mostrarMensagemErro(res.getMsg());
        }
    } catch (Exception e) {
        mostrarMensagemErro("Ocorreu um erro no sistema: " + e.getMessage());
    }
}

// Método auxiliar para validar as entradas de criação de conta
private String validarEntradasCriacaoConta() {
    boolean erro = false;

    if (txt_usuario.getText().isEmpty()) {
        txt_usuario.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
        erro = true;
    }

    if (txt_email.getText().isEmpty() || !txt_email.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        txt_email.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
        erro = true;
    }

    if (txt_numeroDeTelefone.getText().isEmpty() || !txt_numeroDeTelefone.getText().matches("\\+258\\d{9}")) {
        txt_numeroDeTelefone.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
        erro = true;
    }

    if (ps_senha.getText().isEmpty()) {
        ps_senha.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
        erro = true;
    }

    if (ps_senha1.getText().isEmpty()) {
        ps_senha1.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
        erro = true;
    }

    if (erro) {
        return "Por favor, corrija os campos em vermelho!";
    }

    return null; // Nenhum erro
}

// Limpar os estilos de erro antes de validar novamente
private void limparEstilosDeErro() {
    txt_usuario.setStyle("");
    txt_email.setStyle("");
    txt_numeroDeTelefone.setStyle("");
    ps_senha.setStyle("");
    ps_senha1.setStyle("");
}

// Evento KeyPressed para validar e criar conta ao pressionar "Enter"
@FXML
void onFieldKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
        onBT_criarContaPressed(null); // Chama o método de criação de conta
    }
}

@FXML
void onUsernameFieldKeyPressed(KeyEvent event) {

}

@FXML
void onSenhaFieldKeyPressed(KeyEvent event) {

}



// Exibe a mensagem de sucesso e navega para outra tela
private void exibirMensagemSucesso(String mensagemSucesso) throws Exception {
    mostrarMensagemSucesso(mensagemSucesso);
    changeScene("Carregando.fxml");

    PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
    pause.setOnFinished(e -> {
        try {
            changeScene("TelaLogin.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });
    pause.play();
}

// Exibe mensagem de sucesso com JOptionPane
private void mostrarMensagemSucesso(String mensagem) {
    JOptionPane.showMessageDialog(null, mensagem);
}

// Exibe mensagem de erro com JOptionPane
private void mostrarMensagemErro(String mensagemErro) {
    JOptionPane.showMessageDialog(null, mensagemErro);
}


    @FXML
    void onBT_jaTenhoContaPressed(ActionEvent event) throws  Exception{
                 changeScene("Carregando");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     changeScene("TelaLogin");
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
