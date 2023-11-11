package code.fortomorrow.kheloNowAdmin.Model.UpdateMatchres;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateMatchResponse {

    @SerializedName("e")
    @Expose
    private Integer e;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }
}
