package Model;
import java.time.LocalDateTime;


public class Comment {

    private Long id;


    private Long appId;


    private String email;
    
        private String username;

    private String comment;


    private String createdAt;


    private LocalDateTime updatedAt;

    // Construtor sem argumentos necessário para JPA
    public Comment() {
    }

    // Construtor com argumentos para facilitar a criação do feedback
    public Comment(Long appId, String email, String comment) {
        this.appId = appId;
        this.email = email;
        this.comment = comment;// Define a data atual por padrão
        this.updatedAt = LocalDateTime.now(); // Define a data atual por padrão
    }


    protected void onCreate() {
        updatedAt = LocalDateTime.now();
    }

    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
