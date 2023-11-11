package code.fortomorrow.kheloNowAdmin.Model.getRoomIdPass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("room_pass")
    @Expose
    private String roomPass;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomPass() {
        return roomPass;
    }

    public void setRoomPass(String roomPass) {
        this.roomPass = roomPass;
    }

}