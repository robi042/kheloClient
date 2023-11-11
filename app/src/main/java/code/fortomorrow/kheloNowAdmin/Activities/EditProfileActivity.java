package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    String uid;
    String full_name;
    ImageView back;
    String user_email;

    EditText first_name, last_name, username, email, mobile, old_pass, new_pass, conf_pass;
    Button save, change;

    String myPassword;
    private APIService apiService;
    private String jwt_token, secret_id;
    private static Tracker mTracker;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init_view();


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        /*email.setInputType(InputType.TYPE_NULL);
        email.setTextIsSelectable(true);
        email.setKeyListener(null);

        mobile.setInputType(InputType.TYPE_NULL);
        mobile.setTextIsSelectable(true);
        mobile.setKeyListener(null);


        username.setInputType(InputType.TYPE_NULL);
        username.setTextIsSelectable(true);
        username.setKeyListener(null);*/


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = first_name.getText().toString();
                //String last = last_name.getText().toString();
                String emailNow = email.getText().toString();
                String phone = mobile.getText().toString();


                if (TextUtils.isEmpty(first) || TextUtils.isEmpty(emailNow) || TextUtils.isEmpty(phone)) {

                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();

                } else {
                    update_profile_info(first, emailNow, phone);
                }

            }
        });


        getProfileInfo();


    }

    private void init_view() {
        loader = new Dialog(EditProfileActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);

        back = findViewById(R.id.back);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        new_pass = findViewById(R.id.new_pass);
        conf_pass = findViewById(R.id.confirm_pass);
        save = findViewById(R.id.save);
    }

    private void update_profile_info(String first, String emailNow, String phone) {

        loader.show();
        apiService.updateProfileInfo(secret_id, jwt_token, first, emailNow, phone).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                loader.dismiss();

                if (response.body().getE() == 0) {
                    Toasty.success(getApplicationContext(), "updated successfully", Toasty.LENGTH_SHORT).show();
                    getProfileInfo();
                } else {
                    Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                loader.dismiss();

                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfileInfo() {
        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.body().getE() == 0) {
                    first_name.setText(response.body().getM().getName());
                    username.setText(response.body().getM().getUserName());
                    email.setText(response.body().getM().getEmail());
                    mobile.setText(EasySharedPref.read("phone_number", ""));
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

}