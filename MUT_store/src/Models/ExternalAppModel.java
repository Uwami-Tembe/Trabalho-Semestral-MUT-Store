package Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class ExternalAppModel {

    @JsonProperty("_id")
    private String _id;
    @JsonProperty("id")
    private int id;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("developerName")
    private String developerName;
    @JsonProperty("appFilePath")
    private String appFilePath;
    @JsonProperty("imagePaths")
    private List<String> imagePaths;
    @JsonProperty("preco")
    private double preco;
    @JsonProperty("description")
    private String description;
    @JsonProperty("politics")
    private String politics;
    @JsonProperty("isApproved")
    private boolean isApproved;
    @JsonProperty("payments")
    private Payments payments;
    
  @JsonProperty("createdAt") // Mapeia o campo "createdAt" do JSON
    private String createdAt; // Use String ou LocalDateTime dependendo de como você deseja manipular a data

    @JsonProperty("updatedAt") // Mapeia o campo "updatedAt" do JSON
    private String updatedAt; // Use String ou LocalDateTime dependendo de como você deseja manipular a data

    @JsonProperty("__v") // Mapeia o campo "__v" do JSON
    private int __v;
    
    
    
    // Adicionando o objeto Payments

    // Construtor padrão
    public ExternalAppModel() {
    }

    // Getters e Setters
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public int getIdNum() {
        return id;
    }

    public void setIdNum(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }
    
     public String getIconImage() {
        return icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
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

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getAppFilePath() {
        return appFilePath;
    }

    public void setAppFilePath(String appFilePath) {
        this.appFilePath = appFilePath;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }
}
