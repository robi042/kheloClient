package code.fortomorrow.kheloNowAdmin.Model.GetResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("match_id")
    @Expose
    private Integer matchId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getJoined() {
        return isJoined;
    }

    public void setJoined(Boolean joined) {
        isJoined = joined;
    }

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("match_time")
    @Expose
    private String matchTime;
    @SerializedName("entry_fee")
    @Expose
    private String entryFee;
    @SerializedName("map")
    @Expose
    private String map;
    @SerializedName("per_kill_rate")
    @Expose
    private String perKillRate;
    @SerializedName("total_player")
    @Expose
    private String totalPlayer;
    @SerializedName("total_prize")
    @Expose
    private String totalPrize;
    @SerializedName("first_prize")
    @Expose
    private String firstPrize;
    @SerializedName("second_prize")
    @Expose
    private String secondPrize;
    @SerializedName("third_prize")
    @Expose
    private String thirdPrize;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("game_type")
    @Expose
    private String gameType;
    @SerializedName("player_type")
    @Expose
    private String playerType;
    @SerializedName("isJoined")
    @Expose
    private Boolean isJoined;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPerKillRate() {
        return perKillRate;
    }

    public void setPerKillRate(String perKillRate) {
        this.perKillRate = perKillRate;
    }

    public String getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(String totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        this.firstPrize = firstPrize;
    }

    public String getSecondPrize() {
        return secondPrize;
    }

    public void setSecondPrize(String secondPrize) {
        this.secondPrize = secondPrize;
    }

    public String getThirdPrize() {
        return thirdPrize;
    }

    public void setThirdPrize(String thirdPrize) {
        this.thirdPrize = thirdPrize;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public Boolean getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(Boolean isJoined) {
        this.isJoined = isJoined;
    }

}