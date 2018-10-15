package com.example.user.homework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class VPFragment extends Fragment {

    ViewPager pager;
    PagerAdapter pagerAdapter;
    static final int PAGE_COUNT = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phrases = Phraser();
    }

    public static List<String> phrases;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager, container, false);
        pager = view.findViewById(R.id.vp_main);
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        return view;
    }

    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Страница  " + phrases.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(phrases.get(position));
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
    public List<String> Phraser(){
        List<String> phrases = new ArrayList<>();
        phrases.add("SHRECK");
        phrases.add("OS!EL");
        phrases.add("SAMMY");
        return phrases;
    }
}



