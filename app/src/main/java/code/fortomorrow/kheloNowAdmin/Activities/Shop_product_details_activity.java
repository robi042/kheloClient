package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Shop_product_details_activity extends AppCompatActivity {

    ImageView productImage, backButton;
    APIService apiService;
    String jwt_token, secret_id;

    String productID, title, price, discount, hasDiscount, discountedPrice, description, link, image;
    LinearLayout boomLayout, discountLayout, mainLayout;
    TextView discountText, productNameText, descriptionText, productPriceText, discountPriceText;
    AppCompatButton orderNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_product_details);


        init_view();

        mainLayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.interpolation));
        //productImage.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.interpolation));

        productID = getIntent().getStringExtra("product_id");
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        image = getIntent().getStringExtra("image");
        link = getIntent().getStringExtra("link");
        price = getIntent().getStringExtra("price");
        hasDiscount = getIntent().getStringExtra("hasDiscount");
        discount = getIntent().getStringExtra("discount");
        discountedPrice = getIntent().getStringExtra("discounted_price");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (hasDiscount.equals("true")) {
            boomLayout.setVisibility(View.VISIBLE);
            discountLayout.setVisibility(View.VISIBLE);
            discountText.setText(discount + "%");
            //productPriceText.setText(productPriceText.getText().toString(), TextView.BufferType.SPANNABLE);
            //Spannable spannable = (Spannable) productPriceText.getText();
            //spannable.setSpan(new StrikethroughSpan(), 0, productPriceText.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            productPriceText.setBackgroundResource(R.drawable.strike_through_textview);
            productPriceText.setTextColor(Color.RED);
            productPriceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        } else if (hasDiscount.equals("false")) {
            boomLayout.setVisibility(View.GONE);
        }

        productNameText.setText(title);
        descriptionText.setText(description);
        productPriceText.setText("Price: " + "৳" + price);
        discountPriceText.setText("৳" + discountedPrice);
        Picasso.get().load(image).into(productImage);

        //Log.d("pricexx", price + " "+ discountedPrice);

        orderNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = link;

                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Shop_product_details_activity.this, "hi", Toast.LENGTH_SHORT).show();
                Dialog imageAlert = new Dialog(Shop_product_details_activity.this);
                imageAlert.setContentView(R.layout.image_view_alert);
                imageAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //imageAlert.setCancelable(false);
                imageAlert.show();

                Window window = imageAlert.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);

                ImageView closeButton = imageAlert.findViewById(R.id.closeButtonID);
                ImageView resultImage = imageAlert.findViewById(R.id.resultImageID);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageAlert.dismiss();
                    }
                });

                PhotoViewAttacher pAttacher;
                pAttacher = new PhotoViewAttacher(resultImage);
                pAttacher.update();

                Picasso.get().load(image).into(resultImage);
            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        productImage = findViewById(R.id.productImageID);
        backButton = findViewById(R.id.backButtonID);

        boomLayout = findViewById(R.id.boomLayoutID);
        discountText = findViewById(R.id.discountTextID);
        productNameText = findViewById(R.id.productNameTextID);
        descriptionText = findViewById(R.id.descriptionTextID);
        productPriceText = findViewById(R.id.productPriceTextID);
        discountLayout = findViewById(R.id.discountLayoutID);
        discountPriceText = findViewById(R.id.discountPriceTextID);
        mainLayout = findViewById(R.id.mainLayoutID);

        orderNowButton = findViewById(R.id.orderNowButtonID);
    }
}