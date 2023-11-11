package code.fortomorrow.kheloNowAdmin.Adapters.Shop;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.BuySell.Buy_sell_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Products.Product_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Shop_adapter extends RecyclerView.Adapter<Shop_adapter.ViewHolder> {

    private List<Product_response.M> productList;
    Context context;

    public Shop_adapter(Context context, List<Product_response.M> productList) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_item_card, parent, false);
        return new Shop_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product_response.M response = productList.get(position);

        holder.productName.setText(response.title);
        holder.productPrice.setText("৳"+String.valueOf(response.price));
        holder.descriptionText.setText(response.description);

        try {
            if (response.hasDiscount) {
                holder.boomLayout.setVisibility(View.VISIBLE);
                holder.discountText.setText(String.valueOf(response.discount) + "%");
                //holder.productPrice.setText(holder.productPrice.getText().toString(), TextView.BufferType.SPANNABLE);
                //Spannable spannable = (Spannable) holder.productPrice.getText();
                //spannable.setSpan(new StrikethroughSpan(), 0, holder.productPrice.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.productPrice.setBackgroundResource(R.drawable.strike_through_textview);
                holder.discountLayout.setVisibility(View.VISIBLE);
                holder.discountedPrice.setText("৳"+String.valueOf(response.discountedPrice));

                holder.productPrice.setTextColor(Color.RED);
                holder.productPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            }
        } catch (Exception e) {

        }

        Picasso.get().load(response.image).into(holder.productImage);

        setFadeAnimation(holder.productImage);
        //holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private OnBuyNowClickListener onBuyNowClickListener;
    private OnItemClickListener onItemClickListener;

    public interface OnBuyNowClickListener {
        void OnBuyNowButtonClick(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Shop_adapter.OnBuyNowClickListener onBuyNowClickListener, Shop_adapter.OnItemClickListener onItemClickListener) {
        this.onBuyNowClickListener = onBuyNowClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, discountedPrice, descriptionText, discountText;
        AppCompatButton orderNowButton;
        LinearLayout discountLayout, boomLayout;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImageID);
            productName = itemView.findViewById(R.id.productNameID);
            productPrice = itemView.findViewById(R.id.productPriceID);
            discountedPrice = itemView.findViewById(R.id.discountPriceTextID);
            discountLayout = itemView.findViewById(R.id.discountLayoutID);
            boomLayout = itemView.findViewById(R.id.boomLayoutID);
            orderNowButton = itemView.findViewById(R.id.orderNowButtonID);
            descriptionText = itemView.findViewById(R.id.descriptionTextID);
            discountText = itemView.findViewById(R.id.discountTextID);
            mainLayout = itemView.findViewById(R.id.mainLayoutID);

            orderNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBuyNowClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onBuyNowClickListener.OnBuyNowButtonClick(position);
                        }
                    }
                }
            });

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    private void setFadeAnimation(View view) {
        final int FADE_DURATION = 1000;
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        //anim.setFillAfter(true);
        view.startAnimation(anim);
    }
}
