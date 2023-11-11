package code.fortomorrow.kheloNowAdmin.Adapters;

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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.M;
import code.fortomorrow.kheloNowAdmin.R;

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.Viewholder> {
    private List<M> mList;
    private Context context;

    public OngoingAdapter(List<M> mList) {
        this.mList = mList;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.ongoing_layer, parent, false);
        //return new Viewholder(view);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ongoing_layer, parent, false);
        return new OngoingAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
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
            holder.mOne.setText("Winner - " + mList.get(position).getFirstPrize() + " Taka");
            holder.mTwo.setText("Runner Up 1 - " + mList.get(position).getSecondPrize() + " Taka");
            holder.mThree.setText("Runner Up 2 - " + mList.get(position).getThirdPrize() + " Taka");


        } else {

            holder.mPrizeDetails.setVisibility(View.GONE);
            holder.mWinner.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnPrizeClickListener onPrizeClickListener;
    private OnWatchClickListener onWatchClickListener;

    public interface OnWatchClickListener {
        void OnWatchClick(int position);
    }

    public interface OnPrizeClickListener {
        void OnPrizeClick(int position);
    }

    public void setOnClickListener(OngoingAdapter.OnPrizeClickListener onPrizeClickListener, OngoingAdapter.OnWatchClickListener onWatchClickListener) {
        this.onPrizeClickListener = onPrizeClickListener;
        this.onWatchClickListener = onWatchClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTitle, mTime, mTotalPrize, mType, mPerKill, mVersion, mEntryFee, mMap, mOne, mTwo, mThree, mTrue;
        private LinearLayout mPrizeDetails, mWinner, mWatchMatch;
        private ImageView mDrop1, mDrop2;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.mTitle);
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
            mTrue = itemView.findViewById(R.id.mTrue);
            mWinner = itemView.findViewById(R.id.mWinner);
            mDrop1 = itemView.findViewById(R.id.mDrop1);
            mDrop2 = itemView.findViewById(R.id.mDrop2);
            mWinner = itemView.findViewById(R.id.mWinner);

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
        }
    }
}
