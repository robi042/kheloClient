package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor.Arena_of_valor_full_result_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor.Arena_of_valor_top_result_adapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_result_match_info;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Arena_of_valor_result_details_activity extends AppCompatActivity {

    APIService apiService;
    String jwt_token, secret_id;
    String matchID, title, scheduleTime, totalPrize, entryFee;
    TextView winnerPrizeText, entryFeeText, titleText, scheduleText;
    ImageView backButton;

    private List<Arena_valor_result_match_info.M> infoList;
    private Arena_of_valor_full_result_adapter fullResultAdapter;
    private Arena_of_valor_top_result_adapter topResultAdapter;

    RecyclerView fullResultRV, topResultRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_of_valor_result_details);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        set_data();

        apiService.arena_of_valor_result_match_info(secret_id, jwt_token, matchID).enqueue(new Callback<Arena_valor_result_match_info>() {
            @Override
            public void onResponse(Call<Arena_valor_result_match_info> call, Response<Arena_valor_result_match_info> response) {

                if (response.body().e == 0) {
                    infoList = new ArrayList<>();
                    infoList = response.body().m;
                    //Log.d("matchxx", String.valueOf(response.body().e)+" "+String.valueOf(infoList.size()));
                    top_result(infoList);

                    full_result(infoList);
                }
            }

            @Override
            public void onFailure(Call<Arena_valor_result_match_info> call, Throwable t) {

            }
        });


    }

    private void top_result(List<Arena_valor_result_match_info.M> infoList) {
        topResultAdapter = new Arena_of_valor_top_result_adapter(infoList);
        topResultRV.setAdapter(topResultAdapter);
    }

    private void full_result(List<Arena_valor_result_match_info.M> infoList) {

        fullResultAdapter = new Arena_of_valor_full_result_adapter(infoList);
        fullResultRV.setAdapter(fullResultAdapter);
    }


    private void set_data() {
        titleText.setText(title);
        scheduleText.setText(scheduleTime);
        entryFeeText.setText(entryFee);
        winnerPrizeText.setText(totalPrize);
    }

    private void init_view() {
        backButton = findViewById(R.id.backButtonID);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        matchID = getIntent().getStringExtra("match_id");
        title = getIntent().getStringExtra("title");
        scheduleTime = getIntent().getStringExtra("schedule_time");
        totalPrize = getIntent().getStringExtra("winning_prize");
        entryFee = getIntent().getStringExtra("entry_fee");



        titleText = findViewById(R.id.titleTextID);
        winnerPrizeText = findViewById(R.id.winnerPrizeTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);
        scheduleText = findViewById(R.id.scheduleTextID);

        fullResultRV = findViewById(R.id.fullResultRV);
        fullResultRV.setHasFixedSize(true);
        fullResultRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        topResultRV = findViewById(R.id.topResultRV);
        topResultRV.setHasFixedSize(true);
        topResultRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}