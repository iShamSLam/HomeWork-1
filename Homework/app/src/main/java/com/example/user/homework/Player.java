package com.example.user.homework;

import java.util.Comparator;

public class Player {
    private String name;
    private String surname;
    private int image;
    private int Id;
    private int height;

    public Player(String name, String surname, int image, int height, int id) {
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.height = height;
        this.Id = id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getId() {
        return this.Id;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static final Comparator<Player> COMPARE_BY_NAME = (player, t1)
            -> player.getName().compareTo(t1.getName());
    public static final Comparator<Player> COMPARE_BY_HEIGHT = (player, t1) -> {
        if (player.getHeight() > t1.getHeight())
            return 1;
        else return -1;
    };
}
