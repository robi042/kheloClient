package code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Joined_player_list_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public Integer getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public List<M> getM() {
        return m;
    }

    public void setM(List<M> m) {
        this.m = m;
    }

    public class M {

        @SerializedName("team_no")
        @Expose
        public String teamNo;
        @SerializedName("first_player")
        @Expose
        public String firstPlayer;
        @SerializedName("hasFirstPlayer")
        @Expose
        public Boolean hasFirstPlayer;
        @SerializedName("second_player")
        @Expose
        public String secondPlayer;
        @SerializedName("hasSecondPlayer")
        @Expose
        public Boolean hasSecondPlayer;
        @SerializedName("third_player")
        @Expose
        public String thirdPlayer;
        @SerializedName("hasThirdPlayer")
        @Expose
        public Boolean hasThirdPlayer;
        @SerializedName("forth_player")
        @Expose
        public String forthPlayer;
        @SerializedName("hasForthPlayer")
        @Expose
        public Boolean hasForthPlayer;
        @SerializedName("fifth_player")
        @Expose
        public String fifthPlayer;
        @SerializedName("hasFifthPlayer")
        @Expose
        public Boolean hasFifthPlayer;
        @SerializedName("sixth_player")
        @Expose
        public String sixthPlayer;
        @SerializedName("hasSixthPlayer")
        @Expose
        public Boolean hasSixthPlayer;

        public String getTeamNo() {
            return teamNo;
        }

        public void setTeamNo(String teamNo) {
            this.teamNo = teamNo;
        }

        public String getFirstPlayer() {
            return firstPlayer;
        }

        public void setFirstPlayer(String firstPlayer) {
            this.firstPlayer = firstPlayer;
        }

        public Boolean getHasFirstPlayer() {
            return hasFirstPlayer;
        }

        public void setHasFirstPlayer(Boolean hasFirstPlayer) {
            this.hasFirstPlayer = hasFirstPlayer;
        }

        public String getSecondPlayer() {
            return secondPlayer;
        }

        public void setSecondPlayer(String secondPlayer) {
            this.secondPlayer = secondPlayer;
        }

        public Boolean getHasSecondPlayer() {
            return hasSecondPlayer;
        }

        public void setHasSecondPlayer(Boolean hasSecondPlayer) {
            this.hasSecondPlayer = hasSecondPlayer;
        }

        public String getThirdPlayer() {
            return thirdPlayer;
        }

        public void setThirdPlayer(String thirdPlayer) {
            this.thirdPlayer = thirdPlayer;
        }

        public Boolean getHasThirdPlayer() {
            return hasThirdPlayer;
        }

        public void setHasThirdPlayer(Boolean hasThirdPlayer) {
            this.hasThirdPlayer = hasThirdPlayer;
        }

        public String getForthPlayer() {
            return forthPlayer;
        }

        public void setForthPlayer(String forthPlayer) {
            this.forthPlayer = forthPlayer;
        }

        public Boolean getHasForthPlayer() {
            return hasForthPlayer;
        }

        public void setHasForthPlayer(Boolean hasForthPlayer) {
            this.hasForthPlayer = hasForthPlayer;
        }

        public String getFifthPlayer() {
            return fifthPlayer;
        }

        public void setFifthPlayer(String fifthPlayer) {
            this.fifthPlayer = fifthPlayer;
        }

        public Boolean getHasFifthPlayer() {
            return hasFifthPlayer;
        }

        public void setHasFifthPlayer(Boolean hasFifthPlayer) {
            this.hasFifthPlayer = hasFifthPlayer;
        }

        public String getSixthPlayer() {
            return sixthPlayer;
        }

        public void setSixthPlayer(String sixthPlayer) {
            this.sixthPlayer = sixthPlayer;
        }

        public Boolean getHasSixthPlayer() {
            return hasSixthPlayer;
        }

        public void setHasSixthPlayer(Boolean hasSixthPlayer) {
            this.hasSixthPlayer = hasSixthPlayer;
        }
    }
}
