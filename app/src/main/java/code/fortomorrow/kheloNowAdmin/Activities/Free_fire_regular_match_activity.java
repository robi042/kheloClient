package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Free_fire_regular_match_activity extends AppCompatActivity {

    ImageView backButton;
    CardView freeFireRegularButton;
    TextView regularMatchLengthText, premiumMatchLengthText, grandMatchLengthText;
    APIService apiService;
    String jwt_token, secret_id;
    CardView freeFirePremiumButton, freeFireGrandButton;
    String playTypeID = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_regular_match);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        freeFireRegularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                go_to_Activity("free_fire_match_get", "1", playTypeID);
            }
        });

        freeFirePremiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                go_to_Activity("free_fire_match_get", "9", playTypeID);
            }
        });

        freeFireGrandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                go_to_Activity("free_fire_match_get", "10", playTypeID);
            }
        });
    }

    private void go_to_Activity(String child, String gameID, String playTypeID) {
        Intent i = new Intent(getApplicationContext(), DailyMatchActivity.class);
        i.putExtra("child", child);
        i.putExtra("game_id", gameID);
        i.putExtra("play_type", playTypeID);
        startActivity(i);
    }

    private void getMatchCountFreefireRegular() {


        apiService.getmatchlength_regular(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));
                regularMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_freefire_premium_match_length(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));
                premiumMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_freefire_grand_match_length(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));
                grandMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });





    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();

        getMatchCountFreefireRegular();

    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButtonID);
        freeFireRegularButton = findViewById(R.id.freeFireRegularButtonID);

        regularMatchLengthText = findViewById(R.id.regularMatchLengthTextID);
        premiumMatchLengthText = findViewById(R.id.premiumMatchLengthTextID);
        grandMatchLengthText = findViewById(R.id.grandMatchLengthTextID);

        freeFirePremiumButton = findViewById(R.id.freeFirePremiumButtonID);
        freeFireGrandButton = findViewById(R.id.freeFireGrandButtonID);
    }

    private void coming_soon_alert() {
        Dialog comingSoonAlert = new Dialog(Free_fire_regular_match_activity.this);
        comingSoonAlert.setContentView(R.layout.coming_soon_alert);
        comingSoonAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //comingSoonAlert.setCancelable(false);
        comingSoonAlert.show();

        Window window = comingSoonAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }
}