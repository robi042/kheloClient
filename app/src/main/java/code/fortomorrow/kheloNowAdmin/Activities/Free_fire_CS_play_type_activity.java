package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.MatchLength.Match_length_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Free_fire_CS_play_type_activity extends AppCompatActivity {

    TextView titleText;
    String gameID, child;
    CardView soloButton, duoButton, squadButton, soloDuoButton;
    CardView soloSquadButton, duoSquadButton, soloDuoSquadButton;
    CardView freeFireCS_6_vs_6_Button, freeFireCS_1_vs_1_Button;
    APIService apiService;
    String secret_id, jwt_token;
    TextView soloLengthText, duoLengthText, squadLengthText, soloDuoLengthText;
    TextView soloSquadLengthText, duoSquadLengthText, soloDuoSquadLengthText;
    TextView cs_1_vs_1_length_text, cs_6_vs_6_length_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_cs_play_type);

        init_view();

        set_title();

        soloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("1");
            }
        });

        duoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("2");
            }
        });

        squadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("3");
            }
        });

        soloDuoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("4");
            }
        });

        soloSquadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("5");
            }
        });

        duoSquadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("6");
            }
        });

        soloDuoSquadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("7");
            }
        });



        freeFireCS_1_vs_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("8");
            }
        });

        freeFireCS_6_vs_6_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("9");
            }
        });

        match_length("1");
        match_length("2");
        match_length("3");
        match_length("4");
        match_length("5");
        match_length("6");
        match_length("7");
        match_length("8");
        match_length("9");
    }

    private void go_to_activity(String playTypeID) {
        Intent i = new Intent(getApplicationContext(), DailyMatchActivity.class);
        i.putExtra("child", child);
        i.putExtra("game_id", gameID);
        i.putExtra("play_type", playTypeID);
        startActivity(i);
    }

    private void match_length(String playTypeID) {
        apiService.getSeparatedCSMatchLength(secret_id, jwt_token, gameID, playTypeID).enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {
                    if (playTypeID.equals("1")) {
                        soloLengthText.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("2")) {
                        duoLengthText.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("3")) {
                        squadLengthText.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("4")) {
                        soloDuoLengthText.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("5")) {
                        soloSquadLengthText.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("6")) {
                        duoSquadLengthText.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("7")) {
                        soloDuoSquadLengthText.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("8")) {
                        cs_1_vs_1_length_text.setText(response.body().getM().toString() + " Matches Found");
                    } else if (playTypeID.equals("9")) {
                        cs_6_vs_6_length_text.setText(response.body().getM().toString() + " Matches Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {

            }
        });
    }

    private void set_title() {
        if (gameID.equals("1")) {
            titleText.setText("Free Fire (Regular)");
        } else if (gameID.equals("2")) {
            titleText.setText("Free Fire CS (Regular)");
        } else if (gameID.equals("5")) {
            titleText.setText("Free Fire CS (Grand)");
        } else if (gameID.equals("6")) {
            titleText.setText("Daily Scrims");
        } else if (gameID.equals("9")) {
            titleText.setText("Free Fire (Regular Premium)");
        } else if (gameID.equals("10")) {
            titleText.setText("Free Fire (Regular Grand)");
        } else if (gameID.equals("11")) {
            titleText.setText("Arena of valor (Regular)");
        } else if (gameID.equals("12")) {
            titleText.setText("Arena of valor (Grand)");
        }
    }

    private void init_view() {

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        gameID = getIntent().getStringExtra("game_id");
        child = getIntent().getStringExtra("child");
        titleText = findViewById(R.id.titleTextID);

        soloLengthText = findViewById(R.id.soloLengthTextID);
        duoLengthText = findViewById(R.id.duoLengthTextID);
        squadLengthText = findViewById(R.id.squadLengthTextID);
        soloDuoLengthText = findViewById(R.id.soloDuoLengthTextID);
        soloSquadLengthText = findViewById(R.id.soloSquadLengthTextID);
        duoSquadLengthText = findViewById(R.id.duoSquadLengthTextID);
        soloDuoSquadLengthText = findViewById(R.id.soloDuoSquadLengthTextID);
        cs_1_vs_1_length_text = findViewById(R.id.cs_1_vs_1_length_textID);
        cs_6_vs_6_length_text = findViewById(R.id.cs_6_vs_6_length_textID);

        soloButton = findViewById(R.id.soloButtonID);
        duoButton = findViewById(R.id.duoButtonID);
        squadButton = findViewById(R.id.squadButtonID);
        soloDuoButton = findViewById(R.id.soloDuoButtonID);
        soloSquadButton = findViewById(R.id.soloSquadButtonID);
        duoSquadButton = findViewById(R.id.duoSquadButtonID);
        soloDuoSquadButton = findViewById(R.id.soloDuoSquadButtonID);
        freeFireCS_6_vs_6_Button = findViewById(R.id.freeFireCS_6_vs_6_ButtonID);
        freeFireCS_1_vs_1_Button = findViewById(R.id.freeFireCS_1_vs_1_ButtonID);
    }
}