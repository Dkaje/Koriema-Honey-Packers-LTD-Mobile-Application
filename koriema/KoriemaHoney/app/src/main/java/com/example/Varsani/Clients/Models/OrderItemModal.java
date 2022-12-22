package com.example.Varsani.Clients.Models;

public class OrderItemModal {
    String itemName;
    String itemPrice;
    String quantity;
    String subTotal;

    public OrderItemModal(String itemName,String itemPrice,String quantity,String subTotal){
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.quantity=quantity;
        this.subTotal=subTotal;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }
}
