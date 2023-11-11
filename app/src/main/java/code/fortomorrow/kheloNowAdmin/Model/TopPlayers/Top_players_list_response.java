package code.fortomorrow.kheloNowAdmin.Model.TopPlayers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Top_players_list_response {
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

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("total_match_played")
        @Expose
        public Integer totalMatchPlayed;
        @SerializedName("win")
        @Expose
        public Integer win;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTotalMatchPlayed() {
            return totalMatchPlayed;
        }

        public void setTotalMatchPlayed(Integer totalMatchPlayed) {
            this.totalMatchPlayed = totalMatchPlayed;
        }

        public Integer getWin() {
            return win;
        }

        public void setWin(Integer win) {
            this.win = win;
        }
    }
}
