package com.example.user.homework;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Recycle extends Fragment {

     List<Player> players = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle,
                container, false);
        setInitialData();
        RecyclerView recyclerView = view.findViewById(R.id.players);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        RecyclerView.ItemDecoration itm =
                new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itm);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Player_Adapter adapter = new Player_Adapter(getContext(), players);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return view;

    }
    private void setInitialData() {

        players.add(new Player("Кевин", "Гарнет", R.drawable.kevin));
        players.add(new Player("Чарльз", "Баркли", R.drawable.charles));
        players.add(new Player("Скотти", "Пиппен", R.drawable.scotti));
        players.add(new Player("Деннис", "Родман", R.drawable.dennis_rodman));
        players.add(new Player("Тим", "Данкан", R.drawable.duncan));
        players.add(new Player("Леброн", "Джеймс", R.drawable.james));
        players.add(new Player("Карл", "Мэлоун", R.drawable.karlm));
        players.add(new Player("Коби", "Брайант", R.drawable.kobi));
        players.add(new Player("Шакил", "О'Нил", R.drawable.shq));
        players.add(new Player("Майл", "Джордан", R.drawable.michaeljordan));
    }

}