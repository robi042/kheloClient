package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.OrdersAdapter;
import code.fortomorrow.kheloNowAdmin.Adapters.TransactionsAdapter;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.OrdersResponse.OrdersResponse;
import code.fortomorrow.kheloNowAdmin.Model.Transaction.TransactionResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView OrderRV;
    private APIService apiService;
    private String jwt_token,secret_id;
    private LinearLayout noMatchesLayout;
    private static Tracker mTracker;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        init_view();

        getAllOrders();

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getAllOrders() {
        apiService.getAllOrders(secret_id,jwt_token).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                //Log.d("getOrders",new Gson().toJson(response.body()));
                if (response.body().getE() ==0 && response.body().getM().size() !=0){
                    noMatchesLayout.setVisibility(View.GONE);
                    OrderRV.setVisibility(View.VISIBLE);
                    OrderRV.setAdapter(new OrdersAdapter(response.body().getM(),getApplicationContext()));

                }
                else {
                    noMatchesLayout.setVisibility(View.VISIBLE);
                    OrderRV.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                noMatchesLayout.setVisibility(View.VISIBLE);
                OrderRV.setVisibility(View.GONE);
            }
        });
    }

    private void init_view() {
        backButton = findViewById(R.id.backButtonID);
        OrderRV = findViewById(R.id.OrderRV);
        noMatchesLayout = findViewById(R.id.noMatchesLayoutID);
        OrderRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        jwt_token = EasySharedPref.read("jwt_token","");
        secret_id =  EasySharedPref.read("secret_id","");
    }
}