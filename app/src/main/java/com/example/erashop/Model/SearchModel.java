package com.example.erashop.Model;

public class SearchModel {
    String cat_id,sub_cat_id,product_id,name,price,OG_price,image,discount,quantity;

    public SearchModel(String cat_id,String sub_cat_id,String product_id,String name, String price, String OG_price, String image,String discount,String quantity) {
        this.cat_id = cat_id;
        this.sub_cat_id = sub_cat_id;
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.OG_price = OG_price;
        this.image = image;
        this.discount = discount;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
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
