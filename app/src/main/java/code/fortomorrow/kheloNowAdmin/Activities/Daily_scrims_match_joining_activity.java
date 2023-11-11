package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import cn.pedant.SweetAlert.SweetAlertDialog;
import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daily_scrims_match_joining_activity extends AppCompatActivity {

    EditText playerOneEditText, playerTwoEditText, playerThreeEditText, playerFourEditText, playerFiveEditText, playerSixEditText;
    String title, type, version, map, match_type;
    String entry_fee, time, winningprize, perkill;
    String gameName, joinedOrNot, match_Id, left_place, player_type, totalPlayer;
    Boolean stateA = false, stateB = false, stateC = false, stateD = false, stateE = false, stateF = false;
    TextView titleText, entryFeeText, spotLeftText, gameText, nameText, typeText;
    APIService apiService;
    String jwt_token, secret_id;
    Button joinButton, addMoneyButton;
    TextView inSufficientText, balanceText;
    SweetAlertDialog pDialog;
    ImageView backButton;

    Boolean balance_hay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_scrims_match_joining);

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
        stateA = getIntent().getBooleanExtra("a_state", false);
        stateB = getIntent().getBooleanExtra("b_state", false);
        stateC = getIntent().getBooleanExtra("c_state", false);
        stateD = getIntent().getBooleanExtra("d_state", false);
        stateE = getIntent().getBooleanExtra("e_state", false);
        stateF = getIntent().getBooleanExtra("f_state", false);

        //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();

        set_data();

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
                finish();
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player1 = playerOneEditText.getText().toString().trim();
                String player2 = playerTwoEditText.getText().toString().trim();
                String player3 = playerThreeEditText.getText().toString().trim();
                String player4 = playerFourEditText.getText().toString().trim();
                String player5 = playerFiveEditText.getText().toString().trim();
                String player6 = playerSixEditText.getText().toString().trim();

                stateA = false;
                stateB = false;
                stateC = false;
                stateD = false;
                stateE = false;
                stateF = false;

                if (TextUtils.isEmpty(player1) || TextUtils.isEmpty(player2) || TextUtils.isEmpty(player3) || TextUtils.isEmpty(player4)) {
                    if (TextUtils.isEmpty(player1)) {
                        Toasty.error(getApplicationContext(), "Please give 1st player", Toasty.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(player2)) {
                        Toasty.error(getApplicationContext(), "Please give 2nd player", Toasty.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(player3)) {
                        Toasty.error(getApplicationContext(), "Please give 3rd player", Toasty.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(player4)) {
                        Toasty.error(getApplicationContext(), "Please give 4th player", Toasty.LENGTH_SHORT).show();
                    }
                } else {
                    if (player1.length() > 15 || player2.length() > 15 || player3.length() > 15 || player4.length() > 15) {
                        Toasty.error(getApplicationContext(), "Player name should be less than 15", Toasty.LENGTH_SHORT).show();
                    } else {
                        if (char_count(player1) == 0 || char_count(player2) == 0 || char_count(player3) == 0 || char_count(player4) == 0) {
                            if (char_count(player1) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player1 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (char_count(player2) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player2 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (char_count(player3) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player3 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (char_count(player4) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player4 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            }
                        } else {
                            if (!TextUtils.isEmpty(player5)) {
                                if (player5.length() > 15) {
                                    Toasty.error(getApplicationContext(), "Player name should be less than 15", Toasty.LENGTH_SHORT).show();
                                } else {
                                    if (char_count(player5) == 0) {
                                        Toasty.error(getApplicationContext(), "Enter Extra Player1 GAMEID_NAME", Toasty.LENGTH_SHORT).show();
                                    } else {
                                        join_func(player1, player2, player3, player4, player5, player6);
                                    }
                                }
                            } else if (!TextUtils.isEmpty(player6)) {
                                if (player_type.length() > 15) {
                                    Toasty.error(getApplicationContext(), "Player name should be less than 15", Toasty.LENGTH_SHORT).show();
                                } else {
                                    if (char_count(player6) == 0) {
                                        Toasty.error(getApplicationContext(), "Enter Extra Player2 GAMEID_NAME", Toasty.LENGTH_SHORT).show();
                                    } else {
                                        join_func(player1, player2, player3, player4, player5, player6);
                                    }
                                }
                            } else {
                                join_func(player1, player2, player3, player4, player5, player6);
                            }


                        }
                    }

                }
            }
        });
    }

    private void join_func(String player1, String player2, String player3, String player4, String player5, String player6) {
        if (!TextUtils.isEmpty(player1) && (player1.equals(player2) || player1.equals(player3) || player1.equals(player4) || player1.equals(player5) || player1.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (!TextUtils.isEmpty(player2) && (player2.equals(player1) || player2.equals(player3) || player2.equals(player4) || player2.equals(player5) || player2.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (!TextUtils.isEmpty(player3) && (player3.equals(player1) || player3.equals(player2) || player3.equals(player4) || player3.equals(player5) || player3.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (!TextUtils.isEmpty(player4) && (player4.equals(player1) || player4.equals(player2) || player4.equals(player3) || player4.equals(player5) || player4.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (!TextUtils.isEmpty(player5) && (player5.equals(player1) || player5.equals(player2) || player5.equals(player3) || player5.equals(player4) || player5.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (!TextUtils.isEmpty(player6) && (player6.equals(player1) || player6.equals(player2) || player6.equals(player3) || player6.equals(player4) || player6.equals(player5))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else {

            if (!TextUtils.isEmpty(player1)) {
                stateA = true;

                //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            }
            if (!TextUtils.isEmpty(player2)) {
                stateB = true;
            }
            if (!TextUtils.isEmpty(player3)) {
                stateC = true;
            }
            if (!TextUtils.isEmpty(player4)) {
                stateD = true;
            }
            if (!TextUtils.isEmpty(player5)) {
                stateE = true;
            }
            if (!TextUtils.isEmpty(player6)) {
                stateF = true;
            }

            //Log.d("infoxx", match_Id + " " + player1 + " " + " " + player2 + " " + player3 + " " + player4 + " " + player5 + " " + player6 + " " + String.valueOf(stateA) + " " + String.valueOf(stateB) + " " + String.valueOf(stateC) + " " + String.valueOf(stateD) + " " + String.valueOf(stateE) + " " + String.valueOf(stateF));
            pDialog.show();
            //Toasty.success(getApplicationContext(), "Success").show();

            apiService.newScrimsMatchEntryResponse(secret_id, jwt_token, match_Id, stateA.toString(), stateB.toString(), stateC.toString(), stateD.toString(), player1, player2, player3, player4, stateE.toString(), player5, stateF.toString(), player6).enqueue(new Callback<SorkariResponse>() {
                @Override
                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                    //Log.d("responsexx", String.valueOf(response.body().getE()));
                    pDialog.dismiss();
                    if (response.body().getE() == 0) {
                        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForMatch" + match_Id);
                        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForRefund" + secret_id);

                        joinButton.setText("Joined");
                        joinButton.setClickable(false);

                        final SweetAlertDialog pDialog = new SweetAlertDialog(Daily_scrims_match_joining_activity.this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("Success...")
                                .setContentText("Thanks for join!");
                        pDialog.show();
                        pDialog.setCancelable(false);

                        pDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {


                                pDialog.setConfirmClickListener(null);
                                pDialog.cancel();

                                //startActivity(new Intent(getApplicationContext(), DailyMatchActivity.class));
                                finish();


                            }
                        });
                    } else {
                        Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                        //Toasty.error(getApplicationContext(), "response.body().getM()", Toasty.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                    pDialog.dismiss();
                    Log.d("errorxx", t.getMessage());
                }
            });
        }

    }

    private void set_data() {
        titleText.setText(title);
        entryFeeText.setText("Match Entry Fee: ৳" + entry_fee);
        spotLeftText.setText(left_place + " Slots left");
        gameText.setText("আপনার গেম আইডি লেভেল ৪০+ হতে হবে");
        nameText.setText("Enter your exact Free Fire Name");
        typeText.setText("SCRIMS Registration");


    }

    private void init_view() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        titleText = findViewById(R.id.titleTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);
        spotLeftText = findViewById(R.id.spotLeftTextID);
        gameText = findViewById(R.id.gameTextId);
        typeText = findViewById(R.id.typeTextID);
        nameText = findViewById(R.id.nameTextID);
        balanceText = findViewById(R.id.balanceTextID);

        playerOneEditText = findViewById(R.id.playerOneEditTextID);
        playerTwoEditText = findViewById(R.id.playerTwoEditTextID);
        playerThreeEditText = findViewById(R.id.playerThreeEditTextID);
        playerFourEditText = findViewById(R.id.playerFourEditTextID);
        playerFiveEditText = findViewById(R.id.playerFiveEditTextID);
        playerSixEditText = findViewById(R.id.playerSixEditTextID);

        joinButton = findViewById(R.id.joinButtonID);
        addMoneyButton = findViewById(R.id.addMoneyButtonID);
        inSufficientText = findViewById(R.id.inSufficientTextID);
        backButton = findViewById(R.id.backButtonID);
    }

    private int char_count(String word) {
        int wordCount = 0;
        //String word = "hill";
        for (char letter = '0'; letter <= '9'; letter++) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    wordCount++;
                }
            }
        }

        wordCount = word.length() - wordCount;
        return wordCount;
    }

    private void get_balance() {
//Integer.parseInt(entry_fee)
        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                balanceText.setText("Available balance: ৳" + String.valueOf(response.body().getM().getTotalBalance()));
                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(entry_fee)) {
                    inSufficientText.setVisibility(View.VISIBLE);
                    addMoneyButton.setVisibility(View.VISIBLE);
                    joinButton.setVisibility(View.GONE);
                    //int a_b = response.body().getM().getTotalBalance();
                    //                    int e_f = Integer.parseInt(entry_fee)*playerCount;
                    //                    Toast.makeText(getApplicationContext(), String.valueOf(e_f - a_b), Toast.LENGTH_SHORT).show();
                } else {
                    balance_hay = true;
                    checkAll();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void checkAll() {
        if (balance_hay) {
            joinButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}