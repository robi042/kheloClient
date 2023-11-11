package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import code.fortomorrow.kheloNowAdmin.R;

public class Free_fire_1_vs_1_match_join_activity extends AppCompatActivity {

    RecyclerView itemRecyclerView;
    CheckBox player1CheckBox, player2CheckBox;
    ImageView backButton;
    Boolean stateA = false, stateB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire1_vs1_match_join);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init_view() {
        backButton = findViewById(R.id.backButtonID);
        itemRecyclerView = findViewById(R.id.itemRecyclerViewID);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}