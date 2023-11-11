package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    EditText codetx;
    String pin_number;
    RelativeLayout submit;
    private APIService apiService;
    private ProgressDialog progressDialog;

    private static Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        apiService = AppConfig.getRetrofit().create(APIService.class);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        init();

        progressDialog = new ProgressDialog(ForgotPassword.this);


    }

    void init() {
        codetx = findViewById(R.id.code);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_number = codetx.getText().toString();
                if (pin_number.length() > 3) {

                    progressDialog.dismiss();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(ForgotPassword.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_newpass, null);
                    final TextInputEditText new_pass = (TextInputEditText) mView.findViewById(R.id.new_pass);
                    final TextInputEditText confirm_pass = (TextInputEditText) mView.findViewById(R.id.confirm_pass);
                    final Button submit = (Button) mView.findViewById(R.id.submit);
                    mBuilder.setView(mView);
                    final AlertDialog dialog1 = mBuilder.create();
                    dialog1.show();

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String newPassword = new_pass.getText().toString().trim();
                            String confirmPassword = confirm_pass.getText().toString().trim();
                            if (newPassword.length() > 5 && confirmPassword.length() > 5 ) {

                                if (newPassword.equals(confirmPassword)) {
                                    if(isEmailValid(confirmPassword)){
                                        apiService.reset_password(pin_number,confirmPassword).enqueue(new Callback<SorkariResponse>() {
                                            @Override
                                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                                dialog1.dismiss();

                                                final SweetAlertDialog pDialog = new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.SUCCESS_TYPE);
                                                pDialog.setTitleText("অভিনন্দন")
                                                        .setContentText("আপনার পাসওয়ার্ডটি পরিবর্তন হয়েছে!");
                                                pDialog.show();

                                                pDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                        pDialog.setConfirmClickListener(null);


                                                        pDialog.cancel();
                                                        finish();


                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(Call<SorkariResponse> call, Throwable t) {

                                            }
                                        });

                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toasty.error(getApplicationContext(), "পাসওয়ার্ড এ অবশ্যই একটি স্পেশাল ক্যারেক্টার থাকতে হবে( যেমনঃ - @, # )", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    progressDialog.dismiss();
                                    Toasty.error(getApplicationContext(), "আপনি ভুল পাসওয়ার্ড দিয়েছেন ", Toast.LENGTH_SHORT).show();
                                }


                            } else
                                Toast.makeText(getApplicationContext(), "Password should be more than 5 digit", Toast.LENGTH_SHORT).show();


                        }
                    });





                }


            }
        });


    }


    public static boolean isEmailValid(String email) {
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(email);
        return hasSpecial.find();
    }

}