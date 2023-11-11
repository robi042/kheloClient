package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.MatchLength.Match_length_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ludo_main_activity extends AppCompatActivity implements View.OnClickListener {

    private CardView ludoTournamentButton, ludoGrandTournamentButton, ludoQuickTournamentButton, fourPlayerTournamentButton;
    APIService apiService;
    String jwt_token, secret_id;
    TextView matchLengthText, matchGrandLengthText, matchQuickLengthText, matchFourPlayerLengthText;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_main);

        init_view();

        ludoTournamentButton.setOnClickListener(this);
        ludoGrandTournamentButton.setOnClickListener(this);
        ludoQuickTournamentButton.setOnClickListener(this);
        fourPlayerTournamentButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == ludoTournamentButton) {

            //startActivity(new Intent(getActivity(), LudoTournamentActivity.class));
            Intent intent = new Intent(getApplicationContext(), LudoTournamentActivity.class);
            intent.putExtra("game_id", "3");
            startActivity(intent);

        } else if (view == ludoGrandTournamentButton) {

            Intent intent = new Intent(getApplicationContext(), LudoTournamentActivity.class);
            intent.putExtra("game_id", "4");
            startActivity(intent);

        } else if (view == ludoQuickTournamentButton) {
            Intent intent = new Intent(getApplicationContext(), LudoTournamentActivity.class);
            intent.putExtra("game_id", "7");
            startActivity(intent);
        } else if (view == fourPlayerTournamentButton) {
            Intent intent = new Intent(getApplicationContext(), LudoTournamentActivity.class);
            intent.putExtra("game_id", "8");
            startActivity(intent);
        } else if (view == backButton) {
            finish();
        }


    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();

        matchLength();

    }

    private void matchLength() {

        //Log.d("tokenxx", jwt_token);
        apiService.getLudoMatchLength(secret_id, jwt_token, "3").enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {

                    if (response.body().getM() > 0) {
                        matchLengthText.setText(String.valueOf(response.body().getM()) + " Matches found");
                    } else {
                        matchLengthText.setText("No matches found");
                    }
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                matchLengthText.setText("Authentication Failed");
                //Log.d("errorrxx", t.getMessage());

            }
        });

        apiService.getLudoMatchLength(secret_id, jwt_token, "4").enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {

                    if (response.body().getM() > 0) {
                        matchGrandLengthText.setText(String.valueOf(response.body().getM()) + " Matches found");
                    } else {
                        matchGrandLengthText.setText("No matches found");
                    }
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                matchGrandLengthText.setText("Authentication Failed");
                //Log.d("errorrxx", t.getMessage());
            }
        });
        apiService.getLudoMatchLength(secret_id, jwt_token, "7").enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {

                    if (response.body().getM() > 0) {
                        matchQuickLengthText.setText(String.valueOf(response.body().getM()) + " Matches found");
                    } else {
                        matchQuickLengthText.setText("No matches found");
                    }
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                matchQuickLengthText.setText("Authentication Failed");
                //Log.d("errorrxx", t.getMessage());
            }
        });

        apiService.getLudoMatchLength(secret_id, jwt_token, "8").enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {

                    if (response.body().getM() > 0) {
                        matchFourPlayerLengthText.setText(String.valueOf(response.body().getM()) + " Matches found");
                    } else {
                        matchFourPlayerLengthText.setText("No matches found");
                    }
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                matchFourPlayerLengthText.setText("Authentication Failed");
                //Log.d("errorrxx", t.getMessage());
            }
        });

    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        ludoTournamentButton = findViewById(R.id.ludoTournamentButtonID);
        ludoGrandTournamentButton = findViewById(R.id.ludoGrandTournamentButtonID);

        matchLengthText = findViewById(R.id.matchLengthTextID);
        matchGrandLengthText = findViewById(R.id.matchGrandLengthTextID);
        matchQuickLengthText = findViewById(R.id.matchQuickLengthTextID);
        matchFourPlayerLengthText = findViewById(R.id.matchFourPlayerLengthTextID);

        backButton = findViewById(R.id.backButtonID);
        ludoQuickTournamentButton = findViewById(R.id.ludoQuickTournamentButtonID);
        fourPlayerTournamentButton = findViewById(R.id.fourPlayerTournamentButtonID);
    }

    private void coming_soon_alert() {
        Dialog comingSoonAlert = new Dialog(Ludo_main_activity.this);
        comingSoonAlert.setContentView(R.layout.coming_soon_alert);
        comingSoonAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //comingSoonAlert.setCancelable(false);
        comingSoonAlert.show();

        Window window = comingSoonAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }
}