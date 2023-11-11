package code.fortomorrow.kheloNowAdmin.Model.ResultResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("first_player")
    @Expose
    public String firstPlayer;
    @SerializedName("rank")
    @Expose
    public String rank;
    @SerializedName("kill")
    @Expose
    public String kill;
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

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getKill() {
        return kill;
    }

    public void setKill(String kill) {
        this.kill = kill;
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