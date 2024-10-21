package Controller;

import static Constants.Constants.TOKEN_FILE_PATH;
import Model.Usuario;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import java.io.File;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.application.Platform;

public class SucessoController {

    @FXML
    private Label lb_menssagemDeSucesso;

    // Método chamado quando a tela é inicializada
    @FXML
    public void initialize() {
           
        // Configura a mensagem de sucesso
        lb_menssagemDeSucesso.setText("Operação realizada com sucesso!");

        MenuPrincipalController menuController = (MenuPrincipalController) getController("MenuPrincipal");
        Usuario user = userInfo();


        changeScene("MenuPrincipal");
}
}