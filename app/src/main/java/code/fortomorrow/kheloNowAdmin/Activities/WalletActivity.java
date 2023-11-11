package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Fragments.AddMoney_Fragment_new;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_Transaction;
import code.fortomorrow.kheloNowAdmin.Fragments.Withdraw_Fragment;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyResponse.AddMoneyResponse;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WalletActivity extends AppCompatActivity {
    private FloatingActionButton whatsappInWallet;
    private static long timeA;
    private static long timeNow;
    private static final long START_TIME_IN_MILLIS = 60000;
    private long mLeftInMillis;
    private boolean mTimerRunning;
    ViewPager pager;
    TabLayout tabLayout;
    ImageView back;
    TextView amountText, depositText, winningText;
    private APIService apiService;
    private String jwt_token, secret_id;

    private static Tracker mTracker;

    ImageView reloadButton;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        init_view();

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_profile();
            }
        });

        get_profile();
    }

    private void get_profile() {
        //Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT).show();
        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.body().getE() == 0) {
                    depositText.setText("৳ " + String.valueOf(response.body().getM().getDepositBalance()));
                    winningText.setText("৳ " + String.valueOf(response.body().getM().getWinningBalance()));
                    amountText.setText("৳ " + String.valueOf(response.body().getM().getTotalBalance()));
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        apiService = AppConfig.getRetrofit().create(APIService.class);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        timeNow = prefs.getLong("timenow", timeNow);
        mTimerRunning = prefs.getBoolean("mTimerRunning", false);
        timeA = prefs.getLong("time", timeA);

        pager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        amountText = findViewById(R.id.amountTextID);
        depositText = findViewById(R.id.depositTextID);
        winningText = findViewById(R.id.winningTextID);
        back = findViewById(R.id.back);

        loader = new Dialog(WalletActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);


        reloadButton = findViewById(R.id.reloadButtonID);
    }


    @Override
    public void onResume() {
        super.onResume();
        //Getting All Information


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new AddMoney_Fragment_new(WalletActivity.this), "Add Money");
        adapter.addFragment(new Withdraw_Fragment(), "Withdraw");
        adapter.addFragment(new Fragment_Transaction(), "Transaction");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }     //Fragments setup


    public void verify_Payment(final String my_amount, final String my_digit, final String date, final String time, final String myUID, final String b_reference_key, final String source) {
//        Toast.makeText(WalletActivity.this,""+my_amount+" "+my_digit,Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(my_amount)
                && !TextUtils.isEmpty(my_digit)) {

            Dialog confirmAlert = new Dialog(WalletActivity.this);
            confirmAlert.setContentView(R.layout.confirmation_alert);
            confirmAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmAlert.show();

            Window window = confirmAlert.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            TextView yesButton = confirmAlert.findViewById(R.id.yesButtonID);
            TextView noButton = confirmAlert.findViewById(R.id.noButtonID);


            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (timeA == 0) {
                        timeA = System.currentTimeMillis() + START_TIME_IN_MILLIS;
                        mTimerRunning = true;
                        mLeftInMillis = timeA;
                        //Log.d("timelo", String.valueOf(timeA));
                        //Log.d("timeNow", String.valueOf(timeNow));
                        sendReq(my_amount, date, time, my_digit, myUID, b_reference_key, source);
                        confirmAlert.dismiss();


                    } else {
                        timeNow = timeA - System.currentTimeMillis();
                        if (timeNow < 0) {

                            sendReq(my_amount, date, time, my_digit, myUID, b_reference_key, source);
                            confirmAlert.dismiss();
                            timeNow = 0;
                            timeA = 0;
                            mTimerRunning = false;
                            //confirmAlert.dismiss();
                        } else {
                            Toast.makeText(WalletActivity.this, "You can send req after 10 minutes", Toast.LENGTH_SHORT).show();
                            confirmAlert.dismiss();
                        }
                    }
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmAlert.dismiss();
                }
            });

           /* AlertDialog.Builder builder = new AlertDialog.Builder(WalletActivity.this);
            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, int which) {
                    if (timeA == 0) {
                        timeA = System.currentTimeMillis() + START_TIME_IN_MILLIS;
                        mTimerRunning = true;
                        mLeftInMillis = timeA;
                        //Log.d("timelo", String.valueOf(timeA));
                        //Log.d("timeNow", String.valueOf(timeNow));
                        sendReq(my_amount, date, time, my_digit, myUID, b_reference_key, source);
                        dialog.dismiss();


                    } else {
                        timeNow = timeA - System.currentTimeMillis();
                        if (timeNow < 0) {

                            sendReq(my_amount, date, time, my_digit, myUID, b_reference_key, source);
                            dialog.dismiss();
                            timeNow = 0;
                            timeA = 0;
                            mTimerRunning = false;
                            dialog.dismiss();
                        } else {
                            Toast.makeText(WalletActivity.this, "You can send req after 10 minutes", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }


                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();*/


        }
    }

    private void sendReq(final String my_amount, final String date, final String time, final String my_digit, final String myUID, final String b_reference_key, final String source) {
        loader.show();
        apiService.getResponseAddMoney(secret_id, jwt_token, "Add Money", String.valueOf(my_amount), source, my_digit).enqueue(new Callback<AddMoneyResponse>() {
            @Override
            public void onResponse(Call<AddMoneyResponse> call, Response<AddMoneyResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    new SweetAlertDialog(WalletActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("Your AddMoney Request is sent Successfully!")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    sDialog.cancel();
                                }
                            })
                            .show();
                    FirebaseMessaging.getInstance().subscribeToTopic("NotificationForAddMoney" + response.body().getM());
                } else if (response.body().getE() == 5) {
                    Toasty.error(getApplicationContext(), "You are blocked", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddMoneyResponse> call, Throwable t) {
                loader.dismiss();
                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("time", timeA);

        editor.putLong("timeNow", timeNow);
        editor.putBoolean("mTimerRunning", mTimerRunning);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        timeNow = prefs.getLong("timenow", timeNow);
        timeA = prefs.getLong("time", timeA);
//        Log.d("timechaga",String.valueOf(time));
//        Log.d("timenow",String.valueOf(timeNow));
        mTimerRunning = prefs.getBoolean("mTimerRunning", false);
        if (mTimerRunning) {
            mLeftInMillis = timeA - System.currentTimeMillis();
            if (mLeftInMillis < 0) {
                mTimerRunning = false;
                timeA = 0;
                timeNow = 0;
                mTimerRunning = false;
            }
        } else {

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("time", timeA);
        editor.putLong("timeNow", timeNow);
        editor.putBoolean("mTimerRunning", mTimerRunning);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("time", timeA);
        editor.putLong("timeNow", timeNow);
        editor.putBoolean("mTimerRunning", mTimerRunning);
        editor.apply();
    }

}
