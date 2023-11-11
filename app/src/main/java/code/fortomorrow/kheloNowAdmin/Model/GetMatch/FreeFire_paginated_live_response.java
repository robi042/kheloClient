package code.fortomorrow.kheloNowAdmin.Model.GetMatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FreeFire_paginated_live_response {
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
        @SerializedName("match_time")
        @Expose
        public String matchTime;
        @SerializedName("entry_fee")
        @Expose
        public String entryFee;
        @SerializedName("map")
        @Expose
        public String map;
        @SerializedName("per_kill_rate")
        @Expose
        public String perKillRate;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("total_player")
        @Expose
        public String totalPlayer;
        @SerializedName("joined_player")
        @Expose
        public Integer joinedPlayer;
        @SerializedName("left_player")
        @Expose
        public Integer leftPlayer;
        @SerializedName("total_prize")
        @Expose
        public String totalPrize;
        @SerializedName("version")
        @Expose
        public String version;
        @SerializedName("game_type")
        @Expose
        public String gameType;
        @SerializedName("player_type")
        @Expose
        public String playerType;
        @SerializedName("isJoined")
        @Expose
        public Boolean isJoined;
        @SerializedName("room_id")
        @Expose
        public String roomCode;
        @SerializedName("room_pass")
        @Expose
        public String roomPassword;
        @SerializedName("isMatchFull")
        @Expose
        public Boolean isMatchFull;
        @SerializedName("hasFirstPrize")
        @Expose
        public Boolean hasFirstPrize;
        @SerializedName("first_prize")
        @Expose
        public String firstPrize;
        @SerializedName("hasSecondPrize")
        @Expose
        public Boolean hasSecondPrize;
        @SerializedName("second_prize")
        @Expose
        public String secondPrize;
        @SerializedName("hasThirdPrize")
        @Expose
        public Boolean hasThirdPrize;
        @SerializedName("third_prize")
        @Expose
        public String thirdPrize;
        @SerializedName("hasFourthPrize")
        @Expose
        public Boolean hasFourthPrize;
        @SerializedName("fourth_prize")
        @Expose
        public String fourthPrize;
        @SerializedName("hasFifthPrize")
        @Expose
        public Boolean hasFifthPrize;
        @SerializedName("fifth_prize")
        @Expose
        public String fifthPrize;
        @SerializedName("hasSixthPrize")
        @Expose
        public Boolean hasSixthPrize;
        @SerializedName("sixth_prize")
        @Expose
        public String sixthPrize;
        @SerializedName("hasSeventhPrize")
        @Expose
        public Boolean hasSeventhPrize;
        @SerializedName("seventh_prize")
        @Expose
        public String seventhPrize;
        @SerializedName("hasEightthPrize")
        @Expose
        public Boolean hasEightthPrize;
        @SerializedName("eightth_prize")
        @Expose
        public String eightthPrize;
        @SerializedName("hasNinethPrize")
        @Expose
        public Boolean hasNinethPrize;
        @SerializedName("nineth_prize")
        @Expose
        public String ninethPrize;
        @SerializedName("hasTenthPrize")
        @Expose
        public Boolean hasTenthPrize;
        @SerializedName("tenth_prize")
        @Expose
        public String tenthPrize;

        public Boolean getHasFirstPrize() {
            return hasFirstPrize;
        }

        public void setHasFirstPrize(Boolean hasFirstPrize) {
            this.hasFirstPrize = hasFirstPrize;
        }

        public Boolean getHasSecondPrize() {
            return hasSecondPrize;
        }

        public void setHasSecondPrize(Boolean hasSecondPrize) {
            this.hasSecondPrize = hasSecondPrize;
        }

        public Boolean getHasThirdPrize() {
            return hasThirdPrize;
        }

        public void setHasThirdPrize(Boolean hasThirdPrize) {
            this.hasThirdPrize = hasThirdPrize;
        }

        public Boolean getHasFourthPrize() {
            return hasFourthPrize;
        }

        public void setHasFourthPrize(Boolean hasFourthPrize) {
            this.hasFourthPrize = hasFourthPrize;
        }

        public String getFourthPrize() {
            return fourthPrize;
        }

        public void setFourthPrize(String fourthPrize) {
            this.fourthPrize = fourthPrize;
        }

        public Boolean getHasFifthPrize() {
            return hasFifthPrize;
        }

        public void setHasFifthPrize(Boolean hasFifthPrize) {
            this.hasFifthPrize = hasFifthPrize;
        }

        public String getFifthPrize() {
            return fifthPrize;
        }

        public void setFifthPrize(String fifthPrize) {
            this.fifthPrize = fifthPrize;
        }

        public Boolean getHasSixthPrize() {
            return hasSixthPrize;
        }

        public void setHasSixthPrize(Boolean hasSixthPrize) {
            this.hasSixthPrize = hasSixthPrize;
        }

        public String getSixthPrize() {
            return sixthPrize;
        }

        public void setSixthPrize(String sixthPrize) {
            this.sixthPrize = sixthPrize;
        }

        public Boolean getHasSeventhPrize() {
            return hasSeventhPrize;
        }

        public void setHasSeventhPrize(Boolean hasSeventhPrize) {
            this.hasSeventhPrize = hasSeventhPrize;
        }

        public String getSeventhPrize() {
            return seventhPrize;
        }

        public void setSeventhPrize(String seventhPrize) {
            this.seventhPrize = seventhPrize;
        }

        public Boolean getHasEightthPrize() {
            return hasEightthPrize;
        }

        public void setHasEightthPrize(Boolean hasEightthPrize) {
            this.hasEightthPrize = hasEightthPrize;
        }

        public String getEightthPrize() {
            return eightthPrize;
        }

        public void setEightthPrize(String eightthPrize) {
            this.eightthPrize = eightthPrize;
        }

        public Boolean getHasNinethPrize() {
            return hasNinethPrize;
        }

        public void setHasNinethPrize(Boolean hasNinethPrize) {
            this.hasNinethPrize = hasNinethPrize;
        }

        public String getNinethPrize() {
            return ninethPrize;
        }

        public void setNinethPrize(String ninethPrize) {
            this.ninethPrize = ninethPrize;
        }

        public Boolean getHasTenthPrize() {
            return hasTenthPrize;
        }

        public void setHasTenthPrize(Boolean hasTenthPrize) {
            this.hasTenthPrize = hasTenthPrize;
        }

        public String getTenthPrize() {
            return tenthPrize;
        }

        public void setTenthPrize(String tenthPrize) {
            this.tenthPrize = tenthPrize;
        }

        @SerializedName("page_info")
        @Expose
        public PageInfo pageInfo;

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

        public Integer getLeftPlayer() {
            return leftPlayer;
        }

        public void setLeftPlayer(Integer leftPlayer) {
            this.leftPlayer = leftPlayer;
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

        public Boolean getJoined() {
            return isJoined;
        }

        public void setJoined(Boolean joined) {
            isJoined = joined;
        }

        public Boolean getMatchFull() {
            return isMatchFull;
        }

        public void setMatchFull(Boolean matchFull) {
            isMatchFull = matchFull;
        }

        public PageInfo getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }

        public class PageInfo {

            @SerializedName("has_results")
            @Expose
            public Boolean hasResults;
            @SerializedName("page_number")
            @Expose
            public String pageNumber;
            @SerializedName("show_next")
            @Expose
            public Boolean showNext;
            @SerializedName("show_prev")
            @Expose
            public Boolean showPrev;
            @SerializedName("records_remaining")
            @Expose
            public Integer recordsRemaining;
            @SerializedName("offset")
            @Expose
            public Integer offset;

            public Boolean getHasResults() {
                return hasResults;
            }

            public void setHasResults(Boolean hasResults) {
                this.hasResults = hasResults;
            }

            public String getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(String pageNumber) {
                this.pageNumber = pageNumber;
            }

            public Boolean getShowNext() {
                return showNext;
            }

            public void setShowNext(Boolean showNext) {
                this.showNext = showNext;
            }

            public Boolean getShowPrev() {
                return showPrev;
            }

            public void setShowPrev(Boolean showPrev) {
                this.showPrev = showPrev;
            }

            public Integer getRecordsRemaining() {
                return recordsRemaining;
            }

            public void setRecordsRemaining(Integer recordsRemaining) {
                this.recordsRemaining = recordsRemaining;
            }

            public Integer getOffset() {
                return offset;
            }

            public void setOffset(Integer offset) {
                this.offset = offset;
            }
        }

    }
}
