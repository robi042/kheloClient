package code.fortomorrow.kheloNowAdmin.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.DailyMatchActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Daily_scrims_match_joining_activity;
import code.fortomorrow.kheloNowAdmin.Activities.ForgotPassword;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_1_vs_1_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_duo_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_six_vs_six_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_solo_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_squad_match_join_activity;
import code.fortomorrow.kheloNowAdmin.Activities.JoinMatchActivity;
import code.fortomorrow.kheloNowAdmin.Activities.RulesActivity;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_live;
import code.fortomorrow.kheloNowAdmin.Model.GetMatch.FreeFire_paginated_live_response;
import code.fortomorrow.kheloNowAdmin.Model.GetMatch.M;
import code.fortomorrow.kheloNowAdmin.Model.getRoomIdPass.GetRoomIdResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.Retrofit.APIService;
import code.fortomorrow.kheloNowAdmin.Retrofit.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {

    private String joinedOrNot = "";
    Fragment_live fragment_live;
    Context context;
    private APIService apiService;
    private List<FreeFire_paginated_live_response.M> m;
    private String secret_id, jwt_token;

    public MatchAdapter(Fragment_live fragment_live, Context context, List<FreeFire_paginated_live_response.M> m) {
        this.fragment_live = fragment_live;
        this.context = context;
        this.m = m;
        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(context);
        jwt_token = EasySharedPref.read("jwt_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView mImage;
        TextView mTitle, mTime, mTotalPrize, mPerKill, mEntryFee, mType, mVersion, mMap, mSpotLeft, mTotalPlayer, timerText;
        AppCompatButton mJoin;
        ProgressBar progressBar;
        LinearLayout mPrizeDetails, mWinner;
        TextView mTrue;

        ImageView mDrop1, mDrop2;

        TextView mOne, mTwo, mThree;

        LinearLayout mLayer, mRoomIdPassShow, totalPrizeLayoutButton, videoButton, roomDetailsButton;

        public MyViewHolder(View view) {
            super(view);
            mRoomIdPassShow = view.findViewById(R.id.mRoomIdPassShow);
            mTitle = view.findViewById(R.id.mTitle);
            mTime = view.findViewById(R.id.mTime);
            mTotalPrize = view.findViewById(R.id.mTotalPrize);
            mPerKill = view.findViewById(R.id.mPerKill);
            mEntryFee = view.findViewById(R.id.mEntryFee);
            mType = view.findViewById(R.id.mType);
            mVersion = view.findViewById(R.id.mVersion);
            mMap = view.findViewById(R.id.mMap);
            mSpotLeft = view.findViewById(R.id.mSpotLeft);
            mTotalPlayer = view.findViewById(R.id.mTotalPlayer);
            mJoin = view.findViewById(R.id.mJoin);
            totalPrizeLayoutButton = view.findViewById(R.id.totalPrizeLayoutButtonID);
            videoButton = view.findViewById(R.id.videoButtonID);
            roomDetailsButton = view.findViewById(R.id.roomDetailsButtonID);

            mImage = view.findViewById(R.id.mImage);

            progressBar = view.findViewById(R.id.progress);

            mPrizeDetails = view.findViewById(R.id.mPrizeDetails);
            mWinner = view.findViewById(R.id.mWinner);

            mTrue = view.findViewById(R.id.mTrue);

            mDrop1 = view.findViewById(R.id.mDrop1);
            mDrop2 = view.findViewById(R.id.mDrop2);

            mOne = view.findViewById(R.id.mOne);
            mTwo = view.findViewById(R.id.mTwo);
            mThree = view.findViewById(R.id.mThree);

            mLayer = view.findViewById(R.id.mLayer);
            timerText = view.findViewById(R.id.timerTextID);

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

            mPrizeDetails.setOnClickListener(new View.OnClickListener() {
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

            videoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onVideoClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onVideoClickListener.OnVideoClick(position);
                        }
                    }
                }
            });

            /*itemView.seton(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    for (int j = 0; j < adapterView.getChildCount(); j++)
                        adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                    // change the background color of the selected element
                    view.setBackgroundColor(Color.LTGRAY);
                });*/
            //itemView.setOnClickListener(new AdapterView.OnItemClickListener());


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_match_list_layer, parent, false);

        return new MyViewHolder(itemView);
    }

    private OnPrizeClickListener onPrizeClickListener;
    private OnVideoClickListener onVideoClickListener;
    private OnRoomDetailsClickListener onRoomDetailsClickListener;

    public interface OnPrizeClickListener {
        void OnPrizeClick(int position);
    }

    public interface OnRoomDetailsClickListener {
        void OnRoomDetailsClick(int position);
    }

    public interface OnVideoClickListener {
        void OnVideoClick(int position);
    }

    public void setOnClickListener(MatchAdapter.OnPrizeClickListener onPrizeClickListener, OnVideoClickListener onVideoClickListener, MatchAdapter.OnRoomDetailsClickListener onRoomDetailsClickListener) {
        this.onPrizeClickListener = onPrizeClickListener;
        this.onVideoClickListener = onVideoClickListener;
        this.onRoomDetailsClickListener = onRoomDetailsClickListener;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        int pos = position + 1;
        /*CountDownTimer countDownTimer = new CountDownTimer(pos * 60000, 1000) {

            public void onTick(long millisUntilFinished) {
                holder.timerText.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
            }

            public void onFinish() {
                holder.timerText.setText("done!");
            }
        }.start();*/

        holder.mTitle.setText(m.get(position).getTitle());
        holder.mTime.setText(m.get(position).getMatchTime());
        holder.mPerKill.setText(m.get(position).getPerKillRate());
        holder.mEntryFee.setText(m.get(position).getEntryFee());
        if (m.get(position).getPlayerType().toUpperCase().equals("SOLO & DUO")) {
            holder.mType.setText("DUO");
        } else if (m.get(position).getPlayerType().toUpperCase().equals("DUO & SQUAD")) {
            holder.mType.setText("SQUAD");
        } else if (m.get(position).getPlayerType().toUpperCase().equals("SOLO & DUO & SQUAD")) {
            holder.mType.setText("SQUAD");
        } else if (m.get(position).getPlayerType().toUpperCase().equals("SOLO & SQUAD")) {
            holder.mType.setText("SQUAD");
        } else {
            holder.mType.setText(m.get(position).getPlayerType());

        }

        holder.mVersion.setText(m.get(position).getVersion());
        holder.mMap.setText(m.get(position).getMap());
        holder.mTotalPrize.setText(m.get(position).getTotalPrize());

        holder.mLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, String.valueOf(m.get(position).getMatchId()), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context.getApplicationContext(), RulesActivity.class);
                i.putExtra("gameName", m.get(position).getTitle());
                i.putExtra("title", m.get(position).getTitle());
                i.putExtra("type", m.get(position).getGameType());
                i.putExtra("version", m.get(position).getVersion());
                i.putExtra("map", m.get(position).getMap());
                i.putExtra("matchtype", "Paid");
                i.putExtra("entryfee", m.get(position).getEntryFee());
                i.putExtra("time", m.get(position).getMatchTime());
                i.putExtra("winningprize", m.get(position).getTotalPrize());
                i.putExtra("perkill", m.get(position).getPerKillRate());
                i.putExtra("joinedOrNot", String.valueOf(m.get(position).getJoined()));
                i.putExtra("match_id", String.valueOf(m.get(position).getMatchId()));
                i.putExtra("left_place", String.valueOf(m.get(position).getLeftPlayer()));
                i.putExtra("player_type", String.valueOf(m.get(position).getPlayerType()));
                i.putExtra("total_player", String.valueOf(m.get(position).getTotalPlayer()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(i);

            }
        });

        holder.mRoomIdPassShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_live.showPass(String.valueOf(m.get(position).getMatchId()), m.get(position).getTitle(), m.get(position).getMatchTime(), m.get(position).getJoined());
            }
        });


        int booked = Integer.valueOf(m.get(position).getJoinedPlayer());
        int total = Integer.valueOf(m.get(position).getTotalPlayer());

        try {
            int percentage = (booked * 100) / total;
            holder.progressBar.setProgress(percentage);
        } catch (Exception e) {

        }


        int left = total - booked;


        holder.progressBar.getProgressDrawable().setColorFilter(
                context.getResources().getColor(R.color.prgressColor), android.graphics.PorterDuff.Mode.SRC_IN);


        if (left < 0) {
            holder.mSpotLeft.setText("");
            holder.mSpotLeft.setText("0 spots left");
        } else {
            holder.mSpotLeft.setText("");
            holder.mSpotLeft.setText("Only " + left + " spots left");
        }


        holder.mTotalPlayer.setText((booked) + "/" + total);
        holder.mJoin.setText("");
        holder.mJoin.setEnabled(false);


        if (m.get(position).getJoined()) {
            joinedOrNot = "1";
            holder.mJoin.setEnabled(false);
            holder.mJoin.setText("JOINED");
            holder.mJoin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(holder.itemView.getContext(), R.color.dark_green)))));
        } else if (booked == total || booked > total) {
            holder.mJoin.setEnabled(false);
            holder.mJoin.setText("CLOSED");
            holder.mJoin.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            joinedOrNot = "1";
            holder.mJoin.setEnabled(true);
            holder.mJoin.setText("JOIN");
        }
        String joincheck = String.valueOf(m.get(position).getJoined());

        holder.mJoin.setOnClickListener(view -> {

            String text = holder.mJoin.getText().toString();
            //Toast.makeText(holder.itemView.getContext(), String.valueOf(m.get(position).getMatchId()), Toast.LENGTH_SHORT).show();

            if (text.equals("JOIN")) {

                //Toast.makeText(context, m.get(position).getGameType(), Toast.LENGTH_SHORT).show();

                if (m.get(position).getGameType().equals("Daily Scrims(Tournament)")) {
                    Intent i = new Intent(context, Daily_scrims_match_joining_activity.class);
                    i.putExtra("gameName", m.get(position).getTitle());
                    i.putExtra("title", m.get(position).getTitle());
                    i.putExtra("type", m.get(position).getGameType());
                    i.putExtra("version", m.get(position).getVersion());
                    i.putExtra("map", m.get(position).getMap());
                    i.putExtra("matchtype", "Paid");
                    i.putExtra("entryfee", m.get(position).getEntryFee());
                    i.putExtra("time", m.get(position).getMatchTime());
                    i.putExtra("winningprize", m.get(position).getTotalPrize());
                    i.putExtra("perkill", "5");
                    i.putExtra("joinedOrNot", joinedOrNot);
                    i.putExtra("match_id", String.valueOf(m.get(position).getMatchId()));
                    i.putExtra("left_place", String.valueOf(m.get(position).getLeftPlayer()));
                    i.putExtra("player_type", String.valueOf(m.get(position).getPlayerType()));
                    i.putExtra("total_player", String.valueOf(m.get(position).getTotalPlayer()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(i);
                } else {
                    if (holder.mType.getText().toString().toLowerCase().equals("solo")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        Intent i = new Intent(context, Free_fire_solo_match_join_activity.class);
                        i.putExtra("gameName", m.get(position).getTitle());
                        i.putExtra("title", m.get(position).getTitle());
                        i.putExtra("type", m.get(position).getGameType());
                        i.putExtra("version", m.get(position).getVersion());
                        i.putExtra("map", m.get(position).getMap());
                        i.putExtra("matchtype", "Paid");
                        i.putExtra("entryfee", m.get(position).getEntryFee());
                        i.putExtra("time", m.get(position).getMatchTime());
                        i.putExtra("winningprize", m.get(position).getTotalPrize());
                        i.putExtra("perkill", "5");
                        i.putExtra("joinedOrNot", joinedOrNot);
                        i.putExtra("match_id", String.valueOf(m.get(position).getMatchId()));
                        i.putExtra("left_place", String.valueOf(m.get(position).getLeftPlayer()));
                        i.putExtra("player_type", String.valueOf(m.get(position).getPlayerType()));
                        i.putExtra("total_player", String.valueOf(m.get(position).getTotalPlayer()));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);

                    } else if (holder.mType.getText().toString().toLowerCase().equals("duo")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_duo_match_join_activity.class);
                        i.putExtra("gameName", m.get(position).getTitle());
                        i.putExtra("title", m.get(position).getTitle());
                        i.putExtra("type", m.get(position).getGameType());
                        i.putExtra("version", m.get(position).getVersion());
                        i.putExtra("map", m.get(position).getMap());
                        i.putExtra("matchtype", "Paid");
                        i.putExtra("entryfee", m.get(position).getEntryFee());
                        i.putExtra("time", m.get(position).getMatchTime());
                        i.putExtra("winningprize", m.get(position).getTotalPrize());
                        i.putExtra("perkill", "5");
                        i.putExtra("joinedOrNot", joinedOrNot);
                        i.putExtra("match_id", String.valueOf(m.get(position).getMatchId()));
                        i.putExtra("left_place", String.valueOf(m.get(position).getLeftPlayer()));
                        i.putExtra("player_type", String.valueOf(m.get(position).getPlayerType()));
                        i.putExtra("total_player", String.valueOf(m.get(position).getTotalPlayer()));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    } else if (holder.mType.getText().toString().toLowerCase().equals("squad")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_squad_match_join_activity.class);
                        i.putExtra("gameName", m.get(position).getTitle());
                        i.putExtra("title", m.get(position).getTitle());
                        i.putExtra("type", m.get(position).getGameType());
                        i.putExtra("version", m.get(position).getVersion());
                        i.putExtra("map", m.get(position).getMap());
                        i.putExtra("matchtype", "Paid");
                        i.putExtra("entryfee", m.get(position).getEntryFee());
                        i.putExtra("time", m.get(position).getMatchTime());
                        i.putExtra("winningprize", m.get(position).getTotalPrize());
                        i.putExtra("perkill", "5");
                        i.putExtra("joinedOrNot", joinedOrNot);
                        i.putExtra("match_id", String.valueOf(m.get(position).getMatchId()));
                        i.putExtra("left_place", String.valueOf(m.get(position).getLeftPlayer()));
                        i.putExtra("player_type", String.valueOf(m.get(position).getPlayerType()));
                        i.putExtra("total_player", String.valueOf(m.get(position).getTotalPlayer()));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    } else if (holder.mType.getText().toString().equals("6 VS 6")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_six_vs_six_match_join_activity.class);
                        i.putExtra("gameName", m.get(position).getTitle());
                        i.putExtra("title", m.get(position).getTitle());
                        i.putExtra("type", m.get(position).getGameType());
                        i.putExtra("version", m.get(position).getVersion());
                        i.putExtra("map", m.get(position).getMap());
                        i.putExtra("matchtype", "Paid");
                        i.putExtra("entryfee", m.get(position).getEntryFee());
                        i.putExtra("time", m.get(position).getMatchTime());
                        i.putExtra("winningprize", m.get(position).getTotalPrize());
                        i.putExtra("perkill", "5");
                        i.putExtra("joinedOrNot", joinedOrNot);
                        i.putExtra("match_id", String.valueOf(m.get(position).getMatchId()));
                        i.putExtra("left_place", String.valueOf(m.get(position).getLeftPlayer()));
                        i.putExtra("player_type", String.valueOf(m.get(position).getPlayerType()));
                        i.putExtra("total_player", String.valueOf(m.get(position).getTotalPlayer()));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    } else if (holder.mType.getText().toString().equals("1 VS 1")) {
                        //holder.mType.setText(m.get(position).getPlayerType());
                        // Toast.makeText(holder.itemView.getContext(), "duo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Free_fire_solo_match_join_activity.class);
                        i.putExtra("gameName", m.get(position).getTitle());
                        i.putExtra("title", m.get(position).getTitle());
                        i.putExtra("type", m.get(position).getGameType());
                        i.putExtra("version", m.get(position).getVersion());
                        i.putExtra("map", m.get(position).getMap());
                        i.putExtra("matchtype", "Paid");
                        i.putExtra("entryfee", m.get(position).getEntryFee());
                        i.putExtra("time", m.get(position).getMatchTime());
                        i.putExtra("winningprize", m.get(position).getTotalPrize());
                        i.putExtra("perkill", "5");
                        i.putExtra("joinedOrNot", joinedOrNot);
                        i.putExtra("match_id", String.valueOf(m.get(position).getMatchId()));
                        i.putExtra("left_place", String.valueOf(m.get(position).getLeftPlayer()));
                        i.putExtra("player_type", String.valueOf(m.get(position).getPlayerType()));
                        i.putExtra("total_player", String.valueOf(m.get(position).getTotalPlayer()));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(i);
                    }
                }


            } else if (text.equals("JOINED")) {

                Toasty.error(context, "You are joined already").show();
            } else if (text.equals("CLOSED")) {

                Toasty.error(context, "Match Closed").show();
            }


        });


        //TextView lengthText = holder.itemView.findViewById(R.id.lengthTextID);

        String text = m.get(position).getMatchTime();
        String wordToFind = " at ";
        Pattern word = Pattern.compile(wordToFind);
        Matcher match = word.matcher(text);

        String matchDate, matchTime;

        while (match.find()) {

            //get_start_time(holder.timerText, matchDate, matchTime);
            //lengthText.setText(text.substring(0, match.start()) + " " + text.substring(match.end(), text.length()));
            matchDate = text.substring(0, match.start());
            matchTime = text.substring(match.end(), text.length());
            get_start_time(holder.timerText, matchDate, matchTime);
        }


    }

    @Override
    public int getItemCount() {
        return m.size();
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


    public void clear() {
        int size = m.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                m.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    private void startTimer() {


    }


}
