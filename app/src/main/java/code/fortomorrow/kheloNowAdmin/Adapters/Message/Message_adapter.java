package code.fortomorrow.kheloNowAdmin.Adapters.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapters.Ludo.Ludo_game_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Message.Message_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Message_adapter extends RecyclerView.Adapter<Message_adapter.ViewHolder> {
    private List<Message_response.M> messageList;
    private final static int FADE_DURATION = 1000;

    public Message_adapter(List<Message_response.M> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public Message_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.message_card, parent, false);
        return new Message_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Message_adapter.ViewHolder holder, int position) {
        Message_response.M response = messageList.get(position);
        holder.messageText.setText(response.message);
        holder.dateText.setText(response.date);

        startIntroAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, dateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void startIntroAnimation(View view) {
        view.setTranslationY(view.getHeight());
        view.setAlpha(0f);
        view.animate()
                .translationY(0)
                .setDuration(400)
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}
