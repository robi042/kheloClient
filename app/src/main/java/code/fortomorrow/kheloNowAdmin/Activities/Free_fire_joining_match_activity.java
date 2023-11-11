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

public class Free_fire_joining_match_activity extends AppCompatActivity {

    ImageView backButton;
    TextView titleText, balanceText, entryFeeText, spotLeftText, gameText, typeText, nameText;

    String title, type, version, map, match_type;
    String entry_fee, time, winningprize, perkill;
    String gameName, joinedOrNot, match_Id, left_place, player_type, totalPlayer;
    String selectedPosition;

    APIService apiService;
    String jwt_token, secret_id;
    EditText playerOneEditText, playerTwoEditText, playerThreeEditText, playerFourEditText, playerFiveEditText, playerSixEditText;
    EditText exPlayerOneEditText, exPlayerTwoEditText;
    Button joinButton, addMoneyButton;
    TextView inSufficientText;

    Boolean balance_hay;
    Boolean stateA = false, stateB = false, stateC = false, stateD = false, stateE = false, stateF = false, hasExPlayer1 = false, hasExPlayer2 = false;

    SweetAlertDialog pDialog;
    TextView positionText;
    int playerCount = 0;
    TextView totalEntryFeeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_joining_match);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


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
        selectedPosition = getIntent().getStringExtra("selected_position");
        stateA = getIntent().getBooleanExtra("a_state", false);
        stateB = getIntent().getBooleanExtra("b_state", false);
        stateC = getIntent().getBooleanExtra("c_state", false);
        stateD = getIntent().getBooleanExtra("d_state", false);
        stateE = getIntent().getBooleanExtra("e_state", false);
        stateF = getIntent().getBooleanExtra("f_state", false);

        //Log.d("tokenxx", jwt_token);
        positionText.setText("Team No " + String.valueOf(Integer.parseInt(selectedPosition) + 1));

        titleText.setText(title);

        spotLeftText.setText(left_place + " Slots left");

        gameText.setText("আপনার গেম আইডি লেভেল ৪০+ হতে হবে");
        nameText.setText("Enter your exact Free Fire Name");

        if (player_type.toUpperCase().equals("SOLO & DUO")) {
            player_type = "duo";
        } else if (player_type.toUpperCase().equals("DUO & SQUAD")) {
            player_type = "squad";
        } else if (player_type.toUpperCase().equals("SOLO & DUO & SQUAD")) {
            player_type = "squad";
        } else if (player_type.toUpperCase().equals("SOLO & SQUAD")) {
            player_type = "squad";
        }

        if (player_type.equals("solo") || player_type.equals("1 vs 1")) {
            typeText.setText("Solo Registration");

            if (stateA) {
                playerOneEditText.setVisibility(View.VISIBLE);
            }
        }

        if (player_type.equals("duo")) {
            typeText.setText("Duo Registration");

            if (stateA) {
                playerOneEditText.setVisibility(View.VISIBLE);
            }
            if (stateB) {
                playerTwoEditText.setVisibility(View.VISIBLE);
            }

        }

        if (player_type.equals("squad")) {
            typeText.setText("Squad Registration");

            int count = 0;

            if (stateA) {
                count++;
                playerOneEditText.setVisibility(View.VISIBLE);
            }
            if (stateB) {
                count++;
                playerTwoEditText.setVisibility(View.VISIBLE);
            }

            if (stateC) {
                count++;
                playerThreeEditText.setVisibility(View.VISIBLE);
            }

            if (stateD) {
                count++;
                playerFourEditText.setVisibility(View.VISIBLE);
            }

            if (count == 4) {
                exPlayerOneEditText.setVisibility(View.VISIBLE);
                exPlayerTwoEditText.setVisibility(View.VISIBLE);
            }
        }

        if (player_type.equals("6 vs 6")) {
            typeText.setText("6 VS 6 Registration");

            int count = 0;

            if (stateA) {
                count++;
                playerOneEditText.setVisibility(View.VISIBLE);
            }
            if (stateB) {
                count++;
                playerTwoEditText.setVisibility(View.VISIBLE);
            }

            if (stateC) {
                count++;
                playerThreeEditText.setVisibility(View.VISIBLE);
            }

            if (stateD) {
                count++;
                playerFourEditText.setVisibility(View.VISIBLE);
            }

            if (stateE) {
                count++;
                playerFiveEditText.setVisibility(View.VISIBLE);
            }

            if (stateF) {
                count++;
                playerSixEditText.setVisibility(View.VISIBLE);
            }

            if (count == 6) {
                exPlayerOneEditText.setVisibility(View.VISIBLE);
                exPlayerTwoEditText.setVisibility(View.VISIBLE);
            }
        }


        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String player1 = playerOneEditText.getText().toString().trim();
                String player2 = playerTwoEditText.getText().toString().trim();
                String player3 = playerThreeEditText.getText().toString().trim();
                String player4 = playerFourEditText.getText().toString().trim();
                String player5 = playerFiveEditText.getText().toString().trim();
                String player6 = playerSixEditText.getText().toString().trim();

                String exPlayer1 = exPlayerOneEditText.getText().toString().trim();
                String exPlayer2 = exPlayerTwoEditText.getText().toString().trim();

                if ((playerOneEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player1)) || (playerTwoEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player2)) || (playerThreeEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player3)) || (playerFourEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player4)) || (playerFiveEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player5)) || (playerSixEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player6))) {
                    if (playerOneEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player1)) {
                        Toasty.error(getApplicationContext(), "Please give 1st player", Toasty.LENGTH_SHORT).show();
                    } else if (playerTwoEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player2)) {
                        Toasty.error(getApplicationContext(), "Please give 2nd player", Toasty.LENGTH_SHORT).show();
                    } else if (playerThreeEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player3)) {
                        Toasty.error(getApplicationContext(), "Please give 3rd player", Toasty.LENGTH_SHORT).show();
                    } else if (playerFourEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player4)) {
                        Toasty.error(getApplicationContext(), "Please give 4th player", Toasty.LENGTH_SHORT).show();
                    } else if (playerFiveEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player5)) {
                        Toasty.error(getApplicationContext(), "Please give 5th player", Toasty.LENGTH_SHORT).show();
                    } else if (playerSixEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player6)) {
                        Toasty.error(getApplicationContext(), "Please give 6th player", Toasty.LENGTH_SHORT).show();
                    } /*else if (exPlayerOneEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(exPlayer1)) {
                        Toasty.error(getApplicationContext(), "Please Extra Player One", Toasty.LENGTH_SHORT).show();
                    } else if (exPlayerTwoEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(exPlayer2)) {
                        Toasty.error(getApplicationContext(), "Please Extra Player Two", Toasty.LENGTH_SHORT).show();

                    }*/
                } else {
                    if (player1.length() > 12 || player2.length() > 12 || player3.length() > 12 || player4.length() > 12 || player5.length() > 12 || player6.length() > 12 || exPlayer1.length() > 12 || exPlayer2.length() > 12) {
                        Toasty.error(getApplicationContext(), "Player name should be less than 12", Toasty.LENGTH_SHORT).show();
                    } else {
                        if ((playerOneEditText.getVisibility() == View.VISIBLE && char_count(player1) == 0) || (playerTwoEditText.getVisibility() == View.VISIBLE && char_count(player2) == 0) || (playerThreeEditText.getVisibility() == View.VISIBLE && char_count(player3) == 0) || (playerFourEditText.getVisibility() == View.VISIBLE && char_count(player4) == 0) || (playerFiveEditText.getVisibility() == View.VISIBLE && char_count(player5) == 0) || (playerSixEditText.getVisibility() == View.VISIBLE && char_count(player6) == 0)) {
                            if (playerOneEditText.getVisibility() == View.VISIBLE && char_count(player1) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player1 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (playerTwoEditText.getVisibility() == View.VISIBLE && char_count(player2) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player2 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (playerThreeEditText.getVisibility() == View.VISIBLE && char_count(player3) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player3 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (playerFourEditText.getVisibility() == View.VISIBLE && char_count(player4) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player4 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (playerFiveEditText.getVisibility() == View.VISIBLE && char_count(player5) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player5 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (playerSixEditText.getVisibility() == View.VISIBLE && char_count(player6) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Player6 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            }
                        } else {
                            //Log.d("infoxx", "join " + match_Id + " " + selectedPosition + " " + player1 + " " + player2 + " " + player3 + " " + player4 + " " + player5 + " " + player6 + " " + exPlayer1 + " " + exPlayer2 + " " + String.valueOf(stateA) + " " + String.valueOf(stateB) + " " + String.valueOf(stateC) + " " + String.valueOf(stateD) + " " + String.valueOf(stateE) + " " + String.valueOf(stateF) + " " + String.valueOf(hasExPlayer1) + " " + String.valueOf(hasExPlayer2));

                            //Toast.makeText(getApplicationContext(), String.valueOf(char_count(player1) + " " + String.valueOf(char_count(player2))), Toast.LENGTH_SHORT).show();
                            extra_palyer_compare(player1, player2, player3, player4, player5, player6, exPlayer1, exPlayer2);
                        }
                    }

                }

            }
        });

        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                finish();
            }
        });

        if (playerOneEditText.getVisibility() == View.VISIBLE) {
            playerCount = playerCount + 1;
        }

        if (playerTwoEditText.getVisibility() == View.VISIBLE) {
            playerCount = playerCount + 1;
        }

        if (playerThreeEditText.getVisibility() == View.VISIBLE) {
            playerCount = playerCount + 1;
        }

        if (playerFourEditText.getVisibility() == View.VISIBLE) {
            playerCount = playerCount + 1;
        }

        if (playerFiveEditText.getVisibility() == View.VISIBLE) {
            playerCount = playerCount + 1;
        }

        if (playerSixEditText.getVisibility() == View.VISIBLE) {
            playerCount = playerCount + 1;
        }

        entryFeeText.setText("Match Entry Fee: ৳" + entry_fee);
        //String.valueOf(Integer.parseInt(entry_fee) * playerCount)
        totalEntryFeeText.setText("Total Entry Fee: ৳" + String.valueOf(Integer.parseInt(entry_fee) * playerCount));

        get_balance();
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

        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                balanceText.setText("Available balance: ৳" + String.valueOf(response.body().getM().getTotalBalance()));
                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(entry_fee) * playerCount) {
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

    private void init_view() {

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        playerOneEditText = findViewById(R.id.playerOneEditTextID);
        playerTwoEditText = findViewById(R.id.playerTwoEditTextID);
        playerThreeEditText = findViewById(R.id.playerThreeEditTextID);
        playerFourEditText = findViewById(R.id.playerFourEditTextID);
        playerFiveEditText = findViewById(R.id.playerFiveEditTextID);
        playerSixEditText = findViewById(R.id.playerSixEditTextID);

        exPlayerOneEditText = findViewById(R.id.extraOneTextID);
        exPlayerTwoEditText = findViewById(R.id.extraTwoTextID);

        backButton = findViewById(R.id.backButtonID);
        titleText = findViewById(R.id.titleTextID);
        balanceText = findViewById(R.id.balanceTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);
        spotLeftText = findViewById(R.id.spotLeftTextID);
        gameText = findViewById(R.id.gameTextId);
        typeText = findViewById(R.id.typeTextID);
        nameText = findViewById(R.id.nameTextID);
        totalEntryFeeText = findViewById(R.id.totalEntryFeeTextID);

        joinButton = findViewById(R.id.joinButtonID);
        addMoneyButton = findViewById(R.id.addMoneyButtonID);
        inSufficientText = findViewById(R.id.inSufficientTextID);
        positionText = findViewById(R.id.positionTextID);

    }

    private void extra_palyer_compare(String player1, String player2, String player3, String player4, String player5, String player6, String exPlayer1, String exPlayer2) {

        if (playerOneEditText.getVisibility() == View.VISIBLE && (player1.equals(player2) || player1.equals(player3) || player1.equals(player4) || player1.equals(player5) || player1.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerTwoEditText.getVisibility() == View.VISIBLE && (player2.equals(player1) || player2.equals(player3) || player2.equals(player4) || player2.equals(player5) || player2.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerThreeEditText.getVisibility() == View.VISIBLE && (player3.equals(player1) || player3.equals(player2) || player3.equals(player4) || player3.equals(player5) || player3.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerFourEditText.getVisibility() == View.VISIBLE && (player4.equals(player1) || player4.equals(player2) || player4.equals(player3) || player4.equals(player5) || player4.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerFiveEditText.getVisibility() == View.VISIBLE && (player5.equals(player1) || player5.equals(player2) || player5.equals(player3) || player5.equals(player4) || player5.equals(player6))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerSixEditText.getVisibility() == View.VISIBLE && (player6.equals(player1) || player6.equals(player2) || player6.equals(player3) || player6.equals(player4) || player6.equals(player5))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else {

            if (!TextUtils.isEmpty(exPlayer1) || !TextUtils.isEmpty(exPlayer2)) {

                if (!TextUtils.isEmpty(exPlayer1) && (exPlayer1.equals(player1) || exPlayer1.equals(player2) || exPlayer1.equals(player3) || exPlayer1.equals(player4) || exPlayer1.equals(player5) || exPlayer1.equals(player6) || exPlayer1.equals(exPlayer2))) {
                    Toasty.error(getApplicationContext(), "Players name Can't Same").show();
                } else if (!TextUtils.isEmpty(exPlayer2) && (exPlayer2.equals(player1) || exPlayer2.equals(player2) || exPlayer2.equals(player3) || exPlayer2.equals(player4) || exPlayer2.equals(player5) || exPlayer2.equals(player6) || exPlayer2.equals(exPlayer1))) {
                    Toasty.error(getApplicationContext(), "Players name Can't Same").show();
                } else {
                    if (!TextUtils.isEmpty(exPlayer1)) {
                        hasExPlayer1 = true;
                    } else {
                        hasExPlayer1 = false;
                    }

                    if (!TextUtils.isEmpty(exPlayer2)) {
                        hasExPlayer2 = true;
                    } else {
                        hasExPlayer2 = false;
                    }


                    if (!TextUtils.isEmpty(exPlayer1) && char_count(exPlayer1) == 0) {
                        Toasty.error(getApplicationContext(), "Enter Ex. Player One GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                    } else if (!TextUtils.isEmpty(exPlayer2) && char_count(exPlayer2) == 0) {
                        Toasty.error(getApplicationContext(), "Enter Ex. Player Two GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                    } else {
                        join_function(player1, player2, player3, player4, player5, player6, exPlayer1, exPlayer2, stateA, stateB, stateC, stateD, stateE, stateF, hasExPlayer1, hasExPlayer2);

                    }

                }


            } else {
                join_function(player1, player2, player3, player4, player5, player6, exPlayer1, exPlayer2, stateA, stateB, stateC, stateD, stateE, stateF, hasExPlayer1, hasExPlayer2);


            }


        }
    }

    private void join_function(String player1, String player2, String player3, String player4, String player5, String player6, String exPlayer1, String exPlayer2, Boolean stateA, Boolean stateB, Boolean stateC, Boolean stateD, Boolean stateE, Boolean stateF, Boolean hasExPlayer1, Boolean hasExPlayer2) {
        //Log.d("infoxx", match_Id + " " + selectedPosition + " " + player1 + " " + player2 + " " + player3 + " " + player4 + " " + player5 + " " + player6 + " " + exPlayer1 + " " + exPlayer2 + " " + String.valueOf(stateA) + " " + String.valueOf(stateB) + " " + String.valueOf(stateC) + " " + String.valueOf(stateD) + " " + String.valueOf(stateE) + " " + String.valueOf(stateF) + " " + String.valueOf(hasExPlayer1) + " " + String.valueOf(hasExPlayer2));

        pDialog.show();
        apiService.newMatchEntryResponse(secret_id, jwt_token, match_Id, selectedPosition, stateA.toString(), player1, stateB.toString(), player2, stateC.toString(), player3, stateD.toString(), player4, stateE.toString(), player5, stateF.toString(), player6, hasExPlayer1.toString(), exPlayer1,  hasExPlayer2.toString(), exPlayer2).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                //Log.d("responsexx", String.valueOf(response.body().getE()));
                pDialog.dismiss();
                if (response.body().getE() == 0) {
                    FirebaseMessaging.getInstance().subscribeToTopic("NotificationForMatch" + match_Id);
                    FirebaseMessaging.getInstance().subscribeToTopic("NotificationForRefund" + secret_id);

                    joinButton.setText("Joined");
                    joinButton.setClickable(false);

                    final SweetAlertDialog pDialog = new SweetAlertDialog(Free_fire_joining_match_activity.this, SweetAlertDialog.SUCCESS_TYPE);
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
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                pDialog.dismiss();
                Log.d("errorxx", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}