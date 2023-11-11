package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.DailyMatchActivity;
import code.fortomorrow.kheloNowAdmin.Activities.WalletActivity;
import code.fortomorrow.kheloNowAdmin.AnalyticsApplication;
import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.getNumbers.GetNumbersResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddMoney_Fragment_new extends Fragment {

    CardView bKash, rocket, nagad;
    String date, time;
    View myView;
    String myUID;
    private WalletActivity walletac;
    private APIService apiService;
    private String jwt_token, secret_id;
    private static Tracker mTracker;
    LinearLayout videoButton;
    Dialog loader;

    public AddMoney_Fragment_new(WalletActivity walletActivity) {
        walletActivity = this.walletac;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getActivity());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_money__new, container, false);
        myView = view;

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
        //loader.show();

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        String name = this.getClass().getSimpleName();
        mTracker.setScreenName("Screen Name: " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        bKash = view.findViewById(R.id.bkash);
        rocket = view.findViewById(R.id.rocket);
        nagad = view.findViewById(R.id.nagad);
        videoButton = view.findViewById(R.id.videoButtonID);

        //Get Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat time_format = new SimpleDateFormat("hh:mm a");
        Date dateee = new Date(System.currentTimeMillis());
        date = formatter.format(dateee);
        time = time_format.format(dateee);


        bKash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog1 = new Dialog(getActivity());
                dialog1.setContentView(R.layout.add_money_dialog);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();

                Window window = dialog1.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);

                final ImageView close = (ImageView) dialog1.findViewById(R.id.close);
                final ImageView img = (ImageView) dialog1.findViewById(R.id.img);
                final TextView num1 = (TextView) dialog1.findViewById(R.id.num1);
                final TextView num2 = (TextView) dialog1.findViewById(R.id.num2);
                final EditText amount = (EditText) dialog1.findViewById(R.id.amount);
                final EditText digit = (EditText) dialog1.findViewById(R.id.digit);
                final Button verify = (Button) dialog1.findViewById(R.id.verify);

                final String b_reference_key = getRandomString(15);
                //Sitting Resources

                img.setImageDrawable(getResources().getDrawable(R.drawable.bkash));

                apiService.getNumbersList(secret_id, jwt_token, "Bkash").enqueue(new Callback<GetNumbersResponse>() {
                    @Override
                    public void onResponse(Call<GetNumbersResponse> call, Response<GetNumbersResponse> response) {

                        num1.setText(String.valueOf(response.body().getM().getFirstNumber()));
                        num2.setText(String.valueOf(response.body().getM().getSecondNumber()));
                    }

                    @Override
                    public void onFailure(Call<GetNumbersResponse> call, Throwable t) {

                    }
                });
                dialog1.show();


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });


                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        final String my_amount = amount.getText().toString().trim();
                        final String my_digit = digit.getText().toString().trim();
                        final String source = "Bkash ";

                        //((WalletActivity) getActivity()).verify_Payment(my_amount, my_digit, date, time, myUID, b_reference_key, source);

                        if (my_digit.equals(num1.getText().toString().trim()) || my_digit.equals(num2.getText().toString().trim())) {
                            Toasty.error(getActivity(), getString(R.string.wrong_number), Toasty.LENGTH_SHORT).show();
                        } else {
                            ((WalletActivity) getActivity()).verify_Payment(my_amount, my_digit, date, time, myUID, b_reference_key, source);
                            dialog1.dismiss();
                        }


                    }
                });
                num1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", num1.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getContext().getApplicationContext(), "Num1 copied", Toast.LENGTH_SHORT).show();
                    }
                });
                num2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", num2.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getContext().getApplicationContext(), "Num2 copied", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog1 = new Dialog(getActivity());
                dialog1.setContentView(R.layout.add_money_dialog);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();

                Window window = dialog1.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);

                final ImageView close = (ImageView) dialog1.findViewById(R.id.close);
                final ImageView img = (ImageView) dialog1.findViewById(R.id.img);
                final TextView num1 = (TextView) dialog1.findViewById(R.id.num1);
                final TextView num2 = (TextView) dialog1.findViewById(R.id.num2);
                final EditText amount = (EditText) dialog1.findViewById(R.id.amount);
                final EditText digit = (EditText) dialog1.findViewById(R.id.digit);
                final Button verify = (Button) dialog1.findViewById(R.id.verify);


                //Sitting Resources
                img.setImageDrawable(getResources().getDrawable(R.drawable.rocket));
                apiService.getNumbersList(secret_id, jwt_token, "Rocket").enqueue(new Callback<GetNumbersResponse>() {
                    @Override
                    public void onResponse(Call<GetNumbersResponse> call, Response<GetNumbersResponse> response) {

                        num1.setText(String.valueOf(response.body().getM().getFirstNumber()));
                        num2.setText(String.valueOf(response.body().getM().getFirstNumber()));
                    }

                    @Override
                    public void onFailure(Call<GetNumbersResponse> call, Throwable t) {

                    }
                });

                dialog1.show();


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });


                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String my_amount = amount.getText().toString().trim();
                        final String my_digit = digit.getText().toString().trim();
                        final String b_reference_key = getRandomString(15);
                        final String source = "Rocket";

                        if (my_digit.equals(num1.getText().toString().trim()) || my_digit.equals(num2.getText().toString().trim())) {
                            Toasty.error(getActivity(), getString(R.string.wrong_number), Toasty.LENGTH_SHORT).show();
                        } else {
                            ((WalletActivity) getActivity()).verify_Payment(my_amount, my_digit, date, time, myUID, b_reference_key, source);
                            dialog1.dismiss();
                        }


                    }
                });
                num1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", num1.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getContext().getApplicationContext(), "Num1 copied", Toast.LENGTH_SHORT).show();
                    }
                });
                num2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", num2.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getContext().getApplicationContext(), "Num2 copied", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        nagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog1 = new Dialog(getActivity());
                dialog1.setContentView(R.layout.add_money_dialog);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();

                Window window = dialog1.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);

                final ImageView close = (ImageView) dialog1.findViewById(R.id.close);
                final ImageView img = (ImageView) dialog1.findViewById(R.id.img);
                final TextView num1 = (TextView) dialog1.findViewById(R.id.num1);
                final TextView num2 = (TextView) dialog1.findViewById(R.id.num2);
                final EditText amount = (EditText) dialog1.findViewById(R.id.amount);
                final EditText digit = (EditText) dialog1.findViewById(R.id.digit);
                final Button verify = (Button) dialog1.findViewById(R.id.verify);


                //Sitting Resources
                img.setImageDrawable(getResources().getDrawable(R.drawable.nagad));
                apiService.getNumbersList(secret_id, jwt_token, "Nagad").enqueue(new Callback<GetNumbersResponse>() {
                    @Override
                    public void onResponse(Call<GetNumbersResponse> call, Response<GetNumbersResponse> response) {

                        num1.setText(String.valueOf(response.body().getM().getFirstNumber()));
                        num2.setText(String.valueOf(response.body().getM().getSecondNumber()));
                    }

                    @Override
                    public void onFailure(Call<GetNumbersResponse> call, Throwable t) {

                    }
                });
                dialog1.show();


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });


                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String b_reference_key = getRandomString(15);
                        final String source = "Nagad";

                        final String my_amount = amount.getText().toString().trim();
                        final String my_digit = digit.getText().toString().trim();

                        if (my_digit.equals(num1.getText().toString().trim()) || my_digit.equals(num2.getText().toString().trim())) {
                            Toasty.error(getActivity(), getString(R.string.wrong_number), Toasty.LENGTH_SHORT).show();
                        } else {
                            ((WalletActivity) getActivity()).verify_Payment(my_amount, my_digit, date, time, myUID, b_reference_key, source);
                            dialog1.dismiss();
                        }


                    }
                });
                num1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", num1.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getContext().getApplicationContext(), "Num1 copied", Toast.LENGTH_SHORT).show();
                    }
                });
                num2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", num2.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getContext().getApplicationContext(), "Num2 copied", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "Add Money";
                apiService.getVideoLink(secret_id, jwt_token, type).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        if (response.body().getE() == 0) {
                            //Toast.makeText(getActivity(), response.body().getM(), Toast.LENGTH_SHORT).show();
                            String l = response.body().getM();
                            Uri uri = Uri.parse(l); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), response.body().getM(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
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


    @Override
    public void onResume() {
        super.onResume();

    }
}