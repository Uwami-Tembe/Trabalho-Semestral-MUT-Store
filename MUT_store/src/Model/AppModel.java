package Model;

import Controller.CriarAppController;
import Controller.MenuPrincipalController;
import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.Icon;
import jdk.jfr.Description;

public class AppModel implements Downloadable {

    @FXML
    File icon;
    File shot_1;
    File shot_2;
    File shot_3;
    File shot_4;
    File file;
    String filePath;
    String nome;
    String developerName;
    String imagePath;
    float Preco;
    String Description;
    String politics;
    boolean isEmola;
    boolean isMpesa;
    boolean isBankCard;

    
      public AppModel() {}
    public AppModel(File icon, String nome, float Preco, File shot_1, File shot_2,
            File shot_3, File shot_4, String Description, String politics, String developerName, boolean isBankCard, boolean isMpesa, boolean isEmola, File file) {

        this.icon = icon;
        this.nome = nome;
        this.Preco = Preco;
        this.shot_1 = shot_1;
        this.shot_2 = shot_2;
        this.shot_3 = shot_3;
        this.shot_4 = shot_4;
        this.Description = Description;
        this.politics = politics;
        this.developerName = developerName;
        this.isBankCard = isBankCard;
        this.isEmola = isEmola;
        this.isMpesa = isMpesa;
        this.file = file;

    }



    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isIsEmola() {
        return isEmola;
    }
    

    public void setIsEmola(boolean isEmola) {
        this.isEmola = isEmola;
    }

    public boolean isIsMpesa() {
        return isMpesa;
    }

    public void setIsMpesa(boolean isMpesa) {
        this.isMpesa = isMpesa;
    }

    public boolean isIsBankCard() {
        return isBankCard;
    }

    public void setIsBankCard(boolean isBankCard) {
        this.isBankCard = isBankCard;
    }

    public File getIcon() {
        return icon;
    }

    public void setIcon(File icon) {
        this.icon = icon;
    }
    
     /**
     * Retorna o ícone como um ImageView para ser exibido em uma interface JavaFX.
     * 
     * @return ImageView contendo a imagem do ícone.
     */
    public ImageView getIconImage() {
        if (icon != null && icon.exists()) {
            try {
                // Carregando a imagem a partir do arquivo de ícone
                Image image = new Image(icon.toURI().toString());
                // Criando um ImageView para exibir a imagem
                ImageView iconImageView = new ImageView(image);

                // Definir o tamanho desejado do ícone, se necessário
                iconImageView.setFitWidth(64);  // Exemplo de ajuste de largura
                iconImageView.setFitHeight(64); // Exemplo de ajuste de altura
                iconImageView.setPreserveRatio(true);

                return iconImageView;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erro ao carregar o ícone: " + e.getMessage());
            }
        }
        return null; // Caso o ícone não exista ou ocorra um erro
    }


    public File getShot_1() {
        return shot_1;
    }

    public void setShot_1(File shot_1) {
        this.shot_1 = shot_1;
    }

    public File getShot_2() {
        return shot_2;
    }

    public void setShot_2(File shot_2) {
        this.shot_2 = shot_2;
    }

    public File getShot_3() {
        return shot_3;
    }

    public void setShot_3(File shot_3) {
        this.shot_3 = shot_3;
    }

    public File getShot_4() {
        return shot_4;
    }

    public void setShot_4(File shot_4) {
        this.shot_4 = shot_4;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   

    public String getDeveloperName() {
        return developerName;
    }

    public float getPreco() {
        return Preco;
    }

    public String getDescription() {
        return Description;
    }

    public String getPolitics() {
        return politics;
    }

    @Override
    public void setIcon() {

    }

    @Override
    public void setFicheiro() {

    }

    @Override
    public void setNome() {

    }

    @Override
    public void setPreco() {

    }

    @Override
    public void setMetodosDePagamento() {

    }

    @Override
    public void setDescription() {

    }

    @Override
    public void setPolitics() {

    }

    /**
 * Retorna a captura de tela 1 como um ImageView para ser exibida em uma interface JavaFX.
 * 
 * @return ImageView contendo a imagem da captura de tela 1.
 */
public ImageView getShot1Image() {
    return createImageView(shot_1);
}

/**
 * Retorna a captura de tela 2 como um ImageView para ser exibida em uma interface JavaFX.
 * 
 * @return ImageView contendo a imagem da captura de tela 2.
 */
public ImageView getShot2Image() {
    return createImageView(shot_2);
}

/**
 * Retorna a captura de tela 3 como um ImageView para ser exibida em uma interface JavaFX.
 * 
 * @return ImageView contendo a imagem da captura de tela 3.
 */
public ImageView getShot3Image() {
    return createImageView(shot_3);
}

/**
 * Retorna a captura de tela 4 como um ImageView para ser exibida em uma interface JavaFX.
 * 
 * @return ImageView contendo a imagem da captura de tela 4.
 */
public ImageView getShot4Image() {
    return createImageView(shot_4);
}

/**
 * Método auxiliar para carregar uma imagem de arquivo e retornar um ImageView.
 * 
 * @param file Arquivo de imagem a ser carregado.
 * @return ImageView contendo a imagem do arquivo, ou null se o arquivo não existir.
 */
private ImageView createImageView(File file) {
    if (file != null && file.exists()) {
        try {
            // Carregando a imagem a partir do arquivo
            Image image = new Image(file.toURI().toString());
            // Criando um ImageView para exibir a imagem
            ImageView imageView = new ImageView(image);

            // Definir o tamanho desejado da imagem, se necessário
            imageView.setFitWidth(128);  // Exemplo de ajuste de largura
            imageView.setFitHeight(128); // Exemplo de ajuste de altura
            imageView.setPreserveRatio(true);

            return imageView;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }
    }
    return null; // Caso o arquivo não exista ou ocorra um erro
}
}
