package code.fortomorrow.kheloNowAdmin.Adapters.FreeFireJoin;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_solo_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_result_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.MatchAdapter;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Solo_join_adapter extends RecyclerView.Adapter<Solo_join_adapter.MyViewHolder> {
    String totalPlayer;
    private int selectedPosition = -1;
    Free_fire_solo_match_join_activity free_fire_solo_match_join_activity;
    Boolean aState = false;
    private List<Joined_player_list_response.M> joinedPlayerList;

    public Solo_join_adapter(String totalPlayer, Free_fire_solo_match_join_activity free_fire_solo_match_join_activity, List<Joined_player_list_response.M> joinedPlayerList) {
        this.totalPlayer = totalPlayer;
        this.free_fire_solo_match_join_activity = free_fire_solo_match_join_activity;
        this.joinedPlayerList = joinedPlayerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.free_fire_solo_join_match_card, parent, false);
        return new Solo_join_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = position + 1;
        holder.countText.setText(String.valueOf(pos));

        /*if (selectedPosition == position) {
            holder.playerCheckBox.setChecked(true);
        } else {
            holder.playerCheckBox.setChecked(false);
        }*/

        for (int i = 0; i < joinedPlayerList.size(); i++) {
            if (position == Integer.parseInt(joinedPlayerList.get(i).getTeamNo())) {

                try {
                    if (joinedPlayerList.get(i).getHasFirstPlayer()) {

                        holder.playerCheckBox.setChecked(true);
                        holder.playerCheckBox.setEnabled(false);
                        holder.playerCheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e) {

                }
            }
        }

        holder.playerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {


                    selectedPosition = holder.getAdapterPosition();

                    aState = true;
                    free_fire_solo_match_join_activity.get_player_position(String.valueOf(selectedPosition), aState);
                    //holder.playerCheckBox.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    //Toast.makeText(holder.itemView.getContext(), String.valueOf(selectedPosition), Toast.LENGTH_SHORT).show();
                    /*try {
                        notifyDataSetChanged();
                    } catch (Exception e) {

                    }*/
                } else {
                    aState = false;
                    free_fire_solo_match_join_activity.get_player_position("", aState);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(totalPlayer);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countText;
        CheckBox playerCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countText = itemView.findViewById(R.id.countTextID);
            playerCheckBox = itemView.findViewById(R.id.playerCheckBoxID);
        }
    }
}
