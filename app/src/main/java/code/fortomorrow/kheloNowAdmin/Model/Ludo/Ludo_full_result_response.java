package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_full_result_response {
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

        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("player_name")
        @Expose
        public String playerName;
        @SerializedName("rank")
        @Expose
        public String rank;
        @SerializedName("winning_money")
        @Expose
        public String winningMoney;
        @SerializedName("isrefunded")
        @Expose
        public Boolean isrefunded;
        @SerializedName("refund_amount")
        @Expose
        public Integer refundAmount;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getWinningMoney() {
            return winningMoney;
        }

        public void setWinningMoney(String winningMoney) {
            this.winningMoney = winningMoney;
        }

        public Boolean getIsrefunded() {
            return isrefunded;
        }

        public void setIsrefunded(Boolean isrefunded) {
            this.isrefunded = isrefunded;
        }

        public Integer getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(Integer refundAmount) {
            this.refundAmount = refundAmount;
        }
    }

}
