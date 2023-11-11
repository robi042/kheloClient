package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_tournament_game_list_response {
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

        @SerializedName("match_id")
        @Expose
        public Integer matchId;
        @SerializedName("match_date")
        @Expose
        public String matchDate;
        @SerializedName("match_time")
        @Expose
        public String matchTime;
        @SerializedName("entry_fee")
        @Expose
        public String entryFee;
        @SerializedName("host_app")
        @Expose
        public String hostApp;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("hasRoomcode")
        @Expose
        public Boolean hasRoomcode;
        @SerializedName("room_code")
        @Expose
        public String roomCode;

        @SerializedName("hasImage")
        @Expose
        public Boolean hasImage;

        @SerializedName("isReady")
        @Expose
        public Boolean isReady;

        public Boolean getReady() {
            return isReady;
        }

        public void setReady(Boolean ready) {
            isReady = ready;
        }

        public Boolean getHasImage() {
            return hasImage;
        }

        public void setHasImage(Boolean hasImage) {
            this.hasImage = hasImage;
        }

        public Boolean getHasRoomcode() {
            return hasRoomcode;
        }

        public void setHasRoomcode(Boolean hasRoomcode) {
            this.hasRoomcode = hasRoomcode;
        }

        public String getRoomCode() {
            return roomCode;
        }

        public void setRoomCode(String roomCode) {
            this.roomCode = roomCode;
        }

        @SerializedName("total_player")
        @Expose
        public String totalPlayer;
        @SerializedName("joined_player")
        @Expose
        public Integer joinedPlayer;
        @SerializedName("total_prize")
        @Expose
        public String totalPrize;
        @SerializedName("left_player")
        @Expose
        public Integer leftPlayer;
        @SerializedName("isMatchFull")
        @Expose
        public Boolean isMatchFull;
        @SerializedName("isJoined")
        @Expose
        public Boolean isJoined;

        @SerializedName("hasMessage")
        @Expose
        public Boolean hasMessage;
        @SerializedName("message")
        @Expose
        public String message;

        public Boolean getHasMessage() {
            return hasMessage;
        }

        public void setHasMessage(Boolean hasMessage) {
            this.hasMessage = hasMessage;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getMatchId() {
            return matchId;
        }

        public void setMatchId(Integer matchId) {
            this.matchId = matchId;
        }

        public String getMatchDate() {
            return matchDate;
        }

        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
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

        public String getHostApp() {
            return hostApp;
        }

        public void setHostApp(String hostApp) {
            this.hostApp = hostApp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotalPlayer() {
            return totalPlayer;
        }

        public void setTotalPlayer(String totalPlayer) {
            this.totalPlayer = totalPlayer;
        }

        public Integer getJoinedPlayer() {
            return joinedPlayer;
        }

        public void setJoinedPlayer(Integer joinedPlayer) {
            this.joinedPlayer = joinedPlayer;
        }

        public String getTotalPrize() {
            return totalPrize;
        }

        public void setTotalPrize(String totalPrize) {
            this.totalPrize = totalPrize;
        }

        public Integer getLeftPlayer() {
            return leftPlayer;
        }

        public void setLeftPlayer(Integer leftPlayer) {
            this.leftPlayer = leftPlayer;
        }

        public Boolean getMatchFull() {
            return isMatchFull;
        }

        public void setMatchFull(Boolean matchFull) {
            isMatchFull = matchFull;
        }

        public Boolean getJoined() {
            return isJoined;
        }

        public void setJoined(Boolean joined) {
            isJoined = joined;
        }
    }
}
