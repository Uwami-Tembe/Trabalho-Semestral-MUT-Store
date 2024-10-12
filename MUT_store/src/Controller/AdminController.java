package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.Usuario; // Certifique-se de importar a classe Usuario corretamente
import static Models.Api.User.carregarUsuariosDaAPI;
import javafx.scene.control.TableCell;

public class AdminController {

    @FXML
    private TableColumn<Usuario, String> ColunaDataUser;

    @FXML
    private TableColumn<Usuario, String> ColunaNomeDoUser;

    @FXML
    private TableColumn<Usuario, Integer> ColunaStatusUser;

    @FXML
    private TableColumn<Usuario, String> ColunaTipoUser;

    @FXML
    private TableView<Usuario> tabela_users;

    @FXML
    private Button bt_AprovarUser;

    @FXML
    private Button bt_RecusarUser;

    @FXML
    private Button bt_sair;

    @FXML
    private ImageView img_BI;

    @FXML
    private Label lb_mail;

    @FXML
    private Label lb_numeroDeTelefone;

    @FXML
    private Label lb_username;

    @FXML
    void On_bt_sair_pressed(ActionEvent event) {
        // Implementar a lógica para sair
    }

    @FXML
    void onBT_AprovarUserPressed(ActionEvent event) {
        // Lógica para aprovar um usuário
    }

    @FXML
    void onBT_RecusarUserPressed(ActionEvent event) {
        // Lógica para recusar um usuário
    }

    @FXML
    void onBT_AprovarAppPressed(ActionEvent event) {
        // Lógica para aprovar um usuário
    }

    @FXML
    void onBT_RecusarAppPressed(ActionEvent event) {
        // Lógica para recusar um usuário
    }

 @FXML
public void initialize() {
    // Configuração das colunas
    ColunaNomeDoUser.setCellValueFactory(new PropertyValueFactory<>("name"));
    ColunaDataUser.setCellValueFactory(new PropertyValueFactory<>("posted"));
    ColunaTipoUser.setCellValueFactory(new PropertyValueFactory<>("userType"));

ColunaStatusUser.setCellValueFactory(new PropertyValueFactory<>("validated"));

// Aqui, o TableCell deve ser do tipo <Usuario, Integer> já que validated é um número
ColunaStatusUser.setCellFactory(column -> {
    return new TableCell<Usuario, Integer>() {
        @Override
        protected void updateItem(Integer validated, boolean empty) {
            super.updateItem(validated, empty);

            if (empty || validated == null) {
                setText(null); // Limpa o texto quando a célula está vazia
            } else {
                // Mapear os números para as strings
                switch (validated) {
                    case 0:
                        setText("aprovado");
                        break;
                    case 1:
                        setText("recusado");
                        break;
                    case 2:
                        setText("pendente");
                        break;
                    default:
                        setText(""); // Ocultar valores diferentes de 0, 1 e 2
                        setGraphic(null);
                        break;
                }
            }
        }
    };
});

    // Carregar os dados da API e preencher a tabela
    carregarUsuarios();
}


    public void carregarUsuarios() {
        ObservableList<Usuario> listaUsuarios = carregarUsuariosDaAPI(); // Chama o método que faz a requisição à API

        if (listaUsuarios != null) {
            tabela_users.setItems(listaUsuarios); // Define os itens na tabela
        } else {
            System.err.println("Erro ao carregar a lista de usuários.");
        }
    }
}
