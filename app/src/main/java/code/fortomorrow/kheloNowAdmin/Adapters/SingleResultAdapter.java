package code.fortomorrow.kheloNowAdmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.ResultResponse.M;
import code.fortomorrow.kheloNowAdmin.R;

public class SingleResultAdapter extends RecyclerView.Adapter<SingleResultAdapter.Viewholder> {
    private Context context;
    private List<M> ms;

    public SingleResultAdapter( List<M> ms) {
        this.ms = ms;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(context).inflate(R.layout.result_player_list_layer, parent, false);
        return new Viewholder(view);*/

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_result_player_list_layer, parent, false);
        return new SingleResultAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        M response = ms.get(position);
        holder.mRank.setText(response.getRank());
        holder.mPlayerName.setText(response.getUserName());
        holder.mKill.setText(String.valueOf(response.getKill()));
        holder.mWinning.setText(String.valueOf(response.getWinningMoney()));
        try {
            if (response.getIsrefunded()) {
                holder.refundText.setText(String.valueOf(response.getRefundAmount()));
            }else {
                holder.refundText.setText("0");
            }
        }catch (Exception e){
            holder.refundText.setText("0");
        }

    }

    @Override
    public int getItemCount() {
        if (ms.size() > 3) {
            return 3;
        } else {
            return ms.size();
        }
        //return 3;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView mRank, mPlayerName, mKill, mWinning, refundText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mRank = itemView.findViewById(R.id.mRank);
            mPlayerName = itemView.findViewById(R.id.mPlayerName);
            mKill = itemView.findViewById(R.id.mKill);
            mWinning = itemView.findViewById(R.id.mWinning);
            refundText = itemView.findViewById(R.id.refundTextID);

        }
    }
}
