package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.LudoTournamentActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_result_details_activity;
import code.fortomorrow.kheloNowAdmin.Activities.OnGoing_activity;
import code.fortomorrow.kheloNowAdmin.Activities.OnGoing_free_fire_cs_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Result_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Result_free_fire_CS_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_result_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.ResultAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.GetResultResponse;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.M;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentResult extends Fragment {

    CardView freeFireRegularButton, ludoRegularButton, ludoGrandButton, freeFireCSButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_result, container, false);

        init_view(view);

        freeFireRegularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Result_activity.class);
                intent.putExtra("game_id", "1");
                startActivity(intent);
            }
        });

        ludoRegularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Result_activity.class);
                intent.putExtra("game_id", "3");
                startActivity(intent);
            }
        });

        ludoGrandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Result_activity.class);
                intent.putExtra("game_id", "4");
                startActivity(intent);
            }
        });

        freeFireCSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Result_free_fire_CS_activity.class));
            }
        });


        return view;
    }

    private void init_view(View view) {

        freeFireRegularButton = view.findViewById(R.id.freeFireRegularButtonID);
        ludoRegularButton = view.findViewById(R.id.ludoRegularButtonID);
        ludoGrandButton = view.findViewById(R.id.ludoGrandButtonID);
        freeFireCSButton = view.findViewById(R.id.freeFireCSButtonID);

    }


}
