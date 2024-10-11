package Controller;

import Model.AppModel;
import Model.AppModelSummary;
import Model.AppModelDetails;
import Models.Api.App;
import View.MainStage;
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
    changeScene("Carregando"); // Mostra a tela de carregamento imediatamente

    PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
    pause.setOnFinished(ev -> {
        CompletableFuture.supplyAsync(() -> App.buscarAppsResumidos())
            .thenAcceptAsync(appSummaryList -> {
                // Mudança da cena deve ocorrer após o carregamento dos aplicativos
                Platform.runLater(() -> {
                    panel_apps.setHgap(20);
                    panel_apps.setVgap(30);
                    panel_apps.setPrefColumns(4);
                    panel_apps.getChildren().clear();

                    if (appSummaryList.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText(null);
                        alert.setContentText("Não foi possível buscar os aplicativos.");
                        alert.showAndWait();
                        changeScene("MenuPrincipal"); 
                    } else {
                        for (AppModelSummary app : appSummaryList) {
                            VBox appBox = new VBox();
                            appBox.setSpacing(10);

                            ImageView formattedImage = new ImageView(app.getIconUrl());
                            formattedImage.setFitWidth(80);
                            formattedImage.setFitHeight(80);
                            formattedImage.setPreserveRatio(true);

                            Label appName = new Label(app.getNome());
                            appName.setStyle("-fx-text-fill: #517983");
                            appName.setFont(Font.font("System", FontWeight.BOLD, 14));

                            appBox.getChildren().addAll(formattedImage, appName);

                            // Ao clicar, busca detalhes completos
                            appBox.setOnMouseClicked(t -> {
                                try {
                                    // Buscar detalhes do aplicativo antes de mudar de cena
                                    CompletableFuture.supplyAsync(() -> App.buscarDetalhesApp(app.getAppIdString()))
                                        .thenAcceptAsync(appDetails -> Platform.runLater(() -> {
                                            // Aqui mudamos a cena após buscar os detalhes
                                            changeScene("TelaDownload");

                                            // Obter o controlador após a mudança de cena
                                            FazerDownloadController controller = (FazerDownloadController) getController("TelaDownload");
                                            
                                            // Limpe ou carregue o novo conteúdo
                                            if (controller != null) {
                                                controller.loadDownloadPageContent(appDetails);
                                            } else {
                                                System.err.println("FazerDownloadController é null!");
                                            }
                                            System.out.println(appDetails.toString());
                                        }));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            panel_apps.getChildren().add(appBox);
                        }
                        // Mantenha a tela de carregamento até que todos os detalhes estejam carregados
                        changeScene("MenuPrincipal"); 
                    }
                });
            })
            .exceptionally(ex -> {
                // Trate erros aqui
                ex.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao buscar os aplicativos: " + ex.getMessage());
                    alert.showAndWait();
                    changeScene("MenuPrincipal"); // Retorne para a tela principal em caso de erro
                });
                return null;
            });
    });

    pause.play();
}




    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {

    }
    
      @FXML
    void on_panel_profile_click(ActionEvent event) {

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
