package code.fortomorrow.kheloNowAdmin.Adapters;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import code.fortomorrow.kheloNowAdmin.Activities.ResultDetailsActivity;
import code.fortomorrow.kheloNowAdmin.Activities.RulesActivity;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.Free_fire_paginated_result_response;
import code.fortomorrow.kheloNowAdmin.Model.GetResults.M;
import code.fortomorrow.kheloNowAdmin.R;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.Viewholder> {
    private List<Free_fire_paginated_result_response.M> mList;


    public ResultAdapter(List<Free_fire_paginated_result_response.M> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_layer, parent, false);
        return new Viewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mTime.setText(mList.get(position).getMatchTime());
        holder.mTotalPrize.setText(mList.get(position).getTotalPrize());
        holder.mType.setText(mList.get(position).getGameType());
        holder.mPerKill.setText(mList.get(position).getPerKillRate());
        holder.mEntryFee.setText(mList.get(position).getEntryFee());
        holder.mVersion.setText(mList.get(position).getVersion());
        holder.mMap.setText(mList.get(position).getMap());
        holder.mMap.setText(mList.get(position).getMap());
        holder.mMap.setText(mList.get(position).getMap());


        if (!mList.get(position).getTotalPlayer().equals("")) {

            holder.mPrizeDetails.setVisibility(View.VISIBLE);
            holder.mOne.setText("Winner - " + mList.get(position).getFirstPrize() + " Tk");
            holder.mTwo.setText("Runner Up 1 - " + mList.get(position).getSecondPrize() + " Tk");
            holder.mThree.setText("Runner Up 2 - " + mList.get(position).getThirdPrize() + " Tk");


        } else {

            holder.mPrizeDetails.setVisibility(View.GONE);
            holder.mWinner.setVisibility(View.GONE);

        }


        if (mList.get(position).getJoined()) {
            holder.mJoined.setText("JOINED");
            holder.mNotJoinedBackground.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.green));
            holder.mJoined.setText("JOINED");
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnFreeFireItemClickListener onFreeFireItemClickListener;

    private OnPrizeClickListener onPrizeClickListener;

    private OnWatchClickListener onWatchClickListener;

    public interface OnWatchClickListener {
        void OnWatchClick(int position);
    }

    public interface OnPrizeClickListener {
        void OnPrizeClick(int position);
    }

    public interface OnFreeFireItemClickListener {
        void OnFreeFireItemClick(int position);
    }

    public void setOnClickListener(ResultAdapter.OnPrizeClickListener onPrizeClickListener, ResultAdapter.OnWatchClickListener onWatchClickListener, ResultAdapter.OnFreeFireItemClickListener onItemClickListener) {
        this.onPrizeClickListener = onPrizeClickListener;
        this.onWatchClickListener = onWatchClickListener;
        this.onFreeFireItemClickListener = onItemClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTitle, mTime, mTotalPrize, mType, mPerKill, mVersion, mEntryFee, mMap, mOne, mTwo, mThree, mJoined;
        private LinearLayout mWinner, mLayer;
        private ImageView mDrop1, mDrop2;
        private View view;
        private CardView mPrizeDetails, mNotJoinedBackground, mWatchMatch;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTitle = itemView.findViewById(R.id.mTitle);
            mNotJoinedBackground = itemView.findViewById(R.id.mNotJoinedBackground);
            mTime = itemView.findViewById(R.id.mTime);
            mTotalPrize = itemView.findViewById(R.id.mTotalPrize);
            mType = itemView.findViewById(R.id.mType);
            mPerKill = itemView.findViewById(R.id.mPerKill);
            mVersion = itemView.findViewById(R.id.mVersion);
            mEntryFee = itemView.findViewById(R.id.mEntryFee);
            mMap = itemView.findViewById(R.id.mMap);
            mWatchMatch = itemView.findViewById(R.id.mWatchMatch);
            mPrizeDetails = itemView.findViewById(R.id.mPrizeDetails);
            mOne = itemView.findViewById(R.id.mOne);
            mTwo = itemView.findViewById(R.id.mTwo);
            mThree = itemView.findViewById(R.id.mThree);
            mWinner = itemView.findViewById(R.id.mWinner);
            mDrop1 = itemView.findViewById(R.id.mDrop1);
            mDrop2 = itemView.findViewById(R.id.mDrop2);
            mWinner = itemView.findViewById(R.id.mWinner);
            mJoined = itemView.findViewById(R.id.mJoined);
            mLayer = itemView.findViewById(R.id.mLayer);

            mPrizeDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onPrizeClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onPrizeClickListener.OnPrizeClick(position);
                        }
                    }

                    //Toast.makeText(itemView.getContext(), "hello", Toast.LENGTH_SHORT).show();
                }
            });

            mWatchMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onWatchClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onWatchClickListener.OnWatchClick(position);
                        }
                    }
                }
            });

            mLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onFreeFireItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onFreeFireItemClickListener.OnFreeFireItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
