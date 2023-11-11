package code.fortomorrow.kheloNowAdmin.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_joining_match_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_rules_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_paginated_match_list;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_ludo_live extends Fragment implements Ludo_game_list_adapter.OnItemJoinClickListener, Ludo_game_list_adapter.OnItemClickListener, Ludo_game_list_adapter.OnClipClickListener, Ludo_game_list_adapter.OnHowToJoinClickListener, Ludo_game_list_adapter.OnRoomDetailsClickListener, Ludo_game_list_adapter.OnPrizeClickListener, Ludo_game_list_adapter.OnReadyClickListener {

    Session_management session_management;
    String gameID, gameKey, jwt_token, secretID;
    TextView gameText;
    APIService apiService;
    RecyclerView ludoTournamentView;
    LinearLayout noMatchesLayout, loadingLayout;
    //private List<Ludo_tournament_game_list_response.M> ludoGameList;
    private List<Ludo_paginated_match_list.M> ludoGameList = new ArrayList<>();
    private Ludo_game_list_adapter adapter;
    Dialog loader;


    private Boolean isStarted = false;
    private Boolean isVisible = false;

    String type;

    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1;

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
        View view = inflater.inflate(R.layout.fragment_ludo_live, container, false);

        init_view(view);

        gameID = session_management.getUserID();
        gameKey = session_management.getUserKey();


        //isStarted = true;
        //        if (isVisible && isStarted) {
        //            viewDidAppear();
        //        }


        if (gameID.equals("3")) {
            type = "Ludo (Regular)";
        } else if (gameID.equals("4")) {
            type = "Ludo (Grand)";
        } else if (gameID.equals("7")) {
            type = "Ludo (Quick)";
        } else if (gameID.equals("8")) {
            type = "Ludo (4 Player)";
        }

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    ludoTournament_func(gameID, page);

                }
            }
        });


        return view;
    }


    public void viewDidAppear() {
        // your logic
        //Toast.makeText(getActivity(), "live", Toast.LENGTH_SHORT).show();

        page = 1;
        ludoGameList.clear();

        //Toast.makeText(getActivity(), page+" "+ludoGameList.size(), Toast.LENGTH_SHORT).show();
        ludoTournament_func(gameID, page);
    }

    private void init_view(View view) {
        nestedScrollView = view.findViewById(R.id.nestedScrollViewID);
        progressBar = view.findViewById(R.id.progressBarID);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        session_management = new Session_management(getActivity());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getActivity());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secretID = EasySharedPref.read("secret_id", "");

        noMatchesLayout = view.findViewById(R.id.noMatchesLayoutID);
        loadingLayout = view.findViewById(R.id.loadingLayoutID);
        ludoTournamentView = view.findViewById(R.id.ludoTournamentViewID);
        ludoTournamentView.setHasFixedSize(true);
        ludoTournamentView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void ludoTournament_func(String gameID, int page) {

        //Toast.makeText(getActivity(), gameID, Toast.LENGTH_SHORT).show();
        /*loader.show();
        apiService.getLudoTournamentGameList(secretID, jwt_token, gameID).enqueue(new Callback<Ludo_tournament_game_list_response>() {
            @Override
            public void onResponse(Call<Ludo_tournament_game_list_response> call, Response<Ludo_tournament_game_list_response> response) {

                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    noMatchesLayout.setVisibility(View.GONE);
                    ludoTournamentView.setVisibility(View.VISIBLE);
                    ludoGameList = new ArrayList<>();
                    ludoGameList = response.body().getM();
                    adapter = new Ludo_game_list_adapter(ludoGameList);
                    adapter.setOnItemClickListener(Fragment_ludo_live.this::OnItemJoinClick, Fragment_ludo_live.this::OnItemClick, Fragment_ludo_live.this::OnClipItemClick, Fragment_ludo_live.this::OnHowToJoinClick, Fragment_ludo_live.this::OnPrizeClick, Fragment_ludo_live.this::OnReadyClick);
                    ludoTournamentView.setAdapter(adapter);
                } else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    ludoTournamentView.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<Ludo_tournament_game_list_response> call, Throwable t) {
                loader.dismiss();
            }
        });*/

        apiService.getPaginatedLudoMatchList(secretID, jwt_token, gameID, String.valueOf(page)).enqueue(new Callback<Ludo_paginated_match_list>() {
            @Override
            public void onResponse(Call<Ludo_paginated_match_list> call, Response<Ludo_paginated_match_list> response) {

                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    noMatchesLayout.setVisibility(View.GONE);
                    ludoTournamentView.setVisibility(View.VISIBLE);

                    for (int i = 0; i < response.body().getM().size() - 1; i++) {
                        ludoGameList.add(response.body().getM().get(i));
                    }

                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(), String.valueOf(freeFireLiveList.size()), Toast.LENGTH_SHORT).show();
                    if (ludoGameList.size() == 0) {
                        noMatchesLayout.setVisibility(View.VISIBLE);
                        ludoTournamentView.setVisibility(View.GONE);
                    }

                    adapter = new Ludo_game_list_adapter(ludoGameList);
                    adapter.setOnItemClickListener(Fragment_ludo_live.this::OnItemJoinClick, Fragment_ludo_live.this::OnItemClick, Fragment_ludo_live.this::OnClipItemClick, Fragment_ludo_live.this::OnHowToJoinClick, Fragment_ludo_live.this::OnPrizeClick, Fragment_ludo_live.this::OnReadyClick, Fragment_ludo_live.this::OnRoomDetailsClick);
                    adapter.notifyDataSetChanged();
                    loadingLayout.setVisibility(View.GONE);
                    ludoTournamentView.setAdapter(adapter);
                } else {
                    loadingLayout.setVisibility(View.GONE);
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    ludoTournamentView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Ludo_paginated_match_list> call, Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                noMatchesLayout.setVisibility(View.VISIBLE);
                ludoTournamentView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnItemJoinClick(int position) {


        Ludo_paginated_match_list.M response = ludoGameList.get(position);

        String matchState = " ";

        try {
            if (response.getMatchFull()) {

                try {
                    if (response.getJoined()) {
                        try {
                            if (response.getHasRoomcode()) {
                                matchState = "PLAY";
                            }
                        } catch (Exception ej) {
                            matchState = "JOINED";
                        }
                    }
                } catch (Exception er) {

                    matchState = "CLOSE";
                }
            }
        } catch (Exception e) {
            //Log.d("errorxxx", "hix");
            try {
                if (response.getJoined()) {

                    try {
                        if (response.getHasRoomcode()) {
                            matchState = "PLAY";
                        }
                    } catch (Exception ej) {
                        matchState = "JOINED";
                    }
                }
            } catch (Exception er) {
                if (response.getLeftPlayer() == 0) {
                    matchState = "CLOSE";
                } else {
                    matchState = "JOIN";
                }
            }
        }

        if (matchState.equals("JOIN")) {
            Intent intent = new Intent(getActivity(), Ludo_joining_match_activity.class);
            intent.putExtra("game_title", response.getTitle());
            intent.putExtra("match_id", String.valueOf(response.getMatchId()));
            intent.putExtra("entry_fee", response.getEntryFee());
            intent.putExtra("spots_left", String.valueOf(response.getLeftPlayer()));
            startActivity(intent);
        } else if (matchState.equals("PLAY")) {

            String packageName = "com.ludo.king";

            //String packageName = "com.khelo.kheloLudo";

            if (isAppExist(packageName)) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("room_code", response.getRoomCode());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "room code copied", Toast.LENGTH_SHORT).show();

                startActivity(launchIntent);
                //Toast.makeText(getActivity(), "App found", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                startActivity(intent);
            }

        }
    }

    private boolean isAppExist(String packageName) {

        PackageManager pm = getActivity().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {

            //Log.d("errorxxx", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void OnItemClick(int position) {
        Ludo_paginated_match_list.M response = ludoGameList.get(position);

        //Toast.makeText(getActivity(), String.valueOf(response.getMatchId()), Toast.LENGTH_SHORT).show();
        String matchState = " ";

        try {
            if (response.getMatchFull()) {

                try {
                    if (response.getJoined()) {
                        matchState = "JOINED";
                    }
                } catch (Exception er) {

                    matchState = "CLOSE";
                }
            }
        } catch (Exception e) {
            //Log.d("errorxxx", "hix");
            try {
                if (response.getJoined()) {
                    matchState = "JOINED";
                }
            } catch (Exception er) {
                if (response.getLeftPlayer() == 0) {
                    matchState = "CLOSE";
                } else {
                    matchState = "JOIN";
                }
            }
        }

        Intent intent = new Intent(getActivity(), Ludo_rules_activity.class);
        intent.putExtra("game_title", response.getTitle());
        intent.putExtra("match_id", String.valueOf(response.getMatchId()));
        intent.putExtra("entry_fee", response.getEntryFee());
        intent.putExtra("spots_left", String.valueOf(response.getLeftPlayer()));
        intent.putExtra("total_prize", response.getTotalPrize());
        intent.putExtra("game_type", response.getHostApp());
        intent.putExtra("schedule", response.getMatchDate() + " " + response.getMatchTime());
        intent.putExtra("match_state", matchState);
        intent.putExtra("room_code", response.getRoomCode());
        startActivity(intent);
    }

    @Override
    public void OnClipItemClick(int position) {

        Ludo_paginated_match_list.M response = ludoGameList.get(position);

        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("room_code", response.getRoomCode());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getActivity(), "Room Code copied", Toast.LENGTH_SHORT).show();

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
    public void OnHowToJoinClick(int position) {
        //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();

        apiService.getVideoLink(secretID, jwt_token, type).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if (response.body().getE() == 0) {

                    String l = response.body().getM();
                    Uri uri = Uri.parse(l); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                    //Log.d("linkxx", l);

                } else {
                    Toasty.error(getActivity(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                Toasty.error(getActivity(), "Something went wrong", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnPrizeClick(int position) {
        Ludo_paginated_match_list.M response = ludoGameList.get(position);

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
        TextView scheduleText = dialog.findViewById(R.id.scheduleTextID);

        scheduleText.setText(response.getMatchDate() + " " + response.getMatchTime());
        titleText.setText(response.getTitle());

        oneText.setText("Winner: " + response.getTotalPrize() + " tk");
    }


    @Override
    public void OnReadyClick(int position) {

        Ludo_paginated_match_list.M response = ludoGameList.get(position);
        String matchID = String.valueOf(response.getMatchId());

        //Toast.makeText(getActivity(), matchID, Toast.LENGTH_SHORT).show();

        loader.show();
        apiService.getPlayerReady(secretID, jwt_token, matchID).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    Toasty.success(getActivity(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.error(getActivity(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                loader.dismiss();
                Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnRoomDetailsClick(int position) {
        Ludo_paginated_match_list.M response = ludoGameList.get(position);

        try {
            if (response.getJoined()) {
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

                titleText.setText(response.getTitle());
                timeText.setText(response.getMatchDate() + " " + response.getMatchTime());

                try {
                    if (response.getHasRoomcode()) {
                        roomCodeText.setText(response.getRoomCode());
                    }
                } catch (Exception e) {
                    roomCodeText.setText("******");
                }

                clipBoardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (response.getHasRoomcode()) {
                                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("room_code", response.getRoomCode());
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(getActivity(), "Room Code copied", Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            Toasty.error(getActivity(), getString(R.string.room_code_not_available), Toasty.LENGTH_LONG).show();
                        }
                    }
                });

                //Toast.makeText(getActivity(), String.valueOf(response.isJoined), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            //Toasty.error(getActivity(), getString(R.string.not_joined), Toasty.LENGTH_LONG).show();
            not_joined_alert(response.title, response.matchDate + " " + response.matchTime);
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
}