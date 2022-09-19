package com.example.erashop.Model;

public class CartModel {
    String id,quantity,price;

    public CartModel(String id, String quantity, String price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
