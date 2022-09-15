package com.example.erashop.Model;

public class WishlistModel {
    private String id,category_id,sub_category_id,item_name,item_size,item_price,item_OG_price,item_discount, image;

    public WishlistModel(String id, String category_id, String sub_category_id, String item_name, String item_size, String item_price, String item_OG_price, String item_discount, String image) {
        this.id = id;
        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
        this.item_name = item_name;
        this.item_size = item_size;
        this.item_price = item_price;
        this.item_OG_price = item_OG_price;
        this.item_discount = item_discount;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
