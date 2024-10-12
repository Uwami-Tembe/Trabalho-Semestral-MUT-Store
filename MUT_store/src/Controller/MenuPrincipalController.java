package Controller;

import Model.AppModel;
import Model.AppModelSummary;
import Model.AppModelDetails;
import Models.Api.App;
import View.MainStage;
import java.util.logging.Logger;
import java.util.logging.Level;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class MenuPrincipalController {

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
    private ImageView img_pesquisar;

    @FXML
    private Label lb_NomeDoUsuario;

    @FXML
    public TilePane panel_apps = new TilePane();

    @FXML
    private ScrollPane sp_apps;

    @FXML
    private TextField txt_pesquisa;
    

    public Label getLb_NomeDoUsuario() {
        return lb_NomeDoUsuario;
    }

    public void setLb_NomeDoUsuario(String lb_NomeDoUsuario) {
        this.lb_NomeDoUsuario.setText(lb_NomeDoUsuario) ;
    }
    
    
    
    
 private static final Logger logger = Logger.getLogger(MenuPrincipalController.class.getName());
//    @FXML
//    private ProgressIndicator progressIndicator; // Certifique-se de que isso está no controlador

    FazerDownloadController f = new FazerDownloadController();

    public void setFazerDownloadController(FazerDownloadController f) {
        this.f = f;
    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {

        try {
            MainStage.changeScene("CriarApp.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    // Cria um indicador de progresso (Loader)

    @FXML
    public void updateMenu() {
//        panel_apps.setHgap(20);
//        panel_apps.setVgap(30);
//        panel_apps.setPrefWrapLength(4);
//                
//       for(AppModel app: CriarAppController.appList){
//            VBox appBox = new VBox();
//            appBox.setSpacing(20);
//            ImageView formatedImage= new ImageView();            
//            formatedImage.setImage(app.getIconImage().getImage());
//            formatedImage.setFitWidth(80);
//            formatedImage.setFitHeight(80);
//            
//            Label appName = new Label(app.getNome());
//            //appName.setAlignment(Pos.CENTER);
//            appName.setLayoutX(appBox.getLayoutX());
//            appName.setLayoutY(appBox.getLayoutY()+5);
//            appName.setStyle("-fx-text-fill: #517983");
//            appName.setFont(Font.font("System",javafx.scene.text.FontWeight.BOLD,14));
//            
//            appBox.getChildren().addAll(formatedImage, appName);
//            
//            appBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
//                @Override
//                public void handle(MouseEvent t){
//                    try {
//                       f=(FazerDownloadController)MainStage.changeScene("FazerDownload.fxml");
//                        setFazerDownloadController(f);
//                        f.loadDownloadPageContent(app);
//    
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//           
//            
//            panel_apps.getChildren().addAll(appBox);
//        }
    }

@FXML
void On_bt_Loja_pressed(ActionEvent event) {
    changeScene("Carregando"); // Mostra a tela de carregamento

    // Adicionar uma pequena pausa para simular o tempo de carregamento
    PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
    pause.setOnFinished(ev -> {
        CompletableFuture.supplyAsync(() -> App.buscarAppsResumidos())
            .thenAcceptAsync(appSummaryList -> Platform.runLater(() -> {
                panel_apps.setHgap(20);
                panel_apps.setVgap(30);
                panel_apps.setPrefColumns(4);
                panel_apps.getChildren().clear();

                // Verifica se a lista de apps está vazia
                if (appSummaryList.isEmpty()) {
                    showError("Não foi possível buscar os aplicativos.");
                    changeScene("MenuPrincipal");
                } else {
                    // Carrega os aplicativos de forma resumida
                    carregarAplicativosResumidos(appSummaryList);
                }
            }))
            .exceptionally(ex -> {
                Platform.runLater(() -> {
                    showError("Erro ao buscar os aplicativos: " + ex.getMessage());
                    changeScene("MenuPrincipal");
                });
                return null;
            });
    });
    pause.play();
}


// Método para mostrar um alerta de erro
private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erro");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void carregarAplicativosResumidos(List<AppModelSummary> appSummaryList) {
    for (AppModelSummary app : appSummaryList) {
        VBox appBox = new VBox();
        appBox.setSpacing(10);

        // Carrega a imagem do aplicativo
        ImageView formattedImage = new ImageView(app.getIconUrl());
        formattedImage.setFitWidth(80);
        formattedImage.setFitHeight(80);
        formattedImage.setPreserveRatio(true);

        // Nome do aplicativo
        Label appName = new Label(app.getNome());
        appName.setStyle("-fx-text-fill: #517983");
        appName.setFont(Font.font("System", FontWeight.BOLD, 14));

        appBox.getChildren().addAll(formattedImage, appName);

        // Evento de clique para buscar os detalhes do aplicativo
        appBox.setOnMouseClicked(t -> buscarDetalhesAplicativo(app));

        // Adiciona o aplicativo ao painel de visualização
        panel_apps.getChildren().add(appBox);
    }
    changeScene("MenuPrincipal"); // Retorna à tela principal depois de carregar todos
}

// Variável para armazenar a tarefa de busca atual
private CompletableFuture<AppModelDetails> currentTask;

void buscarDetalhesAplicativo(AppModelSummary app) {
    // Log de início do método
    System.out.println("Iniciando a busca por detalhes do aplicativo com ID: " + app.getAppIdString());

    // Cancela a tarefa de busca pendente, se ainda não tiver sido concluída
    if (currentTask != null && !currentTask.isDone()) {
        System.out.println("Tarefa anterior ainda em andamento. Cancelando tarefa anterior.");
        currentTask.cancel(true);
    }

    // Instância da API de aplicativos
    App api = new App(); 

    // Log de início de nova tarefa assíncrona
    System.out.println("Iniciando nova tarefa assíncrona para buscar detalhes do aplicativo.");

    // Inicia uma nova tarefa assíncrona para buscar os detalhes do aplicativo
    currentTask = CompletableFuture.supplyAsync(() -> {
        try {
            // Log da busca de detalhes do aplicativo
            System.out.println("Buscando detalhes do aplicativo para o ID: " + app.getAppIdString());
            return api.buscarDetalhesApp(app.getAppIdString());
        } catch (Exception e) {
            // Log de erro durante a busca
            System.out.println("Erro ao buscar detalhes do aplicativo: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar detalhes do aplicativo.", e);
        }
    })
    // Manipula o resultado da busca quando concluída
    .thenApplyAsync(appDetails -> {
        // Log do retorno da busca
        System.out.println("Busca por detalhes concluída. Atualizando interface.");

        // Atualiza a interface na thread principal do JavaFX
        Platform.runLater(() -> {
            if (appDetails != null) {
                // Log de sucesso na busca
                System.out.println("Detalhes do aplicativo encontrados. Atualizando interface.");

                // Obtém o controlador da tela de download
                FazerDownloadController controller = (FazerDownloadController) getController("TelaDownload");

                if (controller != null) {
                    // Limpa a interface e carrega os novos detalhes do aplicativo
                    clearInterface(controller);
                    controller.loadDownloadPageContent(appDetails);

                    // Muda para a tela de download
                    System.out.println("Mudando para a cena da tela de download.");
                    changeScene("TelaDownload");
                } else {
                    System.out.println("FazerDownloadController é null!");
                }
            } else {
                System.out.println("Detalhes do aplicativo retornaram null.");
            }
        });
        return appDetails;  // Retorna o resultado (AppModelDetails)
    })
    // Captura e trata exceções que possam ocorrer durante a execução assíncrona
    .exceptionally(ex -> {
        // Log de erro durante a execução assíncrona
        System.out.println("Erro ao buscar os detalhes do aplicativo: " + ex.getMessage());

        // Exibe uma mensagem de erro na interface
        Platform.runLater(() -> showError("Erro ao buscar os detalhes do aplicativo: " + ex.getMessage()));
        return null;
    });

    // Log de término do método
    System.out.println("Método buscarDetalhesAplicativo concluído.");
}

// Função para limpar a interface antes de carregar novos dados
private void clearInterface(FazerDownloadController controller) {
    // Verifica se o controller é válido antes de limpar o conteúdo
    if (controller != null) {
        // Log de limpeza da interface
        System.out.println("Limpando o conteúdo anterior da tela de download.");
        controller.clearPreviousContent();  // Limpa o conteúdo anterior da tela de download
    } else {
        System.out.println("Controller é null. Não é possível limpar a interface.");
    }
}




    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {

    }
@FXML
void on_panel_profile_click(MouseEvent event) {  // Mudando para MouseEvent
    MainStage.changeScene("Carregando");

    PauseTransition pause = new PauseTransition(Duration.seconds(1.2));

    try {
        pause.setOnFinished(e -> {
            try {
                MainStage.changeScene("Perfil");
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
    void On_bt_definicoes_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws Exception {
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
    void On_img_pesquisar_click(MouseEvent event) {

    }

}
