
package View;

import Controller.FazerDownloadController;
import Controller.MenuPrincipalController;
import Model.AppModel;
import java.io.IOException;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Application {
    
    public FXMLLoader loginLoader;
    public FXMLLoader carregandoLoader;
    public FXMLLoader menuLoader;
    public FXMLLoader uploadLoader;
    public FXMLLoader downloadLoader;
    public FXMLLoader alterarSenhaLoader;
    public FXMLLoader digitarCodigoLoader;
    public FXMLLoader criarContaLoader;
    public static Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage){
        MainStage.primaryStage=primaryStage;
        try{
       
        loginLoader = new FXMLLoader(getClass().getResource("LoginDesign.fxml"));
        Parent LoginRoot = loginLoader.load();        
        Object loginController = loginLoader.getController();
        Scene telaLogin = new Scene(LoginRoot);
        telaLogin.setUserData("LoginDesign.fxml");
        registerScene("TelaLogin", telaLogin, loginController);
        
        carregandoLoader = new FXMLLoader(getClass().getResource("Carregando.fxml"));
        Parent  carregandoRoot =carregandoLoader.load();        
        Object carregandoController = carregandoLoader.getController();
        Scene telaCarregando = new Scene(carregandoRoot);
        telaCarregando.setUserData("Carregando.fxml");
        registerScene("Carregando", telaCarregando,carregandoController);
        
        menuLoader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));

        Parent menuRoot = menuLoader.load();        
        Object menuController = menuLoader.getController();
        Scene telaMenu = new Scene(menuRoot);
        telaMenu.setUserData("MenuPrincipal.fxml");
        registerScene("MenuPrincipal", telaMenu, menuController);
        
        uploadLoader = new FXMLLoader(getClass().getResource("CriarApp.fxml"));
        Parent uploadRoot = uploadLoader.load();        
        Object uploadController = uploadLoader.getController();
        Scene telaCriarApp = new Scene(uploadRoot);
        telaCriarApp.setUserData("CriarApp.fxml");
        registerScene("CriarApp", telaCriarApp, uploadController);
        
        downloadLoader = new FXMLLoader (getClass().getResource("FazerDownload.fxml"));
        Parent downloadRoot = downloadLoader.load();
        Object downloadController = downloadLoader.getController();
        Scene telaDownload = new Scene(downloadRoot);
        telaDownload.setUserData("FazerDownload.fxml");
        registerScene("TelaDownload",telaDownload, downloadController);
        
        alterarSenhaLoader = new FXMLLoader(getClass().getResource("AlterarSenha.fxml"));
        Parent alterarSenhaRoot = alterarSenhaLoader.load();
        Object alterarSenhaController = alterarSenhaLoader.getController();
        Scene telaAlterarSenha = new Scene(alterarSenhaRoot);
        telaAlterarSenha.setUserData("AlterarSenha.fxml");
        registerScene("AlterarSenha", telaAlterarSenha, alterarSenhaController);
        
        digitarCodigoLoader = new FXMLLoader(getClass().getResource("DigitarCodigo.fxml"));
        Parent digitarCodigoRoot =digitarCodigoLoader.load();
        Object digitarCodigoController = digitarCodigoLoader.getController();
        Scene telaDigitarCodigo = new Scene(digitarCodigoRoot);
        telaDigitarCodigo.setUserData("DigitarCodigo.fxml");
        registerScene("DigitarCodigo", telaDigitarCodigo, digitarCodigoController);
        
        criarContaLoader = new FXMLLoader(getClass().getResource("CriarConta.fxml"));
        Parent criarContaRoot =criarContaLoader.load();
        Object criarContaController = criarContaLoader.getController();
        Scene telaCriarConta = new Scene(criarContaRoot);
        telaCriarConta.setUserData("CriarConta.fxml");
        registerScene("CriarConta", telaCriarConta, criarContaController);

        primaryStage.setTitle("MUT Store");
        primaryStage.setScene(telaLogin);
        primaryStage.show();
        
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
   
    /*public static Object changeScene (String fxml) throws Exception{
        FXMLLoader loader = new FXMLLoader(MainStage.class .getResource(fxml));
        Parent root =loader.load();
        Object controller = loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        return controller;
    }*/
    
    public static Stack<String> sceneHistory = new Stack<>();
    public static Map<String, Scene>sceneMap = new HashMap();
    public static Map<String, Object> controllerMap = new HashMap<>();
    
    public static void setStage(Stage stage){
        primaryStage = stage;
    }
    
    public static void registerScene(String id, Scene scene, Object controller) throws IOException{
        sceneMap.put(id, scene);
        controllerMap.put(id, controller);
    }
    
    public static void changeScene(String id){
        Scene scene=sceneMap.get(id);
        
        if(scene!=null){
            if(primaryStage.getScene()!=null){
                sceneHistory.push(primaryStage.getScene().getUserData().toString());
            }
            primaryStage.setScene(scene);
        }
    }
    
    public static void goBack(){
        if(!sceneHistory.isEmpty()){
            String previousID = sceneHistory.pop();
            Scene scene = sceneMap.get(previousID);
            if(scene!= null){
                 primaryStage.setScene(scene);
            }
            
        }
    }
    
    public static void goTo(String id){
        Scene scene = sceneMap.get(id);
        if(scene!=null){
            primaryStage.setScene(scene);
        }
    }
    
    public static Object getController(String controllerID){
        return controllerMap.get(controllerID);
    }
    
    public static void actualizarMenu(List<AppModel>appList){
        Object controller = getController("MenuPrincipal");
        
        if(controller instanceof MenuPrincipalController){
            ((MenuPrincipalController)controller).updateMenu(appList);
        }
    }
    
    
    public static void createDownloadPage(AppModel a){
        Object controller = getController("TelaDownload");
        if(controller instanceof FazerDownloadController){
            ((FazerDownloadController)controller).loadDownloadPageContent(a.getIconImage(), a.getNome(),a.getPreco(),
                                a.getShot_1(), a.getShot_2(), a.getShot_3(), a.getShot_4(), 
                                a.getDescription(), a.getPolitics(), a.getDeveloperName());
            
              if(a.getPreco()==0.0f){
             ((FazerDownloadController)controller).lb_preco.setText("Grátis");
             
                }
              else{
            ((FazerDownloadController)controller).lb_preco.setPrefWidth(224);
            ((FazerDownloadController)controller).lb_preco.setText(Float.toString(a.getPreco())+" MZN");
            }
        
         }
    }
   
    
    public static void resetScene(String id, String fxml) throws IOException{
        controllerMap.remove(id);
        sceneMap.remove(id);
        
        FXMLLoader loader = new FXMLLoader(MainStage.class.getResource(fxml));
        Parent root = loader.load();
        Object controller = loader.getController();
        Scene scene = new Scene(root);
        scene.setUserData(fxml);
        registerScene(id, scene, controller);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
