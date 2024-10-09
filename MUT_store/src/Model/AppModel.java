
package Model;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class AppModel implements Downloadable, Comparable<Object> {
    @FXML
    ImageView icon;
    ImageView shot_1;
    ImageView shot_2;
    ImageView shot_3;
    ImageView shot_4;
    String filePath;
    String nome;
    String developerName;
    String imagePath;
    float Preco;
    int numberOfDownloads;
    String Description;
    String politics;
    String categoria;
    boolean wallet;
    boolean card;
    public List<String>comentarios=new ArrayList<>();
    
    
   public AppModel(ImageView icon, String nome, float Preco, ImageView shot_1, ImageView shot_2, 
                    ImageView shot_3, ImageView shot_4, String Description, String politics, String developerName, 
                    String categoria, boolean wallet, boolean card){
      
       this.icon = icon;
       this.nome=nome;
       this.Preco = Preco;
       this.shot_1=shot_1;
       this.shot_2=shot_2;
       this.shot_3=shot_3;
       this.shot_4=shot_4;
       this.Description=Description;
       this.politics=politics;
       this.developerName=developerName;
       this.categoria = categoria;
       this.wallet=wallet;
       this.card=card;
       numberOfDownloads=0;  
   } 
   
   public ImageView getIconImage(){
       return icon;
   }
   
      public String getNome(){
       return nome;
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
    public int getNumeberOfDownlaods(){
        return numberOfDownloads;
    }
    
    public String getCategoria(){
        return categoria;
    }
    
    public boolean useCard(){
        return card;
    }
    
    public boolean  useWallet(){
        return wallet;
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

    @Override
    public int compareTo(Object o) {
        AppModel other  = (AppModel)o;
        return this.nome.compareToIgnoreCase(other.getNome());
    }
    

}
