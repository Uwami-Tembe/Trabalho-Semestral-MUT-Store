package Controller;

import Model.AppModelSummary;
import Model.Usuario;
import Models.Api.App;
import View.MainStage;
import static View.MainStage.getController;
import static View.MainStage.changeScene;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import io.reactivex.rxjava3.disposables.Disposable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MenuPrincipalController {
private io.reactivex.rxjava3.disposables.Disposable currentTask;
    @FXML
    private Button bt_criarApp, bt_definicoes, bt_loja, bt_sair, bt_sobre;

    @FXML
    private ImageView img_pesquisar, img_IconCriar;

    @FXML
    private Label lb_NomeDoUsuario;

    @FXML
    public TilePane panel_apps = new TilePane();
    
    @FXML
    public TilePane panel_games = new TilePane();
      
    @FXML
    public TilePane panel_best = new TilePane();

    @FXML
    private ScrollPane sp_apps;

    @FXML
    private TextField txt_pesquisa;

     @FXML
    private com.gluonhq.charm.glisten.control.ProgressIndicator ploader;
        
    private Usuario user = new Usuario();

    private static final Logger logger = Logger.getLogger(MenuPrincipalController.class.getName());

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void setLb_NomeDoUsuario(String nomeUsuario) {
        this.lb_NomeDoUsuario.setText(nomeUsuario);
    }

    @FXML
    public void initialize(boolean showCreateIcon) {
        loadAppsHome();
        if (img_IconCriar != null && bt_criarApp != null) {
            this.img_IconCriar.setVisible(showCreateIcon);
            bt_criarApp.setVisible(showCreateIcon);
        }
    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {
        try {
            MainStage.changeScene("CriarApp.fxml");
        } catch (Exception ex) {
            logger.severe("Erro ao carregar a cena CriarApp: " + ex.getMessage());
        }
    }
    
        @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {

    }
    
    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) {
        loadAppsHome();
    }
    
    @FXML
    void on_panel_profile_click(MouseEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                PerfilController pc = (PerfilController) getController("Perfil");
                pc.setInfoOfUser(user);
                MainStage.changeScene("Perfil");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                MainStage.changeScene("TelaLogin");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void On_img_pesquisar_click(MouseEvent event) {

    }
    
   @FXML
    void on_bt_search(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            ploader.setVisible(true);
            String keyword = txt_pesquisa.getText().trim();
            if (!keyword.isEmpty()) {
                searchApps(keyword);
            } else {
                ploader.setVisible(false);
                System.out.println("Digite uma palavra-chave para buscar.");
            }
        }
}

    public void loadAppsHome() {
        loadApps("");
    }

    public void searchApps(String keyword) {
        loadApps(keyword);
    }

 private void loadApps(String keyword) {
        changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(ev -> {
            Single.fromCallable(() -> App.buscarApps(keyword))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .subscribe(appSummaryList -> Platform.runLater(() -> processApps(appSummaryList)),
                            ex -> Platform.runLater(() -> showError("Erro ao buscar os aplicativos: " + ex.getMessage())));
        });
        pause.play();
    }

 private void processApps(List<AppModelSummary> appSummaryList) {
        panel_apps.getChildren().clear();
        panel_games.getChildren().clear();
        panel_best.getChildren().clear();

        if (appSummaryList.isEmpty()) {
            showError("Não foi possível buscar os aplicativos.");
            changeScene("MenuPrincipal");
            return;
        }

        List<AppModelSummary> gamesApps = new ArrayList<>();
        List<AppModelSummary> sortedApps = new ArrayList<>(appSummaryList);
        sortedApps.sort(Comparator.comparingInt(AppModelSummary::getDownload).reversed());

        int count = 0;

        for (AppModelSummary app : sortedApps) {
            VBox appBox = createAppBox(app);
            panel_apps.getChildren().add(appBox);

            if ("game".equalsIgnoreCase(app.getCategory())) {
                gamesApps.add(app);
            }

            if (app.getDownload() >= 5 && count < 4) {
                panel_best.getChildren().add(createAppBox(app));
                count++;
            }
        }

        for (AppModelSummary gameApp : gamesApps) {
            panel_games.getChildren().add(createAppBox(gameApp));
        }

        changeScene("MenuPrincipal");
    }

    private VBox createAppBox(AppModelSummary app) {
        VBox appBox = new VBox(10);

        ImageView appIcon = new ImageView(app.getIconUrl());
        appIcon.setFitWidth(80);
        appIcon.setFitHeight(80);
        appIcon.setPreserveRatio(true);

        Label appName = new Label(app.getNome());
        appName.setFont(Font.font("System", FontWeight.BOLD, 14));
        appName.setStyle("-fx-text-fill: #517983");

        appBox.getChildren().addAll(appIcon, appName);
        appBox.setOnMouseClicked(t -> buscarDetalhesAplicativo(app));

        return appBox;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInterface(FazerDownloadController controller) {
        if (controller != null) {
            System.out.println("Limpando o conteúdo anterior da tela de download.");
            controller.clearPreviousContent();
        } else {
            System.out.println("Controller é null. Não é possível limpar a interface.");
        }
    }

    private void buscarDetalhesAplicativo(AppModelSummary app) {
        ploader.setVisible(true);
        System.out.println("Iniciando a busca por detalhes do aplicativo com ID: " + app.getAppIdString());

        if (currentTask != null && !currentTask.isDisposed()) {
            System.out.println("Tarefa anterior ainda em andamento. Cancelando tarefa anterior.");
            currentTask.dispose();
        }

        App api = new App();

        currentTask = Single.fromCallable(() -> {
            System.out.println("Buscando detalhes do aplicativo para o ID: " + app.getAppIdString());
            return api.buscarDetalhesApp(app.getAppIdString());
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .doOnSuccess(appDetails -> {
            System.out.println("Busca por detalhes concluída. Atualizando interface.");

            Platform.runLater(() -> {
                if (appDetails != null) {
                    System.out.println("Detalhes do aplicativo encontrados. Atualizando interface.");
                    FazerDownloadController controller = (FazerDownloadController) getController("TelaDownload");

                    if (controller != null) {
                        clearInterface(controller);
                        controller.loadDownloadPageContent(appDetails);
                        controller.setApp(appDetails);
                        System.out.println("Mudando para a cena da tela de download.");
                        changeScene("TelaDownload");
                    } else {
                        System.out.println("FazerDownloadController é null!");
                    }
                    ploader.setVisible(false);
                } else {
                    System.out.println("Detalhes do aplicativo retornaram null.");
                    ploader.setVisible(false);
                }
            });
        })
        .doOnError(ex -> {
            System.out.println("Erro ao buscar os detalhes do aplicativo: " + ex.getMessage());
            Platform.runLater(() -> showError("Erro ao buscar os detalhes do aplicativo: " + ex.getMessage()));
            ploader.setVisible(false);
        })
        .subscribe();
    }

    
    
    
    
}
