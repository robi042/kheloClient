package code.fortomorrow.kheloNowAdmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Transaction.M;
import code.fortomorrow.kheloNowAdmin.R;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.Viewholder> {
    private List<M> transactionsList;
    private Context context;

    public TransactionsAdapter(List<M> transactionsList, Context context) {
        this.transactionsList = transactionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_list, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.id.setText(String.valueOf("TRX: " + transactionsList.get(position).getPhomeNumber()));
        holder.date.setText(transactionsList.get(position).getRequestedTime());

        holder.status.setText(transactionsList.get(position).getStatus());
        holder.option.setText(transactionsList.get(position).getType());
        holder.amount.setText(String.valueOf(transactionsList.get(position).getAmount()));
        holder.methodText.setText(transactionsList.get(position).getPaymentMethod());
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView id, date, time, status, option, amount, methodText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            option = itemView.findViewById(R.id.option);
            amount = itemView.findViewById(R.id.amount);
            methodText = itemView.findViewById(R.id.methodTextID);

        }
    }
}
