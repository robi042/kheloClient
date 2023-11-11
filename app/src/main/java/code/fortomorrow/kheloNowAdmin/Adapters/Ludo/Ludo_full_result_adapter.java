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

public class Ludo_full_result_adapter  extends RecyclerView.Adapter <Ludo_full_result_adapter.ViewHolder>{
    private List<Ludo_full_result_response.M> ludoFullResultList;

    public Ludo_full_result_adapter(List<Ludo_full_result_response.M> ludoFullResultList) {
        this.ludoFullResultList = ludoFullResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_full_result_card, parent, false);
        return new Ludo_full_result_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_full_result_response.M response = ludoFullResultList.get(position);
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
        return ludoFullResultList.size();
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
