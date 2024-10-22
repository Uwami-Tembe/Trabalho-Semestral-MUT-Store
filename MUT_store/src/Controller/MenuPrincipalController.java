package Controller;

import Model.AppModelSummary;
import Model.AppModelDetails;
import Model.Usuario;
import Models.Api.App;
import static Models.Api.User.userInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MenuPrincipalController {

    private io.reactivex.rxjava3.disposables.Disposable currentTask;
    @FXML
    private Button bt_criarApp, bt_definicoes, bt_loja, bt_sair, bt_sobre;

    @FXML
    private ImageView img_pesquisar;

    @FXML
    private ImageView img_iconCriar;

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
    public void initialize() {
          user = userInfo();      
        // Garantindo que a interface gráfica seja manipulada na thread correta
//        Platform.runLater(() -> {
            // Obtendo as informações do usuário
          

            // Verifica se o usuário não é nulo
            if (user != null) {
                lb_NomeDoUsuario.setText(user.getName());
                
                System.out.println(user.getUserType());

                // Se o tipo de usuário for "normal", esconde os botões de criação
                if (user.getUserType().equals("normal")) {
                    bt_criarApp.setVisible(false);
                    if (img_iconCriar != null) {
                        img_iconCriar.setVisible(false);
                    }
                    return;
                }

                // Caso contrário, exibe os botões de criação
                bt_criarApp.setVisible(true);
                if (img_iconCriar != null) {
                    img_iconCriar.setVisible(true);
                }
            }else{
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
            
            // Após ajustar a interface, carrega os aplicativos
           
//        });
    }
    
    public void loadApps(){ 
     loadAppsHome();
    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) {
        try {

            MainStage.changeScene("CriarApp");           
        } catch (Exception ex) {
            System.out.println("Erro ao carregar a cena CriarApp: " + ex.getMessage());
        }
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                MainStage.changeScene("Settings");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {

                MainStage.changeScene("Sobre");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) {
            initialize();
            loadAppsWithoutCache("");
    }

    @FXML
    void on_panel_profile_click(MouseEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                PerfilController pc = (PerfilController) getController("Perfil");
                pc.initialize();
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

    private Map<String, List<AppModelSummary>> appCache = new HashMap<>();

    private void loadApps(String keyword) {
        // Verifica se os aplicativos já estão em cache
        if (appCache.containsKey(keyword)) {
            Platform.runLater(() -> {
                processApps(appCache.get(keyword));
                ploader.setVisible(false); // Esconde o ProgressIndicator
            });
            return; // Retorna se o cache já contém os dados
        }

        // Exibe o ProgressIndicator antes de iniciar o carregamento
        Platform.runLater(() -> ploader.setVisible(true));

        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(ev -> {
            // Inicia o processo de buscar apps em uma thread separada
            Single.fromCallable(() -> App.buscarApps(keyword))
                    .subscribeOn(Schedulers.io()) // Executa a busca em uma thread de I/O
                    .observeOn(Schedulers.computation()) // Processa os dados em uma thread de computação
                    .subscribe(
                            appSummaryList -> {
                                // Armazena os resultados em cache
                                appCache.put(keyword, appSummaryList);
                                Platform.runLater(() -> {
                                    // Atualiza a UI na thread JavaFX após o processamento
                                    processApps(appSummaryList);
                                    ploader.setVisible(false); // Esconde o ProgressIndicator
                                });
                            },
                            ex -> Platform.runLater(() -> {
                                // Mostra o erro na UI e esconde o ProgressIndicator
                                showError("Erro ao buscar os aplicativos: " + ex.getMessage());
                                ploader.setVisible(false); // Esconde o ProgressIndicator
                            })
                    );
        });

        // Inicia a pausa antes de buscar os aplicativos
        pause.play();
    }
  private void loadAppsWithoutCache(String keyword) {
        // Verifica se os aplicativos já estão em cache
//        if (appCache.containsKey(keyword)) {
//            Platform.runLater(() -> {
//                processApps(appCache.get(keyword));
//                ploader.setVisible(false); // Esconde o ProgressIndicator
//            });
//            return; // Retorna se o cache já contém os dados
//        }

        // Exibe o ProgressIndicator antes de iniciar o carregamento
        Platform.runLater(() -> ploader.setVisible(true));

        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(ev -> {
            // Inicia o processo de buscar apps em uma thread separada
            Single.fromCallable(() -> App.buscarApps(keyword))
                    .subscribeOn(Schedulers.io()) // Executa a busca em uma thread de I/O
                    .observeOn(Schedulers.computation()) // Processa os dados em uma thread de computação
                    .subscribe(
                            appSummaryList -> {
                                // Armazena os resultados em cache
                                appCache.put(keyword, appSummaryList);
                                Platform.runLater(() -> {
                                    // Atualiza a UI na thread JavaFX após o processamento
                                    processApps(appSummaryList);
                                    ploader.setVisible(false); // Esconde o ProgressIndicator
                                });
                            },
                            ex -> Platform.runLater(() -> {
                                // Mostra o erro na UI e esconde o ProgressIndicator
                                showError("Erro ao buscar os aplicativos: " + ex.getMessage());
                                ploader.setVisible(false); // Esconde o ProgressIndicator
                            })
                    );
        });

        // Inicia a pausa antes de buscar os aplicativos
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

        // Usando um StringBuilder para gerar a interface mais eficientemente
        List<VBox> appBoxes = new ArrayList<>();
        int count = 0;

        for (AppModelSummary app : sortedApps) {
            VBox appBox = createAppBox(app);
            appBoxes.add(appBox);
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
    VBox appBox = new VBox(10);  // Espaçamento entre os elementos

    // Configuração do ícone do aplicativo
    ImageView appIcon = new ImageView(app.getIconUrl());
    appIcon.setFitWidth(80);
    appIcon.setFitHeight(80);
    appIcon.setPreserveRatio(true);

    // Configuração do nome do aplicativo
    Label appName = new Label(app.getNome());
    appName.setFont(Font.font("System", FontWeight.BOLD, 14));
    appName.setStyle("-fx-text-fill: #517983");  // Cor do texto

    // Adicionar o ícone e o nome do app ao VBox
    appBox.getChildren().addAll(appIcon, appName);

    // Adicionando evento de clique
    appBox.setOnMouseClicked(t -> buscarDetalhesAplicativo(app));

    // Estilizando o VBox com borda, cor de fundo e bordas arredondadas
    appBox.setStyle(
        "-fx-border-color: #000000;" +  // Cor da borda (preto)
        "-fx-border-width: 2;" +       // Largura da borda
        "-fx-border-radius: 5;" +      // Bordas arredondadas
        "-fx-background-color: #f4f4f4;"  // Cor de fundo
    );
    appBox.setPadding(new Insets(10));  // Margem interna do VBox

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

    private Map<String, AppModelDetails> detailsCache = new HashMap<>();

    private void buscarDetalhesAplicativo(AppModelSummary app) {
        String appId = app.getAppIdString();

        // Verifica se os detalhes já estão em cache
        if (detailsCache.containsKey(appId)) {
            // Atualiza a interface com os detalhes do cache
            atualizarInterfaceComDetalhes(detailsCache.get(appId));
            return; // Retorna se o cache já contém os dados
        }

        ploader.setVisible(true);
        System.out.println("Iniciando a busca por detalhes do aplicativo com ID: " + appId);

        if (currentTask != null && !currentTask.isDisposed()) {
            System.out.println("Tarefa anterior ainda em andamento. Cancelando tarefa anterior.");
            currentTask.dispose();
        }

        App api = new App();

        currentTask = Single.fromCallable(() -> api.buscarDetalhesApp(appId))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(appDetails -> {
                    // Armazena os detalhes em cache
                    detailsCache.put(appId, appDetails);
                    atualizarInterfaceComDetalhes(appDetails);
                })
                .doOnError(ex -> {
                    System.out.println("Erro ao buscar os detalhes do aplicativo: " + ex.getMessage());
                    Platform.runLater(() -> showError("Erro ao buscar os detalhes do aplicativo: " + ex.getMessage()));
                    ploader.setVisible(false);
                })
                .subscribe();
    }

    private void atualizarInterfaceComDetalhes(AppModelDetails appDetails) {
        Platform.runLater(() -> {
            if (appDetails != null) {
                System.out.println("Detalhes do aplicativo encontrados. Atualizando interface.");
                FazerDownloadController controller = (FazerDownloadController) getController("TelaDownload");

                if (controller != null) {
                    clearInterface(controller);
                    controller.loadDownloadPageContent(appDetails);
                    controller.setApp(appDetails);
                    controller.initialize();
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
    }

}
