package code.fortomorrow.kheloNowAdmin.Model.PlayType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Play_Type {
    @SerializedName("e")
    @Expose
    private Integer e;
    @SerializedName("m")
    @Expose
    private List<M> m = null;

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
}
