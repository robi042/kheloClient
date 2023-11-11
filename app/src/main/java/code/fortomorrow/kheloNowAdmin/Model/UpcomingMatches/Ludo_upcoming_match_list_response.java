package code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_upcoming_match_list_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<Ludo_upcoming_match_list_response.M> m = null;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public List<Ludo_upcoming_match_list_response.M> getM() {
        return m;
    }

    public void setM(List<Ludo_upcoming_match_list_response.M> m) {
        this.m = m;
    }

    public class M {

        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("match_time")
        @Expose
        public String matchTime;
        @SerializedName("match_date")
        @Expose
        public String matchDate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMatchTime() {
            return matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public String getMatchDate() {
            return matchDate;
        }

        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }
    }
}
