package code.fortomorrow.kheloNowAdmin.Adapters.FreeFireJoin;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_squad_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Squad_join_adapter extends RecyclerView.Adapter<Squad_join_adapter.MyViewHolder> {
    String totalPlayer;
    private int selectedPosition = -1;
    Boolean aState = false, bState = false, cState = false, dState = false;
    Free_fire_squad_match_join_activity free_fire_squad_match_join_activity;
    private List<Joined_player_list_response.M> joinedPlayerList;

    public Squad_join_adapter(String totalPlayer, Free_fire_squad_match_join_activity free_fire_squad_match_join_activity, List<Joined_player_list_response.M> joinedPlayerList) {
        this.totalPlayer = totalPlayer;
        this.free_fire_squad_match_join_activity = free_fire_squad_match_join_activity;
        this.joinedPlayerList = joinedPlayerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.free_fire_squad_join_match_card, parent, false);
        return new Squad_join_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = position + 1;
        holder.countText.setText(String.valueOf(pos));

        for (int i = 0; i < joinedPlayerList.size(); i++) {
            if (position == Integer.parseInt(joinedPlayerList.get(i).getTeamNo())) {

                try {
                    if (joinedPlayerList.get(i).getHasFirstPlayer()) {

                        holder.playerACheckBox.setChecked(true);
                        holder.playerACheckBox.setEnabled(false);
                        holder.playerACheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e1) {

                }

                try {
                    if (joinedPlayerList.get(i).getHasSecondPlayer()) {

                        holder.playerBCheckBox.setChecked(true);
                        holder.playerBCheckBox.setEnabled(false);
                        holder.playerBCheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e2) {

                }

                try {
                    if (joinedPlayerList.get(i).getHasThirdPlayer()) {

                        holder.playerCCheckBox.setChecked(true);
                        holder.playerCCheckBox.setEnabled(false);
                        holder.playerCCheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e3) {

                }

                try {
                    if (joinedPlayerList.get(i).getHasForthPlayer()) {

                        holder.playerDCheckBox.setChecked(true);
                        holder.playerDCheckBox.setEnabled(false);
                        holder.playerDCheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e4) {

                }
            }
        }

        holder.playerACheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    if (selectedPosition != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                    }

                    selectedPosition = holder.getAdapterPosition();
                    aState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {

                    aState = false;

                }
                free_fire_squad_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState, cState, dState);
            }
        });

        holder.playerBCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (selectedPosition != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                    }
                    selectedPosition = holder.getAdapterPosition();
                    bState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    bState = false;

                }
                free_fire_squad_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState, cState, dState);
            }
        });

        holder.playerCCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (selectedPosition != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                    }
                    selectedPosition = holder.getAdapterPosition();
                    cState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    cState = false;

                }
                free_fire_squad_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState, cState, dState);
            }
        });

        holder.playerDCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (selectedPosition != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                    }
                    selectedPosition = holder.getAdapterPosition();
                    dState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    dState = false;

                }
                free_fire_squad_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState, cState, dState);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(totalPlayer) / 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countText;
        CheckBox playerACheckBox, playerBCheckBox, playerCCheckBox, playerDCheckBox;
        LinearLayout mainlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            countText = itemView.findViewById(R.id.countTextID);
            playerACheckBox = itemView.findViewById(R.id.playerACheckBoxID);
            playerBCheckBox = itemView.findViewById(R.id.playerBCheckBoxID);
            playerCCheckBox = itemView.findViewById(R.id.playerCCheckBoxID);
            playerDCheckBox = itemView.findViewById(R.id.playerDCheckBoxID);
            mainlayout = itemView.findViewById(R.id.mainlayoutID);
        }
    }
}
