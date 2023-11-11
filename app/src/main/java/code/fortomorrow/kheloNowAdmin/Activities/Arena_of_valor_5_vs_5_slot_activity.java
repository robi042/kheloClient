package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor.Arena_valor_5_vs_5_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.FreeFireJoin.Duo_join_adapter;
import code.fortomorrow.kheloNowAdmin.Model.CheckJoinTeam.Check_join_team_response;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Arena_of_valor_5_vs_5_slot_activity extends AppCompatActivity {

    String matchID, title, entryFee, version, gameType, playerType, winningPrize, schedule, matchState, totalPlayer, leftPlayer;
    String secret_id, jwt_token;
    APIService apiService;

    RecyclerView itemRecyclerView;
    private List<Check_join_team_response.M> joinedPlayerList;
    private Arena_valor_5_vs_5_adapter adapter;

    Boolean stateA = false, stateB = false, stateC = false, stateD = false, stateE = false;
    String selectedPosition = "";
    AppCompatButton joinNowButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_of_valor_5_vs_5_slot);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();
        apiService.arena_of_valor_join_player_list(secret_id, jwt_token, matchID).enqueue(new Callback<Check_join_team_response>() {
            @Override
            public void onResponse(Call<Check_join_team_response> call, Response<Check_join_team_response> response) {
                if (response.body().getE() == 0) {
                    joinedPlayerList = new ArrayList<>();
                    joinedPlayerList = response.body().getM();

                    adapter = new Arena_valor_5_vs_5_adapter(totalPlayer, Arena_of_valor_5_vs_5_slot_activity.this, joinedPlayerList);
                    itemRecyclerView.setAdapter(adapter);
                } else {
                    Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Check_join_team_response> call, Throwable t) {
                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(selectedPosition)) {
                    Toasty.error(getApplicationContext(), "select player position", Toasty.LENGTH_SHORT).show();
                } else {


                    if (stateA || stateB || stateC || stateD || stateE) {
                        Intent intent = new Intent(getApplicationContext(), Arena_of_valor_match_joining_activity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("match_id", matchID);
                        intent.putExtra("total_player", totalPlayer);
                        intent.putExtra("game_type", gameType);
                        intent.putExtra("player_type", playerType);
                        intent.putExtra("version", version);
                        intent.putExtra("entry_fee", entryFee);
                        intent.putExtra("winning_prize", winningPrize);
                        intent.putExtra("slots_left", leftPlayer);
                        intent.putExtra("state_a", stateA);
                        intent.putExtra("state_b", stateB);
                        intent.putExtra("state_c", stateC);
                        intent.putExtra("state_d", stateD);
                        intent.putExtra("state_e", stateE);
                        intent.putExtra("position", selectedPosition);
                        //intent.putExtra("schedule", response.matchDate + " " + response.matchTime);
                        startActivity(intent);

                        finish();

                    } else {
                        Toasty.error(getApplicationContext(), "select player position", Toasty.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void init_view() {
        backButton = findViewById(R.id.backButtonID);
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        matchID = getIntent().getStringExtra("match_id");
        title = getIntent().getStringExtra("title");
        totalPlayer = getIntent().getStringExtra("total_player");
        entryFee = getIntent().getStringExtra("entry_fee");
        version = getIntent().getStringExtra("version");
        gameType = getIntent().getStringExtra("game_type");
        playerType = getIntent().getStringExtra("player_type");
        schedule = getIntent().getStringExtra("schedule");
        winningPrize = getIntent().getStringExtra("winning_prize");
        matchState = getIntent().getStringExtra("match_state");
        leftPlayer = getIntent().getStringExtra("slots_left");

        joinNowButton = findViewById(R.id.joinNowButtonID);

        itemRecyclerView = findViewById(R.id.itemRecyclerViewID);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemRecyclerView.setItemViewCacheSize(50);
    }

    public void get_player_data(String position, Boolean aState, Boolean bState, Boolean cState, Boolean dState, Boolean eState) {
        selectedPosition = position;
        stateA = aState;
        stateB = bState;
        stateC = cState;
        stateD = dState;
        stateE = eState;
    }
}