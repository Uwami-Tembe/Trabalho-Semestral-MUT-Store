package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payments {
     @JsonProperty("isEmola")
    private boolean isEmola;
      @JsonProperty("isMpesa")
    private boolean isMpesa;
       @JsonProperty("isBankCard")
    private boolean isBankCard;

    // Construtor padrão
    public Payments() {
    }

    // Getters e Setters
    public boolean isEmola() {
        return isEmola;
    }

    public void setEmola(boolean emola) {
        isEmola = emola;
    }

    public boolean isMpesa() {
        return isMpesa;
    }

    public void setMpesa(boolean mpesa) {
        isMpesa = mpesa;
    }

    public boolean isBankCard() {
        return isBankCard;
    }

    public void setBankCard(boolean bankCard) {
        isBankCard = bankCard;
    }
}
