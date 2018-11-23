package com.example.user.homework;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

import static com.example.user.homework.RecycleViewFragment.Sorter1;

public class Player_Adapter extends RecyclerView.Adapter<Player_Adapter.ViewHolder> {
    private static List<Player> players;

    Player_Adapter(List<Player> players, ClickCallBack clickCallBack) {
        this.players = players;
        this.callBack = clickCallBack;
    }

    public static void SetData(List<Player> list) {
        Player_Adapter.players = list;
    }

    private ClickCallBack callBack;
    public static int currDataPos;

    @NonNull
    @Override
    public Player_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Player_Adapter.ViewHolder holder, int position) {
        Player player = players.get(position);
        holder.imageView.setImageResource(player.getImage());
        holder.surnameView.setText(player.getSurname());
        holder.nameView.setText(player.getName());
        holder.cv.setOnClickListener(v -> {
            currDataPos = player.getId();
            callBack.onClick(player.getId());
        });
    }

    public void UpdateUp(Comparator<Player> T1) {
        List<Player> temp = Sorter1(T1);
        PlayersListDiffCallBack diffCallBack =
                new PlayersListDiffCallBack(players, temp);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallBack);
        result.dispatchUpdatesTo(this);
        players = temp;
    }

    public void UpdateDown(Comparator<Player> T1) {
        List<Player> temp = Sorter1(T1);
        PlayersListDiffCallBack diffCallBack =
                new PlayersListDiffCallBack(players, temp);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallBack);
        result.dispatchUpdatesTo(this);
        players = temp;
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final CardView cv;
        final TextView nameView, surnameView;
        int id;
        Drawable drawable;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_pict);
            nameView = itemView.findViewById(R.id.name);
            surnameView = itemView.findViewById(R.id.surname);
            cv = itemView.findViewById(R.id.cv);
        }
    }

    interface ClickCallBack {
        void onClick(int position);
    }
}
