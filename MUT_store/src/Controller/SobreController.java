package Controller;

import Model.Usuario;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SobreController {

    @FXML
    private Button bt_criarApp;

    @FXML
    private Button bt_definicoes;

    @FXML
    private Button bt_loja;

    @FXML
    private Button bt_sair;

    @FXML
    private Button bt_sobre;

    
        @FXML
    private ImageView img_iconCriar;
        @FXML
    public void initialize() {
        // Garantindo que a interface gráfica seja manipulada na thread correta
        Platform.runLater(() -> {
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
                    return;
                }

                // Caso contrário, exibe os botões de criação
                bt_criarApp.setVisible(true);
                if (img_iconCriar != null) {
                    img_iconCriar.setVisible(true);
                }
            }

            // Após ajustar a interface, carrega os aplicativos
        });
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
}
