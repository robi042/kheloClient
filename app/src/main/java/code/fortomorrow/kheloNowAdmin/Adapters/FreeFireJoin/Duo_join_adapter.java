package code.fortomorrow.kheloNowAdmin.Adapters.FreeFireJoin;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_duo_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Duo_join_adapter extends RecyclerView.Adapter<Duo_join_adapter.MyViewHolder> {

    String totalPlayer;
    Free_fire_duo_match_join_activity free_fire_duo_match_join_activity;
    private int selectedPosition = -1, a = -1, b = -1;
    Boolean aState = false, bState = false, cState = false, dState = false, eState = false, fState = false;
    private List<Joined_player_list_response.M> joinedPlayerList;

    public Duo_join_adapter(String totalPlayer, Free_fire_duo_match_join_activity free_fire_duo_match_join_activity, List<Joined_player_list_response.M> joinedPlayerList) {
        this.totalPlayer = totalPlayer;
        this.free_fire_duo_match_join_activity = free_fire_duo_match_join_activity;
        this.joinedPlayerList = joinedPlayerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.free_fire_duo_join_match_card, parent, false);
        return new Duo_join_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = position;
        holder.countText.setText(String.valueOf(pos+1));

        //holder.bindData( joinedPlayerList.get( position ) );

        //Joined_player_list_response.M response = joinedPlayerList.get(position);

        for (int i = 0; i < joinedPlayerList.size(); i++) {
            if (position == Integer.parseInt(joinedPlayerList.get(i).getTeamNo())) {

                try {
                    if (joinedPlayerList.get(i).getHasFirstPlayer()) {

                        holder.playerACheckBox.setChecked(true);
                        holder.playerACheckBox.setEnabled(false);
                        holder.playerACheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e1) {
                    holder.playerACheckBox.setChecked(false);
                    holder.playerACheckBox.setEnabled(true);
                }

                try {
                    if (joinedPlayerList.get(i).getHasSecondPlayer()) {

                        holder.playerBCheckBox.setChecked(true);
                        holder.playerBCheckBox.setEnabled(false);
                        holder.playerBCheckBox.setButtonTintList(ColorStateList.valueOf(Color.DKGRAY));
                    }
                } catch (Exception e2) {
                    holder.playerBCheckBox.setChecked(false);
                    holder.playerBCheckBox.setEnabled(true);
                }

            }
        }

        /*if (position == Integer.parseInt(joinedPlayerList.get(position).getTeamNo())) {

            try {
                if (joinedPlayerList.get(position).getHasFirstPlayer()) {

                    holder.playerACheckBox.setChecked(true);
                    holder.playerACheckBox.setEnabled(false);
                    holder.playerACheckBox.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
                }
            } catch (Exception e1) {

            }

            try {
                if (joinedPlayerList.get(position).getHasSecondPlayer()) {

                    holder.playerBCheckBox.setChecked(true);
                    holder.playerBCheckBox.setEnabled(false);
                    holder.playerBCheckBox.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
                }
            } catch (Exception e2) {

            }

        }*/


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
                        eState = false;
                        fState = false;

                        //holder.mainlayout.setBackgroundColor(Color.WHITE);
                    }

                    selectedPosition = holder.getAdapterPosition();

                    a = 1;
                    aState = true;
                    //holder.playerACheckBox.setChecked(true);
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));//Color(Color.LTGRAY);
                    free_fire_duo_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState);


                } else {
                    aState = false;
                    //a = 0;
                    free_fire_duo_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState);
                }
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
                        eState = false;
                        fState = false;

                        //holder.mainlayout.setBackgroundColor(Color.WHITE);
                    }

                    selectedPosition = holder.getAdapterPosition();
                    b = 1;
                    bState = true;
                    holder.mainlayout.setBackgroundTintList(ColorStateList.valueOf(R.color.black));
                    //holder.mainlayout.setBackgroundColor(Color.LTGRAY);
                    free_fire_duo_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState);


                } else {
                    bState = false;
                    free_fire_duo_match_join_activity.get_player_data(String.valueOf(selectedPosition), aState, bState);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(totalPlayer) / 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countText;
        CheckBox playerACheckBox, playerBCheckBox;
        LinearLayout mainlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            countText = itemView.findViewById(R.id.countTextID);
            playerACheckBox = itemView.findViewById(R.id.playerACheckBoxID);
            playerBCheckBox = itemView.findViewById(R.id.playerBCheckBoxID);
            mainlayout = itemView.findViewById(R.id.mainlayoutID);


        }

        public void bindData(final Joined_player_list_response.M item) {

            this.animationScaleIn(this.itemView);
        }

        private void animationScaleIn(View view) {
            ScaleAnimation animationScaleIn = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            if (getAdapterPosition() % 2f == 0) {
                animationScaleIn.setDuration(800);
            } else {
                animationScaleIn.setDuration(800 * 2);
            }

            view.startAnimation(animationScaleIn);
        }
    }


}
