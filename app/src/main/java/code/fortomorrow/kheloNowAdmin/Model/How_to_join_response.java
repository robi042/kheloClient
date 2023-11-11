package code.fortomorrow.kheloNowAdmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class How_to_join_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public M m;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }

    public class M {

        @SerializedName("link")
        @Expose
        public String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
