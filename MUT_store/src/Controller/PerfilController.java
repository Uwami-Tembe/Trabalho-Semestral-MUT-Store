package Controller;

import Model.Usuario;
import static Models.Api.User.sendDevRequest;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.getController;
import java.io.File;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class PerfilController {

    @FXML
    private com.gluonhq.charm.glisten.control.ProgressIndicator ploader;
    @FXML
    private Button bt_criarApp;

    @FXML
    private Button bt_definicoes;

    @FXML
    private Button bt_loja;

    @FXML
    private Button bt_pedir;

    @FXML
    private Button bt_sair;

    @FXML
    private Button bt_sobre;

    @FXML
    private ImageView img_editarMail;

    @FXML
    private ImageView img_editarTelefone;

    @FXML
    private ImageView img_BI;

    @FXML
    private Label lb_NomeDoUsuario;

    @FXML
    private Label lb_info;

    @FXML
    private Label lb_mail;

    @FXML
    private Label lb_numeroDeTelefone;

    @FXML
    private Pane panel_BI;

    @FXML
    private Pane panel_pedido;

    @FXML
    private Pane panel_profile;

    @FXML
    private ImageView img_iconCriar;

    @FXML
    public void initialize() {
        // Garantindo que a interface gráfica seja manipulada na thread correta
//        Platform.runLater(() -> {
        // Obtendo as informações do usuário
        Usuario user = userInfo();

        // Verifica se o usuário não é nulo
        if (user != null) {

            System.out.println(user.getUserType());

            // Se o tipo de usuário for "normal", esconde os botões de criação
            if (user.getUserType().equals("normal")) {
                bt_criarApp.setVisible(false);
                if (img_iconCriar != null) {
                    img_iconCriar.setVisible(false);
                }
                
                
            lb_NomeDoUsuario.setText(user.getUsername());
            lb_mail.setText(user.getEmail());
            lb_numeroDeTelefone.setText(user.getMobileNumber());
                return;
            }

            // Caso contrário, exibe os botões de criação
            bt_criarApp.setVisible(true);
            if (img_iconCriar != null) {
                img_iconCriar.setVisible(true);
            }

            lb_NomeDoUsuario.setText(user.getUsername());
            lb_mail.setText(user.getEmail());
            lb_numeroDeTelefone.setText(user.getMobileNumber());
            // Após ajustar a interface, carrega os aplicativos
//        });
        }

    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) throws IOException {
        try {
            CriarAppController menuController = (CriarAppController) getController("CriarApp");
            menuController.initialize();
            MainStage.changeScene("CriarApp");
        } catch (Exception ex) {
            System.out.println("Erro ao carregar a cena CriarApp: " + ex.getMessage());
        }
    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) getController("MenuPrincipal");
            menuController.loadApps();
            menuController.initialize();
            MainStage.changeScene("MenuPrincipal");

        } catch (Exception ex) {
            System.out.println("Erro ao carregar a cena CriarApp: " + ex.getMessage());
        }
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                SobreController s = (SobreController) getController("Sobre");
                s.initialize();
                MainStage.changeScene("Sobre");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                SettingsController s = (SettingsController) getController("Settings");
                s.initialize();
                MainStage.changeScene("Settings");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }


@FXML
void On_bt_pedir_pressed(ActionEvent event) {
    
    ploader.setVisible(true);
    // Cria uma tarefa para rodar a requisição em segundo plano
    Task<Boolean> task = new Task<>() {
        @Override
        protected Boolean call() {
            // Chama a função que faz a requisição para a API
            return sendDevRequest();
        }
    };

    // Define o que acontece quando a tarefa for concluída
    task.setOnSucceeded(workerStateEvent -> {
        boolean req = task.getValue();
        // Verifica o resultado da requisição
        if (req) {
            // Se a requisição foi bem-sucedida
             ploader.setVisible(false);
            JOptionPane.showMessageDialog(null, "O pedido foi enviado com sucesso");
        } else {
            // Se houve falha na requisição
             ploader.setVisible(false);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar");
            System.out.println("Falha ao enviar o pedido de dev.");
        }
    });

    // Define o que acontece em caso de falha na tarefa (exceções)
    task.setOnFailed(workerStateEvent -> {
        Throwable exception = task.getException();
         ploader.setVisible(false);
        JOptionPane.showMessageDialog(null, "Erro ao enviar pedido: " + exception.getMessage());
    });

    // Inicia a tarefa em uma nova Thread
    new Thread(task).start();
}



    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws IOException {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                MainStage.changeScene("TelaLogin");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void om_img_editarMail_click(MouseEvent event) {

    }

    @FXML
    void on_img_editarTelefone_click(MouseEvent event) {

    }

    @FXML
    void on_panel_BI_click(MouseEvent event) {
        handleOpenImage(img_BI);
    }

    @FXML
    void on_panel_profile_click(MouseEvent event) {

    }

//    public void setInfoOfUser(Usuario user){
//    lb_NomeDoUsuario.setText(user.getName());
//    lb_mail.setText(user.getEmail());
//    lb_numeroDeTelefone.setText(user.getMobileNumber());
//    
//    }
    private String handleOpenImage(ImageView imgv) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg", "*.PNG", "*.JPG", "*.JPEG"));
        Stage stage = (Stage) imgv.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imgv.setImage(image);
            imgv.setOpacity(1.0);
        }
        return file.toURI().toString();
    }

}
