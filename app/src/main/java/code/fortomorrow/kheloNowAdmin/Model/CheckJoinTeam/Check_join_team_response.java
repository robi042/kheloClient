package code.fortomorrow.kheloNowAdmin.Model.CheckJoinTeam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Check_join_team_response {
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

        @SerializedName("team_no")
        @Expose
        public String teamNo;
        @SerializedName("hasFirstPlayer")
        @Expose
        public Boolean hasFirstPlayer;
        @SerializedName("first_player")
        @Expose
        public String firstPlayer;
        @SerializedName("hasSecondPlayer")
        @Expose
        public Boolean hasSecondPlayer;
        @SerializedName("second_player")
        @Expose
        public String secondPlayer;
        @SerializedName("hasThirdPlayer")
        @Expose
        public Boolean hasThirdPlayer;
        @SerializedName("third_player")
        @Expose
        public String thirdPlayer;
        @SerializedName("hasForthPlayer")
        @Expose
        public Boolean hasForthPlayer;
        @SerializedName("forth_player")
        @Expose
        public String forthPlayer;
        @SerializedName("hasFifthPlayer")
        @Expose
        public Boolean hasFifthPlayer;
        @SerializedName("fifth_player")
        @Expose
        public String fifthPlayer;
        @SerializedName("hasSixthPlayer")
        @Expose
        public Boolean hasSixthPlayer;
        @SerializedName("sixth_player")
        @Expose
        public String sixthPlayer;

        @SerializedName("extra_player_one")
        @Expose
        public String extra_player_one;

        @SerializedName("extra_player_two")
        @Expose
        public String extra_player_two;

        @SerializedName("hasExtraOne")
        @Expose
        public Boolean hasExtraOne;

        @SerializedName("hasExtraTwo")
        @Expose
        public Boolean hasExtraTwo;

        public String getExtra_player_one() {
            return extra_player_one;
        }

        public void setExtra_player_one(String extra_player_one) {
            this.extra_player_one = extra_player_one;
        }

        public String getExtra_player_two() {
            return extra_player_two;
        }

        public void setExtra_player_two(String extra_player_two) {
            this.extra_player_two = extra_player_two;
        }

        public Boolean getHasExtraOne() {
            return hasExtraOne;
        }

        public void setHasExtraOne(Boolean hasExtraOne) {
            this.hasExtraOne = hasExtraOne;
        }

        public Boolean getHasExtraTwo() {
            return hasExtraTwo;
        }

        public void setHasExtraTwo(Boolean hasExtraTwo) {
            this.hasExtraTwo = hasExtraTwo;
        }


        public String getTeamNo() {
            return teamNo;
        }

        public void setTeamNo(String teamNo) {
            this.teamNo = teamNo;
        }

        public Boolean getHasFirstPlayer() {
            return hasFirstPlayer;
        }

        public void setHasFirstPlayer(Boolean hasFirstPlayer) {
            this.hasFirstPlayer = hasFirstPlayer;
        }

        public String getFirstPlayer() {
            return firstPlayer;
        }

        public void setFirstPlayer(String firstPlayer) {
            this.firstPlayer = firstPlayer;
        }

        public Boolean getHasSecondPlayer() {
            return hasSecondPlayer;
        }

        public void setHasSecondPlayer(Boolean hasSecondPlayer) {
            this.hasSecondPlayer = hasSecondPlayer;
        }

        public String getSecondPlayer() {
            return secondPlayer;
        }

        public void setSecondPlayer(String secondPlayer) {
            this.secondPlayer = secondPlayer;
        }

        public Boolean getHasThirdPlayer() {
            return hasThirdPlayer;
        }

        public void setHasThirdPlayer(Boolean hasThirdPlayer) {
            this.hasThirdPlayer = hasThirdPlayer;
        }

        public String getThirdPlayer() {
            return thirdPlayer;
        }

        public void setThirdPlayer(String thirdPlayer) {
            this.thirdPlayer = thirdPlayer;
        }

        public Boolean getHasForthPlayer() {
            return hasForthPlayer;
        }

        public void setHasForthPlayer(Boolean hasForthPlayer) {
            this.hasForthPlayer = hasForthPlayer;
        }

        public String getForthPlayer() {
            return forthPlayer;
        }

        public void setForthPlayer(String forthPlayer) {
            this.forthPlayer = forthPlayer;
        }

        public Boolean getHasFifthPlayer() {
            return hasFifthPlayer;
        }

        public void setHasFifthPlayer(Boolean hasFifthPlayer) {
            this.hasFifthPlayer = hasFifthPlayer;
        }

        public String getFifthPlayer() {
            return fifthPlayer;
        }

        public void setFifthPlayer(String fifthPlayer) {
            this.fifthPlayer = fifthPlayer;
        }

        public Boolean getHasSixthPlayer() {
            return hasSixthPlayer;
        }

        public void setHasSixthPlayer(Boolean hasSixthPlayer) {
            this.hasSixthPlayer = hasSixthPlayer;
        }

        public String getSixthPlayer() {
            return sixthPlayer;
        }

        public void setSixthPlayer(String sixthPlayer) {
            this.sixthPlayer = sixthPlayer;
        }
    }
}
