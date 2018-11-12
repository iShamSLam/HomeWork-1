package com.example.user.homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recycle extends AppCompatActivity {

    private Track_Adapter.ClickCallBack callback;
    List<MusicSpace.Track> tracks = new ArrayList<>();
    static int repaint = 1;

    public Recycle() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int currentTheme = sharedPref.getInt("number", repaint);
        if(repaint<=3)
            repaint+=1;
        else
            repaint = 1;
        int themeId;
        switch (currentTheme) {
            case 2:
                themeId = R.style.Theme_2;
                break;
            case 3:
                themeId = R.style.Theme_3;
                break;
            case 4:
                themeId = R.style.Theme4;
                break;
            default:
                themeId = R.style.Theme_1;
        }
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview);
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.tracks);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        Track_Adapter adapter = new Track_Adapter(tracks, () -> {
            Intent intent = new Intent(this, Information.class);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        findViewById(R.id.floatingActionButton).setOnClickListener(v ->
                this.recreate()
        );

    }

    private void setInitialData() {
        tracks.add(new MusicSpace.Track("Only you", "Metro Boomin feat. Wizkid, Offset", R.raw.only_you_feat_wizkid_offset));
        tracks.add(new MusicSpace.Track("Only 1", "Metro Boomin feat. Travis Scott", R.raw.only_1_feat_travis));
        tracks.add(new MusicSpace.Track("Borrowed Love", "Metro Boomin feat. Swae Lee", R.raw.borrowed_love_feat_swae_lee));
        tracks.add(new MusicSpace.Track("Lesbian", "Metro Boomin feat. Gunna and Young Thug", R.raw.lesbian_feat_gunna_young_thug));
        tracks.add(new MusicSpace.Track("No More", "Metro Boomin feat. Travis Scott and Kodak Black", R.raw.no_more_feat_travis_scott_kodad_black));
    }
}