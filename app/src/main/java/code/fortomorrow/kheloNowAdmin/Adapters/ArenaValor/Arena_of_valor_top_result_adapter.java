package code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_result_match_info;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_of_valor_top_result_adapter extends RecyclerView.Adapter<Arena_of_valor_top_result_adapter.ViewHolder> {
    private List<Arena_valor_result_match_info.M> infoList;

    public Arena_of_valor_top_result_adapter(List<Arena_valor_result_match_info.M> infoList) {
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public Arena_of_valor_top_result_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.arena_of_valor_result_details_card, parent, false);
        return new Arena_of_valor_top_result_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Arena_of_valor_top_result_adapter.ViewHolder holder, int position) {
        Arena_valor_result_match_info.M response = infoList.get(position);

        holder.mPlayerName.setText(response.userName);
        holder.mWinning.setText(response.winningMoney);
        holder.mRank.setText(response.rank);
        try {
            if (response.isrefunded) {
                holder.refundText.setText(String.valueOf(response.refundAmount));
            }
        } catch (Exception e) {
            holder.refundText.setText("0");
        }
    }

    @Override
    public int getItemCount() {
        if (infoList.size() > 3) {
            return 3;
        } else {
            return infoList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mRank, mPlayerName, mWinning, refundText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mRank = itemView.findViewById(R.id.mRank);
            mPlayerName = itemView.findViewById(R.id.mPlayerName);
            mWinning = itemView.findViewById(R.id.mWinning);
            refundText = itemView.findViewById(R.id.refundTextID);
        }
    }
}
