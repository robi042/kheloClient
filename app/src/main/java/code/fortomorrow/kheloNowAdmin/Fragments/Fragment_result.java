package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
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

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.Arena_of_valor_result_details_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_result_details_activity;
import code.fortomorrow.kheloNowAdmin.Activities.ResultDetailsActivity;
import code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor.Arena_valor_result_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_result_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.ResultAdapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_of_valor_match_response;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.Free_fire_paginated_result_response;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_result_pagination_response;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_result extends Fragment implements Arena_valor_result_adapter.OnArenaValorItemClickListener, Arena_valor_result_adapter.OnArenaValorPrizeClickListener, Arena_valor_result_adapter.OnArenaValorWatchClickListener, View.OnClickListener, Ludo_result_game_list_adapter.OnItemClickListener, ResultAdapter.OnPrizeClickListener, Ludo_result_game_list_adapter.OnLudoPrizeClickListener, ResultAdapter.OnWatchClickListener, ResultAdapter.OnFreeFireItemClickListener {

    TextView gameText;
    String gameID, gameKey;
    Session_management session_management;
    APIService apiService;
    String jwt_token, secret_id;
    String type;
    RecyclerView resultRV;

    RelativeLayout mEmpty;

    //private List<M> resultList = new ArrayList<>();

    //private List<Ludo_result_pagination_response.M> ludoResultGameList = new ArrayList<>();

    private List<Free_fire_paginated_result_response.M> resultList = new ArrayList<>();
    private List<Ludo_result_pagination_response.M> ludoResultGameList = new ArrayList<>();

    private Ludo_result_game_list_adapter adapter;
    private ResultAdapter freeFireAdapter;

    private Boolean isStarted = false;
    private Boolean isVisible = false;

    Dialog loader;
    LinearLayout loadingLayout;

    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 2;

    List<Arena_of_valor_match_response.M> arenaValorResultList = new ArrayList<>();
    private Arena_valor_result_adapter arenaValorResultAdapter;

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible && isStarted) {
            viewDidAppear();
        }
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
        View view = inflater.inflate(R.layout.fragment_result2, container, false);

        init_view(view);

        gameID = session_management.getUserID();
        gameKey = session_management.getUserKey();

        if (Integer.parseInt(secret_id) % 2 == 0) {
            type = "even";
        } else {
            type = "odd";
        }

       //isStarted = true;
        //        if (isVisible && isStarted) {
        //            viewDidAppear();
        //        }


        //Toast.makeText(getActivity(), "result", Toast.LENGTH_SHORT).show();
        nestedScrollView = view.findViewById(R.id.nestedScrollViewID);
        progressBar = view.findViewById(R.id.progressBarID);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    get_result(gameID, page);

                }
            }
        });
        return view;
    }

    private void get_result(String gameID, int page) {

        if (gameID.equals("1")) {
            matchList(gameID, page);
        } else if (gameID.equals("2")) {
            matchList(gameID, page);
        } else if (gameID.equals("3")) {
            ludo_list_func(gameID, page);
        } else if (gameID.equals("4")) {
            ludo_list_func(gameID, page);
        } else if (gameID.equals("5")) {
            matchList(gameID, page);
        } else if (gameID.equals("6")) {
            matchList(gameID, page);
        } else if (gameID.equals("7")) {
            ludo_list_func(gameID, page);
        } else if (gameID.equals("8")) {
            ludo_list_func(gameID, page);
        } else if (gameID.equals("9")) {
            matchList(gameID, page);
        } else if (gameID.equals("10")) {
            matchList(gameID, page);
        } else if (gameID.equals("11")) {
            arena_of_valor_matches(gameID);
        } else if (gameID.equals("12")) {
            arena_of_valor_matches(gameID);
        }
    }

    public void viewDidAppear() {
        // your logic
        //Toast.makeText(getActivity(), gameID, Toast.LENGTH_SHORT).show();
        resultRV.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);

        ludoResultGameList.clear();
        resultList.clear();
        arenaValorResultList.clear();
        page = 1;
        //adapter.notifyDataSetChanged();

        get_result(gameID, page);

    }

    private void init_view(View view) {

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        session_management = new Session_management(getActivity());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getActivity());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx", secret_id+" "+jwt_token);

        resultRV = view.findViewById(R.id.mBlogList);
        resultRV.setHasFixedSize(true);
        resultRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        resultRV.setItemViewCacheSize(150);

        mEmpty = view.findViewById(R.id.mEmpty);
        loadingLayout = view.findViewById(R.id.loadingLayoutID);

    }

    private void arena_of_valor_matches(String gameID) {

        apiService.arena_of_valor_get_result_match_list(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<Arena_of_valor_match_response>() {
            @Override
            public void onResponse(Call<Arena_of_valor_match_response> call, Response<Arena_of_valor_match_response> response) {
                if (response.body().e == 0 && response.body().m.size() != 0) {

                    resultRV.setVisibility(View.VISIBLE);
                    mEmpty.setVisibility(View.GONE);

                    for (int i = 0; i < response.body().m.size() - 1; i++) {
                        arenaValorResultList.add(response.body().m.get(i));
                    }

                    arenaValorResultAdapter = new Arena_valor_result_adapter(arenaValorResultList);
                    progressBar.setVisibility(View.GONE);

                    if (arenaValorResultList.size() == 0) {
                        resultRV.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }
                    arenaValorResultAdapter.setOnClickListener(Fragment_result.this::OnArenaValorItemClick, Fragment_result.this::OnArenaValorPrizeClick, Fragment_result.this::onArenaValorWatchClick);
                    loadingLayout.setVisibility(View.GONE);
                    resultRV.setAdapter(arenaValorResultAdapter);

                } else {
                    mEmpty.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    resultRV.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Arena_of_valor_match_response> call, Throwable t) {

            }
        });

    }

    private void ludo_list_func(String gameID, int page) {

        /*ludoResultGameList.clear();
        loader.show();
        apiService.getResultLudoMatchList(secret_id, jwt_token, gameID).enqueue(new Callback<Ludo_tournament_game_list_response>() {
            @Override
            public void onResponse(Call<Ludo_tournament_game_list_response> call, Response<Ludo_tournament_game_list_response> response) {
                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {

                    resultRV.setVisibility(View.VISIBLE);
                    mEmpty.setVisibility(View.GONE);
                    ludoResultGameList = response.body().getM();

                    //Toast.makeText(getActivity(), String.valueOf(ludoResultGameList.size()), Toast.LENGTH_SHORT).show();
                    adapter = new Ludo_result_game_list_adapter(ludoResultGameList);
                    adapter.setOnItemClickListener(Fragment_result.this::OnItemClick, Fragment_result.this::OnLudoPrizeClick);

                    resultRV.setAdapter(adapter);
                } else {

                    resultRV.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Ludo_tournament_game_list_response> call, Throwable t) {
                resultRV.setVisibility(View.GONE);
                mEmpty.setVisibility(View.VISIBLE);
                loader.dismiss();

            }
        });*/

        //loader.show();
        apiService.getPaginatedLudoResultMatchList(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<Ludo_result_pagination_response>() {
            @Override
            public void onResponse(Call<Ludo_result_pagination_response> call, Response<Ludo_result_pagination_response> response) {

                //loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    resultRV.setVisibility(View.VISIBLE);
                    mEmpty.setVisibility(View.GONE);

                    for (int i = 0; i < response.body().getM().size() - 1; i++) {
                        ludoResultGameList.add(response.body().getM().get(i));
                    }

                    adapter = new Ludo_result_game_list_adapter(ludoResultGameList);
                    progressBar.setVisibility(View.GONE);

                    if (ludoResultGameList.size() == 0) {
                        resultRV.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }
                    adapter.setOnItemClickListener(Fragment_result.this::OnItemClick, Fragment_result.this::OnLudoPrizeClick);
                    adapter.notifyDataSetChanged();
                    loadingLayout.setVisibility(View.GONE);
                    resultRV.setAdapter(adapter);
                    //Toast.makeText(getActivity(), String.valueOf(page + " " + ludoResultGameList.size()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ludo_result_pagination_response> call, Throwable t) {
                //loader.dismiss();
                resultRV.setVisibility(View.GONE);
                mEmpty.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
            }
        });

    }

    private void matchList(String gameID, int page) {


        //resultList.clear();
        if (type.equals("odd")) {
            /*loader.show();
            apiService.getResultResponseOne(secret_id, jwt_token, gameID).enqueue(new Callback<GetResultResponse>() {
                @Override
                public void onResponse(Call<GetResultResponse> call, Response<GetResultResponse> response) {
                    loader.dismiss();
                    if (response.body().getE() == 0 && response.body().getM().size() != 0) {


                        resultRV.setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);

                        resultList = response.body().getM();
                        ResultAdapter resultAdapter = new ResultAdapter(response.body().getM());
                        resultAdapter.setOnClickListener(Fragment_result.this::OnPrizeClick, Fragment_result.this::OnWatchClick, Fragment_result.this::OnFreeFireItemClick);

                        resultRV.setAdapter(resultAdapter);
                    } else {

                        mEmpty.setVisibility(View.VISIBLE);
                        resultRV.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetResultResponse> call, Throwable t) {
                    //Log.d("results", new Gson().toJson(t.toString()));
                    resultRV.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                    loader.dismiss();

                }
            });*/

            apiService.getResultResponseOne(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<Free_fire_paginated_result_response>() {
                @Override
                public void onResponse(Call<Free_fire_paginated_result_response> call, Response<Free_fire_paginated_result_response> response) {

                    if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                        resultRV.setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);

                        for (int i = 0; i < response.body().getM().size() - 1; i++) {
                            resultList.add(response.body().getM().get(i));
                        }

                        adapter = new Ludo_result_game_list_adapter(ludoResultGameList);
                        progressBar.setVisibility(View.GONE);

                        if (resultList.size() == 0) {
                            resultRV.setVisibility(View.GONE);
                            mEmpty.setVisibility(View.VISIBLE);
                        }
                        freeFireAdapter = new ResultAdapter(resultList);
                        freeFireAdapter.setOnClickListener(Fragment_result.this::OnPrizeClick, Fragment_result.this::OnWatchClick, Fragment_result.this::OnFreeFireItemClick);
                        loadingLayout.setVisibility(View.GONE);
                        resultRV.setAdapter(freeFireAdapter);
                    }

                }

                @Override
                public void onFailure(Call<Free_fire_paginated_result_response> call, Throwable t) {
                    resultRV.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                }
            });

        } else if (type.equals("even")) {
            /*loader.show();
            apiService.getResultResponseTwo(secret_id, jwt_token, gameID).enqueue(new Callback<GetResultResponse>() {
                @Override
                public void onResponse(Call<GetResultResponse> call, Response<GetResultResponse> response) {
                    loader.dismiss();
                    if (response.body().getE() == 0 && response.body().getM().size() != 0) {

                        resultRV.setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);

                        resultList = response.body().getM();

                        ResultAdapter resultAdapter = new ResultAdapter(response.body().getM());
                        resultAdapter.setOnClickListener(Fragment_result.this::OnPrizeClick, Fragment_result.this::OnWatchClick, Fragment_result.this::OnFreeFireItemClick);

                        resultRV.setAdapter(resultAdapter);
                    } else {

                        mEmpty.setVisibility(View.VISIBLE);
                        resultRV.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetResultResponse> call, Throwable t) {
                    //Log.d("results", new Gson().toJson(t.toString()));
                    loader.dismiss();
                    mEmpty.setVisibility(View.VISIBLE);
                    resultRV.setVisibility(View.GONE);

                }
            });*/

            apiService.getResultResponseTwo(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<Free_fire_paginated_result_response>() {
                @Override
                public void onResponse(Call<Free_fire_paginated_result_response> call, Response<Free_fire_paginated_result_response> response) {

                    if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                        resultRV.setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);

                        for (int i = 0; i < response.body().getM().size() - 1; i++) {
                            resultList.add(response.body().getM().get(i));
                        }

                        adapter = new Ludo_result_game_list_adapter(ludoResultGameList);
                        progressBar.setVisibility(View.GONE);

                        if (resultList.size() == 0) {
                            resultRV.setVisibility(View.GONE);
                            mEmpty.setVisibility(View.VISIBLE);
                        }
                        freeFireAdapter = new ResultAdapter(resultList);
                        freeFireAdapter.setOnClickListener(Fragment_result.this::OnPrizeClick, Fragment_result.this::OnWatchClick, Fragment_result.this::OnFreeFireItemClick);
                        loadingLayout.setVisibility(View.GONE);
                        resultRV.setAdapter(freeFireAdapter);
                    }

                }

                @Override
                public void onFailure(Call<Free_fire_paginated_result_response> call, Throwable t) {
                    resultRV.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                }
            });
        }

    }

    @Override
    public void OnItemClick(int position) {
        Ludo_result_pagination_response.M response = ludoResultGameList.get(position);

        String matchID = String.valueOf(response.getMatchId());

        //Toast.makeText(getActivity(), matchID, Toast.LENGTH_SHORT).show();

        //ludoResultGameList.clear();
        //page = 1;

        Intent intent = new Intent(getActivity(), Ludo_result_details_activity.class);
        intent.putExtra("match_id", matchID);
        intent.putExtra("game_title", response.getTitle());
        intent.putExtra("entry_fee", response.getEntryFee());
        intent.putExtra("total_prize", response.getTotalPrize());
        intent.putExtra("date_time", response.getMatchDate() + " " + response.getMatchTime());
        startActivity(intent);
    }

    @Override
    public void OnPrizeClick(int position) {
        Free_fire_paginated_result_response.M response = resultList.get(position);

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
    public void OnLudoPrizeClick(int position) {
        Ludo_result_pagination_response.M response = ludoResultGameList.get(position);

        //Toast.makeText(getActivity(), response.getTitle(), Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.ludo_prize_pop_up_alert);
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

        LinearLayout closeButton = dialog.findViewById(R.id.closeButtonID);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView oneText = dialog.findViewById(R.id.oneTextID);
        TextView titleText = dialog.findViewById(R.id.titleTextID);

        titleText.setText(response.getTitle());
        oneText.setText("Winner: " + response.getTotalPrize() + " tk");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void OnWatchClick(int position) {
        //"vnd.youtube://youtube.com/channel/" + "UCxjeDCkQgtpZhmCIa960r_w"
        // Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCxjeDCkQgtpZhmCIa960r_w"));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
        }
    }

    @Override
    public void OnFreeFireItemClick(int position) {
        //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();

        Free_fire_paginated_result_response.M response = resultList.get(position);

        Intent i = new Intent(getActivity(), ResultDetailsActivity.class);
        i.putExtra("title", response.getTitle());
        i.putExtra("type", response.getGameType());
        i.putExtra("entryfee", response.getEntryFee());
        i.putExtra("time", response.getMatchTime());
        i.putExtra("winningprize", response.getTotalPrize());
        i.putExtra("perkill", response.getPerKillRate());
        i.putExtra("match_id", String.valueOf(response.getMatchId()));
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(i);

    }

    @Override
    public void onResume() {
        super.onResume();

        isStarted = true;
        if (isVisible && isStarted) {
            viewDidAppear();
        }
    }

    @Override
    public void OnArenaValorPrizeClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorResultList.get(position);

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
    public void onArenaValorWatchClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorResultList.get(position);

        String gameType = response.gameType;

        go_to_video_link(gameType);


    }

    @Override
    public void OnArenaValorItemClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorResultList.get(position);

        String matchID = response.matchId.toString();

        Intent intent = new Intent(getActivity(), Arena_of_valor_result_details_activity.class);
        intent.putExtra("match_id", matchID);
        intent.putExtra("title", response.title);
        intent.putExtra("schedule_time", response.matchDate + " " + response.matchTime);
        intent.putExtra("winning_prize", response.totalPrize);
        intent.putExtra("entry_fee", response.entryFee);
        startActivity(intent);
    }

    private void go_to_video_link(String videoType) {
        apiService.getVideoLink(secret_id, jwt_token, videoType).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if (response.body().getE() == 0) {
                    //Toast.makeText(getActivity(), response.body().getM(), Toast.LENGTH_SHORT).show();
                    String l = response.body().getM();
                    Uri uri = Uri.parse(l); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });
    }
}