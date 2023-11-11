package code.fortomorrow.kheloNowAdmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.CheckJoinTeam.Check_join_team_response;
import code.fortomorrow.kheloNowAdmin.R;

public class JoinedPlayerAdapter extends RecyclerView.Adapter<JoinedPlayerAdapter.ViewHolder> {

    private List<Check_join_team_response.M> playerList;
    private Context context;

    public JoinedPlayerAdapter(List<Check_join_team_response.M> playerList, Context context) {
        this.playerList = playerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = position + 1;
        Check_join_team_response.M response = playerList.get(position);
        holder.teamNoText.setText("Team " + (Integer.parseInt(response.getTeamNo())+1));

        try {
            if (response.getHasFirstPlayer()) {
                holder.playerOneText.setVisibility(View.VISIBLE);
                holder.playerOneText.setText("Player One: "+response.getFirstPlayer());
            }
        } catch (Exception e1) {

        }

        try {
            if (response.getHasSecondPlayer()) {
                holder.playerTwoText.setVisibility(View.VISIBLE);
                holder.playerTwoText.setText("Player Two: "+response.getSecondPlayer());
            }
        } catch (Exception e1) {

        }
        try {
            if (response.getHasThirdPlayer()) {
                holder.playerThreeText.setVisibility(View.VISIBLE);
                holder.playerThreeText.setText("Player Three: "+response.getThirdPlayer());
            }
        } catch (Exception e1) {

        }
        try {
            if (response.getHasForthPlayer()) {
                holder.playerFourText.setVisibility(View.VISIBLE);
                holder.playerFourText.setText("Player Four: "+response.getForthPlayer());
            }
        } catch (Exception e1) {

        }
        try {
            if (response.getHasFifthPlayer()) {
                holder.playerFiveText.setVisibility(View.VISIBLE);
                holder.playerFiveText.setText("Player Five: "+response.getFifthPlayer());
            }
        } catch (Exception e1) {

        }
        try {
            if (response.getHasSixthPlayer()) {
                holder.playerSixText.setVisibility(View.VISIBLE);
                holder.playerSixText.setText("Player Six: "+response.getSixthPlayer());
            }
        } catch (Exception e1) {

        }

        try {
            if (response.getHasExtraOne()) {
                holder.extraOneText.setVisibility(View.VISIBLE);
                holder.extraOneText.setText("Extra 1: "+response.getExtra_player_one());
            }
        } catch (Exception e1) {

        }

        try {
            if (response.getHasExtraTwo()) {
                holder.extraTwoText.setVisibility(View.VISIBLE);
                holder.extraTwoText.setText("Extra 2: "+response.getExtra_player_two());
            }
        } catch (Exception e1) {

        }
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamNoText;
        TextView playerOneText, playerTwoText, playerThreeText, playerFourText, playerFiveText, playerSixText;
        TextView extraOneText, extraTwoText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamNoText = itemView.findViewById(R.id.teamNoTextID);

            playerOneText = itemView.findViewById(R.id.playerOneTextID);
            playerTwoText = itemView.findViewById(R.id.playerTwoTextID);
            playerThreeText = itemView.findViewById(R.id.playerThreeTextID);
            playerFourText = itemView.findViewById(R.id.playerFourTextID);
            playerFiveText = itemView.findViewById(R.id.playerFiveTextID);
            playerSixText = itemView.findViewById(R.id.playerSixTextID);

            extraOneText = itemView.findViewById(R.id.extraOneTextID);
            extraTwoText = itemView.findViewById(R.id.extraTwoTextID);
        }
    }
}
