package code.fortomorrow.kheloNowAdmin.Model.getNumbers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("first_number")
@Expose
private String firstNumber;
@SerializedName("second_number")
@Expose
private String secondNumber;

public String getFirstNumber() {
return firstNumber;
}

public void setFirstNumber(String firstNumber) {
this.firstNumber = firstNumber;
}

public String getSecondNumber() {
return secondNumber;
}

public void setSecondNumber(String secondNumber) {
this.secondNumber = secondNumber;
}

}