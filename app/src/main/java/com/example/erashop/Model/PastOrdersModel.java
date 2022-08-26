package com.example.erashop.Model;

public class PastOrdersModel {
    String name,qty;
    int image;

    public PastOrdersModel(String name, String qty, int image) {
        this.name = name;
        this.qty = qty;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
