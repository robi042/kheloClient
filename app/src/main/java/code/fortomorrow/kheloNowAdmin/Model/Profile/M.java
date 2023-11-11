package code.fortomorrow.kheloNowAdmin.Model.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("name")
@Expose
private String name;
@SerializedName("email")
@Expose
private String email;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("promo")
@Expose
private String promo;
@SerializedName("refer")
@Expose
private String refer;
@SerializedName("status")
@Expose
private String status;
@SerializedName("match_win")
@Expose
private Integer matchWin;
@SerializedName("total_kill")
@Expose
private Integer totalKill;
@SerializedName("total_balance")
@Expose
private Integer totalBalance;
@SerializedName("deposit_balance")
@Expose
private Integer depositBalance;
@SerializedName("winning_balance")
@Expose
private Integer winningBalance;
@SerializedName("total_match_play")
@Expose
private Integer totalMatchPlay;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getPromo() {
return promo;
}

public void setPromo(String promo) {
this.promo = promo;
}

public String getRefer() {
return refer;
}

public void setRefer(String refer) {
this.refer = refer;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public Integer getMatchWin() {
return matchWin;
}

public void setMatchWin(Integer matchWin) {
this.matchWin = matchWin;
}

public Integer getTotalKill() {
return totalKill;
}

public void setTotalKill(Integer totalKill) {
this.totalKill = totalKill;
}

public Integer getTotalBalance() {
return totalBalance;
}

public void setTotalBalance(Integer totalBalance) {
this.totalBalance = totalBalance;
}

public Integer getDepositBalance() {
return depositBalance;
}

public void setDepositBalance(Integer depositBalance) {
this.depositBalance = depositBalance;
}

public Integer getWinningBalance() {
return winningBalance;
}

public void setWinningBalance(Integer winningBalance) {
this.winningBalance = winningBalance;
}

public Integer getTotalMatchPlay() {
return totalMatchPlay;
}

public void setTotalMatchPlay(Integer totalMatchPlay) {
this.totalMatchPlay = totalMatchPlay;
}

}