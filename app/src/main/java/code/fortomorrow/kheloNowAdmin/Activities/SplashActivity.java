package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.Model.GetTokenResponse.ResponseMessage;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.Update.UpdateResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout server_maintance, loadingText;
    ProgressDialog progressDialog;
    String currentVersion = "63", app_name = "khelo2.0.4.apk", updatedVersion, TAG = "DOWNLOAD";
    TextView mLaodingText;
    Context context;
    private APIService apiService;
    Dialog progress;

    private static Tracker mTracker;
    Shimmer shimmer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForAllPlayers");
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForOTP"+deviceId);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        EasySharedPref.init(getApplicationContext());
        FirebaseMessaging.getInstance().unsubscribeFromTopic("all");

        progress = new Dialog(SplashActivity.this);
        progress.setContentView(R.layout.initial_loader);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        ShimmerTextView shimmerTextView = progress.findViewById(R.id.shimmerTextID);

        progress.show();

        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);


        context = this;
        server_maintance = findViewById(R.id.server_maintance);
        loadingText = findViewById(R.id.loadingText);

        NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                SplashActivity.this,
                getLifecycle()
        );

        DialogPropertiesPendulum properties = builder.getDialogProperties();
        server_maintance.setVisibility(View.GONE);

        properties.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
