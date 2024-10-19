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
        if (user != null) {
            
            if(user.getUserType().equals("dev")){
            // Definir o nome do usuário no controlador da tela principal
            menuController.setLb_NomeDoUsuario(user.getName());
            menuController.setUser(user);// Atualiza o Label com o nome do usuário
            menuController.initialize(true);
            }
            if(user.getUserType().equals("normal")){
            // Definir o nome do usuário no controlador da tela principal
            menuController.setLb_NomeDoUsuario(user.getName());
            menuController.setUser(user);// Atualiza o Label com o nome do usuário
            menuController.initialize(false);
            }

        } else {
            // Lidar com o erro se o usuário não foi encontrado
            System.err.println("Erro ao buscar informações do usuário.");
        }

        changeScene("MenuPrincipal");
}
}