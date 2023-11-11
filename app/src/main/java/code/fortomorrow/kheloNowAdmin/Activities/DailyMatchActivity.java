package code.fortomorrow.kheloNowAdmin.Activities;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Pulse;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.MatchAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_live;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_ongoing;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_result;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.Model.getRoomIdPass.GetRoomIdResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyMatchActivity extends AppCompatActivity implements View.OnClickListener, MatchAdapter.OnPrizeClickListener, MatchAdapter.OnVideoClickListener {

    RecyclerView mBlogList;
    static String key;

    private APIService apiService;
    SpinKitView spin_kit;
    ProgressBar progressBar;

    LinearLayout mLoading;
    RelativeLayout mEmpty;

    ImageView mBack;

    TextView mNoMatchFoundText;

    DailyMatchActivity activity;
    String jwt_token, secret_id;
    LinearLayout noMatchesLayout;

    private MatchAdapter mAdapter;


    public static boolean recall = true;
    private static Tracker mTracker;

    String type, gameID, playTypeID;
    TextView titleText;

    List<code.fortomorrow.kheloNowAdmin.Model.GetMatch.M> freeFireList;

    TabLayout tabLayout;
    ViewPager viewPager;
    Session_management session_management;

    private boolean isViewShown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_match);


        init_view();

        set_title();

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        mBack.setOnClickListener(this);

        if (Integer.parseInt(secret_id) % 2 == 0) {
            type = "even";
        } else {
            type = "odd";
        }

        session_management.saveGameID(gameID);
        session_management.savePlayTypeID(playTypeID);
        session_management.saveGameKey(key);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new Fragment_ongoing(), "Ongoing");
        viewPageAdapter.addFragment(new Fragment_live(), "Play");
        viewPageAdapter.addFragment(new Fragment_result(), "Result");


        //viewPager.setCurrentItem(1);
        //viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();
        //TabLayoutControl.enableTabs(tabLayout, false);

        //viewPager.setPageTransformer(false, new FanTransformation());

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

        session_management = new Session_management(getApplicationContext());
        tabLayout = findViewById(R.id.tab_layoutID);
        viewPager = findViewById(R.id.view_pagerLayoutID);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        key = getIntent().getStringExtra("child");
        gameID = getIntent().getStringExtra("game_id");
        playTypeID = getIntent().getStringExtra("play_type");

        noMatchesLayout = findViewById(R.id.noMatchesLayoutID);
        mBlogList = findViewById(R.id.mMatchRV);
        mBlogList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBlogList.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.spin_kit);
        Pulse pulse = new Pulse();
        progressBar.setIndeterminateDrawable(pulse);

        mLoading = findViewById(R.id.mLoading);
        mEmpty = findViewById(R.id.mEmpty);

        mNoMatchFoundText = findViewById(R.id.mNoMatchFoundText);
        titleText = findViewById(R.id.titleTextID);

        mBack = findViewById(R.id.mBack);


    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();


        key = getIntent().getStringExtra("child");


    }


    @Override
    public void onClick(View view) {

        if (view == mBack) {


            finish();
            onBackPressed();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        recall = true;
    }

    public void roomidPassShow(Integer matchID) {
        apiService.getRoomIdPassResponse(secret_id, jwt_token, String.valueOf(matchID)).enqueue(new Callback<GetRoomIdResponse>() {
            @Override
            public void onResponse(Call<GetRoomIdResponse> call, Response<GetRoomIdResponse> response) {
                if (response.body().getE() == 0) {
                    Log.d("room_id_pass", new Gson().toJson(response));
                } else {
                    Log.d("room_id_pass", new Gson().toJson(response));

                }
            }

            @Override
            public void onFailure(Call<GetRoomIdResponse> call, Throwable t) {

            }
        });


    }

    public void changePage() {

    }

    public void showAll(String s) {
        Log.d("ssssssss", s);
    }

    public void showPass(String matchId, String title, String matchTime, Boolean isJoined) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DailyMatchActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_room_details_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog1 = mBuilder.create();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textView = mView.findViewById(R.id.textViewID);
        TextView mRoomId = mView.findViewById(R.id.mRoomId);
        TextView mPassword = mView.findViewById(R.id.mPassword);
        TextView titleText = mView.findViewById(R.id.titleTextID);

        titleText.setText(title);

        LinearLayout mainLayout = mView.findViewById(R.id.mainLayoutID);
        dialog1.show();

        Window window = dialog1.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //wlp.windowAnimations = R.style.DialogAnimation;
        //wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        apiService.getRoomIdPassResponse(secret_id, jwt_token, matchId).enqueue(new Callback<GetRoomIdResponse>() {
            @Override
            public void onResponse(Call<GetRoomIdResponse> call, Response<GetRoomIdResponse> response) {
                //Log.d("responseRoom", new Gson().toJson(response.body()));
                //Log.d("responseRoom", title);
                if (response.body().getE() == 0 && !response.body().getM().getRoomId().isEmpty()) {


                    mRoomId.setText(" " + response.body().getM().getRoomId());
                    mPassword.setText(" " + response.body().getM().getRoomPass());

                    mRoomId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("", response.body().getM().getRoomId());
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(DailyMatchActivity.this, "Room ID copied", Toast.LENGTH_SHORT).show();

                        }
                    });

                    mPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("", response.body().getM().getRoomPass());
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(DailyMatchActivity.this, "Password copied", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else if (isJoined) {
                    //Toasty.error(getApplicationContext(), "রুম আইডি দেওয়া হয়নি ", Toasty.LENGTH_SHORT).show();
                    mainLayout.setVisibility(View.GONE);
                    textView.setText("রুম আইডি দেওয়া হয়নি" + getString(R.string.room_code_time));
                } else {
                    //Toasty.error(getApplicationContext(), " আপনি রেজিষ্ট্রেশন করেন নি!", Toasty.LENGTH_SHORT).show();
                    mainLayout.setVisibility(View.GONE);
                    textView.setText("আপনি রেজিষ্ট্রেশন করেন নি!");
                }
            }

            @Override
            public void onFailure(Call<GetRoomIdResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void OnPrizeClick(int position) {
        //Toast.makeText(getApplicationContext(), "Show", Toast.LENGTH_SHORT).show();

        code.fortomorrow.kheloNowAdmin.Model.GetMatch.M response = freeFireList.get(position);

        String title = response.getTitle();

        Dialog dialog = new Dialog(DailyMatchActivity.this);
        dialog.setContentView(R.layout.free_fire_prize_pop_up_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //wlp.windowAnimations = R.style.DialogAnimation;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView oneText = dialog.findViewById(R.id.oneTextID);
        TextView twoText = dialog.findViewById(R.id.twoTextID);
        TextView threeText = dialog.findViewById(R.id.threeTextID);
        TextView perKillText = dialog.findViewById(R.id.perKillTextID);
        TextView totalPrizeText = dialog.findViewById(R.id.totalPrizeTextID);
        TextView titleText = dialog.findViewById(R.id.titleTextID);

        titleText.setText(title);

        LinearLayout closeButton = dialog.findViewById(R.id.closeButtonID);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        oneText.setText("First prize: " + response.getFirstPrize() + " tk");
        twoText.setText("Second prize: " + response.getSecondPrize() + " tk");
        threeText.setText("Third prize: " + response.getThirdPrize() + " tk");
        perKillText.setText("Per Kill: " + response.getPerKillRate() + " tk");
        totalPrizeText.setText("Total prize: " + response.getTotalPrize() + " tk");

    }

    @Override
    public void OnVideoClick(int position) {

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

    /*class ViewPageAdapter extends CustomPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        protected Fragment getItem(CustomIndexHelper customIndexHelper) {
            return fragments.get(customIndexHelper.getDataPosition());
        }

        @Override
        public int getRealCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }


    }*/

    public class FanTransformation implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {

            page.setTranslationX(-position * page.getWidth());
            page.setPivotX(0);
            page.setPivotY(page.getHeight() / 2);
            page.setCameraDistance(20000);

            if (position < -1) {     // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            } else if (position <= 0) {    // [-1,0]
                page.setAlpha(1);
                page.setRotationY(-120 * Math.abs(position));
            } else if (position <= 1) {    // (0,1]
                page.setAlpha(1);
                page.setRotationY(120 * Math.abs(position));

            } else {    // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);

            }


        }
    }


}
