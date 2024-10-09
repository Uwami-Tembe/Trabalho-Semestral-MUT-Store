package Controller;

import View.MainStage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PayMetodoController {
    
    public static boolean useCard =false;
    public static boolean useWallet=false;
    
  
        @FXML
    private Button bt_Cancelar;

    @FXML
    public Pane panel_card;

    @FXML
    public Pane panel_carteira;

    @FXML
    void On_panel_card_pressed(MouseEvent event) throws IOException {
            MainStage.resetScene("Card", "Card.fxml");
            MainStage.goTo("Card");
    }
    @FXML
    void On_panel_carteira_pressed(MouseEvent event) throws IOException {
            MainStage.resetScene("Carteira", "Carteira.fxml");
            MainStage.goTo("Carteira");
    }

    @FXML
    void onBT_CancelarPressed(ActionEvent event) {
        MainStage.goTo("MenuPrincipal");
    }

}
