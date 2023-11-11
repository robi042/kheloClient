package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.StatisticsAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Fragments.Statistics_fragment_freefire;
import code.fortomorrow.kheloNowAdmin.Fragments.Statistics_fragment_ludo;
import code.fortomorrow.kheloNowAdmin.Model.Statistics.M;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;


public class StatisticsActivity extends AppCompatActivity {

    ImageView back;
    LinearLayout sad;
    RecyclerView recyclerView;
    String uid;
    private List<M> statisticsModelList = new ArrayList<>();
    StatisticsAdapter adapter;
    TextView sorry;

    private APIService apiService;
    private String jwt_token, secret_id;

    private static Tracker mTracker;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        init();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.freeFireID); // change to whichever id should be default
        }

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        //Log.d("infoxxx", jwt_token);

    }


    void init() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment selecFragment = null;

        switch (item.getItemId()) {
            case R.id.freeFireID:
                selecFragment = new Statistics_fragment_freefire();
                break;
            case R.id.ludoID:
                selecFragment = new Statistics_fragment_ludo();
                break;


        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selecFragment).commit();
        return true;
    };


}
