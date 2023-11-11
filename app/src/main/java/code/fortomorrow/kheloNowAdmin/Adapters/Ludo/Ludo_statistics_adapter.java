package code.fortomorrow.kheloNowAdmin.Adapters.Ludo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_match_statistics_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_statistics_adapter extends RecyclerView.Adapter<Ludo_statistics_adapter.ViewHolder>{

    List<Ludo_match_statistics_response.M> ludoList;

    public Ludo_statistics_adapter(List<Ludo_match_statistics_response.M> ludoList) {
        this.ludoList = ludoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_statistics, parent, false);
        return new Ludo_statistics_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_match_statistics_response.M response = ludoList.get(position);
        holder.infoText.setText(response.getMatchTitle());
        holder.dateText.setText(response.getPalyedOn());
        holder.paidText.setText("৳"+String.valueOf(response.getPaid()));
        holder.winningsText.setText("৳"+String.valueOf(response.getWinning()));
        holder.refundText.setText("৳"+String.valueOf(response.getRefundAmount()));
        holder.idText.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return ludoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idText, infoText, dateText, paidText, winningsText, refundText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.id);
            infoText = itemView.findViewById(R.id.info);
            dateText = itemView.findViewById(R.id.date);
            paidText = itemView.findViewById(R.id.paid);
            winningsText = itemView.findViewById(R.id.winnings);
            refundText = itemView.findViewById(R.id.refundTextID);
        }
    }
}
