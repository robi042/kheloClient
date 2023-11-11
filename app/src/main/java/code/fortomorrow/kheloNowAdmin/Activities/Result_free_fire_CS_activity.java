package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import code.fortomorrow.kheloNowAdmin.R;

public class Result_free_fire_CS_activity extends AppCompatActivity {

    CardView freeFireCsRegularMatchButton, freeFireCsGrandMatchButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_free_fire_cs);

        init_view();

        freeFireCsRegularMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Result_activity.class);
                intent.putExtra("game_id", "2");
                startActivity(intent);
            }
        });

        freeFireCsGrandMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Result_activity.class);
                intent.putExtra("game_id", "5");
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init_view() {
        freeFireCsRegularMatchButton = findViewById(R.id.freeFireCsRegularMatchButtonID);
        freeFireCsGrandMatchButton = findViewById(R.id.freeFireCsGrandMatchButtonID);
        backButton = findViewById(R.id.backButtonID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}