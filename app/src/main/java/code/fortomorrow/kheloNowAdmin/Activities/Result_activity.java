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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.Pulse;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_result_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.ResultAdapter;
import code.fortomorrow.kheloNowAdmin.Fragments.FragmentResult;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.GetResultResponse;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.M;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Result_activity extends AppCompatActivity implements View.OnClickListener, Ludo_result_game_list_adapter.OnItemClickListener, ResultAdapter.OnPrizeClickListener, Ludo_result_game_list_adapter.OnLudoPrizeClickListener, ResultAdapter.OnWatchClickListener, ResultAdapter.OnFreeFireItemClickListener {

    ImageView backButton;
    APIService apiService;
    String secret_id, jwt_token;
    String type;

    RecyclerView resultRV;

    SpinKitView spin_kit;
    ProgressBar progressBar;

    LinearLayout mLoading;
    RelativeLayout mEmpty;
    private List<M> resultList;
    private List<Ludo_tournament_game_list_response.M> ludoResultGameList;
    private Ludo_result_game_list_adapter adapter;

    TextView nameText;
    String gameTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init_view();

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (Integer.parseInt(secret_id) % 2 == 0) {
            type = "even";
        } else {
            type = "odd";
        }


        gameTypeID = getIntent().getStringExtra("game_id");

        //Toast.makeText(getApplicationContext(), gameTypeID, Toast.LENGTH_SHORT).show();

        /*if (gameTypeID.equals("1")) {
            matchList(gameTypeID);
            nameText.setText("Free Fire (Regular)");
        } else if (gameTypeID.equals("3")) {
            //ludo_list_func(gameTypeID);
            nameText.setText("Ludo (Regular)");
        } else if (gameTypeID.equals("4")) {
            //ludo_list_func(gameTypeID);
            nameText.setText("Ludo (Grand)");
        } else if (gameTypeID.equals("2")) {
            matchList(gameTypeID);
            nameText.setText("Free Fire (CS Regular)");
        } else if (gameTypeID.equals("5")) {
            matchList(gameTypeID);
            nameText.setText("Free Fire (CS Grand)");
        }*/

    }

    private void init_view() {
        backButton = findViewById(R.id.backButtonID);

        resultRV = findViewById(R.id.mBlogList);
        resultRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        spin_kit = findViewById(R.id.spin_kit);

        progressBar = findViewById(R.id.spin_kit);
        Pulse pulse = new Pulse();
        progressBar.setIndeterminateDrawable(pulse);

        mLoading = findViewById(R.id.mLoading);
        mEmpty = findViewById(R.id.mEmpty);

        nameText = findViewById(R.id.nameTextID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    /*private void ludo_list_func(String gameID) {

        //Log.d("tokenxx", jwt_token);

        ludoResultGameList = new ArrayList<>();
        ludoResultGameList.clear();
        apiService.getResultLudoMatchList(secret_id, jwt_token, gameID).enqueue(new Callback<Ludo_tournament_game_list_response>() {
            @Override
            public void onResponse(Call<Ludo_tournament_game_list_response> call, Response<Ludo_tournament_game_list_response> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    resultRV.setVisibility(View.VISIBLE);
                    mLoading.setVisibility(View.GONE);
                    spin_kit.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.GONE);
                    ludoResultGameList = response.body().getM();

                    //Toast.makeText(getActivity(), String.valueOf(ludoResultGameList.size()), Toast.LENGTH_SHORT).show();
                    adapter = new Ludo_result_game_list_adapter(ludoResultGameList);
                    adapter.setOnItemClickListener(Result_activity.this::OnItemClick, Result_activity.this::OnLudoPrizeClick);
                    resultRV.setAdapter(adapter);
                } else {
                    //Log.d("results", new Gson().toJson(response.body().getM()));
                    //Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();

                    resultRV.setVisibility(View.GONE);
                    mLoading.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Ludo_tournament_game_list_response> call, Throwable t) {
                //Toast.makeText(getActivity(), "elseif", Toast.LENGTH_SHORT).show();

            }
        });
    }*/

    /*private void matchList(String gameID) {


        resultList = new ArrayList<>();
        resultList.clear();

        if (type.equals("odd")) {
            apiService.getResultResponseOne(secret_id, jwt_token, gameID).enqueue(new Callback<GetResultResponse>() {
                @Override
                public void onResponse(Call<GetResultResponse> call, Response<GetResultResponse> response) {
                    if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                        resultRV.setVisibility(View.VISIBLE);
                        mLoading.setVisibility(View.GONE);
                        spin_kit.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.GONE);

                        resultList = response.body().getM();
                        ResultAdapter resultAdapter = new ResultAdapter(response.body().getM());
                        resultAdapter.setOnClickListener(Result_activity.this::OnPrizeClick, Result_activity.this::OnWatchClick, Result_activity.this::OnFreeFireItemClick);
                        resultRV.setAdapter(resultAdapter);
                    } else {
                        mLoading.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                        resultRV.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetResultResponse> call, Throwable t) {
                    //Log.d("results", new Gson().toJson(t.toString()));
                    resultRV.setVisibility(View.GONE);
                    mLoading.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);

                }
            });

        } else if (type.equals("even")) {
            apiService.getResultResponseTwo(secret_id, jwt_token, gameID).enqueue(new Callback<GetResultResponse>() {
                @Override
                public void onResponse(Call<GetResultResponse> call, Response<GetResultResponse> response) {
                    if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                        resultRV.setVisibility(View.VISIBLE);
                        mLoading.setVisibility(View.GONE);
                        spin_kit.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.GONE);

                        resultList = response.body().getM();

                        ResultAdapter resultAdapter = new ResultAdapter(response.body().getM());
                        resultAdapter.setOnClickListener(Result_activity.this::OnPrizeClick, Result_activity.this::OnWatchClick, Result_activity.this::OnFreeFireItemClick);
                        resultRV.setAdapter(resultAdapter);
                    } else {
                        mLoading.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                        resultRV.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetResultResponse> call, Throwable t) {
                    //Log.d("results", new Gson().toJson(t.toString()));
                    mLoading.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                    resultRV.setVisibility(View.GONE);

                }
            });
        }

    }*/

    @Override
    public void OnItemClick(int position) {
        Ludo_tournament_game_list_response.M response = ludoResultGameList.get(position);

        String matchID = String.valueOf(response.getMatchId());

        Intent intent = new Intent(getApplicationContext(), Ludo_result_details_activity.class);
        intent.putExtra("match_id", matchID);
        intent.putExtra("game_title", response.getTitle());
        intent.putExtra("entry_fee", response.getEntryFee());
        intent.putExtra("total_prize", response.getTotalPrize());
        intent.putExtra("date_time", response.getMatchDate() + " " + response.getMatchTime());
        startActivity(intent);
    }

    @Override
    public void OnPrizeClick(int position) {
        code.fortomorrow.kheloNowAdmin.Model.GetResults.M response = resultList.get(position);

        Dialog dialog = new Dialog(Result_activity.this);
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
        Ludo_tournament_game_list_response.M response = ludoResultGameList.get(position);

        Dialog dialog = new Dialog(Result_activity.this);
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

        code.fortomorrow.kheloNowAdmin.Model.GetResults.M response = resultList.get(position);

        Intent i = new Intent(getApplicationContext(), ResultDetailsActivity.class);
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
}

/*Intent i = new Intent(context.getApplicationContext(), ResultDetailsActivity.class);
                i.putExtra("title", holder.mTitle.getText());
                i.putExtra("type", mList.get(position).getGameType());
                i.putExtra("entryfee", mList.get(position).getEntryFee());
                i.putExtra("time", mList.get(position).getMatchTime());
                i.putExtra("winningprize", mList.get(position).getTotalPrize());
                i.putExtra("perkill", mList.get(position).getPerKillRate());
                i.putExtra("match_id", String.valueOf(mList.get(position).getMatchId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(i);*/