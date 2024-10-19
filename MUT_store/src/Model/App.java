/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author Aderito
 */
public class App {
    
    private String nome;
    private int id;
    private float preco;
    private String developerName;
    private String description;
    private String politics;
    public Payments payments;
    private Date updatedAt;
     @JsonProperty("imagePaths")
    private List<String> imagePaths;
    private Boolean isApproved;
    
    private double rating;
    private int downloadCount;
    private int download;
    private String version;
    private String category;
    private String appFilePath;
    private String icon;

    
      public App(){};

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }
    

    // Método para obter a imagem com base em um índice
    public Image getImage(int index) {
        // Verifique se o índice é válido
        if (index >= 0 && index < imagePaths.size()) {
            String imageUrl = imagePaths.get(index);
            return new Image(imageUrl); // Retorna a imagem correspondente
        } else {
            // Retorne uma imagem padrão ou null se o índice não for válido
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }


    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public App(String nome, int id, float preco, String developerName, String description, String politics, Payments payments, Date updatedAt, List<String> imagePath, Boolean isApproved, double rating, int downloadCount, String version, String category, String appFilePath, String icon) {
        this.nome = nome;
        this.id = id;
        this.preco = preco;
        this.developerName = developerName;
        this.description = description;
        this.politics = politics;
        this.payments = payments;
        this.updatedAt = updatedAt;
        this.imagePaths= imagePath;
        this.isApproved = isApproved;
        this.rating = rating;
        this.downloadCount = downloadCount;
        this.version = version;
        this.category = category;
        this.appFilePath = appFilePath;
        this.icon = icon;
    }

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    public List<String> getImagePath() {
        return imagePaths;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePaths = imagePath;
    }

    public String getAppFilePath() {
        return appFilePath;
    }

    public void setAppFilePath(String appFilePath) {
        this.appFilePath = appFilePath;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    
}