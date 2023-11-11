package code.fortomorrow.kheloNowAdmin.Adapters.UpcomingMatchAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.UpcomingMatches.Free_fire_upcoming_match_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Free_fire_upcoming_match_list_adapter extends RecyclerView.Adapter<Free_fire_upcoming_match_list_adapter.ViewHolder> {
    private List<Free_fire_upcoming_match_list_response.M> upcomingList;

    public Free_fire_upcoming_match_list_adapter(List<Free_fire_upcoming_match_list_response.M> upcomingList) {
        this.upcomingList = upcomingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.free_fire_upcoming_match_list_card, parent, false);
        return new Free_fire_upcoming_match_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Free_fire_upcoming_match_list_response.M response = upcomingList.get(position);
        holder.titleText.setText(response.getTitle());
        holder.dateText.setText(response.getMatchTime());
    }

    @Override
    public int getItemCount() {
        return upcomingList.size();
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
