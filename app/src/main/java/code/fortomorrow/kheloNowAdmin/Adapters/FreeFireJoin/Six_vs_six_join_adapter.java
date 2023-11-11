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

import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_six_vs_six_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Six_vs_six_join_adapter extends RecyclerView.Adapter<Six_vs_six_join_adapter.MyViewHolder> {
    String totalPlayer;
    Free_fire_six_vs_six_match_join_activity free_fire_six_vs_six_match_join_activity;
    Boolean aState = false, bState = false, cState = false, dState = false, eState = false, fState = false;
    int selected_position = -1;
    private List<Joined_player_list_response.M> joinedPlayerList;

    public Six_vs_six_join_adapter(String totalPlayer, Free_fire_six_vs_six_match_join_activity free_fire_six_vs_six_match_join_activity, List<Joined_player_list_response.M> joinedPlayerList) {
        this.totalPlayer = totalPlayer;
        this.free_fire_six_vs_six_match_join_activity = free_fire_six_vs_six_match_join_activity;
        this.joinedPlayerList = joinedPlayerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.free_fire_six_vs_six_join_match_card, parent, false);
        return new Six_vs_six_join_adapter.MyViewHolder(view);
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

                try {
                    if (joinedPlayerList.get(i).getHasFifthPlayer()) {

                        holder.playerECheckBox.setChecked(true);
                        holder.playerECheckBox.setEnabled(false);
                        holder.playerECheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e5) {

                }

                try {
                    if (joinedPlayerList.get(i).getHasSixthPlayer()) {

                        holder.playerFCheckBox.setChecked(true);
                        holder.playerFCheckBox.setEnabled(false);
                        holder.playerFCheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e6) {

                }
            }
        }

        holder.playerACheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    if (selected_position != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                        eState = false;
                        fState = false;
                    }

                    selected_position = holder.getAdapterPosition();
                    aState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    aState = false;

                }
                free_fire_six_vs_six_match_join_activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState, fState);
            }
        });

        holder.playerBCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    if (selected_position != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                        eState = false;
                        fState = false;
                    }

                    selected_position = holder.getAdapterPosition();
                    bState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    bState = false;

                }
                free_fire_six_vs_six_match_join_activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState, fState);
            }
        });

        holder.playerCCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    if (selected_position != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                        eState = false;
                        fState = false;
                    }

                    selected_position = holder.getAdapterPosition();
                    cState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    cState = false;
                }
                free_fire_six_vs_six_match_join_activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState, fState);
            }
        });

        holder.playerDCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    if (selected_position != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                        eState = false;
                        fState = false;
                    }

                    selected_position = holder.getAdapterPosition();
                    dState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    dState = false;

                }
                free_fire_six_vs_six_match_join_activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState, fState);
            }
        });

        holder.playerECheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (selected_position != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                        eState = false;
                        fState = false;
                    }

                    selected_position = holder.getAdapterPosition();
                    eState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    eState = false;

                }
                free_fire_six_vs_six_match_join_activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState, fState);
            }
        });

        holder.playerFCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (selected_position != holder.getAdapterPosition()) {
                        aState = false;
                        bState = false;
                        cState = false;
                        dState = false;
                        eState = false;
                        fState = false;
                    }
                    selected_position = holder.getAdapterPosition();
                    fState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));

                } else {
                    fState = false;

                }
                free_fire_six_vs_six_match_join_activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState, fState);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(totalPlayer) / 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countText;
        CheckBox playerACheckBox, playerBCheckBox, playerCCheckBox, playerDCheckBox, playerECheckBox, playerFCheckBox;
        LinearLayout mainlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            countText = itemView.findViewById(R.id.countTextID);
            playerACheckBox = itemView.findViewById(R.id.playerACheckBoxID);
            playerBCheckBox = itemView.findViewById(R.id.playerBCheckBoxID);
            playerCCheckBox = itemView.findViewById(R.id.playerCCheckBoxID);
            playerDCheckBox = itemView.findViewById(R.id.playerDCheckBoxID);
            playerECheckBox = itemView.findViewById(R.id.playerECheckBoxID);
            playerFCheckBox = itemView.findViewById(R.id.playerFCheckBoxID);

            mainlayout = itemView.findViewById(R.id.mainlayoutID);
        }
    }
}
