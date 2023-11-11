package code.fortomorrow.kheloNowAdmin.Model.Statistics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("palyed_on")
    @Expose
    private String palyedOn;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("winning")
    @Expose
    private Integer winning;
    @SerializedName("match_title")
    @Expose
    private String matchTitle;

    @SerializedName("refund_amount")
    @Expose
    private String refund_amount;

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getPalyedOn() {
        return palyedOn;
    }

    public void setPalyedOn(String palyedOn) {
        this.palyedOn = palyedOn;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getWinning() {
        return winning;
    }

    public void setWinning(Integer winning) {
        this.winning = winning;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

}