package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AdminController {

    @FXML
    private TableColumn<?, ?> ColunaDataApp;

    @FXML
    private TableColumn<?, ?> ColunaDataUser;

    @FXML
    private TableColumn<?, ?> ColunaNomeDaApp;

    @FXML
    private TableColumn<?, ?> ColunaNomeDoDev;

    @FXML
    private TableColumn<?, ?> ColunaNomeDoUser;

    @FXML
    private Button bt_AprovarApp;

    @FXML
    private Button bt_AprovarUser;

    @FXML
    private Button bt_RecusarApp;

    @FXML
    private Button bt_RecusarUser;

    @FXML
    private Button bt_sair;

    @FXML
    private ImageView img_BI;

    @FXML
    private ImageView img_file;

    @FXML
    private ImageView img_icon;

    @FXML
    private ImageView img_shot1;

    @FXML
    private ImageView img_shot2;

    @FXML
    private ImageView img_shot3;

    @FXML
    private ImageView img_shot4;

    @FXML
    private Label lb_Metodo_1;

    @FXML
    private Label lb_Metodo_2;

    @FXML
    private Label lb_categoria;

    @FXML
    private Label lb_ficheiro;

    @FXML
    private Label lb_icon;

    @FXML
    private Label lb_mail;

    @FXML
    private Label lb_numeroDeTelefone;

    @FXML
    private Label lb_username;

    @FXML
    private TableView<?> tabela_apps;

    @FXML
    private TableView<?> tabela_users;

    @FXML
    void On_bt_sair_pressed(ActionEvent event) {

    }

    @FXML
    void onBT_AprovarAppPressed(ActionEvent event) {

    }

    @FXML
    void onBT_AprovarUserPressed(ActionEvent event) {

    }

    @FXML
    void onBT_RecusarAppPressed(ActionEvent event) {

    }

    @FXML
    void onBT_RecusarUserPressed(ActionEvent event) {

    }

}
