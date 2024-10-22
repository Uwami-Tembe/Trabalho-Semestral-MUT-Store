package Controller;

import Model.AppModelDetails;
import Model.Comment;
import Model.Usuario;
import Models.Api.App;
import static Models.Api.App.comentarApp;
import static Models.Api.App.listarComentariosApp;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.io.IOException;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class FazerDownloadController {

    @FXML
    private ProgressIndicator ploader;
    @FXML
    private Button bt_Baixar;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button bt_Comentar;

    @FXML
    private Button bt_criarApp;

    @FXML
    private Button bt_stopDownload;

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
    private Label statusLabel;

    @FXML
    private Label lb_developerName;

    @FXML
    private Label lb_politicsLongo;

    @FXML
    private Label lb_preco;

    @FXML
    private Label commentLabel;

//    @FXML
//    private Label lb_preco1;
    @FXML
    private VBox panel_comentarios;

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

    public String appfile;

    public AppModelDetails app;

    public AppModelDetails getApp() {
        return app;
    }

    public void setApp(AppModelDetails app) {
        this.app = app;
        // Obtém o ID do aplicativo
        listarComentarios(app.getId());
    }

    @FXML
    private ImageView img_iconCriar;

    @FXML
    public void initialize() {
        // Garantindo que a interface gráfica seja manipulada na thread correta
        Platform.runLater(() -> {
            // Obtendo as informações do usuário
            Usuario user = userInfo();

            // Verifica se o usuário não é nulo
            if (user != null) {

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
            }

            // Após ajustar a interface, carrega os aplicativos
        });
    }

//public void initialize() {
//    // Chama o método para listar comentários ao inicializar a tela
//    String appId = app.getId(); // Obtém o ID do aplicativo
//    listarComentarios(appId);
//}
    public void loadDownloadPageContent(AppModelDetails app) {
        // Limpa o conteúdo anterior
        clearPreviousContent();

        Usuario user = userInfo();  // Obtém as informações do usuário atual
        List<String> pagos = user.getPagos();  // Supondo que a classe Usuario tenha o método getPagos()

        // Carrega os novos dados do aplicativo
        img_icon.setImage(new Image(app.getIcon()));
        lb_Nome.setText(app.getNome());

        List<String> imagePaths = app.getImagePaths();  // Supondo que imagePaths é uma List<String>
        appfile = app.getAppFilePath();

        // Carrega as imagens de captura de tela
        img_shot_1.setImage(new Image(imagePaths.get(0)));
        img_shot_2.setImage(new Image(imagePaths.get(1)));
        img_shot_3.setImage(new Image(imagePaths.get(2)));
        img_shot_4.setImage(new Image(imagePaths.get(3)));

        // Outros dados do app
        lb_DescricaoLonga.setText(app.getDescription());
        lb_politicsLongo.setText(app.getPolitics());
        lb_developerName.setText(app.getDeveloperName());

        // Torna as imagens visíveis (caso necessário)
        img_icon.setOpacity(1.0);
        img_shot_1.setOpacity(1.0);
        img_shot_2.setOpacity(1.0);
        img_shot_3.setOpacity(1.0);
        img_shot_4.setOpacity(1.0);

        // Verifica se o aplicativo já foi comprado pelo usuário
        System.out.println(pagos.toString());
        System.out.println("APPID" + app.getId());
        if (pagos != null && pagos.contains(app.getId())) {
            // Se o aplicativo já foi comprado, o preço é "Grátis" e o botão diz "Baixar"
            lb_preco.setText("Grátis");
            bt_Baixar.setText("Baixar");
        } else {
            // Se não foi comprado, exibe o preço normal
            if (app.getPreco() == 0.0f) {
                lb_preco.setText("Grátis");
                bt_Baixar.setText("Baixar");
            } else {
                lb_preco.setText(Float.toString((float) app.getPreco()));
                bt_Baixar.setText("Comprar");
            }
        }
    }

// Método para limpar os campos da interface
    public void clearPreviousContent() {
        img_icon.setImage(null);
        lb_Nome.setText("");
        img_shot_1.setImage(null);
        img_shot_2.setImage(null);
        img_shot_3.setImage(null);
        img_shot_4.setImage(null);
        statusLabel.setText("");
        statusLabel.setVisible(false);
        progressBar.setVisible(false);
        bt_stopDownload.setVisible(false);
        bt_Baixar.setVisible(true);
        lb_DescricaoLonga.setText("");
        lb_politicsLongo.setText("");
        lb_developerName.setText("");
        lb_preco.setText("");
        lb_preco.setVisible(true);
        img_icon.setOpacity(0.0);
        img_shot_1.setOpacity(0.0);
        img_shot_2.setOpacity(0.0);
        img_shot_3.setOpacity(0.0);
        img_shot_4.setOpacity(0.0);
    }

    @FXML
    void On_add_icon_click(MouseEvent event) {

    }

    private void listarComentarios(String appId) {
        // Limpa o painel de comentários antes de listar
        panel_comentarios.getChildren().clear();

        // Chama a função para listar comentários
        List<Comment> comments = listarComentariosApp(appId);

        // Adiciona os comentários ao painel
        for (Comment comment : comments) {
            commentLabel = new Label(comment.getComment() + " - " + comment.getUsername());
            commentLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            commentLabel.setStyle("-fx-text-fill: #517983");
            panel_comentarios.getChildren().add(commentLabel); // Adiciona ao VBox
        }
    }

    @FXML
    void On_bt_comentar_pressed(ActionEvent event) {
        // Limpa estilos antigos de erros
        limparEstilosDeErro();

        // Obtém os valores inseridos pelo usuário
        String appId = app.getId();
        String comentario = txt_comentar.getText();

        // Valida se os campos não estão vazios
        boolean erro = false;
        StringBuilder mensagemErro = new StringBuilder();

        // Validação do comentário
        if (comentario.isEmpty()) {
            txt_comentar.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
            mensagemErro.append("O comentário não pode estar vazio.\n");
            erro = true;
        } else if (comentario.length() < 5) { // Validação de comprimento mínimo
            txt_comentar.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
            mensagemErro.append("O comentário deve ter no mínimo 5 caracteres.\n");
            erro = true;
        } else if (comentario.length() > 500) { // Validação de comprimento máximo
            txt_comentar.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
            mensagemErro.append("O comentário deve ter no máximo 500 caracteres.\n");
            erro = true;
        } else if (!comentario.matches("^[a-zA-Z0-9 .,!?]+$")) { // Verifica se há caracteres inválidos
            txt_comentar.setStyle("-fx-border-color: red; -fx-background-color: #FFCCCC;");
            mensagemErro.append("O comentário contém caracteres inválidos.\n");
            erro = true;
        }

        if (erro) {
            // Exibe a mensagem de erro se houver validações não passadas
            JOptionPane.showMessageDialog(null, mensagemErro.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chama a função para enviar o comentário
        boolean comments = comentarApp(appId, comentario);

        // Verifica se o comentário foi enviado com sucesso
        if (!comments) {
            JOptionPane.showMessageDialog(null, "Comentário enviado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            listarComentarios(appId);
            // Opcionalmente, pode limpar os campos após o envio
            txt_comentar.clear();
        }

        listarComentarios(appId);
    }

// Limpa os estilos de erro antes de validar novamente
    private void limparEstilosDeErro() {
        txt_comentar.setStyle(""); // Remove o estilo de erro
    }

    @FXML
    void On_bt_CriarApp_pressed(ActionEvent event) throws IOException {
        try {
            CriarAppController menuController = (CriarAppController) getController("CriarApp");
            menuController.initialize();
            MainStage.changeScene("CriarApp");
        } catch (Exception ex) {
            System.out.println("Erro ao carregar a cena CriarApp: " + ex.getMessage());
        }
    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) getController("MenuPrincipal");
            menuController.loadApps();
            menuController.initialize();
            MainStage.changeScene("MenuPrincipal");

        } catch (Exception ex) {
            System.out.println("Erro ao carregar a cena CriarApp: " + ex.getMessage());
        }
    }

    @FXML
    void On_bt_Sobre_pressed(ActionEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                SobreController s = (SobreController) getController("Sobre");
                s.initialize();
                MainStage.changeScene("Sobre");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void On_bt_definicoes_pressed(ActionEvent event) {
        MainStage.changeScene("Carregando");
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(e -> {
            try {
                SettingsController s = (SettingsController) getController("Settings");
                s.initialize();
                MainStage.changeScene("Settings");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    @FXML
    void On_bt_sair_pressed(ActionEvent event) throws IOException {
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
// Variável para armazenar a tarefa de download
    private Task<Void> downloadTask;

// Botão para parar o download
    @FXML
    void On_bt_stopDownload_pressed(ActionEvent event) {
        // Cancela a tarefa de download
        if (downloadTask != null && downloadTask.isRunning()) {
            downloadTask.cancel();
        }

        // Desvincula o label de status da tarefa antes de modificá-lo diretamente
        statusLabel.textProperty().unbind();

        // Atualiza o rótulo de status manualmente
//    statusLabel.setText("Download cancelado");
        // Ocultar a barra de progresso e o botão de parar
        progressBar.setVisible(false);
        bt_stopDownload.setVisible(false);

        // Reexibir os botões anteriores
        bt_Baixar.setVisible(true);
        lb_preco.setVisible(true);
    }

// Botão para iniciar o download
    @FXML
    void On_bt_baixar_pressed(ActionEvent event) {
        String fileURL = appfile;
        String savePath = "C:/downloads/app.apk";

        // Ocultar os botões e rótulos
        bt_Baixar.setVisible(false);
        lb_preco.setVisible(false);

        // Mostrar a barra de progresso
        progressBar.setVisible(true);
        statusLabel.setVisible(true);
        bt_stopDownload.setVisible(true);

        // Verifica se o app tem preço
        if (app.getPreco() != 0.0f) {
//        downloadTask = App.downloadFileAfterPay(fileURL, savePath, app.getId());
            try {
                PayMetodoController pmc = (PayMetodoController) getController("PayMetodo");
                pmc.setApp(app);
                MainStage.changeScene("PayMetodo");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            downloadTask = App.downloadFile(fileURL, savePath);
            // Vincula o progresso e a mensagem de status à interface
            progressBar.progressProperty().bind(downloadTask.progressProperty());
            statusLabel.textProperty().bind(downloadTask.messageProperty());

            // Listener para quando o download for concluído
            downloadTask.setOnSucceeded(e -> {
                // Desvincula as propriedades antes de definir o valor diretamente
                statusLabel.textProperty().unbind();
                statusLabel.setText("    Download concluído!");
                bt_stopDownload.setVisible(false);
            });

            // Listener para quando o download for cancelado
            downloadTask.setOnCancelled(e -> {
                statusLabel.textProperty().unbind();
                progressBar.setVisible(false);
                bt_Baixar.setVisible(true);
                bt_stopDownload.setVisible(false);
            });

            // Listener para quando o download falhar
            downloadTask.setOnFailed(e -> {
                statusLabel.textProperty().unbind();
                statusLabel.setText("     Erro ao baixar o arquivo.");
                progressBar.setVisible(false);
                bt_Baixar.setVisible(true);
                bt_stopDownload.setVisible(false);
            });

            // Inicia a tarefa de download em uma nova thread
            new Thread(downloadTask).start();
        }

    }
}
