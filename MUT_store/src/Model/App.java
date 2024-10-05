/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Models;

/**
 *
 * @author Aderito
 */
public class App {
    
    private String appName;
    private String developerName;
    private String description;
    private double rating;
    private int downloadCount;
    private String version;
    private String category;
    private String fileUrl;
    private String logoUrl;

    public App(String appName, String developerName, String description, double rating, int downloadCount, String version, String category, String fileUrl, String logoUrl) {
        this.appName = appName;
        this.developerName = developerName;
        this.description = description;
        this.rating = rating;
        this.downloadCount = downloadCount;
        this.version = version;
        this.category = category;
        this.fileUrl = fileUrl;
        this.logoUrl = logoUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


    

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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
    
    
    
    
}
