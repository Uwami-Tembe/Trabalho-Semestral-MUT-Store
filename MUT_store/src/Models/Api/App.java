package Models.Api;

import static Constants.Constants.API_URL;
import static Constants.Constants.TOKEN_FILE_PATH;
import Model.AppModel;
import Model.AppModelSummary;
import Model.AppModelDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import org.apache.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

    

public static Task<Void> downloadFile(String fileURL, String savePath) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                    HttpGet request = new HttpGet(fileURL);
                    try (CloseableHttpResponse response = httpClient.execute(request);
                         InputStream inputStream = response.getEntity().getContent();
                         FileOutputStream outputStream = new FileOutputStream(savePath)) {

                        long fileSize = response.getEntity().getContentLength(); // Tamanho do arquivo
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        long totalBytesRead = 0;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;

                            // Atualiza o progresso
                            updateProgress(totalBytesRead, fileSize);
                        }
                        System.out.println("Arquivo baixado com sucesso!");
                        updateMessage("Arquivo baixado");
                    }
                } catch (IOException e) {
                    updateMessage("Erro no download: " + e.getMessage());
                    System.out.println(e.getMessage());
                    throw e;
                }
                return null;
            }
        };
    }

  public static Response adicionarApp(AppModel app) {
    try {
        URI uri = new URI(API_URL + "/apps/create/app");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        // Adicionando parâmetros de texto ao formulário
        builder.addTextBody("nome", app.getNome(), ContentType.TEXT_PLAIN);
        builder.addTextBody("developerName", app.getDeveloperName(), ContentType.TEXT_PLAIN);
        builder.addTextBody("preco", String.valueOf(app.getPreco()), ContentType.TEXT_PLAIN);
        builder.addTextBody("description", app.getDescription(), ContentType.TEXT_PLAIN);
        builder.addTextBody("politics", app.getPolitics(), ContentType.TEXT_PLAIN);
        
        // Adicionando os métodos de pagamento
        builder.addTextBody("isMpesa", String.valueOf(app.isIsMpesa()), ContentType.TEXT_PLAIN);
        builder.addTextBody("isEmola", String.valueOf(app.isIsEmola()), ContentType.TEXT_PLAIN);
        builder.addTextBody("isBankCard", String.valueOf(app.isIsBankCard()), ContentType.TEXT_PLAIN);

        // Verificando e adicionando o ícone do aplicativo
        Response iconResponse = adicionarArquivo(builder, "icon", app.getIcon());
        if (iconResponse != null) return iconResponse;

        // Verificando e adicionando o arquivo do aplicativo
        Response appFileResponse = adicionarArquivo(builder, "appFile", app.getFile());
        if (appFileResponse != null) return appFileResponse;

        // Adicionando capturas de tela
        List<File> screenshots = List.of(app.getShot_1(), app.getShot_2(), app.getShot_3(), app.getShot_4());
        for (File screenshot : screenshots) {
            Response screenshotResponse = adicionarArquivo(builder, "images", screenshot);
            if (screenshotResponse != null) return screenshotResponse;
        }

        // Criando e enviando a requisição POST
        return enviarRequisicaoPOST(uri, builder.build());
    } catch (Exception e) {
        System.err.println("Erro ao adicionar o aplicativo: " + e.getMessage());
        e.printStackTrace();
        return new Response(500, 1, "Erro interno no servidor.", null);
    }
}
  
