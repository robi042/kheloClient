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
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Free_fire_CS_match_activity extends AppCompatActivity {

    CardView freeFireCsGrandMatchButton, freeFireCsRegularMatchButton;
    ImageView backButton;
    TextView csRegularMatchText, csGrandMatchText;
    APIService apiService;
    String jwt_token, secret_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_cs_match);


        init_view();

        /*apiService.getmatchlength_cs(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));
                mPubgMatch.setText(String.valueOf(response.body().getM()) + " Matches Found");

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });*/

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        freeFireCsRegularMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                go_to_activity("cs_match_get", "2");
            }
        });

        freeFireCsGrandMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                go_to_activity("cs_match_get", "5");
            }
        });


        match_length();
    }

    private void go_to_activity(String child, String gameID) {
        Intent i = new Intent(getApplicationContext(), DailyMatchActivity.class);
        i.putExtra("child", child);
        i.putExtra("game_id", gameID);
        startActivity(i);
    }

    private void match_length() {
        apiService.getmatchlength_cs(secret_id, jwt_token, "2").enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));
                csRegularMatchText.setText(String.valueOf(response.body().getM()) + " Matches Found");

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.getmatchlength_cs(secret_id, jwt_token, "5").enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));
                csGrandMatchText.setText(String.valueOf(response.body().getM()) + " Matches Found");

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        //Log.d("secretxx", jwt_token+" "+secret_id);

        backButton = findViewById(R.id.backButtonID);
        freeFireCsGrandMatchButton = findViewById(R.id.freeFireCsGrandMatchButtonID);
        freeFireCsRegularMatchButton = findViewById(R.id.freeFireCsRegularMatchButtonID);
        csRegularMatchText = findViewById(R.id.csRegularMatchTextID);
        csGrandMatchText = findViewById(R.id.csGrandMatchTextID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}