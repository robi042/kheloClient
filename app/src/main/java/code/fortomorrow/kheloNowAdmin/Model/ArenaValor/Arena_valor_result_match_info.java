package code.fortomorrow.kheloNowAdmin.Model.ArenaValor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Arena_valor_result_match_info {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public class M {

        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("rank")
        @Expose
        public String rank;
        @SerializedName("winning_money")
        @Expose
        public String winningMoney;
        @SerializedName("isrefunded")
        @Expose
        public Boolean isrefunded;
        @SerializedName("refund_amount")
        @Expose
        public Integer refundAmount;

    }
}