//  public static List<ExternalAppModel> buscarApps() {
//    try {
//        URI uri = new URI(API_URL + "/apps/list/apps"); // Ajuste o endpoint se necessário
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(uri) // Se necessário
//                .GET()
//                .build();
//
//        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
//
//        // Verifica se a resposta é um erro
//        if (response.statusCode() != 200) {
//            JsonNode errorNode = objectMapper.readTree(response.body());
//            int errorCode = errorNode.path("code").asInt();
//            String errorMessage = errorNode.path("message").asText();
//            System.err.println("Erro na API: " + errorMessage);
//            return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
//        }
//        
//        
//        System.out.println(response.body());
//        // Mapeia a resposta JSON para uma lista de AppModel
//        JsonNode jsonResponse = objectMapper.readTree(response.body());
//        JsonNode appsNode = jsonResponse.path("apps");
//        
//        return objectMapper.readValue(appsNode.toString(), new TypeReference<List<ExternalAppModel>>() {});
//    } catch (Exception e) {
//        System.err.println("Erro ao buscar aplicativos: " + e.getMessage());
//        return new ArrayList<>(); // Retorna uma lista vazia em caso de exceção
//    }
//}
  
  public static List<AppModelSummary> buscarAppsResumidos() {
    try {
        URI uri = new URI(API_URL + "/apps/summary"); // Ajuste o endpoint para buscar a lista resumida
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri) // Se necessário
                .GET()
                .build();

        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        // Verifica se a resposta é um erro
        if (response.statusCode() != 200) {
            JsonNode errorNode = objectMapper.readTree(response.body());
            int errorCode = errorNode.path("code").asInt();
            String errorMessage = errorNode.path("message").asText();
            System.err.println("Erro na API: " + errorMessage);
            return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
        }

        System.out.println(response.body());
        // Mapeia a resposta JSON para uma lista de ExternalAppModel
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        JsonNode appsNode = jsonResponse.path("apps"); // Supondo que a estrutura JSON mantenha "apps"

        return objectMapper.readValue(appsNode.toString(), new TypeReference<List<AppModelSummary>>() {});
    } catch (Exception e) {
        System.err.println("Erro ao buscar aplicativos: " + e.getMessage());
        return new ArrayList<>(); // Retorna uma lista vazia em caso de exceção
    }
}

public static AppModelDetails buscarDetalhesApp(String appId) {
    try {
        System.out.println("Buscando detalhes para o appId: " + appId);
        URI uri = new URI(API_URL + "/apps/moreInfo/" + appId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        // Verifica se a resposta é um erro
        if (response.statusCode() != 200) {
            JsonNode errorNode = objectMapper.readTree(response.body());
            int errorCode = errorNode.path("code").asInt();
            String errorMessage = errorNode.path("message").asText();
            System.err.println("Erro na API: " + errorMessage);
            return null; // Retorna null em caso de erro
        }

        System.out.println("Response Body: " + response.body()); // Para depuração
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        JsonNode appNode = jsonResponse.get("app"); // Verifica a existência do campo "app"
        
        if (appNode == null) {
            System.err.println("Campo 'app' não encontrado na resposta JSON para appId: " + appId);
            return null; // Retorne null ou faça o tratamento adequado
        }

        // Deserializar o JSON do app para AppModelDetails
        return objectMapper.readValue(appNode.toString(), AppModelDetails.class);
    } catch (Exception e) {
        System.err.println("Erro ao buscar detalhes do aplicativo para appId: " + appId + " - " + e.getMessage());
        e.printStackTrace(); // Adiciona mais detalhes da exceção
        return null; // Retorna null em caso de exceção
    }
}

private static Response adicionarArquivo(MultipartEntityBuilder builder, String campo, File arquivo) {
    if (arquivo == null || !arquivo.exists()) {
        System.out.println("Erro: O arquivo " + campo + " não foi encontrado ou o caminho é inválido.");
        return new Response(400, 1, "Arquivo " + campo + " não encontrado.", null);
    }

    String mimeType = obterMimeType(arquivo);
    builder.addBinaryBody(campo, arquivo, ContentType.create(mimeType), arquivo.getName());
    return null;
}

private static String obterMimeType(File arquivo) {
    try {
        String mimeType = Files.probeContentType(arquivo.toPath());
        return (mimeType != null) ? mimeType : "application/octet-stream"; // Tipo padrão
    } catch (IOException e) {
        System.err.println("Erro ao detectar o tipo MIME do arquivo: " + e.getMessage());
        return "application/octet-stream"; // Tipo padrão em caso de erro
    }
}

private static Response enviarRequisicaoPOST(URI uri, HttpEntity multipart) {
    HttpPost post = new HttpPost(uri);
    post.setEntity(multipart);
    
    try (CloseableHttpClient httpClient = HttpClients.createDefault();
         CloseableHttpResponse response = httpClient.execute(post)) {

        String responseString = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 201) {
            return new Response(201, 0, "Aplicativo salvo com sucesso!", responseString);
        } else {
            System.err.println("Erro na API: " + responseString);
            return new Response(statusCode, 1, "Erro ao enviar o aplicativo", responseString);
        }
    } catch (IOException e) {
        System.err.println("Erro ao enviar a requisição: " + e.getMessage());
        return new Response(500, 1, "Erro ao enviar a requisição.", null);
    }
}
}