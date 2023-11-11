package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
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
import code.fortomorrow.kheloNowAdmin.Activities.DailyMatchActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_image_add_activity;
import code.fortomorrow.kheloNowAdmin.Activities.OnGoing_activity;
import code.fortomorrow.kheloNowAdmin.Activities.OnGoing_free_fire_cs_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_onGoing_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.OngoingAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.M;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOnGoing extends Fragment {

    CardView freeFireRegularButton, ludoRegularButton, ludoGrandButton,freeFireCSButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_on_going, container, false);

        init_view(view);

        freeFireRegularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OnGoing_activity.class);
                intent.putExtra("game_id", "1");
                startActivity(intent);
            }
        });

        ludoRegularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OnGoing_activity.class);
                intent.putExtra("game_id", "3");
                startActivity(intent);
            }
        });

        ludoGrandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OnGoing_activity.class);
                intent.putExtra("game_id", "4");
                startActivity(intent);
            }
        });

        freeFireCSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OnGoing_free_fire_cs_activity.class));
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
