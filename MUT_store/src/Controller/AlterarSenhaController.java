package Controller;

import Model.Usuario;
import Models.Api.Response;
import Models.Api.User;
import View.MainStage;
import View.TelaLogin;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

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
    
    Usuario user = new Usuario();
    
    private String verificationCode;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    
    
    
    // Método que recebe o código de verificação
    public void setVerificationCode(String code) {
        this.verificationCode = code;}
  

@FXML
void On_bt_alterarSenha_Pressed(ActionEvent event) throws Exception {
    TelaLogin lg = new TelaLogin(MainStage.primaryStage);
    
    String senha = ps_senha.getText();
    String senha1 = ps_senha1.getText();
    
    // Verifica se as senhas coincidem
    if (!senha.equals(senha1)) {
        JOptionPane.showMessageDialog(null, "As senhas não coincidem. Por favor, tente novamente.");
        return;  // Interrompe o processo se as senhas não forem iguais
    }
    
    // Caso as senhas coincidam, prossegue com a alteração
    lg.changeScene("Carregando.fxml");
    user.setPassword(senha1);  // Define a senha a ser alterada

    // Task para rodar o processo de alteração da senha em background
    Task<Response> task = new Task<>() {
        @Override
        protected Response call() throws Exception {
            // Substitua pelo método que altera a senha, passando os dados necessários
            return User.resetPassword(user, verificationCode);  // Faz a requisição para alterar a senha do usuário
        }
    };

    task.setOnSucceeded(workerStateEvent -> {
        Response resetResult = task.getValue();  // Pega o resultado da alteração da senha

        // Modificações na interface gráfica devem ser feitas no JavaFX Application Thread
        Platform.runLater(() -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
            pause.setOnFinished(e -> {
                try {
                    if (resetResult.getError_code() == 0) {
                        // Se a senha foi alterada com sucesso, redireciona para o Menu Principal
                        JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
                        lg.changeScene("MenuPrincipal.fxml");
                    } else {
                        // Caso contrário, exibe uma mensagem de erro
                        JOptionPane.showMessageDialog(null, "Falha ao alterar a senha. Tente novamente.");
                        lg.changeScene("AlterarSenha.fxml");  // Volta para a tela de alterar senha, se necessário
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            pause.play();
        });
    });

    task.setOnFailed(workerStateEvent -> {
        // Tratar caso haja falha na requisição
        Exception exception = (Exception) task.getException();
        exception.printStackTrace();
        
        // Modificações na interface gráfica devem ser feitas no JavaFX Application Thread
        Platform.runLater(() -> {
            JOptionPane.showMessageDialog(null, "Erro. Ocorreu um erro ao processar sua solicitação.");
            try {
                lg.changeScene("AlterarSenha.fxml");  // Mude para a tela desejada após a falha
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    });

    // Use um ExecutorService para gerenciar as threads
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(task);
    executor.shutdown();
}



    @FXML
    void On_bt_voltar_Pressed(ActionEvent event) throws Exception {
        TelaLogin lg = new TelaLogin(MainStage.primaryStage);

        lg.changeScene("Carregando.fxml");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

        try {
            pause.setOnFinished(e -> {
                try {
                    lg.changeScene("DigitarCodigo.fxml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
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
