package Models.Api;

import static Constants.Constants.API_URL;
import static Constants.Constants.TOKEN_FILE_PATH;
import Model.Usuario;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe responsável pelas operações da API relacionadas ao usuário.
 */
public class User {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Método para criar uma conta de usuário
    public static Response criarContaAPI(Usuario user) {
        try {
            URI uri = new URI(API_URL + "/users/register");

            // Criando o corpo da requisição em JSON
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", user.getName());
            requestBody.put("email", user.getEmail());
            requestBody.put("password", user.getPassword());
            requestBody.put("username", user.getUsername());
            requestBody.put("mobileNumber", user.getMobileNumber());
            requestBody.put("userType", user.getUserType());

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            // Criando a requisição POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            // Enviando a requisição e recebendo a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Processa a resposta
            Map<String, Object> errorResponse = objectMapper.readValue(response.body(), Map.class);
            return new Response(response.statusCode(), (int) errorResponse.get("error_code"), (String) errorResponse.get("msg"));

        } catch (Exception e) {
            return new Response(500, 1, "Erro interno no servidor.", null);
        }
    }

    // Método para realizar login do usuário
    public static Response loginAPI(Usuario user) {
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

            // Processa a resposta
            Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
            if (response.statusCode() == 200) {
                String token = (String) responseMap.get("token");

                if (token != null) {
                    // Salvar o token em um arquivo
                    try (FileWriter fileWriter = new FileWriter(TOKEN_FILE_PATH)) {
                        fileWriter.write(token);
                        return new Response(response.statusCode(), 0, "Login feito com sucesso!", null);
                    } catch (IOException e) {
                        return new Response(response.statusCode(), 1, "Ocorreu um erro ao salvar o token.", null);
                    }
                } else {
                    return new Response(response.statusCode(), 1, "Token não encontrado na resposta.", null);
                }
            } else {
                return new Response(response.statusCode(), (int) responseMap.get("error_code"), (String) responseMap.get("msg"));
            }

        } catch (Exception e) {
            return new Response(500, 1, "Erro interno no servidor.", null);
        }
    }
    
public static Usuario userInfo() {
    try {
        // Carregar o token do arquivo token.txt
        String token = readTokenFromFile();
        if (token == null || token.isEmpty()) {
            System.err.println("Token não encontrado ou inválido.");
            return null; // Ou lançar uma exceção, conforme sua preferência
        }

        URI uri = new URI(API_URL + "/users/list/user");

        // Criando a requisição GET com o token Bearer
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + token) // Adicionando o token no cabeçalho
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Enviando a requisição e recebendo a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Processa a resposta
        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
        if (response.statusCode() == 200) {
            // Deserializando a resposta JSON para um objeto User
            Usuario user = objectMapper.convertValue(responseMap.get("user"), Usuario.class);
            return user; // Retorna o objeto User
        } else {
            System.err.println("Erro na requisição: " + responseMap.get("msg"));
            return null; // Ou lançar uma exceção
        }

    } catch (Exception e) {
        e.printStackTrace();
        return null; // Ou lançar uma exceção
    }
}

