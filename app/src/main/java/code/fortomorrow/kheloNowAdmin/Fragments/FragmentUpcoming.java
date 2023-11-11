package code.fortomorrow.kheloNowAdmin.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import code.fortomorrow.kheloNowAdmin.Activities.Upcoming_match_activity;
import code.fortomorrow.kheloNowAdmin.R;


public class FragmentUpcoming extends Fragment {
    CardView freeFireRegularButton, ludoUpcomingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        init_view(view);

        freeFireRegularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Upcoming_match_activity.class);
                intent.putExtra("type", "free_fire");
                startActivity(intent);
            }
        });

        ludoUpcomingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Upcoming_match_activity.class);
                intent.putExtra("type", "ludo");
                startActivity(intent);
            }
        });

        return view;
    }

    private void init_view(View view) {
        freeFireRegularButton = view.findViewById(R.id.freeFireRegularButtonID);
        ludoUpcomingButton = view.findViewById(R.id.ludoUpcomingButtonID);
    }
}