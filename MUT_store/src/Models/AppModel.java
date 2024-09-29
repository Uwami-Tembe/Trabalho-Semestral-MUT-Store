package Model;

import Controller.CriarAppController;
import Controller.MenuPrincipalController;
import java.awt.Panel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.Icon;
import jdk.jfr.Description;

public class AppModel implements Downloadable {

    @FXML
    ImageView icon;
    ImageView shot_1;
    ImageView shot_2;
    ImageView shot_3;
    ImageView shot_4;
    ImageView file;
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

    public AppModel(ImageView icon, String nome, float Preco, ImageView shot_1, ImageView shot_2,
            ImageView shot_3, ImageView shot_4, String Description, String politics, String developerName, boolean isBankCard, boolean isMpesa, boolean isEmola, ImageView file) {

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

    public ImageView getIconImage() {
        return icon;
    }

    public ImageView getFile() {
        return file;
    }

    public void setFile(ImageView file) {
        this.file = file;
    }

    public String getNome() {
        return nome;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
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

    public ImageView getShot_1() {
        return shot_1;
    }

    public ImageView getShot_2() {
        return shot_2;
    }

    public ImageView getShot_3() {
        return shot_3;
    }

    public ImageView getShot_4() {
        return shot_4;
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

}
