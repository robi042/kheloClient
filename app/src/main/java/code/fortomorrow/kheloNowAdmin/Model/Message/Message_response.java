package code.fortomorrow.kheloNowAdmin.Model.Message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public class M {

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("date")
        @Expose
        public String date;
    }
}
