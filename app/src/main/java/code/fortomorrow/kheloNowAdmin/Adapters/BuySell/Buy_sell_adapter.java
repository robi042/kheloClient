package code.fortomorrow.kheloNowAdmin.Adapters.BuySell;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Products.Product_response;
import code.fortomorrow.kheloNowAdmin.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Buy_sell_adapter extends RecyclerView.Adapter<Buy_sell_adapter.ViewHolder> {
    private List<Product_response.M> productList;
    Context context;

    public Buy_sell_adapter(Context context, List<Product_response.M> productList) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.buy_sell_item_card, parent, false);
        return new ViewHolder(view);
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

                //holder.priceText.setPaintFlags(holder.nameText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.discountLayout.setVisibility(View.VISIBLE);
                holder.discountedPrice.setText("৳"+String.valueOf(response.discountedPrice));
                holder.productPrice.setTextColor(Color.RED);
                holder.productPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            }
        } catch (Exception e) {

        }

        holder.productImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.interpolation));
        Picasso.get().load(response.image).into(holder.productImage);

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(holder.productImage);
        pAttacher.update();



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private OnBuyNowClickListener onBuyNowClickListener;
    private OnImageClickListener onImageClickListener;
    private OnWatchClickListener onWatchClickListener;

    public interface OnBuyNowClickListener {
        void OnBuyNowButtonClick(int position);
    }

    public interface OnImageClickListener {
        void OnProductImageClick(int position);
    }

    public interface OnWatchClickListener {
        void OnWatchButtonClick(int position);
    }

    public void setOnClickListener(Buy_sell_adapter.OnBuyNowClickListener onBuyNowClickListener, Buy_sell_adapter.OnImageClickListener onImageClickListener, Buy_sell_adapter.OnWatchClickListener onWatchClickListener) {
        this.onBuyNowClickListener = onBuyNowClickListener;
        this.onImageClickListener = onImageClickListener;
        this.onWatchClickListener = onWatchClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, discountedPrice, descriptionText, discountText;
        AppCompatButton buyNowButton, watchNowButton;
        LinearLayout discountLayout, boomLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImageID);
            productName = itemView.findViewById(R.id.productNameID);
            productPrice = itemView.findViewById(R.id.productPriceID);
            discountedPrice = itemView.findViewById(R.id.discountPriceTextID);
            discountLayout = itemView.findViewById(R.id.discountLayoutID);
            boomLayout = itemView.findViewById(R.id.boomLayoutID);
            buyNowButton = itemView.findViewById(R.id.buyNowButtonID);
            watchNowButton = itemView.findViewById(R.id.watchNowButtonID);
            descriptionText = itemView.findViewById(R.id.descriptionTextID);
            discountText = itemView.findViewById(R.id.discountTextID);

            //Matrix matrix = new Matrix();
            //matrix.setRectToRect(new RectF(0,0,productImage.getMeasuredWidth(),productImage.getMeasuredHeight()),new RectF(0,0,productImage.getMeasuredWidth(),productImage.getMeasuredHeight()), Matrix.ScaleToFit.CENTER);


            buyNowButton.setOnClickListener(new View.OnClickListener() {
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

            watchNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onWatchClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onWatchClickListener.OnWatchButtonClick(position);
                        }
                    }
                }
            });

            productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onImageClickListener.OnProductImageClick(position);
                        }
                    }
                }
            });
        }
    }
}
