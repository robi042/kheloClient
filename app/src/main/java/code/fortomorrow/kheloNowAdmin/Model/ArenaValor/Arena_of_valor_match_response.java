package code.fortomorrow.kheloNowAdmin.Model.ArenaValor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Arena_of_valor_match_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public class M {

        @SerializedName("match_id")
        @Expose
        public Integer matchId;
        @SerializedName("match_time")
        @Expose
        public String matchTime;
        @SerializedName("match_date")
        @Expose
        public String matchDate;
        @SerializedName("entry_fee")
        @Expose
        public String entryFee;
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
        @SerializedName("isMatchFull")
        @Expose
        public Boolean isMatchFull;
        @SerializedName("room_code")
        @Expose
        public String room_code;
        @SerializedName("hasRoomcode")
        @Expose
        public Boolean hasRoomcode;
        @SerializedName("page_info")
        @Expose
        public PageInfo pageInfo;

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

        }

    }
}
