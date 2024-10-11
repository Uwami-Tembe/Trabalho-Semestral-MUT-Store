package Controller;

import Model.Usuario;
import Models.Api.Response;
import Models.Api.User;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import static View.MainStage.getScene;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class DigitarCodigoController {

    @FXML
    private Button bt_verificar;

    @FXML
    private Button bt_pedir;

    @FXML
    private TextField txt_codigo;

    @FXML
    private TextField txt_number1;

    @FXML
    private Button voltar;

    Usuario user = new Usuario();

    @FXML
    void On_bt_verificar_Pressed(ActionEvent event) throws Exception {

        changeScene("Carregando");
        user.setMobileNumber(txt_number1.getText());

        // Task para rodar o processo de verificação do código em background
        Task<Response> task = new Task<>() {
            @Override
            protected Response call() throws Exception {
                return User.verifyCode(user, txt_codigo.getText());  // Método que verifica o código
            }
        };

        task.setOnSucceeded(workerStateEvent -> {
            Response verificationResult = task.getValue();  // Pega o resultado da verificação

            PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
            pause.setOnFinished(e -> {
                if (verificationResult.getError_code() == 0) {
                    // Se o código for correto, muda para a tela de Alterar Senha
                    Platform.runLater(() -> {
                        // Obtém o controlador e passa o código de verificação
                        AlterarSenhaController asc = (AlterarSenhaController)  getController("AlterarSenha");
                        asc.setUser(user);
                        asc.setVerificationCode(txt_codigo.getText());  // Passa o código para o controlador
                        changeScene("AlterarSenha");
                    });
                } else {
                    // Caso contrário, exibe uma mensagem de erro
                    Platform.runLater(() -> {
                        JOptionPane.showMessageDialog(null, "Código inválido. Por favor, verifique e tente novamente.");
                        try {
                            changeScene("DigitarCodigo"); // Mude para a tela de erro, se desejar
                        } catch (Exception ex) {
                            Logger.getLogger(DigitarCodigoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            });
            pause.play();
        });

        task.setOnFailed(workerStateEvent -> {
            // Tratar caso haja falha na requisição
            Exception exception = (Exception) task.getException();
            exception.printStackTrace();
            Platform.runLater(() -> {
                JOptionPane.showMessageDialog(null, "Erro. Ocorreu um erro ao processar sua solicitação.");
                try {
                    changeScene("TelaLogin"); // Mude para a tela desejada após a mensagem
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        // Executa o task em segundo plano
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    
@FXML
void On_bt_pedir_Pressed(ActionEvent event) throws Exception {

    // Configura o número de celular
    user.setMobileNumber(txt_number1.getText());
    
    // Muda a cena para a tela de carregamento
    changeScene("Carregando");

    // Task para rodar o processo de envio do SMS em background
    Task<Response> task = new Task<>() {
        @Override
        protected Response call() throws Exception {
            return User.forgotPassword(user);  // Faz a requisição para enviar o SMS
        }
    };

    // Quando o envio de SMS for bem-sucedido
    task.setOnSucceeded(workerStateEvent -> {
        Response smsEnviado = task.getValue();  // Pega o resultado da requisição

        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            if (smsEnviado.getError_code() == 0) {
                JOptionPane.showMessageDialog(null, "Código de recuperação enviado com sucesso!");
                
                // Transição para a tela "DigitarCodigo.fxml"
                try {
                    DigitarCodigoController dcc = (DigitarCodigoController) getController("DigitarCodigo");
                    dcc.txt_number1.setText(user.getMobileNumber());  // Passa o número para o controlador
                    changeScene("DigitarCodigo");  // Muda para a tela de digitar o código
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha no envio do SMS. Não foi possível enviar o código para o número informado.");
                voltarParaLogin();
            }
        });
        pause.play();
    });

    // Quando o envio de SMS falhar
    task.setOnFailed(workerStateEvent -> {
        Exception exception = (Exception) task.getException();
        exception.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erro. Ocorreu um erro ao processar sua solicitação.");
        voltarParaLogin();
    });

    // Executa o task em segundo plano
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
}

// Método auxiliar para voltar à tela de login
private void voltarParaLogin() {
    try {
        changeScene("TelaLogin");  // Volta para a tela de login
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}


    @FXML
    void On_bt_voltar_pressed(ActionEvent event) throws Exception {
        changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

        try {
            pause.setOnFinished(e -> {
                try {
                    MainStage.resetScene("TelaLogin", "LoginDesign.fxml");
                    MainStage.goTo("TelaLogin");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        pause.play();
    }

}
