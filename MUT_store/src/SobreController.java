package Controller;

import View.MainStage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    void On_bt_CriarApp_pressed(ActionEvent event) throws IOException {
            MainStage.resetScene("CriarApp", "CriarApp.fxml");
            MainStage.defineGroupForNewAppRadios();
            MainStage.goTo("CriarApp");
    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) {
        MainStage.goTo("MenuPrincipal");
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
        
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {
        MainStage.goTo("Settings");
    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws IOException {
        MainStage.resetScene("TelaLogin", "LoginDesign.fxml");
        MainStage.goTo("TelaLogin");
    }

}
