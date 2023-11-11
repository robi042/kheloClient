package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import code.fortomorrow.kheloNowAdmin.R;
import pl.droidsonroids.gif.GifImageView;

public class CongratulationActivity extends AppCompatActivity {
    private GifImageView gif_view;
    private Button congo_btn;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);

        congo_btn = findViewById(R.id.congo_btn);
//        getSupportActionBar().setElevation(0);//remove actionbar shadow
//        setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        //webView = (WebView) findViewById(R.id.webView);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("file:///drawable/congo.gif");

        //gif_view = findViewById(R.id.gif_view);
        congo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(CongratulationActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}