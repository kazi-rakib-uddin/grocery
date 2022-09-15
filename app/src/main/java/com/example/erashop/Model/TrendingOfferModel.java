package com.example.erashop.Model;

public class TrendingOfferModel {
    String id,product_name,product_image,product_title_image,trending_status,discount_percentage,category_id,sub_category_id;

    public TrendingOfferModel(String id, String product_name, String product_image, String product_title_image,String trending_status,String discount_percentage,String category_id,String sub_category_id) {
        this.id = id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_title_image = product_title_image;
        this.trending_status = trending_status;
        this.discount_percentage = discount_percentage;
        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
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

    public String getProduct_title_image() {
        return product_title_image;
    }

    public void setProduct_title_image(String product_title_image) {
        this.product_title_image = product_title_image;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(String discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getTrending_status() {
        return trending_status;
    }

    public void setTrending_status(String trending_status) {
        this.trending_status = trending_status;
    }
}
