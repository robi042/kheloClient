package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.glide.slider.library.SliderLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.Arena_of_valor_main_activity;
import code.fortomorrow.kheloNowAdmin.Activities.DailyMatchActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_CS_match_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_regular_match_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_main_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.Slider.Slider_adapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.Model.MatchLength.Match_length_response;
import code.fortomorrow.kheloNowAdmin.Model.Notice.Notice_response;
import code.fortomorrow.kheloNowAdmin.Model.PopUp.Pop_up_data_response;
import code.fortomorrow.kheloNowAdmin.Model.RefreshToken.RefreshToken;
import code.fortomorrow.kheloNowAdmin.Model.ScrollTextView;
import code.fortomorrow.kheloNowAdmin.Model.Slider.Slider_list_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPlay extends Fragment implements View.OnClickListener, Slider_adapter.OnItemClickListener {

    private ViewPager mSlideLayout;
    private LinearLayout mDotsLayout;
    private TextView[] mDots;

    static long i;
    static int a = 0;
    CountDownTimer countDownTimer;
    static boolean count = true;
    private String link;
    private TextView mDailyMatch;
    TextSwitcher textSwitcher;
    private TextView mFreeFireMAtch;
    private CardView mPubg, mFreeFire;
    private CardView ludoTournamentButton;

    private ShimmerTextView mDailyMAtch, mTournamnet;

    private SliderLayout mDemoSlider;
    private APIService apiService;
    private String jwt_token, secret_id;

    private Handler handler;
    private Runnable handlerTask;

    int[] images = {R.drawable.telegram_group, R.drawable.takaadd, R.drawable.match_join, R.drawable.youtube};

    private static Tracker mTracker;
    Dialog progress;
    Shimmer shimmer, shimmer1;
    SliderView sliderView;
    Slider_adapter sliderAdapter;
    List<Slider_list_response.M> sliderList;

    TextView csMatchLengthText, scrimsMatchLengthText, ludoMatchLengthText;
    List<Notice_response.M> noticeList;
    ScrollTextView noticeText;

    CardView freeFireScrimsButton, clashRoyaleButton, arenaValorButton;

    TextView arenaValorTotalMatchLengthText;

    ImageView popUpButton;

    DisplayMetrics metrics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySharedPref.init(getActivity());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("gftxxx", secret_id + " " + jwt_token);

        Shimmer shimmer = new Shimmer();

        shimmer.setRepeatCount(-1)
                .setDuration(2800)
                .setStartDelay(1500)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);


    }

    @Override
    public void onClick(View view) {

        if (view == mPubg) {

            Intent i = new Intent(getContext(), Free_fire_CS_match_activity.class);
            startActivity(i);

        } else if (view == mFreeFire) {

            Intent i = new Intent(getContext(), Free_fire_regular_match_activity.class);
            i.putExtra("child", "free_fire_match_get");
            //i.putExtra("game_id", "1");
            startActivity(i);

        } else if (view == ludoTournamentButton) {

            //startActivity(new Intent(getActivity(), LudoTournamentActivity.class));
            Intent intent = new Intent(getActivity(), Ludo_main_activity.class);
            //intent.putExtra("game_id", "3");
            startActivity(intent);

        } else if (view == freeFireScrimsButton) {
            Intent i = new Intent(getContext(), DailyMatchActivity.class);
            i.putExtra("child", "free_fire_match_get");
            i.putExtra("game_id", "6");
            startActivity(i);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_play, container, false);


        init_view(view);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        mPubg.setOnClickListener(this);
        mFreeFire.setOnClickListener(this);

        ludoTournamentButton.setOnClickListener(this);
        freeFireScrimsButton.setOnClickListener(this);
        //getMatchCountPUBG();
//        getMatchCountFreefire();

        Shimmer shimmer = new Shimmer();

        shimmer.setRepeatCount(-1)
                .setDuration(2800)
                .setStartDelay(1500)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);

        Shimmer shimmer2 = new Shimmer();
        shimmer2.setRepeatCount(-1)
                .setDuration(2800)
                .setStartDelay(1500)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);


        shimmer.start(mDailyMAtch);
        shimmer2.start(mTournamnet);

        myViewPager(view);

        get_slider();
        get_notice();
        cs_total_match_length();
        get_scrims_match_length();
        get_total_ludo_match_length();
        get_arena_valor_match_length();

        clashRoyaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Available soon", Toast.LENGTH_SHORT).show();
                coming_soon_alert();
            }
        });

        arenaValorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coming_soon_alert();
                startActivity(new Intent(getActivity(), Arena_of_valor_main_activity.class));
            }
        });

        popUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_up_data();

            }
        });




        setMargins(popUpButton, 0, 0, -50, 0);

        return view;
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


    private void coming_soon_alert() {
        Dialog comingSoonAlert = new Dialog(getActivity());
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

    private void get_slider() {
        apiService.getSliderList(secret_id, jwt_token).enqueue(new Callback<Slider_list_response>() {
            @Override
            public void onResponse(Call<Slider_list_response> call, Response<Slider_list_response> response) {
                if (response.body().getE() == 0) {
                    sliderList = new ArrayList<>();
                    sliderList = response.body().getM();

                    //Toast.makeText(getActivity(), String.valueOf(sliderList.size()), Toast.LENGTH_SHORT).show();

                    sliderAdapter = new Slider_adapter(sliderList);
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                    sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                    sliderView.startAutoCycle();
                    sliderAdapter.setOnItemClickListener(FragmentPlay.this::OnItemClick);
                    sliderView.setSliderAdapter(sliderAdapter);
                }
            }

            @Override
            public void onFailure(Call<Slider_list_response> call, Throwable t) {

            }
        });
    }

    private void pop_up_data() {

        Dialog popUpAlert = new Dialog(getActivity());
        popUpAlert.setContentView(R.layout.pop_up_alert);
        popUpAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpAlert.setCancelable(false);
        popUpAlert.show();

        Window window = popUpAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        setMargins(popUpButton, 0, 0, 0, 0);

        popUpButton.animate()
                .rotation(5)
                .setDuration(500) //500 is half a second
                .setStartDelay(100) //this is optional
                .start();

        ImageView closeButton = popUpAlert.findViewById(R.id.closeButtonID);
        ImageView popUpClickButton = popUpAlert.findViewById(R.id.popUpClickButtonID);
        LinearLayout imageLayout = popUpAlert.findViewById(R.id.imageLayoutID);
        LinearLayout textLayout = popUpAlert.findViewById(R.id.textLayoutID);
        TextView popUpText = popUpAlert.findViewById(R.id.popUpTextID);
        LinearLayout mainLayout = popUpAlert.findViewById(R.id.mainLayoutID);

        mainLayout.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.interpolation));

        apiService.getPopUpData(secret_id, jwt_token).enqueue(new Callback<Pop_up_data_response>() {
            @Override
            public void onResponse(Call<Pop_up_data_response> call, Response<Pop_up_data_response> response) {
                if (response.body().getE() == 0) {
                    Boolean imageAvailable = response.body().getM().getHasImage();

                    if (imageAvailable) {
                        imageLayout.setVisibility(View.VISIBLE);
                        textLayout.setVisibility(View.GONE);

                        //Display display = getActivity().getWindowManager().getDefaultDisplay();


                        Picasso.get().load(response.body().getM().getImageLink()).into(popUpClickButton);

                        //popUpClickButton.getLayoutParams().width = display.getWidth();

                        popUpClickButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse(response.body().getM().getLink()); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });

                    } else {
                        imageLayout.setVisibility(View.GONE);
                        textLayout.setVisibility(View.VISIBLE);
                        popUpText.setText(response.body().getM().getText());
                    }
                } else {
                    Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pop_up_data_response> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpAlert.dismiss();

                popUpButton.animate()
                        .rotation(-90)
                        .setDuration(500) //500 is half a second
                        .setStartDelay(100) //this is optional
                        .start();

                setMargins(popUpButton, 0, 0, -50, 0);
            }
        });

    }

    private void init_view(View view) {
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mPubg = view.findViewById(R.id.mPubg);
        mFreeFire = view.findViewById(R.id.mFreeFire);
        mFreeFireMAtch = view.findViewById(R.id.mFreeFireMatch);

        ludoTournamentButton = view.findViewById(R.id.ludoTournamentButtonID);
        arenaValorButton = view.findViewById(R.id.arenaValorButtonID);

        mDailyMAtch = view.findViewById(R.id.mDailyMatch);
        mTournamnet = view.findViewById(R.id.mTournament);

        noticeText = view.findViewById(R.id.noticeTextID);

        sliderView = view.findViewById(R.id.image_slider);
        clashRoyaleButton = view.findViewById(R.id.clashRoyaleButtonID);
        freeFireScrimsButton = view.findViewById(R.id.freeFireScrimsButtonID);
        csMatchLengthText = view.findViewById(R.id.csMatchLengthTextID);
        scrimsMatchLengthText = view.findViewById(R.id.scrimsMatchLengthTextID);
        ludoMatchLengthText = view.findViewById(R.id.ludoMatchLengthTextID);

        arenaValorTotalMatchLengthText = view.findViewById(R.id.arenaValorTotalMatchLengthTextID);

        popUpButton = view.findViewById(R.id.popUpButtonID);
    }

    private void get_notice() {

        apiService.getNotice(secret_id, jwt_token).enqueue(new Callback<Notice_response>() {
            @Override
            public void onResponse(Call<Notice_response> call, Response<Notice_response> response) {

                if (response.body().getE() == 0) {
                    noticeList = new ArrayList<>();
                    noticeList = response.body().getM();
                    for (int i = noticeList.size() - 1; i >= 0; i--) {
                        noticeText.append("  " + noticeList.get(i).getTitle());
                    }

                }

                noticeText.setTextColor(Color.WHITE);
                noticeText.startScroll();


            }

            @Override
            public void onFailure(Call<Notice_response> call, Throwable t) {

            }
        });
        //setMarqueeSpeed(noticeText, 1000);

    }

    private void cs_total_match_length() {

        apiService.getCS_all_match_length(secret_id, jwt_token).enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {
                    csMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");
                } else {
                    csMatchLengthText.setText(String.valueOf(response.body().getM()));
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                csMatchLengthText.setText("Authentication failed");
            }
        });
    }

    private void getMatchCountFreefire() {

        apiService.get_full_map_length(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));

                if (response.body().getE() == 0) {
                    mFreeFireMAtch.setText(String.valueOf(response.body().getM()) + " Matches Found");
                } else {
                    mFreeFireMAtch.setText(String.valueOf(response.body().getM()));
                }


            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                mFreeFireMAtch.setText("Authentication failed");
            }
        });

    }

    private void get_scrims_match_length() {
        apiService.get_scrims_match_length(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                //Log.d("match_size", new Gson().toJson(response.body().getM()));

                if (response.body().getE() == 0) {
                    scrimsMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");
                } else {
                    scrimsMatchLengthText.setText(String.valueOf(response.body().getM()));
                }
                //scrimsMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                scrimsMatchLengthText.setText("Authentication failed");
            }
        });
    }

    private void get_total_ludo_match_length() {
        apiService.getTotalLudoMatchLength(secret_id, jwt_token).enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {
                    ludoMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");
                } else {
                    ludoMatchLengthText.setText(String.valueOf(response.body().getM()));
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                ludoMatchLengthText.setText("Authentication failed");
            }
        });
    }

    private void get_arena_valor_match_length() {
        apiService.arena_of_valor_get_match_length(secret_id, jwt_token).enqueue(new Callback<Match_length_response>() {
            @Override
            public void onResponse(Call<Match_length_response> call, Response<Match_length_response> response) {
                if (response.body().getE() == 0) {
                    arenaValorTotalMatchLengthText.setText(String.valueOf(response.body().getM()) + " Matches Found");
                } else {
                    arenaValorTotalMatchLengthText.setText(String.valueOf(response.body().getM()));
                }
            }

            @Override
            public void onFailure(Call<Match_length_response> call, Throwable t) {
                arenaValorTotalMatchLengthText.setText("Authentication failed");
            }
        });
    }


    private void getRefreshToken() {
        apiService.getRefreshToken(secret_id, "123456").enqueue(new Callback<RefreshToken>() {
            @Override
            public void onResponse(Call<RefreshToken> call, Response<RefreshToken> response) {
                //Log.d("refresh_token", new Gson().toJson(response.body()));
                EasySharedPref.write("jwt_token", response.body().getM());
            }

            @Override
            public void onFailure(Call<RefreshToken> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();

        getMatchCountFreefire();

    }

    @Override
    public void onPause() {
        super.onPause();

        count = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        count = false;

    }


    void myViewPager(final View view) {


    }


    public void openWebPage(String url) {

        Uri webpage = Uri.parse(url);

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            webpage = Uri.parse("http://" + url);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void OnItemClick(int position) {
        Slider_list_response.M response = sliderList.get(position);

        //Toast.makeText(getActivity(), response.getLink(), Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse(response.getLink()); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public static void setMarqueeSpeed(TextView tv, float speed) {
        if (tv != null) {
            try {
                Field f = null;
                if (tv instanceof AppCompatTextView) {
                    f = tv.getClass().getSuperclass().getDeclaredField("mMarquee");
                } else {
                    f = tv.getClass().getDeclaredField("mMarquee");
                }
                if (f != null) {
                    f.setAccessible(true);
                    Object marquee = f.get(tv);
                    if (marquee != null) {
                        String scrollSpeedFieldName = "mScrollUnit";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            scrollSpeedFieldName = "mPixelsPerSecond";
                        }
                        Field mf = marquee.getClass().getDeclaredField(scrollSpeedFieldName);
                        mf.setAccessible(true);
                        mf.setFloat(marquee, speed);
                    }
                } else {
                    Log.d("Marquee", "mMarquee object is null.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
