package Controller;

import View.MainStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class FazerDownloadController {

    @FXML
    private Button bt_Baixar;

    @FXML
    private Button bt_Comentar;

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
    private ImageView img_icon;

    @FXML
    private ImageView img_shot_1;

    @FXML
    private ImageView img_shot_2;

    @FXML
    private ImageView img_shot_3;

    @FXML
    private ImageView img_shot_4;

    @FXML
    private Label lb_Descricao;

    @FXML
    private Label lb_Descricao1;

    @FXML
    private Label lb_DescricaoLonga;

    @FXML
    private Label lb_Nome;

    @FXML
    private Label lb_politics;

    @FXML
    private Label lb_developerName;

    @FXML
    private Label lb_politicsLongo;

    @FXML
    private Label lb_preco;

    @FXML
    private Label lb_preco1;

    @FXML
    private AnchorPane panel_comentarios;

    @FXML
    private Pane panel_icon;

    @FXML
    private Pane panel_shot_1;

    @FXML
    private Pane panel_shot_2;

    @FXML
    private Pane panel_shot_3;

    @FXML
    private Pane panel_shot_4;

    @FXML
    private TextArea txt_comentar;

    
    public void loadDownloadPageContent(ImageView icon,String nome, float preco, ImageView shot_1,ImageView shot_2,
                                        ImageView shot_3,ImageView shot_4,String description, String politics, 
                                        String develoerName)
    
    {
        img_icon.setImage(icon.getImage());
        lb_Nome.setText(nome);
        img_shot_1.setImage(shot_1.getImage());
        img_shot_2.setImage(shot_2.getImage());
        img_shot_3.setImage(shot_3.getImage());
        img_shot_4.setImage(shot_4.getImage());
        lb_DescricaoLonga.setText(description);
        lb_politicsLongo.setText(politics);
        lb_developerName.setText(develoerName);
        
        img_icon.setOpacity(1.0);
        img_shot_1.setOpacity(1.0);
        img_shot_2.setOpacity(1.0);
        img_shot_3.setOpacity(1.0);
        img_shot_4.setOpacity(1.0);
       
        if(preco==0.0f){
             lb_preco.setText("Grátis");
             lb_preco1.setText("");
        }
        else
            lb_preco.setText(Float.toString(preco));
    }
    
    
    @FXML
    void On_add_icon_click(MouseEvent event) {

    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) throws  Exception{
        
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_upload_pressed(ActionEvent event) {

    }

}
