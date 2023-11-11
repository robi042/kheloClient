package code.fortomorrow.kheloNowAdmin.Adapters.Ludo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_full_result_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludoi_booyah_adapter extends RecyclerView.Adapter<Ludoi_booyah_adapter.ViewHolder>{
    private List<Ludo_full_result_response.M> ludoTopResultList;

    public Ludoi_booyah_adapter(List<Ludo_full_result_response.M> ludoTopResultList) {
        this.ludoTopResultList = ludoTopResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_full_result_card, parent, false);
        return new Ludoi_booyah_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ludo_full_result_response.M response = ludoTopResultList.get(0);
        holder.rankText.setText(response.getRank());
        holder.playerNameText.setText(response.getPlayerName());
        holder.winnerText.setText(response.getWinningMoney());

        try {
            if(response.isrefunded){
                holder.refundText.setText(String.valueOf(response.getRefundAmount()));
            }
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankText, playerNameText, winnerText, refundText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rankText = itemView.findViewById(R.id.rankTextID);
            playerNameText = itemView.findViewById(R.id.playerNameTextID);
            winnerText = itemView.findViewById(R.id.winnerPrizeTextID);
            refundText = itemView.findViewById(R.id.refundTextID);
        }
    }
}
