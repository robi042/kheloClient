package code.fortomorrow.kheloNowAdmin.Adapters.Ludo;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_paginated_match_list;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_tournament_game_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Timer.CustomRunnable;
import code.fortomorrow.kheloNowAdmin.Timer.TimerShow;
import es.dmoral.toasty.Toasty;

public class Ludo_game_list_adapter extends RecyclerView.Adapter<Ludo_game_list_adapter.ViewHolder> {

    //private List<Ludo_tournament_game_list_response.M> ludoGameList;
    private Handler handler = new Handler();
    CustomRunnable customRunnable;
    private List<Ludo_paginated_match_list.M> ludoGameList;

    public Ludo_game_list_adapter(List<Ludo_paginated_match_list.M> ludoGameList) {
        this.ludoGameList = ludoGameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_game_card, parent, false);
        return new Ludo_game_list_adapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = position + 1;

        //Ludo_tournament_game_list_response.M response = ludoGameList.get(position);

        Ludo_paginated_match_list.M response = ludoGameList.get(position);
        holder.gameTitleText.setText(response.getTitle());
        holder.dateTimeText.setText(response.getMatchDate() + " " + response.getMatchTime());
        holder.prizeText.setText(response.getTotalPrize());
        holder.entryFeeText.setText(response.getEntryFee());
        holder.boardTypeText.setText(response.getHostApp());
        holder.totalPlayerText.setText(String.valueOf(response.getJoinedPlayer() + "/" + response.getTotalPlayer()));
        holder.spotLeftText.setText("Only " + String.valueOf(response.getLeftPlayer()) + " spots left");

        holder.winnerPrizeText.setText(response.getTotalPrize() + " " + "Tk");

