package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.UpcomingMatchAdapter.Free_fire_upcoming_match_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.UpcomingMatchAdapter.Ludo_upcoming_match_adapter;
import code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches.Free_fire_upcoming_match_list_response;
import code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches.Ludo_upcoming_match_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upcoming_match_activity extends AppCompatActivity {

    ImageView backButton;
    TextView titleText;
    String title;
    RecyclerView upcomingMatchView;
    APIService apiService;
    String secret_id, jwt_token;
    LinearLayout noMatchesLayout;

    private List<Free_fire_upcoming_match_list_response.M> upcomingList;
    private List<Ludo_upcoming_match_list_response.M> ludoUpcomingMatchList;
    private Free_fire_upcoming_match_list_adapter adapter;
    private Ludo_upcoming_match_adapter ludoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_match);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title = getIntent().getStringExtra("type");

        if (title.equals("free_fire")) {
            titleText.setText("Free Fire");

            free_fire_upcoming_matches();
        } else if (title.equals("ludo")) {
            titleText.setText("Ludo");

            ludo_upcoming_matches();
        }


    }

    private void ludo_upcoming_matches() {
        apiService.getUpComingLudoMatchList(secret_id, jwt_token).enqueue(new Callback<Ludo_upcoming_match_list_response>() {
            @Override
            public void onResponse(Call<Ludo_upcoming_match_list_response> call, Response<Ludo_upcoming_match_list_response> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    upcomingMatchView.setVisibility(View.VISIBLE);
                    noMatchesLayout.setVisibility(View.GONE);
                    ludoUpcomingMatchList = new ArrayList<>();
                    ludoUpcomingMatchList = response.body().getM();
                    ludoAdapter = new Ludo_upcoming_match_adapter(ludoUpcomingMatchList);
                    upcomingMatchView.setAdapter(ludoAdapter);
                } else {
                    upcomingMatchView.setVisibility(View.GONE);
                    noMatchesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Ludo_upcoming_match_list_response> call, Throwable t) {
                upcomingMatchView.setVisibility(View.GONE);
                noMatchesLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void free_fire_upcoming_matches() {
        apiService.getUpComingFreeFireMatchList(secret_id, jwt_token).enqueue(new Callback<Free_fire_upcoming_match_list_response>() {
            @Override
            public void onResponse(Call<Free_fire_upcoming_match_list_response> call, Response<Free_fire_upcoming_match_list_response> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    upcomingMatchView.setVisibility(View.VISIBLE);
                    noMatchesLayout.setVisibility(View.GONE);
                    upcomingList = new ArrayList<>();
                    upcomingList = response.body().getM();
                    adapter = new Free_fire_upcoming_match_list_adapter(upcomingList);
                    upcomingMatchView.setAdapter(adapter);
                } else {
                    upcomingMatchView.setVisibility(View.GONE);
                    noMatchesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Free_fire_upcoming_match_list_response> call, Throwable t) {
                upcomingMatchView.setVisibility(View.GONE);
                noMatchesLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButtonID);
        titleText = findViewById(R.id.titleTextID);
        noMatchesLayout = findViewById(R.id.noMatchesLayoutID);
        upcomingMatchView = findViewById(R.id.upcomingMatchViewID);
        upcomingMatchView.setHasFixedSize(true);
        upcomingMatchView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}