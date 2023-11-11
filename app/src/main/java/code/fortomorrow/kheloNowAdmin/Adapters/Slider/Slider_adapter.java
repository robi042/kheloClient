package code.fortomorrow.kheloNowAdmin.Adapters.Slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.glide.slider.library.SliderAdapter;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_result_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Slider.Slider_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Slider_adapter extends SliderViewAdapter<Slider_adapter.Holder> {
    List<Slider_list_response.M> sliderList;

    public Slider_adapter(List<Slider_list_response.M> sliderList) {
        this.sliderList = sliderList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_card, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        //viewHolder.imageView.setImageResource(images[position]);
        Slider_list_response.M response = sliderList.get(position);
        Picasso.get().load(response.getImageLink()).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(viewHolder.itemView.getContext(), "This is item in position " + position, Toast.LENGTH_SHORT).show();

                if (onClickListener != null) {
                    //int position = getItemPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.OnItemClick(position);
                    }
                }
            }
        });

    }

    @Override
    public int getCount() {
        return sliderList.size();
    }

    private OnItemClickListener onClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(Slider_adapter.OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class Holder extends SliderViewAdapter.ViewHolder {

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getItemPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.OnItemClick(position);
                        }
                    }
                }
            });*/


        }
    }
}
