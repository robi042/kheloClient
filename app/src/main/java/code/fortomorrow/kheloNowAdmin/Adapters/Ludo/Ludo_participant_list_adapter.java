package code.fortomorrow.kheloNowAdmin.Adapters.Ludo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.JoinedPlayerAdapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.LudoMatchParticipantList_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_participant_list_adapter extends RecyclerView.Adapter<Ludo_participant_list_adapter.ViewHolder> {
    private List<LudoMatchParticipantList_response.M> participantList;

    public Ludo_participant_list_adapter(List<LudoMatchParticipantList_response.M> participantList) {
        this.participantList = participantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_participant_list_card, parent, false);
        return new Ludo_participant_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LudoMatchParticipantList_response.M response = participantList.get(position);
        int pos = position + 1;

        holder.nameText.setText("" + pos + ". " + "Player " + pos + " has joined.");
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.nameTextID);
        }
    }
}
