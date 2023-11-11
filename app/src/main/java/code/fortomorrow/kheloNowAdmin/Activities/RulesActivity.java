package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.JoinedPlayerAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.CheckJoinTeam.Check_join_team_response;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.Rules.Rules_response;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.Model.getRoomIdPass.GetRoomIdResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RulesActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mType, mVersion, mMap, mMatchType, mEntryFee, mMatchSchedule, mWinningPrize, mPerKill, playerTypeText, mTitle, seeallParti, rulesText;
    private ImageView mImage;
    AppCompatButton joinNowButton;
    private RecyclerView joinedPlayerRV;
    private LinearLayout mLoadParticipants;
    private Context context;
    private APIService apiService;
    private String jwt_token, secret_id;
    private String match_id;
    private String total_player, title, type, version, map, match_type, entry_fee, time, winningprize, perkill, gameName, player_type, joinedOrNot, match_Id, left_place;

    private static Tracker mTracker;
    ImageView backButton;
    TextView inSufficientText;
    AppCompatButton addMoneyButton;
    private List<Check_join_team_response.M> playerList;
    Session_management session_management;

    String gameID, ruleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        init_view();
        //seeallParti = findViewById(R.id.seeallParti);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        //Log.d("gameIDX", match_id);

        joinNowButton.setEnabled(false);
        joinNowButton.setText("");

        setText();
        getJoinedPlayer();
        get_room_id_pass_alert();
        get_balance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WalletActivity.class));
            }
        });

        if (gameID.equals("1")) {
            ruleType = "freefire_regular";
        } else if (gameID.equals("2")) {
            ruleType = "cs_regular";
        } else if (gameID.equals("3")) {
            ruleType = "ludo_regular";
        } else if (gameID.equals("4")) {
            ruleType = "ludo_grand";
        } else if (gameID.equals("5")) {
            ruleType = "cs_grand";
        } else if (gameID.equals("6")) {
            ruleType = "tournament";
        } else if (gameID.equals("7")) {
            ruleType = "ludo_quick";
        } else if (gameID.equals("8")) {
            ruleType = "ludo_4player";
        } else if (gameID.equals("9")) {
            ruleType = "freefire_premium";
        } else if (gameID.equals("10")) {
            ruleType = "freefire_grand";
        }

        getRules(ruleType);
    }

    private void get_room_id_pass_alert() {
        apiService.getRoomIdPassResponse(secret_id, jwt_token, match_id).enqueue(new Callback<GetRoomIdResponse>() {
            @Override
            public void onResponse(Call<GetRoomIdResponse> call, Response<GetRoomIdResponse> response) {
                //Log.d("responseRoom",new Gson().toJson(response.body()));
                //Log.d("responseRoom",title);
                if (response.body().getE() == 0 && !response.body().getM().getRoomId().isEmpty()) {
                    //AlertDialog.Builder mBuilder = new AlertDialog.Builder(RulesActivity.this);
                    //View mView = getLayoutInflater().inflate(R.layout.custom_room_details_dialog, null);

                    TextView mRoomId = findViewById(R.id.mRoomId);
                    TextView mPassword = findViewById(R.id.mPassword);

                    /*mBuilder.setView(mView);
                    final AlertDialog dialog1 = mBuilder.create();
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog1.show();*/

                    mRoomId.setText(" " + response.body().getM().getRoomId());
                    mPassword.setText(" " + response.body().getM().getRoomPass());


                    mRoomId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("", response.body().getM().getRoomId());
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(RulesActivity.this, "Room ID copied", Toast.LENGTH_SHORT).show();

                        }
                    });

                    mPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("", response.body().getM().getRoomPass());
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(RulesActivity.this, "Password copied", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetRoomIdResponse> call, Throwable t) {

            }
        });

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

    private void getJoinedPlayer() {

        //Toast.makeText(getApplicationContext(), match_id, Toast.LENGTH_SHORT).show();

        if (type.equals("Daily Scrims(Tournament)")) {
            apiService.getScrimsJoinTeamPlayer(secret_id, jwt_token, match_id).enqueue(new Callback<Check_join_team_response>() {
                @Override
                public void onResponse(Call<Check_join_team_response> call, Response<Check_join_team_response> response) {
                    playerList = new ArrayList<>();
                    playerList = response.body().getM();
                    JoinedPlayerAdapter joinedPlayerAdapter = new JoinedPlayerAdapter(playerList, getApplicationContext());
                    joinedPlayerRV.setAdapter(joinedPlayerAdapter);
                }

                @Override
                public void onFailure(Call<Check_join_team_response> call, Throwable t) {

                }
            });
        } else {
            apiService.get_joined_player_list(secret_id, jwt_token, match_id).enqueue(new Callback<Check_join_team_response>() {
                @Override
                public void onResponse(Call<Check_join_team_response> call, Response<Check_join_team_response> response) {
                    playerList = new ArrayList<>();
                    playerList = response.body().getM();
                    JoinedPlayerAdapter joinedPlayerAdapter = new JoinedPlayerAdapter(playerList, getApplicationContext());
                    joinedPlayerRV.setAdapter(joinedPlayerAdapter);
                }

                @Override
                public void onFailure(Call<Check_join_team_response> call, Throwable t) {

                }
            });
        }


    }

    private void get_balance() {

        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                //balanceText.setText("Available balance: ৳" + response.body().getM().getTotalBalance());
                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(entry_fee)) {
                    inSufficientText.setVisibility(View.VISIBLE);
                    addMoneyButton.setVisibility(View.VISIBLE);
                    joinNowButton.setVisibility(View.GONE);
                } else {
                    joinNowButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }


    void init_view() {
        match_id = getIntent().getStringExtra("match_id");
        EasySharedPref.init(getApplicationContext());

        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        context = getApplicationContext();
        apiService = AppConfig.getRetrofit().create(APIService.class);

        Log.d("dataxx", secret_id + " " + jwt_token + " " + match_id);


        joinedPlayerRV = findViewById(R.id.joinedPlayerRV);
        joinedPlayerRV.setHasFixedSize(true);
        joinedPlayerRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rulesText = findViewById(R.id.rulesTextID);

        mType = findViewById(R.id.mType);
        mVersion = findViewById(R.id.mVersion);
        mMap = findViewById(R.id.mMap);
        mMatchType = findViewById(R.id.mMatchType);
        mEntryFee = findViewById(R.id.mEntryFee);
        mMatchSchedule = findViewById(R.id.mMatchSchedule);
        mWinningPrize = findViewById(R.id.mWinnerPrize);
        mPerKill = findViewById(R.id.mPerKill);
        mTitle = findViewById(R.id.mTitle);
        mImage = findViewById(R.id.mImage);
        playerTypeText = findViewById(R.id.playerTypeTextID);
        mLoadParticipants = findViewById(R.id.mLoadParticipants);
        mLoadParticipants.setOnClickListener(this);

        backButton = findViewById(R.id.backButtonID);
        joinNowButton = findViewById(R.id.joinNowButtonID);
        joinNowButton.setOnClickListener(this);

        inSufficientText = findViewById(R.id.inSufficientTextID);
        addMoneyButton = findViewById(R.id.addMoneyButtonID);


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        session_management = new Session_management(getApplicationContext());
        gameID = session_management.getUserID();


    }


    void setText() {
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
        total_player = getIntent().getStringExtra("total_player");

        if (joinedOrNot.contains("true")) {
            joinNowButton.setEnabled(false);
            joinNowButton.setText("JOINED");
        } else if (Integer.parseInt(left_place) <= 0) {
            joinNowButton.setEnabled(false);
            joinNowButton.setText("CLOSE");
        } else {
            joinNowButton.setEnabled(true);
            joinNowButton.setText("JOIN NOW");
        }
        mTitle.setText(" " + title);
        mType.setText(" " + type);
        mVersion.setText(" " + version);
        mMap.setText("" + map);
        mMatchType.setText(" " + match_type);
        mEntryFee.setText(" ৳" + entry_fee);
        mMatchSchedule.setText(" " + time);
        mWinningPrize.setText(" ৳" + winningprize);
        mPerKill.setText(" ৳" + perkill);

        if (player_type.toUpperCase().equals("SOLO & DUO")) {
            playerTypeText.setText("DUO");
        } else if (player_type.toUpperCase().equals("DUO & SQUAD")) {
            playerTypeText.setText("SQUAD");
        } else if (player_type.toUpperCase().equals("SOLO & DUO & SQUAD")) {
            playerTypeText.setText("SQUAD");
        } else if (player_type.toUpperCase().equals("SOLO & SQUAD")) {
            playerTypeText.setText("SQUAD");
        } else {
            playerTypeText.setText(player_type);

        }


    }


    @Override
    public void onClick(View view) {

        if (view == mLoadParticipants) {

            //seeallParti.setText("Load Again");
            getJoinedPlayer();
            //Toast.makeText(getApplicationContext(), "refreshing", Toast.LENGTH_SHORT).show();
            //joinedPlayerRV.setVisibility(View.VISIBLE);

        }

        if (view == joinNowButton) {
            /*Intent i = new Intent(getApplicationContext(), JoinMatchActivity.class);
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
            i.putExtra("player_type", player_type);
            i.putExtra("total_player", total_player);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            //Log.d("type", type);
            startActivity(i);*/

            String text = joinNowButton.getText().toString();
            //Toast.makeText(holder.itemView.getContext(), String.valueOf(m.get(position).getMatchId()), Toast.LENGTH_SHORT).show();

            if (text.equals("JOINED")) {

                Toasty.error(context, "You are joined").show();
            } else if (text.toLowerCase().equals("join now")) {

                if (type.equals("Daily Scrims(Tournament)")) {
                    Intent i = new Intent(context, Daily_scrims_match_joining_activity.class);
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
                    i.putExtra("player_type", player_type);
                    i.putExtra("total_player", total_player);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(i);

                } else {
                    if (playerTypeText.getText().toString().toLowerCase().equals("solo")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        Intent i = new Intent(context, Free_fire_solo_match_join_activity.class);
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
                        i.putExtra("player_type", player_type);
                        i.putExtra("total_player", total_player);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);

                    } else if (playerTypeText.getText().toString().toLowerCase().equals("duo")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_duo_match_join_activity.class);
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
                        i.putExtra("player_type", player_type);
                        i.putExtra("total_player", total_player);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    } else if (playerTypeText.getText().toString().toLowerCase().equals("squad")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_squad_match_join_activity.class);
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
                        i.putExtra("player_type", player_type);
                        i.putExtra("total_player", total_player);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    } else if (playerTypeText.getText().toString().equals("6 VS 6")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_six_vs_six_match_join_activity.class);
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
                        i.putExtra("player_type", player_type);
                        i.putExtra("total_player", total_player);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    } else if (playerTypeText.getText().toString().equals("1 VS 1")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_solo_match_join_activity.class);
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
                        i.putExtra("player_type", player_type);
                        i.putExtra("total_player", total_player);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    }
                }


            }

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();


    }


}
