package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class Ludo_joining_match_activity extends AppCompatActivity {

    ImageView backButton;
    TextView gameTitleText, balanceText, entryFeeText, spotLeftText, inSufficientText;
    private APIService apiService;
    private String jwt_token, secret_id;
    String gameTitle, gameEntryFee, matchID, left_spots;
    Button addMoneyButton, joinButton;
    EditText playerNameEditText;

    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_ludo_match_activity);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButtonID);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gameTitleText = findViewById(R.id.gameTitleTextID);
        balanceText = findViewById(R.id.balanceTextID);
        entryFeeText = findViewById(R.id.entryFeeTextID);
        spotLeftText = findViewById(R.id.spotLeftTextID);
        inSufficientText = findViewById(R.id.inSufficientTextID);
        playerNameEditText = findViewById(R.id.playerNameEditTextID);

        addMoneyButton = findViewById(R.id.addMoneyButtonID);
        joinButton = findViewById(R.id.joinButtonID);

        gameTitle = getIntent().getStringExtra("game_title");
        gameEntryFee = getIntent().getStringExtra("entry_fee");
        matchID = getIntent().getStringExtra("match_id");
        left_spots = getIntent().getStringExtra("spots_left");

        gameTitleText.setText(gameTitle);
        entryFeeText.setText("Entry Fee: ৳" + gameEntryFee);
        spotLeftText.setText(left_spots + " Slots left");

        get_balance();

        loader = new Dialog(Ludo_joining_match_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);


        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String playerName = playerNameEditText.getText().toString().trim();

                if (TextUtils.isEmpty(playerName)) {
                    Toasty.error(getApplicationContext(), "Player name empty", Toasty.LENGTH_SHORT).show();
                } else {

                    //Toast.makeText(Ludo_joining_match_activity.this, String.valueOf(char_count(playerName)), Toast.LENGTH_SHORT).show();

                    if (char_count(playerName) == 0) {
                        Toasty.error(getApplicationContext(), "Enter Player GAMEID_NAME", Toasty.LENGTH_SHORT).show();
                    } else {
                        loader.show();

                        apiService.joinLudoMatch(secret_id, jwt_token, matchID, playerName).enqueue(new Callback<SorkariResponse>() {
                            @Override
                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {


                                loader.dismiss();

                                if (response.body().getE() == 0) {
                                    FirebaseMessaging.getInstance().subscribeToTopic("NotificationForLudoMatch" + matchID);
                                    FirebaseMessaging.getInstance().subscribeToTopic("NotificationForLudoRefund" + secret_id);
                                    FirebaseMessaging.getInstance().subscribeToTopic("NotificationForLudoMessage" + matchID);
                                    Toasty.success(getApplicationContext(), "Successfully joined", Toasty.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<SorkariResponse> call, Throwable t) {

                                loader.dismiss();
                                Toasty.error(getApplicationContext(), "Something wrong", Toasty.LENGTH_SHORT).show();
                                Log.d("errorxx", t.getMessage());
                            }
                        });
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
                balanceText.setText("Available balance: ৳" + response.body().getM().getTotalBalance());

                if (response.body().getM().getTotalBalance() == 0 || response.body().getM().getTotalBalance() < Integer.parseInt(gameEntryFee)) {
                    inSufficientText.setVisibility(View.VISIBLE);
                    addMoneyButton.setVisibility(View.VISIBLE);
                    joinButton.setVisibility(View.GONE);
                } else {
                    joinButton.setVisibility(View.VISIBLE);
                    inSufficientText.setVisibility(View.GONE);
                    addMoneyButton.setVisibility(View.GONE);

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
    }
}