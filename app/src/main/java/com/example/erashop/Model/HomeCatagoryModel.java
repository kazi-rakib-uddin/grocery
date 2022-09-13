package com.example.erashop.Model;

public class HomeCatagoryModel {

    String name;
    String category_image,category_id;
    int image;

    public HomeCatagoryModel(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public HomeCatagoryModel(String name, String category_image,String Category_id) {
        this.name = name;
        this.category_image = category_image;
        this.category_id = Category_id;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

}
