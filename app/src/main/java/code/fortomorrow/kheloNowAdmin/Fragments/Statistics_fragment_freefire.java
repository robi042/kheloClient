package code.fortomorrow.kheloNowAdmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.StatisticsAdapter;
import code.fortomorrow.kheloNowAdmin.Model.Statistics.M;
import code.fortomorrow.kheloNowAdmin.Model.Statistics.StatisticsResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Statistics_fragment_freefire extends Fragment {

    RecyclerView recyclerView;
    APIService apiService;
    String jwt_token, secret_id;
    LinearLayout noMatchesLayout;

    private Boolean isStarted = false;
    private Boolean isVisible = false;
    private List<M> statisticsList;
    HorizontalScrollView horizontalScrollView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            viewDidAppear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics_freefire, container, false);

        init_view(view);

        isStarted = true;

        if (isVisible && isStarted) {
            viewDidAppear();
        }


        return view;
    }

    public void viewDidAppear() {
        // your logic
        //Toast.makeText(getActivity(), "live", Toast.LENGTH_SHORT).show();
        get_stats();

    }

    private void get_stats() {
        apiService.getStatistics(secret_id, jwt_token).enqueue(new Callback<StatisticsResponse>() {
            @Override
            public void onResponse(Call<StatisticsResponse> call, Response<StatisticsResponse> response) {
                if (response.body().getE() == 0 && response.body().getM().size()!=0) {
                    noMatchesLayout.setVisibility(View.GONE);
                    horizontalScrollView.setVisibility(View.VISIBLE);
                    statisticsList = new ArrayList<>();
                    statisticsList = response.body().getM();
                    //Toast.makeText(getActivity(), String.valueOf(response.body().getM().size()), Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(new StatisticsAdapter(getChildFragmentManager(), statisticsList));
                }else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    horizontalScrollView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StatisticsResponse> call, Throwable t) {
                noMatchesLayout.setVisibility(View.VISIBLE);
                horizontalScrollView.setVisibility(View.GONE);
            }
        });
    }

    private void init_view(View view) {
        recyclerView = view.findViewById(R.id.rv_statistics);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        noMatchesLayout = view.findViewById(R.id.noMatchesLayoutID);
        EasySharedPref.init(getActivity());

        horizontalScrollView = view.findViewById(R.id.horizontalScrollViewID);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
    }
}