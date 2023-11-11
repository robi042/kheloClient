package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_result_pagination_response {
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
        @SerializedName("total_prize")
        @Expose
        public String totalPrize;
        @SerializedName("isJoined")
        @Expose
        public Boolean isJoined;

        @SerializedName("hasMessage")
        @Expose
        public Boolean hasMessage;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("page_info")
        @Expose
        public PageInfo pageInfo;

        public Boolean getJoined() {
            return isJoined;
        }

        public void setJoined(Boolean joined) {
            isJoined = joined;
        }

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

        public String getTotalPrize() {
            return totalPrize;
        }

        public void setTotalPrize(String totalPrize) {
            this.totalPrize = totalPrize;
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
