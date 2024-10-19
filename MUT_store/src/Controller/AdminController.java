package Controller;

import Model.App;
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
import Models.Api.User;
import static Models.Api.User.carregarAppsDaAPI;
import static Models.Api.User.carregarUsuariosDaAPI;
import View.MainStage;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.util.concurrent.CompletableFuture;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.TableCell;
import javafx.util.Duration;

public class AdminController {

    @FXML
    private TableColumn<Usuario, String> ColunaDataUser;

    @FXML
    private TableColumn<Usuario, String> ColunaNomeDoUser;

    @FXML
    private TableColumn<Usuario, Integer> ColunaStatusUser;

    @FXML
    private TableColumn<Usuario, String> ColunaTipoUser;

    //apps
    @FXML
    private TableColumn<Usuario, String> ColunaAppData;

    @FXML
    private TableColumn<Usuario, String> ColunaAppName;

    @FXML
    private TableColumn<Usuario, Integer> ColunaAppStatus;

    @FXML
    private TableColumn<Usuario, String> ColunaNomeDoDev;

    @FXML
    private TableColumn<Usuario, Integer> ColunaAppID;

    @FXML
    private TableView<Usuario> tabela_users;

    @FXML
    private TableView<App> tabela_apps;

    @FXML
    private Button bt_AprovarUser;

    @FXML
    private Button bt_RecusarUser;

    @FXML
    private Button bt_RefreshUser;

    @FXML
    private Button bt_RefreshApp;

    @FXML
    private Button bt_sair;
    
    @FXML
    private ProgressIndicator ploader;

    @FXML
    private ImageView img_BI;

    @FXML
    private Label lb_mail;

    @FXML
    private Label lb_numeroDeTelefone;

    @FXML
    private Label lb_username;

    @FXML
    private ImageView img_icon = new ImageView();

    @FXML
    private ImageView img_shot1 = new ImageView();

    @FXML
    private ImageView img_shot2 = new ImageView();
    @FXML
    private ImageView img_shot3 = new ImageView();
    @FXML
    private ImageView img_shot4 = new ImageView();

    @FXML
    private ImageView img_file = new ImageView();

    @FXML
    private Label lb_Metodo_1;

    @FXML
    private Label lb_Metodo_2;

    @FXML
    private Label lb_categoria;

    @FXML
    void onBT_RefreshUserPressed(ActionEvent event) {
        // Lógica para aprovar um usuário
        initialize();
    }

    @FXML
    void onBT_RefreshAppPressed(ActionEvent event) {
        // Lógica para aprovar um usuário
    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) {
        // Implementar a lógica para sair
           MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

        try {
            pause.setOnFinished(e -> {
                try {
                    MainStage.changeScene("TelaLogin");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        pause.play();
    }

    @FXML
    void onBT_AprovarUserPressed(ActionEvent event) {
        Usuario usuarioSelecionado = tabela_users.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado != null) {
            User.atualizarStatusUsuario(usuarioSelecionado.getUsername(), true);
            // Atualizar a tabela após a alteração
            initialize();
        } else {
            System.err.println("Nenhum usuário selecionado.");
        }
    }

    @FXML
    void onBT_RecusarUserPressed(ActionEvent event) {
        Usuario usuarioSelecionado = tabela_users.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado != null) {
            User.atualizarStatusUsuario(usuarioSelecionado.getUsername(), false);
            // Atualizar a tabela após a alteração
            initialize();
        } else {
            System.err.println("Nenhum usuário selecionado.");
        }
    }

    @FXML
    void onBT_AprovarAppPressed(ActionEvent event) {
         App appSelecionado = tabela_apps.getSelectionModel().getSelectedItem();
        if (appSelecionado != null) {
            User.atualizarStatusApp(appSelecionado.getId(), true);
            // Atualizar a tabela após a alteração
            initialize();
        } else {
            System.err.println("Nenhum app selecionado.");
        }
        // Lógica para aprovar um usuário
    }

    @FXML
    void onBT_RecusarAppPressed(ActionEvent event) {
         App appSelecionado = tabela_apps.getSelectionModel().getSelectedItem();
        if (appSelecionado != null) {
            User.atualizarStatusApp(appSelecionado.getId(), false);
            // Atualizar a tabela após a alteração
            initialize();
        } else {
            System.err.println("Nenhum app selecionado.");
        }
        // Lógica para recusar um usuário
    }

@FXML
public void initialize() {
     
    // Configuração das colunas
    configurarColunas();
    ploader.setVisible(true);

    // Exibe o progress indicator
   

    // Carrega dados de forma assíncrona
    carregarDadosAsync();

    // Adicionar listeners para seleção nas tabelas
    tabela_users.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            mostrarDetalhesDoUsuario(newSelection);
        }
    });

