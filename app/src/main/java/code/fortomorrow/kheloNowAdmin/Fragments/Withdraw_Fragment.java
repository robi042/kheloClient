package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.WalletActivity;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyResponse.AddMoneyResponse;
import code.fortomorrow.kheloNowAdmin.Model.Profile.ProfileResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Withdraw_Fragment extends Fragment {
    Spinner spinner;
    EditText mobile, amount;
    Button withdraw;
    String payment_method, mob_number, bal_amount, date, time;
    String uid;
    View myView;
    //    DatabaseReference bData;
    String mkey;
    private String passwordUser;
    private String jwt_token, secret_id;
    static int old_winning, new_winning;
    private APIService apiService;
    private static Tracker mTracker;
    Dialog loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getActivity());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        //Log.d("secret_id", "" + secret_id + " " + jwt_token);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdraw_, container, false);
        myView = view;

//        uid = SharedPrefManager.getInstance(getContext()).getUID();
//        passwordUser = SharedPref.read("password","");
        //System.out.println(uid);
        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        spinner = view.findViewById(R.id.spinner);
        mobile = view.findViewById(R.id.mobile);
        amount = view.findViewById(R.id.amount);
        withdraw = view.findViewById(R.id.withdraw);


        //Get Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat time_format = new SimpleDateFormat("hh:mm a");
        Date dateee = new Date(System.currentTimeMillis());
        date = formatter.format(dateee);
        time = time_format.format(dateee);


        final String[] paths = {"Choose Method", "Bkash", "Nagad", "Rocket"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0)
                    payment_method = null;

                else {
                    payment_method = paths[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mob_number = mobile.getText().toString();
                bal_amount = amount.getText().toString();

                if (!TextUtils.isEmpty(mob_number) && !TextUtils.isEmpty(bal_amount) && !TextUtils.isEmpty(payment_method)) {
                    final int amountt = Integer.parseInt(bal_amount);


                    apiService.getProfile(secret_id, jwt_token).enqueue(new retrofit2.Callback<ProfileResponse>() {
                        @Override
                        public void onResponse(retrofit2.Call<ProfileResponse> call, Response<ProfileResponse> response) {

                            if (response.body().getE() == 0) {
                                int winning = response.body().getM().getWinningBalance();
                                if (amountt <= winning && amountt > 99) {

                                    Dialog confirmAlert = new Dialog(getActivity());
                                    confirmAlert.setContentView(R.layout.confirmation_alert);
                                    confirmAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    confirmAlert.show();

                                    Window window = confirmAlert.getWindow();
                                    WindowManager.LayoutParams wlp = window.getAttributes();
                                    wlp.gravity = Gravity.CENTER;
                                    wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                                    window.setAttributes(wlp);

                                    TextView yesButton = confirmAlert.findViewById(R.id.yesButtonID);
                                    TextView noButton = confirmAlert.findViewById(R.id.noButtonID);

                                    yesButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mobile.setText("");
                                            amount.setText("");

                                            loader.show();
                                            apiService.getResponseAddMoney(secret_id, jwt_token, "Withdraw Money",
                                                    String.valueOf(amountt), payment_method, mob_number).enqueue(new Callback<AddMoneyResponse>() {
                                                @Override
                                                public void onResponse(Call<AddMoneyResponse> call, Response<AddMoneyResponse> response) {
                                                    //Toast.makeText(getActivity(), String.valueOf(response.body().getE()), Toast.LENGTH_SHORT).show();
                                                    loader.dismiss();
                                                    if (response.body().getE() == 0) {
                                                        FirebaseMessaging.getInstance().subscribeToTopic("NotificationForWithdrawMoney" + response.body().getM());

                                                        final SweetAlertDialog pDialog = new SweetAlertDialog(myView.getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                                        pDialog.setTitleText("Success...")
                                                                .setContentText("Your request has been sent!");
                                                        pDialog.show();
                                                        pDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                pDialog.cancel();
                                                            }
                                                        });
                                                        confirmAlert.dismiss();
                                                    } else if (response.body().getE() == 5) {
                                                        Toasty.error(getActivity(), "You are blocked", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<AddMoneyResponse> call, Throwable t) {
                                                    Log.d("errorxxx", t.getMessage());
                                                    loader.dismiss();
                                                    Toasty.error(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });

                                    noButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            confirmAlert.dismiss();

                                        }
                                    });


                                    /*AlertDialog.Builder mDialog = new AlertDialog.Builder(getActivity());
                                    mDialog.setTitle("Withdraw Confirmation");
                                    mDialog.setMessage("Are you sure?");
                                    AlertDialog testDialog = mDialog.create();
                                    mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            testDialog.dismiss();

                                        }

                                    });

                                    mDialog.setNegativeButton("No",
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog,
                                                                    int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert = mDialog.create();
                                    alert.show();*/
                                } else {

                                    final SweetAlertDialog pDialog = new SweetAlertDialog(myView.getContext(), SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitleText("Sorry").setContentText("Check Winning Amount again & below 100 is restricted");
                                    pDialog.show();

                                    pDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            mobile.setText("");
                                            amount.setText("");
                                            pDialog.cancel();
                                            //getActivity().recreate();
                                        }
                                    });


                                }


                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<ProfileResponse> call, Throwable t) {

                        }
                    });


                } else {

                    final SweetAlertDialog pDialog = new SweetAlertDialog(myView.getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Sorry").setContentText("Winning Amount Cannot be empty");
                    pDialog.show();

                    pDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            pDialog.cancel();

                        }
                    });
                }


            }
        });


        return view;
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


}