package com.example.erashop.Model;

public class HomeItemModel {

    String name;
    String image;

    public HomeItemModel(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