    tabela_apps.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            mostrarDetalhesDoApp(newSelection);
        }
    });
}


// Agrupar a configuração das colunas em um método separado para maior clareza
private void configurarColunas() {
    // Configuração das colunas de aplicativos
    ColunaAppID.setCellValueFactory(new PropertyValueFactory<>("id"));
    ColunaAppName.setCellValueFactory(new PropertyValueFactory<>("nome"));
    ColunaNomeDoDev.setCellValueFactory(new PropertyValueFactory<>("developerName"));
    ColunaAppStatus.setCellValueFactory(new PropertyValueFactory<>("isApproved"));
    ColunaAppData.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

    // Configuração das colunas de usuários
    ColunaNomeDoUser.setCellValueFactory(new PropertyValueFactory<>("username"));
    ColunaDataUser.setCellValueFactory(new PropertyValueFactory<>("posted"));
    ColunaTipoUser.setCellValueFactory(new PropertyValueFactory<>("userType"));
    ColunaStatusUser.setCellValueFactory(new PropertyValueFactory<>("validated"));

    // Mapear status para exibição mais eficiente
    ColunaStatusUser.setCellFactory(column -> new TableCell<Usuario, Integer>() {
        @Override
        protected void updateItem(Integer validated, boolean empty) {
            super.updateItem(validated, empty);

            if (empty || validated == null) {
                setText(null);
            } else {
                setText(mapearStatus(validated));
            }
        }
    });
}

// Método para mapear o status dos usuários
private String mapearStatus(Integer validated) {
    switch (validated) {
        case 0: return "Aprovado";
        case 1: return "Recusado";
        case 2: return "Pendente";
        default: return "Desconhecido";
    }
}

// Carregar dados de forma assíncrona para melhorar a eficiência
private void carregarDadosAsync() {
    CompletableFuture.runAsync(() -> {
        // Carregar dados de forma assíncrona
        ObservableList<Usuario> listaUsuarios = carregarUsuariosDaAPI();
        ObservableList<App> listaApps = carregarAppsDaAPI();

        // Atualizar UI na thread principal após o carregamento
        Platform.runLater(() -> {
            if (listaUsuarios != null) {
                tabela_users.setItems(listaUsuarios);
            }
            if (listaApps != null) {
                tabela_apps.setItems(listaApps);
            }

            // Esconde o progress indicator ao finalizar o carregamento
            ploader.setVisible(false);
        });
    }).exceptionally(ex -> {
        Platform.runLater(() -> {
            System.err.println("Erro ao carregar os dados: " + ex.getMessage());

            // Também esconde o progress indicator em caso de erro
            ploader.setVisible(false);
        });
        return null;
    });
}

private void mostrarDetalhesDoUsuario(Usuario usuario) {
    lb_username.setText(usuario.getUsername());
    lb_numeroDeTelefone.setText(usuario.getMobileNumber()); // Verificar se precisa converter
    lb_mail.setText(usuario.getEmail());
//    img_BI.setImage(usuario.getImg_BI());

    // Mapear o status se necessário
    // lblStatusUsuario.setText(mapearStatus(usuario.getValidated()));
}

private void mostrarDetalhesDoApp(App app) {
    lb_categoria.setText(app.getCategory());
    lb_Metodo_1.setText(app.payments.isIsMpesa() ? "Mpesa/Emola" : "Sem");
    lb_Metodo_2.setText(app.payments.isIsBankCard() ? "BankCard" : "Sem");

    // Exibir as imagens das screenshots
//    img_shot1.setImage(app.getImage(0));
//    img_shot2.setImage(app.getImage(1));
//    img_shot3.setImage(app.getImage(2));
//    img_shot4.setImage(app.getImage(3));
}

}