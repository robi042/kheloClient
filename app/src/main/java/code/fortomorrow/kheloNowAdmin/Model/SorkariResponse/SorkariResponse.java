package code.fortomorrow.kheloNowAdmin.Model.SorkariResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SorkariResponse {
    @SerializedName("e")
    @Expose
    private Integer e;
    private String m;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
