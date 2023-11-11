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
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_participant_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.LudoMatchParticipantList_response;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.Rules.Rules_response;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ludo_rules_activity extends AppCompatActivity {

    TextView gameTitleText, scheduleText, gameTypeText, entryFeeText, winningPrizeText;
    String gameTitle, gameEntryFee, matchID, left_spots, totalPrize, gameType, schedule, roomCode;
    TextView rulesText, inSufficientText;
    RecyclerView participantsListView;
    LinearLayout loadParticipantsButton;

    private APIService apiService;
    String jwt_token, secret_id;
    private List<LudoMatchParticipantList_response.M> participantList;
    private Ludo_participant_list_adapter adapter;
    String matchState;
    AppCompatButton addMoneyButton, joinButton;

    ImageView backButton;
    Session_management session_management;
    String gameID, ruleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ludo_rules_activity);

        init_view();

        gameTitle = getIntent().getStringExtra("game_title");
        gameEntryFee = getIntent().getStringExtra("entry_fee");
        matchID = getIntent().getStringExtra("match_id");
        left_spots = getIntent().getStringExtra("spots_left");
        totalPrize = getIntent().getStringExtra("total_prize");
        gameType = getIntent().getStringExtra("game_type");
        schedule = getIntent().getStringExtra("schedule");
        matchState = getIntent().getStringExtra("match_state");
        roomCode = getIntent().getStringExtra("room_code");

        //Toast.makeText(getApplicationContext(), matchState, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(roomCode) && matchState.equals("JOINED")) {
            //Toast.makeText(getApplicationContext(), roomCode, Toast.LENGTH_SHORT).show();
            /*Dialog roomAlert = new Dialog(Ludo_rules_activity.this);
            roomAlert.setContentView(R.layout.room_code_alert);
            roomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            roomAlert.show();

            Window window = roomAlert.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);*/

            ImageView clipBoardButton = findViewById(R.id.clipBoardButtonID);
            TextView roomCodeText = findViewById(R.id.roomCodeTextID);

            roomCodeText.setText(roomCode);

            clipBoardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("room_code", roomCode);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Room Code copied", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //Toast.makeText(getApplicationContext(), "Room code not available", Toast.LENGTH_SHORT).show();
        }


        gameTitleText.setText(gameTitle);
        scheduleText.setText(schedule);
        entryFeeText.setText(gameEntryFee);
        winningPrizeText.setText(totalPrize);
        gameTypeText.setText(gameType);

        participantsListView = findViewById(R.id.participantsListViewID);
        participantsListView.setHasFixedSize(true);
        participantsListView.setLayoutManager(new LinearLayoutManager(Ludo_rules_activity.this));

        participants_list();

        get_balance();

        loadParticipantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "refreshing", Toast.LENGTH_SHORT).show();

                participants_list();
            }
        });

        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                //finish();
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (joinButton.getText().toString().trim().equals("Join Now")) {

                    Intent intent = new Intent(Ludo_rules_activity.this, Ludo_joining_match_activity.class);
                    intent.putExtra("game_title", gameTitle);
                    intent.putExtra("match_id", String.valueOf(matchID));
                    intent.putExtra("entry_fee", gameEntryFee);
                    intent.putExtra("spots_left", String.valueOf(left_spots));
                    startActivity(intent);
                    finish();

                } else if (joinButton.getText().toString().trim().equals("JOINED")) {

                    Toasty.error(getApplicationContext(), "Already Joined", Toasty.LENGTH_SHORT).show();

                } else if (joinButton.getText().toString().trim().equals("CLOSE")) {

                    Toasty.error(getApplicationContext(), "Match close", Toasty.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        load_rules(ruleType);
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        apiService = AppConfig.getRetrofit().create(APIService.class);

        gameTitleText = findViewById(R.id.gameTitleTextID);
        scheduleText = findViewById(R.id.scheduleTextID);
        gameTypeText = findViewById(R.id.gameTypeTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);
        winningPrizeText = findViewById(R.id.winningPrizeTextID);
        rulesText = findViewById(R.id.rulesTextID);
        inSufficientText = findViewById(R.id.inSufficientTextID);


        loadParticipantsButton = findViewById(R.id.loadParticipantsButtonID);
        addMoneyButton = findViewById(R.id.addMoneyButtonID);
        joinButton = findViewById(R.id.joinButtonID);
        backButton = findViewById(R.id.backButtonID);

        session_management = new Session_management(getApplicationContext());
        gameID = session_management.getUserID();
    }

    private void load_rules(String ruleType) {
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

    private void participants_list() {
        apiService.joinLudoMatchParticipantList(secret_id, jwt_token, matchID).enqueue(new Callback<LudoMatchParticipantList_response>() {
            @Override
            public void onResponse(Call<LudoMatchParticipantList_response> call, Response<LudoMatchParticipantList_response> response) {
                if (response.body().getE() == 0) {
                    participantList = new ArrayList<>();
                    participantList = response.body().getM();
                    adapter = new Ludo_participant_list_adapter(participantList);
                    participantsListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<LudoMatchParticipantList_response> call, Throwable t) {

            }
        });
    }

    private void get_balance() {

        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                //balanceText.setText("Available balance: ৳" + response.body().getM().getTotalBalance());

                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(gameEntryFee)) {
                    inSufficientText.setVisibility(View.VISIBLE);
                    addMoneyButton.setVisibility(View.VISIBLE);
                    joinButton.setVisibility(View.GONE);
                } else {
                    joinButton.setVisibility(View.VISIBLE);
                    inSufficientText.setVisibility(View.GONE);
                    addMoneyButton.setVisibility(View.GONE);

                    if (matchState.equals("Join Now")) {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}