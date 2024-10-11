package Controller;

import View.MainStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CarteiraController {

    @FXML
    private Button bt_FinalizarCompra;

    @FXML
    private Button bt_voltar;

    @FXML
    private TextField genericTextField;

    @FXML
    private ImageView img_viewPassword;

    @FXML
    private Label lb_valorTotal;

    @FXML
    private PasswordField ps_pin;

    @FXML
    private TextField txt_numeroDeTelefone;



    @FXML
    void on_bt_FinalizarCompra(ActionEvent event) {
         //Lógica de pagamento
         MainStage.goTo("Carregando");
         MainStage.delaySceneWithReset("Sucesso", "Sucesso.fxml",1.2f);
         MainStage.goTo("Sucesso");
         MainStage.delaySceneWithoutReset("TelaDownload","FazerDownLoad.fxml",1.2f);
         //Deposi disso deve iniciar o download

    }

    @FXML
    void on_bt_voltar(ActionEvent event) {
        MainStage.goTo("PayMetodo");
    }
    @FXML
    void hidePassword(MouseEvent event) {
        genericTextField.setManaged(false);
        genericTextField.setVisible(false);
        ps_pin.setManaged(true);
        ps_pin.setVisible(true);
    }
    @FXML
    void showPassword(MouseEvent event) {
        genericTextField.setText(ps_pin.getText());
        genericTextField.setManaged(true);
        genericTextField.setVisible(true);
        ps_pin.setManaged(false);
        ps_pin.setVisible(false);
    }

}
