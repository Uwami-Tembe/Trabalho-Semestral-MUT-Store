package Models.Api;

import static Constants.Constants.API_URL;
import static Constants.Constants.TOKEN_FILE_PATH;
import Model.AppModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import org.apache.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Classe responsável pelas operações da API relacionadas a aplicativos.
 */
public class App {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    


    // Método para adicionar um novo aplicativo
public static Response adicionarApp(AppModel app) {
    try {
        URI uri = new URI(API_URL + "/create/app");

        // Criando o builder multipart para a requisição
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        // Adicionando parâmetros de texto ao formulário
        builder.addTextBody("nome", app.getNome(), ContentType.TEXT_PLAIN);
        builder.addTextBody("developerName", app.getDeveloperName(), ContentType.TEXT_PLAIN);
        builder.addTextBody("preco", String.valueOf(app.getPreco()), ContentType.TEXT_PLAIN);
        builder.addTextBody("description", app.getDescription(), ContentType.TEXT_PLAIN);
        builder.addTextBody("politics", app.getPolitics(), ContentType.TEXT_PLAIN);

        // Convertendo o ícone (ImageView) para bytes e adicionando como arquivo binário
        BufferedImage iconImage = SwingFXUtils.fromFXImage(app.getIcon().getImage(), null);
        ByteArrayOutputStream iconBaos = new ByteArrayOutputStream();
        ImageIO.write(iconImage, "png", iconBaos);
        byte[] iconBytes = iconBaos.toByteArray();
        builder.addBinaryBody("icon", iconBytes, ContentType.create("image/png"), "icon.png");

        // Enviar o arquivo binário do aplicativo (APK, EXE, MSI, etc.)
          BufferedImage appFileImage = SwingFXUtils.fromFXImage(app.getFile().getImage(), null);
        ByteArrayOutputStream appFileBaos = new ByteArrayOutputStream();
        ImageIO.write(appFileImage, "png", appFileBaos);
        byte[] appFileBytes = appFileBaos.toByteArray();
        builder.addBinaryBody("appFile", appFileBytes, ContentType.create("image/png"), "appFile.png");
        // Convertendo as imagens do tipo ImageView para byte arrays (capturas de tela)
        List<ImageView> images = List.of(app.getShot_1(), app.getShot_2(), app.getShot_3(), app.getShot_4());
        for (ImageView imageView : images) {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos); // Convertendo para PNG
            byte[] imageBytes = baos.toByteArray();

            // Adicionando as imagens como arquivos binários no corpo do formulário
            builder.addBinaryBody("images", imageBytes, ContentType.create("image/png"), "image.png");
        }

        HttpEntity multipart = builder.build();

        // Criando a requisição POST
        HttpPost post = new HttpPost(uri);
        post.setEntity(multipart);

        // Enviando a requisição usando o HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            // Obtendo a resposta da API
            String responseString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();

            // Processando a resposta
            if (statusCode == 200) {
                return new Response(200, 0, "Sucesso", responseString);
            } else {
                return new Response(statusCode, 1, "Erro ao enviar o aplicativo", responseString);
            }
        }
    } catch (Exception e) {
        // Logando o erro
        System.err.println("Erro ao adicionar o aplicativo: " + e.getMessage());
        e.printStackTrace(); // Mostra a pilha de chamadas
        return new Response(500, 1, "Erro interno no servidor.", null);
    }
}}