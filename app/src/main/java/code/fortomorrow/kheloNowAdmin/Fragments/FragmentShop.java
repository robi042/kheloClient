package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.Shop_product_details_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.BuySell.Buy_sell_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.Shop.Shop_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Products.Product_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoViewAttacher;


public class FragmentShop extends Fragment implements Shop_adapter.OnBuyNowClickListener, Shop_adapter.OnItemClickListener {
    RecyclerView itemRecyclerView;
    Dialog loader;
    APIService apiService;
    String jwt_token, secret_id;
    private List<Product_response.M> productList = new ArrayList<>();
    private Shop_adapter adapter;
    LinearLayout noDataLayout;
    ImageView goToPageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        item_view(view);

        show_item();

        goToPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri uri = Uri.parse("https://m.facebook.com/messages/thread/105161937694520/?entrypoint=web%3Atrigger%3Afb_page_cta_page_primary_button");
                    //Uri uri = Uri.parse("https://www.facebook.com/khelolive.page/"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //go_to_page_alert();
        return view;
    }

    private void go_to_page_alert() {
        Dialog goToPageAlert = new Dialog(getActivity());
        goToPageAlert.setContentView(R.layout.go_to_page_alert);
        goToPageAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        goToPageAlert.setCancelable(false);
        goToPageAlert.show();

        Window window = goToPageAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //wlp.windowAnimations = R.style.DialogAnimation;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        // wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView goToPageButton = goToPageAlert.findViewById(R.id.goToPageButtonID);

        goToPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri uri = Uri.parse("https://m.facebook.com/messages/thread/105161937694520/?entrypoint=web%3Atrigger%3Afb_page_cta_page_primary_button");
                    //Uri uri = Uri.parse("https://www.facebook.com/khelolive.page/"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void show_item() {

        apiService.get_product_list(secret_id, jwt_token, "shop").enqueue(new Callback<Product_response>() {
            @Override
            public void onResponse(Call<Product_response> call, Response<Product_response> response) {

                if (response.body().e == 0 && response.body().m.size() != 0) {
                    noDataLayout.setVisibility(View.GONE);
                    itemRecyclerView.setVisibility(View.VISIBLE);

                    productList = response.body().m;
                    adapter = new Shop_adapter(getActivity(), productList);
                    adapter.setOnClickListener(FragmentShop.this::OnBuyNowButtonClick, FragmentShop.this::OnItemClick);
                    itemRecyclerView.setAdapter(adapter);
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    itemRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Product_response> call, Throwable t) {
                noDataLayout.setVisibility(View.VISIBLE);
                itemRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void item_view(View view) {
        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getActivity());
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        itemRecyclerView = view.findViewById(R.id.itemRecyclerViewID);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        noDataLayout = view.findViewById(R.id.noDataLayoutID);
        goToPageButton = view.findViewById(R.id.goToPageButtonID);


    }

    @Override
    public void OnBuyNowButtonClick(int position) {
        Product_response.M response = productList.get(position);

        String url = response.link;

        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    public void OnItemClick(int position) {
        Product_response.M response = productList.get(position);

        Intent intent = new Intent(getActivity(), Shop_product_details_activity.class);
        intent.putExtra("product_id", String.valueOf(response.productId));
        intent.putExtra("title", response.title);
        intent.putExtra("description", response.description);
        intent.putExtra("image", response.image);
        intent.putExtra("link", response.link);
        intent.putExtra("price", String.valueOf(response.price));
        try {
            intent.putExtra("hasDiscount", String.valueOf(response.hasDiscount));
        } catch (Exception e) {
            intent.putExtra("hasDiscount", "false");
        }
        try {
            intent.putExtra("discount", String.valueOf(response.discount));
        } catch (Exception e) {
            intent.putExtra("discount", "0");
        }
        try {
            intent.putExtra("discounted_price", String.valueOf(response.discountedPrice));
        } catch (Exception e) {
            intent.putExtra("discounted_price", "0");
        }

        startActivity(intent);
        //Toast.makeText(getActivity(), String.valueOf(response.productId), Toast.LENGTH_SHORT).show();
    }
}