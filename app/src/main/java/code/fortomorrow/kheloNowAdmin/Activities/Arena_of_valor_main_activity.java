package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.MatchLength.Match_length_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Arena_of_valor_main_activity extends AppCompatActivity {

    ImageView backButton;
    TextView grandMatchLengthText, regularMatchLengthText;
    APIService apiService;
    String secret_id, jwt_token;

    CardView regularButton,grandButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_of_valor_main);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        match_length();

        regularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_daily_match_activity("11");
            }
        });

        grandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_daily_match_activity("12");
            }
        });

    }

    private void go_to_daily_match_activity(String game_id) {
        Intent i = new Intent(getApplicationContext(), DailyMatchActivity.class);
        i.putExtra("child", "free_fire_match_get");
        i.putExtra("game_id", game_id);
        startActivity(i);
    }

    private void match_length() {
        apiService.arena_of_valor_get_each_type_match_length(secret_id, jwt_token, "11").enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {
                    regularMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");
                } else {
                    regularMatchLengthText.setText(String.valueOf(response.body().getM()));
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                regularMatchLengthText.setText("Authentication failed");
            }
        });

        apiService.arena_of_valor_get_each_type_match_length(secret_id, jwt_token, "12").enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {
                    grandMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");
                } else {
                    grandMatchLengthText.setText(String.valueOf(response.body().getM()));
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                grandMatchLengthText.setText("Authentication failed");
            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx",secret_id+" "+ jwt_token);

        backButton = findViewById(R.id.backButtonID);
        grandMatchLengthText = findViewById(R.id.grandMatchLengthTextID);
        regularMatchLengthText = findViewById(R.id.regularMatchLengthTextID);

        regularButton = findViewById(R.id.regularButtonID);
        grandButton = findViewById(R.id.grandButtonID);
    }
}