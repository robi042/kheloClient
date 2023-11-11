package code.fortomorrow.kheloNowAdmin.Model.Transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("type")
@Expose
private String type;
@SerializedName("amount")
@Expose
private Integer amount;
@SerializedName("status")
@Expose
private String status;
@SerializedName("phome_number")
@Expose
private String phomeNumber;
@SerializedName("payment_method")
@Expose
private String paymentMethod;
@SerializedName("requested_time")
@Expose
private String requestedTime;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getPhomeNumber() {
return phomeNumber;
}

public void setPhomeNumber(String phomeNumber) {
this.phomeNumber = phomeNumber;
}

public String getPaymentMethod() {
return paymentMethod;
}

public void setPaymentMethod(String paymentMethod) {
this.paymentMethod = paymentMethod;
}

public String getRequestedTime() {
return requestedTime;
}

public void setRequestedTime(String requestedTime) {
this.requestedTime = requestedTime;
}

}