package com.example.erashop.Model;

public class SearchModel {
    String name,price,OG_price;
    int image;

    public SearchModel(String name, String price, String OG_price, int image) {
        this.name = name;
        this.price = price;
        this.OG_price = OG_price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOG_price() {
        return OG_price;
    }

    public void setOG_price(String OG_price) {
        this.OG_price = OG_price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
