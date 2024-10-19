package Controller;

import Model.AppModel;
import Model.Usuario;
import Models.Api.App;
import Models.Api.Response;
import static Models.Api.User.userInfo;
import View.MainStage;
import static View.MainStage.changeScene;
import static View.MainStage.getController;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ProgressIndicator ploader;
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
    void On_bt_CriarApp_pressed(ActionEvent event) {

    }

    @FXML
    void On_bt_Loja_pressed(ActionEvent event) throws Exception {
        MenuPrincipalController menuController = (MenuPrincipalController) getController("MenuPrincipal");
        Usuario user = userInfo();
        if (user != null) {
            // Definir o nome do usuário no controlador da tela principal
            menuController.setLb_NomeDoUsuario(user.getName());
            menuController.setUser(user);// Atualiza o Label com o nome do usuário
            menuController.initialize(true);

        } else {
            // Lidar com o erro se o usuário não foi encontrado
            System.err.println("Erro ao buscar informações do usuário.");
        }

        changeScene("MenuPrincipal");

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

        // Criar o AppModel com os dados e os ficheiros selecionados
        AppModel novaApp = new AppModel(iconFile, txt_appNome.getText(), preco, img1, img2, img3, img4,
                txt_appDetalhes.getText(), txt_appPolitics.getText(), txt_appNome.getText(),
                checB_card.isSelected(), checkB_mpesaeEmola.isSelected(), checkB_mpesaeEmola.isSelected(), appFile);

        try {
            // Fazer o upload da nova app para a API
            Response res = App.adicionarApp(novaApp);

            if (res.getError_code() == 0) {
                exibirMensagemSucesso(res.getMsg());
                // Limpa o formulário após o sucesso
            } else {
                mostrarMensagemErro(res.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensagemErro("Erro no sistema. Tente novamente mais tarde.");
        }

        // Atualiza a lista de aplicativos localmente
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
