package com.example.erashop.Model;

public class SubCategoryModel {
    String name,image, Sub_Category_id,Category_id;

    public SubCategoryModel(String name, String image, String id,String Category_id) {
        this.name = name;
        this.image = image;
        this.Sub_Category_id = id;
        this.Category_id = Category_id;
    }

    public String getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(String category_id) {
        Category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSub_Category_id() {
        return Sub_Category_id;
    }

    public void setSub_Category_id(String sub_Category_id) {
        this.Sub_Category_id = sub_Category_id;
    }
}
