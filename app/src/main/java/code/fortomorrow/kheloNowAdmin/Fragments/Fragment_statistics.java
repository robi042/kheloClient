package code.fortomorrow.kheloNowAdmin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import code.fortomorrow.kheloNowAdmin.Activities.DailyMatchActivity;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.TabControl.TabLayoutControl;

public class Fragment_statistics extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        init_view(view);


        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager());
        viewPageAdapter.addFragment(new Statistics_fragment_scrims(), "Scrims");
        viewPageAdapter.addFragment(new Statistics_fragment_freefire(), "Free Fire");
        viewPageAdapter.addFragment(new Statistics_fragment_ludo(), "Ludo");
        viewPageAdapter.addFragment(new Statistics_fragment_arena_of_valor(), "Arena of Valor");

        //viewPager.setCurrentItem(1);
        //viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();

        //TabLayoutControl.enableTabs(tabLayout, false);

        return view;
    }

    private void init_view(View view) {
        tabLayout = view.findViewById(R.id.tab_layoutID);
        viewPager = view.findViewById(R.id.view_pagerLayoutID);
    }

    class ViewPageAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}