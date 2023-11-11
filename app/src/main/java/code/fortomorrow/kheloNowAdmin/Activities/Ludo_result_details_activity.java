package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_full_result_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludoi_booyah_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_full_result_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ludo_result_details_activity extends AppCompatActivity {

    TextView gameTitleText, dateTimeText, winnerPrizeText, entryFeeText;

    String matchID, jwt_token, secret_id;
    String gameTitle, gameEntryFee, totalPrize, datetime;
    ImageView backButton;
    RecyclerView fullResultView, topResultView;
    private APIService apiService;
    private List<Ludo_full_result_response.M> ludoFullResultList;
    private List<Ludo_full_result_response.M> ludoTopResultList;
    private Ludo_full_result_adapter adapter;
    private Ludoi_booyah_adapter booyah_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ludo_result_details_activity);

        backButton = findViewById(R.id.backButtonID);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gameTitleText = findViewById(R.id.gameTitleTextID);
        dateTimeText = findViewById(R.id.dateTimeTextID);
        winnerPrizeText = findViewById(R.id.winnerPrizeTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);

        matchID = getIntent().getStringExtra("match_id");
        gameTitle = getIntent().getStringExtra("game_title");
        gameEntryFee = getIntent().getStringExtra("entry_fee");
        totalPrize = getIntent().getStringExtra("total_prize");
        datetime = getIntent().getStringExtra("date_time");

        gameTitleText.setText(gameTitle);
        dateTimeText.setText(datetime);
        winnerPrizeText.setText(totalPrize);
        entryFeeText.setText(gameEntryFee);

        //Log.d("matchxx", matchID);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        fullResultView = findViewById(R.id.fullResultViewID);
        fullResultView.setHasFixedSize(true);
        fullResultView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        topResultView = findViewById(R.id.topResultViewID);
        topResultView.setHasFixedSize(true);
        topResultView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        load_full_result();

        load_top_result();
    }

    private void load_top_result() {
        apiService.LudoFullResultList(secret_id, jwt_token, matchID).enqueue(new Callback<Ludo_full_result_response>() {
            @Override
            public void onResponse(Call<Ludo_full_result_response> call, Response<Ludo_full_result_response> response) {
                if (response.body().getE() == 0) {
                    ludoTopResultList = new ArrayList<>();
                    ludoTopResultList = response.body().getM();
                    //adapter = new Ludo_full_result_adapter(ludoFullResultList);
                    booyah_adapter = new Ludoi_booyah_adapter(ludoTopResultList);
                    topResultView.setAdapter(booyah_adapter);
                }
            }

            @Override
            public void onFailure(Call<Ludo_full_result_response> call, Throwable t) {

            }
        });
    }

    private void load_full_result() {

        apiService.LudoFullResultList(secret_id, jwt_token, matchID).enqueue(new Callback<Ludo_full_result_response>() {
            @Override
            public void onResponse(Call<Ludo_full_result_response> call, Response<Ludo_full_result_response> response) {
                if (response.body().getE() == 0) {
                    ludoFullResultList = new ArrayList<>();
                    ludoFullResultList = response.body().getM();
                    adapter = new Ludo_full_result_adapter(ludoFullResultList);
                    fullResultView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Ludo_full_result_response> call, Throwable t) {

            }
        });

    }
}