//                update();
            }
        });

        properties.setCancelable(false); // Optional
        properties.setNoInternetConnectionTitle("No Internet"); // Optional
        properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText("Please turn on"); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle("No Internet"); // Optional
        properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        properties.setPleaseTurnOffText("Please turn off"); // Optional
        properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional

        builder.build();
        update();

        //test_fuc();

    }

    private void test_fuc() {
        Toast.makeText(SplashActivity.this, "testing", Toast.LENGTH_SHORT).show();
        apiService.test().enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE()==0){
                    Toast.makeText(SplashActivity.this, response.body().getE().toString(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                //Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("errorxx", t.getMessage());
            }
        });
    }


    void update() {
        apiService = AppConfig.getRetrofit().create(APIService.class);


//        apiService.upDateCheck().enqueue(new Callback<List<UpdateResponse>>() {
//            @Override
//            public void onResponse(Call<List<UpdateResponse>> call, Response<List<UpdateResponse>> response) {
//
//
//                if (response.isSuccessful()) {
//                    Log.d("bodlya11", new Gson().toJson(response.body()));
//                    server_maintance.setVisibility(View.INVISIBLE);
//                    loadingText.setVisibility(View.VISIBLE);
//
//                    for (int i = 0; i < response.body().size(); i++) {
//                        if (!response.body().get(i).().equals(currentVersion)) {
//                            Log.d("bodlya11", new Gson().toJson(response.body()));
//                            SharedPrefManager.getInstance(getApplicationContext()).setUID("");
//                            SharedPrefManager.getInstance(getApplicationContext()).setEmail("");
//                            updatedVersion = response.body().get(i).getKhelo();
//                            final String updatedAppDownloadLink = response.body().get(i).getLink();
//
//                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    switch (which){
//                                        case DialogInterface.BUTTON_POSITIVE:
//                                            new DownloadFile().execute(updatedAppDownloadLink);
//                                            break;
//
//                                        case DialogInterface.BUTTON_NEGATIVE:
//                                            finish();
//                                            finishAffinity();
//                                            break;
//                                    }
//                                }
//                            };
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
//                            builder.setCancelable(false);
//
//                            builder.setMessage("Please Update app.").setPositiveButton("UPDATE", dialogClickListener)
//                                    .setNegativeButton("No", dialogClickListener).show();
//
//                        }
//
//                        else {
//                            SharedPrefManager.getInstance(getApplicationContext());
//                            String uid = SharedPrefManager.getInstance(getApplicationContext()).getUID();
//
//                            if (uid != null || !uid.equals("")) {
//
//                                if (!uid.equals("")) {
//
//                                    apiService.getUpdatedVersion(uid).enqueue(new Callback<ResponseMessage>() {
//                                        @Override
//                                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
//                                            if(response.isSuccessful()){
//                                                Log.d("updateaaaaaaa",new Gson().toJson(response.body().getMessage()));
//                                                Intent iaa;
//                                                if(response.body().getMessage().equals(currentVersion)){
//                                                    if(response.isSuccessful()){
//                                                        iaa = new Intent(getApplicationContext(), MainActivity.class);
//                                                        startActivity(iaa);
//                                                        finish();
//                                                    }
//                                                    else {
//                                                        iaa = new Intent(getApplicationContext(), LoginActivity.class);
//                                                        startActivity(iaa);
//                                                        finish();
//                                                    }
//                                                }
//                                                else {
//                                                    iaa = new Intent(getApplicationContext(), LoginActivity.class);
//                                                    startActivity(iaa);
//                                                    finish();
//                                                }
//
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
//
//                                        }
//                                    });
//
//
//                                } else {
//                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                    finish();
//                                    break;
//                                }
//
//                            } else {
//
//                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                finish();
//                                break;
//
//                            }
//
//                        }
//                    }
//                } else {
//
//                    server_maintance.setVisibility(View.VISIBLE);
//                    Log.d("bodlya11", new Gson().toJson(response.body()));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<UpdateResponse>> call, Throwable t) {
//                if(t instanceof IOException){
//                    server_maintance.setVisibility(View.VISIBLE);
//                    Log.d("bodlya11", new Gson().toJson(t.toString()));
//                }
//                else {
//                    server_maintance.setVisibility(View.VISIBLE);
//                    Log.d("bodlya11", new Gson().toJson(t.toString()));
//                }
//
//            }
//
//        });
        apiService.upDateCheck().enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getE() == 0) {
                        if (!response.body().getM().getKhelo().equals(currentVersion)) {
                            //Log.d("bodlya11", new Gson().toJson(response.body()));
                            updatedVersion = response.body().getM().getKhelo();
                            final String updatedAppDownloadLink = response.body().getM().getLink();

                            Dialog updateAlert = new Dialog(SplashActivity.this);
                            updateAlert.setContentView(R.layout.update_app_alert);
                            updateAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            updateAlert.setCancelable(false);
                            updateAlert.show();

                            Window window = updateAlert.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                            window.setAttributes(wlp);

                            TextView updateButton = updateAlert.findViewById(R.id.updateButtonID);
                            TextView noButton = updateAlert.findViewById(R.id.noButtonID);

                            updateButton.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                                @Override
                                public void onClick(View v) {
                                    new DownloadFile().execute(updatedAppDownloadLink);
                                    //break;
                                }
                            });

                            noButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                    finishAffinity();
                                }
                            });

                            /*DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            new DownloadFile().execute(updatedAppDownloadLink);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            finish();
                                            finishAffinity();
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setCancelable(false);

                            builder.setMessage("Please Update app.").setPositiveButton("UPDATE", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();*/

                        } else {
                            if (!(EasySharedPref.read("jwt_token", "").isEmpty())) {
                                progress.dismiss();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                progress.dismiss();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }

                        }
                    }
                } else {
                    server_maintance.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {


            }

        });
    }


    public class DownloadFile extends AsyncTask<String, Integer, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SplashActivity.this);
            progressDialog.setTitle("Khelo new Verson: " + updatedVersion);
            progressDialog.setMessage("Downloading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }


        @RequiresApi(api = Build.VERSION_CODES.Q)
        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            File apkFile = new File(SplashActivity.this.getCacheDir(), app_name);
            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", apkFile);

            Intent promptInstall = new Intent(Intent.ACTION_VIEW).setDataAndType(uri, "application/vnd.android.package-archive");

            promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            promptInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(promptInstall);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                java.net.URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int fileLength = connection.getContentLength();
                String filePath = Environment.getExternalStorageDirectory().getPath();

                Log.i(TAG, "Filepath : " + filePath + " " + app_name);
                InputStream input = new BufferedInputStream(url.openStream());

                File file = new File(SplashActivity.this.getCacheDir(), app_name);
                FileOutputStream outputStream = new FileOutputStream(file);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress((int) (total * 100 / fileLength));
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
                outputStream.close();
                input.close();

                return "done";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "xxx";
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.setProgress(values[0]);
        }
    }
}