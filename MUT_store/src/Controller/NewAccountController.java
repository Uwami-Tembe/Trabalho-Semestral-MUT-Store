package Controller;

import Models.Api.Response;
import Models.Api.User;
import Models.Usuario;
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
    // Validação dos campos obrigatórios
    String mensagemErro = validarEntradasCriacaoConta();
    if (mensagemErro != null) {
        mostrarMensagemErro(mensagemErro);
        return;
    }

    // Verificação se as senhas correspondem
    if (!ps_senha.getText().equals(ps_senha1.getText())) {
        mostrarMensagemErro("As senhas não correspondem!");
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

// Valida as entradas de criação de conta e retorna uma mensagem de erro, se houver
private String validarEntradasCriacaoConta() {
    if (txt_usuario.getText().isEmpty() || ps_senha.getText().isEmpty() || ps_senha1.getText().isEmpty() || 
        txt_numeroDeTelefone.getText().isEmpty() || txt_email.getText().isEmpty()) {
        return "Todos os campos devem ser preenchidos!";
    }
    return null; // Nenhum erro
}

// Exibe a mensagem de sucesso e navega para outra tela
private void exibirMensagemSucesso(String mensagemSucesso) throws Exception {
    mostrarMensagemSucesso(mensagemSucesso);  // Exibe a mensagem de sucesso

    // Exibe a tela de carregamento e, depois, navega para a tela de login
    TelaLogin.changeScene("Carregando.fxml");
    PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

    pause.setOnFinished(e -> {
        try {
            TelaLogin.changeScene("LoginDesign.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    pause.play();
}

// Método para exibir mensagens de erro na GUI
private void mostrarMensagemErro(String mensagemErro) {
    JOptionPane.showMessageDialog(null, mensagemErro); // Exemplo usando JOptionPane
}

// Método para exibir mensagens de sucesso na GUI
private void mostrarMensagemSucesso(String mensagemSucesso) {
    JOptionPane.showMessageDialog(null, mensagemSucesso); // Exemplo usando JOptionPane
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
