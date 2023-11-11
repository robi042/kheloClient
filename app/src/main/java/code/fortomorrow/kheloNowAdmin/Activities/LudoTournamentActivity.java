package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_ludo_live;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_ongoing;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_result;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;

public class LudoTournamentActivity extends AppCompatActivity {
    private ImageView backFromTournament;
    RecyclerView ludoTournamentView;
    private String secretID, jwt_token;
    private APIService apiService;

    private List<Ludo_tournament_game_list_response.M> ludoGameList;
    private Ludo_game_list_adapter adapter;
    LinearLayout noMatchesLayout;
    String gameID;

    TabLayout tabLayout;
    ViewPager viewPager;
    Session_management session_management;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ludo_tournament_activity);

        init_view();

        set_title();

        backFromTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new Fragment_ongoing(), "Ongoing");
        viewPageAdapter.addFragment(new Fragment_ludo_live(), "Play");
        viewPageAdapter.addFragment(new Fragment_result(), "Result");

        //viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();

        //TabLayoutControl.enableTabs(tabLayout, false);
        //viewPager.setPageTransformer(false, new FanTransformation());

    }

    private void set_title() {
        if (gameID.equals("3")) {
            titleText.setText("Ludo (Regular)");
        } else if (gameID.equals("4")) {
            titleText.setText("Ludo (Grand)");
        } else if (gameID.equals("7")) {
            titleText.setText("Ludo (Premium Match)");
        } else if (gameID.equals("8")) {
            titleText.setText("Ludo (4 Player)");
        }
    }

    private void init_view() {
        session_management = new Session_management(getApplicationContext());
        gameID = getIntent().getStringExtra("game_id");
        session_management.saveGameID(gameID);

        tabLayout = findViewById(R.id.tab_layoutID);
        viewPager = findViewById(R.id.view_pagerLayoutID);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secretID = EasySharedPref.read("secret_id", "");

        backFromTournament = findViewById(R.id.backFromTournament);
        titleText = findViewById(R.id.titleTextID);

        noMatchesLayout = findViewById(R.id.noMatchesLayoutID);
        ludoTournamentView = findViewById(R.id.ludoTournamentViewID);
        ludoTournamentView.setHasFixedSize(true);
        ludoTournamentView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
        //ludoTournament_func();
    }


    class ViewPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}