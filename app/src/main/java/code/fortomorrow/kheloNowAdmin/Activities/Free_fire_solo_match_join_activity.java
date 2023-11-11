package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.FreeFireJoin.Solo_join_adapter;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Free_fire_solo_match_join_activity extends AppCompatActivity {

    ImageView backButton;
    RecyclerView itemRecyclerView;
    String totalPlayer, selectedPosition = "";
    Solo_join_adapter adapter;
    AppCompatButton joinNowButton;
    String title, type, version, map, match_type, entry_fee, time, winningprize, perkill, gameName, joinedOrNot, match_Id, left_place, player_type;
    Boolean stateA = false, stateB = false, stateC = false, stateD = false, stateE = false, stateF = false;
    APIService apiService;
    String jwt_token, secret_id;
    private List<Joined_player_list_response.M> joinedPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_solo_match_join);


        init_view();

        totalPlayer = getIntent().getStringExtra("total_player");
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        version = getIntent().getStringExtra("version");
        map = getIntent().getStringExtra("map");
        match_type = getIntent().getStringExtra("matchtype");
        entry_fee = getIntent().getStringExtra("entryfee");
        time = getIntent().getStringExtra("time");
        winningprize = getIntent().getStringExtra("winningprize");
        perkill = getIntent().getStringExtra("perkill");
        gameName = getIntent().getStringExtra("gameName");
        joinedOrNot = getIntent().getStringExtra("joinedOrNot");
        match_Id = getIntent().getStringExtra("match_id");
        left_place = getIntent().getStringExtra("left_place");
        player_type = getIntent().getStringExtra("player_type");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        apiService.getJoinedList(secret_id, jwt_token, match_Id).enqueue(new Callback<Joined_player_list_response>() {
            @Override
            public void onResponse(Call<Joined_player_list_response> call, Response<Joined_player_list_response> response) {
                if (response.body().getE() == 0) {
                    joinedPlayerList = new ArrayList<>();
                    joinedPlayerList = response.body().getM();

                    adapter = new Solo_join_adapter(totalPlayer, Free_fire_solo_match_join_activity.this, joinedPlayerList);
                    adapter.notifyDataSetChanged();
                    itemRecyclerView.setAdapter(adapter);
                } else {
                    Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Joined_player_list_response> call, Throwable t) {
                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });



        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(selectedPosition)) {
                    Toasty.error(getApplicationContext(), "Select a position", Toasty.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), selectedPosition, Toast.LENGTH_SHORT).show();
                    if (stateA) {
                        Intent i = new Intent(getApplicationContext(), Free_fire_joining_match_activity.class);
                        i.putExtra("gameName", gameName);
                        i.putExtra("title", title);
                        i.putExtra("type", type);
                        i.putExtra("version", version);
                        i.putExtra("map", map);
                        i.putExtra("matchtype", "Paid");
                        i.putExtra("entryfee", entry_fee);
                        i.putExtra("time", time);
                        i.putExtra("winningprize", winningprize);
                        i.putExtra("perkill", "5");
                        i.putExtra("joinedOrNot", joinedOrNot);
                        i.putExtra("match_id", match_Id);
                        i.putExtra("left_place", left_place);
                        i.putExtra("player_type", player_type.toLowerCase());
                        i.putExtra("total_player", totalPlayer);
                        i.putExtra("selected_position", selectedPosition);
                        i.putExtra("a_state", stateA);
                        i.putExtra("b_state", stateB);
                        i.putExtra("c_state", stateC);
                        i.putExtra("d_state", stateD);
                        i.putExtra("e_state", stateE);
                        i.putExtra("f_state", stateF);
                        startActivity(i);

                        finish();
                    } else {
                        Toasty.error(getApplicationContext(), "Select a position", Toasty.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void get_player_position(String position, Boolean aState) {
        selectedPosition = position;
        stateA = aState;
        //Toast.makeText(getApplicationContext(), String.valueOf(position) + " " + stateA.toString(), Toast.LENGTH_SHORT).show();
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        joinNowButton = findViewById(R.id.joinNowButtonID);
        backButton = findViewById(R.id.backButtonID);
        itemRecyclerView = findViewById(R.id.itemRecyclerViewID);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        itemRecyclerView.setItemViewCacheSize(150);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}