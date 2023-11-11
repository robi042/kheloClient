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

import com.airbnb.lottie.L;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.AllResultAdapter;
import code.fortomorrow.kheloNowAdmin.Adapters.SingleResultAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.ResultResponse.M;
import code.fortomorrow.kheloNowAdmin.Model.ResultResponse.ResultResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultDetailsActivity extends AppCompatActivity {
    private APIService apiService;
    private TextView mTitle, mDate, mWinnerPrize, mPerKill, mEntryFee;
    private RecyclerView topResultRV, fullResultRV;
    private String secret_id, jwt_token;
    ImageView backButton;
    private static GoogleAnalytics sAnalytics;
    private static Tracker mTracker;

    private List<M> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);

        initall();

        String title = getIntent().getStringExtra("title");
        String type = getIntent().getStringExtra("type");
        String entryfee = getIntent().getStringExtra("entryfee");
        String time = getIntent().getStringExtra("time");
        String winningprize = getIntent().getStringExtra("winningprize");
        String perkill = getIntent().getStringExtra("perkill");
        String match_id = getIntent().getStringExtra("match_id");

        //Toast.makeText(getApplicationContext(), match_id, Toast.LENGTH_SHORT).show();

        mTitle.setText(title);
        mEntryFee.setText(entryfee);
        mDate.setText(time);
        mWinnerPrize.setText(winningprize);
        mPerKill.setText(perkill);

        /*apiService.getResults(secret_id, jwt_token, match_id).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                //Log.d("Resultsss", new Gson().toJson(response.body().getM()));

            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {

            }
        });*/

        resultList.clear();
        apiService.getResults(secret_id, jwt_token, match_id).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {

                resultList = response.body().getM();

                topResultRV.setAdapter(new SingleResultAdapter(resultList));
                fullResultRV.setAdapter(new AllResultAdapter(resultList));
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

    }

    private void initall() {
        mTitle = findViewById(R.id.mTitle);
        mDate = findViewById(R.id.mDate);
        mWinnerPrize = findViewById(R.id.mWinnerPrize);
        mPerKill = findViewById(R.id.mPerKill);
        mEntryFee = findViewById(R.id.mEntryFee);
        topResultRV = findViewById(R.id.topResultRV);
        fullResultRV = findViewById(R.id.fullResultRV);
        backButton = findViewById(R.id.backButtonID);


        topResultRV.setNestedScrollingEnabled(false);
        fullResultRV.setNestedScrollingEnabled(false);
        topResultRV.setHasFixedSize(true);
        fullResultRV.setHasFixedSize(true);
        topResultRV.setLayoutManager(new LinearLayoutManager(this));
        fullResultRV.setLayoutManager(new LinearLayoutManager(this));

        //topResultRV.setItemViewCacheSize(10);
        fullResultRV.setItemViewCacheSize(100);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
    }
}