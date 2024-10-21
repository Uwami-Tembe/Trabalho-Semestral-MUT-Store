package Controller;

import Model.AppModelDetails;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PayMetodoController {

    public static boolean useCard = false;
    public static boolean useWallet = false;

    @FXML
    private Button bt_Cancelar;

    @FXML
    public Pane panel_card;

    @FXML
    public Pane panel_carteira;

    public AppModelDetails app;

    public AppModelDetails getApp() {
        return app;
    }

    public void setApp(AppModelDetails app) {
        this.app = app;
    }

    @FXML
    void On_panel_card_pressed(MouseEvent event) throws IOException {
        CardController cc = (CardController) getController("Card");

        cc.setApp(app);
        System.out.println(app.getPreco());
//            MainStage.resetScene("Card", "Card.fxml");
//            MainStage.goTo("Card");

        changeScene("Card");
    }

    @FXML
    void On_panel_carteira_pressed(MouseEvent event) throws IOException {
//            MainStage.resetScene("Carteira", "Carteira.fxml");
//            MainStage.goTo("Carteira");

        CarteiraController cc = (CarteiraController) getController("Carteira");

        cc.setApp(app);
        System.out.println(app.getPreco());
//            MainStage.resetScene("Card", "Card.fxml");
//            MainStage.goTo("Card");

        changeScene("Carteira");
    }

    @FXML
    void onBT_CancelarPressed(ActionEvent event) {
        MainStage.goTo("MenuPrincipal");
    }

}
