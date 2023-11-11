package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Pulse;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_onGoing_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.OngoingAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Fragments.FragmentOnGoing;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.M;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnGoing_activity extends AppCompatActivity implements View.OnClickListener {
//,  Ludo_onGoing_game_list_adapter.OnSpectateClickListener,  Ludo_onGoing_game_list_adapter.OnItemClickListener, OngoingAdapter.OnPrizeClickListener, OngoingAdapter.OnWatchClickListener
    RecyclerView ongoingRV;

    SpinKitView spin_kit;
    ProgressBar progressBar;

    LinearLayout mLoading;
    RelativeLayout mEmpty;
    private String jwt_token, secret_id;
    private APIService apiService;
    private List<M> ongoingList = new ArrayList<>();
    private List<Ludo_tournament_game_list_response.M> ludoOnGoingList;
    private static Tracker mTracker;
    String item = "Free Fire (Regular)";
    String[] games = {"Free Fire (Regular)", "Free Fire (CS Regular)", "Free Fire (CS Grand)", "Ludo (Regular)", "Ludo (Grand)"};
    private Ludo_onGoing_game_list_adapter adapter;
    String REGULAR_MATCH_LUDO_MATCH = "01776001380";

    ImageView backButton;
    String gameTypeID;
    TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going);

        init_view();

        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        gameTypeID = getIntent().getStringExtra("game_id");

        if (gameTypeID.equals("1")) {
            matchList(gameTypeID);
            nameText.setText("Free Fire (Regular)");
        } else if (gameTypeID.equals("3")) {
            ludo_list_func(gameTypeID);
            nameText.setText("Ludo (Regular)");
        } else if (gameTypeID.equals("4")) {
            ludo_list_func(gameTypeID);
            nameText.setText("Ludo (Grand)");
        } else if (gameTypeID.equals("2")) {
            matchList(gameTypeID);
            nameText.setText("Free Fire (CS Regular)");
        } else if (gameTypeID.equals("5")) {
            matchList(gameTypeID);
            nameText.setText("Free Fire (CS Grand)");
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init_view() {
        ongoingRV = findViewById(R.id.mBlogList);
        ongoingRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        spin_kit = findViewById(R.id.spin_kit);
        progressBar = findViewById(R.id.spin_kit);
        // Pulse pulse = new Pulse();
        //progressBar.setIndeterminateDrawable(pulse);
        mLoading = findViewById(R.id.mLoading);
        mEmpty = findViewById(R.id.mEmpty);

        backButton = findViewById(R.id.backButtonID);
        nameText = findViewById(R.id.nameTextID);
    }

    void matchList(String gameID) {


        ongoingList.clear();

        apiService.getOngoingResponse(secret_id, jwt_token, gameID).enqueue(new Callback<OngoingResponse>() {
            @Override
            public void onResponse(Call<OngoingResponse> call, Response<OngoingResponse> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    spin_kit.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.VISIBLE);
                    ongoingList = response.body().getM();
                    OngoingAdapter ongoingAdapter = new OngoingAdapter(ongoingList);
                    //ongoingAdapter.setOnClickListener(OnGoing_activity.this::OnPrizeClick, OnGoing_activity.this::OnWatchClick);
                    ongoingRV.setAdapter(ongoingAdapter);

                } else {
                    ongoingRV.setVisibility(View.GONE);
                    spin_kit.setVisibility(View.VISIBLE);
                    mEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<OngoingResponse> call, Throwable t) {

            }
        });


    }

    private void ludo_list_func(String gameID) {
        ludoOnGoingList = new ArrayList<>();
        ludoOnGoingList.clear();
        apiService.getOngoingLudoMatchList(secret_id, jwt_token, gameID).enqueue(new Callback<Ludo_tournament_game_list_response>() {
            @Override
            public void onResponse(Call<Ludo_tournament_game_list_response> call, Response<Ludo_tournament_game_list_response> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    spin_kit.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.VISIBLE);
                    ludoOnGoingList = response.body().getM();
                    adapter = new Ludo_onGoing_game_list_adapter(ludoOnGoingList);
                    //adapter.setOnItemClickListener(OnGoing_activity.this::OnSpectateClick, OnGoing_activity.this::OnImageClick);
                    ongoingRV.setAdapter(adapter);

                } else {
                    spin_kit.setVisibility(View.VISIBLE);
                    mEmpty.setVisibility(View.VISIBLE);
                    ongoingRV.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Ludo_tournament_game_list_response> call, Throwable t) {

            }
        });
    }


    /*@Override
    public void OnImageClick(int position) {
        Ludo_tournament_game_list_response.M response = ludoOnGoingList.get(position);
        String matchID = String.valueOf(response.getMatchId());

        //Toast.makeText(getActivity(), String.valueOf(response.getMatchId()), Toast.LENGTH_SHORT).show();
        try {
            if (response.getHasImage()) {
                //Toasty.error(getActivity(), "Image available", Toasty.LENGTH_SHORT).show();
                String url = "https://api.whatsapp.com/send?phone=+88" + REGULAR_MATCH_LUDO_MATCH;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        } catch (Exception c) {
            Intent intent = new Intent(getApplicationContext(), Ludo_image_add_activity.class);
            intent.putExtra("match_id", matchID);
            intent.putExtra("room_code", response.getRoomCode());
            startActivity(intent);

            //Toast.makeText(getActivity(), response.getRoomCode(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void OnSpectateClick(int position) {
        Toast.makeText(getApplicationContext(), "Available soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemClick(int position) {

    }

    @Override
    public void OnWatchClick(int position) {
        //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://youtube.com/channel/" + "UCxjeDCkQgtpZhmCIa960r_w"));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
        }
    }

    @Override
    public void OnPrizeClick(int position) {
        code.fortomorrow.kheloNowAdmin.Model.Ongoing.M response = ongoingList.get(position);

        Dialog dialog = new Dialog(OnGoing_activity.this);
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
    }*/

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onClick(View view) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


}