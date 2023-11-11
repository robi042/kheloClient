package code.fortomorrow.kheloNowAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.LoginActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Message_activity;
import code.fortomorrow.kheloNowAdmin.Activities.WalletActivity;
import code.fortomorrow.kheloNowAdmin.Adapters.Support_numbers_adapter;
import code.fortomorrow.kheloNowAdmin.Fragments.FragmentMe;
import code.fortomorrow.kheloNowAdmin.Fragments.FragmentPlay;
import code.fortomorrow.kheloNowAdmin.Fragments.FragmentShop;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_buySell;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_statistics;
import code.fortomorrow.kheloNowAdmin.Model.GameType.M;
import code.fortomorrow.kheloNowAdmin.Model.Message.Message_response;
import code.fortomorrow.kheloNowAdmin.Model.PopUp.Pop_up_data_response;
import code.fortomorrow.kheloNowAdmin.Model.PopUp.Pop_up_response;
import code.fortomorrow.kheloNowAdmin.Session.Session_management;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.Status_response;
import code.fortomorrow.kheloNowAdmin.Model.Support.Support_numbers_response;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Support_numbers_adapter.OnItemClickListener {
    String jwt_token;
    String secret_id;
    private CardView matchDetails;
    static String date, time;
    APIService apiService;
    String gameTypeID = "1";
    String playTypeID = "1";
    List<M> gameTypeResponses = new ArrayList<>();
    List<String> gameType = new ArrayList<>();
    List<code.fortomorrow.kheloNowAdmin.Model.PlayType.M> getPlayType = new ArrayList<>();
    List<String> playType = new ArrayList<>();
    private BottomNavigationView navigation;
    private ImageView supportButton, mWallet, messageButton;
    private final String whatsapp_number = "01888681608";
    TextView kheloButton;

    private static GoogleAnalytics sAnalytics;
    private static Tracker mTracker;

    Dialog popUpAlert;
    ImageView closeButton, popUpClickButton;
    TextView popUpText;
    private List<Support_numbers_response.M> numbersList;
    private Support_numbers_adapter adapter;
    LinearLayout mainLayout, imageLayout, textLayout;
    Session_management session_management;
    LinearLayout redDotLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAll();

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        if (TextUtils.isEmpty(session_management.getNotificationForMessage()) || session_management.getNotificationForMessage().equals("-1")) {
            FirebaseMessaging.getInstance().subscribeToTopic("NotificationForMessage" + secret_id);

            session_management.NotificationForMessage("1");

            //Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), session_management.getNotificationForMessage()+" not empty", Toast.LENGTH_SHORT).show();
        }


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WalletActivity.class));
            }
        });

        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whatsapp_support_pop_up();

            }
        });

        //getSupportFragmentManager().beginTransaction().replace(R.id.fagment_container, new FragmentPlay()).commit();

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.play); // change to whichever id should be default

        }

        //navigation.find


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpAlert.dismiss();
            }
        });


        apiService.getPopUpResponse(secret_id, jwt_token).enqueue(new Callback<Pop_up_response>() {
            @Override
            public void onResponse(Call<Pop_up_response> call, Response<Pop_up_response> response) {
                if (response.body().getE() == 0) {
                    Boolean status = response.body().getM().getPopupStatus();

                    //Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
                    if (status) {
                        popUpAlert.show();
                        pop_up_data();
                    } else {
                        popUpAlert.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<Pop_up_response> call, Throwable t) {

            }
        });


        apiService.getmatchlength_regular(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if (response.body().getE() == 0) {

                } else {
                    EasySharedPref.write("jwt_token", "");
                    EasySharedPref.write("secret_id", "");
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                EasySharedPref.write("jwt_token", "");
                EasySharedPref.write("secret_id", "");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //message_pop_up();
                startActivity(new Intent(getApplicationContext(), Message_activity.class));
            }
        });


    }

    private void whatsapp_support_pop_up() {
        String REGULAR_MATCH_LUDO_MATCH = "01776001380";
        String CS_MATCH = "01888681608";
        String ADD_MONEY_ACCOUNT = "01888681608";

                /*String url = "https://api.whatsapp.com/send?phone=+88" + whatsapp_number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.whatsapp_option_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        RecyclerView supportNumbersView = dialog.findViewById(R.id.supportNumbersViewID);
        supportNumbersView.setHasFixedSize(true);
        supportNumbersView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        apiService.getSupportNumbers(secret_id, jwt_token).enqueue(new Callback<Support_numbers_response>() {
            @Override
            public void onResponse(Call<Support_numbers_response> call, Response<Support_numbers_response> response) {

                if (response.body().getE() == 0) {
                    numbersList = new ArrayList<>();
                    numbersList = response.body().getM();
                    adapter = new Support_numbers_adapter(numbersList);
                    adapter.setOnItemClickListener(MainActivity.this::OnItemClick);
                    supportNumbersView.setAdapter(adapter);

                } else {

                }

            }

            @Override
            public void onFailure(Call<Support_numbers_response> call, Throwable t) {

            }
        });
    }

    private void message_pop_up() {
        Dialog messageAlert = new Dialog(MainActivity.this);
        messageAlert.setContentView(R.layout.message_alert);
        messageAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        messageAlert.show();

        Window window = messageAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        TextView messageText = messageAlert.findViewById(R.id.messageTextID);

        /*apiService.get_messages_list(secret_id, jwt_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if (response.body().getE() == 0) {
                    messageText.setText(response.body().getM());
                } else {
                    messageText.setText("No message found");
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                messageText.setText("No message found");
            }
        });*/
    }

    private void pop_up_data() {


        apiService.getPopUpData(secret_id, jwt_token).enqueue(new Callback<Pop_up_data_response>() {
            @Override
            public void onResponse(Call<Pop_up_data_response> call, Response<Pop_up_data_response> response) {
                if (response.body().getE() == 0) {
                    Boolean imageAvailable = response.body().getM().getHasImage();

                    if (imageAvailable) {
                        imageLayout.setVisibility(View.VISIBLE);
                        textLayout.setVisibility(View.GONE);

                        Display display = getWindowManager().getDefaultDisplay();


                        Picasso.get().load(response.body().getM().getImageLink()).into(popUpClickButton);

                        //popUpClickButton.getLayoutParams().width = display.getWidth();

                        popUpClickButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse(response.body().getM().getLink()); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });

                    } else {
                        imageLayout.setVisibility(View.GONE);
                        textLayout.setVisibility(View.VISIBLE);
                        popUpText.setText(response.body().getM().getText());
                    }
                } else {
                    Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pop_up_data_response> call, Throwable t) {
                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });

    }

    private void initAll() {

        popUpAlert = new Dialog(MainActivity.this);
        popUpAlert.setContentView(R.layout.pop_up_alert);
        popUpAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //popUpAlert.setCancelable(false);
        //popUpAlert.show();

        session_management = new Session_management(getApplicationContext());

        Window window = popUpAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        EasySharedPref.init(getApplicationContext());

        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        //Log.d("apitokenxx",secret_id+" "+ jwt_token);
        //apiService = AppConfig.getRetrofit().create(APIService.class);
        mWallet = findViewById(R.id.mWallet);
        apiService = AppConfig.getRetrofit().create(APIService.class);

        navigation = findViewById(R.id.navigation);

        messageButton = findViewById(R.id.messageButtonID);

        closeButton = popUpAlert.findViewById(R.id.closeButtonID);
        popUpClickButton = popUpAlert.findViewById(R.id.popUpClickButtonID);
        imageLayout = popUpAlert.findViewById(R.id.imageLayoutID);
        textLayout = popUpAlert.findViewById(R.id.textLayoutID);
        popUpText = popUpAlert.findViewById(R.id.popUpTextID);
        mainLayout = popUpAlert.findViewById(R.id.mainLayoutID);

        mainLayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.interpolation));

        supportButton = findViewById(R.id.supportButtonID);

        redDotLayout = findViewById(R.id.redDotLayout);
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment selectFragment = null;


        switch (item.getItemId()) {

            case R.id.play:
                selectFragment = new FragmentPlay();
                break;


            case R.id.statistics:
                selectFragment = new Fragment_statistics();
                break;

            case R.id.shop:

                selectFragment = new FragmentShop();
                break;

            case R.id.buySell:
                selectFragment = new Fragment_buySell();
                break;

            case R.id.me:
                selectFragment = new FragmentMe();
                break;


        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fagment_container, selectFragment).commit();
        return true;
    };


    @Override
    protected void onResume() {
        super.onResume();


        //Toast.makeText(getApplicationContext(), secret_id, Toast.LENGTH_SHORT).show();
        apiService.getStatus(secret_id, jwt_token).enqueue(new Callback<Status_response>() {
            @Override
            public void onResponse(Call<Status_response> call, Response<Status_response> response) {
                if (response.body().getE() == 0) {
                    String status = response.body().getM().getStatus();
                    // Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                    if (status.equals("inactive")) {
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.user_status_alert);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false);
                        dialog.show();

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                        window.setAttributes(wlp);

                        AppCompatButton closeButton = dialog.findViewById(R.id.closeButtonID);
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                System.exit(0);
                            }
                        });

                        AppCompatButton contactButton = dialog.findViewById(R.id.contactButtonID);
                        contactButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = "https://api.whatsapp.com/send?phone=+88" + whatsapp_number;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<Status_response> call, Throwable t) {

            }
        });

        apiService.get_messages_list(secret_id, jwt_token).enqueue(new Callback<Message_response>() {
            @Override
            public void onResponse(Call<Message_response> call, Response<Message_response> response) {
                if (response.body().e == 0) {

                    if (!session_management.getMessageCount().equals(String.valueOf(response.body().m.size()))) {
                        redDotLayout.setVisibility(View.VISIBLE);
                    } else {
                        redDotLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Message_response> call, Throwable t) {

            }
        });

        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
    }

    @Override
    public void OnItemClick(int position) {
        Support_numbers_response.M response = numbersList.get(position);
        //Toast.makeText(getApplicationContext(), response.getNumber(), Toast.LENGTH_SHORT).show();

        String url = "https://api.whatsapp.com/send?phone=+88" + response.getNumber();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}