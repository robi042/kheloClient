package code.fortomorrow.kheloNowAdmin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_result_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Support.Support_numbers_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Support_numbers_adapter extends RecyclerView.Adapter<Support_numbers_adapter.ViewHolder> {
    List<Support_numbers_response.M> numbersList;

    public Support_numbers_adapter(List<Support_numbers_response.M> numbersList) {
        this.numbersList = numbersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_numbers_card, parent, false);
        return new Support_numbers_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Support_numbers_response.M response = numbersList.get(position);

        if (response.getType().equals("regular_cs")) {
            holder.typeText.setText("Free fire Regular + Clash Squad");
        }

        if (response.getType().equals("ludo")) {
            holder.typeText.setText("Regular Ludo + Grand Ludo");
        }

        if (response.getType().equals("add_withdraw")) {
            holder.typeText.setText("Add Money + Withdraw");
        }

    }

    @Override
    public int getItemCount() {
        return numbersList.size();
    }

    private OnItemClickListener onClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }


    public void setOnItemClickListener(Support_numbers_adapter.OnItemClickListener onItemClickListener) {

        this.onClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeText;
        LinearLayout supportButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.typeTextID);
            supportButton = itemView.findViewById(R.id.supportButtonID);

            supportButton.setOnClickListener(new View.OnClickListener() {
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
