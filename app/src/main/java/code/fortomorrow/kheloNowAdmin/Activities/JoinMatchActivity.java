
package code.fortomorrow.kheloNowAdmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinMatchActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView gameId;
    String entry;
    TextView mTitle, mAvailableBalance, mMatchEntryFeePerson, mTeamEntrFee, mSpotLeft;
    private boolean duoOnly = false;
    private boolean squadOnly = false;
    TextView mTeamEntryFeeLayer;
    RadioGroup radioGroup;

    RadioButton rd1, rd2, rd3;

    TextView mTypeRegistration, mQute, name;

    EditText playerOne, playerTwo, playerThree, playerFour;

    TextView mInSufficientText;
    Button mAddMoney, mJoin;
    String ref_coin;
    ImageView back;
    private boolean balance_hay = false;
    static String key, child;
    static int total_player;
    String typeForJoin = "";
    View vieew;

    SweetAlertDialog pDialog;
    static boolean conditon = true;

    private static String cost = "";
    private String passwordUser;
    private String left_place;
    String uid;
    private APIService apiService;
    private String jwt_token, secret_id;
    private String title, type, version, map, match_type, entry_fee, time, winningprize, perkill, gameName, joinedOrNot, match_Id, player_type;

    private static Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_match);
        gameId = findViewById(R.id.gameId);
        gameId.setText("আপনার গেম আইডি লেভেল ৪০+ হতে হবে");
        child = getIntent().getStringExtra("type");  // Pubg match get

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        init();
        getbalance();
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
        mMatchEntryFeePerson.setText("Match Entry Fee : " + entry_fee);
        mTitle.setText(title);

        if (left_place != null) {


            long a = Integer.parseInt(left_place);
            long balance = Integer.parseInt(entry_fee);

            if (a < 0) {
                mSpotLeft.setText(String.valueOf("0 Slots left"));

            } else {
                mSpotLeft.setText(String.valueOf(a + " Slots left"));
                if (a == 1) {

                    playerTwo.setVisibility(View.GONE);
                    rd2.setVisibility(View.GONE);
                    rd3.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);

                }
                if (a == 2) {

                    rd3.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);
                }

            }


        } else {

            mSpotLeft.setText(String.valueOf(left_place + " Slots left"));


        }
        checkAll();

        Thread t2 = new Thread() {

            @Override
            public void run() {


                setMatchDetails();
                choose();

            }
        };
        t2.start();
        mAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                finish();
            }
        });

        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(JoinMatchActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                final String player1 = playerOne.getText().toString().trim();
                final String player2 = playerTwo.getText().toString().trim();
                final String player3 = playerThree.getText().toString().trim();
                final String player4 = playerFour.getText().toString().trim();

                if (rd1.isChecked()) {
                    if (TextUtils.isEmpty(player1)) {
                        Toast.makeText(getApplicationContext(), "Player1 empty", Toast.LENGTH_SHORT).show();
                    } else {
                        if (player1.length() > 15) {
                            Toast.makeText(getApplicationContext(), "Player should be in-between 13 and 15", Toast.LENGTH_SHORT).show();
                        } else {
                            //join_func(player1, player2, player3, player4);

                            if (char_count(player1) == 0) {
                                Toast.makeText(getApplicationContext(), "Enter your GAMEID_NAME", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(getApplicationContext(), String.valueOf(char_count(player1)), Toast.LENGTH_SHORT).show();
                                join_func(player1, player2, player3, player4);
                            }
                        }


                    }
                } else if (rd2.isChecked()) {
                    //Toast.makeText(getApplicationContext(), "RD2", Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(player1) || TextUtils.isEmpty(player2)) {
                        if (TextUtils.isEmpty(player1)) {
                            Toast.makeText(getApplicationContext(), "Player1 empty", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(player2)) {
                            Toast.makeText(getApplicationContext(), "Player2 empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (player1.length() > 15 || player2.length() > 15) {
                            Toast.makeText(getApplicationContext(), "Player should be in-between 13 and 15", Toast.LENGTH_SHORT).show();
                        } else {
                            if (char_count(player1) == 0 || char_count(player2) == 0) {
                                if (char_count(player1) == 0) {
                                    Toast.makeText(getApplicationContext(), "Enter Player1 GAMEID_NAME", Toast.LENGTH_SHORT).show();
                                } else if (char_count(player2) == 0) {
                                    Toast.makeText(getApplicationContext(), "Enter Player2 GAMEID_NAME", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                //Toast.makeText(getApplicationContext(), String.valueOf(char_count(player1) + " " + String.valueOf(char_count(player2))), Toast.LENGTH_SHORT).show();
                                join_func(player1, player2, player3, player4);
                            }
                        }

                    }
                } else if (rd3.isChecked()) {
                    if (TextUtils.isEmpty(player1) || TextUtils.isEmpty(player2) || TextUtils.isEmpty(player3) || TextUtils.isEmpty(player4)) {
                        if (TextUtils.isEmpty(player1)) {
                            Toast.makeText(getApplicationContext(), "Player1 empty", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(player2)) {
                            Toast.makeText(getApplicationContext(), "Player2 empty", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(player3)) {
                            Toast.makeText(getApplicationContext(), "Player3 empty", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(player4)) {
                            Toast.makeText(getApplicationContext(), "Player4 empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (player1.length() > 15 || player2.length() > 15 || player3.length() > 15 || player4.length() > 15) {
                            Toast.makeText(getApplicationContext(), "Player should be in-between 13 and 15", Toast.LENGTH_SHORT).show();
                        } else {
                            if (char_count(player1) == 0 || char_count(player2) == 0 || char_count(player3) == 0 || char_count(player4) == 0) {
                                if (char_count(player1) == 0) {
                                    Toast.makeText(getApplicationContext(), "Enter Player1 GAMEID_NAME", Toast.LENGTH_SHORT).show();
                                } else if (char_count(player2) == 0) {
                                    Toast.makeText(getApplicationContext(), "Enter Player2 GAMEID_NAME", Toast.LENGTH_SHORT).show();

                                } else if (char_count(player3) == 0) {
                                    Toast.makeText(getApplicationContext(), "Enter Player3 GAMEID_NAME", Toast.LENGTH_SHORT).show();

                                } else if (char_count(player4) == 0) {
                                    Toast.makeText(getApplicationContext(), "Enter Player4 GAMEID_NAME", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                //Toast.makeText(getApplicationContext(), String.valueOf(char_count(player1) + " " + String.valueOf(char_count(player2))), Toast.LENGTH_SHORT).show();
                                join_func(player1, player2, player3, player4);
                            }
                        }

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "select between type (SOLO/DUO/SQUAD)", Toast.LENGTH_SHORT).show();
                }


            }
        });

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

       /* for (int k = 0; k < word.length(); k++) {

            if (Character.isUpperCase(word.charAt(k))) {
                wordCount++;
            } else if (Character.isLowerCase(word.charAt(k))) {
                wordCount++;
            }
        }*/
        wordCount = word.length() - wordCount;
        return wordCount;
    }

    private void join_func(String player1, String player2, String player3, String player4) {
        if (playerOne.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player1)) {
            Toasty.error(getApplicationContext(), "Please give player One").show();
        } else if (playerTwo.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player2)) {
            Toasty.error(getApplicationContext(), "Please give player Two").show();
        } else if (playerTwo.getVisibility() == View.VISIBLE && player1.equals(player2)) {
            Toasty.error(getApplicationContext(), "Please name Can't Same").show();
        } else if (playerThree.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player3)) {
            Toasty.error(getApplicationContext(), "Please give player Three").show();
        } else if (playerThree.getVisibility() == View.VISIBLE && (player1.equals(player2) || player1.equals(player3) || player2.equals(player3))) {
            Toasty.error(getApplicationContext(), "Please name Can't Same").show();
        } else if (playerFour.getVisibility() == View.VISIBLE && TextUtils.isEmpty(player4)) {
            Toasty.error(getApplicationContext(), "Please give player Four").show();
        } else if (playerFour.getVisibility() == View.VISIBLE && (player1.equals(player2) || player1.equals(player3) || player1.equals(player4) ||
                player2.equals(player3) || player2.equals(player4) || player3.equals(player4))) {
            Toasty.error(getApplicationContext(), "Please name Can't Same").show();
        } else {
            pDialog.show();
            //Toast.makeText(getApplicationContext(), "Hurray", Toast.LENGTH_SHORT).show();
            apiService.getJoinMatchResponse(secret_id, jwt_token, match_Id, player1, player2, player3, player4).enqueue(new Callback<SorkariResponse>() {
                @Override
                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                    pDialog.dismiss();
                    if (response.body().getE() == 0) {
                        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForMatch" + match_Id);
                        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForRefund" + secret_id);

                        mJoin.setText("Joined");
                        mJoin.setClickable(false);

                        final SweetAlertDialog pDialog = new SweetAlertDialog(JoinMatchActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("Success...")
                                .setContentText("Thanks for join!");
                        pDialog.show();

                        pDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {


                                pDialog.setConfirmClickListener(null);


                                pDialog.cancel();
                                finish();


                            }
                        });
                    } else if (response.body().getE() == 1) {
                        Toasty.error(getApplicationContext(), "Slow Internet, try again", Toasty.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (response.body().getE() == 2) {
                        Toasty.error(getApplicationContext(), "এই ম্যাচ ফুল হয়ে গেসে মামা, পরের ম্যাচে আসেন ! খেলা হবে ! ", Toasty.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (response.body().getE() == 3) {
                        Toasty.error(getApplicationContext(), "আপনি ব্লকড হয়ে আছেন ! ", Toasty.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (response.body().getE() == 4) {
                        Toasty.error(getApplicationContext(), "এই ম্যাচ শেষ", Toasty.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (response.body().getE() == 6) {
                        Toasty.error(getApplicationContext(), "Already Joined", Toasty.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (response.body().getE() == 7) {
                        Toasty.error(getApplicationContext(), "Insufficient space", Toasty.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else {
                        Toasty.error(getApplicationContext(), "Insufficient Balance", Toasty.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SorkariResponse> call, Throwable t) {

                }
            });
        }
    }


    void init() {
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setCancelable(false);

        mTitle = findViewById(R.id.mTitle);
        mAvailableBalance = findViewById(R.id.mBalance);
        mMatchEntryFeePerson = findViewById(R.id.mEntrFeePerson);
        mTeamEntrFee = findViewById(R.id.mTeamEntryFee);
        mTeamEntryFeeLayer = findViewById(R.id.mTeamEntryFeeLayer);
        mSpotLeft = findViewById(R.id.mSpotLeft);

        radioGroup = findViewById(R.id.radioGroup);

        playerOne = findViewById(R.id.playerOne);
        playerTwo = findViewById(R.id.playerTwo);
        playerThree = findViewById(R.id.playerThree);
        playerFour = findViewById(R.id.playerFour);

        back = findViewById(R.id.back);

        mTypeRegistration = findViewById(R.id.mTypeRegistration);
        mQute = findViewById(R.id.mQute);
        name = findViewById(R.id.name);

        mInSufficientText = findViewById(R.id.mInSufficientText);
        mAddMoney = findViewById(R.id.mAddMoney);
        mAddMoney.setOnClickListener(this);
        mJoin = findViewById(R.id.mJoin);
        mJoin.setOnClickListener(this);
        back.setOnClickListener(this);

        vieew = findViewById(R.id.vieew);

        rd1 = findViewById(R.id.rd1);
        rd2 = findViewById(R.id.rd2);
        rd3 = findViewById(R.id.rd3);

        playerOne.setVisibility(View.GONE);
        playerTwo.setVisibility(View.GONE);
        playerThree.setVisibility(View.GONE);
        playerFour.setVisibility(View.GONE);

        rd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerTwo.setText("");
                playerThree.setText("");
                playerFour.setText("");

            }
        });


    }


    void getbalance() {


        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                mAvailableBalance.setText("Available balance: ৳" + response.body().getM().getTotalBalance());
                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(entry_fee)) {
                    mInSufficientText.setVisibility(View.VISIBLE);
                    mAddMoney.setVisibility(View.VISIBLE);
                    mJoin.setVisibility(View.GONE);
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
        if (balance_hay == true) {
            mJoin.setVisibility(View.VISIBLE);
        }

        if (!left_place.equals("")) {

            total_player = Integer.valueOf(left_place);
            entry = entry_fee;

            mMatchEntryFeePerson.setText("Match Entry Fee: ৳" + entry);

            int duo = Integer.valueOf(entry) * 2;

            mTeamEntrFee.setText(String.valueOf(duo));
            type = player_type;

            if (type.toUpperCase().equals("SQUAD")) {
                int sqadfee = Integer.valueOf(entry) * 4;
                squadOnly = true;
                mTeamEntrFee.setText(String.valueOf(sqadfee));
                mTeamEntrFee.setVisibility(View.VISIBLE);
                mTeamEntryFeeLayer.setVisibility(View.VISIBLE);
                mTypeRegistration.setText("SQUAD Registration");

                radioGroup.setVisibility(View.VISIBLE);

                playerOne.setVisibility(View.VISIBLE);
                playerTwo.setVisibility(View.VISIBLE);
                playerThree.setVisibility(View.VISIBLE);
                playerFour.setVisibility(View.VISIBLE);
                rd2.setVisibility(View.GONE);
                rd1.setVisibility(View.GONE);
                rd3.setVisibility(View.GONE);
                if (mSpotLeft.getText().toString().equals("1 Slots left")) {
                    playerOne.setVisibility(View.GONE);
                    playerTwo.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);

                }
                if (mSpotLeft.getText().toString().equals("2 Slots left")) {
                    playerOne.setVisibility(View.GONE);
                    playerTwo.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);

                }
                if (mSpotLeft.getText().toString().equals("3 Slots left")) {
                    playerOne.setVisibility(View.GONE);
                    playerTwo.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);

                }

            } else if (type.toUpperCase().equals("SOLO & DUO")) {

                radioGroup.setVisibility(View.VISIBLE);

                playerOne.setVisibility(View.GONE);
                playerTwo.setVisibility(View.GONE);
                rd1.setVisibility(View.VISIBLE);
                rd2.setVisibility(View.VISIBLE);
                rd3.setVisibility(View.GONE);
                mTypeRegistration.setText("SOLO & DUO Registration");
                if (mSpotLeft.getText().toString().equals("1 Slots left")) {

                    playerTwo.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);
                    rd2.setVisibility(View.GONE);

                }


            } else if (type.toUpperCase().equals("DUO & SQUAD")) {

                mTypeRegistration.setText("DUO & SQUAD Registration");
                radioGroup.setVisibility(View.VISIBLE);
                playerOne.setVisibility(View.GONE);
                playerTwo.setVisibility(View.GONE);
                playerThree.setVisibility(View.GONE);
                playerFour.setVisibility(View.GONE);

                rd1.setVisibility(View.GONE);
                rd2.setVisibility(View.VISIBLE);
                rd3.setVisibility(View.VISIBLE);
                if (mSpotLeft.getText().toString().equals("1 Slots left")) {

                    playerTwo.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);
                    rd2.setVisibility(View.GONE);
                    rd3.setVisibility(View.GONE);

                }
                if (mSpotLeft.getText().toString().equals("2 Slots left")) {

                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);
                    rd3.setVisibility(View.GONE);

                }
                rd3.setVisibility(View.VISIBLE);


            } else if (type.toUpperCase().equals("SOLO & DUO & SQUAD")) {

                radioGroup.setVisibility(View.VISIBLE);

                mTypeRegistration.setText("SOLO & DUO & SQUAD Registration");


                rd1.setVisibility(View.VISIBLE);
                rd2.setVisibility(View.VISIBLE);
                rd3.setVisibility(View.VISIBLE);
                if (mSpotLeft.getText().toString().equals("2 Slots left")) {

                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);
                    rd3.setVisibility(View.GONE);

                }
                if (mSpotLeft.getText().toString().equals("1 Slots left")) {

                    playerTwo.setVisibility(View.GONE);
                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);
                    rd2.setVisibility(View.GONE);
                    rd3.setVisibility(View.GONE);

                }
                if (mSpotLeft.getText().toString().equals("3 Slots left")) {

                    playerThree.setVisibility(View.GONE);
                    playerFour.setVisibility(View.GONE);
                    rd3.setVisibility(View.GONE);

                }


            } else if (type.toUpperCase().equals("DUO")) {
                duoOnly = true;
                radioGroup.setVisibility(View.GONE);
                mTeamEntrFee.setVisibility(View.VISIBLE);
                mTeamEntryFeeLayer.setVisibility(View.VISIBLE);
                rd3.setVisibility(View.GONE);
                rd2.setVisibility(View.GONE);
                rd1.setVisibility(View.GONE);
                playerOne.setVisibility(View.VISIBLE);
                playerTwo.setVisibility(View.VISIBLE);
                mQute.setVisibility(View.VISIBLE);
                // playerTwo.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                mTypeRegistration.setText("Duo Registration");
                mQute.setText("This is Duo Match . You can join as Duo");


                if (mSpotLeft.getText().toString().equals("1 Slots left")) {

                    playerTwo.setVisibility(View.GONE);
                    rd2.setVisibility(View.GONE);


                }


            } else if (type.toUpperCase().equals("SOLO")) {

                rd2.setVisibility(View.GONE);
                rd1.setVisibility(View.GONE);

                mTypeRegistration.setText("Solo Registration");
                mQute.setVisibility(View.INVISIBLE);
                playerTwo.setVisibility(View.GONE);
                playerOne.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                rd1.setChecked(true);
            } else if (type.toUpperCase().equals("SOLO & SQUAD")) {

                rd2.setVisibility(View.GONE);
                rd3.setVisibility(View.VISIBLE);
                rd1.setVisibility(View.VISIBLE);

                mTypeRegistration.setText("SQUAD Registration");
                mQute.setVisibility(View.INVISIBLE);
                playerTwo.setVisibility(View.GONE);
                playerOne.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
            }


        }

    }


    void setMatchDetails() {

        if (child.equals("Free Fire(CS)")) {
            name.setText("Enter your exact Free Fire CS Name");
            playerOne.setHint("Player 1 FreeFire Name");
            playerTwo.setHint("Player 2 FreeFire Name");
            playerThree.setHint("Player 3 FreeFire Name");
            playerFour.setHint("Player 4 FreeFire Name");
        } else {

            name.setText("Enter your exact Free Fire Name");

            playerOne.setHint("Player 1 FreeFire Name");
            playerTwo.setHint("Player 2 FreeFire Name");
            playerThree.setHint("Player 3 FreeFire Name");
            playerFour.setHint("Player 4 FreeFire Name");

        }


    }


    void choose() {


        rd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                playerTwo.setVisibility(View.GONE);
                playerThree.setVisibility(View.GONE);
                playerFour.setVisibility(View.GONE);

                playerThree.setText("");
                playerFour.setText("");
                playerTwo.setText("");
                playerOne.setVisibility(View.VISIBLE);
                mTeamEntrFee.setVisibility(View.GONE);
                mTeamEntryFeeLayer.setVisibility(View.GONE);
                vieew.setVisibility(View.GONE);


            }
        });

        rd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int duoo = Integer.valueOf(entry_fee) * 2;
                mTeamEntrFee.setText(String.valueOf(duoo));

                playerTwo.setVisibility(View.VISIBLE);
                playerOne.setVisibility(View.VISIBLE);
                playerThree.setVisibility(View.GONE);
                playerFour.setVisibility(View.GONE);
                mTeamEntrFee.setVisibility(View.VISIBLE);
                mTeamEntryFeeLayer.setVisibility(View.VISIBLE);
                vieew.setVisibility(View.VISIBLE);


            }
        });
        rd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int squad = Integer.valueOf(entry_fee) * 4;
                mTeamEntrFee.setText(String.valueOf(squad));
                playerTwo.setVisibility(View.VISIBLE);
                playerOne.setVisibility(View.VISIBLE);
                playerThree.setVisibility(View.VISIBLE);
                playerFour.setVisibility(View.VISIBLE);
                mTeamEntrFee.setVisibility(View.VISIBLE);
                mTeamEntryFeeLayer.setVisibility(View.VISIBLE);
                vieew.setVisibility(View.VISIBLE);
            }
        });


    }


    @Override
    public void onClick(View v) {

    }
}













