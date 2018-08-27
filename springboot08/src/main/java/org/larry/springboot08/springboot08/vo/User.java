package org.larry.springboot08.springboot08.vo;

import javafx.scene.paint.Color;

public class User {


    private Color favoriteColor;

    public User(Color favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public Color getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(Color favoriteColor) {
        this.favoriteColor = favoriteColor;
    }
}
