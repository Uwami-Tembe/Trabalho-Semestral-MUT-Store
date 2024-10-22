package Controller;

import Model.Usuario;
import Models.Api.Response;
import Models.Api.User;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

        try {
            pause.setOnFinished(e -> {
                try {
                    changeScene("CriarConta");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        pause.play();
    }

    public void mostrarMensagemErro(String S) {
        JOptionPane.showMessageDialog(null, S);
    }

    @FXML
    void On_bt_esqueci_Pressed(ActionEvent event) throws Exception {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

        try {
            pause.setOnFinished(e -> {
                try {
                    MainStage.changeScene("DigitarCodigo");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        pause.play();

        //O código deve ser gerado e enviado aqui
    }
    
    
    
@FXML
void OnUsernamePressed(KeyEvent event) {
    // Pegue o campo de texto que disparou o evento
    String username = txt_usuario.getText();

    // Defina os critérios de validação
    boolean isValid = validarUsername(username);

    // Exiba uma mensagem de erro ou faça outra ação de acordo com a validação
    if (!isValid) {
        txt_usuario.setStyle("-fx-border-color: red;"); // Adiciona uma borda vermelha no TextField
        System.out.println("Nome de usuário inválido! Deve conter entre 5 e 15 caracteres e apenas letras e números.");
    } else {
        txt_usuario.setStyle("-fx-border-color: none;"); // Remove a borda vermelha se válido
    }

    // Verifique se a tecla pressionada foi Enter
    if (event.getCode() == KeyCode.ENTER) {
        if (isValid) {
            login(); // Chama a função de login se o nome de usuário for válido
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, corrija o nome de usuário antes de continuar.");
        }
    }
}

@FXML
void OnSenhaPressed(KeyEvent event) {
    // Verifique se a tecla pressionada foi Enter
    if (event.getCode() == KeyCode.ENTER) {
        login(); // Chama a função de login ao pressionar Enter no campo de senha
    }
}

@FXML
void onBT_entrarPressed(ActionEvent event) throws Exception {
    login(); // Chama a função de login ao clicar no botão
}

public void login() {
    // Obter as entradas do usuário
    String username = txt_usuario.getText();
    String password = ps_senha.getText();

    // Validações essenciais
    boolean isValidUsername = validarUsername(username);
    if (!isValidUsername) {
        mostrarMensagemErro("Nome de usuário inválido! Deve conter entre 5 e 15 caracteres e apenas letras e números.");
        txt_usuario.setStyle("-fx-border-color: red;");
        return;
    } else {
        txt_usuario.setStyle("-fx-border-color: none;");
    }

    String mensagemErro = validarEntradas(username, password);
    if (mensagemErro != null) {
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

// Método auxiliar para validar o nome de usuário
private boolean validarUsername(String username) {
    return username.matches("[a-zA-Z0-9]*") && username.length() >= 4 && username.length() <= 15;
}

// Método auxiliar para validar username e password
private String validarEntradas(String username, String password) {
    if (username.isEmpty() || password.isEmpty()) {
        return "Por favor, preencha todos os campos!";
    }
    return null; // Se não houver erros, retorna null
}




   

// Exibe a mensagem de sucesso e navega para outra tela
    private void exibirMensagemSucesso(String mensagemSucesso) throws Exception {
        mostrarMensagemSucesso(mensagemSucesso);  // Método para exibir mensagem na GUI

        // Exibe a tela de carregamento antes de mudar para o próximo estágio
        changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

        pause.setOnFinished(e -> {
            try {
                // Troca de cena para uma tela de menu ou próximo passo
                Usuario user = userInfo();

                if (user != null) {
                    if (user.getUserType().equals("admin")) {
                        AdminController mpc = (AdminController) getController("Admin");
//            mpc.setUser(user);
//            mpc.setLb_NomeDoUsuario(user.getName());
                        mpc.initialize();
                        changeScene("Admin");
                        return;
                    }
              
                    MenuPrincipalController mpc = (MenuPrincipalController) getController("MenuPrincipal");
                    mpc.initialize();
                    mpc.loadApps();
                    changeScene("MenuPrincipal");

                }
                JOptionPane.showMessageDialog(null, "Ocorreu um erro durante o login, tente novamente");

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
