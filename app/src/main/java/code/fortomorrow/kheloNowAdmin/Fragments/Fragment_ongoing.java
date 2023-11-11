package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_image_add_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor.Arena_valor_ongoing_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_onGoing_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.OngoingAdapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_of_valor_match_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.M;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingResponse;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.Model.Support.Support_numbers_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_ongoing extends Fragment implements Arena_valor_ongoing_adapter.OnArenaValorPrizeClickListener, Arena_valor_ongoing_adapter.OnArenaValorSpectateClickListener, View.OnClickListener, Ludo_onGoing_game_list_adapter.OnSpectateClickListener, Ludo_onGoing_game_list_adapter.OnImageClickListener, Ludo_onGoing_game_list_adapter.OnItemClickListener, OngoingAdapter.OnPrizeClickListener, OngoingAdapter.OnWatchClickListener {

    APIService apiService;
    String jwt_token, secret_id;
    Session_management session_management;
    String gameID, gameKey;
    RecyclerView ongoingRV;
    ProgressBar progressBar;
    RelativeLayout mEmpty;

    private List<M> ongoingList = new ArrayList<>();
    private List<Ludo_tournament_game_list_response.M> ludoOnGoingList;
    private List<Support_numbers_response.M> numbersList;
    private Ludo_onGoing_game_list_adapter adapter;

    TextView gameText;
    private Boolean isStarted = false;
    private Boolean isVisible = false;
    Dialog loader;

    private List<Arena_of_valor_match_response.M> arenaValorOngoingList;
    private Arena_valor_ongoing_adapter arenaValorOngoingAdapter;

    LinearLayout noMatchesLayout, loadingLayout;

    @Override
    public void onStart() {
        super.onStart();

    }

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
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);

        init_view(view);

        gameID = session_management.getUserID();
        gameKey = session_management.getUserKey();

        //isStarted = true;
        //        if (isVisible && isStarted) {
        //            viewDidAppear();
        //        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        isStarted = true;
        if (isVisible && isStarted) {
            viewDidAppear();
        }
    }

    public void viewDidAppear() {


        if (gameID.equals("1")) {
            matchList(gameID);
        } else if (gameID.equals("2")) {
            matchList(gameID);
        } else if (gameID.equals("3")) {
            ludo_list_func(gameID);
        } else if (gameID.equals("4")) {
            ludo_list_func(gameID);
        } else if (gameID.equals("5")) {
            matchList(gameID);
        } else if (gameID.equals("6")) {
            matchList(gameID);
        } else if (gameID.equals("7")) {
            ludo_list_func(gameID);
        } else if (gameID.equals("8")) {
            ludo_list_func(gameID);
        } else if (gameID.equals("9")) {
            matchList(gameID);
        } else if (gameID.equals("10")) {
            matchList(gameID);
        } else if (gameID.equals("11")) {
            arena_of_valor_matches(gameID);
        } else if (gameID.equals("12")) {
            arena_of_valor_matches(gameID);
        }
        // your logic
        //Toast.makeText(getActivity(), "onging", Toast.LENGTH_SHORT).show();
    }

    private void arena_of_valor_matches(String gameID) {

        apiService.arena_of_valor_get_ongoing_match_list(secret_id, jwt_token, gameID).enqueue(new Callback<Arena_of_valor_match_response>() {
            @Override
            public void onResponse(Call<Arena_of_valor_match_response> call, Response<Arena_of_valor_match_response> response) {

                if (response.body().e == 0 && response.body().m.size() != 0) {

                    noMatchesLayout.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.VISIBLE);
                    arenaValorOngoingList = new ArrayList<>();
                    arenaValorOngoingList = response.body().m;
                    arenaValorOngoingAdapter = new Arena_valor_ongoing_adapter(arenaValorOngoingList);
                    arenaValorOngoingAdapter.setOnClickListener(Fragment_ongoing.this::OnArenaValorPrizeClick, Fragment_ongoing.this::onArenaValorSpectateClick);
                    ongoingRV.setAdapter(arenaValorOngoingAdapter);

                } else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Arena_of_valor_match_response> call, Throwable t) {
                noMatchesLayout.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
                ongoingRV.setVisibility(View.GONE);
            }
        });
    }

    private void init_view(View view) {
        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getActivity());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        session_management = new Session_management(getActivity());
        ongoingRV = view.findViewById(R.id.mBlogList);
        ongoingRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        noMatchesLayout = view.findViewById(R.id.noMatchesLayoutID);
        loadingLayout = view.findViewById(R.id.loadingLayoutID);

    }

    void matchList(String gameID) {


        ongoingList.clear();


        apiService.getOngoingResponse(secret_id, jwt_token, gameID).enqueue(new Callback<OngoingResponse>() {
            @Override
            public void onResponse(Call<OngoingResponse> call, Response<OngoingResponse> response) {

                if (response.body().getE() == 0 && response.body().getM().size() != 0) {

                    noMatchesLayout.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.VISIBLE);
                    ongoingList = response.body().getM();
                    OngoingAdapter ongoingAdapter = new OngoingAdapter(ongoingList);
                    ongoingAdapter.setOnClickListener(Fragment_ongoing.this::OnPrizeClick, Fragment_ongoing.this::OnWatchClick);

                    ongoingRV.setAdapter(ongoingAdapter);

                } else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<OngoingResponse> call, Throwable t) {
                noMatchesLayout.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
                ongoingRV.setVisibility(View.GONE);
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

                    noMatchesLayout.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.VISIBLE);
                    ludoOnGoingList = response.body().getM();
                    adapter = new Ludo_onGoing_game_list_adapter(ludoOnGoingList);
                    adapter.setOnItemClickListener(Fragment_ongoing.this::OnSpectateClick, Fragment_ongoing.this::OnImageClick);

                    ongoingRV.setAdapter(adapter);

                } else {

                    noMatchesLayout.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    ongoingRV.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Ludo_tournament_game_list_response> call, Throwable t) {

                noMatchesLayout.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
                ongoingRV.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnImageClick(int position) {
        Ludo_tournament_game_list_response.M response = ludoOnGoingList.get(position);
        String matchID = String.valueOf(response.getMatchId());

        Intent intent = new Intent(getActivity(), Ludo_image_add_activity.class);
        intent.putExtra("match_id", matchID);
        intent.putExtra("room_code", response.getRoomCode());
        startActivity(intent);


        /*try {
            if (response.getHasImage()) {
                //Toasty.error(getActivity(), "Image available", Toasty.LENGTH_SHORT).show();
                final String[] REGULAR_MATCH_LUDO_MATCH = {null};

                apiService.getSupportNumbers(secret_id, jwt_token).enqueue(new Callback<Support_numbers_response>() {
                    @Override
                    public void onResponse(Call<Support_numbers_response> call, Response<Support_numbers_response> response) {

                        if (response.body().getE() == 0) {
                            numbersList = new ArrayList<>();
                            numbersList = response.body().getM();
                            //adapter = new Support_numbers_adapter(numbersList);
                            //adapter.setOnItemClickListener(MainActivity.this::OnItemClick);
                            //supportNumbersView.setAdapter(adapter);
                            //Toast.makeText(getActivity(), numbersList.get(1).getNumber(), Toast.LENGTH_SHORT).show();
                            REGULAR_MATCH_LUDO_MATCH[0] = numbersList.get(1).getNumber();
                            String url = "https://api.whatsapp.com/send?phone=+88" + REGULAR_MATCH_LUDO_MATCH[0];
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);

                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<Support_numbers_response> call, Throwable t) {

                    }
                });

            }
        } catch (Exception c) {
            Intent intent = new Intent(getActivity(), Ludo_image_add_activity.class);
            intent.putExtra("match_id", matchID);
            intent.putExtra("room_code", response.getRoomCode());
            startActivity(intent);

            //Toast.makeText(getActivity(), response.getRoomCode(), Toast.LENGTH_SHORT).show();
        }*/


    }

    @Override
    public void OnSpectateClick(int position) {
        Toast.makeText(getActivity(), "Available soon", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void OnPrizeClick(int position) {
        code.fortomorrow.kheloNowAdmin.Model.Ongoing.M response = ongoingList.get(position);

        Dialog dialog = new Dialog(getActivity());
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
    }

    @Override
    public void onClick(View view) {

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
    public void OnItemClick(int position) {
        Ludo_tournament_game_list_response.M response = ludoOnGoingList.get(position);
        String matchID = String.valueOf(response.getMatchId());

        Toast.makeText(getActivity(), matchID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnArenaValorPrizeClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorOngoingList.get(position);

        String matchID = response.matchId.toString();

        String title = response.title;

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.arena_valor_prize_alert);
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

        TextView titleText = dialog.findViewById(R.id.titleTextID);
        TextView totalPrizeText = dialog.findViewById(R.id.totalPrizeTextID);
        LinearLayout closeButton = dialog.findViewById(R.id.closeButtonID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        titleText.setText(title);
        totalPrizeText.setText("Total prize: " + response.totalPrize + " tk");
    }

    @Override
    public void onArenaValorSpectateClick(int position) {
        Toast.makeText(getActivity(), "Available soon", Toast.LENGTH_SHORT).show();
    }
}