package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.GetTokenResponse.ResponseMessage;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static code.fortomorrow.kheloNowAdmin.Activities.LoginActivity.generateRandomDigits;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class RegistrationActivity extends Activity {

    private static long timeA;
    private static long timeNow;
    private static final long START_TIME_IN_MILLIS = 30000;
    private long mLeftInMillis;
    private boolean mTimerRunning;
    String mobile;
    LinearLayout main;
    ImageView back;
    EditText mfirst_name, mlast_name, musername, memail, mmobile, mpassword, mpromo, valid_code;
    AppCompatButton mLogin, register;
    LinearLayout videoButton;
    ImageView mEye;
    private Button get_code, not_valid_mail;

    String myRandom;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    Context context;
    String android_id = "a";
    Activity activity;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private static Tracker mTracker;
    Boolean eye = true;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = this;

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        timeNow = prefs.getLong("timenow", timeNow);
        mTimerRunning = prefs.getBoolean("mTimerRunning", false);
        timeA = prefs.getLong("time", timeA);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        //android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                //Settings.Secure.ANDROID_ID);
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        activity = (Activity) RegistrationActivity.this;
        apiService = AppConfig.getRetrofit().create(APIService.class);
        progressDialog = new ProgressDialog(RegistrationActivity.this);

        // mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.back);
        main = findViewById(R.id.main);
        mfirst_name = findViewById(R.id.first_name);
        mlast_name = findViewById(R.id.last_name);
        musername = findViewById(R.id.username);
        memail = findViewById(R.id.email);
        mmobile = findViewById(R.id.mobile);
        mpassword = findViewById(R.id.password);
        mpromo = findViewById(R.id.promo);
        register = findViewById(R.id.register);
        mLogin = findViewById(R.id.mLogin);
        valid_code = findViewById(R.id.valid_code);
        get_code = findViewById(R.id.check_mail);
        not_valid_mail = findViewById(R.id.not_valid_mail);

        valid_code.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        mpromo.setVisibility(View.GONE);
        mpassword.setVisibility(View.GONE);


//        Dialog dialog = new Dialog(RegistrationActivity.this);
//        dialog.setContentView(R.layout.congratulations);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(wlp);
//
//
//        //final EditText memail = (EditText) dialog.findViewById(R.id.email);
//        final Button submit = (Button) dialog.findViewById(R.id.congo_btn);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                finish();
//            }
//        });
//        sweetDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        sweetDialog.setTitleText("Please wait..");
//        sweetDialog.setCancelable(true);




        mLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();
//                onBackPressed();
            }

        });
        get_code.setVisibility(View.GONE);
        not_valid_mail.setVisibility(View.GONE);

        memail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                not_valid_mail.setVisibility(View.VISIBLE);

                if (emailValidator(memail.getText().toString().trim())) {

                    get_code.setVisibility(View.VISIBLE);
                    not_valid_mail.setVisibility(View.GONE);
                }
                else {

                    get_code.setVisibility(View.GONE);
                    not_valid_mail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(memail.getText().toString().trim())) {
                    get_code.setVisibility(View.GONE);
                    not_valid_mail.setVisibility(View.GONE);
                }

            }
        });

        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(main.getWindowToken(), 0);

                String first_name = mfirst_name.getText().toString().trim();
                String last_name = mlast_name.getText().toString().trim();
                String username = musername.getText().toString().trim();
                String email = memail.getText().toString().trim();
                mobile = mmobile.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String promo = "" + mpromo.getText().toString().trim();



                if (!TextUtils.isEmpty(first_name) && !TextUtils.isEmpty(last_name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(mobile)) {


//                    if (password.length() < 6) {
//                        Toasty.error(getApplicationContext(), "আপনাকে ৫ অক্ষরের থেকে বেশী পাসওয়ার্ড দিতে হবে", Toast.LENGTH_SHORT).show();
//                        mpassword.setError("পাসওয়ার্ড সাইজ কম");
//                    } else if (!TextUtils.isEmpty(password) && isEmailValid(password)) {
                        if (timeA == 0) {
                            timeA = System.currentTimeMillis() + START_TIME_IN_MILLIS;
                            mTimerRunning = true;
                            mLeftInMillis = timeA;
                            //Log.d("timelo", String.valueOf(timeA));
                            //Log.d("timeNow", String.valueOf(timeNow));

                            progressDialog.setTitle("Please Wait... ");
                            progressDialog.setTitle("A code is sending ");
                            progressDialog.show();
                            apiService.getToken(mobile, username, email).enqueue(new Callback<ResponseMessage>() {
                                @Override
                                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                    if (response.isSuccessful() && response.body().getE() == 0) {
//                                    Toasty.success(getApplicationContext(),"You will get a Code in your Phone",Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        valid_code.setVisibility(View.VISIBLE);
                                        register.setVisibility(View.VISIBLE);
                                        mpromo.setVisibility(View.VISIBLE);
                                        mpassword.setVisibility(View.VISIBLE);

//                                        Dialog dialog = new Dialog(RegistrationActivity.this);
//                                        dialog.setContentView(R.layout.dialog_forgot_pass);
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                        dialog.show();
//
//                                        Window window = dialog.getWindow();
//                                        WindowManager.LayoutParams wlp = window.getAttributes();
//                                        wlp.gravity = Gravity.CENTER;
//                                        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                                        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
//                                        window.setAttributes(wlp);
//
//
//                                        final EditText memail = (EditText) dialog.findViewById(R.id.email);
//                                        final Button submit = (Button) dialog.findViewById(R.id.submit);

                                        //memail.setHint("Enter your OTP/Token number");


                                    } else if (response.body().getE() == 2) {
                                        mmobile.setError("Number exist");
                                        Toasty.error(getApplicationContext(), "এই নাম্বার সহ একটি ব্যবহারকারী ইতিমধ্যে বিদ্যমান", Toasty.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    } else if (response.body().getE() == 3) {
                                        musername.setError("Username exist");
                                        Toasty.error(getApplicationContext(), musername.getText().toString() +" নামের একজন ব্যবহারকারীর  ইতিমধ্যে রয়েছে", Toasty.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                    else if (response.body().getE() == 4) {
                                        memail.setError("Email exist");
                                        Toasty.error(getApplicationContext(), "এই ই-মেইল সহ একটি ব্যবহারকারী ইতিমধ্যে বিদ্যমান", Toasty.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                    else if (response.body().getE() == 5) {
                                        Toasty.error(getApplicationContext(), "আপনার মেসেজ লিমিট শেষ(দৈনিক ৩ বার)", Toasty.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    } else {
                                        Toasty.error(getApplicationContext(), "Wrong Number or User exist", Toasty.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                    progressDialog.dismiss();

                                }
                            });


                        } else {
                            timeNow = timeA - System.currentTimeMillis();
                            if (timeNow < 0) {


                                progressDialog.setTitle("Please Wait... ");
                                progressDialog.setTitle("A code is sending ");
                                progressDialog.show();
                                apiService.getToken(mobile, username, email).enqueue(new Callback<ResponseMessage>() {
                                    @Override
                                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                        if (response.isSuccessful() && response.body().getE() == 0) {
                                            progressDialog.dismiss();
                                            valid_code.setVisibility(View.VISIBLE);
                                            register.setVisibility(View.VISIBLE);
                                            mpromo.setVisibility(View.VISIBLE);
                                            mpassword.setVisibility(View.VISIBLE);
//
//                                            Dialog dialog = new Dialog(RegistrationActivity.this);
//                                            dialog.setContentView(R.layout.dialog_forgot_pass);
//                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                            dialog.show();
//
//                                            Window window = dialog.getWindow();
//                                            WindowManager.LayoutParams wlp = window.getAttributes();
//                                            wlp.gravity = Gravity.CENTER;
//                                            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                                            //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
//                                            window.setAttributes(wlp);
//
//
//                                            final EditText memail = (EditText) dialog.findViewById(R.id.email);
//                                            final Button submit = (Button) dialog.findViewById(R.id.submit);

//                                            submit.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View view) {
//                                                    progressDialog.setTitle("Please Wait... ");
//                                                    progressDialog.show();
//                                                    String full_name = first_name + " " + last_name;
//                                                    apiService.registration(full_name, email, mobile, password, username, promo, promo, memail.getText().toString()).enqueue(new Callback<ResponseMessage>() {
//                                                        @Override
//                                                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
//                                                            progressDialog.dismiss();
//                                                            if (response.isSuccessful()) {
//                                                                if (response.body().getE() == 0) {
//                                                                    dialog.dismiss();
//                                                                    Toasty.success(getApplicationContext(), "Registration Successfully", Toasty.LENGTH_SHORT).show();
//                                                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                                                                    finish();
//                                                                } else {
//                                                                    dialog.dismiss();
//                                                                    Toasty.error(getApplicationContext(), "Token wrong ").show();
//                                                                }
//                                                            } else {
//                                                                Toasty.error(getApplicationContext(), "Error" + response.message(), Toasty.LENGTH_SHORT).show();
//                                                            }
//
//                                                        }
//
//                                                        @Override
//                                                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
//                                                            progressDialog.dismiss();
//                                                            Toasty.error(getApplicationContext(), "Error" + t.toString(), Toasty.LENGTH_SHORT).show();
//
//                                                        }
//                                                    });
//
//
//                                                }
//                                            });
                                        } else if (response.body().getE() == 2) {
                                            mmobile.setError("Number exist");
                                            Toasty.error(getApplicationContext(), "Number exist", Toasty.LENGTH_LONG).show();
                                            progressDialog.dismiss();

                                        } else if (response.body().getE() == 3) {
                                            musername.setError("Username exist");
                                            Toasty.error(getApplicationContext(), "Username exist", Toasty.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                        else if (response.body().getE() == 4) {
                                            memail.setError("Email exist");
                                            Toasty.error(getApplicationContext(), "এই ই-মেইল সহ একটি ব্যবহারকারী ইতিমধ্যে বিদ্যমান", Toasty.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                        else if (response.body().getE() == 5) {
                                            Toasty.error(getApplicationContext(), "আপনার মেসেজ লিমিট শেষ(দৈনিক ৩ বার)", Toasty.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        } else {
                                            Toasty.error(getApplicationContext(), "Wrong Number or User exist", Toasty.LENGTH_LONG).show();
                                            progressDialog.dismiss();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                        progressDialog.dismiss();

                                    }
                                });
                                //Log.d("timechaga", String.valueOf(timeA));
                                //Log.d("timenow", String.valueOf(timeNow));
                                timeNow = 0;
                                timeA = 0;
                                mTimerRunning = false;
                            } else {
                                Toast.makeText(RegistrationActivity.this, "আবার ৩০ সেকেন্ড পরে চেষ্টা করুন", Toast.LENGTH_SHORT).show();
                            }
                        }


//                    } else {
//                        mpassword.setError("স্পেশাল ক্যারেক্টার মিসিং");
//                        Toasty.error(getApplicationContext(), "পাসওয়ার্ড এ অবশ্যই একটি স্পেশাল ক্যারেক্টার থাকতে হবে( যেমনঃ - @, # )", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                } else {
                    Toast.makeText(getApplicationContext(), "সকল ফিল্ড দেওয়া আবশ্যক", Toast.LENGTH_SHORT).show();

                    if (TextUtils.isEmpty(first_name)) {
                        mfirst_name.setError(first_name);
                        return;
                    }

                    if (TextUtils.isEmpty(last_name)) {
                        mlast_name.setError("Last Name cannot be empty");
                        return;
                    }

                    if (TextUtils.isEmpty(username)) {
                        musername.setError("Username cannot be empty");
                        return;
                    }

                    if (TextUtils.isEmpty(email)) {
                        memail.setError("Email cannot be empty");
                        return;
                    }

                    if (TextUtils.isEmpty(mobile)) {
                        mmobile.setError("Mobile Number cannot be empty");
                        return;
                    }

//                    if (TextUtils.isEmpty(password)) {
//                        mpassword.setError("Password cannot be empty");
//                        return;
//                    }
                    if(TextUtils.isEmpty(valid_code.getText().toString().trim())){
                        valid_code.setError("Code cannot be empty");
                        return;
                    }


                }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = mfirst_name.getText().toString().trim();
                String last_name = mlast_name.getText().toString().trim();
                String username = musername.getText().toString().trim();
                String email = memail.getText().toString().trim();
                mobile = mmobile.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String promo = "" + mpromo.getText().toString().trim();
                if (TextUtils.isEmpty(first_name)) {
                    mfirst_name.setError("First Name cannot be empty");
                    return;
                }

                else if (TextUtils.isEmpty(last_name)) {
                    mlast_name.setError("Last Name cannot be empty");
                    return;
                }

                else if (TextUtils.isEmpty(username)) {
                    musername.setError("Username cannot be empty");
                    return;
                }

                else if (TextUtils.isEmpty(email)) {
                    memail.setError("Email cannot be empty");
                    return;
                }

                else if (TextUtils.isEmpty(mobile)) {
                    mmobile.setError("Mobile Number cannot be empty");
                    return;
                }

                else if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Password cannot be empty");
                    return;
                }
                else if(TextUtils.isEmpty(valid_code.getText().toString().trim())){
                    valid_code.setError("Code cannot be empty");
                    return;
                }
                else{
                    if (password.length() < 6) {
                        Toasty.error(getApplicationContext(), "আপনাকে ৫ অক্ষরের থেকে বেশী পাসওয়ার্ড দিতে হবে", Toast.LENGTH_SHORT).show();
                        mpassword.setError("পাসওয়ার্ড সাইজ কম");
                    } else if (!TextUtils.isEmpty(password) && isEmailValid(password)) {
                        progressDialog.setTitle("Please Wait... ");
                        progressDialog.show();
                        String full_name = first_name + " " + last_name;
                        apiService.registration(full_name, email, mobile, password, username, promo, promo, valid_code.getText().toString()).enqueue(new Callback<ResponseMessage>() {
                            @Override
                            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    if (response.body().getE() == 0) {
                                        //dialog.dismiss();
                                        Dialog dialog = new Dialog(RegistrationActivity.this);
                                        dialog.setContentView(R.layout.congratulations);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        Window window = dialog.getWindow();
                                        WindowManager.LayoutParams wlp = window.getAttributes();
                                        wlp.gravity = Gravity.CENTER;
                                        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                                        window.setAttributes(wlp);


                                        //final EditText memail = (EditText) dialog.findViewById(R.id.email);
                                        final Button submit = (Button) dialog.findViewById(R.id.congo_btn);
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                                finish();
                                            }
                                        });
                                        //Toasty.success(getApplicationContext(), "Registration Successfully", Toasty.LENGTH_SHORT).show();

                                    } else {
                                        //dialog.dismiss();
                                        Toasty.error(getApplicationContext(), "Token wrong ").show();
                                    }
                                } else {
                                    Toasty.error(getApplicationContext(), "Error" + response.message(), Toasty.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                progressDialog.dismiss();
                                Toasty.error(getApplicationContext(), "Error" + t.toString(), Toasty.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else {
                        mpassword.setError("স্পেশাল ক্যারেক্টার মিসিং");
                        Toasty.error(getApplicationContext(), "পাসওয়ার্ড এ অবশ্যই একটি স্পেশাল ক্যারেক্টার থাকতে হবে( যেমনঃ - @, # )", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });

        videoButton = findViewById(R.id.videoButtonID);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "Sign Up";
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

        mEye = findViewById(R.id.mEye);
        mEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }


    public static boolean isEmailValid(String email) {
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(email);
        return hasSpecial.find();
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    static String getRandomString(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("time", timeA);

        editor.putLong("timeNow", timeNow);
        editor.putBoolean("mTimerRunning", mTimerRunning);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        timeNow = prefs.getLong("timenow", timeNow);
        timeA = prefs.getLong("time", timeA);
//        Log.d("timechaga",String.valueOf(time));
//        Log.d("timenow",String.valueOf(timeNow));
        mTimerRunning = prefs.getBoolean("mTimerRunning", false);
        if (mTimerRunning) {
            mLeftInMillis = timeA - System.currentTimeMillis();
            if (mLeftInMillis < 0) {
                mTimerRunning = false;
                timeA = 0;
                timeNow = 0;
                mTimerRunning = false;
            }
        } else {

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("time", timeA);
        editor.putLong("timeNow", timeNow);
        editor.putBoolean("mTimerRunning", mTimerRunning);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("time", timeA);
        editor.putLong("timeNow", timeNow);
        editor.putBoolean("mTimerRunning", mTimerRunning);
        editor.apply();
    }


}