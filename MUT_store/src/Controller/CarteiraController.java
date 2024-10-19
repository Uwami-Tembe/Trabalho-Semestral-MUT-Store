package Controller;

import Model.AppModelDetails;
import static Models.Api.App.buyAppCarteira;
import View.MainStage;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.concurrent.Task;
import javax.swing.JOptionPane;

public class CarteiraController {

    
        @FXML
    private ProgressIndicator ploader;
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
  public AppModelDetails app;

    public AppModelDetails getApp() {
        return app;
    }

    public void setApp(AppModelDetails app) {
        ploader.setVisible(false);
        this.app = app;
      lb_valorTotal.setText(Double.toString(app.getPreco()));
    }



@FXML
void on_bt_FinalizarCompra(ActionEvent event) {
    ploader.setVisible(true);
    // Obtém os dados necessários para a compra
    String msisdn = txt_numeroDeTelefone.getText(); // Número de telefone do usuário
    String appId = app.getId(); // Presumindo que o AppModelDetails tenha um método getId()

    // Criação de um Task para executar a lógica de pagamento em segundo plano
    Task<Boolean> purchaseTask = new Task<Boolean>() {
        @Override
        protected Boolean call() throws Exception {
            // Lógica de pagamento
            return buyAppCarteira(msisdn, appId);
        }

        @Override
        protected void succeeded() {
            // O que fazer se a compra for bem-sucedida
            if (getValue()) { // getValue() retorna o resultado do método call()
                MainStage.goTo("Carregando");
                MainStage.delaySceneWithReset("Sucesso", "Sucesso.fxml", 1.2f);
                MainStage.goTo("Sucesso");
                MainStage.delaySceneWithoutReset("TelaPrincipal", "MenuPrincipal.fxml", 1.2f);
                // Inicie o download se necessário
            } else {
                // Caso a compra falhe, exiba uma mensagem de erro
                ploader.setVisible(false);
                JOptionPane.showMessageDialog(null, "A compra falhou. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        protected void failed() {
            // O que fazer se ocorrer uma falha na execução do Task
            ploader.setVisible(false);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a compra. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    };

    // Executar o Task em uma nova Thread
    new Thread(purchaseTask).start();
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
