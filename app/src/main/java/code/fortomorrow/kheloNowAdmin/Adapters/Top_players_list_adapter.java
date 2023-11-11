package code.fortomorrow.kheloNowAdmin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_full_result_adapter;
import code.fortomorrow.kheloNowAdmin.Model.TopPlayers.Top_players_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Top_players_list_adapter extends RecyclerView.Adapter<Top_players_list_adapter.ViewHolder> {
    private List<Top_players_list_response.M> topPlayerList;

    public Top_players_list_adapter(List<Top_players_list_response.M> topPlayerList) {
        this.topPlayerList = topPlayerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.top_players_list_card, parent, false);
        return new Top_players_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = position + 1;
        Top_players_list_response.M response = topPlayerList.get(position);

        holder.positionText.setText(String.valueOf(pos));
        holder.nameText.setText(response.getName());
        holder.matchPlayedText.setText(String.valueOf(response.getTotalMatchPlayed()));
        holder.matchWinText.setText(String.valueOf(response.getWin()));
    }

    @Override
    public int getItemCount() {
        return topPlayerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView positionText, nameText, matchPlayedText, matchWinText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            positionText = itemView.findViewById(R.id.positionTextID);
            nameText = itemView.findViewById(R.id.nameTextID);
            matchPlayedText = itemView.findViewById(R.id.matchPlayedTextID);
            matchWinText = itemView.findViewById(R.id.matchWinTextID);
        }
    }
}
