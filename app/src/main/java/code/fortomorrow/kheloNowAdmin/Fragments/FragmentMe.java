package code.fortomorrow.kheloNowAdmin.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import java.io.IOException;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.EditProfileActivity;
import code.fortomorrow.kheloNowAdmin.Activities.LoginActivity;
import code.fortomorrow.kheloNowAdmin.Activities.OrderActivity;
import code.fortomorrow.kheloNowAdmin.Activities.ReferActivity;
import code.fortomorrow.kheloNowAdmin.Activities.StatisticsActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Top_players_activity;
import code.fortomorrow.kheloNowAdmin.Activities.WalletActivity;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMe extends Fragment implements View.OnClickListener {

    private String myUID;

    private TextView name, match, kill, win, balance;
    private LinearLayout refer, profile, order, wallet, statistics, support, share, logout;

    private String whatsapp_number = "0 1518-424201";
    private APIService apiService;
    private String jwt_token, secret_id;
    private static Tracker mTracker;
    LinearLayout topPlayersButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getActivity());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

    }

    @Override
    public void onClick(View view) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_me, container, false);
        balance = view.findViewById(R.id.balance);
        name = view.findViewById(R.id.name);
        match = view.findViewById(R.id.match);
        kill = view.findViewById(R.id.kill);
        win = view.findViewById(R.id.win);
        refer = view.findViewById(R.id.refer);
        profile = view.findViewById(R.id.profile);
        order = view.findViewById(R.id.order);
        wallet = view.findViewById(R.id.wallet);
        statistics = view.findViewById(R.id.statistics);
        support = view.findViewById(R.id.support);
        share = view.findViewById(R.id.share);
        logout = view.findViewById(R.id.logout);
        topPlayersButton = view.findViewById(R.id.topPlayersButtonID);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        allClickListener(view);

        topPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Top_players_activity.class));
            }
        });

        return view;

    }

    void allClickListener(final View view1) {

        refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view1.getContext(), ReferActivity.class);
                startActivity(i);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view1.getContext(), EditProfileActivity.class);
                startActivity(i);

            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view1.getContext(), OrderActivity.class);
                startActivity(i);

            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view1.getContext(), WalletActivity.class);
                startActivity(i);


            }
        });


        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view1.getContext(), StatisticsActivity.class);
                startActivity(i);

            }
        });


        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://api.whatsapp.com/send?phone=+88" + whatsapp_number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Khela");
                    String sAux = "\"KHELO\"" +"is an online gaming platform app. Download app from the below link and enjoy.\n\n";
                    sAux = sAux + "https://app-uploads-apk.s3.ap-south-1.amazonaws.com/khelo.apk";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }


            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EasySharedPref.write("jwt_token", "");
                EasySharedPref.write("secret_id", "");
                Intent i = new Intent(view1.getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        apiService.getProfile(secret_id, jwt_token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                //Log.d("Profile", new Gson().toJson(response.body().getM()));
                name.setText(response.body().getM().getName());
                match.setText(String.valueOf(response.body().getM().getTotalMatchPlay()));
                kill.setText(String.valueOf(response.body().getM().getTotalKill()));
                win.setText(String.valueOf(response.body().getM().getMatchWin()));
                balance.setText(String.valueOf(String.valueOf(response.body().getM().getTotalBalance())));
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });


    }


}
