package Model;

import Model.Payments;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class AppModelSummary {

    @JsonProperty("appId")
    private int appId;
    @JsonProperty("iconUrl")
    private String iconUrl;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("type")
    private String type;
       @JsonProperty("category")
    private String category;
       @JsonProperty("download")
    private int download;
    public int getAppId() {
        return appId;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
      public String getAppIdString() {
        return Integer.toString(appId);
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AppModelSummary() {
    }

    
    
    public AppModelSummary(int appId, String iconUrl, String nome, String type) {
        this.appId = appId;
        this.iconUrl = iconUrl;
        this.nome = nome;
        this.type = type;
    }
    
    
    
    
    
    
}


    
