package com.example.erashop.Model;

public class OrderDetailsModel {
    String product_id,quantity,amount,date;

    public OrderDetailsModel(String product_id, String quantity, String amount, String date) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.amount = amount;
        this.date = date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
