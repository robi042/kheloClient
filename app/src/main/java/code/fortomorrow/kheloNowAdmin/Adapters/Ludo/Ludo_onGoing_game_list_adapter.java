package code.fortomorrow.kheloNowAdmin.Adapters.Ludo;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.OngoingAdapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_onGoing_game_list_adapter extends RecyclerView.Adapter<Ludo_onGoing_game_list_adapter.Viewholder> {
    private List<Ludo_tournament_game_list_response.M> ludoOnGoingList;

    public Ludo_onGoing_game_list_adapter(List<Ludo_tournament_game_list_response.M> ludoOnGoingList) {
        this.ludoOnGoingList = ludoOnGoingList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_ongoing_game_list_card, parent, false);
        return new Ludo_onGoing_game_list_adapter.Viewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Ludo_tournament_game_list_response.M response = ludoOnGoingList.get(position);
        holder.gameTitleText.setText(response.getTitle());
        holder.dateTimeText.setText(response.getMatchDate() + " " + response.getMatchTime());
        holder.prizeText.setText(response.getTotalPrize());
        holder.entryFeeText.setText(response.getEntryFee());
        holder.boardTypeText.setText(response.getHostApp());

        try {
            if (response.getJoined()) {
                holder.uploadImageButton.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            holder.uploadImageButton.setVisibility(View.INVISIBLE);
        }

        /*try{
            if(response.getHasImage()){
                holder.imageText.setText("Uploaded");
                holder.uploadImageButton.setBackgroundTintList(ColorStateList.valueOf(R.color.green));
            }
        }catch (Exception ex){

        }*/

    }

    @Override
    public int getItemCount() {
        return ludoOnGoingList.size();
    }

    private OnSpectateClickListener onSpectateListener;
    private OnImageClickListener onImageClickListener;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnSpectateClickListener {
        void OnSpectateClick(int position);
    }

    public interface OnImageClickListener{
        void OnImageClick(int position);
    }

    public void setOnItemClickListener(Ludo_onGoing_game_list_adapter.OnSpectateClickListener onSpectateListener, Ludo_onGoing_game_list_adapter.OnImageClickListener onImageClickListener) {
        this.onSpectateListener = onSpectateListener;
        this.onImageClickListener = onImageClickListener;
        //this.onItemClickListener = onItemClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, entryFeeText, boardTypeText, imageText;
        LinearLayout spectateButton, uploadImageButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            entryFeeText = itemView.findViewById(R.id.entryFeeTextID);
            boardTypeText = itemView.findViewById(R.id.boardTypeID);
            spectateButton = itemView.findViewById(R.id.spectateButtonID);
            uploadImageButton = itemView.findViewById(R.id.uploadImageButtonID);
            imageText = itemView.findViewById(R.id.imageTextID);

            itemView.setOnClickListener(new View.OnClickListener() {
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

            spectateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSpectateListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onSpectateListener.OnSpectateClick(position);
                        }
                    }
                }
            });

            uploadImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onImageClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onImageClickListener.OnImageClick(position);
                        }
                    }
                }
            });
        }
    }
}
