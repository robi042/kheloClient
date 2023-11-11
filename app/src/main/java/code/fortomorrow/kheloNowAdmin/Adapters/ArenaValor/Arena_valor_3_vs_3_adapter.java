package code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor;

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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.Arena_of_valor_3_vs_3_slot_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Arena_of_valor_5_vs_5_slot_activity;
import code.fortomorrow.kheloNowAdmin.Model.CheckJoinTeam.Check_join_team_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_valor_3_vs_3_adapter extends RecyclerView.Adapter<Arena_valor_3_vs_3_adapter.MyViewHolder> {
    String totalPlayer;
    Arena_of_valor_3_vs_3_slot_activity activity;
    private List<Check_join_team_response.M> joinedPlayerList;
    Boolean aState = false, bState = false, cState = false, dState = false, eState = false;
    int selected_position = -1;

    public Arena_valor_3_vs_3_adapter(String totalPlayer, Arena_of_valor_3_vs_3_slot_activity arena_of_valor_3_vs_3_slot_activity, List<Check_join_team_response.M> joinedPlayerList) {
        this.totalPlayer = totalPlayer;
        this.activity = arena_of_valor_3_vs_3_slot_activity;
        this.joinedPlayerList = joinedPlayerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.arena_of_valor_3_vs_3_card, parent, false);
        return new Arena_valor_3_vs_3_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.countText.setText(String.valueOf(position + 1));

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
                    }
                    selected_position = holder.getAdapterPosition();
                    aState = true;
                    holder.mainLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(holder.itemView.getContext(), R.color.black)))));
                } else {
                    aState = false;
                }
                activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState);
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
                    }

                    selected_position = holder.getAdapterPosition();
                    bState = true;
                    holder.mainLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(holder.itemView.getContext(), R.color.black)))));
                } else {
                    bState = false;

                }
                activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState);
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
                    }

                    selected_position = holder.getAdapterPosition();
                    cState = true;
                    holder.mainLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(holder.itemView.getContext(), R.color.black)))));
                } else {
                    cState = false;
                }
                activity.get_player_data(String.valueOf(selected_position), aState, bState, cState, dState, eState);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(totalPlayer) / 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countText;
        CheckBox playerACheckBox, playerBCheckBox, playerCCheckBox;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            countText = itemView.findViewById(R.id.countTextID);
            playerACheckBox = itemView.findViewById(R.id.playerACheckBoxID);
            playerBCheckBox = itemView.findViewById(R.id.playerBCheckBoxID);
            playerCCheckBox = itemView.findViewById(R.id.playerCCheckBoxID);
            mainLayout = itemView.findViewById(R.id.mainLayoutID);
        }
    }
}
