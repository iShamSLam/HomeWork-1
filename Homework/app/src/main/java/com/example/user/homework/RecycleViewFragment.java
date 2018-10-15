package com.example.user.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.user.homework.Player_Adapter.currDataPos;

public class RecycleViewFragment extends Fragment {

    private static List<Player> players = new ArrayList<>();
    public static Player_Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle,
                container, false);
        setInitialData();
        RecyclerView recyclerView = view.findViewById(R.id.players);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new Player_Adapter(players, v -> {
            Context context = getContext();
            Intent intent = new Intent(context, InformationActivity.class);
            intent.putExtra("name", players.get(players.get(currDataPos).getId()).getName());
            intent.putExtra("surname", players.get(players.get(currDataPos).getId()).getSurname());
            intent.putExtra("id", players.get(players.get(currDataPos).getId()).getImage());
            intent.putExtra("height", players.get(players.get(currDataPos).getId()).getHeight());
            context.startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public static List<Player> DownSorter(Comparator<Player> T1) {
        List<Player> result = new ArrayList<>();
        for (Player player : players
                ) {
            result.add(player);
        }
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setId(i);
        }
        Collections.sort(result, Collections.reverseOrder(T1));
        return result;
    }

    public static List<Player> UpSorter(Comparator<Player> T1) {
        List<Player> result = new ArrayList<>();
        for (Player player : players) {
            result.add(player);
        }
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setId(i);
        }
        Collections.sort(result, T1);
        return result;
    }

    private void setInitialData() {

        players.add(new Player("Кевин", "Гарнет", R.drawable.kevin, 211, 0));
        players.add(new Player("Чарльз", "Баркли", R.drawable.charles, 198, 1));
        players.add(new Player("Скотти", "Пиппен", R.drawable.scotti, 203, 2));
        players.add(new Player("Деннис", "Родман", R.drawable.dennis_rodman, 201, 3));
        players.add(new Player("Тим", "Данкан", R.drawable.duncan, 211, 4));
        players.add(new Player("Леброн", "Джеймс", R.drawable.james, 203, 5));
        players.add(new Player("Карл", "Мэлоун", R.drawable.karlm, 206, 6));
        players.add(new Player("Коби", "Брайант", R.drawable.kobi, 198, 7));
        players.add(new Player("Шакил", "О'Нил", R.drawable.shq, 216, 8));
        players.add(new Player("Майл", "Джордан", R.drawable.michaeljordan, 198, 9));
    }
}
