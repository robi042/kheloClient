package code.fortomorrow.kheloNowAdmin.Model.MatchLength;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match_length_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public Integer m;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }
}
