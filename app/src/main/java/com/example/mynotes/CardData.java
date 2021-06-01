package com.example.mynotes;

public class CardData {
    private String title;
    private int picture;
    private boolean like;
    private String description;
    //alt+shift и тыкать курсоры - это мультикурсор


    public CardData(String title, String description, int picture,  boolean like) {
        this.title = title;
        this.picture = picture;
        this.like = like;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getPicture() {
        return picture;
    }

    public boolean isLike() {
        return like;
    }

    public String getDescription() {
        return description;
    }
}
