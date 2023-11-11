package code.fortomorrow.kheloNowAdmin.Adapters.ArenaValor;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapters.MatchAdapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_of_valor_match_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_valor_match_adapter extends RecyclerView.Adapter<Arena_valor_match_adapter.ViewHolder> {
    private List<Arena_of_valor_match_response.M> arenaValorMatchList;
    Context context;

    public Arena_valor_match_adapter(List<Arena_of_valor_match_response.M> arenaValorMatchList) {
        this.arenaValorMatchList = arenaValorMatchList;
    }

    @NonNull
    @Override
    public Arena_valor_match_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.arena_valor_match_list_card, parent, false);
        return new Arena_valor_match_adapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Arena_valor_match_adapter.ViewHolder holder, int position) {
        Arena_of_valor_match_response.M response = arenaValorMatchList.get(position);

        holder.titleText.setText(response.title);
        holder.scheduleText.setText(response.matchDate + " " + response.matchTime);
        holder.totalPrizeText.setText(response.totalPrize);
        holder.entryFeeText.setText(response.entryFee);
        holder.gameTypeText.setText(response.gameType);
        holder.playerTypeText.setText(response.playerType);
        holder.versionText.setText(response.version);

        holder.spotLeftText.setText("Only " + String.valueOf(response.leftPlayer) + " spots left");
        holder.totalPlayerText.setText(String.valueOf(response.joinedPlayer + "/" + response.totalPlayer));

        int percentageProgress = (response.joinedPlayer * 100) / Integer.parseInt(response.totalPlayer);
        holder.progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        holder.progressBar.setProgress(percentageProgress);

        int booked = Integer.valueOf(response.joinedPlayer);
        int total = Integer.valueOf(response.totalPlayer);

        if (response.isJoined) {

            holder.joinButton.setEnabled(false);
            holder.joinButton.setText("JOINED");
            holder.joinButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(holder.itemView.getContext(), R.color.dark_green)))));
        } else if (booked == total || booked > total) {
            holder.joinButton.setEnabled(false);
            holder.joinButton.setText("CLOSED");
            holder.joinButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            //joinedOrNot = "1";
            holder.joinButton.setEnabled(true);
            holder.joinButton.setText("JOIN");
        }

        get_start_time(holder.timerText, response.matchDate, response.matchTime);
    }

    @Override
    public int getItemCount() {
        return arenaValorMatchList.size();
    }

    private OnArenaValorItemClickListener onArenaValorItemClickListener;
    private OnArenaValorPrizeClickListener onArenaValorPrizeClickListener;
    private OnArenaValorVideoClickListener onArenaValorVideoClickListener;
    private OnArenaValorJoinButtonClickListener onArenaValorJoinButtonClickListener;
    private OnArenaValorRoomDetailsClickListener onArenaValorRoomDetailsClickListener;

    public interface OnArenaValorRoomDetailsClickListener {
        void OnArenaValorRoomDetailsClick(int position);
    }

    public interface OnArenaValorItemClickListener {
        void OnArenaValorItemClick(int position);
    }

    public interface OnArenaValorPrizeClickListener {
        void OnArenaValorPrizeClick(int position);
    }

    public interface OnArenaValorVideoClickListener {
        void onArenaValorVideoClick(int position);
    }

    public interface OnArenaValorJoinButtonClickListener {
        void onArenaValorJoinClick(int position);
    }

    public void setOnClickListener(Arena_valor_match_adapter.OnArenaValorPrizeClickListener onArenaValorPrizeClickListener, Arena_valor_match_adapter.OnArenaValorVideoClickListener onArenaValorVideoClickListener, Arena_valor_match_adapter.OnArenaValorJoinButtonClickListener onArenaValorJoinButtonClickListener, Arena_valor_match_adapter.OnArenaValorItemClickListener onArenaValorItemClickListener, Arena_valor_match_adapter.OnArenaValorRoomDetailsClickListener onArenaValorRoomDetailsClickListener) {
        this.onArenaValorPrizeClickListener = onArenaValorPrizeClickListener;
        this.onArenaValorVideoClickListener = onArenaValorVideoClickListener;
        this.onArenaValorJoinButtonClickListener = onArenaValorJoinButtonClickListener;
        this.onArenaValorItemClickListener = onArenaValorItemClickListener;
        this.onArenaValorRoomDetailsClickListener = onArenaValorRoomDetailsClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, scheduleText, totalPrizeText, entryFeeText;
        TextView gameTypeText, versionText, playerTypeText, spotLeftText;
        TextView totalPlayerText, timerText;
        AppCompatButton joinButton;
        ProgressBar progressBar;
        LinearLayout totalPrizeLayoutButton, videoButton;
        LinearLayout itemViewButton, roomDetailsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleTextID);
            scheduleText = itemView.findViewById(R.id.scheduleTextID);
            totalPrizeText = itemView.findViewById(R.id.totalPrizeTextID);
            entryFeeText = itemView.findViewById(R.id.entryFeeTextID);
            gameTypeText = itemView.findViewById(R.id.gameTypeTextID);
            versionText = itemView.findViewById(R.id.versionTextID);
            playerTypeText = itemView.findViewById(R.id.playerTypeTextID);
            spotLeftText = itemView.findViewById(R.id.spotLeftTextID);
            totalPlayerText = itemView.findViewById(R.id.totalPlayerTextID);
            timerText = itemView.findViewById(R.id.timerTextID);
            progressBar = itemView.findViewById(R.id.progressBarID);
            joinButton = itemView.findViewById(R.id.joinButtonID);
            totalPrizeLayoutButton = itemView.findViewById(R.id.totalPrizeLayoutButtonID);
            videoButton = itemView.findViewById(R.id.videoButtonID);
            itemViewButton = itemView.findViewById(R.id.itemViewButtonID);
            roomDetailsButton = itemView.findViewById(R.id.roomDetailsButtonID);

            roomDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onArenaValorRoomDetailsClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorRoomDetailsClickListener.OnArenaValorRoomDetailsClick(position);
                        }
                    }
                }
            });

            totalPrizeLayoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onArenaValorPrizeClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorPrizeClickListener.OnArenaValorPrizeClick(position);
                        }
                    }
                }
            });

            videoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onArenaValorVideoClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorVideoClickListener.onArenaValorVideoClick(position);
                        }
                    }
                }
            });

            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (joinButton.getText().toString().trim().equals("JOIN")) {
                        if (onArenaValorJoinButtonClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                onArenaValorJoinButtonClickListener.onArenaValorJoinClick(position);
                            }
                        }
                    } else {

                    }
                }
            });


            itemViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onArenaValorItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorItemClickListener.OnArenaValorItemClick(position);
                        }
                    }
                }
            });

        }
    }

    private void get_start_time(TextView timerText, String matchDate, String matchTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm a");

        String currentDate = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

        try {
            Date date1 = simpleDateFormat.parse(currentDate + " " + currentTime);
            Date date2 = simpleDateFormat.parse(matchDate + " " + matchTime);

            long different = date2.getTime() - date1.getTime();


            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            //long elapsedSeconds = different / secondsInMilli;
            String time;
            if (elapsedDays <= 0 && elapsedHours <= 0 && elapsedMinutes <= 0) {
                time = "GAME STARTS";
                timerText.setText(time);
            } else {
                if (elapsedDays <= 0 && elapsedHours <= 0) {
                    time = String.valueOf(elapsedMinutes) + " minutes";
                    timerText.setText(time);
                } else if (elapsedDays <= 0 && elapsedMinutes <= 0) {
                    time = String.valueOf(elapsedHours) + " hours";
                    timerText.setText(time);
                } else if (elapsedHours <= 0 && elapsedMinutes <= 0) {
                    time = String.valueOf(elapsedDays) + " days";
                    timerText.setText(time);
                } else if (elapsedDays <= 0) {
                    time = String.valueOf(elapsedHours) + " hours " + String.valueOf(elapsedMinutes) + " minutes";
                    timerText.setText(time);
                } else if (elapsedHours <= 0) {
                    time = String.valueOf(elapsedDays) + " days " + String.valueOf(elapsedMinutes) + " minutes";
                    timerText.setText(time);
                } else if (elapsedMinutes <= 0) {
                    time = String.valueOf(elapsedDays) + " days " + String.valueOf(elapsedHours) + " hours";
                    timerText.setText(time);
                } else {
                    time = String.valueOf(elapsedDays) + " days " + String.valueOf(elapsedHours) + " hours " + String.valueOf(elapsedMinutes) + " minutes";
                    timerText.setText(time);
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
