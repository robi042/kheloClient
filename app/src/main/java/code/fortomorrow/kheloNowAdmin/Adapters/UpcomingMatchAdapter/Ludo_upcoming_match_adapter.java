package code.fortomorrow.kheloNowAdmin.Adapters.UpcomingMatchAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches.Free_fire_upcoming_match_list_response;
import code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches.Ludo_upcoming_match_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_upcoming_match_adapter extends RecyclerView.Adapter<Ludo_upcoming_match_adapter.ViewHolder> {
    private List<Ludo_upcoming_match_list_response.M> ludoUpcomingMatchList;

    public Ludo_upcoming_match_adapter(List<Ludo_upcoming_match_list_response.M> ludoUpcomingMatchList) {
        this.ludoUpcomingMatchList = ludoUpcomingMatchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_upcoming_match_list_card, parent, false);
        return new Ludo_upcoming_match_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_upcoming_match_list_response.M response = ludoUpcomingMatchList.get(position);
        holder.dateText.setText(response.getMatchDate() + " " + response.getMatchTime());
        holder.titleText.setText(response.getTitle());

    }

    @Override
    public int getItemCount() {
        return ludoUpcomingMatchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, dateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleTextID);
            dateText = itemView.findViewById(R.id.dateTextID);
        }
    }
}
