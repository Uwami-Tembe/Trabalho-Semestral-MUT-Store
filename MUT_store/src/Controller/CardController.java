package Controller;

import View.MainStage;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class CardController {

    @FXML
    private Button bt_FinalizarCompra;

    @FXML
    private Button bt_voltar;

    @FXML
    private ComboBox<?> combo_ano;

    @FXML
    private ComboBox<?> combo_mes;

    @FXML
    private TextField genericTextField;

    @FXML
    private ImageView img_viewPassword;

    @FXML
    private Label lb_valorTotal;

    @FXML
    private PasswordField ps_cvv;

    @FXML
    private TextField txt_numeroDoCartao;

    @FXML
    private TextField txt_titular;



    @FXML
    void on_bt_FinalizarCompra(ActionEvent event) {//////////////////////////inicio do método
        //Logica de pagamento 
        
         MainStage.goTo("Carregando");
         MainStage.delaySceneWithReset("Sucesso", "Sucesso.fxml",1.2f);
         MainStage.goTo("Sucesso");
         MainStage.delaySceneWithoutReset("TelaDownload","FazerDownLoad.fxml",1.2f);
         //Deposi disso deve iniciar o download

      }/////////////////////////////////////////////////////////////fim do método

    @FXML
    void on_bt_voltar_pressed(ActionEvent event) {
        MainStage.goTo("PayMetodo");
    }
    @FXML
    void hidePassword(MouseEvent event) {
        genericTextField.setManaged(false);
        genericTextField.setVisible(false);
        ps_cvv.setManaged(true);
        ps_cvv.setVisible(true);
    }
    @FXML
    void showPassword(MouseEvent event) {
        genericTextField.setText(ps_cvv.getText());
        genericTextField.setManaged(true);
        genericTextField.setVisible(true);
        ps_cvv.setManaged(false);
        ps_cvv.setVisible(false);
    }

}
