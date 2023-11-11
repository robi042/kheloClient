package code.fortomorrow.kheloNowAdmin.Adapters.Ludo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.OngoingAdapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_result_pagination_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;

public class Ludo_result_game_list_adapter extends RecyclerView.Adapter<Ludo_result_game_list_adapter.ViewHolder> {

    private List<Ludo_result_pagination_response.M> ludoResultGameList;

    public Ludo_result_game_list_adapter(List<Ludo_result_pagination_response.M> ludoResultGameList) {
        this.ludoResultGameList = ludoResultGameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_result_game_list_card, parent, false);
        return new Ludo_result_game_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_result_pagination_response.M response = ludoResultGameList.get(position);
        holder.gameTitleText.setText(response.getTitle());
        holder.dateTimeText.setText(response.getMatchDate() + " " + response.getMatchTime());
        holder.prizeText.setText(response.getTotalPrize());
        holder.entryFeeText.setText(response.getEntryFee());
        holder.boardTypeText.setText(response.getHostApp());
        holder.winnerPrizeText.setText(response.getTotalPrize() + " " + "tk");


        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (response.getHasMessage()) {
                        Toasty.error(holder.itemView.getContext(), response.getMessage(), Toasty.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    //holder.messageText.setText(response.getMessage());
                    //Toast.makeText(holder.itemView.getContext(), "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {
            if (response.getJoined()) {
                holder.matchStateText.setText("JOINED");

                try {
                    if (response.getHasMessage()) {
                        holder.messageButton.setVisibility(View.VISIBLE);
                        //holder.messageText.setText(response.getMessage());
                        //
                    }
                } catch (Exception e) {
                    //holder.messageText.setVisibility(View.GONE);
                    //holder.messageText.setText(response.getMessage());
                    holder.messageButton.setVisibility(View.GONE);
                }
            }
        } catch (Exception er) {
            holder.matchStateText.setText("NOT JOINED");

        }


    }

    @Override
    public int getItemCount() {
        return ludoResultGameList.size();
    }

    private OnItemClickListener onClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    private OnLudoPrizeClickListener onLudoPrizeClickListener;


    public interface OnLudoPrizeClickListener {
        void OnLudoPrizeClick(int position);
    }


    public void setOnItemClickListener(Ludo_result_game_list_adapter.OnItemClickListener onItemClickListener, Ludo_result_game_list_adapter.OnLudoPrizeClickListener onLudoPrizeClickListener) {

        this.onClickListener = onItemClickListener;
        this.onLudoPrizeClickListener = onLudoPrizeClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, entryFeeText, boardTypeText, messageText;
        TextView matchStateText, winnerPrizeText;
        LinearLayout prizeDetailsButton, winnerLayout, messageButton;
        ImageView arrowDownImage, arrowUpImage;

        Boolean state = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            entryFeeText = itemView.findViewById(R.id.entryFeeTextID);
            boardTypeText = itemView.findViewById(R.id.boardTypeID);
            matchStateText = itemView.findViewById(R.id.matchStateTextID);
            prizeDetailsButton = itemView.findViewById(R.id.prizeDetailsButtonID);
            winnerLayout = itemView.findViewById(R.id.winnerLayoutID);
            winnerPrizeText = itemView.findViewById(R.id.winnerPrizeTextID);
            messageText = itemView.findViewById(R.id.messageTextID);

            arrowDownImage = itemView.findViewById(R.id.arrowDownImageID);
            arrowUpImage = itemView.findViewById(R.id.arrowUpImageID);
            messageButton = itemView.findViewById(R.id.messageButtonID);

            prizeDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onLudoPrizeClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onLudoPrizeClickListener.OnLudoPrizeClick(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
