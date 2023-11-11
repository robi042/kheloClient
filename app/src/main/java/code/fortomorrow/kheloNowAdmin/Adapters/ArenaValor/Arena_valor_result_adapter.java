package code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_of_valor_match_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_valor_result_adapter extends RecyclerView.Adapter<Arena_valor_result_adapter.ViewHolder> {
    List<Arena_of_valor_match_response.M> arenaValorResultList;

    public Arena_valor_result_adapter(List<Arena_of_valor_match_response.M> arenaValorResultList) {
        this.arenaValorResultList = arenaValorResultList;
    }

    @NonNull
    @Override
    public Arena_valor_result_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.arena_valor_result_card, parent, false);
        return new Arena_valor_result_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Arena_valor_result_adapter.ViewHolder holder, int position) {
        Arena_of_valor_match_response.M response = arenaValorResultList.get(position);

        holder.titleText.setText(response.title);
        holder.scheduleText.setText(response.matchDate + " " + response.matchTime);
        holder.totalPrizeText.setText(response.totalPrize);
        holder.entryFeeText.setText(response.entryFee);
        holder.gameTypeText.setText(response.gameType);
        holder.playerTypeText.setText(response.playerType);
        holder.versionText.setText(response.version);

        try{
            if(response.isJoined){
                holder.statusText.setText("JOINED");
            }
        }catch (Exception e){
            holder.statusText.setText("NOT JOINED");
        }
    }

    @Override
    public int getItemCount() {
        return arenaValorResultList.size();
    }

    private OnArenaValorPrizeClickListener onArenaValorPrizeClickListener;
    private OnArenaValorWatchClickListener onArenaValorWatchClickListener;
    private OnArenaValorItemClickListener onArenaValorItemClickListener;

    public interface OnArenaValorPrizeClickListener {
        void OnArenaValorPrizeClick(int position);
    }

    public interface OnArenaValorItemClickListener {
        void OnArenaValorItemClick(int position);
    }

    public interface OnArenaValorWatchClickListener {
        void onArenaValorWatchClick(int position);
    }

    public void setOnClickListener(Arena_valor_result_adapter.OnArenaValorItemClickListener onArenaValorItemClickListener,Arena_valor_result_adapter.OnArenaValorPrizeClickListener onArenaValorPrizeClickListener, Arena_valor_result_adapter.OnArenaValorWatchClickListener onArenaValorWatchClickListener) {
        this.onArenaValorPrizeClickListener = onArenaValorPrizeClickListener;
        this.onArenaValorWatchClickListener = onArenaValorWatchClickListener;
        this.onArenaValorItemClickListener = onArenaValorItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, scheduleText, totalPrizeText, entryFeeText;
        TextView gameTypeText, versionText, playerTypeText, spotLeftText;
        TextView totalPlayerText, timerText, statusText;
        AppCompatButton joinButton;
        ProgressBar progressBar;
        LinearLayout totalPrizeLayoutButton, watchButton;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleTextID);
            scheduleText = itemView.findViewById(R.id.scheduleTextID);
            totalPrizeText = itemView.findViewById(R.id.totalPrizeTextID);
            entryFeeText = itemView.findViewById(R.id.entryFeeTextID);
            gameTypeText = itemView.findViewById(R.id.gameTypeTextID);
            versionText = itemView.findViewById(R.id.versionTextID);
            playerTypeText = itemView.findViewById(R.id.playerTypeTextID);
            mainLayout = itemView.findViewById(R.id.mainLayoutID);
            statusText = itemView.findViewById(R.id.statusTextID);

            totalPrizeLayoutButton = itemView.findViewById(R.id.totalPrizeLayoutButtonID);
            watchButton = itemView.findViewById(R.id.watchButtonID);

            totalPrizeLayoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onArenaValorPrizeClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorPrizeClickListener.OnArenaValorPrizeClick(position);
                        }
                    }
                }
            });

            watchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onArenaValorWatchClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorWatchClickListener.onArenaValorWatchClick(position);
                        }
                    }
                }
            });

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onArenaValorItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorItemClickListener.OnArenaValorItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
