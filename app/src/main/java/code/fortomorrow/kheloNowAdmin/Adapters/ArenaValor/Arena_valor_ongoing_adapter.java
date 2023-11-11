package code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_of_valor_match_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_valor_ongoing_adapter extends RecyclerView.Adapter<Arena_valor_ongoing_adapter.ViewHolder> {
    private List<Arena_of_valor_match_response.M> arenaValorOngoingList;

    public Arena_valor_ongoing_adapter(List<Arena_of_valor_match_response.M> arenaValorOngoingList) {
        this.arenaValorOngoingList = arenaValorOngoingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.arena_valor_ongoing_list_card, parent, false);
        return new Arena_valor_ongoing_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Arena_of_valor_match_response.M response = arenaValorOngoingList.get(position);

        holder.titleText.setText(response.title);
        holder.scheduleText.setText(response.matchDate + " " + response.matchTime);
        holder.totalPrizeText.setText(response.totalPrize);
        holder.entryFeeText.setText(response.entryFee);
        holder.gameTypeText.setText(response.gameType);
        holder.playerTypeText.setText(response.playerType);
        holder.versionText.setText(response.version);
    }

    @Override
    public int getItemCount() {
        return arenaValorOngoingList.size();
    }

    private OnArenaValorPrizeClickListener onArenaValorPrizeClickListener;
    private OnArenaValorSpectateClickListener onArenaValorSpectateClickListener;


    public interface OnArenaValorPrizeClickListener {
        void OnArenaValorPrizeClick(int position);
    }

    public interface OnArenaValorSpectateClickListener {
        void onArenaValorSpectateClick(int position);
    }

    public void setOnClickListener(Arena_valor_ongoing_adapter.OnArenaValorPrizeClickListener onArenaValorPrizeClickListener, Arena_valor_ongoing_adapter.OnArenaValorSpectateClickListener onArenaValorSpectateClickListener) {
        this.onArenaValorPrizeClickListener = onArenaValorPrizeClickListener;
        this.onArenaValorSpectateClickListener = onArenaValorSpectateClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, scheduleText, totalPrizeText, entryFeeText;
        TextView gameTypeText, versionText, playerTypeText, spotLeftText;
        TextView totalPlayerText, timerText;
        AppCompatButton joinButton;
        ProgressBar progressBar;
        LinearLayout totalPrizeLayoutButton, spectateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleTextID);
            scheduleText = itemView.findViewById(R.id.scheduleTextID);
            totalPrizeText = itemView.findViewById(R.id.totalPrizeTextID);
            entryFeeText = itemView.findViewById(R.id.entryFeeTextID);
            gameTypeText = itemView.findViewById(R.id.gameTypeTextID);
            versionText = itemView.findViewById(R.id.versionTextID);
            playerTypeText = itemView.findViewById(R.id.playerTypeTextID);

            totalPrizeLayoutButton = itemView.findViewById(R.id.totalPrizeLayoutButtonID);
            spectateButton = itemView.findViewById(R.id.spectateButtonID);

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

            spectateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
