package code.fortomorrow.kheloNowAdmin.Model.getRoomIdPass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRoomIdResponse {
    @SerializedName("e")
    @Expose
    private Integer e;
    @SerializedName("m")
    @Expose
    private M m;

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
}
