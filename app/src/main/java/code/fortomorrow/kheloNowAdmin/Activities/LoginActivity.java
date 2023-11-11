package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.andreseko.SweetAlert.SweetAlertDialog;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.Model.GetTokenResponse.ResponseMessage;
import code.fortomorrow.kheloNowAdmin.Model.LoginResponse.LoginResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private FloatingActionButton whatsappiconInlogin;
    LinearLayout contact;
    AppCompatButton signup, forword_btn;

    EditText memail, mpassword;
    TextView forgot;

    private APIService apiService;
    ImageView mEye;
    static boolean eye = true;


    String whatsapp_number;

    private ProgressDialog progressDialog1;

    private ProgressDialog progressDialog;
    private static Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusBar(getWindow().getDecorView().findViewById(android.R.id.content), this);
        setContentView(R.layout.activity_login);

        EasySharedPref.init(getApplicationContext());
        String jwt_token = EasySharedPref.read("jwt_token", "");
        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForAllPlayers");

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        if (!(EasySharedPref.read("jwt_token", "").isEmpty())) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        whatsappiconInlogin = findViewById(R.id.whatsappiconInlogin);
        whatsappiconInlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+8801888681608";
                String url = "https://api.whatsapp.com/send?phone=" + number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        apiService = AppConfig.getRetrofit().create(APIService.class);


        progressDialog1 = new ProgressDialog(this);
        contact = findViewById(R.id.contact);
        forgot = findViewById(R.id.forgot);
        signup = findViewById(R.id.signup);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        forword_btn = findViewById(R.id.forword_btn);
        mEye = findViewById(R.id.mEye);
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        mEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (eye) {
                    mpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eye = false;
                    mEye.setImageResource(R.drawable.ic_eye);


                } else {

                    mpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eye = true;
                    mEye.setImageResource(R.drawable.ic_invisible_eye);

                }


            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "https://api.whatsapp.com/send?phone=+88" + whatsapp_number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);

            }
        });

        forword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if (!TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(password)
                ) {
                    loginUserAccount(email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter valid phone no and password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_forgot_pass);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);


                final EditText memail = (EditText) dialog.findViewById(R.id.email);
                final Button submit = (Button) dialog.findViewById(R.id.submit);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String email = memail.getText().toString();
                        //Log.d("emailaa", email);
                        if (email.length() > 9) {
                            progressDialog1.setMessage("Sending Code to " + email + " ,\nplease wait. . .");
                            progressDialog1.show();
                            apiService.getTokenForReset(email, deviceId).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    if (response.body().getE() == 0) {
                                        progressDialog1.dismiss();
                                        dialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
                                    } else {
                                        progressDialog1.dismiss();
                                        dialog.dismiss();
                                        Toasty.error(getApplicationContext(), "You can submit after 24 hours", Toasty.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Toasty.error(getApplicationContext(), "Error", Toasty.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Enter Valid Phone", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        LinearLayout videoButton = findViewById(R.id.videoButtonID);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "Log In";
                apiService.getVideoLink("", "", type).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        if (response.body().getE() == 0) {
                            //Toast.makeText(getActivity(), response.body().getM(), Toast.LENGTH_SHORT).show();
                            String l = response.body().getM();
                            Uri uri = Uri.parse(l); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


    private void loginUserAccount(String email, String password) {

        SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.show();

        apiService.getLoginResponse(email, password, "1234564yr65eyye").enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), String.valueOf(response.body().getE()), Toast.LENGTH_SHORT).show();
                    if (response.body().getE() == 0) {
                        pDialog.dismiss();
                        EasySharedPref.write("phone_number", email);
                        EasySharedPref.write("jwt_token", response.body().getM().getApiToken());
                        EasySharedPref.write("secret_id", String.valueOf(response.body().getM().getSecretId()));
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                    } else {
                        pDialog.dismiss();
                        Toasty.error(getApplicationContext(), "আপনি পাসওয়ার্ড ভুল দিয়েছেন", Toasty.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pDialog.dismiss();
                Toasty.error(getApplicationContext(), "আপনি পাসওয়ার্ড ভুল দিয়েছেন", Toasty.LENGTH_LONG).show();
                Log.d("responseBodyx", t.toString());

            }
        });


    }

    //Notice bar white color
    public static void transparentStatusBar(View view, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window w = activity.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }


    public static String generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return String.valueOf(m + new Random().nextInt(9 * m));

    }


}