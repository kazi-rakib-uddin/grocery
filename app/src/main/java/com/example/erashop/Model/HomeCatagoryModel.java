package com.example.erashop.Model;

public class HomeCatagoryModel {

    String name;
    int image;

    public HomeCatagoryModel(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
