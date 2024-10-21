package Controller;

import Model.AppModel;
import Model.Usuario;
import Models.Api.App;
import Models.Api.Response;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class CriarAppController {

    @FXML
    private com.gluonhq.charm.glisten.control.ProgressIndicator ploader;
    @FXML
    public Button bt_Upload;

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
    private CheckBox checB_card;

    @FXML
    private CheckBox checkB_mpesaeEmola;

    @FXML
    private ImageView img_delete_shots;

    @FXML
    private ImageView img_file;

    @FXML
    public ImageView img_icon;

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
    private Label lb_FacaUpload;

    @FXML
    public Label lb_Nome;

    @FXML
    private Label lb_Nome1;

    private File lastDirectory = null; // Variável para armazenar o último diretório

    public Image image = null;

    public File file = null;

    @FXML
    private Label lb_carregueImagens;

    @FXML
    private Label lb_ficheiro;

    @FXML
    private Label lb_icon;

    @FXML
    private Label lb_nota;

    @FXML
    private Label lb_politics;

    @FXML
    private Label lb_preco;

    @FXML
    private Label lb_screenshots;

    @FXML
    private Pane panel_add_file;

    @FXML
    private Pane panel_add_icon;

    @FXML
    private Pane panel_add_shot_1;

    @FXML
    private Pane panel_add_shot_2;

    @FXML
    private Pane panel_add_shot_3;

    @FXML
    private Pane panel_add_shot_4;

    @FXML
    private ToggleGroup categoriaGroup;
    @FXML
    private RadioButton rdbt_categoriaJogo;

    @FXML
    private RadioButton rdbt_categoriaApp;

    @FXML
    private TextArea txt_appDetalhes;

    @FXML
    private TextField txt_appNome;

    @FXML
    private TextArea txt_appPolitics;

    @FXML
    private TextField txt_appPreco;

    public String iconPath;
    public String filePath;
    public String imgPath1;
    public String imgPath2;
    public String imgPath3;
    public String imgPath4;
    // Constantes para tipos de arquivos
    private static final String[] IMAGE_EXTENSIONS = {"*.png", "*.jpg", "*.jpeg", "*.PNG", "*.JPG", "*.JPEG"};
    private static final String[] FILE_EXTENSIONS = {"*.apk", "*.exe", "*.APK", "*.EXE"};
    private static final double IMAGE_OPACITY_DEFAULT = 0.4;
    private static final double IMAGE_OPACITY_ACTIVE = 1.0;
    private static final double DELAY_SECONDS = 1.2;

    public void mostrarMensagemErro(String S) {
        JOptionPane.showMessageDialog(null, S);
    }

    private void exibirMensagemSucesso(String mensagemSucesso) throws Exception {
        JOptionPane.showMessageDialog(null, mensagemSucesso);

    }

    MenuPrincipalController m = new MenuPrincipalController();

    public void setMenuController(MenuPrincipalController m) {
        this.m = m;
    }

    public static List<AppModel> appList = new ArrayList();

    @FXML
    void On_add_file_click(MouseEvent event) {
        filePath = handleFileSelection(img_file, FILE_EXTENSIONS);
    }

    @FXML
    void On_add_icon_click(MouseEvent event) {
        iconPath = handleFileSelection(img_icon, IMAGE_EXTENSIONS);
    }

    @FXML
    void On_add_shot_1_click(MouseEvent event) {
        imgPath1 = handleFileSelection(img_shot_1, IMAGE_EXTENSIONS);
    }

    @FXML
    void On_add_shot_2_click(MouseEvent event) {
        imgPath2 = handleFileSelection(img_shot_2, IMAGE_EXTENSIONS);
    }

    @FXML
    void On_add_shot_3_click(MouseEvent event) {
        imgPath3 = handleFileSelection(img_shot_3, IMAGE_EXTENSIONS);
    }

    @FXML
    void On_add_shot_4_click(MouseEvent event) {
        imgPath4 = handleFileSelection(img_shot_4, IMAGE_EXTENSIONS);
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

                    try {
                        MenuPrincipalController menuController = (MenuPrincipalController) getController("MenuPrincipal");
                        menuController.loadApps();
                        menuController.initialize();
                        MainStage.changeScene("MenuPrincipal");

                    } catch (Exception ex) {
                        System.out.println("Erro ao carregar a cena CriarApp: " + ex.getMessage());
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

@FXML
void On_bt_upload_pressed(ActionEvent event) {
    // Validação dos campos obrigatórios antes de criar o AppModel
    if (txt_appNome.getText().isEmpty() || txt_appPreco.getText().isEmpty() || iconPath.isEmpty() || filePath.isEmpty()) {
        mostrarMensagemErro("Por favor, preencha todos os campos obrigatórios e selecione os arquivos.");
        return;
    }

    // Verificar se o preço é um número válido
    float preco;
    try {
        preco = Float.parseFloat(txt_appPreco.getText());
    } catch (NumberFormatException e) {
        mostrarMensagemErro("Preço inválido. Insira um valor numérico.");
        return;
    }

    // Verificar se os arquivos existem
    File iconFile = new File(iconPath);
    if (!iconFile.exists()) {
        mostrarMensagemErro("O ícone selecionado não existe.");
        return;
    }

    File appFile = new File(filePath);
    if (!appFile.exists()) {
        mostrarMensagemErro("O arquivo do aplicativo selecionado não existe.");
        return;
    }

    // Verificar as capturas de tela (opcional)
    File img1 = new File(imgPath1);
    File img2 = new File(imgPath2);
    File img3 = new File(imgPath3);
    File img4 = new File(imgPath4);
    if (!img1.exists() || !img2.exists() || !img3.exists() || !img4.exists()) {
        mostrarMensagemErro("Uma ou mais capturas de tela não existem.");
        return;
    }

    // Configurar o ToggleGroup para as categorias (caso ainda não tenha feito)
    ToggleGroup categoriaGroup = new ToggleGroup();
    rdbt_categoriaJogo.setToggleGroup(categoriaGroup);
    rdbt_categoriaApp.setToggleGroup(categoriaGroup);

    // Pegar o RadioButton selecionado
    RadioButton selectedCategoria = (RadioButton) categoriaGroup.getSelectedToggle();

    // Obter o texto do RadioButton selecionado
    String categoriaSelecionada = selectedCategoria.getText();

    // Criar o AppModel com os dados e os ficheiros selecionados
    AppModel novaApp = new AppModel(iconFile, txt_appNome.getText(), preco, img1, img2, img3, img4,
            txt_appDetalhes.getText(), txt_appPolitics.getText(), txt_appNome.getText(),
            checB_card.isSelected(), checkB_mpesaeEmola.isSelected(), checkB_mpesaeEmola.isSelected(), appFile, categoriaSelecionada);

    // Task para rodar o upload em segundo plano
    Task<Response> uploadTask = new Task<>() {
        @Override
        protected Response call() throws Exception {
            // Fazer o upload da nova app para a API
            return App.adicionarApp(novaApp);
        }
    };

    // Mostrar o ProgressIndicator (loader) no início do upload
    ploader.setVisible(true);

    // Executar o upload em segundo plano
    uploadTask.setOnSucceeded(e -> {
        ploader.setVisible(false); // Esconder o loader após a conclusão

        Response res = uploadTask.getValue();
        if (res.getError_code() == 0) {
            try {
                exibirMensagemSucesso(res.getMsg());
                // Limpar o formulário após o sucesso
            } catch (Exception ex) {
                Logger.getLogger(CriarAppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mostrarMensagemErro(res.getMsg());
        }
    });

    uploadTask.setOnFailed(e -> {
        ploader.setVisible(false); // Esconder o loader após o erro
        mostrarMensagemErro("Erro no sistema. Tente novamente mais tarde.");
    });

    // Executa a tarefa em uma nova thread
    new Thread(uploadTask).start();
}


// Função para limpar o formulário após o upload bem-sucedido
    @FXML
    void On_img_delete_shots_click(MouseEvent event) {
        ImageView defaultImage = new ImageView("/View/Login/imagens/image-icon.png");

        img_shot_1.setImage(defaultImage.getImage());
        img_shot_2.setImage(defaultImage.getImage());
        img_shot_3.setImage(defaultImage.getImage());
        img_shot_4.setImage(defaultImage.getImage());
        img_shot_1.setOpacity(0.4);
        img_shot_2.setOpacity(0.4);
        img_shot_3.setOpacity(0.4);
        img_shot_4.setOpacity(0.4);
    }

    public String handleFileSelection(ImageView imgView, String[] extensions) {
        // Verifica se o ImageView é nulo
        if (imgView == null || imgView.getScene() == null) {
            showAlert("Erro", "ImageView ou cena não podem ser nulos.");
            return null;
        }

        // Verifica se as extensões são válidas
        if (extensions == null || extensions.length == 0) {
            showAlert("Erro", "Nenhuma extensão válida foi fornecida.");
            return null;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos de Imagem", extensions));

        // Verifica se lastDirectory é um diretório válido
        if (lastDirectory != null && lastDirectory.exists() && lastDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(lastDirectory);
        } else {
            System.out.println("Diretório inicial inválido ou não definido.");
        }

        Stage stage = (Stage) imgView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Verifica se o arquivo é legível
                if (Files.isReadable(selectedFile.toPath())) {
                    lastDirectory = selectedFile.getParentFile();  // Atualiza lastDirectory
                    imgView.setImage(new Image(selectedFile.toURI().toString()));  // Define a imagem no ImageView
                    imgView.setOpacity(IMAGE_OPACITY_ACTIVE);  // Define a opacidade

                    // Retorna o caminho completo normalizado
                    String fullPath = selectedFile.getAbsolutePath();
                    System.out.println("Arquivo selecionado: " + fullPath);
                    return fullPath;
                } else {
                    showAlert("Erro", "Acesso negado ao arquivo: " + selectedFile.getAbsolutePath());
                }
            } catch (SecurityException e) {
                showAlert("Erro", "Problema de permissão ao acessar o arquivo: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Nenhum arquivo foi selecionado.");
        }

        return null;
    }

// Método auxiliar para exibir alertas
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
