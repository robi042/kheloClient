package code.fortomorrow.kheloNowAdmin.Model.Products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;


    public class M {

        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("hasDiscount")
        @Expose
        public Boolean hasDiscount;
        @SerializedName("discount")
        @Expose
        public String discount;
        @SerializedName("discounted_price")
        @Expose
        public Float discountedPrice;

        @SerializedName("watch_link")
        @Expose
        public  String watch_link;

    }

}
