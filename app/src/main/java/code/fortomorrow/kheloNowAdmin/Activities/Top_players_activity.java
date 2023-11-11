package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.Top_players_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.TopPlayers.Top_players_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_players_activity extends AppCompatActivity {

    ImageView backButton;
    RecyclerView topPlayersListView;
    String secret_id, jwt_token;
    APIService apiService;
    private List<Top_players_list_response.M> topPlayerList;
    private Top_players_list_adapter adapter;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_players);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loader = new Dialog(Top_players_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        top_players_list();
    }

    private void top_players_list() {

        loader.show();
        apiService.getTopPlayers(secret_id, jwt_token).enqueue(new Callback<Top_players_list_response>() {
            @Override
            public void onResponse(Call<Top_players_list_response> call, Response<Top_players_list_response> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    topPlayerList = new ArrayList<>();
                    topPlayerList = response.body().getM();
                    adapter = new Top_players_list_adapter(topPlayerList);
                    topPlayersListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Top_players_list_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButtonID);
        topPlayersListView = findViewById(R.id.topPlayersListViewID);
        topPlayersListView.setHasFixedSize(true);
        topPlayersListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}