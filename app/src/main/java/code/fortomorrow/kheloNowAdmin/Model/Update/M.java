package code.fortomorrow.kheloNowAdmin.Model.Update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("khelo")
@Expose
private String khelo;
@SerializedName("link")
@Expose
private String link;
@SerializedName("id")
@Expose
private String id;

public String getKhelo() {
return khelo;
}

public void setKhelo(String khelo) {
this.khelo = khelo;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}