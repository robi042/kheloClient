package code.fortomorrow.kheloNowAdmin.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.Arena_of_valor_3_vs_3_slot_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Arena_of_valor_5_vs_5_slot_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Arena_of_valor_rules_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor.Arena_valor_match_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.MatchAdapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_of_valor_match_response;
import code.fortomorrow.kheloNowAdmin.Model.GetMatch.FreeFire_paginated_live_response;
import code.fortomorrow.kheloNowAdmin.Model.GetMatch.M;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.getRoomIdPass.GetRoomIdResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_live extends Fragment implements View.OnClickListener, Arena_valor_match_adapter.OnArenaValorRoomDetailsClickListener, Arena_valor_match_adapter.OnArenaValorItemClickListener, Arena_valor_match_adapter.OnArenaValorJoinButtonClickListener, Arena_valor_match_adapter.OnArenaValorPrizeClickListener, Arena_valor_match_adapter.OnArenaValorVideoClickListener, MatchAdapter.OnPrizeClickListener, MatchAdapter.OnVideoClickListener, MatchAdapter.OnRoomDetailsClickListener {

    String gameID, gameKey, playTypeID;
    Session_management session_management;
    APIService apiService;
    String jwt_token, secret_id;
    String type;
    RecyclerView mBlogList;

    LinearLayout noMatchesLayout, loadingLayout;

    List<M> freeFireList = new ArrayList<>();


    private Boolean isStarted = false;
    private Boolean isVisible = false;

    Dialog loader;
    String videoType;

    private List<FreeFire_paginated_live_response.M> freeFireLiveList = new ArrayList<>();

    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1;

    private List<Arena_of_valor_match_response.M> arenaValorMatchList = new ArrayList<>();
    private Arena_valor_match_adapter arenaValorMatchAdapter;

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
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        init_view(view);


        if (Integer.parseInt(secret_id) % 2 == 0) {
            type = "even";
        } else {
            type = "odd";
        }

        //isStarted = true;
        //        if (isVisible && isStarted) {
        //            viewDidAppear();
        //        }


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    get_matches(gameID, page);

                }
            }
        });

        return view;
    }

    public void viewDidAppear() {

        mBlogList.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        freeFireLiveList.clear();
        arenaValorMatchList.clear();
        page = 1;
        get_matches(gameID, page);
    }

    private void get_matches(String gameID, int page) {
        //Toast.makeText(getActivity(), gameID, Toast.LENGTH_SHORT).show();

        if (gameID.equals("6")) {
            scrims_matches(gameID, page);
        } else if (gameID.equals("11") || gameID.equals("12")) {
            arena_of_valor_matches(gameID, page);
        } else {
            checkMatchHayKeya(gameID, page);
        }
    }

    private void arena_of_valor_matches(String gameID, int page) {
        apiService.arena_of_valor_get_created_match_list(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<Arena_of_valor_match_response>() {
            @Override
            public void onResponse(Call<Arena_of_valor_match_response> call, Response<Arena_of_valor_match_response> response) {

                if (response.body().e == 0 && response.body().m.size() != 0) {
                    noMatchesLayout.setVisibility(View.GONE);
                    mBlogList.setVisibility(View.VISIBLE);

                    for (int i = 0; i < response.body().m.size() - 1; i++) {
                        arenaValorMatchList.add(response.body().m.get(i));
                    }

                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(), String.valueOf(freeFireLiveList.size()), Toast.LENGTH_SHORT).show();
                    if (arenaValorMatchList.size() == 0) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                    }

                    arenaValorMatchAdapter = new Arena_valor_match_adapter(arenaValorMatchList);
                    loadingLayout.setVisibility(View.GONE);
                    arenaValorMatchAdapter.setOnClickListener(Fragment_live.this::OnArenaValorPrizeClick, Fragment_live.this::onArenaValorVideoClick, Fragment_live.this::onArenaValorJoinClick, Fragment_live.this::OnArenaValorItemClick, Fragment_live.this::OnArenaValorRoomDetailsClick);
                    mBlogList.setAdapter(arenaValorMatchAdapter);
                } else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    mBlogList.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Arena_of_valor_match_response> call, Throwable t) {
                noMatchesLayout.setVisibility(View.VISIBLE);
                mBlogList.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
            }
        });


    }

    private void scrims_matches(String gameID, int page) {

        /*loader.show();
        apiService.getPaginatedCSFreeFireMatchList(secret_id, jwt_token, gameID).enqueue(new Callback<GetMatchResponse>() {
            @Override
            public void onResponse(Call<GetMatchResponse> call, Response<GetMatchResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    //Log.d("checkaaaa", new Gson().toJson(response.body().getM()));
                    //Toast.makeText(getActivity(), "regualr"+" "+String.valueOf(response.body().getM().size()), Toast.LENGTH_SHORT).show();
                    noMatchesLayout.setVisibility(View.GONE);
                    mBlogList.setVisibility(View.VISIBLE);
                    freeFireList = response.body().getM();
                    MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                    matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick);
                    mBlogList.setAdapter(matchAdapter);
                } else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    mBlogList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetMatchResponse> call, Throwable t) {
                noMatchesLayout.setVisibility(View.VISIBLE);
                mBlogList.setVisibility(View.GONE);
                loader.dismiss();
            }
        });*/

        apiService.getCreatedScrimsMatchList(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<FreeFire_paginated_live_response>() {
            @Override
            public void onResponse(Call<FreeFire_paginated_live_response> call, Response<FreeFire_paginated_live_response> response) {

                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    noMatchesLayout.setVisibility(View.GONE);
                    mBlogList.setVisibility(View.VISIBLE);

                    for (int i = 0; i < response.body().getM().size() - 1; i++) {
                        freeFireLiveList.add(response.body().getM().get(i));
                    }

                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(), String.valueOf(freeFireLiveList.size()), Toast.LENGTH_SHORT).show();
                    if (freeFireLiveList.size() == 0) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                    }

                    MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                    matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                    loadingLayout.setVisibility(View.GONE);
                    mBlogList.setAdapter(matchAdapter);
                } else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    mBlogList.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FreeFire_paginated_live_response> call, Throwable t) {
                noMatchesLayout.setVisibility(View.VISIBLE);
                mBlogList.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        isStarted = true;
        if (isVisible && isStarted) {
            viewDidAppear();
        }

    }

    private void checkMatchHayKeya(String gameID, int page) {

        if (gameKey.equals("cs_match_get")) {

            if (type.equals("odd")) {

                /*loader.show();
                apiService.getCSmatchOne(secret_id, jwt_token, gameID).enqueue(new Callback<GetMatchResponse>() {
                    @Override
                    public void onResponse(Call<GetMatchResponse> call, Response<GetMatchResponse> response) {

                        loader.dismiss();
                        if (response.body().getE()==0 && response.body().getM().size() != 0) {
                            //Log.d("checkaaaa", new Gson().toJson(response.body().getM()));
                            //Toast.makeText(getActivity(), "CS"+" "+String.valueOf(response.body().getM().size()), Toast.LENGTH_SHORT).show();
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);
                            freeFireList = response.body().getM();
                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), response.body().getM());
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                            mBlogList.setAdapter(matchAdapter);
                        } else {

                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onFailure(Call<GetMatchResponse> call, Throwable t) {

                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loader.dismiss();

                    }
                });*/

                apiService.getCSmatchOne(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<FreeFire_paginated_live_response>() {
                    @Override
                    public void onResponse(Call<FreeFire_paginated_live_response> call, Response<FreeFire_paginated_live_response> response) {

                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);

                            for (int i = 0; i < response.body().getM().size() - 1; i++) {
                                freeFireLiveList.add(response.body().getM().get(i));
                            }

                            progressBar.setVisibility(View.GONE);

                            if (freeFireLiveList.size() == 0) {
                                noMatchesLayout.setVisibility(View.VISIBLE);
                                mBlogList.setVisibility(View.GONE);
                            }

                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                            loadingLayout.setVisibility(View.GONE);
                            mBlogList.setAdapter(matchAdapter);
                        } else {
                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<FreeFire_paginated_live_response> call, Throwable t) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                    }
                });

                /*apiService.get_separated_cs_list_one(secret_id, jwt_token, gameID, playTypeID, String.valueOf(page)).enqueue(new Callback<FreeFire_paginated_live_response>() {
                    @Override
                    public void onResponse(Call<FreeFire_paginated_live_response> call, Response<FreeFire_paginated_live_response> response) {

                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);

                            for (int i = 0; i < response.body().getM().size() - 1; i++) {
                                freeFireLiveList.add(response.body().getM().get(i));
                            }

                            progressBar.setVisibility(View.GONE);

                            if (freeFireLiveList.size() == 0) {
                                noMatchesLayout.setVisibility(View.VISIBLE);
                                mBlogList.setVisibility(View.GONE);
                            }

                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                            loadingLayout.setVisibility(View.GONE);
                            mBlogList.setAdapter(matchAdapter);
                        } else {
                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<FreeFire_paginated_live_response> call, Throwable t) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                    }
                });*/


            } else if (type.equals("even")) {
                /*loader.show();
                apiService.getCSmatchTwo(secret_id, jwt_token, gameID).enqueue(new Callback<GetMatchResponse>() {
                    @Override
                    public void onResponse(Call<GetMatchResponse> call, Response<GetMatchResponse> response) {
                        loader.dismiss();
                        if (response.body().getE()==0 && response.body().getM().size() != 0) {
                            //Log.d("checkaaaa", new Gson().toJson(response.body().getM()));
                            //Toast.makeText(getActivity(), "CS"+" "+String.valueOf(response.body().getM().size()), Toast.LENGTH_SHORT).show();

                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);
                            freeFireList = response.body().getM();
                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), response.body().getM());
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);

                            mBlogList.setAdapter(matchAdapter);
                        } else {

                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onFailure(Call<GetMatchResponse> call, Throwable t) {

                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loader.dismiss();

                    }
                });*/

                apiService.getCSmatchTwo(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<FreeFire_paginated_live_response>() {
                    @Override
                    public void onResponse(Call<FreeFire_paginated_live_response> call, Response<FreeFire_paginated_live_response> response) {

                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);

                            for (int i = 0; i < response.body().getM().size() - 1; i++) {
                                freeFireLiveList.add(response.body().getM().get(i));
                            }

                            progressBar.setVisibility(View.GONE);

                            if (freeFireLiveList.size() == 0) {
                                noMatchesLayout.setVisibility(View.VISIBLE);
                                mBlogList.setVisibility(View.GONE);
                            }

                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                            loadingLayout.setVisibility(View.GONE);
                            mBlogList.setAdapter(matchAdapter);
                        } else {
                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<FreeFire_paginated_live_response> call, Throwable t) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                    }
                });

  /*              apiService.get_separated_cs_list_two(secret_id, jwt_token, gameID, playTypeID, String.valueOf(page)).enqueue(new Callback<FreeFire_paginated_live_response>() {
                    @Override
                    public void onResponse(Call<FreeFire_paginated_live_response> call, Response<FreeFire_paginated_live_response> response) {

                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);

                            for (int i = 0; i < response.body().getM().size() - 1; i++) {
                                freeFireLiveList.add(response.body().getM().get(i));
                            }

                            progressBar.setVisibility(View.GONE);

                            if (freeFireLiveList.size() == 0) {
                                noMatchesLayout.setVisibility(View.VISIBLE);
                                mBlogList.setVisibility(View.GONE);
                            }

                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                            loadingLayout.setVisibility(View.GONE);
                            mBlogList.setAdapter(matchAdapter);
                        } else {
                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<FreeFire_paginated_live_response> call, Throwable t) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                    }
                });
*/
            }


        } else if (gameKey.equals("free_fire_match_get")) {


            if (type.equals("odd")) {
                //getRegularmatchOne  getCreatedTestMatchList
                /*loader.show();
                apiService.getRegularmatchOne(secret_id, jwt_token, gameID).enqueue(new Callback<GetMatchResponse>() {
                    @Override
                    public void onResponse(Call<GetMatchResponse> call, Response<GetMatchResponse> response) {
                        loader.dismiss();
                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);


                            freeFireList = response.body().getM();
                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);

                            mBlogList.setAdapter(matchAdapter);
                        } else {

                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);


                        }
                    }

                    @Override
                    public void onFailure(Call<GetMatchResponse> call, Throwable t) {

                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loader.dismiss();

                    }
                });*/

                //Log.d("tokenxx", secret_id+" "+jwt_token);

                apiService.getRegularmatchOne(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<FreeFire_paginated_live_response>() {
                    @Override
                    public void onResponse(Call<FreeFire_paginated_live_response> call, Response<FreeFire_paginated_live_response> response) {

                        //Toast.makeText(getActivity(), response.body().getE().toString(), Toast.LENGTH_SHORT).show();

                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);

                            for (int i = 0; i < response.body().getM().size() - 1; i++) {
                                freeFireLiveList.add(response.body().getM().get(i));
                            }

                            progressBar.setVisibility(View.GONE);

                            if (freeFireLiveList.size() == 0) {
                                noMatchesLayout.setVisibility(View.VISIBLE);
                                mBlogList.setVisibility(View.GONE);
                            }

                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                            loadingLayout.setVisibility(View.GONE);
                            mBlogList.setAdapter(matchAdapter);
                        } else {
                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<FreeFire_paginated_live_response> call, Throwable t) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                    }
                });

            } else if (type.equals("even")) {
                //getCreatedTestMatchList  getRegularmatchTwo
                /*loader.show();
                apiService.getRegularmatchTwo(secret_id, jwt_token, gameID).enqueue(new Callback<GetMatchResponse>() {
                    @Override
                    public void onResponse(Call<GetMatchResponse> call, Response<GetMatchResponse> response) {
                        loader.dismiss();
                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {

                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);
                            freeFireList = response.body().getM();
                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);

                            mBlogList.setAdapter(matchAdapter);
                        } else {

                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);
                            //
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMatchResponse> call, Throwable t) {

                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loader.dismiss();
                        //
                    }
                });*/

                apiService.getRegularmatchTwo(secret_id, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<FreeFire_paginated_live_response>() {
                    @Override
                    public void onResponse(Call<FreeFire_paginated_live_response> call, Response<FreeFire_paginated_live_response> response) {
                        //Toast.makeText(getActivity(), response.body().getE().toString()+"e", Toast.LENGTH_SHORT).show();
                        if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                            noMatchesLayout.setVisibility(View.GONE);
                            mBlogList.setVisibility(View.VISIBLE);

                            for (int i = 0; i < response.body().getM().size() - 1; i++) {
                                freeFireLiveList.add(response.body().getM().get(i));
                            }

                            progressBar.setVisibility(View.GONE);

                            if (freeFireLiveList.size() == 0) {
                                noMatchesLayout.setVisibility(View.VISIBLE);
                                mBlogList.setVisibility(View.GONE);
                            }

                            MatchAdapter matchAdapter = new MatchAdapter(Fragment_live.this, getActivity(), freeFireLiveList);
                            matchAdapter.setOnClickListener(Fragment_live.this::OnPrizeClick, Fragment_live.this::OnVideoClick, Fragment_live.this::OnRoomDetailsClick);
                            loadingLayout.setVisibility(View.GONE);
                            mBlogList.setAdapter(matchAdapter);
                        } else {
                            noMatchesLayout.setVisibility(View.VISIBLE);
                            mBlogList.setVisibility(View.GONE);
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<FreeFire_paginated_live_response> call, Throwable t) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        mBlogList.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                    }
                });
            }

        }
    }

    private void init_view(View view) {
        session_management = new Session_management(getActivity());
        gameID = session_management.getUserID();
        gameKey = session_management.getUserKey();
        playTypeID = session_management.getPlayTypeID();

        //Toast.makeText(getActivity(), playTypeID, Toast.LENGTH_SHORT).show();

        nestedScrollView = view.findViewById(R.id.nestedScrollViewID);
        progressBar = view.findViewById(R.id.progressBarID);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getActivity());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx", gameID+" "+secret_id + " " + jwt_token);
        noMatchesLayout = view.findViewById(R.id.noMatchesLayoutID);

        mBlogList = view.findViewById(R.id.mMatchRV);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadingLayout = view.findViewById(R.id.loadingLayoutID);
    }

    public void showPass(String matchId, String title, String matchTime, Boolean isJoined) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
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

                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("", response.body().getM().getRoomId());
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(getActivity(), "Room ID copied", Toast.LENGTH_SHORT).show();

                        }
                    });

                    mPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("", response.body().getM().getRoomPass());
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(getActivity(), "Password copied", Toast.LENGTH_SHORT).show();

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

        FreeFire_paginated_live_response.M response = freeFireLiveList.get(position);

        //Toast.makeText(getActivity(),type+" "+ String.valueOf(response.matchId), Toast.LENGTH_SHORT).show();

        String title = response.getTitle();
        String schedule = response.getMatchTime();

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
        TextView fourText = dialog.findViewById(R.id.fourTextID);
        TextView fiveText = dialog.findViewById(R.id.fiveTextID);
        TextView sixText = dialog.findViewById(R.id.sixTextID);
        TextView sevenText = dialog.findViewById(R.id.sevenTextID);
        TextView eightText = dialog.findViewById(R.id.eightTextID);
        TextView nineText = dialog.findViewById(R.id.nineTextID);
        TextView tenText = dialog.findViewById(R.id.tenTextID);

        LinearLayout oneLayout = dialog.findViewById(R.id.oneLayoutID);
        LinearLayout twoLayout = dialog.findViewById(R.id.twoLayoutID);
        LinearLayout threeLayout = dialog.findViewById(R.id.threeLayoutID);
        LinearLayout fourLayout = dialog.findViewById(R.id.fourLayoutID);
        LinearLayout fiveLayout = dialog.findViewById(R.id.fiveLayoutID);
        LinearLayout sixLayout = dialog.findViewById(R.id.sixLayoutID);
        LinearLayout sevenLayout = dialog.findViewById(R.id.sevenLayoutID);
        LinearLayout eightLayout = dialog.findViewById(R.id.eightLayoutID);
        LinearLayout nineLayout = dialog.findViewById(R.id.nineLayoutID);
        LinearLayout tenLayout = dialog.findViewById(R.id.tenLayoutID);

        TextView perKillText = dialog.findViewById(R.id.perKillTextID);
        TextView totalPrizeText = dialog.findViewById(R.id.totalPrizeTextID);
        TextView titleText = dialog.findViewById(R.id.titleTextID);
        TextView scheduleText = dialog.findViewById(R.id.scheduleTextID);

        titleText.setText(title);
        scheduleText.setText(schedule);

        LinearLayout closeButton = dialog.findViewById(R.id.closeButtonID);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        perKillText.setText("Per Kill: " + response.getPerKillRate() + " tk");
        totalPrizeText.setText("Total prize: " + response.getTotalPrize() + " tk");

        try {
            if (response.getHasFirstPrize()) {
                oneLayout.setVisibility(View.VISIBLE);
                oneText.setText("First prize: " + response.getFirstPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasSecondPrize()) {
                twoLayout.setVisibility(View.VISIBLE);
                twoText.setText("Second prize: " + response.getSecondPrize() + " tk");

            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasThirdPrize()) {
                threeLayout.setVisibility(View.VISIBLE);
                threeText.setText("Third prize: " + response.getThirdPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasFourthPrize()) {
                fourLayout.setVisibility(View.VISIBLE);
                fourText.setText("Fourth prize: " + response.getFourthPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasFifthPrize()) {
                fiveLayout.setVisibility(View.VISIBLE);
                fiveText.setText("Fifth prize: " + response.getFifthPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasSixthPrize()) {
                sixLayout.setVisibility(View.VISIBLE);
                sixText.setText("Sixth prize: " + response.getSixthPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasSeventhPrize()) {
                sevenLayout.setVisibility(View.VISIBLE);
                sevenText.setText("Seventh prize: " + response.getSeventhPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasEightthPrize()) {
                eightLayout.setVisibility(View.VISIBLE);
                eightText.setText("Eight prize: " + response.getEightthPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasNinethPrize()) {
                nineLayout.setVisibility(View.VISIBLE);
                nineText.setText("Ninth prize: " + response.getNinethPrize() + " tk");
            }
        } catch (Exception e) {

        }

        try {
            if (response.getHasTenthPrize()) {
                tenLayout.setVisibility(View.VISIBLE);
                tenText.setText("Tenth prize: " + response.getTenthPrize() + " tk");
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void OnRoomDetailsClick(int position) {
        FreeFire_paginated_live_response.M response = freeFireLiveList.get(position);

        //Toast.makeText(getActivity(), String.valueOf(response.matchId), Toast.LENGTH_SHORT).show();
        if (response.isJoined) {
            Dialog roomCodeAlert = new Dialog(getActivity());
            roomCodeAlert.setContentView(R.layout.room_code_password_alert);
            roomCodeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //loader.setCancelable(false);
            roomCodeAlert.show();

            Window window = roomCodeAlert.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //wlp.windowAnimations = R.style.DialogAnimation;
            wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
            // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);

            TextView titleText = roomCodeAlert.findViewById(R.id.titleTextID);
            TextView timeText = roomCodeAlert.findViewById(R.id.timeTextID);
            TextView roomCodeText = roomCodeAlert.findViewById(R.id.roomCodeTextID);
            LinearLayout clipBoardButton = roomCodeAlert.findViewById(R.id.clipBoardButtonID);

            LinearLayout passClipBoardButton = roomCodeAlert.findViewById(R.id.passClipBoardButtonID);
            TextView passwordText = roomCodeAlert.findViewById(R.id.passwordTextID);

            titleText.setText(response.title);
            timeText.setText(response.matchTime);

            if (!TextUtils.isEmpty(response.roomCode)) {
                roomCodeText.setText(response.roomCode);
            } else {
                roomCodeText.setText("******");
            }

            if (!TextUtils.isEmpty(response.roomPassword)) {
                passwordText.setText(response.roomPassword);
            } else {
                passwordText.setText("******");
            }

            passClipBoardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(response.roomPassword)) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("room_password", response.roomPassword);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getActivity(), "Room Password copied", Toast.LENGTH_SHORT).show();

                    } else {
                        Toasty.error(getActivity(), getString(R.string.room_password_not_available), Toasty.LENGTH_LONG).show();
                    }
                }
            });

            clipBoardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(response.roomCode)) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("room_code", response.roomCode);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getActivity(), "Room ID copied", Toast.LENGTH_SHORT).show();

                    } else {
                        Toasty.error(getActivity(), getString(R.string.room_code_not_available), Toasty.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            //Toasty.error(getActivity(), getString(R.string.not_joined), Toasty.LENGTH_LONG).show();
            not_joined_alert(response.title, response.matchTime);
        }
    }

    private void not_joined_alert(String title, String time) {
        Dialog notJoinedAlert = new Dialog(getActivity());
        notJoinedAlert.setContentView(R.layout.not_joined_alert);
        notJoinedAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //loader.setCancelable(false);
        notJoinedAlert.show();

        Window window = notJoinedAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //wlp.windowAnimations = R.style.DialogAnimation;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView titleText = notJoinedAlert.findViewById(R.id.titleTextID);
        TextView timeText = notJoinedAlert.findViewById(R.id.timeTextID);
        timeText.setText(time);
        titleText.setText(title);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void OnVideoClick(int position) {

        //cs_match_get
        if (gameID.equals("1")) {
            videoType = "Full Map Regular";
        } else if (gameID.equals("2")) {
            videoType = "CS Regular";
        } else if (gameID.equals("5")) {
            //titleText.setText("Free Fire CS (grand)");
            videoType = "CS Grand";
        } else if (gameID.equals("6")) {
            //titleText.setText("Daily Scrims");
            videoType = "Daily Scrims";
        } else if (gameID.equals("9")) {
            //titleText.setText("Free Fire (Regular Premium)");
            videoType = "Full Map Premium";
        } else if (gameID.equals("10")) {
            //titleText.setText("Free Fire (Regular Grand)");
            videoType = "Full Map Grand";
        }

        //Toast.makeText(getActivity(), videoType, Toast.LENGTH_SHORT).show();

        go_to_video_link(videoType);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void OnArenaValorPrizeClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorMatchList.get(position);

        String matchID = response.matchId.toString();

        String title = response.title;
        String schedule = response.matchDate + " " + response.matchTime;

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
        TextView scheduleText = dialog.findViewById(R.id.scheduleTextID);
        TextView totalPrizeText = dialog.findViewById(R.id.totalPrizeTextID);
        LinearLayout closeButton = dialog.findViewById(R.id.closeButtonID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        titleText.setText(title);
        scheduleText.setText(schedule);
        totalPrizeText.setText("Total prize: " + response.totalPrize + " tk");
    }

    @Override
    public void onArenaValorVideoClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorMatchList.get(position);
        String gameType = response.gameType;

        //Toast.makeText(getActivity(), gameType, Toast.LENGTH_SHORT).show();

        go_to_video_link(gameType);
    }

    @Override
    public void onArenaValorJoinClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorMatchList.get(position);
        String matchID = response.matchId.toString();

        //Toast.makeText(getActivity(), matchID, Toast.LENGTH_SHORT).show();

        //Intent intent = new Intent(getActivity(), Arena_of_valor_rules_activity.class);
        //        intent.putExtra("match_id", matchID);
        //        startActivity(intent);

        if (response.playerType.equals("5 VS 5")) {
            Intent intent = new Intent(getActivity(), Arena_of_valor_5_vs_5_slot_activity.class);
            intent.putExtra("title", response.title);
            intent.putExtra("match_id", matchID);
            intent.putExtra("total_player", response.totalPlayer);
            intent.putExtra("game_type", response.gameType);
            intent.putExtra("player_type", response.playerType);
            intent.putExtra("version", response.version);
            intent.putExtra("entry_fee", response.entryFee);
            intent.putExtra("winning_prize", response.totalPrize);
            intent.putExtra("slots_left", String.valueOf(response.leftPlayer));
            intent.putExtra("schedule", response.matchDate + " " + response.matchTime);
            startActivity(intent);
        } else if (response.playerType.equals("3 VS 3")) {
            Intent intent = new Intent(getActivity(), Arena_of_valor_3_vs_3_slot_activity.class);
            intent.putExtra("title", response.title);
            intent.putExtra("match_id", matchID);
            intent.putExtra("total_player", response.totalPlayer);
            intent.putExtra("game_type", response.gameType);
            intent.putExtra("player_type", response.playerType);
            intent.putExtra("version", response.version);
            intent.putExtra("entry_fee", response.entryFee);
            intent.putExtra("winning_prize", response.totalPrize);
            intent.putExtra("slots_left", String.valueOf(response.leftPlayer));
            intent.putExtra("schedule", response.matchDate + " " + response.matchTime);
            startActivity(intent);
        }
    }

    @Override
    public void OnArenaValorItemClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorMatchList.get(position);
        String matchID = response.matchId.toString();

        //Toast.makeText(getActivity(), matchID, Toast.LENGTH_SHORT).show();

        String matchState = " ";

        if (response.isMatchFull) {

            if (response.isJoined) {
                matchState = "JOINED";
            } else {
                matchState = "CLOSE";
            }

        } else {
            if (response.isJoined) {
                matchState = "JOINED";
            } else {
                if (response.leftPlayer == 0) {
                    matchState = "CLOSE";
                } else {
                    matchState = "JOIN";
                }
            }


        }


        Intent intent = new Intent(getActivity(), Arena_of_valor_rules_activity.class);
        intent.putExtra("title", response.title);
        intent.putExtra("match_id", matchID);
        intent.putExtra("total_player", response.totalPlayer);
        intent.putExtra("game_type", response.gameType);
        intent.putExtra("player_type", response.playerType);
        intent.putExtra("version", response.version);
        intent.putExtra("entry_fee", response.entryFee);
        intent.putExtra("room_code", response.room_code);
        intent.putExtra("winning_prize", response.totalPrize);
        intent.putExtra("match_state", matchState);
        intent.putExtra("slots_left", String.valueOf(response.leftPlayer));
        intent.putExtra("schedule", response.matchDate + " " + response.matchTime);
        startActivity(intent);
    }


    @Override
    public void OnArenaValorRoomDetailsClick(int position) {
        Arena_of_valor_match_response.M response = arenaValorMatchList.get(position);
        String matchID = response.matchId.toString();

        if (response.isJoined) {
            Dialog roomCodeAlert = new Dialog(getActivity());
            roomCodeAlert.setContentView(R.layout.room_code_alert);
            roomCodeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //loader.setCancelable(false);
            roomCodeAlert.show();

            Window window = roomCodeAlert.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //wlp.windowAnimations = R.style.DialogAnimation;
            wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
            // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);

            TextView titleText = roomCodeAlert.findViewById(R.id.titleTextID);
            TextView timeText = roomCodeAlert.findViewById(R.id.timeTextID);
            TextView roomCodeText = roomCodeAlert.findViewById(R.id.roomCodeTextID);
            LinearLayout clipBoardButton = roomCodeAlert.findViewById(R.id.clipBoardButtonID);

            titleText.setText(response.title);
            timeText.setText(response.matchTime);

            if (response.hasRoomcode) {
                roomCodeText.setText(response.room_code);
            } else {
                roomCodeText.setText("******");
            }

            clipBoardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (response.hasRoomcode) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("room_code", response.room_code);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getActivity(), "Room Code copied", Toast.LENGTH_SHORT).show();

                    } else {
                        Toasty.error(getActivity(), getString(R.string.room_code_not_available), Toasty.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            //Toasty.error(getActivity(), getString(R.string.not_joined), Toasty.LENGTH_LONG).show();
            not_joined_alert(response.title, response.matchTime);
        }
    }
}