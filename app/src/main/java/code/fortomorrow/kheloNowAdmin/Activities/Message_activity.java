package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapters.Message.Message_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Message.Message_response;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Message_activity extends AppCompatActivity {

    String jwt_token, secret_id;
    APIService apiService;

    ImageView backButton;

    RecyclerView messageView;
    private List<Message_response.M> messageList;
    private Message_adapter messageAdapter;

    Session_management session_management;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        get_messages();
    }

    private void get_messages() {

        apiService.get_messages_list(secret_id, jwt_token).enqueue(new Callback<Message_response>() {
            @Override
            public void onResponse(Call<Message_response> call, Response<Message_response> response) {

                if (response.body().e == 0) {
                    messageList = new ArrayList<>();
                    messageList = response.body().m;
                    messageAdapter = new Message_adapter(messageList);
                    messageView.setAdapter(messageAdapter);

                    session_management.saveMessageCount(String.valueOf(response.body().m.size()));

                }


            }

            @Override
            public void onFailure(Call<Message_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);

        //Log.d("dataxxx", secret_id+" "+jwt_token);

        backButton = findViewById(R.id.backButtonID);

        messageView = findViewById(R.id.messageView);
        messageView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        messageView.setLayoutManager(linearLayoutManager);
        //messageView.scrollToPosition(0);

        session_management = new Session_management(getApplicationContext());
    }
}