        int percentageProgress = (response.getJoinedPlayer() * 100) / Integer.parseInt(response.getTotalPlayer());
        holder.progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);


        holder.progressBar.setProgress(percentageProgress);

        //Log.d("errorxxx", String.valueOf(response.getMatchFull())+" "+String.valueOf(response.getJoined()));

        try {
            if (response.getMatchFull()) {

                try {
                    if (response.getJoined()) {

                        holder.readyButton.setVisibility(View.VISIBLE);

                        try {
                            if (response.getHasRoomcode()) {
                                holder.joinButton.setText("PLAY");
                                holder.joinButton.setTextColor(Color.YELLOW);

                                holder.roomCodeLayout.setVisibility(View.VISIBLE);
                                holder.roomCodeText.setText(response.getRoomCode());
                            }
                        } catch (Exception e) {
                            holder.joinButton.setText("JOINED");
                            holder.joinButton.setEnabled(false);
                            holder.joinButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(holder.itemView.getContext(), R.color.dark_green)))));
                        }
                    }
                } catch (Exception er) {
                    holder.joinButton.setText("CLOSED");
                    holder.joinButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    holder.joinButton.setEnabled(false);
                }
            }
        } catch (Exception e) {
            //Log.d("errorxxx", "hix");
            try {
                if (response.getJoined()) {
                    holder.readyButton.setVisibility(View.VISIBLE);
                    try {
                        if (response.getHasRoomcode()) {
                            holder.joinButton.setText("PLAY");
                            holder.roomCodeLayout.setVisibility(View.VISIBLE);
                            holder.roomCodeText.setText(response.getRoomCode());
                        }
                    } catch (Exception ef) {
                        holder.joinButton.setText("JOINED");
                        holder.joinButton.setEnabled(false);
                        holder.joinButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(holder.itemView.getContext(), R.color.dark_green)))));
                    }
                }
            } catch (Exception er) {


                if (response.getLeftPlayer() == 0) {
                    holder.joinButton.setText("CLOSED");
                    holder.joinButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    holder.joinButton.setEnabled(false);
                } else {
                    holder.joinButton.setText("JOIN");
                    holder.joinButton.setEnabled(true);
                }
            }
        }

        try {
            if (response.getReady()) {
                holder.readyButton.setText("Already ready");
            }
        } catch (Exception e) {
            holder.readyButton.setText("Ready");
        }

        get_start_time(holder.timerText, response.getMatchDate(), response.getMatchTime());

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

    @Override
    public int getItemCount() {
        return ludoGameList.size();
    }

    private OnItemJoinClickListener onJoinClickListener;
    private OnItemClickListener onClickListener;
    private OnClipClickListener onClipClickListener;
    private OnHowToJoinClickListener onHowToJoinClickListener;
    private OnReadyClickListener onReadyClickListener;
    private OnPrizeClickListener onPrizeClickListener;
    private OnRoomDetailsClickListener onRoomDetailsClickListener;

    public interface OnRoomDetailsClickListener {
        void OnRoomDetailsClick(int position);
    }

    public interface OnReadyClickListener {
        void OnReadyClick(int position);
    }

    public interface OnHowToJoinClickListener {
        void OnHowToJoinClick(int position);
    }

    public interface OnItemJoinClickListener {
        void OnItemJoinClick(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnClipClickListener {
        void OnClipItemClick(int position);
    }


    public interface OnPrizeClickListener {
        void OnPrizeClick(int position);
    }


    public void setOnItemClickListener(Ludo_game_list_adapter.OnItemJoinClickListener onJoinClickListener, Ludo_game_list_adapter.OnItemClickListener onItemClickListener, Ludo_game_list_adapter.OnClipClickListener onClipClickListener, Ludo_game_list_adapter.OnHowToJoinClickListener onHowToJoinClickListener, Ludo_game_list_adapter.OnPrizeClickListener onPrizeClickListener, Ludo_game_list_adapter.OnReadyClickListener onReadyClickListener, Ludo_game_list_adapter.OnRoomDetailsClickListener onRoomDetailsClickListener) {
        this.onJoinClickListener = onJoinClickListener;
        this.onClickListener = onItemClickListener;
        this.onClipClickListener = onClipClickListener;
        this.onHowToJoinClickListener = onHowToJoinClickListener;
        this.onPrizeClickListener = onPrizeClickListener;
        this.onReadyClickListener = onReadyClickListener;
        this.onRoomDetailsClickListener = onRoomDetailsClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, entryFeeText, boardTypeText;
        TextView totalPlayerText, spotLeftText, winnerPrizeText, roomCodeText, timerText;
        ProgressBar progressBar;
        LinearLayout prizeDetailsButton, winnerLayout, roomCodeLayout;
        AppCompatButton joinButton;
        Boolean state = true;
        ImageView arrowDownImage, arrowUpImage;
        LinearLayout clipBoardButton, howToJoinButton, roomDetailsButton;
        AppCompatButton readyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            entryFeeText = itemView.findViewById(R.id.entryFeeTextID);
            boardTypeText = itemView.findViewById(R.id.boardTypeID);

            totalPlayerText = itemView.findViewById(R.id.totalPlayerTextID);
            spotLeftText = itemView.findViewById(R.id.spotLeftTextID);
            progressBar = itemView.findViewById(R.id.progressBarID);
            joinButton = itemView.findViewById(R.id.joinButtonID);
            winnerPrizeText = itemView.findViewById(R.id.winnerPrizeTextID);
            roomCodeText = itemView.findViewById(R.id.roomCodeTextID);
            roomDetailsButton = itemView.findViewById(R.id.roomDetailsButtonID);

            arrowDownImage = itemView.findViewById(R.id.arrowDownImageID);
            arrowUpImage = itemView.findViewById(R.id.arrowUpImageID);

            roomCodeLayout = itemView.findViewById(R.id.roomCodeLayoutID);
            winnerLayout = itemView.findViewById(R.id.winnerLayoutID);
            prizeDetailsButton = itemView.findViewById(R.id.prizeDetailsButtonID);
            clipBoardButton = itemView.findViewById(R.id.clipBoardButtonID);
            howToJoinButton = itemView.findViewById(R.id.howToJoinButtonID);
            timerText = itemView.findViewById(R.id.timerTextID);
            readyButton = itemView.findViewById(R.id.readyButtonID);

            roomDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRoomDetailsClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onRoomDetailsClickListener.OnRoomDetailsClick(position);
                        }
                    }
                }
            });

            readyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (readyButton.getText().toString().equals("Ready")) {
                        if (onReadyClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                onReadyClickListener.OnReadyClick(position);
                            }
                        }
                    } else {
                        Toasty.error(itemView.getContext(), "already ready", Toasty.LENGTH_SHORT).show();
                    }


                }
            });


            howToJoinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onHowToJoinClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onHowToJoinClickListener.OnHowToJoinClick(position);
                        }
                    }
                }
            });

            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (joinButton.getText().toString().trim().equals("JOIN") || joinButton.getText().toString().trim().equals("PLAY")) {
                        if (onJoinClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                onJoinClickListener.OnItemJoinClick(position);
                            }
                        }
                    } else if (joinButton.getText().toString().trim().equals("JOINED")) {
                        Toasty.error(itemView.getContext(), "Already Joined", Toasty.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(itemView.getContext(), "Match unavailable", Toasty.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.OnItemClick(position);
                        }
                    }
                }
            });

            clipBoardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClipClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClipClickListener.OnClipItemClick(position);
                        }
                    }
                }
            });

            prizeDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPrizeClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onPrizeClickListener.OnPrizeClick(position);
                        }
                    }
                }
            });
        }
    }

    private class AsyncTaskExample extends AsyncTask<TimerShow, Void, TimerShow> {


        @Override
        protected TimerShow doInBackground(TimerShow... bounds) {
            try {
                return bounds[0];
            } catch (Exception f) {

            }
            return bounds[0];
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected void onPostExecute(TimerShow t) {
            super.onPostExecute(t);
            CountDownTimer countDownTimer = new CountDownTimer(t.x * 60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    t.t.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
                }

                public void onFinish() {
                    t.t.setText("Times up");
                }
            }.start();

        }

        public void coundown(TextView t, int position) {
            CountDownTimer countDownTimer = new CountDownTimer(position * 60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    t.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
                }

                public void onFinish() {
                    t.setText("done!");
                }
            }.start();
        }
    }
}