public static ObservableList<Usuario> carregarUsuariosDaAPI() {
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    
    try {
        // Carregar o token do arquivo token.txt
        String token = readTokenFromFile();
        if (token == null || token.isEmpty()) {
            System.err.println("Token não encontrado ou inválido.");
            return listaUsuarios; // Retorna uma lista vazia
        }

        URI uri = new URI(API_URL + "/users/list-all");

        // Criando a requisição GET com o token Bearer
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + token) // Adicionando o token no cabeçalho
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Enviando a requisição e recebendo a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Processa a resposta
        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
        if (response.statusCode() == 200) {
            // Supondo que a resposta contenha uma lista de usuários no campo "users"
            List<Map<String, Object>> usersList = (List<Map<String, Object>>) responseMap.get("users");
            
            for (Map<String, Object> userMap : usersList) {
               
                // Convertendo cada entrada da lista em um objeto Usuario
                Usuario user = objectMapper.convertValue(userMap, Usuario.class);
                listaUsuarios.add(user); // Adiciona o usuário à lista
            }
        } else {
            System.err.println("Erro na requisição: " + responseMap.get("msg"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return listaUsuarios; // Retorna a lista de usuários (vazia se ocorreu algum erro)
}

public static ObservableList<Model.App> carregarAppsDaAPI() {
    ObservableList<Model.App> listaUsuarios = FXCollections.observableArrayList();
    
    try {
        // Carregar o token do arquivo token.txt
        String token = readTokenFromFile();
        if (token == null || token.isEmpty()) {
            System.err.println("Token não encontrado ou inválido.");
            return listaUsuarios; // Retorna uma lista vazia
        }

        URI uri = new URI(API_URL + "/apps/list/apps");

        // Criando a requisição GET com o token Bearer
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + token) // Adicionando o token no cabeçalho
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Enviando a requisição e recebendo a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Processa a resposta
        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
        if (response.statusCode() == 200) {
            // Supondo que a resposta contenha uma lista de usuários no campo "users"
            List<Map<String, Object>> usersList = (List<Map<String, Object>>) responseMap.get("apps");
            
            for (Map<String, Object> userMap : usersList) {
               
                // Convertendo cada entrada da lista em um objeto Usuario
                Model.App user = objectMapper.convertValue(userMap, Model.App.class);
                listaUsuarios.add(user); // Adiciona o usuário à lista
            }
        } else {
            System.err.println("Erro na requisição: " + responseMap.get("msg"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return listaUsuarios; // Retorna a lista de usuários (vazia se ocorreu algum erro)
}

public static void atualizarStatusUsuario(String username, boolean status) {
        try {
            String token = readTokenFromFile();
            
        if (token == null || token.isEmpty()) {
            System.err.println("Token não encontrado ou inválido.");
            return; // Ou lançar uma exceção, conforme sua preferência
        }

            URL url = new URL(API_URL + "/users/change-status");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty( "Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"username\": \"%s\", \"status\": %b}", username, status);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Sucesso
                System.out.println("Status do usuário atualizado com sucesso.");
            } else {
                // Tratamento de erro
                System.err.println("Erro ao atualizar status do usuário. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao enviar requisição à API: " + e.getMessage());
        }
    }

public static void atualizarStatusApp(int username, boolean status) {
        try {
            String token = readTokenFromFile();
            
        if (token == null || token.isEmpty()) {
            System.err.println("Token não encontrado ou inválido.");
            return; // Ou lançar uma exceção, conforme sua preferência
        }

            URL url = new URL(API_URL + "/apps/change-status");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty( "Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"id\": \"%s\", \"status\": %b}", username, status);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Sucesso
                System.out.println("Status do usuário atualizado com sucesso.");
            } else {
                // Tratamento de erro
                System.err.println("Erro ao atualizar status do usuário. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao enviar requisição à API: " + e.getMessage());
        }
    }


public static Response forgotPassword(Usuario user) {
    try {
        URI uri = new URI(API_URL + "/users/forgot-reset");

        // Criando o corpo da requisição em JSON
        Map<String, String> requestBody = new HashMap<>();
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

        // Processando a resposta
        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
        if (response.statusCode() == 200) {
            return new Response(response.statusCode(), 0, "Código de verificação enviado com sucesso!", null);
        } else {
            return new Response(response.statusCode(), (int) responseMap.get("error_code"), (String) responseMap.get("msg"));
        }

    } catch (Exception e) {
        return new Response(500, 1, "Erro interno no servidor ao enviar o código.", null);
    }
}

public static Response verifyCode(Usuario user, String code) {
    try {
        URI uri = new URI(API_URL + "/users/verify-code");

        // Criando o corpo da requisição em JSON
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobileNumber", user.getMobileNumber());
        requestBody.put("resetCode", code);

        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        // Criando a requisição POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        // Enviando a requisição e recebendo a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Processando a resposta
        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
        if (response.statusCode() == 200) {
            return new Response(response.statusCode(), 0, "Código verificado com sucesso!", null);
        } else {
            return new Response(response.statusCode(), (int) responseMap.get("error_code"), (String) responseMap.get("msg"));
        }

    } catch (Exception e) {
        return new Response(500, 1, "Erro interno no servidor ao verificar o código.", null);
    }
}

public static Response resetPassword(Usuario user, String verificationCode) {
    try {
        URI uri = new URI(API_URL + "/users/reset-password");

        // Criando o corpo da requisição em JSON
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobileNumber", user.getMobileNumber());
        requestBody.put("newPassword", user.getPassword());
        requestBody.put("resetCode", verificationCode);


        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        // Criando a requisição POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        // Enviando a requisição e recebendo a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Processando a resposta
        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
        if (response.statusCode() == 200) {
            
                        String token = (String) responseMap.get("token");

                if (token != null) {
                    // Salvar o token em um arquivo
                    try (FileWriter fileWriter = new FileWriter(TOKEN_FILE_PATH)) {
                        fileWriter.write(token);
                        return new Response(response.statusCode(), 0, "Senha redefinida com sucesso!, Login feito com sucesso!", null);
                    } catch (IOException e) {
                        return new Response(response.statusCode(), 1, "Ocorreu um erro ao salvar o token.", null);
                    }
                }
                
            return new Response(response.statusCode(), 0, "Senha redefinida com sucesso!", null);
        } else {
            return new Response(response.statusCode(), (int) responseMap.get("error_code"), (String) responseMap.get("msg"));
        }

    } catch (Exception e) {
        return new Response(500, 1, "Erro interno no servidor ao redefinir a senha.", null);
    }
}


    // Método para ler o token do arquivo
    public static String readTokenFromFile() {
        try {
            return new String(Files.readAllBytes(Paths.get(TOKEN_FILE_PATH)));
        } catch (IOException e) {
            return null; // Não reporta erro, pois isso não deve aparecer na resposta
        }
    }

    // Método para atualizar os dados do usuário
    public static Response atualizarDadosAPI(Usuario user) {
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

            // Processa a resposta
            Map<String, Object> errorResponse = objectMapper.readValue(response.body(), Map.class);
            if (response.statusCode() == 200) {
                
                
                
                
                return new Response(response.statusCode(), 0, "Dados do usuário atualizados com sucesso!", null);
            } else {
                return new Response(response.statusCode(), (int) errorResponse.get("error_code"), (String) errorResponse.get("msg"));
            }

        } catch (Exception e) {
            return new Response(500, 1, "Erro interno no servidor.", null);
        }
    }

    // Método para deletar a conta do usuário
    public static Response deletarContaAPI(Usuario user) {
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

            // Processa a resposta
            Map<String, Object> errorResponse = objectMapper.readValue(response.body(), Map.class);
            if (response.statusCode() == 200) {
                return new Response(response.statusCode(), 0, "Conta deletada com sucesso!", null);
            } else {
                return new Response(response.statusCode(), (int) errorResponse.get("error_code"), (String) errorResponse.get("msg"));
            }

        } catch (Exception e) {
            return new Response(500, 1, "Erro interno no servidor.", null);
        }
    }
}
