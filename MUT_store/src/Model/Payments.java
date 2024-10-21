package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payments {
     @JsonProperty("isEmola")
    private boolean isEmola;
      @JsonProperty("isMpesa")
    private boolean isMpesa;
       @JsonProperty("isBankCard")
    private boolean isBankCard;
   @JsonProperty("isApproved")
    private boolean isApproved;

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

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
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
