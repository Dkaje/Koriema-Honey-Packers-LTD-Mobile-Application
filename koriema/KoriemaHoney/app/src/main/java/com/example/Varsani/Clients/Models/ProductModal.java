package com.example.Varsani.Clients.Models;


public class ProductModal {

    private String productID;
    private String productName;
    private String stock;
    private String price;
    private String imgUrl;
    private String desc;


    public ProductModal() {
    }

    public ProductModal(String productID, String productName, String stock, String price, String imgUrl,String desc) {
        this.productID = productID;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.imgUrl = imgUrl;
        this.desc = desc;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getStock() {
        return stock;
    }

    public String getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDesc() {
        return desc;
    }
}
