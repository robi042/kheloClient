package code.fortomorrow.kheloNowAdmin.Model.GetResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Free_fire_paginated_result_response {
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
        @SerializedName("total_prize")
        @Expose
        public String totalPrize;
        @SerializedName("first_prize")
        @Expose
        public String firstPrize;
        @SerializedName("second_prize")
        @Expose
        public String secondPrize;
        @SerializedName("third_prize")
        @Expose
        public String thirdPrize;
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
        private Boolean isJoined;
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
