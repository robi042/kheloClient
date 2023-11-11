package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

public class Arena_of_valor_match_joining_activity extends AppCompatActivity {

    ImageView backButton;

    String matchID, title, entryFee, version, gameType, playerType, winningPrize, schedule, matchState, totalPlayer, leftPlayer;
    String secret_id, jwt_token;
    APIService apiService;

    TextView titleText, balanceText, entryFeeText, totalEntryFeeText, spotLeftText;
    TextView typeText, positionText, inSufficientText;
    AppCompatButton addMoneyButton, joinButton;
    Boolean balance_hay;

    Boolean stateA = false, stateB = false, stateC = false, stateD = false, stateE = false;
    String selectedPosition = "";
    int playerCount = 0;

    EditText playerOneEditText, playerTwoEditText, playerThreeEditText, playerFourEditText, playerFiveEditText, extraOneText, extraTwoText;

    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_of_valor_match_joining);

        init_view();

        get_balance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Log.d("infoxx", selectedPosition + " " + String.valueOf(stateA) + " " + String.valueOf(stateB) + " " + String.valueOf(stateC) + " " + String.valueOf(stateD) + " " + String.valueOf(stateE));

        if (playerType.equals("3 VS 3")) {
            if (stateA) {
                playerOneEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateB) {
                playerTwoEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateC) {
                playerThreeEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateA && stateB && stateC) {
                extraOneText.setVisibility(View.VISIBLE);
            }
        }else if (playerType.equals("5 VS 5")) {
            if (stateA) {
                playerOneEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateB) {
                playerTwoEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateC) {
                playerThreeEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateD) {
                playerFourEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateE) {
                playerFiveEditText.setVisibility(View.VISIBLE);
                playerCount++;
            }

            if (stateA && stateB && stateC && stateD && stateE) {
                extraOneText.setVisibility(View.VISIBLE);
                extraTwoText.setVisibility(View.VISIBLE);
            }
        }

        set_text();

        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                finish();
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1 = playerOneEditText.getText().toString().trim();
                String player2 = playerTwoEditText.getText().toString().trim();
                String player3 = playerThreeEditText.getText().toString().trim();
                String player4 = playerFourEditText.getText().toString().trim();
                String player5 = playerFiveEditText.getText().toString().trim();
                String exPlayer1 = extraOneText.getText().toString().trim();
                String exPlayer2 = extraTwoText.getText().toString().trim();

                if ((playerOneEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player1)) || (playerTwoEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player2)) || (playerThreeEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player3)) || (playerFourEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player4)) || (playerFiveEditText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player5)) || extraOneText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(exPlayer1) || extraTwoText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(exPlayer2)) {
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
                    } else if (extraOneText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(exPlayer1)) {
                        Toasty.error(getApplicationContext(), "Please give Extra player 1", Toasty.LENGTH_SHORT).show();
                    } else if (extraTwoText.getVisibility() == View.VISIBLE && TextUtils.isEmpty(exPlayer2)) {
                        Toasty.error(getApplicationContext(), "Please give Extra player 2", Toasty.LENGTH_SHORT).show();
                    }
                } else {

                    if (player1.length() > 15 || player2.length() > 15 || player3.length() > 15 || player4.length() > 15 || player5.length() > 15 || exPlayer1.length() > 15 || exPlayer2.length() > 15) {
                        Toasty.error(getApplicationContext(), "Player name should be less than 15", Toasty.LENGTH_SHORT).show();
                    } else {
                        if ((playerOneEditText.getVisibility() == View.VISIBLE && char_count(player1) == 0) || (playerTwoEditText.getVisibility() == View.VISIBLE && char_count(player2) == 0) || (playerThreeEditText.getVisibility() == View.VISIBLE && char_count(player3) == 0) || (playerFourEditText.getVisibility() == View.VISIBLE && char_count(player4) == 0) || (playerFiveEditText.getVisibility() == View.VISIBLE && char_count(player5) == 0) || (extraOneText.getVisibility() == View.VISIBLE && char_count(exPlayer1) == 0) || (extraTwoText.getVisibility() == View.VISIBLE && char_count(exPlayer2) == 0)) {
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

                            } else if (extraOneText.getVisibility() == View.VISIBLE && char_count(exPlayer1) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Extra Player 1 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            } else if (extraTwoText.getVisibility() == View.VISIBLE && char_count(exPlayer2) == 0) {
                                Toasty.error(getApplicationContext(), "Enter Extra Player 2 GAMEID_NAME", Toasty.LENGTH_SHORT).show();

                            }
                        } else {
                            //Toast.makeText(getApplicationContext(), String.valueOf(char_count(player1) + " " + String.valueOf(char_count(player2))), Toast.LENGTH_SHORT).show();
                            join_func(player1, player2, player3, player4, player5, exPlayer1, exPlayer2);
                        }
                    }
                }
            }
        });
    }

    private void join_func(String player1, String player2, String player3, String player4, String player5, String exPlayer1, String exPlayer2) {
        if (playerOneEditText.getVisibility() == View.VISIBLE && (player1.equals(player2) || player1.equals(player3) || player1.equals(player4) || player1.equals(player5) || player1.equals(exPlayer1) || player1.equals(exPlayer2))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerTwoEditText.getVisibility() == View.VISIBLE && (player2.equals(player1) || player2.equals(player3) || player2.equals(player4) || player2.equals(player5) || player2.equals(exPlayer1) || player2.equals(exPlayer2))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerThreeEditText.getVisibility() == View.VISIBLE && (player3.equals(player1) || player3.equals(player2) || player3.equals(player4) || player3.equals(player5) || player3.equals(exPlayer1) || player3.equals(exPlayer2))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerFourEditText.getVisibility() == View.VISIBLE && (player4.equals(player1) || player4.equals(player2) || player4.equals(player3) || player4.equals(player5) || player4.equals(exPlayer1) || player4.equals(exPlayer2))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (playerFiveEditText.getVisibility() == View.VISIBLE && (player5.equals(player1) || player5.equals(player2) || player5.equals(player3) || player5.equals(player4) || player5.equals(exPlayer1) || player5.equals(exPlayer2))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (extraOneText.getVisibility() == View.VISIBLE && (exPlayer1.equals(player1) || exPlayer1.equals(player2) || exPlayer1.equals(player3) || exPlayer1.equals(player4) || exPlayer1.equals(player5) || exPlayer1.equals(exPlayer2))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else if (extraTwoText.getVisibility() == View.VISIBLE && (exPlayer2.equals(player1) || exPlayer2.equals(player2) || exPlayer2.equals(player3) || exPlayer2.equals(player4) || exPlayer2.equals(player5) || exPlayer2.equals(exPlayer1))) {
            Toasty.error(getApplicationContext(), "Players name Can't Same").show();
        } else {
            Log.d("infoxx", matchID + " " + selectedPosition + " " + player1 + " " + " " + player2 + " " + player3 + " " + player4 + " " + player5 + " " + exPlayer1 + " " + exPlayer2 + " " + String.valueOf(stateA) + " " + String.valueOf(stateB) + " " + String.valueOf(stateC) + " " + String.valueOf(stateD) + " " + String.valueOf(stateE));

            pDialog.show();
            apiService.arena_of_valor_match_entry(secret_id, jwt_token, matchID, selectedPosition, stateA.toString(), player1, stateB.toString(), player2, stateC.toString(), player3, stateD.toString(), player4, stateE.toString(), player5, exPlayer1, exPlayer2).enqueue(new Callback<SorkariResponse>() {
                @Override
                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                    //Log.d("responsexx", String.valueOf(response.body().getE()));
                    pDialog.dismiss();
                    if (response.body().getE() == 0) {
                        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForMatch" + matchID);
                        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForRefund" + secret_id);

                        joinButton.setText("Joined");
                        joinButton.setClickable(false);

                        final SweetAlertDialog pDialog = new SweetAlertDialog(Arena_of_valor_match_joining_activity.this, SweetAlertDialog.SUCCESS_TYPE);
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

    private void set_text() {
        titleText.setText(title);
        entryFeeText.setText("Match Entry Fee: ৳" + entryFee);
        totalEntryFeeText.setText("Total Entry Fee: ৳" + String.valueOf(Integer.parseInt(entryFee) * playerCount));
        positionText.setText("Team No " + String.valueOf(Integer.parseInt(selectedPosition) + 1));
        typeText.setText(playerType + " Registration");
        spotLeftText.setText(leftPlayer + " Slots left");
    }

    private void get_balance() {

        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                balanceText.setText("Available balance: ৳" + String.valueOf(response.body().getM().getTotalBalance()));
                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(entryFee) * playerCount) {
                    Toast.makeText(Arena_of_valor_match_joining_activity.this, String.valueOf(playerCount), Toast.LENGTH_SHORT).show();
                    inSufficientText.setVisibility(View.VISIBLE);
                    addMoneyButton.setVisibility(View.VISIBLE);
                    joinButton.setVisibility(View.GONE);

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
        leftPlayer = getIntent().getStringExtra("slots_left");
        winningPrize = getIntent().getStringExtra("winning_prize");
        matchState = getIntent().getStringExtra("match_state");
        stateA = getIntent().getBooleanExtra("state_a", false);
        stateB = getIntent().getBooleanExtra("state_b", false);
        stateC = getIntent().getBooleanExtra("state_c", false);
        stateD = getIntent().getBooleanExtra("state_d", false);
        stateE = getIntent().getBooleanExtra("state_e", false);
        selectedPosition = getIntent().getStringExtra("position");

        titleText = findViewById(R.id.titleTextID);
        balanceText = findViewById(R.id.balanceTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);
        totalEntryFeeText = findViewById(R.id.totalEntryFeeTextID);
        spotLeftText = findViewById(R.id.spotLeftTextID);
        typeText = findViewById(R.id.typeTextID);
        positionText = findViewById(R.id.positionTextID);
        inSufficientText = findViewById(R.id.inSufficientTextID);

        addMoneyButton = findViewById(R.id.addMoneyButtonID);
        joinButton = findViewById(R.id.joinButtonID);

        playerOneEditText = findViewById(R.id.playerOneEditTextID);
        playerTwoEditText = findViewById(R.id.playerTwoEditTextID);
        playerThreeEditText = findViewById(R.id.playerThreeEditTextID);
        playerFourEditText = findViewById(R.id.playerFourEditTextID);
        playerFiveEditText = findViewById(R.id.playerFiveEditTextID);
        extraOneText = findViewById(R.id.extraOneTextID);
        extraTwoText = findViewById(R.id.extraTwoTextID);
    }
}