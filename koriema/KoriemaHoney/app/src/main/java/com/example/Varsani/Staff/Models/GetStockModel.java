package com.example.Varsani.Staff.Models;

public class GetStockModel {
    String stockID;
    String productName;
    String stock;
    String price;

    public GetStockModel(String stockID, String productName, String stock,String price){
        this.productName=productName;
        this.stock=stock;
        this.stockID=stockID;
        this.price=price;
    }

    public String getStockID() {
        return stockID;
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
}
