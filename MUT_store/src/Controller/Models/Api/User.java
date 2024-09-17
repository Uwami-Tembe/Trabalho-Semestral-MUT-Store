package Controller.Models.Api;

import static Constants.Constants.API_URL;
import Controller.Models.Usuario;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável pelas operações da API relacionadas ao usuário.
 */
public class User {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Método para criar uma conta de usuário
    public static boolean criarContaAPI(Usuario user) {
        try {
            URI uri = new URI(API_URL + "/users/register");

            // Criando o corpo da requisição em JSON
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", user.getName());
            requestBody.put("email", user.getEmail());
            requestBody.put("password", user.getPassword());
            requestBody.put("username", user.getUsername());
            requestBody.put("mobileNumber", user.getMobileNumber());

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            // Criando a requisição POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            // Enviando a requisição e recebendo a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica o código de resposta
            if (response.statusCode() == 201) {
                System.out.println("Conta criada com sucesso!");
                return true;
            } else {
                System.out.println("Erro ao criar conta. Código: " + response.statusCode());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para realizar login do usuário
    public static void loginAPI(Usuario user) {
        try {
            URI uri = new URI(API_URL + "/users/login");

            // Criando o corpo da requisição em JSON
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", user.getUsername());
            requestBody.put("password", user.getPassword());

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            // Criando a requisição POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            // Enviando a requisição e recebendo a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica o código de resposta
            if (response.statusCode() == 200) {
                System.out.println("Login realizado com sucesso!");
            } else {
                System.out.println("Erro ao realizar login. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar os dados do usuário
    public static void atualizarDadosAPI(Usuario user) {
        try {
            URI uri = new URI(API_URL + "/users/update");

            // Criando o corpo da requisição em JSON
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", user.getName());
            requestBody.put("email", user.getEmail());

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            // Criando a requisição PUT
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            // Enviando a requisição e recebendo a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica o código de resposta
            if (response.statusCode() == 200) {
                System.out.println("Dados do usuário atualizados com sucesso!");
            } else {
                System.out.println("Erro ao atualizar dados. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para deletar a conta do usuário
    public static void deletarContaAPI(Usuario user) {
        try {
            URI uri = new URI(API_URL + "/users/delete");

            // Criando o corpo da requisição em JSON
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", user.getEmail());

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            // Criando a requisição DELETE
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            // Enviando a requisição e recebendo a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica o código de resposta
            if (response.statusCode() == 200) {
                System.out.println("Conta deletada com sucesso!");
            } else {
                System.out.println("Erro ao deletar conta. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
