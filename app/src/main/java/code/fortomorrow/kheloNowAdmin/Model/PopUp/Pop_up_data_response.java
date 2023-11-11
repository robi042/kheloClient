package code.fortomorrow.kheloNowAdmin.Model.PopUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pop_up_data_response {
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

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("text")
        @Expose
        public String text;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("hasImage")
        @Expose
        public Boolean hasImage;
        @SerializedName("hasText")
        @Expose
        public Boolean hasText;
        @SerializedName("image_link")
        @Expose
        public String imageLink;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Boolean getHasImage() {
            return hasImage;
        }

        public void setHasImage(Boolean hasImage) {
            this.hasImage = hasImage;
        }

        public Boolean getHasText() {
            return hasText;
        }

        public void setHasText(Boolean hasText) {
            this.hasText = hasText;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }
    }


}



