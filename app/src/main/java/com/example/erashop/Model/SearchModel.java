package com.example.erashop.Model;

public class SearchModel {
    String product_id,name,price,OG_price,image,discount;

    public SearchModel(String product_id,String name, String price, String OG_price, String image,String discount) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.OG_price = OG_price;
        this.image = image;
        this.discount = discount;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
