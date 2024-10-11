package Controller;

import Model.Usuario;
import Models.Api.Response;
import Models.Api.User;
import View.MainStage;
import static View.MainStage.changeScene;
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
    
changeScene("Carregando.fxml");
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
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlterarSenha.fxml"));
                        Parent root = loader.load();

                        // Obtém o controlador e passa o código de verificação
                        AlterarSenhaController controller = loader.getController();
                        controller.setUser(user);
                        controller.setVerificationCode(txt_codigo.getText());  // Passa o código para o controlador

                        MainStage.primaryStage.setScene(new Scene(root));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } else {
                // Caso contrário, exibe uma mensagem de erro
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, "Código inválido. Por favor, verifique e tente novamente.");
                    try {
                        changeScene("DigitarCodigo.fxml"); // Mude para a tela de erro, se desejar
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
                changeScene("TelaLogin.fxml"); // Mude para a tela desejada após a mensagem
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
   
    user.setMobileNumber(txt_number1.getText());
    changeScene("Carregando.fxml");

    // Task para rodar o processo de envio do SMS em background
    Task<Response> task = new Task<>() {
        @Override
        protected Response call() throws Exception {
            return User.forgotPassword(user);  // Faz a requisição para enviar o SMS
        }
    };

    task.setOnSucceeded(workerStateEvent -> {
        Response smsEnviado = task.getValue();  // Pega o resultado da requisição

        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            if (smsEnviado.getError_code() == 0) {
                // Mensagem de sucesso indicando que o código foi enviado
                JOptionPane.showMessageDialog(null, "Código de recuperação enviado com sucesso!");
            } else {
                // Mensagem de erro se o código não foi enviado
                JOptionPane.showMessageDialog(null, "Falha no envio do SMS. Não foi possível enviar o código para o número informado.");
            }

            // Transição para a tela "DigitarCodigo.fxml"
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DigitarCodigo.fxml"));
                Parent root = loader.load();

                // Obtém o controlador da tela "DigitarCodigo"
                DigitarCodigoController controller = loader.getController();
                // Passa o número de telefone para o campo txt_number1 da tela "DigitarCodigo"
                controller.txt_number1.setText(txt_number1.getText());  // Passa o número para o controlador

                // Define a nova cena
                MainStage.primaryStage.setScene(new Scene(root));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    });

    task.setOnFailed(workerStateEvent -> {
        // Tratar caso haja falha na requisição
        Exception exception = (Exception) task.getException();
        exception.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erro. Ocorreu um erro ao processar sua solicitação.");
        
        // Retorna à tela de login após mostrar a mensagem de erro
        try {
            changeScene("TelaLogin.fxml"); // Mude para a tela desejada após a mensagem
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    // Executa o task em segundo plano
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
}





    @FXML
    void On_bt_voltar_pressed(ActionEvent event) throws Exception {
         changeScene("Carregando.fxml");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                    changeScene("LoginDesign.fxml");
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

}
