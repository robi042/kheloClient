package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_match_statistics_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public List<M> getM() {
        return m;
    }

    public void setM(List<M> m) {
        this.m = m;
    }

    public class M {

        @SerializedName("match_title")
        @Expose
        public String matchTitle;
        @SerializedName("palyed_on")
        @Expose
        public String palyedOn;
        @SerializedName("paid")
        @Expose
        public Integer paid;
        @SerializedName("winning")
        @Expose
        public Integer winning;
        @SerializedName("refund_amount")
        @Expose
        public Integer refundAmount;

        public String getMatchTitle() {
            return matchTitle;
        }

        public void setMatchTitle(String matchTitle) {
            this.matchTitle = matchTitle;
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

        public Integer getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(Integer refundAmount) {
            this.refundAmount = refundAmount;
        }
    }
}
