package com.example.user.homework;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Player_Adapter extends RecyclerView.Adapter<Player_Adapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Player> players;

    Player_Adapter(Context context, List<Player> players) {
        this.players = players;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Player_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Player_Adapter.ViewHolder holder, int position) {
        Player player = players.get(position);
        holder.imageView.setImageResource(player.getImage());
        holder.surnameView.setText(player.getSurname());
        holder.nameView.setText(player.getName());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, surnameView;
        int id;
        Drawable drawable;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_pict);
            nameView = itemView.findViewById(R.id.name);
            surnameView = itemView.findViewById(R.id.surname);

            itemView.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, Information.class);
                int j=0;
                for (int i = 0; i < players.size(); i++) {
                    if (nameView.getText() == players.get(i).getName())
                        j = i;
                }
                intent.putExtra("name", nameView.getText());
                intent.putExtra("surname", surnameView.getText());
                intent.putExtra("id", players.get(j).getImage());
                context.startActivity(intent);
            });
        }
    }
}



