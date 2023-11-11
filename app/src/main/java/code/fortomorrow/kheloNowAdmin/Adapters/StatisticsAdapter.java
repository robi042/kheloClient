package code.fortomorrow.kheloNowAdmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Statistics.M;
import code.fortomorrow.kheloNowAdmin.R;


public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {


    private FragmentManager context;
    private List<M> list;

    public StatisticsAdapter(FragmentManager context, List<M> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistics, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.id.setText(String.valueOf(position+1));
        viewHolder.date.setText(list.get(position).getPalyedOn());
        viewHolder.info.setText(list.get(position).getMatchTitle());
        viewHolder.paid.setText("৳"+list.get(position).getPaid());
        viewHolder.winnings.setText("৳"+list.get(position).getWinning());
        viewHolder.refundText.setText("৳"+list.get(position).getRefund_amount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView id,info,date,paid,winnings, refundText;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            id = (TextView)view.findViewById(R.id.id);
            info = (TextView)view.findViewById(R.id.info);
            date = (TextView)view.findViewById(R.id.date);
            paid = (TextView)view.findViewById(R.id.paid);
            winnings = (TextView)view.findViewById(R.id.winnings);
            refundText = view.findViewById(R.id.refundTextID);

        }


    }




}