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
import code.fortomorrow.kheloNowAdmin.Adapters.BuySell.Buy_sell_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Products.Product_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoViewAttacher;


public class Fragment_buySell extends Fragment implements Buy_sell_adapter.OnBuyNowClickListener, Buy_sell_adapter.OnImageClickListener, Buy_sell_adapter.OnWatchClickListener {

    RecyclerView itemRecyclerView;
    private Buy_sell_adapter buy_sell_adapter;
    Dialog loader;
    APIService apiService;
    String jwt_token, secret_id;
    private List<Product_response.M> productList = new ArrayList<>();
    private Buy_sell_adapter adapter;
    LinearLayout noDataLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_buy_sell, container, false);

        item_view(view);

        show_item();

        return view;
    }

    private void show_item() {

        apiService.get_product_list(secret_id, jwt_token, "buy_sell").enqueue(new Callback<Product_response>() {
            @Override
            public void onResponse(Call<Product_response> call, Response<Product_response> response) {
                if (response.body().e == 0 && response.body().m.size() != 0) {
                    noDataLayout.setVisibility(View.GONE);
                    itemRecyclerView.setVisibility(View.VISIBLE);

                    productList = response.body().m;
                    adapter = new Buy_sell_adapter(getActivity(), productList);
                    adapter.setOnClickListener(Fragment_buySell.this::OnBuyNowButtonClick, Fragment_buySell.this::OnProductImageClick, Fragment_buySell.this::OnWatchButtonClick);
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
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        noDataLayout = view.findViewById(R.id.noDataLayoutID);

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
    public void OnProductImageClick(int position) {

        /*Product_response.M response = productList.get(position);

        Dialog imageAlert = new Dialog(getActivity());
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

        Picasso.get().load(response.image).into(resultImage);*/
    }

    @Override
    public void OnWatchButtonClick(int position) {
        Product_response.M response = productList.get(position);

        String url = response.watch_link;

        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}