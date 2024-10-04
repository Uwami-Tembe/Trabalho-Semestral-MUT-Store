package Controller;

import View.MainStage;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class DigitarCodigoController {

    @FXML
    private Button bt_verificar;

    @FXML
    private TextField txt_codigo;

    @FXML
    private Button voltar;

    @FXML
    void On_bt_verificar_Pressed(ActionEvent event) throws Exception {
         MainStage.changeScene("Carregando");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        //Aplicar uma condição se o código for o correcto segue os códigos abaixo mudando de tela caso contrário deve emitir um erro
         try{ 
             pause.setOnFinished(e->{
                 try {
                     MainStage.changeScene("AlterarSenha");
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
         }
         catch(Exception e){
             e.printStackTrace();
         }
         pause.play();
    }

    @FXML
    void On_bt_voltar_pressed(ActionEvent event) throws Exception {
         MainStage.changeScene("Carregando");
         PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        
         try{ 
             pause.setOnFinished(e->{
                 try {
                     MainStage.changeScene("TelaLogin");
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
         }
         catch(Exception e){
             e.printStackTrace();
         }
         pause.play();
    }

}
