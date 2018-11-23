package com.example.user.homework;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class PlayersListDiffCallBack extends DiffUtil.Callback {

    private List<Player> mOldList;
    private List<Player> mNewList;

    public PlayersListDiffCallBack(List<Player> oldList, List<Player> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList != null ? mOldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewList != null ? mNewList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldPos, int newPos) {
        return
                (mNewList.get(newPos).getName() == mOldList.get(oldPos).getName()) &&
                        (mNewList.get(newPos).getSurname() == mOldList.get(oldPos).getSurname());
    }

    @Override
    public boolean areContentsTheSame(int oldPos, int newPos) {
        return mNewList.get(newPos).equals(mOldList.get(oldPos));
    }
}
