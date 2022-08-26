package com.example.erashop.Model;

public class WishlistModel {
    private String item_name,item_size,item_price,item_OG_price,item_discount;
    private int image;

    public WishlistModel(String item_name, String item_size, String item_price, String item_OG_price, String item_discount, int image) {
        this.item_name = item_name;
        this.item_size = item_size;
        this.item_price = item_price;
        this.item_OG_price = item_OG_price;
        this.item_discount = item_discount;
        this.image = image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_OG_price() {
        return item_OG_price;
    }

    public void setItem_OG_price(String item_OG_price) {
        this.item_OG_price = item_OG_price;
    }

    public String getItem_discount() {
        return item_discount;
    }

    public void setItem_discount(String item_discount) {
        this.item_discount = item_discount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
