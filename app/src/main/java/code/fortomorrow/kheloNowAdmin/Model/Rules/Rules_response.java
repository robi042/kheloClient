package code.fortomorrow.kheloNowAdmin.Model.Rules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rules_response {

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

        @SerializedName("rule")
        @Expose
        public String rule;
        @SerializedName("type")
        @Expose
        public String type;

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
