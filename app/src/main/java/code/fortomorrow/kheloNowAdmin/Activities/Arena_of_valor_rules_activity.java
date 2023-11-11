package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.JoinedPlayerAdapter;
import code.fortomorrow.kheloNowAdmin.Model.CheckJoinTeam.Check_join_team_response;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.Rules.Rules_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Arena_of_valor_rules_activity extends AppCompatActivity {

    String matchID, roomCode, title, entryFee, version, gameType, playerType, winningPrize, schedule, matchState, totalPlayer, leftPlayer;
    TextView playerTypeText, gameTypeText, entryFeeText, versionText;
    TextView scheduleText, gameTitleText, winningPrizeText, roomCodeText;
    Dialog loader;
    String secret_id, jwt_token;
    APIService apiService;

    TextView inSufficientText, rulesText;
    AppCompatButton addMoneyButton, joinButton;
    private List<Check_join_team_response.M> playerList;
    RecyclerView participantsListView;
    LinearLayout loadParticipantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_of_valor_rules);

        init_view();

        set_data();

        balance_check();

        registered_participants();

        room_code_func();

        getRules(gameType);

        //Toast.makeText(getApplicationContext(), String.valueOf(leftPlayer), Toast.LENGTH_SHORT).show();
        loadParticipantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registered_participants();
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (joinButton.getText().toString().trim().equals("Join Now")) {
                    if (playerType.equals("5 VS 5")) {
                        Intent intent = new Intent(getApplicationContext(), Arena_of_valor_5_vs_5_slot_activity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("match_id", matchID);
                        intent.putExtra("total_player", totalPlayer);
                        intent.putExtra("game_type", gameType);
                        intent.putExtra("player_type", playerType);
                        intent.putExtra("version", version);
                        intent.putExtra("entry_fee", entryFee);
                        intent.putExtra("winning_prize", winningPrize);
                        intent.putExtra("slots_left", String.valueOf(leftPlayer));
                        //intent.putExtra("schedule", response.matchDate + " " + matchTime);
                        startActivity(intent);
                    } else if (playerType.equals("3 VS 3")) {
                        Intent intent = new Intent(getApplicationContext(), Arena_of_valor_3_vs_3_slot_activity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("match_id", matchID);
                        intent.putExtra("total_player", totalPlayer);
                        intent.putExtra("game_type", gameType);
                        intent.putExtra("player_type", playerType);
                        intent.putExtra("version", version);
                        intent.putExtra("entry_fee", entryFee);
                        intent.putExtra("winning_prize", winningPrize);
                        intent.putExtra("slots_left", String.valueOf(leftPlayer));
                        //intent.putExtra("schedule", response.matchDate + " " + response.matchTime);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void room_code_func() {
        if (matchState.equals("JOINED")) {
            if (roomCode == null) {
                roomCodeText.setText("******");
            } else {
                roomCodeText.setText(roomCode);
            }
        } else {
            roomCodeText.setText("******");
        }
    }

    private void getRules(String ruleType) {

        //Toast.makeText(getApplicationContext(), ruleType, Toast.LENGTH_SHORT).show();
        apiService.getAllRules(secret_id, jwt_token, ruleType).enqueue(new Callback<Rules_response>() {
            @Override
            public void onResponse(Call<Rules_response> call, Response<Rules_response> response) {
                if (response.body().getE() == 0) {
                    rulesText.setText(response.body().getM().getRule());
                }
            }

            @Override
            public void onFailure(Call<Rules_response> call, Throwable t) {

            }
        });
    }

    private void registered_participants() {


        apiService.arena_of_valor_join_player_list(secret_id, jwt_token, matchID).enqueue(new Callback<Check_join_team_response>() {
            @Override
            public void onResponse(Call<Check_join_team_response> call, Response<Check_join_team_response> response) {
                playerList = new ArrayList<>();
                playerList = response.body().getM();
                JoinedPlayerAdapter joinedPlayerAdapter = new JoinedPlayerAdapter(playerList, getApplicationContext());
                participantsListView.setAdapter(joinedPlayerAdapter);
            }

            @Override
            public void onFailure(Call<Check_join_team_response> call, Throwable t) {

            }
        });
    }

    private void balance_check() {
        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                //balanceText.setText("Available balance: ৳" + response.body().getM().getTotalBalance());

                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(entryFee)) {

                    if (matchState.equals("JOIN")) {

                        inSufficientText.setVisibility(View.VISIBLE);
                        addMoneyButton.setVisibility(View.VISIBLE);
                        joinButton.setVisibility(View.GONE);

                    } else if (matchState.equals("JOINED")) {
                        joinButton.setVisibility(View.VISIBLE);
                        inSufficientText.setVisibility(View.GONE);
                        addMoneyButton.setVisibility(View.GONE);

                        joinButton.setText("JOINED");

                    } else if (matchState.equals("CLOSE")) {
                        joinButton.setVisibility(View.VISIBLE);
                        inSufficientText.setVisibility(View.GONE);
                        addMoneyButton.setVisibility(View.GONE);
                        joinButton.setText("CLOSE");
                    }
                } else {
                    joinButton.setVisibility(View.VISIBLE);
                    inSufficientText.setVisibility(View.GONE);
                    addMoneyButton.setVisibility(View.GONE);

                    if (matchState.equals("JOIN")) {

                        joinButton.setText("Join Now");

                    } else if (matchState.equals("JOINED")) {

                        joinButton.setText("JOINED");

                    } else if (matchState.equals("CLOSE")) {

                        joinButton.setText("CLOSE");
                    }

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void set_data() {
        gameTitleText.setText(title);
        playerTypeText.setText(playerType);
        gameTypeText.setText(gameType);
        entryFeeText.setText(entryFee);
        versionText.setText(version);
        scheduleText.setText(schedule);
        winningPrizeText.setText(winningPrize);
    }

    private void init_view() {
        loader = new Dialog(getApplicationContext());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

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
        roomCode = getIntent().getStringExtra("room_code");

        gameTitleText = findViewById(R.id.gameTitleTextID);
        playerTypeText = findViewById(R.id.playerTypeTextID);
        gameTypeText = findViewById(R.id.gameTypeTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);
        versionText = findViewById(R.id.versionTextID);
        scheduleText = findViewById(R.id.scheduleTextID);
        winningPrizeText = findViewById(R.id.winningPrizeTextID);
        inSufficientText = findViewById(R.id.inSufficientTextID);
        addMoneyButton = findViewById(R.id.addMoneyButtonID);
        joinButton = findViewById(R.id.joinButtonID);
        roomCodeText = findViewById(R.id.roomCodeTextID);
        rulesText = findViewById(R.id.rulesTextID);

        loadParticipantsButton = findViewById(R.id.loadParticipantsButtonID);
        participantsListView = findViewById(R.id.participantsListViewID);
        participantsListView.setHasFixedSize(true);
        participantsListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}